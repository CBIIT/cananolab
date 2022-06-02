import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Properties } from '../../../../assets/properties';

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

    constructor(private httpClient:HttpClient) { }

    ngOnInit(): void {
        this.sampleData={};
        let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/community/getCollaborationGroups');
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
        let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/community/setupNew');
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
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/community/deleteCollaborationGroups',this.collaborationGroup);
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
        let url=this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/community/editCollaborationGroup?groupId='+group.id);
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
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/community/deleteUserAccess',this.userInfoBean);
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
            let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/community/getsamples?groupId='+group.id);
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
        let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/community/addCollaborationGroups',this.collaborationGroup);
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
        let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/community/addUserAccess',userAccess);
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
        let url=this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/core/getUsers?searchStr='+this.userInfoBean['recipient']+'&dataOwner=');
        url.subscribe(data=>{
            this.users=data;
            this.errors={};
        },
        error=> {
            this.errors=error;
        })
    }

}
