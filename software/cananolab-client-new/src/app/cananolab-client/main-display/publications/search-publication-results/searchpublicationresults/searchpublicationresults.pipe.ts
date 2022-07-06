import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchpublicationresults'
})
export class SearchpublicationresultsPipe implements PipeTransform {

    transform(value: String, ...args: unknown[]): unknown {
        let val = value;
        console.log(val)
        if (val.indexOf('[DISCLAIMER]')>-1) {
            val = val.replace('[DISCLAIMER].','');
            val = val.replace('[DISCLAIMER]','');
            return [val,true]
        }
        else {
            return [val]
        }
    }

}
