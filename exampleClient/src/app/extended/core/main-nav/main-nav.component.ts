import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';

import { AuthenticationService } from 'src/app/core/authentication.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/index';

import { Globals } from 'src/app/common/shared';
import { MainNavComponent } from 'src/app/core/main-nav/main-nav.component';

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.scss', './main-nav-mixin.component.scss'],
})
export class MainNavExtendedComponent extends MainNavComponent {
  constructor(
    public router: Router,
    public translate: TranslateService,
    public globals: Globals,

    public authenticationService: AuthenticationService,
    public globalPermissionService: GlobalPermissionService,
    public userExtendedService: UserExtendedService
  ) {
    super(
      router,
      translate,
      globals,

      authenticationService,
      globalPermissionService,
      userExtendedService
    );
  }
}
