package gov.nih.nci.cananolab.util;

import java.util.HashMap;
import java.util.Map;

public class CaNanoLabConstants {
	public static final String DOMAIN_MODEL_NAME = "caNanoLab";

	public static final String SDK_BEAN_JAR = "caNanoLabSDK-beans.jar";

	public static final String CSM_APP_NAME = "caNanoLab";

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	public static final String ACCEPT_DATE_FORMAT = "MM/dd/yy";

	// File upload
	public static final String FILEUPLOAD_PROPERTY = "caNanoLab.properties";

	public static final String UNCOMPRESSED_FILE_DIRECTORY = "decompressedFiles";

	public static final String EMPTY = "N/A";

	// caNanoLab property file
	public static final String CANANOLAB_PROPERTY = "caNanoLab.properties";

	public static final String BOOLEAN_YES = "Yes";

	public static final String BOOLEAN_NO = "No";

	public static final String[] BOOLEAN_CHOICES = new String[] { BOOLEAN_YES,
			BOOLEAN_NO };

	public static final String DEFAULT_SAMPLE_PREFIX = "NANO-";

	public static final String DEFAULT_APP_OWNER = "NCICB";

	public static final String APP_OWNER;

	public static final String VIEW_COL_DELIMITER = "~~~";

	public static final String VIEW_CLASSNAME_DELIMITER = "!!!";

	static {
		String appOwner = PropertyReader.getProperty(CANANOLAB_PROPERTY,
				"applicationOwner").trim();
		if (appOwner == null || appOwner.length() == 0)
			appOwner = DEFAULT_APP_OWNER;
		APP_OWNER = appOwner;
	}

	public static final String SAMPLE_PREFIX;
	static {
		String samplePrefix = PropertyReader.getProperty(CANANOLAB_PROPERTY,
				"samplePrefix");
		if (samplePrefix == null || samplePrefix.length() == 0)
			samplePrefix = DEFAULT_SAMPLE_PREFIX;
		SAMPLE_PREFIX = samplePrefix;
	}

	public static final String GRID_INDEX_SERVICE_URL;
	static {
		String gridIndexServiceURL = PropertyReader.getProperty(
				CANANOLAB_PROPERTY, "gridIndexServiceURL").trim();
		GRID_INDEX_SERVICE_URL = gridIndexServiceURL;
	}

	/*
	 * The following Strings are nano specific
	 *
	 */
	public static final String[] DEFAULT_CHARACTERIZATION_SOURCES = new String[] { APP_OWNER };

	public static final String ASSOCIATED_FILE = "Other Associated File";

	public static final String PROTOCOL_FILE = "Protocol File";

	public static final String FOLDER_PARTICLE = "particles";

	// public static final String FOLDER_REPORT = "reports";

	public static final String FOLDER_PUBLICATION = "publications";

	public static final String FOLDER_PROTOCOL = "protocols";

	public static final String[] DEFAULT_POLYMER_INITIATORS = new String[] {
			"Free Radicals", "Peroxide" };

	public static final String CHARACTERIZATION_FILE = "characterizationFile";

	public static final int MAX_VIEW_TITLE_LENGTH = 23;

	public static final String CSM_DATA_CURATOR = APP_OWNER + "_DataCurator";

	public static final String CSM_RESEARCHER = APP_OWNER + "_Researcher";

	public static final String CSM_ADMIN = APP_OWNER + "_Administrator";

	public static final String CSM_PUBLIC_GROUP = "Public";

	public static final String[] VISIBLE_GROUPS = new String[] {
			CSM_DATA_CURATOR, CSM_RESEARCHER };

	public static final String AUTO_COPY_ANNOTATION_PREFIX = "COPY";

	public static final String AUTO_COPY_ANNNOTATION_VIEW_COLOR = "red";

	public static final String CSM_READ_ROLE = "R";

	public static final String CSM_DELETE_ROLE = "D";

	public static final String CSM_EXECUTE_ROLE = "E";

	public static final String CSM_CURD_ROLE = "CURD";

	public static final String CSM_CUR_ROLE = "CUR";

	public static final String CSM_READ_PRIVILEGE = "READ";

	public static final String CSM_EXECUTE_PRIVILEGE = "EXECUTE";

	public static final String CSM_DELETE_PRIVILEGE = "DELETE";

	public static final String CSM_CREATE_PRIVILEGE = "CREATE";

	public static final String CSM_PG_PROTOCOL = "protocol";

	public static final String CSM_PG_PARTICLE = "nanoparticle";

	public static final String CSM_PG_PUBLICATION = "publication";

	public static final String PHYSICAL_CHARACTERIZATION_CLASS_NAME = "Physical Characterization";

	public static final String IN_VITRO_CHARACTERIZATION_CLASS_NAME = "In Vitro Characterization";

	public static final short CHARACTERIZATION_ROOT_DISPLAY_ORDER = 0;

	public static final Map<String, Integer> CHARACTERIZATION_ORDER_MAP = new HashMap<String, Integer>();
	static {
		CHARACTERIZATION_ORDER_MAP.put(new String("Physical Characterization"),
				new Integer(0));
		CHARACTERIZATION_ORDER_MAP.put(new String("In Vitro Characterization"),
				new Integer(1));
		CHARACTERIZATION_ORDER_MAP.put(new String("Toxicity"), new Integer(2));
		CHARACTERIZATION_ORDER_MAP.put(new String("Cytotoxicity"), new Integer(
				3));
		CHARACTERIZATION_ORDER_MAP.put(new String("Immunotoxicity"),
				new Integer(4));
		CHARACTERIZATION_ORDER_MAP.put(new String("Blood Contact"),
				new Integer(5));
		CHARACTERIZATION_ORDER_MAP.put(new String("Immune Cell Function"),
				new Integer(6));
	}

	/* image file name extension */
	public static final String[] IMAGE_FILE_EXTENSIONS = { "AVS", "BMP", "CIN",
			"DCX", "DIB", "DPX", "FITS", "GIF", "ICO", "JFIF", "JIF", "JPE",
			"JPEG", "JPG", "MIFF", "OTB", "P7", "PALM", "PAM", "PBM", "PCD",
			"PCDS", "PCL", "PCX", "PGM", "PICT", "PNG", "PNM", "PPM", "PSD",
			"RAS", "SGI", "SUN", "TGA", "TIF", "TIFF", "WMF", "XBM", "XPM",
			"YUV", "CGM", "DXF", "EMF", "EPS", "MET", "MVG", "ODG", "OTG",
			"STD", "SVG", "SXD", "WMF" };

	public static final String[] PRIVATE_DISPATCHES = { "create", "delete",
			"setupUpdate", "setupDeleteAll", "add", "remove" };

	public static final String PHYSICOCHEMICAL_ASSAY_PROTOCOL = "physico-chemical assay";
	public static final String INVITRO_ASSAY_PROTOCOL = "in vitro assay";

	public static final String NODE_UNAVAILABLE = "Unable to connect to the grid location that you selected";

	// default discovery internal for grid index server
	public static final int DEFAULT_GRID_DISCOVERY_INTERVAL_IN_MINS = 20;

	public static final String DOMAIN_MODEL_VERSION = "1.4";

	public static final String GRID_SERVICE_PATH = "wsrf-canano/services/cagrid/CaNanoLabService";

}
