import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CananolabClientComponent } from './cananolab-client/cananolab-client.component';
import { PublicationsComponent } from './cananolab-client/main-display/publications/publications.component';
import { HomeComponent } from './cananolab-client/main-display/home/home.component';
import { WorkflowComponent } from './cananolab-client/main-display/workflow/workflow.component';
import { ProtocolsComponent } from './cananolab-client/main-display/protocols/protocols.component';
import { SamplesComponent } from './cananolab-client/main-display/samples/samples.component';
import { MyWorkspaceComponent } from './cananolab-client/main-display/my-workspace/my-workspace.component';
import { LogoutComponent } from './cananolab-client/main-display/logout/logout.component';
import { TestComponent } from './cananolab-client/main-display/test/test.component';
import { CurationComponent } from './cananolab-client/main-display/curation/curation.component';
import { GroupsComponent } from './cananolab-client/main-display/groups/groups.component';
import { MyFavoritesComponent } from './cananolab-client/main-display/my-favorites/my-favorites.component';
import { SubmitPublicationComponent } from './cananolab-client/main-display/publications/submit-publication/submit-publication.component';
import { SearchPublicationComponent } from './cananolab-client/main-display/publications/search-publication/search-publication.component';
import { SearchSamplesByPublicationComponent } from './cananolab-client/main-display/publications/search-samples-by-publication/search-samples-by-publication.component';
import { ProtocolSearchComponent } from './cananolab-client/main-display/protocols/protocol-search/protocol-search.component';
import { ProtocolCreateComponent } from './cananolab-client/main-display/protocols/protocol-create/protocol-create.component';
import { ProtocolSearchResultsComponent } from './cananolab-client/main-display/protocols/protocol-search/protocol-search-results/protocol-search-results.component';
import { ProtocolCreateCharlieComponent } from './cananolab-client/main-display/protocols/protocol-create-charlie/protocol-create-charlie.component';
import { ProtocolEditBravoComponent } from './cananolab-client/main-display/protocols/protocol-edit-bravo/protocol-edit-bravo.component';
import { SampleSearchComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-search.component';
import { SampleCreateComponent } from './cananolab-client/main-display/samples/general-info/sample-create/sample-create.component';
import { SampleCopyComponent } from './cananolab-client/main-display/samples/general-info/sample-copy/sample-copy.component';
import { SampleAdvancedSearchComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-advanced-search/sample-advanced-search.component';
import { SampleSearchResultsComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-search-results/sample-search-results.component';
import { SampleEditComponent } from './cananolab-client/main-display/samples/general-info/sample-edit/sample-edit.component';
import { SampleViewComponent } from './cananolab-client/main-display/samples/sample-view/sample-view.component';
import { CharacterizationComponent } from './cananolab-client/main-display/samples/characterization/characterization.component';
import { EditcharacterizationComponent } from './cananolab-client/main-display/samples/characterization/editcharacterization/editcharacterization.component';
import { CompositionComponent } from './cananolab-client/main-display/samples/composition/composition/composition.component';
import { FunctionalizingentityComponent } from './cananolab-client/main-display/samples/composition/functionalizingentity/functionalizingentity.component';
import { NanomaterialentityComponent } from './cananolab-client/main-display/samples/composition/nanomaterialentity/nanomaterialentity.component';
import { CompositionfileComponent } from './cananolab-client/main-display/samples/composition/compositionfile/compositionfile.component';
import { ChemicalassociationComponent } from './cananolab-client/main-display/samples/composition/chemicalassociation/chemicalassociation.component';
import { SamplePublicationsComponent } from './cananolab-client/main-display/samples/publications/sample-publications/sample-publications.component';
import { SampleAdvancedSearchResultsComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-advanced-search-results/sample-advanced-search-results.component';
import { ProtocolViewComponent } from './cananolab-client/main-display/protocols/protocol-view/protocol-view.component';
import { EditpublicationComponent } from './cananolab-client/main-display/samples/publications/editpublication/editpublication.component';
import { SearchpublicationresultsComponent } from './cananolab-client/main-display/publications/search-publication-results/searchpublicationresults/searchpublicationresults.component';
import { LoginComponent } from './cananolab-client/main-display/home/right-side-bar/home-user-actions/login/login.component';
import { SearchResultsComponent } from './cananolab-client/main-display/search-results/search-results/search-results.component';
const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },
    { path: 'home/login', component: LoginComponent },
    { path: 'home/searchresults', component: SearchResultsComponent },
    { path: 'home/publications', component: PublicationsComponent },
    { path: 'home/publications/submitPublication', component: SubmitPublicationComponent },
    { path: 'home/publications/searchPublication', component: SearchPublicationComponent },
    { path: 'home/publications/publicationSearchResults', component: SearchpublicationresultsComponent },

    { path: 'home/protocols/protocolCreateComponent', component: ProtocolCreateCharlieComponent },
    { path: 'home/protocols/protocolSearchComponent', component: ProtocolSearchComponent },
    { path: 'home/protocols/protocolEditComponent', component: ProtocolEditBravoComponent },
    { path: 'home/protocols/protocolViewComponent', component: ProtocolViewComponent },

    { path: 'home/protocols/protocolSearchComponent', component: ProtocolSearchComponent },
    { path: 'home/protocols/protocolSearchResults', component: ProtocolSearchResultsComponent },

    { path: 'home/protocols', component: ProtocolsComponent },
    { path: 'home/samples', component: SamplesComponent },

    { path: 'home/samples/samplesEdit/:sampleId', component: SampleEditComponent },
    { path: 'home/samples/samplesView/:sampleId', component: SampleViewComponent }, // @TODO Add Composition view here
    { path: 'home/samples/samplesSearch', component: SampleSearchComponent },
    { path: 'home/samples/samplesSearchResults', component: SampleSearchResultsComponent },
    { path: 'home/samples/sampleAdvancedSearchResults', component: SampleAdvancedSearchResultsComponent },

    { path: 'home/samples/SampleAdvancedSearchResults/:searchResults', component: SampleAdvancedSearchResultsComponent },

    { path: 'home/samples/samplesAdvancedSearch', component: SampleAdvancedSearchComponent },
    { path: 'home/samples/sampleCreate', component: SampleCreateComponent },
    { path: 'home/samples/samplesCopy', component: SampleCopyComponent },
    { path: 'home/samples/characterization/:sampleId', component: CharacterizationComponent },
    { path: 'home/samples/characterization/editcharacterization/:sampleId/:type', component: EditcharacterizationComponent },
    { path: 'home/samples/characterization/editcharacterization/:sampleId/:charId/:charClassName/:type', component: EditcharacterizationComponent },
    { path: 'home/samples/composition/:sampleId', component: CompositionComponent },
    { path: 'home/samples/composition/functionalizingentity/:sampleId/:dataId', component: FunctionalizingentityComponent },
    { path: 'home/samples/composition/functionalizingentity/:sampleId', component: FunctionalizingentityComponent },
    { path: 'home/samples/composition/nanomaterialentity/:sampleId/:dataId/:sampleName', component: NanomaterialentityComponent },
    { path: 'home/samples/composition/compositionfile/:sampleId/:dataId', component: CompositionfileComponent },
    { path: 'home/samples/composition/compositionfile/:sampleId', component: CompositionfileComponent },
    { path: 'home/samples/composition/chemicalassociation/:sampleId/:dataId', component: ChemicalassociationComponent },
    { path: 'home/samples/composition/chemicalassociation/:sampleId', component: ChemicalassociationComponent },
    { path: 'home/samples/publications/:sampleId', component: SamplePublicationsComponent },
    { path: 'home/samples/publications/publication/:sampleId/:type', component: EditpublicationComponent },
    { path: 'home/samples/publications/publication/:sampleId/:publicationId/:type', component: EditpublicationComponent },
    { path: 'home/samples/publications/publication/:publicationId', component: EditpublicationComponent },

    { path: 'home/curation', component: CurationComponent },
    { path: 'home/groups', component: GroupsComponent },
    { path: 'home/workflow', component: WorkflowComponent },
    { path: 'home/myworkspace', component: MyWorkspaceComponent },
    { path: 'home/myfavorites', component: MyFavoritesComponent },

    // { path: 'home/logout', component: HomeComponent },
     { path: 'home/logout', component: LogoutComponent },

    { path: 'home/test', component: TestComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
