
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-paziente-gia-presente',
  templateUrl: './dialog-paziente-gia-presente.component.html',
  styleUrls: ['./dialog-paziente-gia-presente.component.css']
})
export class DialogPazienteGiaPresenteComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

}
