import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { PointOfContactService } from './point-of-contact.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component( {
    selector: 'canano-point-of-contact',
    templateUrl: './point-of-contact.component.html',
    styleUrls: ['./point-of-contact.component.scss']
} )
export class PointOfContactComponent implements OnInit, OnDestroy{

    // This will be an array of Points of Contact
    @Input() poc;
    @Input() sampleData;

    showPocEditor = false;
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private pointOfContactService: PointOfContactService){
        this.pointOfContactService.hidePointOfContactEditorEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
          () => {
              this.showPocEditor = false;
          });
    }

    ngOnInit(): void{
    }

    onEditPocClick(){
        this.showPocEditor = (! this.showPocEditor);
    }


    ngOnDestroy(){
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }
}
