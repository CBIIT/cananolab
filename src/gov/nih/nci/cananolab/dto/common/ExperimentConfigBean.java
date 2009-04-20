package gov.nih.nci.cananolab.dto.common;

import gov.nih.nci.cananolab.domain.common.ExperimentConfig;
import gov.nih.nci.cananolab.domain.common.Instrument;
import gov.nih.nci.cananolab.domain.common.Technique;
import gov.nih.nci.cananolab.util.Comparators;
import gov.nih.nci.cananolab.util.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * View bean for technique and associated instruments;
 *
 * @author pansu, tanq
 *
 */
public class ExperimentConfigBean {
	private ExperimentConfig domain;
	private List<Instrument> instruments = new ArrayList<Instrument>(20);

	public ExperimentConfigBean() {
		domain = new ExperimentConfig();
		domain.setTechnique(new Technique());
	}

	public ExperimentConfigBean(ExperimentConfig config) {
		domain = config;
		for (Instrument instrument : config.getInstrumentCollection()) {
			instruments.add(instrument);
		}
		Collections.sort(instruments,
				new Comparators.InstrumentCreationDateComparator());
	}

	public ExperimentConfig getDomain() {
		return domain;
	}

	public void setDomain(ExperimentConfig domain) {
		this.domain = domain;
	}

	public String getTechniqueDisplayName() {
		String techniqueDisplayName = "";
		if (domain.getTechnique() != null
				&& domain.getTechnique().getAbbreviation() != null
				&& domain.getTechnique().getAbbreviation().trim().length() > 0) {
			techniqueDisplayName = domain.getTechnique().getType() + "("
					+ domain.getTechnique().getAbbreviation() + ")";
		} else {
			techniqueDisplayName = domain.getTechnique().getType();
		}
		return techniqueDisplayName;
	}

	public void addInstrument(Instrument instrument) {
		if (instruments.contains(instrument)) {
			instruments.remove(instrument);
		}
		instruments.add(instrument);
	}

	public void removeInstrument(Instrument instrument) {
		instruments.remove(instrument);
	}

	/**
	 * @return the instruments
	 */
	public List<Instrument> getInstruments() {
		return instruments;
	}

	/**
	 * @param instruments
	 *            the instruments to set
	 */
	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}

	public void setupDomain(String createdBy) throws Exception {
		if (domain.getId() != null && domain.getId() == 0) {
			domain.setId(null);
		}
		if (domain.getId() == null) {
			domain.setCreatedBy(createdBy);
			domain.setCreatedDate(new Date());
		}
		if (domain.getInstrumentCollection() != null) {
			domain.getInstrumentCollection().clear();
		} else {
			domain.setInstrumentCollection(new HashSet<Instrument>());
		}
		int i = 0;
		for (Instrument instrument : instruments) {
			if (instrument.getType() != null
					&& instrument.getType().length() > 0
					|| instrument.getManufacturer() != null
					&& instrument.getManufacturer().length() > 0
					|| instrument.getModelName() != null
					&& instrument.getModelName().length() > 0) {
				instrument.setCreatedBy(createdBy);
				instrument.setCreatedDate(DateUtils.addSecondsToCurrentDate(i));
				domain.getInstrumentCollection().add(instrument);
				i++;
			}
		}
	}

	public boolean equals(Object obj) {
		boolean eq = false;
		if (obj instanceof ExperimentConfigBean) {
			ExperimentConfigBean c = (ExperimentConfigBean) obj;
			Long thisId = this.domain.getId();
			if (thisId != null && thisId.equals(c.getDomain().getId())) {
				eq = true;
			}
		}
		return eq;
	}

	public int hashCode() {
		return domain.hashCode();
	}

	private String getInstrumentDisplayName(Instrument instrument) {
		StringBuffer sb = new StringBuffer();
		if (instrument.getType() != null
				&& instrument.getType().trim().length() > 0) {
			sb.append(instrument.getType());
			sb.append(" ");
		}
		if (instrument.getManufacturer() != null
				&& instrument.getManufacturer().trim().length() > 0) {
			sb.append("(");
			sb.append(instrument.getManufacturer());
			if (instrument.getModelName() != null
					&& instrument.getModelName().trim().length() > 0) {
				sb.append(", ");
				sb.append(instrument.getModelName());

			}
			sb.append(")");
		}
		return sb.toString();
	}

	public String[] getInstrumentDisplayNames() {
		List<String> displayNames = new ArrayList<String>();
		for (Instrument instrument : instruments) {
			displayNames.add(getInstrumentDisplayName(instrument));
		}
		if (displayNames.isEmpty()) {
			return null;
		}
		return displayNames.toArray(new String[displayNames.size()]);
	}

}
