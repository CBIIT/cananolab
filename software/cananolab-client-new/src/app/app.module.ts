import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowseCananolabComponent } from './cananolab-client/main-display/home/browse-cananolab/browse-cananolab.component';
import { BrowserModule } from '@angular/platform-browser';
import { CananolabClientComponent } from './cananolab-client/cananolab-client.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './cananolab-client/header/header.component';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HomeFeaturesComponent } from './cananolab-client/main-display/home/right-side-bar/home-features/home-features.component';
import { HomeHowToComponent } from './cananolab-client/main-display/home/right-side-bar/home-how-to/home-how-to.component';
import { HomeKeepingUpWithCananolabComponent } from './cananolab-client/main-display/home/right-side-bar/home-keeping-up-with-cananolab/home-keeping-up-with-cananolab.component';
import { HomeUserActionsComponent } from './cananolab-client/main-display/home/right-side-bar/home-user-actions/home-user-actions.component';
import { HomeWhatsNewComponent } from './cananolab-client/main-display/home/right-side-bar/home-whats-new/home-whats-new.component';
import { HttpClientModule } from '@angular/common/http';
import { IdleComponent } from './cananolab-client/common/components/idle/idle.component';
import { HomeComponent } from './cananolab-client/main-display/home/home.component';
import { LeftNavigationMenuComponent } from './cananolab-client/left-navigation-menu/left-navigation-menu.component';
import { LeftStaticMenuComponent } from './cananolab-client/left-static-menu/left-static-menu.component';
import { NgModule } from '@angular/core';
import { RightSideBarComponent } from './cananolab-client/main-display/home/right-side-bar/right-side-bar.component';
import { SetObjectValueModule } from './cananolab-client/common/modules/set-object-value/set-object-value.module';
import { SharedModule } from './cananolab-client/common/modules/set-object-value/shared.module';
import { StatusDisplayComponent } from './cananolab-client/status-display/status-display.component';
import { TopKeywordSearchComponent } from './cananolab-client/header/top-keyword-search/top-keyword-search.component';
import { TopMainMenuComponent } from './cananolab-client/top-main-menu/top-main-menu.component';
import { LoginComponent } from './cananolab-client/main-display/home/right-side-bar/home-user-actions/login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    BrowseCananolabComponent,
    CananolabClientComponent,
    HeaderComponent,
    IdleComponent,
    LeftStaticMenuComponent,
    LoginComponent,
    HomeComponent,
    HomeFeaturesComponent,
    HomeHowToComponent,
    HomeKeepingUpWithCananolabComponent,
    HomeUserActionsComponent,
    HomeWhatsNewComponent,
    LeftNavigationMenuComponent,
    LoginComponent,
    RightSideBarComponent,
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
        SharedModule
    ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
