package gov.nih.nci.cananolab.dto.common;

import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.domain.common.Keyword;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;
import gov.nih.nci.cananolab.util.DateUtil;
import gov.nih.nci.cananolab.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.struts.upload.FormFile;

/**
 * This class represents attributes of a lab file to be viewed in a view page.
 *
 * @author pansu
 *
 */
public class FileBean {
	protected File domainFile = new File();

	private String[] visibilityGroups = new String[0];

	private String visibilityStr;

	private boolean hidden = false;

	private boolean image = false;

	private String keywordsStr;

	private String externalUrl; // url as an external link

	private FormFile uploadedFile;

	private byte[] newFileData; // data from uploadedFile if upload happened

	private String location; // e.g. local, caNanoLab-WashU

	private String fullPath; // e.g.
	// C:/temp/caNanoLab/fileUpload/particles/NCL-23/...,

	// or for remote files, it will be the remote download URL

	private String createdDateStr;

	public FileBean() {
		domainFile.setUriExternal(false);
	}

	public FileBean(File File) {
		this.domainFile = File;
		SortedSet<String> keywordStrs = new TreeSet<String>();
		if (domainFile.getKeywordCollection() != null) {
			for (Keyword keyword : domainFile.getKeywordCollection()) {
				keywordStrs.add(keyword.getName());
			}
		}
		keywordsStr = StringUtils.join(keywordStrs, "\r\n");
		if (File.getUriExternal() != null && File.getUriExternal()) {
			externalUrl = File.getUri();
		}
	}

	public String[] getVisibilityGroups() {
		return this.visibilityGroups;
	}

	public void setVisibilityGroups(String[] visibilityGroups) {
		this.visibilityGroups = visibilityGroups;
	}

	public String getDisplayName() {
		return getDomainFile().getTitle();
	}

	public String getVisibilityStr() {
		this.visibilityStr = StringUtils.join(this.visibilityGroups, "<br>");
		return this.visibilityStr;
	}

	public void setVisibilityStr(String visibilityStr) {
		this.visibilityStr = visibilityStr;
	}

	public boolean isHidden() {
		return this.hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isImage() {
		image = StringUtils.isImgFileExt(getDomainFile().getUri());
		return image;
	}

	public File getDomainFile() {
		return domainFile;
	}

	public String getKeywordsStr() {
		return keywordsStr;
	}

	public void setKeywordsStr(String keywordsStr) {
		this.keywordsStr = keywordsStr;
	}

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	public String getUrlTarget() {
		if (domainFile.getUriExternal() != null && domainFile.getUriExternal()) {
			return "pop";
		}
		return "_self";
	}

	public FormFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(FormFile uploadedFile) throws Exception {
		this.uploadedFile = uploadedFile;
	}

	public void setupDomainFile(String internalUriPath, String createdBy,
			int index) throws Exception {
		if (domainFile.getId() == null
				|| domainFile.getCreatedBy() != null
				&& domainFile.getCreatedBy().equals(
						CaNanoLabConstants.AUTO_COPY_ANNOTATION_PREFIX)) {
			domainFile.setCreatedBy(createdBy);
			// domainFile.setCreatedDate(new Date());
			// fix for MySQL database, which supports precision only up to
			// seconds
			domainFile.setCreatedDate(DateUtil.addSecondsToCurrentDate(index));
		}
		if (uploadedFile != null && uploadedFile.getFileName().length() > 0) {
			domainFile.setName(uploadedFile.getFileName());
			newFileData = uploadedFile.getFileData();
		} else {
			newFileData = null;
		}
		// if entered external url
		if (domainFile.getUriExternal() && externalUrl != null
				&& externalUrl.length() > 0) {
			domainFile.setUri(externalUrl);
			domainFile.setName(externalUrl);
		} else {
			String timestamp = StringUtils.convertDateToString(new Date(),
					"yyyyMMdd_HH-mm-ss-SSS");
			// if uploaded new file
			if (newFileData != null) {
				domainFile.setUri(internalUriPath + "/" + timestamp + "_"
						+ domainFile.getName());
			}
		}

		if (keywordsStr != null) {
			if (domainFile.getKeywordCollection() != null) {
				domainFile.getKeywordCollection().clear();
			} else {
				domainFile.setKeywordCollection(new HashSet<Keyword>());
			}
			String[] strs = keywordsStr.split("\r\n");
			for (String str : strs) {
				// change to upper case
				Keyword keyword = new Keyword();
				keyword.setName(str.toUpperCase());
				domainFile.getKeywordCollection().add(keyword);
			}
		}
	}

	public byte[] getNewFileData() {
		return newFileData;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public void setDomainFile(File domainFile) {
		this.domainFile = domainFile;
	}

	public String getCreatedDateStr() {
		createdDateStr = StringUtils.convertDateToString(domainFile
				.getCreatedDate(), CaNanoLabConstants.DATE_FORMAT);
		return createdDateStr;
	}

}
