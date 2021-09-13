import { Component, OnInit, OnChanges } from '@angular/core';
import { CurrentPathService } from '../current-path.service';
import {Router} from "@angular/router"
import { AjaxService } from '../ajax.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnChanges {

  bt1
  bt2
  bt3

  bt4
  label

  user = localStorage.getItem('username')

  constructor(private path: CurrentPathService, private router: Router,private ajaxService: AjaxService) { }

  ngOnInit(): void {
    this.changeSelected();
  }

  ngOnChanges(): void {
    this.changeSelected();
  }

  changeSelected() {     
    switch(this.path.currentPath()) {
      case "/soccorso": {
        this.bt1 = {
          backgroundColor: 'white',
          color: 'black'
        }
        this.bt2 = {
          backgroundColor: 'transparent',
          color: 'white'
        }
        this.bt3 = {
          backgroundColor: 'transparent',
          color: 'white'
        }
        this.bt4 = {
          display: 'block'
        }
        this.label = {
          display: 'inline'
        }

        break;
      }
      case "/stanze": {
        this.bt1 = {
          backgroundColor: 'transparent',
          color: 'white'
        }
        this.bt2 = {
          backgroundColor: 'white',
          color: 'black'
        }
        this.bt3 = {
          backgroundColor: 'transparent',
          color: 'white'
        }
        this.bt4 = {
          display: 'block'
        }
        this.label = {
          display: 'inline'
        }

        break;
      }
      case "/pazienti": {
        this.bt1 = {
          backgroundColor: 'transparent',
          color: 'white'
        }
        this.bt2 = {
          backgroundColor: 'transparent',
          color: 'white'
        }
        this.bt3 = {
          backgroundColor: 'white',
          color: 'black'
        }
        this.bt4 = {
          display: 'block'
        }
        this.label = {
          display: 'inline'
        }

        break;
      }
      case "/login": {
        this.bt1 = {
          display: 'none'
        }
        this.bt2 = {
          display: 'none'
        }
        this.bt3 = {
          display: 'none'
        }
        this.bt4 = {
          display: 'none'
        }
        this.label = {
          display: 'none'
        }
        break
      }
      default: {
        this.bt1 = {
          display: 'none'
        }
        this.bt2 = {
          display: 'none'
        }
        this.bt3 = {
          display: 'none'
        }
        this.bt4 = {
          display: 'none'
        }
        this.label = {
          display: 'none'
        }
      }
    }
  }

  changeRoute(route) {
    this.router.navigate(['/' + route])

    setTimeout(() => {
      this.changeSelected()
    }
    , 10);
  }

  logout() {
    this.ajaxService.post('operatore/logout', localStorage.getItem('username')).subscribe(response => {
      if(response) {
        localStorage.clear()
        this.router.navigate(['/login'])
      } else {

      }
    })

  }

  redirect() {
    this.router.navigate(['/aiuto'])
  }

}
