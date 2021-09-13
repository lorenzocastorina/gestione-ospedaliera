import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AjaxService } from '../ajax.service';
import { DialogConfermaComponent } from '../dialog-conferma/dialog-conferma.component';

@Component({
  selector: 'app-dialog-visualizza-letti-liberi',
  templateUrl: './dialog-visualizza-letti-liberi.component.html',
  styleUrls: ['./dialog-visualizza-letti-liberi.component.css']
})
export class DialogVisualizzaLettiLiberiComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,  private ajaxService: AjaxService, public dialog: MatDialog) { }

  letti
  numero = 0

  ngOnInit(): void {
    this.ajaxService.get("letto/liberi").subscribe(response => {
      this.letti = response

      for(let i = 0; i < this.letti.length; i++) {
        this.numero++;
      }
    })


  }

  cambiaLetto(l) {
    this.ajaxService.post("paziente/cambiaLetto", {idLettoVecchio: this.data.letto.id, idLettoNuovo: l}).subscribe(response => {
      if(response) {
        window.location.reload();
      }
    },
    error => {
      console.log(error)
      this.dialog.open(DialogConfermaComponent, {data: {paziente: {}, stato: "errore"}, autoFocus: false})
    })
  }
}
