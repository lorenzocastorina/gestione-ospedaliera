import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-conferma',
  templateUrl: './dialog-conferma.component.html',
  styleUrls: ['./dialog-conferma.component.css']
})
export class DialogConfermaComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {

  }

}
