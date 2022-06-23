// --------------------------------------------------------------------------------------------
// ------ This is the Publications screen which is called from  -------------------------------
// ------ the top left "Navigation Tree" menu when a specific Sample has been selected. -------
// ------ Not to be confused with "Publications" in the top horizontal menu.  -----------------
// --------------------------------------------------------------------------------------------

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { NavigationService } from '../../../../common/services/navigation.service';
import { Router } from '@angular/router';
import { ApiService } from '../../../../common/services/api.service';
import { StatusDisplayService } from 'src/app/cananolab-client/status-display/status-display.service';
@Component( {
    selector: 'canano-sample-publications',
    templateUrl: './sample-publications.component.html',
    styleUrls: ['./sample-publications.component.scss']
} )
export class SamplePublicationsComponent implements OnInit{
    sampleId = Properties.CURRENT_SAMPLE_ID;
    helpUrl = Consts.HELP_URL_SAMPLE_PUBLICATIONS;
    propertiesLoaded;
    data;
    publicationData;
    toolHeadingNameManage;
    editUrl=false;
    constructor( private statusDisplayService:StatusDisplayService,private apiService:ApiService,private router:Router,private navigationService:NavigationService,private route: ActivatedRoute ){
    }

    ngOnInit(): void{
        this.editUrl=this.statusDisplayService.isEditUrl();
        this.publicationData={};
        this.navigationService.setCurrentSelectedItem(3);
        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId=params['sampleId'];
                this.apiService.getSampleName(this.sampleId).subscribe(
                    data=>this.toolHeadingNameManage='Sample ' +data['sampleName'] + ' Publication'
                )

                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                };
                let url = this.apiService.doGet(Consts.QUERY_PUBLICATION_SUMMARY_VIEW,'sampleId='+this.sampleId);
                url.subscribe(data=> {
                    console.log(data)
                    this.data=data;
                    this.propertiesLoaded=true;
                    Properties.SAMPLE_TOOLS = true;
                    this.separateDataSets(data);

                },
                error=>{

                })
            })


    }

    addPublication(type) {
        this.router.navigate(['/home/samples/publications/publication',this.sampleId,type]);
    }

    editpublication(type,publicationId) {
        console.log(publicationId)
        this.router.navigate(['/home/samples/publications/publication',this.sampleId,publicationId,type]);
    }


    separateDataSets(data) {
        let setupUrl=this.apiService.doGet(Consts.QUERY_PUBLICATION_SETUP,'');
        setupUrl.subscribe(setupData=> {
            let defaultCategories = setupData['publicationCategories'];
            defaultCategories.forEach(item=> {
                this.publicationData[item]=[];
            });

            let keys = Object.keys(data.category2Publications);
            keys.forEach(category=> {
                if (!this.publicationData[category]){
                    this.publicationData[category]=[];
                }

                let currentArray=data.category2Publications[category];
                if (currentArray.length) {
                    currentArray.forEach(item=> {
                        this.publicationData[category].push(item);
                    })
                }
            })
        })


    }

    splitKeywords(keywords) {
        return keywords.split('\n')
    }

}
