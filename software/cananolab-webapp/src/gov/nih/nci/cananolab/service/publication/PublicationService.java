/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.cananolab.service.publication;

import gov.nih.nci.cananolab.domain.common.Publication;
import gov.nih.nci.cananolab.dto.common.AccessibilityBean;
import gov.nih.nci.cananolab.dto.common.PublicationBean;
import gov.nih.nci.cananolab.exception.NoAccessException;
import gov.nih.nci.cananolab.exception.PublicationException;
import gov.nih.nci.cananolab.security.AccessControlInfo;
import gov.nih.nci.cananolab.service.BaseService;
import gov.nih.nci.cananolab.service.publication.helper.PublicationServiceHelper;

import java.util.List;

/**
 * Interface defining methods invovled in submiting and searching publications.
 *
 * @author tanq
 *
 */
public interface PublicationService extends BaseService {

	/**
	 * Persist a new publication or update an existing publication
	 *
	 * @param publication
	 * @param sampleNames
	 * @param fileData
	 * @param authors
	 *
	 * @throws Exception
	 */
	public void savePublication(PublicationBean publicationBean)
			throws PublicationException, NoAccessException;

	public PublicationBean findPublicationById(String publicationId,
			Boolean loadAccessInfo) throws PublicationException,
			NoAccessException;

	public PublicationBean findPublicationByKey(String keyName,
			Object keyValue, Boolean loadAccessInfo)
			throws PublicationException, NoAccessException;

	public List<PublicationBean> findPublicationsBySampleId(String sampleId)
			throws PublicationException;

	public int getNumberOfPublicPublications() throws PublicationException;
	
	public int getNumberOfPublicPublicationsForJob() throws PublicationException;

	public List<String> findPublicationIdsBy(String title, String category,
			String sampleName, String[] researchAreas, String[] keywords,
			String pubMedId, String digitalObjectId, String[] authors,
			String[] nanomaterialEntityClassNames,
			String[] otherNanomaterialEntityTypes,
			String[] functionalizingEntityClassNames,
			String[] otherFunctionalizingEntityTypes,
			String[] functionClassNames, String[] otherFunctionTypes)
			throws PublicationException;

	public void deletePublication(Publication publication)
			throws PublicationException, NoAccessException;

	/**
	 * Parse PubMed XML file and store the information into a PublicationBean
	 *
	 * @param pubMedId
	 * @return
	 * @throws PublicationException
	 */
	public PublicationBean getPublicationFromPubMedXML(String pubMedId)
			throws PublicationException;

	public PublicationBean findNonPubMedNonDOIPublication(
			String publicationType, String title, String firstAuthorLastName,
			String firstAuthorFirstName) throws PublicationException;

	public void removePublicationFromSample(String sampleName,
			Publication publication) throws PublicationException,
			NoAccessException;

	public void assignAccessibility(AccessControlInfo access,
			Publication publication) throws PublicationException,
			NoAccessException;

	public void removeAccessibility(AccessControlInfo access,
			Publication publication) throws PublicationException,
			NoAccessException;

	public List<String> findPublicationIdsByOwner(String currentOwner)
			throws PublicationException;

	public PublicationBean findPublicationByIdWorkspace(String id, boolean b)
			throws PublicationException;
	
	public PublicationServiceHelper getPublicationServiceHelper();
}
