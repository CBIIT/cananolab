import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'canano-chemicalassociation',
  templateUrl: './chemicalassociation.component.html',
  styleUrls: ['./chemicalassociation.component.scss']
})
export class ChemicalassociationComponent implements OnInit {

    constructor( private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }

  ngOnInit(): void{
    this.route.params.subscribe(
        ( params: Params ) => {
          console.log(params['sampleId'])
          console.log(params['dataId'])
        } 
    );
}

}
