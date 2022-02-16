// ----------------------------------------------------------------------------------------------------
// -----------------           Read and parse the CConfiguration file                ------------------
// ----------------------------------------------------------------------------------------------------
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UtilService } from './util.service';
import { Observable } from 'rxjs';
import { Properties } from '../../../../assets/properties';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService{

    constructor( private httpClient: HttpClient, private utilService: UtilService ){
        /**
         * Properties.CONFIG_COMPLETE will be used in other components to determine if the setting of configurable items is complete.
         */
        Properties.CONFIG_COMPLETE = false;
        this.initConfiguration();
    }

    initConfiguration(){

        this.readTextFile( 'assets/' + Properties.CONFIG_FILE ).subscribe(
            data => {

                this.parseConfig( data );
                Properties.CONFIG_COMPLETE = true;
            },
            err => {
                if( err.status === 404 ){
                    console.error( 'Could not find CONFIG_FILE file "' + 'assets/' + Properties.CONFIG_FILE );
                }
                console.error( 'Could not access CONFIG_FILE file! ', err.status );
                Properties.CONFIG_COMPLETE = true;
            }
        );
    }

    /**
     * Do a cold subscribe to read a plain text file (Ex. assets/configuration)
     * @param file
     */
    readTextFile( file ): Observable<any>{
        return this.httpClient.get( file,
            {
                responseType: 'text'
            } );
    }

    parseConfig( data ){

        // Some editors and operating systems don't save "end of line"s the same way
        let config = data.replace( /\r\n/g, '\r' ).replace( /\n/g, '\r' ).split( /\r/ );

        for( let line of config ){

            // Test for comments here
            if( !line.match( '^\s*#' ) ){

                // If there is no "=" in the line, there is nothing we can do with it.
                if( line.includes( '=' ) ){

                    // Parse the "key" and "value" from the line
                    let value = line.replace( /.*?=\s*/, '' );
                    let key = line.replace( /\s*?=.*$/, '' );
                    key = key.replace( /^\s*/, '' );

                    /*  Some examples:

                                        if( key === 'OHIF_SERVER_URL' ){
                                            if( !this.utilService.isNullOrUndefinedOrEmpty( value ) ){
                                                Properties.OHIF_SERVER_URL = value;
                                            }
                                        }

                                        if( key === 'MAX_VIDEO_FPS' ){
                                            if( !this.utilService.isNullOrUndefinedOrEmpty( value ) ){
                                                Properties.MAX_VIDEO_FPS = value;
                                            }
                                        }

                                        if( key === 'OHIF_MODALITIES' ){
                                            // toUpperCase for case insensitive.
                                            Properties.OHIF_MODALITIES = value.toUpperCase().split( /\s*,\s*!/ );
                                        }
                    */

                    // Look for TEST_MODE (true or false) in the config file.
                    if( key === 'TEST_MODE' ){
                        Properties.TEST_MODE = this.utilService.isTrue( value );
                    }


                    if( key === 'HTTP_TIMEOUT' ){
                        if( !this.utilService.isNullOrUndefinedOrEmpty( value ) ){
                            Properties.HTTP_TIMEOUT = value;
                        }
                    }


                    if( key === 'MAX_WIDTH' ){
                        if( !this.utilService.isNullOrUndefinedOrEmpty( value ) ){
                            Properties.MAX_WIDTH = value;
                        }
                    }

                }
            }
        }
    }
}
