import { Component, OnInit } from '@angular/core';
import { CurrentPathService } from '../current-path.service';
import {Router} from "@angular/router"

@Component({
  selector: 'app-header-logout',
  templateUrl: './header-logout.component.html',
  styleUrls: ['./header-logout.component.css']
})
export class HeaderLogoutComponent implements OnInit {

  user = localStorage.getItem('username')


  constructor(private path: CurrentPathService, private router: Router) { }

  ngOnInit(): void {

  }

  redirect() {
    this.router.navigate(['/aiuto'])
  }
}
