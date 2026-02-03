import {Component, inject, OnInit, signal, WritableSignal} from '@angular/core';
import { MenuService } from '../../services/menu.service';
import { MenuItem } from '../../entity/menuItem.type';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-menu',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './menu.component.html',
  standalone: true,
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  menuService = inject(MenuService);
  menuItems = signal<Array<MenuItem>>([]);
  currentIndex = signal<number>(0);
  isLoading = signal(true);
  error: WritableSignal<string | null> = signal(null);

  ngOnInit() {
    this.loadMenuItems();
  }

  currentMenuItem(): MenuItem {
    return this.menuItems()[this.currentIndex()];
  }
  previousMenuItem(): void {
    const current = this.currentIndex();
    const maxIndex = this.menuItems().length - 1;
    this.currentIndex.set(current == 0 ? maxIndex : current - 1);
  }

  nextMenuItem(): void {
    const current = this.currentIndex();
    const maxIndex = this.menuItems().length - 1;
    this.currentIndex.set(current == maxIndex ? 0 : current + 1);
  }

  loadMenuItems(): void {
    this.isLoading.set(true);
    this.error.set(null);

    this.menuService.fetchMenuItems().subscribe({
      next: (menuItems) => {
        this.menuItems.set(menuItems);
      },
      error: (error) => {
      this.error.set('Failed to load menu. Please try again later');
      this.isLoading.set(false);
      console.error('Error loading menu:', error);
    },
      complete: () => {
        this.isLoading.set(false);
        console.log('Menu items loaded.');}
    });
  }

  formatPrice(price: number): string {
    return `R${price.toFixed(2)}`;
  }

  retryLoad(): void {
    this.loadMenuItems();
  }

  openMenu(): void {
    window.open('assets/images/menu.png', '_blank');
  }

  openAltMenu(): void {
    window.open('assets/images/alt-menu.png', '_blank');
  }
}
