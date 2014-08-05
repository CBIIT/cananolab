'use strict';

var app = angular.module('angularApp')
.controller('editSampleCtrl', function (sampleService,navigationService,groupService,$scope,$rootScope,$filter,ngTableParams,$http,$location,$modal,$routeParams) {

//var app = angular.module('angularApp').app.controller('editSampleCtrl', function (sampleService,utilsService,navigationService, groupService, $rootScope,$scope,$http,$location,$filter,$routeParams) {
    $rootScope.tabs = navigationService.query();
    $rootScope.groups = groupService.get();
    $scope.sampleData = sampleService.sampleData;
    $scope.sampleId = sampleService.sampleId;
    $scope.scratchPad = sampleService.scratchPad;
    $scope.master = {};

    var editSampleData = {"editSampleData":{"dirty": false}};
    $scope.scratchPad = editSampleData;

    $scope.editSampleForm = false;
    $scope.loader = true;


    // Displays left hand nav for samples section. navTree shows nav and navDetail is page index //
    $rootScope.navTree = true;
    $rootScope.navDetail = 0;

    //goBack
    //Change location if user hits the Back button
    $scope.goBack = function() {
        $location.path("/sampleResults").replace();
        $location.search('sampleId', null);
    };

    //Get URL params
    if ($routeParams.sampleId) {
      //  $scope.sampleId = $routeParams.sampleId.data;
      $scope.sampleId.data = $routeParams.sampleId;

    } else {
        $scope.message = "Need to add a sampleId to the url";
    }

    //Print out the scope for this page
    console.log("scope");
    console.dir($scope);
    console.log("rootScope");
    console.dir($rootScope);

    $scope.returnUserReadableBoolean = function(val) {
        if (val== true) {
            return "Yes";
        }
        return "No";
    }

    //Populate the sampleData if browsing in Firefox to local file
    if(location.hostname == "") {
        $scope.loader = true;
        /* if no hostname then populate with raw data */
    	console.info("No REST Service, loading hard coded sampleData")
        var hardCodedSampleData = [{"sampleName":"Gaheen_Test","sampleId":38404103,"userIsCurator":true,"pointOfContacts":[{"id":15695942,"contactPerson":"","organization":{"id":11796483,"name":"NCL"},"role":"","address":{"line1":"CBER, FDA, 1401 Rockville Pike","line2":"HFM 335","city":"Rockville","stateProvince":"MD","zip":"20852-1448","country":"USA"},"firstName":"","lastName":"","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true}],"keywords":[],"accessToSample":{"userAccesses":[],"groupAccesses":[{"groupName":"Curator","roleName":"","roleDisplayName":"read update delete","loginName":"","accessBy":"","sampleId":38404103}]},"dataAvailability":null,"organizationNamesForUser":["AIST_HTRC","BB_SH_DFCI_WCMC_BWH_MIT","BB_SH_KCI_DFCI_WCMC_BWH_MIT","BROWN_STANFORD","BWH_AnesthesiologyD","C-Sixty (CNI)","CAS_VT_VCU","CLM_UHA_CDS_INSERM","CP_UCLA_CalTech","CWRU","Childrens Hospital Los Angeles","Chris","DC","DNT","DWU_SNU_SU_US","GATECH","GATECH_EMORY","GATECH_UCSF","GIST_LifeScienceD","HarvardU_PhysD","Harvard_MIT_DHST","JHU_KSU","JSTA_FHU_NEC_MU_AIST_JAPAN","JSTA_JFCR_NEC_FHU_TUSM_NIAIST","JST_AIST_FHU_TU_NEC_MU_JAPAN","Joe Barchi","KI","KU_JSTC_JAPAN","LMRT","MIT_ChemD","MIT_ChemEngineeringD","MIT_LMRT","MIT_MGH","MIT_MGH_GIST","MIT_UC_BBIC_HST_CU","MSKCC_CU_UA","Mansoor Amiji","Mark Kester","Mark Kester PSU","NCL","NEU","NEU_DPS","NEU_MGH_UP_FCCC","NEU_MIT_MGH","NIEHS","NIOSH","NRCWE","NWU","NWU_ChemD_IIN","Nanotechnology Characterization Laboratory","Nanotechnology_Characterization_Laboratory","New One","PNNL_CBBG","PURDUE","PURDUEU_BN","RIT_KI_SU","SNL_UNM","SNU_CHINA","STANFORD","STANFORD_ChemD","STANFORD_MIPS","STANFORD_OM","SUNYB_ILPB","SY Org","SY company","Sharon","TAM_UT","TTU","UAM_CSIC_IMDEA","UCSD_ChemBiochemD","UCSD_MIT_MGH","UC_HU_UEN_GERMANY","UI","UKY","UL_NL","UM","UM-C","UMC_DVP","UMC_RadiolD","UN","UNC","UNC_ChemD","UTHSCH_UMG_MDACC_RU_UTA","UT_UMG_MDACC_RU_UTA","UToronto","VCU_VT_EHC","WSU","WSU_DPS","WUSTL","WUSTL_DIM","WU_SU_CHINA","YALE","my org","natcompany","other","shanyang","shanyangcomp"],"contactRoles":["Chris Contact Role","investigator","manufacturer"],"allGroupNames":["Carbon Tube Group","Demo University","Demo-Collaboration","NCI group","NCNHIR","Test Collaboration"],"filteredUsers":{"lethai":"Le, Thai","Omelchen":"Omelchenko, Marina","burnskd":"Burns, Kevin","canano_guest":"Guest, Guest","grodzinp":"Grodzinski, Piotr","swand":"Swan, Don","skoczens":"Skoczen, Sarah","sternstephan":"Stern, Stephan","zolnik":"Zolnik, Banu","canano_res":"Researcher, CaNano","hunseckerk":"Hunsecker, Kelly","lipkeyfg":"Lipkey, Foster","marina":"Dobrovolskaia, Marina","pottert":"Potter, Tim","uckunf":"Uckun, Fatih","michal":"Lijowski, Michal","mcneils":"Mcneil, Scott","neunb":"Neun, Barry","cristr":"Crist, Rachael","zhengji":"Zheng, Jiwen","SchaeferH":"Schaefer, Henry","frittsmj":"Fritts, Martin","benhamm":"Benham, Mick","masoods":"Masood, Sana","mclelandc":"McLeland, Chris","torresdh":"Torres, David","KlemmJ":"Klemm, Juli","patria":"Patri, Anil","hughesbr":"Hughes, Brian","clogstonj":"Clogston, Jeff","hinkalgw":"Hinkal, George","MorrisS2":"Morris, Stephanie","sharon":"Gaheen, Sharon"},"roleNames":{"R":"read","CURD":"read update delete"},"showReviewButton":false,"errors":[]}];
        hardCodedSampleData = [{"sampleName":"WUSTL-JMarshIEEEUS2000-01","sampleId":3735561,"userIsCurator":true,"pointOfContacts":[{"id":15695884,"contactPerson":"","organization":{"id":11796482,"name":"WUSTL"},"role":"investigator","address":{"line1":"","line2":"","city":"Saint Louis","stateProvince":"MO","zip":"","country":"USA"},"firstName":"Jon","lastName":"Marsh","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":15695945,"contactPerson":"","organization":{"id":11796482,"name":"WUSTL"},"role":"investigator","address":{"line1":"","line2":"","city":"Saint Louis","stateProvince":"MO","zip":"","country":"USA"},"firstName":"Samuel","lastName":"Wickline","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":15695949,"contactPerson":"","organization":{"id":11796482,"name":"WUSTL"},"role":"investigator","address":{"line1":"","line2":"","city":"Saint Louis","stateProvince":"MO","zip":"","country":"USA"},"firstName":"Gregory","lastName":"Lanza","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true}],"keywords":["ACOUSTIC MICROSCOPY","AVIDIN","BIOTIN","FIBRIN CLOTS","LIPID MONOLAYER","PERFLUOROCARBON","THROMBIN","ULTRASOUND"],"accessToSample":{"userAccesses":[],"groupAccesses":[{"groupName":"Curator","roleName":"","roleDisplayName":"read update delete","loginName":"","accessBy":"","sampleId":3735561},{"groupName":"Public","roleName":"","roleDisplayName":"read","loginName":"","accessBy":"","sampleId":3735561}]},"dataAvailability":{"sampleName":"WUSTL-JMarshIEEEUS2000-01","dataAvailability":"caNanoLab: 30%; MINChar: 33%","caNanoLabScore":"30.0% (9 out of 30)","mincharScore":"33.0% (3 out of 9)","chemicalAssocs":["attachment","encapsulation","entrapment"],"physicoChars":["molecular weight","physical state","purity","relaxivity","shape","size","solubility","surface"],"invitroChars":["blood contact","cytotoxicity","enzyme induction","immune cell function","metabolic stability","oxidative stress","sterility","targeting","transfection"],"invivoChars":["pharmacokinetics","toxicology"],"availableEntityNames":["sample function","functionalizing entities","general sample information","publications","attachment","sample composition","size","encapsulation","nanomaterial entities"],"caNano2MINChar":{"zeta potential":"surface charge","sample composition":"chemical composition","purity":"purity","surface charge":"surface charge","shape":"shape","attachment":"surface chemistry","surface area":"surface area","size":"particle size/size distribution"}},"organizationNamesForUser":["AIST_HTRC","BB_SH_DFCI_WCMC_BWH_MIT","BB_SH_KCI_DFCI_WCMC_BWH_MIT","BROWN_STANFORD","BWH_AnesthesiologyD","C-Sixty (CNI)","CAS_VT_VCU","CLM_UHA_CDS_INSERM","CP_UCLA_CalTech","CWRU","Childrens Hospital Los Angeles","Chris","DC","DNT","DWU_SNU_SU_US","GATECH","GATECH_EMORY","GATECH_UCSF","GIST_LifeScienceD","HarvardU_PhysD","Harvard_MIT_DHST","JHU_KSU","JSTA_FHU_NEC_MU_AIST_JAPAN","JSTA_JFCR_NEC_FHU_TUSM_NIAIST","JST_AIST_FHU_TU_NEC_MU_JAPAN","Joe Barchi","KI","KU_JSTC_JAPAN","LMRT","MIT_ChemD","MIT_ChemEngineeringD","MIT_LMRT","MIT_MGH","MIT_MGH_GIST","MIT_UC_BBIC_HST_CU","MSKCC_CU_UA","Mansoor Amiji","Mark Kester","Mark Kester PSU","NCL","NEU","NEU_DPS","NEU_MGH_UP_FCCC","NEU_MIT_MGH","NIEHS","NIOSH","NRCWE","NWU","NWU_ChemD_IIN","Nanotechnology Characterization Laboratory","Nanotechnology_Characterization_Laboratory","New One","PNNL_CBBG","PURDUE","PURDUEU_BN","RIT_KI_SU","SNL_UNM","SNU_CHINA","STANFORD","STANFORD_ChemD","STANFORD_MIPS","STANFORD_OM","SUNYB_ILPB","SY Org","SY company","Sharon","TAM_UT","TTU","UAM_CSIC_IMDEA","UCSD_ChemBiochemD","UCSD_MIT_MGH","UC_HU_UEN_GERMANY","UI","UKY","UL_NL","UM","UM-C","UMC_DVP","UMC_RadiolD","UN","UNC","UNC_ChemD","UTHSCH_UMG_MDACC_RU_UTA","UT_UMG_MDACC_RU_UTA","UToronto","VCU_VT_EHC","WSU","WSU_DPS","WUSTL","WUSTL_DIM","WU_SU_CHINA","YALE","my org","natcompany","other","shanyang","shanyangcomp"],"contactRoles":["Chris Contact Role","investigator","manufacturer"],"allGroupNames":["Carbon Tube Group","Demo University","Demo-Collaboration","NCI group","NCNHIR","Test Collaboration"],"filteredUsers":{"lethai":"Le, Thai","Omelchen":"Omelchenko, Marina","burnskd":"Burns, Kevin","canano_guest":"Guest, Guest","grodzinp":"Grodzinski, Piotr","swand":"Swan, Don","skoczens":"Skoczen, Sarah","sternstephan":"Stern, Stephan","zolnik":"Zolnik, Banu","canano_res":"Researcher, CaNano","hunseckerk":"Hunsecker, Kelly","lipkeyfg":"Lipkey, Foster","marina":"Dobrovolskaia, Marina","pottert":"Potter, Tim","uckunf":"Uckun, Fatih","michal":"Lijowski, Michal","mcneils":"Mcneil, Scott","neunb":"Neun, Barry","cristr":"Crist, Rachael","zhengji":"Zheng, Jiwen","SchaeferH":"Schaefer, Henry","frittsmj":"Fritts, Martin","benhamm":"Benham, Mick","masoods":"Masood, Sana","mclelandc":"McLeland, Chris","torresdh":"Torres, David","KlemmJ":"Klemm, Juli","patria":"Patri, Anil","hughesbr":"Hughes, Brian","clogstonj":"Clogston, Jeff","hinkalgw":"Hinkal, George","MorrisS2":"Morris, Stephanie","sharon":"Gaheen, Sharon"},"roleNames":{"R":"read","CURD":"read update delete"},"showReviewButton":false,"errors":[]}];
        //This data has an address1 (sampleId=38404103)
        /*
        if($scope.sampleId.data == '38404103' ) {
            // Has an Address1
            hardCodedSampleData = [{"sampleName":"Gaheen_Test","sampleId":38404103,"userIsCurator":true,"pointOfContacts":[{"id":15695942,"contactPerson":"","organization":{"id":11796483,"name":"NCL"},"role":"","address":{"line1":"CBER, FDA, 1401 Rockville Pike","line2":"HFM 335","city":"Rockville","stateProvince":"MD","zip":"20852-1448","country":"USA"},"firstName":"","lastName":"","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true}],"keywords":[],"accessToSample":{"userAccesses":[],"groupAccesses":[{"groupName":"Curator","roleName":"","roleDisplayName":"read update delete","loginName":"","accessBy":"","sampleId":38404103}]},"dataAvailability":null,"organizationNamesForUser":["AIST_HTRC","BB_SH_DFCI_WCMC_BWH_MIT","BB_SH_KCI_DFCI_WCMC_BWH_MIT","BROWN_STANFORD","BWH_AnesthesiologyD","C-Sixty (CNI)","CAS_VT_VCU","CLM_UHA_CDS_INSERM","CP_UCLA_CalTech","CWRU","Childrens Hospital Los Angeles","Chris","DC","DNT","DWU_SNU_SU_US","GATECH","GATECH_EMORY","GATECH_UCSF","GIST_LifeScienceD","HarvardU_PhysD","Harvard_MIT_DHST","JHU_KSU","JSTA_FHU_NEC_MU_AIST_JAPAN","JSTA_JFCR_NEC_FHU_TUSM_NIAIST","JST_AIST_FHU_TU_NEC_MU_JAPAN","Joe Barchi","KI","KU_JSTC_JAPAN","LMRT","MIT_ChemD","MIT_ChemEngineeringD","MIT_LMRT","MIT_MGH","MIT_MGH_GIST","MIT_UC_BBIC_HST_CU","MSKCC_CU_UA","Mansoor Amiji","Mark Kester","Mark Kester PSU","NCL","NEU","NEU_DPS","NEU_MGH_UP_FCCC","NEU_MIT_MGH","NIEHS","NIOSH","NRCWE","NWU","NWU_ChemD_IIN","Nanotechnology Characterization Laboratory","Nanotechnology_Characterization_Laboratory","New One","PNNL_CBBG","PURDUE","PURDUEU_BN","RIT_KI_SU","SNL_UNM","SNU_CHINA","STANFORD","STANFORD_ChemD","STANFORD_MIPS","STANFORD_OM","SUNYB_ILPB","SY Org","SY company","Sharon","TAM_UT","TTU","UAM_CSIC_IMDEA","UCSD_ChemBiochemD","UCSD_MIT_MGH","UC_HU_UEN_GERMANY","UI","UKY","UL_NL","UM","UM-C","UMC_DVP","UMC_RadiolD","UN","UNC","UNC_ChemD","UTHSCH_UMG_MDACC_RU_UTA","UT_UMG_MDACC_RU_UTA","UToronto","VCU_VT_EHC","WSU","WSU_DPS","WUSTL","WUSTL_DIM","WU_SU_CHINA","YALE","my org","natcompany","other","shanyang","shanyangcomp"],"contactRoles":["Chris Contact Role","investigator","manufacturer"],"allGroupNames":["Carbon Tube Group","Demo University","Demo-Collaboration","NCI group","NCNHIR","Test Collaboration"],"filteredUsers":{"lethai":"Le, Thai","Omelchen":"Omelchenko, Marina","burnskd":"Burns, Kevin","canano_guest":"Guest, Guest","grodzinp":"Grodzinski, Piotr","swand":"Swan, Don","skoczens":"Skoczen, Sarah","sternstephan":"Stern, Stephan","zolnik":"Zolnik, Banu","canano_res":"Researcher, CaNano","hunseckerk":"Hunsecker, Kelly","lipkeyfg":"Lipkey, Foster","marina":"Dobrovolskaia, Marina","pottert":"Potter, Tim","uckunf":"Uckun, Fatih","michal":"Lijowski, Michal","mcneils":"Mcneil, Scott","neunb":"Neun, Barry","cristr":"Crist, Rachael","zhengji":"Zheng, Jiwen","SchaeferH":"Schaefer, Henry","frittsmj":"Fritts, Martin","benhamm":"Benham, Mick","masoods":"Masood, Sana","mclelandc":"McLeland, Chris","torresdh":"Torres, David","KlemmJ":"Klemm, Juli","patria":"Patri, Anil","hughesbr":"Hughes, Brian","clogstonj":"Clogston, Jeff","hinkalgw":"Hinkal, George","MorrisS2":"Morris, Stephanie","sharon":"Gaheen, Sharon"},"roleNames":{"R":"read","CURD":"read update delete"},"showReviewButton":false,"errors":[]}];
        };
        if($scope.sampleId.data == '24063238' ) {
            //Has two Points of Contact
            hardCodedSampleData = [{"sampleName":"NCL-49","sampleId":24063238,"userIsCurator":true,"pointOfContacts":[{"id":44204032,"contactPerson":"","organization":{"id":44236800,"name":"natcompany"},"role":"","address":{"line1":"9 local street","line2":"","city":"","stateProvince":"","zip":"","country":""},"firstName":"natalie","lastName":"yang","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":35749890,"contactPerson":"","organization":{"id":35782656,"name":"BB_SH_DFCI_WCMC_BWH_MIT"},"role":"Chris Contact Role","address":{"line1":"This is an address","line2":"sfafsa","city":"sdfaf","stateProvince":"MD","zip":"20874","country":"USA"},"firstName":"","lastName":"","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":43679744,"contactPerson":"","organization":{"id":43712512,"name":"SY company"},"role":"Chris Contact Role","address":{"line1":"ldjsf&","line2":"","city":"","stateProvince":"","zip":"","country":""},"firstName":"","lastName":"","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":43810816,"contactPerson":"","organization":{"id":43843584,"name":"my org"},"role":"","address":{"line1":"","line2":"","city":"","stateProvince":"","zip":"","country":""},"firstName":"shan","lastName":"","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":43876352,"contactPerson":"","organization":{"id":43909120,"name":"other"},"role":"","address":{"line1":"","line2":"","city":"","stateProvince":"","zip":"","country":""},"firstName":"","lastName":"","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true}],"keywords":[],"accessToSample":{"userAccesses":[{"groupName":"","roleName":"","roleDisplayName":"read","loginName":"Omelchen","accessBy":"","sampleId":24063238}],"groupAccesses":[{"groupName":"NCI group","roleName":"","roleDisplayName":"read","loginName":"","accessBy":"","sampleId":24063238},{"groupName":"Curator","roleName":"","roleDisplayName":"read update delete","loginName":"","accessBy":"","sampleId":24063238}]},"dataAvailability":{"sampleName":"NCL-49","dataAvailability":"caNanoLab: 23%; MINChar: 22%","caNanoLabScore":"23.0% (7 out of 30)","mincharScore":"22.0% (2 out of 9)","chemicalAssocs":["attachment","encapsulation","entrapment"],"physicoChars":["molecular weight","physical state","purity","relaxivity","shape","size","solubility","surface"],"invitroChars":["blood contact","cytotoxicity","enzyme induction","immune cell function","metabolic stability","oxidative stress","sterility","targeting","transfection"],"invivoChars":["pharmacokinetics","toxicology"],"availableEntityNames":["publications","sample composition","size","general sample information","nanomaterial entities","cytotoxicity","blood contact"],"caNano2MINChar":{"zeta potential":"surface charge","sample composition":"chemical composition","purity":"purity","surface charge":"surface charge","shape":"shape","attachment":"surface chemistry","surface area":"surface area","size":"particle size/size distribution"}},"organizationNamesForUser":["AIST_HTRC","BB_SH_DFCI_WCMC_BWH_MIT","BB_SH_KCI_DFCI_WCMC_BWH_MIT","BROWN_STANFORD","BWH_AnesthesiologyD","C-Sixty (CNI)","CAS_VT_VCU","CLM_UHA_CDS_INSERM","CP_UCLA_CalTech","CWRU","Childrens Hospital Los Angeles","Chris","DC","DNT","DWU_SNU_SU_US","GATECH","GATECH_EMORY","GATECH_UCSF","GIST_LifeScienceD","HarvardU_PhysD","Harvard_MIT_DHST","JHU_KSU","JSTA_FHU_NEC_MU_AIST_JAPAN","JSTA_JFCR_NEC_FHU_TUSM_NIAIST","JST_AIST_FHU_TU_NEC_MU_JAPAN","Joe Barchi","KI","KU_JSTC_JAPAN","LMRT","MIT_ChemD","MIT_ChemEngineeringD","MIT_LMRT","MIT_MGH","MIT_MGH_GIST","MIT_UC_BBIC_HST_CU","MSKCC_CU_UA","Mansoor Amiji","Mark Kester","Mark Kester PSU","NCL","NEU","NEU_DPS","NEU_MGH_UP_FCCC","NEU_MIT_MGH","NIEHS","NIOSH","NRCWE","NWU","NWU_ChemD_IIN","Nanotechnology Characterization Laboratory","Nanotechnology_Characterization_Laboratory","New One","PNNL_CBBG","PURDUE","PURDUEU_BN","RIT_KI_SU","SNL_UNM","SNU_CHINA","STANFORD","STANFORD_ChemD","STANFORD_MIPS","STANFORD_OM","SUNYB_ILPB","SY Org","SY company","Sharon","TAM_UT","TTU","UAM_CSIC_IMDEA","UCSD_ChemBiochemD","UCSD_MIT_MGH","UC_HU_UEN_GERMANY","UI","UKY","UL_NL","UM","UM-C","UMC_DVP","UMC_RadiolD","UN","UNC","UNC_ChemD","UTHSCH_UMG_MDACC_RU_UTA","UT_UMG_MDACC_RU_UTA","UToronto","VCU_VT_EHC","WSU","WSU_DPS","WUSTL","WUSTL_DIM","WU_SU_CHINA","YALE","my org","natcompany","other","shanyang","shanyangcomp"],"contactRoles":["Chris Contact Role","investigator","manufacturer"],"allGroupNames":["Carbon Tube Group","Demo University","Demo-Collaboration","NCI group","NCNHIR","Test Collaboration"],"filteredUsers":{"lethai":"Le, Thai","Omelchen":"Omelchenko, Marina","burnskd":"Burns, Kevin","canano_guest":"Guest, Guest","grodzinp":"Grodzinski, Piotr","swand":"Swan, Don","skoczens":"Skoczen, Sarah","sternstephan":"Stern, Stephan","zolnik":"Zolnik, Banu","canano_res":"Researcher, CaNano","hunseckerk":"Hunsecker, Kelly","lipkeyfg":"Lipkey, Foster","marina":"Dobrovolskaia, Marina","pottert":"Potter, Tim","uckunf":"Uckun, Fatih","michal":"Lijowski, Michal","mcneils":"Mcneil, Scott","neunb":"Neun, Barry","cristr":"Crist, Rachael","zhengji":"Zheng, Jiwen","SchaeferH":"Schaefer, Henry","frittsmj":"Fritts, Martin","benhamm":"Benham, Mick","masoods":"Masood, Sana","mclelandc":"McLeland, Chris","torresdh":"Torres, David","KlemmJ":"Klemm, Juli","patria":"Patri, Anil","hughesbr":"Hughes, Brian","clogstonj":"Clogston, Jeff","hinkalgw":"Hinkal, George","MorrisS2":"Morris, Stephanie","sharon":"Gaheen, Sharon"},"roleNames":{"R":"read","CURD":"read update delete"},"showReviewButton":false,"errors":[]}];
        };
        if($scope.sampleId.data == '3735561' ) {
            //Has three points of contact.  Data seems to be in error because there should be only one primary contact.  All are primary
            hardCodedSampleData = [{"sampleName":"WUSTL-JMarshIEEEUS2000-01","sampleId":3735561,"userIsCurator":true,"pointOfContacts":[{"id":15695884,"contactPerson":"","organization":{"id":11796482,"name":"WUSTL"},"role":"investigator","address":{"line1":"","line2":"","city":"Saint Louis","stateProvince":"MO","zip":"","country":"USA"},"firstName":"Jon","lastName":"Marsh","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":15695945,"contactPerson":"","organization":{"id":11796482,"name":"WUSTL"},"role":"investigator","address":{"line1":"","line2":"","city":"Saint Louis","stateProvince":"MO","zip":"","country":"USA"},"firstName":"Samuel","lastName":"Wickline","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true},{"id":15695949,"contactPerson":"","organization":{"id":11796482,"name":"WUSTL"},"role":"investigator","address":{"line1":"","line2":"","city":"Saint Louis","stateProvince":"MO","zip":"","country":"USA"},"firstName":"Gregory","lastName":"Lanza","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true}],"keywords":["ACOUSTIC MICROSCOPY","AVIDIN","BIOTIN","FIBRIN CLOTS","LIPID MONOLAYER","PERFLUOROCARBON","THROMBIN","ULTRASOUND"],"accessToSample":{"userAccesses":[],"groupAccesses":[{"groupName":"Curator","roleName":"","roleDisplayName":"read update delete","loginName":"","accessBy":"","sampleId":3735561},{"groupName":"Public","roleName":"","roleDisplayName":"read","loginName":"","accessBy":"","sampleId":3735561}]},"dataAvailability":{"sampleName":"WUSTL-JMarshIEEEUS2000-01","dataAvailability":"caNanoLab: 30%; MINChar: 33%","caNanoLabScore":"30.0% (9 out of 30)","mincharScore":"33.0% (3 out of 9)","chemicalAssocs":["attachment","encapsulation","entrapment"],"physicoChars":["molecular weight","physical state","purity","relaxivity","shape","size","solubility","surface"],"invitroChars":["blood contact","cytotoxicity","enzyme induction","immune cell function","metabolic stability","oxidative stress","sterility","targeting","transfection"],"invivoChars":["pharmacokinetics","toxicology"],"availableEntityNames":["sample function","functionalizing entities","general sample information","publications","attachment","sample composition","size","encapsulation","nanomaterial entities"],"caNano2MINChar":{"zeta potential":"surface charge","sample composition":"chemical composition","purity":"purity","surface charge":"surface charge","shape":"shape","attachment":"surface chemistry","surface area":"surface area","size":"particle size/size distribution"}},"organizationNamesForUser":["AIST_HTRC","BB_SH_DFCI_WCMC_BWH_MIT","BB_SH_KCI_DFCI_WCMC_BWH_MIT","BROWN_STANFORD","BWH_AnesthesiologyD","C-Sixty (CNI)","CAS_VT_VCU","CLM_UHA_CDS_INSERM","CP_UCLA_CalTech","CWRU","Childrens Hospital Los Angeles","Chris","DC","DNT","DWU_SNU_SU_US","GATECH","GATECH_EMORY","GATECH_UCSF","GIST_LifeScienceD","HarvardU_PhysD","Harvard_MIT_DHST","JHU_KSU","JSTA_FHU_NEC_MU_AIST_JAPAN","JSTA_JFCR_NEC_FHU_TUSM_NIAIST","JST_AIST_FHU_TU_NEC_MU_JAPAN","Joe Barchi","KI","KU_JSTC_JAPAN","LMRT","MIT_ChemD","MIT_ChemEngineeringD","MIT_LMRT","MIT_MGH","MIT_MGH_GIST","MIT_UC_BBIC_HST_CU","MSKCC_CU_UA","Mansoor Amiji","Mark Kester","Mark Kester PSU","NCL","NEU","NEU_DPS","NEU_MGH_UP_FCCC","NEU_MIT_MGH","NIEHS","NIOSH","NRCWE","NWU","NWU_ChemD_IIN","Nanotechnology Characterization Laboratory","Nanotechnology_Characterization_Laboratory","New One","PNNL_CBBG","PURDUE","PURDUEU_BN","RIT_KI_SU","SNL_UNM","SNU_CHINA","STANFORD","STANFORD_ChemD","STANFORD_MIPS","STANFORD_OM","SUNYB_ILPB","SY Org","SY company","Sharon","TAM_UT","TTU","UAM_CSIC_IMDEA","UCSD_ChemBiochemD","UCSD_MIT_MGH","UC_HU_UEN_GERMANY","UI","UKY","UL_NL","UM","UM-C","UMC_DVP","UMC_RadiolD","UN","UNC","UNC_ChemD","UTHSCH_UMG_MDACC_RU_UTA","UT_UMG_MDACC_RU_UTA","UToronto","VCU_VT_EHC","WSU","WSU_DPS","WUSTL","WUSTL_DIM","WU_SU_CHINA","YALE","my org","natcompany","other","shanyang","shanyangcomp"],"contactRoles":["Chris Contact Role","investigator","manufacturer"],"allGroupNames":["Carbon Tube Group","Demo University","Demo-Collaboration","NCI group","NCNHIR","Test Collaboration"],"filteredUsers":{"lethai":"Le, Thai","Omelchen":"Omelchenko, Marina","burnskd":"Burns, Kevin","canano_guest":"Guest, Guest","grodzinp":"Grodzinski, Piotr","swand":"Swan, Don","skoczens":"Skoczen, Sarah","sternstephan":"Stern, Stephan","zolnik":"Zolnik, Banu","canano_res":"Researcher, CaNano","hunseckerk":"Hunsecker, Kelly","lipkeyfg":"Lipkey, Foster","marina":"Dobrovolskaia, Marina","pottert":"Potter, Tim","uckunf":"Uckun, Fatih","michal":"Lijowski, Michal","mcneils":"Mcneil, Scott","neunb":"Neun, Barry","cristr":"Crist, Rachael","zhengji":"Zheng, Jiwen","SchaeferH":"Schaefer, Henry","frittsmj":"Fritts, Martin","benhamm":"Benham, Mick","masoods":"Masood, Sana","mclelandc":"McLeland, Chris","torresdh":"Torres, David","KlemmJ":"Klemm, Juli","patria":"Patri, Anil","hughesbr":"Hughes, Brian","clogstonj":"Clogston, Jeff","hinkalgw":"Hinkal, George","MorrisS2":"Morris, Stephanie","sharon":"Gaheen, Sharon"},"roleNames":{"R":"read","CURD":"read update delete"},"showReviewButton":false,"errors":[]}];
        };
        if($scope.sampleId.data == '38404097' ) {
            hardCodedSampleData = [{"sampleName":"YALE_HHMI-JParkNatureMat2012-06","sampleId":38404097,"userIsCurator":true,"pointOfContacts":[{"id":37945344,"contactPerson":"","organization":{"id":37978112,"name":"YALE"},"role":"investigator","address":{"line1":"Department of Biomedical and Environmental Engineering","line2":"Yale University School of Engineering and Applied Sciences","city":"New Haven","stateProvince":"CT","zip":"06511","country":"USA"},"firstName":"Tarek","lastName":"Fahmy","middleInitial":"","phoneNumber":"","email":"","sampleId":0,"primaryContact":true}],"keywords":["PHOTOINDUCED POLYMERIZATION"],"accessToSample":{"userAccesses":[],"groupAccesses":[{"groupName":"Curator","roleName":"","roleDisplayName":"read update delete","loginName":"","accessBy":"","sampleId":38404097},{"groupName":"Public","roleName":"","roleDisplayName":"read","loginName":"","accessBy":"","sampleId":38404097}]},"dataAvailability":{"sampleName":"YALE_HHMI-JParkNatureMat2012-06","dataAvailability":"caNanoLab: 26%; MINChar: 11%","caNanoLabScore":"26.0% (8 out of 30)","mincharScore":"11.0% (1 out of 9)","chemicalAssocs":["attachment","encapsulation","entrapment"],"physicoChars":["molecular weight","physical state","purity","relaxivity","shape","size","solubility","surface"],"invitroChars":["blood contact","cytotoxicity","enzyme induction","immune cell function","metabolic stability","oxidative stress","sterility","targeting","transfection"],"invivoChars":["pharmacokinetics","toxicology"],"availableEntityNames":["sample composition","publications","encapsulation","nanomaterial entities","functionalizing entities","pharmacokinetics","general sample information","sample function"],"caNano2MINChar":{"zeta potential":"surface charge","sample composition":"chemical composition","purity":"purity","surface charge":"surface charge","shape":"shape","attachment":"surface chemistry","surface area":"surface area","size":"particle size/size distribution"}},"organizationNamesForUser":["AIST_HTRC","BB_SH_DFCI_WCMC_BWH_MIT","BB_SH_KCI_DFCI_WCMC_BWH_MIT","BROWN_STANFORD","BWH_AnesthesiologyD","C-Sixty (CNI)","CAS_VT_VCU","CLM_UHA_CDS_INSERM","CP_UCLA_CalTech","CWRU","Childrens Hospital Los Angeles","Chris","DC","DNT","DWU_SNU_SU_US","GATECH","GATECH_EMORY","GATECH_UCSF","GIST_LifeScienceD","HarvardU_PhysD","Harvard_MIT_DHST","JHU_KSU","JSTA_FHU_NEC_MU_AIST_JAPAN","JSTA_JFCR_NEC_FHU_TUSM_NIAIST","JST_AIST_FHU_TU_NEC_MU_JAPAN","Joe Barchi","KI","KU_JSTC_JAPAN","LMRT","MIT_ChemD","MIT_ChemEngineeringD","MIT_LMRT","MIT_MGH","MIT_MGH_GIST","MIT_UC_BBIC_HST_CU","MSKCC_CU_UA","Mansoor Amiji","Mark Kester","Mark Kester PSU","NCL","NEU","NEU_DPS","NEU_MGH_UP_FCCC","NEU_MIT_MGH","NIEHS","NIOSH","NRCWE","NWU","NWU_ChemD_IIN","Nanotechnology Characterization Laboratory","Nanotechnology_Characterization_Laboratory","New One","PNNL_CBBG","PURDUE","PURDUEU_BN","RIT_KI_SU","SNL_UNM","SNU_CHINA","STANFORD","STANFORD_ChemD","STANFORD_MIPS","STANFORD_OM","SUNYB_ILPB","SY Org","SY company","Sharon","TAM_UT","TTU","UAM_CSIC_IMDEA","UCSD_ChemBiochemD","UCSD_MIT_MGH","UC_HU_UEN_GERMANY","UI","UKY","UL_NL","UM","UM-C","UMC_DVP","UMC_RadiolD","UN","UNC","UNC_ChemD","UTHSCH_UMG_MDACC_RU_UTA","UT_UMG_MDACC_RU_UTA","UToronto","VCU_VT_EHC","WSU","WSU_DPS","WUSTL","WUSTL_DIM","WU_SU_CHINA","YALE","my org","natcompany","other","shanyang","shanyangcomp"],"contactRoles":["Chris Contact Role","investigator","manufacturer"],"allGroupNames":["Carbon Tube Group","Demo University","Demo-Collaboration","NCI group","NCNHIR","Test Collaboration"],"filteredUsers":{"lethai":"Le, Thai","Omelchen":"Omelchenko, Marina","burnskd":"Burns, Kevin","canano_guest":"Guest, Guest","grodzinp":"Grodzinski, Piotr","swand":"Swan, Don","skoczens":"Skoczen, Sarah","sternstephan":"Stern, Stephan","zolnik":"Zolnik, Banu","canano_res":"Researcher, CaNano","hunseckerk":"Hunsecker, Kelly","lipkeyfg":"Lipkey, Foster","marina":"Dobrovolskaia, Marina","pottert":"Potter, Tim","uckunf":"Uckun, Fatih","michal":"Lijowski, Michal","mcneils":"Mcneil, Scott","neunb":"Neun, Barry","cristr":"Crist, Rachael","zhengji":"Zheng, Jiwen","SchaeferH":"Schaefer, Henry","frittsmj":"Fritts, Martin","benhamm":"Benham, Mick","masoods":"Masood, Sana","mclelandc":"McLeland, Chris","torresdh":"Torres, David","KlemmJ":"Klemm, Juli","patria":"Patri, Anil","hughesbr":"Hughes, Brian","clogstonj":"Clogston, Jeff","hinkalgw":"Hinkal, George","MorrisS2":"Morris, Stephanie","sharon":"Gaheen, Sharon"},"roleNames":{"R":"read","CURD":"read update delete"},"showReviewButton":false,"errors":[]}];
        };
        */
        $scope.sampleData = hardCodedSampleData[0];
        $scope.loader = false;
        $scope.editSampleForm = true;

    } else {
        /* if hostname fetch the data */
        /* REST: http://localhost:8080/caNanoLab/rest/sample/edit?sampleId=20917507 */
        //$http({method: 'GET', url: '/caNanoLab/rest/sample/edit?sampleId=20917507'+$scope.sampleId.data}).
    	//alert("What is the sampleId?");
        $http({method: 'GET', url: 'http://localhost:8080/caNanoLab/rest/sample/edit?sampleId='+$scope.sampleId.data}).
            success(function(data, status, headers, config, statusText) {
                $scope.sampleData = data;
                $scope.loader = false;
                $scope.editSampleForm = true;
            }).
            error(function(data, status, headers, config, statusText) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                if(status != 200){
                    $scope.message = "Response code " + status.toString() + ":  " + statusText;
                    console.log($scope.message);
                    console.dir(data);
                } else {
                    $scope.message = data;
                }
                $scope.loader = false;
                $scope.submissionView = false;
        });
    }

// * Page Change Events *
//Add keyword
    $scope.addKeyword=function(){
        if($scope.newKeyword.length > 1) {
            $scope.sampleData.keywords.push($scope.newKeyword.toUpperCase());
            $scope.newKeyword = "";
            $scope.scratchPad.editSampleData.dirty = true;
        }
    };
    $scope.removeKeyword = function(item) {
        var index = $scope.sampleData.keywords.indexOf(item)
        $scope.sampleData.keywords.splice(index, 1);
        $scope.scratchPad.editSampleData.dirty = true;
    }

    $scope.changedSampleName = function() {
        $scope.scratchPad.editSampleData.dirty = true;
    };

// * editSample Button Bar
    $scope.delete = function() {

    };
    $scope.copy = function() {
        //Submit a copy of $root.master with the samplName changed
        $modal.open({
            templateUrl: 'views/sample/edit/modal/copySample.html',
            backdrop: true,
            windowClass: 'modal',
            controller: function ($scope, $modalInstance, sampleName) {
                $scope.copyOfSampleData = angular.copy($scope.master);
                $scope.copyOfSampleData.sampleName = sampleName;
                $scope.submit = function () {
                    console.log('Submiting user info.');
                    console.log(user);
                    $modalInstance.dismiss('cancel');
                }
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            },
            resolve: {
                copyOfSampleData: function () {
                    return $scope.copyOfSampleData;
                }
            }
        });
    };
    $scope.reset = function() {
         $scope.sampleData = angular.copy($scope.master);
        //$location.path("/editSample").replace();
        //$location.search('sampleId', $scope.sampleId);
    };
    $scope.update = function() {

    };

// Modal for Access To Sample (1)
    $scope.openPointOfContactModal = function(sampleData) {
        alert("You did it.  You click on a button.");
        var modalInstance = $modal.open({
            templateUrl: 'views/sample/edit/modal/accessToSampleModal.html',
            controller: 'AccessToSampleModalCtrl',
            size: 'sm',
            resolve: {
          }
        });
    };

// Modal for Access To Sample (2)
    $scope.openAccessToSampleModal = function(sampleId) {
        alert("You did it.  You click on a button.");
        var modalInstance = $modal.open({
            templateUrl: 'views/sample/edit/modal/accessToSampleModal.html',
            controller: 'keywordModalCtrl',
            size: 'sm',
            resolve: {
            sampleId: function () {
              return sampleId;
            },
            sampleData: function() {
              return data;
            }
          }
        });
    };

// Modal for Data Availability (3)
    $scope.openDataAvailability = function(sampleId) {

          $http({method: 'GET', url: '/caNanoLab/rest/sample/viewDataAvailability',params: {"sampleId":sampleId}}).
          success(function(data, status, headers, config) {
            var modalInstance = $modal.open({
              templateUrl: 'views/sampleDataAvailability.html',
              controller: 'SampleDataAvailabilityCtrl',
              size: 'sm',
              resolve: {
                sampleId: function () {
                  return sampleId;
                },
                sampleData: function() {
                  return data;
                }
              }
            });
          }).
          error(function(data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $scope.message = data;
          });
    };

	$scope.submitData = function(isValid) {
		$scope.submitted = true;
		if (isValid) {
			//$http({method: 'POST', url: '/caNanoLab/rest/security/register', data: {"title":$scope.title,"firstName":$scope.firstName,"lastName":$scope.lastName,"email":$scope.email,"phone":$scope.phone,"organization":$scope.organization,"fax":$scope.fax,"comment":$scope.comment,"registerToUserList":$scope.registerToUserList}, headers: {'Content-Type':'application/json'}}).
	    	  $http({method: 'GET', url: '/caNanoLab/rest/security/register', params: {"title":$scope.title,"firstName":$scope.firstName,"lastName":$scope.lastName,"email":$scope.email,"phone":$scope.phone,"organization":$scope.organization,"fax":$scope.fax,"comment":$scope.comment,"registerToUserList":$scope.registerToUserList}}).
			    success(function(data, status, headers, config) {
			      // this callback will be called asynchronously
			      // when the response is available
			    	$scope.message="Your registration request has been sent to the administrator for assignment of your User ID and Password. You should receive this information via e-mail within one business day from time of submission.";
			    }).
			    error(function(data, status, headers, config) {
			      // called asynchronously if an error occurs
			      // or server returns response with an error status.
			    	$scope.message=data;
	    	});
		};
	};
    $scope.master = angular.copy($scope.sampleData);
    $scope.reset();
});