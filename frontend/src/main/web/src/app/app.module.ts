import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { TableComponent } from './modules/table/table.component';
import {NgbModal, NgbModalConfig, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule} from "@angular/forms";
import { LoginModalComponent } from './modules/login-modal/login-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    TableComponent,
    LoginModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FormsModule
  ],
  providers: [NgbModalConfig, NgbModal],
  entryComponents: [LoginModalComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
