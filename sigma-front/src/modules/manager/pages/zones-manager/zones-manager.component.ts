import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
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

  constructor(private zonesManagerService: ZoneManagerService) {
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
  }

  ngOnInit(): void {
    this.getZones();
  }

  ngAfterViewInit(): void {
    this.tablesForZoneDataSource.paginator = this.paginatorTablesInZone;
  }

  @ViewChild(MatPaginator)
  paginatorTablesInZone!: MatPaginator;

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

  editNumberOfChairs(table: Table): void {}

  removeTableFromZone(table: Table): void {}

  changeZone(zoneId: number): void {}

  pageChanged(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getTablesForZoneByCurrentPage(this.selectedZone.id, false);
  }
}
