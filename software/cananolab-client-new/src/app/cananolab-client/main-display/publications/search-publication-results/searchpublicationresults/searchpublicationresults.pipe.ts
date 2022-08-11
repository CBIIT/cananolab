import { Pipe, PipeTransform } from '@angular/core';
import { Consts } from 'src/app/constants';
@Pipe({
  name: 'searchpublicationresults'
})
export class SearchpublicationresultsPipe implements PipeTransform {

    transform(value: String, ...args: unknown[]): unknown {
        let val = value;
        if (val.indexOf('[DISCLAIMER]')>-1) {
            val = val.replace('[DISCLAIMER].','');
            val = val.replace('[DISCLAIMER]','');
            val = val.replace('rest/publication/download',Consts.QUERY_PUBLICATION_DOWNLOAD)
            return [val,true]
        }
        else {
            val = val.replace('rest/publication/download',Consts.QUERY_PUBLICATION_DOWNLOAD)
            return [val]
        }
    }

}
