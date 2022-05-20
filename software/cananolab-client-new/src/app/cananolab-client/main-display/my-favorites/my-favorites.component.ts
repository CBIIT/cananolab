import { Component, OnInit } from '@angular/core';
import { Consts, ProtocolScreen } from '../../../constants';
import { ApiService } from '../../common/services/api.service';
import { Router } from '@angular/router';
import { ProtocolsService } from '../protocols/protocols.service';

@Component( {
    selector: 'canano-my-favorites',
    templateUrl: './my-favorites.component.html',
    styleUrls: ['./my-favorites.component.scss']
} )
export class MyFavoritesComponent implements OnInit{
    helpUrl = Consts.HELP_URL_FAVORITE;
    toolHeadingNameSearchSample = 'My Favorites';
    showSampleFavs = true;
    showProtocolsFavs = true;
    showPublicationsFavs = true;
    favSamples = [];
    favProtocols = [];
    favPublications = [];
    protocolScreen = ProtocolScreen;
    protocolScreenToShow = ProtocolScreen.PROTOCOL_SEARCH_INPUT_SCREEN;

    constructor( private apiService: ApiService, private router: Router,
                 private protocolsService: ProtocolsService ){

    }

    ngOnInit(): void{
        this.apiService.doGet( Consts.QUERY_GET_FAVORITE, '' ).subscribe(
            data => {
                this.favSamples = <any>data['samples'];
                this.favProtocols = <any>data['protocols'];
                this.favPublications = <any>data['publications'];
                console.log( 'MHL QUERY_GET_FAVORITE: ', data );
            },

            ( err ) => {
                console.error( 'MHL ERROR> QUERY_GET_FAVORITE: ', err );
            } );

    }

    deleteProtocolFromFavorites( favToDelete ){
        this.deleteSampleFromFavorites( favToDelete );
    }

    deletePublicationFromFavorites( favToDelete ){
        this.deleteSampleFromFavorites( favToDelete );
    }

    deleteSampleFromFavorites( favToDelete ){
        console.log( 'MHL favToDelete: ', favToDelete );
        this.apiService.doPost( Consts.QUERY_DELETE_FAVORITE, favToDelete ).subscribe(
            data => {
                this.favSamples = <any>data['samples'];
                this.favProtocols = <any>data['protocols'];
                this.favPublications = <any>data['publications'];

            },
            err => {
                console.log( 'MHL ERROR deleteSampleFromFavorites: ', err );

            }
        );
    }

    navigateToSampleEdit( sampleId ){
        this.router.navigate( ['home/samples/samplesEdit', '?sampleId=' + sampleId] );  // @FIXME  Don't hard code these
    }

    navigateToSampleView( sampleId ){
        this.router.navigate( ['home/samples/samplesView', '?sampleId=' + sampleId] );  // @FIXME  Don't hard code these
    }

    navigateToProtocolEdit( protocolId ){
        console.log( 'MHL navigateToProtocolEdit protocolId: ', protocolId );
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, protocolId );
        this.router.navigate( ['home/protocols/protocolEditComponent'] );  // @FIXME  Don't hard code these
    }


    navigateToProtocolView( protocolId ){
        console.log( 'MHL navigateToProtocolView protocolId: ', protocolId );
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, protocolId );
        this.router.navigate( ['home/protocols/protocolViewComponent'] );  // @FIXME  Don't hard code these
    }

}
