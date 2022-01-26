import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { Table } from 'src/modules/root/models/table';
import { Zone } from 'src/modules/root/models/zone';
import { ZoneManagerService } from '../../services/zone-manager.service';

@Component({
  selector: 'app-zones-manager',
  templateUrl: './zones-manager.component.html',
  styleUrls: ['./zones-manager.component.scss'],
})
export class ZonesManagerComponent implements OnInit {
  zones: Array<Zone>;
  tablesForZone: Array<Table>;
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
    this.zones = [];
    this.tablesForZone = [];
    this.selectedZone = new Zone();
    this.selectedTable = new Table();
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
  }

  ngOnInit(): void {
    this.getZones();
  }

  @ViewChild('newZoneDialog') newZoneDialog!: TemplateRef<any>;

  @ViewChild('addTableDialog') addTableDialog!: TemplateRef<any>;

  @ViewChild('editTableDialog') editTableDialog!: TemplateRef<any>;

  getZones(): void {
    this.zonesManagerService.getZones().subscribe((data) => {
      this.zones = data;
      if (data.length !== 0) {
        this.selectedZone = data[0];
        this.getTablesForZone(data[0].id);
      } else {
        this.selectedZone = new Zone();
      }
    });
  }

  getTablesForZone(zoneId: number): void {
    this.zonesManagerService.getTablesForZone(zoneId).subscribe((data) => {
      const tablesForZone = data;
      this.tablesForZone = tablesForZone;
    });
  }

  changeZone(zoneId: number): void {
    this.selectedZone = this.zones.filter((z) => z.id === zoneId)[0];
    this.getTablesForZone(zoneId);
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
    table.x = 0;
    table.y = 0;
    this.zonesManagerService.addTable(table).subscribe(
      (resp) => {
        console.log(resp);
        this.addTableToSelectedZoneDialog.closeAll();
        this.getTablesForZone(this.selectedZone.id);
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
        this.getTablesForZone(this.selectedZone.id);
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  removeTableFromZone(): void {
    let table = this.selectedTable;
    this.zonesManagerService.removeTableFromZone(table).subscribe(
      () => {
        this.editTableSelectedZoneDialog.closeAll();
        this.getTablesForZone(this.selectedZone.id);
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  tableColor(table: Table): string {
    if (table.state === 'RESERVED') {
      return 'lightgray';
    } else if (table.state === 'IN_PROGRESS') {
      return 'orange';
    } else if (table.state === 'TO_DELIVER') {
      return 'red';
    } else if (table.state === 'FREE') {
      return 'green';
    } else {
      return 'blue';
    }
  }

  dragEnd(event: any): void {
    let element = event.source.getRootElement();
    let boundingClientRect = element.getBoundingClientRect();
    let parentPosition = this.getPosition(element);
    this.tablesForZone.forEach((table) => {
      if (table.id == element.id) {
        table.x = table.x + boundingClientRect.x - parentPosition.left;
        table.y = table.y + boundingClientRect.y - parentPosition.top;
        this.zonesManagerService
          .updateTablePosition(table)
          .subscribe((data) => {
            console.log(data);
            this.getTablesForZone(this.selectedZone.id);
          });
      }
    });
    event.source._dragRef.reset();
  }

  getPosition(el: any) {
    let x = 0;
    let y = 0;
    while (el && !isNaN(el.offsetLeft) && !isNaN(el.offsetTop)) {
      x += el.offsetLeft - el.scrollLeft;
      y += el.offsetTop - el.scrollTop;
      el = el.offsetParent;
    }
    return { top: y, left: x };
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
