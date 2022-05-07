import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'canano-other-dropdown',
  templateUrl: './other-dropdown.component.html',
  styleUrls: ['../../../../btn-bravo-canano.scss','./other-dropdown.component.scss']
})
export class OtherDropdownComponent implements OnInit {
  otherValue;
  @Input() value;
  @Input() field;
  @Input() array;
  @Input() label;
  @Output() saveOther = new EventEmitter<Object>();

  constructor() { }

  ngOnInit(): void {
      console.log(this.value)
  }
  save() {
    this.saveOther.emit({'change':true,'field':this.field,'array':this.array,'value':this.otherValue, 'oldValue':this.value});
  };

  cancel() {
      console.log(this.field, this.value)
    this.saveOther.emit({'change':false,'field':this.field,'array':this.array,'value':this.value, 'oldValue':this.value});
  }
}
