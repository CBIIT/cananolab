import { Component, OnInit } from '@angular/core';
import { Consts, ProtocolScreen } from '../../../constants';
import { ApiService } from '../../common/services/api.service';
import { Router } from '@angular/router';
import { ProtocolsService } from '../protocols/protocols.service';

@Component({
  selector: 'canano-my-workspace',
  templateUrl: './my-workspace.component.html',
  styleUrls: ['./my-workspace.component.scss']
})
export class MyWorkspaceComponent implements OnInit {
    helpUrl = Consts.HELP_URL_WORKSPACE;
    toolHeadingNameSearchSample = 'My Workspace';
    samples = [];
    protocols = [];
    publications = [];
    showSampleWorkspaces = true;
    showProtocolsWorkspaces = true;
    showPublicationsWorkspaces = true;
    errors={};
  constructor( private apiService: ApiService, private router: Router,
               private protocolsService: ProtocolsService ) { }

  ngOnInit(): void {
    this.loadData();

  }

  loadData() {
    this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=sample' ).subscribe(
        data => {
            this.samples = data['samples'];
            this.errors={};
        },

        ( err ) => {
            this.errors=err;
            console.log( 'ERROR QUERY_GET_WORKSPACE samples: ', err );
        } );

    this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=protocol' ).subscribe(
        data => {
            this.errors={};
            this.protocols = data['protocols'];
            console.log( 'QUERY_GET_WORKSPACE protocols: ', data );
        },

        ( err ) => {
            this.errors=err;
            console.error( 'ERROR QUERY_GET_WORKSPACE protocols: ', err );
        } );


    this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=publication' ).subscribe(
        data => {
            this.errors={};
            this.publications = data['publications'];

            console.log( 'QUERY_GET_WORKSPACE publications: ', data );
        },

        ( err ) => {
            this.errors=err;
            console.error( 'ERROR> QUERY_GET_WORKSPACE publications: ', err );
        } );
  }

    navigateToSampleView(sampleId, sampleName){
        this.router.navigate(['home/samples/view-sample', sampleId  ]);
    }

    navigateToSampleEdit(sampleId, sampleName){
        this.router.navigate(['home/samples/sample', sampleId ]);
    }

    /**
     * Delete from favorites
     * @param sampleId
     */
    navigateToSampleDelete(sampleId){
        if (confirm("Are you sure you wish to delete this sample?")) {
            this.apiService.doGet( Consts.QUERY_SAMPLE_DELETE_FROM_WORKSPACE, 'sampleId=' + sampleId ).subscribe(
                data => {
                    this.errors={};
                    this.loadData();
                },

                ( err ) => {
                    this.loadData();
                    console.error( '\'caNanoLab/rest/sample/deleteSampleFromWorkspace?sampleId=\'' + sampleId + ':', err );
                } );
        }
    }



    navigateToProtocolEdit(protocolId, protocolName){
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, protocolId );
        this.router.navigate(['home/protocols/edit-protocol',protocolId]);
    }

    navigateToProtocolDelete(protocolId){
        if (confirm("Are you sure you wish to delete this protocol?")) {
                this.apiService.doGet( Consts.QUERY_DELETE_PROTOCOL_BY_ID, 'protocolId=' + protocolId ).subscribe(
                    data => {
                    this.errors={};
                    this.loadData();
                },

                ( err ) => {
                    this.errors=err;
                    console.error( '\'caNanoLab/rest/sample/deleteProtocolById?protocolId=\'' + protocolId + ':', err );
                } );
        }

    }

    navigateToPublicationEdit(publicationId){
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, publicationId );
        this.router.navigate(['home/samples/publications/publication',publicationId]);
    }

    navigateToPublicationDelete(publicationId){
        if (confirm("Are you sure you wish to delete this publication?")) {
            this.apiService.doGet( Consts.QUERY_DELETE_PUBLICATION_BY_ID, 'publicationId=' + publicationId ).subscribe(
                data => {
                    this.errors={};
                    this.loadData();
                },

                ( err ) => {
                    this.errors=err;
                } );
        }

    }
}
