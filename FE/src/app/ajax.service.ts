import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AjaxService {

  url = 'http://127.0.0.1';
  port = ':8080';



  constructor(private http: HttpClient) { }

  get(path) {
    return this.http.get(this.url + this.port + "/" + path)
  }

  post(path, body) {

    return this.http.post(this.url + this.port + "/" + path, body)
  }
}
