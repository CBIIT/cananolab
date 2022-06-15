import { Component, OnInit } from '@angular/core';
import { SearchPublicationService } from './search-publication.service';
import { ApiService } from '../../../common/services/api.service';
import { Router } from '@angular/router';
import { Consts } from 'src/app/constants';
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
    loading;
    loadingMessage=Consts.searchingMessage;
    toolHeadingNameManage;
    setupData:any={
        publicationResearchAreas:[],
        publicationCategories:[],

    };

    constructor(private router:Router,private apiService:ApiService,private searchPublicationService:SearchPublicationService) { }

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
        this.loading=true;
        this.loadingMessage=Consts.loadingMessage;
        let url = this.apiService.doGet(Consts.QUERY_PUBLICATION_SETUP,'')
        url.subscribe(data=> {
            this.setupData=data;
            this.errors={};
            this.loading=null;
        },
        errors=> {
            this.loading=null;
            this.errors=errors;
        })
    }

    reset() {
        this.data = JSON.parse(JSON.stringify(this.dataTrailer));
    }

    searchPublication() {
        this.loading=true;
        this.loadingMessage=Consts.searchingMessage;
        this.apiService.doPost(Consts.QUERY_PUBLICATION_SEARCH,this.data).subscribe(data=> {
            this.errors={};
            this.searchPublicationService.setPublicationSearchResults(data);
            this.router.navigate( ['home/publications/publication-search-results'] );

        },
        error=> {
            this.loading=null;
            this.errors=error;
        })
    }

}
