import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../authentication/login/login.component';
import { ProfileComponent } from '../authentication/profile/profile/profile.component';
import { BartenderComponent } from '../cook&bartender/pages/bartender/bartender.component';
import { CookComponent } from '../cook&bartender/pages/cook/cook.component';
import { FoodDrinksManagerComponent } from '../manager/pages/food-drinks-manager/food-drinks-manager.component';
import { PeopleManagerComponent } from '../manager/pages/people-manager/people-manager.component';
import { ZonesManagerComponent } from '../manager/pages/zones-manager/zones-manager.component';
import { WaiterAddItemsComponent } from '../waiter/pages/waiter-addItems/waiter-addItems.component';
import { WaiterOrderComponent } from '../waiter/pages/waiter-order/waiter-order.component';
import { WaiterTablesComponent } from '../waiter/pages/waiter-tables/waiter-tables.component';
import { RoleGuardService } from '../authentication/guard-url';
import { ReportsComponent } from '../manager/pages/reports/reports.component';
import { WaiterTableComponent } from '../waiter/components/waiter-table/waiter-table.component';
import { WaiterGuardService } from '../authentication/waiter-guard';

const routes: Routes = [
  {
    path: 'people',
    component: PeopleManagerComponent,
    canActivate: [RoleGuardService],
  },
  {
    path: 'foodDrinks',
    component: FoodDrinksManagerComponent,
    canActivate: [RoleGuardService],
  },
  {
    path: 'zones',
    component: ZonesManagerComponent,
    canActivate: [RoleGuardService],
  },
  { path: 'waiterTables', component: WaiterTablesComponent },
  {
    path: 'waiterOrder',
    component: WaiterOrderComponent,
    canActivate: [WaiterGuardService],
  },
  { path: 'cook', component: CookComponent },
  { path: 'bartender', component: BartenderComponent },
  {
    path: 'waiterAddItems',
    component: WaiterAddItemsComponent,
    canActivate: [WaiterGuardService],
  },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  {
    path: 'reports',
    component: ReportsComponent,
    canActivate: [RoleGuardService],
  },
  { path: 'waiterTables', component: WaiterTablesComponent },
  { path: '', redirectTo: '/waiterTables', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
