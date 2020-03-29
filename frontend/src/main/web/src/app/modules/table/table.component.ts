import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {HelloService} from "../../api/hello.service";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit, OnDestroy {

  greeting: string;
  subscription: Subscription;
  cards = ['2C', '6H', '8C', 'QS', 'QH'];

  constructor(private helloService: HelloService) {
  }

  ngOnInit(): void {
    this.subscription = this.helloService.getGreeting().subscribe(result => this.greeting = result.content);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
