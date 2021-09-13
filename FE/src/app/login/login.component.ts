import { Component, OnInit } from '@angular/core';
import { AjaxService } from '../ajax.service';
import {Router} from "@angular/router"
import { typeofExpr } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public random = Math.floor((Math.random() * 7) + 1);

  public bgStyle = {
    background: "url('../../assets/img/login" + this.random + ".jpg') no-repeat fixed center",
    backgroundSize: "2000px"
  }

  public error = false
  public username = ""
  public password = ""

  constructor(private ajaxService: AjaxService, private router: Router) {}

  ngOnInit(): void {
  }

  login() {
    let body = {
      username: this.username,
      password: this.password
    }
    this.ajaxService.post('operatore/login', body).subscribe(response => {



      if(response !== null) {

        this.router.navigate(['/soccorso'])
        localStorage.setItem('username', body.username);
        console.log("Benvenuto " + localStorage.getItem('username'))
        this.error = false
      } else if(response === null) {


        localStorage.clear();
        this.error = true

        console.log("Errore nelle credenziali")
      }
    });
  }


}
