import { Component, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Table } from 'src/app/models/table';
import { Zone } from 'src/app/models/zone';
import { WaiterTablesService } from 'src/app/services/waiter-tables.service';

@Component({
  selector: 'app-waiter-tables',
  templateUrl: './waiter-tables.component.html',
  styleUrls: ['./waiter-tables.component.scss'],
})
export class WaiterTablesComponent implements OnInit {

  constructor(
    private service: WaiterTablesService,
    private dialog: MatDialog
  ) {}

  zones : Zone[] = [];
  tables : Table[] = []

  ngOnInit(): void {
    this.service.getAllZones().subscribe( (data) => {
      this.zones = data;
    })
  }

  getTables(id: number): void {

    this.service.getTablesForZone(id).subscribe( (data) => {
      this.tables = data;
      console.log(this.tables);
    })
  }

  tableClick(table: Table){
    switch(table.state){
      case 'FREE':
        break;
      case 'RESERVED':
        break;
      case 'IN_PROGRESS':
        break;
      case 'TO_DELIVER':
        break;
      case 'DONE':
        break;
    }
  }
}
