import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { DashboardComponent } from "./dashboard.component";
import { LoginComponent } from './login.component';
import { HeroService } from "./hero.service";
import { AuthenticationService } from "./authentication.service";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [
    AuthenticationService,
    HeroService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
