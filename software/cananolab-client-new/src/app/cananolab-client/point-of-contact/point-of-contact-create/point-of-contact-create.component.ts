import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { PointOfContactService } from '../point-of-contact.service';
import { UtilService } from '../../common/services/util.service';
import { Consts } from '../../../constants';
import { ApiService } from '../../common/services/api.service';

@Component( {
    selector: 'canano-point-of-contact-create',
    templateUrl: './point-of-contact-create.component.html',
    styleUrls: ['./point-of-contact-create.component.scss']
} )
export class PointOfContactCreateComponent implements OnInit, AfterViewInit{
    @Input() sampleData = '';
    @Input() poc = [];
    @Input() sampleName = '';

    firstName = '';
    middleInitial = '';
    lastName = '';
    phoneNumber = '';
    email = '';
    addressLine1 = '';
    addressLine2 = '';
    addressCity = '';
    addressStateProvince = '';
    addressZip = '';
    addressCountry = '';

    role = '';
    organization = '';
    pocData = {};
    selectedOrganization = '';
    selectedRole = '';
    showPocCreate = false;

    constructor( private pointOfContactService: PointOfContactService, private utilService: UtilService,
                 private apiService: ApiService){
    }

    ngOnInit(): void{
        this.pointOfContactService.showPointOfContactCreateEmitter.subscribe(
            ( data ) => {
                this.showPocCreate = data;
            }
        );

        if( this.sampleData === undefined ){
            this.sampleData = '';
        }
/*

        else{
            this.selectedOrganization = this.sampleData['organizationNamesForUser'][0];
            this.selectedRole = this.sampleData['contactRoles'][0]
        }
*/
    }

    onPocCreateSaveClick(){
        let dto = {};
        dto['sampleName'] = this.sampleName;
        dto['newSampleName'] = null;
        dto['sampleId'] = 0;
        let pointOfContacts = [];

        this.pocData['organization'] = {};
        this.pocData['organization']['name'] = this.selectedOrganization;
        this.pocData['dirty'] = true;

        this.pocData['firstName'] = this.firstName;
        this.pocData['role'] = this.selectedRole;
        this.pocData['middleInitial'] = this.middleInitial;
        this.pocData['lastName'] = this.lastName;
        this.pocData['phoneNumber'] = this.phoneNumber;
        this.pocData['email'] = this.email;
        this.pocData['address'] = {};
        this.pocData['address']['line1'] = this.addressLine1;
        this.pocData['address']['line2'] = this.addressLine2;
        this.pocData['address']['city'] = this.addressCity;
        this.pocData['address']['stateProvince'] = this.addressStateProvince;
        this.pocData['address']['zip'] = this.addressZip;
        this.pocData['address']['country'] = this.addressCountry;
        pointOfContacts.push( this.pocData);
        dto['pointOfContacts'] = pointOfContacts;

        this.apiService.doPost( Consts.QUERY_SAMPLE_POC_UPDATE_SAVE, dto ).subscribe(
            data => {
                this.pocData['sampleId'] = data['sampleId']; // The Sample creator will need this
                this.pointOfContactService.emitNewPoc( this.pocData );
                this.showPocCreate = false;
            },
            (err) => {
                console.error('server POC save: ', err);
                this.showPocCreate = false;
            });
    }

    roleSelectClicked( rol ){
        this.selectedRole = rol; // ngModel should have done this :(
    }

    // @TODO Rename
    roleOrganizationClicked( org ){
        this.selectedOrganization = org;
    }

    onPocCreateCancelClick(){
        this.showPocCreate = false;
    }

    /*
     * sampleData, which has the data for the two dropdowns,
     * arrives as @Input() sampleData.
     * This value is not available instantly, so I wait here so I can set the initial values
     */
    async ngAfterViewInit(): Promise<void>{
        // So we don't hang if there is a problem sampleData['organizationNamesForUser']
        let runaway = 1000; // @TEST  this is normal X 10
        while( (this.sampleData['organizationNamesForUser'] === undefined ) && (runaway > 0 ) ) {
            runaway--;
            await this.utilService.sleep( 150 ); // @TEST  this is normal X 10
        }
        this.selectedOrganization = this.sampleData['organizationNamesForUser'][0];
        this.selectedRole = this.sampleData['contactRoles'][0]
    }

}
