import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { SwaggerComponent } from 'src/app/swagger/swagger.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { AuthGuard } from './core/auth-guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: './extended/core/core.module#CoreExtendedModule',
  },
  { path: 'swagger-ui', component: SwaggerComponent, canActivate: [AuthGuard] },
  {
    path: '',
    loadChildren: './extended/admin/admin.module#AdminExtendedModule',
  },
  {
    path: '',
    loadChildren: './extended/account/account.module#AccountExtendedModule',
  },
  {
    path: 'reporting',
    loadChildren: './reporting-module/reporting.module#ReportingModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'country',
    loadChildren: './extended/entities/country/country.module#CountryExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'address',
    loadChildren: './extended/entities/address/address.module#AddressExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'city',
    loadChildren: './extended/entities/city/city.module#CityExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'filmactor',
    loadChildren: './extended/entities/film-actor/film-actor.module#FilmActorExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'staff',
    loadChildren: './extended/entities/staff/staff.module#StaffExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'language',
    loadChildren: './extended/entities/language/language.module#LanguageExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'film',
    loadChildren: './extended/entities/film/film.module#FilmExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'store',
    loadChildren: './extended/entities/store/store.module#StoreExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'inventory',
    loadChildren: './extended/entities/inventory/inventory.module#InventoryExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'rental',
    loadChildren: './extended/entities/rental/rental.module#RentalExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'actor',
    loadChildren: './extended/entities/actor/actor.module#ActorExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'payment',
    loadChildren: './extended/entities/payment/payment.module#PaymentExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'filmcategory',
    loadChildren: './extended/entities/film-category/film-category.module#FilmCategoryExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'category',
    loadChildren: './extended/entities/category/category.module#CategoryExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'customer',
    loadChildren: './extended/entities/customer/customer.module#CustomerExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'scheduler',
    loadChildren: './scheduler/scheduler.module#SchedulerModule',
    canActivate: [AuthGuard],
  },
  { path: '**', component: ErrorPageComponent },
];

export const routingModule: ModuleWithProviders = RouterModule.forRoot(routes);
