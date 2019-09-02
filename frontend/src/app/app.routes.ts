import { RouterModule, Routes } from '@angular/router';
import { GeneralComponent } from './general_grid/general.component';

const APP_ROUTES: Routes = [
  { path: 'home', component: GeneralComponent },
  { path: 'patient', component: GeneralComponent },
  { path: '**', pathMatch: 'full', redirectTo: 'home'}
];

export const APP_ROUTING = RouterModule.forRoot(APP_ROUTES, { useHash:true });
