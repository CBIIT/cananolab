import { Injectable } from '@angular/core';
import { TopMenuData } from '../../top-main-menu/top-main-menu.service';

@Injectable( {
    providedIn: 'root'
} )
export class UtilService{

    // Defaults if no height and width are provided.
    newWindowHeight = 800;
    newWindowWidth = 800;

    imgWindow = null;

    constructor(){
    }

    openWindow( pageURL, name, width = this.newWindowWidth, height = this.newWindowHeight ){
        window.open( pageURL, name, 'alwaysRaised,dependent,toolbar,status,scrollbars,resizable,width=' + width + ',height=' + height );
    }

    /**
     * @TODO Doesn't work right yet
     * @param event
     * @param imgSrc
     * @param imgId
     */
    popImage(event, imgSrc, imgId) {
        console.log('popImage event: ', event);
        console.log('popImage imgSrc: ', imgSrc);
        console.log('popImage imgId: ', imgId);

        let popImg = new Image();
        popImg.src = imgSrc;
        if (this.imgWindow != null && this.imgWindow.open) {
            this.imgWindow.close();
        }
        let topPos = 50;
        let leftPos = 50;
        let maxWidth = 800;
        let maxHeight = 800;
        if (popImg.width > 0) {
            let width = popImg.width + 20;
            let height = popImg.height + 20;
            if (width > height) {
                if (width > maxWidth) {
                    let ratio = maxWidth / width;
                    width = maxWidth;
                    height = ratio * height;
                }
            } else {
                if (height > maxHeight) {
                    let ratio = maxHeight / height;
                    height = maxHeight;
                    width = ratio * width;
                }
            }
            this.imgWindow = window.open('', 'charFileWindow', 'width=' + width + ',height=' + height + ',left=' + leftPos + ',top=' + topPos);
            this.imgWindow.document.writeln('<html><head><title>Characterization File</title></head>');
            this.imgWindow.document.writeln("<body onLoad=\"self.focus();\" bgcolor=\"#FFFFFF\">");
            this.imgWindow.document.writeln("<img width='" + (width - 20) + "' height='" + (height - 20) + "' styleId='" + imgId + "' src='" + imgSrc + "'/>");
            this.imgWindow.document.writeln("</body></html>");
        } else {
            this.imgWindow = window.open("", "charFileWindow", "left=" + leftPos + ",top=" + topPos);
            this.imgWindow.document.writeln("<html><head><title>Characterization File</title></head>");
            this.imgWindow.document.writeln("<body onLoad=\"resizePopup();\" bgcolor=\"#FFFFFF\">");
            this.imgWindow.document.writeln('<img id=\'popImage\' styleId=\'' + imgId + '\' src=\'' + imgSrc + '\'/>');
            this.imgWindow.document.writeln("</body></html>");
        }
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
