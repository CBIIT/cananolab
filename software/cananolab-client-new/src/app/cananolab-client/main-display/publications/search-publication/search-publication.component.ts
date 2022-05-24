import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Properties } from '../../../../../assets/properties';
@Component({
  selector: 'canano-search-publication',
  templateUrl: './search-publication.component.html',
  styleUrls: ['../../../../btn-bravo-canano.scss','./search-publication.component.scss']
})
export class SearchPublicationComponent implements OnInit {
    data;
    dataTrailer;
    helpUrl;
    toolHeadingNameManage;
    setupData;

    constructor(private httpClient:HttpClient) { }

    ngOnInit(): void {
        this.getSetupData();
        this.data={
            "category":null,
            "titleOperand":"contains",
            "nameOperand":"contains",
            "researchArea":[],
            "functionTypes":[]
        };
        this.dataTrailer=JSON.parse(JSON.stringify(this.data));
    }

    clickResearchArea(event) {
        if (event.target.checked) {
            this.data['researchArea'].push(event.target.value)
        }
        else {
            this.data['researchArea'].splice(this.data.researchArea.indexOf(event.target.value),1)
        }
    }

    getSetupData() {
        let url=this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/publication/setup');
        url.subscribe(data=> {
            this.setupData=data;
        },
        errors=> {

        })
    }

    reset() {
        this.data = JSON.parse(JSON.stringify(this.dataTrailer));
    }

    searchPublication() {
        console.log(this.data)
    }


}
