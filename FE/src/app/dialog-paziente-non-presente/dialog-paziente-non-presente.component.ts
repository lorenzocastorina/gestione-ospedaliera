import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-paziente-non-presente',
  templateUrl: './dialog-paziente-non-presente.component.html',
  styleUrls: ['./dialog-paziente-non-presente.component.css']
})
export class DialogPazienteNonPresenteComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,) { }

  ngOnInit(): void {
  }

}
