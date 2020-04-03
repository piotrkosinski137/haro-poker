import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class HelloService {

  constructor(private http: HttpClient) {
  }

  getGreeting(): Observable<any> {
    return this.http.get(environment.PROXY_PATH + 'hello');
  }
}
