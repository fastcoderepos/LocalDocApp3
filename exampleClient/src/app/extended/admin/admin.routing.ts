import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from 'src/app/core/auth-guard';

import { EntityHistoryComponent } from 'src/app/admin/entity-history/entity-history.component';

const routes: Routes = [
  {
    path: 'rolepermission',
    loadChildren: './user-management/rolepermission/rolepermission.module#RolepermissionExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'role',
    loadChildren: './user-management/role/role.module#RoleExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'userpermission',
    loadChildren: './user-management/userpermission/userpermission.module#UserpermissionExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'permission',
    loadChildren: './user-management/permission/permission.module#PermissionExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'userrole',
    loadChildren: './user-management/userrole/userrole.module#UserroleExtendedModule',
    canActivate: [AuthGuard],
  },
  {
    path: 'user',
    loadChildren: './user-management/user/user.module#UserExtendedModule',
    canActivate: [AuthGuard],
  },
  { path: 'entityHistory', component: EntityHistoryComponent, canActivate: [AuthGuard] },
];

export const routingModule: ModuleWithProviders = RouterModule.forChild(routes);
