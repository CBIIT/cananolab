package gov.nih.nci.cananolab.dto.particle.characterization;

import gov.nih.nci.cananolab.domain.common.DerivedBioAssayData;
import gov.nih.nci.cananolab.domain.common.DerivedDatum;
import gov.nih.nci.cananolab.dto.common.LabFileBean;
import gov.nih.nci.cananolab.util.CaNanoLabComparators;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * View bean for DerivedBioAssayData
 * 
 * @author pansu
 * 
 */
public class DerivedBioAssayDataBean {
	private DerivedBioAssayData domainBioAssayData = new DerivedBioAssayData();

	private LabFileBean labFileBean = new LabFileBean();

	private List<DerivedDatum> datumList = new ArrayList<DerivedDatum>();

	public DerivedBioAssayDataBean() {

	}

	public DerivedBioAssayDataBean(DerivedBioAssayData derivedBioAssayData) {
		domainBioAssayData = derivedBioAssayData;
		if (domainBioAssayData.getLabFile() != null) {
			labFileBean = new LabFileBean(domainBioAssayData.getLabFile());
		}
		if (domainBioAssayData.getDerivedDatumCollection() != null) {
			datumList.addAll(domainBioAssayData.getDerivedDatumCollection());
		}
		Collections.sort(datumList,
				new CaNanoLabComparators.DerivedDatumDateComparator());
	}

	public DerivedBioAssayData getDomainBioAssayData() {
		return domainBioAssayData;
	}

	public LabFileBean getLabFileBean() {
		return labFileBean;
	}

	public List<DerivedDatum> getDatumList() {
		if (domainBioAssayData.getDerivedDatumCollection() != null) {
			domainBioAssayData.getDerivedDatumCollection().clear();
		} else {
			domainBioAssayData
					.setDerivedDatumCollection(new HashSet<DerivedDatum>());
		}
		for (DerivedDatum datum : datumList) {
			domainBioAssayData.getDerivedDatumCollection().add(datum);
		}
		return datumList;
	}

	public void addDerivedDatum() {
		datumList.add(new DerivedDatum());
	}

	public void removeDerivedDatum(int ind) {
		datumList.remove(ind);
	}

	public void setupDomainBioAssayData(Map<String, String> typeToClass,
			String createdBy, String internalUriPath) throws Exception {
		if (domainBioAssayData.getId() == null
				|| domainBioAssayData.getCreatedBy() != null
				&& domainBioAssayData.getCreatedBy().equals(
						CaNanoLabConstants.AUTO_COPY_ANNOTATION_PREFIX)) {
			domainBioAssayData.setCreatedBy(createdBy);
			domainBioAssayData.setCreatedDate(new Date());
		}
		if (domainBioAssayData.getDerivedDatumCollection() != null) {
			domainBioAssayData.getDerivedDatumCollection().clear();
		} else {
			domainBioAssayData
					.setDerivedDatumCollection(new HashSet<DerivedDatum>());
		}
		labFileBean.setupDomainFile(internalUriPath, createdBy);
		domainBioAssayData.setLabFile(labFileBean.getDomainFile());
		for (DerivedDatum datum : datumList) {
			if (datum.getId() == null) {
				datum.setCreatedBy(createdBy);
				datum.setCreatedDate(new Date());
			}
			domainBioAssayData.getDerivedDatumCollection().add(datum);
		}
	}
}
