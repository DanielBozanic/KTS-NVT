import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PeopleManagerComponent } from './components/people-manager/people-manager.component';
import { WaiterTablesComponent } from './components/waiter-tables/waiter-tables.component';

const routes: Routes = [
  { path: 'people', component: PeopleManagerComponent },
  { path: 'waiterTables', component: WaiterTablesComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
