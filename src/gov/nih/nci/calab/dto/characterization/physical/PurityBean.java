/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.characterization.physical;

import gov.nih.nci.calab.domain.nano.characterization.physical.Purity;
import gov.nih.nci.calab.dto.characterization.CharacterizationBean;
import gov.nih.nci.calab.dto.characterization.DerivedBioAssayDataBean;

import java.util.List;


/**
 * This class represents the Purity characterization information to be shown in
 * the view page.
 * 
 * @author chande
 * 
 */
public class PurityBean extends CharacterizationBean {
	private String id;
	/*
	private String homogeneityInLigand;
	private String residualSolvents;
	private String freeComponents;
	private String freeComponentsUnit;
	*/
	
	public PurityBean() {
		super();
		initSetup();
	}
	
	public PurityBean(Purity aChar) {
		super(aChar);

		this.id = aChar.getId().toString();
		/*
		if (aChar.getHomogeneityInLigand() != null)
			this.homogeneityInLigand = aChar.getHomogeneityInLigand().toString();
		this.residualSolvents = aChar.getResidualSolvents();
		if (aChar.getFreeComponents() != null) {
			this.freeComponents = aChar.getFreeComponents().getValue();
			this.freeComponentsUnit = aChar.getFreeComponents().getUnitOfMeasurement();
		}
		*/
	}
	
	public void setDerivedBioAssayDataList(
			List<DerivedBioAssayDataBean> derivedBioAssayData) {
		super.setDerivedBioAssayDataList(derivedBioAssayData);
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
	
	public Purity getDomainObj() {
		Purity purity = new Purity();
		super.updateDomainObj(purity);
		
		if (this.id != null && !this.id.equals(""))
			purity.setId(new Long(this.id));

		return purity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
