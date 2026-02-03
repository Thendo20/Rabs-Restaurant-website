import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import {NavigationComponent} from '../components/navigation/navigation.component';

@Component({
  selector: 'app-home',
  imports: [
    NavigationComponent,
    RouterLink
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  standalone: true
})
export class HomeComponent {

}
