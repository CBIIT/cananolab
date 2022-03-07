import { EventEmitter, Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class SampleAvailabilityDisplayService {

    showSampleAvailabilityDisplayEmitter = new EventEmitter();

  constructor() { }

    displayStuff( s ){
        this.showSampleAvailabilityDisplayEmitter.emit( s );

    }
}
