import { Component, OnInit } from '@angular/core';
import { Consts } from 'src/app/constants';
import { ApiService } from '../../common/services/api.service';
@Component({
  selector: 'canano-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['../../../btn-bravo-canano.scss','./groups.component.scss']
})
export class GroupsComponent implements OnInit {
    helpUrl='javascript:openHelpWindow('+'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Collaboration+Groups' + ')';
    toolHeadingNameManage='Manage Collaboration Groups';
    data;
    errors={};
    sampleData;
    collaborationGroup;
    collaborationGroupTrailer;
    collaborationIndex;
    userFormIndex;
    userInfoBean;
    users;

    constructor(private apiService:ApiService) { }

    ngOnInit(): void {
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
        this.apiService.doGet(Consts.QUERY_COLLABORATION_EDIT_GROUP,'groupId='+group.id)
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

}
