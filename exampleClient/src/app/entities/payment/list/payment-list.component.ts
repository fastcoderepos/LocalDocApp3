import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material';

import { IPayment } from '../ipayment';
import { PaymentService } from '../payment.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PaymentNewComponent } from '../new/payment-new.component';
import { BaseListComponent, Globals, listColumnType, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { CustomerService } from 'src/app/entities/customer/customer.service';
import { RentalService } from 'src/app/entities/rental/rental.service';
import { StaffService } from 'src/app/entities/staff/staff.service';

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss'],
})
export class PaymentListComponent extends BaseListComponent<IPayment> implements OnInit {
  title = 'Payment';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public paymentService: PaymentService,
    public errorService: ErrorService,
    public customerService: CustomerService,
    public rentalService: RentalService,
    public staffService: StaffService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, paymentService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Payment';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['paymentId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'customerId',
            value: undefined,
            referencedkey: 'customerId',
          },
        ],
        isParent: false,
        descriptiveField: 'customerDescriptiveField',
        referencedDescriptiveField: 'customerId',
        service: this.customerService,
        associatedObj: undefined,
        table: 'customer',
        type: 'ManyToOne',
        url: 'payments',
        listColumn: 'customer',
        label: 'customer',
      },
      {
        column: [
          {
            key: 'rentalId',
            value: undefined,
            referencedkey: 'rentalId',
          },
        ],
        isParent: false,
        descriptiveField: 'rentalDescriptiveField',
        referencedDescriptiveField: 'rentalId',
        service: this.rentalService,
        associatedObj: undefined,
        table: 'rental',
        type: 'ManyToOne',
        url: 'payments',
        listColumn: 'rental',
        label: 'rental',
      },
      {
        column: [
          {
            key: 'staffId',
            value: undefined,
            referencedkey: 'staffId',
          },
        ],
        isParent: false,
        descriptiveField: 'staffDescriptiveField',
        referencedDescriptiveField: 'staffId',
        service: this.staffService,
        associatedObj: undefined,
        table: 'staff',
        type: 'ManyToOne',
        url: 'payments',
        listColumn: 'staff',
        label: 'staff',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'amount',
        searchColumn: 'amount',
        label: 'amount',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'paymentDate',
        searchColumn: 'paymentDate',
        label: 'payment Date',
        sort: true,
        filter: true,
        type: listColumnType.DateTime,
      },
      {
        column: 'paymentId',
        searchColumn: 'paymentId',
        label: 'payment Id',
        sort: true,
        filter: true,
        type: listColumnType.Number,
      },
      {
        column: 'customerDescriptiveField',
        searchColumn: 'customer',
        label: 'customer',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'rentalDescriptiveField',
        searchColumn: 'rental',
        label: 'rental',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'staffDescriptiveField',
        searchColumn: 'staff',
        label: 'staff',
        sort: true,
        filter: true,
        type: listColumnType.String,
      },
      {
        column: 'actions',
        label: 'Actions',
        sort: false,
        filter: false,
        type: listColumnType.String,
      },
    ];
    this.selectedColumns = this.columns;
    this.displayedColumns = this.columns.map((obj) => {
      return obj.column;
    });
  }
  addNew(comp) {
    if (!comp) {
      comp = PaymentNewComponent;
    }
    super.addNew(comp);
  }
}
