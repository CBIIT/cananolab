import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CananolabClientComponent } from './cananolab-client/cananolab-client.component';
import { HeaderComponent } from './cananolab-client/header/header.component';
import { TopKeywordSearchComponent } from './cananolab-client/header/top-keyword-search/top-keyword-search.component';
import { TopMainMenuComponent } from './cananolab-client/top-main-menu/top-main-menu.component';
import { MainDisplayComponent } from './cananolab-client/main-display/main-display.component';
import { HomeComponent } from './cananolab-client/main-display/home/home.component';
import { WorkflowComponent } from './cananolab-client/main-display/workflow/workflow.component';
import { ProtocolsComponent } from './cananolab-client/main-display/protocols/protocols.component';
import { SamplesComponent } from './cananolab-client/main-display/samples/samples.component';
import { PublicationsComponent } from './cananolab-client/main-display/publications/publications.component';
import { GroupsComponent } from './cananolab-client/main-display/groups/groups.component';
import { CurationComponent } from './cananolab-client/main-display/curation/curation.component';
import { MyWorkspaceComponent } from './cananolab-client/main-display/my-workspace/my-workspace.component';
import { MyFavoritesComponent } from './cananolab-client/main-display/my-favorites/my-favorites.component';
import { LogoutComponent } from './cananolab-client/main-display/logout/logout.component';
import { LeftStaticMenuComponent } from './cananolab-client/left-static-menu/left-static-menu.component';
import { MainDisplayHeadingComponent } from './cananolab-client/main-display/main-display-heading/main-display-heading.component';
import { RightSideBarComponent } from './cananolab-client/main-display/home/right-side-bar/right-side-bar.component';
import { HomeUserActionsComponent } from './cananolab-client/main-display/home/right-side-bar/home-user-actions/home-user-actions.component';
import { HomeFeaturesComponent } from './cananolab-client/main-display/home/right-side-bar/home-features/home-features.component';
import { HomeHowToComponent } from './cananolab-client/main-display/home/right-side-bar/home-how-to/home-how-to.component';
import { HomeWhatsNewComponent } from './cananolab-client/main-display/home/right-side-bar/home-whats-new/home-whats-new.component';
import { HomeKeepingUpWithCananolabComponent } from './cananolab-client/main-display/home/right-side-bar/home-keeping-up-with-cananolab/home-keeping-up-with-cananolab.component';
import { LoginComponent } from './cananolab-client/main-display/home/right-side-bar/home-user-actions/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LeftNavigationMenuComponent } from './cananolab-client/left-navigation-menu/left-navigation-menu.component';
import { TestComponent } from './cananolab-client/main-display/test/test.component';
import { BrowseCananolabComponent } from './cananolab-client/main-display/home/browse-cananolab/browse-cananolab.component';
import { StatusDisplayComponent } from './cananolab-client/status-display/status-display.component';
import { SearchPublicationComponent } from './cananolab-client/main-display/publications/search-publication/search-publication.component';
import { SearchSamplesByPublicationComponent } from './cananolab-client/main-display/publications/search-samples-by-publication/search-samples-by-publication.component';
import { ProtocolSearchComponent } from './cananolab-client/main-display/protocols/protocol-search/protocol-search.component';
import { ProtocolSearchResultsComponent } from './cananolab-client/main-display/protocols/protocol-search/protocol-search-results/protocol-search-results.component';
import { SearchResultsPagerComponent } from './cananolab-client/common/components/search-results-pager/search-results-pager.component';
import { ManageAccessibilityComponent } from './cananolab-client/common/components/manage-accessibility/manage-accessibility.component';
import { ManageAccessibilityGroupComponent } from './cananolab-client/common/components/manage-accessibility/manage-accessibility-group/manage-accessibility-group.component';
import { ProtocolSearchResultsDisplayTitlePipe } from './cananolab-client/main-display/protocols/protocol-search/protocol-search-results/protocol-search-results-display-title.pipe';
import { ProtocolSearchResultsDisplayDescriptionPipe } from './cananolab-client/main-display/protocols/protocol-search/protocol-search-results/protocol-search-results-display-description.pipe';
import { ProtocolSearchResultsDisplayHrefPipe } from './cananolab-client/main-display/protocols/protocol-search/protocol-search-results/protocol-search-results-display-href.pipe';
import { ProtocolViewComponent } from './cananolab-client/main-display/protocols/protocol-view/protocol-view.component';
import { ProtocolCreateComponent } from './cananolab-client/main-display/protocols/protocol-create/protocol-create.component';
import { SampleSearchComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-search.component';
import { SampleSearchResultsComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-search-results/sample-search-results.component';
import { SampleCreateComponent } from './cananolab-client/main-display/samples/general-info/sample-create/sample-create.component';
import { SampleCopyComponent } from './cananolab-client/main-display/samples/general-info/sample-copy/sample-copy.component';
import { SampleAvailabilityDisplayComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-search-results/sample-availability-display/sample-availability-display.component';
import { SampleEditComponent } from './cananolab-client/main-display/samples/general-info/sample-edit/sample-edit.component';
import { SampleViewComponent } from './cananolab-client/main-display/samples/sample-view/sample-view.component';
import { CharacterizationComponent } from './cananolab-client/main-display/samples/characterization/characterization.component';
import { CompositionComponent } from './cananolab-client/main-display/samples/composition/composition/composition.component';
import { SamplePublicationsComponent } from './cananolab-client/main-display/samples/publications/sample-publications/sample-publications.component';
import { FunctionalizingentityComponent } from './cananolab-client/main-display/samples/composition/functionalizingentity/functionalizingentity.component';
import { NanomaterialentityComponent } from './cananolab-client/main-display/samples/composition/nanomaterialentity/nanomaterialentity.component';
import { CompositionfileComponent } from './cananolab-client/main-display/samples/composition/compositionfile/compositionfile.component';
import { ChemicalassociationComponent } from './cananolab-client/main-display/samples/composition/chemicalassociation/chemicalassociation.component';
import { ManageFileEditComponent } from './cananolab-client/main-display/samples/composition/nanomaterialentity/manage-file-edit/manage-file-edit.component';
import { ComposingElementFormComponent } from './cananolab-client/main-display/samples/composition/nanomaterialentity/manage-composing-element/composing-element-form/composing-element-form.component';
import { ManageComposingElementComponent } from './cananolab-client/main-display/samples/composition/nanomaterialentity/manage-composing-element/manage-composing-element.component';
import { OtherDropdownComponent } from './cananolab-client/common/components/other-dropdown/other-dropdown.component';
import { SetObjectValueModule } from './cananolab-client/common/modules/set-object-value/set-object-value.module';
import { EditcharacterizationComponent } from './cananolab-client/main-display/samples/characterization/editcharacterization/editcharacterization.component';
import { FileComponent } from './cananolab-client/common/components/file/file.component';
import { EditpublicationComponent } from './cananolab-client/main-display/samples/publications/editpublication/editpublication.component';
import { SearchpublicationresultsComponent } from './cananolab-client/main-display/publications/search-publication-results/searchpublicationresults/searchpublicationresults.component';
import { SearchResultsComponent } from './cananolab-client/main-display/search-results/search-results/search-results.component';
import { AdvancedSearchComponent } from './cananolab-client/main-display/samples/general-info/sample-search/advanced-search/advanced-search.component';
import { AdvancedSearchResultsComponent } from './cananolab-client/main-display/samples/general-info/sample-search/advanced-search-results/advanced-search-results.component';

@NgModule({
  declarations: [
    AppComponent,
    CananolabClientComponent,
    HeaderComponent,
    TopKeywordSearchComponent,
    TopMainMenuComponent,
    MainDisplayComponent,
    HomeComponent,
    WorkflowComponent,
    ProtocolsComponent,
    SamplesComponent,
    PublicationsComponent,
    GroupsComponent,
    CurationComponent,
    MyWorkspaceComponent,
    MyFavoritesComponent,
    LogoutComponent,
    LeftStaticMenuComponent,
    MainDisplayHeadingComponent,
    RightSideBarComponent,
    HomeUserActionsComponent,
    HomeFeaturesComponent,
    HomeHowToComponent,
    HomeWhatsNewComponent,
    HomeKeepingUpWithCananolabComponent,
    LoginComponent,
    LeftNavigationMenuComponent,
    TestComponent,
    BrowseCananolabComponent,
    StatusDisplayComponent,
    SearchPublicationComponent,
    SearchSamplesByPublicationComponent,
    ProtocolSearchComponent,
    ProtocolSearchResultsComponent,
    SearchResultsPagerComponent,
    ManageAccessibilityComponent,
    ManageAccessibilityGroupComponent,
    ProtocolSearchResultsDisplayTitlePipe,
    ProtocolSearchResultsDisplayDescriptionPipe,
    ProtocolSearchResultsDisplayHrefPipe,
    ProtocolViewComponent,
    ProtocolCreateComponent,
    SampleSearchComponent,
    SampleSearchResultsComponent,
    SampleCreateComponent,
    SampleCopyComponent,
    SampleAvailabilityDisplayComponent,
    SampleEditComponent,
    SampleViewComponent,
    CharacterizationComponent,
    CompositionComponent,
    SamplePublicationsComponent,
    FunctionalizingentityComponent,
    NanomaterialentityComponent,
    CompositionfileComponent,
    ChemicalassociationComponent,
    SamplePublicationsComponent,
    ManageFileEditComponent,
    ComposingElementFormComponent,
    ManageComposingElementComponent,
    OtherDropdownComponent,
    EditcharacterizationComponent,
    FileComponent,
    EditpublicationComponent,
    SearchpublicationresultsComponent,
    SearchResultsComponent,
    AdvancedSearchComponent,
    AdvancedSearchResultsComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule,
        SetObjectValueModule

    ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
