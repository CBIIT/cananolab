import { Component, OnInit } from '@angular/core';
import { Consts, ProtocolScreen } from '../../../constants';
import { ApiService } from '../../common/services/api.service';
import { Router } from '@angular/router';
import { ProtocolsService } from '../protocols/protocols.service';

@Component({
  selector: 'canano-my-workspace',
  templateUrl: './my-workspace.component.html',
  styleUrls: ['./my-workspace.component.scss']
})
export class MyWorkspaceComponent implements OnInit {
    helpUrl = Consts.HELP_URL_WORKSPACE;
    toolHeadingNameSearchSample = 'My Workspace';
    samples = [];
    protocols = [];
    publications = [];
    showSampleWorkspaces = true;
    showProtocolsWorkspaces = true;
    showPublicationsWorkspaces = true;
    errors={};
  constructor( private apiService: ApiService, private router: Router,
               private protocolsService: ProtocolsService ) { }

  ngOnInit(): void {
    this.loadData();

  }

  loadData() {
    this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=sample' ).subscribe(
        data => {
            this.samples = data['samples'];
            this.errors={};
        },

        ( err ) => {
            this.errors=err;
            console.log( 'ERROR QUERY_GET_WORKSPACE samples: ', err );
        } );

    this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=protocol' ).subscribe(
        data => {
            this.errors={};
            this.protocols = data['protocols'];
            console.log( 'QUERY_GET_WORKSPACE protocols: ', data );
        },

        ( err ) => {
            this.errors=err;
            console.error( 'ERROR QUERY_GET_WORKSPACE protocols: ', err );
        } );


    this.apiService.doGet( Consts.QUERY_GET_WORKSPACE, 'type=publication' ).subscribe(
        data => {
            this.errors={};
            this.publications = data['publications'];
            this.publications=[
                {
                    "editable":true,
                    "name":"TEST: Reduced prealbumin (transthyretin) in CSF of severely demented patients with Alzheimer's disease.",
                    "id":110493703,
                    "submisstionStatus":"Deleted",
                    "createdDate":1639680183000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/3223231\" target=\"_blank\">3223231</a><br>10.1111/j.1600-0404.1988.tb03687.x",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"Methodological analysis of an experimental spinal cord compression model in the rat.",
                    "id":110493705,
                    "submisstionStatus":"In Draft",
                    "createdDate":1639680349000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/3223232\" target=\"_blank\">3223232</a><br>10.1111/j.1600-0404.1988.tb03688.x",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"TEST: Alteration of amino acid content of cerebrospinal fluid from patients with epilepsy.",
                    "id":110493706,
                    "submisstionStatus":"In Draft",
                    "createdDate":1639680373000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/3223234\" target=\"_blank\">3223234</a><br>10.1111/j.1600-0404.1988.tb03690.x",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"TEST: The incidence of intracranial gliomas in southern Finland.",
                    "id":110493707,
                    "submisstionStatus":"In Draft",
                    "createdDate":1639680397000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/3223235\" target=\"_blank\">3223235</a><br>10.1111/j.1600-0404.1988.tb03691.x",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"Computerized tomography in infantile encephalitis.",
                    "id":111575047,
                    "submisstionStatus":"In Draft",
                    "createdDate":1639758538000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/223434\" target=\"_blank\">223434</a><br>10.1001/archpedi.1979.02130080043008",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"TEST",
                    "id":111575048,
                    "submisstionStatus":"In Draft",
                    "createdDate":1639758603000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"Computerized tomography in infantile encephalitis.",
                    "id":112197632,
                    "submisstionStatus":"In Draft",
                    "createdDate":1640060064000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"MyBook",
                    "id":113934339,
                    "submisstionStatus":"In Draft",
                    "createdDate":1646153956000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"TEST ME Book",
                    "id":115474432,
                    "submisstionStatus":"Approved",
                    "createdDate":1649775486000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher, Public)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"TEST CURATE",
                    "id":115802112,
                    "submisstionStatus":"In Draft",
                    "createdDate":1652451690000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"YYdddXX A Tubular DNA Nanodevice as a siRNA/Chemo-Drug Co-delivery Vehicle for Combined Cancer Therapy.",
                    "id":116457472,
                    "submisstionStatus":"In Draft",
                    "createdDate":1655918883000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/33089613\" target=\"_blank\">33089613</a><br>10.1002/anie.202009842",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"My Pub Book Chapter",
                    "id":116457473,
                    "submisstionStatus":"In Draft",
                    "createdDate":1655919827000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"XXX A field survey on home environmental sanitation in two districts of Kaohsiung city, Taiwan.",
                    "id":116457474,
                    "submisstionStatus":"In Draft",
                    "createdDate":1655920703000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/456\" target=\"_blank\">456</a>",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"Test Pub AXX",
                    "id":116457475,
                    "submisstionStatus":"In Draft",
                    "createdDate":1655921025000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"re1",
                    "id":116555776,
                    "submisstionStatus":"In Draft",
                    "createdDate":1655921588000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 },
                 {
                    "editable":true,
                    "name":"rep11333",
                    "id":116555777,
                    "submisstionStatus":"In Draft",
                    "createdDate":1655921774000,
                    "fileId":0,
                    "externalURL":null,
                    "comments":null,
                    "pubMedDOIId":"",
                    "access":"(Owner, Shared with: Curator, Researcher)",
                    "actions":[

                    ],
                    "owner":true
                 }
            ]
            console.log( 'QUERY_GET_WORKSPACE publications: ', data );
        },

        ( err ) => {
            this.errors=err;
            console.error( 'ERROR> QUERY_GET_WORKSPACE publications: ', err );
        } );
  }

    navigateToSampleView(sampleId, sampleName){
        this.router.navigate(['home/samples/view-sample', sampleId  ]);
    }

    navigateToSampleEdit(sampleId, sampleName){
        this.router.navigate(['home/samples/sample', sampleId ]);
    }

    /**
     * Delete from favorites
     * @param sampleId
     */
    navigateToSampleDelete(sampleId){
        if (confirm("Are you sure you wish to delete this sample?")) {
            this.apiService.doGet( Consts.QUERY_SAMPLE_DELETE_FROM_WORKSPACE, 'sampleId=' + sampleId ).subscribe(
                data => {
                    this.errors={};
                    this.loadData();
                },

                ( err ) => {
                    this.loadData();
                    console.error( '\'caNanoLab/rest/sample/deleteSampleFromWorkspace?sampleId=\'' + sampleId + ':', err );
                } );
        }
    }



    navigateToProtocolEdit(protocolId, protocolName){
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, protocolId );
        this.router.navigate(['home/protocols/edit-protocol',protocolId]);
    }

    navigateToProtocolDelete(protocolId){
        if (confirm("Are you sure you wish to delete this protocol?")) {
                this.apiService.doGet( Consts.QUERY_DELETE_PROTOCOL_BY_ID, 'protocolId=' + protocolId ).subscribe(
                    data => {
                    this.errors={};
                    this.loadData();
                },

                ( err ) => {
                    this.errors=err;
                    console.error( '\'caNanoLab/rest/sample/deleteProtocolById?protocolId=\'' + protocolId + ':', err );
                } );
        }

    }

    navigateToPublicationEdit(publicationId){
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, publicationId );
        this.router.navigate(['home/samples/publications/publication',publicationId]);
    }

    navigateToPublicationDelete(publicationId){
        if (confirm("Are you sure you wish to delete this publication?")) {
            this.apiService.doGet( Consts.QUERY_DELETE_PUBLICATION_BY_ID, 'publicationId=' + publicationId ).subscribe(
                data => {
                    this.errors={};
                    this.loadData();
                },

                ( err ) => {
                    this.errors=err;
                } );
        }

    }
}
