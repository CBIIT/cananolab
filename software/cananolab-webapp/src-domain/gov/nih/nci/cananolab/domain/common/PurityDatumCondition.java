package gov.nih.nci.cananolab.domain.common;

import gov.nih.nci.cananolab.domain.particle.SynthesisPurity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 **/

public class PurityDatumCondition implements Serializable {
    /**
     * An attribute to allow serialization of the domain objects
     */
    private static final long serialVersionUID = 1234567890L;

    private String createdBy;

    private Date createdDate;

    private Long id;

    private String name;

    private String property;

    private String value;

    private String valueType;

    private String valueUnit;

    private String operand;

    private PurityDatum purityDatum;

    private Long purityDatumPkId;

    public PurityDatum getPurityDatum() {
        return purityDatum;
    }

    public void setPurityDatum(PurityDatum purityDatum) {
        this.purityDatum = purityDatum;
        this.purityDatumPkId = purityDatum.getId();
    }

    /**
     * Retrieves the value of the createdBy attribute
     *
     * @return createdBy
     **/

    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of createdBy attribute
     **/

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Retrieves the value of the createdDate attribute
     *
     * @return createdDate
     **/

    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of createdDate attribute
     **/

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Retrieves the value of the id attribute
     *
     * @return id
     **/

    public Long getId() {
        return id;
    }

    /**
     * Sets the value of id attribute
     **/

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the value of the name attribute
     *
     * @return name
     **/

    public String getName() {
        return name;
    }

    /**
     * Sets the value of name attribute
     **/

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the value of the property attribute
     *
     * @return property
     **/

    public String getProperty() {
        return property;
    }

    /**
     * Sets the value of property attribute
     **/

    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Retrieves the value of the value attribute
     *
     * @return value
     **/

    public String getValue() {
        return value;
    }

    /**
     * Sets the value of value attribute
     **/

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value of the valueType attribute
     *
     * @return valueType
     **/

    public String getValueType() {
        return valueType;
    }

    /**
     * Sets the value of valueType attribute
     **/

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    /**
     * Retrieves the value of the valueUnit attribute
     *
     * @return valueUnit
     **/

    public String getValueUnit() {
        return valueUnit;
    }

    /**
     * Sets the value of valueUnit attribute
     **/

    public void setValueUnit(String valueUnit) {
        this.valueUnit = valueUnit;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public Long getPurityDatumPkId() {
        return purityDatumPkId;
    }

    private void setPurityDatumPkId(Long purityDatumPkId) {
        this.purityDatumPkId = purityDatumPkId;
    }

    /**
     * Compares <code>obj</code> to it self and returns true if they both are same
     *
     * @param obj
     **/
    public boolean equals(Object obj) {
        if (obj instanceof PurityDatumCondition) {
            PurityDatumCondition c = (PurityDatumCondition) obj;
            return getId() != null && getId().equals(c.getId());
        }
        return false;
    }

    /**
     * Returns hash code for the primary key of the object
     **/
    public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
        return 0;
    }

}