import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartType, ChartOptions } from 'chart.js';
import { Label } from 'ng2-charts';
import { FormGroup, FormControl } from '@angular/forms';
import { ReportsManagerService } from '../../services/reports-manager.service';
import { stringToDate, increaseMonth } from '../../utils/dateUtil';
import * as moment from 'moment';
@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss'],
})
export class ReportsComponent implements OnInit {
  form: FormGroup = new FormGroup({
    startDate: new FormControl(''),
    endDate: new FormControl(''),
  });

  barChartOptions: ChartOptions = {
    responsive: true,
    scales: { xAxes: [{}], yAxes: [{}] },
  };
  barChartLabels: Label[] = [];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [];

  constructor(private reportManagerService: ReportsManagerService) {}
  ngOnInit(): void {}

  onSubmit(): void {
    const { startDate, endDate } = this.form.value;
    if (startDate != '' && endDate != '') {
      this.reportManagerService
        .postReportQuery({
          startMonth: startDate,
          endMonth: endDate,
          salesPerMonth: [],
          expensesPerMonth: [],
        })
        .subscribe((data) => {
          const report = data;
          this.setBarChartLabels(report.startMonth, report.endMonth);
          this.setBarChartData(report.salesPerMonth, report.expensesPerMonth);
        });
    }
  }

  setBarChartLabels(date1: Date, date2: Date): void {
    this.barChartLabels = [];
    var startDate = stringToDate(date1);
    var endDate = stringToDate(date2);
    var tempDate = startDate;
    for (let i = 0; i <= moment(endDate).diff(startDate, 'months', true); i++) {
      this.barChartLabels.push(
        `${tempDate.format('MMM')} - ${tempDate.format('YYYY')}`
      );
      tempDate = increaseMonth(tempDate);
    }
  }

  setBarChartData(salesPerMonth: any, expensesPerMonth: any): void {
    this.barChartData = [];
    this.barChartData.push({ data: salesPerMonth, label: 'Sales' });
    this.barChartData.push({ data: expensesPerMonth, label: 'Expenses' });
  }
}
