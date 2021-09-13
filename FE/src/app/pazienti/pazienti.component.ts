import { Component, OnInit } from '@angular/core';
import { DialogVisualizzaPazienteComponent } from '../dialog-visualizza-paziente/dialog-visualizza-paziente.component';
import { MatDialog } from '@angular/material/dialog'
import { CheckLoginService } from '../check-login.service';
import { AjaxService } from '../ajax.service';

@Component({
  selector: 'app-pazienti',
  templateUrl: './pazienti.component.html',
  styleUrls: ['./pazienti.component.css']
})
export class PazientiComponent implements OnInit {

  rosso = "#c62828"
  giallo = "#fbc02d"
  verde = "#2e7d32"
  bianco = "#e0e0e0"

  nomeSelez = "";

  maschio
  femmina

  etaSelez = "-";
  verso = "minore"

  ricovero
  soccorso
  dimesso

  totPazienti

  pazienti: any = []

  pazientiFiltrati = this.pazienti

  constructor(public dialog: MatDialog, public check: CheckLoginService, private ajaxService: AjaxService) { }

  ngOnInit(): void {
    this.check.check()

    this.ajaxService.get("paziente/getPazienti").subscribe(response => {

        this.pazienti = response


        for(let i = 0; i < this.pazienti.length; i++) {
          if(this.pazienti[i].sesso === "ND") {
            this.pazienti[i].sesso = ""
          }

          switch (this.pazienti[i].stato) {
            case "ProntoSoccorso":
              this.pazienti[i].stato = "In pronto soccorso"
              break;
            case "Ricoverato":
              this.pazienti[i].stato = "In ricovero"
              break;
            case "Dimesso":
              this.pazienti[i].stato = "Dimesso"
              break;
            default:
              break;
          }
        }

         this.pazientiFiltrati = this.pazienti
         this.contaPazienti()


    },
    error => {
      console.log(error)
    })


  }

  reset() {
    this.nomeSelez = "";
    this.filtra()
  }

  change() {
    if(this.verso === "minore"){
      this.verso = "maggiore"
    } else if(this.verso === "maggiore"){
      this.verso = "uguale"
    } else if(this.verso === "uguale"){
      this.verso = "minore"
    }

    this.filtra()
  }

  change2() {
    this.etaSelez = "-"

    this.filtra()
  }

  filtra() {
    this.pazientiFiltrati = this.pazienti

    this.filtraNome()
    this.filtraSesso()
    this.filtraEta()
    this.filtraStato()

    this.contaPazienti()
  }

  filtraNome() {
    if(this.nomeSelez === "" || this.nomeSelez === " ")
      return

    this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
      return element.nome.toLowerCase().includes(this.nomeSelez.toLowerCase()) || element.cognome.toLowerCase().includes(this.nomeSelez.toLowerCase())
    })
  }

  filtraSesso() {

    if(this.maschio && !this.femmina) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.sesso === "M"
      })
    } else if(!this.maschio && this.femmina) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.sesso === "F"
      })
    } else if(this.maschio && this.femmina) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.sesso === "M" || "F"
      })
    } else {
      return
    }
  }

  filtraEta() {
    if(this.etaSelez === "-")
      return

    const oggi = new Date();

    if(this.verso === "minore") {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        let dataPaziente = new Date(element.data_nascita)
        let anniPaziente = oggi.getFullYear() - dataPaziente.getFullYear()

        return anniPaziente <= parseInt(this.etaSelez, 10);
      })
    } else if(this.verso === "maggiore") {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        let dataPaziente = new Date(element.data_nascita)
        let anniPaziente = oggi.getFullYear() - dataPaziente.getFullYear()

        return anniPaziente >= parseInt(this.etaSelez, 10);
      })
    } else if(this.verso === "uguale") {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        let dataPaziente = new Date(element.data_nascita)
        let anniPaziente = oggi.getFullYear() - dataPaziente.getFullYear()

        return anniPaziente === parseInt(this.etaSelez, 10);
      })
    }

  }


  filtraStato() {

    if(this.ricovero && !this.soccorso && !this.dimesso) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.stato === "In ricovero"
      })
    } else if (!this.ricovero && this.soccorso && !this.dimesso) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.stato === "In pronto soccorso"
      })
    } else if (!this.ricovero && !this.soccorso && this.dimesso) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.stato === "Dimesso"
      })
    } else if (this.ricovero && this.soccorso && !this.dimesso) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.stato === "In ricovero" || element.stato === "In pronto soccorso"
      })
    } else if (this.ricovero && !this.soccorso && this.dimesso) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.stato === "In ricovero" || element.stato === "Dimesso"
      })
    } else if (!this.ricovero && this.soccorso && this.dimesso) {
      this.pazientiFiltrati = this.pazientiFiltrati.filter((element) => {
        return element.stato === "In pronto soccorso" || element.stato === "Dimesso"
      })
    } else {
      return
    }

  }

  contaPazienti() {
    this.totPazienti = this.pazientiFiltrati.length
  }


  openDialog(paziente) {
    let dialogRef = this.dialog.open(DialogVisualizzaPazienteComponent, {data: {paziente: paziente}, autoFocus: false})

    dialogRef.afterClosed().subscribe(result => {





    })
  }




}
