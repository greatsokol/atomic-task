import {Routes} from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./features/master-list/master-list').then(c => c.MasterList)
  },
  {
    path: 'add',
    loadComponent: () => import('./features/master-form/master-form').then(c => c.MasterForm)
  },
  {
    path: ':id',
    loadComponent: () => import('./features/master-form/master-form').then(c => c.MasterForm)
  }
];
