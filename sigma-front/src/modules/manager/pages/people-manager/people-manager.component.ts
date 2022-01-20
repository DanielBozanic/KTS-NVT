import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { Employee } from '../../../root/models/employee';
import { PeopleManagerService } from '../../services/people-manager.service';

@Component({
  selector: 'app-people-manager',
  templateUrl: './people-manager.component.html',
  styleUrls: ['./people-manager.component.scss'],
})
export class PeopleManagerComponent implements OnInit {
  displayedColumnsPeople: string[];
  totalRows: number;
  pageSize: number;
  currentPage: number;
  pageSizeOptions: number[];
  employeeData: Array<Employee>;
  employeeDataSource: MatTableDataSource<Employee>;
  oldEmployeeData: Employee;
  isLoading: boolean;

  constructor(
    private peopleManagerService: PeopleManagerService,
    private addDialog: MatDialog,
    private editDialog: MatDialog
  ) {
    this.displayedColumnsPeople = [
      'name',
      'paymentBigDecimal',
      'type',
      'code',
      'edit',
      'delete',
    ];
    this.totalRows = 0;
    this.pageSize = 5;
    this.currentPage = 0;
    this.pageSizeOptions = [5, 10, 25];
    this.employeeData = [];
    this.employeeDataSource = new MatTableDataSource<Employee>(
      this.employeeData
    );
    this.oldEmployeeData = new Employee();
    this.isLoading = false;
  }

  ngOnInit(): void {
    this.getNumberOfActiveEmployeeRecords();
    this.getEmployeesByCurrentPage(false);
  }

  ngAfterViewInit(): void {
    this.employeeDataSource.paginator = this.paginatorEmployee;
  }

  @ViewChild(MatPaginator)
  paginatorEmployee!: MatPaginator;

  @ViewChild('addEmployeeDialog') addEmployeeDialog!: TemplateRef<any>;

  @ViewChild('editEmployeeDialog') editEmployeeDialog!: TemplateRef<any>;

  getEmployeesByCurrentPage(deleting: boolean): void {
    this.isLoading = true;
    this.peopleManagerService
      .getEmployeesByCurrentPage(this.currentPage, this.pageSize)
      .subscribe((data) => {
        const employees = data;
        this.employeeData = employees;
        this.employeeDataSource.data = employees;
        setTimeout(() => {
          if (employees.length === 0 && deleting) {
            if (this.currentPage !== 0) {
              const setPrev = this.currentPage - 1;
              this.currentPage = setPrev;
              this.paginatorEmployee.pageIndex = setPrev;
            }
            this.getEmployeesByCurrentPage(false);
          } else {
            this.paginatorEmployee.pageIndex = this.currentPage;
          }
          this.paginatorEmployee.length = this.totalRows;
        });
        this.isLoading = false;
      });
  }

  getNumberOfActiveEmployeeRecords(): void {
    this.peopleManagerService
      .getNumberOfActiveEmployeeRecords()
      .subscribe((data) => {
        this.totalRows = data;
      });
  }

  openAddDialog(): void {
    this.addDialog.open(this.addEmployeeDialog);
  }

  addEmployee(employee: Employee): void {
    this.peopleManagerService.addEmployee(employee).subscribe(
      (response) => {
        console.log(response);
        this.getNumberOfActiveEmployeeRecords();
        this.getEmployeesByCurrentPage(false);
        this.addDialog.closeAll();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  openEditDialog(employee: Employee): void {
    this.oldEmployeeData = employee;
    this.editDialog.open(this.editEmployeeDialog);
  }

  editEmployee(employee: Employee): void {
    employee.id = this.oldEmployeeData.id;
    const editFormName = employee.name;
    const editFormPayment = employee.paymentBigDecimal;
    if (editFormName === null || editFormName === '') {
      employee.name = this.oldEmployeeData.name;
    }
    if (editFormPayment === null) {
      employee.paymentBigDecimal = this.oldEmployeeData.paymentBigDecimal;
    }
    this.peopleManagerService.editEmployee(employee).subscribe(
      (response) => {
        console.log(response);
        this.getEmployeesByCurrentPage(false);
        this.editDialog.closeAll();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  deleteEmployee(employeeId: number): void {
    this.peopleManagerService.deleteEmployee(employeeId).subscribe(
      () => {
        this.getNumberOfActiveEmployeeRecords();
        this.getEmployeesByCurrentPage(true);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  pageChanged(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getEmployeesByCurrentPage(false);
  }
}
