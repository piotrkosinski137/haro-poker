import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {HelloService} from "../../api/hello.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {LoginModalComponent} from "../login-modal/login-modal.component";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})

export class TableComponent implements OnInit, AfterViewInit, OnDestroy {

  greeting: string;
  subscription: Subscription;
  cards$: Observable<any>;
  playerNames = [];

  constructor(private helloService: HelloService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.cards$ = this.helloService.getCards();
    this.subscription = this.helloService.getGreeting().subscribe(result => this.greeting = result.content);
  }

  ngAfterViewInit() {
    this.openModal();
  }

  openModal() {
    this.modalService.open(LoginModalComponent, {
      backdrop: 'static',
      keyboard: false
    }).result.then((playerName) => {
      this.playerNames.push(playerName)
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
