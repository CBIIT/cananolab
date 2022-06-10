import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';
@Component({
  selector: 'canano-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit {
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

}
