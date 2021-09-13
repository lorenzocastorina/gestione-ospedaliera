import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CurrentPathService {

  constructor() { }


  currentPath() {
    
    return window.location.pathname;
  }
}
