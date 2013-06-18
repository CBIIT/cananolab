/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.common;

import gov.nih.nci.calab.domain.Instrument;
import gov.nih.nci.calab.domain.InstrumentConfiguration;
import gov.nih.nci.calab.service.util.StringUtils;

/**
 * This class represents an instrument configuration used for characterization
 * 
 * @author pansu
 * 
 */
public class InstrumentConfigBean {
	private String id;

	private String description;

	private InstrumentBean instrumentBean = new InstrumentBean();

	public InstrumentConfigBean() {
	}

	public InstrumentConfigBean(InstrumentConfiguration config) {
		this.id = StringUtils.convertToString(config.getId());
		this.description = (config.getDescription() != null) ? config
				.getDescription() : "";
		this.instrumentBean = new InstrumentBean(config.getInstrument());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InstrumentBean getInstrumentBean() {
		return instrumentBean;
	}

	public void setInstrumentBean(InstrumentBean instrumentBean) {
		this.instrumentBean = instrumentBean;
	}

	public InstrumentConfigBean copy() {
		InstrumentConfigBean newInstrumentConfigBean = new InstrumentConfigBean();
		//do not copy id
		newInstrumentConfigBean.setDescription(description);
		newInstrumentConfigBean.setInstrumentBean(instrumentBean);
		return newInstrumentConfigBean;
	}
	
	public InstrumentConfiguration getDomainObject() {
		InstrumentConfiguration instrumentConfig = new InstrumentConfiguration();
		if (id != null && id.length() > 0) {
			instrumentConfig.setId(new Long(id));
		}
		Instrument instrument=instrumentBean.getDomainObject();
		instrumentConfig.setDescription(description);
		instrumentConfig.setInstrument(instrument);
		return instrumentConfig;
	}
}
