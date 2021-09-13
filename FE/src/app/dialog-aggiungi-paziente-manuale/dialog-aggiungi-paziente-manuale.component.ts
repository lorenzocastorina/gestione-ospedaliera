import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ZXingScannerModule } from '@zxing/ngx-scanner';

import province from 'src/assets/province-italia.json';
import nazioni from 'src/assets/nationalities.json';
import { enableDebugTools } from '@angular/platform-browser';
import moment from 'moment';

@Component({
  selector: 'app-dialog-aggiungi-paziente-manuale',
  templateUrl: './dialog-aggiungi-paziente-manuale.component.html',
  styleUrls: ['./dialog-aggiungi-paziente-manuale.component.scss']
})
export class DialogAggiungiPazienteManualeComponent implements OnInit {



  lista_nazioni = [];
  province_italiane = [];

  vuoto = true
  todayDate:Date = new Date();

  dataCorretta = true



  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    for(let i = 0; i < province.length; i++) {
      this.province_italiane[i] = province[i].nome
    }

    for(let j = 0; j < nazioni.length; j++) {
      this.lista_nazioni[j] = nazioni[j]
    }


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
