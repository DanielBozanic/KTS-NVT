import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PeopleManagerComponent } from './components/people-manager/people-manager.component';

const routes: Routes = [{ path: 'people', component: PeopleManagerComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
