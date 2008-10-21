package gov.nih.nci.cananolab.service.publication.impl;

import gov.nih.nci.cagrid.cananolab.client.CaNanoLabServiceClient;
import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlquery.QueryModifier;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cananolab.domain.common.Author;
import gov.nih.nci.cananolab.domain.common.Publication;
import gov.nih.nci.cananolab.domain.particle.NanoparticleSample;
import gov.nih.nci.cananolab.dto.common.PublicationBean;
import gov.nih.nci.cananolab.dto.particle.ParticleBean;
import gov.nih.nci.cananolab.exception.CaNanoLabSecurityException;
import gov.nih.nci.cananolab.exception.PublicationException;
import gov.nih.nci.cananolab.service.publication.PublicationService;
import gov.nih.nci.cananolab.service.publication.helper.PublicationServiceHelper;
import gov.nih.nci.cananolab.util.CaNanoLabComparators;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Remote implementation of PublicationService
 * 
 * @author tanq
 * 
 */
public class PublicationServiceRemoteImpl implements PublicationService {
	private static Logger logger = Logger
			.getLogger(PublicationServiceRemoteImpl.class);
	private CaNanoLabServiceClient gridClient;

	public PublicationServiceRemoteImpl(String serviceUrl) throws Exception {
		gridClient = new CaNanoLabServiceClient(serviceUrl);
	}

	/**
	 * Persist a new publication or update an existing publication
	 * 
	 * @param publication
	 * @param particleNames
	 * @param fileData
	 * @param authors
	 *    
	 * @throws Exception
	 */
	public void savePublication(Publication publication, String[] particleNames,
			byte[] fileData, Collection<Author> authors) throws PublicationException {
		throw new PublicationException("not implemented for grid service.");
	}

		public List<PublicationBean> findPublicationsBy(String title,
			String category, String nanoparticleName, 
			String[] researchArea, String keywordsStr,
			String pubMedId, String digitalObjectId, String authorsStr,
			String[] nanoparticleEntityClassNames,
			String[] otherNanoparticleTypes,
			String[] functionalizingEntityClassNames,
			String[] otherFunctionalizingEntityTypes,
			String[] functionClassNames, String[] otherFunctionTypes)
			throws PublicationException, CaNanoLabSecurityException {
		List<PublicationBean> publicationBeans = new ArrayList<PublicationBean>();
		try {
			Publication[] publications = gridClient.getPublicationsBy(title, category, 
					nanoparticleName, researchArea, keywordsStr, pubMedId, 
					digitalObjectId, authorsStr, nanoparticleEntityClassNames, 
					otherNanoparticleTypes, functionalizingEntityClassNames, 
					otherFunctionalizingEntityTypes, functionClassNames, otherFunctionTypes);
			
			if (publications != null) {
				for (Publication publication : publications) {
					loadParticleSamplesForPublication(publication);
					publicationBeans.add(new PublicationBean(publication, true, false));
				}
			}
			Collections.sort(publicationBeans,
					new CaNanoLabComparators.PublicationBeanTitleComparator());
			return publicationBeans;
		} catch (RemoteException e) {
			logger.error(CaNanoLabConstants.NODE_UNAVAILABLE, e);
			//should show warning to user
			//throw new DocumentException(CaNanoLabConstants.NODE_UNAVAILABLE, e);
			return publicationBeans;
		} catch (Exception e) {
			String err = "Problem finding publication info.";
			logger.error(err, e);
			return publicationBeans;
			//if may cause by grid version incompatible
			//throw new DocumentException(err, e);
		}
	}



	public List<PublicationBean> findPublicationsByParticleSampleId(String particleId,
			boolean loadParticle, boolean loadAuthor)
		throws PublicationException {	
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.common.Publication");
			Association association = new Association();
			association
					.setName("gov.nih.nci.cananolab.domain.particle.NanoparticleSample");
			association.setRoleName("nanoparticleSampleCollection");
	
			Attribute attribute = new Attribute();
			attribute.setName("id");
			attribute.setPredicate(Predicate.EQUAL_TO);
			attribute.setValue(particleId);
			association.setAttribute(attribute);
	
			target.setAssociation(association);
			query.setTarget(target);
			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.common.Publication");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(
					results);
			Publication publication = null;
			List<PublicationBean> publications = new ArrayList<PublicationBean>();
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				publication = (Publication) obj;
				publications.add(new PublicationBean(publication));
			}
			return publications;
		} catch (RemoteException e) {
			logger.error(CaNanoLabConstants.NODE_UNAVAILABLE, e);
			//throw new DocumentException(CaNanoLabConstants.NODE_UNAVAILABLE, e);	
			return null;
		} catch (Exception e) {
			String err = "Error finding publications for particle.";
			logger.error(err, e);
			return null;
			//if may cause by grid version incompatible
			//throw new DocumentException(err, e);
		}
	}
	
	private void loadParticleSamplesForPublication(Publication publication)
		throws PublicationException {
		try {
			CQLQuery query = new CQLQuery();
		
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target
					.setName("gov.nih.nci.cananolab.domain.particle.NanoparticleSample");
			Association association = new Association();
			association.setName("gov.nih.nci.cananolab.domain.common.Publication");
			association.setRoleName("publicationCollection");
		
			Attribute attribute = new Attribute();
			attribute.setName("id");
			attribute.setPredicate(Predicate.EQUAL_TO);
			attribute.setValue(publication.getId().toString());
			association.setAttribute(attribute);
		
			target.setAssociation(association);
			query.setTarget(target);
			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.particle.NanoparticleSample");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			NanoparticleSample particleSample = null;
			publication
					.setNanoparticleSampleCollection(new HashSet<NanoparticleSample>());
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				particleSample = (NanoparticleSample) obj;
				publication.getNanoparticleSampleCollection().add(particleSample);
			}
		} catch (Exception e) {
			String err = "Problem loading nanoparticle samples for the publication : "
					+ publication.getId();
			logger.error(err, e);
			throw new PublicationException(err, e);
		}
	}
	
	
	public void loadAuthorsForPublication(Publication publication)
		throws PublicationException {
		try {
			CQLQuery query = new CQLQuery();
		
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target
					.setName("gov.nih.nci.cananolab.domain.common.Author");
			Association association = new Association();
			association.setName("gov.nih.nci.cananolab.domain.common.Publication");
			association.setRoleName("publicationCollection");	
		
			Attribute attribute = new Attribute();
			attribute.setName("id");
			attribute.setPredicate(Predicate.EQUAL_TO);
			attribute.setValue(publication.getId().toString());
			association.setAttribute(attribute);
		
			target.setAssociation(association);
			query.setTarget(target);
			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.common.Author");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			Author author = null;
			publication.setAuthorCollection(new HashSet<Author>());
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				author = (Author) obj;
				publication.getAuthorCollection().add(author);
			}
			
//			CQLQuery query = new CQLQuery();
//			
//			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
//			target
//					.setName("gov.nih.nci.cananolab.domain.common.Author");
//		
//			Attribute attribute = new Attribute();
//			attribute.setName("id");
//			attribute.setPredicate(Predicate.EQUAL_TO);
//			attribute.setValue("6881282");
//		
//			target.setAttribute(attribute);
//			query.setTarget(target);
//			CQLQueryResults results = gridClient.query(query);
//			results
//					.setTargetClassname("gov.nih.nci.cananolab.domain.common.Author");
//			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
//			Author author = null;
//			publication.setAuthorCollection(new HashSet<Author>());
//			while (iter.hasNext()) {
//				java.lang.Object obj = iter.next();
//				author = (Author) obj;
//				publication.getAuthorCollection().add(author);
//			}
		} catch (Exception e) {
			String err = "Problem loading authors for the publication : "
					+ publication.getId();
			logger.error(err, e);
			throw new PublicationException(err, e);
		}
	}
	
	public PublicationBean findPublicationById(String publicationId) throws PublicationException {
		try {
			Publication publication = findDomainPublicationById(publicationId);
			PublicationBean publicationBean = new PublicationBean(publication, false, true);
			return publicationBean;
		} catch (Exception e) {
			String err = "Problem finding the publication by id: " + publicationId;
			logger.error(err, e);
			throw new PublicationException(err, e);
		}
	}
	
	public Publication findDomainPublicationById(String publicationId) throws PublicationException{
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.common.Publication");
			Attribute attribute = new Attribute();
			attribute.setName("id");
			attribute.setPredicate(Predicate.EQUAL_TO);
			attribute.setValue(publicationId);
			target.setAttribute(attribute);
			query.setTarget(target);
			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.common.Publication");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			Publication publication = null;
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				publication = (Publication) obj;
			}
			loadParticleSamplesForPublication(publication);
			loadAuthorsForPublication(publication);
			return publication;
		} catch (RemoteException e) {
			logger.error(CaNanoLabConstants.NODE_UNAVAILABLE, e);
			throw new PublicationException(CaNanoLabConstants.NODE_UNAVAILABLE, e);	
		} catch (Exception e) {
			String err = "Problem finding the publication by id: " + publicationId;
			logger.error(err, e);
			throw new PublicationException(err, e);
		}
	}	

	public void exportDetail(PublicationBean aPub, OutputStream out)
		throws PublicationException{		
		try {
			PublicationServiceHelper helper = new PublicationServiceHelper();
			helper.exportDetail(aPub, out);
		} catch (Exception e) {
			String err = "error exporting detail view for "
					+ aPub.getDomainFile().getTitle();
			logger.error(err, e);
			throw new PublicationException(err, e);
		}
	}
	
	public int getNumberOfPublicPublications() throws PublicationException {
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.common.Publication");
			query.setTarget(target);
			QueryModifier modifier = new QueryModifier();
			modifier.setCountOnly(true);
			query.setQueryModifier(modifier);

			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.common.Publication");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			int count = 0;
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				count = ((Long) obj).intValue();
			}
			return count;
		} catch (Exception e) {
			String err = "Error finding counts of remote public publications.";
			logger.error(err, e);
			throw new PublicationException(err, e);
		}
	}
	
	public void exportSummary(ParticleBean particleBean,
			OutputStream out) throws IOException {
		PublicationServiceHelper helper = new PublicationServiceHelper();
		helper.exportSummary(particleBean, out);
	}
	
	public void removePublicationFromParticle(NanoparticleSample particle,
			Long dataId) 	throws PublicationException{
		throw new PublicationException("not implemented for grid service.");
	}
}
