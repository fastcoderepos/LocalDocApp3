import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { PaymentExtendedService } from '../payment.service';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CustomerExtendedService } from 'src/app/extended/entities/customer/customer.service';
import { RentalExtendedService } from 'src/app/extended/entities/rental/rental.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';

import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { PaymentDetailsComponent } from 'src/app/entities/payment/index';

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.scss'],
})
export class PaymentDetailsExtendedComponent extends PaymentDetailsComponent implements OnInit {
  title: string = 'Payment';
  parentUrl: string = 'payment';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public global: Globals,
    public paymentExtendedService: PaymentExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public customerExtendedService: CustomerExtendedService,
    public rentalExtendedService: RentalExtendedService,
    public staffExtendedService: StaffExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      global,
      paymentExtendedService,
      pickerDialogService,
      errorService,
      customerExtendedService,
      rentalExtendedService,
      staffExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
