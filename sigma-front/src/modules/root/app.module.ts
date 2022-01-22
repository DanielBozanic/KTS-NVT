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
import { CreateMenuDialogComponent } from '../manager/components/create-menu-dialog/create-menu-dialog.component';
import { AddItemInMenuDialogComponent } from '../manager/components/add-item-in-menu-dialog/add-item-in-menu-dialog.component';
import { CreateItemDialogComponent } from '../manager/components/create-item-dialog/create-item-dialog.component';
import { CreateZoneDialogComponent } from '../manager/components/create-zone-dialog/create-zone-dialog.component';
import { CodeVerificationDialogComponent } from '../waiter/components/code-verification-dialog/code-verification-dialog.component';
import { TableItemsDialogComponent } from '../waiter/components/table-items-dialog/table-items-dialog.component';
import { ItemSearchformComponent } from '../waiter/components/item-searchform/item-searchform.component';
import { ItemAdditionComponent } from '../waiter/components/item-addition/item-addition.component';

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
    autoHide: false,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 10,
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
    CreateMenuDialogComponent,
    CreateItemDialogComponent,
    AddItemInMenuDialogComponent,
    CreateZoneDialogComponent,
    CodeVerificationDialogComponent,
    TableItemsDialogComponent,
    ItemSearchformComponent,
    ItemAdditionComponent,
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
  ],
  providers: [HeaderComponent, RoleGuardService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule { }
