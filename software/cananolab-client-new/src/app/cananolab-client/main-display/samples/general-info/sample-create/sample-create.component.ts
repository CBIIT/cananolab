import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../../constants';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/cananolab-client/common/services/api.service';
import { Properties } from 'src/assets/properties';
@Component( {
    selector: 'canano-sample-create',
    templateUrl: './sample-create.component.html',
    styleUrls: ['./sample-create.component.scss']
} )
export class SampleCreateComponent implements OnInit{
    currentDropdownValues={};
    data;
    dataTrailer;
    errors={};
    helpUrl = Consts.HELP_URL_SAMPLE_EDIT;
    pointOfContact;
    pointOfContactIndex;

    constructor(private apiService:ApiService,private httpClient: HttpClient,
                 private router: Router){
    }

    ngOnInit(): void{
        setTimeout(()=> {
            Properties.SAMPLE_TOOLS = false;
        })
        this.apiService.doGet(Consts.QUERY_SAMPLE_SUBMISSION_SETUP,'').subscribe(data=> {
            this.data=data;
            this.data['sampleId']=0;
            this.pointOfContact={dirty:true,organization:{name:""},address:{},role:""};
            this.dataTrailer=JSON.parse(JSON.stringify(this.data));
        },
        errors=> {
            this.errors=errors;
        })
    }

    addOtherValue(field,currentValue) {
        this.currentDropdownValues[field]=currentValue;
    };

    addPointOfContact() {
        this.pointOfContactIndex=-1;
    }

    reset() {
        this.data=JSON.parse(JSON.stringify(this.dataTrailer));
        this.pointOfContact={dirty:true,organization:{name:""},address:{},role:""};
        this.errors={};
    }

    onSaveSample(){
        this.data.pointOfContacts.push(this.pointOfContact);
        this.apiService.doPost(Consts.QUERY_SAMPLE_POC_UPDATE_SAVE,this.data).subscribe(data=> {
            if( data['errors'].length > 0 ){
                this.errors['error'] = data['errors'];
            }else{
                this.router.navigate( ['home/samples/sample', data.sampleId] );
            }
        },
        errors=>{
            this.errors=errors;
        })
    }

    saveOther(newItem: Object) {
        if (newItem['change'] && newItem['value']) {
            this.addDropdownItem(newItem['array'],newItem['value'])
            this.setValue(newItem['field'],newItem['value']);
        }
        else {
            this.setValue(newItem['field'],newItem['value']);
        }
    };
}
