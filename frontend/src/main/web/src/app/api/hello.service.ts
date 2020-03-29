import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HelloService {

  API_PATH = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getGreeting(): Observable<any> {
    return this.http.get(this.API_PATH +'/hello');
  }
}
