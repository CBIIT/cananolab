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
import { SampleSearchComponent } from './cananolab-client/main-display/samples/sample-search/sample-search.component';
import { SampleCreateComponent } from './cananolab-client/main-display/samples/sample-create/sample-create.component';
import { SampleCopyComponent } from './cananolab-client/main-display/samples/sample-copy/sample-copy.component';
import { SampleAdvancedSearchComponent } from './cananolab-client/main-display/samples/sample-search/sample-advanced-search/sample-advanced-search.component';
import { SampleSearchResultsComponent } from './cananolab-client/main-display/samples/sample-search/sample-search-results/sample-search-results.component';
import { SampleEditComponent } from './cananolab-client/main-display/samples/sample-edit/sample-edit.component';
import { SampleViewComponent } from './cananolab-client/main-display/samples/sample-view/sample-view.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },

    { path: 'home/publications', component: PublicationsComponent },
    { path: 'home/publications/submitPublication', component: SubmitPublicationComponent },
    { path: 'home/publications/searchPublication', component: SearchPublicationComponent },

    { path: 'home/protocols/protocolCreateComponent', component: ProtocolCreateCharlieComponent },
    { path: 'home/protocols/protocolSearchComponent', component: ProtocolSearchComponent },
    { path: 'home/protocols/protocolEditComponent', component: ProtocolEditBravoComponent },

    { path: 'home/protocols/protocolSearchComponent', component: ProtocolSearchComponent },
    { path: 'home/protocols/protocolSearchResults', component: ProtocolSearchResultsComponent },

    { path: 'home/protocols', component: ProtocolsComponent },
    { path: 'home/samples', component: SamplesComponent },

    { path: 'home/samples/samplesEdit/:sampleId', component: SampleEditComponent },
    { path: 'home/samples/samplesView/:sampleId', component: SampleViewComponent },
    { path: 'home/samples/samplesSearch', component: SampleSearchComponent },
    { path: 'home/samples/samplesSearchResults', component: SampleSearchResultsComponent },
    { path: 'home/samples/samplesAdvancedSearch', component: SampleAdvancedSearchComponent },
    { path: 'home/samples/sampleCreate', component: SampleCreateComponent },
    { path: 'home/samples/samplesCopy', component: SampleCopyComponent },

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
