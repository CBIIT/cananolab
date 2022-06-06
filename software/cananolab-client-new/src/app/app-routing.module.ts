import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
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
import { SearchPublicationComponent } from './cananolab-client/main-display/publications/search-publication/search-publication.component';
import { SearchSamplesByPublicationComponent } from './cananolab-client/main-display/publications/search-samples-by-publication/search-samples-by-publication.component';
import { ProtocolSearchComponent } from './cananolab-client/main-display/protocols/protocol-search/protocol-search.component';
import { ProtocolSearchResultsComponent } from './cananolab-client/main-display/protocols/protocol-search/protocol-search-results/protocol-search-results.component';
import { ProtocolCreateComponent } from './cananolab-client/main-display/protocols/protocol-create/protocol-create.component';
import { SampleSearchComponent } from './cananolab-client/main-display/samples/general-info/sample-search/sample-search.component';
import { SampleCreateComponent } from './cananolab-client/main-display/samples/general-info/sample-create/sample-create.component';
import { SampleCopyComponent } from './cananolab-client/main-display/samples/general-info/sample-copy/sample-copy.component';
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
import { ProtocolViewComponent } from './cananolab-client/main-display/protocols/protocol-view/protocol-view.component';
import { EditpublicationComponent } from './cananolab-client/main-display/samples/publications/editpublication/editpublication.component';
import { SearchpublicationresultsComponent } from './cananolab-client/main-display/publications/search-publication-results/searchpublicationresults/searchpublicationresults.component';
import { LoginComponent } from './cananolab-client/main-display/home/right-side-bar/home-user-actions/login/login.component';
import { SearchResultsComponent } from './cananolab-client/main-display/search-results/search-results/search-results.component';
import { AdvancedSearchComponent } from './cananolab-client/main-display/samples/general-info/sample-search/advanced-search/advanced-search.component';
import { AdvancedSearchResultsComponent } from './cananolab-client/main-display/samples/general-info/sample-search/advanced-search-results/advanced-search-results.component';
const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },
    { path: 'home/login', component: LoginComponent },
    { path: 'home/search-results', component: SearchResultsComponent },
    { path: 'home/publications', component: PublicationsComponent },
    { path: 'home/publications/publication-search', component: SearchPublicationComponent },
    { path: 'home/publications/sample-search-by-publication', component: SearchSamplesByPublicationComponent },
    { path: 'home/publications/publication-search-results', component: SearchpublicationresultsComponent },

    { path: 'home/protocols/protocol-create', component: ProtocolCreateComponent },
    { path: 'home/protocols/edit-protocol/:protocolId', component: ProtocolCreateComponent },
    { path: 'home/protocols/edit-protocol/:protocolId/:message', component: ProtocolCreateComponent },
    { path: 'home/protocols/protocol-search', component: ProtocolSearchComponent },
    { path: 'home/protocols/protocol-search-results', component: ProtocolSearchResultsComponent },
    { path: 'home/protocols/protocolViewComponent', component: ProtocolViewComponent },

    { path: 'home/protocols', component: ProtocolsComponent },
    { path: 'home/samples', component: SamplesComponent },

    { path: 'home/samples/sample/:sampleId', component: SampleEditComponent },
    { path: 'home/samples/view-sample/:sampleId', component: SampleViewComponent }, // @TODO Add Composition view here
    { path: 'home/samples/sample-search', component: SampleSearchComponent },
    { path: 'home/samples/sample-search-results', component: SampleSearchResultsComponent },
    { path: 'home/samples/sample-advanced-search-results', component: AdvancedSearchResultsComponent },

    { path: 'home/samples/sample-advanced-search-results/:searchResults', component: AdvancedSearchResultsComponent },

    { path: 'home/samples/sample-advanced-search', component: AdvancedSearchComponent },
    { path: 'home/samples/sample-create', component: SampleCreateComponent },
    { path: 'home/samples/sample-copy', component: SampleCopyComponent },
    { path: 'home/samples/characterization/:sampleId', component: CharacterizationComponent },
    { path: 'home/samples/view-characterization/:sampleId', component: CharacterizationComponent },
    { path: 'home/samples/characterization/edit-characterization/:sampleId/:type', component: EditcharacterizationComponent },
    { path: 'home/samples/characterization/edit-characterization/:sampleId/:charId/:charClassName/:type', component: EditcharacterizationComponent },
    { path: 'home/samples/composition/:sampleId', component: CompositionComponent },
    { path: 'home/samples/view-composition/:sampleId', component: CompositionComponent },
    { path: 'home/samples/composition/functionalizing-entity/:sampleId/:dataId', component: FunctionalizingentityComponent },
    { path: 'home/samples/composition/functionalizing-entity/:sampleId', component: FunctionalizingentityComponent },
    { path: 'home/samples/composition/nanomaterial-entity/:sampleId/:dataId', component: NanomaterialentityComponent },
    { path: 'home/samples/composition/nanomaterial-entity/:sampleId', component: NanomaterialentityComponent },
    { path: 'home/samples/composition/composition-file/:sampleId/:dataId', component: CompositionfileComponent },
    { path: 'home/samples/composition/composition-file/:sampleId', component: CompositionfileComponent },
    { path: 'home/samples/composition/chemical-association/:sampleId/:dataId', component: ChemicalassociationComponent },
    { path: 'home/samples/composition/chemical-association/:sampleId', component: ChemicalassociationComponent },
    { path: 'home/samples/publications/:sampleId', component: SamplePublicationsComponent },
    { path: 'home/samples/view-publications/:sampleId', component: SamplePublicationsComponent },
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
