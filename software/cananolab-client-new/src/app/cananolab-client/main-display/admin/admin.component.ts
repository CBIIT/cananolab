import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Consts } from '../../../constants';
import { ApiService } from '../../common/services/api.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'canano-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

    currentUrl='admin';
    message='';
    errors={};
    resetData={oldpassword:"",newpassword:""};
    toolHeadingName='Manage Users';
    userData={};
    username='';
    userSearchResults;
    searchUserName='';
    constructor(private activatedRoute:ActivatedRoute,private router:Router,private apiService:ApiService,private httpClient:HttpClient) { }
    ngOnInit(): void {
        this.activatedRoute.params.subscribe(data=> {
            this.username=data['username'];
            if (this.username) {
                this.resetData['username']=this.username;
                this.apiService.doGet(Consts.QUERY_ADMIN_USER_READ,'username='+this.username).subscribe(data=> {
                    let userData=this.setupUserData();
                    Object.keys(userData).forEach(element=> { // we dont want credentials so create new object first then get keys //
                        if (element=='roles') {
                            data[element].splice(data[element].indexOf('ROLE_ANONYMOUS'),1)
                        }
                        this.userData[element]=data[element];
                    })

                })
            }
        })
        if (this.router.url.includes('update-user')) {
            this.currentUrl='update-user';
            this.toolHeadingName='Update User';
        }
        if (this.router.url.includes('create-user')) {
            this.currentUrl='create-user';
            this.toolHeadingName='Create User';
            this.userData=this.setupUserData();
            this.userData['username']='';
        }
        if (this.router.url.includes('search-user')) {
            this.currentUrl='search-user';
            this.toolHeadingName='Search Users';
        }
        if (this.router.url.includes('reset-password')) {
            this.currentUrl='reset-password';
            this.toolHeadingName='Reset Password';
        }


    }

    navigateToEditUser(user) {
        this.router.navigate(['/home/admin/update-user',user.username])
    }

    navigateToResetPassword(user) {
        this.router.navigate(['/home/admin/reset-password',user.username])
    }

    reset() {
        this.userData=this.setupUserData();
        if (this.currentUrl=='create-user') {
            this.userData['username']=='';
        };
        if (this.currentUrl=='update-user') {
            this.userData['username']=this.username;
        }
    }
    resetPassword() {
        let params =[];
        Object.keys(this.resetData).forEach(key=> {
           params.push(key+'='+this.resetData[key])
        })
        // let resetData='oldpassword=asad&newpassword=sadasds&username=abcdefu'

        let headers = new HttpHeaders( {
            'Content-Type': 'application/x-www-form-urlencoded',
            } );
        let options={
            headers:headers,
            method:'post',
        }
        this.httpClient.post( Consts.QUERY_ADMIN_RESET_PASSWORD,params.join('&'),options).subscribe(data=> {
            this.message='Password Reset Successfully';
        },
        errors=> {
            if (errors.error.text=='success') {
                this.errors={};
                this.message='Password Reset Successfully'
            }
            else {
                console.log('a')
                this.message='';
                this.errors=errors;
            }
        })

    }

    setupUserData() {
        let userData={
            username:"",
            department: "",
            emailId: "",
            enabled: true,
            firstName: "",
            lastName: "",
            organization: "",
            phoneNumber: "",
            roles: [],
            title: ""
        }
        return userData;
    }



    searchForUsers() {
        this.apiService.doGet(Consts.QUERY_ADMIN_SEARCH_USERS,'username='+this.searchUserName).subscribe(data=>{
            this.userSearchResults=data;
        })
    }


    submit() {
        let url;
        if (this.currentUrl=='create-user') {
            this.apiService.doPost(Consts.QUERY_ADMIN_USER_CREATE,this.userData).subscribe(data=> {
                this.router.navigate(['/home/admin/update-user',data.username])
            })
        } url = Consts.QUERY_ADMIN_USER_CREATE;
        if (this.currentUrl=='update-user') {
            this.apiService.doPost(Consts.QUERY_ADMIN_USER_UPDATE,this.userData).subscribe(data=> {
                this.message='User Successfully Updated'
            })
        }


    }

}
