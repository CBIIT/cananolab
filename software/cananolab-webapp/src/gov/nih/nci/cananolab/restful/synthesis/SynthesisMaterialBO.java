package gov.nih.nci.cananolab.restful.synthesis;

import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.domain.common.Keyword;
import gov.nih.nci.cananolab.domain.common.Protocol;
import gov.nih.nci.cananolab.domain.common.Supplier;
import gov.nih.nci.cananolab.domain.particle.SmeInherentFunction;
import gov.nih.nci.cananolab.domain.particle.Synthesis;
import gov.nih.nci.cananolab.domain.particle.SynthesisMaterial;
import gov.nih.nci.cananolab.domain.particle.SynthesisMaterialElement;
import gov.nih.nci.cananolab.dto.common.FileBean;
import gov.nih.nci.cananolab.dto.common.ProtocolBean;
import gov.nih.nci.cananolab.dto.particle.SampleBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisMaterialBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisMaterialElementBean;
import gov.nih.nci.cananolab.exception.SynthesisException;
import gov.nih.nci.cananolab.restful.core.BaseAnnotationBO;
import gov.nih.nci.cananolab.restful.sample.InitSampleSetup;
import gov.nih.nci.cananolab.restful.util.PropertyUtil;
import gov.nih.nci.cananolab.restful.util.SynthesisUtil;
import gov.nih.nci.cananolab.restful.view.edit.SimpleFileBean;
import gov.nih.nci.cananolab.restful.view.edit.SimpleProtocol;
import gov.nih.nci.cananolab.restful.view.edit.SimpleSynthesisMaterialBean;
import gov.nih.nci.cananolab.restful.view.edit.SimpleSynthesisMaterialElementBean;
import gov.nih.nci.cananolab.security.CananoUserDetails;
import gov.nih.nci.cananolab.security.enums.SecureClassesEnum;
import gov.nih.nci.cananolab.security.service.SpringSecurityAclService;
import gov.nih.nci.cananolab.security.utils.SpringSecurityUtil;
import gov.nih.nci.cananolab.service.curation.CurationService;
import gov.nih.nci.cananolab.service.protocol.ProtocolService;
import gov.nih.nci.cananolab.service.sample.SampleService;
import gov.nih.nci.cananolab.service.sample.SynthesisService;
import gov.nih.nci.cananolab.ui.form.SynthesisForm;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.DateUtils;
import gov.nih.nci.cananolab.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
@Component("synthesisMaterialBO")
public class SynthesisMaterialBO extends BaseAnnotationBO {
    Logger logger = Logger.getLogger(SynthesisMaterialBO.class);

    @Autowired
    private CurationService curationServiceDAO;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private SpringSecurityAclService springSecurityAclService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SynthesisService synthesisService;

    @Autowired
    private ProtocolService protocolService;

    public SynthesisMaterialBO() {
    }

    public List<String> create(SimpleSynthesisMaterialBean synMatBean,
                               HttpServletRequest request)
            throws Exception {
        List<String> msgs;
        String sampleId = synMatBean.getSampleId();
        SynthesisMaterialBean entityBean = transferSynthesisMaterialBean(synMatBean, request);
//        SampleBean sampleBean = setupSampleById(sampleId, request);
//        List<String> otherSampleNames = synMatBean.getOtherSampleNames();
        msgs = validateInputs(request, entityBean);
        if (msgs.size() > 0) {
            return msgs;
        }
        this.saveEntity(request, sampleId, entityBean);
        InitSynthesisSetup.getInstance().persistSynthesisMaterialDropdowns(
                request, entityBean);

        //TODO check if this is a new supplier?
//        SampleBean[] otherSampleBeans = null;
//        if (otherSampleNames != null) {
//            otherSampleBeans = prepareCopy(request, otherSampleNames, sampleBean);
//        }
//        if (otherSampleBeans != null) {
//            synthesisService.copyAndSaveSynthesisMaterial(entityBean, sampleBean, otherSampleBeans);
//        }

        msgs.add("success");
        request.getSession().setAttribute("tab", "1");
        return msgs;
    }



    private SynthesisMaterialBean transferSynthesisMaterialBean(SimpleSynthesisMaterialBean synMatBean,
                                                                HttpServletRequest request){
        //Transfer from the simple front-end bean to a full bean
        //TODO write
        SynthesisMaterialBean bean = new SynthesisMaterialBean();
        SynthesisMaterial material = new SynthesisMaterial();


        //set up domain and bean
//        material.setId(synMatBean.getId());

        if((synMatBean.getId()!=null)&&(synMatBean.getId()>0)){
            material.setId(synMatBean.getId());
        }
        material.setCreatedBy(synMatBean.getCreatedBy());
        material.setCreatedDate(synMatBean.getDate());

        material.setDescription(synMatBean.getDescription());
        bean.setDescription(synMatBean.getDescription());
        if(synMatBean.getType()!=null) {
            bean.setType(synMatBean.getType());
        } else
        {
            bean.setType("Synthesis");
        }

        //Add parent object to domain
        Synthesis synthesis;
        try{
            synthesis = synthesisService.getHelper().findSynthesisBySampleId(synMatBean.getSampleId());
            material.setSynthesis(synthesis);

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }

        //Set files for domain and bean
        FileBean fileBean;
        File file;
        List<FileBean> fileBeanList = new ArrayList<FileBean>();
        Set<File> fileList = new HashSet<File>();
        if(synMatBean.getFileElements()!=null) {
            for (SimpleFileBean sFileBean : synMatBean.getFileElements()) {
                file = new File();
                fileBean = new FileBean();
                file.setId(sFileBean.getId());
                file.setCreatedBy(sFileBean.getCreatedBy());
                fileBean.setCreatedBy(sFileBean.getCreatedBy());
                file.setCreatedDate(sFileBean.getCreatedDate());
                file.setTitle(sFileBean.getTitle());
                file.setDescription(sFileBean.getDescription());
                //TODO figure out what name is supposed to do.  Eliminate if "nothing"  file.setName(sFileBean.get);
                file.setType(sFileBean.getType());
                file.setUri(sFileBean.getUri());
                file.setUriExternal(sFileBean.getUriExternal());
                fileBean.setExternalUrl(sFileBean.getExternalUrl());
                if (!StringUtils.isEmpty(sFileBean.getKeywordsStr())) {
                    String[] strs = sFileBean.getKeywordsStr().split("\r\n");
                    for (String str : strs) {
                        // change to upper case
                        Keyword keyword = new Keyword();
                        keyword.setName(str.toUpperCase());
                        file.getKeywordCollection().add(keyword);
                    }
                }
                fileBean.setTheAccess(sFileBean.getTheAccess());
                fileBean.setDomainFile(file);
                fileBeanList.add(fileBean);
                fileList.add(file);

                //TODO check if this can replace all of the above
                FileBean testFileBean = new FileBean(sFileBean);
                File testFile = testFileBean.getDomainFile();
            }
            bean.setFiles(fileBeanList);
            material.setFileCollection(fileList);
        }

        //Set protocol for domain and bean
        //TODO why is this not null?
        try {
            SimpleProtocol sProtocol = synMatBean.getSimpleProtocol();
            if (sProtocol != null && sProtocol.getDomainId() !=null) {
                ProtocolBean protocolBean = protocolService.findProtocolById(sProtocol.getDomainId().toString());
                Protocol protocol = protocolBean.getDomain();
                bean.setProtocolBean(protocolBean);
                material.setProtocol(protocol);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }



        //Add synthesisMaterialElements to bean and domain
        Set<SynthesisMaterialElement> smes = new HashSet<SynthesisMaterialElement>();
        List<SynthesisMaterialElementBean> smeBeans = new ArrayList<SynthesisMaterialElementBean>();
        List<SimpleSynthesisMaterialElementBean> sSMEbeans = synMatBean.getMaterialElements();

        if(sSMEbeans !=null){
            //Loop through simple beans, creating new material elements from each
            for(SimpleSynthesisMaterialElementBean sSMEBean: sSMEbeans){
                SynthesisMaterialElement synthesisMaterialElement = new SynthesisMaterialElement();

                synthesisMaterialElement.setDescription(sSMEBean.getDescription());
                synthesisMaterialElement.setChemicalName(sSMEBean.getChemicalName());
                synthesisMaterialElement.setMolecularFormula(sSMEBean.getMolecularFormula());
                synthesisMaterialElement.setMolecularFormulaType(sSMEBean.getMolecularFormulaType());
                synthesisMaterialElement.setPubChemId(sSMEBean.getPubChemId());
                synthesisMaterialElement.setPubChemDatasourceName(sSMEBean.getPubChemDataSource());
                synthesisMaterialElement.setValue(sSMEBean.getValue());
                synthesisMaterialElement.setValueUnit(sSMEBean.getValueUnit());
                synthesisMaterialElement.setCreatedBy(sSMEBean.getCreatedBy());
                synthesisMaterialElement.setCreatedDate(sSMEBean.getCreatedDate());
                synthesisMaterialElement.setSynthesisMaterial(material);
                synthesisMaterialElement.setId(sSMEBean.getId());
                synthesisMaterialElement.setType(sSMEBean.getType());


                //check supplier
                //TODO this is clumsy.  Should probably be a simple bean
//                Map<String, String> supplierMap = sSMEBean.getSupplierMap();
                Supplier supplier = sSMEBean.getSupplier();
//                if(supplierMap!=null) {
//                    Supplier supplier = new Supplier();
//                    supplier.setSupplierName(supplierMap.get("SupplierName"));
//                    supplier.setLot(supplierMap.get("Lot"));
//                    if(supplierMap.get("id")!=null) {
//                        supplier.setId(Long.valueOf(supplierMap.get("id")));
//                    } else {
//                        //new supplier
//
//                    }
                    synthesisMaterialElement.setSupplier(supplier);
//                }

                //check for Files
                List<SimpleFileBean> sfileBeans = sSMEBean.getFiles();
                Set<File> files = new HashSet<File>();
                if(sfileBeans!=null){
                for(SimpleFileBean simpleFileBean: sfileBeans){

                    File file1 = new File();
                    file1.setUriExternal(simpleFileBean.getUriExternal());
                    file1.setUri(simpleFileBean.getUri());
                    file1.setType(simpleFileBean.getType());
                    file1.setDescription(simpleFileBean.getDescription());
                    file1.setTitle(simpleFileBean.getTitle());
                    file1.setCreatedDate(simpleFileBean.getCreatedDate());
                    file1.setCreatedBy(simpleFileBean.getCreatedBy());
                    files.add(file1);
                }}
                synthesisMaterialElement.setFiles(files);

                //Loop through functions
                List<Map<String,String>> functions = sSMEBean.getInherentFunctionList();
                Set<SmeInherentFunction> smeInherentFunctionSet = new HashSet<SmeInherentFunction>();
                if (functions != null) {
                for(Map<String, String> function: functions){
                    //id, type, description
                    SmeInherentFunction smeInherentFunction = new SmeInherentFunction();

                    smeInherentFunction.setType(function.get("type").toString());
                    smeInherentFunction.setDescription(function.get("description").toString());
//                   Is this a new function or does it have an ID
                    if(function.get("id")!=null) {
                        smeInherentFunction.setId(Long.valueOf(function.get("id")));
                    }
//                    smeInherentFunction.setId(function.get("id"));
                    //TODO this is circular.  Rework this
                    smeInherentFunction.setSynthesisMaterialElement(synthesisMaterialElement);
                    smeInherentFunctionSet.add(smeInherentFunction);
                }}
                synthesisMaterialElement.setSmeInherentFunctions(smeInherentFunctionSet);
                SynthesisMaterialElementBean materialElementBean = new SynthesisMaterialElementBean(synthesisMaterialElement);
                smeBeans.add(materialElementBean);
                smes.add(synthesisMaterialElement);
            }
            bean.setSynthesisMaterialElements(smeBeans);
            material.setSynthesisMaterialElements(smes);

        }
        bean.setDomainEntity(material);
        return bean;
    }

    private List<String> validateInputs(HttpServletRequest request, SynthesisMaterialBean entityBean) {
        //todo write

        List<String> msgs = new ArrayList<String>();
        msgs = validateEntity(request, msgs, entityBean);
        msgs = validateMaterialElements(request, msgs, entityBean);
        msgs = validateFile(request, msgs, entityBean);

        return msgs;


    }

    private List<String> validateFile(HttpServletRequest request, List<String> msgs,
                                            SynthesisMaterialBean entityBean) {
        //ActionMessages msgs = new ActionMessages();
        for (FileBean filebean : entityBean.getFiles()) {
            msgs = validateFileBean(request, msgs, filebean);
            if (msgs.size()>0) {
                return msgs;
            }
        }
        return msgs;
    }

    private List<String> validateEntity(HttpServletRequest httpRequest, List<String> msgs, SynthesisMaterialBean synthesisMaterialBean){
        //TODO write

        return msgs;
    }

    private List<String> validateMaterialElements(HttpServletRequest httpRequest, List<String> msgs, SynthesisMaterialBean synthesisMaterialBean){
        //TODO write

        return msgs;
    }

    private List<String> saveEntity(HttpServletRequest request, String sampleId, SynthesisMaterialBean entityBean) throws Exception {

        List<String> msgs = new ArrayList<String>();
        SampleBean sampleBean = setupSampleById(sampleId, request);
        CananoUserDetails userDetails = SpringSecurityUtil.getPrincipal();

        boolean newEntity = true;

        try {
            entityBean.setUpDomainEntity(userDetails.getUsername());
            if (entityBean.getDomainEntity().getId() != null) {
                newEntity = false;
            }
        } catch (ClassCastException ex) {

            entityBean.setType(null);
        }
        synthesisService.saveSynthesisMaterial(sampleBean, entityBean);
        // retract from public if updating an existing public record and not curator
        if (!newEntity && !userDetails.isCurator() &&
                springSecurityAclService.checkObjectPublic(sampleBean.getDomain().getId(), SecureClassesEnum.SAMPLE.getClazz())) {
            retractFromPublic(request, sampleBean.getDomain().getId(), sampleBean.getDomain().getName(), "sample", SecureClassesEnum.SAMPLE.getClazz());
            msgs.add(PropertyUtil.getProperty("sample", "message.updateSample.retractFromPublic"));
            return msgs;
        }
        return msgs;

    }

    public List<String> delete(SimpleSynthesisMaterialBean synthesisMaterialBean, HttpServletRequest request) throws Exception {

        List<String> msgs = new ArrayList<String>();
        SynthesisMaterialBean entityBean = transferSynthesisMaterialBean(synthesisMaterialBean, request);
        entityBean.setUpDomainEntity(SpringSecurityUtil.getLoggedInUserName());
        String sampleId = synthesisMaterialBean.getSampleId();
        synthesisService.deleteSynthesisMaterial(new Long(sampleId),entityBean.getDomainEntity());

        msgs.add("success");
        return msgs;
    }

    @Override
    public CurationService getCurationServiceDAO() {
        return this.curationServiceDAO;
    }

    @Override
    public SampleService getSampleService() {
        return this.sampleService;
    }

    @Override
    public SpringSecurityAclService getSpringSecurityAclService() {
        return this.springSecurityAclService;
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public SimpleSynthesisMaterialBean removeFile(SimpleSynthesisMaterialBean simpleSynthesisMaterialBean, HttpServletRequest httpRequest) throws Exception {
        //Assumption is they have ONE file submitted attached to the object.  That is the file to be removed
        SynthesisMaterialBean entityBean = transferSynthesisMaterialBean(simpleSynthesisMaterialBean, httpRequest);

//        List<FileBean> files = entityBean.getFiles();
//        if(files.size()>1){
//            throw new SynthesisException("Can only remove one file at a time from SynthesisMaterial");
//        }
//        FileBean theFile = files.get(0);
//        entityBean.removeFile(theFile);

//        FileBean theFile = new FileBean(simpleSynthesisMaterialBean.getFileBeingEdited());
        //TODO needs a better match

        FileBean theFile = entityBean.getFile(simpleSynthesisMaterialBean.getFileBeingEdited().getId());
        entityBean.removeFile(theFile);

        List<String> msgs = validateInputs(httpRequest, entityBean);
        if (msgs.size() > 0) {
            SimpleSynthesisMaterialBean synMat = new SimpleSynthesisMaterialBean();
            synMat.setErrors(msgs);
            return synMat;
        }
        this.saveEntity(httpRequest, simpleSynthesisMaterialBean.getSampleId(), entityBean);
        httpRequest.setAttribute("anchor", "file");
        this.checkOpenForms(entityBean, httpRequest);
        return setupUpdate(simpleSynthesisMaterialBean.getSampleId(), entityBean.getDomainEntity().getId().toString()
                , httpRequest);
    }



    private void checkOpenForms(SynthesisMaterialBean synthesisMaterialBean, HttpServletRequest request) throws Exception {
        String dispatch = request.getParameter("dispatch");
        String browserDispatch = getBrowserDispatch(request);
        HttpSession session = request.getSession();


        InitSynthesisSetup.getInstance().persistSynthesisMaterialDropdowns(
                request, synthesisMaterialBean);

        // Synthesis Material Type
//        String entityType = synthesisMaterialBean.getDomainEntity().getType();
//        setOtherValueOption(request, entityType, "synthesisMaterialTypes");

        //TODO Check SynthesisMaterialElement?


        String detailPage = InitSynthesisSetup.getInstance().getDetailPage(
                "synthesisMaterial");

        request.setAttribute("synthesisDetailPage", detailPage);

    }

    public SimpleSynthesisMaterialBean setupUpdate(String sampleId, String synMatId, HttpServletRequest httpRequest) throws Exception {
        SynthesisForm form = new SynthesisForm();
        // set up other particles with the same primary point of contact
//        InitSampleSetup.getInstance().getOtherSampleNames(httpRequest, sampleId, sampleService);

        try {
            SynthesisMaterialBean synthesisMaterialBean = synthesisService.findSynthesisMaterialById(new Long(sampleId),
                    new Long(synMatId));

            SynthesisBean synthesisBean = synthesisService.findSynthesisBySampleId(new Long(sampleId));
            synthesisMaterialBean.setSynthesis(synthesisBean);
//            synthesisMaterialBean.setSynthesisId(synthesisBean.getDomainId());
            form.setSynthesisMaterialBean(synthesisMaterialBean);
            this.checkOpenForms(synthesisMaterialBean, httpRequest);
            httpRequest.getSession().setAttribute("sampleId", sampleId);
            SimpleSynthesisMaterialBean simpleSynthesisMaterialBean = new SimpleSynthesisMaterialBean();
            simpleSynthesisMaterialBean.transferSynthesisMaterialBeanToSimple(synthesisMaterialBean, httpRequest,
                    springSecurityAclService);
            simpleSynthesisMaterialBean.setProtocolLookup(httpRequest, protocolService);
            return simpleSynthesisMaterialBean;
        }
        catch (IllegalFormatConversionException e) {
            logger.error("Either sample id or data id is not an appropriate identifier", e);
            throw e;
        }
    }

    public SimpleSynthesisMaterialBean removeMaterialElement(SimpleSynthesisMaterialBean simpleSynthesisMaterialBean,
                                                             HttpServletRequest httpRequest) throws Exception {
        List<String> msgs = new ArrayList<String>();
        SynthesisMaterialBean entity = transferSynthesisMaterialBean(simpleSynthesisMaterialBean, httpRequest);
        SimpleSynthesisMaterialElementBean elementBeingEdited = simpleSynthesisMaterialBean.getMaterialElementBeingEdited();
        SynthesisMaterialElementBean materialElementBean = entity.getSynthesisMaterialElementById(elementBeingEdited.getId());
        entity.removeMaterialElement(materialElementBean);
        msgs = validateInputs(httpRequest, entity);
        //If an error, return blank class
        if (msgs.size() > 0) {
            SimpleSynthesisMaterialBean nullBean = new SimpleSynthesisMaterialBean();
            nullBean.setErrors(msgs);
            return nullBean;
        }
        this.saveEntity(httpRequest, simpleSynthesisMaterialBean.getSampleId(), entity);
        this.checkOpenForms(entity, httpRequest);
        return setupUpdate(simpleSynthesisMaterialBean.getSampleId(), entity.getDomainEntity().getId().toString(),
                httpRequest);
    }

    public SimpleSynthesisMaterialBean saveFile(SimpleSynthesisMaterialBean simpleSynthesisMaterialBean,
                                                HttpServletRequest httpRequest) throws Exception{

        SynthesisMaterialBean synthesisMaterialBean = transferSynthesisMaterialBean(simpleSynthesisMaterialBean, httpRequest);
        List<FileBean> fileList = synthesisMaterialBean.getFiles();


        String timestamp = DateUtils.convertDateToString(new Date(), "yyyyMMdd_HH-mm-ss-SSS");

        SampleBean sampleBean = setupSampleById(simpleSynthesisMaterialBean.getSampleId(), httpRequest);
        FileBean theNewFile = new FileBean(simpleSynthesisMaterialBean.getFileBeingEdited());



        //Determine the directory for saving the file
        String internalUriPath = Constants.FOLDER_PARTICLE+'/'+sampleBean.getDomain().getName()+'/'+"synthesisMaterial";
        theNewFile.setupDomainFile(internalUriPath,SpringSecurityUtil.getLoggedInUserName());


        byte[] newFileData = (byte[]) httpRequest.getSession().getAttribute("newFileData");
        if(!theNewFile.getDomainFile().getUriExternal()){
            if(newFileData!=null){
                theNewFile.setNewFileData((byte[]) httpRequest.getSession().getAttribute("newFileData"));
                theNewFile.getDomainFile().setUri(Constants.FOLDER_PARTICLE + '/'
                        + sampleBean.getDomain().getName() + '/' + "nanomaterialEntity"+ "/" + timestamp + "_"
                        + theNewFile.getDomainFile().getName());
            }else if(theNewFile.getDomainFile().getId()!=null){
                theNewFile.getDomainFile().setUri(theNewFile.getDomainFile().getName());
            }else{
                theNewFile.getDomainFile().setUri(null);
            }
        }
        synthesisMaterialBean.addFile(theNewFile);

//
//        // save entity to save file because inverse="false"
        List<String> msgs = validateInputs(httpRequest, synthesisMaterialBean);
        if (msgs.size()>0) {
            SimpleSynthesisMaterialBean simpleSynMatBean = new SimpleSynthesisMaterialBean();
            simpleSynMatBean.setErrors(msgs);
            return simpleSynMatBean;
        }
        this.saveEntity(httpRequest,simpleSynthesisMaterialBean.getSampleId(), synthesisMaterialBean);
//        compositionService.assignAccesses(entity.getDomainEntity().getSampleComposition(), theFile.getDomainFile());

        httpRequest.setAttribute("anchor", "file");
        httpRequest.setAttribute("dataId", synthesisMaterialBean.getDomainEntity().getId().toString());
        httpRequest.getSession().removeAttribute("newFileData");

        return setupUpdate(simpleSynthesisMaterialBean.getSampleId(), synthesisMaterialBean.getDomainEntity().getId().toString(), httpRequest);


    }

    public SimpleSynthesisMaterialBean saveMaterialElement(SimpleSynthesisMaterialBean simpleSynthesisMaterialBean,
                                                           HttpServletRequest httpServletRequest) throws Exception {
        SynthesisMaterialBean entity = null;
        String sampleId = simpleSynthesisMaterialBean.getSampleId();
        try {
            entity = transferSynthesisMaterialBean(simpleSynthesisMaterialBean, httpServletRequest);

            SimpleSynthesisMaterialElementBean elementBeingEdited = simpleSynthesisMaterialBean.getMaterialElementBeingEdited();
            SynthesisMaterialElementBean newElementBean = new SynthesisMaterialElementBean(elementBeingEdited);
            newElementBean.getDomainEntity().setSynthesisMaterialId(entity.getId());
//            newElementBean.getDomainEntity().setSynthesisMaterial(entity.getDomainEntity());




            List<SynthesisMaterialElementBean> synthesisMaterialElementBeans = entity.getSynthesisMaterialElements();
            synthesisMaterialElementBeans.add(newElementBean);
            for (SynthesisMaterialElementBean synthesisMaterialElementBean : synthesisMaterialElementBeans) {
                synthesisMaterialElementBean.setupDomain(SpringSecurityUtil.getLoggedInUserName());

            }
            List<String> msgs,msgs2 = new ArrayList<String>();
            msgs = validateInputs(httpServletRequest, entity);
            msgs2 = this.saveEntity(httpServletRequest, sampleId, entity);
            if(msgs2.size()>0){
                msgs.addAll(msgs2);
            }
            if (msgs.size() > 0) {
                SimpleSynthesisMaterialBean simpleSynthesisMaterialBean_error = new SimpleSynthesisMaterialBean();
                simpleSynthesisMaterialBean_error.setErrors(msgs);
                return simpleSynthesisMaterialBean_error;
            }

            httpServletRequest.setAttribute("dataId", entity.getDomainEntity().getId().toString());


        }
        catch (Exception e) {
            logger.error("Error while saving Synthesis Material Element ", e);
            throw new SynthesisException("Error while saving Synthesis Material Element ");
        }
        return setupUpdate(sampleId, entity.getDomainEntity().getId().toString(), httpServletRequest);
    }

    public Map<String, Object> setupNew(String sampleId, HttpServletRequest request) throws Exception {
        SynthesisMaterialBean synthesisMaterialBean = new SynthesisMaterialBean();
        List<String> otherNames = InitSampleSetup.getInstance().getOtherSampleNames(request, sampleId, sampleService);
        this.setLookups(request);
        this.checkOpenForms(synthesisMaterialBean, request);
        Map<String,Object> testLookup = new HashMap<String, Object>();
        testLookup.put("protocolLookup", this.setProtocolLookup(request));
        testLookup.putAll(SynthesisUtil.reformatLocalSearchDropdownsInSessionForSynthesisMaterial(request.getSession()));
        List<String> supplierNames = synthesisService.getSupplierNames();
        testLookup.put("supplierNames",supplierNames);
        return testLookup;
    }

    public void setLookups(HttpServletRequest request) throws Exception {
        ServletContext appContext = request.getSession().getServletContext();


//        List<ProtocolBean> protocols = protocolService.getSynthesisProtocols(request);
        InitSynthesisSetup.getInstance().setSynthesisMaterialDropdowns(request);

//        InitSetup.getInstance().getDefaultTypesByLookup(appContext,
//                "wallTypes", "carbon nanotube", "wallType");
    }

    public SynthesisMaterialBean setupSynMaterialForAdvSearch(String sampleId, Long id,
                                                              HttpServletRequest httpRequest) {
        //TODO write
        return null;
    }

    public List<SimpleProtocol> setProtocolLookup(HttpServletRequest request)
            throws Exception {
        List<SimpleProtocol> protocolLookup = new ArrayList<SimpleProtocol>();
        List<ProtocolBean> protoBeans = protocolService.getSynthesisProtocols(request);

        if (protoBeans == null)
            return protocolLookup;

        for (ProtocolBean protoBean : protoBeans) {
            SimpleProtocol simpleProto = new SimpleProtocol();
            simpleProto.transferFromProtocolBean(protoBean);
            protocolLookup.add(simpleProto);
        }
        return protocolLookup;
    }




}
