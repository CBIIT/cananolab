package gov.nih.nci.cananolab.service.sample.impl;

import gov.nih.nci.cagrid.cananolab.client.CaNanoLabServiceClient;
import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlquery.QueryModifier;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cananolab.domain.common.Keyword;
import gov.nih.nci.cananolab.domain.common.Organization;
import gov.nih.nci.cananolab.domain.common.PointOfContact;
import gov.nih.nci.cananolab.domain.common.Publication;
import gov.nih.nci.cananolab.domain.particle.Sample;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.SampleBean;
import gov.nih.nci.cananolab.exception.DuplicateEntriesException;
import gov.nih.nci.cananolab.exception.SampleException;
import gov.nih.nci.cananolab.service.sample.SampleService;
import gov.nih.nci.cananolab.service.sample.helper.SampleServiceHelper;
import gov.nih.nci.cananolab.util.Comparators;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.SortableName;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;

import org.apache.log4j.Logger;

/**
 * Service methods involving samples
 *
 * @author pansu
 *
 */
public class SampleServiceRemoteImpl implements SampleService {
	private static Logger logger = Logger
			.getLogger(SampleServiceRemoteImpl.class);
	private CaNanoLabServiceClient gridClient;
	private SampleServiceHelper helper = new SampleServiceHelper();

	public SampleServiceRemoteImpl(String serviceUrl) throws SampleException {
		try {
			gridClient = new CaNanoLabServiceClient(serviceUrl);
		} catch (Exception e) {
			throw new SampleException("Can't create grid client succesfully.");
		}
	}

	/**
	 * Persist a new sample or update an existing canano sample
	 *
	 * @param sample
	 * @throws SampleException,
	 *             DuplicateEntriesException
	 */
	public void saveSample(SampleBean sample, UserBean user)
			throws SampleException, DuplicateEntriesException {
		throw new SampleException("Not implemented for grid service");
	}

	/**
	 *
	 * @param samplePointOfContacts
	 * @param nanomaterialEntityClassNames
	 * @param otherNanoparticleTypes
	 * @param functionalizingEntityClassNames
	 * @param otherFunctionalizingEntityTypes
	 * @param functionClassNames
	 * @param otherFunctionTypes
	 * @param characterizationClassNames
	 * @param wordList
	 * @return
	 * @throws SampleException
	 */
	public List<SampleBean> findSamplesBy(String samplePointOfContact,
			String[] nanomaterialEntityClassNames,
			String[] otherNanoparticleTypes,
			String[] functionalizingEntityClassNames,
			String[] otherFunctionalizingEntityTypes,
			String[] functionClassNames, String[] otherFunctionTypes,
			String[] characterizationClassNames,
			String[] otherCharacterizationTypes, String[] wordList,
			UserBean user) throws SampleException {
		try {
			String[] sampleViewStrs = gridClient.getSampleViewStrs(
					samplePointOfContact, nanomaterialEntityClassNames,
					functionalizingEntityClassNames, functionClassNames,
					characterizationClassNames, wordList);
			// String[] sampleViewStrs = {
			// "35444457~~~NCICB-6~~~DNT~~~Carbon nanotube!!!small
			// molecule~~~therapeutic~~~Enzyme Induction:Molecular
			// Weight:Oxidative Stress",
			// "35445457~~~NCICB-60~~~DNT~~~Carbon nanotube!!!small
			// molecule~~~therapeutic~~~Enzyme Induction:Molecular
			// Weight:Oxidative Stress",
			// };
			List<SampleBean> samples = new ArrayList<SampleBean>();
			if (sampleViewStrs != null && sampleViewStrs.length > 0) {
				String[] columns = null;
				for (String sampleStr : sampleViewStrs) {
					columns = sampleStr.split(Constants.VIEW_COL_DELIMITER);
					Sample sample = new Sample();
					// id
					sample.setId(new Long(columns[0]));
					// sample name
					sample.setName(columns[1]);
					// source
					PointOfContact primaryPOC = new PointOfContact();
					Organization org = new Organization();
					primaryPOC.setFirstName(columns[2]);
					primaryPOC.setLastName(columns[3]);
					org.setName(columns[4]);
					primaryPOC.setOrganization(org);
					sample.setPrimaryPointOfContact(primaryPOC);

					SampleBean sampleBean = new SampleBean(sample);
					// composition, set all compositions as NanoparticleEntity
					// for now
					if (columns.length > 5 && columns[5] != null
							&& columns[5].length() > 0) {
						String[] compositionsClazzNames = columns[5]
								.split(Constants.VIEW_CLASSNAME_DELIMITER);
						if (compositionsClazzNames != null) {
							sampleBean
									.setNanomaterialEntityClassNames(compositionsClazzNames);
						}
					}
					// functionClassNames
					if (columns.length > 6 && columns[6] != null
							&& columns[6].length() > 0) {
						String[] functionClazzNames = columns[6]
								.split(Constants.VIEW_CLASSNAME_DELIMITER);
						if (functionClazzNames != null) {
							sampleBean
									.setFunctionClassNames(functionClazzNames);
						}
					}

					// characterizationClassNames
					if (columns.length > 7 && columns[7] != null
							&& columns[7].length() > 0) {
						String[] characterizationClazzNames = columns[7]
								.split(Constants.VIEW_CLASSNAME_DELIMITER);
						if (characterizationClazzNames != null) {
							sampleBean
									.setCharacterizationClassNames(characterizationClazzNames);
						}
					}
					samples.add(sampleBean);
				}
				Collections.sort(samples,
						new Comparators.SampleBeanComparator());
			}
			return samples;
		} catch (RemoteException e) {
			logger.error(Constants.NODE_UNAVAILABLE, e);
			throw new SampleException(Constants.NODE_UNAVAILABLE, e);
		} catch (Exception e) {
			String err = "Problem finding samples with the given search parameters.";
			logger.error(err, e);
			throw new SampleException(err, e);
		}
	}

	public SampleBean findSampleById(String sampleId, UserBean user)
			throws SampleException {
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.particle.Sample");
			Attribute attribute = new Attribute();
			attribute.setName("id");
			attribute.setPredicate(Predicate.EQUAL_TO);
			attribute.setValue(sampleId);
			target.setAttribute(attribute);
			query.setTarget(target);
			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.particle.Sample");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			Sample sample = null;
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				sample = (Sample) obj;
				loadSamplesAssociations(sample);
			}
			SampleBean sampleBean = new SampleBean(sample);
			if (checkCompositionForSample(sample)) {
				sampleBean.setHasComposition(true);
			}
			if (checkCharacterizationsForSample(sample)) {
				sampleBean.setHasCharacterizations(true);
			}
			if (checkPublicationsForSample(sample)) {
				sampleBean.setHasPublications(true);
			}
			return sampleBean;
		} catch (RemoteException e) {
			logger.error(Constants.NODE_UNAVAILABLE, e);
			throw new SampleException(Constants.NODE_UNAVAILABLE, e);
		} catch (Exception e) {
			String err = "Problem finding the remote sample by id: " + sampleId;
			logger.error(err, e);
			throw new SampleException(err, e);
		}
	}

	/**
	 * Get all the associated data of a sample
	 *
	 * @param particleSample
	 * @throws Exception
	 */
	private void loadSamplesAssociations(Sample sample) throws Exception {
		// source
		loadPointOfContactsForSample(sample);
		// keyword
		loadKeywordsForSample(sample);
	}

	/**
	 * load the source for an associated NanoparticleSample
	 *
	 * @param particleId
	 * @return
	 * @throws ParticleException
	 *
	 */
	private void loadPointOfContactsForSample(Sample sample) throws Exception {
		PointOfContact primaryPOC = gridClient
				.getPrimaryPointOfContactBySampleId(sample.getId().toString());
		PointOfContact[] otherPOCs = gridClient
				.getOtherPointOfContactsBySampleId(sample.getId().toString());
		if (primaryPOC != null) {
			sample.setPrimaryPointOfContact(primaryPOC);
		}
		if (otherPOCs != null && otherPOCs.length > 0) {
			Collection<PointOfContact> otherPOCCollection = new HashSet<PointOfContact>(
					Arrays.asList(otherPOCs));
			sample.setOtherPointOfContactCollection((otherPOCCollection));
		}
	}

	/**
	 * load all keywords for an associated NanoparticleSample equal to
	 * particleId
	 *
	 */
	private void loadKeywordsForSample(Sample sample) throws Exception {
		Keyword[] keywords = gridClient.getKeywordsBySampleId(sample.getId()
				.toString());
		if (keywords != null && keywords.length > 0) {
			sample.setKeywordCollection(new HashSet<Keyword>(Arrays
					.asList(keywords)));
		}
	}

	private Boolean checkCompositionForSample(Sample sample) throws Exception {
		CQLQuery query = new CQLQuery();
		QueryModifier modifier = new QueryModifier();
		modifier.setCountOnly(true);
		query.setQueryModifier(modifier);
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target
				.setName("gov.nih.nci.cananolab.domain.particle.SampleComposition");
		Association association = new Association();
		association.setName("gov.nih.nci.cananolab.domain.particle.Sample");
		association.setRoleName("sample");
		Attribute attribute = new Attribute();
		attribute.setName("id");
		attribute.setPredicate(Predicate.EQUAL_TO);
		attribute.setValue(sample.getId().toString());
		association.setAttribute(attribute);
		target.setAssociation(association);
		query.setTarget(target);
		CQLQueryResults results = gridClient.query(query);
		results
				.setTargetClassname("gov.nih.nci.cananolab.domain.particle.SampleComposition");
		CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
		int count = 0;
		while (iter.hasNext()) {
			java.lang.Object obj = iter.next();
			count = ((Long) obj).intValue();
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	private Boolean checkCharacterizationsForSample(Sample sample)
			throws Exception {
		CQLQuery query = new CQLQuery();
		QueryModifier modifier = new QueryModifier();
		modifier.setCountOnly(true);
		query.setQueryModifier(modifier);
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target
				.setName("gov.nih.nci.cananolab.domain.particle.Characterization");
		Association association = new Association();
		association.setName("gov.nih.nci.cananolab.domain.particle.Sample");
		association.setRoleName("sample");
		Attribute attribute = new Attribute();
		attribute.setName("id");
		attribute.setPredicate(Predicate.EQUAL_TO);
		attribute.setValue(sample.getId().toString());
		association.setAttribute(attribute);
		target.setAssociation(association);
		query.setTarget(target);
		CQLQueryResults results = gridClient.query(query);
		results
				.setTargetClassname("gov.nih.nci.cananolab.domain.particle.Characterization");
		CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
		int count = 0;
		while (iter.hasNext()) {
			java.lang.Object obj = iter.next();
			count = ((Long) obj).intValue();
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	private Boolean checkPublicationsForSample(Sample sample) throws Exception {
		Publication[] publications = gridClient
				.getPublicationsBySampleId(sample.getId().toString());
		if (publications.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getNumberOfPublicSamples() throws SampleException {
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.particle.Sample");
			query.setTarget(target);
			QueryModifier modifier = new QueryModifier();
			modifier.setCountOnly(true);
			query.setQueryModifier(modifier);

			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.particle.Sample");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			int count = 0;
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				count = ((Long) obj).intValue();
			}
			return count;
		} catch (Exception e) {
			String err = "Error finding counts of remote public samples.";
			logger.error(err, e);
			throw new SampleException(err, e);
		}
	}

	public SampleBean findFullSampleById(String sampleId, UserBean user)
			throws SampleException {
		throw new SampleException("Not implemented for grid service");
	}

	public SampleBean findSampleByName(String sampleName, UserBean user)
			throws SampleException {
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.particle.Sample");
			Attribute attribute = new Attribute();
			attribute.setName("id");
			attribute.setPredicate(Predicate.EQUAL_TO);
			attribute.setValue(sampleName);
			target.setAttribute(attribute);
			query.setTarget(target);
			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.particle.Sample");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			Sample sample = null;
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				sample = (Sample) obj;
				loadSamplesAssociations(sample);
			}
			SampleBean sampleBean = new SampleBean(sample);
			return sampleBean;
		} catch (RemoteException e) {
			logger.error(Constants.NODE_UNAVAILABLE, e);
			throw new SampleException(Constants.NODE_UNAVAILABLE, e);
		} catch (Exception e) {
			String err = "Problem finding the remote sample by name: "
					+ sampleName;
			logger.error(err, e);
			throw new SampleException(err, e);
		}
	}

	public SortedSet<String> findAllSampleNames(UserBean user)
			throws SampleException {
		throw new SampleException("Not implemented for grid service");
	}

	public SortedSet<String> findAllSampleNames() throws SampleException {
		throw new SampleException("Not implemented for grid service");
	}

	public SortedSet<SortableName> findOtherSamplesFromSamePointOfContact(
			String sampleId, UserBean user) throws SampleException {
		throw new SampleException("Not implemented for grid service");
	}
}
