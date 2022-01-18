import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class SearchResultsPagerService {
    currentPageChangeEmitter  = new EventEmitter();
    pageCountEmitter  = new EventEmitter();
    pageLengthEmitter  = new EventEmitter();
    nextPageEmitter  = new EventEmitter();

    currentPage = 0;
    pageCount = 0;

    constructor() { }

    setPageCount( pc ){
        console.log('MHL 200 SearchResultsPagerService.setPageCount pc: ', pc);
        this.pageCount = pc;
        this.pageCountEmitter.emit(this.pageCount);
    }

    getPageCount(){
        return this.pageCount;
    }

    goToNextPage(){
        this.nextPageEmitter.emit();
    }

    setCurrentPage( p ){
        this.currentPage = p;
        this.currentPageChangeEmitter.emit( this.currentPage );
    }

    getCurrentPage(){
        return this.currentPage;
    }

}
