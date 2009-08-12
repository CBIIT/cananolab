package gov.nih.nci.cananolab.service.sample.helper;

import gov.nih.nci.cananolab.domain.agentmaterial.OtherFunctionalizingEntity;
import gov.nih.nci.cananolab.domain.common.Keyword;
import gov.nih.nci.cananolab.domain.common.Organization;
import gov.nih.nci.cananolab.domain.common.PointOfContact;
import gov.nih.nci.cananolab.domain.function.OtherFunction;
import gov.nih.nci.cananolab.domain.nanomaterial.OtherNanomaterialEntity;
import gov.nih.nci.cananolab.domain.particle.Characterization;
import gov.nih.nci.cananolab.domain.particle.ComposingElement;
import gov.nih.nci.cananolab.domain.particle.Function;
import gov.nih.nci.cananolab.domain.particle.FunctionalizingEntity;
import gov.nih.nci.cananolab.domain.particle.NanomaterialEntity;
import gov.nih.nci.cananolab.domain.particle.Sample;
import gov.nih.nci.cananolab.dto.common.PointOfContactBean;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.AdvancedSampleSearchBean;
import gov.nih.nci.cananolab.dto.particle.CharacterizationQueryBean;
import gov.nih.nci.cananolab.dto.particle.CompositionQueryBean;
import gov.nih.nci.cananolab.exception.NoAccessException;
import gov.nih.nci.cananolab.service.security.AuthorizationService;
import gov.nih.nci.cananolab.system.applicationservice.CustomizedApplicationService;
import gov.nih.nci.cananolab.util.ClassUtils;
import gov.nih.nci.cananolab.util.Comparators;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.StringUtils;
import gov.nih.nci.cananolab.util.TextMatchMode;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Helper class providing implementations of search methods needed for both
 * local implementation of SampleService and grid service *
 * 
 * @author pansu, tanq
 * 
 */
public class SampleServiceHelper {
	private AuthorizationService authService;
	private static Logger logger = Logger.getLogger(SampleServiceHelper.class);

	public SampleServiceHelper() {
		try {
			authService = new AuthorizationService(Constants.CSM_APP_NAME);
		} catch (Exception e) {
			logger.error("Can't create authorization service: " + e);
		}
	}

	public List<Sample> findSamplesBy(String samplePointOfContact,
			String[] nanomaterialEntityClassNames,
			String[] otherNanomaterialEntityTypes,
			String[] functionalizingEntityClassNames,
			String[] otherFunctionalizingEntityTypes,
			String[] functionClassNames, String[] otherFunctionTypes,
			String[] characterizationClassNames,
			String[] otherCharacterizationTypes, String[] wordList,
			UserBean user) throws Exception {
		List<Sample> samples = new ArrayList<Sample>();

		// can't query for the entire Sample object due to
		// limitations in pagination in SDK
		DetachedCriteria crit = DetachedCriteria.forClass(Sample.class)
				.setProjection(Projections.distinct(Property.forName("name")));

		if (samplePointOfContact != null && samplePointOfContact.length() > 0) {
			TextMatchMode pocMatchMode = new TextMatchMode(samplePointOfContact);
			Disjunction disjunction = Restrictions.disjunction();
			crit.createAlias("primaryPointOfContact", "pointOfContact");
			crit.createAlias("pointOfContact.organization", "organization");
			crit.createAlias("otherPointOfContactCollection", "otherPoc",
					CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("otherPoc.organization", "otherOrg",
					CriteriaSpecification.LEFT_JOIN);
			String critStrs[] = { "pointOfContact.lastName",
					"pointOfContact.firstName", "pointOfContact.role",
					"organization.name", "otherPoc.lastName",
					"otherPoc.firstName", "otherOrg.name" };
			for (String critStr : critStrs) {
				Criterion pocCrit = Restrictions.ilike(critStr, pocMatchMode
						.getUpdatedText(), pocMatchMode.getMatchMode());
				disjunction.add(pocCrit);
			}
			crit.add(disjunction);
		}

		// join composition
		if (nanomaterialEntityClassNames != null
				&& nanomaterialEntityClassNames.length > 0
				|| otherNanomaterialEntityTypes != null
				&& otherNanomaterialEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0
				|| functionalizingEntityClassNames != null
				&& functionalizingEntityClassNames.length > 0
				|| otherFunctionalizingEntityTypes != null
				&& otherFunctionalizingEntityTypes.length > 0) {
			crit.createAlias("sampleComposition", "comp",
					CriteriaSpecification.LEFT_JOIN);
		}
		// join nanomaterial entity
		if (nanomaterialEntityClassNames != null
				&& nanomaterialEntityClassNames.length > 0
				|| otherNanomaterialEntityTypes != null
				&& otherNanomaterialEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			crit.createAlias("comp.nanomaterialEntityCollection", "nanoEntity",
					CriteriaSpecification.LEFT_JOIN);
		}

		// join functionalizing entity
		if (functionalizingEntityClassNames != null
				&& functionalizingEntityClassNames.length > 0
				|| otherFunctionalizingEntityTypes != null
				&& otherFunctionalizingEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			crit.createAlias("comp.functionalizingEntityCollection",
					"funcEntity", CriteriaSpecification.LEFT_JOIN);
		}

		// nanomaterial entity
		if (nanomaterialEntityClassNames != null
				&& nanomaterialEntityClassNames.length > 0
				|| otherNanomaterialEntityTypes != null
				&& otherNanomaterialEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			Disjunction disjunction = Restrictions.disjunction();
			if (nanomaterialEntityClassNames != null
					&& nanomaterialEntityClassNames.length > 0) {
				Criterion nanoEntityCrit = Restrictions.in("nanoEntity.class",
						nanomaterialEntityClassNames);
				disjunction.add(nanoEntityCrit);
			}
			if (otherNanomaterialEntityTypes != null
					&& otherNanomaterialEntityTypes.length > 0) {
				Criterion otherNanoCrit1 = Restrictions.eq("nanoEntity.class",
						"OtherNanomaterialEntity");
				Criterion otherNanoCrit2 = Restrictions.in("nanoEntity.type",
						otherNanomaterialEntityTypes);
				Criterion otherNanoCrit = Restrictions.and(otherNanoCrit1,
						otherNanoCrit2);
				disjunction.add(otherNanoCrit);
			}
			crit.add(disjunction);
		}

		// functionalizing entity
		// need to turn class names into integers in order for the .class
		// clause to work
		if (functionalizingEntityClassNames != null
				&& functionalizingEntityClassNames.length > 0
				|| otherFunctionalizingEntityTypes != null
				&& otherFunctionalizingEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			Disjunction disjunction = Restrictions.disjunction();
			if (functionalizingEntityClassNames != null
					&& functionalizingEntityClassNames.length > 0) {
				Integer[] functionalizingEntityClassNameIntegers = this
						.convertToFunctionalizingEntityClassOrderNumber(functionalizingEntityClassNames);
				Criterion funcEntityCrit = Restrictions.in("funcEntity.class",
						functionalizingEntityClassNameIntegers);
				disjunction.add(funcEntityCrit);
			}
			if (otherFunctionalizingEntityTypes != null
					&& otherFunctionalizingEntityTypes.length > 0) {
				Integer classOrderNumber = Constants.FUNCTIONALIZING_ENTITY_SUBCLASS_ORDER_MAP
						.get("otherFunctionalizingEntity");
				Criterion otherFuncCrit1 = Restrictions.eq("funcEntity.class",
						classOrderNumber);
				Criterion otherFuncCrit2 = Restrictions.in("funcEntity.type",
						otherNanomaterialEntityTypes);
				Criterion otherFuncCrit = Restrictions.and(otherFuncCrit1,
						otherFuncCrit2);
				disjunction.add(otherFuncCrit);
			}
			crit.add(disjunction);
		}

		// function
		if (functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			Disjunction disjunction = Restrictions.disjunction();
			crit.createAlias("nanoEntity.composingElementCollection",
					"compElement", CriteriaSpecification.LEFT_JOIN)
					.createAlias("compElement.inherentFunctionCollection",
							"inFunc", CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("funcEntity.functionCollection", "func",
					CriteriaSpecification.LEFT_JOIN);
			if (functionClassNames != null && functionClassNames.length > 0) {
				Criterion funcCrit1 = Restrictions.in("inFunc.class",
						functionClassNames);
				Criterion funcCrit2 = Restrictions.in("func.class",
						functionClassNames);
				disjunction.add(funcCrit1).add(funcCrit2);
			}
			if (otherFunctionTypes != null && otherFunctionTypes.length > 0) {
				Criterion otherFuncCrit1 = Restrictions.and(Restrictions.eq(
						"inFunc.class", "OtherFunction"), Restrictions.in(
						"inFunc.type", otherFunctionTypes));
				Criterion otherFuncCrit2 = Restrictions.and(Restrictions.eq(
						"func.class", "OtherFunction"), Restrictions.in(
						"func.type", otherFunctionTypes));
				disjunction.add(otherFuncCrit1).add(otherFuncCrit2);
			}
			crit.add(disjunction);
		}

		// join characterization
		if (characterizationClassNames != null
				&& characterizationClassNames.length > 0 || wordList != null
				&& wordList.length > 0) {
			crit.createAlias("characterizationCollection", "chara",
					CriteriaSpecification.LEFT_JOIN);
		}

		// characterization
		if (characterizationClassNames != null
				&& characterizationClassNames.length > 0) {
			crit
					.add(Restrictions.in("chara.class",
							characterizationClassNames));
		}

		// join keyword, finding, publication
		if (wordList != null && wordList.length > 0) {
			crit.createAlias("keywordCollection", "keyword1");
			crit.createAlias("chara.findingCollection", "finding",
					CriteriaSpecification.LEFT_JOIN).createAlias(
					"finding.fileCollection", "charFile",
					CriteriaSpecification.LEFT_JOIN).createAlias(
					"charFile.keywordCollection", "keyword2",
					CriteriaSpecification.LEFT_JOIN);
			// publication keywords
			crit.createAlias("publicationCollection", "pub1",
					CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("pub1.keywordCollection", "keyword3",
					CriteriaSpecification.LEFT_JOIN);
		}

		// keyword
		if (wordList != null && wordList.length > 0) {
			// turn words into upper case before searching keywords
			String[] upperKeywords = new String[wordList.length];
			for (int i = 0; i < wordList.length; i++) {
				upperKeywords[i] = wordList[i].toUpperCase();
			}
			Disjunction disjunction = Restrictions.disjunction();
			for (String keyword : upperKeywords) {
				Criterion keywordCrit1 = Restrictions.like("keyword1.name",
						keyword, MatchMode.ANYWHERE);
				Criterion keywordCrit2 = Restrictions.like("keyword2.name",
						keyword, MatchMode.ANYWHERE);
				Criterion keywordCrit3 = Restrictions.like("keyword3.name",
						keyword, MatchMode.ANYWHERE);
				disjunction.add(keywordCrit1);
				disjunction.add(keywordCrit2);
				disjunction.add(keywordCrit3);
			}
			for (String word : wordList) {
				Criterion summaryCrit1 = Restrictions.ilike(
						"chara.designMethodsDescription", word,
						MatchMode.ANYWHERE);
				Criterion summaryCrit2 = Restrictions.ilike(
						"charFile.description", word, MatchMode.ANYWHERE);
				Criterion summaryCrit = Restrictions.or(summaryCrit1,
						summaryCrit2);
				disjunction.add(summaryCrit);
			}
			crit.add(disjunction);
		}

		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List results = appService.query(crit);
		List filteredResults = new ArrayList(results);
		// get public data
		if (user == null) {
			filteredResults = authService.filterNonPublic(results);
		}
		for (Object obj : filteredResults) {
			String sampleName = obj.toString();
			try {
				Sample sample = findSampleByName(sampleName, user);
				samples.add(sample);
			} catch (NoAccessException e) {
				// ignore no access exception
				logger.debug("User doesn't have access to sample with name "
						+ sampleName);
			}
		}
		Collections.sort(samples, new Comparators.SampleNameComparator());
		return samples;
	}

	public List<String> findSampleNamesBy(String samplePointOfContact,
			String[] nanomaterialEntityClassNames,
			String[] otherNanomaterialEntityTypes,
			String[] functionalizingEntityClassNames,
			String[] otherFunctionalizingEntityTypes,
			String[] functionClassNames, String[] otherFunctionTypes,
			String[] characterizationClassNames,
			String[] otherCharacterizationTypes, String[] wordList,
			UserBean user) throws Exception {
		List<String> sampleNames = new ArrayList<String>();

		// can't query for the entire Sample object due to
		// limitations in pagination in SDK
		DetachedCriteria crit = DetachedCriteria.forClass(Sample.class)
				.setProjection(Projections.distinct(Property.forName("name")));

		if (!StringUtils.isEmpty(samplePointOfContact)) {
			TextMatchMode pocMatchMode = new TextMatchMode(samplePointOfContact);
			Disjunction disjunction = Restrictions.disjunction();
			crit.createAlias("primaryPointOfContact", "pointOfContact");
			crit.createAlias("pointOfContact.organization", "organization");
			crit.createAlias("otherPointOfContactCollection", "otherPoc",
					CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("otherPoc.organization", "otherOrg",
					CriteriaSpecification.LEFT_JOIN);
			String critStrs[] = { "pointOfContact.lastName",
					"pointOfContact.firstName", "pointOfContact.role",
					"organization.name", "otherPoc.lastName",
					"otherPoc.firstName", "otherOrg.name" };
			for (String critStr : critStrs) {
				Criterion pocCrit = Restrictions.ilike(critStr, pocMatchMode
						.getUpdatedText(), pocMatchMode.getMatchMode());
				disjunction.add(pocCrit);
			}
			crit.add(disjunction);
		}

		// join composition
		if (nanomaterialEntityClassNames != null
				&& nanomaterialEntityClassNames.length > 0
				|| otherNanomaterialEntityTypes != null
				&& otherNanomaterialEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0
				|| functionalizingEntityClassNames != null
				&& functionalizingEntityClassNames.length > 0
				|| otherFunctionalizingEntityTypes != null
				&& otherFunctionalizingEntityTypes.length > 0) {
			crit.createAlias("sampleComposition", "comp",
					CriteriaSpecification.LEFT_JOIN);
		}
		// join nanomaterial entity
		if (nanomaterialEntityClassNames != null
				&& nanomaterialEntityClassNames.length > 0
				|| otherNanomaterialEntityTypes != null
				&& otherNanomaterialEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			crit.createAlias("comp.nanomaterialEntityCollection", "nanoEntity",
					CriteriaSpecification.LEFT_JOIN);
		}

		// join functionalizing entity
		if (functionalizingEntityClassNames != null
				&& functionalizingEntityClassNames.length > 0
				|| otherFunctionalizingEntityTypes != null
				&& otherFunctionalizingEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			crit.createAlias("comp.functionalizingEntityCollection",
					"funcEntity", CriteriaSpecification.LEFT_JOIN);
		}

		// nanomaterial entity
		if (nanomaterialEntityClassNames != null
				&& nanomaterialEntityClassNames.length > 0
				|| otherNanomaterialEntityTypes != null
				&& otherNanomaterialEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			Disjunction disjunction = Restrictions.disjunction();
			if (nanomaterialEntityClassNames != null
					&& nanomaterialEntityClassNames.length > 0) {
				Criterion nanoEntityCrit = Restrictions.in("nanoEntity.class",
						nanomaterialEntityClassNames);
				disjunction.add(nanoEntityCrit);
			}
			if (otherNanomaterialEntityTypes != null
					&& otherNanomaterialEntityTypes.length > 0) {
				Criterion otherNanoCrit1 = Restrictions.eq("nanoEntity.class",
						"OtherNanomaterialEntity");
				Criterion otherNanoCrit2 = Restrictions.in("nanoEntity.type",
						otherNanomaterialEntityTypes);
				Criterion otherNanoCrit = Restrictions.and(otherNanoCrit1,
						otherNanoCrit2);
				disjunction.add(otherNanoCrit);
			}
			crit.add(disjunction);
		}

		// functionalizing entity
		// need to turn class names into integers in order for the .class
		// clause to work
		if (functionalizingEntityClassNames != null
				&& functionalizingEntityClassNames.length > 0
				|| otherFunctionalizingEntityTypes != null
				&& otherFunctionalizingEntityTypes.length > 0
				|| functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			Disjunction disjunction = Restrictions.disjunction();
			if (functionalizingEntityClassNames != null
					&& functionalizingEntityClassNames.length > 0) {
				Integer[] functionalizingEntityClassNameIntegers = this
						.convertToFunctionalizingEntityClassOrderNumber(functionalizingEntityClassNames);
				Criterion funcEntityCrit = Restrictions.in("funcEntity.class",
						functionalizingEntityClassNameIntegers);
				disjunction.add(funcEntityCrit);
			}
			if (otherFunctionalizingEntityTypes != null
					&& otherFunctionalizingEntityTypes.length > 0) {
				Integer classOrderNumber = Constants.FUNCTIONALIZING_ENTITY_SUBCLASS_ORDER_MAP
						.get("otherFunctionalizingEntity");
				Criterion otherFuncCrit1 = Restrictions.eq("funcEntity.class",
						classOrderNumber);
				Criterion otherFuncCrit2 = Restrictions.in("funcEntity.type",
						otherNanomaterialEntityTypes);
				Criterion otherFuncCrit = Restrictions.and(otherFuncCrit1,
						otherFuncCrit2);
				disjunction.add(otherFuncCrit);
			}
			crit.add(disjunction);
		}

		// function
		if (functionClassNames != null && functionClassNames.length > 0
				|| otherFunctionTypes != null && otherFunctionTypes.length > 0) {
			Disjunction disjunction = Restrictions.disjunction();
			crit.createAlias("nanoEntity.composingElementCollection",
					"compElement", CriteriaSpecification.LEFT_JOIN)
					.createAlias("compElement.inherentFunctionCollection",
							"inFunc", CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("funcEntity.functionCollection", "func",
					CriteriaSpecification.LEFT_JOIN);
			if (functionClassNames != null && functionClassNames.length > 0) {
				Criterion funcCrit1 = Restrictions.in("inFunc.class",
						functionClassNames);
				Criterion funcCrit2 = Restrictions.in("func.class",
						functionClassNames);
				disjunction.add(funcCrit1).add(funcCrit2);
			}
			if (otherFunctionTypes != null && otherFunctionTypes.length > 0) {
				Criterion otherFuncCrit1 = Restrictions.and(Restrictions.eq(
						"inFunc.class", "OtherFunction"), Restrictions.in(
						"inFunc.type", otherFunctionTypes));
				Criterion otherFuncCrit2 = Restrictions.and(Restrictions.eq(
						"func.class", "OtherFunction"), Restrictions.in(
						"func.type", otherFunctionTypes));
				disjunction.add(otherFuncCrit1).add(otherFuncCrit2);
			}
			crit.add(disjunction);
		}

		// join characterization
		if (characterizationClassNames != null
				&& characterizationClassNames.length > 0 || wordList != null
				&& wordList.length > 0) {
			crit.createAlias("characterizationCollection", "chara",
					CriteriaSpecification.LEFT_JOIN);
		}

		// characterization
		if (characterizationClassNames != null
				&& characterizationClassNames.length > 0) {
			crit
					.add(Restrictions.in("chara.class",
							characterizationClassNames));
		}

		// join keyword, finding, publication
		if (wordList != null && wordList.length > 0) {
			crit.createAlias("keywordCollection", "keyword1");
			crit.createAlias("chara.findingCollection", "finding",
					CriteriaSpecification.LEFT_JOIN).createAlias(
					"finding.fileCollection", "charFile",
					CriteriaSpecification.LEFT_JOIN).createAlias(
					"charFile.keywordCollection", "keyword2",
					CriteriaSpecification.LEFT_JOIN);
			// publication keywords
			crit.createAlias("publicationCollection", "pub1",
					CriteriaSpecification.LEFT_JOIN);
			crit.createAlias("pub1.keywordCollection", "keyword3",
					CriteriaSpecification.LEFT_JOIN);
		}

		// keyword
		if (wordList != null && wordList.length > 0) {
			// turn words into upper case before searching keywords
			String[] upperKeywords = new String[wordList.length];
			for (int i = 0; i < wordList.length; i++) {
				upperKeywords[i] = wordList[i].toUpperCase();
			}
			Disjunction disjunction = Restrictions.disjunction();
			for (String keyword : upperKeywords) {
				Criterion keywordCrit1 = Restrictions.like("keyword1.name",
						keyword, MatchMode.ANYWHERE);
				Criterion keywordCrit2 = Restrictions.like("keyword2.name",
						keyword, MatchMode.ANYWHERE);
				Criterion keywordCrit3 = Restrictions.like("keyword3.name",
						keyword, MatchMode.ANYWHERE);
				disjunction.add(keywordCrit1);
				disjunction.add(keywordCrit2);
				disjunction.add(keywordCrit3);
			}
			for (String word : wordList) {
				Criterion summaryCrit1 = Restrictions.ilike(
						"chara.designMethodsDescription", word,
						MatchMode.ANYWHERE);
				Criterion summaryCrit2 = Restrictions.ilike(
						"charFile.description", word, MatchMode.ANYWHERE);
				Criterion summaryCrit = Restrictions.or(summaryCrit1,
						summaryCrit2);
				disjunction.add(summaryCrit);
			}
			crit.add(disjunction);
		}

		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List results = appService.query(crit);
		List filteredResults = new ArrayList(results);
		// get public data
		if (user == null) {
			filteredResults = authService.filterNonPublic(results);
		}
		for (Object obj : filteredResults) {
			String sampleName = obj.toString();
			if (user == null
					|| authService.checkReadPermission(user, sampleName)) {
				sampleNames.add(sampleName);
			} else { // ignore no access exception
				logger.debug("User doesn't have access to sample with name "
						+ sampleName);
			}
		}
		Collections.sort(sampleNames, new Comparators.SortableNameComparator());
		return sampleNames;
	}

	/**
	 * Return all stored functionalizing entity class names. In case of
	 * OtherFunctionalizingEntity, store the OtherFunctionalizingEntity type
	 * 
	 * @param particleSample
	 * @return
	 */
	public SortedSet<String> getStoredFunctionalizingEntityClassNames(
			Sample particleSample) {
		SortedSet<String> storedEntities = new TreeSet<String>();

		if (particleSample.getSampleComposition() != null
				&& particleSample.getSampleComposition()
						.getFunctionalizingEntityCollection() != null) {
			for (FunctionalizingEntity entity : particleSample
					.getSampleComposition()
					.getFunctionalizingEntityCollection()) {
				if (entity instanceof OtherFunctionalizingEntity) {
					storedEntities.add(((OtherFunctionalizingEntity) entity)
							.getType());
				} else {
					storedEntities.add(ClassUtils.getShortClassName(entity
							.getClass().getCanonicalName()));
				}
			}
		}
		return storedEntities;
	}

	/**
	 * Return all stored function class names. In case of OtherFunction, store
	 * the otherFunction type
	 * 
	 * @param particleSample
	 * @return
	 */
	public SortedSet<String> getStoredFunctionClassNames(Sample particleSample) {
		SortedSet<String> storedFunctions = new TreeSet<String>();

		if (particleSample.getSampleComposition() != null) {
			if (particleSample.getSampleComposition()
					.getNanomaterialEntityCollection() != null) {
				for (NanomaterialEntity entity : particleSample
						.getSampleComposition()
						.getNanomaterialEntityCollection()) {
					if (entity.getComposingElementCollection() != null) {
						for (ComposingElement element : entity
								.getComposingElementCollection()) {
							if (element.getInherentFunctionCollection() != null) {
								for (Function function : element
										.getInherentFunctionCollection()) {
									if (function instanceof OtherFunction) {
										storedFunctions
												.add(((OtherFunction) function)
														.getType());
									} else {
										storedFunctions.add(ClassUtils
												.getShortClassName(function
														.getClass()
														.getCanonicalName()));
									}
								}
							}
						}
					}
				}
			}
			if (particleSample.getSampleComposition()
					.getFunctionalizingEntityCollection() != null) {
				for (FunctionalizingEntity entity : particleSample
						.getSampleComposition()
						.getFunctionalizingEntityCollection()) {
					if (entity.getFunctionCollection() != null) {
						for (Function function : entity.getFunctionCollection()) {
							if (function instanceof OtherFunction) {
								storedFunctions.add(((OtherFunction) function)
										.getType());
							} else {
								storedFunctions.add(ClassUtils
										.getShortClassName(function.getClass()
												.getCanonicalName()));
							}
						}
					}
				}
			}
		}
		return storedFunctions;
	}

	/**
	 * Return all stored nanomaterial entity class names. In case of
	 * OtherNanomaterialEntity, store the otherNanomaterialEntity type
	 * 
	 * @param particleSample
	 * @return
	 */
	public SortedSet<String> getStoredNanomaterialEntityClassNames(
			Sample particleSample) {
		SortedSet<String> storedEntities = new TreeSet<String>();

		if (particleSample.getSampleComposition() != null
				&& particleSample.getSampleComposition()
						.getNanomaterialEntityCollection() != null) {
			for (NanomaterialEntity entity : particleSample
					.getSampleComposition().getNanomaterialEntityCollection()) {
				if (entity instanceof OtherNanomaterialEntity) {
					storedEntities.add(((OtherNanomaterialEntity) entity)
							.getType());
				} else {
					storedEntities.add(ClassUtils.getShortClassName(entity
							.getClass().getCanonicalName()));
				}
			}
		}
		return storedEntities;
	}

	public SortedSet<String> getStoredCharacterizationClassNames(Sample particle) {
		SortedSet<String> storedChars = new TreeSet<String>();
		if (particle.getCharacterizationCollection() != null) {
			for (Characterization achar : particle
					.getCharacterizationCollection()) {
				storedChars.add(ClassUtils.getShortClassName(achar.getClass()
						.getCanonicalName()));
			}
		}
		return storedChars;
	}

	public Sample findSampleByName(String sampleName, UserBean user)
			throws Exception {
		Sample sample = null;
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();

		DetachedCriteria crit = DetachedCriteria.forClass(Sample.class).add(
				Property.forName("name").eq(sampleName));
		crit.setFetchMode("primaryPointOfContact", FetchMode.JOIN);
		crit.setFetchMode("primaryPointOfContact.organization", FetchMode.JOIN);
		crit.setFetchMode("otherPointOfContactCollection", FetchMode.JOIN);
		crit.setFetchMode("otherPointOfContactCollection.organization",
				FetchMode.JOIN);
		crit.setFetchMode("keywordCollection", FetchMode.JOIN);
		crit.setFetchMode("characterizationCollection", FetchMode.JOIN);
		crit.setFetchMode("sampleComposition.nanomaterialEntityCollection",
				FetchMode.JOIN);
		crit
				.setFetchMode(
						"sampleComposition.nanomaterialEntityCollection.composingElementCollection",
						FetchMode.JOIN);
		crit
				.setFetchMode(
						"sampleComposition.nanomaterialEntityCollection.composingElementCollection.inherentFunctionCollection",
						FetchMode.JOIN);

		crit.setFetchMode("sampleComposition.functionalizingEntityCollection",
				FetchMode.JOIN);
		crit
				.setFetchMode(
						"sampleComposition.functionalizingEntityCollection.functionCollection",
						FetchMode.JOIN);
		crit.setFetchMode("publicationCollection", FetchMode.JOIN);
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List result = appService.query(crit);
		if (!result.isEmpty()) {
			sample = (Sample) result.get(0);
			if (authService.checkReadPermission(user, sample.getName())) {
				return sample;
			} else {
				throw new NoAccessException();
			}
		}
		return sample;
	}

	public List<Keyword> findKeywordsBySampleId(String sampleId, UserBean user)
			throws Exception {
		List<Keyword> keywords = new ArrayList<Keyword>();

		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		DetachedCriteria crit = DetachedCriteria.forClass(Sample.class).add(
				Property.forName("id").eq(new Long(sampleId)));
		crit.setFetchMode("keywordCollection", FetchMode.JOIN);
		List result = appService.query(crit);
		Sample sample = null;
		if (!result.isEmpty()) {
			sample = (Sample) result.get(0);
			// check whether user has access to the sample
			if (authService.checkReadPermission(user, sample.getName())) {
				keywords.addAll(sample.getKeywordCollection());
			} else {
				throw new NoAccessException(
						"User doesn't have access to the sample.");
			}
		}
		return keywords;
	}

	public PointOfContact findPrimaryPointOfContactBySampleId(String sampleId,
			UserBean user) throws Exception {
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		HQLCriteria crit = new HQLCriteria(
				"select aSample.primaryPointOfContact from gov.nih.nci.cananolab.domain.particle.Sample aSample where aSample.id = "
						+ sampleId);
		List result = appService.query(crit);
		PointOfContact poc = null;
		if (!result.isEmpty()) {
			poc = (PointOfContact) result.get(0);
			if (authService.checkReadPermission(user, poc.getId().toString())) {
				return poc;
			} else {
				throw new NoAccessException();
			}
		}
		return poc;
	}

	public List<PointOfContact> findOtherPointOfContactBySampleId(
			String sampleId, UserBean user) throws Exception {
		List<PointOfContact> pocs = new ArrayList<PointOfContact>();
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		HQLCriteria crit = new HQLCriteria(
				"select aSample.otherPointOfContactCollection from gov.nih.nci.cananolab.domain.particle.Sample aSample where aSample.id = "
						+ sampleId);
		List results = appService.query(crit);
		List filteredResults = new ArrayList(results);
		if (user == null) {
			filteredResults = authService.filterNonPublic(results);
		}
		for (Object obj : filteredResults) {
			PointOfContact poc = (PointOfContact) obj;

		}
		return pocs;
	}

	public int getNumberOfPublicSamples() throws Exception {
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List<String> publicData = appService.getAllPublicData();
		HQLCriteria crit = new HQLCriteria(
				"select name from gov.nih.nci.cananolab.domain.particle.Sample");
		List results = appService.query(crit);
		List<String> publicNames = new ArrayList<String>();
		for (Object obj : results) {
			String name = (String) obj.toString();
			if (StringUtils.containsIgnoreCase(publicData, name)) {
				publicNames.add(name);
			}
		}
		return publicNames.size();
	}

	public String[] getCharacterizationClassNames(String sampleId)
			throws Exception {
		String hql = "select distinct achar.class from gov.nih.nci.cananolab.domain.particle.characterization.Characterization achar"
				+ " where achar.sample.id = " + sampleId;
		return this.getClassNames(hql);
	}

	public String[] getFunctionalizingEntityClassNames(String sampleId)
			throws Exception {
		SortedSet<String> names = new TreeSet<String>();
		DetachedCriteria crit = DetachedCriteria.forClass(Sample.class).add(
				Property.forName("id").eq(new Long(sampleId)));
		crit.setFetchMode("sampleComposition.functionalizingEntityCollection",
				FetchMode.JOIN);
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List results = appService.query(crit);
		for (Object obj : results) {
			Sample particleSample = (Sample) obj;
			names = this
					.getStoredFunctionalizingEntityClassNames(particleSample);
		}
		return names.toArray(new String[0]);
	}

	public String[] getFunctionClassNames(String sampleId) throws Exception {
		SortedSet<String> names = new TreeSet<String>();
		DetachedCriteria crit = DetachedCriteria.forClass(Sample.class).add(
				Property.forName("id").eq(new Long(sampleId)));
		crit.setFetchMode("sampleComposition.nanomaterialEntityCollection",
				FetchMode.JOIN);
		crit
				.setFetchMode(
						"sampleComposition.nanomaterialEntityCollection.composingElementCollection",
						FetchMode.JOIN);
		crit
				.setFetchMode(
						"sampleComposition.nanomaterialEntityCollection.composingElementCollection.inherentFunctionCollection",
						FetchMode.JOIN);
		crit.setFetchMode("sampleComposition.functionalizingEntityCollection",
				FetchMode.JOIN);
		crit
				.setFetchMode(
						"sampleComposition.functionalizingEntityCollection.functionCollection",
						FetchMode.JOIN);
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List results = appService.query(crit);
		for (Object obj : results) {
			Sample particleSample = (Sample) obj;
			names = this.getStoredFunctionClassNames(particleSample);
		}
		return names.toArray(new String[0]);
	}

	public String[] getNanomaterialEntityClassNames(String sampleId)
			throws Exception {
		String hql = "select distinct entity.class from "
				+ " gov.nih.nci.cananolab.domain.particle.NanomaterialEntity entity"
				+ " where entity.class!='OtherNanomaterialEntity' and entity.sampleComposition.sample.id = "
				+ sampleId;

		String[] classNames = this.getClassNames(hql);
		SortedSet<String> names = new TreeSet<String>();
		if (classNames.length > 0) {
			names.addAll(Arrays.asList(classNames));
		}
		String hql2 = "select distinct entity.type from "
				+ " gov.nih.nci.cananolab.domain.nanomaterial.OtherNanomaterialEntity entity"
				+ " where entity.sampleComposition.sample.id = " + sampleId;
		String[] otherTypes = this.getClassNames(hql2);
		if (otherTypes.length > 0) {
			names.addAll(Arrays.asList(otherTypes));
		}
		return names.toArray(new String[0]);
	}

	private String[] getClassNames(String hql) throws Exception {
		String[] classNames = null;
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		HQLCriteria crit = new HQLCriteria(hql);
		List results = appService.query(crit);
		if (results != null) {
			classNames = new String[results.size()];
		} else {
			classNames = new String[0];
		}
		int i = 0;
		for (Object obj : results) {
			classNames[i] = (String) obj.toString();
			i++;
		}
		return classNames;
	}

	public String[] getSampleViewStrs(List<Sample> samples) {
		List<String> sampleStrings = new ArrayList<String>(samples.size());

		List<String> columns = new ArrayList<String>(7);
		for (Sample sample : samples) {
			columns.clear();
			columns.add(sample.getId().toString());
			columns.add(sample.getName());
			PointOfContactBean primaryPOC = new PointOfContactBean(sample
					.getPrimaryPointOfContact());
			columns.add(primaryPOC.getDomain().getFirstName());
			columns.add(primaryPOC.getDomain().getLastName());
			columns.add(primaryPOC.getDomain().getOrganization().getName());
			// nanomaterial entities and functionalizing entities are in one
			// column.
			SortedSet<String> entities = new TreeSet<String>();
			entities.addAll(getStoredNanomaterialEntityClassNames(sample));
			entities.addAll(getStoredFunctionalizingEntityClassNames(sample));
			columns.add(StringUtils.join(entities,
					Constants.VIEW_CLASSNAME_DELIMITER));
			columns.add(StringUtils.join(getStoredFunctionClassNames(sample),
					Constants.VIEW_CLASSNAME_DELIMITER));
			columns.add(StringUtils.join(
					getStoredCharacterizationClassNames(sample),
					Constants.VIEW_CLASSNAME_DELIMITER));

			sampleStrings.add(StringUtils.joinEmptyItemIncluded(columns,
					Constants.VIEW_COL_DELIMITER));
		}
		return sampleStrings.toArray(new String[0]);
	}

	public String[] getSampleViewStrs(Sample sample) {
		List<String> columns = new ArrayList<String>(7);
		columns.clear();
		columns.add(sample.getId().toString());
		columns.add(sample.getName());
		PointOfContactBean primaryPOC = new PointOfContactBean(sample
				.getPrimaryPointOfContact());
		columns.add(primaryPOC.getDomain().getFirstName());
		columns.add(primaryPOC.getDomain().getLastName());
		columns.add(primaryPOC.getDomain().getOrganization().getName());
		// nanomaterial entities and functionalizing entities are in one
		// column.
		SortedSet<String> entities = new TreeSet<String>();
		entities.addAll(getStoredNanomaterialEntityClassNames(sample));
		entities.addAll(getStoredFunctionalizingEntityClassNames(sample));
		columns.add(StringUtils.join(entities,
				Constants.VIEW_CLASSNAME_DELIMITER));
		columns.add(StringUtils.join(getStoredFunctionClassNames(sample),
				Constants.VIEW_CLASSNAME_DELIMITER));
		columns.add(StringUtils.join(
				getStoredCharacterizationClassNames(sample),
				Constants.VIEW_CLASSNAME_DELIMITER));
		return columns.toArray(new String[0]);
	}

	public AuthorizationService getAuthService() {
		return authService;
	}

	public Integer[] convertToFunctionalizingEntityClassOrderNumber(
			String[] classNames) {
		Integer[] orderNumbers = new Integer[classNames.length];
		int i = 0;
		for (String name : classNames) {
			orderNumbers[i] = Constants.FUNCTIONALIZING_ENTITY_SUBCLASS_ORDER_MAP
					.get(name);
			i++;
		}
		return orderNumbers;
	}

	public Organization findOrganizationByName(String orgName, UserBean user)
			throws Exception {
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		DetachedCriteria crit = DetachedCriteria.forClass(Organization.class);
		crit.add(Restrictions.eq("name", orgName));
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List results = appService.query(crit);
		Organization org = null;
		for (Object obj : results) {
			org = (Organization) obj;
			if (authService.checkReadPermission(user, org.getId().toString())) {
				return org;
			} else {
				throw new NoAccessException();
			}
		}
		return org;
	}

	public PointOfContact findPointOfContactByNameAndOrg(String firstName,
			String lastName, String orgName, UserBean user) throws Exception {
		PointOfContact poc = null;

		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		DetachedCriteria crit = DetachedCriteria.forClass(PointOfContact.class);
		crit.createAlias("organization", "organization");
		if (!StringUtils.isEmpty(lastName))
			crit.add(Restrictions.eq("lastName", lastName));
		if (!StringUtils.isEmpty(firstName))
			crit.add(Restrictions.eq("firstName", firstName));
		if (!StringUtils.isEmpty(orgName))
			crit.add(Restrictions.eq("organization.name", orgName));
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List results = appService.query(crit);
		for (Object obj : results) {
			poc = (PointOfContact) obj;
			if (authService.checkReadPermission(user, poc.getId().toString())) {
				return poc;
			} else {
				throw new NoAccessException();
			}
		}
		return poc;
	}

	private Junction getCompositionJunction(
			AdvancedSampleSearchBean searchBean, DetachedCriteria crit) throws Exception {
		if (searchBean.getCompositionQueries().isEmpty()) {
			return null;
		}
		Junction junction = null;
		Disjunction compDisjunction = Restrictions.disjunction();
		Conjunction compConjunction = Restrictions.conjunction();
		// composition queries
		crit.createAlias("sampleComposition", "comp",
				CriteriaSpecification.LEFT_JOIN);
		Boolean hasChemicalName = false;
		if (searchBean.getHasNanomaterial()) {
			crit.createAlias("comp.nanomaterialEntityCollection", "nanoEntity",
					CriteriaSpecification.LEFT_JOIN);
			for (CompositionQueryBean query : searchBean
					.getCompositionQueries()) {
				if (!StringUtils.isEmpty(query.getChemicalName())) {
					hasChemicalName = true;
					break;
				}
			}
			if (hasChemicalName) {
				crit.createAlias("nanoEntity.composingElementCollection",
						"compElement", CriteriaSpecification.LEFT_JOIN);
			}
		}
		if (searchBean.getHasAgentMaterial()) {
			crit.createAlias("comp.functionalizingEntityCollection",
					"funcEntity", CriteriaSpecification.LEFT_JOIN);
		}

		for (CompositionQueryBean compQuery : searchBean
				.getCompositionQueries()) {
			TextMatchMode chemicalNameMatchMode = null;
			if (compQuery.getOperand().equals("equals")) {
				chemicalNameMatchMode = new TextMatchMode(compQuery
						.getChemicalName());
			} else if (compQuery.getOperand().equals("contains")) {
				chemicalNameMatchMode = new TextMatchMode("*"
						+ compQuery.getChemicalName() + "*");
			}
			// nanomaterial entity
			if (compQuery.getCompositionType().equals("nanomaterial entity")) {
				String nanoEntityClassName = ClassUtils
						.getShortClassNameFromDisplayName(compQuery
								.getEntityType());
				Class clazz = ClassUtils.getFullClass("nanomaterial." + nanoEntityClassName);
				Criterion nanoEntityCrit = null;
				// other entity type
				if (clazz == null) {
					Criterion otherNanoCrit1 = Restrictions.eq(
							"nanoEntity.class", "OtherNanomaterialEntity");
					Criterion otherNanoCrit2 = Restrictions.eq(
							"nanoEntity.type", compQuery.getEntityType());
					nanoEntityCrit = Restrictions.and(otherNanoCrit1,
							otherNanoCrit2);
				} else {
					nanoEntityCrit = Restrictions.eq("nanoEntity.class",
							nanoEntityClassName);
				}
				if (hasChemicalName) {
					Criterion chemicalNameCrit = Restrictions.ilike(
							"compElement.name", chemicalNameMatchMode
									.getUpdatedText(), chemicalNameMatchMode
									.getMatchMode());
					nanoEntityCrit = Restrictions.and(nanoEntityCrit,
							chemicalNameCrit);
				}
				compConjunction.add(nanoEntityCrit);
				compDisjunction.add(nanoEntityCrit);
			}
			// functionalizing entity
			else if (compQuery.getCompositionType().equals(
					"functionalizing entity")) {
				String funcEntityClassName = ClassUtils
						.getShortClassNameFromDisplayName(compQuery
								.getEntityType());
				Class clazz = ClassUtils.getFullClass("agentmaterial." + funcEntityClassName);
				Criterion funcEntityCrit = null;
				// other entity type
				if (clazz == null) {
					Criterion otherFuncCrit1 = Restrictions.eq(
							"funcEntity.class", "OtherFunctionalizingEntity");
					Criterion otherFuncCrit2 = Restrictions.eq(
							"funcEntity.type", compQuery.getEntityType());
					funcEntityCrit = Restrictions.and(otherFuncCrit1,
							otherFuncCrit2);
				} else {
					Integer funcClassNameInteger = Constants.FUNCTIONALIZING_ENTITY_SUBCLASS_ORDER_MAP
							.get(funcEntityClassName);
					funcEntityCrit = Restrictions.eq("funcEntity.class",
							funcClassNameInteger);
				}
				Criterion chemicalNameCrit = Restrictions.ilike(
						"funcEntity.name", chemicalNameMatchMode
								.getUpdatedText(), chemicalNameMatchMode
								.getMatchMode());
				funcEntityCrit = Restrictions.and(funcEntityCrit,
						chemicalNameCrit);
				compConjunction.add(funcEntityCrit);
				compDisjunction.add(funcEntityCrit);
			}
		}
		if (searchBean.getCompositionLogicalOperator().equals("and")) {
			junction = compConjunction;
		} else if (searchBean.getCompositionLogicalOperator().equals("or")) {
			junction = compDisjunction;
		}
		return junction;
	}

	private Junction getCharacterizationJunction(
			AdvancedSampleSearchBean searchBean, DetachedCriteria crit) {
		if (searchBean.getCharacterizationQueries().isEmpty()) {
			return null;
		}
		Junction junction = null;
		Disjunction charDisjunction = Restrictions.disjunction();
		Conjunction charConjunction = Restrictions.conjunction();

		// join characterization
		crit.createAlias("characterizationCollection", "chara",
				CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("chara.findingCollection", "finding",
				CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("finding.datumCollection", "datum",
				CriteriaSpecification.LEFT_JOIN);
		for (CharacterizationQueryBean charQuery : searchBean
				.getCharacterizationQueries()) {
			String charClassName = ClassUtils
					.getShortClassNameFromDisplayName(charQuery
							.getCharacterizationName());
			Criterion charCrit = null;
			if (charClassName == null) {
				Criterion otherCharCrit1 = Restrictions.eq("chara.class",
						"OtherCharacterization");
				Criterion otherCharCrit2 = Restrictions.eq("chara.name",
						charQuery.getCharacterizationName());
				charCrit = Restrictions.and(otherCharCrit1, otherCharCrit2);
			} else {
				charCrit = Restrictions.eq("chara.class", charClassName);
			}
			charCrit = Restrictions.and(charCrit, Restrictions.eq("datum.name",
					charQuery.getDatumName()));

			Float datumValue = new Float(charQuery.getDatumValue());
			charCrit = Restrictions.and(charCrit, Restrictions.eq(
					"datum.valueUnit", charQuery.getDatumValueUnit()));
			if (charQuery.getOperand().equals("=")) {
				charCrit = Restrictions.and(charCrit, Expression.eq(
						"datum.value", datumValue));
			} else if (charQuery.getOperand().equals(">")) {
				charCrit = Restrictions.and(charCrit, Expression.gt(
						"datum.value", datumValue));
			} else if (charQuery.getOperand().equals(">=")) {
				charCrit = Restrictions.and(charCrit, Expression.ge(
						"datum.value", datumValue));
			} else if (charQuery.getOperand().equals("<")) {
				charCrit = Restrictions.and(charCrit, Expression.lt(
						"datum.value", datumValue));
			} else if (charQuery.getOperand().equals("<=")) {
				charCrit = Restrictions.and(charCrit, Expression.le(
						"datum.value", datumValue));
			}
			charConjunction.add(charCrit);
			charDisjunction.add(charCrit);

			if (searchBean.getCharacterizationLogicalOperator().equals("and")) {
				junction = charConjunction;
			} else if (searchBean.getCharacterizationLogicalOperator().equals(
					"or")) {
				junction = charDisjunction;
			}
		}
		return junction;
	}

	public List<String> findSampleNamesByAdvancedSearch(
			AdvancedSampleSearchBean searchBean, UserBean user)
			throws Exception {
		List<String> sampleNames = new ArrayList<String>();
		DetachedCriteria crit = DetachedCriteria.forClass(Sample.class)
				.setProjection(Projections.distinct(Property.forName("name")));
		Disjunction disjunction = Restrictions.disjunction();
		Conjunction conjunction = Restrictions.conjunction();
		Junction compJunction = getCompositionJunction(searchBean, crit);
		Junction charJunction = getCharacterizationJunction(searchBean, crit);

		if (searchBean.getLogicalOperator().equals("and")) {
			if (compJunction != null)
				conjunction.add(compJunction);
			if (charJunction != null)
				conjunction.add(charJunction);
			crit.add(conjunction);
		} else if (searchBean.getLogicalOperator().equals("or")) {
			if (compJunction != null)
				disjunction.add(compJunction);
			if (charJunction != null)
				disjunction.add(charJunction);
			crit.add(disjunction);
		}

		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List results = appService.query(crit);
		List filteredResults = new ArrayList(results);

		// get public data
		if (user == null) {
			filteredResults = authService.filterNonPublic(results);
		}
		for (Object obj : filteredResults) {
			String sampleName = obj.toString();
			if (user == null
					|| authService.checkReadPermission(user, sampleName)) {
				sampleNames.add(sampleName);
			} else { // ignore no access exception
				logger.debug("User doesn't have access to sample with name "
						+ sampleName);
			}
		}
		Collections.sort(sampleNames, new Comparators.SortableNameComparator());
		return sampleNames;
	}
}