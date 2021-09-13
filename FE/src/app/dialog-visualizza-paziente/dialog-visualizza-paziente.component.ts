import { Component, OnInit, Inject} from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogConfermaComponent } from '../dialog-conferma/dialog-conferma.component';
import { AjaxService } from '../ajax.service';


@Component({
  selector: 'app-dialog-visualizza-paziente',
  templateUrl: './dialog-visualizza-paziente.component.html',
  styleUrls: ['./dialog-visualizza-paziente.component.css']
})
export class DialogVisualizzaPazienteComponent implements OnInit {


  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private ajaxService: AjaxService, public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  conferma() {

    let dialogRef = this.dialog.open(DialogConfermaComponent, {data: {paziente: this.data.paziente, stato: "eliminazione"}, autoFocus: false})


    dialogRef.afterClosed().subscribe(result => {

      if(result !== null && result !== undefined) {

        let pazienteSupporto = {...this.data.paziente}

        switch(this.data.paziente.stato) {
          case "In pronto soccorso":
            pazienteSupporto.stato = "ProntoSoccorso"
            break;
          case "In ricovero":
            pazienteSupporto.stato = "Ricoverato"
            break;
          case "Dimesso":
            pazienteSupporto.stato = "Dimesso"
            break;
        }

        if(this.data.paziente.sesso === "") {
          pazienteSupporto.sesso = "ND"
        }


        this.ajaxService.post("paziente/rimuovi", pazienteSupporto).subscribe(response => {

          if(response) {
            window.location.reload();
            console.log("Dati del paziente: " + this.data.paziente + " eliminati")
          }
          else {
            console.log("Non è stato possibile eliminare il paziente: " + response)
          }
        },
        error => {
          console.log(error)

          this.dialog.open(DialogConfermaComponent, {data: {paziente: this.data.paziente, stato: "errore3"}, autoFocus: false})

        })
      } else {

      }

    })

  }



  apriPdf(string) {
    window.open('assets/documenti/' + string, '_blank', 'fullscreen=yes');
    return false;
  }
}
