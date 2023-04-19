package gov.nih.nci.cananolab.restful.view.characterization.properties;


import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationBean;
import gov.nih.nci.cananolab.util.ClassUtils;
import gov.nih.nci.cananolab.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
use = JsonTypeInfo.Id.NAME,
include = JsonTypeInfo.As.PROPERTY,
defaultImpl = SimplePropertyDefault.class)
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,property = "propertyName")
@JsonSubTypes({
@JsonSubTypes.Type(value = SimpleCytotoxicity.class, name = "SimpleCytotoxicity"),
@JsonSubTypes.Type(value = SimpleEnzymeInduction.class, name = "SimpleEnzymeInduction"),
@JsonSubTypes.Type(value = SimplePhysicalState.class, name = "SimplePhysicalState"),
@JsonSubTypes.Type(value = SimpleShape.class, name = "Shape"),
@JsonSubTypes.Type(value = SimpleSolubility.class, name = "SimpleSolubility"),
@JsonSubTypes.Type(value = SimpleSurface.class, name = "SimpleSurface"),
@JsonSubTypes.Type(value = SimpleTransfection.class, name = "SimpleTransfection"),
@JsonSubTypes.Type(value = SimpleTargeting.class, name = "SimpleTargeting")
})

public class SimpleCharacterizationProperty {

	String propertyName = "";
	String propertyDisplayName = "";


	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String getPropertyDisplayName() {
		return propertyDisplayName;
	}
	public void setPropertyDisplayName(String propertyDisplayName) {
		this.propertyDisplayName = propertyDisplayName;
	}
	
	public void setLookups(HttpServletRequest request) throws Exception {
    }
	
	public void transferFromPropertyBean(HttpServletRequest request, CharacterizationBean charBean, boolean needOptions) 
			throws Exception {
		this.propertyName = ClassUtils.getShortClassNameFromDisplayName(charBean.getCharacterizationName());
		this.propertyDisplayName = StringUtils.getCamelCaseFormatInWords(this.propertyName);
	}
	
	public void transferToPropertyBean(CharacterizationBean charBean) throws Exception {
    }
	public List<String> getPropertyViewTitles() {return new ArrayList<String>();}
	public List<String> getPropertyViewValues() {return new ArrayList<String>();}


}
