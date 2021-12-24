import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CookComponent } from '../cook&bartender/pages/cook/cook.component';
import { FoodDrinksManagerComponent } from '../manager/pages/food-drinks-manager/food-drinks-manager.component';
import { PeopleManagerComponent } from '../manager/pages/people-manager/people-manager.component';
import { WaiterAddItemsComponent } from '../waiter/pages/waiter-addItems/waiter-addItems.component';
import { WaiterOrderComponent } from '../waiter/pages/waiter-order/waiter-order.component';
import { WaiterTablesComponent } from '../waiter/pages/waiter-tables/waiter-tables.component';

const routes: Routes = [
  { path: 'people', component: PeopleManagerComponent },
  { path: 'foodDrinks', component: FoodDrinksManagerComponent },
  { path: 'waiterTables', component: WaiterTablesComponent },
  { path: 'waiterOrder', component: WaiterOrderComponent },
  { path: 'cook', component: CookComponent },
  { path: 'waiterAddItems', component: WaiterAddItemsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
