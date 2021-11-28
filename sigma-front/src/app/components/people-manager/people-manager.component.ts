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
import { Employee } from '../../models/employee';
import { PeopleManagerService } from 'src/app/services/people-manager.service';
import { PaymentValidator } from './payment-validator';

@Component({
  selector: 'app-people-manager',
  templateUrl: './people-manager.component.html',
  styleUrls: ['./people-manager.component.scss'],
})
export class PeopleManagerComponent implements OnInit {
  displayedColumnsPeople: string[] = [
    'name',
    'paymentBigDecimal',
    'type',
    'code',
    'edit',
    'delete',
  ];
  jobs = ['BARTENDER', 'COOK', 'WAITER'];
  totalRows = 0;
  pageSize = 5;
  currentPage = 0;
  pageSizeOptions: number[] = [5, 10, 25];
  employeeData: Array<Employee> = [];
  employeeDataSource = new MatTableDataSource<Employee>(this.employeeData);
  addEmployeeForm!: FormGroup;
  editEmployeeForm!: FormGroup;
  isLoading = false;

  constructor(
    private peopleManagerService: PeopleManagerService,
    private dialog: MatDialog
  ) {}

  @ViewChild(MatPaginator)
  paginatorEmployee!: MatPaginator;

  @ViewChild('addEmployeeDialog') addEmployeeDialog!: TemplateRef<any>;

  ngOnInit(): void {
    this.addEmployeeForm = new FormGroup(
      {
        name: new FormControl('', Validators.required),
        paymentBigDecimal: new FormControl(0, Validators.required),
        type: new FormControl('BARTENDER'),
      },
      PaymentValidator
    );
    this.editEmployeeForm = new FormGroup({
      id: new FormControl(),
      name: new FormControl(),
      paymentBigDecimal: new FormControl(),
    });
    this.getNumberOfActiveEmployeeRecords();
    this.getEmployeesByCurrentPage();
  }

  ngAfterViewInit(): void {
    this.employeeDataSource.paginator = this.paginatorEmployee;
  }

  get paymentBigDecimal(): AbstractControl | null {
    return this.editEmployeeForm.get('paymentBigDecimal');
  }

  setNewName(e: Event): void {
    this.editEmployeeForm.patchValue({
      name: (e.target as HTMLTextAreaElement).value,
    });
  }

  setNewPayment(e: Event): void {
    this.editEmployeeForm.patchValue({
      paymentBigDecimal: (e.target as HTMLTextAreaElement).value,
    });
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(this.addEmployeeDialog);
    dialogRef.afterClosed().subscribe(() => {
      this.addEmployeeForm.reset();
      this.addEmployeeForm.patchValue({
        type: 'BARTENDER',
      });
    });
  }

  getEmployeesByCurrentPage(): void {
    this.isLoading = true;
    this.peopleManagerService
      .getEmployeesByCurrentPage(this.currentPage, this.pageSize)
      .subscribe((data) => {
        const activeEmployees = data.filter((e) => e.active === true);
        this.employeeData = activeEmployees;
        this.employeeDataSource.data = activeEmployees;
        setTimeout(() => {
          if (activeEmployees.length === 0) {
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

  addEmployee(): void {
    this.peopleManagerService.addEmployee(this.addEmployeeForm.value).subscribe(
      (response) => {
        console.log(response);
        this.getNumberOfActiveEmployeeRecords();
        this.getEmployeesByCurrentPage();
        this.dialog.closeAll();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  editEmployee(employee: Employee): void {
    this.editEmployeeForm.patchValue({
      id: employee.id,
    });
    if (
      this.editEmployeeForm.get('name')?.value === null ||
      this.editEmployeeForm.get('name')?.value === ''
    ) {
      this.editEmployeeForm.patchValue({
        name: employee.name,
      });
    }
    if (
      this.editEmployeeForm.get('paymentBigDecimal')?.value === null ||
      this.editEmployeeForm.get('paymentBigDecimal')?.value === ''
    ) {
      this.editEmployeeForm.patchValue({
        paymentBigDecimal: employee.paymentBigDecimal,
      });
    }
    this.peopleManagerService
      .editEmployee(this.editEmployeeForm.value)
      .subscribe(
        (response) => {
          console.log(response);
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
    }
  }

  validateNewPayment(event: Event): boolean | null {
    const key = (event as KeyboardEvent).key.charCodeAt(0);
    return key === 8 || key === 0 ? null : key >= 48 && key <= 57;
  }

  pageChanged(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getEmployeesByCurrentPage();
  }
}
