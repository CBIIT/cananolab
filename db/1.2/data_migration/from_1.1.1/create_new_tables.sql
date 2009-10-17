-- create DATUM

CREATE TABLE DATUM
(
  DATUM_PK_ID                  NUMBER           NOT NULL,
  NAME                         VARCHAR2(2000)   NOT NULL,
  VALUE                        NUMBER           NOT NULL,
  VALUE_UNIT                   VARCHAR2(200),
  DERIVED_BIOASSAY_DATA_PK_ID  NUMBER,
  CONTROL_NAME                 VARCHAR2(200),
  CONTROL_TYPE                 VARCHAR2(100),
  LIST_INDEX                   NUMBER,
  STATISTICS_TYPE              VARCHAR2(200),
  BIOASSAY_DATA_CATEGORY       VARCHAR2(2000)
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_DATUM ON DATUM
(DATUM_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DATUM ADD (
  CONSTRAINT PK_DATUM PRIMARY KEY (DATUM_PK_ID));



-- create DATUM_NAME

CREATE TABLE DEF_DATUM_NAME
(
  DATUM_NAME_PK_ID       NUMBER                 NOT NULL,
  NAME                   VARCHAR2(2000)         NOT NULL,
  IS_DATUM_PARSED        NUMBER(2)              DEFAULT 1                     NOT NULL,
  CHARACTERIZATION_NAME  VARCHAR2(2000)
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_DEF_DATUM_NAME ON DEF_DATUM_NAME
(DATUM_NAME_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DEF_DATUM_NAME ADD (
  CONSTRAINT PK_DEF_DATUM_NAME PRIMARY KEY (DATUM_NAME_PK_ID));


-- create DATUM_CONDITION

CREATE TABLE DATUM_CONDITION
(
  DATUM_CONDITION_PK_ID  NUMBER                 NOT NULL,
  NAME                   VARCHAR2(100)          NOT NULL,
  VALUE                  NUMBER                 NOT NULL,
  VALUE_UNIT             VARCHAR2(200)          NOT NULL,
  DATUM_PK_ID            NUMBER                 NOT NULL,
  LIST_INDEX             NUMBER,
  STATISTICS_TYPE        VARCHAR2(200)
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_DATUM_CONDITION ON DATUM_CONDITION
(DATUM_CONDITION_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DATUM_CONDITION ADD (
  CONSTRAINT PK_DATUM_CONDITION PRIMARY KEY (DATUM_CONDITION_PK_ID));

-- create DERIVED_BIOASSAY_DATA

CREATE TABLE DERIVED_BIOASSAY_DATA
(
  DERIVED_BIOASSAY_DATA_PK_ID  NUMBER           NOT NULL,
  CHARACTERIZATION_PK_ID       NUMBER,
  CATEGORY                     VARCHAR2(500),
  LIST_INDEX                   NUMBER
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_DERIVED_BIOASSAY_DATA ON DERIVED_BIOASSAY_DATA
(DERIVED_BIOASSAY_DATA_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DERIVED_BIOASSAY_DATA ADD (
  CONSTRAINT PK_DERIVED_BIOASSAY_DATA PRIMARY KEY (DERIVED_BIOASSAY_DATA_PK_ID));


-- create KEYWORD_BIOASSAY_DATA

CREATE TABLE KEYWORD_BIOASSAY_DATA
(
  KEYWORD_PK_ID                NUMBER           NOT NULL,
  DERIVED_BIOASSAY_DATA_PK_ID  NUMBER           NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_KEYWORD_BIOASSAY_DATA ON KEYWORD_BIOASSAY_DATA
(KEYWORD_PK_ID, DERIVED_BIOASSAY_DATA_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE KEYWORD_BIOASSAY_DATA ADD (
  CONSTRAINT PK_KEYWORD_BIOASSAY_DATA PRIMARY KEY (KEYWORD_PK_ID, DERIVED_BIOASSAY_DATA_PK_ID));


-- create  PROTOCOL_FILE

CREATE TABLE PROTOCOL_FILE
(
  PROTOCOL_FILE_PK_ID  NUMBER                   NOT NULL,
  PROTOCOL_PK_ID       NUMBER                   NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_PROTOCOL_FILE ON PROTOCOL_FILE
(PROTOCOL_FILE_PK_ID)
LOGGING
NOPARALLEL;




-- create INSTRUMENT_CONFIG 

-- CREATE TABLE INSTRUMENT_CONFIG
-- (
--   INSTRUMENT_CONFIG_PK_ID  NUMBER               NOT NULL,
--   DESCRIPTION              VARCHAR2(4000),
--   INSTRUMENT_PK_ID         NUMBER               NOT NULL
-- )
-- LOGGING 
-- NOCACHE
-- NOPARALLEL;


-- CREATE UNIQUE INDEX PK_INSTRUMENT_CONFIG ON INSTRUMENT_CONFIG
-- (INSTRUMENT_CONFIG_PK_ID)
-- LOGGING
-- NOPARALLEL;




--  create Def_Function_type

CREATE TABLE DEF_FUNCTION_TYPE
(
  FUNCTION_TYPE_PK_ID  NUMBER                   NOT NULL,
  NAME                 VARCHAR2(200)            NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_FUNCTION_TYPE ON DEF_FUNCTION_TYPE
(FUNCTION_TYPE_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DEF_FUNCTION_TYPE ADD (
  CONSTRAINT PK_FUNCTION_TYPE PRIMARY KEY (FUNCTION_TYPE_PK_ID));


--Alter existing tables

ALTER TABLE PROTOCOL_FILE ADD (
  CONSTRAINT PK_PROTOCOL_FILE PRIMARY KEY (PROTOCOL_FILE_PK_ID));



-- alter lab_file table to add a new column
ALTER TABLE LAB_FILE
	ADD type	VARCHAR2(200);


-- alter characterization table

ALTER TABLE CHARACTERIZATION
	ADD protocol_file_pk_id	NUMBER;

-- ALTER TABLE CHARACTERIZATION
-- 	ADD Instrument_config_pk_id	NUMBER;


-- alter protocol


ALTER TABLE PROTOCOL
	MODIFY (PROTOCOL_NAME	VARCHAR2(2000) )
	ADD PROTOCOL_TYPE	VARCHAR2(2000);


-- CREATE TABLE INSTRUMENT_TMP
-- (
--   INSTRUMENT_PK_ID  NUMBER                 NOT NULL,
--   TYPE                   VARCHAR2(200),
--   ABBREVIATION           VARCHAR2(50),
--   MANUFACTURER           VARCHAR2(2000)
-- )
-- LOGGING 
-- NOCACHE
-- NOPARALLEL;


-- ALTER TABLE INSTRUMENT_TMP ADD (
--   PRIMARY KEY (INSTRUMENT_PK_ID));

CREATE TABLE DEF_BIOASSAY_DATA_CATEGORY
(
  CATEGORY_PK_ID         NUMBER                 NOT NULL,
  NAME                   VARCHAR2(2000)         NOT NULL,
  CHARACTERIZATION_NAME  VARCHAR2(2000)
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_BIOASSAY_DATA_CATEGORY ON DEF_BIOASSAY_DATA_CATEGORY
(CATEGORY_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DEF_BIOASSAY_DATA_CATEGORY ADD (
  CONSTRAINT PK_BIOASSAY_DATA_CATEGORY PRIMARY KEY (CATEGORY_PK_ID));

CREATE TABLE DEF_CHARACTERIZATION_CATEGORY
(
  CHAR_CATEGORY_PK_ID  NUMBER                   NOT NULL,
  CATEGORY             VARCHAR2(200)            NOT NULL,
  NAME                 VARCHAR2(200)            NOT NULL,
  HAS_ACTION           NUMBER                   NOT NULL,
  CATEGORY_ORDER       NUMBER                   NOT NULL,
  INDENT_LEVEL         NUMBER                   NOT NULL,
  NAME_ABBREVIATION    VARCHAR2(200)
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_CHARACTERIZATION_CATEGORY ON DEF_CHARACTERIZATION_CATEGORY
(CHAR_CATEGORY_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DEF_CHARACTERIZATION_CATEGORY ADD (
  CONSTRAINT PK_CHARACTERIZATION_CATEGORY PRIMARY KEY (CHAR_CATEGORY_PK_ID));



-- create def_measure_type

CREATE TABLE DEF_MEASURE_TYPE
(
  MEASURE_TYPE_PK_ID  NUMBER                    NOT NULL,
  NAME                VARCHAR2(200)             NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_DEF_MEASURE_TYPE ON DEF_MEASURE_TYPE
(MEASURE_TYPE_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DEF_MEASURE_TYPE ADD (
  CONSTRAINT PK_DEF_MEASURE_TYPE PRIMARY KEY (MEASURE_TYPE_PK_ID));


 
-- create bioassay_data_data_category
CREATE TABLE BIOASSAY_DATA_DATA_CATEGORY
(
  DERIVED_BIOASSAY_DATA_PK_ID  NUMBER           NOT NULL,
  CATEGORY_INDEX               NUMBER           NOT NULL,
  CATEGORY_NAME                VARCHAR2(2000)   NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_BIOASSAY_DATA_DATA_CATEGORY ON BIOASSAY_DATA_DATA_CATEGORY
(DERIVED_BIOASSAY_DATA_PK_ID, CATEGORY_INDEX)
LOGGING
NOPARALLEL;


ALTER TABLE BIOASSAY_DATA_DATA_CATEGORY ADD (
  CONSTRAINT PK_BIOASSAY_DATA_DATA_CATEGORY PRIMARY KEY (DERIVED_BIOASSAY_DATA_PK_ID, CATEGORY_INDEX));


ALTER TABLE BIOASSAY_DATA_DATA_CATEGORY ADD (
  CONSTRAINT FK_DATA_DATA_CATEGORY FOREIGN KEY (DERIVED_BIOASSAY_DATA_PK_ID) 
    REFERENCES DERIVED_BIOASSAY_DATA (DERIVED_BIOASSAY_DATA_PK_ID));

CREATE TABLE DEF_CHARACTERIZATION_FILE_TYPE
(
  FILE_TYPE_PK_ID  NUMBER                       NOT NULL,
  NAME             VARCHAR2(2000)               NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX PK_DEF_FILE_TYPE ON DEF_CHARACTERIZATION_FILE_TYPE
(FILE_TYPE_PK_ID)
LOGGING
NOPARALLEL;


ALTER TABLE DEF_CHARACTERIZATION_FILE_TYPE ADD (
  CONSTRAINT PK_DEF_FILE_TYPE PRIMARY KEY (FILE_TYPE_PK_ID));

CREATE TABLE DEF_MORPHOLOGY_TYPE
(
  MORPHOLOGY_TYPE_PK_ID  NUMBER                    NOT NULL,
  NAME     VARCHAR2(200)                           NOT NULL  
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_MORPHOLOGY_TYPE ADD (
  CONSTRAINT PK_DEF_MORPHOLOGY_TYPE PRIMARY KEY (MORPHOLOGY_TYPE_PK_ID));


CREATE TABLE DEF_CELLLINE_TYPE
(
  CELLLINE_TYPE_PK_ID  NUMBER                      NOT NULL,
  NAME     VARCHAR2(200)                           NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_CELLLINE_TYPE ADD (
  CONSTRAINT PK_DEF_CELLLINE_TYPE PRIMARY KEY (CELLLINE_TYPE_PK_ID));


CREATE TABLE DEF_MOLECULAR_FORMULA_TYPE
(
  MOLECULAR_FORMULA_TYPE_PK_ID  NUMBER             NOT NULL,
  NAME     VARCHAR2(200)                           NOT NULL
) 
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_MOLECULAR_FORMULA_TYPE ADD (
  CONSTRAINT PK_DEF_MOLECULAR_FORMULA_TYPE PRIMARY KEY (MOLECULAR_FORMULA_TYPE_PK_ID));


CREATE TABLE DEF_SHAPE_TYPE
(
  SHAPE_TYPE_PK_ID  NUMBER                         NOT NULL,
  NAME     VARCHAR2(200)                           NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_SHAPE_TYPE ADD (
  CONSTRAINT PK_DEF_SHAPE_TYPE PRIMARY KEY (SHAPE_TYPE_PK_ID));

CREATE TABLE DEF_SOLVENT_TYPE
(
  SOLVENT_TYPE_PK_ID  NUMBER                       NOT NULL,
  NAME     VARCHAR2(200)                           NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_SOLVENT_TYPE ADD (
  CONSTRAINT PK_DEF_SOLVENT_TYPE PRIMARY KEY (SOLVENT_TYPE_PK_ID));

CREATE TABLE DEF_SURFACE_GROUP_TYPE
(
  SURFACE_GROUP_TYPE_PK_ID  NUMBER                 NOT NULL,
  NAME     VARCHAR2(200)                           NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_SURFACE_GROUP_TYPE ADD (
  CONSTRAINT PK_DEF_SURFACE_GROUP_TYPE PRIMARY KEY (SURFACE_GROUP_TYPE_PK_ID));
  

CREATE TABLE DEF_BOND_TYPE
(
  BOND_TYPE_PK_ID  NUMBER                       NOT NULL,
  NAME             VARCHAR2(200)                NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


ALTER TABLE DEF_BOND_TYPE ADD (
  CONSTRAINT PK_DEF_BOND_TYPE PRIMARY KEY (BOND_TYPE_PK_ID));


CREATE TABLE DEF_ACTIVATION_METHOD
(
  ACTIVATION_METHOD_PK_ID  NUMBER               NOT NULL,
  NAME                     VARCHAR2(200)        NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_ACTIVATION_METHOD ADD (
  CONSTRAINT PK_DEF_ACTIVATION_METHOD PRIMARY KEY (ACTIVATION_METHOD_PK_ID));

CREATE TABLE DEF_SPECIES_NAME
(
  SPECIES_NAME_PK_ID  NUMBER                          NOT NULL,
  NAME                VARCHAR2(200)                   NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_SPECIES_NAME ADD (
  CONSTRAINT PK_DEF_SPECIES PRIMARY KEY (SPECIES_NAME_PK_ID));


CREATE TABLE DEF_IMAGE_CONTRAST_AGENT_TYPE
(
  AGENT_TYPE_PK_ID  NUMBER            NOT NULL,
  NAME              VARCHAR2(200)     NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_IMAGE_CONTRAST_AGENT_TYPE ADD (
  CONSTRAINT PK_DEF_AGENT_TYPE PRIMARY KEY (AGENT_TYPE_PK_ID));

CREATE TABLE DEF_WALL_TYPE
(
  WALL_TYPE_PK_ID  NUMBER                       NOT NULL,
  NAME             VARCHAR2(200)                NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_WALL_TYPE ADD (
  CONSTRAINT PK_DEF_WALL_TYPE PRIMARY KEY (WALL_TYPE_PK_ID));
  
  
CREATE TABLE DEF_MEASURE_UNIT
(
  MEASURE_UNIT_PK_ID  NUMBER                    NOT NULL,
  UNIT_NAME           VARCHAR2(50)				NOT NULL,
  DESCRIPTION         VARCHAR2(1000),
  UNIT_TYPE           VARCHAR2(100)				NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


ALTER TABLE DEF_MEASURE_UNIT ADD (
  PRIMARY KEY (MEASURE_UNIT_PK_ID));  


CREATE TABLE DEF_PROTOCOL_TYPE
(
  PROTOCOL_TYPE_PK_ID  NUMBER                   NOT NULL,
  NAME                 VARCHAR2(2000)           NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

CREATE TABLE DEF_SAMPLE_TYPE
(
  SAMPLE_TYPE_PK_ID  NUMBER                     NOT NULL,
  NAME               VARCHAR2(200)				NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

CREATE TABLE DEF_STORAGE_TYPE
(
  STORAGE_TYPE_ID  NUMBER                       NOT NULL,
  NAME     VARCHAR2(200)						NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;


CREATE TABLE DEF_ASSAY_TYPE
(
  ASSAY_TYPE_PK_ID  NUMBER                      NOT NULL,
  NAME              VARCHAR2(200)				NOT NULL,
  DESCRIPTION       VARCHAR2(4000),
  EXECUTE_ORDER     VARCHAR2(10)
)
LOGGING 
NOCACHE
NOPARALLEL;

CREATE TABLE DEF_FUNCTION_AGENT_TARGET_TYPE
(
  AGENT_TARGET_TYPE_PK_ID  NUMBER               NOT NULL,
  NAME                     VARCHAR2(2000)       NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

CREATE TABLE DEF_FUNCTION_AGENT_TYPE
(
  AGENT_TYPE_PK_ID  NUMBER                      NOT NULL,
  NAME              VARCHAR2(2000)              NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

CREATE TABLE DEF_FUNCTION_LINKAGE_TYPE
(
  LINKAGE_TYPE_PK_ID  NUMBER                    NOT NULL,
  NAME                VARCHAR2(2000)            NOT NULL
)
LOGGING 
NOCACHE
NOPARALLEL;

ALTER TABLE DEF_ASSAY_TYPE ADD (
  PRIMARY KEY (ASSAY_TYPE_PK_ID));

ALTER TABLE DEF_STORAGE_TYPE ADD (
  PRIMARY KEY (STORAGE_TYPE_ID));
-- Add new column to Surface Chemistry

ALTER TABLE SURFACE_CHEMISTRY ADD MOLECULAR_FORMULA_TYPE VARCHAR2(200);

-- Add new column to Surface for zeta potentail unit

ALTER TABLE SURFACE ADD ZETA_POTENTIAL_UNIT VARCHAR2(100); 

-- update shape table to add min_dimension_unit and max_dimension_unit
ALTER TABLE SHAPE ADD MIN_DIMENSION_UNIT VARCHAR2(100);
ALTER TABLE SHAPE ADD MAX_DIMENSION_UNIT VARCHAR2(100);
