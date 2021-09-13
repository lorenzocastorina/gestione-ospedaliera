import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AjaxService } from '../ajax.service';
import { MatDialog } from '@angular/material/dialog'
import { DialogVisualizzaLettiLiberiComponent } from '../dialog-visualizza-letti-liberi/dialog-visualizza-letti-liberi.component';
import { DialogConfermaComponent } from '../dialog-conferma/dialog-conferma.component';
import html2pdf from 'html2pdf.js'


@Component({
  selector: 'app-dialog-visualizza-letto',
  templateUrl: './dialog-visualizza-letto.component.html',
  styleUrls: ['./dialog-visualizza-letto.component.css']
})
export class DialogVisualizzaLettoComponent implements OnInit {

  letto_corrente
  paziente_corrente
  lettiNuovi

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,  private ajaxService: AjaxService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.paziente_corrente = this.data.paziente
    this.letto_corrente = this.data.letto

    this.ajaxService.get("letto/getLetti").subscribe(response => {
      this.lettiNuovi = response
    })
  }

  cambiaLetto(p, l) {


    let dialogRef = this.dialog.open(DialogVisualizzaLettiLiberiComponent, {data: {paziente: p, letto: l}, autoFocus: false})

    dialogRef.afterClosed().subscribe(result => {

      })

  }

  dimettiPaziente() {

    let dialogRef = this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_corrente, stato: "ricovero"}, autoFocus: false})


    dialogRef.afterClosed().subscribe(result => {

      if(result != null) {
        this.ajaxService.post("paziente/dimettiRicovero", this.paziente_corrente).subscribe(response => {

          if(response) {
            this.salvaDiagnosi(this.paziente_corrente)

          }
          else {
            console.log("non è stato possibile dimettere il paziente: " + response)
          }

          this.ajaxService.get("letto/getLetti").subscribe(response => {
            this.lettiNuovi = response


          })

          //window.location.reload();
        },
        error => {
          console.log(error)
          this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_corrente, stato: "errore"}, autoFocus: false})

        })
      } else {

      }

    })





  }

  salvaDiagnosi(paziente) {
    console.log(paziente)
    var opt = {
      margin:       1,
      filename:     'diagnosi-' + paziente.cognome + '-' + Math.random().toString(36).substring(2,6) + '.pdf',
      image:        { type: 'jpeg', quality: 0.98 },
      html2canvas:  { scale: 2 },
      jsPDF:        { unit: 'in', format: 'letter', orientation: 'portrait' }
    };




    this.ajaxService.post("documento/aggiungi", {pazienteDTO: paziente, nome: opt.filename}).subscribe(response => {
      if(response) {
        html2pdf().set(opt).from(paziente.diagnosi, 'string').save();
      } else {
        console.log("Non è stato possibile salvare la diagnosi di:" + paziente.nome + " " + paziente.cognome)
      }
    },
    error => {
      console.log(error)
      this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_corrente, stato: "errore"}, autoFocus: false})
    })

  }


}
