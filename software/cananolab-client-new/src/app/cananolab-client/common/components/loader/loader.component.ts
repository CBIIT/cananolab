import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Input } from '@angular/core';
@Component({
  selector: 'canano-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit,OnChanges {
    constructor() { }
    @Input() loading;
    @Input() message;
    loadingMessage='Loading'
    ngOnInit(): void {
        if (this.message)
        {
            this.loadingMessage=this.message;
        }
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.message)
        {
            this.loadingMessage=this.message;
        }
    }

}
