import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
  currentSelectedItem;

  constructor() { }

  setCurrentSelectedItem(route) {
    this.currentSelectedItem=route;
}
getCurrentSelectedItem() {
    return this.currentSelectedItem;
}
}
