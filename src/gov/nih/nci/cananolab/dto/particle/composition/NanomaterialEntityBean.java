package gov.nih.nci.cananolab.dto.particle.composition;

import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.domain.function.TargetingFunction;
import gov.nih.nci.cananolab.domain.nanomaterial.Biopolymer;
import gov.nih.nci.cananolab.domain.nanomaterial.CarbonNanotube;
import gov.nih.nci.cananolab.domain.nanomaterial.Dendrimer;
import gov.nih.nci.cananolab.domain.nanomaterial.Emulsion;
import gov.nih.nci.cananolab.domain.nanomaterial.Fullerene;
import gov.nih.nci.cananolab.domain.nanomaterial.Liposome;
import gov.nih.nci.cananolab.domain.nanomaterial.OtherNanomaterialEntity;
import gov.nih.nci.cananolab.domain.nanomaterial.Polymer;
import gov.nih.nci.cananolab.domain.particle.ComposingElement;
import gov.nih.nci.cananolab.domain.particle.Function;
import gov.nih.nci.cananolab.domain.particle.NanomaterialEntity;
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
import java.util.Map;

/**
 * Represents the view bean for the NanomaterialEntity domain object
 *
 * @author pansu
 *
 */
public class NanomaterialEntityBean extends BaseCompositionEntityBean {
	private Polymer polymer = new Polymer();

	private Biopolymer biopolymer = new Biopolymer();

	private Dendrimer dendrimer = new Dendrimer();

	private CarbonNanotube carbonNanotube = new CarbonNanotube();

	private Liposome liposome = new Liposome();

	private Emulsion emulsion = new Emulsion();

	private Fullerene fullerene = new Fullerene();

	private List<ComposingElementBean> composingElements = new ArrayList<ComposingElementBean>();

	private NanomaterialEntity domainEntity;

	private boolean withProperties = false;

	private ComposingElementBean theComposingElement = new ComposingElementBean();

	public NanomaterialEntityBean() {
	}

	public NanomaterialEntityBean(NanomaterialEntity nanomaterialEntity) {
		description = nanomaterialEntity.getDescription();
		domainEntity = nanomaterialEntity;
		if (domainEntity instanceof Biopolymer) {
			biopolymer = (Biopolymer) domainEntity;
			withProperties = true;
		} else if (domainEntity instanceof Dendrimer) {
			dendrimer = (Dendrimer) domainEntity;
			withProperties = true;
		} else if (domainEntity instanceof CarbonNanotube) {
			carbonNanotube = (CarbonNanotube) domainEntity;
			withProperties = true;
		} else if (domainEntity instanceof Liposome) {
			liposome = (Liposome) domainEntity;
			withProperties = true;
		} else if (domainEntity instanceof Emulsion) {
			emulsion = (Emulsion) domainEntity;
			withProperties = true;
		} else if (domainEntity instanceof Polymer) {
			polymer = (Polymer) domainEntity;
			withProperties = true;
		} else if (domainEntity instanceof Fullerene) {
			fullerene = (Fullerene) domainEntity;
			withProperties = true;
		}
		className = ClassUtils.getShortClassName(nanomaterialEntity.getClass()
				.getName());
		for (ComposingElement composingElement : nanomaterialEntity
				.getComposingElementCollection()) {
			composingElements.add(new ComposingElementBean(composingElement));
		}
		Collections.sort(composingElements,
				new Comparators.ComposingElementBeanDateComparator());
		if (nanomaterialEntity.getFileCollection() != null) {
			for (File file : nanomaterialEntity.getFileCollection()) {
				files.add(new FileBean(file));
			}
		}
		Collections.sort(files, new Comparators.FileBeanDateComparator());
	}

	public NanomaterialEntity getDomainCopy() {
		NanomaterialEntity copy = (NanomaterialEntity) ClassUtils.deepCopy(this
				.getDomainEntity());
		// clear Ids, reset createdBy and createdDate, add prefix to
		copy.setId(null);
		copy.setCreatedBy(Constants.AUTO_COPY_ANNOTATION_PREFIX);
		copy.setCreatedDate(new Date());
		if (copy.getComposingElementCollection().isEmpty()) {
			copy.setComposingElementCollection(null);
		} else {
			// have to create a new collection otherwise Hibernate complains
			Collection<ComposingElement> ces = copy
					.getComposingElementCollection();
			copy.setComposingElementCollection(new HashSet<ComposingElement>());
			copy.getComposingElementCollection().addAll(ces);
			for (ComposingElement ce : copy.getComposingElementCollection()) {
				ce.setId(null);
				ce.setCreatedBy(Constants.AUTO_COPY_ANNOTATION_PREFIX);
				ce.setCreatedDate(new Date());
				if (ce.getInherentFunctionCollection().isEmpty()) {
					ce.setInherentFunctionCollection(null);
				} else {
					Collection<Function> functions = ce
							.getInherentFunctionCollection();
					ce.setInherentFunctionCollection(new HashSet<Function>());
					ce.getInherentFunctionCollection().addAll(functions);
					for (Function function : ce.getInherentFunctionCollection()) {
						function.setId(null);
						function
								.setCreatedBy(Constants.AUTO_COPY_ANNOTATION_PREFIX);
						function.setCreatedDate(new Date());
						if (function instanceof TargetingFunction) {
							((TargetingFunction) function)
									.setTargetCollection(null);
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

	public Dendrimer getDendrimer() {
		return dendrimer;
	}

	public Biopolymer getBiopolymer() {
		return biopolymer;
	}

	public CarbonNanotube getCarbonNanotube() {
		return carbonNanotube;
	}

	public List<ComposingElementBean> getComposingElements() {
		return composingElements;
	}

	public Emulsion getEmulsion() {
		return emulsion;
	}

	public Fullerene getFullerene() {
		return fullerene;
	}

	public Liposome getLiposome() {
		return liposome;
	}

	public Polymer getPolymer() {
		return polymer;
	}

	public NanomaterialEntity getDomainEntity() {
		return domainEntity;
	}

	public void setupDomainEntity(Map<String, String> typeToClass,
			String createdBy, String internalUriPath) throws Exception {
		className = typeToClass.get(type.toLowerCase());
		Class clazz = null;
		if (className == null) {
			clazz = OtherNanomaterialEntity.class;
		} else {
			clazz = ClassUtils.getFullClass(className);
		}
		if (domainEntity == null) {
			domainEntity = (NanomaterialEntity) clazz.newInstance();
		}
		if (domainEntity instanceof OtherNanomaterialEntity) {
			((OtherNanomaterialEntity) domainEntity).setType(type);
		} else if (domainEntity instanceof Biopolymer) {
			domainEntity = biopolymer;
		} else if (domainEntity instanceof Dendrimer) {
			domainEntity = dendrimer;
		} else if (domainEntity instanceof CarbonNanotube) {
			domainEntity = carbonNanotube;
		} else if (domainEntity instanceof Liposome) {
			domainEntity = liposome;
		} else if (domainEntity instanceof Emulsion) {
			domainEntity = emulsion;
		} else if (domainEntity instanceof Polymer) {
			domainEntity = polymer;
		} else if (domainEntity instanceof Fullerene) {
			domainEntity = fullerene;
		}
		if (domainEntity.getId() == null
				|| domainEntity.getCreatedBy() != null
				&& domainEntity.getCreatedBy().equals(
						Constants.AUTO_COPY_ANNOTATION_PREFIX)) {
			domainEntity.setCreatedBy(createdBy);
			domainEntity.setCreatedDate(new Date());
		}
		domainEntity.setDescription(description);
		if (domainEntity.getComposingElementCollection() != null) {
			domainEntity.getComposingElementCollection().clear();
		} else {
			domainEntity
					.setComposingElementCollection(new HashSet<ComposingElement>());
		}
		int i = 0;
		for (ComposingElementBean composingElementBean : composingElements) {
			composingElementBean.setupDomain(typeToClass, createdBy, i);
			ComposingElement domain = composingElementBean.getDomain();
			if (domain.getId() == null) {
				domain.setCreatedBy(createdBy);
				domain.setCreatedDate(new Date());
			}
			domain.setNanomaterialEntity(domainEntity);
			domainEntity.getComposingElementCollection().add(domain);
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

	public void addComposingElement(ComposingElementBean element) {
		// if an old one exists, remove it first
		int index = composingElements.indexOf(element);
		if (index != -1) {
			composingElements.remove(element);
			// retain the original order
			composingElements.add(index, element);
		} else {
			composingElements.add(element);
		}
	}

	public void removeComposingElement(ComposingElementBean element) {
		composingElements.remove(element);
	}

	public NanomaterialEntityBean copy() throws Exception {
		NanomaterialEntityBean copiedEntity = (NanomaterialEntityBean) ClassUtils
				.deepCopy(this);
		return copiedEntity;
	}

	public void updateType(Map<String, String> classToType) {
		if (domainEntity instanceof OtherNanomaterialEntity) {
			type = ((OtherNanomaterialEntity) domainEntity).getType();
		} else {
			type = classToType.get(className);
		}
		// set composing element function type
		for (ComposingElementBean compElementBean : getComposingElements()) {
			for (FunctionBean functionBean : compElementBean
					.getInherentFunctions()) {
				functionBean.updateType(classToType);
			}
		}
	}

	public boolean isWithProperties() {
		return withProperties;
	}

	public ComposingElementBean getTheComposingElement() {
		return theComposingElement;
	}

	public void setTheComposingElement(ComposingElementBean theComposingElement) {
		this.theComposingElement = theComposingElement;
	}
}
