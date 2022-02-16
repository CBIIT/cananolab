import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'protocolSearchResultsDisplayDescription'
})
export class ProtocolSearchResultsDisplayDescriptionPipe implements PipeTransform {

    transform( value: string ): any{
        if( value === undefined){
            return '';
        }
        let temp = value.replace(/^.*Description:/, '').replace(/<a.*$/, '').replace(/<br>/gi, '');

        return temp;

    }

}
