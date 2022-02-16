import { Pipe, PipeTransform } from '@angular/core';

@Pipe( {
    name: 'protocolSearchResultsDisplayTitle'
} )
export class ProtocolSearchResultsDisplayTitlePipe implements PipeTransform{


    transform( value: string ): any{
        if( value === undefined){
            return '';
        }
        let temp = value.replace( /^Title:/, '' ).replace( /Description:.*$/, '' ).replace( /<br>/gi, '' );

        return temp;

    }
}
