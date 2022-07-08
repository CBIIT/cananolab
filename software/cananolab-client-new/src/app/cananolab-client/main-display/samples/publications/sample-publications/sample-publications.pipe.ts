import { Pipe, PipeTransform } from '@angular/core';
import { Consts } from 'src/app/constants';
@Pipe({
  name: 'samplePublications'
})
export class SamplePublicationsPipe implements PipeTransform {

  transform(value: String, ...args: unknown[]): unknown {
      let val = value;
      val = val.replace('rest/publication/download',Consts.QUERY_PUBLICATION_DOWNLOAD)
    return val;
  }

}
