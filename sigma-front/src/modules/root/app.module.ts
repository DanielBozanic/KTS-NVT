import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PeopleManagerComponent } from '../manager/pages/people-manager/people-manager.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularMaterialModule } from './angular-material.module';
import { NotifierModule, NotifierOptions } from 'angular-notifier';
import { CarouselModule } from 'primeng/carousel';
import { FlexLayoutModule } from '@angular/flex-layout';
import { WaiterTablesComponent } from '../waiter/pages/waiter-tables/waiter-tables.component';
import { FoodDrinksManagerComponent } from '../manager/pages/food-drinks-manager/food-drinks-manager.component';
import { WaiterOrderComponent } from '../waiter/pages/waiter-order/waiter-order.component';
import { CookComponent } from '../cook&bartender/pages/cook/cook.component';
import { WaiterAddItemsComponent } from '../waiter/pages/waiter-addItems/waiter-addItems.component';
import { BartenderComponent } from '../cook&bartender/pages/bartender/bartender.component';
import { ZonesManagerComponent } from '../manager/pages/zones-manager/zones-manager.component';
import { TableDialogComponent } from '../manager/components/table-dialog/table-dialog.component';
import { HeaderComponent } from '../navigation/header/header.component';
import { SidenavListComponent } from '../navigation/sidenav-list/sidenav-list.component';
import { LoginComponent } from '../authentication/login/login.component';
import { ProfileComponent } from '../authentication/profile/profile/profile.component';
import { AddEmployeeDialogComponent } from '../manager/components/add-employee-dialog/add-employee-dialog.component';
import { EditEmployeeDialogComponent } from '../manager/components/edit-employee-dialog/edit-employee-dialog.component';
import { RoleGuardService } from '../authentication/guard-url';
import { ReportsComponent } from '../manager/pages/reports/reports.component';
import { ChartsModule } from 'ng2-charts';

const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'left',
      distance: 12,
    },
    vertical: {
      position: 'bottom',
      distance: 12,
      gap: 10,
    },
  },
  theme: 'material',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4,
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease',
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50,
    },
    shift: {
      speed: 300,
      easing: 'ease',
    },
    overlap: 150,
  },
};

@NgModule({
  declarations: [
    AppComponent,
    PeopleManagerComponent,
    WaiterTablesComponent,
    FoodDrinksManagerComponent,
    WaiterOrderComponent,
    CookComponent,
    BartenderComponent,
    WaiterAddItemsComponent,
    ZonesManagerComponent,
    TableDialogComponent,
    HeaderComponent,
    LoginComponent,
    ProfileComponent,
    SidenavListComponent,
    AddEmployeeDialogComponent,
    EditEmployeeDialogComponent,
    ReportsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    CarouselModule,
    FlexLayoutModule,
    NotifierModule.withConfig(customNotifierOptions),
    ChartsModule,
  ],
  providers: [HeaderComponent, RoleGuardService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
