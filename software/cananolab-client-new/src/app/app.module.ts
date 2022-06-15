import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { CananolabClientComponent } from './cananolab-client/cananolab-client.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './cananolab-client/header/header.component';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { IdleComponent } from './cananolab-client/common/components/idle/idle.component';
import { LeftNavigationMenuComponent } from './cananolab-client/left-navigation-menu/left-navigation-menu.component';
import { LeftStaticMenuComponent } from './cananolab-client/left-static-menu/left-static-menu.component';
import { NgModule } from '@angular/core';
import { SetObjectValueModule } from './cananolab-client/common/modules/set-object-value/set-object-value.module';
import { StatusDisplayComponent } from './cananolab-client/status-display/status-display.component';
import { TopKeywordSearchComponent } from './cananolab-client/header/top-keyword-search/top-keyword-search.component';
import { TopMainMenuComponent } from './cananolab-client/top-main-menu/top-main-menu.component';


@NgModule({
  declarations: [
    AppComponent,
    CananolabClientComponent,
    HeaderComponent,
    IdleComponent,
    LeftStaticMenuComponent,
    LeftNavigationMenuComponent,
    StatusDisplayComponent,
    TopKeywordSearchComponent,
    TopMainMenuComponent,
  ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule,
        SetObjectValueModule,
    ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
