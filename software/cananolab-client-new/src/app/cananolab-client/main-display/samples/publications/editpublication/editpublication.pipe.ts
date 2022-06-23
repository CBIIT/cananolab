import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filtersamples'
})
export class EditpublicationPipe implements PipeTransform {

  transform(value, filterValue): unknown {
      let array=[];
      if (filterValue) {
          value.forEach(item=> {
              if (item.toLowerCase().indexOf(filterValue.toLowerCase())>-1) {
                array.push(item)
              }
          })
          return array
      }
      else {
          return value
      }
  }

}
