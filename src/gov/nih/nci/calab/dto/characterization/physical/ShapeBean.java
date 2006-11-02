package gov.nih.nci.calab.dto.characterization.physical;

import gov.nih.nci.calab.domain.nano.characterization.physical.Shape;
import gov.nih.nci.calab.domain.nano.characterization.Characterization;
import gov.nih.nci.calab.domain.nano.characterization.DerivedBioAssayData;

import gov.nih.nci.calab.dto.characterization.CharacterizationBean;
import gov.nih.nci.calab.dto.characterization.DerivedBioAssayDataBean;
import gov.nih.nci.calab.dto.characterization.DatumBean;

import java.util.List;


/**
 * This class represents the Shape characterization information to be shown in
 * the view page.
 * 
 * @author chande
 * 
 */
public class ShapeBean extends CharacterizationBean {
	private String type;
	private String maxDimension;
	private String minDimension;
	
	private String otherShapeType;
	
	public ShapeBean() {
		super();
		initSetup();
	}
	
	public ShapeBean(Shape aChar) {
		super(aChar);

		this.type = aChar.getType();
		this.minDimension = aChar.getMinDimension().toString();
		this.maxDimension = aChar.getMaxDimension().toString();
	}
	
	public void setDerivedBioAssayData(
			List<DerivedBioAssayDataBean> derivedBioAssayData) {
		super.setDerivedBioAssayData(derivedBioAssayData);
		initSetup();
	}
	
	public void initSetup() {
		/*
		for (DerivedBioAssayDataBean table:getDerivedBioAssayData()) {
			DatumBean average=new DatumBean();
			average.setType("Average");
			average.setValueUnit("nm");
			DatumBean zaverage=new DatumBean();
			zaverage.setType("Z-Average");
			zaverage.setValueUnit("nm");
			DatumBean pdi=new DatumBean();
			pdi.setType("PDI");
			table.getDatumList().add(average);
			table.getDatumList().add(zaverage);
			table.getDatumList().add(pdi);
		}
		*/
	}
	
	public Shape getDomainObj() {
		Shape shape = new Shape();
		super.updateDomainObj(shape);
		
		if (this.type == "Other" && this.otherShapeType != "") 
			shape.setType(this.otherShapeType);
		else
			shape.setType(this.type);
		shape.setMinDimension(Float.valueOf(this.minDimension));
		shape.setMaxDimension(Float.valueOf(this.maxDimension));
		
		return shape;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaxDimension() {
		return maxDimension;
	}

	public void setMaxDimension(String maxDimension) {
		this.maxDimension = maxDimension;
	}

	public String getMinDimension() {
		return minDimension;
	}

	public void setMinDimension(String minDimension) {
		this.minDimension = minDimension;
	}

	public String getOtherShapeType() {
		return otherShapeType;
	}

	public void setOtherShapeType(String otherShapeType) {
		this.otherShapeType = otherShapeType;
	}
}
