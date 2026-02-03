import {inject, Injectable} from '@angular/core';
import { MenuItem } from '../entity/menuItem.type';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private apiUrl = 'http://localhost:8081/api/v1/public';
  http = inject(HttpClient);

  constructor() { }

  private menuItems: Array<MenuItem> = [
    {
      itemId: 1,
      itemName: 'Pap and Gravy',
      price: 50.00,
      description: "Some delicious food",
      stockCount: 5
    },
    {
      itemId: 2,
      itemName: 'Pap and chicken',
      price: 50.00,
      description: "Some really delicious food",
      stockCount: 10
    }
  ];

  fetchMenuItems(): Observable<MenuItem[]> {
    return this.http.get<Array<MenuItem>>(`${this.apiUrl}/items`)
      .pipe(
        catchError(this.handleError<MenuItem[]>('getMenuItems, []'))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T)
    }
  }

}
