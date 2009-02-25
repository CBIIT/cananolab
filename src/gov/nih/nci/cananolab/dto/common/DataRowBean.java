package gov.nih.nci.cananolab.dto.common;

import gov.nih.nci.cananolab.domain.common.Condition;
import gov.nih.nci.cananolab.domain.common.DataRow;
import gov.nih.nci.cananolab.domain.common.Datum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * View bean for Datum
 *
 * @author pansu, tanq
 *
 */
public class DataRowBean {
	private DataRow domain = new DataRow();
	private List<Datum> data = new ArrayList<Datum>();
	private List<Condition> conditions= new ArrayList<Condition>();

	public DataRowBean() {
	}

	public DataRowBean(List<Datum> data) {
		domain = data.get(0).getDataRow();
		this.data = data;
	}

	public void addDatum(Datum datum) {
		if (data.contains(datum)) {
			data.remove(datum);
		}
		datum.setDataRow(domain);
		data.add(datum);
	}

	public void addDatumColumn(Datum datum) {
		datum.setDataRow(domain);
		if (data.contains(datum)) {
			for (Datum thisDatum : data) {
				if (thisDatum.getId().equals(datum.getId())) {
					thisDatum.setName(datum.getName());
					thisDatum.setValueType(datum.getValueType());
					thisDatum.setValueUnit(datum.getValueUnit());
				}
			}
		} else {
			data.add(datum);
		}
	}

	public void removeDatum(Datum datum) {
		data.remove(datum);
	}

	/**
	 * @return the data
	 */
	public Collection<Datum> getData() {
		return data;
	}

	/**
	 * @return the domain
	 */
	public DataRow getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(DataRow domain) {
		this.domain = domain;
	}

	/**
	 * Compares <code>obj</code> to it self and returns true if they both are
	 * same
	 *
	 * @param obj
	 */
	public boolean equals(Object obj) {
		if (obj instanceof DataRowBean) {
			DataRowBean dataRowBean = (DataRowBean) obj;
			if (getDomain().getId() != null
					&& getDomain().getId().equals(
							dataRowBean.getDomain().getId()))
				return true;
		}
		return false;
	}

	/**
	 * Returns hash code for the primary key of the object
	 */
	public int hashCode() {
		if (getDomain().getId() != null)
			return getDomain().getId().hashCode();
		return 0;
	}

	public List<Condition> getConditions() {
		return conditions;
	}
	
	public void addConditionColumn(Condition condition) {
		//condition.setDataRow(domain);
		if (conditions.contains(condition)) {
			for (Condition thisCondition : conditions) {
				if (thisCondition.getId().equals(condition.getId())) {
					thisCondition.setName(condition.getName());
					thisCondition.setValueType(condition.getValueType());
					thisCondition.setValueUnit(condition.getValueUnit());
				}
			}
		} else {
			conditions.add(condition);
		}
	}
}
