import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Component, OnInit, Inject } from '@angular/core';
import province from 'src/assets/province-italia.json';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import nazioni from 'src/assets/nationalities.json';
import moment from 'moment';

@Component({
  selector: 'app-dialog-scannerizza-paziente',
  templateUrl: './dialog-scannerizza-paziente.component.html',
  styleUrls: ['./dialog-scannerizza-paziente.component.scss']
})
export class DialogScannerizzaPazienteComponent implements OnInit {

  scannerEnabled=true
  risultato
  isTrovato = {
    color: 'grey'
  }
  paziente

  lista_nazioni = [];
  province_italiane = [];

  vuoto = true
  todayDate:Date = new Date();

  dataCorretta = true

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.data.paziente = null


    for(let i = 0; i < province.length; i++) {
      this.province_italiane[i] = province[i].nome
    }

    for(let j = 0; j < nazioni.length; j++) {
      this.lista_nazioni[j] = nazioni[j]
    }
  }

  scanSuccessHandler(e) {
    this.scannerEnabled = false

    this.data.paziente = JSON.parse(e);
    this.data.paziente.data_nascita = new Date(this.data.paziente.data_nascita)
    this.dataNascita = true
    this.checkVuoto(this.data.paziente.nome, 'nome')
    this.checkVuoto(this.data.paziente.cognome, 'cognome')
    this.checkVuoto(this.data.paziente.cf, 'cf')

  }

  scanFailureHandler(e) {
    this.risultato = "Paziente non trovato"
  }


  nome = false
  cognome = false
  dataNascita = false
  cf = false

  checkVuoto(testo, stringa) {
    if(testo === "" || undefined) {
      switch(stringa) {
        case "nome":
          this.nome = false
          break;
        case "cognome":
          this.cognome = false
          break;
        case "dataNascita":
          this.dataNascita = false
          break;
        case "cf":
          this.cf = false
          break;
      }

    } else {
      switch(stringa) {
        case "nome":
          this.nome = true
          break;
        case "cognome":
          this.cognome = true
          break;
        case "dataNascita":
          this.dataNascita = true
          break;
        case "cf":
          this.cf = true
          break;
      }
    }

    if(this.nome && this.cognome && this.dataNascita && this.cf) {
      this.vuoto = false
    } else {
      this.vuoto = true
    }


  }

  controlloData(stringa) {

    var regExp = /[a-zA-Z]/g;

    if(regExp.test(stringa)) {
      this.dataCorretta = false

      return
    }

    if(stringa.length > 10 || stringa.length < 8) {

      this.dataCorretta = false

      return
    } else if (stringa.length >= 8 && stringa.length <= 10 ) {
      switch(stringa.length){
        case 8:
          if(stringa.charAt(1) === "/" && stringa.charAt(3) === "/"){
            break;
          } else {
            this.dataCorretta = false
            return
          }
        case 10:
          if(stringa.charAt(2) === "/" && stringa.charAt(5) === "/"){
            break;
          } else {
            this.dataCorretta = false
            return
          }
        case 9:
          if((stringa.charAt(1) === "/" && stringa.charAt(4) === "/") || (stringa.charAt(2) === "/" && stringa.charAt(4) === "/")){
            break;
          } else {
            this.dataCorretta = false
            return
          }
      }
    } else {
      this.dataCorretta = false

      return
    }

    var dateMomentObject = moment(stringa, "DD/MM/YYYY");
    let dataInserita = dateMomentObject.toDate();





    if(dataInserita <= this.todayDate) {
      this.dataCorretta = true
      return;
    } else {
      this.dataCorretta = false
      return;
    }
  }


}
