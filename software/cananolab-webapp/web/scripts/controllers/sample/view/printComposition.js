'use strict';
var app = angular.module('angularApp', [
        'ngRoute', 'ngSanitize', 'ngRoute'
    ])

app.controller('PrintCompositionCtrl', function (utilsService, $rootScope, $scope, $http, $filter, $routeParams, sampleService) {
        $scope.sampleId = utilsService.getParameterFromURL('sampleId');

        $scope.loader = true;
        $http({
            method: 'GET',
            url: '/caNanoLab/rest/composition/summaryPrint?sampleId=' + $scope.sampleId
        }).
        then(function (data, status, headers, config) {
            data = data['data']
            //data = '{"compositionSections":["nanomaterial entity","functionalizing entity","chemical association","composition file"],"sampleName":"NCL-23-1","nanomaterialentity":{"polymer":{"Description":"Poly 1","Files":[],"ComposingElements":[{"Description":"111","Function":null,"MolecularFormulaDisplayName":"123 (SMARTS)","DisplayName":"lipid (name: lipid 1)","PubChemId":null,"PubChemLink":null,"PubChemDataSourceName":""}],"isWithProperties":true,"detailsPage":"views/sample/composition/nanomaterialEntity/PolymerInfo.html","Properties":{"Initiator":"1","isCrossLinked":true,"CrossLinkDegree":25.0}},"liposome":{"Description":"lipo","Files":[],"ComposingElements":[],"isWithProperties":true,"detailsPage":"views/sample/composition/nanomaterialEntity/LiposomeInfo.html","Properties":{"PolymerName":"poly 2","IsPolymarized":true}},"fullerene":{"Description":"full","Files":[],"ComposingElements":[],"isWithProperties":true,"detailsPage":"views/sample/composition/nanomaterialEntity/FullereneInfo.html","Properties":{"AverageDiameterUnit":" nm","AverageDiameter":3.0,"NoOfCarbons":23}},"metal particle":{"Description":"metal","Files":[],"ComposingElements":[]},"carbon":{"Description":"Carbon","Files":[],"ComposingElements":[{"Description":"","Function":["endosomolysis"],"MolecularFormulaDisplayName":"","DisplayName":"coat (name: Coat)","PubChemId":null,"PubChemLink":null,"PubChemDataSourceName":""}]},"dendrimer":{"Description":"G4.5 COONa terminated PAMAM dendrimer-Magnevist&#195;???&#195;??&#195;?&#194;&#174; complex","Files":[{"fileId":24947968,"ExternalURI":"false","Description":"NCL23 is a generation 4.5 (G4.5) COONa terminated dendrimer with associated MagnevistÃ‚Â® in a dendrimer-MagnevistÃ‚Â® complex.","KeywordStr":"","isImage":true,"KeyWordDisplayName":"","Title":"NCL23 Composition Schematic","URI":"particles/NCL-23-1/nanoparticleEntity/20080620_13-43-16-506_NCL23 Composition.png"}],"ComposingElements":[{"Description":"","Function":null,"MolecularFormulaDisplayName":"","DisplayName":"core (name: Diaminobutane (DAB))","PubChemId":null,"PubChemLink":null,"PubChemDataSourceName":null},{"Description":"Intern Buf","Function":null,"MolecularFormulaDisplayName":"","DisplayName":"Internal buffer (name: Chemical)","PubChemId":null,"PubChemLink":null,"PubChemDataSourceName":""}],"isWithProperties":true,"detailsPage":"views/sample/composition/nanomaterialEntity/DendrimerInfo.html","Properties":{"Branch":"1-4","Generation":4.5}},"nanohorn":{"Description":"nano h","Files":[],"ComposingElements":[]},"quantum dot":{"Description":"quantum","Files":[],"ComposingElements":[]},"carbon nanotube":{"Description":"cadrbon nano","Files":[],"ComposingElements":[{"Description":"","Function":["endosomolysis: endo"],"MolecularFormulaDisplayName":"","DisplayName":"monomer (name: mono)","PubChemId":null,"PubChemLink":null,"PubChemDataSourceName":""}],"isWithProperties":true,"detailsPage":"views/sample/composition/nanomaterialEntity/CarbonNanotubeInfo.html","Properties":{"Diameter":2.0,"AverageLength":1.0,"chirality":"1","AverageLengthUnit":" nm","WallType":"DWNT","DiameterUnit":"um"}},"emulsion":{"Description":"emul","Files":[],"ComposingElements":[{"Description":"","Function":null,"MolecularFormulaDisplayName":"","DisplayName":"excipient (name: exc)","PubChemId":null,"PubChemLink":null,"PubChemDataSourceName":""}],"isWithProperties":true,"detailsPage":"views/sample/composition/nanomaterialEntity/EmulsionInfo.html","Properties":{"PolymerName":"poly 1","IsPolymarized":true}},"biopolymer":{"Description":"bio poly","Files":[],"ComposingElements":[{"Description":"","Function":null,"MolecularFormulaDisplayName":"","DisplayName":"modifier (name: modi)","PubChemId":null,"PubChemLink":null,"PubChemDataSourceName":""}],"isWithProperties":true,"detailsPage":"views/sample/composition/nanomaterialEntity/BiopolymerInfo.html","Properties":{"Name":"2","Type":"DNA","Sequence":"2"}}},"composingElements":[],"functionsList":[{"Type":"imaging function","FunctionDescription":"","withImagingFunction":true,"withTargettingFunction":false,"FunctionDescriptionDisplayName":"","Modality":"","TargetDisplayNames":null}],"functionalizingentity":{"antibody":{"Value":null,"ActivationMethod":"enzymatic cleavage, nothing","pubChemID":null,"ValueUnit":"","MolecularFormula":"","detailsPage":"views/sample/composition/functionalizingEntity/AntibodyInfo.html","pubChemDS":"","Properties":{"Type":"ScFv","IsoType":"IgD","Species":"goat"},"Name":"anti body chem name","Functions":[{"Type":"imaging function","FunctionDescription":"desc","withImagingFunction":true,"withTargettingFunction":false,"FunctionDescriptionDisplayName":"desc","Modality":"gamma ray","TargetDisplayNames":null}],"pubChemLink":null,"Files":[],"Decription":"","Desc":""},"small molecule":{"Value":null,"ActivationMethod":"","pubChemID":null,"ValueUnit":"","MolecularFormula":"","detailsPage":"views/sample/composition/functionalizingEntity/SmallMoleculeInfo.html","pubChemDS":null,"Properties":{"AlternateName":"Magnevist","isWithProperties":true},"Name":"Magnevist","Functions":[{"Type":"imaging function","FunctionDescription":"","withImagingFunction":true,"withTargettingFunction":false,"FunctionDescriptionDisplayName":"","Modality":"","TargetDisplayNames":null}],"pubChemLink":null,"Files":[{"fileId":24947969,"ExternalURI":"false","Description":"Chemical structure of Magnevist","KeywordStr":"","isImage":true,"KeyWordDisplayName":"","Title":"Chemical Structure of Magnevist","URI":"particles/NCL-23-1/functionalizingEntity/20080620_14-01-47-504_Magnevist.png"}],"Decription":"","Desc":""},"biopolymer":{"Value":null,"ActivationMethod":"","pubChemID":null,"ValueUnit":"","MolecularFormula":"","detailsPage":"views/sample/composition/functionalizingEntity/BiopolymerInfo.html","pubChemDS":"","Properties":{"Type":"peptide","Sequence":"seq"},"Name":"bio poly","Functions":[{"Type":"targeting function","FunctionDescription":"target","withImagingFunction":false,"withTargettingFunction":true,"FunctionDescriptionDisplayName":"target","Modality":null,"TargetDisplayNames":["antigen targ: targ desc, species: horse"]}],"pubChemLink":null,"Files":[],"Decription":"","Desc":""}},"chemicalassociation":{"encapsulation":{"Description":"encap","BondType":null,"AttachmentId":null,"AssocitedElements":{"CompositiontypeB":"functionalizing entity","CompositiontypeA":"nanomaterial entity","DomainElementNameA":"mono","DomainElementNameB":"anti body chem name","ComposingElementTypeA":"monomer","ComposingElementTypeB":null,"DomainAssociationId":43352064,"EntityDisplayNameA":"carbon nanotube","ComposingElemetIdB":null,"ComposingElemetIdA":43155458,"EntityDisplayNameB":"antibody","ComposingElementNameB":null,"ComposingElementNameA":"mono"}}},"compositionfile":{"image":[{"fileId":24947970,"ExternalURI":"false","Description":null,"KeywordStr":"","isImage":true,"KeyWordDisplayName":"","Title":"Schematic illustration of NCL23 composition","URI":"particles/NCL-23-1/compositionFile/20080620_14-02-46-861_NCL23 Composition.png"}]}}';
            $scope.compositionSections = data.compositionSections;
            $scope.nanomaterialentity = data.nanomaterialentity;
            $scope.functionalizingentity = data.functionalizingentity;
            $scope.chemicalassociation = data.chemicalassociation;
            $scope.compositionfile = data.compositionfile;
            $scope.sampleName = $routeParams.sampleName;

            $scope.loader = false;

            $scope.nanomaterialentityEmpty = utilsService.isHashEmpty(data.nanomaterialentity);
            $scope.functionalizingentityEmpty = utilsService.isHashEmpty(data.functionalizingentity);
            $scope.chemicalassociationEmpty = utilsService.isHashEmpty($scope.chemicalassociation);
            $scope.compositionfileEmpty = utilsService.isHashEmpty(data.compositionfile);

        }).
        catch(function (data, status, headers, config) {
            data = data['data']
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $scope.message = data;
            $scope.loader = false;

        });
    });

    app.filter('newlines', function () {
        return function (text) {
            if (text && typeof (text) == 'string') {
            return text.replace(/\n/g, '<br/>').replace(/&amp;apos;/g, "'").replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"')
            }
            return '';
        }
        });