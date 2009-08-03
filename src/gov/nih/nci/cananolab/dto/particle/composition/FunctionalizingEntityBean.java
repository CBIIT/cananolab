package gov.nih.nci.cananolab.dto.particle.composition;

import gov.nih.nci.cananolab.domain.agentmaterial.Antibody;
import gov.nih.nci.cananolab.domain.agentmaterial.Biopolymer;
import gov.nih.nci.cananolab.domain.agentmaterial.OtherFunctionalizingEntity;
import gov.nih.nci.cananolab.domain.agentmaterial.SmallMolecule;
import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.domain.function.ImagingFunction;
import gov.nih.nci.cananolab.domain.function.Target;
import gov.nih.nci.cananolab.domain.function.TargetingFunction;
import gov.nih.nci.cananolab.domain.particle.ActivationMethod;
import gov.nih.nci.cananolab.domain.particle.Function;
import gov.nih.nci.cananolab.domain.particle.FunctionalizingEntity;
import gov.nih.nci.cananolab.dto.common.FileBean;
import gov.nih.nci.cananolab.util.ClassUtils;
import gov.nih.nci.cananolab.util.Comparators;
import gov.nih.nci.cananolab.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Represents the view bean for the FunctionalizingEntity domain object
 * 
 * @author pansu
 * 
 */
public class FunctionalizingEntityBean extends BaseCompositionEntityBean {
	private String molecularFormula;

	private String molecularFormulaType;

	private String name;

	private String value;

	private String valueUnit;

	private String pubChemDataSourceName;

	private String pubChemId;

	private SmallMolecule smallMolecule = new SmallMolecule();

	private Antibody antibody = new Antibody();

	private Biopolymer biopolymer = new Biopolymer();

	private FunctionalizingEntity domainEntity;

	private List<FunctionBean> functions = new ArrayList<FunctionBean>();

	private ActivationMethod activationMethod = new ActivationMethod();

	private boolean withProperties = false;

	private boolean withImagingFunction = false;

	private boolean withTargetingFunction = false;

	private FunctionBean theFunction = new FunctionBean();

	public FunctionalizingEntityBean() {
	}

	public FunctionalizingEntityBean(FunctionalizingEntity functionalizingEntity) {
		description = functionalizingEntity.getDescription();
		name = functionalizingEntity.getName();
		molecularFormula = functionalizingEntity.getMolecularFormula();
		molecularFormulaType = functionalizingEntity.getMolecularFormulaType();
		if (functionalizingEntity.getValue() != null) {
			value = functionalizingEntity.getValue().toString();
		}
		valueUnit = functionalizingEntity.getValueUnit();
		pubChemDataSourceName = functionalizingEntity
				.getPubChemDataSourceName();
		if (functionalizingEntity.getPubChemId() != null) {
			pubChemId = functionalizingEntity.getPubChemId().toString();
		}
		domainEntity = functionalizingEntity;
		if (functionalizingEntity instanceof Antibody) {
			antibody = (Antibody) functionalizingEntity;
			withProperties = true;
		} else if (functionalizingEntity instanceof SmallMolecule) {
			smallMolecule = (SmallMolecule) functionalizingEntity;
			withProperties = true;
		} else if (functionalizingEntity instanceof Biopolymer) {
			biopolymer = (Biopolymer) functionalizingEntity;
			withProperties = true;
		}
		className = ClassUtils.getShortClassName(functionalizingEntity
				.getClass().getName());
		if (functionalizingEntity.getFunctionCollection() != null) {
			for (Function function : functionalizingEntity
					.getFunctionCollection()) {
				functions.add(new FunctionBean(function));
				if (function instanceof ImagingFunction) {
					withImagingFunction = true;
				}
				if (function instanceof TargetingFunction) {
					withTargetingFunction = true;
				}
			}
		}
		Collections.sort(functions,
				new Comparators.FunctionBeanDateComparator());

		if (functionalizingEntity.getActivationMethod() != null) {
			activationMethod = functionalizingEntity.getActivationMethod();
		}
		if (functionalizingEntity.getFileCollection() != null) {
			for (File file : functionalizingEntity.getFileCollection()) {
				files.add(new FileBean(file));
			}
		}
		Collections.sort(files, new Comparators.FileBeanDateComparator());
		updateType();
	}

	public FunctionalizingEntity getDomainCopy() {
		FunctionalizingEntity copy = (FunctionalizingEntity) ClassUtils
				.deepCopy(domainEntity);
		// clear Id
		copy.setId(null);
		copy.setCreatedBy(Constants.AUTO_COPY_ANNOTATION_PREFIX);
		copy.setCreatedDate(new Date());
		if (copy.getActivationMethod() != null) {
			copy.getActivationMethod().setId(null);
		}
		if (copy.getFunctionCollection().isEmpty()) {
			copy.setFunctionCollection(null);
		} else {
			Collection<Function> functions = copy.getFunctionCollection();
			copy.setFunctionCollection(new HashSet<Function>());
			copy.getFunctionCollection().addAll(functions);
			for (Function function : copy.getFunctionCollection()) {
				function.setId(null);
				function.setCreatedBy(Constants.AUTO_COPY_ANNOTATION_PREFIX);
				function.setCreatedDate(new Date());
				if (function instanceof TargetingFunction) {
					if (((TargetingFunction) function).getTargetCollection()
							.isEmpty()) {
						((TargetingFunction) function)
								.setTargetCollection(null);
					} else {
						Collection<Target> targets = ((TargetingFunction) function)
								.getTargetCollection();
						((TargetingFunction) function)
								.setTargetCollection(new HashSet<Target>());
						((TargetingFunction) function).getTargetCollection()
								.addAll(targets);
						for (Target target : ((TargetingFunction) function)
								.getTargetCollection()) {
							target.setId(null);
							target
									.setCreatedBy(Constants.AUTO_COPY_ANNOTATION_PREFIX);
							target.setCreatedDate(new Date());
						}
					}
				}
			}
		}
		if (copy.getFileCollection().isEmpty()) {
			copy.setFileCollection(null);
		} else {
			Collection<File> files = copy.getFileCollection();
			copy.setFileCollection(new HashSet<File>());
			copy.getFileCollection().addAll(files);
			for (File file : copy.getFileCollection()) {
				file.setId(null);
				file.setCreatedBy(Constants.AUTO_COPY_ANNOTATION_PREFIX);
				file.setCreatedDate(new Date());
			}
		}
		return copy;
	}

	public FunctionalizingEntity getDomainEntity() {
		return domainEntity;
	}

	public Antibody getAntibody() {
		return antibody;
	}

	public Biopolymer getBiopolymer() {
		return biopolymer;
	}

	public SmallMolecule getSmallMolecule() {
		return smallMolecule;
	}

	public List<FunctionBean> getFunctions() {
		return functions;
	}

	public String getMolecularFormula() {
		return molecularFormula;
	}

	public void setMolecularFormula(String molecularFormula) {
		this.molecularFormula = molecularFormula;
	}

	public String getMolecularFormulaType() {
		return molecularFormulaType;
	}

	public void setMolecularFormulaType(String molecularFormulaType) {
		this.molecularFormulaType = molecularFormulaType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueUnit() {
		return valueUnit;
	}

	public void setValueUnit(String valueUnit) {
		this.valueUnit = valueUnit;
	}

	public ActivationMethod getActivationMethod() {
		return activationMethod;
	}

	public void setupDomainEntity(String createdBy, String internalUriPath)
			throws Exception {
		className = ClassUtils.getShortClassNameFromDisplayName(type);
		Class clazz = null;
		if (className == null) {
			clazz = OtherFunctionalizingEntity.class;
		} else {
			clazz = ClassUtils.getFullClass("agentmaterial." + className);
		}
		if (domainEntity == null) {
			domainEntity = (FunctionalizingEntity) clazz.newInstance();
		}
		if (domainEntity instanceof OtherFunctionalizingEntity) {
			((OtherFunctionalizingEntity) domainEntity).setType(type);
		} else if (domainEntity instanceof Antibody) {
			domainEntity = antibody;
		} else if (domainEntity instanceof SmallMolecule) {
			domainEntity = smallMolecule;
		} else if (domainEntity instanceof Biopolymer) {
			domainEntity = biopolymer;
		}
		domainEntity.setDescription(description);
		domainEntity.setMolecularFormula(molecularFormula);
		domainEntity.setMolecularFormulaType(molecularFormulaType);
		domainEntity.setName(name);
		if (value != null && value.trim().length() > 0) {
			domainEntity.setValue(new Float(value));
		} else {
			domainEntity.setValue(null);
		}
		domainEntity.setValueUnit(valueUnit);
		domainEntity.setPubChemDataSourceName(pubChemDataSourceName);
		if (pubChemId != null && pubChemId.trim().length() > 0) {
			domainEntity.setPubChemId(new Long(pubChemId));
		} else {
			domainEntity.setPubChemId(null);
		}
		if (activationMethod != null
				&& ((activationMethod.getActivationEffect() != null && activationMethod
						.getActivationEffect().trim().length() > 0) || (activationMethod
						.getType() != null && activationMethod.getType().trim()
						.length() > 0))) {
			domainEntity.setActivationMethod(activationMethod);
		} else {
			domainEntity.setActivationMethod(null);
		}

		if (domainEntity.getId() == null
				|| domainEntity.getCreatedBy() != null
				&& domainEntity.getCreatedBy().equals(
						Constants.AUTO_COPY_ANNOTATION_PREFIX)) {
			domainEntity.setCreatedBy(createdBy);
			domainEntity.setCreatedDate(new Date());
		}
		if (domainEntity.getFunctionCollection() != null) {
			domainEntity.getFunctionCollection().clear();
		} else {
			domainEntity.setFunctionCollection(new HashSet<Function>());
		}
		int i = 0;
		for (FunctionBean functionBean : functions) {
			functionBean.setupDomainFunction(createdBy, i);
			domainEntity.getFunctionCollection().add(
					functionBean.getDomainFunction());
			i++;
		}
		if (domainEntity.getFileCollection() != null) {
			domainEntity.getFileCollection().clear();
		} else {
			domainEntity.setFileCollection(new HashSet<File>());
		}
		int j = 0;
		for (FileBean file : files) {
			file.setupDomainFile(internalUriPath, createdBy, j);
			domainEntity.getFileCollection().add(file.getDomainFile());
			j++;
		}
	}

	private void updateType() {
		if (domainEntity instanceof OtherFunctionalizingEntity) {
			type = ((OtherFunctionalizingEntity) domainEntity).getType();
		} else {
			type = ClassUtils.getDisplayName(className);
		}
	}

	public String getMolecularFormulaDisplayName() {
		StringBuffer buffer = new StringBuffer();
		if (molecularFormula != null && molecularFormula.length() > 0) {
			buffer.append(molecularFormula);
			if (molecularFormulaType != null
					&& molecularFormulaType.length() > 0) {
				buffer.append(" (");
				buffer.append(molecularFormulaType);
				buffer.append(")");
			}
		}
		return buffer.toString();
	}

	public String[] getFunctionDisplayNames() {
		List<String> displayNames = new ArrayList<String>();
		for (FunctionBean function : functions) {
			displayNames.add(function.getDisplayName());
		}
		if (displayNames.isEmpty()) {
			return null;
		}
		return displayNames.toArray(new String[displayNames.size()]);
	}

	public boolean isWithProperties() {
		return withProperties;
	}

	public String getActivationMethodDisplayName() {
		StringBuffer buffer = new StringBuffer();
		if (getDomainEntity().getActivationMethod() != null) {
			if (getDomainEntity().getActivationMethod().getType() != null) {
				buffer
						.append(getDomainEntity().getActivationMethod()
								.getType());
			}
			if (getDomainEntity().getActivationMethod().getActivationEffect() != null) {
				buffer.append(", ");
				buffer.append(getDomainEntity().getActivationMethod()
						.getActivationEffect());
			}
		}
		return buffer.toString();
	}

	// for use in chemical association submission form
	public String getDisplayName() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getType());
		if (getDomainEntity().getName() != null) {
			buffer.append(" (name: ");
			buffer.append(getDomainEntity().getName());
			if (getDomainEntity().getValue() != null) {
				buffer.append(", amount: ");
				buffer.append(getDomainEntity().getValue());
				if (getDomainEntity().getValueUnit() != null) {
					buffer.append(" ");
					buffer.append(getDomainEntity().getValueUnit());
				}
			}
			buffer.append(")");
		}
		return buffer.toString();
	}

	public boolean isWithImagingFunction() {
		return withImagingFunction;
	}

	public boolean isWithTargetingFunction() {
		return withTargetingFunction;
	}

	public FunctionBean getTheFunction() {
		return theFunction;
	}

	public void setTheFunction(FunctionBean theFunction) {
		this.theFunction = theFunction;
	}

	public void addFunction(FunctionBean function) {
		int index = functions.indexOf(function);
		if (index != -1) {
			functions.remove(function);
			// retain the original order
			functions.add(index, function);
		} else {
			functions.add(function);
		}
	}

	public void removeFunction(FunctionBean function) {
		functions.remove(function);
	}

	// used for DWR ajax in bodySubmitChemicalAssociation.jsp
	public String getDomainId() {
		if (getDomainEntity().getId() != null) {
			return getDomainEntity().getId().toString();
		} else {
			return null;
		}
	}

	public String getPubChemDataSourceName() {
		return pubChemDataSourceName;
	}

	public String getPubChemId() {
		return pubChemId;
	}

	public void setPubChemDataSourceName(String pubChemDataSourceName) {
		this.pubChemDataSourceName = pubChemDataSourceName;
	}

	public void setPubChemId(String pubChemId) {
		this.pubChemId = pubChemId;
	}
}
