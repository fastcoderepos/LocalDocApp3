import { Component, OnInit, Inject } from '@angular/core';
import { PaymentExtendedService } from '../payment.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CustomerExtendedService } from 'src/app/extended/entities/customer/customer.service';
import { RentalExtendedService } from 'src/app/extended/entities/rental/rental.service';
import { StaffExtendedService } from 'src/app/extended/entities/staff/staff.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { PaymentNewComponent } from 'src/app/entities/payment/index';

@Component({
  selector: 'app-payment-new',
  templateUrl: './payment-new.component.html',
  styleUrls: ['./payment-new.component.scss'],
})
export class PaymentNewExtendedComponent extends PaymentNewComponent implements OnInit {
  title: string = 'New Payment';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<PaymentNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public paymentExtendedService: PaymentExtendedService,
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
      dialogRef,
      data,
      global,
      pickerDialogService,
      paymentExtendedService,
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
