package gov.nih.nci.cananolab.service.document.impl;

import gov.nih.nci.cagrid.cananolab.client.CaNanoLabServiceClient;
import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlquery.QueryModifier;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cananolab.domain.common.LabFile;
import gov.nih.nci.cananolab.domain.common.Report;
import gov.nih.nci.cananolab.domain.particle.NanoparticleSample;
import gov.nih.nci.cananolab.exception.DocumentException;
import gov.nih.nci.cananolab.exception.ProtocolException;
import gov.nih.nci.cananolab.service.document.DocumentService;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Remote implementation of DocumentService
 * 
 * @author tanq
 * 
 */
public class DocumentServiceRemoteImpl implements DocumentService {
	private static Logger logger = Logger
			.getLogger(DocumentServiceRemoteImpl.class);
	private CaNanoLabServiceClient gridClient;

	public DocumentServiceRemoteImpl(String serviceUrl) throws Exception {
		gridClient = new CaNanoLabServiceClient(serviceUrl);
	}


	//TODO
	public int getNumberOfPublicDocuments() throws DocumentException {
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.common.Report");
			query.setTarget(target);
			QueryModifier modifier = new QueryModifier();
			modifier.setCountOnly(true);
			query.setQueryModifier(modifier);

			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.common.Report");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			int count = 0;
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				count = ((Long) obj).intValue();
			}
			return count;
		} catch (Exception e) {
			String err = "Error finding counts of remote public reports.";
			logger.error(err, e);
			throw new DocumentException(err, e);
		}
	}

	//TODO XXXXXXXXXXXXXX
	public LabFile[] findDocumentsByParticleSampleId(String particleId)
			throws DocumentException {
		{
			try {
				CQLQuery query = new CQLQuery();
				gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
				target.setName("gov.nih.nci.cananolab.domain.common.Report");
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
						.setTargetClassname("gov.nih.nci.cananolab.domain.common.Report");
				CQLQueryResultsIterator iter = new CQLQueryResultsIterator(
						results);
				Report report = null;
				List<Report> reports = new ArrayList<Report>();
				while (iter.hasNext()) {
					java.lang.Object obj = iter.next();
					report = (Report) obj;
					reports.add(report);
				}
				//return reports.toArray(new Report[0]);
				//TODO
				return null;
			} catch (RemoteException e) {
				logger.error(CaNanoLabConstants.NODE_UNAVAILABLE, e);
				throw new DocumentException(CaNanoLabConstants.NODE_UNAVAILABLE, e);	
			} catch (Exception e) {
				String err = "Error finding reports for particle.";
				logger.error(err, e);
				throw new DocumentException(err, e);
			}
		}
	}
	
	public void removeDocumentFromParticle(NanoparticleSample particle,
			Long dataId) 	throws DocumentException{
		throw new DocumentException("not implemented for grid service.");
	}
}
