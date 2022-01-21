import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Table } from 'src/modules/root/models/table';
import { Zone } from 'src/modules/root/models/zone';
import { ZoneManagerService } from '../../services/zone-manager.service';

@Component({
  selector: 'app-zones-manager',
  templateUrl: './zones-manager.component.html',
  styleUrls: ['./zones-manager.component.scss'],
})
export class ZonesManagerComponent implements OnInit {
  displayedColumnsTablesForZone: string[];
  zones: Array<Zone>;
  currentPage: number;
  pageSize: number;
  totalRows: number;
  tablesForZone: Array<Table>;
  tablesForZoneDataSource: MatTableDataSource<Table>;
  isLoading: boolean;
  selectedZone: Zone;
  selectedTable: Table;
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  verticalPosition: MatSnackBarVerticalPosition;

  constructor(
    private zonesManagerService: ZoneManagerService,
    private snackBar: MatSnackBar,
    private createNewZoneDialog: MatDialog,
    private addTableToSelectedZoneDialog: MatDialog,
    private editTableSelectedZoneDialog: MatDialog
  ) {
    this.currentPage = 0;
    this.pageSize = 10;
    this.totalRows = 0;
    this.zones = [];
    this.tablesForZone = [];
    this.displayedColumnsTablesForZone = ['numberOfChairs', 'edit', 'remove'];
    this.tablesForZoneDataSource = new MatTableDataSource<Table>(
      this.tablesForZone
    );
    this.isLoading = false;
    this.selectedZone = new Zone();
    this.selectedTable = new Table();
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
  }

  ngOnInit(): void {
    this.getZones();
  }

  ngAfterViewInit(): void {
    this.tablesForZoneDataSource.paginator = this.paginatorTablesInZone;
  }

  @ViewChild(MatPaginator)
  paginatorTablesInZone!: MatPaginator;

  @ViewChild('newZoneDialog') newZoneDialog!: TemplateRef<any>;

  @ViewChild('addTableDialog') addTableDialog!: TemplateRef<any>;

  @ViewChild('editTableDialog') editTableDialog!: TemplateRef<any>;

  getZones(): void {
    this.zonesManagerService.getZones().subscribe((data) => {
      this.zones = data;
      if (data.length !== 0) {
        this.selectedZone = data[0];
        this.getNumberOfTablesForZoneRecords(data[0].id);
        this.getTablesForZoneByCurrentPage(data[0].id, false);
      } else {
        this.selectedZone = new Zone();
        this.currentPage = 0;
        this.totalRows = 0;
        this.tablesForZoneDataSource.data = [];
      }
    });
  }

  getNumberOfTablesForZoneRecords(zoneId: number): void {
    this.zonesManagerService
      .getNumberOfTablesForZoneRecords(zoneId)
      .subscribe((data) => {
        this.totalRows = data;
      });
  }

  getTablesForZoneByCurrentPage(zoneId: number, deleting: boolean): void {
    this.isLoading = true;
    this.zonesManagerService
      .getTablesForZoneByCurrentPage(zoneId, this.currentPage, this.pageSize)
      .subscribe((data) => {
        const tablesForZone = data;
        this.tablesForZone = tablesForZone;
        this.tablesForZoneDataSource.data = tablesForZone;
        setTimeout(() => {
          if (tablesForZone.length === 0 && deleting) {
            if (this.currentPage !== 0) {
              const setPrev = this.currentPage - 1;
              this.currentPage = setPrev;
              this.paginatorTablesInZone.pageIndex = setPrev;
            }
            this.getTablesForZoneByCurrentPage(zoneId, false);
          } else {
            this.paginatorTablesInZone.pageIndex = this.currentPage;
          }
          this.paginatorTablesInZone.length = this.totalRows;
        });
        this.isLoading = false;
      });
  }

  changeZone(zoneId: number): void {
    this.selectedZone = this.zones.filter((z) => z.id === zoneId)[0];
    this.currentPage = 0;
    this.getNumberOfTablesForZoneRecords(zoneId);
    this.getTablesForZoneByCurrentPage(zoneId, false);
  }

  openCreateNewZoneDialog(): void {
    this.createNewZoneDialog.open(this.newZoneDialog);
  }

  createNewZone(zone: Zone): void {
    this.zonesManagerService.createNewZone(zone).subscribe(
      (resp) => {
        console.log(resp);
        this.createNewZoneDialog.closeAll();
        this.getZones();
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  openAddTableDialog(): void {
    this.addTableToSelectedZoneDialog.open(this.addTableDialog);
  }

  openEditTableDialog(table: Table): void {
    this.selectedTable = table;
    this.editTableSelectedZoneDialog.open(this.editTableDialog);
  }

  addTableToSelectedZone(numberOfChairs: number = 0): void {
    let table = new Table();
    table['numberOfChairs'] = numberOfChairs;
    table['zoneId'] = this.selectedZone.id;
    this.zonesManagerService.addTable(table).subscribe(
      (resp) => {
        console.log(resp);
        this.addTableToSelectedZoneDialog.closeAll();
        this.getNumberOfTablesForZoneRecords(this.selectedZone.id);
        this.getTablesForZoneByCurrentPage(this.selectedZone.id, false);
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  editNumberOfChairs(numberOfChairs: number = 0): void {
    let table = this.selectedTable;
    table.numberOfChairs = numberOfChairs;
    this.zonesManagerService.updateNumberOfChairs(table).subscribe(
      (resp) => {
        console.log(resp);
        this.editTableSelectedZoneDialog.closeAll();
        this.getTablesForZoneByCurrentPage(this.selectedZone.id, false);
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  removeTableFromZone(table: Table): void {
    this.zonesManagerService.removeTableFromZone(table).subscribe(
      () => {
        this.getNumberOfTablesForZoneRecords(this.selectedZone.id);
        this.getTablesForZoneByCurrentPage(this.selectedZone.id, true);
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  pageChanged(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getTablesForZoneByCurrentPage(this.selectedZone.id, false);
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
