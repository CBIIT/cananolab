/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.characterization.invitro;

import gov.nih.nci.calab.domain.nano.characterization.toxicity.Cytotoxicity;
import gov.nih.nci.calab.dto.characterization.CharacterizationBean;

/**
 * This class represents the cytotoxicity characterization information to be
 * shown in the view page.
 * 
 * @author pansu
 * 
 */
public class CytotoxicityBean extends CharacterizationBean {

	private String cellLine;

	// private String cellDeathMethod;

	public CytotoxicityBean() {
		super();
	}

	public CytotoxicityBean(CytotoxicityBean propBean,
			CharacterizationBean charBean) {
		super(charBean);
		this.cellLine = propBean.getCellLine();
	}

	public CytotoxicityBean(Cytotoxicity aChar) {
		super(aChar);

		this.cellLine = aChar.getCellLine();
		// this.cellDeathMethod = aChar.getCellDeathMethod();
	}

	public String getCellLine() {
		return cellLine;
	}

	public void setCellLine(String cellLine) {
		this.cellLine = cellLine;
	}

	public void updateDomainObj(Cytotoxicity cytotoxicity) {
		super.updateDomainObj(cytotoxicity);
		cytotoxicity.setCellLine(this.cellLine);
	}
}
