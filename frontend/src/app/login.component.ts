import {Component} from "@angular/core";

import {AuthenticationService} from "./authentication.service"

@Component({
  selector: 'login-form',
  templateUrl: 'login.component.html'
})
export class LoginComponent {
  error = false;

  credentials = {
    username: 'username',
    password: 'password'
  };

  constructor(private authenticationService: AuthenticationService) {}

  login() {
    this.authenticationService.login(this.credentials)
  }

}
