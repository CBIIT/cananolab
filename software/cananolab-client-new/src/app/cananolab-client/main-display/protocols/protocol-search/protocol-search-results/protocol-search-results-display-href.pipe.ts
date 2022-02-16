import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'protocolSearchResultsDisplayHref'
})
export class ProtocolSearchResultsDisplayHrefPipe implements PipeTransform {


    transform( value: string ): any{
        if( value === undefined){
            return '';
        }
        let temp = value.replace(/^.*<a\s*href=/, '').replace(/\s*target=.*$/, '').replace(/<br>/gi, '');

        return temp;

    }

}


