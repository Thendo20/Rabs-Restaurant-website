import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '',
    pathMatch: 'full',
    loadComponent: async () => {
      const m = await import('./home/home.component');
      return m.HomeComponent;
    },
  },

  {
    path: 'home',
    pathMatch: 'full',
    loadComponent: async () => {
      const m = await import('./home/home.component');
      return m.HomeComponent;
    }
  },

  {
    path: 'menu',
    pathMatch: 'full',
    loadComponent: async () => {
      const m = await import('./components/menu/menu.component');
      return m.MenuComponent;
    }
  },

  { path: '**',
    pathMatch: 'full',
    redirectTo: '/home'
  }
];
