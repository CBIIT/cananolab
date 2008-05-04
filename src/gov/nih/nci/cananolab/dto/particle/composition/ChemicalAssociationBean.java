package gov.nih.nci.cananolab.dto.particle.composition;

import gov.nih.nci.cananolab.domain.common.LabFile;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.chemicalassociation.Attachment;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.chemicalassociation.ChemicalAssociation;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.chemicalassociation.OtherChemicalAssociation;
import gov.nih.nci.cananolab.dto.common.LabFileBean;
import gov.nih.nci.cananolab.util.ClassUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ChemicalAssociationBean {
	private String type;

	private String description;

	private String className;

	private ChemicalAssociation domainAssociation;

	private Attachment attachment = new Attachment();

	private List<LabFileBean> files = new ArrayList<LabFileBean>();

	private AssociatedElementBean associatedElementA = new AssociatedElementBean();

	private AssociatedElementBean associatedElementB = new AssociatedElementBean();

	private Long assocId;
	
	public ChemicalAssociationBean() {
	}

	public ChemicalAssociationBean(ChemicalAssociation chemicalAssociation) {
		domainAssociation = chemicalAssociation;
		assocId=domainAssociation.getId();
		if (chemicalAssociation instanceof Attachment) {
			attachment = (Attachment) chemicalAssociation;
		}
		description = chemicalAssociation.getDescription();
		className = ClassUtils.getShortClassName(chemicalAssociation.getClass()
				.getName());
		if (chemicalAssociation.getLabFileCollection() != null) {
			for (LabFile file : chemicalAssociation.getLabFileCollection()) {
				files.add(new LabFileBean(file));
			}
		}
		associatedElementA = new AssociatedElementBean(chemicalAssociation
				.getAssociatedElementA());
		associatedElementB = new AssociatedElementBean(chemicalAssociation
				.getAssociatedElementB());
	}

	public ChemicalAssociation getDomainAssociation() {
		return domainAssociation;
	}

	public void setupDomainAssociation(Map<String, String> typeToClass,
			String createdBy) throws Exception {
		className = typeToClass.get(type);
		Class clazz = ClassUtils.getFullClass(className);
		if (domainAssociation == null || !clazz.equals(domainAssociation.getClass())) {	
			domainAssociation = (ChemicalAssociation) clazz.newInstance();
		}
		if (domainAssociation.getId() == null) {
			domainAssociation.setCreatedBy(createdBy);
			domainAssociation.setCreatedDate(new Date());
		}
		
		attachment.setCreatedBy(domainAssociation.getCreatedBy());
		attachment.setCreatedDate(domainAssociation.getCreatedDate());

		if (className.equals("Attachment")) {
			domainAssociation = attachment;
		}
		if (assocId!=null) {
			domainAssociation.setId(assocId);
		}
		domainAssociation.setDescription(description);
		associatedElementA.setupDomainElement(typeToClass, createdBy);
		associatedElementB.setupDomainElement(typeToClass, createdBy);
		domainAssociation.setAssociatedElementA(associatedElementA
				.getDomainElement());
		domainAssociation.setAssociatedElementB(associatedElementB
				.getDomainElement());
		if (domainAssociation.getLabFileCollection() != null) {
			domainAssociation.getLabFileCollection().clear();
		} else {
			domainAssociation.setLabFileCollection(new HashSet<LabFile>());
		}
		for (LabFileBean file : files) {
			file.getDomainFile().setCreatedBy(createdBy);
			file.getDomainFile().setCreatedDate(new Date());
			domainAssociation.getLabFileCollection().add(file.getDomainFile());
		}
	}

	public String getType() {
		return type;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public List<LabFileBean> getFiles() {
		return files;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassName() {
		return className;
	}

	public AssociatedElementBean getAssociatedElementA() {
		return associatedElementA;
	}

	public AssociatedElementBean getAssociatedElementB() {
		return associatedElementB;
	}

	public void addFile() {
		files.add(new LabFileBean());
	}

	public void removeFile(int ind) {
		files.remove(ind);
	}
	
	public void setType(String type) {
		this.type=type;
	}

	public void updateType(Map<String, String> classToType) {
		if (domainAssociation instanceof OtherChemicalAssociation) {
			type = ((OtherChemicalAssociation) domainAssociation).getType();
		} else {
			type = classToType.get(className);
		}
		associatedElementA.updateType(classToType);
		associatedElementB.updateType(classToType);
	}
}
