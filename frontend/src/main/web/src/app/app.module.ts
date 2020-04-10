import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {TableComponent} from './modules/table/table.component';
import {NgbModal, NgbModalConfig, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule} from "@angular/forms";
import {LoginModalComponent} from './modules/login-modal/login-modal.component';
import {BetDashboardComponent} from './modules/table/bet-dashboard/bet-dashboard.component';
import {PlayerGameDashboardComponent} from "./modules/table/player-game-dashboard/player-game-dashboard.component";
import {AdminPanelComponent} from './modules/admin-panel/admin-panel.component';
import {BlindPanelComponent} from './modules/admin-panel/blind-panel/blind-panel.component';
import {PlayerBidsPanelComponent} from './modules/admin-panel/player-bids-panel/player-bids-panel.component';
import {NavbarComponent} from './modules/navbar/navbar.component';
import {GamePlayerSocketService} from "./api/websocket/game-player-socket.service";
import {GamePlayerRestService} from "./api/rest/game-player-rest.service";

@NgModule({
  declarations: [
    AppComponent,
    TableComponent,
    LoginModalComponent,
    BetDashboardComponent,
    PlayerGameDashboardComponent,
    AdminPanelComponent,
    BlindPanelComponent,
    PlayerBidsPanelComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NgbModule,
    FormsModule
  ],
  providers: [GamePlayerSocketService, GamePlayerRestService, NgbModalConfig, NgbModal],
  entryComponents: [LoginModalComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
