import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class SetObjectValueModule {

}


declare global {
    interface Object {
        setValue(path,value);
        addDropdownItem(path,value);
    }
}


Object.defineProperty(Object.prototype, "setValue", {
    value: function(path,value) {
        var last = (path = path.split('.')).splice(-1);
        [this].concat(path).reduce(function(a, b) {
            return a[b]
        })[last] = value;
    },
    enumerable : false
});

Object.defineProperty(Object.prototype, "addDropdownItem", {
    value: function(path,value) {
        var last = (path = path.split('.')).splice(-1);
        [this].concat(path).reduce(function(a, b) {
            return a[b]
        })
        [last].push(value)
    },
    enumerable : false
});
