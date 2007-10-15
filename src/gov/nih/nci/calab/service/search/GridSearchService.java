package gov.nih.nci.calab.service.search;

import gov.nih.nci.cagrid.cananolab.client.CaNanoLabSvcClient;
import gov.nih.nci.calab.domain.AssociatedFile;
import gov.nih.nci.calab.domain.Report;
import gov.nih.nci.calab.domain.Source;
import gov.nih.nci.calab.domain.nano.characterization.Characterization;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.ComposingElement;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.DendrimerComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.ParticleComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.SurfaceGroup;
import gov.nih.nci.calab.domain.nano.function.Function;
import gov.nih.nci.calab.domain.nano.particle.Nanoparticle;
import gov.nih.nci.calab.dto.characterization.CharacterizationBean;
import gov.nih.nci.calab.dto.common.LabFileBean;
import gov.nih.nci.calab.dto.function.FunctionBean;
import gov.nih.nci.calab.dto.particle.ParticleBean;
import gov.nih.nci.calab.dto.remote.GridNodeBean;
import gov.nih.nci.calab.service.common.LookupService;
import gov.nih.nci.calab.service.remote.RemoteQueryFacade;
import gov.nih.nci.calab.service.remote.RemoteQuerySystemPropertyConfigurer;
import gov.nih.nci.calab.service.util.CaNanoLabConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Remote search calls across the grid.
 * 
 * @author pansu
 * 
 */
public class GridSearchService {
	private static Logger logger = Logger.getLogger(GridSearchService.class);

	/**
	 * Retrieve remote reports files from the given grid node.
	 * 
	 * @param reportTitle
	 * @param reportType
	 * @param particleType
	 * @param functionTypes
	 * @param gridNode
	 * @return
	 * @throws Exception
	 */
	public List<LabFileBean> getRemoteReports(String reportTitle,
			String reportType, String particleType, String[] functionTypes,
			GridNodeBean gridNode) throws Exception {
		List<LabFileBean> reports = new ArrayList<LabFileBean>();

		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());
			if (reportType.equals(CaNanoLabConstants.REPORT)) {
				Report[] gridReports = gridClient.getReports(reportTitle,
						particleType, functionTypes);
				if (gridReports != null) {
					for (Report report : gridReports) {
						LabFileBean fileBean = new LabFileBean(report, gridNode
								.getHostName());
						fileBean.setInstanceType(CaNanoLabConstants.REPORT);
						reports.add(fileBean);
					}
				}
			} else if (reportType.equals(CaNanoLabConstants.ASSOCIATED_FILE)) {
				AssociatedFile[] gridAssociatedFiles = gridClient
						.getOtherAssociatedFiles(reportTitle, particleType,
								functionTypes);
				if (gridAssociatedFiles != null) {
					for (AssociatedFile report : gridAssociatedFiles) {
						LabFileBean fileBean = new LabFileBean(report, gridNode
								.getHostName());
						fileBean
								.setInstanceType(CaNanoLabConstants.ASSOCIATED_FILE);
						reports.add(fileBean);
					}
				}
			} else {
				Report[] gridReports = gridClient.getReports(reportTitle,
						particleType, functionTypes);
				if (gridReports != null) {
					for (Report report : gridReports) {
						LabFileBean fileBean = new LabFileBean(report, gridNode
								.getHostName());
						fileBean.setInstanceType(CaNanoLabConstants.REPORT);
						reports.add(fileBean);
					}
				}
				AssociatedFile[] gridAssociatedFiles = gridClient
						.getOtherAssociatedFiles(reportTitle, particleType,
								functionTypes);
				if (gridAssociatedFiles != null) {
					for (AssociatedFile report : gridAssociatedFiles) {
						LabFileBean fileBean = new LabFileBean(report, gridNode
								.getHostName());
						fileBean
								.setInstanceType(CaNanoLabConstants.ASSOCIATED_FILE);
						reports.add(fileBean);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in searching remote reports: " + e);
			throw e;
		}
		return reports;
	}

	/**
	 * Retrieve nanoparticles from the given grid node
	 * 
	 * @param particleType
	 * @param functionTypes
	 * @param characterizationTypes
	 * @param gridNode
	 * @return
	 * @throws Exception
	 */
	public List<ParticleBean> getRemoteNanoparticles(String particleType,
			String[] functionTypes, String[] characterizationTypes,
			GridNodeBean gridNode) throws Exception {
		List<ParticleBean> particles = new ArrayList<ParticleBean>();
		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());
			Nanoparticle[] gridParticles = gridClient.getNanoparticles(
					particleType, characterizationTypes, functionTypes);
			if (gridParticles != null) {
				for (Nanoparticle particle : gridParticles) {
					Characterization[] gridCharacterizations = gridClient
							.getCharacterizationsByParticleName(particle
									.getName());
					if (gridCharacterizations != null) {
						particle.setCharacterizationCollection(Arrays
								.asList(gridCharacterizations));
					}
					Function[] gridFunctions = gridClient
							.getFunctionsByParticleName(particle.getName());
					if (gridFunctions != null) {
						particle.setFunctionCollection(Arrays
								.asList(gridFunctions));
					}
					Source gridSource = gridClient
							.getSourceByParticleName(particle.getName());
					if (gridSource != null)
						particle.setSource(gridSource);
					particles.add(new ParticleBean(particle, gridNode
							.getHostName()));
				}
			}
		} catch (Exception e) {
			logger.error("Error in searching remote particles: " + e);
			throw e;
		}
		return particles;
	}

	public Nanoparticle getRemoteNanoparticle(String particleName,
			GridNodeBean gridNode) throws Exception {
		Nanoparticle particle = null;
		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());
			particle = gridClient.getNanoparticle(particleName);
			Characterization[] gridCharacterizations = gridClient
					.getCharacterizationsByParticleName(particle.getName());
			if (gridCharacterizations != null) {
				particle.setCharacterizationCollection(Arrays
						.asList(gridCharacterizations));
			}
			Function[] gridFunctions = gridClient
					.getFunctionsByParticleName(particle.getName());
			if (gridFunctions != null) {
				particle.setFunctionCollection(Arrays.asList(gridFunctions));
			}
			Source gridSource = gridClient.getSourceByParticleName(particle
					.getName());
			if (gridSource != null)
				particle.setSource(gridSource);
		} catch (Exception e) {
			logger.error("Error in getting remote particle: " + e);
			throw e;
		}
		return particle;
	}

	/**
	 * Get a lookup table with key being characterization type and value being a
	 * list of CharacterizationBeans the given particle has.
	 * 
	 * @param particleName
	 * @param gridNode
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<CharacterizationBean>> getRemoteCharacterizationMap(
			String particleName, GridNodeBean gridNode) throws Exception {
		Map<String, List<CharacterizationBean>> charTypeChars = null;

		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());
			// get saved characterizations saved in a remote node
			Characterization[] gridCharacterizations = gridClient
					.getCharacterizationsByParticleName(particleName);
			// get all characterization type characterizations
			charTypeChars = new HashMap<String, List<CharacterizationBean>>();

			// set up lookup table, key characterization type, value
			// characterizationBeans for the side menu. Keep the
			// characterizations types in order as in lookupService.
			if (gridCharacterizations != null) {
				LookupService lookupService = new LookupService();
				Map<String, List<CharacterizationBean>> orderedCharTypeChars = lookupService
						.getCharacterizationTypeCharacterizations();
				// set abbreviation for each saved characterization
				for (String charType : orderedCharTypeChars.keySet()) {
					List<CharacterizationBean> newCharBeans = new ArrayList<CharacterizationBean>();
					List<CharacterizationBean> orderedCharList = orderedCharTypeChars
							.get(charType);
					for (Characterization chara : gridCharacterizations) {
						for (CharacterizationBean displayBean : orderedCharList) {
							if (displayBean.getName().equals(chara.getName())) {
								CharacterizationBean charBean = new CharacterizationBean(
										chara);
								charBean.setAbbr(displayBean.getAbbr());
								newCharBeans.add(charBean);
							}
						}
					}
					if (!newCharBeans.isEmpty()) {
						charTypeChars.put(charType, newCharBeans);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in getting remote characterization map: " + e);
			throw e;
		}
		return charTypeChars;
	}

	public Map<String, List<FunctionBean>> getRemoteFunctionMap(
			String particleName, GridNodeBean gridNode) throws Exception {
		Map<String, List<FunctionBean>> funcTypeFuncs = null;
		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());
			Function[] gridFunctions = gridClient
					.getFunctionsByParticleName(particleName);

			funcTypeFuncs = new HashMap<String, List<FunctionBean>>();
			List<FunctionBean> funcs = new ArrayList<FunctionBean>();
			if (gridFunctions != null) {
				for (Function func : gridFunctions) {
					if (funcTypeFuncs.get(func.getType()) != null) {
						funcs = (List<FunctionBean>) (funcTypeFuncs.get(func
								.getType()));
					} else {
						funcs = new ArrayList<FunctionBean>();
						funcTypeFuncs.put(func.getType(), funcs);
					}
					funcs.add(new FunctionBean(func));
				}
			}
		} catch (Exception e) {
			logger.error("Error in getting remote function map: " + e);
			throw e;
		}
		return funcTypeFuncs;
	}

	public List<LabFileBean> getRemoteReports(String particleName,
			GridNodeBean gridNode) throws Exception {
		List<LabFileBean> reports = null;
		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());

			reports = new ArrayList<LabFileBean>();
			Report[] gridReports = gridClient
					.getReportsByParticleName(particleName);
			if (gridReports != null) {
				for (Report report : gridReports) {
					LabFileBean fileBean = new LabFileBean(report, gridNode
							.getHostName());
					fileBean.setInstanceType(CaNanoLabConstants.REPORT);
					reports.add(fileBean);
				}
			}
		} catch (Exception e) {
			logger.error("Error in getting remote reports: " + e);
			throw e;
		}
		return reports;
	}

	public List<LabFileBean> getRemoteAssociatedFiles(String particleName,
			GridNodeBean gridNode) throws Exception {
		List<LabFileBean> files = null;
		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());

			files = new ArrayList<LabFileBean>();
			AssociatedFile[] gridFiles = gridClient
					.getOtherAssociatedFilesByParticleName(particleName);
			if (gridFiles != null) {
				for (AssociatedFile file : gridFiles) {
					LabFileBean fileBean = new LabFileBean(file, gridNode
							.getHostName());
					fileBean
							.setInstanceType(CaNanoLabConstants.ASSOCIATED_FILE);
					files.add(fileBean);
				}
			}
		} catch (Exception e) {
			logger.error("Error in getting remote files: " + e);
			throw e;
		}
		return files;
	}

	public ParticleComposition getRemoteComposition(String compositionId,
			String particleName, GridNodeBean gridNode) throws Exception {
		ParticleComposition comp = null;
		try {
			CaNanoLabSvcClient gridClient = new CaNanoLabSvcClient(gridNode
					.getAddress());
			comp = (ParticleComposition) gridClient.getCharacterization(Long
					.parseLong(compositionId), particleName);
			ComposingElement[] composingElements = gridClient
					.getComposingElements(Long.parseLong(compositionId),
							particleName);
			if (comp != null && composingElements != null) {
				comp.setComposingElementCollection(Arrays
						.asList(composingElements));
			}
			if (comp instanceof DendrimerComposition) {
				SurfaceGroup[] surfaceGroups = gridClient.getSurfaceGroups(Long
						.parseLong(compositionId), particleName);
				if (comp != null && surfaceGroups != null) {
					((DendrimerComposition) comp)
							.setSurfaceGroupCollection(Arrays
									.asList(surfaceGroups));
				}
			}
		} catch (Exception e) {
			logger.error("Error in getting remote composition: " + e);
			throw e;
		}
		return comp;
	}

	/**
	 * Retrieve the content of the remote file directly from the webapp hosting
	 * the file (not going through the grid).
	 * 
	 * @param fileId
	 * @param gridNode
	 * @return
	 * @throws Exception
	 */
	public byte[] getRemoteFileContent(String fileId, GridNodeBean gridNode)
			throws Exception {
		byte[] fileContent = null;
		try {
			String remoteCodeBase = RemoteQuerySystemPropertyConfigurer
					.getRemoteServiceUrlCodebase(gridNode.getAppServiceURL());
			// dynamically set system property to contain the remote caNanoLab
			// hostname
			System.getProperties().put("remote.codebase", remoteCodeBase);

			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					"caNanoLabClientContext.xml");
			RemoteQueryFacade remoteQueryFacade = (RemoteQueryFacade) ctx
					.getBean("remoteQueryProxy");
			fileContent = remoteQueryFacade.retrievePublicFileContent(Long
					.parseLong(fileId));
		} catch (Exception e) {
			logger.error("Error in getting remote file content: " + e);
			throw e;
		}
		return fileContent;
	}
}
