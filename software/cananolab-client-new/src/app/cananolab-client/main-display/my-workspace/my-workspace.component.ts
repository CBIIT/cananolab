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

  constructor( private apiService: ApiService, private router: Router,
               private protocolsService: ProtocolsService ) { }

  ngOnInit(): void {
      this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=sample' ).subscribe(
          data => {
              this.samples = data['samples'];
              console.log( 'MHL QUERY_GET_WORKSPACE samples: ', data );
          },

          ( err ) => {
              console.error( 'MHL ERROR> QUERY_GET_WORKSPACE samples: ', err );
          } );

      this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=protocol' ).subscribe(
          data => {
              this.protocols = data['protocols'];
              console.log( 'MHL QUERY_GET_WORKSPACE protocols: ', data );
          },

          ( err ) => {
              console.error( 'MHL ERROR> QUERY_GET_WORKSPACE protocols: ', err );
          } );


      this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=publication' ).subscribe(
          data => {
              this.publications = data['publications'];
              console.log( 'MHL QUERY_GET_WORKSPACE publications: ', data );
          },

          ( err ) => {
              console.error( 'MHL ERROR> QUERY_GET_WORKSPACE publications: ', err );
          } );

  }


    navigateToSampleView(sampleId, sampleName){
        this.router.navigate(['home/samples/view-sample', '?sampleId=' + sampleId + '&sampleName=' + sampleName ]);
    }


    navigateToSampleEdit(sampleId, sampleName){
        this.router.navigate(['home/samples/sample', '?sampleId=' + sampleId + '&sampleName=' + sampleName ]);
    }

    /**
     * Delete from favorites
     * @param sampleId
     */
    navigateToSampleDelete(sampleId){

        this.apiService.doGet( 'caNanoLab/rest/sample/deleteSampleFromWorkspace', 'sampleId=' + sampleId ).subscribe(
            data => {
                console.log( 'MHL Answer from \'caNanoLab/rest/sample/deleteSampleFromWorkspace?sampleId=\'' + sampleId + ':', data );
            },

            ( err ) => {
                console.error( 'MHL ERROR from \'caNanoLab/rest/sample/deleteSampleFromWorkspace?sampleId=\'' + sampleId + ':', err );

            } );
    }


    navigateToProtocolView(protocolId, sampleName){
        console.log('MHL navigateToProtocolView: ', protocolId);
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_VIEW_SCREEN, protocolId );

        this.router.navigate(['home/protocols/protocolViewComponent']);
    }

    navigateToProtocolEdit(protocolId, protocolName){
        console.log('MHL navigateToProtocolEdit protocolId: ' + protocolId + '    name: ', protocolName);
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, protocolId );
        this.router.navigate(['home/protocols/protocolEditComponent']);
    }

    navigateToProtocolDelete(protocolId){
        console.log('MHL navigateToProtocolDelete sampleId: ', protocolId);
        this.apiService.doGet( 'caNanoLab/rest/protocol/deleteProtocolById', 'protocolId=' + protocolId ).subscribe(
            data => {
                console.log( 'MHL Answer from \'caNanoLab/rest/sample/deleteProtocolById?protocolId=\'' + protocolId + ':', data );
            },

            ( err ) => {
                console.error( 'MHL ERROR from \'caNanoLab/rest/sample/deleteProtocolById?protocolId=\'' + protocolId + ':', err );
            } );
    }
}
