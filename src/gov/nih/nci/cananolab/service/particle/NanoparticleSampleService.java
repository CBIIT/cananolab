package gov.nih.nci.cananolab.service.particle;

import gov.nih.nci.cananolab.domain.common.Keyword;
import gov.nih.nci.cananolab.domain.common.Source;
import gov.nih.nci.cananolab.domain.particle.NanoparticleSample;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.OtherFunction;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.base.OtherNanoparticleEntity;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.functionalization.OtherFunctionalizingEntity;
import gov.nih.nci.cananolab.dto.particle.NanoparticleSampleBean;
import gov.nih.nci.cananolab.exception.DuplicateEntriesException;
import gov.nih.nci.cananolab.exception.ParticleException;
import gov.nih.nci.cananolab.service.util.CaNanoLabComparators;
import gov.nih.nci.cananolab.system.applicationservice.CustomizedApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 * This class includes service calls involved around a nanoparticle sample
 * 
 * @author pansu
 * 
 */
public class NanoparticleSampleService {
	private static Logger logger = Logger
			.getLogger(NanoparticleSampleService.class);

	/**
	 * 
	 * @return all sample sources
	 */
	public SortedSet<Source> getAllSampleSources() throws ParticleException {
		SortedSet<Source> sampleSources = new TreeSet<Source>(
				new CaNanoLabComparators.SampleSourceComparator());
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();

			List results = appService.getAll(Source.class);
			for (Object obj : results) {
				sampleSources.add((Source) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all nanoparticle sample sources",
					e);
			throw new ParticleException();
		}

		return sampleSources;
	}

	/**
	 * Persist a new nanoparticle sample
	 * 
	 * @param particleSampleBean
	 * @throws Exception
	 */
	public void createNewNanoparticleSample(
			NanoparticleSampleBean particleSampleBean) throws Exception {
		NanoparticleSample particleSample = particleSampleBean
				.getParticleSample();

		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		NanoparticleSample dbParticle = (NanoparticleSample) appService
				.getObject(NanoparticleSample.class, "name", particleSample
						.getName());
		if (dbParticle != null) {
			throw new DuplicateEntriesException(
					"This nanoparticle sample ID has already been used.  Please use a different one");
		}

		Source dbSource = (Source) appService.getObject(Source.class,
				"organizationName", particleSample.getSource()
						.getOrganizationName());
		if (dbSource != null) {
			particleSample.setSource(dbSource);
		}

		Collection<Keyword> keywords = new HashSet<Keyword>();
		for (Keyword keyword : particleSample.getKeywordCollection()) {
			Keyword dbKeyword = (Keyword) appService.getObject(Keyword.class,
					"name", keyword.getName());
			if (dbKeyword != null) {
				keywords.add(dbKeyword);
			} else {
				keywords.add(keyword);
			}
		}
		particleSample.setKeywordCollection(keywords);
		appService.saveOrUpdate(particleSample);
	}

	/**
	 * Return user-defined function types
	 * 
	 * @return
	 * @throws ParticleException
	 */
	public SortedSet<String> getAllOtherFunctionTypes()
			throws ParticleException {
		SortedSet<String> types = new TreeSet<String>();
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();

			List results = appService.getAll(OtherFunction.class);
			for (Object obj : results) {
				OtherFunction other = (OtherFunction) obj;
				types.add(other.getType());
			}
		} catch (Exception e) {
			logger.error("Error in retrieving other function types", e);
			throw new ParticleException();
		}
		return types;
	}


	/**
	 * Return user-defined functionalizing entity types
	 * 
	 * @return
	 * @throws ParticleException
	 */
	public SortedSet<String> getAllOtherNanoparticleEntityTypes()
			throws ParticleException {
		SortedSet<String> types = new TreeSet<String>();
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();

			List results = appService.getAll(OtherNanoparticleEntity.class);
			for (Object obj : results) {
				OtherNanoparticleEntity other = (OtherNanoparticleEntity) obj;
				types.add(other.getType());
			}
		} catch (Exception e) {
			logger.error("Error in retrieving other values for nanoparticle entity", e);
			throw new ParticleException();
		}
		return types;
	}

	/**
	 * Return user-defined functionalizing entity types
	 * 
	 * @return
	 * @throws ParticleException
	 */
	public SortedSet<String> getAllOtherFunctionalizingEntityTypes()
			throws ParticleException {
		SortedSet<String> types = new TreeSet<String>();
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();

			List results = appService.getAll(OtherFunctionalizingEntity.class);
			for (Object obj : results) {
				OtherFunctionalizingEntity other = (OtherFunctionalizingEntity) obj;
				types.add(other.getType());
			}
		} catch (Exception e) {
			logger.error("Error in retrieving other values for functionalizing entity", e);
			throw new ParticleException();
		}
		return types;
	}

}
