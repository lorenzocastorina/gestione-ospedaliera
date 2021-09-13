import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { CheckLoginService } from '../check-login.service';
import { DialogVisualizzaLettoComponent } from '../dialog-visualizza-letto/dialog-visualizza-letto.component';
import { AjaxService } from '../ajax.service';
import { Letto } from '../interfacce/letto';
import { DialogPazienteNonPresenteComponent } from '../dialog-paziente-non-presente/dialog-paziente-non-presente.component';

@Component({
  selector: 'app-stanze',
  templateUrl: './stanze.component.html',
  styleUrls: ['./stanze.component.css']
})
export class StanzeComponent implements OnInit {



  public letti 
  public isDataLoaded = false


  constructor(public dialog: MatDialog, public check: CheckLoginService, private ajaxService: AjaxService) {}

  ngOnInit(): void {
    this.check.check()

    this.ajaxService.get("letto/getLetti").subscribe(response => {
      this.letti = response
      this.isDataLoaded = true
    })
  }

  apriDialog(l) {
    let dialogRef
    let infoLetto = this.letti[l-1]
    let infoPaziente

    this.ajaxService.get("paziente/letto/" + l).subscribe(response => {

      if(response === null) {
        dialogRef = this.dialog.open(DialogPazienteNonPresenteComponent, {data: {letto: infoLetto}, autoFocus: false})

        dialogRef.afterClosed().subscribe(result => {

          this.ajaxService.get("letto/getLetti").subscribe(response => {
            this.letti = response
          })

        })
      } else {

        infoPaziente = response
        dialogRef = this.dialog.open(DialogVisualizzaLettoComponent, {data: {paziente: infoPaziente, letto: infoLetto}, autoFocus: false})

        dialogRef.afterClosed().subscribe(result => {

          if(result !== undefined && result !== null ) {
            this.letti = result
          }


        })
      }

    },
    error => {
      console.log(error)

      dialogRef = this.dialog.open(DialogPazienteNonPresenteComponent, {data: {letto: infoLetto}, autoFocus: false})

        dialogRef.afterClosed().subscribe(result => {

          this.ajaxService.get("letto/getLetti").subscribe(response => {
            this.letti = response
          })

        })
    })




  }


  aggiornaStati() {

  }
}


