import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  header = false

  constructor(private router: Router) {
    this.router.events.subscribe((ev) => {
      if (ev instanceof NavigationEnd) {

        if(window.location.pathname === "/login" || window.location.pathname === "aiuto") {
          this.header = false
        } else if(window.location.pathname === "/soccorso" || window.location.pathname === "/stanze" || window.location.pathname === "/pazienti") {
          this.header = true
        } else {
          this.header = false
        }


      }
    });
  }

}
