import { Injectable } from '@angular/core';
import { AjaxService } from './ajax.service';
import {Router} from "@angular/router"

@Injectable({
  providedIn: 'root'
})
export class CheckLoginService {

  constructor(private ajaxService: AjaxService, private router: Router) { }

  check() {

    this.ajaxService.get("operatore/isLogged/" + localStorage.getItem('username')).subscribe(response => {

      if(response) {
        console.log("Bentornato " + localStorage.getItem('username'))

      } else {
        localStorage.clear();
        this.router.navigate(['/login'])
        console.log("Nessun utente loggato " + localStorage.getItem('username'))
      }
    });
  }

}
