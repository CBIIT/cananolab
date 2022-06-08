import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../../constants';
import { PointOfContactService } from '../../../../point-of-contact/point-of-contact.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/cananolab-client/common/services/api.service';

@Component( {
    selector: 'canano-sample-create',
    templateUrl: './sample-create.component.html',
    styleUrls: ['./sample-create.component.scss']
} )
export class SampleCreateComponent implements OnInit{
    currentDropdownValues={};
    data;
    errors={};
    helpUrl = Consts.HELP_URL_SAMPLE_EDIT;
    pointOfContact;
    pointOfContactIndex;

    constructor(private apiService:ApiService,private pointOfContactService: PointOfContactService, private httpClient: HttpClient,
                 private router: Router){
    }

    ngOnInit(): void{
        this.apiService.doGet(Consts.QUERY_SAMPLE_SUBMISSION_SETUP,'').subscribe(data=> {
            this.data=data;
            this.data['sampleId']=0;
            this.data.pointOfContact={dirty:true,organization:{name:""},address:{},role:""};
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

    onSaveSample(){
        this.data.pointOfContacts.push(this.data.pointOfContact);
        delete this.data.pointOfContact;
        this.apiService.doPost(Consts.QUERY_SAMPLE_POC_UPDATE_SAVE,this.data).subscribe(data=> {
            this.router.navigate(['home/samples/sample',data.sampleId]);
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
