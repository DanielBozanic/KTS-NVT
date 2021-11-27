import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { Employee } from 'src/app/models/Employee';
import { PeopleManagerService } from 'src/app/services/people-manager.service';

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
  employeeData: Array<Employee> = [];
  employeeDataSource = new MatTableDataSource<Employee>(this.employeeData);
  addEmployeeForm!: FormGroup;

  constructor(
    private peopleManagerService: PeopleManagerService,
    private dialog: MatDialog
  ) {}

  @ViewChild(MatPaginator)
  paginatorEmployee!: MatPaginator;

  @ViewChild('addEmployeeDialog') addEmployeeDialog!: TemplateRef<any>;

  ngOnInit(): void {
    this.addEmployeeForm = new FormGroup({
      name: new FormControl('', Validators.required),
      paymentBigDecimal: new FormControl('', Validators.required),
      type: new FormControl('BARTENDER'),
    });
    this.getAllEmployees();
  }

  ngAfterViewInit(): void {
    this.employeeDataSource.paginator = this.paginatorEmployee;
  }

  get paymentBigDecimal() {
    return this.addEmployeeForm.get('paymentBigDecimal');
  }

  openDialog() {
    let dialogRef = this.dialog.open(this.addEmployeeDialog);
    dialogRef.afterClosed().subscribe(() => {
      this.addEmployeeForm.reset();
      this.addEmployeeForm.patchValue({
        type: 'BARTENDER',
      });
    });
  }

  getAllEmployees(): void {
    this.peopleManagerService.getAllEmployees().subscribe((data) => {
      this.employeeData = data.filter((e) => e.active === true);
      this.employeeDataSource = new MatTableDataSource<Employee>(
        this.employeeData
      );
      this.employeeDataSource.paginator = this.paginatorEmployee;
    });
  }

  addEmployee() {
    this.peopleManagerService.addEmployee(this.addEmployeeForm.value).subscribe(
      (response) => {
        console.log(response);
        this.getAllEmployees();
        this.dialog.closeAll();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  editEmployee(employee: Employee): void {
    console.log(employee);
  }

  deleteEmployee(employeeId: number): void {
    this.peopleManagerService.deleteEmployee(employeeId).subscribe(
      () => {
        this.getAllEmployees();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  hasError = (controlName: string, errorName: string) => {
    return this.addEmployeeForm.controls[controlName].hasError(errorName);
  };

  checkPayment() {
    if (this.addEmployeeForm.hasError('paymentBigDecimalInvalid')) {
      this.addEmployeeForm
        .get('paymentBigDecimal')
        ?.setErrors([{ paymentBigDecimalInvalid: true }]);
    }
  }
}
