import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Properties } from '../../../../../assets/properties';
import { SearchPublicationService } from './search-publication.service';
import { ApiService } from '../../../common/services/api.service';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'canano-search-publication',
  templateUrl: './search-publication.component.html',
  styleUrls: ['../../../../btn-bravo-canano.scss','./search-publication.component.scss']
})
export class SearchPublicationComponent implements OnInit {
    data;
    dataResults;
    dataTrailer;
    errors;
    helpUrl;
    toolHeadingNameManage;
    setupData;

    constructor(private router:Router,private apiService:ApiService,private searchPublicationService:SearchPublicationService,private httpClient:HttpClient) { }

    ngOnInit(): void {
        this.errors={};
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
            this.errors={};
        },
        errors=> {
            this.errors=errors;
        })
    }

    reset() {
        this.data = JSON.parse(JSON.stringify(this.dataTrailer));
    }

    searchPublication() {
        this.apiService.doPost('caNanoLab/rest/publication/searchPublication',this.data).subscribe(data=> {
            this.errors={};
            this.searchPublicationService.setPublicationSearchResults(data);
            this.router.navigate( ['home/publications/publication-search-results'] );

        },
        error=> {
            this.errors=error;
        })
    }

}
