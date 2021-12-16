import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FoodDrinksManagerComponent } from '../manager/pages/food-drinks-manager/food-drinks-manager.component';
import { PeopleManagerComponent } from '../manager/pages/people-manager/people-manager.component';
import { WaiterTablesComponent } from '../waiter/pages/waiter-tables/waiter-tables.component';

const routes: Routes = [
  { path: 'people', component: PeopleManagerComponent },
  { path: 'foodDrinks', component: FoodDrinksManagerComponent },
  { path: 'waiterTables', component: WaiterTablesComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
