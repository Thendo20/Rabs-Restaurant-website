import {Component, signal} from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-navigation',
  imports: [
    RouterLink
  ],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.scss',
  standalone: true
})
export class NavigationComponent {
  isMenuOpen = signal(false);

  toggleMenu() {
    this.isMenuOpen.update(currentValue => !currentValue);
  }

  closeMenu() {
    this.isMenuOpen.set(false);
  }
}
