import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Router, ActivatedRoute } from '@angular/router';

import { PaymentExtendedService } from '../payment.service';
import { PaymentNewExtendedComponent } from '../new/payment-new.component';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';

import { CustomerExtendedService } from 'src/app/extended/entities/customer/customer.service';
import { RentalExtendedService } from 'src/app/extended/entities/rental/rental.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { PaymentListComponent } from 'src/app/entities/payment/index';

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss'],
})
export class PaymentListExtendedComponent extends PaymentListComponent implements OnInit {
  title: string = 'Payment';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public paymentService: PaymentExtendedService,
    public errorService: ErrorService,
    public customerService: CustomerExtendedService,
    public rentalService: RentalExtendedService,
    public staffService: StaffExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      paymentService,
      errorService,
      customerService,
      rentalService,
      staffService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(PaymentNewExtendedComponent);
  }
}
