package gov.nih.nci.cananolab.service.particle;

import gov.nih.nci.cananolab.domain.common.Instrument;
import gov.nih.nci.cananolab.domain.particle.NanoparticleSample;
import gov.nih.nci.cananolab.domain.particle.characterization.Characterization;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationSummaryBean;
import gov.nih.nci.cananolab.exception.ParticleCharacterizationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.SortedSet;


/**
 * Interface defining service methods involving characterizations
 * 
 * @author pansu, tanq
 * 
 */
public interface NanoparticleCharacterizationService {
	
	
	public void saveCharacterization(NanoparticleSample particleSample,
			Characterization achar) throws Exception ;

	public Characterization findCharacterizationById(String charId)
			throws ParticleCharacterizationException;

	public Boolean checkRedundantViewTitle(NanoparticleSample particleSample,
			Characterization chara) throws ParticleCharacterizationException ;
	
	public SortedSet<String> findAllCharacterizationSources()
			throws ParticleCharacterizationException;

	public List<Instrument> findAllInstruments()
			throws ParticleCharacterizationException;

	public Instrument findInstrumentBy(String instrumentType,
			String manufacturer) throws ParticleCharacterizationException;
	
	// for dwr ajax
	public String getInstrumentAbbreviation(String instrumentType)
			throws ParticleCharacterizationException;

	// use in dwr ajax
	public String[] getDerivedDatumValueUnits(String derivedDatumName)
			throws ParticleCharacterizationException;

	public SortedSet<Characterization> findParticleCharacterizationsByClass(
			String particleName, String className)
			throws ParticleCharacterizationException;

	public CharacterizationSummaryBean getParticleCharacterizationSummaryByClass(
			String particleName, String className, UserBean user)
			throws ParticleCharacterizationException;

	// set lab file visibility of a characterization
	public void retrieveVisiblity(CharacterizationBean charBean, UserBean user)
			throws ParticleCharacterizationException;

	public void exportDetail(CharacterizationBean achar, OutputStream out)
			throws ParticleCharacterizationException;

	//private short setDetailSheet(CharacterizationBean achar, HSSFWorkbook wb,
			//HSSFSheet sheet, HSSFPatriarch patriarch, short rowCount);

	public void exportSummary(CharacterizationSummaryBean summaryBean,
			OutputStream out) throws IOException;
	
	//private short setSummarySheet(CharacterizationSummaryBean summaryBean,
			//HSSFWorkbook wb, HSSFSheet sheet, short rowCount);
	
	public void deleteCharacterization(Characterization chara)
			throws ParticleCharacterizationException;
	
	public void exportFullSummary(CharacterizationSummaryBean summaryBean,
			OutputStream out) throws IOException;
}