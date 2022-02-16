import { Injectable } from '@angular/core';
import { TopMenuData } from '../../top-main-menu/top-main-menu.service';

@Injectable( {
    providedIn: 'root'
} )
export class UtilService{

    // Defaults if no height and width are provided.
    newWindowHeight = 800;
    newWindowWidth = 800;

    constructor(){
    }

    openWindow( pageURL, name, width = this.newWindowWidth, height = this.newWindowHeight ){
        window.open( pageURL, name, 'alwaysRaised,dependent,toolbar,status,scrollbars,resizable,width=' + width + ',height=' + height );
    }


    isNullOrUndefined( v ): boolean{
        let res = false;
        if( v == null ){
            res = true;
        }
        if( v === null ){
            res = true;
        }
        if( typeof v === 'undefined' ){
            res = true;
        }
        return res;
    }


    isNullOrUndefinedOrEmpty( v ): boolean{
        if( this.isNullOrUndefined( v ) ){
            return true;
        }

        return this.isEmpty( v ); // CHECKME
    }

    isEmpty( obj ){
        for( let key in obj ){
            if( obj.hasOwnProperty( key ) ){
                return false;
            }
        }
        return true;
    }

    sleep( ms ){
        return new Promise( resolve => setTimeout( resolve, ms ) );
    }

    isTrue( value ){

        if( typeof value === 'boolean' ){
            return value;
        }

        let val = value.toUpperCase();
        return (val === 'TRUE') || (val === 'YES') || (val === 'ON') || (val === '1') || (val === '');

    }

    // @TODO DOCUMENT THIS!!!!!!!!!!!!!!!!!!!!!!!!!!
    getRouteByName( name ){
        for( let item of TopMenuData ){
            if( item['name'] === name ){
                return item.route;
            }
        }
        return '';
    }
}
