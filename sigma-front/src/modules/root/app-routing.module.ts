import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BartenderComponent } from '../cook&bartender/pages/bartender/bartender.component';
import { CookComponent } from '../cook&bartender/pages/cook/cook.component';
import { FoodDrinksManagerComponent } from '../manager/pages/food-drinks-manager/food-drinks-manager.component';
import { PeopleManagerComponent } from '../manager/pages/people-manager/people-manager.component';
import { ZonesManagerComponent } from '../manager/pages/zones-manager/zones-manager.component';
import { WaiterAddItemsComponent } from '../waiter/pages/waiter-addItems/waiter-addItems.component';
import { WaiterOrderComponent } from '../waiter/pages/waiter-order/waiter-order.component';
import { WaiterTablesComponent } from '../waiter/pages/waiter-tables/waiter-tables.component';

const routes: Routes = [
  { path: 'people', component: PeopleManagerComponent },
  { path: 'foodDrinks', component: FoodDrinksManagerComponent },
  { path: 'zones', component: ZonesManagerComponent },
  { path: 'waiterTables', component: WaiterTablesComponent },
  { path: 'waiterOrder', component: WaiterOrderComponent },
  { path: 'cook', component: CookComponent },
  { path: 'bartender', component: BartenderComponent },
  { path: 'waiterAddItems', component: WaiterAddItemsComponent },
  // { path: 'login', component: undefined },
  { path: '', redirectTo: '/waiterTables', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
