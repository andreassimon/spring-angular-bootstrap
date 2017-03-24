import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions} from '@angular/http';
import {Credentials} from "./credentials";

@Injectable()
export class AuthenticationService {
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {
  }

  login(credentials: Credentials) {
    this.http.get('http://localhost:8080/user/', new RequestOptions({
      headers: new Headers({
        authorization: `Basic ${btoa(`${credentials.username}:${credentials.password}`)}`
      })
    })).subscribe(response => {
      alert(response);
    })
  }

}
