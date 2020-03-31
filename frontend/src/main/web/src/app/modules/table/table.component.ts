import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import {HelloService} from "../../api/hello.service";
import {NgbModal, NgbModalConfig, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit, AfterViewInit , OnDestroy {

  greeting: string;
  subscription: Subscription;
  cards = ['2C', '6H', '8C', 'QS', 'QH'];
  playerNames = [];

  @ViewChild('loginModal', {static: false})
  loginModal: any;

  modalOption: NgbModalOptions = {};

  constructor(private helloService: HelloService, config: NgbModalConfig, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.subscription = this.helloService.getGreeting().subscribe(result => this.greeting = result.content);
  }

  ngAfterViewInit(){
    this.openModal();
  }

  openModal() {
    this.modalOption.backdrop = 'static';
    this.modalOption.keyboard = false;
    this.modalService.open(this.loginModal, this.modalOption).result.then((playerName) => {
      this.playerNames.push(playerName)
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
