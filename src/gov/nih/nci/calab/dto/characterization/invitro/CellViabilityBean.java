package gov.nih.nci.calab.dto.characterization.invitro;

import gov.nih.nci.calab.domain.nano.characterization.invitro.CellViability;
import gov.nih.nci.calab.dto.characterization.CharacterizationBean;
import gov.nih.nci.calab.dto.characterization.DatumBean;
import gov.nih.nci.calab.dto.characterization.DerivedBioAssayDataBean;

import java.util.List;


/**
 * This class represents the cellViability characterization information to be shown in
 * the view page.
 * 
 * @author beasleyj
 * 
 */
public class CellViabilityBean extends CharacterizationBean {
	
	private String cellLine;	
	private String cellDeathMethod;

	public CellViabilityBean() {
		super();
	}
	
	public CellViabilityBean(CellViability aChar) {
		super(aChar);

		this.cellLine = aChar.getCellLine();
		this.cellDeathMethod = aChar.getCellDeathMethod();
	}

	public void setDerivedBioAssayDataList(
			List<DerivedBioAssayDataBean> derivedBioAssayData) {
		super.setDerivedBioAssayDataList(derivedBioAssayData);
		
		for (DerivedBioAssayDataBean table:getDerivedBioAssayDataList()) {
			for (DatumBean datum : table.getDatumList()) {
				datum.setType("Percent Cell Viability");
				datum.setValueUnit("%");
			}			
		}
	}

	public String getCellLine() {
		return cellLine;
	}

	public void setCellLine(String cellLine) {
		this.cellLine = cellLine;
	}
	
	public CellViability getDomainObj() {
		CellViability cellViability = new CellViability();
		super.updateDomainObj(cellViability);
		cellViability.setCellLine(this.cellLine);
		return cellViability;
	}
}
