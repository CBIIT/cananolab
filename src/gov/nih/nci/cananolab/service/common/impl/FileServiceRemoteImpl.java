package gov.nih.nci.cananolab.service.common.impl;

import gov.nih.nci.cagrid.cananolab.client.CaNanoLabServiceClient;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cananolab.domain.common.Keyword;
import gov.nih.nci.cananolab.domain.common.LabFile;
import gov.nih.nci.cananolab.dto.common.LabFileBean;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.exception.CaNanoLabSecurityException;
import gov.nih.nci.cananolab.exception.FileException;
import gov.nih.nci.cananolab.service.common.FileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Remote implementation of FileService
 * 
 * @author pansu
 * 
 */
public class FileServiceRemoteImpl implements FileService {
	Logger logger = Logger.getLogger(FileServiceRemoteImpl.class);
	private CaNanoLabServiceClient gridClient;

	public FileServiceRemoteImpl(String serviceUrl) throws Exception {
		gridClient = new CaNanoLabServiceClient(serviceUrl);
	}

	/**
	 * Load the file for the given fileId from the database
	 * 
	 * @param fileId
	 * @return
	 */
	public LabFileBean findFileById(String fileId) throws FileException {
		LabFileBean fileBean = null;
		try {
			CQLQuery query = new CQLQuery();
			gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
			target.setName("gov.nih.nci.cananolab.domain.common.LabFile");
			Attribute attribute = new Attribute();
			attribute.setName("id");
			attribute.setPredicate(Predicate.EQUAL_TO);
			attribute.setValue(fileId);
			target.setAttribute(attribute);
			query.setTarget(target);
			CQLQueryResults results = gridClient.query(query);
			results
					.setTargetClassname("gov.nih.nci.cananolab.domain.common.LabFile");
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results);
			LabFile file = null;
			while (iter.hasNext()) {
				java.lang.Object obj = iter.next();
				file = (LabFile) obj;
				loadKeywordsForFile(file); 
			}
			if (file != null) {
				fileBean = new LabFileBean(file);
			}
			return fileBean;
		} catch (Exception e) {
			logger.error("Problem finding the file by id: " + fileId, e);
			throw new FileException();
		}
	}

	/**
	 * Load the file for the given fileId from the database. Also check whether
	 * user can do it.
	 * 
	 * @param fileId
	 * @return
	 */
	public LabFileBean findFileById(String fileId, UserBean user)
			throws FileException, CaNanoLabSecurityException {
		LabFileBean fileBean = null;
		try {
			// remote service already filtered out non-public files
			fileBean = findFileById(fileId);
			return fileBean;
		} catch (Exception e) {
			logger.error("Problem finding the file by id: " + fileId, e);
			throw new FileException();
		}
	}

	public List<LabFile> findFilesByCompositionInfoId(String id,
			String className) throws FileException {
		try {
			List<LabFile> fileSet = new ArrayList<LabFile>();
			LabFile[] files = gridClient.getLabFilesByCompositionInfoId(id,
					className);
			if (files != null) {
				for (LabFile file : files) {
					loadKeywordsForFile(file);
					fileSet.add(file);
				}
			}
			return fileSet;
		} catch (Exception e) {
			String err = "Error finding files by " + className + "and id " + id;
			logger.error(err, e);
			throw new FileException(err, e);
		}
	}

	private void loadKeywordsForFile(LabFile file) throws Exception {
		Keyword[] keywords = gridClient.getKeywordsByFileId(file.getId()
				.toString());
		if (keywords != null & keywords.length > 0) {
			file.setKeywordCollection(new HashSet<Keyword>(Arrays
					.asList(keywords)));
		}
	}

	public void saveCopiedFileAndSetVisibility(LabFile copy, UserBean user,
			String oldSampleName, String newSampleName) throws FileException {
		throw new FileException("Not implemented for grid service");
	}

	// save to the file system fileData is not empty
	public void writeFile(LabFile file, byte[] fileData) throws FileException {
		throw new FileException("Not implemented for grid service");
	}

	/**
	 * Preparing keywords and other information prior to saving a file
	 * 
	 * @param file
	 * @throws FileException
	 */
	public void prepareSaveFile(LabFile file) throws FileException {
		throw new FileException("Not implemented for grid service");
	}

	// retrieve file visibility
	public void retrieveVisibility(LabFileBean fileBean, UserBean user)
			throws FileException {
		throw new FileException("Not implemented for grid service");
	}
}
