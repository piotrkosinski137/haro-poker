import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelloService} from "./api/hello.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  greeting: string;
  subscription: Subscription;

  constructor(private helloService: HelloService) {
  }

  ngOnInit(): void {
    this.subscription = this.helloService.getGreeting().subscribe(result => this.greeting = result.content);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
