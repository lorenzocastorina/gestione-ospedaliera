import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'
import { DialogAggiungiPazienteManualeComponent } from '../dialog-aggiungi-paziente-manuale/dialog-aggiungi-paziente-manuale.component';
import { CheckLoginService } from '../check-login.service';
import { AjaxService } from '../ajax.service';
import { DialogScannerizzaPazienteComponent } from '../dialog-scannerizza-paziente/dialog-scannerizza-paziente.component';
import { DialogConfermaComponent } from '../dialog-conferma/dialog-conferma.component';
import { DialogPazienteGiaPresenteComponent } from '../dialog-paziente-gia-presente/dialog-paziente-gia-presente.component';
import { DialogLettiOccupatiComponent } from '../dialog-letti-occupati/dialog-letti-occupati.component';
import html2pdf from 'html2pdf.js'


@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {


  lista_pazienti:any = [];
  constructor(public dialog: MatDialog, public check: CheckLoginService, private ajaxService: AjaxService) { }

  ngOnInit(): void {
    this.check.check()

    this.getPazienti()


  }



  paziente_corrente = {
    id: "",
    nome: "",
    cognome: "",
    sesso: "",
    nazionalita: "",
    data_nascita: null,
    luogo_nascita: "",
    cf: "",
    data_entrata: null,
    codice: "",
    diagnosi: "",
    documenti: []
  }

  paziente_dialog = {
    id: "",
    nome: "",
    cognome: "",
    sesso: "",
    nazionalita: "",
    data_nascita: null,
    luogo_nascita: "",
    cf: "",
    data_entrata: null,
    codice: "",
    diagnosi: "",
    documenti: []
  }

  // template per generare i Qr Code{ "id": "", "nome": "Franco", "cognome": "Tozzi", "sesso": "M", "data_nascita": null, "nazionalita": "Italian", "luogo_nascita": "Roma", "cf": "HGRFHE67G23J509I", "data_ricovero": "" , "codice": "VERDE", "diagnosi": "boh", "documenti": [] }





  onRowClicked(item) {
    this.paziente_corrente = item;

  }

  openDialog() {


    let dialogRef = this.dialog.open(DialogAggiungiPazienteManualeComponent, {data: {paziente: {}}, autoFocus: false})

    dialogRef.afterClosed().subscribe(result => {

      if(result !== null && result !== undefined) {
      this.paziente_dialog.nome = result.paziente.nome
      this.paziente_dialog.cognome = result.paziente.cognome
      this.paziente_dialog.sesso = result.paziente.sesso ? result.paziente.sesso : "ND"
      this.paziente_dialog.nazionalita = result.paziente.nazionalita ? result.paziente.nazionalita : ""
      this.paziente_dialog.data_nascita = new Date(result.paziente.data_nascita)
      this.paziente_dialog.luogo_nascita = result.paziente.luogo_nascita ? result.paziente.luogo_nascita : ""
      this.paziente_dialog.cf = result.paziente.cf
      this.paziente_dialog.data_entrata = new Date()
      this.paziente_dialog.codice = result.paziente.codice ? result.paziente.codice : "BIANCO"
      this.paziente_dialog.diagnosi = result.paziente.diagnosi ? result.paziente.diagnosi : ""


      this.ajaxService.post("paziente/aggiungi", this.paziente_dialog).subscribe(response => {


        location.reload();
        return false;
      },
      error => {
        console.log(error)
        this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_dialog, stato: "errore2"}, autoFocus: false})
      })
      } else {

      }
    })


  }

  openDialog2() {
    let dialogRef = this.dialog.open(DialogScannerizzaPazienteComponent, {data: {paziente: {}}, autoFocus: false})


    dialogRef.afterClosed().subscribe(result => {

      if(result !== null && result !== undefined) {
        this.paziente_dialog.nome = result.paziente.nome
        this.paziente_dialog.cognome = result.paziente.cognome
        this.paziente_dialog.sesso = result.paziente.sesso ? result.paziente.sesso : "ND"
        this.paziente_dialog.nazionalita = result.paziente.nazionalita ? result.paziente.nazionalita : ""
        this.paziente_dialog.data_nascita = new Date(result.paziente.data_nascita)
        this.paziente_dialog.luogo_nascita = result.paziente.luogo_nascita ? result.paziente.luogo_nascita : ""
        this.paziente_dialog.cf = result.paziente.cf
        this.paziente_dialog.data_entrata = new Date()
        this.paziente_dialog.codice = result.paziente.codice ? result.paziente.codice : "BIANCO"
        this.paziente_dialog.diagnosi = result.paziente.diagnosi ? result.paziente.diagnosi : ""

      this.ajaxService.post("paziente/aggiungi", this.paziente_dialog).subscribe(response => {

        location.reload();
        return false;
      },
      error => {
        console.log(error)
        this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_dialog, stato: "errore2"}, autoFocus: false})
      })
      } else {

      }
    })

  }

  openDialog3() {
    let dialogRef = this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_corrente, stato: "pronto"}, autoFocus: false})


    dialogRef.afterClosed().subscribe(result => {


      if(result !== null && result !== undefined) {

        result.paziente.codice = this.cambiaColore(result.paziente.codice)

        this.ajaxService.post("paziente/ricovera", result.paziente).subscribe(response => {
          if(response) {
            for( var i = 0; i < this.lista_pazienti.length; i++){

             if ( this.lista_pazienti[i].cf === result.paziente.cf) {

                 this.lista_pazienti.splice(i, 1);
             }
           }
         }
       },
       error => {
         console.log(error)
         let dialogRef = this.dialog.open(DialogLettiOccupatiComponent, {autoFocus: false})
         dialogRef.afterClosed().subscribe(result => {})

       })
      } else {

      }

    })

    this.getPazienti()
  }

  openDialog4() {
    let dialogRef = this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_corrente, stato: "pronto2"}, autoFocus: false})


    dialogRef.afterClosed().subscribe(result => {


      if(result !== null && result !== undefined) {

        result.paziente.codice = this.cambiaColore(result.paziente.codice)

        this.ajaxService.post("paziente/dimettiSoccorso", result.paziente).subscribe(response => {
          if(response) {
            this.salvaDiagnosi(result.paziente)
            for( var i = 0; i < this.lista_pazienti.length; i++){

             if ( this.lista_pazienti[i].cf === result.paziente.cf) {

                 this.lista_pazienti.splice(i, 1);
             }
           }

         }
       },
       error => {
         console.log(error)
         this.dialog.open(DialogConfermaComponent, {data: {paziente: this.paziente_corrente, stato: "errore"}, autoFocus: false})


       })
      } else {

      }

    })

    this.getPazienti()
  }

  salvaDiagnosi(paziente) {
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

  getPazienti() {



    this.paziente_corrente = {
      id: "",
    nome: "",
    cognome: "",
    sesso: "",
    nazionalita: "",
    data_nascita: null,
    luogo_nascita: "",
    cf: "",
    data_entrata: null,
    codice: "",
    diagnosi: "",
    documenti: []
    }

    this.ajaxService.get("paziente/soccorso").subscribe(response => {


      this.lista_pazienti = response

      for(let i = 0; i < this.lista_pazienti.length; i++) {
        var data_salvata = this.lista_pazienti[i].data_entrata
        var data_salvata2 = this.lista_pazienti[i].data_nascita
        this.lista_pazienti[i].data_entrata = new Date(data_salvata)
        this.lista_pazienti[i].data_nascita = new Date(data_salvata2)

        this.lista_pazienti[i].codice = this.cambiaColore(this.lista_pazienti[i].codice)
      }


    })
  }




  cambiaColore(stringa) {
    switch(stringa) {
      case "ROSSO":
        return "#aa2e25"
      break;
      case "GIALLO":
        return "#ffc233"
      break;
      case "VERDE":
        return "#47824a"
      break;
      case "BIANCO":
        return "#e0e0e0"
      break;
    }
  }

}

