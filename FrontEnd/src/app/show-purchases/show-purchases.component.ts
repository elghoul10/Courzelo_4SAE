import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShowPurchasesService } from 'app/services/show-purchases.service';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import autotable from 'jspdf-autotable';

@Component({
  selector: 'app-show-purchases',
  templateUrl: './show-purchases.component.html',
  styleUrls: ['./show-purchases.component.scss'],
})
export class ShowPurchasesComponent implements OnInit {
  Purchases: any;
  idb: any;
  constructor(
    private purchasesService: ShowPurchasesService,
    private activaterouter: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.idb = this.activaterouter.snapshot.params['idb'];

    this.purchasesService.ShowPurchases(this.idb).subscribe((data) => {
      this.Purchases = data;
      console.log(this.Purchases);
    });
  }
  @ViewChild('content') content: ElementRef;

  public SavePDF(): void {
    let content = this.content.nativeElement;
    let doc = new jsPDF();
    doc.text('Facture Information', 15, 10);
    doc.text('-----------------------------------------', 15, 15);
    doc.text('Date: ' + new Date().toLocaleDateString(), 15, 20);
    doc.text('-----------------------------------------', 15, 30);
    doc.text('', 15, 35);
    const data = [];
    let total: any = 0;

    for (let i = 0; i < this.Purchases.length; i++) {
      const purchase = this.Purchases[i];
      total = total + purchase.prix;
      data.push([
        purchase.id.toString(),
        purchase.course.courseId.toString(),
        purchase.course.courseTitle.toString(),
        purchase.prix.toString(),
        purchase.date_purchase.toString(),
      ]);
    }

    // Set up table headers
    const headers = [
      'ID',
      'Course ID',
      'Course Title',
      'Price',
      'Date of Purchase',
    ];

    // Add table to PDF
    autoTable(doc, {
      head: [headers],
      body: data,
      startY: 40, // Start table at Y position 40
    });

    const finalY = (doc as any).lastAutoTable.finalY;

    // Add a horizontal line under the table
    doc.text('-----------------------------------------', 100, finalY + 10);

    // Add total under the table
    doc.text('Total: ' + total.toString(), 100, finalY + 15);

    doc.save('facture.pdf');
  }
}
