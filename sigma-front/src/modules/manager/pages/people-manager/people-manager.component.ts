import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { Employee } from '../../../root/models/employee';
import { PeopleManagerService } from '../../services/people-manager.service';
import { PaymentValidator } from '../../directives/payment-validator';

@Component({
  selector: 'app-people-manager',
  templateUrl: './people-manager.component.html',
  styleUrls: ['./people-manager.component.scss'],
})
export class PeopleManagerComponent implements OnInit {
  displayedColumnsPeople: string[];
  jobs: string[];
  totalRows: number;
  pageSize: number;
  currentPage: number;
  pageSizeOptions: number[];
  employeeData: Array<Employee>;
  employeeDataSource: MatTableDataSource<Employee>;
  addEmployeeForm!: FormGroup;
  editEmployeeForm!: FormGroup;
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
    this.jobs = ['BARTENDER', 'COOK', 'WAITER'];
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
    this.addEmployeeForm = new FormGroup(
      {
        name: new FormControl('', Validators.required),
        paymentBigDecimal: new FormControl(0, Validators.required),
        type: new FormControl('BARTENDER'),
      },
      PaymentValidator
    );
    this.editEmployeeForm = new FormGroup(
      {
        id: new FormControl(),
        name: new FormControl(),
        paymentBigDecimal: new FormControl(),
      },
      PaymentValidator
    );
    this.getNumberOfActiveEmployeeRecords();
    this.getEmployeesByCurrentPage();
  }

  ngAfterViewInit(): void {
    this.employeeDataSource.paginator = this.paginatorEmployee;
  }

  @ViewChild(MatPaginator)
  paginatorEmployee!: MatPaginator;

  @ViewChild('addEmployeeDialog') addEmployeeDialog!: TemplateRef<any>;

  @ViewChild('editEmployeeDialog') editEmployeeDialog!: TemplateRef<any>;

  get paymentBigDecimalAddForm(): AbstractControl | null {
    return this.addEmployeeForm.get('paymentBigDecimal');
  }

  get paymentBigDecimalEditForm(): AbstractControl | null {
    return this.editEmployeeForm.get('paymentBigDecimal');
  }

  getEmployeesByCurrentPage(): void {
    this.isLoading = true;
    this.peopleManagerService
      .getEmployeesByCurrentPage(this.currentPage, this.pageSize)
      .subscribe((data) => {
        const employees = data;
        this.employeeData = employees;
        this.employeeDataSource.data = employees;
        setTimeout(() => {
          if (employees.length === 0) {
            const setPrev = this.currentPage - 1;
            this.currentPage = setPrev;
            this.paginatorEmployee.pageIndex = setPrev;
            this.getEmployeesByCurrentPage();
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
    let dialogRef = this.addDialog.open(this.addEmployeeDialog);
    dialogRef.afterClosed().subscribe(() => {
      this.addEmployeeForm.reset();
      this.addEmployeeForm.patchValue({
        type: 'BARTENDER',
      });
    });
  }

  addEmployee(): void {
    this.peopleManagerService.addEmployee(this.addEmployeeForm.value).subscribe(
      (response) => {
        console.log(response);
        this.getNumberOfActiveEmployeeRecords();
        this.getEmployeesByCurrentPage();
        this.addDialog.closeAll();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  openEditDialog(employee: Employee): void {
    this.editEmployeeForm.patchValue({
      id: employee.id,
    });
    this.oldEmployeeData = employee;
    let dialogRef = this.editDialog.open(this.editEmployeeDialog);
    dialogRef.afterClosed().subscribe(() => {
      this.editEmployeeForm.reset();
    });
  }

  editEmployee(): void {
    const editFormName = this.editEmployeeForm.get('name')?.value;
    const editFormPayment =
      this.editEmployeeForm.get('paymentBigDecimal')?.value;
    if (editFormName === null || editFormName === '') {
      this.editEmployeeForm.patchValue({
        name: this.oldEmployeeData.name,
      });
    }
    if (editFormPayment === null || editFormPayment === '') {
      this.editEmployeeForm.patchValue({
        paymentBigDecimal: this.oldEmployeeData.paymentBigDecimal,
      });
    }
    this.peopleManagerService
      .editEmployee(this.editEmployeeForm.value)
      .subscribe(
        (response) => {
          console.log(response);
          this.getEmployeesByCurrentPage();
          this.editDialog.closeAll();
        },
        (error) => {
          console.log(error);
        }
      );
    this.editEmployeeForm.reset();
  }

  deleteEmployee(employeeId: number): void {
    this.peopleManagerService.deleteEmployee(employeeId).subscribe(
      () => {
        this.getNumberOfActiveEmployeeRecords();
        this.getEmployeesByCurrentPage();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  hasError = (controlName: string, errorName: string) => {
    return this.addEmployeeForm.controls[controlName].hasError(errorName);
  };

  checkPayment(): void {
    if (this.addEmployeeForm.hasError('paymentBigDecimalInvalid')) {
      this.addEmployeeForm
        .get('paymentBigDecimal')
        ?.setErrors([{ paymentBigDecimalInvalid: true }]);
    } else if (this.editEmployeeForm.hasError('paymentBigDecimalInvalid')) {
      this.editEmployeeForm
        .get('paymentBigDecimal')
        ?.setErrors([{ paymentBigDecimalInvalid: true }]);
    }
  }

  pageChanged(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getEmployeesByCurrentPage();
  }
}
