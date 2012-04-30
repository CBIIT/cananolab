var instrumentCache = null;
var currentExperimentConfig = null;
var numberOfInstruments = 0; // number of unique instruments in the cache,
// used to generate instrument id
function retrieveTechniqueAbbreviation() {
	var techniqueType = document.getElementById("techniqueType").value;
	if (techniqueType != null && techniqueType != "other") {
		ExperimentConfigManager.getInstrumentTypesByTechniqueType(
				techniqueType, updateInstrumentDropDown);
		ExperimentConfigManager.getTechniqueAbbreviation(techniqueType,
				function(abbreviation) {
					dwr.util.setValue("techniqueAbbr", abbreviation);
				});
	}
}
function updateInstrumentDropDown(instrumentTypes) {
	var id = "type";
	var selectedValue = dwr.util.getValue(id);
	dwr.util.removeAllOptions(id);
	dwr.util.addOptions(id, [ "" ]);
	dwr.util.addOptions(id, instrumentTypes);
	dwr.util.addOptions(id, [ "[other]" ]);
	dwr.util.setValue(id, selectedValue);
}
function setManufacturerOptions(manufacturerTypes) {
	dwr.util.removeAllOptions("instrumentManufacturer"
			+ instrumentManufacturerIndex);
	dwr.util.addOptions("instrumentManufacturer" + instrumentManufacturerIndex,
			[ "" ]);
	dwr.util.addOptions("instrumentManufacturer" + instrumentManufacturerIndex,
			manufacturerTypes);
	dwr.util.addOptions("instrumentManufacturer" + instrumentManufacturerIndex,
			[ "[other]" ]);
}
function setTheExperimentConfig(configId) {
	numberOfInstruments = 0;
	ExperimentConfigManager.getExperimentConfigById(configId,
			populateExperimentConfig);
	show("deleteExperimentConfig");
	openSubmissionForm("ExperimentConfig");
	disableOuterButtons();
	// Feature request [26487] Deeper Edit Links.
	window.setTimeout("openOneInstrument()", 200);
}
// Populate Instrument submission form and auto open it for user.
function openOneInstrument() {
	if (currentExperimentConfig != null
			&& currentExperimentConfig.instruments.length == 1) {
		var instrument = currentExperimentConfig.instruments[0];
		populateInstrumentForm(instrument);
	} else {
		hide("newInstrument");
	}
}
// Populate the Experiment Config form.
function populateExperimentConfig(experimentConfig) {
	if (experimentConfig != null) {
		currentExperimentConfig = experimentConfig;
		if (experimentConfig.domain.technique != null) {
			dwr.util.setValue("techniqueType",
					experimentConfig.domain.technique.type);
			dwr.util.setValue("techniqueAbbr",
					experimentConfig.domain.technique.abbreviation);
		}
		if (experimentConfig.domain.id != null) {
			ExperimentConfigManager.getInstrumentTypesByConfigId(
					experimentConfig.domain.id, updateInstrumentDropDown);
		}
		dwr.util.setValue("configDescription",
				experimentConfig.domain.description);
		dwr.util.setValue("configId", experimentConfig.domain.id);
		// clear the cache for each new experimentConfig
		instrumentCache = {};
		populateInstruments();
	} else {
		sessionTimeout();
	}
}
// Populate the Instrument table inside Experiment Config form.
function populateInstruments() {
	var instruments = currentExperimentConfig.instruments;
	dwr.util.removeAllRows("instrumentRows", {
		filter : function(tr) {
			return (tr.id != "pattern" && tr.id != "patternHeader");
		}
	});
	var instrument, id;
	if (instruments.length > 0) {
		show("instrumentTable");
	} else {
		hide("instrumentTable");
	}
	for ( var i = 0; i < instruments.length; i++) {
		instrument = instruments[i];
		id = instrument.id;
		dwr.util.cloneNode("pattern", {
			idSuffix : id
		});
		dwr.util.setValue("instrumentId" + id, instrument.id);
		dwr.util.setValue("instrumentManufacturer" + id,
				instrument.manufacturer);
		dwr.util.setValue("instrumentModelName" + id, instrument.modelName);
		dwr.util.setValue("instrumentType" + id, instrument.type);
		$("pattern" + id).style.display = "";
		if (instrumentCache[id] == null) {
			numberOfInstruments++;
		}
		instrumentCache[id] = instrument;
	}
}
function clearExperimentConfig() {
	// go to server and clean form bean
	ExperimentConfigManager.resetTheExperimentConfig(populateExperimentConfig);
	hide("deleteExperimentConfig");
	hide("newInstrument");
	numberOfInstruments = 0;
}
function deleteTheExperimentConfig(publicRetract) {
	var validateRetract = true;
	if (publicRetract == "true") {
		validateRetract = confirmPublicDataUpdate();
	}
	if (validateRetract) {
		var answer = confirmDelete("experiment config");
		if (answer != 0) {
			submitAction("charForm", "characterization",
					"deleteExperimentConfig", 2);
		}
	}
}
function validateSaveConfig(publicRetract, actionName) {
	var validateRetract = true;
	if (publicRetract == "true") {
		validateRetract = confirmPublicDataUpdate();
	}
	if (validateRetract) {
		if (validateShapeInfo() && validateSolubilityInfo()
				&& validateSavingTheData('newInstrument', 'Instrument')) {
			submitAction("charForm", actionName, "saveExperimentConfig",
					2);
			return true;
		} else {
			return false;
		}
	}
}
function addInstrument() {
	var instrument = {
		id : null,
		manufacturer : null,
		modelName : null,
		type : null
	};
	dwr.util.getValues(instrument);
	if (instrument.id == null || instrument.id.length == 0
			|| instrument.id.toLowerCase() == "null") {
		instrument.id = -1000 - numberOfInstruments;
	}
	if (instrument.manufacturer != "" || instrument.modelName != ""
			|| instrument.type != "") {
		ExperimentConfigManager.addInstrument(instrument, function(
				experimentConfig) {
			if (experimentConfig == null) {
				sessionTimeout();
			}
			currentExperimentConfig = experimentConfig;
		});
		window.setTimeout("populateInstruments()", 200);
		return true;
	} else {
		alert("Please fill in values");
		return false;
	}
}
function clearInstrument() {
	document.getElementById("id").value = null;
	document.getElementById("manufacturer").value = "";
	document.getElementById("modelName").value = "";
	document.getElementById("type").value = "";
	hide("deleteInstrument");
}
function editInstrument(eleid) {
	// we were an id of the form "edit{id}", eg "edit42". We lookup the "42"
	var instrument = instrumentCache[eleid.substring(4)];
	populateInstrumentForm(instrument);
}
function populateInstrumentForm(instrument) {
	dwr.util.setValues(instrument);
	show("deleteInstrument");
	show("newInstrument");
}
function deleteTheInstrument() {
	var eleid = document.getElementById("id").value;
	// we were an id of the form "delete{id}", eg "delete42". We lookup the "42"
	// var instrument = instrumentCache[eleid.substring(6)];
	if (eleid != "") {
		var instrument = instrumentCache[eleid];
		if (confirm("Are you sure you want to delete '"
				+ instrument.manufacturer + " " + instrument.modelName + "'?")) {
			ExperimentConfigManager.deleteInstrument(instrument, function(
					experimentConfig) {
				if (experimentConfig == null) {
					sessionTimeout();
				}
				currentExperimentConfig = experimentConfig;
			});
			window.setTimeout("populateInstruments()", 200);
			hide("newInstrument");
		}
	}
}
