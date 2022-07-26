import { Component, OnInit } from '@angular/core';
import { Consts } from 'src/app/constants';
import { ApiService } from '../../common/services/api.service';
import { Router } from '@angular/router';
import { StatusDisplayService } from '../../status-display/status-display.service';
import { Properties } from '../../../../assets/properties';
@Component({
  selector: 'canano-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['../../../btn-bravo-canano.scss','./groups.component.scss']
})
export class GroupsComponent implements OnInit {
    helpUrl='javascript:openHelpWindow('+'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Collaboration+Groups' + ')';
    toolHeadingNameManage='Manage Collaboration Groups';
    currentUrl;
    data;
    errors={};
    sampleData;
    collaborationGroup;
    collaborationGroupTrailer;
    collaborationIndex;
    userFormIndex;
    userInfoBean;
    users;
    groups;
    constructor(private statusDisplayService:StatusDisplayService,private router:Router,private apiService:ApiService) { }

    ngOnInit(): void {
        this.groups=Properties['GROUPS'];
        if (this.router.url.includes('collaboration-groups')) {
            this.currentUrl='collaboration-groups'
            this.toolHeadingNameManage='Manage Collaboration Groups'
        }
        else {
            this.currentUrl='manage-groups';
            this.toolHeadingNameManage='Manage Community'
        }
        this.sampleData={};
        let url = this.apiService.doGet(Consts.QUERY_COLLABORATION_GET_GROUPS,'');
        url.subscribe(data=> {
            this.data=data;
            this.errors={};
        },
        error=> {
            this.errors=error;
            console.log('erorr')
        })
    }

    addCollaborationGroup() {
        this.collaborationIndex=-1;
        this.collaborationGroup={
            "userAccesses":[]
        }
        let url = this.apiService.doGet(Consts.QUERY_COLLABORATION_SETUP_NEW,'');
        url.subscribe(data=> {
            this.errors={};
        },
        error=> {
            this.errors=error;
        })
        setTimeout(function() {
            document.getElementById('collaborationForm').scrollIntoView();
        },100);
    }

    addUser() {
        this.userFormIndex=-1;
        this.users=null;
        this.userInfoBean={
            "recipient":""
        };
    }

    cancelCollaborationGroup() {
        this.collaborationIndex=null;
    }

    cancelUser() {
        this.userFormIndex=null;
    }

    deleteCollaborationGroup() {
        if (confirm("Are you sure you wish to delete this collaboration group?")) {
            let url = this.apiService.doPost(Consts.QUERY_COLLABORATION_DELETE_GROUPS,this.collaborationGroup);
            url.subscribe(data=>{
                this.data=data;
                this.errors={};
            },
            error=> {
                this.errors=error;
            })
        }
    };

    editCollaborationGroup(group) {
        this.collaborationIndex=group.id;
        setTimeout(function() {
            document.getElementById('collaborationForm').scrollIntoView();
        },100);
        let url = this.apiService.doGet(Consts.QUERY_COLLABORATION_EDIT_GROUP,'groupId='+group.id)
        url.subscribe(data=> {
            setTimeout(function() {
                document.getElementById('collaborationForm').scrollIntoView();
            },100);
            this.errors={};
            this.collaborationGroup=data;
            console.log(data)
            this.collaborationGroupTrailer=JSON.parse(JSON.stringify(data));
        },
        error=> {
            this.errors=error;
        })
    }

    deleteUser(user,index) {
        if (confirm("Are you sure you wish to delete this user?")) {
            this.userInfoBean=user;
            let url = this.apiService.doPost(Consts.QUERY_COLLABORATION_DELETE_USER_ACCESS,this.userInfoBean);
            url.subscribe(data=> {
                this.collaborationGroup=data;
                this.errors={};
            },
            error=> {
                this.errors=error;
            })
        };
    };

    expand(group,index,expand) {
        if (expand) {
            let url = this.apiService.doGet(Consts.QUERY_COLLABORATION_GET_SAMPLES,'groupId='+group.id)
                // this.sampleData[group.id]=[{"sampleId":65765379,"sampleName":"ISU-LHuntimerAHM2013-01","pointOfContact":"ISU_ChemBiolEngin<br>Chemical and Biological Engineering<br>Iowa State University<br>Ames IA 50011","composition":["Polymer"],"functions":[],"characterizations":["Inflammatory response","Toxicology","other"],"dataAvailability":"N/A","createdDate":1510138426000,"editable":false,"nanoEntityDesc":"Copolymer nanoparticle: 20:80 CPH:SA\nThis nanoparticle is described in Huntimer L, Ramer-Tait AE, Petersen LK, Ross KA, Walz KA, Wang C, Hostetter J, Narasimhan B, Wannemuehler MJ. Evaluation of biocompatibility and administration site reactogenicity of polyanhydride-particle-based platform for vaccine delivery. Adv Health Mater. 2:369-378 (2013)."},{"sampleId":65765380,"sampleName":"ISU-LHuntimerAHM2013-02","pointOfContact":"ISU_ChemBiolEngin<br>Chemical and Biological Engineering<br>Iowa State University<br>Ames IA 50011","composition":["Polymer"],"functions":[],"characterizations":["Inflammatory response","Toxicology","other"],"dataAvailability":"N/A","createdDate":1510138426000,"editable":false,"nanoEntityDesc":"Copolymer nanoparticle: 50:50 CPTEG:CPH\nThis nanoparticle is described in Huntimer L, Ramer-Tait AE, Petersen LK, Ross KA, Walz KA, Wang C, Hostetter J, Narasimhan B, Wannemuehler MJ. Evaluation of biocompatibility and administration site reactogenicity of polyanhydride-particle-based platform for vaccine delivery. Adv Health Mater. 2:369-378 (2013)."},{"sampleId":65765381,"sampleName":"ISU-LHuntimerAHM2013-03","pointOfContact":"ISU_ChemBiolEngin<br>Chemical and Biological Engineering<br>Iowa State University<br>Ames IA 50011","composition":["Polymer"],"functions":[],"characterizations":["Inflammatory response","Toxicology","other"],"dataAvailability":"N/A","createdDate":1510138426000,"editable":false,"nanoEntityDesc":"Copolymer nanoparticle: 20:80 CPTEG:CPH\nThis nanoparticle is described in Huntimer L, Ramer-Tait AE, Petersen LK, Ross KA, Walz KA, Wang C, Hostetter J, Narasimhan B, Wannemuehler MJ. Evaluation of biocompatibility and administration site reactogenicity of polyanhydride-particle-based platform for vaccine delivery. Adv Health Mater. 2:369-378 (2013)."},{"sampleId":65765382,"sampleName":"ISU-LHuntimerAHM2013-04","pointOfContact":"ISU_ChemBiolEngin<br>Chemical and Biological Engineering<br>Iowa State University<br>Ames IA 50011","composition":["Polymer"],"functions":["imaging function"],"characterizations":["Persistence"],"dataAvailability":"N/A","createdDate":1510162290000,"editable":false,"nanoEntityDesc":"Copolymer nanoparticle: 20:80 CPH:SA loaded with 0.5% Kodak X-Sight 640 LSS Dye\nThis nanoparticle is described in Huntimer L, Ramer-Tait AE, Petersen LK, Ross KA, Walz KA, Wang C, Hostetter J, Narasimhan B, Wannemuehler MJ. Evaluation of biocompatibility and administration site reactogenicity of polyanhydride-particle-based platform for vaccine delivery. Adv Health Mater. 2:369-378 (2013)."},{"sampleId":65765385,"sampleName":"ISU-LHuntimerAHM2013-05","pointOfContact":"ISU_ChemBiolEngin<br>Chemical and Biological Engineering<br>Iowa State University<br>Ames IA 50011","composition":["Polymer"],"functions":["imaging function"],"characterizations":["Persistence"],"dataAvailability":"N/A","createdDate":1510162290000,"editable":false,"nanoEntityDesc":"Copolymer nanoparticle: 50:50 CPTEG:CPH loaded with 0.5% Kodak X-Sight 640 LSS Dye\nThis nanoparticle is described in Huntimer L, Ramer-Tait AE, Petersen LK, Ross KA, Walz KA, Wang C, Hostetter J, Narasimhan B, Wannemuehler MJ. Evaluation of biocompatibility and administration site reactogenicity of polyanhydride-particle-based platform for vaccine delivery. Adv Health Mater. 2:369-378 (2013)."},{"sampleId":65765386,"sampleName":"ISU-LHuntimerAHM2013-06","pointOfContact":"ISU_ChemBiolEngin<br>Chemical and Biological Engineering<br>Iowa State University<br>Ames IA 50011","composition":["Polymer"],"functions":["imaging function"],"characterizations":["Persistence"],"dataAvailability":"N/A","createdDate":1510162290000,"editable":false,"nanoEntityDesc":"Copolymer nanoparticle: 20:80 CPTEG:CPH loaded with 0.5% Kodak X-Sight 640 LSS Dye\nThis nanoparticle is described in Huntimer L, Ramer-Tait AE, Petersen LK, Ross KA, Walz KA, Wang C, Hostetter J, Narasimhan B, Wannemuehler MJ. Evaluation of biocompatibility and administration site reactogenicity of polyanhydride-particle-based platform for vaccine delivery. Adv Health Mater. 2:369-378 (2013)."}]
            url.subscribe(data=> {
                this.sampleData[group.id]=data;
                this.errors={};
            },
            error=> {
                this.errors=error;
            })
            group['expand']=true;
        }
        else {
            console.log('')
            group['expand']=false;
        }
        console.log(group.expand)
    }

    saveCollaborationGroup() {
        let url = this.apiService.doPost(Consts.QUERY_COLLABORATION_ADD_COLLABORATION_GROUPS,this.collaborationGroup);
        url.subscribe(data=> {
            this.data=data;
            this.errors={};
            this.collaborationIndex=null;
        },
        error=> {
            this.errors=error;
        })
    }

    saveUser() {
        let userAccess={
            "accessType":"user",
            "recipient":this.userInfoBean.recipient,
            "recipientDisplayName":"",
            "roleDisplayName":"READ",
            "roleName":"R"
        }
        let url = this.apiService.doPost(Consts.QUERY_COLLABORATION_ADD_USER_ACCESS,userAccess);
        url.subscribe(data=>{
            this.collaborationGroup.userAccesses=data['userAccesses'];
            this.errors={};
            this.userFormIndex=null;
        },
        error=>{
            this.errors=error;
        })
    }

    searchForUser() {
        if (!this.userInfoBean['recipient']) {
            this.userInfoBean['recipient']='';
        };
        let url = this.apiService.doGet(Consts.QUERY_GET_USERS,'searchStr='+this.userInfoBean['recipient']+'&dataOwner=');
        url.subscribe(data=>{
            this.users=data;
            this.errors={};
        },
        error=> {
            this.errors=error;
        })
    }

    isCurator() {
        if (this.groups.indexOf('Curator')>-1) {
            return true
        }
        return false
    }

}
