import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.scss']
})
export class LoginModalComponent {

  constructor(public activeModal: NgbActiveModal) {
  }

  onModalSubmitted(playerName: string) {
    this.activeModal.close(playerName);
  }
}
