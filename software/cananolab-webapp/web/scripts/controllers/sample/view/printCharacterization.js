'use strict';
var app = angular.module('angularApp', [
    'ngRoute', 'ngSanitize', 'ngRoute'
  ])

  app.controller('PrintCharacterizationCtrl', function (utilsService, $rootScope, $scope, $http, $location, $filter, sampleService) {
    // $scope.sampleId = $routeParams.sampleId;
    // Displays left hand nav for samples section. navTree shows nav and navDetail is page index //
    $scope.sampleId = utilsService.getParameterFromURL('sampleId');

    // $scope.data=[{"type":"physico-chemical characterization","charsByAssayType":{"molecular weight":[[{"name":"Assay Type","value":"molecular weight"},{"name":"Point of Contact","value":"DNT"},{"name":"Experiment Configurations","value":[{"Technique":["size exclusion chromatography with multi-angle laser light scattering(SEC-MALLS)"]},{"Instruments":[""]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"DNT NCL200612A Fig 24","fileId":21900545,"description":"SEC chromatograms of NCL23 using two different columns. The molar mass distribution plot for NCL23 using 2 different size exclusion columns is shown.  NCL23 elutes faster using the G3000 column.  The calculated molar mass for NCL23 was 24.17 kDa and 24.62 kDa, and the polydispersity index was 1.003 and 1.032 using the G3000 and G4000 columns, respectively. The molecular weight determined by SEC-MALLS for NCL23 is very similar to that of NCL22. Since NCL23 is NCL22 with incorporated Magnevist (noncovalent inclusion complex), the results suggest that Magnevist is no longer incorporated with the dendrimer after fractionation"}],"Data and Conditions":[{"name":"colTitles","value":["molecular weight<br>(observed,kDa)","PDI<br>(observed)"]},{"name":"colValues","value":["24.17","1.003"]}]},{"Files":[{"imageTitle":"DNT NCL200612A Fig 24","fileId":21900546,"description":"SEC chromatograms of NCL23 using two different columns. The molar mass distribution plot for NCL23 using 2 different size exclusion columns is shown.  NCL23 elutes faster using the G3000 column.  The calculated molar mass for NCL23 was 24.17 kDa and 24.62 kDa, and the polydispersity index was 1.003 and 1.032 using the G3000 and G4000 columns, respectively. The molecular weight determined by SEC-MALLS for NCL23 is very similar to that of NCL22. Since NCL23 is NCL22 with incorporated Magnevist (noncovalent inclusion complex), the results suggest that Magnevist is no longer incorporated with the dendrimer after fractionation"}],"Data and Conditions":[{"name":"colTitles","value":["molecular weight<br>(observed,kDa)","PDI<br>(observed)"]},{"name":"colValues","value":["24.62","1.032"]}]}]},{"name":"Analysis and Conclusion","value":"The molecular weight determined by SEC-MALLS for NCL23 is very similar to that of NCL22.  Since NCL23 is NCL22 with associated Magnevist, the results suggest that Magnevist is no longer associated with the dendrimer after fractionation."}],[{"name":"Assay Type","value":"molecular weight"},{"name":"Point of Contact","value":"DNT"},{"name":"Experiment Configurations","value":[{"Technique":["asymmetrical flow field-flow fractionation with multi-angle laser light scattering(AFFF-MALLS)"]},{"Instruments":[""]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 27","fileId":21900547,"description":"Molar mass versus elution time plot of NCL22 and NCL23 by AFFF-MALLS. Concentration of NCL22: 1 mg/mL in H O; concentration of NCL23: 2 mg/mL in PBS; Conditions: Injection volume: 100 uL; 10kDa regenerated cellulose membrane; 350 um channel thickness; 1 mL/min channel flow; 3 mL/min cross-flow. AFFF is an innovative separation method for an efficient separation and characterization of nanoparticles, polymers, and proteins that is both fast and gentle.  When coupled with a MALLS system, the molar mass and rms radius can be obtained for the fractionated sample.  The molar mass distribution plot shows that NCL22 and NCL23 have similar molar mass by using AFFF as separation method.  The calculated molar mass of NCL22 and NCL23 was 21.63 kDa and 20.74 kDa, and the polydipersity index was 1.046 and 1.078, respectively (the molar mass of both NCL22 and NCL23 was determined by using the dn/dc value of NCL22, which was measured using an RI detector)."}],"Data and Conditions":[{"name":"colTitles","value":["sample concentration<br>(observed,mg/mL)","molecular weight<br>(observed,kDa)","solvent media<br>(observed)","PDI<br>(observed)"]},{"name":"colValues","value":["2","20.74","PBS","1.078"]}]}]},{"name":"Analysis and Conclusion","value":"NCL23 and NCL22 have a similar molar mass by using AFFF as a separation method.  NCL23 is NCL22 with associated Magnevist."}],[{"name":"Assay Type","value":"molecular weight"},{"name":"Experiment Configurations","value":[{"Technique":["matrix assisted laser desorption ionisation - time of flight(MALDI-TOF)"]},{"Instruments":[""]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"","fileId":21900544,"description":"Mass spectra for (A) NCL 22 and (B) NCL23.  The theoretical molecular weight of NCL22 is 26.28 kDa.  The actual/experimental result based on MS was 22 kDa for both samples.  The experimental details are as follows: DHB matrix, 10 mg/mL. CH CN/H O = 3/7 (v/v).  Molecular weight spectra obtained by MALDI-TOF, waith a major peak at 22 kDa and minor peaks centered around 43 kDa and 64 kDa, are consistent with the information provided by DNT for G4.5 NaCOO dendrimer samples NCL22 and NCL23. Incorporating Magnevist did not change the spectrum of NCL23."}],"Data and Conditions":[{"name":"colTitles","value":["molecular weight<br>(observed,kDa)"]},{"name":"colValues","value":["22.0"]}]}]},{"name":"Analysis and Conclusion","value":"Association with Magnevist did not change the spectrum of NCL23."}]],"physical state":[[{"name":"Assay Type","value":"physical state"},{"name":"Point of Contact","value":"DNT"},{"name":"Properties","value":"solid-powder"},{"name":"Design Description","value":"DNT 082006"},{"name":"Characterization Results","value":[]}]],"purity":[[{"name":"Assay Type","value":"purity"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"DNT 082006 (HPLC)"},{"name":"Experiment Configurations","value":[{"Technique":["high performance liquid chromatography(HPLC)"]},{"Instruments":[""]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 17","fileId":21900548,"description":"HPLC chromatogram for NCL23. The chromatogram and elution profile for NCL23 are shown.  The retention time for the main peak is 38.0 min and corresponds to 79% +- 1% of the total area (based on peaks eluted between 36.9 and 39.9 min). The UV spectra (data not shown) for these peaks are consistent with that of NCL23 measured with a UV-Vis spectrophotometer. The nature of the broad peak at 47.1 min is unknown."}]}]}],[{"name":"Assay Type","value":"purity"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"DNT 082006 (CE)"},{"name":"Experiment Configurations","value":[{"Technique":["capillary electrophoresis"]},{"Instruments":[""]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 22","fileId":21900549,"description":"Typical electropherogram of (A) NCL22 and (B) NCL23. Sample concentration: 0.1 mg/mL in water; capillary: 40 cm x 50 um I.D; buffer: 20 mM sodium phosphate (ph = 7.4); separation voltage: -14kV; injection pressure: 0.5 psi/20s; detector: UV (wavelength 200 nm). CE is a powerful chromatorgraphic technique that separates analytes on the basis of electrophoretic mobility differences.  Mobility is determined by the mass-to-charge ratio of the analyte.  CE has high separation efficiencies, high sensitivity, short run time and high automation capability.  DE is extensively used to evaluate the molecular distribution of dendirmers, since the charge distibution and electrophoretic mobility often change upon dendirmer surface conjugation.  Figure 22 shows that NCL22 and NCL23 have very similar electrophoretic mobilities (both are in the range of [4.3-5.2] x 10 cm V S), which indicates they have the same charge/mass ratio."}]}]},{"name":"Analysis and Conclusion","value":"NCL23 has very similar electrophoretic mobilities to NCL22, which indicates that they have the same charge/mass ratio.  NCL23 is NCL22 with Magnevist."}],[{"name":"Assay Type","value":"purity"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"DNT 122006 UV-Vis"},{"name":"Experiment Configurations","value":[{"Technique":["spectrophotometry"]},{"Instruments":["[Ljava.lang.String;@5a72f1e9"]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"fileId":23801100,"title":"UV-Vis spectra for the dendrimers studied.","description":"UV-Vis spectra were recorded using a Thermo Electron Evolution 300 spectrophotometer (Waltham, MA). Samples were prepared in HPLC-grade water and measured in quartz microcuvettes (b = 10 mm, QS109.004, Hellma, Plainview, NY). The UV-Vis spectra are consistent with the dendrimers in that no absorption above 230 nm was observed."}]}]},{"name":"Analysis and Conclusion","value":"The UV-Vis sprectra are consistent with the dendrimers in that no absorption above 230 nm was observed."}]],"size":[[{"name":"Assay Type","value":"size"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"The effect of size based on 25 degree Celsius and Saline solvent"},{"name":"Experiment Configurations","value":[{"Technique":["dynamic light scattering(DLS)"]},{"Instruments":["[Ljava.lang.String;@30682663"]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 4","fileId":21540118,"description":"Statistics graph based on size distribution by volume for NCL23 in saline at 25 degrees Celsius"}],"Data and Conditions":[{"name":"colTitles","value":["PDI<br>(observed)","size<br>(Z-average,nm)","temperature<br>(observed,Celsius)","solvent media<br>(observed)","peak1<br>(observed,nm)"]},{"name":"colValues","value":["0.235","7.4","25","saline","5.3"]}]}]}],[{"name":"Assay Type","value":"size"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"The effect of size based on 25 degree Celsius and PBS solvent"},{"name":"Experiment Configurations","value":[{"Technique":["dynamic light scattering(DLS)"]},{"Instruments":["[Ljava.lang.String;@5b04669"]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 5","fileId":21540119,"description":"Statistics graph based on size distribution by volume for NCL23 in PBS at 37 degrees Celsius"}],"Data and Conditions":[{"name":"colTitles","value":["peak1<br>(observed,nm)","size<br>(Z-average,nm)","temperature<br>(observed,Celsius)","solvent media<br>(observed)","PDI<br>(observed)"]},{"name":"colValues","value":["6.1","8.4","25","PBS","0.265"]}]}]}],[{"name":"Assay Type","value":"size"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"The effect of size based on 37 degree Celsius and PBS solvent"},{"name":"Experiment Configurations","value":[{"Technique":["dynamic light scattering(DLS)"]},{"Instruments":["[Ljava.lang.String;@77de9a69"]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 6","fileId":21540120,"description":"Statistics graph based on size distribution by volume for NCL23 in PBS at 37 degrees Celsius"}],"Data and Conditions":[{"name":"colTitles","value":["PDI<br>(observed)","size<br>(Z-average,nm)","temperature<br>(observed,Celsius)","solvent media<br>(observed)","peak1<br>(mean,nm)"]},{"name":"colValues","value":["0.358","9.8","37","PBS","5.6"]}]}]}],[{"name":"Assay Type","value":"size"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"Intensity and Volume weighted graphs for NCL22 and NCL23"},{"name":"Experiment Configurations","value":[{"Technique":["dynamic light scattering(DLS)"]},{"Instruments":["[Ljava.lang.String;@376ba0fa"]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 10","fileId":21540121,"description":"The intensity-weighted (A) and volume-weighted size distribution (B) plots for NCL22 and NCL23. Multiple DLS measurements of NCL22 and NCL23 at 2000 ppm (2 mg/mL) in 10 mM NaCl were averaged and presented as intensity (Figure 10, A) and volume (Figure 10, B) distributions calculated using a non-negative least squares (NNLS) fit to the inverse Laplace transform.  Limited data suggest a slight increase in size occurs because of the presence of Gd-complex, but more extensive data are needed to confirm this suggestion.  For particles in the sub-100 nm range, the scattered intensity exhibits a d dependence, where d is the diameter.  In other words, a single 100-nm particle will scatter roughly the same amount of light as 1,000,000 particles with a diameter of 1 nm.  That is why the conversion to volume from intensity indicates that the smaller mode is predominant on a volume (or number) basis, and the large mode virtually disappears.  The smaller particle size peak below 10 nm is identified as the &quot;primary&quot; size."}]}]}],[{"name":"Assay Type","value":"size"},{"name":"Point of Contact","value":"DNT"},{"name":"Design Description","value":"DNT 122006 DLS"},{"name":"Experiment Configurations","value":[{"Technique":["dynamic light scattering(DLS)"]},{"Instruments":["[Ljava.lang.String;@471164ef"]},{"Description":[""]}]},{"name":"Characterization Results","value":[{"Files":[{"fileId":23801091,"title":"Statistics graph based on size distribution by volume for NCL23 in saline at 25 Ã‚Â°C.","description":"Statistics graph based on size distribution by volume for NCL23 in saline at 25 &#194;&#176;C."}],"Data and Conditions":[{"name":"colTitles","value":["average<br>(nm)","size<br>(Z-average,nm)","temperature<br>(observed,Celsius)","solvent media<br>(observed)","PDI"]},{"name":"colValues","value":["5.3","7.4","25","saline","0.235"]}]},{"Files":[{"fileId":23801092,"title":"Statistics graph based on size distribution by volume for NCL23 in PBS at 25 Ã‚Â°C.","description":"Statistics graph based on size distribution by volume for NCL23 in PBS at 25 &#194;&#176;C."}],"Data and Conditions":[{"name":"colTitles","value":["average<br>(nm)","size<br>(Z-average,nm)","temperature<br>(observed,Celsius)","solvent media<br>(observed)","PDI"]},{"name":"colValues","value":["6.1","8.4","25","PBS","0.265"]}]},{"Files":[{"fileId":23801093,"title":"Statistics graph based on size distribution by volume for NCL23 in PBS at 37 Ã‚Â°C.","description":"Statistics graph based on size distribution by volume for NCL23 in PBS at 37 &#194;&#176;C."}],"Data and Conditions":[{"name":"colTitles","value":["average<br>(nm)","size<br>(Z-average,nm)","temperature<br>(observed,Celsius)","solvent media<br>(observed)","PDI"]},{"name":"colValues","value":["5.6","9.8","37","PBS","0.358"]}]}]}]],"solubility":[[{"name":"Assay Type","value":"solubility"},{"name":"Point of Contact","value":"DNT"},{"name":"Properties","value":"N/A"},{"name":"Design Description","value":"DNT 082006"},{"name":"Characterization Results","value":[]}]]}},{"type":"in vitro characterization","charsByAssayType":{"blood contact":[[{"name":"Assay Type","value":"coagulation"},{"name":"Protocol","value":"ITA-12 (ITA-12), version 1.0"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"ANALYSIS OF NANOPARTICLE EFFECT ON COAGULATION - DONOR GROUP 3 (ITA&#8209;12). ","fileId":22457601,"description":"NCL22 and NCL23 at low concentrations (0.0156 and 0.25 mg/mL, respectively) and NCL22, NCL23 and NCL24 at high concentration (1mg/mL) were used to evaluate potential particle effects on blood coagulation. For each nanoparticle concentration, two independent samples were prepared and analyzed in duplicate (%CV &lt; 5%). Each bar represents the mean of duplicate results. The normal plasma standard (N) and abnormal plasma standard (ABN) were used for the instrument control. Plasma pooled from at least three donors was either untreated (Unt.) or treated with nanoparticle preparations NCL22, NCL23, and NCL24. The dotted red line indicates the clinical standard cut-off for normal coagulation time for each of the tests. The results demonstrate that, in this group of donors, neither nanoparticle test sample interfered with coagulation."}]},{"Files":[{"fileId":23801110,"title":"Analysis of nanoparticle effect on coagulation - donor group 1 (ITA&#8209;12). ","description":"NCL22, NCL23, and NCL24 at high (1mg/mL) concentrations were used to evaluate potential particle effects on the biochemical component of the blood coagulation cascade (prothrombin time [PT]; activated partial thromboplastin time [APTT]; Thrombin time and Reptilase time). For each nanoparticle, three independent samples were prepared and analyzed in duplicate (%CV &lt; 5%). Each bar represents the mean of duplicate results. Normal plasma standard (N) and abnormal plasma standard (ABN) were used for the instrument control. Plasma pooled from at least three donors was either untreated (Unt.) or treated with nanoparticle preparations NCL22, NCL23, or NCL24. The dotted red line indicates the clinical standard cut-off for normal coagulation time for each of the tests. The results demonstrate that high concentrations of each nanoparticle sample delay coagulation time of plasma derived from donor group 1 above clinically acceptable standard in APTT, thrombin time and reptilase time tests."}],"Data and Conditions":[{"name":"colTitles","value":["PT<br>(observed,Seconds)","APTT<br>(observed,Seconds)","Thrombin<br>(observed,Seconds)","Reptilase<br>(observed,Seconds)"]},{"name":"colValues","value":["13.3","35.0","25.0","24.0"]}]},{"Files":[{"fileId":23801111,"title":"Analysis of nanoparticle effect on coagulation - donor group 2 (ITA&#8209;12).","description":"NCL22 and NCL23 at low concentrations (0.0156 and 0.25 mg/mL, respectively) were used to evaluate potential particle effects on blood coagulation. For each nanoparticle concentration, three independent samples were prepared and analyzed in duplicate (%CV &lt; 5%). Each bar represents the mean of duplicate results. The normal plasma standard (N) and abnormal plasma standard (ABN) were used for the instrument control. Plasma pooled from at least three donors was either untreated (Unt.) or treated with nanoparticle preparations NCL22 or NCL23. Plasma samples exposed to high concentrations of NCL22, NCL23, and NCL24 were also included in the analysis; one sample of each nanoparticle formulation at high concentration was prepared and analyzed in duplicate (%CV &lt; 5%). Each bar represents the mean of duplicate results. The dotted red line indicates the clinical standard cut-off for normal coagulation time for each of the tests. The results demonstrate that, in this group of donors, neither nanoparticle test sample interferes with coagulation."}]}]}],[{"name":"Assay Type","value":"complement activation"},{"name":"Protocol","value":"ITA-5 (ITA-5), version 1.0"},{"name":"Characterization Results","value":[{"Files":[{"fileId":23801118,"title":"Analysis of complement activation (ITA-5)","description":"Analysis of complement activation (ITA-5). NCL22, NCL23 and NCL24 were tested for their ability to activate a complement. PBS and cobra venom factor (CVF) were used as the negative and positive control, respectively. NCL22 at both concentrations and NCL23 at low concentration did not induce complement activation, evidenced by an intensity of bands A and C that was similar to that of the negative control. NCL23 and NCL24 at 1mg/mL induced complement activation, evidenced by the appearance of split product (bands B and D) that was similar to that of the positive control."}],"Data and Conditions":[{"name":"colTitles","value":["complement activation induced<br>(boolean)","sample concentration<br>(observed,mg/mL)"]},{"name":"colValues","value":["1","1"]},{"name":"colValues","value":["0",".25"]}]}]}],[{"name":"Assay Type","value":"hemolysis"},{"name":"Point of Contact","value":"DNT"},{"name":"Protocol","value":"ITA-1 (ITA-1), version 1.0"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 39","fileId":21900562,"description":"Analysis of nanoparticle hemolytic properties (ITA-1). NCL22 and NCL23 at either high (1 mg/mL) or low (0.0156 and 0.25 mg/mL, respectively) concentration, and NCL24 at 1 mg/mL were used to evaluate potential particle effects on the integrity of red blood cells.  Three independent samples were prepared for each nanoparticle concentration and analyzed in duplicate (%CV &lt; 20). Each bar represents the mean of duplicate results.  Triton X-100 was used as a positive control.  PBS was used to reconstitute nanoparticle and represented the negative control.  Neither nanoparticle sample revealed hemolytic properties."}],"Data and Conditions":[{"name":"colTitles","value":["is hemolytic<br>(boolean)"]},{"name":"colValues","value":["0"]}]}]},{"name":"Analysis and Conclusion","value":"Neither NCL23 or NCL22 revealed hemolytic properties.  NCL23 is NCL22 with associated Magnevist."}],[{"name":"Assay Type","value":"platelet aggregation"},{"name":"Point of Contact","value":"DNT"},{"name":"Protocol","value":"ITA-2 (ITA-2), version 1.0"},{"name":"Design Description","value":"Nanoparticle ability to induce platelet aggregation"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 40A&B","fileId":21900566,"description":"Analysis of nanoparticle ability to induce platelet aggregation (ITA-2).  NCL22 and NCL23 at either high (1 mg/mL) or low (0.0156 and 0.25 mg/mL, respectively) concentration, and NCL24 at 1 mg/mL were used to evaluate potential particle effects on the cellular component of the blood coagulation cascade.  Fore each nanoparticle concentration, three independent samples were prepared and analyzed in duplicate (%CV &lt; 20%). Each bar represents to mean of duplicate results.  The results demonstrate that neither nanoparticle sample is capable of inducing platelet aggregation.  Collagen and PBS were used as a positive and negative control, respectively. (B) Analysis of nanoparticle effect on collagen-induced platelet aggregation (ITA-2). NCL22 and NCL23 at either high (1 mg/mL) or low (0.0156 and 0.25 mg/mL, respectively) concentration, and NCL24 at 1 mg/mL were used to evaluate potential particle interference with platelet aggregation caused by a known activation. For each nanoparticle concentration, three independent samples were prepared and analyzed in duplication (%CV &lt; 20%). Each bar represents the mean of duplicate results.   The results demonstrate the high doses of NCL23 andNCL24 enhance collagen-induced platelet aggregation, while low concentration of these particles did no disturb the process.  NCL22 at both high and low doses did not interfere with collagen-induce platelet aggregation."}],"Data and Conditions":[{"name":"colTitles","value":["sample concentration<br>(observed,mg/mL)","interferes with collagen-induced platelett aggregation?<br>(boolean)","induces platelet aggregation?<br>(boolean)","% platelet aggregation<br>(mean)","% Collagen Induced Platelet Aggregation<br>(mean)"]},{"name":"colValues","value":["1","1","0","-5.67","0.706"]},{"name":"colValues","value":[".25","0","0","-9.03","26.27"]}]}]}]],"cytotoxicity":[[{"name":"Assay Type","value":"cell viability"},{"name":"Properties","value":"N/A"},{"name":"Design Description","value":"DNT 082006 LDH LLC"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 32","fileId":21900555,"description":"LDH cytotoxicity assay in LLC-PK1 cells. Porcine renal proximal tubule cells were treated for 6, 24, and 48 h with 0.004-1.0 mg/mL of test sample. Cytotoxicity was determined at each time point as described in the LLC-PK1 Kidney Cytotoxicity Assay (GTA-1).  Dashed red line indicates 0% LDH leakage."}]}]}],[{"name":"Assay Type","value":"cell viability"},{"name":"Properties","value":"N/A"},{"name":"Design Description","value":"Cell lines were treated for 6, 24, and 48 h with 0.004-1.0 mg/ML of sample"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 33","fileId":21900556,"description":"MTT cytotoxicity assay in Hep-G2 cells. Hep-G2 cells were treated for 6, 24, and 48 h with 0.02-5.0 mg/mL of test sample. Cytotoxicity was determined at each time point as described in the HEP G2 Hepatocarcinoma Cytotoxicity Assay (GTA-2).  Dashed red line indicates 100% control viability."}]}]}],[{"name":"Assay Type","value":"cell viability"},{"name":"Point of Contact","value":"DNT"},{"name":"Properties","value":"N/A"},{"name":"Design Description","value":"Cell lines were treated for 6, 24, and 48 h with 0.004-1.0 mg/ML of sample"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 31","fileId":21900554,"description":"MTT cytotoxicity assay in LLC-PK1 cells. Porcine renal proximal tubule cells were treated for 6, 24, and 48 h with 0.004-1.0 mg/mL of test sample. Cytotoxicity was determined at each time point as described in the LLC-PK1 Kidney Cytotoxicity Assay (GTA-1). Dashed red line indicates 100% control viability."}],"Data and Conditions":[{"name":"colTitles","value":["percent cell viability<br>(observed,%)","time<br>(observed,hours)","sample concentration<br>(observed,mg/mL)"]},{"name":"colValues","value":["122.4","0",".004"]},{"name":"colValues","value":["99.1","6",".004"]},{"name":"colValues","value":["108.4","24",".004"]},{"name":"colValues","value":["112.8","48",".004"]},{"name":"colValues","value":["96.6","0",".008"]},{"name":"colValues","value":["106.8","6",".008"]},{"name":"colValues","value":["98.1","24",",008"]},{"name":"colValues","value":["166.5","48",".008"]},{"name":"colValues","value":["124.3","0",".016"]},{"name":"colValues","value":["96.0","6",".016"]},{"name":"colValues","value":["112.4","24",".016"]},{"name":"colValues","value":["91.3","48",".016"]},{"name":"colValues","value":["114.6","0",".031"]},{"name":"colValues","value":["109.3","6",".031"]},{"name":"colValues","value":["103.6","24",".031"]},{"name":"colValues","value":["136.3","48",".031"]}]}]}]],"immune cell function":[[{"name":"Assay Type","value":"CFU-GM"},{"name":"Protocol","value":"ITA-3 (ITA-3), version 1.0"},{"name":"Design Description","value":"Analysis of nanoparticle toxicity to bone marrow cells"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 41","fileId":21900568,"description":"Analysis of nanoparticle toxicity to bone marrow cells (ITA-3).  NCL22 and NCL23 at either high (1 mg/mL) or low (0.0156 and 0.25 mg/mL, respectively) concentration, and NCL24 at 1 mg/mL were used to evaluate potential particle effects on the formation of granulocyte-macrophage colonies from bone marrow precursors.  For each nanoparticle concentration, three independent samples were prepared and analyzed in duplicate (%CV &lt; 20%). Each bar represents to mean of duplicate results.  The results demonstrate that NCL22 and a low concentration of NCL23 are not myelosuppressive, while a high concentration of NCL23 and NCL24 suppresses CFU-GM formation (p&lt;0.05). Cisplatin and PBS were used as a positive and negative control, respectively."}],"Data and Conditions":[{"name":"colTitles","value":["sample concentration<br>(observed,mg/mL)","number of CFU-GM colonies<br>(mean)"]},{"name":"colValues","value":[".25","46.0"]},{"name":"colValues","value":["1","40.0"]}]}]}],[{"name":"Assay Type","value":"chemotaxis"},{"name":"Protocol","value":"ITA-8 (ITA-8), version 1.0"},{"name":"Characterization Results","value":[{"Files":[{"fileId":23801121,"title":"Analysis of nanoparticle effect on chemotaxis (ITA-8)","description":"Analysis of nanoparticle effect on chemotaxis (ITA-8). NCL22, NCL23 and NCL24 did not induce chemotaxis of HL-60 macrophage-like cells. PBS and FBS were used as negative and positive controls, respectively."}],"Data and Conditions":[{"name":"colTitles","value":["% chemotaxis<br>(mean)","sample concentration<br>(observed,mg/mL)"]},{"name":"colValues","value":["1.63","1"]},{"name":"colValues","value":["1.87",".25"]}]}]}],[{"name":"Assay Type","value":"cytokine induction"},{"name":"Protocol","value":"ITA-10 (ITA-10), version 1.0"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 49B","fileId":22457609,"description":"Cytokine secretion by peripheral blood mononuclear cells (PBMCs) (ITA-10) 48 hrs. PBMCs isolated from healthy donors were either untreated or treated for 48 h with bacterial LPS, LPS +IFNx, NCL22 at 0.2 mg/mL, NCL23 at 0.2 mg/mL or NCL24 at 0.2 mg/mL.  Two independent samples were analyzed for each concentration (%CV &lt; 25%). Each bar represents the mean of duplicate results.  Cell culture supernatants were diluted 1:5 and analyzed by flow cytometry using a CBA inflammation kit for quantitative determination of cytokines IL-10, TNF, IL-1, IL-6 and IL-8.  Shown are concentrations measured in individual samples after dilution. None of the nanoparticle formulations resulted in cytokine induction."}],"Data and Conditions":[{"name":"colTitles","value":["IL10<br>(observed,pg/mL)","IL1B<br>(observed,pg/mL)","IL8<br>(observed,pg/mL)","IL6<br>(observed,pg/mL)","TNFA<br>(mode,pg/mL)"]},{"name":"colValues","value":["2.7","2.7","608.4","9.7","2.7"]}]},{"Files":[{"fileId":23801127,"title":"Cytokine secretion by peripheral blood mononuclear cells (PBMCs) (ITA-10) 24 hrs.","description":"Cytokine secretion by peripheral blood mononuclear cells (PBMCs) (ITA&amp;#8209;10) 24 hrs. PBMCs isolated from healthy donors were either untreated or treated for 24 h with bacterial lipopolysaccharide (LPS), LPS +IFNg, NCL22 at 0.2 mg/mL, NCL23 at 0.2 mg/mL or NCL24 at 0.2 mg/mL. Two independent samples were analyzed for each concentration (%CV &lt; 25%). Each bar represents the mean of duplicate results. Cell culture supernatants were diluted 1:5 and analyzed by flow cytometry using a Cytometric Bead Array (CBA) inflammation kit for quantitative determination of cytokines IL-10, TNF, IL-1, IL-6 and IL-8. Shown are concentrations measured in individual samples after dilution. None of the nanoparticle formulations resulted in cytokine induction."}],"Data and Conditions":[{"name":"colTitles","value":["IL10<br>(observed,pg/mL)","IL1B<br>(observed,pg/mL)","IL8<br>(observed,pg/mL)","IL6<br>(observed,pg/mL)","TNFA<br>(observed,pg/mL)"]},{"name":"colValues","value":["5.7","6.2","562.6","22.1","5.7"]}]}]}],[{"name":"Assay Type","value":"cytotoxic activity of NK cells"},{"name":"Protocol","value":"ITA-11 (ITA-11), version 1.0"},{"name":"Design Description","value":"DNT 082006"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig50C","fileId":22457613,"description":"Analysis of cytotoxic activity of NK cells by RT-CES.  The NK cell line NK92 (source ATCC) and tumor cell line HepG2 (source DTP) were used as effectors and targets, respectively.  The effector and target cells were co-cultured at an effector-to-target (E:T) ratio of 1:5 untreated or in the presence of NCL22, NCL23, or NCL24 at a concentration of 0.2 mg/mL.  Cell viability was continuously monitored in real time for 48 h. The percentage of cytoxicty was calculated by comparing the AIC values of untreated cells or of cells co-cultured in the presence of nanoparticles with that of the targets growth curve. Results from duplicate samples are shown (%CV &lt; 20%). Each bar represents the mean of duplicate results. NCL22, NCL23, and NCL24 did not interfere with the cytotoxicity of NK cells towards tumor targets."}]},{"Files":[{"fileId":23801128,"title":"Analysis of cytotoxic activity of NK cells by 51Cr-release Assay (ITA-11A).","description":"Analysis of cytotoxic activity of NK cells by 51Cr-release Assay (ITA-11A). The NK cell line NK92 (source Laboratory of Experimental Immunology [LEI]) and tumor cell line K562 (source Developmental Therapeutics Program [DTP]) were used as effectors and targets, respectively. The effector and 51Cr-loaded target cells were co-cultured at different effector-to-target (E:T) ratios without, or in the presence of, NCL22, NCL23, and NCL24. For each concentration, two independent samples were prepared and analyzed in triplicate (%CV &lt; 20%). Each data point represents the mean of triplicate results. Additional samples were included to control for nanoparticle-induced chromium release from target cells. NCL22 at both concentrations and NCL23 at a low concentration did not interfere with the cytotoxicity of NK cells, while high doses of NCL23 and NCL24 slightly inhibited the cytotoxicity of NK cells towards K562 targets."}]},{"Files":[{"fileId":23801129,"title":"Analysis of cytotoxic activity of NK cells by (RT-CES).","description":"Analysis of cytotoxic activity of NK cells by (RT-CES). The NK cell line NK92 (source American Type Culture Collection [ATCC]) and tumor cell line HepG2 (source DTP) were used as effectors and targets, respectively. Effector and target cells were co-cultured at an effector-to-target (E:T) ratio of 1:5 without, or in the presence of, NCL22, NCL23, and NCL24 (%CV &lt; 20%). Additional samples were included to control for nanoparticle-associated toxicity to target cells. Cell viability was continuously monitored in real time for 48 h. Data were collected every 30 min during the first 22 h, every 2 min from 22 to 25 h, and every 10 min from 26 to 48 h. Nanoparticles did not interfere with the instrument detection system and were not toxic to tumor targets. NCL22, NCL23, and NCL24 did not interfere with cytotoxicity of NK cells towards tumor targets. NCL22 and NCL24 slightly inhibited the viability of effector NK92 cells."}]}]}],[{"name":"Assay Type","value":"leukocyte proliferation"},{"name":"Protocol","value":"ITA-6 (ITA-6), version 1.0"},{"name":"Design Description","value":"DNT 122006"},{"name":"Characterization Results","value":[{"Files":[{"fileId":23801115,"title":"Analysis of nanoparticle effect on leuckocyte proliferation (ITA-6).","description":"Analysis of nanoparticle effect on leuckocyte proliferation (ITA-6). NCL22, NCL23, and NCL24 did not induce leukocyte proliferation. Phytohemaglutinin-M (PHM) was used as a positive control for proliferation induction. For each nanoparticle concentration, three independent samples were prepared and analyzed in duplicate (%CV &lt; 25%). Each bar represents the mean of duplicate results. NCL22 did not suppress proliferation induced by PHA-M, while NCL23 at 1mg/mL suppressed PHA-M&#195;&#8218;&#226;&#8364;&#8220;induced proliferation. PBS was used as a negative control."}]}]}],[{"name":"Assay Type","value":"phagocytosis"},{"name":"Protocol","value":"ITA-9 (ITA-9), version 1.0"},{"name":"Design Description","value":"DNT 082006"},{"name":"Characterization Results","value":[{"Files":[{"imageTitle":"August 2006 DNT NCL200612A Fig 48","fileId":22457607,"description":"Phagocytosis assay (ITA-9). NCL22 and NCL23 were analyzed at high (1 mg/mL) and low (0.0156 and 0.25 mg/mL respectively) Concentrations;  NCL24 was analyzed at 1 mg/mL. For each concentration, three independent samples were prepared and analyzed in duplicate (%CV &lt; 25%). Each bar represents the mean of duplicate results. Zymosan A was used as positive control. NCL22, NCL23, and NCL24 were not phagocytosed by macrophages. NCL22 did no affect phagocytic uptake of Zymosan A, while NCL23 and NCL24 suppressed phagocytosis of Zymosan A.  All particles were tested at a concentration of 1 mg/mL."}]}]}]]}}];
    $scope.loader = 1;
    $http({
      method: 'GET',
      url: '/caNanoLab/rest/sample/characterizationView?sampleId=' + $scope.sampleId
    }).
    then(function (data, status, headers, config) {
      data = data['data']
      $scope.data = data;
      $scope.sampleName = sampleService.sampleName($scope.sampleId);
      $scope.loader = 0;

    }).
    catch(function (data, status, headers, config) {
      data = data['data']
      $scope.message = data;
      $scope.loader = 0;
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