import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PeopleManagerComponent } from '../manager/pages/people-manager/people-manager.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularMaterialModule } from './angular-material.module';
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
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
