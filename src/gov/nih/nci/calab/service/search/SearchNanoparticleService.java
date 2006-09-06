package gov.nih.nci.calab.service.search;

import gov.nih.nci.calab.db.DataAccessProxy;
import gov.nih.nci.calab.db.IDataAccess;
import gov.nih.nci.calab.domain.nano.particle.Nanoparticle;
import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.dto.particle.ParticleBean;
import gov.nih.nci.calab.exception.CalabException;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.service.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class includes methods invovled in searching nanoparticles.
 * 
 * @author pansu
 * 
 */
public class SearchNanoparticleService {
	private static Logger logger = Logger
			.getLogger(SearchNanoparticleService.class);

	/**
	 * Search for nanoparticles based on particle source, type, function types,
	 * characterizationType, characterizations, keywords and filter the
	 * nanoparticles by user visibility.
	 * 
	 * @param particleSource
	 * @param particleType
	 * @param functionTypes
	 * @param characterizationType
	 * @param characterizations
	 * @param keywords
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<ParticleBean> basicSearch(String particleSource,
			String particleType, String[] functionTypes,
			String[] characterizations, String[] keywords, String keywordType,
			UserBean user) throws Exception {
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		String[] particleKeywords = null;
		String[] assayResultKeywords = null;
		if (keywordType.equals("nanoparticle")) {
			particleKeywords = keywords;
		} else {
			assayResultKeywords = keywords;
		}
		List<ParticleBean> particles = new ArrayList<ParticleBean>();

		try {
			List<Object> paramList = new ArrayList<Object>();
			List<String> whereList = new ArrayList<String>();

			String where = "";
			String keywordFrom = "";
			String functionFrom = "";

			if (particleSource != null && particleSource.length() > 0) {
				where = "where ";
				whereList.add("particle.source.organizationName=? ");
				paramList.add(particleSource);
			}
			if (particleType != null && particleType.length() > 0) {
				paramList.add(particleType);
				where = "where ";
				whereList.add("particle.type=? ");
			}

			if (functionTypes != null && functionTypes.length > 0) {
				List<String> inList = new ArrayList<String>();
				where = "where ";
				for (String functionType : functionTypes) {
					paramList.add(functionType);
					inList.add("?");
				}
				functionFrom = "left join particle.functionCollection function ";
				whereList.add("function.type in ("
						+ StringUtils.join(inList, ", ") + ") ");
			}

			if (particleKeywords != null && particleKeywords.length > 0) {
				List<String> inList = new ArrayList<String>();
				where = "where ";
				for (String keyword : particleKeywords) {
					paramList.add(keyword);
					inList.add("?");
				}
				keywordFrom = "left join particle.keywordCollection keyword ";

				whereList.add("keyword.name in ("
						+ StringUtils.join(inList, ", ") + ") ");
			}

			String whereStr = StringUtils.join(whereList, " and ");
			String hqlString = "select particle from Nanoparticle as particle left join fetch particle.characterizationCollection "
					+ functionFrom + keywordFrom + where + whereStr;

			ida.open();

			List<? extends Object> results = (List<? extends Object>) ida
					.searchByParam(hqlString, paramList);
			for (Object obj : new HashSet<Object>(results)) {
				Nanoparticle particle = (Nanoparticle) obj;
				ParticleBean particleBean = new ParticleBean(particle);
				// filter by characterization types
				boolean matchedCharacterization = false;
				for (String chara : characterizations) {
					for (String charb : particleBean.getCharacterizationTypes()) {
						if (charb.endsWith(chara)) {
							matchedCharacterization = true;
							break;
						}
					}
				}
				if (characterizations.length==0 || matchedCharacterization) {
					particles.add(particleBean);
				}
			}
		} catch (Exception e) {
			logger
					.error("Problem finding particles with thet given search parameters ");
			throw e;
		} finally {
			ida.close();
		}

		UserService userService = new UserService(CalabConstants.CSM_APP_NAME);

		List<ParticleBean> filteredParticles = userService
				.getFilteredParticles(user, particles);

		return filteredParticles;
	}

	/**
	 * Query nanoparticle general information such as name, type, keywords and
	 * visibilities.
	 * 
	 * @param particleName
	 * @param particleType
	 * @return
	 * @throws Exception
	 */
	public ParticleBean getGeneralInfo(String particleName, String particleType)
			throws Exception {

		Nanoparticle particle = null;
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			// get the existing particle from database created during sample
			// creation
			List results = ida
					.search("from Nanoparticle as particle left join fetch particle.characterizationCollection left join fetch particle.keywordCollection where particle.name='"
							+ particleName
							+ "' and particle.type='"
							+ particleType + "'");

			for (Object obj : results) {
				particle = (Nanoparticle) obj;
			}
			if (particle == null) {
				throw new CalabException("No such particle in the database");
			}
		} catch (Exception e) {
			logger.error("Problem finding particle with name: " + particleName);
			throw e;
		} finally {
			ida.close();
		}

		ParticleBean particleBean = new ParticleBean(particle);
		UserService userService = new UserService(CalabConstants.CSM_APP_NAME);
		List<String> accessibleGroups = userService.getAccessibleGroups(
				particleName, "R");
		String[] visibilityGroups = accessibleGroups.toArray(new String[0]);
		particleBean.setVisibilityGroups(visibilityGroups);

		return particleBean;
	}

	public ParticleBean getCharacterizationInfo(String particleName, String particleType)
			throws Exception {

		Nanoparticle particle = null;
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			// get the existing particle from database created during sample
			// creation
//			List results = ida
//					.search("from Nanoparticle as particle left join fetch particle.characterizationCollection left join fetch particle.keywordCollection where particle.name='"
//							+ particleName
//							+ "' and particle.type='"
//							+ particleType + "'");
			List results = ida
			.search("from Nanoparticle as particle where particle.name='"
					+ particleName
					+ "' and particle.type='"
					+ particleType + "'");

			for (Object obj : results) {
				particle = (Nanoparticle) obj;
			}
			if (particle == null) {
				throw new CalabException("No such particle in the database");
			}
		} catch (Exception e) {
			logger.error("Problem finding particle with name: " + particleName);
			throw e;
		} finally {
			ida.close();
		}

		ParticleBean particleBean = new ParticleBean(particle);
		UserService userService = new UserService(CalabConstants.CSM_APP_NAME);
		List<String> accessibleGroups = userService.getAccessibleGroups(
				particleName, "R");
		String[] visibilityGroups = accessibleGroups.toArray(new String[0]);
		particleBean.setVisibilityGroups(visibilityGroups);

		return particleBean;
	}
}
