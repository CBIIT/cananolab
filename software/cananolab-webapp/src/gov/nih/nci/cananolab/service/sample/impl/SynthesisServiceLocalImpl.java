package gov.nih.nci.cananolab.service.sample.impl;

import gov.nih.nci.cananolab.domain.characterization.OtherCharacterization;
import gov.nih.nci.cananolab.domain.common.Datum;
import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.domain.common.Instrument;
import gov.nih.nci.cananolab.domain.common.PurificationConfig;
import gov.nih.nci.cananolab.domain.common.PurityColumnHeader;

import gov.nih.nci.cananolab.domain.common.PurityDatumCondition;
import gov.nih.nci.cananolab.domain.common.Supplier;
import gov.nih.nci.cananolab.domain.particle.*;
import gov.nih.nci.cananolab.dto.common.FileBean;

import gov.nih.nci.cananolab.dto.particle.SampleBean;


import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisFunctionalizationBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisFunctionalizationElementBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisMaterialBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisMaterialElementBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisPurificationBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisPurityBean;
import gov.nih.nci.cananolab.exception.CompositionException;
import gov.nih.nci.cananolab.exception.NoAccessException;
import gov.nih.nci.cananolab.exception.PointOfContactException;
import gov.nih.nci.cananolab.exception.SampleException;
import gov.nih.nci.cananolab.exception.SynthesisException;
import gov.nih.nci.cananolab.security.enums.SecureClassesEnum;
import gov.nih.nci.cananolab.security.service.SpringSecurityAclService;
import gov.nih.nci.cananolab.security.utils.SpringSecurityUtil;
import gov.nih.nci.cananolab.service.BaseServiceLocalImpl;
import gov.nih.nci.cananolab.service.sample.SynthesisService;
import gov.nih.nci.cananolab.service.sample.helper.SynthesisHelper;
import gov.nih.nci.cananolab.system.applicationservice.ApplicationException;
import gov.nih.nci.cananolab.system.applicationservice.CaNanoLabApplicationService;
import gov.nih.nci.cananolab.system.applicationservice.client.ApplicationServiceProvider;
import gov.nih.nci.cananolab.system.query.hibernate.HQLCriteria;
import gov.nih.nci.cananolab.util.Comparators;
import gov.nih.nci.cananolab.util.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.LongToDoubleFunction;
import net.sf.ehcache.management.sampled.SampledEhcacheMBeans;
import net.sf.ehcache.management.sampled.SampledMBeanRegistrationProvider;
import org.apache.log4j.Logger;
import org.bouncycastle.util.test.Test;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import sun.tools.tree.ThisExpression;

@Component("synthesisService")
public class SynthesisServiceLocalImpl extends BaseServiceLocalImpl implements SynthesisService {
    private static Logger logger = org.apache.log4j.Logger.getLogger(SynthesisServiceLocalImpl.class);

    @Autowired
    private SynthesisHelper synthesisHelper;

    @Autowired
    private SpringSecurityAclService springSecurityAclService;

    public void copyAndSaveSynthesisFunctionalization(SynthesisFunctionalizationBean entityBean,
                                                      SampleBean oldSampleBean, SampleBean[] newSampleBeans) throws SynthesisException, NoAccessException {
        try {
            for (SampleBean sampleBean : newSampleBeans) {
                SynthesisFunctionalizationBean copyBean = null;
                SynthesisFunctionalization copy = entityBean.getDomainCopy(SpringSecurityUtil.getLoggedInUserName());
                try {
                    copyBean = new SynthesisFunctionalizationBean(copy);
                    for (FileBean fileBean : copyBean.getFiles()) {
                        fileUtils.updateClonedFileInfo(fileBean, oldSampleBean.getDomain().getName(),
                                sampleBean.getDomain().getName());
                    }
                }
                catch (Exception e) {
                    String err = "Problem saving Synthesis Functionalization MHL010";
                    logger.error(err, e);
                    throw new SynthesisException(err, e);
                }
                if (copyBean != null) {
                    saveSynthesisFunctionalization(sampleBean, copyBean);
                }

            }
        }
        catch (NoAccessException e) {
            throw e;
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Functionalization MHL011";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void copyAndSaveSynthesisMaterial(SynthesisMaterialBean entityBean, SampleBean oldSampleBean,
                                             SampleBean[] newSampleBeans) throws SynthesisException, NoAccessException {
        try {
            for (SampleBean sampleBean : newSampleBeans) {
                SynthesisMaterialBean copyBean = null;
                SynthesisMaterial copy = entityBean.getDomainCopy(SpringSecurityUtil.getLoggedInUserName());
                try {
                    copyBean = new SynthesisMaterialBean(copy);
                    for (FileBean fileBean : copyBean.getFiles()) {
                        fileUtils.updateClonedFileInfo(fileBean, oldSampleBean.getDomain().getName(),
                                sampleBean.getDomain().getName());
                    }
                }
                catch (Exception e) {
                    String err = "Problem saving Synthesis Material";
                    logger.error(err, e);
                    throw new SynthesisException(err, e);
                }
                if (copyBean != null) {
                    saveSynthesisMaterial(sampleBean, copyBean);
                }

            }
        }
        catch (NoAccessException e) {
            throw e;
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Material";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }

    }

    public void copyAndSaveSynthesisPurification(SynthesisPurificationBean entityBean, SampleBean oldSampleBean,
                                                 SampleBean[] newSampleBeans) throws SynthesisException,
            NoAccessException {
        try {
            for (SampleBean sampleBean : newSampleBeans) {
                SynthesisPurificationBean copyBean = null;
                SynthesisPurification copy = entityBean.getDomainCopy(SpringSecurityUtil.getLoggedInUserName(), true);
                try {
                    copyBean = new SynthesisPurificationBean(copy);
                    for (FileBean fileBean : copyBean.getFiles()) {
                        fileUtils.updateClonedFileInfo(fileBean, oldSampleBean.getDomain().getName(),
                                sampleBean.getDomain().getName());
                    }
                }
                catch (Exception e) {
                    String err = "Problem saving Synthesis Purification";
                    logger.error(err, e);
                    throw new SynthesisException(err, e);
                }
                if (copyBean != null) {
                    saveSynthesisPurification(sampleBean, copyBean);
                }

            }
        }
        catch (NoAccessException e) {
            throw e;
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Purification";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void deleteSynthesis(Synthesis synthesis) throws SynthesisException, NoAccessException {
        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        Long sampleId = synthesis.getSample().getId();
        if (synthesis.getSynthesisPurifications() != null) {
            for (SynthesisPurification purification : synthesis.getSynthesisPurifications()) {
                deleteSynthesisPurification(sampleId, purification);
            }
        }
        synthesis.setSynthesisPurifications(null);

        if (synthesis.getSynthesisFunctionalizations() != null) {
            for (SynthesisFunctionalization functionalization : synthesis.getSynthesisFunctionalizations()) {
                deleteSynthesisFunctionalization(sampleId, functionalization);
            }
        }
        synthesis.setSynthesisFunctionalizations(null);

        if (synthesis.getSynthesisMaterials() != null) {
            for (SynthesisMaterial synthesisMaterial : synthesis.getSynthesisMaterials()) {
                deleteSynthesisMaterial(sampleId, synthesisMaterial);
            }
        }
        synthesis.setSynthesisMaterials(null);

        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            appService.delete(synthesis);
        }
        catch (Exception e) {
            String err = "Problem deleting synthesis by id: " + synthesis.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    // TODO
    public void deleteSynthesisFunctionalization(Long sampleId,
                                                 SynthesisFunctionalization synthesisFunctionalization) throws SynthesisException, NoAccessException {
/*

            if (SpringSecurityUtil.getPrincipal() == null) {
                throw new NoAccessException();
            }
            try {
                //Delete attached files
                if(synthesisFunctionalization.getFileCollection()!= null){
                    for(File file:synthesisFunctionalization.getFileCollection()){
                        deleteSynthesisFunctionalizationFile(synthesisFunctionalization,file);
                    }
                }

                //Delete attached functionalization elements
                if(synthesisFunctionalization.getSynthesisFunctionalizationElements() !=null){
                    for(SynthesisFunctionalizationElement element: synthesisFunctionalization
                    .getSynthesisFunctionalizationElements()){
                        deleteSynthesisFunctionalizationElement(sampleId, synthesisFunctionalization,element);
                    }
                }


                CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                        .getApplicationService();
                appService.delete(synthesisFunctionalization);
            } catch (Exception e) {
                String err = "Error deleting synthesis functionalization " + synthesisFunctionalization.getId();
                logger.error(err, e);
                throw new SynthesisException(err, e);
            }

*/
    }

    public void deleteSynthesisMaterial(Long sampleId, SynthesisMaterial synthesisMaterial) throws SynthesisException
            , NoAccessException {
        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {
            //Delete attached files
            if (synthesisMaterial.getFileCollection() != null) {
                for (File file : synthesisMaterial.getFileCollection()) {
                    deleteSynthesisMaterialFile(synthesisMaterial, file);
                }
            }

            //Delete attached material elements
            if (synthesisMaterial.getSynthesisMaterialElements() != null) {
                for (SynthesisMaterialElement element : synthesisMaterial.getSynthesisMaterialElements()) {
                    deleteSynthesisMaterialElement(sampleId, synthesisMaterial, element);
                }
            }

            //refresh the SynthesisMaterial object with the dependents removed
            SynthesisMaterial tempMat = synthesisHelper.findSynthesisMaterialById(sampleId, synthesisMaterial.getId());
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
//            appService.delete(synthesisMaterial);
            appService.delete(tempMat);
        }
        catch (Exception e) {
            String err = "Error deleting synthesis material " + synthesisMaterial.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void deleteSynthesisPurification(Long sampleId, SynthesisPurification synthesisPurification) throws SynthesisException,
            NoAccessException {
        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {


            //Delete attached material elements
            if (synthesisPurification.getPurities() != null) {
                for (SynthesisPurity element : synthesisPurification.getPurities()) {
                    deleteSynthesisPurity(sampleId, synthesisPurification, element);
                }
            }


            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            appService.delete(synthesisPurification);
        }
        catch (Exception e) {
            String err = "Error deleting synthesis material " + synthesisPurification.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

public SynthesisPurity findPurityById(Long purityId) throws SynthesisException{
        try{
            SynthesisPurity purity = synthesisHelper.getPurityById(purityId);
            return  purity;
        }
        catch (Exception e) {
            String err = "Error finding purity by ID: " + purityId;
            throw new SynthesisException(err, e);
        }

}

    public SynthesisBean findSynthesisBySampleId(Long sampleId) throws SynthesisException {
        SynthesisBean synthesisBean = null;
        try {
            Synthesis synthesis = synthesisHelper.findSynthesisBySampleId(sampleId);
            if (synthesis != null) {
                synthesisBean = new SynthesisBean(synthesis);
            }
        }
        catch (Exception e) {
            String err = "Error finding synthesis by sample ID: " + sampleId;
            throw new SynthesisException(err, e);
        }
        return synthesisBean;
    }

    public Long findSynthesisIdBySampleId(Long sampleId) throws SynthesisException {
        try {
            Synthesis synthesis = synthesisHelper.findSynthesisBySampleId(sampleId);
            if (synthesis != null) {
                return synthesis.getId();
            }
        }
        catch (Exception e) {
            String err = "Error finding synthesis by sample ID: " + sampleId;
            throw new SynthesisException(err, e);
        }
        return null;
    }

    public SynthesisFunctionalizationBean findSynthesisFunctionalizationById(Long sampleId, Long dataId) throws NoAccessException, SynthesisException {
        SynthesisFunctionalizationBean sfBean = null;
        try {
            SynthesisFunctionalization synthesisFunctionalization =
                    synthesisHelper.findSynthesisFunctionalizationById(sampleId, dataId);
            //Add parent object to domain
            Synthesis synthesis;
            try {
                synthesis = getHelper().findSynthesisBySampleId(sampleId);
                synthesisFunctionalization.setSynthesis(synthesis);
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
            if (synthesisFunctionalization != null) {
                sfBean = new SynthesisFunctionalizationBean(synthesisFunctionalization);
            }

        }
        catch (NoAccessException e) {
            throw e;
        }
        catch (Exception e) {
            String err = "Problem finding the synthesis functionalization entity by id: " + dataId;
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
        return sfBean;
    }

    public SynthesisMaterialBean findSynthesisMaterialById(Long sampleId, Long dataId) throws NoAccessException,
            SynthesisException {
        SynthesisMaterialBean smBean = null;
        try {
            SynthesisMaterial synthesisMaterial = synthesisHelper.findSynthesisMaterialById(sampleId, dataId);
            //Add parent object to domain
            Synthesis synthesis;
            try {
                synthesis = getHelper().findSynthesisBySampleId(sampleId);
                synthesisMaterial.setSynthesis(synthesis);

            }
            catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
            if (synthesisMaterial != null) {
                smBean = new SynthesisMaterialBean(synthesisMaterial);
            }

        }
        catch (NoAccessException e) {
            throw e;
        }
        catch (Exception e) {
            String err = "Problem finding the synthesis material entity by id: " + dataId;
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
        return smBean;
    }

    public SynthesisPurificationBean findSynthesisPurificationById(Long sampleId, Long dataId) throws NoAccessException, SynthesisException {
        SynthesisPurificationBean spBean = null;
        try {
            SynthesisPurification synthesisPurification = synthesisHelper.findSynthesisPurificationById(sampleId,
                    dataId);
            if (synthesisPurification != null) {
                Long synid = findSynthesisIdBySampleId(sampleId);
                synthesisPurification.setSynthesisId(synid);
                spBean = new SynthesisPurificationBean(synthesisPurification);
            }

        }
        catch (NoAccessException e) {
            throw e;
        }
        catch (Exception e) {
            String err = "Problem finding the synthesis purification entity by id: " + dataId;
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
        return spBean;
    }

    public SynthesisHelper getHelper() {
        return synthesisHelper;
    }

    public void saveSynthesisFunctionalization(SampleBean sampleBean,
                                               SynthesisFunctionalizationBean synthesisFunctionalizationBean) throws SynthesisException, NoAccessException {

        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {
            Sample sample = sampleBean.getDomain();
            if (!springSecurityAclService.currentUserHasWritePermission(sample.getId(),
                    SecureClassesEnum.SAMPLE.getClazz())) {
                throw new NoAccessException();
            }
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            SynthesisFunctionalization synthesisFunctionalization = synthesisFunctionalizationBean.getDomainEntity();
            Boolean newEntity = true;
            Boolean newSynthesis = true;
            //Check if this is a new functionalization or an update of an existing
            if (synthesisFunctionalization.getId() != null) {
                newEntity = false;
            }

            if( ! newEntity ) {
                //Make doubly sure that the entity hasn't been left cached in memory but already removed from database
                try {
                    appService
                            .load( SynthesisFunctionalization.class,
                                    synthesisFunctionalizationBean.getDomainEntity().getId() );
                } catch( Exception e ) {
                    String err = "Object doesn't exist in the database anymore.  Please log in again.";
                    logger.error( err );
                    throw new SynthesisException( err, e );
                }
            }

            Synthesis synthesis;
            if (sample.getSynthesis() == null) {
                //This is a new synthesis.  We need to create it, as well as the functionalization
                synthesis = createSynthesis(sampleBean);
            } else {
                synthesis = sample.getSynthesis();
            }
            synthesisFunctionalization.setSynthesis(synthesis);

            if (!newEntity) {
                //Get the functionalization by id from database
                Long test1 = synthesisFunctionalization.getSynthesis().getId();
                Long test2 = synthesis.getId();
                if (!synthesisFunctionalization.getSynthesis().getId().equals(synthesis.getId())) {
                    //something has gone wrong and the functionalization does not attach to the correct synthesis
                    throw new SynthesisException("functionalization does not match synthesis", new Exception());
                }

            }
            //We'll be adding or updating the functionalization in Synthesis

            //add functionalization to synthesis

            for (FileBean fileBean : synthesisFunctionalizationBean.getFiles()) {
                fileUtils.prepareSaveFile(fileBean.getDomainFile());
            }
            //save

            for (SynthesisFunctionalizationElementBean synthesisFunctionalizationElementBean :
                    synthesisFunctionalizationBean.getSynthesisFunctionalizationElements()) {
                this.saveSynthesisFunctionalizationElement(synthesisFunctionalization,
                        synthesisFunctionalizationElementBean);
            }
            appService.saveOrUpdate(synthesisFunctionalization);

            for (FileBean fileBean : synthesisFunctionalizationBean.getFiles()) {
                fileUtils.writeFile(fileBean);
            }

        }
        catch (NoAccessException e) {
            throw e;
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Functionalization MHL012";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void saveSynthesisMaterial(SampleBean sampleBean, SynthesisMaterialBean synthesisMaterialBean) throws SynthesisException, NoAccessException {

        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {
            Sample sample = sampleBean.getDomain();
            if (!springSecurityAclService.currentUserHasWritePermission(sample.getId(),
                    SecureClassesEnum.SAMPLE.getClazz())) {
                throw new NoAccessException();
            }
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            SynthesisMaterial synthesisMaterial = synthesisMaterialBean.getDomainEntity();
            Boolean newEntity = true;
            Boolean newSynthesis = true;
            //Check if this is a new material or an update of an existing
            if (synthesisMaterial.getId() != null) {
                newEntity = false;
            }
            //Make doubly sure that the entity hasn't been left cached in memory but already removed from database
            if (!newEntity) {
                try {
                    appService
                            .load(SynthesisMaterial.class, synthesisMaterialBean.getDomainEntity().getId());
                }
                catch (Exception e) {
                    String err = "Object doesn't exist in the database anymore.  Please log in again.";
                    logger.error(err);
                    throw new SynthesisException(err, e);
                }
            }


            Synthesis synthesis;
            if (sample.getSynthesis() == null) {
                //This is a new synthesis.  We need to create it, as well as the material
                synthesis = createSynthesis(sampleBean);
            } else {
                synthesis = sample.getSynthesis();
            }
//            synthesisMaterial.setSynthesis(synthesis);
            synthesisMaterial.setSynthesisId(synthesis.getId());
            if (!newEntity) {
                //Get the material by id from database
//                Long test1 = synthesisMaterial.getSynthesis().getId();
                Long test1 = synthesisMaterial.getSynthesisId();
                Long test2 = synthesis.getId();
                if (!synthesisMaterial.getSynthesisId().equals(synthesis.getId())) {
                    //something has gone wrong and the material does not attach to the correct synthesis
                    throw new SynthesisException("material does not match synthesis", new Exception());
                }

            }
            //We'll be adding or updating the material in Synthesis

            //add material to synthesis

            for (FileBean fileBean : synthesisMaterialBean.getFiles()) {
                fileUtils.prepareSaveFile(fileBean.getDomainFile());
            }
            //save

            for (SynthesisMaterialElementBean synthesisMaterialElementBean :
                    synthesisMaterialBean.getSynthesisMaterialElements()) {
                //check the if there is a new Supplier
                if (synthesisMaterialElementBean.getSupplier() != null && synthesisMaterialElementBean.getSupplier().getId() == null) {
                    Supplier supplier = createSupplierRecord(synthesisMaterialElementBean.getSupplier());
                    synthesisMaterialElementBean.getDomainEntity().setSupplier(supplier);
                } else if (synthesisMaterialElementBean.getSupplier() != null) {
                    appService.saveOrUpdate(synthesisMaterialElementBean.getSupplier());
                }

            }

            appService.saveOrUpdate(synthesisMaterial);

            for (SynthesisMaterialElementBean synthesisMaterialElementBean :
                    synthesisMaterialBean.getSynthesisMaterialElements()) {
                this.saveSynthesisMaterialElement(synthesisMaterial, synthesisMaterialElementBean);
            }
            for (FileBean fileBean : synthesisMaterialBean.getFiles()) {
                fileUtils.writeFile(fileBean);
            }

        }
        catch (NoAccessException e) {
            String err = "Error writing file when saving Synthesis Material. ";
            logger.error(err, e);
            throw new SynthesisException(err, e);

        }
        catch (DataIntegrityViolationException e) {
            String err = "Database Integrity violation when saving Synthesis Material. ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Material :";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void saveSynthesisPurification(SampleBean sampleBean, SynthesisPurificationBean synthesisPurificationBean) throws SynthesisException, NoAccessException {

        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            SynthesisPurification synthesisPurification = synthesisPurificationBean.getDomainEntity();
            Boolean newEntity = true;
            Boolean newSynthesis = true;
            if (synthesisPurification.getId() != null) {
                newEntity = false;
            }
            Sample sample = sampleBean.getDomain();
            if (!springSecurityAclService.currentUserHasWritePermission(sample.getId(),
                    SecureClassesEnum.SAMPLE.getClazz())) {
                throw new NoAccessException();
            }

            Synthesis synthesis;
            if (sample.getSynthesis() == null) {
                //This is a new synthesis.  We need to create it, as well as the material
                synthesis = createSynthesis(sampleBean);
            } else {
                synthesis = sample.getSynthesis();
            }

            if (!newEntity) {
                try {
                    appService
                            .load(SynthesisPurification.class, synthesisPurificationBean.getDomainEntity().getId());
                }
                catch (Exception e) {
                    String err = "Object doesn't exist in the database anymore.  Please log in again.";
                    logger.error(err);
                    throw new SynthesisException(err, e);
                }
                //Get the purification by id from database
                Long test1 = synthesisPurification.getId();
                Long test2 = synthesis.getId();
                if (!synthesisPurification.getSynthesisId().equals(synthesis.getId())) {
                    //something has gone wrong and the purification does not attach to the correct synthesis
                    throw new SynthesisException("purification does not match synthesis", new Exception());
                }

            } else {
                synthesisPurification.setSynthesis(synthesis);
                synthesisPurification.setSynthesisId(synthesis.getId());
            }

            savePurificationConfig(synthesisPurification);

//            if(synthesisMaterialElementBean.getSupplier()!= null && synthesisMaterialElementBean.getSupplier()
//            .getId()==null){
//                Supplier supplier = createSupplierRecord(synthesisMaterialElementBean.getSupplier());
//                synthesisMaterialElementBean.getDomainEntity().setSupplier(supplier);
//            } else if(synthesisMaterialElementBean.getSupplier()!=null){
//                appService.saveOrUpdate(synthesisMaterialElementBean.getSupplier());
//            }

            for (FileBean fileBean : synthesisPurificationBean.getFiles()) {
                fileUtils.prepareSaveFile(fileBean.getDomainFile());
            }


//TODO Enable when I get the other stuff lined up
            appService.saveOrUpdate(synthesisPurification);

            for (SynthesisPurityBean synthesisPurityBean : synthesisPurificationBean.getPurityBeans()) {
                this.saveSynthesisPurity(synthesisPurityBean, synthesisPurificationBean);
            }

            for (FileBean fileBean : synthesisPurificationBean.getFiles()) {
                fileUtils.writeFile(fileBean);
            }

        }
        catch (ApplicationException e) {
            logger.error("Problem saving synthesis purification ", e);
            throw new SynthesisException("Error in saving synthesis purification ", e);
        }
        catch (Exception e) {
            logger.error("Problem saving synthesis purification ", e);
            throw new SynthesisException("Error in saving synthesis purification ", e);
        }
    }

    public void saveSynthesisPurity(SynthesisPurityBean synthesisPurityBean,
                                    SynthesisPurificationBean synthesisPurificationBean) throws SynthesisException {

        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            SynthesisPurity synthesisPurity = synthesisPurityBean.getDomain();
            Boolean newEntity = true;
            if (synthesisPurity.getId() != null) {
                newEntity = false;
            }

            if (!newEntity) {
                //Get the purification by id from database
                Long test1 = synthesisPurity.getSynthesisPurificationId();
                Long test2 = synthesisPurificationBean.getDomainEntity().getId();
                if (!test1.equals(test2)) {
                    //something has gone wrong and the material does not attach to the correct synthesis
                    throw new SynthesisException("element does not match purification", new Exception());
                }


                try {
                    SynthesisPurity tempPure = (SynthesisPurity)appService.getObject(SynthesisPurity.class, "id", synthesisPurityBean.getDomain().getId());

                    synthesisPurity.setCreatedDate(tempPure.getCreatedDate());
                    synthesisPurity.setCreatedBy(tempPure.getCreatedBy());
                }
                catch (Exception e) {
                    String err = "Object doesn't exist in the database anymore.  Please log in again.";
                    logger.error(err);
                    throw new SynthesisException(err, e);
                }
            } else {
                synthesisPurity.setCreatedDate(new Date());
                synthesisPurity.setCreatedBy(SpringSecurityUtil.getLoggedInUserName() + ":" + Constants.AUTO_COPY_ANNOTATION_PREFIX);
            }

            for (FileBean fileBean : synthesisPurityBean.getFiles()) {
                fileUtils.prepareSaveFile(fileBean.getDomainFile());
            }


            synthesisPurity.setSynthesisPurification(synthesisPurificationBean.getDomainEntity());

            Set<PurityDatumCondition> originalDatum = synthesisHelper.getPurityDatumByPurity(synthesisPurity.getId());



            for (PurityDatumCondition datum : synthesisPurity.getPurityDatumCollection()) {
                if (datum.getId() == null) {
                    //create new datum
//                    datum.setSynthesisPurity(synthesisPurity);
//                    datum.setSynthesisPurity(synthesisPurity);
                    PurityDatumCondition newDatum = createDatum(datum);
//                    datum.setId(newDatum.getId());
                } else {
                    saveDatum(datum);
                }

            }


            //TODO enable when I have everything aligned
            appService.saveOrUpdate(synthesisPurity);


            for (FileBean fileBean : synthesisPurityBean.getFiles()) {
                fileUtils.writeFile(fileBean);
            }
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Purity ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public List<String> getSupplierNames() throws SynthesisException {
        try {
            return synthesisHelper.getAllSupplierNames();
        }
        catch (Exception e) {
            String err = "Error finding suppliers ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public List<String> getFunctionalizationActivationMethods() throws SynthesisException {
        try {
            return synthesisHelper.getAllActivationMethods();
        }
        catch (Exception e) {
            String err = "Error finding Activation Methods ";
            e.printStackTrace();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public Supplier createSupplierRecord(Supplier supplier) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
            appService.saveOrUpdate(supplier);
            return supplier;
        }
        catch (Exception e) {
            String err = "Problem saving new Supplier ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void deleteSynthesisMaterialElement(Long sampleId, SynthesisMaterial synthesisMaterial,
                                               SynthesisMaterialElement element) throws NoAccessException,
            SynthesisException {
        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            if (element.getFiles() != null) {
                for (File file : element.getFiles()) {
                    deleteSynthesisMaterialElementFile(sampleId, element, file);
                }
            }
            if (element.getSmeInherentFunctions() != null) {
                for (SmeInherentFunction function : element.getSmeInherentFunctions()) {
                    deleteSmeInherentFunction(sampleId, element, function);
                }
            }
            appService.delete(element);
        }
        catch (Exception e) {
            String err = "Error deleting synthesis material element " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void deleteSmeInherentFunction(Long sampleId, SynthesisMaterialElement element,
                                          SmeInherentFunction function) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            List<SmeInherentFunction> functionList = synthesisHelper.findSmeFunctionByElementId(sampleId,
                    element.getId(), "SynthesisMaterialElement");
            element.setSmeInherentFunctions(new HashSet<SmeInherentFunction>(functionList));
            element.getSmeInherentFunctions().remove(function);
            appService.delete(function);

        }
        catch (Exception e) {
            String err = "Error deleting SME Inherent Function " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void deleteSynthesisFunctionalizationElement(Long sampleId,
                                                        SynthesisFunctionalization synthesisFunctionalization,
                                                        SynthesisFunctionalizationElement element) throws NoAccessException, SynthesisException {
        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            if (element.getFiles() != null) {
                for (File file : element.getFiles()) {
                    deleteSynthesisFunctionalizationElementFile(sampleId, element, file);
                }
            }
            if (element.getSfeInherentFunctions() != null) {
                for (SfeInherentFunction function : element.getSfeInherentFunctions()) {
                    deleteSfeInherentFunction(sampleId, element, function);
                }
            }
            appService.delete(element);
        }
        catch (Exception e) {
            String err = "Error deleting synthesis functionalization element " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }

    }

    public void deleteSfeInherentFunction(Long sampleId, SynthesisFunctionalizationElement element,
                                          SfeInherentFunction function) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            List<SfeInherentFunction> functionList = synthesisHelper.findSfeFunctionByElementId(sampleId,
                    element.getId(), "SynthesisFunctionalizationElement");
            element.setSfeInherentFunctions(new HashSet<SfeInherentFunction>(functionList));
            element.getSfeInherentFunctions().remove(function);
            appService.delete(function);

        }
        catch (Exception e) {
            String err = "Error deleting SFE Inherent Function " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }

    }

    private void deleteSynthesisFunctionalizationElementFile(Long sampleId, SynthesisFunctionalizationElement element
            , File file) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            List<File> fileList = synthesisHelper.findFilesBySynMatElement(sampleId, element.getId(),
                    "SynthesisFunctionalizationElement");
            element.setFiles(new HashSet<File>(fileList));
            element.getFiles().remove(file);
            appService.saveOrUpdate(element);
        }
        catch (Exception e) {
            String err = "Error deleting synthesis functionalization element file " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private void deleteSynthesisPurity(Long sampleId, SynthesisPurification synthesisPurification,
                                       SynthesisPurity element) throws SynthesisException {
        //TODO write
        if (element.getFiles() != null) {
            for (File file : element.getFiles()) {
                deleteSynthesisPurityFile(element, file);
            }

        }
        if (element.getPurityDatumCollection() != null) {
            //delete datum
            for (PurityDatumCondition purityDatumCondition : element.getPurityDatumCollection()) {
                deletePurityDatum(sampleId, element, purityDatumCondition);
            }

        }

    }

    private void deleteSynthesisMaterialFile(SynthesisMaterial synthesisMaterial, File file) throws NoAccessException
            , SynthesisException {
        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
//            // load files first
            List<File> fileList = synthesisHelper.findFilesByMaterialId(synthesisMaterial.getSynthesisId(),
                    synthesisMaterial.getId(), "SynthesisMaterial");
            synthesisMaterial.setFileCollection(new HashSet<File>(fileList));
            synthesisMaterial.getFileCollection().remove(file);
            appService.saveOrUpdate(synthesisMaterial);


        }
        catch (Exception e) {
            String err = "Error deleting synthesis material  file " + file.getUri();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private void deleteSynthesisPurityFile(SynthesisPurity element, File file) {
        //TODO write
        //This file might be used by other options so we want to check
        //If file is used elsewhere, then set fileid to null and leave it
        //If not used elsewhere, then do we want to delete file record?
        //Will that leave the file itself orphaned on server?

    }

    private void deletePurityDatum(Long sampleId, SynthesisPurity element, PurityDatumCondition purityDatum) throws SynthesisException {
        //TODO write
        try {

        }
        catch (Exception e) {
            String err = "Error deleting Purity Datum Condtion " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }

    }

    private void deleteSynthesisMaterialElementFile(Long sampleId, SynthesisMaterialElement element, File file) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            List<File> fileList = synthesisHelper.findFilesBySynMatElement(sampleId, element.getId(),
                    "SynthesisMaterialElement");
            element.setFiles(new HashSet<File>(fileList));
            element.getFiles().remove(file);
            appService.saveOrUpdate(element);
        }
        catch (Exception e) {
            String err = "Error deleting synthesis material element file " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private void deletePurityDatumCondition(PurityDatumCondition element) throws SynthesisException {
        //TODO write
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
//            List<SmeInherentFunction> functionList = synthesisHelper.findSmeFunctionByElementId(sampleId, element
//            .getId(),"SynthesisMaterialElement");
//            element.setSmeInherentFunctions(new HashSet<SmeInherentFunction>(functionList));
//            element.getSmeInherentFunctions().remove(function);
            appService.delete(element);

        }
        catch (Exception e) {
            String err = "Error deleting Purity Datum " + element.getId();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private void savePurificationConfig(SynthesisPurification purification) throws Exception {
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();

            Set<PurificationConfig> oldConfigs = synthesisHelper.findConfigByPurificationId(purification.getId());

            Set<PurificationConfig> configs = purification.getPurificationConfigs();
            for (PurificationConfig config : configs) {
                if (!oldConfigs.contains(config)) {
                    //TODO new config
                    appService.saveOrUpdate(config);
                }
            }


//        if(configs!=null){
//            for(PurificationConfig config:configs){
//                if(config.getInstrumentCollection()!=null){
//                    for(Instrument instrument:config.getInstrumentCollection()){
//                        if(instrument.getId()==null){
//                            Instrument newInstrument = createInstrumentRecord(instrument);
//                            instrument.setId(newInstrument.getId());
//                        } else{
//                            appService.saveOrUpdate(instrument);
//                        }
//                    }
//                }
//
//                if(config.getPurificationConfigPkId()==null){
//                    PurificationConfig newConfig = createTechniqueRecord(config);
//                    config.setPurificationConfigPkId(newConfig.getPurificationConfigPkId());
//                } else{
//                    appService.saveOrUpdate(config);
//                }
//            }
//        }
        }
        catch (Exception e) {
            String err = "Problem saving Purification Configuration ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private PurityDatumCondition createDatum(PurityDatumCondition datum) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
//Need to blank condition until we have a datum id

            appService.saveOrUpdate(datum);
//            Set<PurityDatumCondition> conditionHolder = datum.getConditionCollection();
//            datum.setConditionCollection(null);
//            for (PurityDatumCondition condition : datum.getConditionCollection()) {
////                condition.setPurityDatumPkId(datum.getId());
////                condition.setPurityDatum(datum);
//                PurityDatumCondition newCondition = createCondition(condition);
////                condition.setId(newCondition.getId());
//            }
//            datum.setConditionCollection(conditionHolder);
            //TODO get the id?
            return datum;
        }
        catch (Exception e) {
            String err = "Problem saving new Purity Datum ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private PurityDatumCondition saveDatum(PurityDatumCondition datum) throws SynthesisException {
        try {
//            if (datum.getConditionCollection() != null) {
//                for (PurityDatumCondition condition : datum.getConditionCollection()) {
//                    condition = saveCondition(condition);
//                }
//            }
            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
            try {
                appService
                        .load(PurityDatumCondition.class, datum.getId());
                SynthesisPurity tempDatum = (SynthesisPurity)appService.getObject(PurityDatumCondition.class, "id", datum.getId());

                datum.setCreatedDate(tempDatum.getCreatedDate());
                datum.setCreatedBy(tempDatum.getCreatedBy());
            }
            catch (Exception e) {
                String err = "Object doesn't exist in the database anymore.  Please log in again.";
                logger.error(err);
                throw new SynthesisException(err, e);
            }
//            Set<PurityDatumCondition> conditionSet = synthesisHelper.findPurityDatumConditionByDatum(datum.getId());
//
//            datum.setConditionCollection(conditionSet);
//            for (PurityDatumCondition condition : conditionSet) {
//                condition.setPurityDatum(datum);
//            }
            appService.saveOrUpdate(datum);
            return datum;
        }
        catch (Exception e) {
            String err = "Problem saving PurityDatumCondition ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private PurityDatumCondition createCondition(PurityDatumCondition condition) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
            appService.saveOrUpdate(condition);
            return condition;
        }
        catch (Exception e) {
            String err = "Problem saving new Condition ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

//    private Supplier createSupplierRecord(HashMap<String,String> supplierMap) throws SynthesisException {
//        Supplier supplier = new Supplier();
//        supplier.setLot(supplierMap.get("Lot"));
//        supplier.setSupplierName(supplierMap.get("supplierMap"));
//        return createSupplierRecord(supplier);
//    }

    private PurityDatumCondition saveCondition(PurityDatumCondition condition) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
            appService.saveOrUpdate(condition);
            return condition;
        }
        catch (Exception e) {
            String err = "Problem saving PurityDatumCondition ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private Synthesis createSynthesis(SampleBean sampleBean) throws SynthesisException, NoAccessException {

        try {
            Synthesis synthesis = new Synthesis();
            synthesis.setSample(sampleBean.getDomain());

            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();

            appService.saveOrUpdate(synthesis);
            // save default access
            springSecurityAclService.saveDefaultAccessForNewObject(synthesis.getId(),
                    SecureClassesEnum.SYNTHESIS.getClazz());

            return synthesis;
        }
        catch (ApplicationException e) {
            logger.error("Error in saving the synthesis. ", e);
            throw new SynthesisException("Error in saving the synthesis. ", e);
        }
        catch (NoAccessException e) {
            logger.error("User does not have access to edit synthesis ", e);
            throw e;
        }
        catch (Exception e) {
            logger.error("Error in saving the synthesis. ", e);
            throw new SynthesisException("Error in saving the synthesis. ", e);
        }
    }

    public void saveSynthesisMaterialElement(SynthesisMaterial material,
                                             SynthesisMaterialElementBean synthesisMaterialElementBean) throws SynthesisException {

        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            SynthesisMaterialElement synthesisMaterialElement = synthesisMaterialElementBean.getDomainEntity();
            Boolean newEntity = true;
            if (synthesisMaterialElement.getId() != null) {
                newEntity = false;
            }

            if (!newEntity) {
                //Get the material by id from database
                Long test1 = synthesisMaterialElement.getSynthesisMaterialId();
                Long test2 = material.getId();
                if (!test1.equals(test2)) {
                    //something has gone wrong and the material does not attach to the correct synthesis
                    throw new SynthesisException("element does not match material", new Exception());
                }

                try {
                    appService
                            .load(SynthesisMaterialElement.class,
                                    synthesisMaterialElementBean.getDomainEntity().getId());
                }
                catch (Exception e) {
                    String err = "Object doesn't exist in the database anymore.  Please log in again.";
                    logger.error(err);
                    throw new SynthesisException(err, e);
                }
            }

            for (FileBean fileBean : synthesisMaterialElementBean.getFiles()) {
                fileUtils.prepareSaveFile(fileBean.getDomainFile());
            }

            //check if this is a new supplier (has no id). Create if needed
            if (synthesisMaterialElement.getSupplier() != null && synthesisMaterialElement.getSupplier().getId() == null) {
                Supplier supplier = createSupplierRecord(synthesisMaterialElement.getSupplier());
                synthesisMaterialElement.setSupplier(supplier);
            }

            appService.saveOrUpdate(synthesisMaterialElement);

            for (FileBean fileBean : synthesisMaterialElementBean.getFiles()) {
                fileUtils.writeFile(fileBean);
            }
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Material Element ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public void saveSynthesisFunctionalizationElement(SynthesisFunctionalization functionalization,
                                                      SynthesisFunctionalizationElementBean synthesisFunctionalizationElementBean) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
            SynthesisFunctionalizationElement synthesisFunctionalizationElement =
                    synthesisFunctionalizationElementBean.getDomainEntity();
            Boolean newEntity = true;
            if (synthesisFunctionalizationElement.getId() != null) {
                newEntity = false;
            }

            if (!newEntity) {
                //Get the functionalization by id from database
                Long test1 = synthesisFunctionalizationElement.getSynthesisFunctionalization().getId();
                Long test2 = functionalization.getId();
                if (!test1.equals(test2)) {
                    //something has gone wrong and the functionalization does not attach to the correct synthesis
                    throw new SynthesisException("element does not match functionalization", new Exception());
                }

                try {
                    appService
                            .load(SynthesisFunctionalizationElement.class,
                                    synthesisFunctionalizationElementBean.getDomainEntity().getId());
                }
                catch (Exception e) {
                    String err = "Object doesn't exist in the database anymore.  Please log in again.";
                    logger.error(err);
                    throw new SynthesisException(err, e);
                }
            }

            for (FileBean fileBean : synthesisFunctionalizationElementBean.getFiles()) {
                fileUtils.prepareSaveFile(fileBean.getDomainFile());
            }



            appService.saveOrUpdate(synthesisFunctionalizationElement);
            for(SfeInherentFunction function:synthesisFunctionalizationElement.getSfeInherentFunctions()){
                function.setSynthesisFunctionalizationElement(synthesisFunctionalizationElement);
                appService.saveOrUpdate(function);
            }

            for (FileBean fileBean : synthesisFunctionalizationElementBean.getFiles()) {
                fileUtils.writeFile(fileBean);
            }
        }
        catch (Exception e) {
            String err = "Problem saving Synthesis Functionalization Element ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }


//    public SortedSet<String> getSupplierNames() throws Exception
//    {
//        try {
//            SortedSet<String> names = new TreeSet<String>(new Comparators.SortableNameComparator());
//            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
//            .getApplicationService();
//            HQLCriteria crit = new HQLCriteria("select org.name from gov.nih.nci.cananolab.domain.common
//            .Organization org");
//            List results = appService.query(crit);
//
//            logger.debug("Completed select org.name from gov.nih.nci.cananolab.domain.common.Organization org");
//            for (int i = 0; i < results.size(); i++){
//                String name = ((String) results.get(i)).trim();
//                names.add(name);
//            }
//            return names;
//        } catch (Exception e) {
//            String err = "Error finding suppliers for " + SpringSecurityUtil.getLoggedInUserName();
//            logger.error(err, e);
//            throw new SynthesisException(err, e);
//        }
//    }

    public Instrument createInstrumentRecord(Instrument instrument) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
            appService.saveOrUpdate(instrument);
            return instrument;
        }
        catch (Exception e) {
            String err = "Problem saving new Technique ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public PurityColumnHeader createColumnHeader(PurityColumnHeader header) throws SynthesisException {
        try{
            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
            appService.saveOrUpdate(header);
            return header;
        }
        catch (Exception e) {
            String err = "Problem saving new Purity Column Header ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public PurityColumnHeader getColumnHeaderById(Long id) throws SynthesisException {
        try {
            PurityColumnHeader columnHeader = synthesisHelper.findPurityColumnHeaderById(id, "PurityColumnHeader");

                return columnHeader;

        }
        catch (Exception e) {
            String err = "Problem fetching Purity Column Header ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public PurificationConfig createTechniqueRecord(PurificationConfig technique) throws SynthesisException {
        try {
            CaNanoLabApplicationService appService =
                    (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
            appService.saveOrUpdate(technique);
            return technique;
        }
        catch (Exception e) {
            String err = "Problem saving new Technique ";
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    private void deleteSynthesisFunctionalizationFile(SynthesisFunctionalization synthesisFunctionalization,
                                                      File file) throws NoAccessException, SynthesisException {
        if (SpringSecurityUtil.getPrincipal() == null) {
            throw new NoAccessException();
        }
        try {
            CaNanoLabApplicationService appService = (CaNanoLabApplicationService) ApplicationServiceProvider
                    .getApplicationService();
//            // load files first
            List<File> fileList =
                    synthesisHelper.findFilesByFunctionalizationId(synthesisFunctionalization.getSynthesisId(),
                            synthesisFunctionalization.getId(), "SynthesisFunctionalization");
            synthesisFunctionalization.setFiles(new HashSet<File>(fileList));
            synthesisFunctionalization.getFiles().remove(file);
            appService.saveOrUpdate(synthesisFunctionalization);


        }
        catch (Exception e) {
            String err = "Error deleting synthesis functionalization  file " + file.getUri();
            logger.error(err, e);
            throw new SynthesisException(err, e);
        }
    }

    public int getNumberOfSuppliers() throws Exception {
        CaNanoLabApplicationService appService =
                (CaNanoLabApplicationService) ApplicationServiceProvider.getApplicationService();
        HQLCriteria crit = new HQLCriteria("select id from gov.nih.nci.cananolab.domain.common.Supplier");
        List results = appService.query(crit);
        int cnt = 0;
        for (int i = 0; i < results.size(); i++) {
            cnt++;
        }
        return cnt;
    }

    @Override
    public SpringSecurityAclService getSpringSecurityAclService() {
        return springSecurityAclService;
    }
}
