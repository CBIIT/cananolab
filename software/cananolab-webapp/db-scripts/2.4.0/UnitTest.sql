/*
 This script is intended for setting up a local database for unit testing by developers
 */

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
SET NAMES utf8;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

drop table if exists csm_role_privilege;
drop table if exists csm_user_group;
drop table if exists csm_user_group_role_pg;
drop table if exists csm_user_pe;
drop table if exists csm_pg_pe;
drop table if exists csm_privilege;
drop table if exists csm_role;
drop table if exists csm_group;
drop table if exists csm_mapping;
drop table if exists csm_protection_element;
drop table if exists csm_protection_group;
drop table if exists csm_user;
drop table if exists csm_application;
drop table if exists csm_configuration_props;
drop table if exists csm_filter_clause;
drop table if exists csm_password_history;


DROP TABLE IF EXISTS `acl_entry`;
DROP TABLE IF EXISTS `acl_object_identity`;
DROP TABLE IF EXISTS `acl_class`;
DROP TABLE IF EXISTS `acl_sid`;
DROP TABLE IF EXISTS `antibody`;
DROP TABLE IF EXISTS `biopolymer_f`;
DROP TABLE IF EXISTS `functionalizing_entity_file`;
DROP TABLE IF EXISTS `target`;
DROP TABLE IF EXISTS `nano_function`;
DROP TABLE IF EXISTS `small_molecule`;
DROP TABLE IF EXISTS `other_functionalizing_entity`;
DROP TABLE IF EXISTS `functionalizing_entity`;
DROP TABLE IF EXISTS `activation_method`;
DROP TABLE IF EXISTS `administration`;
DROP TABLE IF EXISTS `chemical_association_file`;
DROP TABLE IF EXISTS `chemical_association`;
DROP TABLE IF EXISTS `composing_element`;
DROP TABLE IF EXISTS `associated_element`;
DROP TABLE IF EXISTS `author_publication`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `biopolymer_p`;
DROP TABLE IF EXISTS `carbon_nanotube`;
DROP TABLE IF EXISTS `experiment_config_instrument`;
DROP TABLE IF EXISTS `experiment_config`;
DROP TABLE IF EXISTS `datum_condition`;
DROP TABLE IF EXISTS `datum`;
DROP TABLE IF EXISTS `finding_file`;
DROP TABLE IF EXISTS `finding`;
DROP TABLE IF EXISTS `physical_state`;
DROP TABLE IF EXISTS `shape`;
DROP TABLE IF EXISTS `solubility`;
DROP TABLE IF EXISTS `characterization`;
DROP TABLE IF EXISTS `common_lookup`;
DROP TABLE IF EXISTS `composition_file`;
DROP TABLE IF EXISTS `DATA_AVAILABILITY`;
DROP TABLE IF EXISTS `DATA_AVAILABILITY`;
DROP TABLE IF EXISTS `data_review_status`;
DROP TABLE IF EXISTS `databasechangelog`;
DROP TABLE IF EXISTS `databasechangeloglock`;
DROP TABLE IF EXISTS `dendrimer`;
DROP TABLE IF EXISTS `emulsion`;
DROP TABLE IF EXISTS `purity_datum_condition`;
DROP TABLE IF EXISTS `experiment_condition`;
DROP TABLE IF EXISTS `favorite_data`;
DROP TABLE IF EXISTS `keyword_file`;
DROP TABLE IF EXISTS `nanomaterial_entity_file`;
DROP TABLE IF EXISTS `fullerene`;
DROP TABLE IF EXISTS `group_authorities`;
DROP TABLE IF EXISTS `group_members`;
DROP TABLE IF EXISTS `groups`;
DROP TABLE IF EXISTS `hibernate_unique_key`;
DROP TABLE IF EXISTS `purification_config_instrument`;
DROP TABLE IF EXISTS `instrument`;
DROP TABLE IF EXISTS `instrument_to_review`;
DROP TABLE IF EXISTS `keyword_sample`;
DROP TABLE IF EXISTS `keyword`;
DROP TABLE IF EXISTS `liposome`;
DROP TABLE IF EXISTS `other_nanomaterial_entity`;
DROP TABLE IF EXISTS `polymer`;
DROP TABLE IF EXISTS `nanomaterial_entity`;
DROP TABLE IF EXISTS `composition`;
DROP TABLE IF EXISTS `sample_publication`;
DROP TABLE IF EXISTS `sample_other_poc`;
DROP TABLE IF EXISTS `purity_file`;
DROP TABLE IF EXISTS `purity_datum`;
DROP TABLE IF EXISTS `purity_column_header`;
DROP TABLE IF EXISTS `synthesis_material_file`;
DROP TABLE IF EXISTS `synthesis_functionalization_file`;
DROP TABLE IF EXISTS `purification_config`;
DROP TABLE IF EXISTS `synthesis_purity`;
DROP TABLE IF EXISTS `synthesis_material_element_file`;
DROP TABLE IF EXISTS `sme_inherent_function`;
DROP TABLE IF EXISTS `synthesis_purification`;
DROP TABLE IF EXISTS `synthesis_material_element`;
DROP TABLE IF EXISTS `supplier`;
DROP TABLE IF EXISTS `synthesis_material`;
DROP TABLE IF EXISTS `sfe_inherent_function`;
DROP TABLE IF EXISTS `synthesis_functionalization_element_file`;
drop table IF exists `synthesis_functionalization_element`;
DROP TABLE IF EXISTS `synthesis_functionalization`;
DROP TABLE IF EXISTS `synthesis`;
DROP TABLE IF EXISTS `sample`;
DROP TABLE IF EXISTS `point_of_contact`;
DROP TABLE IF EXISTS `organization`;
DROP TABLE IF EXISTS `protocol`;
DROP TABLE IF EXISTS `publication`;
DROP TABLE IF EXISTS `file`;
DROP TABLE IF EXISTS `technique`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `acl_class`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `acl_class`
(
    `id`    bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `class` varchar(100)        NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_acl_class` (`class`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_class`
--

LOCK TABLES `acl_class` WRITE;
/*!40000 ALTER TABLE `acl_class`
    DISABLE KEYS */;
INSERT IGNORE INTO `acl_class` (`id`, `class`)
VALUES (13, 'gov.nih.nci.cananolab.domain.common.Author'),
       (9, 'gov.nih.nci.cananolab.domain.common.ExperimentConfig'),
       (10, 'gov.nih.nci.cananolab.domain.common.Finding'),
       (15, 'gov.nih.nci.cananolab.domain.common.Organization'),
       (16, 'gov.nih.nci.cananolab.domain.common.PointOfContact'),
       (8, 'gov.nih.nci.cananolab.domain.particle.Characterization'),
       (17, 'gov.nih.nci.cananolab.dto.common.CollaborationGroupBean'),
       (14, 'gov.nih.nci.cananolab.dto.common.FileBean'),
       (11, 'gov.nih.nci.cananolab.dto.common.ProtocolBean'),
       (12, 'gov.nih.nci.cananolab.dto.common.PublicationBean'),
       (7, 'gov.nih.nci.cananolab.dto.particle.composition.ChemicalAssociationBean'),
       (4, 'gov.nih.nci.cananolab.dto.particle.composition.ComposingElementBean'),
       (2, 'gov.nih.nci.cananolab.dto.particle.composition.CompositionBean'),
       (5, 'gov.nih.nci.cananolab.dto.particle.composition.FunctionalizingEntityBean'),
       (6, 'gov.nih.nci.cananolab.dto.particle.composition.FunctionBean'),
       (3, 'gov.nih.nci.cananolab.dto.particle.composition.NanomaterialEntityBean'),
       (1, 'gov.nih.nci.cananolab.dto.particle.SampleBean');
/*!40000 ALTER TABLE `acl_class`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_entry`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `acl_entry`
(
    `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `acl_object_identity` bigint(20) unsigned NOT NULL,
    `ace_order`           int(11)             NOT NULL,
    `sid`                 bigint(20) unsigned NOT NULL,
    `mask`                int(10) unsigned    NOT NULL,
    `granting`            tinyint(1)          NOT NULL,
    `audit_success`       tinyint(1)          NOT NULL,
    `audit_failure`       tinyint(1)          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_acl_entry` (`acl_object_identity`, `ace_order`),
    KEY `fk_acl_entry_acl` (`sid`),
    CONSTRAINT `fk_acl_entry_acl` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_acl_entry_object` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 189454
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_entry`
--

LOCK TABLES `acl_entry` WRITE;
/*!40000 ALTER TABLE `acl_entry`
    DISABLE KEYS */;
INSERT IGNORE INTO `acl_entry` (`id`, `acl_object_identity`, `ace_order`, `sid`, `mask`, `granting`, `audit_success`,
                                `audit_failure`)
VALUES (1, 1, 0, 3, 1, 1, 0, 0),
       (2, 1, 1, 3, 2, 1, 0, 0),
       (3, 1, 2, 3, 8, 1, 0, 0),
       (4, 1, 3, 13, 1, 1, 0, 0),
       (5, 1, 4, 13, 2, 1, 0, 0),
       (6, 1, 5, 13, 8, 1, 0, 0),
       (7, 1, 6, 14, 1, 1, 0, 0),
       (15, 1, 7, 12, 1, 1, 0, 0),
       (189281, 9008, 0, 12, 1, 1, 0, 0),
       (189291, 9010, 0, 3, 1, 1, 0, 0),
       (189292, 9010, 1, 3, 2, 1, 0, 0),
       (189293, 9010, 2, 3, 8, 1, 0, 0),
       (189294, 9010, 3, 13, 1, 1, 0, 0),
       (189295, 9010, 4, 13, 2, 1, 0, 0),
       (189296, 9010, 5, 13, 8, 1, 0, 0),
       (189297, 9010, 6, 14, 1, 1, 0, 0),
       (189307, 9011, 0, 3, 1, 1, 0, 0),
       (189308, 9011, 1, 3, 2, 1, 0, 0),
       (189309, 9011, 2, 3, 8, 1, 0, 0),
       (189310, 9011, 3, 13, 1, 1, 0, 0),
       (189311, 9011, 4, 13, 2, 1, 0, 0),
       (189312, 9011, 5, 13, 8, 1, 0, 0),
       (189313, 9011, 6, 14, 1, 1, 0, 0),
       (189323, 9012, 0, 3, 1, 1, 0, 0),
       (189324, 9012, 1, 3, 2, 1, 0, 0),
       (189325, 9012, 2, 3, 8, 1, 0, 0),
       (189326, 9012, 3, 13, 1, 1, 0, 0),
       (189327, 9012, 4, 13, 2, 1, 0, 0),
       (189328, 9012, 5, 13, 8, 1, 0, 0),
       (189329, 9012, 6, 14, 1, 1, 0, 0),
       (189353, 9015, 0, 3, 1, 1, 0, 0),
       (189354, 9015, 1, 3, 2, 1, 0, 0),
       (189355, 9015, 2, 3, 8, 1, 0, 0),
       (189356, 9015, 3, 13, 1, 1, 0, 0),
       (189357, 9015, 4, 13, 2, 1, 0, 0),
       (189358, 9015, 5, 13, 8, 1, 0, 0),
       (189359, 9015, 6, 14, 1, 1, 0, 0),
       (189360, 9015, 7, 12, 1, 1, 0, 0),
       (189370, 9017, 0, 3, 1, 1, 0, 0),
       (189371, 9017, 1, 3, 2, 1, 0, 0),
       (189372, 9017, 2, 3, 8, 1, 0, 0),
       (189373, 9017, 3, 13, 1, 1, 0, 0),
       (189374, 9017, 4, 13, 2, 1, 0, 0),
       (189375, 9017, 5, 13, 8, 1, 0, 0),
       (189376, 9017, 6, 14, 1, 1, 0, 0),
       (189386, 9018, 0, 3, 1, 1, 0, 0),
       (189387, 9018, 1, 3, 2, 1, 0, 0),
       (189388, 9018, 2, 3, 8, 1, 0, 0),
       (189389, 9018, 3, 13, 1, 1, 0, 0),
       (189390, 9018, 4, 13, 2, 1, 0, 0),
       (189391, 9018, 5, 13, 8, 1, 0, 0),
       (189392, 9018, 6, 14, 1, 1, 0, 0),
       (189400, 4, 0, 3, 1, 1, 0, 0),
       (189401, 4, 1, 3, 2, 1, 0, 0),
       (189402, 4, 2, 3, 8, 1, 0, 0),
       (189403, 4, 3, 13, 1, 1, 0, 0),
       (189404, 4, 4, 13, 2, 1, 0, 0),
       (189405, 4, 5, 13, 8, 1, 0, 0),
       (189406, 4, 6, 14, 1, 1, 0, 0),
       (189407, 4, 7, 12, 1, 1, 0, 0),
       (189417,9021,0,3,1,1,0,0),
       (189418,9021,1,3,2,1,0,0),
       (189419,9021,2,3,8,1,0,0),
       (189420,9021,3,13,1,1,0,0),
       (189421,9021,4,13,2,1,0,0),
       (189422,9021,5,13,8,1,0,0),
       (189423,9021,6,14,1,1,0,0),
       (189433,9022,0,3,1,1,0,0),
       (189434,9022,1,3,2,1,0,0),
       (189435,9022,2,3,8,1,0,0),
       (189436,9022,3,13,1,1,0,0),
       (189437,9022,4,13,2,1,0,0),
       (189438,9022,5,13,8,1,0,0),
       (189439,9022,6,14,1,1,0,0),
       (189440,9019,0,3,1,1,0,0),
       (189441,9019,1,3,2,1,0,0),
       (189442,9019,2,3,8,1,0,0),
       (189443,9019,3,13,1,1,0,0),
       (189444,9019,4,13,2,1,0,0),
       (189445,9019,5,13,8,1,0,0),
       (189446,9019,6,14,1,1,0,0),
       (189447,9020,0,3,1,1,0,0),
       (189448,9020,1,3,2,1,0,0),
       (189449,9020,2,3,8,1,0,0),
       (189450,9020,3,13,1,1,0,0),
       (189451,9020,4,13,2,1,0,0),
       (189452,9020,5,13,8,1,0,0),
       (189453,9020,6,14,1,1,0,0);
/*!40000 ALTER TABLE `acl_entry`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_object_identity`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `acl_object_identity`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `object_id_class`    bigint(20) unsigned NOT NULL,
    `object_id_identity` bigint(20)          NOT NULL,
    `parent_object`      bigint(20) unsigned DEFAULT NULL,
    `owner_sid`          bigint(20) unsigned DEFAULT NULL,
    `entries_inheriting` tinyint(1)          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_acl_object_identity` (`object_id_class`, `object_id_identity`),
    KEY `fk_acl_object_identity_parent` (`parent_object`),
    KEY `fk_acl_object_identity_owner` (`owner_sid`),
    CONSTRAINT `fk_acl_object_identity_class` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`),
    CONSTRAINT `fk_acl_object_identity_owner` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`),
    CONSTRAINT `fk_acl_object_identity_parent` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9021
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_object_identity`
--

LOCK TABLES `acl_object_identity` WRITE;
/*!40000 ALTER TABLE `acl_object_identity`
    DISABLE KEYS */;
INSERT IGNORE INTO `acl_object_identity` (`id`, `object_id_class`, `object_id_identity`, `parent_object`, `owner_sid`,
                                          `entries_inheriting`)
VALUES (1, 1, 1000, NULL, 3, 1),
       (2, 16, 1000, NULL, 3, 1),
       (3, 17, 1000, 2, 3, 1),
       (4, 1, 1005, NULL, 3, 1),
       (5, 16, 1005, NULL, 3, 1),
       (6, 17, 1005, 5, 3, 1),
       (9008, 15, 65601536, NULL, 3, 1),
       (9009, 16, 65568768, 9008, 3, 1),
       (9010, 1, 65634304, NULL, 3, 1),
       (9011, 1, 65634305, NULL, 3, 1),
       (9012, 1, 65634306, NULL, 3, 1),
       (9013, 8, 65961984, 9011, 3, 1),
       (9014, 8, 65961985, 9012, 3, 1),
       (9015, 11, 66256896, NULL, 3, 1),
       (9016, 8, 66420736, 1, 3, 1),
       (9017, 11, 66584576, NULL, 3, 1),
       (9018, 11, 66584577, NULL, 3, 1),
       (9019, 11, 1111, NULL, 3, 1),
       (9020, 11, 66945024, NULL, 3, 1),
       (9021,11,66945025,NULL,3,1),
       (9022,11,66945026,NULL,3,1);
/*!40000 ALTER TABLE `acl_object_identity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_sid`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `acl_sid`
(
    `id`        bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `principal` tinyint(1)          NOT NULL,
    `sid`       varchar(100)        NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_acl_sid` (`sid`, `principal`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_sid`
--

LOCK TABLES `acl_sid` WRITE;
/*!40000 ALTER TABLE `acl_sid`
    DISABLE KEYS */;
INSERT IGNORE INTO `acl_sid` (`id`, `principal`, `sid`)
VALUES (1, 1, 'anonymousUser'),
       (2, 1, 'canano_admin'),
       (3, 1, 'canano_curator'),
       (4, 1, 'canano_guest'),
       (5, 1, 'canano_res1'),
       (6, 1, 'canano_res2'),
       (7, 1, 'guest1'),
       (8, 1, 'guest2'),
       (9, 1, 'guest3'),
       (10, 1, 'guest4'),
       (11, 1, 'guest5'),
       (15, 0, 'ROLE_ADMIN'),
       (12, 0, 'ROLE_ANONYMOUS'),
       (13, 0, 'ROLE_CURATOR'),
       (14, 0, 'ROLE_RESEARCHER');
/*!40000 ALTER TABLE `acl_sid`
    ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `users`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `users`
(
    `username`     varchar(100) NOT NULL,
    `password`     varchar(100) NOT NULL,
    `first_name`   varchar(100) NOT NULL,
    `last_name`    varchar(100) NOT NULL,
    `organization` varchar(500) DEFAULT NULL,
    `department`   varchar(100) DEFAULT NULL,
    `title`        varchar(100) DEFAULT NULL,
    `phone_number` varchar(100) DEFAULT NULL,
    `email_id`     varchar(100) DEFAULT NULL,
    `enabled`      varchar(1)   DEFAULT NULL,
    PRIMARY KEY (`username`),
    UNIQUE KEY `id_users_UNIQUE` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT IGNORE INTO `users` (`username`, `password`, `first_name`, `last_name`, `organization`, `department`, `title`,
                            `phone_number`, `email_id`, `enabled`)
VALUES ('canano_admin', '$2a$10$c5XRfWd.OcuIEG3clJntF.EKrF8RBB4vhKStkVirxpqC9B0ryyakS', 'caNanoLab', 'Guest', '', '',
        '', '', '', '1'),
       ('canano_curator', '$2a$10$YS6y4fhLRH4uGq7mo2mjBOEu1Lsf3O90DUpnnxChBtcLE8Co0ICqq', 'caNanoLab', 'Guest', '', '',
        '', '', '', '1'),
       ('canano_guest', '$2a$10$2uNpjCXSrSFL2HhSHlsvhu2aFFj69/aCiHJ5Ka6KHn/2shNpIC2dW', 'caNanoLab', 'Guest', '', '',
        '', '', '', '1'),
       ('canano_res1', '$2a$10$4zZmhs.mF.b7LrjkpZqCFuANJt1UNpOeI8feAJIRwiN03YWlIRedm', 'caNanoLab', 'Guest', '', '', '',
        '', '', '1'),
       ('canano_res2', '$2a$10$MG1GG3rYlHFsB/fVnNOG3.c6N4x7sa9cCdcWaPg2TuRuFtBOhkDba', 'caNanoLab', 'Guest', '', '', '',
        '', '', '1'),
       ('guest1', '$2a$10$k/2Zg9sTcZvzMqUco1JI5O9qyfyA0QeQfgEYTvNAH8fFaaKLHFBFW', 'caNanoLab', 'Guest', '', '', '', '',
        '', '1'),
       ('guest2', '$2a$10$efSvHYCMRUf3rBxiGCz.5uX8X80g.VtzGPe84p6OiD7ZKbCXhCz0m', 'caNanoLab', 'Guest', '', '', '', '',
        '', '1'),
       ('guest3', '$2a$10$CGQ4cs.bzV5i6GSzn8wF3uCP2sdL5sIZsPlXAG5uW4Fl0GUrhkq5.', 'caNanoLab', 'Guest', '', '', '', '',
        '', '1'),
       ('guest4', '$2a$10$bbf0gBxkz6sZ85VRDTG.XOMTUmrKX6qNHhsj2C4wK.B/N9p5jhiWK', 'caNanoLab', 'Guest', '', '', '', '',
        '', '1'),
       ('guest5', '$2a$10$avCA1Z0vSJvGPWpuiNCnUu0iQDR1muwacsOttckeCWQ7vOilOnK.2', 'caNanoLab', 'Guest', '', '', '', '',
        '', '1');
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `authorities`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `authorities`
(
    `username`  varchar(100) NOT NULL,
    `authority` varchar(100) NOT NULL,
    UNIQUE KEY `ix_auth_username` (`username`, `authority`),
    CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities`
    DISABLE KEYS */;
INSERT IGNORE INTO `authorities` (`username`, `authority`)
VALUES ('canano_admin', 'ROLE_ADMIN'),
       ('canano_curator', 'ROLE_CURATOR'),
       ('canano_guest', 'ROLE_ANONYMOUS'),
       ('canano_res1', 'ROLE_RESEARCHER'),
       ('canano_res2', 'ROLE_RESEARCHER'),
       ('guest1', 'ROLE_RESEARCHER'),
       ('guest2', 'ROLE_RESEARCHER'),
       ('guest3', 'ROLE_RESEARCHER'),
       ('guest4', 'ROLE_RESEARCHER'),
       ('guest5', 'ROLE_RESEARCHER');
/*!40000 ALTER TABLE `authorities`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activation_method`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `activation_method`
(
    `activation_method_pk_id` bigint(20)   NOT NULL,
    `type`                    varchar(200) NOT NULL,
    `activation_effect`       text,
    PRIMARY KEY (`activation_method_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activation_method`
--

LOCK TABLES `activation_method` WRITE;
/*!40000 ALTER TABLE `activation_method`
    DISABLE KEYS */;
INSERT IGNORE INTO `activation_method` (`activation_method_pk_id`, `type`, `activation_effect`)
VALUES (65765376, 'infrared', 'glows');
/*!40000 ALTER TABLE `activation_method`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administration`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `administration`
(
    `administration_id`  bigint(20)   NOT NULL,
    `site_name`          varchar(200) DEFAULT NULL,
    `site_logo`          varchar(200) DEFAULT NULL,
    `visitor_count`      bigint(20)   DEFAULT NULL,
    `counter_start_date` datetime     NOT NULL,
    `created_by`         varchar(200) NOT NULL,
    `created_date`       datetime     NOT NULL,
    `updated_by`         varchar(200) DEFAULT NULL,
    `updated_date`       datetime     DEFAULT NULL,
    PRIMARY KEY (`administration_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administration`
--

LOCK TABLES `administration` WRITE;
/*!40000 ALTER TABLE `administration`
    DISABLE KEYS */;
INSERT IGNORE INTO `administration` (`administration_id`, `site_name`, `site_logo`, `visitor_count`,
                                     `counter_start_date`, `created_by`, `created_date`, `updated_by`, `updated_date`)
VALUES (0, NULL, NULL, 509182, '2010-06-03 08:28:27', 'admin', '2010-06-03 08:28:27', NULL, NULL);
/*!40000 ALTER TABLE `administration`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `antibody`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `antibody`
(
    `antibody_pk_id` bigint(20) NOT NULL,
    `species`        varchar(200) DEFAULT NULL,
    `type`           varchar(200) DEFAULT NULL,
    `isotype`        varchar(200) DEFAULT NULL,
    PRIMARY KEY (`antibody_pk_id`),
    UNIQUE KEY `antibody_pk_id` (`antibody_pk_id`),
    KEY `antibody_pk_id_2` (`antibody_pk_id`),
    CONSTRAINT `FK_antibody_functionalizing_entity` FOREIGN KEY (`antibody_pk_id`) REFERENCES `functionalizing_entity` (`functionalizing_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antibody`
--

LOCK TABLES `antibody` WRITE;
/*!40000 ALTER TABLE `antibody`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `antibody`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `associated_element`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `associated_element`
(
    `associated_element_pk_id` bigint(20)   NOT NULL,
    `molecular_formula`        varchar(2000)  DEFAULT NULL,
    `molecular_formula_type`   varchar(200)   DEFAULT NULL,
    `description`              text,
    `created_by`               varchar(200) NOT NULL,
    `created_date`             datetime     NOT NULL,
    `name`                     varchar(200)   DEFAULT NULL,
    `value`                    decimal(22, 3) DEFAULT NULL,
    `value_unit`               varchar(200)   DEFAULT NULL,
    `pub_chem_datasource_name` varchar(200)   DEFAULT NULL,
    `pub_chem_id`              bigint(20)     DEFAULT NULL,
    PRIMARY KEY (`associated_element_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `associated_element`
--

LOCK TABLES `associated_element` WRITE;
/*!40000 ALTER TABLE `associated_element`
    DISABLE KEYS */;
INSERT IGNORE INTO `associated_element` (`associated_element_pk_id`, `molecular_formula`, `molecular_formula_type`,
                                         `description`, `created_by`, `created_date`, `name`, `value`, `value_unit`,
                                         `pub_chem_datasource_name`, `pub_chem_id`)
VALUES (65732608, '', '', 'Test CE', 'canano_curator', '2020-01-08 19:11:45', 'Fred', 12.000, 'mg', '', NULL),
       (65732609, '', '', 'Test FE', 'canano_curator', '2020-01-08 19:13:24', 'Foo88', 87.000, 'g', '', NULL),
       (65732610, '', '', 'test', 'canano_curator', '2020-01-08 19:14:52', 'Varbonder', 987.000, 'uCi/mg', '', NULL),
       (65732611, 'H03', 'Hill', 'Test RNA', 'canano_curator', '2020-01-08 19:16:46', 'Ribo Test', NULL, '', '', NULL),
       (65732612, 'smiling', 'SMILES', 'Test lipid', 'canano_curator', '2020-01-08 19:21:14', 'Lipid test', NULL, '',
        '', NULL),
       (65732613, '', '', 'Test modifier', 'canano_curator', '2020-01-08 19:21:56', 'Modifier Test', NULL, '', '',
        NULL),
       (66682880, '', '', '', 'canano_curator', '2020-01-10 20:19:50', 'Iron', 8.000, 'g', '', NULL);
/*!40000 ALTER TABLE `associated_element`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `author`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `author`
(
    `author_pk_id` bigint(20)   NOT NULL,
    `first_name`   varchar(200) NOT NULL,
    `last_name`    varchar(200) NOT NULL,
    `initial`      varchar(10) DEFAULT NULL,
    `created_by`   varchar(200) NOT NULL,
    `created_date` datetime     NOT NULL,
    PRIMARY KEY (`author_pk_id`),
    UNIQUE KEY `author_pk_id` (`author_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `author`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `author_publication`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `author_publication`
(
    `author_pk_id`      bigint(20) NOT NULL,
    `publication_pk_id` bigint(20) NOT NULL,
    PRIMARY KEY (`author_pk_id`, `publication_pk_id`),
    KEY `author_pk_id` (`author_pk_id`),
    KEY `publication_pk_id` (`publication_pk_id`),
    CONSTRAINT `FK_author_publication_author` FOREIGN KEY (`author_pk_id`) REFERENCES `author` (`author_pk_id`),
    CONSTRAINT `FK_author_publication_publication` FOREIGN KEY (`publication_pk_id`) REFERENCES `publication` (`publication_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author_publication`
--

LOCK TABLES `author_publication` WRITE;
/*!40000 ALTER TABLE `author_publication`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `author_publication`
    ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `biopolymer_f`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `biopolymer_f`
(
    `biopolymer_pk_id` bigint(20)  NOT NULL,
    `type`             varchar(50) NOT NULL,
    `sequence`         text,
    PRIMARY KEY (`biopolymer_pk_id`),
    UNIQUE KEY `biopolymer_pk_id` (`biopolymer_pk_id`),
    KEY `biopolymer_pk_id_2` (`biopolymer_pk_id`),
    CONSTRAINT `FK_biopolymer_f_functionalizing_entity` FOREIGN KEY (`biopolymer_pk_id`) REFERENCES `functionalizing_entity` (`functionalizing_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biopolymer_f`
--

LOCK TABLES `biopolymer_f` WRITE;
/*!40000 ALTER TABLE `biopolymer_f`
    DISABLE KEYS */;
INSERT IGNORE INTO `biopolymer_f` (`biopolymer_pk_id`, `type`, `sequence`)
VALUES (65732609, 'polysaccharide', 'Biotest');
/*!40000 ALTER TABLE `biopolymer_f`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `biopolymer_p`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `biopolymer_p`
(
    `biopolymer_pk_id` bigint(20)  NOT NULL,
    `type`             varchar(50) NOT NULL,
    `sequence`         text,
    `name`             varchar(200) DEFAULT NULL,
    PRIMARY KEY (`biopolymer_pk_id`),
    KEY `biopolymer_pk_id` (`biopolymer_pk_id`),
    CONSTRAINT `FK_biopolymer_p_nanomaterial_entity` FOREIGN KEY (`biopolymer_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biopolymer_p`
--

LOCK TABLES `biopolymer_p` WRITE;
/*!40000 ALTER TABLE `biopolymer_p`
    DISABLE KEYS */;
INSERT IGNORE INTO `biopolymer_p` (`biopolymer_pk_id`, `type`, `sequence`, `name`)
VALUES (65667072, 'peptide', 'A1B2C3D4', 'BioSynthJan8');
/*!40000 ALTER TABLE `biopolymer_p`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carbon_nanotube`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `carbon_nanotube`
(
    `carbon_nanotube_pk_id` bigint(20) NOT NULL,
    `chirality`             varchar(100)   DEFAULT NULL,
    `diameter`              decimal(22, 3) DEFAULT NULL,
    `diameter_unit`         varchar(200)   DEFAULT NULL,
    `average_length`        decimal(22, 3) DEFAULT NULL,
    `average_length_unit`   varchar(200)   DEFAULT NULL,
    `wall_type`             varchar(100)   DEFAULT NULL,
    PRIMARY KEY (`carbon_nanotube_pk_id`),
    KEY `carbon_nanotube_pk_id` (`carbon_nanotube_pk_id`),
    CONSTRAINT `FK_carbon_nanotube_nanomaterial_entity` FOREIGN KEY (`carbon_nanotube_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carbon_nanotube`
--

LOCK TABLES `carbon_nanotube` WRITE;
/*!40000 ALTER TABLE `carbon_nanotube`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `carbon_nanotube`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `characterization`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `characterization`
(
    `characterization_pk_id`    bigint(20)   NOT NULL,
    `design_method_description` text,
    `created_date`              datetime     NOT NULL,
    `created_by`                varchar(200) NOT NULL,
    `protocol_pk_id`            bigint(20)   DEFAULT NULL,
    `sample_pk_id`              bigint(20)   DEFAULT NULL,
    `discriminator`             varchar(50)  NOT NULL,
    `cytotoxicity_cell_line`    text,
    `surface_is_hydrophobic`    tinyint(4)   DEFAULT NULL,
    `characterization_date`     datetime     DEFAULT NULL,
    `poc_pk_id`                 bigint(20)   DEFAULT NULL,
    `transfection_cell_line`    text,
    `analysis_conclusion`       text,
    `enzyme_induction_enzyme`   varchar(200) DEFAULT NULL,
    `other_char_assay_category` varchar(200) DEFAULT NULL,
    `other_char_name`           varchar(200) DEFAULT NULL,
    `assay_type`                varchar(200) DEFAULT NULL,
    `targeting_cell_line`       text,
    PRIMARY KEY (`characterization_pk_id`),
    KEY `particle_sample_pk_id` (`sample_pk_id`),
    KEY `protocol_file_pk_id` (`protocol_pk_id`),
    CONSTRAINT `FK_characterization_protocol` FOREIGN KEY (`protocol_pk_id`) REFERENCES `protocol` (`protocol_pk_id`),
    CONSTRAINT `FK_characterization_sample` FOREIGN KEY (`sample_pk_id`) REFERENCES `sample` (`sample_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `characterization`
--

LOCK TABLES `characterization` WRITE;
/*!40000 ALTER TABLE `characterization`
    DISABLE KEYS */;
INSERT IGNORE INTO `characterization` (`characterization_pk_id`, `design_method_description`, `created_date`,
                                       `created_by`, `protocol_pk_id`, `sample_pk_id`, `discriminator`,
                                       `cytotoxicity_cell_line`, `surface_is_hydrophobic`, `characterization_date`,
                                       `poc_pk_id`, `transfection_cell_line`, `analysis_conclusion`,
                                       `enzyme_induction_enzyme`, `other_char_assay_category`, `other_char_name`,
                                       `assay_type`, `targeting_cell_line`)
VALUES (65961984, 'This is a method', '2020-01-08 19:18:01', 'canano_curator', 66584577, 65634305, 'MetabolicStability',
        NULL, NULL, '2020-01-08 00:00:00', 65568768, NULL, NULL, NULL, NULL, NULL, 'SynAssay', NULL),
       (65961985, 'Test description', '2020-01-08 19:22:53', 'canano_curator', 66256896, 65634306, 'Toxicology', NULL,
        NULL, '2020-01-08 00:00:00', 65568768, NULL, 'Conclusion Test', NULL, NULL, NULL, 'toxicity', NULL),
       (66420736, 'Test for Syn characterization', '2020-01-10 13:26:14', 'canano_curator', 66256896, 1000, 'Sterility',
        NULL, NULL, '2020-01-10 00:00:00', 1000, NULL, NULL, NULL, NULL, NULL, 'endotoxin', NULL);
/*!40000 ALTER TABLE `characterization`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chemical_association`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `chemical_association`
(
    `chemical_association_pk_id`      bigint(20)   NOT NULL,
    `composition_pk_id`               bigint(20)   DEFAULT NULL,
    `associated_element_a_pk_id`      bigint(20)   NOT NULL,
    `associated_element_b_pk_id`      bigint(20)   NOT NULL,
    `description`                     text,
    `created_by`                      varchar(200) NOT NULL,
    `created_date`                    datetime     NOT NULL,
    `discriminator`                   varchar(200) DEFAULT NULL,
    `attachment_bond_type`            varchar(200) DEFAULT NULL,
    `other_chemical_association_type` varchar(200) DEFAULT NULL,
    PRIMARY KEY (`chemical_association_pk_id`),
    KEY `associated_element_a_pk_id` (`associated_element_a_pk_id`),
    KEY `associated_element_b_pk_id` (`associated_element_b_pk_id`),
    KEY `composition_pk_id` (`composition_pk_id`),
    CONSTRAINT `FK_chemical_association_associated_element_a` FOREIGN KEY (`associated_element_a_pk_id`) REFERENCES `associated_element` (`associated_element_pk_id`),
    CONSTRAINT `FK_chemical_association_associated_element_b` FOREIGN KEY (`associated_element_b_pk_id`) REFERENCES `associated_element` (`associated_element_pk_id`),
    CONSTRAINT `fk_ca_entry_comp` FOREIGN KEY (`composition_pk_id`) REFERENCES `composition` (`composition_pk_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chemical_association`
--

LOCK TABLES `chemical_association` WRITE;
/*!40000 ALTER TABLE `chemical_association`
    DISABLE KEYS */;
INSERT IGNORE INTO `chemical_association` (`chemical_association_pk_id`, `composition_pk_id`,
                                           `associated_element_a_pk_id`, `associated_element_b_pk_id`, `description`,
                                           `created_by`, `created_date`, `discriminator`, `attachment_bond_type`,
                                           `other_chemical_association_type`)
VALUES (65830912, 65699841, 65732610, 65732609, 'Linking', 'canano_curator', '2020-01-08 19:15:32', 'Attachment',
        'electrostatic', NULL);
/*!40000 ALTER TABLE `chemical_association`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chemical_association_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `chemical_association_file`
(
    `chemical_association_pk_id` bigint(20) NOT NULL,
    `file_pk_id`                 bigint(20) NOT NULL,
    PRIMARY KEY (`chemical_association_pk_id`, `file_pk_id`),
    KEY `chemical_association_pk_id` (`chemical_association_pk_id`),
    KEY `file_pk_id` (`file_pk_id`),
    CONSTRAINT `FK_chemical_association_file_chemical_association` FOREIGN KEY (`chemical_association_pk_id`) REFERENCES `chemical_association` (`chemical_association_pk_id`),
    CONSTRAINT `FK_chemical_association_file_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chemical_association_file`
--

LOCK TABLES `chemical_association_file` WRITE;
/*!40000 ALTER TABLE `chemical_association_file`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `chemical_association_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `common_lookup`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `common_lookup`
(
    `common_lookup_pk_id` bigint(20)   NOT NULL,
    `name`                varchar(200) NOT NULL,
    `attribute`           varchar(200) NOT NULL,
    `value`               varchar(200) NOT NULL,
    PRIMARY KEY (`common_lookup_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_lookup`
--

LOCK TABLES `common_lookup` WRITE;
/*!40000 ALTER TABLE `common_lookup`
    DISABLE KEYS */;
INSERT IGNORE INTO `common_lookup` (`common_lookup_pk_id`, `name`, `attribute`, `value`)
VALUES (1, 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering', 'abbreviation',
        'AFFF-MALLS'),
       (2, 'atomic force microscopy', 'abbreviation', 'AFM'),
       (3, 'confocal laser scanning microscopy', 'abbreviation', 'CLSM'),
       (4, 'dynamic light scattering', 'abbreviation', 'DLS'),
       (5, 'electron microprobe analysis', 'abbreviation', 'EMPA'),
       (6, 'energy dispersive spectroscopy', 'abbreviation', 'EDS'),
       (7, 'environmental transmission electron microscopy', 'abbreviation', 'ETEM'),
       (8, 'fast protein liquid chromatography', 'abbreviation', 'FPLC'),
       (9, 'focused ion beam - scanning electron microscopy', 'abbreviation', 'FIB-SEM'),
       (10, 'gel filtration chromatography', 'abbreviation', 'GFC'),
       (11, 'high performance liquid chromatography', 'abbreviation', 'HPLC'),
       (12, 'high resolution scanning electron microscopy', 'abbreviation', 'HR-SEM'),
       (13, 'high resolution transmission electron microscopy', 'abbreviation', 'HRTEM'),
       (14, 'matrix assisted laser desorption ionisation - time of flight', 'abbreviation', 'MALDI-TOF'),
       (15, 'multi-angle laser light scattering', 'abbreviation', 'MALLS'),
       (16, 'polymerase chain reaction', 'abbreviation', 'PCR'),
       (17, 'scanning auger spectrometry', 'abbreviation', 'SAM'),
       (18, 'scanning electron microscopy', 'abbreviation', 'SEM'),
       (19, 'scanning probe microscopy ', 'abbreviation', 'SPM'),
       (20, 'scanning tunneling  microscopy', 'abbreviation', 'STM'),
       (21, 'size exclusion chromatography with multi-angle laser light scattering', 'abbreviation', 'SEC-MALLS'),
       (22, 'transmission electron microscopy', 'abbreviation', 'TEM'),
       (23, 'X-ray photoelectron spectroscopy', 'abbreviation', 'XPS'),
       (24, 'blood contact', 'assayType', 'coagulation'),
       (25, 'blood contact', 'assayType', 'complement activation'),
       (26, 'blood contact', 'assayType', 'hemolysis'),
       (27, 'blood contact', 'assayType', 'plasma protein binding'),
       (28, 'blood contact', 'assayType', 'platelet aggregation'),
       (29, 'cytotoxicity', 'assayType', 'caspase 3 apoptosis'),
       (30, 'cytotoxicity', 'assayType', 'cell viability'),
       (31, 'cytotoxicity', 'assayType', 'gene expression'),
       (32, 'cytotoxicity', 'assayType', 'mitochondrial function'),
       (33, 'cytotoxicity', 'assayType', 'mitochondrial membrane potential'),
       (34, 'cytotoxicity', 'assayType', 'proliferation'),
       (35, 'immune cell function', 'assayType', 'CFU-GM'),
       (36, 'immune cell function', 'assayType', 'chemotaxis'),
       (37, 'immune cell function', 'assayType', 'cytokine induction'),
       (38, 'immune cell function', 'assayType', 'cytotoxic activity of nk cells'),
       (39, 'immune cell function', 'assayType', 'leukocyte proliferation'),
       (40, 'immune cell function', 'assayType', 'oxidative burst'),
       (41, 'immune cell function', 'assayType', 'phagocytosis'),
       (42, 'oxidative stress', 'assayType', 'gsh homeostasis'),
       (43, 'oxidative stress', 'assayType', 'lipid peroxidation'),
       (44, 'oxidative stress', 'assayType', 'ros generation'),
       (45, 'sterility', 'assayType', 'bacterial/yeast/mold'),
       (46, 'sterility', 'assayType', 'endotoxin'),
       (47, 'sterility', 'assayType', 'mycoplasma'),
       (48, 'targeting', 'assayType', 'cell binding/internalization'),
       (49, 'targeting', 'assayType', 'gene expression'),
       (50, 'attachment', 'bondType', 'covalent'),
       (51, 'attachment', 'bondType', 'electrostatic'),
       (52, 'attachment', 'bondType', 'hydrogen'),
       (53, 'attachment', 'bondType', 'ionic'),
       (54, 'attachment', 'bondType', 'van der Waals'),
       (55, 'publication', 'category', 'book chapter'),
       (56, 'publication', 'category', 'editorial'),
       (57, 'publication', 'category', 'peer review article'),
       (58, 'publication', 'category', 'proceeding'),
       (59, 'publication', 'category', 'report'),
       (60, 'publication', 'category', 'review'),
       (61, 'emulsion', 'composingElementType', 'bulk phase'),
       (62, 'emulsion', 'composingElementType', 'dispersed phase'),
       (63, 'emulsion', 'composingElementType', 'emulsifier'),
       (64, 'caspase 3 apoptosis', 'datumName', '% of control'),
       (65, 'caspase 3 apoptosis', 'datumName', 'fluorescence'),
       (66, 'cell binding/internalization', 'datumName', 'cellular fluorescence'),
       (67, 'cell viability', 'datumName', 'LC50'),
       (68, 'CFU-GM', 'datumName', 'number of CFU-GM colonies'),
       (69, 'CFU-GM', 'datumName', 'total number of bone marrow cells'),
       (70, 'chemotaxis', 'datumName', '% of control'),
       (71, 'coagulation', 'datumName', 'activated partial thromboplastin time (APTT)'),
       (72, 'coagulation', 'datumName', 'prothrombin time (PT)'),
       (73, 'coagulation', 'datumName', 'reptilase time'),
       (74, 'coagulation', 'datumName', 'thrombin time'),
       (75, 'complement activation', 'datumName', 'is complement activation induced?'),
       (76, 'cytokine induction', 'datumName', 'IL1 Beta'),
       (77, 'cytokine induction', 'datumName', 'IL10'),
       (78, 'cytokine induction', 'datumName', 'IL6'),
       (79, 'cytokine induction', 'datumName', 'IL8'),
       (80, 'cytokine induction', 'datumName', 'TNF Alpha'),
       (81, 'cytotoxic activity of nk cells', 'datumName', '% of control'),
       (82, 'enzyme induction', 'datumName', '% of control'),
       (83, 'gene expression', 'datumName', 'cellular fluorescence'),
       (84, 'gene expression', 'datumName', 'fluorescence ratio'),
       (85, 'gsh homeostasis', 'datumName', '% of control'),
       (86, 'hemolysis', 'datumName', 'is hemolytic?'),
       (87, 'leukocyte proliferation', 'datumName', '% of control'),
       (88, 'lipid peroxidation', 'datumName', '% of control'),
       (89, 'mitochondrial function', 'datumName', 'luminescence'),
       (90, 'mitochondrial membrane potential', 'datumName', 'fluorescence ratio'),
       (91, 'mitochondrial membrane potential', 'datumName', 'ratio of red to green fluorescence'),
       (92, 'molecular weight', 'datumName', 'molecular weight'),
       (93, 'oxidative burst', 'datumName', '% of control'),
       (94, 'phagocytosis', 'datumName', 'fold change vs. control'),
       (95, 'plasma protein binding', 'datumName', 'peptide bound'),
       (96, 'plasma protein binding', 'datumName', 'protein bound'),
       (97, 'platelet aggregation', 'datumName', '% of aggregation vs. control'),
       (98, 'platelet aggregation', 'datumName', '% of collagen induced aggregation vs. control'),
       (99, 'platelet aggregation', 'datumName', 'is above threshold?'),
       (100, 'proliferation', 'datumName', '% of control'),
       (101, 'proliferation', 'datumName', 'ratio of cell line1 to cell line 2'),
       (102, 'purity', 'datumName', '% purity for sample'),
       (103, 'relaxivity', 'datumName', 'R1'),
       (104, 'relaxivity', 'datumName', 'R2'),
       (105, 'relaxivity', 'datumName', 'T1'),
       (106, 'relaxivity', 'datumName', 'T2'),
       (107, 'ros generation', 'datumName', '% of control'),
       (108, 'size', 'datumName', 'PDI'),
       (109, 'size', 'datumName', 'size'),
       (110, 'surface', 'datumName', 'charge'),
       (111, 'surface', 'datumName', 'surface area'),
       (112, 'surface', 'datumName', 'zeta potential'),
       (113, 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering', 'instrument',
        'control module'),
       (114, 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering', 'instrument',
        'photometer'),
       (115, 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering', 'instrument',
        'refractometer'),
       (116, 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering', 'instrument',
        'separation column'),
       (117, 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering', 'instrument',
        'spectrophotometer'),
       (118, 'atomic force microscopy', 'instrument', 'atomic force microscope'),
       (119, 'capillary electrophoresis', 'instrument', 'capillary electrophoresis instrument'),
       (120, 'cell counting', 'instrument', 'coulter counter'),
       (121, 'cell counting', 'instrument', 'hemocytometer'),
       (122, 'coagulation detection', 'instrument', 'coagulation monitor'),
       (123, 'colony counting', 'instrument', 'automated colony counter'),
       (124, 'confocal laser scanning microscopy', 'instrument', 'confocal microscope system'),
       (125, 'dynamic light scattering', 'instrument', 'dynamic light scattering instrument'),
       (126, 'electron microprobe analysis', 'instrument', 'electron microprobe'),
       (127, 'electrophoretic light scattering', 'instrument', 'electrophoretic light scattering instrument'),
       (128, 'energy dispersive spectroscopy', 'instrument', 'energy dispersive spectrometer'),
       (129, 'environmental transmission electron microscopy', 'instrument',
        'environmental transmission electron microscope'),
       (130, 'fast protein liquid chromatography', 'instrument', 'control module'),
       (131, 'fast protein liquid chromatography', 'instrument', 'fraction collector'),
       (132, 'fast protein liquid chromatography', 'instrument', 'separation column'),
       (133, 'fast protein liquid chromatography', 'instrument', 'spectrophotometer'),
       (134, 'flow cytometry', 'instrument', 'flow cytometer'),
       (135, 'focused ion beam - scanning electron microscopy', 'instrument', 'scanning electron microscope'),
       (136, 'gas sorption', 'instrument', 'gas sorption detector'),
       (137, 'gel filtration chromatography', 'instrument', 'gel filtration column'),
       (138, 'high performance liquid chromatography', 'instrument', 'control module'),
       (139, 'high performance liquid chromatography', 'instrument', 'fraction collector'),
       (140, 'high performance liquid chromatography', 'instrument', 'separation column'),
       (141, 'high performance liquid chromatography', 'instrument', 'spectrophotometer'),
       (142, 'high resolution scanning electron microscopy', 'instrument', 'scanning electron microscope'),
       (143, 'high resolution transmission electron microscopy', 'instrument', 'transmission electron microscope'),
       (144, 'imaging', 'instrument', 'imaging system'),
       (145, 'mass quantitation', 'instrument', 'analytical balance'),
       (146, 'matrix assisted laser desorption ionisation - time of flight', 'instrument',
        'maldi-tof mass spectrometer'),
       (147, 'multi-angle laser light scattering', 'instrument', 'refractometer'),
       (148, 'multi-angle laser light scattering', 'instrument', 'spectrophotometer'),
       (149, 'particle quantitation', 'instrument', 'coulter counter'),
       (150, 'particle quantitation', 'instrument', 'hemocytometer'),
       (151, 'polymerase chain reaction', 'instrument', 'thermal cycler'),
       (152, 'powder diffraction', 'instrument', 'powder diffractometer'),
       (153, 'radioactivity quantiation', 'instrument', 'scintillation counter'),
       (154, 'refractometry', 'instrument', 'refractometer'),
       (155, 'scanning auger spectrometry', 'instrument', 'scanning auger spectrometer'),
       (156, 'scanning electron microscopy', 'instrument', 'scanning electron microscope'),
       (157, 'scanning probe microscopy', 'instrument', 'scanning probe microscope'),
       (158, 'scanning tunneling  microscopy', 'instrument', 'scanning tunneling microscope'),
       (159, 'size exclusion chromatography with multi-angle laser light scattering', 'instrument', 'control module'),
       (160, 'size exclusion chromatography with multi-angle laser light scattering', 'instrument', 'photometer'),
       (161, 'size exclusion chromatography with multi-angle laser light scattering', 'instrument', 'refractometer'),
       (162, 'size exclusion chromatography with multi-angle laser light scattering', 'instrument',
        'separation column'),
       (163, 'size exclusion chromatography with multi-angle laser light scattering', 'instrument',
        'spectrophotometer'),
       (164, 'spectrophotometry', 'instrument', 'spectrophotometer'),
       (165, 'transmission electron microscopy', 'instrument', 'transmission electron microscope'),
       (166, 'X-ray photoelectron spectroscopy', 'instrument', 'X-ray photoelectron spectrometer'),
       (167, 'zeta potential analysis', 'instrument', 'zeta potential analyzer'),
       (168, 'antibody', 'isotype', 'IgA'),
       (169, 'antibody', 'isotype', 'IgD'),
       (170, 'antibody', 'isotype', 'IgE'),
       (171, 'antibody', 'isotype', 'IgG'),
       (172, 'antibody', 'isotype', 'IgM'),
       (173, 'instrument', 'manufacturer', 'Agilent'),
       (174, 'instrument', 'manufacturer', 'Amersham'),
       (175, 'instrument', 'manufacturer', 'Beckman/Coulter'),
       (176, 'instrument', 'manufacturer', 'Becton Dickinson'),
       (177, 'instrument', 'manufacturer', 'BioLogics'),
       (178, 'instrument', 'manufacturer', 'Biorad'),
       (179, 'instrument', 'manufacturer', 'BioTek'),
       (180, 'instrument', 'manufacturer', 'Brookhaven Instruments'),
       (181, 'instrument', 'manufacturer', 'Carl Zeiss'),
       (182, 'instrument', 'manufacturer', 'Diagnostica Stago'),
       (183, 'instrument', 'manufacturer', 'EDAX'),
       (184, 'instrument', 'manufacturer', 'Hitachi'),
       (185, 'instrument', 'manufacturer', 'JEOL'),
       (186, 'instrument', 'manufacturer', 'Kodak'),
       (187, 'instrument', 'manufacturer', 'Malvern'),
       (188, 'instrument', 'manufacturer', 'Micromeritics'),
       (189, 'instrument', 'manufacturer', 'Molecular Devices'),
       (190, 'instrument', 'manufacturer', 'Molecular Imaging'),
       (191, 'instrument', 'manufacturer', 'Philips'),
       (192, 'instrument', 'manufacturer', 'Quantachrome Instruments'),
       (193, 'instrument', 'manufacturer', 'Shimadzu'),
       (194, 'instrument', 'manufacturer', 'Tecan'),
       (195, 'instrument', 'manufacturer', 'Thermo Electron'),
       (196, 'instrument', 'manufacturer', 'Waters'),
       (197, 'instrument', 'manufacturer', 'Wyatt Technologies'),
       (198, 'imaging function', 'modality', 'bioluminescence'),
       (199, 'imaging function', 'modality', 'fluorescence'),
       (200, 'imaging function', 'modality', 'infrared'),
       (201, 'imaging function', 'modality', 'MRI'),
       (202, 'imaging function', 'modality', 'neutron scattering'),
       (203, 'imaging function', 'modality', 'PET'),
       (204, 'imaging function', 'modality', 'Raman spectroscopy'),
       (205, 'imaging function', 'modality', 'SPECT'),
       (206, 'imaging function', 'modality', 'ultrasound'),
       (207, 'imaging function', 'modality', 'X-ray'),
       (208, 'condition', 'name', 'centrifugation'),
       (209, 'condition', 'name', 'culture media'),
       (210, 'condition', 'name', 'electromagnetic radiation'),
       (211, 'condition', 'name', 'freeze thaw'),
       (212, 'condition', 'name', 'long term storage'),
       (213, 'condition', 'name', 'lyophilization'),
       (214, 'condition', 'name', 'ph'),
       (215, 'condition', 'name', 'sample concentration'),
       (216, 'condition', 'name', 'short term storage'),
       (217, 'condition', 'name', 'solvent media'),
       (218, 'condition', 'name', 'sonication'),
       (219, 'condition', 'name', 'temperature'),
       (220, 'culture media', 'property', 'media type'),
       (221, 'culture media', 'property', 'serum percentage'),
       (222, 'electromagnetic radiation', 'property', 'bandwidth'),
       (223, 'electromagnetic radiation', 'property', 'frequency'),
       (224, 'electromagnetic radiation', 'property', 'time'),
       (225, 'electromagnetic radiation', 'property', 'wavelength'),
       (226, 'long term storage', 'property', 'lyophilized'),
       (227, 'long term storage', 'property', 'time'),
       (228, 'lyophilization', 'property', 'time'),
       (229, 'lyophilization', 'property', 'time'),
       (230, 'short term storage', 'property', 'lyophilized'),
       (231, 'short term storage', 'property', 'time'),
       (232, 'solvent media', 'property', 'ion concentration'),
       (233, 'solvent media', 'property', 'ionic strength'),
       (234, 'solvent media', 'property', 'molecular formula'),
       (235, 'solvent media', 'property', 'osmolality'),
       (236, 'solvent media', 'property', 'serum percentage'),
       (237, 'solvent media', 'property', 'with serum'),
       (238, 'sonication', 'property', 'number of pulses'),
       (239, 'sonication', 'property', 'pulse duration'),
       (240, 'publication', 'researchArea', 'animal'),
       (241, 'publication', 'researchArea', 'cell line'),
       (242, 'publication', 'researchArea', 'characterization'),
       (243, 'publication', 'researchArea', 'clinical trials'),
       (244, 'publication', 'researchArea', 'in vitro'),
       (245, 'publication', 'researchArea', 'in vivo'),
       (246, 'publication', 'researchArea', 'synthesis'),
       (247, 'point of contact', 'role', 'investigator'),
       (248, 'point of contact', 'role', 'manufacturer'),
       (249, 'solubility', 'solvent', 'alcohol'),
       (250, 'solubility', 'solvent', 'phospate-buffered saline'),
       (251, 'solubility', 'solvent', 'saline'),
       (252, 'solubility', 'solvent', 'water'),
       (253, 'publication', 'status', 'in preparation'),
       (254, 'publication', 'status', 'in press'),
       (255, 'publication', 'status', 'published'),
       (256, 'publication', 'status', 'submitted'),
       (257, 'activation method', 'type', 'enzymatic cleavage'),
       (258, 'activation method', 'type', 'infrared'),
       (259, 'activation method', 'type', 'MRI'),
       (260, 'activation method', 'type', 'NMR'),
       (261, 'activation method', 'type', 'pH'),
       (262, 'activation method', 'type', 'ultrasound'),
       (263, 'activation method', 'type', 'ultraviolet'),
       (264, 'antibody', 'type', 'Fab'),
       (265, 'antibody', 'type', 'ScFv'),
       (266, 'antibody', 'type', 'whole'),
       (267, 'biopolymer', 'type', 'DNA'),
       (268, 'biopolymer', 'type', 'peptide'),
       (269, 'biopolymer', 'type', 'protein'),
       (270, 'biopolymer', 'type', 'RNA'),
       (271, 'composing element', 'type', 'coat'),
       (272, 'composing element', 'type', 'core'),
       (273, 'composing element', 'type', 'lipid'),
       (274, 'composing element', 'type', 'modifier'),
       (275, 'composing element', 'type', 'monomer'),
       (276, 'composing element', 'type', 'repeat unit'),
       (277, 'composing element', 'type', 'shell'),
       (278, 'composing element', 'type', 'terminal group'),
       (279, 'file', 'type', 'document'),
       (280, 'file', 'type', 'graph'),
       (281, 'file', 'type', 'image'),
       (282, 'file', 'type', 'spread sheet'),
       (283, 'molecular formula', 'type', 'SMARTS'),
       (284, 'molecular formula', 'type', 'SMILES'),
       (285, 'physical state', 'type', 'colloid-emulsion'),
       (286, 'physical state', 'type', 'colloid-gel'),
       (287, 'physical state', 'type', 'colloid-sol'),
       (288, 'physical state', 'type', 'fluid-gas'),
       (289, 'physical state', 'type', 'fluid-liquid'),
       (290, 'physical state', 'type', 'fluid-vapor'),
       (291, 'physical state', 'type', 'solid-crystal'),
       (292, 'physical state', 'type', 'solid-fibril'),
       (293, 'physical state', 'type', 'solid-glass'),
       (294, 'physical state', 'type', 'solid-granule'),
       (295, 'physical state', 'type', 'solid-powder'),
       (296, 'protocol', 'type', 'in vitro assay'),
       (297, 'protocol', 'type', 'in vivo assay'),
       (298, 'protocol', 'type', 'physico-chemical assay'),
       (299, 'protocol', 'type', 'radio labeling'),
       (300, 'protocol', 'type', 'safety'),
       (301, 'protocol', 'type', 'sample preparation'),
       (302, 'protocol', 'type', 'synthesis'),
       (303, 'shape', 'type', '2D-circle'),
       (304, 'shape', 'type', '2D-diamond'),
       (305, 'shape', 'type', '2D-ellipse'),
       (306, 'shape', 'type', '2D-parallelogram'),
       (307, 'shape', 'type', '2D-polygon'),
       (308, 'shape', 'type', '2D-rectangle'),
       (309, 'shape', 'type', '2D-square'),
       (310, 'shape', 'type', '2D-trapezoid'),
       (311, 'shape', 'type', '2D-triangle'),
       (312, 'shape', 'type', '3D-cone'),
       (313, 'shape', 'type', '3D-cube'),
       (314, 'shape', 'type', '3D-cylinder'),
       (315, 'shape', 'type', '3D-disc'),
       (316, 'shape', 'type', '3D-fibril'),
       (317, 'shape', 'type', '3D-hexahedron'),
       (318, 'shape', 'type', '3D-needle'),
       (319, 'shape', 'type', '3D-oblate spheroid'),
       (320, 'shape', 'type', '3D-polyhedron'),
       (321, 'shape', 'type', '3D-prolate spheroid'),
       (322, 'shape', 'type', '3D-rod'),
       (323, 'shape', 'type', '3D-sphere'),
       (324, 'shape', 'type', '3D-tetrahedron'),
       (325, 'shape', 'type', '3D-tetrapod'),
       (326, 'species', 'type', 'cat'),
       (327, 'species', 'type', 'cattle'),
       (328, 'species', 'type', 'dog'),
       (329, 'species', 'type', 'goat'),
       (330, 'species', 'type', 'guinea pig'),
       (331, 'species', 'type', 'hamster'),
       (332, 'species', 'type', 'horse'),
       (333, 'species', 'type', 'human'),
       (334, 'species', 'type', 'mouse'),
       (335, 'species', 'type', 'pig'),
       (336, 'species', 'type', 'rat'),
       (337, 'species', 'type', 'sheep'),
       (338, 'species', 'type', 'yeast'),
       (339, 'species', 'type', 'zebrafish'),
       (340, 'technique', 'type', 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering'),
       (341, 'technique', 'type', 'atomic force microscopy'),
       (342, 'technique', 'type', 'capillary electrophoresis'),
       (343, 'technique', 'type', 'cell counting'),
       (344, 'technique', 'type', 'coagulation detection'),
       (345, 'technique', 'type', 'colony counting'),
       (346, 'technique', 'type', 'confocal laser scanning microscopy'),
       (347, 'technique', 'type', 'dynamic light scattering'),
       (348, 'technique', 'type', 'electron microprobe analysis'),
       (349, 'technique', 'type', 'electrophoretic light scattering'),
       (350, 'technique', 'type', 'energy dispersive spectroscopy'),
       (351, 'technique', 'type', 'environmental transmission electron microscopy'),
       (352, 'technique', 'type', 'fast protein liquid chromatography'),
       (353, 'technique', 'type', 'flow cytometry'),
       (354, 'technique', 'type', 'focused ion beam - scanning electron microscopy'),
       (355, 'technique', 'type', 'gas sorption'),
       (356, 'technique', 'type', 'gel filtration chromatography'),
       (357, 'technique', 'type', 'high performance liquid chromatography'),
       (358, 'technique', 'type', 'high resolution scanning electron microscopy'),
       (359, 'technique', 'type', 'high resolution transmission electron microscopy'),
       (360, 'technique', 'type', 'imaging'),
       (361, 'technique', 'type', 'mass quantitation'),
       (362, 'technique', 'type', 'matrix assisted laser desorption ionisation - time of flight'),
       (363, 'technique', 'type', 'particle quantitation'),
       (364, 'technique', 'type', 'polymerase chain reaction'),
       (365, 'technique', 'type', 'powder diffraction'),
       (366, 'technique', 'type', 'radioactivity quantiation'),
       (367, 'technique', 'type', 'refractometry'),
       (368, 'technique', 'type', 'scanning auger spectrometry'),
       (369, 'technique', 'type', 'scanning electron microscopy'),
       (370, 'technique', 'type', 'scanning probe microscopy'),
       (371, 'technique', 'type', 'scanning tunneling  microscopy'),
       (372, 'technique', 'type', 'size exclusion chromatography with multi-angle laser light scattering'),
       (373, 'technique', 'type', 'spectrophotometry'),
       (374, 'technique', 'type', 'transmission electron microscopy'),
       (375, 'technique', 'type', 'X-ray photoelectron spectroscopy'),
       (376, 'technique', 'type', 'zeta potential analysis'),
       (377, 'LC50', 'unit', 'mg/L'),
       (378, 'LC50', 'unit', 'nmol/L'),
       (379, 'bandwidth', 'unit', 'Hz'),
       (380, 'centrifugation', 'unit', 'g-Force'),
       (381, 'centrifugation', 'unit', 'RPM'),
       (382, 'charge', 'unit', 'a.u.'),
       (383, 'charge', 'unit', 'aC'),
       (384, 'charge', 'unit', 'Ah'),
       (385, 'charge', 'unit', 'C'),
       (386, 'charge', 'unit', 'esu'),
       (387, 'charge', 'unit', 'Fr'),
       (388, 'charge', 'unit', 'statC'),
       (389, 'dimension', 'unit', ' nm'),
       (390, 'freeze thaw', 'unit', 'number of cycles'),
       (391, 'frequency', 'unit', 'Hz'),
       (392, 'ion concentration', 'unit', 'mol/L'),
       (393, 'ionic strength', 'unit', 'mol/L'),
       (394, 'luminescence signal', 'unit', 'volt'),
       (395, 'molecular weight', 'unit', 'kDa'),
       (396, 'osmolality', 'unit', 'osmol/kg'),
       (397, 'power', 'unit', 'kilowatt'),
       (398, 'power', 'unit', 'watt'),
       (399, 'size', 'unit', 'nm'),
       (400, 'sample concentration', 'unit', 'g/mL'),
       (401, 'sample concentration', 'unit', 'mg/mL'),
       (402, 'sample concentration', 'unit', 'mg/mL'),
       (403, 'sample concentration', 'unit', 'ug/uL'),
       (404, 'surface area', 'unit', 'nm^2'),
       (405, 'temperature', 'unit', 'Celsius'),
       (406, 'temperature', 'unit', 'Fahrenheit'),
       (407, 'time', 'unit', 'day'),
       (408, 'time', 'unit', 'hour'),
       (409, 'time', 'unit', 'minute'),
       (410, 'time', 'unit', 'second'),
       (411, 'wavelength', 'unit', 'nm'),
       (412, 'zeta potential', 'unit', 'mV'),
       (413, 'datum and condition', 'valueType', 'boolean'),
       (414, 'datum and condition', 'valueType', 'mean'),
       (415, 'datum and condition', 'valueType', 'median'),
       (416, 'datum and condition', 'valueType', 'mode'),
       (417, 'datum and condition', 'valueType', 'observed'),
       (418, 'datum and condition', 'valueType', 'RMS'),
       (419, 'datum and condition', 'valueType', 'standard deviation'),
       (420, 'datum and condition', 'valueType', 'Z-score'),
       (421, 'datum and condition', 'valueType', 'Z-average'),
       (422, 'composing element', 'valueUnit', '%'),
       (423, 'composing element', 'valueUnit', '%mole'),
       (424, 'composing element', 'valueUnit', '%vol'),
       (425, 'composing element', 'valueUnit', '%wt/vol'),
       (426, 'functionalizing entity', 'valueUnit', '%'),
       (427, 'functionalizing entity', 'valueUnit', '%mole'),
       (428, 'functionalizing entity', 'valueUnit', '%vol'),
       (429, 'functionalizing entity', 'valueUnit', '%wt/vol'),
       (430, 'carbon nanotube', 'wallType', 'DWNT'),
       (431, 'carbon nanotube', 'wallType', 'MWNT'),
       (432, 'carbon nanotube', 'wallType', 'SWNT'),
       (433, 'attachment', 'otherBondType', 'Noncovalent'),
       (434, 'size', 'otherDatumName', 'Zeta Potential'),
       (436, 'imaging function', 'otherModality', 'Bioluminescence'),
       (437, 'emulsion', 'otherComposingElementType', 'core'),
       (438, 'emulsion', 'otherComposingElementType', 'excipient'),
       (439, 'emulsion', 'otherComposingElementType', 'coat'),
       (440, 'emulsion', 'otherComposingElementType', 'modifier'),
       (441, 'composing element', 'otherMolecularFormulaType', 'Hill'),
       (442, 'functionalizing entity', 'otherMolecularFormulaType', 'Hill'),
       (444, 'imaging function', 'otherModality', 'gamma ray'),
       (445, 'attachment', 'otherBondType', 'intercalation'),
       (446, 'composing element', 'otherType', 'polymer'),
       (447, 'composing element', 'otherType', 'RNA'),
       (448, 'imaging function', 'otherModality', 'beta radiation'),
       (449, 'composing element', 'otherValueUnit', 'Da'),
       (450, 'publication', 'otherCategory', 'proceeding'),
       (451, 'composing element', 'otherValueUnit', 'ug'),
       (452, 'composing element', 'otherValueUnit', 'pmol'),
       (453, 'functionalizing entity', 'otherValueUnit', 'pmol'),
       (454, 'composing element', 'otherValueUnit', 'nM'),
       (455, 'composing element', 'otherValueUnit', 'uL'),
       (456, 'functionalizing entity', 'otherValueUnit', 'uL'),
       (457, 'composing element', 'otherValueUnit', 'uM'),
       (458, 'functionalizing entity', 'otherValueUnit', 'nM'),
       (459, 'functionalizing entity', 'otherValueUnit', 'uM'),
       (460, 'composing element', 'otherValueUnit', 'mg'),
       (461, 'functionalizing entity', 'otherValueUnit', 'mg'),
       (462, 'composing element', 'otherValueUnit', 'mL'),
       (463, 'composing element', 'otherValueUnit', 'wt/wt'),
       (465, 'composing element', 'otherValueUnit', '%wt/wt'),
       (466, 'functionalizing entity', 'otherValueUnit', '%wt/wt'),
       (467, 'composing element', 'otherValueUnit', 'uCi/mg'),
       (468, 'functionalizing entity', 'otherValueUnit', 'mL'),
       (469, 'composing element', 'otherValueUnit', 'nmol'),
       (470, 'functionalizing entity', 'otherValueUnit', 'nmol'),
       (471, 'functionalizing entity', 'otherValueUnit', 'ug'),
       (472, 'composing element', 'otherValueUnit', 'mg/mL'),
       (473, 'composing element', 'otherValueUnit', 'mM'),
       (474, 'functionalizing entity', 'otherValueUnit', 'mg/mL'),
       (475, 'composing element', 'otherValueUnit', 'wt%'),
       (476, 'Height', 'otherUnit', 'micrometer'),
       (477, 'size', 'otherDatumName', 'Height'),
       (478, 'Width', 'otherUnit', 'micrometer'),
       (479, 'size', 'otherDatumName', 'Width'),
       (480, 'functionalizing entity', 'otherValueUnit', 'wt%'),
       (481, 'Width (top)', 'otherUnit', 'micrometer'),
       (482, 'size', 'otherDatumName', 'Width (top)'),
       (483, 'Width (bottom)', 'otherUnit', 'micrometer'),
       (484, 'size', 'otherDatumName', 'Width (bottom)'),
       (485, 'composing element', 'otherValueUnit', 'g'),
       (486, 'imaging function', 'otherModality', 'NMR'),
       (487, 'functionalizing entity', 'otherValueUnit', 'g'),
       (488, 'composing element', 'otherValueUnit', 'mmol'),
       (489, 'Feret\'s Diameter', 'otherUnit', 'nm'),
       (490, 'size', 'otherDatumName', 'Feret\'s Diameter'),
       (491, 'composing element', 'otherValueUnit', 'mL'),
       (492, 'composing element', 'otherValueUnit', 'umol'),
       (493, 'molecular weight', 'otherUnit', 'g/mol'),
       (494, 'functionalizing entity', 'otherValueUnit', 'mmol'),
       (495, 'Surface Area', 'otherUnit', 'm^2/g'),
       (496, 'size', 'otherDatumName', 'Surface Area'),
       (497, 'functionalizing entity', 'otherValueUnit', 'ug/uL'),
       (498, 'composing element', 'otherValueUnit', 'ug/uL'),
       (499, 'size', 'otherDatumName', 'Length'),
       (500, 'Length', 'otherUnit', 'nm'),
       (501, 'Length', 'otherUnit', 'micrometer'),
       (502, 'Diameter', 'otherUnit', 'nm'),
       (503, 'size', 'otherDatumName', 'Diameter'),
       (504, 'composing element', 'otherValueUnit', 'M'),
       (505, 'functionalizing entity', 'otherValueUnit', 'M'),
       (507, 'molecular weight distribution', 'otherUnit', 'g/mol'),
       (508, 'molecular weight', 'otherDatumName', 'molecular weight distribution'),
       (509, 'composing element', 'otherValueUnit', 'ug/mL'),
       (510, 'composing element', 'otherType', 'excipient'),
       (512, 'size', 'otherDatumName', 'Aspect Ratio'),
       (513, 'Width', 'otherUnit', 'nm'),
       (515, 'cell viability', 'otherDatumName', 'IC50'),
       (516, 'IC50', 'otherUnit', 'microM'),
       (517, 'IC50', 'otherUnit', 'nM'),
       (518, 'composing element', 'otherValueUnit', '%w'),
       (519, 'composing element', 'otherValueUnit', '%wt'),
       (520, 'composing element', 'otherValueUnit', 'mCi'),
       (521, 'functionalizing entity', 'otherValueUnit', 'mCi'),
       (522, 'functionalizing entity', 'otherValueUnit', 'mM'),
       (523, 'Height', 'otherUnit', 'microm'),
       (524, 'shape', 'otherDatumName', 'Height'),
       (525, 'physical state', 'otherType', '3D-cube'),
       (526, 'solubility', 'otherSolvent', '3D-cube'),
       (527, 'Width', 'otherUnit', 'microm'),
       (528, 'shape', 'otherDatumName', 'Width'),
       (529, 'physical state', 'otherType', '3D-cylinder'),
       (530, 'solubility', 'otherSolvent', '3D-cylinder'),
       (531, 'shape', 'otherDatumName', 'Width (top)'),
       (532, 'shape', 'otherDatumName', 'Width (bottom)'),
       (533, 'composing element', 'otherValueUnit', 'MBq'),
       (534, 'size', 'otherUnit', 'nm'),
       (535, 'size', 'otherDatumName', 'Size'),
       (536, 'imaging function', 'otherModality', 'photoluminescence'),
       (537, 'molecular weight', 'otherDatumName', 'polydispersity index'),
       (538, 'size', 'otherDatumName', 'polydispersity index'),
       (539, 'Zeta Potential', 'otherUnit', 'mV'),
       (1010, 'pubchem', 'dataSource', 'Compound'),
       (1011, 'pubchem', 'dataSource', 'BioAssay'),
       (1012, 'pubchem', 'dataSource', 'Substance'),
       (1013, 'synthesis', 'purityType', 'Final Purification'),
       (1014, 'synthesis', 'purityType', 'Interim Purification'),
       (1015, 'synthesis', 'materialType', 'coat'),
       (1016, 'synthesis', 'materialType', 'core'),
       (1017, 'synthesis', 'materialType', 'excipient'),
       (1018, 'synthesis', 'materialType', 'internal buffer'),
       (1019, 'synthesis', 'materialType', 'lipid'),
       (1020, 'synthesis', 'materialType', 'modifier'),
       (1021, 'synthesis', 'materialType', 'monomer'),
       (1022, 'synthesis', 'materialType', 'polymer'),
       (1023, 'synthesis', 'materialType', 'repeat unit'),
       (1024, 'synthesis', 'materialType', 'RNA'),
       (1025, 'synthesis', 'materialType', 'shell'),
       (1026, 'synthesis', 'materialType', 'terminal group'),
       (1027, 'material', 'value_unit', '%'),
       (1028, 'material', 'value_unit', '%mol'),
       (1029, 'material', 'value_unit', '%mole'),
       (1030, 'material', 'value_unit', '%wt'),
       (1031, 'material', 'value_unit', '%wt/vol'),
       (1032, 'material', 'value_unit', '%wt/wt'),
       (1033, 'material', 'value_unit', 'g'),
       (1034, 'material', 'value_unit', 'g/cm3'),
       (1035, 'material', 'value_unit', 'g/mL'),
       (1036, 'material', 'value_unit', 'Gy'),
       (1037, 'material', 'value_unit', 'L'),
       (1038, 'material', 'value_unit', 'M'),
       (1039, 'material', 'value_unit', 'mCi'),
       (1040, 'material', 'value_unit', 'mg'),
       (1041, 'material', 'value_unit', 'mg/mL'),
       (1042, 'material', 'value_unit', 'microCi'),
       (1043, 'material', 'value_unit', 'microCi/mg'),
       (1044, 'material', 'value_unit', 'mL'),
       (1045, 'material', 'value_unit', 'mM'),
       (1046, 'material', 'value_unit', 'mmol'),
       (1047, 'material', 'value_unit', 'mmol/L'),
       (1048, 'material', 'value_unit', 'mol'),
       (1049, 'material', 'value_unit', 'mol%'),
       (1050, 'material', 'value_unit', 'mole%'),
       (1051, 'material', 'value_unit', 'ng'),
       (1052, 'material', 'value_unit', 'nM'),
       (1053, 'material', 'value_unit', 'nmol'),
       (1054, 'material', 'value_unit', 'pmol'),
       (1055, 'material', 'value_unit', 'uCi/mg'),
       (1056, 'material', 'value_unit', 'ug'),
       (1057, 'material', 'value_unit', 'ug/mL'),
       (1058, 'material', 'value_unit', 'ug/uL'),
       (1059, 'material', 'value_unit', 'uL'),
       (1060, 'material', 'value_unit', 'uL/mL'),
       (1061, 'material', 'value_unit', 'uM'),
       (1062, 'material', 'value_unit', 'umol'),
       (1063, 'material', 'value_unit', 'wt%'),
       (1064, 'material', 'value_unit', 'wt/wt'),
       (1065, 'function', 'type', 'transfection'),
       (1066, 'function', 'type', 'therapeutic function'),
       (1067, 'function', 'type', 'therapeutic'),
       (1068, 'function', 'type', 'targeting function'),
       (1069, 'function', 'type', 'targeting'),
       (1070, 'function', 'type', 'magnetic hyperthermia'),
       (1071, 'function', 'type', 'magnetic'),
       (1072, 'function', 'type', 'Linker'),
       (1073, 'function', 'type', 'imaging function'),
       (1074, 'function', 'type', 'imaging'),
       (1075, 'function', 'type', 'Fluorescence'),
       (1076, 'function', 'type', 'endosomolysis'),
       (1077, 'function', 'type', 'Drug nanocarrier'),
       (1078, 'function', 'type', 'Drug carrier'),
       (1079, 'function', 'type', 'Adjuvant'),
       (1080, 'functionalization', 'type', 'Polymer'),
       (1081, 'functionalization', 'type', 'Quantum Dot'),
       (1082, 'functionalization', 'type', 'Magnetic Particle'),
       (1083, 'functionalization', 'type', 'radioisotope'),
       (1084, 'functionalization', 'type', 'Monomer'),
       (1085, 'functionalization', 'type', 'cell membrane'),
       (1086, 'functionalization', 'type', 'surface coated porous nanoparticles'),
       (1087, 'functionalization', 'type', 'porous nanoparticle'),
       (1088, 'functionalization', 'type', 'RNA'),
       (1089, 'functionalization', 'type', 'Fluorophore'),
       (1090, 'functionalization', 'type', 'modifier'),
       (1091, 'functionalization', 'type', 'antibody'),
       (1092, 'functionalization', 'type', 'biopolymer'),
       (1093, 'functionalization', 'type', 'small molecule'),
       (1094, 'synthesis', 'otherMaterialType', 'reagent'),
       (1096, 'protocol', 'type', 'purification'),
       (13959168, 'technique', 'otherType', 'gel electrophoresis'),
       (13959169, 'gel electrophoresis', 'otherInstrument', 'fluorescence excitation device'),
       (13959170, 'gel electrophoresis', 'otherInstrument', 'cooled digital camera'),
       (13959171, 'instrument', 'otherManufacturer', 'Hamamatsu'),
       (13959172, 'imaging', 'otherInstrument', 'fluorescence excitation device'),
       (13959173, 'imaging', 'otherInstrument', 'cooled digital camera'),
       (14680064, 'instrument', 'otherManufacturer', 'FEI'),
       (14680065, 'cytotoxicity', 'otherDatumName', 'Fraction of nonviable cells relative to control'),
       (14680066, 'Fraction of nonviable cells relative to control', 'otherUnit', '%'),
       (14680067, 'cytotoxicity', 'otherDatumName', 'dose'),
       (14680068, 'dose', 'otherUnit', 'microg/mL'),
       (14680069, 'cytotoxicity', 'otherDatumName', 'p-value'),
       (14680070, 'gel electrophoresis', 'otherInstrument', 'research microscope'),
       (14680071, 'instrument', 'otherManufacturer', 'Olympus'),
       (14680072, 'transmission electron microscopy', 'otherInstrument', 'digital camera'),
       (14680073, 'instrument', 'otherManufacturer', 'Soft Imaging Systems'),
       (14680074, 'comet assay', 'otherDatumName', 'p-value'),
       (14680075, 'comet assay', 'otherDatumName', '% tail'),
       (14680076, 'comet assay', 'otherDatumName', 'dose'),
       (14680077, 'oxidative stress', 'otherDatumName', 'relative fluorescence unit'),
       (14680078, 'oxidative stress', 'otherDatumName', 'dose'),
       (14680079, 'cytotoxicity', 'otherDatumName', ' Fraction of nonviable cells relative to control'),
       (14680080, ' Fraction of nonviable cells relative to control', 'otherUnit', '%'),
       (15007744, 'size', 'otherDatumName', 'maximum size'),
       (15007745, 'maximum size', 'otherUnit', 'nm'),
       (15007746, 'size', 'otherDatumName', 'minimum size'),
       (15007747, 'minimum size', 'otherUnit', 'nm'),
       (15007748, 'cytotoxicity', 'otherDatumName', 'p - value'),
       (15007749, 'carbon nanotube', 'otherDiameterUnit', ' nm'),
       (15007750, 'carbon nanotube', 'otherAverageLengthUnit', ' nm'),
       (15007751, 'size', 'otherDatumName', 'maximum length'),
       (15007752, 'maximum length', 'otherUnit', 'nm'),
       (15007753, 'size', 'otherDatumName', 'minimum length'),
       (15007754, 'minimum length', 'otherUnit', 'nm'),
       (15007755, 'size', 'otherDatumName', 'maximum diameter'),
       (15007756, 'maximum diameter', 'otherUnit', 'nm'),
       (15007757, 'size', 'otherDatumName', 'minimum diameter'),
       (15007758, 'minimum diameter', 'otherUnit', 'nm'),
       (15007759, 'size', 'otherDatumName', 'Number of Samples'),
       (15007760, 'size', 'otherDatumName', 'Standard Deviation'),
       (15007761, 'Standard Deviation', 'otherUnit', 'nm'),
       (15007762, 'surface', 'otherDatumName', 'number of samples'),
       (15007763, 'surface', 'otherDatumName', 'standard deviation'),
       (15007764, 'standard deviation', 'otherUnit', 'mV'),
       (15007765, 'size', 'otherUnit', 'microm'),
       (15007766, 'cytotoxicity', 'otherDatumName', 'p value'),
       (15007767, 'cytotoxicity', 'otherDatumName', 'Fraction of nonviable cells'),
       (15007768, 'Fraction of nonviable cells', 'otherUnit', '%'),
       (15007769, 'maximum size', 'otherUnit', 'microm'),
       (15007770, 'minimum size', 'otherUnit', 'microm'),
       (15007771, 'dose', 'otherUnit', 'microg/L'),
       (15007772, 'cytotoxicity', 'otherDatumName', 'Fraction of noviable cells'),
       (15007773, 'Fraction of noviable cells', 'otherUnit', '%'),
       (15007774, 'cytotoxicity', 'otherDatumName', 'Fraction of cells with mitochondrial depolarization'),
       (15007775, 'Fraction of cells with mitochondrial depolarization', 'otherUnit', '%'),
       (15007776, 'technique', 'otherType', 'laser diffraction'),
       (15007777, 'laser diffraction', 'otherInstrument', 'laser diffraction instrument'),
       (15007778, 'Diameter', 'otherUnit', 'microm'),
       (15007779, 'instrument', 'otherManufacturer', 'Zeiss'),
       (15007780, 'instrument', 'otherManufacturer', 'Varian'),
       (15007781, 'technique', 'otherType', 'X-ray diffraction'),
       (15007782, 'X-ray diffraction', 'otherInstrument', 'X-ray diffraction spectrometer'),
       (15007783, 'instrument', 'otherManufacturer', 'Kratos Analytical'),
       (15007784, 'cytotoxicity', 'otherDatumName', 'Treatment Time'),
       (15007785, 'Treatment Time', 'otherUnit', 'hours'),
       (15007786, 'technique', 'otherType', 'atomic absorption spectroscopy'),
       (15007787, 'atomic absorption spectroscopy', 'otherInstrument', 'atomic absorption spectrometer'),
       (15007788, 'instrument', 'otherManufacturer', 'PerkinElmer'),
       (15400960, 'size', 'otherAssayType', 'gel permeation chromatography'),
       (15400961, 'size', 'otherAssayType', 'size'),
       (15400962, 'purity', 'otherAssayType', 'high performance liquid chromatography'),
       (15400963, 'other_pc', 'otherAssayType', 'nuclear magnetic resonance'),
       (15400965, 'molecular weight', 'otherAssayType', 'gel permeation chromatography'),
       (15400966, 'technique', 'otherType', 'gel permeation chromatography'),
       (15400968, 'technique', 'otherType', 'nuclear magnetic resonance'),
       (15400969, 'nuclear magnetic resonance', 'otherInstrument', 'nuclear magnetic resonance spectrometer'),
       (15400970, 'nuclear magnetic resonance', 'otherInstrument', 'nuclear magnetic resonance instrument'),
       (15400971, 'molecular weight', 'otherAssayType', 'molecular weight'),
       (15400972, 'cytotoxicity', 'otherDatumName', 'fluorescence'),
       (15400973, 'cytotoxicity', 'otherDatumName', '% of control'),
       (15400974, 'sample concentration', 'otherUnit', 'ug/mL'),
       (15400975, 'condition', 'otherName', 'treatment duration'),
       (15400976, 'treatment duration', 'otherUnit', 'h'),
       (15400977, 'oxidative stress', 'otherDatumName', '% of control'),
       (15400978, 'cytotoxicity', 'otherAssayType', 'chromosome condensation'),
       (15400979, 'technique', 'otherType', 'fluorescence microscopy'),
       (15400980, 'size', 'otherDatumName', 'size variation'),
       (15400981, 'size variation', 'otherUnit', '%'),
       (15400982, 'purity', 'otherAssayType', 'UV-Vis absorbance spectrophotometry'),
       (15400983, 'technique', 'otherType', 'inductively coupled plasma atomic emission spectroscopy'),
       (15400984, 'targeting', 'otherDatumName', 'uptake half-life'),
       (15400985, 'uptake half-life', 'otherUnit', 'h'),
       (15400986, 'targeting', 'otherDatumName', 'maximum # of internalized NPs'),
       (15400987, 'targeting', 'otherDatumName', 'uptake rate'),
       (15400988, 'uptake rate', 'otherUnit', 'NPs/h'),
       (15400989, 'targeting', 'otherDatumName', 'maximum number of internalized NPs / cell'),
       (15400990, 'other_pc', 'otherAssayType', 'uv-vis absorbance spectrophotometry '),
       (15400991, 'other_pc', 'otherAssayType', 'FT-IR spectroscopy'),
       (15400992, 'other_pc', 'otherAssayType', 'gel electrophoresis'),
       (15400993, 'other_pc', 'otherAssayType', 'protein assay'),
       (15400994, 'shape', 'otherAssayType', 'shape'),
       (15400995, 'shape', 'otherDatumName', 'size variation'),
       (15400996, 'shape', 'otherDatumName', 'Length'),
       (15400997, 'targeting', 'otherDatumName', 'background absorbance'),
       (15400998, 'condition', 'otherName', 'wavelength'),
       (15400999, 'targeting', 'otherDatumName', 'absorbance'),
       (15401000, 'technique', 'otherType', 'differential centrifugal sedimentation'),
       (15401001, 'instrument', 'otherManufacturer', 'CPS Instruments'),
       (15401002, 'surface', 'otherAssayType', 'surface'),
       (15401003, 'instrument', 'otherManufacturer', 'Dynatech'),
       (15401004, 'size exclusion chromatography with multi-angle laser light scattering', 'otherInstrument',
        'multi angle light scattering detector'),
       (15401005, 'instrument', 'otherManufacturer', 'TosoHaas'),
       (15401006, 'high performance liquid chromatography', 'otherInstrument', 'HPLC autosampler'),
       (15401007, 'molecular weight', 'otherDatumName', 'PDI'),
       (15401008, 'molecular weight', 'otherDatumName', 'weight average molecular weight'),
       (15401009, 'molecular weight', 'otherDatumName', 'number average molecular weight'),
       (15401010, 'size exclusion chromatography with multi-angle laser light scattering', 'otherInstrument',
        'separations module'),
       (15401011, 'size exclusion chromatography with multi-angle laser light scattering', 'otherInstrument',
        'guard column'),
       (15401012, 'cytotoxicity', 'otherDatumName', 'treatment duration'),
       (15401013, 'treatment duration', 'otherUnit', 'hours'),
       (15401014, 'cytotoxicity', 'otherDatumName', '% of nonviable cells'),
       (15401015, '% of nonviable cells', 'otherUnit', '%'),
       (15401016, 'cytotoxicity', 'otherDatumName', 'sample concentration'),
       (15401017, 'cytotoxicity', 'otherDatumName', '% of nonviable cells relative to control'),
       (15401018, '% of nonviable cells relative to control', 'otherUnit', '%'),
       (15401019, 'oxidative stress', 'otherDatumName', 'sample concentration'),
       (15401020, 'cytotoxicity', 'otherDatumName', '% of cells with mitochondrial depolarization'),
       (15401021, '% of cells with mitochondrial depolarization', 'otherUnit', '%'),
       (15401022, 'cytotoxicity', 'otherDatumName', '% of cell with mitochondrial depolarization'),
       (15401023, '% of cell with mitochondrial depolarization', 'otherUnit', '%'),
       (15401024, 'cytotoxicity', 'otherDatumName', '% of cells with mitochodrial depolarization'),
       (15401025, '% of cells with mitochodrial depolarization', 'otherUnit', '%'),
       (15401026, 'cytotoxicity', 'otherDatumName', '% of viable cells relative to control'),
       (15401027, '% of viable cells relative to control', 'otherUnit', '%'),
       (15401028, 'other_vt', 'otherAssayType', 'actin cytoskeleton disruption'),
       (15401029, 'other_vt', 'otherAssayType', 'VE-cadherin'),
       (15401030, 'other_vt', 'otherAssayType', 'Tubule Formation/Angiogenesis'),
       (15401031, 'cytotoxicity', 'otherDatumName', '% of LDH release relative to control'),
       (15401032, '% of LDH release relative to control', 'otherUnit', '%'),
       (15401033, 'fullerene', 'otherAverageDiameterUnit', 'nm'),
       (15401034, 'size', 'otherDatumName', 'peak1'),
       (15401035, 'peak1', 'otherUnit', 'nm'),
       (15401036, 'instrument', 'otherManufacturer', 'Park Systems'),
       (15401037, 'solubility', 'otherSolvent', 'THF'),
       (15401038, 'solubility', 'otherAssayType', 'solubility'),
       (15401039, 'technique', 'otherType', 'weight'),
       (15401040, 'weight', 'otherInstrument', 'balance'),
       (15401041, 'instrument', 'otherManufacturer', 'Sartorius'),
       (15401042, 'solubility', 'otherDatumName', 'solubility'),
       (15401043, 'solubility', 'otherUnit', 'mg/L'),
       (15401044, 'scanning electron microscopy', 'otherInstrument', 'digital imaging capture system'),
       (15401045, 'instrument', 'otherManufacturer', 'Point Electronic GmBh'),
       (15401046, 'purity', 'assayType', 'purity'),
       (15401047, 'technique', 'otherType', 'elemental analysis'),
       (15401048, 'elemental analysis', 'otherInstrument', 'elemental analysis instrument'),
       (15401049, 'instrument', 'otherManufacturer', 'LECO'),
       (15401050, 'purity', 'otherDatumName', 'O'),
       (15401051, 'O', 'otherUnit', '%'),
       (15401052, 'purity', 'otherDatumName', 'H'),
       (15401053, 'H', 'otherUnit', '%'),
       (15401054, 'purity', 'otherDatumName', 'C'),
       (15401055, 'C', 'otherUnit', '%'),
       (15401056, 'technique', 'otherType',
        'high performance liquid chromatography - evaporative light scattering detection'),
       (15401057, 'high performance liquid chromatography - evaporative light scattering detection', 'otherInstrument',
        'liquid chromatograph/mass spectrometer ion-trap system'),
       (15401058, 'high performance liquid chromatography - evaporative light scattering detection', 'otherInstrument',
        'evaporative light scattering detector'),
       (15401059, 'instrument', 'otherManufacturer', 'Alltech'),
       (15401060, 'purity', 'otherDatumName', 'amount of cyclodextrin'),
       (15401061, 'amount of cyclodextrin', 'otherUnit', 'ug/mg'),
       (15401062, 'purity', 'otherDatumName', 'cyclodextrin content'),
       (15401063, 'cyclodextrin content', 'otherUnit', 'ug/mg'),
       (15401064, 'datum and condition', 'otherValueType', 'number of samples'),
       (15401065, 'other_pc', 'otherAssayType', 'stability'),
       (15401066, 'other_pc', 'otherAssayType', 'RBITC content'),
       (15401067, 'instrument', 'otherManufacturer', 'Labsystems'),
       (15401068, 'other', 'otherDatumName', 'RBITC content'),
       (15401069, 'RBITC content', 'otherUnit', 'ug/mg'),
       (15401070, 'other_pc', 'otherAssayType', 'elemental analysis'),
       (15401071, 'other', 'otherDatumName', 'O'),
       (15401072, 'other', 'otherDatumName', 'H'),
       (15401073, 'other', 'otherDatumName', 'C'),
       (15401074, 'other_pc', 'otherAssayType', 'cyclodextrin content'),
       (15401075, 'other', 'otherDatumName', 'cyclodextrin content'),
       (15401076, 'high performance liquid chromatography - evaporative light scattering detection', 'otherInstrument',
        'separation column'),
       (15401077, 'other_pc', 'otherAssayType', 'RBITC release'),
       (15401078, 'ex vivo', 'otherAssayType', 'gastrointestinal transit studies'),
       (15401079, 'ex vivo', 'otherDatumName', 'MRT'),
       (15401080, 'MRT', 'otherUnit', 'h'),
       (15401081, 'ex vivo', 'otherDatumName', 'K_el'),
       (15401082, 'K_el', 'otherUnit', '1/h'),
       (15401083, 'ex vivo', 'otherDatumName', 'AUC_adh'),
       (15401084, 'AUC_adh', 'otherUnit', 'mg h'),
       (15401085, 'ex vivo', 'otherDatumName', 'Qmax'),
       (15401086, 'Qmax', 'otherUnit', 'mg'),
       (15401087, 'ex vivo', 'otherAssayType', 'imaging'),
       (15401088, 'fluorescence microscopy', 'otherInstrument', 'research microscope with fluorescence source'),
       (15401089, 'imaging', 'otherAssayType', 'imaging'),
       (15401090, 'imaging', 'otherInstrument', 'gamma camera'),
       (15401091, 'instrument', 'otherManufacturer', 'Siemens Medical'),
       (15401092, 'other_pc', 'otherAssayType', 'dye content'),
       (15401093, 'other_pc', 'otherAssayType', 'dye release'),
       (15401094, 'other_pc', 'otherAssayType', 'acoustic microscopy'),
       (15401095, 'technique', 'otherType', 'acoustic microscopy'),
       (15401096, 'acoustic microscopy', 'otherInstrument', 'immersion transducer'),
       (15401097, 'instrument', 'otherManufacturer', 'Panametrics'),
       (15401098, 'acoustic microscopy', 'otherInstrument', 'ultrasonic pulser-receiver'),
       (15401099, 'acoustic microscopy', 'otherInstrument', '4 axis programmable stage controller'),
       (15401100, 'instrument', 'otherManufacturer', 'Aerotech'),
       (15401101, 'other', 'otherDatumName', 'reflectivity enhancement'),
       (15401102, 'reflectivity enhancement', 'otherUnit', 'dB'),
       (15401103, 'condition', 'otherName', 'safflower seed oil content'),
       (15401104, 'safflower seed oil content', 'otherUnit', '%'),
       (15401105, 'condition', 'otherName', 'PFOB content'),
       (15401106, 'PFOB content', 'otherUnit', '%'),
       (15401107, 'other', 'otherDatumName', 'integrated backscatter 15 - 30 MHz'),
       (15401108, 'integrated backscatter 15 - 30 MHz', 'otherUnit', 'dB'),
       (15401109, 'other', 'otherDatumName', 'total amount  of bound PFOB'),
       (15401110, 'total amount  of bound PFOB', 'otherUnit', 'ug'),
       (15401111, 'other', 'otherDatumName', 'B parameter error'),
       (15401112, 'other', 'otherDatumName', 'B parameter'),
       (15401113, 'other', 'otherDatumName', 'A parameter error'),
       (15401114, 'other', 'otherDatumName', 'A parameter'),
       (15401115, 'high performance liquid chromatography', 'otherInstrument', 'gas chromatograph'),
       (15401116, 'technique', 'otherType', 'gas chromatography'),
       (15401117, 'gas chromatography', 'otherInstrument', 'gas chromatograph'),
       (15401118, 'other', 'otherDatumName', 'total amount of bound PFOB'),
       (15401119, 'total amount of bound PFOB', 'otherUnit', 'ug'),
       (15401120, 'other', 'otherDatumName', 'relflectivity enhancement'),
       (15401121, 'relflectivity enhancement', 'otherUnit', 'dB'),
       (15401122, 'condition', 'otherName', 'SMC treated biotinylated antitissue factor antibody'),
       (15401123, 'other', 'otherDatumName', 'integrated backscatter 10 - 25 MHz'),
       (15401124, 'integrated backscatter 10 - 25 MHz', 'otherUnit', 'dB'),
       (15401125, 'other_pc', 'otherAssayType', 'lipid-induced fluorescence enhancement'),
       (15401126, 'targeting', 'otherDatumName', '% of viable cells'),
       (15401127, '% of viable cells', 'otherUnit', '%'),
       (15401128, 'other_pc', 'otherAssayType', 'uv-vis absorption spectrometry'),
       (15401129, 'other_pc', 'otherAssayType', 'uv-vis spectroscopy'),
       (15401130, 'relaxivity', 'otherAssayType', 'relaxivity'),
       (15401131, 'instrument', 'otherManufacturer', 'Bruker'),
       (15401132, 'R2', 'otherUnit', '1/s 1/mM'),
       (15401133, 'R1', 'otherUnit', '1/s 1/mM'),
       (15401134, 'other_pc', 'otherAssayType', 'uv-vis absorption spectroscopy'),
       (15401135, 'targeting', 'otherAssayType', 'receptor binding'),
       (15401136, 'cell counting', 'otherInstrument', 'gamma counter'),
       (15401137, 'instrument', 'otherManufacturer', 'Packard'),
       (15401138, 'targeting', 'otherDatumName', 'IC50'),
       (15401139, 'IC50', 'otherUnit', 'ug/mL'),
       (15401140, 'radioactivity quantiation', 'otherInstrument', 'gamma counter'),
       (15401141, 'technique', 'otherType', 'dark field microscopy'),
       (15401142, 'dark field microscopy', 'otherInstrument', 'research microscope'),
       (15401143, 'nuclear magnetic resonance', 'otherInstrument', 'single-sided NMR probe'),
       (15401144, 'instrument', 'otherManufacturer', 'ACT GmbH'),
       (15401145, 'T2', 'otherUnit', 'ms'),
       (15401146, 'T1', 'otherUnit', 'ms'),
       (15401147, 'other_pc', 'otherAssayType', 'quantification of drug payload'),
       (15401148, 'cytotoxicity', 'otherDatumName', '% of viable cells'),
       (15401149, 'other_pc', 'otherAssayType', 'Quantification of drug loading'),
       (15401150, 'other', 'otherDatumName', 'amount of drug/unit of NP mass'),
       (15401151, 'amount of drug/unit of NP mass', 'otherUnit', 'ug/mg'),
       (15401152, 'R2', 'otherUnit', '1/mM 1/s'),
       (15401153, 'R1', 'otherUnit', '1/mM 1/s'),
       (15401154, 'instrument', 'otherManufacturer', 'Applied Biosystems'),
       (15401155, 'other_pc', 'otherAssayType', 'FTIR spectroscopy'),
       (15401156, 'other_pc', 'otherAssayType', 'X-ray diffraction'),
       (15401157, 'nuclear magnetic resonance', 'otherInstrument', 'MRI scanner'),
       (15401158, 'relaxivity', 'otherDatumName', 'R2*'),
       (15401159, 'R2*', 'otherUnit', '1/mM 1/s'),
       (15401160, 'other_pc', 'otherAssayType', 'iron content'),
       (15401161, 'inductively coupled plasma atomic emission spectroscopy', 'otherInstrument',
        'inductively coupled plasma-atomic emission spectrometer'),
       (15401162, 'instrument', 'otherManufacturer', 'Thermo Scientific'),
       (15401163, 'other_pc', 'otherAssayType', 'magnetic properties'),
       (15401164, 'technique', 'otherType', 'magnetic property measurement'),
       (15401165, 'instrument', 'otherManufacturer', 'Quantum Design'),
       (15401166, 'magnetic property measurement', 'otherInstrument', 'SQUID sample magnetometer'),
       (15401167, 'other', 'otherDatumName', 'saturation magnetization'),
       (15401168, 'saturation magnetization', 'otherUnit', 'emu/g of Fe'),
       (15401169, 'other_pc', 'otherAssayType', 'Ninhydrin Assay'),
       (15401170, 'other_pc', 'otherAssayType', 'signal contrast enhancement'),
       (15401171, 'imaging', 'otherInstrument', 'MRI scanner'),
       (15401172, 'instrument', 'otherManufacturer', 'GE Healthcare'),
       (15401173, 'other_pc', 'otherAssayType', 'magnetic property'),
       (15401174, 'imaging', 'otherInstrument', 'PET scanner'),
       (15401175, 'ex vivo', 'otherAssayType', 'histology'),
       (15401176, 'technique', 'otherType', 'illumination'),
       (15401177, 'illumination', 'otherInstrument', 'laser light source'),
       (15401178, 'instrument', 'otherManufacturer', 'B&W Tek'),
       (15401179, 'instrument', 'otherManufacturer', 'Nikon'),
       (15401180, 'fluorescence microscopy', 'otherInstrument', 'research microscope'),
       (15401181, 'instrument', 'otherManufacturer', 'Micromass'),
       (15401182, 'electrophoretic light scattering', 'otherInstrument', 'zeta potential analyzer'),
       (15401183, 'T2', 'otherUnit', 's'),
       (15401184, 'condition', 'otherName', 'Fe concentration'),
       (15401185, 'Fe concentration', 'otherUnit', 'ug/mL'),
       (15401186, 'T1', 'otherUnit', 's'),
       (15401187, 'other_pc', 'otherAssayType', 'uv-vis absorbance spectroscopy'),
       (15401188, 'other_pc', 'otherAssayType', 'uv-vis absorbance spectrometry'),
       (15401189, 'other_pc', 'otherAssayType', 'elemental composition'),
       (15401190, 'imaging', 'otherInstrument', 'NMR spectrometer'),
       (15401191, 'confocal laser scanning microscopy', 'otherInstrument', 'laser scanning confocal microscope'),
       (15401192, 'confocal laser scanning microscopy', 'otherInstrument', 'inverted research microscope'),
       (15401194, 'condition', 'otherName', 'wash'),
       (15401195, 'condition', 'otherName', 'Tris concentration'),
       (15401196, 'Tris concentration', 'otherUnit', 'mM'),
       (15401197, 'condition', 'otherName', 'NaCl concentration'),
       (15401198, 'NaCl concentration', 'otherUnit', 'mM'),
       (15401199, 'size', 'otherDatumName', 'number of adsorbed proteins/ASPN'),
       (15401200, 'condition', 'otherName', 'number of oligo stands/AuNP'),
       (15401201, 'condition', 'otherName', 'number of oligo strands/AuNP'),
       (15401202, 'size', 'otherDatumName', 'number of adsorbed proteins/ASNP'),
       (15401203, 'size', 'otherDatumName', 'diameter after media'),
       (15401204, 'diameter after media', 'otherUnit', 'nm'),
       (15401205, 'size', 'otherDatumName', 'diameter before media'),
       (15401206, 'diameter before media', 'otherUnit', 'nm'),
       (15401207, 'surface', 'otherDatumName', 'zeta potential after media'),
       (15401208, 'zeta potential after media', 'otherUnit', 'mV'),
       (15401209, 'surface', 'otherDatumName', 'zeta potential before media'),
       (15401210, 'zeta potential before media', 'otherUnit', 'mV'),
       (15401211, '', 'otherAssayType', 'protein adsorption'),
       (15401212, 'instrument', 'otherManufacturer', 'Photon Technology International'),
       (15401213, 'other_pc', 'otherAssayType', 'protein adsorption'),
       (15401214, 'other', 'otherDatumName', 'number of adsorbed proteins/ASNP'),
       (15401215, 'other', 'otherDatumName', 'number of adsorbed proteins/ASPN'),
       (15401216, 'targeting', 'otherDatumName', '% of gene expression relative to control'),
       (15401217, '% of gene expression relative to control', 'otherUnit', '%'),
       (15401218, 'instrument', 'otherManufacturer', 'Guava Technologies/Millipore'),
       (15401219, 'fluorescence microscopy', 'otherInstrument', 'inverted research microscope'),
       (15401220, 'fluorescence microscopy', 'otherInstrument', 'digital camera'),
       (15401221, 'targeting', 'otherDatumName', '% gene expression of control'),
       (15401222, '% gene expression of control', 'otherUnit', '%'),
       (15401223, 'cytotoxicity', 'otherDatumName', '% viable cells of control'),
       (15401224, '% viable cells of control', 'otherUnit', '%'),
       (15401225, 'sample concentration', 'otherUnit', 'nmol/mL'),
       (15401226, 'cytotoxicity', 'otherDatumName', '% viable cells to control'),
       (15401227, '% viable cells to control', 'otherUnit', '%'),
       (15401228, 'other_pc', 'otherAssayType', 'Thermogravimetric Analysis of quantity of Gd2O3 in Gd2O3@SWNHag'),
       (15401229, 'other', 'otherDatumName', 'Gd2O3 content'),
       (15401230, 'Gd2O3 content', 'otherUnit', '%'),
       (15401231, 'other_pc', 'otherAssayType', 'ICP-AES Analysis of Gd2O3 content in Gd2O3@SWNHag'),
       (15401232, 'instrument', 'otherManufacturer', 'Horiba'),
       (15401233, 'technique', 'otherType', 'thermogravimetric analysis'),
       (15401234, 'other_pc', 'otherAssayType', 'Gd2O3 content'),
       (15401235, 'other_pc', 'otherAssayType', 'scanning transmission electron microscopy'),
       (15401236, 'technique', 'otherType', 'scanning transmission electron microscopy'),
       (15401237, 'scanning transmission electron microscopy', 'otherInstrument',
        'scanning transmission electron microscope'),
       (15401238, 'scanning transmission electron microscopy', 'otherInstrument',
        'energy dispersive X-ray spectrometer'),
       (15892480, 'sample concentration', 'otherUnit', 'nM'),
       (15892481, 'cytotoxicity', 'otherAssayType', 'intracellular ATP content'),
       (15892482, 'intracellular ATP content', 'otherDatumName', 'luminescence'),
       (15892483, 'cytotoxicity', 'otherAssayType', 'cellular metabolism'),
       (15892484, 'cellular metabolism', 'otherDatumName', 'fluorescence'),
       (16056320, 'targeting', 'otherAssayType', 'immunoassay'),
       (16056321, 'immunoassay', 'otherDatumName', 'sample concentration after treatment'),
       (16056322, 'sample concentration after treatment', 'otherUnit', 'pM'),
       (16056323, 'datum and condition', 'otherValueType', 'standard error of mean'),
       (16678912, 'other_pc', 'otherAssayType', 'imaging'),
       (16678913, 'cytotoxicity', 'otherDatumName', 'IC50'),
       (16678914, 'other_vt', 'otherAssayType', 'LDH release'),
       (16678915, 'other', 'otherDatumName', '% of control'),
       (16678916, '% of control', 'otherUnit', '%'),
       (16678917, 'sample concentration', 'otherUnit', 'uM'),
       (16678918, 'other_vt', 'otherAssayType', 'cytochrome c release'),
       (16678919, 'other_vt', 'otherAssayType', 'apoptosis'),
       (16678920, 'other_vt', 'otherAssayType', 'cholesterol impact on pore formation in plasma membrane'),
       (16678921, 'technique', 'otherType', 'surface plasmon resonance'),
       (16678922, 'surface plasmon resonance', 'otherInstrument', 'surface plasmon resonance instrument'),
       (16678923, 'targeting', 'otherDatumName', 'binding response'),
       (16678924, 'binding response', 'otherUnit', 'RU'),
       (20000001, 'protocol', 'otherType', 'sterility'),
       (20000002, 'size', 'otherDatumName', 'average'),
       (20000003, 'cell viability', 'otherDatumName', 'percent cell viability'),
       (20000004, 'platelet aggregation', 'otherDatumName', 'induces platelet aggregation?'),
       (20000005, 'platelet aggregation', 'otherDatumName', 'interferes with collagen-induced platelett aggregation?'),
       (20000006, 'molecular weight', 'otherDatumName', 'pdi'),
       (20000007, 'PT', 'otherUnit', 'Seconds'),
       (20000008, 'coagulation', 'otherDatumName', 'PT'),
       (20000009, 'APTT', 'otherUnit', 'Seconds'),
       (20000010, 'coagulation', 'otherDatumName', 'APTT'),
       (20000011, 'Thrombin', 'otherUnit', 'Seconds'),
       (20000012, 'coagulation', 'otherDatumName', 'Thrombin'),
       (20000013, 'Reptilase', 'otherUnit', 'Seconds'),
       (20000014, 'coagulation', 'otherDatumName', 'Reptilase'),
       (20000015, 'platelet aggregation', 'otherDatumName', '% platelet aggregation'),
       (20000016, '% platelet aggregation', 'otherUnit', 'percent'),
       (20000017, 'platelet aggregation', 'otherDatumName', '% Collagen Induced Platelet Aggregation'),
       (20000018, 'chemotaxis', 'otherDatumName', '% chemotaxis'),
       (20000019, 'complement activation', 'otherDatumName', 'complement activation induced'),
       (20000020, 'IL10', 'otherUnit', 'pg/mL'),
       (20000021, 'cytokine induction', 'otherDatumName', 'IL10'),
       (20000022, 'IL1B', 'otherUnit', 'pg/mL'),
       (20000023, 'cytokine induction', 'otherDatumName', 'IL1B'),
       (20000024, 'IL8', 'otherUnit', 'pg/mL'),
       (20000025, 'cytokine induction', 'otherDatumName', 'IL8'),
       (20000026, 'IL6', 'otherUnit', 'pg/mL'),
       (20000027, 'cytokine induction', 'otherDatumName', 'IL6'),
       (20000028, 'TNFA', 'otherUnit', 'pg/mL'),
       (20000029, 'cytokine induction', 'otherDatumName', 'TNFA'),
       (25505024, 'size', 'otherDatumName', 'peak1'),
       (25505025, 'peak1', 'otherUnit', 'nm'),
       (25505027, 'sample concentration', 'otherUnit', 'g/ml'),
       (25505028, 'blood contact', 'otherDatumName', '% Collagen Induced Platelet Aggregation'),
       (25505029, 'blood contact', 'otherDatumName', '% platelet aggregation'),
       (25505030, 'blood contact', 'otherDatumName', 'interferes with collagen-induced platelett aggregation?'),
       (25505031, 'blood contact', 'otherDatumName', 'induces platelet aggregation?'),
       (25668864, 'cytotoxicity', 'otherDatumName', 'percent cell viability'),
       (25668865, 'percent cell viability', 'otherUnit', '%'),
       (25668866, 'condition', 'otherName', 'time'),
       (25668867, 'time', 'otherUnit', 'hours'),
       (25668868, 'average', 'otherUnit', 'nm'),
       (26193152, 'blood contact', 'otherDatumName', 'complement activation induced'),
       (26193153, 'immune cell function', 'otherDatumName', 'number of CFU-GM colonies'),
       (26193154, 'immune cell function', 'otherDatumName', 'total number of bone marrow cells'),
       (26193155, 'immune cell function', 'otherDatumName', '% chemotaxis'),
       (26193156, 'charge', 'otherUnit', 'a.u'),
       (26193157, 'condition', 'otherName', 'Sample Batch'),
       (26836992, 'other_pc', 'otherAssayType', 'DNA quantification'),
       (26836993, 'technique', 'otherType', 'spectrofluorometry'),
       (26836994, 'spectrofluorometry', 'otherInstrument', 'spectrofluorometer'),
       (26836995, 'other', 'otherDatumName', 'number of DNA strains/particle'),
       (26836996, 'condition', 'otherName', 'SDS'),
       (26836997, 'NaCl concentration', 'otherUnit', 'M'),
       (26836998, 'other', 'otherDatumName', 'DNA surface coverage'),
       (26836999, 'DNA surface coverage', 'otherUnit', 'pmol/cm^2'),
       (26837000, 'other_pc', 'otherAssayType', 'controlled aggregation'),
       (26837001, 'Height', 'otherUnit', 'nm'),
       (26837002, 'shape', 'otherDatumName', 'diameter'),
       (26837003, 'instrument', 'otherManufacturer', 'Dako'),
       (26837004, 'imaging', 'otherInstrument', 'research microscope'),
       (26837005, 'instrument', 'otherManufacturer', 'Leica'),
       (27656192, 'solubility', 'otherUnit', 'mg/mL'),
       (27656193, 'differential centrifugal sedimentation', 'otherInstrument',
        'differential centrifugal sedimentation instrument'),
       (27656194, 'other_pc', 'otherAssayType', 'x-ray photoelectron spectroscopy'),
       (27656195, 'other_pc', 'otherAssayType', 'enzymatic degradation'),
       (28246016, 'surface', 'otherDatumName', 'silanol free/H-bond ratio'),
       (28246017, 'cytotoxicity', 'otherDatumName', 'LC50'),
       (28246018, 'LC50', 'otherUnit', 'ug/mL'),
       (28246019, 'technique', 'otherType', 'protein quantitation'),
       (28246020, 'protein quantitation', 'otherInstrument', 'microarray scanner'),
       (28246021, 'imaging', 'otherInstrument', 'microarray scanner'),
       (28246022, 'instrument', 'otherManufacturer', 'Affymetrix'),
       (28246023, 'technique', 'otherType', 'biochemical quantitation'),
       (28246024, 'biochemical quantitation', 'otherInstrument', 'bioanalyzer'),
       (28246025, 'other_vt', 'otherAssayType', 'mRNA quantitation'),
       (28246026, 'polymerase chain reaction', 'otherInstrument', 'qRT-PCR instrument'),
       (28246027, 'instrument', 'otherManufacturer', 'Roche Applied Science'),
       (28246028, 'other_vt', 'otherAssayType', 'Gene Set Enrichment'),
       (28246029, 'other_pc', 'otherAssayType', 'photoluminescence spectrophotometry'),
       (28246030, 'instrument', 'otherManufacturer', 'Jobin Yvon'),
       (28246031, 'other_pc', 'otherAssayType', 'fluorescence  lifetime'),
       (28246032, 'surface', 'otherDatumName', 'decay time'),
       (28246033, 'decay time', 'otherUnit', 'ns'),
       (28508160, 'other_vt', 'otherAssayType', 'cell capture with magnetic field'),
       (28508161, 'imaging', 'otherInstrument', 'digital camera'),
       (28508162, 'other_pc', 'otherAssayType', 'laser light irradiation'),
       (28508163, 'other_pc', 'otherAssayType', 'fluorescence correlation spectroscopy'),
       (28508164, 'confocal laser scanning microscopy', 'otherInstrument', ' fluorescence lifetime microscope system '),
       (28508165, 'instrument', 'otherManufacturer', 'Picoquant'),
       (28508166, 'technique', 'otherType', 'time-resolved fluorescence microscopy '),
       (28508167, 'time-resolved fluorescence microscopy ', 'otherInstrument',
        ' fluorescence lifetime microscope system '),
       (28508168, 'other_vt', 'otherAssayType', 'fluorescence correlation spectroscopy'),
       (28508169, 'instrument', 'otherManufacturer', 'OBB Corp'),
       (28508170, 'zeta potential analysis', 'otherInstrument', 'electrophoretic light scattering instrument'),
       (28508171, 'technique', 'otherType', 'fourier transform infrared spectrophotometry'),
       (28508172, 'fourier transform infrared spectrophotometry', 'otherInstrument', 'spectrophotometer'),
       (28508173, 'fourier transform infrared spectrophotometry', 'otherInstrument',
        'fourier transform infrared spectrophotometer'),
       (28508174, 'other_pc', 'otherAssayType', 'Fe concentration'),
       (28508175, 'other_pc', 'otherAssayType', 'degree of thiolation'),
       (28508176, 'other_pc', 'otherDatumName', 'degree of thiolation'),
       (28508177, 'degree of thiolation', 'otherUnit', 'mM/g'),
       (28508178, 'other', 'otherDatumName', 'Number of Strands/Particle'),
       (28508179, 'other', 'otherDatumName', 'number of DNA strands/particle'),
       (28508180, 'other', 'otherDatumName', 'number of DNA strands'),
       (28508181, 'other_pc', 'otherAssayType', 'loading efficiency'),
       (28508182, 'other_pc', 'otherDatumName', 'FITC loading efficiency'),
       (28508183, 'FITC loading efficiency', 'otherUnit', '%'),
       (28508184, 'other_pc', 'otherAssayType', 'dye release'),
       (28508185, 'other_pc', 'otherAssayType', 'dye loading efficiency'),
       (28508186, 'other_pc', 'otherDatumName', '% released'),
       (28508187, '% released', 'otherUnit', '%'),
       (28508188, 'condition', 'otherName', 'release time'),
       (28508189, 'release time', 'otherUnit', 'h'),
       (28508190, 'condition', 'otherName', 'GSH concentration'),
       (28508191, 'GSH concentration', 'otherUnit', 'mM'),
       (28508192, 'release time', 'otherUnit', 'min'),
       (28508193, 'other_pc', 'otherAssayType', 'DNA encapsulation efficiency'),
       (28508194, 'other_pc', 'otherAssayType', 'encapsulated plasmid DNA stability'),
       (28508195, 'other_pc', 'otherDatumName', 'DNA loading efficiency'),
       (28508196, 'DNA loading efficiency', 'otherUnit', '%'),
       (28508197, 'other_pc', 'otherAssayType', 'drug encapsulation efficiency'),
       (28508198, 'high performance liquid chromatography', 'otherInstrument', 'dual absorbance detector'),
       (28508199, 'other_pc', 'otherDatumName', 'drug encapsulation efficiency'),
       (28508200, 'drug encapsulation efficiency', 'otherUnit', '%'),
       (28508201, 'IC50', 'otherUnit', 'uM'),
       (28508202, 'cytotoxicity', 'otherAssayType', 'apoptosis'),
       (28508203, 'instrument', 'otherManufacturer', 'BD Biosciences'),
       (28508204, 'other_pc', 'otherDatumName', 'drug concentration'),
       (28508205, 'drug concentration', 'otherUnit', 'mg/mL'),
       (28508206, 'other_vt', 'otherAssayType', 'P-gp and NFkB activity'),
       (28508207, 'technique', 'otherType', 'centrifugal filtration'),
       (28508208, 'centrifugal filtration', 'otherInstrument', 'centrifugal filter unit'),
       (28508209, 'instrument', 'otherManufacturer', 'Millipore'),
       (28508210, 'cytotoxicity', 'otherDatumName', 'combination index'),
       (28508211, 'molecular formula', 'otherType', 'Hill'),
       (28508212, 'other_vt', 'otherAssayType', 'acoustic microscopy'),
       (28508213, 'other_vt', 'otherDatumName', 'reflectivity enhancement'),
       (28508214, 'other_vt', 'otherDatumName', 'mean integrated backscatter'),
       (28508215, 'mean integrated backscatter', 'otherUnit', 'db'),
       (28508216, 'other_vt', 'otherDatumName', 'PFOB content'),
       (28508217, 'PFOB content', 'otherUnit', 'ug'),
       (28508218, 'other_vt', 'otherDatumName', 'B parameter error'),
       (28508219, 'B parameter error', 'otherUnit', '1/ug'),
       (28508220, 'other_vt', 'otherDatumName', 'B parameter'),
       (28508221, 'B parameter', 'otherUnit', '1/ug'),
       (28508222, 'other_vt', 'otherDatumName', 'A parameter error'),
       (28508223, 'A parameter error', 'otherUnit', 'db'),
       (28508224, 'other_vt', 'otherDatumName', 'A parameter'),
       (28508225, 'A parameter', 'otherUnit', 'db'),
       (28508226, 'other_vt', 'otherDatumName', 'integrated backscatter'),
       (28508227, 'integrated backscatter', 'otherUnit', 'dB'),
       (28508228, 'other_pc', 'otherAssayType', 'fluorescence and singlet oxygen quantum yield'),
       (28508229, 'other_pc', 'otherDatumName', 'singlet oxygen quantum yield'),
       (28508230, 'other_pc', 'otherDatumName', 'fluorescence quantum yield'),
       (28508231, 'other_pc', 'otherDatumName', 'extinction coefficient'),
       (28508232, 'extinction coefficient', 'otherUnit', 'L/mol/cm'),
       (28508233, 'other_pc', 'otherAssayType', 'lipid induced fluorescence enhancement'),
       (28508234, 'other_pc', 'otherAssayType', 'drug loading efficiency'),
       (28508235, 'other_pc', 'otherDatumName', 'drug loading efficiency'),
       (28508236, 'drug loading efficiency', 'otherUnit', '%'),
       (28508237, 'drug concentration', 'otherUnit', 'uM'),
       (28508238, 'targeting', 'otherDatumName', '% viable cells'),
       (28508239, '% viable cells', 'otherUnit', '%'),
       (28508240, 'targeting', 'otherDatumName', 'fluorescence intensity'),
       (28508241, 'fluorescence intensity', 'otherUnit', 'au'),
       (28508242, 'cytotoxicity', 'otherDatumName', '% nonviable cells (internalized)'),
       (28508243, '% nonviable cells (internalized)', 'otherUnit', '%'),
       (28508244, 'cytotoxicity', 'otherDatumName', '% nonviable cells (non-internalized)'),
       (28508245, '% nonviable cells (non-internalized)', 'otherUnit', '%'),
       (28508246, 'cytotoxicity', 'otherDatumName', '% nonviable cells'),
       (28508247, '% nonviable cells', 'otherUnit', '%'),
       (28508248, 'cytotoxicity', 'otherDatumName', '% viable cells'),
       (28508249, 'Fe concentration', 'otherUnit', 'mg/mL'),
       (28508250, 'other_pc', 'otherDatumName', 'number of photosensitizers per nanoparticle '),
       (28508251, 'instrument', 'otherManufacturer', 'Hewlett-Packard'),
       (28508252, 'other_pc', 'otherAssayType', 'fluorescence spectrophotometry'),
       (28508253, 'instrument', 'otherManufacturer', 'Ocean Optics'),
       (28508254, 'other_pc', 'otherAssayType', 'wavelength dispersive spectroscopy'),
       (28508255, 'technique', 'otherType', 'wavelength dispersive spectroscopy'),
       (28508256, 'wavelength dispersive spectroscopy', 'otherInstrument', 'wavelength dispersive spectrometer'),
       (28508257, 'other_pc', 'otherAssayType', 'fluorescence quantum yield'),
       (28508258, 'other_pc', 'otherAssayType', 'stability'),
       (28508259, 'purity', 'otherAssayType', 'gel filtration chromatography'),
       (28508260, 'gel filtration chromatography', 'otherInstrument', 'purification system'),
       (28508261, 'instrument', 'otherManufacturer', 'Amersham Pharmacia Biotech'),
       (28508262, 'spectrophotometry', 'otherInstrument', 'spectrofluorometer'),
       (28508263, 'purity', 'otherDatumName', 'diameter'),
       (28508264, 'condition', 'otherName', 'fetal bovine serum'),
       (28508265, 'condition', 'otherName', 'phosphate buffered saline'),
       (28508266, 'size', 'otherAssayType', 'gel filtration chromatography'),
       (28508267, 'other_pc', 'otherDatumName', 'FWHM'),
       (28508268, 'FWHM', 'otherUnit', 'nm'),
       (28508269, 'condition', 'otherName', 'time since injection As precursor'),
       (28508270, 'time since injection As precursor', 'otherUnit', 'min'),
       (28508271, 'other_pc', 'otherDatumName', 'lambda_max'),
       (28508272, 'lambda_max', 'otherUnit', 'nm'),
       (28508273, 'condition', 'otherName', 'shell thickness'),
       (28508274, 'shell thickness', 'otherUnit', 'monolayer'),
       (28508275, 'surface', 'otherDatumName', 'lambda_max'),
       (28508276, 'other_pc', 'otherAssayType', '\"click\" reaction optimization'),
       (28508277, 'other_vt', 'otherAssayType', 'endosome escape'),
       (28508278, 'imaging', 'otherInstrument', 'fluorescence inverted microscope'),
       (28508279, 'other_vt', 'otherAssayType', 'endosomal escape'),
       (28508280, 'surface', 'otherDatumName', 'maximum zeta potential'),
       (28508281, 'maximum zeta potential', 'otherUnit', 'mV'),
       (28508282, 'surface', 'otherDatumName', 'minimum zeta potential'),
       (28508283, 'minimum zeta potential', 'otherUnit', 'mV'),
       (28508284, 'size', 'otherDatumName', 'number of oligo strands/AuNP'),
       (28508285, 'surface', 'otherDatumName', 'number of oligo strands/AuNP'),
       (28508286, 'technique', 'otherType', 'inductively coupled plasma mass spectrometry'),
       (28508287, 'inductively coupled plasma mass spectrometry', 'otherInstrument',
        'inductively coupled plasma mass spectrometer'),
       (28508288, 'targeting', 'otherDatumName', 'number of oligo strands/AuNP'),
       (28508289, 'other_pc', 'otherAssayType', 'melting temperature'),
       (28508290, 'other_pc', 'otherDatumName', 'melting temperature'),
       (28508291, 'melting temperature', 'otherUnit', 'C'),
       (28508292, 'other_pc', 'otherAssayType', 'DNA load'),
       (28508293, 'other_pc', 'otherDatumName', 'number of oligo strands'),
       (28508294, 'other_pc', 'otherDatumName', 'number of oligo strands/AuNP'),
       (28508295, 'targeting', 'otherDatumName', 'cellular fluorescence'),
       (28508296, '', 'otherAssayType', 'gene expression'),
       (28508297, 'other_vt', 'otherAssayType', 'nuclease degradation'),
       (28508298, 'targeting', 'otherDatumName', 'fluorescence'),
       (28508299, 'other_pc', 'otherAssayType', 'binding constant'),
       (28508300, 'other_pc', 'otherDatumName', 'binding constant'),
       (28508301, 'binding constant', 'otherUnit', '10E20 1/M/cm'),
       (28508302, 'condition', 'otherName', 'ASODN concentration'),
       (28508303, 'ASODN concentration', 'otherUnit', 'nmol'),
       (28508304, 'condition', 'otherName', 'gold particles concentration'),
       (28508305, 'gold particles concentration', 'otherUnit', 'nmol'),
       (28508306, 'temperature', 'otherUnit', 'Kelvin'),
       (28508307, 'other_pc', 'otherDatumName', 'free energy change'),
       (28508308, 'free energy change', 'otherUnit', 'kcal/mol'),
       (28508309, 'other_pc', 'otherDatumName', 'entropy change'),
       (28508310, 'entropy change', 'otherUnit', 'kcal/mol/K'),
       (28508311, 'other_pc', 'otherDatumName', 'enthalphy change'),
       (28508312, 'other_pc', 'otherDatumName', 'enthalpy change'),
       (28508313, 'enthalpy change', 'otherUnit', 'kcal/mol'),
       (28508314, 'other_pc', 'otherAssayType', 'glutathione resistance'),
       (28508315, 'other_vt', 'otherAssayType', 'stability'),
       (28508316, 'binding constant', 'otherUnit', 'Z/M/cm'),
       (28508317, 'other_vt', 'otherAssayType', 'DTT displacement'),
       (28508318, 'condition', 'otherName', 'number of amine groups'),
       (28508319, 'surface', 'otherDatumName', 'number of F3 peptide/g (Fe)'),
       (28508320, 'number of F3 peptide/g (Fe)', 'otherUnit', 'Z'),
       (28508321, 'surface', 'otherDatumName', 'number of F3 peptides/nanoworm'),
       (28508322, 'surface', 'otherDatumName', 'number of CREKA peptides/g (Fe)'),
       (28508323, 'number of CREKA peptides/g (Fe)', 'otherUnit', 'Z'),
       (28508324, 'surface', 'otherDatumName', 'number of CREKA peptides/nanosphere'),
       (28508325, 'surface', 'otherDatumName', 'number of CREKA peptide/nanosphere'),
       (28508326, 'other_pc', 'otherAssayType', 'photothermal property'),
       (28508327, 'instrument', 'otherManufacturer', 'RPMC Lasers'),
       (28508328, 'technique', 'otherType', 'infrared imaging'),
       (28508329, 'infrared imaging', 'otherInstrument', 'infrared camera'),
       (28508330, 'instrument', 'otherManufacturer', 'FLIR'),
       (28508331, 'other_vt', 'otherAssayType', 'photothermal cytotoxicity'),
       (28508332, 'other_pc', 'otherAssayType', 'X-ray absorption'),
       (28508333, 'imaging', 'otherInstrument', 'micro CT scanner'),
       (28508334, 'other_pc', 'otherAssayType', 'polymer number'),
       (28508335, 'other_pc', 'otherAssayType', 'number of polymers'),
       (28508336, 'other_pc', 'otherAssayType', 'Raman spectroscopy'),
       (28508337, 'technique', 'otherType', 'Raman spectroscopy'),
       (28508338, 'Raman spectroscopy', 'otherInstrument', 'Raman spectrometer'),
       (28508339, 'targeting', 'otherAssayType', 'thermally assisted internalization'),
       (28508340, 'cytotoxicity', 'otherAssayType', 'temperature assisted cell viability'),
       (28508341, '', 'otherAssayType', 'size'),
       (28508342, 'cytotoxicity', 'otherAssayType', 'elevated temperature assisted cell viability'),
       (28508343, 'other_pc', 'otherAssayType', 'nuclear magnetic resonance'),
       (28508344, 'other_pc', 'otherAssayType', 'elctron spin resonance'),
       (28508345, 'technique', 'otherType', 'electron spin resonance'),
       (28508346, 'technique', 'otherType', 'electron spin resonance spectroscopy'),
       (28508347, 'electron spin resonance spectroscopy', 'otherInstrument', 'electron spin resonance spectrometer'),
       (28508348, 'Diameter', 'otherUnit', 'mm'),
       (28508349, 'other_pc', 'otherAssayType', 'pore size'),
       (28508350, 'other_pc', 'otherDatumName', 'pore size'),
       (28508351, 'pore size', 'otherUnit', 'nm'),
       (28508352, 'other_pc', 'otherAssayType', 'porous nanostructure'),
       (28508353, 'other_pc', 'otherDatumName', 'pore volume'),
       (28508354, 'pore volume', 'otherUnit', 'cm^3/g'),
       (28508355, 'other_pc', 'otherDatumName', 'pore width'),
       (28508356, 'pore width', 'otherUnit', 'nm'),
       (28508357, 'other_pc', 'otherDatumName', 'pore surface area'),
       (28508358, 'pore surface area', 'otherUnit', 'm^2/g'),
       (28508359, 'other_pc', 'otherAssayType', 'photoluminescence spectroscopy'),
       (28508360, 'instrument', 'otherManufacturer', 'Princeton Instruments'),
       (28508361, 'other_pc', 'otherAssayType', 'degradation'),
       (28508362, 'other_pc', 'otherAssayType', 'Fourier transform infrared spectroscopy'),
       (28508363, 'other_pc', 'otherAssayType', 'photostability'),
       (28508364, 'other_pc', 'otherAssayType', 'electron spin resonance spectroscopy'),
       (28508365, 'imaging', 'otherInstrument', 'industrial microscope'),
       (28508366, 'instrument', 'otherManufacturer', 'Photometrics'),
       (28508367, 'imaging', 'otherInstrument', 'inverted microscope'),
       (28508368, 'technique', 'otherType', 'multiphoton laser scanning microscopy'),
       (28508369, 'multiphoton laser scanning microscopy', 'otherInstrument', 'multiphoton laser scanning microscope'),
       (28508370, 'confocal laser scanning microscopy', 'otherInstrument',
        'research microscope with fluorescence source'),
       (28508371, 'technique', 'otherType', 'multi photon confocal laser scanning microscopy'),
       (28508372, 'multi photon confocal laser scanning microscopy', 'otherInstrument',
        'multi photon confocal laser scanning microscope '),
       (28508373, 'other_pc', 'otherAssayType', 'drug release'),
       (28508374, 'other_pc', 'otherAssayType', 'fluorescence imaging'),
       (28508375, 'instrument', 'otherManufacturer', 'Caliper Life Sciences'),
       (28508376, '', 'otherAssayType', 'photoluminescence quantum yield'),
       (28508377, 'other_pc', 'otherAssayType', 'photoluminescence quantum yield'),
       (28508378, 'other_pc', 'otherDatumName', 'quantum yield'),
       (28508379, 'quantum yield', 'otherUnit', '%'),
       (28508380, 'treatment duration', 'otherUnit', 'min'),
       (28508381, 'other_pc', 'otherAssayType', 'drug content'),
       (28508382, 'high performance liquid chromatography', 'otherInstrument', 'HPLC system'),
       (28508383, 'instrument', 'otherManufacturer', 'Phenomenex'),
       (28508384, 'other_pc', 'otherDatumName', 'encapsulation efficiency'),
       (28508385, 'encapsulation efficiency', 'otherUnit', '%'),
       (28508386, 'functionalizing entity', 'otherValueUnit', 'Gy'),
       (28508387, 'functionalizing entity', 'otherValueUnit', 'uCi/mg'),
       (28508388, '', 'otherAssayType', 'chelation efficiency'),
       (28508389, 'other_pc', 'otherAssayType', 'chelation property'),
       (28508390, 'other_pc', 'otherDatumName', 'chelation efficiency'),
       (28508391, 'chelation efficiency', 'otherUnit', '%'),
       (28508392, 'other_pc', 'otherAssayType', 'chelation efficiency'),
       (28508393, 'other_pc', 'otherAssayType', 'chelation stability'),
       (28508394, 'cytotoxicity', 'otherDatumName', 'LD50'),
       (28508395, 'LD50', 'otherUnit', 'mg/L'),
       (28508396, 'targeting', 'otherDatumName', 'fraction of internalized nanoparticles'),
       (28508397, 'fraction of internalized nanoparticles', 'otherUnit', '%'),
       (28508398, 'technique', 'otherType', 'deconvolution fluorescence microscopy'),
       (28508399, 'deconvolution fluorescence microscopy', 'otherInstrument', 'deconvolution fluorescence microscope'),
       (28508400, 'instrument', 'otherManufacturer', 'Applied Precision'),
       (28508401, 'targeting', 'otherDatumName', '% internalized nanoparticles'),
       (28508402, '% internalized nanoparticles', 'otherUnit', '%'),
       (28508403, 'condition', 'otherName', 'solvent/water ratio'),
       (28508404, 'cytotoxicity', 'otherDatumName', 'TC50'),
       (28508405, 'TC50', 'otherUnit', 'mg/mL'),
       (28508406, 'cytotoxicity', 'otherDatumName', 'T50'),
       (28508407, 'T50', 'otherUnit', 'mg/mL'),
       (28508408, 'condition', 'otherName', 'nanoparticle treatment'),
       (28508409, 'condition', 'otherName', 'PEG molecular mass'),
       (28508410, 'PEG molecular mass', 'otherUnit', 'kDa'),
       (28508411, 'cytotoxicity', 'otherDatumName', '% cell viability'),
       (28508412, '% cell viability', 'otherUnit', '%'),
       (29097984, 'other_pc', 'otherAssayType', 'fluorescence quenching'),
       (29097985, 'cytotoxicity', 'otherDatumName', '% of apoptotic cells'),
       (29097986, '% of apoptotic cells', 'otherUnit', '%'),
       (29097987, 'cytotoxicity', 'otherDatumName', '% viable cell of control'),
       (29097988, 'cytotoxicity', 'otherDatumName', '% apoptotic cells'),
       (29097989, '% apoptotic cells', 'otherUnit', '%'),
       (29097990, 'cytotoxicity', 'otherDatumName', '% caspase 3/7 activation'),
       (29097991, '% caspase 3/7 activation', 'otherUnit', '%'),
       (29097992, 'condition', 'otherName', 'paclitaxel concentration'),
       (29097993, 'paclitaxel concentration', 'otherUnit', 'nM'),
       (29097994, 'condition', 'otherName', 'ceramide concentration'),
       (29097995, 'ceramide concentration', 'otherUnit', 'uM'),
       (29097996, 'other_pc', 'otherDatumName', 'loading capacity'),
       (29097997, 'loading capacity', 'otherUnit', 'ug/mg'),
       (29097998, 'other_pc', 'otherDatumName', 'loading efficiency'),
       (29097999, 'loading efficiency', 'otherUnit', '%'),
       (29098000, 'other_pc', 'otherDatumName', 'cumulative release'),
       (29098001, 'cumulative release', 'otherUnit', '%'),
       (29098002, 'MINChar', 'entity', 'agglomeration and/or aggregation'),
       (29098003, 'MINChar', 'entity', 'crystal structure/crystallinity'),
       (29098004, 'MINChar', 'entity', 'purity'),
       (29098005, 'MINChar', 'entity', 'shape'),
       (29098006, 'MINChar', 'entity', 'surface area'),
       (29098007, 'MINChar', 'entity', 'surface charge'),
       (29098008, 'MINChar', 'entity', 'surface chemistry'),
       (29098009, 'MINChar', 'entity', 'particle size/size distribution'),
       (29098010, 'MINChar', 'entity', 'chemical composition'),
       (29098011, 'caNano2MINChar', 'purity', 'purity'),
       (29098012, 'caNano2MINChar', 'shape', 'shape'),
       (29098013, 'caNano2MINChar', 'surface area', 'surface area'),
       (29098014, 'caNano2MINChar', 'surface charge', 'surface charge'),
       (29098015, 'caNano2MINChar', 'zeta potential', 'surface charge'),
       (29098016, 'caNano2MINChar', 'attachment', 'surface chemistry'),
       (29098017, 'caNano2MINChar', 'size', 'particle size/size distribution'),
       (29098018, 'caNano2MINChar', 'sample composition', 'chemical composition'),
       (29687808, 'imaging', 'otherInstrument', 'brightfield microscope'),
       (29687809, 'confocal laser scanning microscopy', 'otherInstrument', 'confocal laser scanning microscope'),
       (29687810, 'ceramide concentration', 'otherUnit', 'nM'),
       (29687811, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-6'),
       (29687812, 'physico-chemical assay protocol type', 'otherVersion', '1.1'),
       (29687813, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-7'),
       (29687814, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-10'),
       (29687815, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-8'),
       (29687816, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-9'),
       (29687817, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-11'),
       (29687818, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-14'),
       (29687819, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-12'),
       (29687820, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-13'),
       (29687821, 'in vitro assay protocol type', 'otherName', 'ITA-14'),
       (29687822, 'in vitro assay protocol type', 'otherVersion', '1.0'),
       (29687823, 'in vitro assay protocol type', 'otherName', 'ITA-5.1'),
       (29687824, 'in vitro assay protocol type', 'otherName', 'ITA 5.2'),
       (29687825, 'in vitro assay protocol type', 'otherName', 'GTA-14'),
       (29687826, 'in vitro assay protocol type', 'otherName', 'GTA-11'),
       (29687827, 'in vitro assay protocol type', 'otherName', 'GTA-12'),
       (29687828, 'in vitro assay protocol type', 'otherName', 'ITA-11'),
       (29687829, 'in vitro assay protocol type', 'otherName', 'GTA-1 LDH'),
       (29687830, 'in vitro assay protocol type', 'otherName', 'GTA-1 MTT'),
       (29687831, 'in vitro assay protocol type', 'otherName', 'GTA-2 LDH'),
       (29687832, 'in vitro assay protocol type', 'otherName', 'GTA-2 MTT'),
       (29687833, 'in vitro assay protocol type', 'otherName', 'GTA-3'),
       (29687834, 'in vitro assay protocol type', 'otherName', 'GTA-1'),
       (29687835, 'in vitro assay protocol type', 'otherName', 'GTA-2'),
       (29687836, 'in vitro assay protocol type', 'otherName', 'GTA-4'),
       (29687837, 'in vitro assay protocol type', 'otherName', 'GTA-5'),
       (29687838, 'in vitro assay protocol type', 'otherName', 'GTA-6'),
       (29687839, 'in vitro assay protocol type', 'otherName', 'GTA-7'),
       (29687840, 'in vitro assay protocol type', 'otherName', 'ITA-1'),
       (29687841, 'in vitro assay protocol type', 'otherName', 'ITA-2'),
       (29687842, 'in vitro assay protocol type', 'otherName', 'ITA-3'),
       (29687843, 'in vitro assay protocol type', 'otherName', 'ITA-4'),
       (29687844, 'in vitro assay protocol type', 'otherName', 'ITA-5'),
       (29687845, 'in vitro assay protocol type', 'otherName', 'ITA-6'),
       (29687846, 'in vitro assay protocol type', 'otherName', 'ITA-7'),
       (29687847, 'in vitro assay protocol type', 'otherName', 'ITA-8'),
       (29687848, 'in vitro assay protocol type', 'otherName', 'ITA-9'),
       (29687849, 'in vitro assay protocol type', 'otherName', 'ITA-10'),
       (29687850, 'in vitro assay protocol type', 'otherName', 'ITA-12'),
       (29687851, 'sterility protocol type', 'otherName', 'STE-1'),
       (29687852, 'sterility protocol type', 'otherVersion', '1.0'),
       (29687853, 'sterility protocol type', 'otherName', 'STE-2'),
       (29687854, 'other_pc', 'otherAssayType', 'x-ray photoelectron spectroscopy'),
       (29687855, 'other_pc', 'otherDatumName', 'PLGA O content'),
       (29687856, 'other_pc', 'otherDatumName', 'PLGA N content'),
       (29687857, 'other_pc', 'otherDatumName', 'PLGA C content'),
       (29687858, 'other_pc', 'otherDatumName', 'PbAE O content'),
       (29687859, 'other_pc', 'otherDatumName', 'PbAE N content'),
       (29687860, 'other_pc', 'otherDatumName', 'PbAE C content'),
       (29687861, 'other_pc', 'otherDatumName', 'polymer blend O content'),
       (29687862, 'other_pc', 'otherDatumName', 'polymer blend N content'),
       (29687863, 'other_pc', 'otherDatumName', 'polymer blend C content'),
       (29687864, 'surface', 'otherDatumName', 'PLGA O content'),
       (29687865, 'surface', 'otherDatumName', 'PLGA N content'),
       (29687866, 'surface', 'otherDatumName', 'PLGA C content'),
       (29687867, 'surface', 'otherDatumName', 'PbAE O content'),
       (29687868, 'surface', 'otherDatumName', 'PbAE N content'),
       (29687869, 'surface', 'otherDatumName', 'PbAE C content'),
       (29687870, 'surface', 'otherDatumName', 'polymer blend O content'),
       (29687871, 'surface', 'otherDatumName', 'polymer blend N content'),
       (29687872, 'surface', 'otherDatumName', 'polymer blend C content'),
       (29687873, 'physico-chemical assay protocol type', 'otherName', 'NIST-NCL PCC-1'),
       (29687874, 'physico-chemical assay protocol type', 'otherVersion', '1.0'),
       (29687875, 'transfection', 'otherAssayType', 'transfection'),
       (29687876, 'size', 'otherAssayType', 'surface area'),
       (29687877, 'size', 'otherDatumName', 'pore size'),
       (29687878, 'instrument', 'otherManufacturer', 'Gatan'),
       (29687879, 'instrument', 'otherManufacturer', 'PSS'),
       (29687880, 'other_pc', 'otherAssayType', 'elemental composition'),
       (29687881, '', 'otherInstrument', 'inductively coupled plasma mass spectrometer'),
       (29687882, 'other_pc', 'otherDatumName', 'H'),
       (29687883, 'other_pc', 'otherDatumName', 'N'),
       (29687884, 'other_pc', 'otherDatumName', 'C'),
       (29687885, 'other_pc', 'otherAssayType', 'polycyclic aromatic hydrocarbons content'),
       (29687886, 'other_pc', 'otherDatumName', 'Chrysene'),
       (29687887, 'other_pc', 'otherDatumName', 'Pyrene'),
       (29687888, 'other_pc', 'otherDatumName', 'Fluorene'),
       (29687889, 'other_pc', 'otherDatumName', 'Flouranthene'),
       (29687890, 'other_pc', 'otherDatumName', 'Phenanthrene'),
       (29687891, 'other_pc', 'otherDatumName', 'Acenaphtene'),
       (29687892, 'fullerene', 'otherAverageDiameterUnit', ' nm'),
       (29687893, 'cell counting', 'otherInstrument', 'fluorescence microscope'),
       (29687894, 'instrument', 'otherManufacturer', 'ChemoMetec'),
       (29687895, 'other_vt', 'otherAssayType', 'cell cycle analysis'),
       (29687896, '', 'otherInstrument', 'spectrofluorometer'),
       (29687897, 'other_vt', 'otherAssayType', 'DNA damage'),
       (29687898, 'gel electrophoresis', 'otherInstrument', 'fluorescence microscope'),
       (29687899, 'other_vt', 'otherAssayType', 'DNA purification and cII mutation'),
       (29687900, 'other_pc', 'otherDatumName', 'Fe'),
       (29687901, 'other_pc', 'otherDatumName', 'Co Ni Mn'),
       (29687902, 'Length', 'otherUnit', 'microm'),
       (29687903, 'oxidative stress', 'otherDatumName', 'H2O2 reaction equivalents'),
       (29687904, 'H2O2 reaction equivalents', 'otherUnit', 'nM'),
       (29687905, 'oxidative stress', 'otherDatumName', 'ROS production'),
       (29687906, 'other_vt', 'otherDatumName', 'G2 u'),
       (29687907, 'condition', 'otherName', 'treatment stage'),
       (29687908, 'other_vt', 'otherDatumName', 'G1 u'),
       (29687909, 'condition', 'otherName', 'sample concentration in cell free media'),
       (29687910, 'other_vt', 'otherDatumName', 'p'),
       (29687911, 'condition', 'otherName', 'total number of plaque forming units screened'),
       (29687912, 'condition', 'otherName', 'sample number'),
       (29687913, 'other_vt', 'otherDatumName', 'mutant frequency'),
       (29687914, 'other_vt', 'otherDatumName', 'total number of plaque forming units screened'),
       (29687915, 'instrument', 'otherManufacturer', 'Photal Otsuka'),
       (29687916, 'size', 'otherDatumName', 'number averaged diameter'),
       (29687917, 'number averaged diameter', 'otherUnit', 'nm'),
       (29687918, 'size', 'otherDatumName', 'intensity averaged diameter'),
       (29687919, 'intensity averaged diameter', 'otherUnit', 'nm'),
       (29687920, 'other_pc', 'otherAssayType', 'X-ray diffraction'),
       (29687921, 'other_pc', 'otherDatumName', 'Ti concentration'),
       (29687922, 'technique', 'otherType', 'microfluidics'),
       (29687923, 'microfluidics', 'otherInstrument', 'bioanalyzer'),
       (29687924, 'condition', 'otherName', 'fraction G'),
       (29687925, 'surface', 'otherDatumName', 'number of paclitaxel molecules per SWNT'),
       (29687926, 'datum and condition', 'otherValueType', 'number averaged'),
       (30572544, 'activation method', 'otherType', 'does not require activation'),
       (30572545, 'composing element', 'otherType', 'Internal buffer'),
       (30638080, 'in vitro assay protocol type', 'otherName', 'CHLA1'),
       (31784960, 'physico-chemical assay protocol type', 'otherName', 'Demo-PCC'),
       (31784961, 'instrument', 'otherManufacturer', 'Test'),
       (32505856, 'physico-chemical assay protocol type', 'otherName',
        'Measuring the Size of Nanoparticles in Aqueous Media Using Batch-Mode DLS'),
       (32505857, 'in vitro assay protocol type', 'otherVersion', '1.1'),
       (32505858, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-13'),
       (32505859, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-12'),
       (32505860, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-14'),
       (32505861, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-11'),
       (32505862, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-9'),
       (32505863, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-8'),
       (32505864, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-10'),
       (32505865, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-7'),
       (32505866, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-6'),
       (32505867, 'physico-chemical assay protocol type', 'otherName', 'NIST - NCL Joint Assay Protocol, PCC-1'),
       (32505868, 'in vitro assay protocol type', 'otherName', 'Apoptosis'),
       (32505869, 'in vitro assay protocol type', 'otherName', 'NCL Method GTA-12'),
       (32505870, 'in vitro assay protocol type', 'otherName', 'NCL Method GTA-11'),
       (32505871, 'in vitro assay protocol type', 'otherName', 'NCL Method GTA-14'),
       (32505872, 'in vitro assay protocol type', 'otherName', 'NCL Method GTA-7'),
       (32505873, 'other_pc', 'otherAssayType', 'magnetic property'),
       (32505874, 'magnetic property measurement', 'otherInstrument', 'VSM magnetometer'),
       (32505875, 'instrument', 'otherManufacturer', 'Lakeshore'),
       (32505876, 'other_pc', 'otherDatumName', 'coercivity'),
       (32505877, 'coercivity', 'otherUnit', 'Oe'),
       (32505878, 'other_pc', 'otherDatumName', 'saturation magnetization'),
       (32505879, 'saturation magnetization', 'otherUnit', 'emu/g'),
       (32505880, 'instrument', 'otherManufacturer', 'Rigaku'),
       (32505881, 'other_pc', 'otherAssayType', 'magnetic hyperthermia'),
       (32505882, 'technique', 'otherType', 'temperature measurement'),
       (32505883, 'temperature measurement', 'otherInstrument', 'fiber temperature sensor'),
       (32505884, 'instrument', 'otherManufacturer', 'Luxtron'),
       (33390592, 'technique', 'otherType', 'coulter principle'),
       (33390593, 'coulter principle', 'otherInstrument', 'coulter counter'),
       (33390594, 'other_vt', 'otherAssayType', 'adhesion'),
       (33390595, 'shape', 'otherType', '3D-hemisphere'),
       (33390596, 'size', 'otherDatumName', 'volume'),
       (33390597, 'volume', 'otherUnit', 'um^3'),
       (33390598, 'other_pc', 'otherAssayType', 'porosity'),
       (33390599, 'other_pc', 'otherDatumName', 'maximum porosity'),
       (33390600, 'maximum porosity', 'otherUnit', '%'),
       (33390601, 'other_pc', 'otherDatumName', 'minimum porosity'),
       (33390602, 'minimum porosity', 'otherUnit', '%'),
       (33390603, 'dimension', 'otherUnit', 'um'),
       (33390604, 'Diameter', 'otherUnit', 'um'),
       (33390605, 'surface', 'otherDatumName', 'Zeta Potential'),
       (33980416, 'other_pc', 'otherAssayType', 'concentration'),
       (33980417, 'thermogravimetric analysis', 'otherInstrument', 'thermogravimetric analyzer'),
       (33980418, 'instrument', 'otherManufacturer', 'TA Instruments'),
       (33980419, 'instrument', 'otherManufacturer', 'Asylum Research'),
       (33980420, 'atomic force microscopy', 'otherInstrument', 'AFM probe'),
       (33980421, 'instrument', 'otherManufacturer', 'Budget Sensors'),
       (33980422, 'other_pc', 'otherAssayType', 'filtration'),
       (33980423, 'other_pc', 'otherDatumName', 'diameter'),
       (33980424, 'condition', 'otherName', 'filtration status'),
       (33980425, 'other_pc', 'otherAssayType', 'persistence length'),
       (33980426, 'other_pc', 'otherDatumName', 'persistence length (LP)'),
       (33980427, 'persistence length (LP)', 'otherUnit', 'um'),
       (33980428, 'other_pc', 'otherDatumName', 'contour length (LC)'),
       (33980429, 'contour length (LC)', 'otherUnit', 'um'),
       (34504704, 'size', 'otherDatumName', 'large pore maximum size'),
       (34504705, 'large pore maximum size', 'otherUnit', 'nm'),
       (34504706, 'size', 'otherDatumName', 'large pore minimum size'),
       (34504707, 'large pore minimum size', 'otherUnit', 'nm'),
       (34504708, 'size', 'otherDatumName', 'small pore maximum size'),
       (34504709, 'small pore maximum size', 'otherUnit', 'nm'),
       (34504710, 'size', 'otherDatumName', 'small pore minimum size'),
       (34504711, 'small pore minimum size', 'otherUnit', 'nm'),
       (34504712, 'other_pc', 'otherAssayType', 'siRNA concentration'),
       (34504713, 'other_pc', 'otherAssayType', 'siRNA release'),
       (34734080, 'other_vt', 'otherDatumName', 'critical shear rate'),
       (34734081, 'critical shear rate', 'otherUnit', '1/s'),
       (34734082, 'condition', 'otherName', 'particle diameter'),
       (34734083, 'particle diameter', 'otherUnit', 'um'),
       (35258368, 'biopolymer', 'otherType', 'siRNA'),
       (35258369, 'functionalizing entity', 'otherValueUnit', 'uL/mL'),
       (35258370, 'targeting', 'otherDatumName', 'IC90'),
       (35258371, 'IC90', 'otherUnit', 'pM'),
       (35258372, 'functionalizing entity', 'otherValueUnit', 'uM'),
       (35258373, 'other_vt', 'otherAssayType', 'growth arrest'),
       (35258374, 'functionalizing entity', 'otherValueUnit', 'uL'),
       (35913728, 'instrument', 'otherManufacturer', 'Biacore'),
       (35913729, 'other_vt', 'otherAssayType', 'drug concentration'),
       (35913730, '', 'otherAssayType', 'drug release'),
       (35913731, 'other_vt', 'otherAssayType', '[other]'),
       (35913732, 'other_vt', 'otherDatumName', 'T50'),
       (35913733, 'T50', 'otherUnit', 'h'),
       (35913734, 'other_vt', 'otherAssayType', 'drug release'),
       (36110336, 'other_vv', 'otherAssayType', 'biodistribution'),
       (36110337, 'other_pc', 'otherDatumName', 'T50'),
       (36110338, 'size', 'otherDatumName', 'pore volume fraction'),
       (36110339, 'pore volume fraction', 'otherUnit', '%'),
       (36110340, 'other_vv', 'otherAssayType', 'therapeutic efficacy'),
       (36110341, 'pharmacokinetics', 'otherAssayType', 'pharmacokinetics'),
       (36110342, 'pharmacokinetics', 'otherDatumName', 'T1/2'),
       (36110343, 'T1/2', 'otherUnit', 'h'),
       (36110344, 'pharmacokinetics', 'otherDatumName', 'Clearance'),
       (36110345, 'Clearance', 'otherUnit', 'mL/h/kg'),
       (36110346, 'pharmacokinetics', 'otherDatumName', 'Vz'),
       (36110347, 'Vz', 'otherUnit', 'mL/kg'),
       (36110348, 'pharmacokinetics', 'otherDatumName', 'AUC'),
       (36110349, 'AUC', 'otherUnit', 'h x ng/mL'),
       (36110350, 'pharmacokinetics', 'otherDatumName', 'Cmax'),
       (36110351, 'Cmax', 'otherUnit', 'ng/mL'),
       (36110352, 'other_vv', 'otherAssayType', 'stability'),
       (36110353, 'technique', 'otherType', 'liquid chromatography - mass spectrometry'),
       (36110354, 'other_vv', 'otherAssayType', 'drug accumulation'),
       (36110355, 'other_vv', 'otherDatumName', 'drug amount in tumor'),
       (36110356, 'drug amount in tumor', 'otherUnit', 'ng/mg'),
       (36110357, 'technique', 'otherType', 'liquid scintillation counting'),
       (36110358, 'other_vv', 'otherAssayType', 'toxicity'),
       (36110359, 'toxicology', 'otherAssayType', 'toxicity'),
       (36110360, 'liquid scintillation counting', 'otherInstrument', 'liquid scintillation analyzer'),
       (36110361, 'condition', 'otherName', 'time of exposure'),
       (36110362, 'time of exposure', 'otherUnit', 'h'),
       (36110363, 'other_pc', 'otherDatumName', 'recovered fluorescence'),
       (36110364, 'recovered fluorescence', 'otherUnit', '%'),
       (36110365, 'size', 'otherDatumName', 'recovered fluorescence'),
       (36110366, 'carbon nanotube', 'otherDiameterUnit', 'nm'),
       (36110367, 'carbon nanotube', 'otherAverageLengthUnit', 'nm'),
       (37027840, 'other_pc', 'otherAssayType', 'quantum yield'),
       (37027841, 'spectrophotometry', 'otherInstrument', 'quantum yield measurement system'),
       (37027842, 'condition', 'otherName', 'SA concentration'),
       (37027843, 'SA concentration', 'otherUnit', 'mol%'),
       (37027844, 'other_pc', 'otherAssayType', 'water solubilization efficacy'),
       (37027845, 'condition', 'otherName', 'ODA concentration'),
       (37027846, 'ODA concentration', 'otherUnit', 'mol%'),
       (37027847, 'condition', 'otherName', 'DSPE concentration'),
       (37027848, 'DSPE concentration', 'otherUnit', 'mol%'),
       (37027849, 'condition', 'otherName', 'DSPE-PEG2k concentration'),
       (37027850, 'DSPE-PEG2k concentration', 'otherUnit', 'mol%'),
       (37027851, 'other', 'otherDatumName', 'size'),
       (37027852, 'other_pc', 'otherDatumName', 'temperature'),
       (37027853, 'time of exposure', 'otherUnit', 's'),
       (37027854, 'instrument', 'otherManufacturer', 'Renishaw'),
       (37027855, 'molecular weight', 'otherDatumName', 'average'),
       (37027856, 'condition', 'otherName', 'fraction [G]'),
       (37027857, 'surface', 'otherDatumName', 'calculated zeta potential'),
       (37027858, 'calculated zeta potential', 'otherUnit', 'mV'),
       (37027859, 'surface', 'otherDatumName', 'conductivity'),
       (37027860, 'conductivity', 'otherUnit', 'mS/cm'),
       (37027861, 'other_pc', 'otherDatumName', 'fraction recovered'),
       (37027862, 'time of exposure', 'otherUnit', 'min'),
       (37027863, 'surface', 'otherDatumName', 'fraction recovered'),
       (37027864, 'condition', 'otherName', 'DPPE-COOH concentration'),
       (37027865, 'DPPE-COOH concentration', 'otherUnit', 'mol%'),
       (37027866, 'condition', 'otherName', 'DPPE-NH2 concentration'),
       (37027867, 'DPPE-NH2 concentration', 'otherUnit', 'mol%'),
       (37027868, 'shape', 'otherDatumName', 'aspect ratio'),
       (37027869, 'other_pc', 'otherAssayType', 'uv-vis spectrophotometry'),
       (37027870, 'other_pc', 'otherDatumName', 'molar extinction coefficient'),
       (37027871, 'molar extinction coefficient', 'otherUnit', '1/M 1/cm'),
       (37027872, 'other_pc', 'otherDatumName', 'maximum absorbance wavelength'),
       (37027873, 'maximum absorbance wavelength', 'otherUnit', 'nm'),
       (37027874, 'other_pc', 'otherAssayType', 'photoacoustic spectroscopy'),
       (37027875, 'technique', 'otherType', 'photoacoustic spectrometry'),
       (37027876, 'photoacoustic spectrometry', 'otherInstrument', 'photoacoustic spectrometer'),
       (37027877, 'instrument', 'otherManufacturer', 'Visualsonics'),
       (37027878, 'other_pc', 'otherDatumName', 'normalized photoacoustic signal '),
       (37027879, 'condition', 'otherName', 'photoacoustic excitation intensity'),
       (37027880, 'photoacoustic excitation intensity', 'otherUnit', 'mJ'),
       (37027881, 'condition', 'otherName', 'photoacoustic excitation wavelength'),
       (37027882, 'photoacoustic excitation wavelength', 'otherUnit', 'nm'),
       (37027883, 'molar extinction coefficient', 'otherUnit', 'G 1/M 1/cm'),
       (37027884, 'ex vivo', 'otherAssayType', 'photoacoustic spectroscopy'),
       (37027885, 'ex vivo', 'otherDatumName', 'normalized photoacoustic signal'),
       (37027886, 'other_pc', 'otherAssayType', 'Raman spectrophotometry'),
       (37027887, 'other_pc', 'otherDatumName', 'Raman signal'),
       (37027888, 'condition', 'otherName', 'Raman excitation wavelength'),
       (37027889, 'Raman excitation wavelength', 'otherUnit', 'nm'),
       (37027890, 'technique', 'otherType', 'photoacoustic imaging'),
       (37027891, 'photoacoustic imaging', 'otherInstrument', 'photoacoustic imaging instrument'),
       (37027892, 'instrument', 'otherManufacturer', 'Endra'),
       (37027893, 'imaging', 'otherDatumName', 'photoacoustic signal enhancement'),
       (37027895, 'other_vt', 'otherDatumName', 'limit of detection'),
       (37027896, 'limit of detection', 'otherUnit', 'fM'),
       (37027897, 'other_vt', 'otherAssayType', 'SERS detection sensitivity'),
       (37027898, 'other_vv', 'otherAssayType', 'SERS detection sensitivity'),
       (37027899, 'other_vv', 'otherDatumName', 'limit of detection'),
       (37027900, 'limit of detection', 'otherUnit', 'pM'),
       (37027901, 'other_vt', 'otherAssayType', 'photoacoustic detection sensitivity'),
       (37027902, 'limit of detection', 'otherUnit', 'nM'),
       (37027903, 'other_vv', 'otherAssayType', 'reproducibility of photoacoustic imaging system'),
       (37027904, 'other_vv', 'otherAssayType', 'photothermal response'),
       (37027905, 'imaging', 'otherDatumName', 'T1/2'),
       (37027906, 'T1/2', 'otherUnit', 'min'),
       (37027907, 'imaging', 'otherDatumName', 'photoacoustic background'),
       (37027908, 'imaging', 'otherDatumName', 'tumor size'),
       (37027909, 'tumor size', 'otherUnit', 'mm^3'),
       (37027910, 'file', 'otherType', 'movie'),
       (37027911, 'datum and condition', 'otherValueType', 'number of replicates'),
       (37027912, 'other_vv', 'otherAssayType', 'photoacoustic imaging detection sensitivity'),
       (37027913, 'composing element', 'otherValueUnit', 'ug'),
       (37027914, 'condition', 'otherName', 'sample volume'),
       (37027915, 'sample volume', 'otherUnit', 'uL'),
       (37027916, 'condition', 'otherName', 'photoacoustic sample concentration'),
       (37027917, 'photoacoustic sample concentration', 'otherUnit', 'nM'),
       (37027918, 'sample concentration', 'otherUnit', 'pM'),
       (37027919, 'Raman signal', 'otherUnit', 'au'),
       (37421056, 'other_pc', 'otherAssayType', 'number of gadolinium ions'),
       (37421057, 'other_pc', 'otherDatumName', 'number of gadolinium ions'),
       (37421058, 'other_pc', 'otherAssayType', 'absorbance spectrophotometry'),
       (37421059, 'other_pc', 'otherDatumName', 'absorbance coefficient'),
       (37421060, 'absorbance coefficient', 'otherUnit', '1/cm 1/M'),
       (37421061, 'other_pc', 'otherDatumName', 'absorbance peak wavelength'),
       (37421062, 'absorbance peak wavelength', 'otherUnit', 'nm'),
       (37421063, 'nuclear magnetic resonance', 'otherInstrument', 'custom built small animal MRI scanner'),
       (37421064, 'T1', 'otherUnit', '1/mM 1/s'),
       (37421065, 'other_pc', 'otherAssayType', 'photobleaching'),
       (37421066, 'other_vt', 'otherAssayType', 'MRI detection sensitivity'),
       (37421067, 'other_vt', 'otherAssayType', 'photoacoustic imaging depth penetration'),
       (37421068, 'other_vt', 'otherDatumName', 'penetration depth'),
       (37421069, 'penetration depth', 'otherUnit', 'mm'),
       (37421070, 'other_vt', 'otherAssayType', 'Raman imaging penetration depth'),
       (37421073, 'imaging', 'otherDatumName', 'photoacoustic signal'),
       (37421074, 'photoacoustic signal', 'otherUnit', 'au'),
       (37421075, 'imaging', 'otherDatumName', 'Raman signal'),
       (37421076, 'imaging', 'otherDatumName', 'MRI contrast-to-noise ratio'),
       (37421078, 'photoacoustic signal', 'otherUnit', '%'),
       (37421079, 'imaging', 'otherAssayType', 'intra-operative photoacoustic imaging'),
       (37421080, 'imaging', 'otherAssayType', 'tumor resection'),
       (37421081, 'imaging', 'otherAssayType', 'multimodality imaging'),
       (37421082, 'imaging', 'otherAssayType', 'multimodality imaging sensitivity'),
       (37421083, 'imaging', 'otherAssayType', 'multimodality kinetics'),
       (37421084, 'scanning transmission electron microscopy', 'otherInstrument', 'spectrophotometer'),
       (37421085, 'inductively coupled plasma atomic emission spectroscopy', 'otherInstrument',
        'inductively coupled plasma mass spectrometer'),
       (37421086, 'other_vv', 'otherAssayType', 'health status'),
       (37421087, 'technique', 'otherType', 'liight microscopy'),
       (37421088, 'ex vivo', 'otherAssayType', 'immunohistochemistry'),
       (37421089, 'ex vivo', 'otherAssayType', 'TUNEL'),
       (37421090, 'ex vivo', 'otherAssayType', 'gene expression'),
       (37421091, 'instrument', 'otherManufacturer', 'Eppendorf'),
       (37421092, 'technique', 'otherType', 'fluorometry'),
       (37421093, 'fluorometry', 'otherInstrument', 'fluorometer'),
       (37421094, 'instrument', 'otherManufacturer', 'Invitrogen'),
       (37421095, 'imaging', 'otherAssayType', 'imaging'),
       (37421096, 'technique', 'otherType', 'positron emission tomography'),
       (37421097, 'positron emission tomography', 'otherInstrument', 'positron emission tomograph'),
       (37421098, 'instrument', 'otherManufacturer', 'CTI Concorde Microsystems'),
       (37421099, 'ex vivo', 'otherAssayType', 'imaging'),
       (37912576, 'imaging', 'otherInstrument', 'fluorescence imaging system'),
       (37912577, 'other_pc', 'otherDatumName', 'SB505124 concentration'),
       (37912578, 'SB505124 concentration', 'otherUnit', 'ng/mg'),
       (37912579, 'drug concentration', 'otherUnit', 'ng/mg'),
       (37912580, 'other_pc', 'otherDatumName', 'IL-2 concentration'),
       (37912581, 'IL-2 concentration', 'otherUnit', 'ng/mg'),
       (37912582, 'SB505124 concentration', 'otherUnit', 'ug/mg'),
       (38371328, 'DNA loading efficiency', 'otherUnit', 'ng/mg'),
       (38371329, 'drug loading efficiency', 'otherUnit', 'ng/mg'),
       (38371330, 'drug loading efficiency', 'otherUnit', 'ug/mg'),
       (38764544, 'other_vv', 'otherAssayType', 'adaptive immunity'),
       (38764545, 'other_vv', 'otherAssayType', 'NK depletion'),
       (38764546, 'pharmacokinetics', 'otherDatumName', 'amount of dye'),
       (38764547, 'amount of dye', 'otherUnit', 'percentage of injected dose'),
       (38764548, 'other_vv', 'otherAssayType', 'dye accumulation'),
       (38764549, 'other_vv', 'otherAssayType', 'activation of innate and adaptive immunity'),
       (38764550, 'instrument', 'otherManufacturer', 'LaVision BioTec'),
       (38764551, 'liight microscopy', 'otherInstrument', 'microscope'),
       (38764552, 'other_pc', 'otherAssayType', 'NMR spectroscopy'),
       (39190528, 'biopolymer', 'otherType', 'polysaccharide'),
       (39190529, 'other_vv', 'otherAssayType', 'oral absorption'),
       (39190530, 'other_pc', 'otherAssayType', 'heparin-taurocholic coupling ration'),
       (39190531, 'other_pc', 'otherDatumName', 'heparin-taurocholic acid coupling ratio'),
       (39190532, 'other_pc', 'otherAssayType', 'heparin-taurocholic acid coupling ratio'),
       (39190533, 'other_vv', 'otherDatumName', 'clearance'),
       (39190534, 'clearance', 'otherUnit', 'mL/min/kg'),
       (39190535, 'condition', 'otherName', 'number of replicates'),
       (39190536, 'condition', 'otherName', 'dose'),
       (39190537, 'dose', 'otherUnit', 'mg/kg'),
       (39190538, 'other_vv', 'otherDatumName', 'Cmax'),
       (39190539, 'Cmax', 'otherUnit', 'IU/mL'),
       (39190540, 'other_vv', 'otherDatumName', 'Tmax'),
       (39190541, 'Tmax', 'otherUnit', 'h'),
       (39190542, 'other_vv', 'otherDatumName', 'AUC'),
       (39190543, 'AUC', 'otherUnit', 'ug/mL/min'),
       (39190544, 'other_pc', 'otherDatumName', 'HTA-docetaxel coupling ratio'),
       (39190545, 'HTA-docetaxel coupling ratio', 'otherUnit', 'coupling mole'),
       (39190546, 'other_pc', 'otherAssayType', 'critical micelles concentration'),
       (39190547, 'other_pc', 'otherDatumName', 'critical micelles concentration'),
       (39190548, 'critical micelles concentration', 'otherUnit', 'ug/mL'),
       (39190549, 'other_pc', 'otherDatumName', 'taurocholic acid-heparin coupling ratio'),
       (39190550, 'other_pc', 'otherAssayType', 'heparin-taurocholic acid coupling'),
       (39190551, 'other_pc', 'otherAssayType', 'HTA-docetaxel coupling'),
       (39190552, 'other_pc', 'otherDatumName', 'docetaxel-HTA coupling ratio'),
       (39190553, 'functionalizing entity', 'otherValueUnit', 'mol'),
       (39190554, 'other_pc', 'otherDatumName', 'polydispersity index in FeSSIF'),
       (39190555, 'other_pc', 'otherDatumName', 'diameter in FeSSIF'),
       (39190556, 'diameter in FeSSIF', 'otherUnit', 'nm'),
       (39190557, 'other_pc', 'otherDatumName', 'diameter in FaSSIF'),
       (39190558, 'diameter in FaSSIF', 'otherUnit', 'nm'),
       (39190559, 'imaging', 'otherDatumName', 'Raman signal intensity'),
       (39190560, 'Raman signal intensity', 'otherUnit', 'au'),
       (39190561, 'dose', 'otherUnit', 'pM'),
       (39190562, 'other_pc', 'otherAssayType', 'spectral analysis'),
       (39911424, 'other_vt', 'otherAssayType', 'dendritic cells activation'),
       (39911425, 'other_vv', 'otherAssayType', 'microneedle array assisted drug delivery'),
       (39911426, 'other_vv', 'otherAssayType', 'activation of innate immunity'),
       (39911427, 'other_vv', 'otherAssayType', 'internalization in skin-draining lymph nodes'),
       (39911428, 'other_vv', 'otherAssayType', 'prolonged T cell response'),
       (39911429, 'other_vv', 'otherAssayType', 'antigen-specific T cell response'),
       (39911430, 'other_vv', 'otherAssayType', 'effector function activation'),
       (39911431, 'imaging', 'otherInstrument', 'research upright microscope'),
       (39911432, 'other_vv', 'otherAssayType', 'microneedle array assisted delivery'),
       (39911433, 'technique', 'otherType', 'optical coherence tomography'),
       (39911434, 'optical coherence tomography', 'otherInstrument', 'OCT microscope'),
       (39911435, 'instrument', 'otherManufacturer', 'Michelson Diagnostics'),
       (39911436, 'ex vivo', 'otherAssayType', 'proliferation'),
       (39911437, 'physico-chemical assay protocol type', 'otherName', 'Dynamic Light Scattering Video Protocol'),
       (39911438, 'physico-chemical assay protocol type', 'otherName', 'NCL - Dynamic Light Scattering Video Protocol'),
       (39911439, 'in vitro assay protocol type', 'otherName',
        'NEU - Video Protocol on Therapeutic Gene Delivery and Transfection in Human Pancreatic Cancer Cells using Epidermal Growth Factor Receptor-targeted Gelatin Nanoparticles'),
       (39911440, 'physico-chemical assay protocol type', 'otherName',
        'NCL - Video Protocol on Dynamic Light Scattering'),
       (39911441, 'in vitro assay protocol type', 'otherName',
        'NEU-MAmiji - Video Protocol on Therapeutic Gene Delivery and Transfection in Human Pancreatic Cancer Cells using EGFR-targeted Gelatin Nanoparticles'),
       (39911442, 'physico-chemical assay protocol type', 'otherName',
        'UTSA-KZurab - Gold Nanostar Synthesis with a Silver Seed Mediated Growth Method'),
       (39911443, 'physico-chemical assay protocol type', 'otherName',
        'UTSA-KZurab - Video Protocol on Gold Nanostar Synthesis with a Silver Seed Mediated Growth Method'),
       (39911444, 'in vitro assay protocol type', 'otherName',
        'VAMC-JDouglas - Video Protocol on Antibody Transfection into Neurons as a Tool to Study Disease Pathogenesis'),
       (39911445, 'physico-chemical assay protocol type', 'otherName',
        'UTSA-ZKereselidze - Video Protocol on Gold Nanostar Synthesis with a Silver Seed Mediated Growth Method'),
       (39911446, 'in vivo assay protocol type', 'otherName',
        'UWO-CChoi-Fong - Video Protocol on Evaluation of Nanoparticle Uptake in Tumors in Real Time Using Intravital Imaging'),
       (39911447, 'in vivo assay protocol type', 'otherVersion', '1.0'),
       (39911448, 'in vivo assay protocol type', 'otherName',
        'UWO-CFCho - Video Protocol on Evaluation of Nanoparticle Uptake in Tumors in Real Time Using Intravital Imaging'),
       (39911449, 'in vivo assay protocol type', 'otherName',
        'CWRU-AWen - Video Protocol on Viral Nanoparticles for In vivo Tumor Imaging'),
       (39911450, 'in vitro assay protocol type', 'otherName',
        'CU-AHughes - Video Protocol on Rapid Isolation of Viable Circulating Tumor Cells from Patient Blood Samples'),
       (39911451, 'in vivo assay protocol type', 'otherName',
        'USC-JYHwang - Video Protocol on Analysis of Targeted Viral Protein Nanoparticles Delivered to HER2+ Tumors'),
       (39911452, 'in vivo assay protocol type', 'otherVersion', '1'),
       (39911453, 'in vitro assay protocol type', 'otherName',
        'TU-SAhmadian - Video Protocol on Cellular Toxicity of Nanogenomedicine in MCF-7 Cell Line: MTT assay'),
       (39911454, 'instrument', 'otherManufacturer', 'Abbott'),
       (39911455, 'other_vv', 'otherDatumName', 'heparin concentration'),
       (39911456, 'heparin concentration', 'otherUnit', 'IU/mL'),
       (39911457, 'other_vv', 'otherDatumName', 'tumor volume'),
       (39911458, 'tumor volume', 'otherUnit', 'mm^3'),
       (39911459, 'time of exposure', 'otherUnit', 'day'),
       (39911460, 'other_vv', 'otherDatumName', 'body weight'),
       (39911461, 'body weight', 'otherUnit', 'g'),
       (39911462, 'other_vv', 'otherDatumName', 'tumor weight'),
       (39911463, 'tumor weight', 'otherUnit', 'g'),
       (39911464, 'ex vivo', 'otherDatumName', 'Ki-67 ratio'),
       (39911465, 'size', 'otherDatumName', 'intensity'),
       (39911466, 'intensity', 'otherUnit', '%'),
       (39911467, 'condition', 'otherName', 'diameter'),
       (39911468, 'tumor weight', 'otherUnit', 'mg'),
       (39911469, 'other_pc', 'otherDatumName', 'I374/I390'),
       (39911470, 'condition', 'otherName', 'log(concentration)'),
       (39911471, 'log(concentration)', 'otherUnit', 'mg/mL'),
       (40402944, 'cytotoxicity', 'otherDatumName', 'fluorescence signal'),
       (40402945, 'fluorescence signal', 'otherUnit', 'RFU'),
       (40402946, 'other_pc', 'otherDatumName', 'Raman signal in serum'),
       (40402947, 'Raman signal in serum', 'otherUnit', 'au'),
       (40402948, 'other_pc', 'otherDatumName', 'Raman signal in water'),
       (40402949, 'Raman signal in water', 'otherUnit', 'au'),
       (40402950, 'other_pc', 'otherAssayType', 'sensitivity'),
       (40402951, 'other_pc', 'otherDatumName', 'limit of detection'),
       (40402952, 'condition', 'otherName', 'batch number'),
       (40402953, 'other_pc', 'otherAssayType', 'photoacoustic detection sensitivity'),
       (40402954, 'other_pc', 'otherDatumName', 'photoacoustic signal intensity'),
       (40402955, 'photoacoustic signal intensity', 'otherUnit', 'au'),
       (40402956, 'other_vv', 'otherDatumName', 'Raman signal intensity'),
       (40402957, 'fluorescence signal', 'otherUnit', 'au'),
       (40402958, 'condition', 'otherName', 'number of cells per well'),
       (40402959, 'other_vv', 'otherDatumName', 'fold photoacoustic signal intensity'),
       (40402960, 'imaging', 'otherDatumName', 'postinjection photoacoustic signal'),
       (40402961, 'postinjection photoacoustic signal', 'otherUnit', 'au'),
       (40402962, 'imaging', 'otherDatumName', 'preinjection photoacoustic signal'),
       (40402963, 'preinjection photoacoustic signal', 'otherUnit', 'au'),
       (40402964, 'other_pc', 'otherAssayType', 'morphology'),
       (40402965, 'functionalizing entity', 'otherValueUnit', 'umol'),
       (40402966, 'other_pc', 'otherDatumName', 'coupling molar ratio/Gem'),
       (40402967, 'other_pc', 'otherDatumName', 'coupling molar ratio/ATF'),
       (40402968, 'other_pc', 'otherDatumName', 'coupling molar ratio/IONP'),
       (41091072, 'other_vv', 'otherDatumName', 'photoacoustic signal intensity'),
       (41091073, 'condition', 'otherName', 'mouse setup'),
       (41091074, 'other_vv', 'otherDatumName', 'temperature'),
       (41091075, 'temperature', 'otherUnit', 'C'),
       (41091076, 'condition', 'otherName', 'assay setup'),
       (41091077, 'imaging', 'otherDatumName', 'photoacoustic signal intensity'),
       (41091078, 'other_vv', 'otherDatumName', 'ICP signal'),
       (41091079, 'condition', 'otherName', 'Au standard'),
       (41091080, 'Au standard', 'otherUnit', 'ppm'),
       (41091081, 'other_vv', 'otherDatumName', 'Au concentration'),
       (41091082, 'Au concentration', 'otherUnit', 'ug/g'),
       (41091083, 'condition', 'otherName', 'GNR volume'),
       (41091084, 'GNR volume', 'otherUnit', 'uL'),
       (41811968, 'size', 'otherDatumName', 'volume peak fraction'),
       (41811969, 'volume peak fraction', 'otherUnit', '%'),
       (41811970, 'condition', 'otherName', 'dilution factor'),
       (41811971, 'condition', 'otherName', 'dispensing medium'),
       (41811972, 'size', 'otherDatumName', 'volume peak'),
       (41811973, 'volume peak', 'otherUnit', 'nm'),
       (41811974, 'size', 'otherDatumName', 'intensity peak fraction'),
       (41811975, 'intensity peak fraction', 'otherUnit', '%'),
       (41811976, 'size', 'otherDatumName', 'intensity peak'),
       (41811977, 'intensity peak', 'otherUnit', 'nm'),
       (41811978, 'other_pc', 'otherDatumName', 'volume peak fraction'),
       (41811979, 'other_pc', 'otherDatumName', 'volume peak'),
       (41811980, 'other_pc', 'otherDatumName', 'intensity peak fraction'),
       (41811981, 'other_pc', 'otherDatumName', 'intensity peak'),
       (41811982, 'other_pc', 'otherDatumName', 'polydispersity index'),
       (41811983, 'other_vt', 'otherAssayType', 'migration'),
       (41811984, 'other_vt', 'otherAssayType', 'invasion'),
       (41811985, 'technique', 'otherType', 'inductively coupled plasma optical emission spectrometry'),
       (41811986, 'inductively coupled plasma optical emission spectrometry', 'otherInstrument',
        'inductively coupled plasma optical emission spectrometer'),
       (41811987, 'Clearance', 'otherUnit', 'mL/h'),
       (41811988, 'condition', 'otherName', 'element'),
       (41811989, 'pharmacokinetics', 'otherDatumName', 'Vd'),
       (41811990, 'Vd', 'otherUnit', 'mL/kg'),
       (41811991, 'AUC', 'otherUnit', 'h x umol/mL'),
       (41811992, 'pharmacokinetics', 'otherDatumName', 'C0'),
       (41811993, 'C0', 'otherUnit', 'mmol/L'),
       (41811994, 'pharmacokinetics', 'otherDatumName', 'limit of detection (maximum)'),
       (41811995, 'limit of detection (maximum)', 'otherUnit', 'ng/g'),
       (41811996, 'pharmacokinetics', 'otherDatumName', 'limit of detection (minimum)'),
       (41811997, 'limit of detection (minimum)', 'otherUnit', 'ng/g'),
       (41811998, 'pharmacokinetics', 'otherDatumName', 'limit of quantitation (maximum)'),
       (41811999, 'limit of quantitation (maximum)', 'otherUnit', 'ng/g'),
       (41812000, 'pharmacokinetics', 'otherDatumName', 'limit of quantitation (minimum)'),
       (41812001, 'limit of quantitation (minimum)', 'otherUnit', 'ng/g'),
       (41812002, 'other_vv', 'otherDatumName', 'hematocrit'),
       (41812003, 'hematocrit', 'otherUnit', '%'),
       (41812004, 'other_vv', 'otherDatumName', 'hemoglobin'),
       (41812005, 'hemoglobin', 'otherUnit', 'g/dL'),
       (41812006, 'other_vv', 'otherDatumName', 'red blood cells'),
       (41812007, 'red blood cells', 'otherUnit', 'M/uL'),
       (41812008, 'other_vv', 'otherDatumName', 'white blood cells'),
       (41812009, 'white blood cells', 'otherUnit', '1/L'),
       (41812010, 'targeting', 'otherDatumName', 'optical density increase relative to control'),
       (41812011, 'other_pc', 'otherDatumName', 'number of drug molecules per IONP'),
       (41812012, 'high performance liquid chromatography', 'otherInstrument', 'solvent delivery module'),
       (41812013, 'high performance liquid chromatography', 'otherInstrument', 'detector'),
       (41812014, 'condition', 'otherName', 'cathepsin B'),
       (41812015, 'cathepsin B', 'otherUnit', 'uM'),
       (41812016, 'condition', 'otherName', 'serum concentration'),
       (41812017, 'serum concentration', 'otherUnit', '%'),
       (41812018, 'cytotoxicity', 'otherDatumName', 'cell viability'),
       (41812019, 'cell viability', 'otherUnit', '%'),
       (41812020, 'condition', 'otherName', 'drug equivalent concentration'),
       (41812021, 'drug equivalent concentration', 'otherUnit', 'uM'),
       (41812022, 'other_pc', 'otherDatumName', 'number of ATF per IONP'),
       (41812023, 'technique', 'otherType', 'magnetic resonance imaging'),
       (41812024, 'magnetic resonance imaging', 'otherInstrument', 'small animal MRI scanner'),
       (41812025, 'instrument', 'otherManufacturer', 'Oxford Magnet Technology'),
       (41812026, 'magnetic resonance imaging', 'otherInstrument', 'console'),
       (41812027, 'other_vv', 'otherDatumName', 'MRI signal change'),
       (41812028, 'MRI signal change', 'otherUnit', '%'),
       (41812029, 'condition', 'otherName', 'time post injection'),
       (41812030, 'time post injection', 'otherUnit', 'week'),
       (41812031, 'condition', 'otherName', 'elapsed time'),
       (41812032, 'elapsed time', 'otherUnit', 'week'),
       (41812033, 'liquid chromatography - mass spectrometry', 'otherInstrument',
        'liquid chromatograph mass spectrometer'),
       (41812034, 'drug loading efficiency', 'otherUnit', '%(w/w)'),
       (41812035, 'other_pc', 'otherDatumName', 'drug remaining fraction'),
       (41812036, 'drug remaining fraction', 'otherUnit', '%'),
       (41812037, 'dose', 'otherUnit', 'mg/mL'),
       (41812038, 'pharmacokinetics', 'otherDatumName', 'Vdss'),
       (41812039, 'Vdss', 'otherUnit', 'mL/kg'),
       (41812040, 'pharmacokinetics', 'otherDatumName', 'mean residence time'),
       (41812041, 'mean residence time', 'otherUnit', 'h'),
       (41812042, 'pharmacokinetics', 'otherDatumName', 'fraction of injected dose'),
       (41812043, 'fraction of injected dose', 'otherUnit', '%'),
       (41812044, 'time post injection', 'otherUnit', 'hour'),
       (41812045, 'other_vv', 'otherDatumName', 'fraction of injected dose'),
       (41812046, 'condition', 'otherName', 'organ'),
       (41812047, 'time post injection', 'otherUnit', 'min'),
       (42336256, 'condition', 'otherName', 'viscosity'),
       (42336257, 'viscosity', 'otherUnit', 'cP'),
       (42336258, 'point of contact', 'otherRole', 'Program Manager'),
       (42336259, 'size exclusion chromatography with multi-angle laser light scattering', 'otherInstrument',
        'isocratic pump'),
       (42336260, 'size exclusion chromatography with multi-angle laser light scattering', 'otherInstrument',
        'autosampler'),
       (42336261, 'size exclusion chromatography with multi-angle laser light scattering', 'otherInstrument',
        'detector'),
       (42336262, 'size exclusion chromatography with multi-angle laser light scattering', 'otherInstrument',
        'refractive index detector'),
       (42336263, 'asymmetrical flow field-flow fractionation with multi-angle laser light scattering',
        'otherInstrument', 'multi-angle light scattering detector'),
       (42500096, 'high performance liquid chromatography', 'otherInstrument', 'degasser'),
       (42500097, 'high performance liquid chromatography', 'otherInstrument', 'capillary pump'),
       (42500098, 'purity', 'otherDatumName', 'limit of detection'),
       (42500099, 'limit of detection', 'otherUnit', 'ug/mL'),
       (42500100, 'high performance liquid chromatography', 'otherInstrument', '[other]'),
       (42500101, 'high performance liquid chromatography', 'otherInstrument', 'integrator'),
       (42500102, 'other_pc', 'otherDatumName', 'half life'),
       (42500103, 'half life', 'otherUnit', 'hour'),
       (42500104, 'sterility protocol type', 'otherName', 'Gel-Clot LAL Assay'),
       (42500105, 'in vitro assay protocol type', 'otherName', 'Detection of Microbial Contamination'),
       (42500106, 'sterility protocol type', 'otherName', 'Detection of Microbial Contamination'),
       (42500107, 'sterility', 'otherDatumName', 'endotoxin level'),
       (42500108, 'endotoxin level', 'otherUnit', 'EU'),
       (42500109, 'toxicology', 'otherAssayType', 'cell viability'),
       (42500110, 'other_vv', 'otherDatumName', 'elimination half-life'),
       (42500111, 'elimination half-life', 'otherUnit', 'h'),
       (42500112, 'other_vv', 'otherDatumName', 'distribution half-life'),
       (42500113, 'distribution half-life', 'otherUnit', 'h'),
       (42500114, 'condition', 'otherName', 'excretion'),
       (42500115, 'other_vv', 'otherDatumName', 'tumor/muscle ratio'),
       (42500116, 'physico-chemical assay protocol type', 'otherName', 'STE-1'),
       (42500117, 'physico-chemical assay protocol type', 'otherName', 'STE-2'),
       (42500118, 'physico-chemical assay protocol type', 'otherName', ' NCL Method STE-1.3'),
       (42500119, 'other_pc', 'otherAssayType', 'sterility'),
       (42500120, 'other_pc', 'otherDatumName', 'endotoxin level'),
       (42500121, 'endotoxin level', 'otherUnit', 'EU/mg'),
       (42500122, 'other_vv', 'otherDatumName', 'tumor volume ratio'),
       (42500123, 'condition', 'otherName', 'treatment type'),
       (42500124, 'instrument', 'otherManufacturer', 'Xenogen'),
       (42500125, 'imaging', 'otherDatumName', 'relative bioluminescence intensity'),
       (42500126, 'relative bioluminescence intensity', 'otherUnit', '%'),
       (42500127, 'time of exposure', 'otherUnit', 'week'),
       (42500128, 'imaging', 'otherDatumName', 'bioluminescence photon intensity'),
       (42500129, 'bioluminescence photon intensity', 'otherUnit', 'photons/sec/cm^2/sr'),
       (42500130, 'molecular weight', 'otherDatumName', 'molecular weight filtered sample'),
       (42500131, 'molecular weight filtered sample', 'otherUnit', 'kDa'),
       (42500132, 'molecular weight', 'otherDatumName', 'molecular weight unfiltered sample 2'),
       (42500133, 'molecular weight unfiltered sample 2', 'otherUnit', 'kDa'),
       (42500134, 'molecular weight', 'otherDatumName', 'molecular weight unfiltered sample 1'),
       (42500135, 'molecular weight unfiltered sample 1', 'otherUnit', 'kDa'),
       (43450368, 'instrument', 'otherManufacturer', 'Radim'),
       (43450369, 'instrument', 'otherManufacturer', 'Siemens'),
       (43450370, 'other_pc', 'otherDatumName', 'iron oxide particle size'),
       (43450371, 'iron oxide particle size', 'otherUnit', 'nm'),
       (43450372, 'instrument', 'otherManufacturer', 'Princeton Applied Research'),
       (43450373, 'magnetic property measurement', 'otherInstrument', 'magnetometer'),
       (43450374, 'instrument', 'otherManufacturer', 'FW Bell'),
       (43450375, 'magnetic property measurement', 'otherInstrument', 'magnet power supply'),
       (43450376, 'instrument', 'otherManufacturer', 'Danfysik'),
       (43450377, 'temperature measurement', 'otherInstrument', 'induction heating system'),
       (43450378, 'instrument', 'otherManufacturer', 'Ameritherm'),
       (43450379, 'instrument', 'otherManufacturer', 'Jasco'),
       (43450380, 'other_pc', 'otherDatumName', 'amount of encapsulated drug '),
       (43450381, 'amount of encapsulated drug ', 'otherUnit', 'ug/mg'),
       (43450382, 'loading capacity', 'otherUnit', '%'),
       (43450383, 'other_pc', 'otherAssayType', 'hyperthermia induced drug release'),
       (43450384, 'technique', 'otherType', 'alternating magnetic field'),
       (43450385, 'alternating magnetic field', 'otherInstrument', 'induction heating system'),
       (43450386, 'alternating magnetic field', 'otherInstrument', 'fiber temperature sensor'),
       (43450387, 'instrument', 'otherManufacturer', 'SAEC Radim'),
       (43712512, 'imaging', 'otherInstrument', 'confocal microscope'),
       (43712513, 'other_vv', 'otherAssayType', 'gene expression'),
       (43712514, 'other_pc', 'otherAssayType', 'antisense load'),
       (43712515, 'other_pc', 'otherDatumName', 'antisense load'),
       (43712516, 'antisense load', 'otherUnit', 'strands per AuNP'),
       (43712517, 'targeting', 'otherDatumName', 'relative p21 mRNA expression'),
       (43712518, 'condition', 'otherName', 'time post doxorubicin treatment'),
       (43712519, 'time post doxorubicin treatment', 'otherUnit', 'h'),
       (43712520, 'targeting', 'otherDatumName', 'relative Bcl2L12 mRNA expression'),
       (43712521, 'other_vt', 'otherAssayType', 'mRNA cleavage'),
       (43712522, 'condition', 'otherName', 'time post SNA injection'),
       (43712523, 'time post SNA injection', 'otherUnit', 'day'),
       (43712524, 'other_vv', 'otherDatumName', 'mouse 1 tumor weight'),
       (43712525, 'mouse 1 tumor weight', 'otherUnit', 'mg'),
       (43712526, 'other_vv', 'otherDatumName', 'mouse 2 tumor weight'),
       (43712527, 'mouse 2 tumor weight', 'otherUnit', 'mg'),
       (43712528, 'other_vv', 'otherDatumName', 'mouse 3 tumor weight'),
       (43712529, 'mouse 3 tumor weight', 'otherUnit', 'mg'),
       (43712530, 'other_vv', 'otherDatumName', 'mouse 4 tumor weight'),
       (43712531, 'mouse 4 tumor weight', 'otherUnit', 'mg'),
       (43712532, 'other_vv', 'otherDatumName', 'mouse 5 tumor weight'),
       (43712533, 'mouse 5 tumor weight', 'otherUnit', 'mg'),
       (43712534, 'other_vv', 'otherDatumName', 'mouse expired'),
       (43712535, 'technique', 'otherType', 'staining'),
       (43712536, 'staining', 'otherInstrument', 'staining system'),
       (43712537, 'other_vv', 'otherDatumName', 'aCasp-3 (periphery)'),
       (43712538, 'aCasp-3 (periphery)', 'otherUnit', 'number of cells/high power field'),
       (43712539, 'other_vv', 'otherDatumName', 'aCasp-3 (center)'),
       (43712540, 'aCasp-3 (center)', 'otherUnit', 'number of cells/high power field'),
       (43712541, 'other_vv', 'otherDatumName', 'TUNEL (periphery)'),
       (43712542, 'TUNEL (periphery)', 'otherUnit', 'number of cells/high power field'),
       (43712543, 'other_vv', 'otherDatumName', 'TUNEL (center)'),
       (43712544, 'TUNEL (center)', 'otherUnit', 'number of cells/high power field'),
       (43712545, 'instrument', 'otherManufacturer', 'Thermo Fisher'),
       (43712546, 'pharmacokinetics', 'otherDatumName', 'total body clearance CLT'),
       (43712547, 'total body clearance CLT', 'otherUnit', 'mL/min/kg'),
       (43712548, 'condition', 'otherName', 'hybrid coefficient B'),
       (43712549, 'hybrid coefficient B', 'otherUnit', 'nmol/L'),
       (43712550, 'AUC', 'otherUnit', 'nmol x min/L'),
       (43712551, 'pharmacokinetics', 'otherDatumName', 'volume of distribution during elimination phase Vdbeta'),
       (43712552, 'volume of distribution during elimination phase Vdbeta', 'otherUnit', 'L/kg'),
       (43712553, 'pharmacokinetics', 'otherDatumName', 'central compartment volume Vc'),
       (43712554, 'central compartment volume Vc', 'otherUnit', 'L/kg'),
       (43712555, 'pharmacokinetics', 'otherDatumName', 'SNA elimination half-life beta t1/2 '),
       (43712556, 'SNA elimination half-life beta t1/2 ', 'otherUnit', 'min'),
       (43712557, 'pharmacokinetics', 'otherDatumName', 'SNA distribution half-life alpha t1/2'),
       (43712558, 'SNA distribution half-life alpha t1/2', 'otherUnit', 'min'),
       (43712559, 'pharmacokinetics', 'otherDatumName', 'rate constant for elimination phase beta'),
       (43712560, 'rate constant for elimination phase beta', 'otherUnit', '1/min'),
       (43712561, 'pharmacokinetics', 'otherDatumName', 'rate constant for distribution phase alpha'),
       (43712562, 'rate constant for distribution phase alpha', 'otherUnit', '1/min'),
       (43712563, 'pharmacokinetics', 'otherDatumName', 'hybrid coefficient A'),
       (43712564, 'hybrid coefficient A', 'otherUnit', 'nmol/L'),
       (43712565, 'cytotoxicity', 'otherDatumName', 'relative Bcl2L12 mRNA expression'),
       (43712566, 'cytotoxicity', 'otherDatumName', 'relative p21 mRNA expression'),
       (43712567, 'toxicology', 'otherDatumName', 'cytokine level'),
       (43712568, 'cytokine level', 'otherUnit', 'pg/mL'),
       (43712569, 'condition', 'otherName', 'cytokine'),
       (43712570, 'other_vv', 'otherDatumName', 'number of gold nanoparticles/g tissue'),
       (43712571, 'molecular weight', 'otherDatumName', 'large pore maximum size'),
       (43712572, 'other_vt', 'otherAssayType', 'scavenger receptor inhibition'),
       (43712573, 'other_vt', 'otherAssayType', 'blood-brain barrier'),
       (43712575, 'imaging', 'otherAssayType', 'blood-brain barrier penetration'),
       (43712576, 'imaging', 'otherInstrument', 'preclinical imaging system'),
       (44269568, 'other_vv', 'otherAssayType', 'blood-brain barrier penetration'),
       (44269569, 'infrared imaging', 'otherInstrument', 'preclinical imaging system'),
       (44269570, 'other_vt', 'otherDatumName', 'SNAs/cell'),
       (44269571, 'condition', 'otherName', 'concentration'),
       (44269572, 'concentration', 'otherUnit', 'ug/mL'),
       (44269573, 'condition', 'otherName', 'scavenger receptor inhibitor'),
       (44269574, 'other_vv', 'otherAssayType', 'localization'),
       (44269575, 'technique', 'otherType', 'laser ablation'),
       (44269576, 'laser ablation', 'otherInstrument', 'laser ablation system'),
       (44269577, 'instrument', 'otherManufacturer', 'New Wave Research'),
       (44826624, 'cell counting', 'otherInstrument', 'cytometer'),
       (44826625, 'physico-chemical assay protocol type', 'otherName', 'demo'),
       (44826626, 'gel permeation chromatography', 'otherInstrument', 'double gel permeation column'),
       (44826627, 'instrument', 'otherManufacturer', 'Polymer Laboratories'),
       (44826628, 'high performance liquid chromatography', 'otherInstrument', 'C-18 reverse phase column'),
       (44826629, 'instrument', 'otherManufacturer', 'Richard Scientific'),
       (44826630, 'high performance liquid chromatography', 'otherInstrument', 'fluorescence detector'),
       (44826631, 'instrument', 'otherManufacturer', 'Groton Technology'),
       (44826632, 'condition', 'otherName', 'drug status'),
       (44826633, 'drug concentration', 'otherUnit', '%'),
       (44826634, '', 'otherAssayType', 'fractionation'),
       (44826635, 'other_pc', 'otherAssayType', 'fractionation'),
       (44826636, 'other_pc', 'otherDatumName', 'membrane retention'),
       (44826637, 'membrane retention', 'otherUnit', '%'),
       (44826638, 'condition', 'otherName', 'molecular weight cut-off'),
       (44826639, 'molecular weight cut-off', 'otherUnit', 'kDa'),
       (44826640, 'technique', 'otherType', 'computed tomography'),
       (44826641, 'computed tomography', 'otherInstrument', 'small animal CT scanner'),
       (44826642, 'other_pc', 'otherAssayType', 'radioisotope content'),
       (44826643, 'other_pc', 'otherDatumName', 'radioisotope content'),
       (44826644, 'radioisotope content', 'otherUnit', '%(w/w)'),
       (44826645, 'fraction of injected dose', 'otherUnit', '%ID/cm^3'),
       (44826646, 'pharmacokinetics', 'otherDatumName', 'plasma concentration low molecular component (modeled)'),
       (44826647, 'plasma concentration low molecular component (modeled)', 'otherUnit', '%ID/cm^3'),
       (44826648, 'pharmacokinetics', 'otherDatumName', 'plasma fraction of injected dose'),
       (44826649, 'plasma fraction of injected dose', 'otherUnit', '%ID/cm^3'),
       (44826650, 'pharmacokinetics', 'otherDatumName', 'interstitial fraction of injected nanoparticles (modeled)'),
       (44826651, 'interstitial fraction of injected nanoparticles (modeled)', 'otherUnit', '%ID/cm^3'),
       (44826652, 'pharmacokinetics', 'otherDatumName', 'interstitial fraction of injected dose (modeled)'),
       (44826653, 'interstitial fraction of injected dose (modeled)', 'otherUnit', '%ID/cm^3'),
       (44826654, 'pharmacokinetics', 'otherDatumName', 'tumor total fraction of injected dose (modeled)'),
       (44826655, 'pharmacokinetics', 'otherDatumName', 'tumor fraction of injected dose'),
       (44826656, 'tumor fraction of injected dose', 'otherUnit', '%ID/cm^3'),
       (44826657, 'pharmacokinetics', 'otherDatumName', 'plasma concentration of low molecular component at time 0'),
       (44826658, 'plasma concentration of low molecular component at time 0', 'otherUnit', '%ID/mL'),
       (44826659, 'pharmacokinetics', 'otherDatumName', 'plasma concentration of nanoparticles at time 0'),
       (44826660, 'plasma concentration of nanoparticles at time 0', 'otherUnit', '%ID/mL'),
       (44826661, 'pharmacokinetics', 'otherDatumName', 'rate of elimination of low molecular component from plasma'),
       (44826662, 'rate of elimination of low molecular component from plasma', 'otherUnit', '1/s'),
       (44826663, 'pharmacokinetics', 'otherDatumName', 'rate of elimination of nanoparticles from plasma'),
       (44826664, 'rate of elimination of nanoparticles from plasma', 'otherUnit', '1/s'),
       (44826665, 'pharmacokinetics', 'otherDatumName', 'tumor apparent permeability for low molecular component'),
       (44826666, 'tumor apparent permeability for low molecular component', 'otherUnit', 'cm/s'),
       (44826667, 'pharmacokinetics', 'otherDatumName', 'tumor apparent permeability for nanoparticles'),
       (44826668, 'tumor apparent permeability for nanoparticles', 'otherUnit', 'cm/s'),
       (44826669, 'pharmacokinetics', 'otherDatumName', 'nanoparticles uptake by tumor cells'),
       (44826670, 'nanoparticles uptake by tumor cells', 'otherUnit', '1/s'),
       (44826671, 'pharmacokinetics', 'otherDatumName', 'mouse plasma volume'),
       (44826672, 'mouse plasma volume', 'otherUnit', 'mL'),
       (44826673, 'pharmacokinetics', 'otherDatumName', 'tumor vascular volume'),
       (44826674, 'tumor vascular volume', 'otherUnit', 'mL'),
       (44826675, 'pharmacokinetics', 'otherDatumName', 'tumor interstitial volume'),
       (44826676, 'tumor interstitial volume', 'otherUnit', 'mL'),
       (44826677, 'pharmacokinetics', 'otherDatumName', 'tumor cellular volume'),
       (44826678, 'tumor cellular volume', 'otherUnit', 'mL'),
       (44826679, 'pharmacokinetics', 'otherDatumName', 'tumor vasculature surface area'),
       (44826680, 'tumor vasculature surface area', 'otherUnit', 'cm^2'),
       (44826681, 'pharmacokinetics', 'otherDatumName', 'hematocrit'),
       (44826682, 'fraction of injected dose', 'otherUnit', '%/cm^3'),
       (45711360, 'other_pc', 'otherAssayType', 'magnetic separation efficiency'),
       (45711361, 'technique', 'otherType', 'magnetic separation'),
       (45711362, 'magnetic separation', 'otherInstrument', 'magnetic separation instrument'),
       (45711363, 'other_pc', 'otherDatumName', 'capture efficiency'),
       (45711364, 'capture efficiency', 'otherUnit', '%'),
       (45711365, 'condition', 'otherName', 'medium'),
       (45711366, 'time', 'otherUnit', 'month'),
       (45711367, 'other_pc', 'otherDatumName', 'magnetic moment'),
       (45711368, 'magnetic moment', 'otherUnit', 'emu/g'),
       (45711369, 'condition', 'otherName', 'magnetic field'),
       (45711370, 'magnetic field', 'otherUnit', 'kOe'),
       (45711371, 'other_pc', 'otherDatumName', 'absorbance'),
       (45711372, 'other_pc', 'otherAssayType', 'labeling confimation'),
       (45711373, 'other_pc', 'otherAssayType', 'recovery'),
       (45711374, 'other_pc', 'otherDatumName', 'recovery'),
       (45711375, 'recovery', 'otherUnit', '%'),
       (45711376, 'targeting', 'otherDatumName', 'capture efficiency'),
       (45711377, 'other_pc', 'otherDatumName', 'number of affinity binding sites'),
       (45711378, 'other_pc', 'otherAssayType', 'number of binding sites'),
       (45711379, 'other_vt', 'otherDatumName', 'capture efficiency'),
       (45711380, 'concentration', 'otherUnit', 'mg/mL'),
       (45711381, 'targeting', 'otherDatumName', 'capture cell number'),
       (45711382, 'condition', 'otherName', 'spiked cell number'),
       (45711383, 'confocal laser scanning microscopy', 'otherInstrument', 'imaging system'),
       (45711384, 'instrument', 'otherManufacturer', 'Andor'),
       (45711385, 'confocal laser scanning microscopy', 'otherInstrument', 'confocal scanner unit'),
       (45711386, 'instrument', 'otherManufacturer', 'Yokogawa'),
       (45711387, 'confocal laser scanning microscopy', 'otherInstrument', 'EMCCD camera'),
       (45711388, 'targeting', 'otherDatumName', 'number of tumor cells measurement 3'),
       (45711389, 'targeting', 'otherDatumName', 'number of tumor cells measurement 2'),
       (45711390, 'targeting', 'otherDatumName', 'number of tumor cells measurement 1'),
       (45711391, 'targeting', 'otherDatumName', 'number of tumor cells'),
       (45711392, 'number of tumor cells', 'otherUnit', '%'),
       (45711393, 'datum and condition', 'otherValueType', 'relative standard deviation'),
       (45711394, 'targeting', 'otherDatumName', 'number of circulating tumor cells'),
       (45711395, 'sample volume', 'otherUnit', 'mL'),
       (45711396, 'condition', 'otherName', 'gender'),
       (45711397, 'condition', 'otherName', 'cancer type'),
       (45711398, 'other_pc', 'otherDatumName', 'number of affinity sites'),
       (45711399, 'other_vv', 'otherDatumName', 'mice 7 tumor weight'),
       (45711400, 'mice 7 tumor weight', 'otherUnit', 'mg'),
       (45711401, 'condition', 'otherName', 'time post tumor inoculation'),
       (45711402, 'time post tumor inoculation', 'otherUnit', 'day'),
       (45711403, 'other_vv', 'otherDatumName', 'mouse 6 tumor weight'),
       (45711404, 'mouse 6 tumor weight', 'otherUnit', 'mg'),
       (45711405, 'targeting', 'otherDatumName', 'relative Bcl2L12 expression'),
       (45711406, 'other_vv', 'otherDatumName', 'relative Bcl2L12 expression'),
       (45711407, 'condition', 'otherName', 'number of treatments'),
       (46268416, 'gel permeation chromatography', 'otherInstrument', 'degasser'),
       (46268417, 'gel permeation chromatography', 'otherInstrument', 'pump'),
       (46268418, 'gel permeation chromatography', 'otherInstrument', 'autosampler'),
       (46268419, 'molecular weight', 'otherDatumName', 'maximum number of music acid repeat units'),
       (46268420, 'molecular weight', 'otherDatumName', 'minimum number of music acid repeat units'),
       (46268421, 'gel permeation chromatography', 'otherInstrument', 'refractive index detector'),
       (46268422, 'other_pc', 'otherAssayType', 'composition'),
       (46268423, 'other_pc', 'otherAssayType', 'dissociation constant'),
       (46268424, 'other_pc', 'otherDatumName', 'dissociation constant'),
       (46268425, 'condition', 'otherName', 'chemical compound'),
       (46268426, 'binding constant', 'otherUnit', '1/M'),
       (46268427, 'toxicology', 'otherDatumName', 'mouse expired'),
       (46268428, 'condition', 'otherName', 'drug equivalent dose'),
       (46268429, 'drug equivalent dose', 'otherUnit', 'mg/kg'),
       (46268430, 'toxicology', 'otherDatumName', 'time of maximum body weight loss'),
       (46268431, 'time of maximum body weight loss', 'otherUnit', 'day'),
       (46268432, 'toxicology', 'otherDatumName', 'maximum body weight loss'),
       (46268433, 'maximum body weight loss', 'otherUnit', '%'),
       (46268434, 'toxicology', 'otherDatumName', 'body weight change'),
       (46268435, 'body weight change', 'otherUnit', '%'),
       (46268436, 'time post injection', 'otherUnit', 'day'),
       (46268437, 'toxicology', 'otherDatumName', 'body weight'),
       (46268438, 'high performance liquid chromatography', 'otherInstrument', 'reverse phase column'),
       (46268439, 'high performance liquid chromatography', 'otherInstrument', 'guard column'),
       (46268440, 'other_pc', 'otherDatumName', 'conjugated drug content'),
       (46268441, 'conjugated drug content', 'otherUnit', 'wt%'),
       (46268442, 'other_pc', 'otherDatumName', 'maximum number of MAP-CPT conjugates per particle'),
       (46268443, 'other_pc', 'otherDatumName', 'minimum number of MAP-CPT conjugates per paritcle '),
       (46268444, 'Vd', 'otherUnit', 'mL'),
       (46268445, 'AUC', 'otherUnit', 'ug h/mL'),
       (46268446, 'pharmacokinetics', 'otherDatumName', 'elimination half-life'),
       (46268447, 'pharmacokinetics', 'otherDatumName', 'redistribution half-life'),
       (46268448, 'redistribution half-life', 'otherUnit', 'h'),
       (46268449, 'pharmacokinetics', 'otherDatumName', 'plasma concentration of unconjugated CPT'),
       (46268450, 'plasma concentration of unconjugated CPT', 'otherUnit', 'ug/mL'),
       (46268451, 'time post injection', 'otherUnit', 'h'),
       (46268452, 'pharmacokinetics', 'otherDatumName', 'plasma concentration of polymer bound CPT'),
       (46268453, 'plasma concentration of polymer bound CPT', 'otherUnit', 'ug/mL'),
       (46268454, 'other_pc', 'otherDatumName', 'maximum size'),
       (46268455, 'other_pc', 'otherDatumName', 'minimum size'),
       (46268456, 'targeting', 'otherDatumName', 'drug internalization'),
       (46268457, 'drug internalization', 'otherUnit', 'ng/mg'),
       (46268458, 'elapsed time', 'otherUnit', 'hour'),
       (46268459, 'elapsed time', 'otherUnit', 'day'),
       (46268460, 'other_vv', 'otherDatumName', 'fraction of unconjugated CPT'),
       (46268461, 'fraction of unconjugated CPT', 'otherUnit', '% per organ'),
       (46268462, 'fraction of injected dose', 'otherUnit', '%ID total CPT/g'),
       (46268463, 'fraction of unconjugated CPT', 'otherUnit', '%'),
       (46268464, 'other_vv', 'otherDatumName', 'total CPT concentration'),
       (46268465, 'total CPT concentration', 'otherUnit', 'ug/mL'),
       (46268466, 'in vivo assay protocol type', 'otherName', 'asd'),
       (46268467, 'in vivo assay protocol type', 'otherVersion', 'asd'),
       (46268468, 'other_pc', 'otherDatumName', 'size'),
       (46268469, 'condition', 'otherName', 'antibody concentration'),
       (46268470, 'antibody concentration', 'otherUnit', 'mg/mL'),
       (46268471, 'drug equivalent concentration', 'otherUnit', 'ug/mL'),
       (46268472, 'condition', 'otherName', 'herceptin equivalent dose'),
       (46268473, 'herceptin equivalent dose', 'otherUnit', 'mg/kg'),
       (46268474, 'condition', 'otherName', 'phenylboronic acid'),
       (46268475, 'other_vv', 'otherDatumName', 'p-value vs saline'),
       (46268476, 'other_vv', 'otherDatumName', 'number of mice with tumors regressed to zero at the end of study'),
       (46268477, 'other_vv', 'otherDatumName', 'number of mice euthanized'),
       (46268478, 'other_vv', 'otherDatumName', 'initial number of mice'),
       (46268479, 'other_vv', 'otherDatumName', 'final number of mice'),
       (46268480, 'other_vv', 'otherDatumName', 'number of mice expired due to treatment'),
       (46268481, 'other_vv', 'otherDatumName', 'number of mice expired due to non-treatment'),
       (46268482, 'molecular weight', 'otherDatumName', 'refractive index increment'),
       (46268483, 'refractive index increment', 'otherUnit', 'mL/g'),
       (46661632, 'in vitro assay protocol type', 'otherName', 'Test submit protocol in prod 12162014'),
       (46661633, 'in vitro assay protocol type', 'otherVersion', 'test'),
       (46661634, 'blood contact', 'otherDatumName', 'activated partial thromboplastin time (APTT)'),
       (46661635, 'in vitro assay protocol type', 'otherName', 'test protocol name'),
       (46661636, 'in vitro assay protocol type', 'otherVersion', 'test protocol version'),
       (47251456, 'safety protocol type', 'otherName', 'Test Demo'),
       (47251457, 'safety protocol type', 'otherVersion', '1.0'),
       (47251458, 'carbon nanotube', 'otherAverageLengthUnit', 'mM'),
       (47251459, 'blood contact', 'otherDatumName', 'PT'),
       (47251460, 'physico-chemical assay protocol type', 'otherName', 'Test Demo 1'),
       (47251461, 'physico-chemical assay protocol type', 'otherName', 'Demo Physico'),
       (47251462, 'other_vt', 'otherDatumName', 'migration rate'),
       (47251463, 'migration rate', 'otherUnit', '%'),
       (47251464, 'ex vivo', 'otherDatumName', 'fraction PCNA positive cells'),
       (47251465, 'fraction PCNA positive cells', 'otherUnit', '%'),
       (47251466, 'ex vivo', 'otherDatumName', 'microvessel density'),
       (47251467, 'microvessel density', 'otherUnit', '1/mm^2'),
       (47251468, 'ex vivo', 'otherDatumName', 'pericyte density'),
       (47251469, 'pericyte density', 'otherUnit', '1/mm^2'),
       (47251470, 'ex vivo', 'otherDatumName', 'fraction metastatic area'),
       (47251471, 'fraction metastatic area', 'otherUnit', '%'),
       (47251472, 'condition', 'otherName', 'pH'),
       (47251473, 'instrument', 'otherManufacturer', 'Dikma Technologies'),
       (47251474, 'pharmacokinetics', 'otherDatumName', 'drug plasma concentration'),
       (47251475, 'drug plasma concentration', 'otherUnit', 'ug/mL'),
       (47251476, 'pharmacokinetics', 'otherDatumName', 'half life'),
       (47251477, 'half life', 'otherUnit', 'h'),
       (47251478, 'toxicology', 'otherDatumName', 'survival'),
       (47251479, 'survival', 'otherUnit', 'day'),
       (47251480, 'other_pc', 'otherDatumName', 'conjugation efficiency'),
       (47251481, 'conjugation efficiency', 'otherUnit', '%'),
       (47251482, 'technique', 'otherType', 'high content screening'),
       (47251483, 'high content screening', 'otherInstrument', 'high content analysis reader'),
       (47251484, 'condition', 'otherName', 'inhibitor concentration'),
       (47251485, 'inhibitor concentration', 'otherUnit', 'uM'),
       (47251486, 'condition', 'otherName', 'inhibitor'),
       (47251487, 'other_vv', 'otherDatumName', 'survival'),
       (47251488, 'survival', 'otherUnit', 'month'),
       (47251489, 'dose', 'otherUnit', 'mg/m^2'),
       (47251490, 'condition', 'otherName', 'differentiation grade'),
       (47251491, 'condition', 'otherName', 'stage'),
       (47251492, 'condition', 'otherName', 'primary tumor site'),
       (47251493, 'condition', 'otherName', 'sample ID'),
       (47251494, 'other_vv', 'otherAssayType', 'study description'),
       (47251495, 'other_pc', 'otherDatumName', 'estimated number of CPT molecules/particle'),
       (47251496, 'other_pc', 'otherDatumName', 'minimum number of MAP-CPT conjugates per particle'),
       (47251497, 'instrument', 'otherManufacturer', 'QImaging'),
       (47251498, 'ex vivo', 'otherDatumName', 'Topo-1 high expression'),
       (47251499, 'ex vivo', 'otherDatumName', 'Topo-1 low expression'),
       (47251500, 'ex vivo', 'otherDatumName', 'Topo-1 expression'),
       (47251501, 'condition', 'otherName', 'survival'),
       (47251502, 'ex vivo', 'otherDatumName', 'Topo-1 mRNA expression'),
       (47251503, 'ex vivo', 'otherDatumName', 'VEGF score'),
       (47251504, 'VEGF score', 'otherUnit', 'staining intensity score'),
       (47251505, 'ex vivo', 'otherDatumName', 'CD31 score'),
       (47251506, 'CD31 score', 'otherUnit', 'staining intensity score'),
       (47251507, 'ex vivo', 'otherDatumName', 'CaIX score'),
       (47251508, 'CaIX score', 'otherUnit', 'staining intensity score'),
       (47251509, 'ex vivo', 'otherDatumName', 'Ki-67 score'),
       (47251510, 'Ki-67 score', 'otherUnit', 'staining intensity score'),
       (47251511, 'ex vivo', 'otherDatumName', 'Topo-1 score'),
       (47251512, 'Topo-1 score', 'otherUnit', 'staining intensity score'),
       (47251513, 'ex vivo', 'otherAssayType', 'protein expression'),
       (47251514, 'instrument', 'otherManufacturer', 'Luminex'),
       (47972352, 'ex vivo', 'otherAssayType', 'single nucleotide polymorphism genotyping'),
       (47972353, 'technique', 'otherType', 'genotyping'),
       (47972354, 'genotyping', 'otherInstrument', 'genotyping system'),
       (47972355, 'ex vivo', 'otherAssayType', 'mRNA expression'),
       (47972356, 'Topo-1 mRNA expression', 'otherUnit', 'au'),
       (47972357, 'ex vivo', 'otherDatumName', 'relative Topo-1 mRNA expression'),
       (47972358, 'condition', 'otherName', 'time post treatment'),
       (47972359, 'time post treatment', 'otherUnit', 'day'),
       (47972360, 'toxicology', 'otherDatumName', 'cytokine level change'),
       (47972361, 'cytokine level change', 'otherUnit', '%'),
       (47972362, 'condition', 'otherName', 'adverse event grade'),
       (47972363, 'toxicology', 'otherDatumName', 'platelet count'),
       (47972364, 'platelet count', 'otherUnit', '1/uL'),
       (47972365, 'toxicology', 'otherDatumName', 'hemoglobin'),
       (47972366, 'other_pc', 'otherAssayType', 'binding model'),
       (47972367, 'other_pc', 'otherAssayType', 'conjugation efficiency'),
       (47972368, 'imaging', 'otherAssayType', 'immunohistochemistry'),
       (48168960, 'targeting', 'otherAssayType', 'proteoglycan expression'),
       (48627712, 'cytotoxicity', 'otherAssayType', 'expansion'),
       (48627713, 'cytotoxicity', 'otherAssayType', 'magnetic field assisted proliferation'),
       (48627714, 'cytotoxicity', 'otherAssayType', 'magnetic field assisted expansion'),
       (48627715, 'other_vv', 'otherDatumName', 'Thy1.1+ cell expansion'),
       (48627716, 'Thy1.1+ cell expansion', 'otherUnit', '%'),
       (48627717, 'condition', 'otherName', 'particle stimulation'),
       (48627718, 'other_vv', 'otherDatumName', 'total number of Thy1.1+ cells'),
       (48627719, 'other_vv', 'otherAssayType', 'cell exxpansion'),
       (48627720, 'other_vv', 'otherAssayType', 'cell expansion'),
       (48627721, 'technique', 'otherType', 'nanoparticle tracking analysis'),
       (48627722, 'nanoparticle tracking analysis', 'otherInstrument', 'nanoparticle tracking analyzer'),
       (48627723, 'instrument', 'otherManufacturer', 'Nanosight'),
       (48627724, 'other_pc', 'otherDatumName', 'anti-CD28 density'),
       (48627725, 'anti-CD28 density', 'otherUnit', 'protein/um^2'),
       (48627726, 'other_pc', 'otherDatumName', 'number of anti-CD28 per particle'),
       (48627727, 'other_pc', 'otherDatumName', 'MHC-Ig density'),
       (48627728, 'MHC-Ig density', 'otherUnit', 'protein/um^2'),
       (48627729, 'other_pc', 'otherDatumName', 'number of MHC-Ig dimers per particle'),
       (48627730, 'other_pc', 'otherAssayType', 'number of bound protein'),
       (48627731, 'other_vt', 'otherAssayType', 'equilibrium binding'),
       (48627732, 'other_vt', 'otherAssayType', 'disassociation'),
       (48627733, 'other_vt', 'otherDatumName', 'number of TCR-MHC contacts'),
       (48627734, 'other_vt', 'otherDatumName', 'half life'),
       (48627735, 'half life', 'otherUnit', 's'),
       (48627736, 'other_vt', 'otherDatumName', 'off-rate'),
       (48627737, 'off-rate', 'otherUnit', '1/s'),
       (48627738, 'condition', 'otherName', 'T cells'),
       (48627739, 'other_vt', 'otherAssayType', 'magnetic field assisted cell clustering'),
       (48627740, 'other_vt', 'otherDatumName', 'number of clusters'),
       (48627741, 'elapsed time', 'otherUnit', 'min'),
       (48627742, 'other_vt', 'otherDatumName', 'cluster size'),
       (48627743, 'cluster size', 'otherUnit', 'um^2'),
       (48627744, 'other_vt', 'otherDatumName', 'number of proliferated cells'),
       (48627745, 'condition', 'otherName', 'initial number of cells'),
       (48627746, 'other_pc', 'otherAssayType', 'protein binding'),
       (48627747, 'other_pc', 'otherAssayType', 'fluorescence interference'),
       (48627748, 'other_vt', 'otherAssayType', 'magnetic field assisted cell proliferation'),
       (48627749, 'other_vt', 'otherAssayType', 'magnetic field assisted cell expansion'),
       (48857088, 'other_vt', 'otherAssayType', 'magnetic field assisted disassociation'),
       (49315840, 'other_pc', 'otherAssayType', 'PEG density'),
       (49315841, 'other_pc', 'otherDatumName', 'PEG mass fraction'),
       (49315842, 'PEG mass fraction', 'otherUnit', 'wt%'),
       (49315843, 'other_pc', 'otherDatumName', 'PEG density'),
       (49315844, 'PEG density', 'otherUnit', 'number of PEG molecules/nm^2'),
       (49315845, 'other_vv', 'otherDatumName', 'survival probability'),
       (49315846, 'other_vv', 'otherDatumName', 'tumor growth delay'),
       (49315847, 'tumor growth delay', 'otherUnit', 'day'),
       (49315848, 'other_vv', 'otherDatumName', 'time to end point'),
       (49315849, 'time to end point', 'otherUnit', 'day'),
       (49315850, 'size', 'otherDatumName', 'coefficient of variation'),
       (49315851, 'coefficient of variation', 'otherUnit', '%'),
       (49315852, 'other_pc', 'otherDatumName', 'incorporation efficiency'),
       (49315853, 'incorporation efficiency', 'otherUnit', '%'),
       (49315854, 'other_pc', 'otherAssayType', 'incorporation efficiency'),
       (49315855, 'drug equivalent concentration', 'otherUnit', 'nM'),
       (49315856, 'other_vv', 'otherDatumName', 'apoptotic index/proliferative index'),
       (49315857, 'apoptotic index/proliferative index', 'otherUnit', '%'),
       (49315858, 'other_vv', 'otherDatumName', 'apoptotic index'),
       (49315859, 'apoptotic index', 'otherUnit', '%'),
       (49315860, 'other_vv', 'otherDatumName', 'proliferative index'),
       (49315861, 'proliferative index', 'otherUnit', '%'),
       (49315862, 'other_vv', 'otherDatumName', 'tumor growth delay fraction'),
       (49315863, 'tumor growth delay fraction', 'otherUnit', '%'),
       (49315864, 'other_vv', 'otherDatumName', 'relative body weight'),
       (49315865, 'relative body weight', 'otherUnit', '%'),
       (49315866, 'other_vv', 'otherDatumName', 'food intake after drug administration'),
       (49315867, 'food intake after drug administration', 'otherUnit', 'g'),
       (49315868, 'other_vv', 'otherDatumName', 'food intake during drug administration'),
       (49315869, 'food intake during drug administration', 'otherUnit', 'g'),
       (49315870, 'other_vv', 'otherDatumName', 'food intake before drug administration'),
       (49315871, 'food intake before drug administration', 'otherUnit', 'g'),
       (49315872, 'other_vv', 'otherDatumName', 'food intake per day after drug administration'),
       (49315873, 'food intake per day after drug administration', 'otherUnit', 'g'),
       (49315874, 'other_vv', 'otherDatumName', 'food intake per day during drug administration'),
       (49315875, 'food intake per day during drug administration', 'otherUnit', 'g'),
       (49315876, 'other_vv', 'otherDatumName', 'food intake per day before drug administration'),
       (49315877, 'food intake per day before drug administration', 'otherUnit', 'g'),
       (49315878, 'drug loading efficiency', 'otherUnit', 'wt%'),
       (49315879, 'instrument', 'otherManufacturer', 'Stanford Photonics'),
       (49315880, 'other_vv', 'otherDatumName', 'photon emission integrated density'),
       (49315881, 'other_vv', 'otherDatumName', 'tumor counts'),
       (49315882, 'other_vv', 'otherDatumName', 'scale of lung tumor burden'),
       (49315883, 'other_vv', 'otherDatumName', 'food intake per day'),
       (49315884, 'food intake per day', 'otherUnit', 'g'),
       (49315885, 'other_pc', 'otherDatumName', 'initial radioactivity fraction'),
       (49315886, 'initial radioactivity fraction', 'otherUnit', '%'),
       (49315887, 'condition', 'otherName', 'assay number'),
       (49315888, 'imaging', 'otherInstrument', 'preclinical PET/CT scanner'),
       (49315889, 'other_vv', 'otherDatumName', 'tumor fraction of injected dose'),
       (49315890, 'tumor fraction of injected dose', 'otherUnit', '%ID/g'),
       (49315891, 'AUC', 'otherUnit', 'h x %ID/g'),
       (49315892, 'technique', 'otherType', 'multimodality imaging'),
       (49315893, 'multimodality imaging', 'otherInstrument', 'preclinical small animal PET/CT scanner'),
       (49315894, 'fraction of injected dose', 'otherUnit', '%ID/g'),
       (49315895, 'other_vv', 'otherAssayType', 'tumor accumulation kinetics'),
       (49315896, 'imaging', 'otherAssayType', 'tumor penetration'),
       (49315897, 'other_vv', 'otherAssayType', 'tumor penetration'),
       (49315898, 'imaging', 'otherDatumName', 'tumor tissue penetration depth'),
       (49315899, 'tumor tissue penetration depth', 'otherUnit', 'um'),
       (49315900, 'imaging', 'otherDatumName', 'clearance'),
       (49315901, 'clearance', 'otherUnit', '%'),
       (49315902, 'condition', 'otherName', 'end time'),
       (49315903, 'end time', 'otherUnit', 'hour'),
       (49315904, 'condition', 'otherName', 'start time'),
       (49315905, 'start time', 'otherUnit', 'hour'),
       (49315906, 'imaging', 'otherAssayType', 'tumor clearance'),
       (49315907, 'other_vv', 'otherAssayType', 'tumor uptake model'),
       (50364416, 'size', 'otherDatumName', 'r3'),
       (50364417, 'r3', 'otherUnit', 'nm'),
       (50364418, 'size', 'otherDatumName', 'r2'),
       (50364419, 'r2', 'otherUnit', 'nm'),
       (50364420, 'size', 'otherDatumName', 'r1'),
       (50364421, 'r1', 'otherUnit', 'nm'),
       (50364422, 'size', 'otherDatumName', 'nominal r3'),
       (50364423, 'nominal r3', 'otherUnit', 'nm'),
       (50364424, 'size', 'otherDatumName', 'nominal r2'),
       (50364425, 'nominal r2', 'otherUnit', 'nm'),
       (50364426, 'size', 'otherDatumName', 'nominal r1'),
       (50364427, 'nominal r1', 'otherUnit', 'nm'),
       (50364428, 'surface', 'otherDatumName', 'absorption'),
       (50364429, 'absorption', 'otherUnit', 'm^2'),
       (50364430, 'condition', 'otherName', 'maximum wavelength'),
       (50364431, 'maximum wavelength', 'otherUnit', 'nm'),
       (50364432, 'surface', 'otherDatumName', 'geometric cross section'),
       (50364433, 'geometric cross section', 'otherUnit', 'm^2'),
       (50364434, 'absorption', 'otherUnit', 'fm^2'),
       (50364435, 'geometric cross section', 'otherUnit', 'fm^2'),
       (50364436, 'other_pc', 'otherAssayType', 'photothermal transduction efficiency'),
       (50364437, 'temperature measurement', 'otherInstrument', 'digital thermometer'),
       (50364438, 'instrument', 'otherManufacturer', 'Omega'),
       (50364439, 'other_pc', 'otherDatumName', 'photothermal transduction efficiency'),
       (50364440, 'photothermal transduction efficiency', 'otherUnit', '%'),
       (50364441, 'technique', 'otherType', 'photothermal induction'),
       (50364442, 'photothermal induction', 'otherInstrument', 'diode laser'),
       (50364443, 'instrument', 'otherManufacturer', 'Diomed'),
       (50364444, 'condition', 'otherName', 'mouse ID'),
       (50364445, 'other_vv', 'otherDatumName', 'maximum temperature change in tumor'),
       (50364446, 'maximum temperature change in tumor', 'otherUnit', 'C'),
       (50364447, 'other_vv', 'otherDatumName', 'mass of Au per g of wet tissue'),
       (50364448, 'mass of Au per g of wet tissue', 'otherUnit', 'ug'),
       (50364449, 'other_vv', 'otherDatumName', 'fraction of injected dose per g of tumor'),
       (50364450, 'fraction of injected dose per g of tumor', 'otherUnit', '%'),
       (50364451, 'other_vv', 'otherDatumName', 'number of particles per g of tumor'),
       (50364452, 'imaging', 'otherDatumName', 'normalized luciferase signal intensity'),
       (50364453, 'imaging', 'otherAssayType', 'bioluminescence imaging'),
       (50364454, 'imaging', 'otherInstrument', 'ICCD camera'),
       (50364455, 'instrument', 'otherManufacturer', 'Angiodynamics'),
       (50364456, 'surface', 'otherDatumName', 'absorption cross section'),
       (50364457, 'absorption cross section', 'otherUnit', 'fm^2'),
       (50364458, 'other_vv', 'otherDatumName', 'number of Au particles per g of tumor'),
       (50364459, 'surface', 'otherDatumName', 'scattering efficiency'),
       (50364460, 'surface', 'otherDatumName', 'absorption efficiency'),
       (50364461, 'surface', 'otherDatumName', 'total extinction efficiency'),
       (50364462, 'condition', 'otherName', 'particle concentration'),
       (50364463, 'particle concentration', 'otherUnit', 'particles/mL'),
       (50364464, 'condition', 'otherName', 'optical density'),
       (50495488, 'physico-chemical assay protocol type', 'otherName', 'Demo Protocol'),
       (51609600, 'in vitro assay protocol type', 'otherName', 'test 10292015 protocol'),
       (52330496, 'physico-chemical assay protocol type', 'otherName', 'Demo Protocol 2'),
       (52330497, 'physico-chemical assay protocol type', 'otherVersion', '1'),
       (53051392, 'carbon nanotube', 'otherAverageLengthUnit', 'mmol'),
       (53051393, 'in vivo assay protocol type', 'otherName', 'TestSharon'),
       (54034432, 'synthesis protocol type', 'otherName', 'Synthesis of HER2 aptamer modified gold nanostar'),
       (54755328, 'particle concentration', 'otherUnit', 'nmol/mL'),
       (54755329, 'other_pc', 'otherAssayType', 'phosphorus assay'),
       (54755330, 'condition', 'otherName', 'lipids concentration'),
       (54755331, 'lipids concentration', 'otherUnit', 'uM'),
       (54755332, 'other_pc', 'otherAssayType', 'chemokine receptor expression'),
       (54755333, 'other_pc', 'otherDatumName', 'fraction of GNVs positive for respective chemokine receptor'),
       (54755334, 'fraction of GNVs positive for respective chemokine receptor', 'otherUnit', '%'),
       (54755335, 'condition', 'otherName', 'chemokine'),
       (54755336, 'survival', 'otherUnit', '%'),
       (54755337, 'toxicology', 'otherDatumName', 'IL-1beta'),
       (54755338, 'IL-1beta', 'otherUnit', 'pg/mL'),
       (54755339, 'toxicology', 'otherDatumName', 'IL-6'),
       (54755340, 'IL-6', 'otherUnit', 'pg/mL'),
       (54755341, 'toxicology', 'otherDatumName', 'THF-alpha'),
       (54755342, 'THF-alpha', 'otherUnit', 'pg/mL'),
       (55115776, 'other_vt', 'otherAssayType', 'colocalization'),
       (55115777, 'technique', 'otherType', 'confocal microscopy'),
       (55115778, 'confocal microscopy', 'otherInstrument', 'confocal microscope'),
       (55115779, 'other_pc', 'otherAssayType', 'colocalization'),
       (55115780, 'other_vt', 'otherDatumName', 'fraction of initial PKH26-GNV fluorescence'),
       (55115781, 'fraction of initial PKH26-GNV fluorescence', 'otherUnit', '%'),
       (55115782, 'other_pc', 'otherAssayType', 'coating efficiency'),
       (55115783, 'other_pc', 'otherDatumName', 'fraction of fluorescence intensity'),
       (55115784, 'fraction of fluorescence intensity', 'otherUnit', '%'),
       (55115785, 'condition', 'otherName', 'nanomaterial derivation type'),
       (55115786, 'condition', 'otherName', 'nanomaterial derivation method'),
       (55115787, 'other_pc', 'otherDatumName', 'number of GNVs coated with plasma membrane'),
       (55115788, 'number of GNVs coated with plasma membrane', 'otherUnit', '%'),
       (55115789, 'synthesis protocol type', 'otherName', 'SUV Synthesis'),
       (55115790, 'synthesis protocol type', 'otherName', 'SNA Synthesis'),
       (55115791, 'instrument', 'otherManufacturer', 'Carestream'),
       (55115792, 'other_vv', 'otherAssayType', 'targeting'),
       (55115793, 'other_vv', 'otherDatumName', 'fluorescence intensity'),
       (55115794, 'condition', 'otherName', 'mice type'),
       (55115795, 'other_vv', 'otherDatumName', 'amount of curcumin in colon tissue'),
       (55115796, 'amount of curcumin in colon tissue', 'otherUnit', 'mAU'),
       (55115797, 'condition', 'otherName', 'stability medium temperature'),
       (55115798, 'stability medium temperature', 'otherUnit', 'Celsius'),
       (55115799, 'condition', 'otherName', 'stability medium pH'),
       (55115800, 'condition', 'otherName', 'stability medium'),
       (55115801, 'other_pc', 'otherDatumName', 'zeta potential'),
       (55115802, 'integrin receptor expression', 'otherAssayType', 'integrin receptor expression'),
       (55115803, 'integrin receptor expression', 'otherDatumName', 'fraction of IGNVs positive for LFA-1 integrin'),
       (55115804, 'fraction of IGNVs positive for LFA-1 integrin', 'otherUnit', '%'),
       (55115805, 'cytotoxicity', 'otherDatumName', 'ATP content'),
       (55115806, 'ATP content', 'otherUnit', 'RFU'),
       (55115807, 'particle concentration', 'otherUnit', 'nmol'),
       (55115808, 'cytotoxicity', 'otherDatumName', 'cell number'),
       (55115809, 'condition', 'otherName', 'passage'),
       (55115810, 'condition', 'otherName', 'treated'),
       (55115811, 'dose', 'otherUnit', 'nmol'),
       (55115812, 'toxicology', 'otherDatumName', 'ALT'),
       (55115813, 'ALT', 'otherUnit', 'U/L'),
       (55115814, 'toxicology', 'otherDatumName', 'AST'),
       (55115815, 'AST', 'otherUnit', 'U/L'),
       (55115816, 'toxicology', 'otherDatumName', 'weight'),
       (55115817, 'weight', 'otherUnit', 'g'),
       (55115818, 'other_pc', 'otherAssayType', 'integrin receptor expression'),
       (55115819, 'other_pc', 'otherDatumName', 'fraction of IGNVs positive for LFA-1 integrin'),
       (55115820, 'histology', 'otherAssayType', 'histology'),
       (55738368, 'other_vt', 'otherDatumName', 'fraction of initial IGNV-PKH26 fluorescence'),
       (55738369, 'fraction of initial IGNV-PKH26 fluorescence', 'otherUnit', '%'),
       (55738370, 'other_vv', 'otherAssayType', 'chemokine receptor expression'),
       (55738371, 'condition', 'otherName', 'particle type'),
       (55738372, 'other_vv', 'otherDatumName', 'fraction of injected dose per gram of tissue'),
       (55738373, 'fraction of injected dose per gram of tissue', 'otherUnit', '%'),
       (55738374, 'condition', 'otherName', 'drug amount'),
       (55738375, 'drug amount', 'otherUnit', 'ug'),
       (55738376, 'other_pc', 'otherDatumName', 'fraction of IGNVs positive for respective chemokine receptor'),
       (55738377, 'fraction of IGNVs positive for respective chemokine receptor', 'otherUnit', '%'),
       (55738378, 'activation method', 'otherType', 'Oscillation-based drug release'),
       (55738379, 'condition', 'otherName', 'particle coat origin'),
       (56393728, 'particle concentration', 'otherUnit', 'pM'),
       (56393729, 'targeting', 'otherDatumName', 'total Au+/Mg2+'),
       (56393730, 'other_pc', 'otherDatumName', 'relative fluorescence - fraction of free dye'),
       (56393731, 'relative fluorescence - fraction of free dye', 'otherUnit', '%'),
       (56393732, 'other_pc', 'otherAssayType', 'PEG length optimization'),
       (56393733, 'other_pc', 'otherDatumName', 'PEG/Au ratio'),
       (56393734, 'particle concentration', 'otherUnit', 'pmol'),
       (56393735, 'condition', 'otherName', 'medium volume'),
       (56393736, 'medium volume', 'otherUnit', 'mL'),
       (56393737, 'pharmacokinetics', 'otherDatumName', 'Au concentration in blood'),
       (56393738, 'Au concentration in blood', 'otherUnit', 'pmol/mL'),
       (56393739, 'pharmacokinetics', 'otherDatumName', 'AuNP concentration in blood'),
       (56393740, 'AuNP concentration in blood', 'otherUnit', 'pmol/mL'),
       (56393741, 'AUC', 'otherUnit', 'pmol x hour'),
       (56393742, 'other_vv', 'otherAssayType', 'tumor uptake'),
       (56393743, 'other_vv', 'otherDatumName', 'clearance rate'),
       (56393744, 'clearance rate', 'otherUnit', '%ID/hour'),
       (56393745, 'other_vv', 'otherDatumName', 'uptake rate'),
       (56393746, 'uptake rate', 'otherUnit', '%ID/hour'),
       (56393747, 'other_vv', 'otherDatumName', 'update rate'),
       (56393748, 'update rate', 'otherUnit', '%ID/hour'),
       (56393749, 'AUC', 'otherUnit', '%ID x hour'),
       (56393750, 'other_ex_vivo', 'otherAssayType', 'tumor penetration'),
       (56393751, 'other_ex_vivo', 'otherDatumName', 'permeation distance'),
       (56393752, 'permeation distance', 'otherUnit', 'um'),
       (56393753, 'other_ex_vivo', 'otherAssayType', 'tissue absorption'),
       (56393754, 'technique', 'otherType', 'fluorescence imaging'),
       (56393755, 'fluorescence imaging', 'otherInstrument', 'preclinical imaging system'),
       (56393756, 'other_ex_vivo', 'otherDatumName', 'fluorescence intensity'),
       (56393757, 'condition', 'otherName', 'tissue thickness'),
       (56393758, 'tissue thickness', 'otherUnit', 'mm'),
       (56393759, 'particle concentration', 'otherUnit', 'nM'),
       (56393760, 'other_ex_vivo', 'otherAssayType', 'tumor accumulation model'),
       (56393761, 'toxicology', 'otherDatumName', 'TNF-alpha'),
       (56393762, 'TNF-alpha', 'otherUnit', 'pg/mL'),
       (56393763, 'other_ex_vivo', 'otherDatumName', 'diffusion coefficient in tumor'),
       (56393764, 'diffusion coefficient in tumor', 'otherUnit', 'um^2/hour'),
       (56393765, 'other_ex_vivo', 'otherDatumName', 'R square'),
       (56393766, 'other_ex_vivo', 'otherDatumName', 'chi square'),
       (56393767, 'other_ex_vivo', 'otherDatumName', 'tumor capillary permeability'),
       (56393768, 'tumor capillary permeability', 'otherUnit', 'um/hour'),
       (56393769, 'other_ex_vivo', 'otherDatumName', 'cell internalization rate'),
       (56393770, 'cell internalization rate', 'otherUnit', '1/hour'),
       (56393771, 'other_pc', 'otherAssayType', 'protein surface density'),
       (56393772, 'instrument', 'otherManufacturer', 'BMG LABTECH'),
       (56393773, 'other_pc', 'otherDatumName', 'number of transferrins/AuNP'),
       (56393774, 'other_pc', 'otherDatumName', 'number of tranferrins/AuNP'),
       (56393775, 'other_vt', 'otherAssayType', 'dissociation'),
       (56393776, 'spectrofluorometry', 'otherInstrument', 'preclinical imaging system'),
       (56393777, 'other_vt', 'otherDatumName', 'dissociation constant'),
       (56393778, 'dissociation constant', 'otherUnit', 'nM'),
       (56393779, 'other_ex_vivo', 'otherDatumName', 'available volume fraction of the tumor interstitium'),
       (56393780, 'inhibitor concentration', 'otherUnit', 'mg/mL'),
       (56393781, 'publication', 'otherCategory', 'Article'),
       (56393782, 'other_pc', 'otherDatumName', 'PEG/AuNP ratio'),
       (56393783, 'other_vt', 'otherDatumName', 'cell fluorescence'),
       (56393784, 'cell fluorescence', 'otherUnit', 'au'),
       (56393785, 'particle concentration', 'otherUnit', 'fM'),
       (56393786, 'targeting', 'otherDatumName', 'cell fluorescence'),
       (56393787, 'particle concentration', 'otherUnit', 'M'),
       (56393788, 'targeting', 'otherDatumName', 'dissociation constant'),
       (56393789, 'other_pc', 'otherAssayType', 'conjugation confirmation'),
       (56393790, 'high performance liquid chromatography', 'otherInstrument', 'separation module'),
       (56393791, 'high performance liquid chromatography', 'otherInstrument', 'size exclusion column'),
       (56393792, 'instrument', 'otherManufacturer', 'Tosoh Bioscience'),
       (56393793, 'other_pc', 'otherAssayType', 'labeling quantification'),
       (56393794, 'technique', 'otherType', 'dialysis'),
       (56393795, 'dialysis', 'otherInstrument', 'dialysis device'),
       (56393796, 'other_pc', 'otherDatumName', 'PEG/TF ratio'),
       (56393797, 'other_pc', 'otherDatumName', 'TF concentration'),
       (56393798, 'TF concentration', 'otherUnit', 'umol'),
       (56393799, 'other_pc', 'otherDatumName', 'OPSS-PEG concentration'),
       (56393800, 'OPSS-PEG concentration', 'otherUnit', 'umol'),
       (56393801, 'other_vt', 'otherDatumName', 'error'),
       (56393802, 'concentration', 'otherUnit', 'nm'),
       (56393803, 'other_vt', 'otherDatumName', 'activation'),
       (56393804, 'other_vt', 'otherDatumName', 'alkaline phosphatase activation'),
       (56393805, 'alkaline phosphatase activation', 'otherUnit', '%'),
       (56393806, 'other_vt', 'otherAssayType', 'activation'),
       (57049088, 'other_vv', 'otherAssayType', 'imaging'),
       (57049089, 'polydispersity index', 'otherUnit', 'mV'),
       (57049090, 'other_pc', 'otherAssayType', 'ligand surface density'),
       (57049091, 'other_pc', 'otherDatumName', 'ligand surface density'),
       (57049092, 'ligand surface density', 'otherUnit', 'ligands/nm^2'),
       (57409536, 'other', 'otherAssayType', 'Morphology'),
       (57409537, 'other', 'otherAssayType', 'Transmission Electron Microscopy'),
       (57409538, 'AUC', 'otherUnit', 'h x mg/mL'),
       (57409539, 'other_ex_vv', 'otherAssayType', 'imaging'),
       (57769984, 'sample preparation protocol type', 'otherName',
        'Large Scale Purification of RNA Nanoparticles by Preparative Ultracentrifugation'),
       (57769985, 'sample preparation protocol type', 'otherName',
        'Simple Method for Constructing RNA Triangle, Square, Pentagon by Tuning Interior RNA 3WJ Angle from 60 to 90 or 108 degrees'),
       (57769986, 'synthesis protocol type', 'otherName',
        'Fabrication of pRNA nanoparticles to deliver therapeutic RNAs and bioactive compounds into tumor cells.'),
       (58064896, 'in vitro assay protocol type', 'otherName', 'test'),
       (58064897, 'particle concentration', 'otherUnit', 'mg/mL'),
       (59244544, 'particle concentration', 'otherUnit', 'umol/L'),
       (59244545, 'technique', 'otherType', 'luminometry'),
       (59244546, 'luminometry', 'otherInstrument', 'luminometer'),
       (59244547, 'targeting', 'otherDatumName', 'renilla/firefly ratio'),
       (59244548, 'particle concentration', 'otherUnit', 'nmol/L'),
       (59244549, 'time of exposure', 'otherUnit', 'hour'),
       (59244550, 'targeting', 'otherDatumName', 'fold increase in gene expression'),
       (59244551, 'condition', 'otherName', 'gene'),
       (59244552, 'other_ex_vv', 'otherAssayType', 'gene expression'),
       (59244553, 'other_ex_vv', 'otherDatumName', 'fraction of normal gene expression'),
       (59244554, 'fraction of normal gene expression', 'otherUnit', '%'),
       (59244555, 'cytotoxicity', 'otherDatumName', 'fraction of normal caspase 3 signal'),
       (59244556, 'fraction of normal caspase 3 signal', 'otherUnit', '%'),
       (59244557, 'maximum # of internalized NPs', 'otherUnit', '%'),
       (59244558, 'other_ex_vivo', 'otherAssayType', 'histology'),
       (59244559, 'other_ex_vivo', 'otherAssayType', 'therapeutic efficacy'),
       (59244560, 'other_ex_vivo', 'otherDatumName', 'fraction of normal gene expression'),
       (59244561, 'condition', 'otherName', 'fraction of normal gene expression'),
       (59244562, 'technique', 'otherType', 'temperature gradient gel electrophoresis'),
       (59244563, 'temperature gradient gel electrophoresis', 'otherInstrument',
        'temperature gradient gel electrophoresis instrument'),
       (59244564, 'instrument', 'otherManufacturer', 'Biometra'),
       (59244565, 'melting temperature', 'otherUnit', 'Celsius'),
       (59670528, 'targeting', 'otherDatumName', 'fraction of bound particles'),
       (59670529, 'fraction of bound particles', 'otherUnit', '%'),
       (60227584, 'synthesis protocol type', 'otherName', 'Test Submit Protocol As Curator 04234442'),
       (60227585, 'synthesis protocol type', 'otherVersion', 'Test Protocol Version 1'),
       (60227586, 'synthesis protocol type', 'otherName', 'Test Submit Protocol As Curator 05000223'),
       (60227587, 'synthesis protocol type', 'otherName', 'Test Submit Protocol As Curator 05061357'),
       (60555359, 'magnetic field', 'otherUnit', 'Tesla'),
       (63307779, 'cytotoxicity', 'otherDatumName', 'cell viability B'),
       (63307780, 'cytotoxicity', 'otherDatumName', 'cell viability C'),
       (63307781, 'Number Modifier', 'numberModifier', '<'),
       (63307782, 'Number Modifier', 'numberModifier', '>'),
       (63307783, 'Number Modifier', 'numberModifier', '='),
       (63307784, 'Number Modifier', 'numberModifier', '~'),
       (66093056, 'metabolic stability', 'otherDatumName', 'Col1Datum'),
       (66093057, 'Col1Datum', 'otherUnit', 'number'),
       (66093058, 'datumOperator', 'numberModifier', '='),
       (66093059, 'dilution factor', 'otherUnit', 'num'),
       (66224128, 'metabolic stability', 'otherAssayType', 'SynAssay'),
       (66224129, 'synthesis protocol type', 'otherName', 'Synthesis Test Protocol'),
       (66224130, 'synthesis protocol type', 'otherVersion', '1.0'),
       (66551808, 'in vivo assay protocol type', 'otherName', 'Test Characterization Protocol'),
       (66551809, 'in vitro assay protocol type', 'otherName', 'Test In Vitro Characterization Protocol'),
       (66879488, 'synthesis', 'otherType', 'Synthesis'),
       (73367556, 'other_pc', 'otherDatumName', 'dose fraction per gram tissue'),
       (73367557, 'other_vv', 'otherDatumName', 'dose fraction per gram tissue'),
       (73367558, 'pharmacokinetics', 'otherDatumName', 'dose fraction per gram tissue'),
       (73367559, 'dose fraction per gram tissue', 'otherUnit', '%');
/*!40000 ALTER TABLE `common_lookup`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `composing_element`
--


/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `composing_element`
(
    `composing_element_pk_id`   bigint(20)   NOT NULL,
    `element_type`              varchar(100) NOT NULL,
    `nanomaterial_entity_pk_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`composing_element_pk_id`),
    KEY `composing_element_pk_id` (`composing_element_pk_id`),
    KEY `nanoparticle_entity_pk_id` (`nanomaterial_entity_pk_id`),
    CONSTRAINT `FK_composing_element_associated_element` FOREIGN KEY (`composing_element_pk_id`) REFERENCES `associated_element` (`associated_element_pk_id`),
    CONSTRAINT `FK_composing_element_nanoparticle_entity` FOREIGN KEY (`nanomaterial_entity_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `composing_element`
--

LOCK TABLES `composing_element` WRITE;
/*!40000 ALTER TABLE `composing_element`
    DISABLE KEYS */;
INSERT IGNORE INTO `composing_element` (`composing_element_pk_id`, `element_type`, `nanomaterial_entity_pk_id`)
VALUES (65732608, 'monomer', 65667072),
       (65732610, 'coat', 65667073),
       (65732611, 'RNA', 65667074),
       (65732612, 'lipid', 65667075),
       (65732613, 'modifier', 65667075),
       (66682880, 'core', 66617344);
/*!40000 ALTER TABLE `composing_element`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `composition`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `composition`
(
    `composition_pk_id` bigint(20) NOT NULL,
    `sample_pk_id`      bigint(20) DEFAULT NULL,
    PRIMARY KEY (`composition_pk_id`),
    UNIQUE KEY `composition_pk_id` (`composition_pk_id`),
    KEY `particle_sample_pk_id` (`sample_pk_id`),
    CONSTRAINT `FK_composition_sample` FOREIGN KEY (`sample_pk_id`) REFERENCES `sample` (`sample_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `composition`
--

LOCK TABLES `composition` WRITE;
/*!40000 ALTER TABLE `composition`
    DISABLE KEYS */;
INSERT IGNORE INTO `composition` (`composition_pk_id`, `sample_pk_id`)
VALUES (65699840, 1000),
       (66650112, 1005),
       (65699841, 65634304),
       (65699842, 65634305),
       (65699843, 65634306);
/*!40000 ALTER TABLE `composition`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `composition_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `composition_file`
(
    `composition_pk_id` bigint(20) NOT NULL,
    `file_pk_id`        bigint(20) NOT NULL,
    PRIMARY KEY (`composition_pk_id`, `file_pk_id`),
    KEY `composition_pk_id` (`composition_pk_id`),
    KEY `file_pk_id` (`file_pk_id`),
    CONSTRAINT `FK_composition_file_composition` FOREIGN KEY (`composition_pk_id`) REFERENCES `composition` (`composition_pk_id`),
    CONSTRAINT `FK_composition_file_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `composition_file`
--

LOCK TABLES `composition_file` WRITE;
/*!40000 ALTER TABLE `composition_file`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `composition_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATA_AVAILABILITY`
--

CREATE TABLE `DATA_AVAILABILITY`
(
    `sample_id`             bigint(20)   NOT NULL,
    `datasource_name`       varchar(20)  DEFAULT NULL,
    `available_entity_name` varchar(200) DEFAULT NULL,
    `created_date`          datetime     NOT NULL,
    `created_by`            varchar(200) NOT NULL,
    `updated_date`          datetime     DEFAULT NULL,
    `updated_by`            varchar(200) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATA_AVAILABILITY`
--

LOCK TABLES `DATA_AVAILABILITY` WRITE;
/*!40000 ALTER TABLE `DATA_AVAILABILITY`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `DATA_AVAILABILITY`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_review_status`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `data_review_status`
(
    `data_id`        bigint(20)   NOT NULL,
    `data_type`      varchar(200) NOT NULL,
    `data_name`      varchar(200) NOT NULL,
    `status`         varchar(200) NOT NULL,
    `submitted_date` datetime     NOT NULL,
    `submitted_by`   varchar(200) NOT NULL,
    PRIMARY KEY (`data_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_review_status`
--

LOCK TABLES `data_review_status` WRITE;
/*!40000 ALTER TABLE `data_review_status`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `data_review_status`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `databasechangelog`
(
    `ID`           varchar(63)  NOT NULL,
    `AUTHOR`       varchar(63)  NOT NULL,
    `FILENAME`     varchar(200) NOT NULL,
    `DATEEXECUTED` datetime     NOT NULL,
    `MD5SUM`       varchar(32)  DEFAULT NULL,
    `DESCRIPTION`  varchar(255) DEFAULT NULL,
    `COMMENTS`     varchar(255) DEFAULT NULL,
    `TAG`          varchar(255) DEFAULT NULL,
    `LIQUIBASE`    varchar(10)  DEFAULT NULL,
    PRIMARY KEY (`ID`, `AUTHOR`, `FILENAME`)
) ENGINE = MyISAM
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `databasechangelog`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `databasechangeloglock`
(
    `ID`          int(11)    NOT NULL,
    `LOCKED`      tinyint(1) NOT NULL,
    `LOCKGRANTED` datetime     DEFAULT NULL,
    `LOCKEDBY`    varchar(255) DEFAULT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = MyISAM
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock`
    DISABLE KEYS */;
INSERT IGNORE INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`)
VALUES (1, 0, NULL, NULL);
/*!40000 ALTER TABLE `databasechangeloglock`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datum`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `datum`
(
    `datum_pk_id`   bigint(20)      NOT NULL,
    `name`          varchar(200)    NOT NULL,
    `value`         decimal(30, 10) NOT NULL,
    `value_type`    varchar(200) DEFAULT NULL,
    `value_unit`    varchar(200) DEFAULT NULL,
    `created_by`    varchar(200)    NOT NULL,
    `created_date`  datetime        NOT NULL,
    `finding_pk_id` bigint(20)   DEFAULT NULL,
    `file_pk_id`    bigint(20)   DEFAULT NULL,
    `numberMod`     varchar(20)  DEFAULT '=',
    PRIMARY KEY (`datum_pk_id`),
    KEY `file_pk_id` (`file_pk_id`),
    KEY `finding_pk_id` (`finding_pk_id`),
    CONSTRAINT `FK_datum_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_datum_finding` FOREIGN KEY (`finding_pk_id`) REFERENCES `finding` (`finding_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datum`
--

LOCK TABLES `datum` WRITE;
/*!40000 ALTER TABLE `datum`
    DISABLE KEYS */;
INSERT IGNORE INTO `datum` (`datum_pk_id`, `name`, `value`, `value_type`, `value_unit`, `created_by`, `created_date`,
                            `finding_pk_id`, `file_pk_id`, `numberMod`)
VALUES (66486272, 'endotoxin level', 1.0000000000, NULL, NULL, 'canano_curator', '2020-01-10 13:28:17', 66453504, NULL,
        '='),
       (66486273, 'endotoxin level', 2.0000000000, NULL, NULL, 'canano_curator', '2020-01-10 13:29:57', 66453504, NULL,
        '='),
       (66486274, 'endotoxin level', 3.0000000000, NULL, NULL, 'canano_curator', '2020-01-10 13:31:37', 66453504, NULL,
        '='),
       (66748416, 'Col1Datum', 3.0000000000, 'number of replicates', 'number', 'canano_curator', '2020-01-10 20:49:52',
        65994752, NULL, '='),
       (66748417, 'Col1Datum', 3.0000000000, 'number of replicates', 'number', 'canano_curator', '2020-01-10 20:51:32',
        65994752, NULL, '='),
       (66748418, 'Col1Datum', 3.0000000000, 'number of replicates', 'number', 'canano_curator', '2020-01-10 20:53:12',
        65994752, NULL, '=');
/*!40000 ALTER TABLE `datum`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datum_condition`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `datum_condition`
(
    `datum_pk_id`     bigint(20) NOT NULL,
    `condition_pk_id` bigint(20) NOT NULL,
    PRIMARY KEY (`datum_pk_id`, `condition_pk_id`),
    KEY `datum_pk_id` (`datum_pk_id`),
    KEY `condition_pk_id` (`condition_pk_id`),
    CONSTRAINT `FK_datum_condition_datum` FOREIGN KEY (`datum_pk_id`) REFERENCES `datum` (`datum_pk_id`),
    CONSTRAINT `FK_datum_condition_experiment_condition` FOREIGN KEY (`condition_pk_id`) REFERENCES `experiment_condition` (`condition_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datum_condition`
--

LOCK TABLES `datum_condition` WRITE;
/*!40000 ALTER TABLE `datum_condition`
    DISABLE KEYS */;
INSERT IGNORE INTO `datum_condition` (`datum_pk_id`, `condition_pk_id`)
VALUES (66486272, 66519040),
       (66486272, 66519041),
       (66486273, 66519042),
       (66486273, 66519043),
       (66486274, 66519044),
       (66486274, 66519045),
       (66748416, 66781184),
       (66748417, 66781185),
       (66748418, 66781186);
/*!40000 ALTER TABLE `datum_condition`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dendrimer`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `dendrimer`
(
    `dendrimer_pk_id` bigint(20) NOT NULL,
    `generation`      decimal(22, 3) DEFAULT NULL,
    `branch`          varchar(200)   DEFAULT NULL,
    PRIMARY KEY (`dendrimer_pk_id`),
    KEY `dendrimer_pk_id` (`dendrimer_pk_id`),
    CONSTRAINT `FK_dendrimer_nanomaterial_entity` FOREIGN KEY (`dendrimer_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dendrimer`
--

LOCK TABLES `dendrimer` WRITE;
/*!40000 ALTER TABLE `dendrimer`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `dendrimer`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emulsion`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `emulsion`
(
    `emulsion_pk_id` bigint(20) NOT NULL,
    `polymer_name`   varchar(200) DEFAULT NULL,
    `is_polymerized` tinyint(4)   DEFAULT NULL,
    PRIMARY KEY (`emulsion_pk_id`),
    KEY `emulsion_pk_id` (`emulsion_pk_id`),
    CONSTRAINT `FK_emulsion_nanomaterial_entity` FOREIGN KEY (`emulsion_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emulsion`
--

LOCK TABLES `emulsion` WRITE;
/*!40000 ALTER TABLE `emulsion`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `emulsion`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiment_condition`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `experiment_condition`
(
    `condition_pk_id` bigint(20)   NOT NULL,
    `name`            varchar(200) NOT NULL,
    `property`        varchar(200) DEFAULT NULL,
    `value`           varchar(200) NOT NULL,
    `value_unit`      varchar(200) DEFAULT NULL,
    `value_type`      varchar(200) DEFAULT NULL,
    `created_by`      varchar(200) NOT NULL,
    `created_date`    datetime     NOT NULL,
    PRIMARY KEY (`condition_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiment_condition`
--

LOCK TABLES `experiment_condition` WRITE;
/*!40000 ALTER TABLE `experiment_condition`
    DISABLE KEYS */;
INSERT IGNORE INTO `experiment_condition` (`condition_pk_id`, `name`, `property`, `value`, `value_unit`, `value_type`,
                                           `created_by`, `created_date`)
VALUES (1000, 'Synthesis condition 1', '', '42', 'g', 'observed', 'canano_user', '2019-12-06 12:15:00'),
       (1111, 'Synthesis condition 1', '', '42', 'g', 'observed', 'canano_user', '2019-12-06 12:15:00'),
       (66060288, 'dilution factor', 'other', '0.9', 'num', 'observed', 'canano_curator', '2020-01-08 19:19:47'),
       (66060289, 'dilution factor', 'other', '0.8', 'num', 'observed', 'canano_curator', '2020-01-08 19:21:27'),
       (66060290, 'dilution factor', 'other', '0.5', 'num', 'observed', 'canano_curator', '2020-01-08 19:23:07'),
       (66519040, 'drug amount', NULL, '100', NULL, NULL, 'canano_curator', '2020-01-10 13:28:18'),
       (66519041, 'chemokine', NULL, '12.4', NULL, NULL, 'canano_curator', '2020-01-10 13:28:19'),
       (66519042, 'drug amount', NULL, '100', NULL, NULL, 'canano_curator', '2020-01-10 13:29:58'),
       (66519043, 'chemokine', NULL, '9.0', NULL, NULL, 'canano_curator', '2020-01-10 13:29:59'),
       (66519044, 'drug amount', NULL, '97', NULL, NULL, 'canano_curator', '2020-01-10 13:31:38'),
       (66519045, 'chemokine', NULL, '4', NULL, NULL, 'canano_curator', '2020-01-10 13:31:39'),
       (66781184, 'dilution factor', 'other', '0.9', 'num', 'observed', 'canano_curator', '2020-01-10 20:49:53'),
       (66781185, 'dilution factor', 'other', '0.8', 'num', 'observed', 'canano_curator', '2020-01-10 20:51:33'),
       (66781186, 'dilution factor', 'other', '0.5', 'num', 'observed', 'canano_curator', '2020-01-10 20:53:13');
/*!40000 ALTER TABLE `experiment_condition`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiment_config`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `experiment_config`
(
    `experiment_config_pk_id` bigint(20)   NOT NULL,
    `description`             text,
    `created_date`            datetime     NOT NULL,
    `created_by`              varchar(200) NOT NULL,
    `characterization_pk_id`  bigint(20) DEFAULT NULL,
    `technique_pk_id`         bigint(20) DEFAULT NULL,
    PRIMARY KEY (`experiment_config_pk_id`),
    KEY `characterization_pk_id` (`characterization_pk_id`),
    KEY `technique_pk_id` (`technique_pk_id`),
    CONSTRAINT `FK_experiment_config_characterization` FOREIGN KEY (`characterization_pk_id`) REFERENCES `characterization` (`characterization_pk_id`),
    CONSTRAINT `FK_experiment_config_technique` FOREIGN KEY (`technique_pk_id`) REFERENCES `technique` (`technique_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiment_config`
--

LOCK TABLES `experiment_config` WRITE;
/*!40000 ALTER TABLE `experiment_config`
    DISABLE KEYS */;
INSERT IGNORE INTO `experiment_config` (`experiment_config_pk_id`, `description`, `created_date`, `created_by`,
                                        `characterization_pk_id`, `technique_pk_id`)
VALUES (65863680, 'PCR description', '2020-01-08 19:18:01', 'canano_curator', 65961984, 65896448),
       (66125824, 'colony counting', '2020-01-09 23:03:07', 'canano_curator', 65961985, 66158592),
       (66322432, 'Test coulter counter', '2020-01-10 13:26:14', 'canano_curator', 66420736, 66355200);
/*!40000 ALTER TABLE `experiment_config`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiment_config_instrument`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `experiment_config_instrument`
(
    `experiment_config_pk_id` bigint(20) NOT NULL,
    `instrument_pk_id`        bigint(20) NOT NULL,
    PRIMARY KEY (`experiment_config_pk_id`, `instrument_pk_id`),
    KEY `experiment_config_pk_id` (`experiment_config_pk_id`),
    KEY `instrument_pk_id` (`instrument_pk_id`),
    CONSTRAINT `FK_experiment_config_instrument_experiment_config` FOREIGN KEY (`experiment_config_pk_id`) REFERENCES `experiment_config` (`experiment_config_pk_id`),
    CONSTRAINT `FK_experiment_config_instrument_instrument` FOREIGN KEY (`instrument_pk_id`) REFERENCES `instrument` (`instrument_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiment_config_instrument`
--

LOCK TABLES `experiment_config_instrument` WRITE;
/*!40000 ALTER TABLE `experiment_config_instrument`
    DISABLE KEYS */;
INSERT IGNORE INTO `experiment_config_instrument` (`experiment_config_pk_id`, `instrument_pk_id`)
VALUES (65863680, 65929216),
       (66125824, 66191360),
       (66125824, 66191361),
       (66322432, 66387968);
/*!40000 ALTER TABLE `experiment_config_instrument`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_data`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `favorite_data`
(
    `favorite_data_id`    bigint(20)   DEFAULT NULL,
    `data_id`             bigint(20)   DEFAULT NULL,
    `data_type`           varchar(200) DEFAULT NULL,
    `data_name`           varchar(200) DEFAULT NULL,
    `login_name`          varchar(200) DEFAULT NULL,
    `protocol_file_id`    bigint(20)   DEFAULT NULL,
    `pubmed_id`           bigint(20)   DEFAULT NULL,
    `editable`            tinyint(1)   DEFAULT '0',
    `description`         text,
    `protocol_file_title` varchar(200) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_data`
--

LOCK TABLES `favorite_data` WRITE;
/*!40000 ALTER TABLE `favorite_data`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `favorite_data`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `file`
(
    `file_pk_id`      bigint(20)   NOT NULL,
    `file_name`       varchar(500)  DEFAULT NULL,
    `file_uri`        varchar(500)  DEFAULT NULL,
    `file_extension`  varchar(100)  DEFAULT NULL,
    `created_by`      varchar(200) NOT NULL,
    `created_date`    datetime     NOT NULL,
    `title`           varchar(500)  DEFAULT NULL,
    `description`     text,
    `comments`        varchar(2000) DEFAULT NULL,
    `file_type`       varchar(200)  DEFAULT NULL,
    `is_uri_external` tinyint(4)   NOT NULL,
    PRIMARY KEY (`file_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file`
    DISABLE KEYS */;
INSERT IGNORE INTO `file` (`file_pk_id`, `file_name`, `file_uri`, `file_extension`, `created_by`, `created_date`,
                           `title`, `description`, `comments`, `file_type`, `is_uri_external`)
VALUES (1000, 'NCIt_CTCAE_5.0', 'https://evs.nci.nih.gov/ftp1/CTCAE/CTCAE_5.0/NCIt_CTCAE_5.0.xlsx', 'xlsx',
        'canano_curator', '2019-12-06 12:15:00', 'Synthesis File', 'dummy row for testing of synthesis', NULL, 'Excel',
        0),
       (1005, 'NCIt_CTCAE_5.0', 'https://evs.nci.nih.gov/ftp1/CTCAE/CTCAE_5.0/NCIt_CTCAE_5.0.xlsx', 'xlsx',
        'canano_curator', '2019-08-28 00:00:00', 'Synthesis File', 'dummy row for testing of synthesis', NULL, 'Excel',
        0),
       (1111, 'NCIt_CTCAE_5.0', 'https://evs.nci.nih.gov/ftp1/CTCAE/CTCAE_5.0/NCIt_CTCAE_5.0.xlsx', 'xlsx',
        'canano_curator', '2019-12-06 12:15:00', 'Synthesis File', 'dummy row for testing of synthesis', NULL, 'Excel',
        0),
       (1222, 'NCIt_CTCAE_5.0', 'https://evs.nci.nih.gov/ftp1/CTCAE/CTCAE_5.0/NCIt_CTCAE_5.0.xlsx', 'xlsx',
        'canano_curator', '2019-08-28 00:00:00', 'Synthesis File', 'Sample 2 test file', NULL, 'Excel', 0),
       (1333, 'NCIt_CTCAE_5.0', 'https://evs.nci.nih.gov/ftp1/CTCAE/CTCAE_5.0/NCIt_CTCAE_5.0.xlsx', 'xlsx',
        'canano_curator', '2019-12-06 12:15:00', 'Synthesis File', 'dummy row for testing of synthesis', NULL, 'Excel',
        0),
       (1444, 'NCIt_CTCAE_5.0', 'https://evs.nci.nih.gov/ftp1/CTCAE/CTCAE_5.0/NCIt_CTCAE_5.0.xlsx', 'xlsx',
        'canano_curator', '2019-12-06 12:15:00', 'Synthesis File', 'dummy row for testing of synthesis', NULL, 'Excel',
        0),
       (1555, 'NCIt_CTCAE_5.0', 'https://evs.nci.nih.gov/ftp1/CTCAE/CTCAE_5.0/NCIt_CTCAE_5.0.xlsx', 'xlsx',
        'canano_curator', '2019-12-06 12:15:00', 'Synthesis File', 'dummy row for testing of synthesis', NULL, 'Excel',
        0),
       (66289664, '2020 Holiday & Pay Calendar.pdf', 'protocols/20200110_12-52-36-363_2020 Holiday & Pay Calendar.pdf',
        NULL, 'canano_curator', '2020-01-10 12:52:34', 'Synthesis Test Protocol and Holiday Calendar',
        'Uploaded pdf of holiday calendar as a stand-in file for a protocol', NULL, NULL, 0),
       (66715648, 'Finding.png',
        'particles/Synthesis Sample Jan82020 2/metabolicstability/20200110_20-49-52-093_Finding.png', NULL,
        'canano_curator', '2020-01-10 20:49:52', 'jpg', 'finding', NULL, 'image', 0);
/*!40000 ALTER TABLE `file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finding`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `finding`
(
    `finding_pk_id`          bigint(20)   NOT NULL,
    `characterization_pk_id` bigint(20) DEFAULT NULL,
    `created_by`             varchar(200) NOT NULL,
    `created_date`           datetime     NOT NULL,
    UNIQUE KEY `finding_pk_id` (`finding_pk_id`),
    KEY `characterization_pk_id` (`characterization_pk_id`),
    CONSTRAINT `FK_finding_characterization` FOREIGN KEY (`characterization_pk_id`) REFERENCES `characterization` (`characterization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finding`
--

LOCK TABLES `finding` WRITE;
/*!40000 ALTER TABLE `finding`
    DISABLE KEYS */;
INSERT IGNORE INTO `finding` (`finding_pk_id`, `characterization_pk_id`, `created_by`, `created_date`)
VALUES (65994752, 65961984, 'canano_curator', '2020-01-08 19:19:46'),
       (66453504, 66420736, 'canano_curator', '2020-01-10 13:28:17');
/*!40000 ALTER TABLE `finding`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finding_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `finding_file`
(
    `finding_pk_id` bigint(20) NOT NULL,
    `file_pk_id`    bigint(20) NOT NULL,
    PRIMARY KEY (`finding_pk_id`, `file_pk_id`),
    KEY `file_pk_id` (`file_pk_id`),
    KEY `finding_pk_id` (`finding_pk_id`),
    CONSTRAINT `FK_finding_file_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_finding_file_finding` FOREIGN KEY (`finding_pk_id`) REFERENCES `finding` (`finding_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finding_file`
--

LOCK TABLES `finding_file` WRITE;
/*!40000 ALTER TABLE `finding_file`
    DISABLE KEYS */;
INSERT IGNORE INTO `finding_file` (`finding_pk_id`, `file_pk_id`)
VALUES (65994752, 66715648);
/*!40000 ALTER TABLE `finding_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fullerene`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `fullerene`
(
    `fullerene_pk_id`       bigint(20) NOT NULL,
    `number_of_carbon`      decimal(22, 0) DEFAULT NULL,
    `average_diameter`      decimal(22, 3) DEFAULT NULL,
    `average_diameter_unit` varchar(200)   DEFAULT NULL,
    PRIMARY KEY (`fullerene_pk_id`),
    KEY `fullerene_pk_id` (`fullerene_pk_id`),
    CONSTRAINT `FK_fullerene_nanomaterial_entity` FOREIGN KEY (`fullerene_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fullerene`
--

LOCK TABLES `fullerene` WRITE;
/*!40000 ALTER TABLE `fullerene`
    DISABLE KEYS */;
INSERT IGNORE INTO `fullerene` (`fullerene_pk_id`, `number_of_carbon`, `average_diameter`, `average_diameter_unit`)
VALUES (65667073, 6, 3.300, ' nm');
/*!40000 ALTER TABLE `fullerene`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `functionalizing_entity`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `functionalizing_entity`
(
    `functionalizing_entity_pk_id` bigint(20) NOT NULL,
    `activation_method_pk_id`      bigint(20) DEFAULT NULL,
    `composition_pk_id`            bigint(20) DEFAULT NULL,
    PRIMARY KEY (`functionalizing_entity_pk_id`),
    KEY `activation_method_pk_id` (`activation_method_pk_id`),
    KEY `functionalizing_entity_pk_id` (`functionalizing_entity_pk_id`),
    KEY `composition_pk_id` (`composition_pk_id`),
    CONSTRAINT `FK_functionalizing_entity_activation_method` FOREIGN KEY (`activation_method_pk_id`) REFERENCES `activation_method` (`activation_method_pk_id`),
    CONSTRAINT `FK_functionalizing_entity_associated_element` FOREIGN KEY (`functionalizing_entity_pk_id`) REFERENCES `associated_element` (`associated_element_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functionalizing_entity`
--

LOCK TABLES `functionalizing_entity` WRITE;
/*!40000 ALTER TABLE `functionalizing_entity`
    DISABLE KEYS */;
INSERT IGNORE INTO `functionalizing_entity` (`functionalizing_entity_pk_id`, `activation_method_pk_id`,
                                             `composition_pk_id`)
VALUES (65732609, 65765376, 65699841);
/*!40000 ALTER TABLE `functionalizing_entity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `functionalizing_entity_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `functionalizing_entity_file`
(
    `functionalizing_entity_pk_id` bigint(20) NOT NULL,
    `file_pk_id`                   bigint(20) NOT NULL,
    PRIMARY KEY (`functionalizing_entity_pk_id`, `file_pk_id`),
    KEY `functionalizing_entity_pk_id` (`functionalizing_entity_pk_id`),
    KEY `file_pk_id` (`file_pk_id`),
    CONSTRAINT `FK_functionalizing_entity_file_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_functionalizing_entity_file_functionalizing_entity` FOREIGN KEY (`functionalizing_entity_pk_id`) REFERENCES `functionalizing_entity` (`functionalizing_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functionalizing_entity_file`
--

LOCK TABLES `functionalizing_entity_file` WRITE;
/*!40000 ALTER TABLE `functionalizing_entity_file`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `functionalizing_entity_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_authorities`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `group_authorities`
(
    `group_id`  int(11)     NOT NULL,
    `authority` varchar(50) NOT NULL,
    KEY `fk_group_members_group_idx` (`group_id`),
    CONSTRAINT `fk_group_authorities_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_authorities`
--

LOCK TABLES `group_authorities` WRITE;
/*!40000 ALTER TABLE `group_authorities`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `group_authorities`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_members`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `group_members`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` varchar(100) NOT NULL,
    `group_id` int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_group_members_group_idx` (`group_id`),
    KEY `fk_group_members_username_idx` (`username`),
    CONSTRAINT `fk_group_members_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_group_members_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_members`
--

LOCK TABLES `group_members` WRITE;
/*!40000 ALTER TABLE `group_members`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `group_members`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `groups`
(
    `id`                int(11)      NOT NULL AUTO_INCREMENT,
    `group_name`        varchar(50)  NOT NULL,
    `group_description` varchar(200) DEFAULT NULL,
    `created_by`        varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `group_name_UNIQUE` (`group_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `groups`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_unique_key`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `hibernate_unique_key`
(
    `next_hi` bigint(20) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_unique_key`
--

LOCK TABLES `hibernate_unique_key` WRITE;
/*!40000 ALTER TABLE `hibernate_unique_key`
    DISABLE KEYS */;
INSERT IGNORE INTO `hibernate_unique_key` (`next_hi`)
VALUES (2042);
/*!40000 ALTER TABLE `hibernate_unique_key`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instrument`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `instrument`
(
    `instrument_pk_id` bigint(20)   NOT NULL,
    `type`             varchar(200)  DEFAULT NULL,
    `manufacturer`     varchar(2000) DEFAULT NULL,
    `model_name`       varchar(200)  DEFAULT NULL,
    `created_date`     datetime     NOT NULL,
    `created_by`       varchar(200) NOT NULL,
    PRIMARY KEY (`instrument_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instrument`
--

LOCK TABLES `instrument` WRITE;
/*!40000 ALTER TABLE `instrument`
    DISABLE KEYS */;
INSERT IGNORE INTO `instrument` (`instrument_pk_id`, `type`, `manufacturer`, `model_name`, `created_date`, `created_by`)
VALUES (6, 'flow cytometer', 'Becton Dickinson', 'FACS Calibur', '2009-10-14 09:17:05', 'canano_admin'),
       (1000, 'scale', 'Biome', 'Test Scale', '2019-12-06 12:15:00', 'canano_curator'),
       (1005, 'centrifuge', 'Heraeus', 'Contifuge 17RS', '2019-08-28 00:00:00', 'canano_curator'),
       (1111, 'scale', 'Biome', 'Test Scale', '2019-12-06 12:15:00', 'canano_curator'),
       (1222, 'centrifuge', 'Heraeus', 'Contifuge 17RS', '2019-08-28 00:00:00', 'canano_curator'),
       (8552448, 'zeta potential analyzer', 'Malvern', 'Zetasizer 1000', '2009-10-14 09:17:05', 'canano_admin'),
       (9502721, 'zeta potential analyzer', 'Malvern', 'Zetasizer Nano ZS', '2009-10-14 09:17:05', 'canano_admin'),
       (13795328, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS', '2009-10-14 13:44:14',
        'canano_admin'),
       (13795329, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS', '2009-10-14 13:54:34',
        'canano_admin'),
       (13795330, 'fluorescence excitation device', '', 'home made fluorescence excitation device',
        '2009-10-14 15:02:30', 'canano_admin'),
       (13795331, 'cooled digital camera', 'Hamamatsu', 'Orca II BT 512 G', '2009-10-14 15:04:41', 'canano_admin'),
       (14647296, '', 'FEI', 'Tecnai 10', '2009-10-22 14:42:54', 'canano_admin'),
       (14647297, 'research microscope', 'Olympus', 'BH2', '2009-10-22 15:45:13', 'canano_admin'),
       (14647298, 'digital camera', 'Soft Imaging Systems', 'MegaView III', '2009-10-27 11:13:40', 'canano_admin'),
       (14647299, '', 'FEI', 'Tecnai 10', '2009-10-27 11:14:05', 'canano_admin'),
       (14647300, '', 'Molecular Devices', 'Gemini EM', '2009-10-27 16:03:04', 'canano_admin'),
       (14647301, '', '', 'Burker chamber', '2009-10-29 09:25:12', 'canano_admin'),
       (14647302, 'transmission electron microscope', 'FEI', 'Tecnai 10', '2009-10-29 09:58:28', 'canano_admin'),
       (14647303, '', '', 'Burker chamber', '2009-10-29 10:58:34', 'canano_admin'),
       (15106048, 'hemocytometer', '', '', '2009-11-02 14:31:31', 'canano_admin'),
       (15106049, 'gas sorption detector', 'Micromeritics', 'FlowSorb II 2300', '2009-11-06 10:36:16', 'canano_admin'),
       (15106050, 'gas sorption detector', 'Micromeritics', 'Gemini V', '2009-11-06 10:36:15', 'canano_admin'),
       (15106051, 'flow cytometer', 'Becton Dickinson', ' FACS Calibur', '2009-11-06 16:52:34', 'canano_admin'),
       (15106052, 'flow cytometer', 'Becton Dickinson', 'FACSCalibur', '2009-11-09 08:52:43', 'canano_admin'),
       (15106053, 'flow cytometer', 'Becton Dickinson', ' FACSCalibur', '2009-11-09 10:31:03', 'canano_admin'),
       (15106054, 'laser diffraction instrument', 'Malvern', 'Metasizer 2000', '2009-11-11 09:06:05', 'canano_admin'),
       (15106055, 'scanning electron microscope', 'Zeiss', 'LEO 1530', '2009-11-11 10:57:09', 'canano_admin'),
       (15106056, 'scanning tunneling microscope', 'Zeiss', 'LEO 1530', '2009-11-11 11:24:38', 'canano_admin'),
       (15106057, 'spectrophotometer', 'Varian', 'FTS 7000', '2009-11-11 11:32:43', 'canano_admin'),
       (15106058, '', 'Philips', 'XDC-700 Guinier–Hagg type camera', '2009-11-11 11:41:07', 'canano_admin'),
       (15106059, '', 'Philips', 'XDC-700 Guinier-Hagg type camera', '2009-11-11 11:41:41', 'canano_admin'),
       (15106060, 'X-ray photoelectron spectrometer', 'Kratos Analytical', 'AXIS Ultra DLD', '2009-11-11 11:46:26',
        'canano_admin'),
       (15106061, 'atomic absorption spectrometer', 'PerkinElmer', 'AAnalyst 800', '2009-11-11 13:46:14',
        'canano_admin'),
       (15106062, 'laser diffraction instrument', 'Malvern', 'Mastersizer 2000', '2009-11-11 14:59:15', 'canano_admin'),
       (15106063, 'X-ray diffraction spectrometer', 'Philips', 'XDC-700 Guinier–Hagg type camera',
        '2009-11-12 08:20:34', 'canano_admin'),
       (15106064, 'atomic absorption spectrometer', 'PerkinElmer', 'Analyst 800', '2009-11-12 08:26:40',
        'canano_admin'),
       (15106065, 'X-ray diffraction spectrometer', 'Philips', 'XDC-700 Guinier-Hagg type camera',
        '2009-11-12 11:45:46', 'canano_admin'),
       (15663104, 'nuclear magnetic resonance spectrometer', 'Varian', 'INOVA-400', '2009-12-10 15:53:46',
        'canano_admin'),
       (15663105, 'nuclear magnetic resonance instrument', 'Varian', 'INOVA-400', '2009-12-11 08:13:20',
        'canano_admin'),
       (15663106, 'spectrophotometer', 'Molecular Devices', 'VersaMax', '2009-12-23 10:21:14', 'canano_admin'),
       (15663107, 'spectrophotometer', 'Molecular Devices', 'Gemini XPS', '2009-12-23 10:45:52', 'canano_admin'),
       (15663108, 'transmission electron microscope', 'Hitachi', 'H 7000', '2009-12-30 10:54:27', 'canano_admin'),
       (15663109, 'transmission electron microscope', 'JEOL', '1400', '2010-01-05 11:27:04', 'canano_admin'),
       (15663110, '', 'CPS Instruments', 'Disc Centrifuge', '2010-01-05 11:32:22', 'canano_admin'),
       (15663111, 'spectrophotometer', 'Varian', '50', '2010-01-05 11:49:04', 'canano_admin'),
       (15663112, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer Nano S90', '2010-01-05 12:27:28',
        'canano_admin'),
       (15663113, 'spectrophotometer', 'Varian', '', '2010-01-05 12:41:30', 'canano_admin'),
       (15663114, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano S90', '2010-01-05 12:48:45',
        'canano_admin'),
       (15663115, 'spectrophotometer', 'Dynatech', 'MR5000', '2010-01-05 13:26:56', 'canano_admin'),
       (15663116, '', 'Wyatt Technologies', 'HELEOS', '2010-01-07 12:39:09', 'canano_admin'),
       (15663117, 'spectrophotometer', 'Waters', '2489 UV/Visible detector', '2010-01-07 12:39:08', 'canano_admin'),
       (15663118, 'separation column', 'Waters', '2695 Separations Module', '2010-01-07 12:39:07', 'canano_admin'),
       (15663119, 'multi angle light scattering detector', 'Wyatt Technologies', 'HELEOS ', '2010-01-07 12:52:36',
        'canano_admin'),
       (15663120, 'refractometer', 'Wyatt Technologies', 'Optilab rEX', '2010-01-07 12:52:37', 'canano_admin'),
       (15663121, 'control module', 'Waters', 'Delta 600', '2010-01-07 13:30:09', 'canano_admin'),
       (15663122, 'HPLC autosampler', 'Waters', '717 Plus Autosampler', '2010-01-07 13:54:44', 'canano_admin'),
       (15663123, 'spectrophotometer', 'Waters', '2996 photodiode array detector', '2010-01-07 13:54:43',
        'canano_admin'),
       (15663124, 'fraction collector', 'Waters', 'Fraction  Collector III', '2010-01-07 13:58:18', 'canano_admin'),
       (15663125, 'separation column', 'TosoHaas', 'G 3000 PW 05762 (300 mm x 7.5 mm, 10 mm)', '2010-01-07 17:15:30',
        'canano_admin'),
       (15663126, 'separation column', 'TosoHaas', 'TSK-Gel Guard PHW 06762 (75 mm x 7.5 mm, 12 mm) ',
        '2010-01-07 17:15:28', 'canano_admin'),
       (15663127, 'separation column', 'TosoHaas', 'G 4000 PW (300 mm x 7.5 mm, 17 mm) ', '2010-01-07 17:15:31',
        'canano_admin'),
       (15663128, 'separation column', 'TosoHaas', 'G 2000 PW 05761 (300 mm x 7.5 mm, 10 mm) ', '2010-01-07 17:15:29',
        'canano_admin'),
       (15663129, 'separation column', 'TosoHaas', 'G 3000 PW 05762 (300 mm x 7.5 mm, 12 microm)',
        '2010-01-08 10:05:01', 'canano_admin'),
       (15663130, 'guard column', 'TosoHaas', 'TSK-Gel Guard PHW 06762 (7.5 mm x 7.5 mm, 13 microm) ',
        '2010-01-08 10:04:59', 'canano_admin'),
       (15663131, '', 'TosoHaas', 'G 4000 PW 05107 (300 mm x 7.5 mm, 17 microm) ', '2010-01-08 10:05:02',
        'canano_admin'),
       (15663132, 'separations module', 'Waters', '2695 Separations Module', '2010-01-08 10:04:55', 'canano_admin'),
       (15663133, 'separation column', 'TosoHaas', ' G 2000 PW 05761 (300 mm x 7.5 mm, 12 microm)',
        '2010-01-08 10:05:00', 'canano_admin'),
       (15663134, 'separation column', 'TosoHaas', 'G3000PW 05762 (300 mm x 7.5 mm, 12 microm)', '2010-01-08 10:07:02',
        'canano_admin'),
       (15663135, 'separation column', 'TosoHaas', 'G4000PW 05107 (300 mm x 7.5 mm, 17 microm) ', '2010-01-08 10:07:03',
        'canano_admin'),
       (15663136, 'separation column', 'TosoHaas', 'G2000PW 05761 (300 mm x 7.5 mm, 12 microm) ', '2010-01-08 10:07:01',
        'canano_admin'),
       (15663137, 'separation column', 'TosoHaas', 'G4000PW 05107 (300 mm x 7.5 mm, 17 microm', '2010-01-08 10:28:50',
        'canano_admin'),
       (15663138, 'separation column', 'TosoHaas', 'G4000PW 05763 (300 mm x 7.5 mm, 17 microm) ', '2010-01-08 12:03:36',
        'canano_admin'),
       (15663139, 'separation column', 'TosoHaas', 'G4000PW 05763 (300 mm x 7.5 mm, 17 microm', '2010-01-08 12:09:13',
        'canano_admin'),
       (15663140, 'transmission electron microscope', 'JEOL', '1220', '2010-01-14 11:25:16', 'canano_admin'),
       (15663141, 'atomic force microscope', 'Park Systems', 'XE-100', '2010-01-14 15:44:12', 'canano_admin'),
       (15663142, 'balance', 'Sartorius', 'MC 5', '2010-01-14 16:23:54', 'canano_admin'),
       (15663143, 'dynamic light scattering instrument', 'Malvern', 'Zetamaster', '2010-01-19 16:16:59',
        'canano_admin'),
       (15663144, 'digital imaging capture system', 'Point Electronic GmBh', 'DISS', '2010-01-19 16:25:23',
        'canano_admin'),
       (15663145, 'scanning electron microscope', 'Zeiss', 'DSM 940A', '2010-01-19 16:25:22', 'canano_admin'),
       (15663146, 'electrophoretic light scattering instrument', 'Malvern', 'Zetamaster', '2010-01-19 16:28:15',
        'canano_admin'),
       (15663147, 'elemental analysis instrument', 'LECO', 'CHN-900', '2010-01-19 16:46:35', 'canano_admin'),
       (15663148, 'liquid chromatograph/mass spectrometer ion-trap system', 'Agilent', '1100 series LC',
        '2010-01-20 09:36:33', 'canano_admin'),
       (15663149, 'evaporative light scattering detector', 'Alltech', 'ELSD', '2010-01-20 09:44:00', 'canano_admin'),
       (15663150, 'spectrophotometer', 'Labsystems', 'iEMS Reader MF', '2010-01-22 13:12:36', 'canano_admin'),
       (15663151, 'evaporative light scattering detector', 'Alltech', 'CHN-900', '2010-01-22 15:24:37', 'canano_admin'),
       (15663152, 'separation column', 'Agilent', 'reversed-phase NH2-Zorbax  (4.6mm x 150mm, 5 microm)',
        '2010-01-22 15:24:36', 'canano_admin'),
       (15663153, 'spectrophotometer', 'Tecan', 'GENios', '2010-01-25 08:42:10', 'canano_admin'),
       (15663154, 'research microscope with fluorescence source', 'Olympus', 'CH40-URFLT50', '2010-01-25 10:07:46',
        'canano_admin'),
       (15663155, 'gamma camera', 'Siemens Medical', 'e.cam dual-head variable-angle system', '2010-01-25 13:53:25',
        'canano_admin'),
       (15663156, 'immersion transducer', 'Panametrics', 'V324', '2010-01-26 12:07:33', 'canano_admin'),
       (15663157, 'ultrasonic pulser-receiver', 'Panametrics', 'Panametrics 5900', '2010-01-26 12:21:20',
        'canano_admin'),
       (15663158, '4 axis programmable stage controller', 'Aerotech', 'Unidex 12', '2010-01-26 13:12:59',
        'canano_admin'),
       (15663159, 'gas chromatograph', 'Agilent', '6890', '2010-01-26 15:24:55', 'canano_admin'),
       (15663160, 'ultrasonic pulser-receiver', 'Panametrics', '5900', '2010-01-26 16:06:51', 'canano_admin'),
       (15663161, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer', '2010-01-28 11:20:31', 'canano_admin'),
       (15663162, 'spectrophotometer', 'Hitachi', 'F-4500 fluorescence spectrophotometer', '2010-01-28 14:07:22',
        'canano_admin'),
       (15663163, 'nuclear magnetic resonance instrument', 'Bruker', 'minispec mq20', '2010-02-09 09:29:16',
        'canano_admin'),
       (15663164, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer 1000', '2010-02-09 13:51:22',
        'canano_admin'),
       (15663165, '', 'Malvern', 'Zetasizer 1000', '2010-02-09 13:52:42', 'canano_admin'),
       (15663166, '', 'Malvern', 'Zetasizer 1000', '2010-02-10 12:05:37', 'canano_admin'),
       (15663167, '', 'Malvern', 'Zetasizer 1000', '2010-02-11 12:57:42', 'canano_admin'),
       (15663168, '', 'Malvern', 'Zetasizer 1000', '2010-02-11 13:01:34', 'canano_admin'),
       (15663169, '', 'Malvern', 'Zetasizer 1000', '2010-02-11 13:05:30', 'canano_admin'),
       (15663170, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer 1000', '2010-02-11 13:20:22',
        'canano_admin'),
       (15663171, 'gamma counter', 'Packard', 'Riastar', '2010-02-15 10:36:07', 'canano_admin'),
       (15663172, 'research microscope', 'Olympus', 'BX40', '2010-02-15 15:22:19', 'canano_admin'),
       (15663173, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer 4', '2010-02-18 10:56:57',
        'canano_admin'),
       (15663174, 'single-sided NMR probe', 'ACT GmbH', 'Profile  NMR-MOUSE®', '2010-02-24 15:45:40', 'canano_admin'),
       (15663175, 'nuclear magnetic resonance spectrometer', 'Bruker', 'minispec', '2010-02-24 15:45:39',
        'canano_admin'),
       (15663176, 'spectrophotometer', '', '', '2010-02-25 09:15:37', 'canano_admin'),
       (15663177, 'spectrophotometer', '', '', '2010-02-25 12:13:40', 'canano_admin'),
       (15663178, 'transmission electron microscope', 'Philips', 'EM 420', '2010-03-02 14:31:41', 'canano_admin'),
       (15663179, 'maldi-tof mass spectrometer', 'Applied Biosystems', 'Voyager-DE PRO ', '2010-03-02 14:57:07',
        'canano_admin'),
       (15663180, 'scintillation counter', 'Packard', '', '2010-03-02 15:48:56', 'canano_admin'),
       (15663181, 'dynamic light scattering instrument', 'Wyatt Technologies', 'DynaPro', '2010-03-02 17:09:48',
        'canano_admin'),
       (15663182, 'MRI scanner', 'Siemens Medical', 'MAGNETOM Trio, A Tim System 3T', '2010-03-03 09:23:19',
        'canano_admin'),
       (15663183, 'inductively coupled plasma-atomic emission spectrometer', 'Thermo Scientific',
        'TJA IRIS Advantage/1000', '2010-03-03 10:39:33', 'canano_admin'),
       (15663184, '', 'Quantum Design', 'MPMS-XL', '2010-03-03 10:59:41', 'canano_admin'),
       (15663185, 'SQUID sample magnetometer', 'Quantum Design', 'MPMS-XL', '2010-03-03 13:09:10', 'canano_admin'),
       (15663186, 'MRI scanner', 'GE Healthcare', 'Excite 1.5 T', '2010-03-04 09:37:24', 'canano_admin'),
       (15663187, 'MRI scanner', 'GE Healthcare', 'Systems Revision 12.0 M5', '2010-03-04 09:47:33', 'canano_admin'),
       (15663188, 'PET scanner', 'Siemens Medical', 'microPET R4', '2010-03-04 11:15:25', 'canano_admin'),
       (15663189, 'laser light source', 'B&W Tek', '', '2010-03-05 15:37:13', 'canano_admin'),
       (15663190, 'spectrophotometer', 'Varian', 'Cary 50', '2010-03-05 17:25:32', 'canano_admin'),
       (15663191, 'research microscope with fluorescence source', 'Nikon', 'TE2000', '2010-03-05 17:56:46',
        'canano_admin'),
       (15663192, 'research microscope', 'Nikon', 'TE2000', '2010-03-08 10:00:10', 'canano_admin'),
       (15663193, 'maldi-tof mass spectrometer', 'Micromass', 'TofSpec-2E', '2010-03-08 16:06:28', 'canano_admin'),
       (15663194, 'zeta potential analyzer', 'Malvern', 'Zetasizer Nano ZS ZEN3600', '2010-03-08 16:11:13',
        'canano_admin'),
       (15663195, 'zeta potential analyzer', 'Malvern', 'Zetasizer Nano ZS ZEN 3600', '2010-03-08 16:15:41',
        'canano_admin'),
       (15663196, 'digital camera', 'Hamamatsu', 'ORCA-HR', '2010-03-08 17:04:50', 'canano_admin'),
       (15663197, 'transmission electron microscope', 'Philips', 'CM-100', '2010-03-08 17:04:49', 'canano_admin'),
       (15663198, 'transmission electron microscope', 'Philips', 'CM 100', '2010-03-09 08:52:33', 'canano_admin'),
       (15663199, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS ZEN3600',
        '2010-03-09 08:57:17', 'canano_admin'),
       (15663200, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS ZEN 3600',
        '2010-03-09 10:05:42', 'canano_admin'),
       (15663201, 'nuclear magnetic resonance spectrometer', 'Varian', 'Unity Inova', '2010-03-09 13:33:34',
        'canano_admin'),
       (15663202, 'nuclear magnetic resonance spectrometer', 'Bruker', 'DRX500', '2010-03-10 09:13:01', 'canano_admin'),
       (15663203, 'spectrophotometer', 'PerkinElmer', 'Lambda 20', '2010-03-10 10:43:51', 'canano_admin'),
       (15663204, 'transmission electron microscope', 'Philips', 'CM100', '2010-03-10 14:14:35', 'canano_admin'),
       (15663205, 'NMR spectrometer', 'Varian', 'Unity Inova', '2010-03-11 11:14:43', 'canano_admin'),
       (15663206, 'inverted research microscope', 'Olympus', 'XR71', '2010-03-11 11:42:15', 'canano_admin'),
       (15663207, 'laser scanning confocal microscope', 'Olympus', 'FluoView 500', '2010-03-11 11:42:14',
        'canano_admin'),
       (15663208, 'spectrophotometer', 'Agilent', '8453', '2010-03-15 09:02:33', 'canano_admin'),
       (15663209, 'transmission electron microscope', 'JEOL', '2010F', '2010-03-15 09:10:10', 'canano_admin'),
       (15663210, 'spectrophotometer', 'Photon Technology International', 'FluoDia T70', '2010-03-17 16:50:57',
        'canano_admin'),
       (15663211, 'spectrophotometer', 'BioTek', 'PowerWave', '2010-03-18 12:39:20', 'canano_admin'),
       (15663212, 'digital camera', 'Olympus', 'DP20', '2010-03-18 12:54:34', 'canano_admin'),
       (15663213, 'inverted research microscope', 'Olympus', 'IX51', '2010-03-18 12:54:33', 'canano_admin'),
       (15663214, 'digital camera', 'Olympus', 'DP70', '2010-03-19 09:20:31', 'canano_admin'),
       (15663215, 'flow cytometer', 'Guava Technologies/Millipore', 'EasyCite Mini', '2010-03-22 09:29:40',
        'canano_admin'),
       (15663216, 'inductively coupled plasma-atomic emission spectrometer', 'Horiba', 'ULTIMA 2',
        '2010-03-29 15:25:43', 'canano_admin'),
       (15663217, 'energy dispersive X-ray spectrometer', 'EDAX', 'Genesis 4000', '2010-03-29 15:39:19',
        'canano_admin'),
       (15663218, 'scanning transmission electron microscope', 'Hitachi', 'HD2300', '2010-03-29 15:39:18',
        'canano_admin'),
       (15663219, 'spectrophotometer', 'Biorad', '680', '2010-03-29 15:44:37', 'canano_admin'),
       (16646144, 'transmission electron microscope', 'Zeiss', '902', '2010-04-12 10:13:52', 'canano_admin'),
       (16646145, 'spectrophotometer', 'Biorad', '550', '2010-04-12 10:36:13', 'canano_admin'),
       (16646146, 'laser scanning confocal microscope', 'Zeiss', 'LSM 510', '2010-04-13 10:44:15', 'canano_admin'),
       (16646147, 'surface plasmon resonance instrument', 'GE Healthcare', 'Biacore X', '2010-04-13 14:35:51',
        'canano_admin'),
       (20000011, '', 'Agilent', '', '2009-10-06 12:29:01', 'canano_admin'),
       (25373952, '', 'Malvern', '', '2009-10-10 16:37:34', 'canano_admin'),
       (25439488, '', 'Wyatt Technologies', '', '2009-10-12 12:29:43', 'canano_admin'),
       (25439489, '', 'Malvern', '', '2009-10-12 12:35:55', 'canano_admin'),
       (25439490, 'dynamic light scattering instrument', 'Malvern', '', '2009-10-12 12:38:05', 'canano_admin'),
       (25636096, 'spectrophotometer', 'Thermo Electron', 'Evolution 300', '2009-10-14 17:45:54', 'canano_admin'),
       (26804224, 'spectrophotometer', 'BioTek', 'Synergy HT', '2010-05-11 10:20:36', 'canano_admin'),
       (26804225, 'spectrofluorometer', 'Molecular Devices', 'Gemini EM', '2010-05-11 10:26:37', 'canano_admin'),
       (26804226, 'scanning electron microscope', 'Hitachi', 'S-4700', '2010-05-13 14:37:59', 'canano_admin'),
       (26804227, 'dynamic light scattering instrument', 'Brookhaven Instruments', '90Plus', '2010-05-13 14:45:16',
        'canano_admin'),
       (26804228, 'electrophoretic light scattering instrument', 'Brookhaven Instruments', 'ZetaPlus',
        '2010-05-13 14:53:54', 'canano_admin'),
       (26804229, 'spectrophotometer', 'Biorad', '3550', '2010-05-13 15:00:21', 'canano_admin'),
       (26804230, 'flow cytometer', 'Dako', 'CyAn ADP', '2010-05-13 17:32:28', 'canano_admin'),
       (26804231, 'research microscope', 'Olympus', '', '2010-05-13 18:05:07', 'canano_admin'),
       (26804232, 'research microscope', 'Leica', 'DMI4000B', '2010-05-14 11:52:11', 'canano_admin'),
       (27459584, 'coulter counter', 'Beckman/Coulter', 'Z2 COULTER COUNTER', '2010-06-04 17:43:07', 'canano_admin'),
       (27459585, '', 'JEOL', '1400', '2010-06-07 16:06:11', 'canano_admin'),
       (27459586, 'differential centrifugal sedimentation instrument', 'CPS Instruments', 'DCS', '2010-06-07 16:14:44',
        'canano_admin'),
       (27459587, 'scintillation counter', 'Packard', 'Riastar', '2010-06-07 16:35:36', 'canano_admin'),
       (28213248, 'electrophoretic light scattering instrument', 'Brookhaven Instruments', '90Plus',
        '2010-06-09 10:20:06', 'canano_admin'),
       (28213249, 'microarray scanner', 'PerkinElmer', 'ScanArray ExpressHT', '2010-06-09 11:22:52', 'canano_admin'),
       (28213250, 'microarray scanner', 'Affymetrix', 'GeneChip Scanner 3000', '2010-06-09 13:49:13', 'canano_admin'),
       (28213251, 'bioanalyzer', 'Agilent', '2100 Bioanalyzer ', '2010-06-09 13:56:06', 'canano_admin'),
       (28213252, 'qRT-PCR instrument', 'Roche Applied Science', 'Lightcycler II', '2010-06-10 12:30:19',
        'canano_admin'),
       (28213253, 'thermal cycler', 'Roche Applied Science', 'Lightcycler II', '2010-06-10 14:16:02', 'canano_admin'),
       (28213254, '', 'Roche Applied Science', 'Lightcycler II', '2010-06-10 14:16:46', 'canano_admin'),
       (28213255, 'transmission electron microscope', 'JEOL', 'JEM 2020', '2010-06-11 17:49:47', 'canano_admin'),
       (28213256, 'spectrophotometer', 'Shimadzu', 'UV-3600', '2010-06-11 18:05:11', 'canano_admin'),
       (28213257, 'spectrofluorometer', 'Jobin Yvon', 'Fluorolog-3', '2010-06-11 18:13:31', 'canano_admin'),
       (28475392, 'digital camera', 'Olympus', 'DP71', '2010-06-15 14:14:26', 'canano_admin'),
       (28475393, 'MRI scanner', 'GE Healthcare', 'HDx', '2010-06-15 15:29:35', 'canano_admin'),
       (28475394, 'inverted research microscope', 'Olympus', 'IX71', '2010-06-16 15:21:09', 'canano_admin'),
       (28475395, ' fluorescence lifetime microscope system ', 'Picoquant', 'MicroTime 200', '2010-06-17 10:09:40',
        'canano_admin'),
       (28475396, ' fluorescence lifetime microscope system ', 'OBB Corp', 'EasyLife L', '2010-06-22 11:49:34',
        'canano_admin'),
       (28475397, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS model ZEN 3600',
        '2010-06-22 13:13:26', 'canano_admin'),
       (28475398, 'nuclear magnetic resonance instrument', 'Varian', 'Unity Inova', '2010-06-22 13:57:07',
        'canano_admin'),
       (28475399, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS  ZEN 3600',
        '2010-06-22 15:03:52', 'canano_admin'),
       (28475400, 'spectrophotometer', 'PerkinElmer', 'Spectrum GX', '2010-06-24 16:49:17', 'canano_admin'),
       (28475401, 'fourier transform infrared spectrophotometer', 'PerkinElmer', 'Spectrum GX', '2010-06-24 16:53:56',
        'canano_admin'),
       (28475402, 'inductively coupled plasma-atomic emission spectrometer', 'PerkinElmer', 'Optima 2000 DV',
        '2010-06-24 17:04:03', 'canano_admin'),
       (28475403, 'electrophoretic light scattering instrument', 'Brookhaven Instruments', 'ZetaPALS',
        '2010-06-25 14:49:25', 'canano_admin'),
       (28475404, 'scanning electron microscope', 'Hitachi', 'S-4800', '2010-06-25 14:55:21', 'canano_admin'),
       (28475405, 'spectrophotometer', 'Shimadzu', 'UV160U', '2010-07-12 13:38:11', 'canano_admin'),
       (28475406, 'imaging system', 'Kodak', 'Gel Logic 100', '2010-07-13 16:41:11', 'canano_admin'),
       (28475407, 'research microscope', 'Olympus', 'BX61', '2010-07-13 16:53:50', 'canano_admin'),
       (28475408, 'flow cytometer', 'Beckman/Coulter', 'EPICS ALTRA', '2010-07-13 16:57:29', 'canano_admin'),
       (28475409, 'transmission electron microscope', 'JEOL', '', '2010-07-15 14:57:54', 'canano_admin'),
       (28475410, 'dual absorbance detector', 'Waters', '2487', '2010-07-15 15:03:27', 'canano_admin'),
       (28475411, 'separation column', 'Agilent', 'ZORBAX 5 microm 4.6 x 150 mm', '2010-07-16 16:58:15',
        'canano_admin'),
       (28475412, 'centrifugal filter unit', 'Millipore', 'Centricon', '2010-07-16 17:17:59', 'canano_admin'),
       (28475413, 'separation column', 'Agilent', 'ZORBAX 5 microm 4.6 mm x 150 mm', '2010-07-16 18:10:32',
        'canano_admin'),
       (28475414, 'separation column', 'Agilent', 'ZORBAX 5 microm 4.6 x 150 mm 883995-902', '2010-07-16 18:12:37',
        'canano_admin'),
       (28475415, 'separation column', 'Agilent', 'ZORBAX 5 um 4.6 mm x 150 mm 883995-902', '2010-07-16 18:15:16',
        'canano_admin'),
       (28475416, 'separation column', 'Waters', 'Spherisorb 5 um, 4.6 mm x 150 mm PSS839568', '2010-07-16 18:24:45',
        'canano_admin'),
       (28475417, 'ultrasonic pulser-receiver', 'Panametrics', '5900 6-mm diameter, 25-mm focal length',
        '2010-07-22 09:46:11', 'canano_admin'),
       (28475418, 'spectrofluorometer', 'Hitachi', 'F-4500 fluorescence spectrophotometer', '2010-07-23 11:16:44',
        'canano_admin'),
       (28475419, 'spectrofluorometer', 'Hitachi', 'F-4500', '2010-07-23 11:19:09', 'canano_admin'),
       (28475420, 'spectrophotometer', 'Hewlett-Packard', '8453', '2010-08-02 15:15:25', 'canano_admin'),
       (28475421, 'spectrophotometer', 'Ocean Optics', 'S2000', '2010-08-02 15:32:42', 'canano_admin'),
       (28475422, 'wavelength dispersive spectrometer', 'JEOL', '733', '2010-08-02 16:00:06', 'canano_admin'),
       (28475423, 'transmission electron microscope', 'JEOL', '2000FX', '2010-08-02 17:35:28', 'canano_admin'),
       (28475424, 'purification system', 'Amersham Pharmacia Biotech', 'AKTAprime', '2010-08-03 10:08:25',
        'canano_admin'),
       (28475425, 'spectrofluorometer', 'Ocean Optics', 'USB2000-FL', '2010-08-03 10:11:42', 'canano_admin'),
       (28475426, 'flow cytometer', 'BD Biosciences', 'LSR II', '2010-08-09 17:09:17', 'canano_admin'),
       (28475427, 'inverted research microscope', 'Nikon', 'T2000', '2010-08-10 12:50:31', 'canano_admin'),
       (28475428, 'fluorescence inverted microscope', 'Nikon', '', '2010-08-16 13:43:54', 'canano_admin'),
       (28475429, 'fluorescence inverted microscope', '', '', '2010-08-17 17:37:16', 'canano_admin'),
       (28475430, 'flow cytometer', '', '', '2010-08-17 17:42:40', 'canano_admin'),
       (28475431, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer Nano', '2010-08-17 18:00:36',
        'canano_admin'),
       (28475432, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano', '2010-08-17 18:03:51',
        'canano_admin'),
       (28475433, 'spectrofluorometer', '', '', '2010-08-17 18:14:52', 'canano_admin'),
       (28475434, 'thermal cycler', 'Biorad', 'iCycler', '2010-08-18 10:29:59', 'canano_admin'),
       (28475435, 'flow cytometer', 'Guava Technologies/Millipore', 'easyCyte', '2010-08-24 10:01:53', 'canano_admin'),
       (28475436, 'inductively coupled plasma mass spectrometer', 'Thermo Scientific', '', '2010-08-24 10:18:27',
        'canano_admin'),
       (28475437, 'spectrofluorometer', 'Jobin Yvon', 'Fluorolog-3 FL3-22', '2010-08-24 14:21:18', 'canano_admin'),
       (28475438, 'thermal cycler', '', '', '2010-08-25 14:56:36', 'canano_admin'),
       (28475439, 'spectrofluorometer', 'Photon Technology International', 'FluoDia T70', '2010-08-25 15:23:16',
        'canano_admin'),
       (28475440, 'spectrofluorometer', 'Molecular Devices', 'SpectraMax Gemini EM', '2010-08-30 16:57:17',
        'canano_admin'),
       (28475441, 'dynamic light scattering instrument', '', '', '2010-09-08 17:48:16', 'canano_admin'),
       (28475442, 'transmission electron microscope', '', '', '2010-09-15 17:54:19', 'canano_admin'),
       (28475443, 'laser light source', 'RPMC Lasers', '810-nm diode laser', '2010-09-16 09:51:02', 'canano_admin'),
       (28475444, 'infrared camera', 'FLIR', 'ThermaCAM S60', '2010-09-16 10:10:21', 'canano_admin'),
       (28475445, 'spectrophotometer', 'Molecular Devices', 'SpectraMax', '2010-09-16 10:26:21', 'canano_admin'),
       (28475446, 'laser light source', '', '', '2010-09-16 10:33:01', 'canano_admin'),
       (28475447, 'micro CT scanner', 'GE Healthcare', 'eXplore Locus', '2010-09-16 10:39:02', 'canano_admin'),
       (28475448, 'Raman spectrometer', 'Horiba', 'LabRAM HR 800', '2010-09-23 10:19:27', 'canano_admin'),
       (28475449, 'scanning electron microscope', '', '', '2010-10-18 18:08:32', 'canano_admin'),
       (28475450, 'electron spin resonance spectrometer', 'JEOL', 'ES-FE3XG', '2010-10-19 09:54:18', 'canano_admin'),
       (28475451, 'electron spin resonance spectrometer', 'JEOL', 'JES-FE3XG', '2010-10-19 09:55:39', 'canano_admin'),
       (28475452, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS90',
        '2010-10-19 14:16:42', 'canano_admin'),
       (28475453, 'spectrophotometer', 'Molecular Devices', 'SpectraMax Plus', '2010-10-19 14:32:01', 'canano_admin'),
       (28475454, 'spectrofluorometer', 'Molecular Devices', 'Gemini XPS', '2010-10-19 14:32:53', 'canano_admin'),
       (28475455, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer Nano ZS90', '2010-10-19 15:10:39',
        'canano_admin'),
       (28475456, 'electrophoretic light scattering instrument', 'Malvern', 'Malvern Zetasizer Nano ZS90',
        '2010-10-19 15:19:07', 'canano_admin'),
       (28475457, 'gas sorption detector', 'Micromeritics', 'ASAP 2020', '2010-10-25 17:35:29', 'canano_admin'),
       (28475458, 'spectrophotometer', 'Princeton Instruments', 'Acton', '2010-10-25 17:43:53', 'canano_admin'),
       (28475459, 'research microscope', 'Nikon', '', '2010-10-25 17:47:09', 'canano_admin'),
       (28475460, 'fourier transform infrared spectrophotometer', 'Thermo Scientific', 'Nicolet 6700',
        '2010-10-26 09:49:20', 'canano_admin'),
       (28475461, 'industrial microscope', 'Nikon', 'ECLIPSE LV150', '2010-10-26 10:08:15', 'canano_admin'),
       (28475462, '', 'Nikon', 'ECLIPSE LV150', '2010-10-26 10:10:05', 'canano_admin'),
       (28475463, 'cooled digital camera', 'Photometrics', 'CoolSNAP HQ', '2010-10-26 10:10:06', 'canano_admin'),
       (28475464, 'research microscope', 'Nikon', 'ECLIPSE LV150', '2010-10-26 10:11:26', 'canano_admin'),
       (28475465, 'inverted microscope', 'Nikon', '', '2010-10-26 10:16:17', 'canano_admin'),
       (28475466, 'research microscope with fluorescence source', '', '', '2010-10-26 10:36:44', 'canano_admin'),
       (28475467, 'multiphoton laser scanning microscope', 'Biorad', 'Radiance 2100/AGR-3Q', '2010-10-26 10:41:09',
        'canano_admin'),
       (28475468, 'multi photon confocal laser scanning microscope ', 'Biorad', 'Radiance 2100/AGR-3Q',
        '2010-10-26 15:57:41', 'canano_admin'),
       (28475469, 'spectrophotometer', 'Hewlett-Packard', '8452A', '2010-10-26 17:57:35', 'canano_admin'),
       (28475470, 'inductively coupled plasma-atomic emission spectrometer', 'PerkinElmer', 'Optima 3000 DV',
        '2010-10-27 13:55:32', 'canano_admin'),
       (28475471, 'imaging system', 'Caliper Life Sciences', 'IVIS 200', '2010-10-27 14:10:19', 'canano_admin'),
       (28475472, 'inverted microscope', '', '', '2010-10-28 15:17:13', 'canano_admin'),
       (28475473, 'scanning electron microscope', 'JEOL', '6320FV', '2010-11-02 15:13:08', 'canano_admin'),
       (28475474, 'HPLC system', 'Agilent', '1100', '2010-11-03 09:58:09', 'canano_admin'),
       (28475475, 'separation column', 'Phenomenex', 'Curosil-PFP, 250 x 4.6 mm, 5 um', '2010-11-03 10:04:17',
        'canano_admin'),
       (28475476, 'scintillation counter', 'Packard', 'Tri-Carb Scintillation Analyzer', '2010-11-08 10:45:31',
        'canano_admin'),
       (28475477, 'deconvolution fluorescence microscope', 'Applied Precision', 'Delta Vision RT',
        '2010-11-08 13:11:42', 'canano_admin'),
       (28475478, 'dynamic light scattering instrument', 'Brookhaven Instruments', 'ZetaPALS', '2010-11-09 15:39:49',
        'canano_admin'),
       (28475479, 'transmission electron microscope', 'JEOL', 'JEM-200CX', '2010-11-10 17:35:41', 'canano_admin'),
       (28475480, 'spectrophotometer', 'Molecular Devices', 'SpectraMax Plus384', '2010-11-11 12:00:42',
        'canano_admin'),
       (28475481, 'separation column', 'Phenomenex', 'Curosil-PFP 250 x 4.6 mm, 5 um', '2010-11-11 13:00:39',
        'canano_admin'),
       (28475482, 'separation column', 'Phenomenex', 'Curosil-PFP 250 x 4.6 mm 5 um', '2010-11-11 13:32:29',
        'canano_admin'),
       (28475483, 'spectrophotometer', 'Varian', 'Cary 5000', '2010-11-16 17:20:15', 'canano_admin'),
       (28475484, 'spectrophotometer', 'Varian', 'Cary 500', '2010-11-16 17:20:14', 'canano_admin'),
       (28475485, 'nuclear magnetic resonance spectrometer', 'Bruker', '400-MHz 1H NMR', '2010-11-17 14:52:27',
        'canano_admin'),
       (28475486, 'scintillation counter', 'PerkinElmer', 'Tri-Carb Scintillation Analyzer', '2010-11-19 09:55:37',
        'canano_admin'),
       (29032448, 'spectrofluorometer', 'Shimadzu', 'RF-PC100', '2010-11-22 10:55:45', 'canano_admin'),
       (29032449, 'transmission electron microscope', 'JEOL', 'JEM 100X', '2010-11-23 12:04:24', 'canano_admin'),
       (29032450, 'inverted research microscope', 'Nikon', 'Eclipse TE2000-U', '2010-11-24 11:14:22', 'canano_admin'),
       (29491200, 'spectrophotometer', 'BioTek', '', '2010-11-30 15:11:26', 'canano_admin'),
       (29491201, 'brightfield microscope', '', '', '2010-11-30 17:03:49', 'canano_admin'),
       (29491202, 'electrophoretic light scattering instrument', '', '', '2010-12-08 18:07:05', 'canano_admin'),
       (29491203, 'X-ray photoelectron spectrometer', '', '', '2010-12-08 18:18:15', 'canano_admin'),
       (29491204, 'dynamic light scattering instrument', 'Brookhaven Instruments', 'ZetaPlus', '2010-12-15 10:54:45',
        'canano_admin'),
       (29491205, 'gas sorption detector', 'Micromeritics', 'ASAP 2000', '2010-12-21 15:51:24', 'canano_admin'),
       (29491206, 'digital camera', 'Gatan', '', '2010-12-21 16:19:33', 'canano_admin'),
       (29491207, 'transmission electron microscope', 'JEOL', 'JEM-3010', '2010-12-21 16:19:32', 'canano_admin'),
       (29491208, 'dynamic light scattering instrument', 'PSS', 'Nicomp 380', '2010-12-21 16:23:45', 'canano_admin'),
       (29491209, 'elemental analysis instrument', 'Fisons Instruments/Thermo Scientific', 'NA 1500 NCS',
        '2010-12-21 16:54:23', 'canano_admin'),
       (29491210, 'inductively coupled plasma mass spectrometer', 'PerkinElmer', 'Elan 6100', '2010-12-21 16:57:45',
        'canano_admin'),
       (29491211, 'inductively coupled plasma mass spectrometer', 'PerkinElmer', 'Elan 6100', '2010-12-21 16:57:44',
        'canano_admin'),
       (29491212, 'spectrofluorometer', 'PerkinElmer', 'Victor 2 Model 1420', '2010-12-21 17:35:22', 'canano_admin'),
       (29491213, 'fluorescence microscope', 'ChemoMetec', 'NucleoCounter', '2010-12-22 11:52:52', 'canano_admin'),
       (29491214, 'flow cytometer', 'BD Biosciences', 'FACSCanto', '2010-12-22 12:04:06', 'canano_admin'),
       (29491215, 'fluorescence microscope', 'Olympus', '', '2010-12-22 12:38:59', 'canano_admin'),
       (29491216, 'dynamic light scattering instrument', 'Photal Otsuka', 'DSL-7000', '2011-01-10 13:44:14',
        'canano_admin'),
       (29491217, 'transmission electron microscope', 'Zeiss', '', '2011-01-10 13:52:51', 'canano_admin'),
       (29491218, 'X-ray diffraction spectrometer', 'JEOL', 'JSX-3201', '2011-01-10 14:02:39', 'canano_admin'),
       (29491219, 'spectrophotometer', 'Thermo Scientific', '', '2011-01-10 14:33:57', 'canano_admin'),
       (29491220, 'bioanalyzer', 'Agilent', '2100', '2011-01-10 15:26:39', 'canano_admin'),
       (29491221, 'spectrophotometer', 'Thermo Scientific', 'NanoDrop 1000', '2011-01-10 15:28:18', 'canano_admin'),
       (29491222, 'microarray scanner', 'Agilent', 'Microarray Scanner', '2011-01-10 16:48:10', 'canano_admin'),
       (29491223, 'spectrophotometer', 'Agilent', 'Cary 6000i', '2011-01-14 16:43:32', 'canano_admin'),
       (29491224, 'atomic force microscope', '', '', '2011-01-14 18:07:15', 'canano_admin'),
       (30375936, 'dynamic light scattering instrument', 'Wyatt Technologies', 'DynaPro Titan', '2011-02-18 03:10:30',
        'canano_admin'),
       (30769152, 'flow cytometer', 'BD Biosciences', 'BD LSR-II (BD) ', '2011-02-28 05:39:12', 'canano_admin'),
       (31653888, '', 'PerkinElmer', '', '2011-10-17 16:52:43', 'canano_guest'),
       (31653889, 'dynamic light scattering instrument', 'Beckman/Coulter', '', '2011-10-17 16:52:42', 'canano_guest'),
       (31653890, 'atomic force microscope', 'Wyatt Technologies', '', '2011-10-18 08:33:36', 'canano_guest'),
       (31653891, '', 'Test', '', '2011-10-18 12:24:40', 'canano_guest'),
       (32047104, '', 'Applied Biosystems', '', '2011-12-09 11:35:51', 'canano_admin'),
       (32309248, 'electron microprobe', 'BioLogics', '', '2012-02-29 16:26:22', 'canano_admin'),
       (32899072, 'VSM magnetometer', 'Lakeshore', 'VSM 7300 ', '2012-06-13 15:12:42', 'canano_admin'),
       (32899073, 'transmission electron microscope', 'FEI', 'Tecnai F20', '2012-06-13 15:56:11', 'canano_admin'),
       (32899074, 'X-ray diffraction spectrometer', 'Rigaku', 'D/Max', '2012-06-14 16:09:24', 'canano_admin'),
       (32899075, 'fourier transform infrared spectrophotometer', 'Thermo Scientific', 'Nicolet Avatar FTIR 330',
        '2012-06-14 16:20:18', 'canano_admin'),
       (32899076, 'fiber temperature sensor', 'Luxtron', '', '2012-06-15 12:29:21', 'canano_admin'),
       (33488896, 'digital camera', 'Nikon', 'DQC-FS', '2012-06-28 17:05:44', 'canano_admin'),
       (33947648, 'thermogravimetric analyzer', 'TA Instruments', 'QA5000', '2012-07-10 12:29:33', 'canano_admin'),
       (33947649, 'atomic force microscope', 'Asylum Research', 'MFP-3D', '2012-07-10 12:49:05', 'canano_admin'),
       (33947650, 'AFM probe', 'Budget Sensors', 'TAP300Al-G', '2012-07-10 12:53:28', 'canano_admin'),
       (33947651, 'spectrophotometer', 'Molecular Devices', 'SpectraMax M5', '2012-07-10 13:03:57', 'canano_admin'),
       (33947652, 'inverted research microscope', 'Olympus', 'IX81', '2012-07-12 12:31:18', 'canano_admin'),
       (34471936, 'gas sorption detector', 'Micromeritics', 'ASAP 202', '2012-07-26 15:27:01', 'canano_admin'),
       (34471937, 'transmission electron microscope', 'JEOL', '2010', '2012-07-26 15:36:37', 'canano_admin'),
       (34471938, 'scanning electron microscope', 'Hitachi', 'S-5200', '2012-07-26 15:40:14', 'canano_admin'),
       (35323904, 'confocal laser scanning microscope', 'Carl Zeiss', 'LSM 510 META', '2012-08-27 16:48:28',
        'canano_admin'),
       (35323905, 'flow cytometer', 'Dako', 'MoFlo', '2012-08-28 12:51:56', 'canano_admin'),
       (35323906, 'flow cytometer', 'Becton Dickinson', '', '2012-08-28 13:13:55', 'canano_admin'),
       (35880960, 'laser diffraction instrument', 'Horiba', 'LS950', '2012-09-25 13:13:15', 'canano_admin'),
       (35880961, 'surface plasmon resonance instrument', 'Biacore', 'T100', '2012-09-25 13:20:57', 'canano_admin'),
       (36208640, 'inductively coupled plasma-atomic emission spectrometer', 'Varian', 'Vista-PRO',
        '2012-10-15 14:10:42', 'canano_admin'),
       (36208641, 'laser diffraction instrument', 'Horiba', 'LA-950', '2012-10-29 13:57:12', 'canano_admin'),
       (36208642, 'liquid scintillation analyzer', 'PerkinElmer', 'Tri-Carb 2900', '2012-10-30 17:11:42',
        'canano_admin'),
       (36995072, 'spectrophotometer', 'Agilent', 'Varian Cary 50', '2012-11-27 14:26:31', 'canano_admin'),
       (36995073, 'quantum yield measurement system', 'Hamamatsu', 'C9920-02', '2012-11-27 14:48:11', 'canano_admin'),
       (36995074, 'Raman spectrometer', 'Renishaw', 'InVia Raman microscope', '2012-12-14 14:19:49', 'canano_admin'),
       (36995075, 'transmission electron microscope', 'FEI', 'Tecnai G2 X-Twin', '2013-01-24 14:43:35', 'canano_admin'),
       (36995076, 'spectrophotometer', 'BioTek', 'Synergy 4', '2013-01-24 15:07:07', 'canano_admin'),
       (36995077, 'photoacoustic spectrometer', 'Visualsonics', 'LAZR', '2013-01-24 15:24:09', 'canano_admin'),
       (36995078, 'photoacoustic imaging instrument', 'Endra', 'Nexus 128', '2013-01-25 14:00:19', 'canano_admin'),
       (36995079, 'inductively coupled plasma mass spectrometer', 'Thermo Scientific',
        'IRIS Advantage/1000 Radial ICAP  ', '2013-01-29 13:10:11', 'canano_admin'),
       (36995080, 'dynamic light scattering instrument', 'BD Biosciences', '', '2013-02-22 09:18:00', 'canano_admin'),
       (37519360, 'custom built small animal MRI scanner', '', '', '2013-02-27 12:37:24', 'canano_admin'),
       (37519361, 'photoacoustic imaging instrument', '', '', '2013-02-27 12:58:50', 'canano_admin'),
       (37519362, 'research microscope', 'Leica', 'DM2000', '2013-02-28 12:05:51', 'canano_admin'),
       (37519363, 'scanning electron microscope', 'FEI', 'Magellan XHR', '2013-02-28 12:08:35', 'canano_admin'),
       (37519364, 'confocal laser scanning microscope', 'Leica', 'TCS SP2 AOBS', '2013-02-28 12:10:34', 'canano_admin'),
       (37519365, 'scanning transmission electron microscope', 'FEI', 'Tecnai G2 X-Twin', '2013-02-28 12:11:54',
        'canano_admin'),
       (37519366, 'spectrophotometer', 'Beckman/Coulter', 'DU-640', '2013-03-11 13:27:42', 'canano_admin'),
       (37519367, 'inductively coupled plasma-atomic emission spectrometer', 'Thermo Scientific',
        'IRIS Advantage/1000 Radial ICAP  ', '2013-03-12 13:18:40', 'canano_admin'),
       (37519368, 'scanning transmission electron microscope', 'FEI', 'Tecnai F20', '2013-03-27 14:36:27',
        'canano_admin'),
       (37519369, 'qRT-PCR instrument', 'Eppendorf', 'Mastercycler ep realplex', '2013-03-28 10:26:34', 'canano_admin'),
       (37519370, 'fluorometer', 'Invitrogen', 'Qubit', '2013-04-04 11:20:52', 'canano_admin'),
       (37519371, 'positron emission tomograph', 'CTI Concorde Microsystems', 'microPET R4', '2013-04-16 12:20:04',
        'canano_admin'),
       (37519372, 'gamma counter', 'Packard', 'Cobra II', '2013-04-16 12:26:40', 'canano_admin'),
       (37847040, 'confocal laser scanning microscope', 'Biorad', 'MRC 600', '2013-04-24 11:45:55', 'canano_admin'),
       (37847041, 'SQUID sample magnetometer', 'Quantum Design', 'MPMS2', '2013-04-24 13:48:35', 'canano_admin'),
       (37847042, 'SQUID sample magnetometer', 'Quantum Design', '', '2013-04-25 12:51:57', 'canano_admin'),
       (37847043, 'fluorescence imaging system', 'Siemens Medical', 'bonSai', '2013-04-25 14:10:59', 'canano_admin'),
       (37847044, 'spectrophotometer', 'Beckman/Coulter', '', '2013-05-17 12:05:36', 'canano_admin'),
       (38699008, 'transmission electron microscope', 'FEI', 'BioTWIN', '2013-05-20 13:19:44', 'canano_admin'),
       (38699009, '', 'Beckman/Coulter', '', '2013-05-20 17:08:10', 'canano_admin'),
       (38699010, '', 'LaVision BioTec', '', '2013-05-24 12:26:36', 'canano_admin'),
       (38699011, 'microscope', 'Olympus', 'BX61WI', '2013-05-24 12:46:22', 'canano_admin'),
       (38699012, 'nuclear magnetic resonance spectrometer', 'Bruker', '', '2013-05-27 13:12:17', 'canano_admin'),
       (39256064, 'electrophoretic light scattering instrument', 'Photal Otsuka', 'ELS-8000', '2013-07-25 12:03:44',
        'canano_admin'),
       (39256065, 'nuclear magnetic resonance spectrometer', '', '', '2013-07-25 12:08:53', 'canano_admin'),
       (39256066, 'thermogravimetric analyzer', 'TA Instruments', 'QA50', '2013-07-25 12:16:25', 'canano_admin'),
       (39256067, 'spectrophotometer', 'Thermo Scientific', 'Varioskan Flash', '2013-07-25 15:06:55', 'canano_admin'),
       (39256068, 'dynamic light scattering instrument', 'Photal Otsuka', 'ELS-8000', '2013-07-26 14:28:59',
        'canano_admin'),
       (39256069, 'fourier transform infrared spectrophotometer', '', '', '2013-07-29 12:05:27', 'canano_admin'),
       (39256070, 'imaging system', 'Kodak', 'Image Station 4000MM ', '2013-07-29 13:49:21', 'canano_admin'),
       (39878656, 'flow cytometer', 'BD Biosciences', 'FACSCanto II', '2013-09-03 14:42:51', 'canano_admin'),
       (39878657, 'inverted microscope', 'Nikon', 'TE2000', '2013-09-04 10:15:08', 'canano_admin'),
       (39878658, 'fluorescence inverted microscope', 'Nikon', 'TE2000', '2013-09-04 10:15:42', 'canano_admin'),
       (39878659, 'flow cytometer', 'Becton Dickinson', 'FACSCanto II', '2013-09-04 11:05:37', 'canano_admin'),
       (39878660, 'fluorescence inverted microscope', 'Nikon', 'TE2000-U', '2013-09-04 11:30:27', 'canano_admin'),
       (39878661, 'research microscope', 'Leica', 'DM5500', '2013-09-04 11:34:02', 'canano_admin'),
       (39878662, 'research upright microscope', 'Leica', 'DM5500', '2013-09-04 11:34:59', 'canano_admin'),
       (39878663, 'OCT microscope', 'Michelson Diagnostics', 'EX1301', '2013-09-05 12:30:33', 'canano_admin'),
       (39878664, 'flow cytometer', 'BD Biosciences', 'FACSAria II', '2013-09-05 12:50:11', 'canano_admin'),
       (39878665, 'gamma counter', 'PerkinElmer', 'Wallac Wizard 1480', '2013-11-07 12:27:29', 'canano_admin'),
       (39878666, 'hemocytometer', 'Abbott', 'Cell-Dyn 3500', '2013-11-07 12:30:19', 'canano_admin'),
       (39878667, 'fourier transform infrared spectrophotometer', 'Thermo Scientific', 'Nicolet 380',
        '2013-11-11 14:23:12', 'canano_admin'),
       (39878668, 'nuclear magnetic resonance spectrometer', 'Bruker', 'AVANCE 400 FT', '2013-11-11 14:51:34',
        'canano_admin'),
       (39878669, 'nuclear magnetic resonance instrument', 'Bruker', 'AVANCE 400 FT', '2013-11-12 13:21:03',
        'canano_admin'),
       (39878670, 'transmission electron microscope', 'JEOL', 'JEM-100CX', '2013-11-13 10:05:03', 'canano_admin'),
       (39878671, 'scanning electron microscope', 'JEOL', 'JSM-7600', '2013-11-13 14:53:34', 'canano_admin'),
       (40239104, 'photoacoustic spectrometer', 'Endra', 'Nexus 128', '2013-11-25 11:10:30', 'canano_admin'),
       (40239105, 'Raman spectrometer', 'Renishaw', 'inVia', '2013-11-27 14:48:34', 'canano_admin'),
       (40239106, 'transmission electron microscope', 'Hitachi', 'H-7500', '2013-12-06 13:19:29', 'canano_admin'),
       (40239107, 'research microscope', '', '', '2013-12-06 13:42:53', 'canano_admin'),
       (40239108, 'spectrophotometer', 'Molecular Devices', 'SpectraMax Plus 384', '2013-12-06 14:57:58',
        'canano_admin'),
       (40239109, 'MRI scanner', 'Siemens Medical', 'Magneton Trio, a Tim System ', '2013-12-09 12:57:45',
        'canano_admin'),
       (40239110, 'HPLC system', 'Beckman/Coulter', '', '2013-12-11 12:46:38', 'canano_admin'),
       (41680896, 'inductively coupled plasma mass spectrometer', 'Thermo Scientific', 'XSERIES 2',
        '2014-01-17 12:36:46', 'canano_admin'),
       (41680897, 'transmission electron microscope', 'Hitachi', 'HF-2000', '2014-01-20 13:57:53', 'canano_admin'),
       (41680898, 'inductively coupled plasma optical emission spectrometer', 'Varian', 'Vista MPX',
        '2014-01-21 13:18:51', 'canano_admin'),
       (41680899, 'inductively coupled plasma mass spectrometer', 'Agilent', '7500CX', '2014-01-22 13:56:00',
        'canano_admin'),
       (41680900, 'inverted microscope', 'Olympus', 'IX50', '2014-01-27 12:27:56', 'canano_admin'),
       (41680901, 'solvent delivery module', 'Beckman/Coulter', 'System Gold 126 Solvent Module', '2014-01-27 13:35:42',
        'canano_admin'),
       (41680902, 'detector', 'Beckman/Coulter', 'System Gold 168 Detector', '2014-01-27 13:35:43', 'canano_admin'),
       (41680903, 'spectrophotometer', 'Biorad', 'SmartSpec 3000', '2014-01-29 11:07:51', 'canano_admin'),
       (41680904, 'small animal MRI scanner', 'Oxford Magnet Technology', '4.7 T small animal MRI scanner',
        '2014-01-29 12:07:01', 'canano_admin'),
       (41680905, 'console', 'Varian', 'UNITY INOVA', '2014-01-29 12:12:34', 'canano_admin'),
       (41680906, 'liquid chromatograph mass spectrometer', 'Shimadzu', 'LCMS-2020', '2014-02-26 12:33:55',
        'canano_admin'),
       (42369024, 'isocratic pump', 'Agilent', 'G1310A', '2014-03-28 13:40:50', 'canano_admin'),
       (42369025, 'refractive index detector', 'Wyatt Technologies', 'Optilab rEX', '2014-03-28 13:40:55',
        'canano_admin'),
       (42369026, 'separation column', 'TosoHaas', 'G4000PW 05763', '2014-03-28 13:40:53', 'canano_admin'),
       (42369027, 'detector', 'Wyatt Technologies', 'DAWN EOS', '2014-03-28 13:40:54', 'canano_admin'),
       (42369028, 'autosampler', 'Agilent', 'G1329A', '2014-03-28 13:40:51', 'canano_admin'),
       (42369029, 'guard column', 'TosoHaas', '06762', '2014-03-28 13:40:52', 'canano_admin'),
       (42369030, 'separation column', 'TosoHaas', 'TSKgel G4000PW 05763', '2014-03-28 13:43:08', 'canano_admin'),
       (42369031, 'guard column', 'TosoHaas', 'TSKgel Guard PW 06762', '2014-03-28 13:43:07', 'canano_admin'),
       (42369032, 'control module', 'Wyatt Technologies', 'Eclipse 2', '2014-03-28 15:08:28', 'canano_admin'),
       (42369033, 'multi-angle light scattering detector', 'Wyatt Technologies', 'DAWN EOS', '2014-03-28 15:08:29',
        'canano_admin'),
       (42467328, 'degasser', 'Agilent', 'G1379A', '2014-03-31 10:10:12', 'canano_admin'),
       (42467329, 'separation column', 'Agilent', 'Zorbax 300SB-C8', '2014-03-31 10:10:15', 'canano_admin'),
       (42467330, 'detector', 'Agilent', 'G1315B', '2014-03-31 10:10:16', 'canano_admin'),
       (42467331, 'capillary pump', 'Agilent', 'G1376A', '2014-03-31 10:10:13', 'canano_admin'),
       (42467332, 'HPLC autosampler', 'Agilent', 'G1377A', '2014-03-31 10:10:14', 'canano_admin'),
       (42467333, 'solvent delivery module', 'Shimadzu', 'LC-20AT', '2014-03-31 10:49:04', 'canano_admin'),
       (42467334, '[other]', 'Shimadzu', 'C-R3A', '2014-03-31 10:49:07', 'canano_admin'),
       (42467335, 'separation column', 'Agilent', 'Zorbax C-18', '2014-03-31 10:49:08', 'canano_admin'),
       (42467336, 'detector', 'Shimadzu', 'SPD-20A', '2014-03-31 10:49:05', 'canano_admin'),
       (42467337, 'HPLC autosampler', 'Shimadzu', 'SIL-20AC', '2014-03-31 10:49:06', 'canano_admin'),
       (42467338, 'integrator', 'Shimadzu', 'C-R3A', '2014-03-31 10:54:15', 'canano_admin'),
       (42467339, 'gamma counter', '', '', '2014-04-03 09:48:09', 'canano_admin'),
       (42467340, '', 'Xenogen', '', '2014-04-07 12:14:02', 'canano_admin'),
       (42467341, '', 'Xenogen', '', '2014-04-07 12:14:03', 'canano_admin'),
       (43384832, 'fourier transform infrared spectrophotometer', 'PerkinElmer', '100', '2014-05-07 12:53:06',
        'canano_admin'),
       (43384833, 'scanning electron microscope', 'FEI', 'Inspect', '2014-05-07 13:33:42', 'canano_admin'),
       (43384834, 'transmission electron microscope', 'FEI', 'CM20', '2014-05-08 10:54:03', 'canano_admin'),
       (43384835, 'powder diffractometer', 'Siemens', 'D500', '2014-05-08 11:00:39', 'canano_admin'),
       (43384836, 'VSM magnetometer', 'Princeton Applied Research', '155', '2014-05-08 11:14:46', 'canano_admin'),
       (43384837, 'magnetometer', 'FW Bell', '640', '2014-05-08 11:14:47', 'canano_admin'),
       (43384838, 'magnet power supply', 'Danfysik', 'SYSTEM 8000', '2014-05-08 11:16:50', 'canano_admin'),
       (43384839, 'induction heating system', 'Ameritherm', 'Easyheat 224', '2014-05-08 11:27:31', 'canano_admin'),
       (43384840, 'spectrophotometer', 'Jasco', 'V-650', '2014-05-08 12:17:01', 'canano_admin'),
       (43384841, 'fiber temperature sensor', '', 'RF-immune fiber optic probe', '2014-05-08 13:04:10', 'canano_admin'),
       (43384842, 'scintillation counter', 'Packard', 'Minaxi 5500', '2014-05-08 14:07:45', 'canano_admin'),
       (43384843, 'gamma camera', '', 'small animal gamma camera', '2014-05-08 14:18:06', 'canano_admin'),
       (43384844, 'spectrophotometer', 'SAEC Radim', 'Sirio S', '2014-05-09 10:36:21', 'canano_admin'),
       (43384845, 'fourier transform infrared spectrophotometer', 'PerkinElmer', 'Spectrum 100', '2014-05-12 11:24:17',
        'canano_admin'),
       (43384846, '', 'ChemoMetec', '', '2014-05-12 16:39:43', 'canano_admin'),
       (43646976, 'confocal microscope', 'Zeiss', 'LSM 510', '2014-05-23 15:38:35', 'canano_admin'),
       (43646977, 'qRT-PCR instrument', 'Applied Biosystems', '7500 Fast Real-Time PCR', '2014-06-10 13:10:04',
        'canano_admin'),
       (43646978, 'staining system', 'Dako', 'Autostainer Plus', '2014-06-11 13:39:38', 'canano_admin'),
       (43646979, 'staining system', 'Leica', 'BOND-MAX', '2014-06-11 13:39:39', 'canano_admin'),
       (43646980, 'inductively coupled plasma mass spectrometer', 'Thermo Fisher', 'XSERIES 2', '2014-06-12 13:01:27',
        'canano_admin'),
       (43646981, 'preclinical imaging system', 'PerkinElmer', 'IVIS Spectrum', '2014-06-20 16:09:31', 'canano_admin'),
       (44564480, 'small animal MRI scanner', 'Bruker', 'Biospec 94', '2014-06-23 13:10:41', 'canano_admin'),
       (44564481, 'laser ablation system', 'New Wave Research', 'UP213', '2014-06-23 13:50:20', 'canano_admin'),
       (44793856, 'cytometer', 'Invitrogen', 'Countess', '2014-07-22 12:15:56', 'canano_admin'),
       (44793857, 'double gel permeation column', 'Polymer Laboratories', 'PL-aquagel-OH-40', '2014-08-12 11:02:22',
        'canano_admin'),
       (44793858, 'HPLC system', 'Agilent', 'HP 1100', '2014-08-12 11:03:45', 'canano_admin'),
       (44793859, 'C-18 reverse phase column', 'Richard Scientific', 'HIRPB-4438', '2014-08-12 12:13:17',
        'canano_admin'),
       (44793860, 'fluorescence detector', 'Groton Technology', 'FD-500', '2014-08-12 12:27:52', 'canano_admin'),
       (44793861, 'positron emission tomograph', 'Siemens', 'Focus 220', '2014-08-12 15:08:20', 'canano_admin'),
       (44793862, 'small animal CT scanner', 'Siemens', 'MicroCat II', '2014-08-12 15:14:19', 'canano_admin'),
       (44793863, 'inductively coupled plasma mass spectrometer', '', '', '2014-08-14 12:47:17', 'canano_admin'),
       (45645824, 'magnetic separation instrument', 'Invitrogen', 'DynaMag-Spin', '2014-09-01 14:19:22',
        'canano_admin'),
       (45645825, 'spectrophotometer', 'Shimadzu', 'UV-2550', '2014-09-02 11:25:10', 'canano_admin'),
       (45645826, 'transmission electron microscope', 'FEI', 'Tecnai G2 20 TWIN', '2014-09-03 10:09:41',
        'canano_admin'),
       (45645827, 'VSM magnetometer', 'Lakeshore', '7410', '2014-09-03 10:41:56', 'canano_admin'),
       (45645828, 'inverted microscope', 'Nikon', 'Eclipse Ti-U', '2014-09-03 12:37:58', 'canano_admin'),
       (45645829, 'spectrofluorometer', 'Horiba', 'FluoroLog-3', '2014-09-03 15:14:20', 'canano_admin'),
       (45645830, 'inverted research microscope', 'Nikon', 'Eclipse Ti-U', '2014-09-04 12:42:57', 'canano_admin'),
       (45645831, 'imaging system', 'Andor', 'Revolution XD', '2014-09-05 10:50:45', 'canano_admin'),
       (45645832, 'confocal scanner unit', 'Yokogawa', 'CSU22', '2014-09-05 10:53:05', 'canano_admin'),
       (45645833, 'EMCCD camera', 'Andor', 'iXon DV885K', '2014-09-05 11:03:24', 'canano_admin'),
       (45645834, 'research microscope', 'Nikon', 'Eclipse Ti-U', '2014-09-05 11:39:08', 'canano_admin'),
       (45645835, 'digital camera', 'Nikon', 'DS-Ri1', '2014-09-05 11:41:02', 'canano_admin'),
       (45645836, 'thermal cycler', 'Biorad', '', '2014-09-05 11:51:07', 'canano_admin'),
       (45645837, 'thermal cycler', 'Applied Biosystems', 'Gene Amp PCR system 9700', '2014-10-02 13:45:12',
        'canano_admin'),
       (45645838, 'confocal laser scanning microscope', 'Zeiss', 'LSM 510-NLO', '2014-10-03 14:35:41', 'canano_admin'),
       (45645839, 'small animal MRI scanner', 'Bruker', 'Biospec 94/30', '2014-10-06 11:32:09', 'canano_admin'),
       (46202880, 'degasser', 'Agilent', '', '2014-10-20 14:51:31', 'canano_admin'),
       (46202881, 'double gel permeation column', 'Polymer Laboratories', 'PL aquagel - OH 40 8 um 300 x 7.5 mm',
        '2014-10-20 14:51:34', 'canano_admin'),
       (46202882, 'pump', 'Agilent', '', '2014-10-20 14:51:32', 'canano_admin'),
       (46202883, 'autosampler', 'Agilent', '', '2014-10-20 14:51:33', 'canano_admin'),
       (46202884, 'nuclear magnetic resonance spectrometer', 'Varian', 'Inova 500', '2014-10-21 10:42:40',
        'canano_admin'),
       (46202885, 'nuclear magnetic resonance spectrometer', 'Varian', 'Inova 600', '2014-10-21 10:43:57',
        'canano_admin'),
       (46202886, 'spectrophotometer', 'Tecan', 'Infinite 200', '2014-10-21 10:50:03', 'canano_admin'),
       (46202887, 'spectrofluorometer', 'Tecan', 'Infinite 200', '2014-10-21 11:19:27', 'canano_admin'),
       (46202888, 'HPLC system', 'Agilent', '', '2014-10-22 12:43:34', 'canano_admin'),
       (46202889, 'fluorescence detector', '', '', '2014-10-22 12:43:35', 'canano_admin'),
       (46202890, 'reverse phase column', '', '', '2014-10-22 12:43:36', 'canano_admin'),
       (46202891, 'reverse phase column', 'Phenomenex', 'Synergi 4 um Hydro-RP 80A', '2014-10-22 12:45:26',
        'canano_admin'),
       (46202892, 'guard column', '', '', '2014-10-22 12:46:49', 'canano_admin'),
       (46202893, 'HPLC system', '', '', '2014-10-23 11:32:09', 'canano_admin'),
       (46202894, 'transmission electron microscope', 'FEI', 'Tecnai T12', '2014-10-24 14:15:12', 'canano_admin'),
       (46202895, 'confocal laser scanning microscope', 'Zeiss', 'LSM 510 META', '2014-10-29 15:09:26', 'canano_admin'),
       (47022080, 'capillary electrophoresis instrument', 'Ameritherm', 'test', '2014-12-16 14:43:59', 'canano_admin'),
       (47644672, 'preclinical imaging system', 'Caliper Life Sciences', 'Xenogen IVIS 200', '2015-01-13 14:38:28',
        'canano_admin'),
       (47644673, 'digital camera', 'Leica', 'DFC 320', '2015-01-13 14:49:13', 'canano_admin'),
       (47644674, 'separation column', 'Dikma Technologies', 'Diamonsil C18', '2015-01-14 13:48:47', 'canano_admin'),
       (47644675, 'dual absorbance detector', 'Shimadzu', 'SPD-20A', '2015-01-14 13:48:46', 'canano_admin'),
       (47644676, 'flow cytometer', 'BD Biosciences', 'Accuri C6', '2015-01-16 11:56:48', 'canano_admin'),
       (47644677, 'reverse phase column', 'Dikma Technologies', 'Diamonsil C18', '2015-01-16 12:16:50', 'canano_admin'),
       (47644678, 'high content analysis reader', 'Thermo Scientific',
        'ArrayScan XTI High Content Analysis (HCA) Reader', '2015-01-16 14:46:28', 'canano_admin'),
       (47644679, 'guard column', 'Agilent', '', '2015-01-28 11:31:47', 'canano_admin'),
       (47644680, 'reverse phase column', 'Phenomenex', 'Synergi 4 um Hydro-RP 80 A', '2015-01-28 11:31:46',
        'canano_admin'),
       (47644681, 'fluorescence detector', 'Agilent', '', '2015-01-28 11:31:45', 'canano_admin'),
       (47644682, '', 'Phenomenex', 'Synergi 4 um Hydro-RP 80 A', '2015-01-28 12:15:12', 'canano_admin'),
       (47644683, 'digital camera', 'QImaging', '', '2015-01-28 13:30:50', 'canano_admin'),
       (47644684, 'thermal cycler', 'Applied Biosystems', '', '2015-01-29 11:37:37', 'canano_admin'),
       (47644685, 'bioanalyzer', 'Luminex', 'Flexmap 3D', '2015-01-29 13:00:24', 'canano_admin'),
       (47644686, 'bioanalyzer', 'Luminex', 'Luminex 100', '2015-01-29 13:00:23', 'canano_admin'),
       (47939584, 'genotyping system', 'Affymetrix', 'GeneChip Scanner 3000 7G', '2015-01-30 10:59:52', 'canano_admin'),
       (48300032, 'spectrophotometer', 'Shimadzu', 'SPD-20A', '2015-04-10 13:00:09', 'canano_admin'),
       (48300033, 'spectrophotometer', 'Shimadzu', 'SPD-2A', '2015-04-10 14:17:27', 'canano_admin'),
       (48300034, 'spectrophotometer', 'Thermo Fisher', 'Varioskan Flash Multimode Reader', '2015-04-10 14:42:54',
        'canano_admin'),
       (48300035, 'inverted microscope', 'Zeiss', 'Axio Vert.A1', '2015-04-10 14:54:00', 'canano_admin'),
       (48300036, 'spectrofluorometer', 'Thermo Fisher', 'Varioskan Flash Multimode Reader Type 3001',
        '2015-04-13 11:11:16', 'canano_admin'),
       (48300037, 'research microscope', 'Olympus', 'BX51', '2015-04-13 11:46:48', 'canano_admin'),
       (48300038, 'spectrofluorometer', 'Thermo Fisher', 'Varioskan Flash Multimode Reader', '2015-04-13 11:59:13',
        'canano_admin'),
       (48300039, 'confocal laser scanning microscope', 'Zeiss', 'LSM-510', '2015-04-13 15:04:08', 'canano_admin'),
       (48300040, 'nanoparticle tracking analyzer', 'Nanosight', 'LM10', '2015-04-15 13:52:06', 'canano_admin'),
       (48300041, 'confocal laser scanning microscope', 'Zeiss', 'LSM 510', '2015-04-16 14:29:39', 'canano_admin'),
       (49250304, 'thermogravimetric analyzer', 'TA Instruments', 'Q50', '2015-07-02 15:17:52', 'canano_admin'),
       (49250305, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano XS', '2015-07-03 14:25:15',
        'canano_admin'),
       (49250306, 'reverse phase column', 'Phenomenex', 'Luna C18 250 x 2.8 mm 5 um', '2015-07-03 14:37:10',
        'canano_admin'),
       (49250307, 'detector', 'Beckman/Coulter', '128', '2015-07-03 14:37:09', 'canano_admin'),
       (49250308, 'solvent delivery module', 'Beckman/Coulter', '126P', '2015-07-03 14:37:08', 'canano_admin'),
       (49250309, 'HPLC system', 'Beckman/Coulter', 'System Gold', '2015-07-03 14:37:07', 'canano_admin'),
       (49250310, 'spectrophotometer', 'PerkinElmer', 'Victor 3V', '2015-07-06 13:54:42', 'canano_admin'),
       (49250311, 'inverted microscope', 'Zeiss', 'Axiovert 200M', '2015-07-06 14:41:07', 'canano_admin'),
       (49250312, 'imaging system', 'Stanford Photonics', 'bioluminescence imaging system', '2015-07-08 10:42:03',
        'canano_admin'),
       (49250313, 'research upright microscope', 'Zeiss', 'Axioskop 49', '2015-07-08 10:45:19', 'canano_admin'),
       (49250314, 'research upright microscope', 'Zeiss', 'Axioskop 40', '2015-07-08 12:14:44', 'canano_admin'),
       (49250315, 'gamma counter', 'PerkinElmer', 'Wizard2', '2015-07-09 14:57:51', 'canano_admin'),
       (49250316, 'preclinical PET/CT scanner', 'Siemens', 'Inveon', '2015-07-13 13:49:47', 'canano_admin'),
       (49250317, 'preclinical small animal PET/CT scanner', 'Siemens', 'Inveon', '2015-07-14 11:16:28',
        'canano_admin'),
       (49250318, 'confocal laser scanning microscope', 'Zeiss', 'LSM 700', '2015-07-16 11:57:20', 'canano_admin'),
       (49250319, 'laser scanning confocal microscope', 'Zeiss', 'LSM 700', '2015-07-16 12:18:11', 'canano_admin'),
       (49250320, 'inverted microscope', 'Zeiss', 'Axiovert', '2015-07-16 12:28:21', 'canano_admin'),
       (49250321, 'spectrofluorometer', 'PerkinElmer', 'LS55', '2015-07-16 12:51:55', 'canano_admin'),
       (49250322, 'inverted research microscope', 'Zeiss', 'Axiovert 200M', '2015-07-16 12:54:15', 'canano_admin'),
       (50233344, 'dynamic light scattering instrument', 'Malvern', 'Zetasizer Nano series', '2015-09-04 12:27:34',
        'canano_admin'),
       (50233345, 'electrophoretic light scattering instrument', 'Malvern', 'Zetasizer Nano series',
        '2015-09-04 12:36:01', 'canano_admin'),
       (50233346, 'scanning electron microscope', 'FEI', 'Quanta 650', '2015-09-04 12:39:03', 'canano_admin'),
       (50233347, 'digital thermometer', 'Omega', 'HH309A', '2015-09-04 14:24:17', 'canano_admin'),
       (50233348, 'diode laser', 'Diomed', '15 Plus', '2015-09-07 15:14:28', 'canano_admin'),
       (50233349, 'inductively coupled plasma mass spectrometer', 'PerkinElmer', 'ELAN 9000', '2015-09-08 12:05:58',
        'canano_admin'),
       (50233350, 'research upright microscope', 'Olympus', 'BX 41', '2015-09-08 15:42:00', 'canano_admin'),
       (50233351, 'ICCD camera', 'Princeton Instruments', 'PI-MAX', '2015-09-09 10:46:43', 'canano_admin'),
       (50233352, 'diode laser', 'Angiodynamics', 'Diomed 15 Plus', '2015-09-09 10:52:13', 'canano_admin'),
       (50233353, 'scanning electron microscope', 'FEI', 'Quanta 650 FEG', '2015-09-09 13:01:50', 'canano_admin'),
       (50233354, 'inductively coupled plasma mass spectrometer', 'PerkinElmer', 'ELA 9000', '2015-09-11 13:12:36',
        'canano_admin'),
       (51412992, 'transmission electron microscope', 'FEI', 'Tecnai 20', '2015-10-22 10:46:25', 'canano_admin'),
       (52002816, 'bioanalyzer', 'Alltech', 'test', '2015-10-29 11:23:05', 'canano_admin'),
       (52002817, 'induction heating system', 'Abbott', 'test', '2015-10-29 11:26:41', 'canano_admin'),
       (52002818, 'coagulation monitor', 'Amersham Pharmacia Biotech', 'test', '2015-10-29 11:28:45', 'canano_admin'),
       (52002819, 'deconvolution fluorescence microscope', 'B&W Tek', 'test', '2015-10-29 11:30:44', 'canano_admin'),
       (55083008, 'confocal microscope', 'Nikon', 'A1', '2016-04-29 11:53:12', 'canano_admin'),
       (55083009, 'spectrofluorometer', 'BioTek', 'Synergy HT', '2016-05-02 11:51:08', 'canano_admin'),
       (55083010, 'preclinical imaging system', 'Carestream', 'Kodak Image Station 4000MM', '2016-05-03 11:02:25',
        'canano_admin'),
       (55803904, 'dynamic light scattering instrument', 'Luxtron', 'Zetasizer Nano ZS', '2016-05-11 12:13:52',
        'canano_admin'),
       (55803905, 'spectrophotometer', 'Varian', 'Cary 100 BIO', '2016-05-11 12:45:13', 'canano_admin'),
       (55803906, '', 'Malvern', 'Zetasizer Nano ZS', '2016-05-16 11:46:02', 'canano_admin'),
       (55803907, 'spectrofluorometer', 'Varian', 'Cary 100 BIO', '2016-05-16 12:24:19', 'canano_admin'),
       (56295424, '', 'Malvern', 'Zetasizer Nano', '2016-05-25 10:16:34', 'canano_admin'),
       (56295425, 'inductively coupled plasma-atomic emission spectrometer', 'PerkinElmer', 'Optima 3000',
        '2016-05-25 11:11:22', 'canano_admin'),
       (56295426, 'preclinical imaging system', 'Carestream', 'In-Vivo FX MS PRO', '2016-05-25 11:35:59',
        'canano_admin'),
       (56295427, 'digital camera', 'Leica', 'DFC420', '2016-05-26 11:22:52', 'canano_admin'),
       (56295428, 'spectrofluorometer', 'BMG LABTECH', 'PHERAstar Plus', '2016-06-01 11:34:10', 'canano_admin'),
       (56295429, 'detector', 'Waters', '2996', '2016-06-09 14:45:36', 'canano_admin'),
       (56295430, 'separation module', 'Waters', '2695', '2016-06-09 14:45:35', 'canano_admin'),
       (56295431, 'separation column', 'Waters', 'SunFire C8 (5 um 4.6 mm x 75 mm)', '2016-06-09 14:45:34',
        'canano_admin'),
       (56295432, 'size exclusion column', 'Tosoh Bioscience', 'TSKgel G4000 SWxl', '2016-06-10 11:44:57',
        'canano_admin'),
       (56295433, 'dialysis device', 'Thermo Scientific', '88402', '2016-06-10 12:12:08', 'canano_admin'),
       (56983552, 'thermogravimetric analyzer', 'TA Instruments', 'Q5000', '2016-07-15 13:25:22', 'canano_admin'),
       (56983553, 'preclinical imaging system', 'Caliper Life Sciences', 'IVIS Lumina', '2016-07-18 12:58:30',
        'canano_admin'),
       (56983554, 'spectrofluorometer', 'Molecular Devices', 'SpectraMax M5', '2016-07-20 10:59:38', 'canano_admin'),
       (58261504, 'preclinical imaging system', 'Caliper Life Sciences', 'IVIS Lumina II', '2016-10-17 11:15:07',
        'canano_admin'),
       (58261505, 'flow cytometer', 'Beckman/Coulter', 'CyAn ADP', '2016-10-17 16:53:18', 'canano_admin'),
       (58261506, '', 'Beckman/Coulter', 'CyAn ADP', '2016-10-21 11:58:42', 'canano_admin'),
       (59375616, 'luminometer', 'BioTek', 'Synergy 4', '2016-11-08 11:56:09', 'canano_admin'),
       (59375617, 'qRT-PCR instrument', 'Applied Biosystems', 'Step-One Plus Real Time PCR  System',
        '2016-11-08 14:16:29', 'canano_admin'),
       (59375618, 'spectrofluorometer', 'Horiba', 'SPEX Fluolog-3', '2016-11-09 12:57:59', 'canano_admin'),
       (59375619, 'qRT-PCR instrument', 'Applied Biosystems', 'Step-One Plus Real Time PCR System',
        '2016-11-11 11:37:24', 'canano_admin'),
       (59375620, 'preclinical imaging system', 'Caliper Life Sciences', 'IVIS Spectrum', '2016-11-16 12:01:34',
        'canano_admin'),
       (59375621, 'confocal laser scanning microscope', 'Olympus', 'FV1000', '2016-11-16 12:50:17', 'canano_admin'),
       (59375622, 'temperature gradient gel electrophoresis instrument', 'Biometra', 'TGGE system',
        '2016-11-18 13:11:14', 'canano_admin'),
       (60096512, 'thermal cycler', 'MJ Research', '', '2016-12-08 11:07:52', 'canano_admin'),
       (61145088, 'scintillation counter', 'PerkinElmer', 'Tri-Carb 2000CA', '2017-01-25 11:48:05', 'canano_admin'),
       (61145089, 'scintillation counter', 'Packard', 'Tri-Carb 2000CA', '2017-01-25 13:25:30', 'canano_admin'),
       (61145090, 'spinning disk confocal microscope', 'PerkinElmer', 'UltraVIEW', '2017-01-26 11:42:07',
        'canano_admin'),
       (61145091, 'inductively coupled plasma mass spectrometer', 'PerkinElmer', 'ELAN DRC Plus', '2017-01-27 16:53:18',
        'canano_admin'),
       (61145092, 'spectrophotometer', 'BMG Labtech', 'FLUOstar Omega', '2017-01-30 09:51:57', 'canano_admin'),
       (61669376, 'transmission electron microscope', 'Zeiss', 'Libre 200 FE', '2017-03-15 12:11:09', 'canano_admin'),
       (61669377, 'spectrophotometer', 'Thermo Scientific', 'NanoDrop 2000', '2017-03-15 12:16:50', 'canano_admin'),
       (61669378, 'inverted research microscope', 'Zeiss', 'Axio Observer Z1', '2017-03-15 12:45:44', 'canano_admin'),
       (62914560, 'spectrofluorometer', 'PerkinElmer', 'LS 55', '2017-05-03 09:53:56', 'canano_admin'),
       (62914561, 'HPLC system', 'Agilent', '1200', '2017-05-03 12:35:55', 'canano_admin'),
       (62914562, 'reverse phase column', '', 'Nucleosil C18', '2017-05-03 12:35:56', 'canano_admin'),
       (62914563, 'transmission electron microscope', 'Zeiss', 'LEO EM 910', '2017-05-04 13:30:38', 'canano_admin'),
       (62914564, 'digital camera', 'Gatan', 'Orius SC1000', '2017-05-04 13:30:39', 'canano_admin'),
       (63275008, 'scintillation counter', 'Packard', 'Tri Carb 4000', '2017-05-08 12:16:49', 'canano_admin'),
       (64258048, 'electrophoretic light scattering instrument', 'Malvern', '', '2017-08-02 10:11:58', 'canano_admin'),
       (64258049, 'transmission electron microscope', 'FEI', 'Tecmai T12', '2017-08-02 10:37:50', 'canano_admin'),
       (64258050, 'digital camera', 'TVIPS', 'F224', '2017-08-02 10:37:51', 'canano_admin'),
       (64258051, 'bead-formed agarose-based gel filtration matrix', '', 'Sepharose 6B', '2017-08-02 12:06:58',
        'canano_admin'),
       (64258052, 'automated hematology analyzer', 'Sysmex', 'XE-2100', '2017-08-03 20:04:21', 'canano_admin'),
       (64258053, 'confocal laser scanning microscope', 'Zeiss', 'LSM 410', '2017-08-04 13:12:10', 'canano_admin'),
       (64258054, 'scintillation counter', 'Wallac', '1409', '2017-08-04 13:45:21', 'canano_admin'),
       (64847872, 'C-18 reverse phase column', '', 'Zorbax 300SB-C18 column', '2017-08-29 10:37:46', 'canano_admin'),
       (64847873, '', '', 'Dowex cation-exchange resin beads', '2017-08-29 10:48:45', 'canano_admin'),
       (64847874, 'reverse phase column', '', 'C8 column', '2017-08-29 10:54:12', 'canano_admin'),
       (64847875, 'spectrofluorometer', 'Kontron Instruments', 'SFM 25', '2017-08-29 10:55:53', 'canano_admin'),
       (65929216, 'thermal cycler', 'Agilent', 'Ag 1000', '2020-01-08 19:18:01', 'canano_curator'),
       (66191360, 'automated colony counter', 'Biacore', 'BA4', '2020-01-09 23:03:07', 'canano_curator'),
       (66191361, 'automated colony counter', 'Applied Biosystems', 'aBio 1201', '2020-01-09 23:11:40',
        'canano_curator'),
       (66387968, 'coulter counter', 'Andor', 'Andor1', '2020-01-10 13:26:14', 'canano_curator');
/*!40000 ALTER TABLE `instrument`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instrument_to_review`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `instrument_to_review`
(
    `instrument_pk_id`        bigint(20) NOT NULL,
    `instrument_config_pk_id` bigint(20) NOT NULL,
    `characterization_pk_id`  bigint(20) NOT NULL,
    `instrument_type`         varchar(200) DEFAULT NULL,
    `description`             text
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instrument_to_review`
--

LOCK TABLES `instrument_to_review` WRITE;
/*!40000 ALTER TABLE `instrument_to_review`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `instrument_to_review`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `keyword`
(
    `keyword_pk_id` bigint(20)   NOT NULL,
    `name`          varchar(100) NOT NULL,
    PRIMARY KEY (`keyword_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword`
--

LOCK TABLES `keyword` WRITE;
/*!40000 ALTER TABLE `keyword`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `keyword`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `keyword_file`
(
    `keyword_pk_id` bigint(20) NOT NULL,
    `file_pk_id`    bigint(20) NOT NULL,
    PRIMARY KEY (`keyword_pk_id`, `file_pk_id`),
    KEY `keyword_pk_id` (`keyword_pk_id`),
    KEY `lab_file_pk_id` (`file_pk_id`),
    CONSTRAINT `FK_keyword_file_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_keyword_file_keyword` FOREIGN KEY (`keyword_pk_id`) REFERENCES `keyword` (`keyword_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword_file`
--

LOCK TABLES `keyword_file` WRITE;
/*!40000 ALTER TABLE `keyword_file`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `keyword_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword_sample`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `keyword_sample`
(
    `keyword_pk_id` bigint(20) NOT NULL,
    `sample_pk_id`  bigint(20) NOT NULL,
    PRIMARY KEY (`keyword_pk_id`, `sample_pk_id`),
    KEY `keyword_pk_id` (`keyword_pk_id`),
    KEY `particle_sample_pk_id` (`sample_pk_id`),
    CONSTRAINT `FK_keyword_sample_keyword` FOREIGN KEY (`keyword_pk_id`) REFERENCES `keyword` (`keyword_pk_id`),
    CONSTRAINT `FK_keyword_sample_sample` FOREIGN KEY (`sample_pk_id`) REFERENCES `sample` (`sample_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword_sample`
--

LOCK TABLES `keyword_sample` WRITE;
/*!40000 ALTER TABLE `keyword_sample`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `keyword_sample`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liposome`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `liposome`
(
    `liposome_pk_id` bigint(20) NOT NULL,
    `is_polymerized` tinyint(4)   DEFAULT NULL,
    `polymer_name`   varchar(200) DEFAULT NULL,
    PRIMARY KEY (`liposome_pk_id`),
    KEY `liposome_pk_id` (`liposome_pk_id`),
    CONSTRAINT `FK_liposome_nanomaterial_entity` FOREIGN KEY (`liposome_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liposome`
--

LOCK TABLES `liposome` WRITE;
/*!40000 ALTER TABLE `liposome`
    DISABLE KEYS */;
INSERT IGNORE INTO `liposome` (`liposome_pk_id`, `is_polymerized`, `polymer_name`)
VALUES (65667074, 1, 'Leofric');
/*!40000 ALTER TABLE `liposome`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nano_function`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `nano_function`
(
    `function_pk_id`               bigint(20)   NOT NULL,
    `description`                  text,
    `discriminator`                varchar(200) DEFAULT NULL,
    `functionalizing_entity_pk_id` bigint(20)   DEFAULT NULL,
    `composing_element_pk_id`      bigint(20)   DEFAULT NULL,
    `imaging_function_modality`    varchar(200) DEFAULT NULL,
    `other_function_type`          varchar(200) DEFAULT NULL,
    `created_by`                   varchar(200) NOT NULL,
    `created_date`                 datetime     NOT NULL,
    PRIMARY KEY (`function_pk_id`),
    KEY `composing_element_pk_id` (`composing_element_pk_id`),
    KEY `functionalizing_entity_pk_id` (`functionalizing_entity_pk_id`),
    CONSTRAINT `FK_function_composing_element` FOREIGN KEY (`composing_element_pk_id`) REFERENCES `composing_element` (`composing_element_pk_id`),
    CONSTRAINT `FK_function_functionalizing_entity` FOREIGN KEY (`functionalizing_entity_pk_id`) REFERENCES `functionalizing_entity` (`functionalizing_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nano_function`
--

LOCK TABLES `nano_function` WRITE;
/*!40000 ALTER TABLE `nano_function`
    DISABLE KEYS */;
INSERT IGNORE INTO `nano_function` (`function_pk_id`, `description`, `discriminator`, `functionalizing_entity_pk_id`,
                                    `composing_element_pk_id`, `imaging_function_modality`, `other_function_type`,
                                    `created_by`, `created_date`)
VALUES (65798144, 'Still glowing', 'ImagingFunction', 65732609, NULL, 'infrared', NULL, 'canano_curator',
        '2020-01-08 19:13:24'),
       (65798145, 'Therapeutic test', 'TherapeuticFunction', NULL, 65732610, NULL, NULL, 'canano_curator',
        '2020-01-08 19:14:52'),
       (65798146, 'Therapeutic lipid', 'TherapeuticFunction', NULL, 65732612, NULL, NULL, 'canano_curator',
        '2020-01-08 19:21:14');
/*!40000 ALTER TABLE `nano_function`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nanomaterial_entity`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `nanomaterial_entity`
(
    `nanomaterial_entity_pk_id` bigint(20)   NOT NULL,
    `composition_pk_id`         bigint(20)   NOT NULL,
    `discriminator`             varchar(200) DEFAULT NULL,
    `description`               text,
    `created_by`                varchar(200) NOT NULL,
    `created_date`              datetime     NOT NULL,
    PRIMARY KEY (`nanomaterial_entity_pk_id`),
    KEY `composition_pk_id` (`composition_pk_id`),
    CONSTRAINT `FK_nanomaterial_entity_composition` FOREIGN KEY (`composition_pk_id`) REFERENCES `composition` (`composition_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nanomaterial_entity`
--

LOCK TABLES `nanomaterial_entity` WRITE;
/*!40000 ALTER TABLE `nanomaterial_entity`
    DISABLE KEYS */;
INSERT IGNORE INTO `nanomaterial_entity` (`nanomaterial_entity_pk_id`, `composition_pk_id`, `discriminator`,
                                          `description`, `created_by`, `created_date`)
VALUES (65667072, 65699840, 'Biopolymer', 'Test sample', 'canano_curator', '2020-01-08 19:11:47'),
       (65667073, 65699841, 'Fullerene', 'Full test', 'canano_curator', '2020-01-08 19:14:54'),
       (65667074, 65699842, 'Liposome', 'Test liposome', 'canano_curator', '2020-01-08 19:16:46'),
       (65667075, 65699843, 'OtherNanomaterialEntity', 'Metal example', 'canano_curator', '2020-01-08 19:21:58'),
       (66617344, 66650112, 'OtherNanomaterialEntity', 'Fe', 'canano_curator', '2020-01-10 20:19:50');
/*!40000 ALTER TABLE `nanomaterial_entity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nanomaterial_entity_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `nanomaterial_entity_file`
(
    `nanomaterial_entity_pk_id` bigint(20) NOT NULL,
    `file_pk_id`                bigint(20) NOT NULL,
    PRIMARY KEY (`nanomaterial_entity_pk_id`, `file_pk_id`),
    KEY `file_pk_id` (`file_pk_id`),
    KEY `nanoparticle_entity_pk_id` (`nanomaterial_entity_pk_id`),
    CONSTRAINT `FK_nanomaterial_entity_file_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_nanomaterial_entity_file_nanomaterial_entity` FOREIGN KEY (`nanomaterial_entity_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nanomaterial_entity_file`
--

LOCK TABLES `nanomaterial_entity_file` WRITE;
/*!40000 ALTER TABLE `nanomaterial_entity_file`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `nanomaterial_entity_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `organization`
(
    `organization_pk_id` bigint(20)   NOT NULL,
    `name`               varchar(200) NOT NULL,
    `streetAddress1`     varchar(200) DEFAULT NULL,
    `streetAddress2`     varchar(200) DEFAULT NULL,
    `city`               varchar(100) DEFAULT NULL,
    `state`              varchar(100) DEFAULT NULL,
    `postal_code`        varchar(10)  DEFAULT NULL,
    `country`            varchar(100) DEFAULT NULL,
    `created_date`       datetime     NOT NULL,
    `created_by`         varchar(200) NOT NULL,
    PRIMARY KEY (`organization_pk_id`),
    UNIQUE KEY `name` (`name`),
    UNIQUE KEY `organization_pk_id` (`organization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization`
    DISABLE KEYS */;
INSERT IGNORE INTO `organization` (`organization_pk_id`, `name`, `streetAddress1`, `streetAddress2`, `city`, `state`,
                                   `postal_code`, `country`, `created_date`, `created_by`)
VALUES (1000, 'Synthesis test organization', NULL, NULL, NULL, NULL, NULL, NULL, '2019-12-06 12:15:00',
        'canano_curator'),
       (1005, 'Synthesis demo organization', NULL, NULL, NULL, NULL, NULL, NULL, '2019-12-06 12:15:00',
        'canano_curator'),
       (5931008, 'GATECH_EMORY', '', 'Emory University and Georgia Institute of Technology', 'Atlanta', 'GA', '30332',
        'USA', '2008-09-26 00:00:00', 'canano_admin'),
       (6455296, 'NWU', '', '', 'Chicago', 'IL', '60611', 'USA', '2008-09-30 00:00:00', 'canano_admin'),
       (7274496, 'STANFORD', 'Geballe Laboratory for Advanced Materials', 'Stanford University', 'Stanford', 'CA',
        '94305', 'USA', '2008-10-09 00:00:00', 'canano_admin'),
       (9568258, 'NEU_MIT_MGH', '', 'Northeastern University', 'Boston', 'MA', '', 'USA', '2009-08-31 00:00:00',
        'canano_admin'),
       (10125312, 'GATECH', 'School of Chemistry and Biochemistry', 'Georgia Institute of Technology', 'Atlanta', 'GA',
        '30332', 'USA', '2008-10-16 00:00:00', 'canano_admin'),
       (10289152, 'UNC', '', 'University of North Carolina', 'Chapel Hill', 'NC', '27599', 'USA', '2008-11-10 00:00:00',
        'canano_admin'),
       (11239424, 'CWRU', '2071 Martin Luther King Jr. Drive, Wickenden Building', '', 'Cleveland', 'OH', '44106',
        'USA', '2009-09-17 00:00:00', 'canano_admin'),
       (11239425, 'WU_SU_CHINA',
        'Key Laboratory of Biomedical Polymers of Ministry of Education, Department of Chemistry', 'Wuhan University',
        'Wuhan', '', '430072', 'People\'s Republic of China', '2008-11-20 00:00:00', 'canano_admin'),
       (11239426, 'SNU_CHINA',
        'Department of Chemistry, and Department of Biology, Life and EnVironmental Science College, Shanghai Normal UniVersity',
        '100 Guilin Road', 'Shanghai', '', '200234', 'China', '2008-12-01 00:00:00', 'canano_admin'),
       (11239427, 'UM', '', 'University of Michigan', 'Ann Arbor', 'MI', '', 'USA', '2009-01-06 00:00:00',
        'canano_admin'),
       (11796480, 'VCU_VT_EHC', 'Department of Radiology, Sanger Hall, B3-021, 1101 E Marshall St, PO Box 980072',
        'Virginia Commonwealth University', 'Richmond', 'VA', '23298-0072', 'USA', '2009-03-10 00:00:00',
        'canano_admin'),
       (11796481, 'CAS_VT_VCU', '', 'Chinese Academy of Sciences', 'Beijing', '', '100080', 'China',
        '2009-03-11 00:00:00', 'canano_admin'),
       (11796482, 'WUSTL', 'Department of Radiology', 'Washington University School of Medicine', 'St Louis', 'MO',
        '63110', 'USA', '2009-03-30 00:00:00', 'canano_admin'),
       (11796484, 'TTU', 'Department of Chemical Engineering', 'Texas Tech University', 'Lubbock', 'TX', '79409', 'USA',
        '2009-04-06 00:00:00', 'canano_admin'),
       (11796485, 'UC_HU_UEN_GERMANY', 'Hautklinik, Photobiologisches Labor', 'Universitatsklinikum Charite', 'Berlin',
        '', '', 'Germany', '2009-05-07 00:00:00', 'canano_admin'),
       (12812288, 'GATECH_UCSF', '', 'Georgia Institute of Technology', 'Atlanta', 'GA', '30332', '',
        '2009-04-20 00:00:00', 'canano_admin'),
       (12812289, 'NEU_MGH_UP_FCCC', '', 'Northeastern University', 'Boston', 'MA', '', 'USA', '2009-05-06 00:00:00',
        'canano_admin'),
       (12812290, 'JST_AIST_FHU_TU_NEC_MU_JAPAN',
        'Department of Applied Chemistry, Faculty of Engineering, and Center for Future Chemistry,  Kyushu University',
        '744 Moto-oka, Nishi-ku,', 'Fukuoka', '', '', 'Japan', '2009-05-18 00:00:00', 'canano_admin'),
       (12976128, 'JSTA_FHU_NEC_MU_AIST_JAPAN', 'SORST, Japan Science and Technology Agency c/o NEC', '34 Miyukigaoka',
        'Tsukuba', 'Ibaraki', '305-8501', 'Japan', '2009-07-27 00:00:00', 'canano_admin'),
       (12976129, 'NEU', '', 'Northeastern University', 'Boston', 'MA', '', 'USA', '2009-05-22 00:00:00',
        'canano_admin'),
       (12976130, 'PURDUE', '', 'Purdue University', 'West Lafayette', 'IN', '', 'USA', '2009-06-08 00:00:00',
        'canano_admin'),
       (12976131, 'MSKCC_CU_UA',
        'Molecular Pharmacology and Chemistry Program and Departments of Medicine and Radiology',
        'Memorial Sloan Kettering Cancer Center', 'New York', 'NY', '10021', 'USA', '2009-08-19 00:00:00',
        'canano_admin'),
       (12976133, 'MIT_UC_BBIC_HST_CU', 'Department of Chemical Engineering Massachusetts Institute of Technology',
        '45 Carleton Street, E25-342', 'Cambridge', 'MA', '02139', 'USA', '2009-09-09 00:00:00', 'canano_admin'),
       (12976135, 'UAM_CSIC_IMDEA', 'Departamento de Biologia, Universidad Autonoma de Madrid,',
        'C/ Darwin 2, Cantoblanco', 'Madrid', '', '28049', 'Spain', '2009-09-11 00:00:00', 'canano_admin'),
       (12976136, 'UI', 'Department of Chemistry', 'University of Iowa', 'Iowa City', 'IA', '52242', 'USA',
        '2009-09-14 00:00:00', 'canano_admin'),
       (12976137, 'WSU', 'Department of Pharmaceutical Sciences', 'Wayne State University', 'Detroit', 'MI', '48202',
        'USA', '2009-09-29 00:00:00', 'canano_admin'),
       (12976138, 'CP_UCLA_CalTech', 'Chemical Engineering', 'California Institute of Technology', 'Pasadena', 'CA',
        '91125', 'USA', '2009-10-05 00:00:00', 'canano_admin'),
       (12976139, 'NIOSH', 'National Institute for Occupational Safety and Health',
        'Health Effects Laboratory Division', 'Morgantown', 'WV', '', 'USA', '2009-10-07 00:00:00', 'canano_admin'),
       (13598720, 'CLM_UHA_CDS_INSERM', '', '', '', '', '', 'France', '2009-10-14 00:00:00', 'canano_admin'),
       (14843904, 'KI', 'Karolinska Institutet', '57 Huddinge', 'Stockholm', '', 'SE-141', 'Sweden',
        '2008-09-30 00:00:00', 'canano_admin'),
       (14843905, 'RIT_KI_SU', 'Royal Institute of Technology', 'Drottning Kristinas vag 51', 'Stockholm ', '',
        '100 44', 'Sweden', '2009-11-10 00:00:00', 'canano_admin'),
       (15728641, 'DWU_SNU_SU_US', 'Dongduk Women?s University', '23-1, Wolgok-dong, Seongbuk-gu,', 'Seoul', '',
        '136-714', 'Republic of Korea', '2009-12-23 00:00:00', 'canano_admin'),
       (15728642, 'UToronto', 'University of Toronto', '4 Taddle Creek Road', 'Toronto', 'Ontario', 'M5S 3G9', 'Canada',
        '2009-12-30 00:00:00', 'canano_admin'),
       (15728643, 'UM-C', '', 'University of Missouri-Columbia', 'Columbia', 'MO', '65212', 'USA',
        '2010-01-05 00:00:00', 'canano_admin'),
       (15728644, 'UN', 'Department of Pharmacy and Pharmaceutical Technology', 'University of Navarra', 'Pamplona', '',
        '31008', 'Spain', '2010-01-19 00:00:00', 'canano_admin'),
       (15728646, 'UKY', 'Department of Chemistry, 101 Chemistry-Physics Building', 'University of Kentucky',
        'Lexington', 'KY', '40506-0055', 'USA', '2010-03-12 00:00:00', 'canano_admin'),
       (15728647, 'JSTA_JFCR_NEC_FHU_TUSM_NIAIST', '', '', 'Tokyo', '', '', 'Japan', '2008-08-20 00:00:00',
        'canano_admin'),
       (26935297, 'UNC_ChemD',
        'Department of Chemistry Lineberger Comprehensive Cancer Center Institute for Nanomedicine',
        'University of North Carolina', 'Chapel Hill', 'NC', '27599', 'USA', '2010-05-13 00:00:00', 'canano_admin'),
       (26935298, 'UL_NL', 'Nanomedicine Laboratory, Center for Drug Delivery Research, The School of Pharmacy',
        'University of London', 'London', '', 'WC1N 1AX', 'United Kingdom', '2010-05-13 00:00:00', 'canano_admin'),
       (27099136, 'UMC_DVP', 'Department of Veterinary Pathobiology, Veterinary Medical Diagnostic Laboratory',
        'University of Missouri', 'Columbia', 'MO', '', 'USA', '2010-06-04 10:40:29', 'canano_admin'),
       (27099137, 'UMC_RadiolD', 'Department of Radiology', 'University of Missouri-Columbia', 'Columbia', 'MO',
        '65212', 'USA', '2010-06-04 16:27:58', 'canano_admin'),
       (28082176, 'PNNL_CBBG', 'Cell Biology and Biochemistry Group, Box 999, Mail Stop P7-56',
        'Pacific Northwest National Laboratory', 'Richland', 'WA', '99532', 'USA', '2010-06-09 09:55:01',
        'canano_admin'),
       (28082177, 'SUNYB_ILPB', 'Institute for Lasers, Photonics and Biophotonics',
        'The State University of New York at Buffalo', 'Buffalo', 'NY', '14260', 'USA', '2010-06-11 14:11:09',
        'canano_admin'),
       (28639232, 'PURDUEU_BN',
        'Birck Nanotechnology, Bindley Bioscience, and Purdue Cancer Center,  225 S. University Street',
        'Purdue University', 'West Lafayette', 'IN', '47906', 'USA', '2009-06-08 00:00:00', 'canano_admin'),
       (28639233, 'NEU_DPS', 'Department of Pharmaceutical Sciences, School of Pharmacy, Northeastern University',
        '360 Huntington Avenue  110 Mugar Life Sciences Building ', 'Boston', 'MA', '02115', 'USA',
        '2010-06-25 14:32:13', 'canano_admin'),
       (28639234, 'WUSTL_DIM', '', '', 'Saint Louis', 'MO', '', 'USA', '2009-03-30 00:00:00', 'canano_admin'),
       (28639235, 'MIT_ChemD', 'Department of Chemistry, Massachusetts Institute of Technology',
        '77 Massachusetts Avenue', 'Cambridge', 'MA', '02139', 'USA', '2010-08-02 14:03:24', 'canano_admin'),
       (28639236, 'LMRT', 'Laboratory for Multiscale Regenerative Technologies',
        'Massachusetts Institute of Technology; E19-502DC', 'Cambridge', 'MA', '02139', 'USA', '2010-08-09 16:42:39',
        'canano_admin'),
       (28639237, 'MIT_LMRT', 'Laboratory for Multiscale Regenerative Technologies',
        'Massachusetts Institute of Technology; E19-502D', 'Cambridge', 'MA', '02139', 'USA', '2010-08-09 17:19:32',
        'canano_admin'),
       (28639238, 'Harvard_MIT_DHST', 'Harvard-MIT Division of Health Sciences and Technology',
        'Massachusetts Institute of Technology', 'Cambridge', 'MA', '02139', 'USA', '2010-08-16 11:48:22',
        'canano_admin'),
       (28639239, 'UCSD_ChemBiochemD',
        'Materials Science and Engineering Program, Department of Chemistry and Biochemistry, Department of Bioengineering, University of California, San Diego',
        '9500 Gilman', 'La Jolla', 'CA', '92093', 'USA', '2010-09-08 17:35:17', 'canano_admin'),
       (28639240, 'HarvardU_PhysD', 'Department of Physics', ' Harvard University', 'Cambridge', 'MA', '02138', 'USA',
        '2010-10-18 18:01:48', 'canano_admin'),
       (28639241, 'BWH_AnesthesiologyD',
        'Department of Anesthesiology, Brigham and Women\'s Hospital, Harvard Medical School', '75 Francis Street',
        'Boston', 'MA', '02115', 'USA', '2010-11-01 17:44:41', 'canano_admin'),
       (28639242, 'MIT_ChemEngineeringD', 'Department of Chemical Engineering, Massachusetts Institute of Technology',
        '77 Massachusetts Avenue', 'Cambridge', 'MA', '02139', 'USA', '2010-11-01 17:48:38', 'canano_admin'),
       (28639243, 'GIST_LifeScienceD',
        'Research Center for Biomolecular Nanotechnology, Department of Life Science, Gwangju Institute of Science and Technology,',
        'Buk-gu', 'Gwangju', '', '500-712', 'South Korea', '2008-08-20 00:00:00', 'canano_admin'),
       (30048256, 'WSU_DPS', 'Department of Pharmaceutical Sciences, Wayne State University', '259 Mack Avenue',
        'Detroit', 'MI', '48202', 'USA', '2010-12-14 11:38:39', 'canano_admin'),
       (30048257, 'NRCWE', 'National Research Center for the Working Environment', 'Lerso Parkalle 105', 'Copenhagen',
        '', 'DK-2100', 'Denmark', '2010-12-21 15:41:10', 'canano_admin'),
       (30048258, 'AIST_HTRC', '', '', '', '', '', '', '2011-01-10 13:24:24', 'canano_admin'),
       (30048259, 'STANFORD_ChemD', 'Department of Chemistry', ' Stanford University', 'Stanford', 'CA', '94305', 'USA',
        '2011-01-14 16:09:51', 'canano_admin'),
       (30146560, 'Childrens Hospital Los Angeles', '4650 Sunset Boulevard SRT 302',
        'Developmental Therapeutics Program', 'Los Angeles', 'CA', '90027', 'USA', '2011-02-18 02:23:22',
        'canano_admin'),
       (32636928, 'DC', 'Thayer School of Engineering', 'Dartmouth College', 'Hanover', 'NH', '03755', 'USA',
        '2012-06-13 14:39:47', 'canano_admin'),
       (33128448, 'UT_UMG_MDACC_RU_UTA', 'Department of Nanomedicine and Biomedical Engineering',
        'The University of Texas Health Science Center at Houston,', 'Houston', 'TX', '', 'USA', '2012-06-27 11:51:03',
        'canano_admin'),
       (33128449, 'UTHSCH_UMG_MDACC_RU_UTA', 'Department of Nanomedicine and Biomedical Engineering',
        'The University of Texas Health Science Center at Houston', 'Houston', 'TX', '', 'USA', '2012-06-27 11:51:03',
        'canano_admin'),
       (36765696, 'JHU_KSU', 'Department of Materials Science and Engineering', 'Johns Hopkins University', 'Baltimore',
        'MD', '21218', 'USA', '2012-11-27 13:09:15', 'canano_admin'),
       (36765697, 'STANFORD_OM', 'Molecular Imaging Program, Department of Radiology and Bio-X Program ',
        'Stanford University School of Medicine', 'Stanford', 'CA', '94305', 'USA', '2012-12-14 14:06:01',
        'canano_admin'),
       (36765698, 'STANFORD_MIPS', 'Molecular Imaging Program, Department of Radiology and Bio-X Program ',
        'Stanford University School of Medicine', 'Stanford', 'CA', '94305', 'USA', '2013-01-24 12:45:11',
        'canano_admin'),
       (37978112, 'YALE', 'Department of Biomedical and Environmental Engineering',
        'Yale University School of Engineering and Applied Sciences', 'New Haven', 'CT', '06511', 'USA',
        '2013-05-16 14:30:20', 'canano_admin'),
       (39092224, 'CUK', 'Department of Otolaryngology-Head and Neck Surgery',
        'College of Medicine, The Catholic University of Korea', '', '', '', 'Republic of Korea', '2013-07-25 11:30:18',
        'canano_admin'),
       (39092225, 'KNUT', 'Department of Chemical & Biological Engineering',
        'Korea National University of Transportation', 'Chungju', '', '380-702', 'Republic of Korea',
        '2013-07-25 11:34:33', 'canano_admin'),
       (39616512, 'QUB',
        'The Centre for Infection and Immunity, School of Medicine, Dentistry and Biomedical Sciences, Queen\'s University Belfast',
        'University Road', 'Belfast', '', 'BT9 7AE', 'United Kingdom', '2013-09-03 13:57:35', 'canano_admin'),
       (40697856, 'EMORY_Surgery', 'Departments of Surgery, Radiology and Imaging Sciences',
        'Emory University School of Medicine', 'Atlanta', 'GA', '30322', 'USA', '2013-12-06 12:34:50', 'canano_admin'),
       (40697857, 'EMORY_Radiology_Imaging_Sciences', 'Department of Radiology and Imaging Sciences',
        'Emory University School of Medicine', 'Atlanta', 'GA', '30322', 'USA', '2013-12-06 12:38:23', 'canano_admin'),
       (41385984, 'NWU_ChemD_CLPI', 'Department of Chemistry, Chemistry of Life Processes Institute',
        'Northwestern University', 'Evanston', 'IL', '60208', 'USA', '2014-01-17 11:18:33', 'canano_admin'),
       (41385985, 'NWU_MedD',
        'Department of  Medicine, Lurie 4-113, Feinberg School of Medicine, Northwestern University',
        '303 East Superior Street', 'Chicago', 'IL', '60611', 'USA', '2014-01-17 11:23:09', 'canano_admin'),
       (41975808, 'UTSMC', 'Department of Radiology', 'University of Texas Southwestern Medical Center', 'Dallas', 'TX',
        '75390', 'USA', '2014-03-28 11:27:14', 'canano_admin'),
       (43220992, 'NCSR \"Demokritos\"',
        'Sol-Gel Laboratory, Institute for Advanced Materials, Physicochemical Processes,  Nanotechnology & Microsystems, NCSR \"Demokritos\"',
        '15310 Aghia Paraskevi', 'Attikis', '', '', 'Greece', '2014-05-07 12:20:36', 'canano_admin'),
       (43810816, 'NWU_NeurologyD',
        'Ken and Ruth Davee Department of Neurology, The Northwestern Brain Tumor Institute, The Robert H. Lurie Comprehensive Cancer Center, Northwestern University',
        '303 East Superior', 'Chicago', 'IL', '60611', 'United States', '2014-06-09 11:25:35', 'canano_admin'),
       (45383680, 'WU_KLACBM',
        'Key Laboratory of Analytical Chemistry for Biology and Medicine (Ministry of Education), College of Chemistry and Molecular Sciences',
        'State Key Laboratory of Virology, and Wuhan Institute of Biotechnology, Wuhan University', 'Wuhan', '',
        '430072', 'People\'s Republic of China', '2014-09-01 12:24:29', 'canano_admin'),
       (45973504, 'Caltech_ChemEngineering', 'Chemical Engineering  ', 'California Institute of Technology', 'Pasadena',
        'CA', '91125', 'United States', '2014-10-20 13:11:04', 'canano_admin'),
       (47611904, 'SJTU-SchoolofMedicine',
        'Department of Pharmacology, Institute of Medical Sciences, Shanghai Jiao Tong University School of Medicine',
        '280 South Chongqing Road', 'Shanghai', '', '', 'China', '2015-01-09 13:35:12', 'canano_admin'),
       (47611906, 'BRICH-Molecular_Pharmacology', 'City of Hope Comprehensive Cancer Center', 'Beckman Center',
        'Duarte', 'CA', '91010', 'USA', '2015-01-26 12:04:57', 'canano_admin'),
       (48398336, 'JHU_Pathology', 'Department of Pathology, Johns Hopkins School of Medicine',
        '733 N. Broadway, MRB 639', 'Baltimore', 'MD', '21205', 'USA', '2015-04-14 12:45:57', 'canano_admin'),
       (49020928, 'UI_UC_DMSE', 'Department of Materials Science and Engineering',
        'University of Illinois at Urbana–Champaign', 'Urbana', 'IL', '61801', 'USA', '2015-07-02 13:05:08',
        'canano_admin'),
       (49020929, 'UI-UC_DVCM', 'Department of Veterinary Clinical Medicine',
        'University of Illinois at Urbana–Champaign', 'Urbana', 'IL', '61801', 'USA', '2015-07-02 13:08:24',
        'canano_admin'),
       (49020930, 'UI--UC_DMSE', 'Department Materials Science and Engineering',
        'University of Illinois at Urbana–Champaign', 'Urbana', 'IL', '61801', 'USA', '2015-07-02 13:12:45',
        'canano_admin'),
       (49020931, 'UI-UC_DFSHN', 'Department of Food Science and Human Nutrition',
        'University of Illinois at Urbana–Champaign', 'Urbana', 'IL', '61801', 'USA', '2015-07-02 13:16:27',
        'canano_admin'),
       (49020932, 'UI-UC_DMSE', 'Department Materials Science and Engineering',
        'University of Illinois at Urbana–Champaign', 'Urbana', 'IL', '61801', 'USA', '2015-07-03 10:21:04',
        'canano_admin'),
       (50167808, 'RU_BMC',
        'Division of Molecular Imaging, Department of Radiology Baylor College of Medicine, BCM 360',
        'One Baylor Plaza', 'Houston', 'TX', '77030', 'USA', '2015-09-04 10:23:02', 'canano_admin'),
       (50167809, 'RU_ChemD',
        'Department of Chemistry Department of Electrical and Computer Engineering Rice University', '6100 Main Street',
        'Houston', 'TX', '77005', 'USA', '2015-09-10 12:39:04', 'canano_admin'),
       (50167810, 'BCM_RadiolD', 'test', 'test', 'test', 'md', '20850', 'usa', '2015-09-10 12:42:34', 'canano_admin'),
       (50987011, 'UToronto_IBBE',
        'Institute of Biomaterials and Biomedical Engineering Donnelly Centre for Cellular and Biomolecular Research Department of Chemical Engineering Department of Chemistry',
        'University of Toronto', 'Toronto', 'CA-ON', '', 'Canada', '2015-10-08 12:09:43', 'canano_admin'),
       (53379072, 'UChicago', '929 E 57st.', '', 'CHICAGO', 'IL', '60615', 'US', '2015-11-19 18:14:33', 'canano_admin'),
       (53379073, 'Caltech', '1200 E California Blvd', '', 'Pasadena', 'CA', '91125', 'USA', '2015-11-19 18:33:57',
        'canano_admin'),
       (54394880, 'UL_BCC', 'Brown Cancer Center, University of Louisville, CTRB 309', '505 Hancock Street',
        'Louisville', 'KY', '40202', 'USA', '2016-04-27 10:13:21', 'canano_admin'),
       (57901056, 'OSU_CP', 'College of Pharmacy, The Ohio State University',
        'Biomedical Research Tower, Rm. 418, 460 W 12th Ave.', 'Columbus', 'OH', '43210', 'USA', '2016-09-21 13:32:21',
        'canano_admin'),
       (58884096, 'UCLA', '570 Westwood plaza CNSI 6511', '', 'Los Angeles', 'CA', '90095', 'USA',
        '2016-11-01 12:52:27', 'LiuX'),
       (59736064, 'MCCRC_CR', 'Clinical Research, Mary Crowley Cancer Research Center', '1700 Pacific Ave., Suite 1100',
        'Dallas', 'TX', '75201', 'USA', '2016-12-07 14:09:08', 'canano_admin'),
       (59736065, 'UTMDACC_DGMO',
        'Department of Genitourinary Medical Oncology, University of Texas, MD Anderson Cancer Center',
        '1155 Pressler, Unit 1374', 'Houston', 'TX', '77030', 'USA', '2016-12-13 12:23:23', 'canano_admin'),
       (59736066, 'GU_DOnc', 'Department of Oncology, Experimental Therapeutics Division',
        'Georgetown University Medical Center, TRB/E420, 3970 Reservoir Rd NW', 'Washington', 'DC', '20057-1469', 'USA',
        '2016-12-15 14:33:55', 'canano_admin'),
       (60522496, 'MC2TCN', '1275 York Ave', '', 'New York', 'NY', '10065', 'USA', '2017-01-06 14:41:17',
        'canano_admin'),
       (61407232, 'CWRU_BiomedEngDep', '2071 Martin Luther King Jr. Drive, Wickenden Building', '', 'Cleveland', 'OH',
        '44106', 'USA', '2017-03-15 10:41:16', 'canano_admin'),
       (62619648, 'UNC_CNDD',
        'Center for Nanotechnology in Drug Delivery, Eshelman School of Pharmacy, University of North Carolina at Chapel Hill',
        'Genetic Medicine Building, Room 1094, Campus Box 7362', 'Chapel Hill', 'NC', '27599', 'USA',
        '2017-05-02 13:08:15', 'canano_admin'),
       (62619649, 'JMUW_CCTMS', 'Functional Polymer Materials, Chair for Chemical Technology of Materials Synthesis',
        'Julius Maximilians Universitat Wuerzburg, Rontgenring 11, 97070', 'Wuerzburg', '', '97070', 'Germany',
        '2017-05-02 13:19:00', 'canano_admin'),
       (63832064, 'SZMC_HU_NCL', 'Shaare Zedek Medical Center - Oncology Institute', 'POB 3235', 'Jerusalem', '',
        '91031', 'Israel', '2017-08-01 13:16:19', 'canano_admin'),
       (64913408, 'MGH-CenterSystBiol', 'Center for Systems Biology, Massachusetts General Hospital',
        'Harvard Medical School', 'Boston', 'MA', '02114', 'USA', '2017-09-15 12:50:25', 'canano_admin'),
       (64913409, 'BWH-LabNanomatBiomat', 'Laboratory of Nanomedicine and Biomaterials, Department of Anesthesiology',
        'Brigham and Women\'s Hospital, Harvard Medical School', 'Boston', 'MA', '02115', 'USA', '2017-09-15 12:52:11',
        'canano_admin'),
       (65601536, 'caNanoLab', '', '', '', '', '', '', '2020-01-08 19:07:58', 'canano_curator');
/*!40000 ALTER TABLE `organization`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_functionalizing_entity`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `other_functionalizing_entity`
(
    `other_func_entity_pk_id` bigint(20)   NOT NULL,
    `type`                    varchar(200) NOT NULL,
    PRIMARY KEY (`other_func_entity_pk_id`),
    KEY `other_func_entity_pk_id` (`other_func_entity_pk_id`),
    CONSTRAINT `FK_other_functionalizing_entity_functionalizing_entity` FOREIGN KEY (`other_func_entity_pk_id`) REFERENCES `functionalizing_entity` (`functionalizing_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_functionalizing_entity`
--

LOCK TABLES `other_functionalizing_entity` WRITE;
/*!40000 ALTER TABLE `other_functionalizing_entity`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `other_functionalizing_entity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_nanomaterial_entity`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `other_nanomaterial_entity`
(
    `other_nanomaterial_entity_pk_id` bigint(20)   NOT NULL,
    `type`                            varchar(200) NOT NULL,
    PRIMARY KEY (`other_nanomaterial_entity_pk_id`),
    KEY `other_nanoparticle_entity_pk_id` (`other_nanomaterial_entity_pk_id`),
    CONSTRAINT `FK_other_nanomaterial_entity_nanomaterial_entity` FOREIGN KEY (`other_nanomaterial_entity_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_nanomaterial_entity`
--

LOCK TABLES `other_nanomaterial_entity` WRITE;
/*!40000 ALTER TABLE `other_nanomaterial_entity`
    DISABLE KEYS */;
INSERT IGNORE INTO `other_nanomaterial_entity` (`other_nanomaterial_entity_pk_id`, `type`)
VALUES (65667075, 'metal particle'),
       (66617344, 'metal particle');
/*!40000 ALTER TABLE `other_nanomaterial_entity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physical_state`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `physical_state`
(
    `physical_state_pk_id` bigint(20)   NOT NULL,
    `type`                 varchar(200) NOT NULL,
    PRIMARY KEY (`physical_state_pk_id`),
    KEY `physical_state_pk_id` (`physical_state_pk_id`),
    CONSTRAINT `FK_physical_state_characterization` FOREIGN KEY (`physical_state_pk_id`) REFERENCES `characterization` (`characterization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physical_state`
--

LOCK TABLES `physical_state` WRITE;
/*!40000 ALTER TABLE `physical_state`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `physical_state`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `point_of_contact`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `point_of_contact`
(
    `poc_pk_id`          bigint(20)   NOT NULL,
    `role`               varchar(200) DEFAULT NULL,
    `first_name`         varchar(200) DEFAULT NULL,
    `last_name`          varchar(200) DEFAULT NULL,
    `middle_initial`     varchar(50)  DEFAULT NULL,
    `phone`              varchar(50)  DEFAULT NULL,
    `email`              varchar(200) DEFAULT NULL,
    `created_date`       datetime     NOT NULL,
    `created_by`         varchar(200) NOT NULL,
    `organization_pk_id` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`poc_pk_id`),
    KEY `organization_pk_id` (`organization_pk_id`),
    CONSTRAINT `FK_point_of_contact_organization` FOREIGN KEY (`organization_pk_id`) REFERENCES `organization` (`organization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point_of_contact`
--

LOCK TABLES `point_of_contact` WRITE;
/*!40000 ALTER TABLE `point_of_contact`
    DISABLE KEYS */;
INSERT IGNORE INTO `point_of_contact` (`poc_pk_id`, `role`, `first_name`, `last_name`, `middle_initial`, `phone`,
                                       `email`, `created_date`, `created_by`, `organization_pk_id`)
VALUES (1000, 'investigator', 'canano', 'curator', NULL, NULL, NULL, '2019-12-06 12:15:00', 'canano_curator', 1000),
       (1005, 'investigator', 'canano', 'curator', NULL, NULL, NULL, '2019-12-06 12:15:00', 'canano_curator', 1005),
       (65568768, '', 'Canano', 'Curator', '', '', '', '2020-01-08 19:07:58', 'canano_curator', 65601536);
/*!40000 ALTER TABLE `point_of_contact`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `polymer`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `polymer`
(
    `polymer_pk_id`     bigint(20) NOT NULL,
    `is_cross_linked`   tinyint(4)     DEFAULT NULL,
    `cross_link_degree` decimal(22, 3) DEFAULT NULL,
    `initiator`         varchar(200)   DEFAULT NULL,
    PRIMARY KEY (`polymer_pk_id`),
    KEY `polymer_pk_id` (`polymer_pk_id`),
    CONSTRAINT `FK_polymer_nanomaterial_entity` FOREIGN KEY (`polymer_pk_id`) REFERENCES `nanomaterial_entity` (`nanomaterial_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `polymer`
--

LOCK TABLES `polymer` WRITE;
/*!40000 ALTER TABLE `polymer`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `polymer`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `protocol`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `protocol`
(
    `protocol_pk_id`        bigint(20)   NOT NULL,
    `protocol_name`         varchar(2000) DEFAULT NULL,
    `protocol_type`         varchar(2000) DEFAULT NULL,
    `created_by`            varchar(200) NOT NULL,
    `created_date`          datetime     NOT NULL,
    `protocol_abbreviation` varchar(200)  DEFAULT NULL,
    `protocol_version`      varchar(200)  DEFAULT NULL,
    `file_pk_id`            bigint(20)    DEFAULT NULL,
    PRIMARY KEY (`protocol_pk_id`),
    UNIQUE KEY `protocol_pk_id` (`protocol_pk_id`),
    KEY `FK_protocol_file` (`file_pk_id`),
    CONSTRAINT `FK_protocol_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocol`
--

LOCK TABLES `protocol` WRITE;
/*!40000 ALTER TABLE `protocol`
    DISABLE KEYS */;
INSERT IGNORE INTO `protocol` (`protocol_pk_id`, `protocol_name`, `protocol_type`, `created_by`, `created_date`,
                               `protocol_abbreviation`, `protocol_version`, `file_pk_id`)
VALUES (66256896, 'Synthesis Test Protocol', 'synthesis', 'canano_curator', '2020-01-10 12:52:34', 'STP', '1.0',
        66289664),
       (66584576, 'Test Characterization Protocol', 'in vivo assay', 'canano_curator', '2020-01-10 20:03:01', 'TCP',
        '1', NULL),
       (66584577, 'Test In Vitro Characterization Protocol', 'in vitro assay', 'canano_curator', '2020-01-10 20:04:41',
        'TiVCP', '1.1', NULL),
       (66945024, 'Test Synthesis Purification Protocol', 'purification', 'canano_curator', '2020-04-21 16:02:41',
        'Sypur', '1.0', NULL),
        (1111, 'Test Protocol for Purification 2', 'purification', 'canano_curator', '2020-05-26 18:00:00','Pure2','1.0',NULL),
       (66945025,'Test Purification Protocol 1','purification','canano_curator','2020-07-07 12:43:59','TPP 1','1.0',1555),
       (66945026,'Test Purification Protocol 2','purification','canano_curator','2020-07-07 12:48:28','TPP2','1.0',66289664);
/*!40000 ALTER TABLE `protocol`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publication`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `publication`
(
    `publication_pk_id`  bigint(20)   NOT NULL,
    `category`           varchar(200) NOT NULL,
    `publication_status` varchar(50)  NOT NULL,
    `pubmed_id`          bigint(20)   DEFAULT NULL,
    `digital_object_id`  varchar(200) DEFAULT NULL,
    `journal_name`       varchar(200) DEFAULT NULL,
    `volume`             varchar(50)  DEFAULT NULL,
    `start_page`         varchar(50)  DEFAULT NULL,
    `end_page`           varchar(50)  DEFAULT NULL,
    `year`               int(11)      DEFAULT NULL,
    `research_area`      varchar(200) DEFAULT NULL,
    PRIMARY KEY (`publication_pk_id`),
    CONSTRAINT `FK_publication_lab_file` FOREIGN KEY (`publication_pk_id`) REFERENCES `file` (`file_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publication`
--

LOCK TABLES `publication` WRITE;
/*!40000 ALTER TABLE `publication`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `publication`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purification_config`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `purification_config`
(
    `purification_config_pk_id`    bigint(20)   NOT NULL COMMENT 'purification_config_pk_id',
    `synthesis_purification_pk_id` bigint(20) DEFAULT NULL COMMENT 'synthesis_purification_pk_id',
    `technique_pk_id`              bigint(20) DEFAULT NULL COMMENT 'technique_pk_id',
    `description`                  text COMMENT 'description',
    `created_by`                   varchar(200) NOT NULL COMMENT 'created_by',
    `created_date`                 datetime     NOT NULL COMMENT 'created_date',
    PRIMARY KEY (`purification_config_pk_id`),
    KEY `FK_synthesis_purification_TO_purification_config` (`synthesis_purification_pk_id`),
    KEY `FK_technique_TO_purification_config_idx` (`technique_pk_id`),
    CONSTRAINT `FK_synthesis_purification_TO_purification_config` FOREIGN KEY (`synthesis_purification_pk_id`) REFERENCES `synthesis_purification` (`synthesis_purification_pk_id`),
    CONSTRAINT `FK_technique_TO_purification_config` FOREIGN KEY (`technique_pk_id`) REFERENCES `technique` (`technique_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purification_config`
--

LOCK TABLES `purification_config` WRITE;
/*!40000 ALTER TABLE `purification_config`
    DISABLE KEYS */;
INSERT IGNORE INTO `purification_config` (`purification_config_pk_id`, `synthesis_purification_pk_id`,
                                          `technique_pk_id`, `description`, `created_by`, `created_date`)
VALUES (1000, 1000, 1000, 'Configuration for synthesis purification', 'canano_curator', '2019-12-06 12:15:00'),
       (1001, 1000, 1005, 'Configuration for purification 2', 'canano_curator', '2019-12-06 12:15:00'),
       (1002, 1001, 65896448, 'Coulter configuration', 'canano_curator', '2019-12-06 12:15:00'),
       (1005, 1005, 1005, NULL, 'canano_curator', '2019-08-28 00:00:00'),
       (1111, 1111, 1111, 'Configuration for synthesis purification', 'canano_curator', '2019-12-06 12:15:00'),
       (1222, 1222, 1222, NULL, 'canano_curator', '2019-08-28 00:00:00');
/*!40000 ALTER TABLE `purification_config`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purification_config_instrument`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `purification_config_instrument`
(
    `purification_config_pk_id` bigint(20) NOT NULL COMMENT 'purification_config_pk_id',
    `instrument_pk_id`          bigint(20) NOT NULL COMMENT 'instrument_pk_id',
    PRIMARY KEY (`purification_config_pk_id`, `instrument_pk_id`),
    KEY `FK_instrument_TO_purification_config_instrument` (`instrument_pk_id`),
    CONSTRAINT `FK_instrument_TO_purification_config_instrument` FOREIGN KEY (`instrument_pk_id`) REFERENCES `instrument` (`instrument_pk_id`),
    CONSTRAINT `FK_purification_config_TO_purification_config_instrument` FOREIGN KEY (`purification_config_pk_id`) REFERENCES `purification_config` (`purification_config_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purification_config_instrument`
--

LOCK TABLES `purification_config_instrument` WRITE;
/*!40000 ALTER TABLE `purification_config_instrument`
    DISABLE KEYS */;
INSERT IGNORE INTO `purification_config_instrument` (`purification_config_pk_id`, `instrument_pk_id`)
VALUES (1000, 1000),
       (1005, 1005),
       (1111, 1111),
       (1222, 1222),
       (1002, 28475412),
       (1001, 44793856),
       (1001, 62914561);
/*!40000 ALTER TABLE `purification_config_instrument`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purity_column_header`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `purity_column_header` (
  `column_pk_id` bigint(20) NOT NULL,
  `name` varchar(200) NOT NULL,
  `property` varchar(200) DEFAULT NULL,
  `value_type` varchar(200) DEFAULT NULL,
  `value_unit` varchar(200) DEFAULT NULL,
  `created_by` varchar(200) NOT NULL,
  `created_date` datetime NOT NULL,
  `column_order` INT(10) NOT NULL,
  `constant_value` DECIMAL(30,10) DEFAULT NULL,
  PRIMARY KEY (`column_pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


LOCK TABLES `purity_column_header` WRITE;
/*!40000 ALTER TABLE `purity_column_header`
    DISABLE KEYS */;
INSERT IGNORE INTO `canano`.`purity_column_header` (
`column_pk_id`,
 `name`, `value_type`,
 `value_unit`,
 `created_by`,
 `created_date`,
 `column_order`)
 VALUES (2000, 'Purity datum 1', 'purity', '%', 'canano_curator', '2020-07-20 16:08:15',0),
 (2010, 'Synthesis condition 1', 'observed', 'g', 'canano_curator', '2020-07-20 16:08:15',1),
 (1010, 'datum test', 'observed', 'mg', 'canano_curator', '2020-07-20 16:08:15',0),
 (2030, 'datum test 2', 'observed', 'mg', 'canano_curator', '2020-07-20 16:08:15',0),
 (2040,'Purity datum 2','purity','%','canano_curator','2021-02-05 15:50:00',0),
 (2050,'Purity condition 2-1','mean','%','canano_curator','2021-02-05 15:50:00',1),
 (2060,'Purity condition 2-2','standard deviation','%','canano_curator','2021-02-05 15:50:00',2);
/*!40000 ALTER TABLE `purity_column_header`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purity_datum_condition`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `purity_datum_condition`
(
    `purity_datum_pk_id`     bigint(20)   NOT NULL COMMENT 'purity_datum_pk_id',
    `condition_pk_id` bigint(20)   NOT NULL COMMENT 'condition_pk_id',
    `row_number` int NOT NULL COMMENT 'row number',
    `purity_pk_id`  bigint(20)  NOT NULL,
    `name`            varchar(200) NOT NULL,
    `property`        varchar(200) DEFAULT NULL,
    `value`           varchar(200) NOT NULL,
    `value_unit`      varchar(200) DEFAULT NULL,
    `value_type`      varchar(200) DEFAULT NULL,
    `created_by`      varchar(200) NOT NULL,
    `created_date`    datetime     NOT NULL,
    `numberMod`          varchar(20)  DEFAULT '=' COMMENT 'numberMod',
    `type`      varchar(20) DEFAULT NULL,
    `column_pk_id`		 bigint(20)	NOT NULL,
    PRIMARY KEY (`condition_pk_id`),
    KEY `FK_column_TO_purity_datum_condition`(`column_pk_id`),
 --   CONSTRAINT `FK_purity_datum_TO_purity_datum_condition` FOREIGN KEY (`purity_datum_pk_id`) REFERENCES `purity_datum` (`purity_datum_pk_id`),
    CONSTRAINT `FK_column_TO_purity_condition` FOREIGN KEY (`column_pk_id`) REFERENCES `purity_column_header` (`column_pk_id`),
    CONSTRAINT `FK_purity_TO_pur_datum_condition` FOREIGN KEY (`purity_pk_id`) REFERENCES `synthesis_purity` (`purity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purity_datum_condition`
--

LOCK TABLES `purity_datum_condition` WRITE;
/*!40000 ALTER TABLE `purity_datum_condition`
    DISABLE KEYS */;
INSERT IGNORE INTO `purity_datum_condition` (`purity_datum_pk_id`, `condition_pk_id`,`row_number`,`purity_pk_id`, `name`, `property`, `value`,
                                             `value_unit`, `value_type`, `created_by`, `created_date`,`type`,`column_pk_id`)
VALUES (1000,1000, 1,1000,'Synthesis condition 1', NULL, '42', 'g', 'observed', 'canano_user', '2019-12-06 12:15:00','condition',2010),
       (1010,1100,2,1000, 'Synthesis condition 2', NULL, '43', 'g', 'observed', 'canano_user', '2019-12-06 12:17:00','condition',2010),
       (1020,1010,1,1000,'Purity datum 1',NULL, '55', '%',NULL,'canano_user', '2019-12-06 12:15:00','datum',2000),
       (1030,1020, 2,1000,'Purity datum 2', NULL, '57.1','%',NULL,'canano_curator', '2019-12-06 12:15:00','datum',2000),
       (1111,1111, 2,1111,'datum_test', NULL, '84', 'mg', 'observed', 'canano_curator', '2019-12-06 12:15:00','condition',2030),
       (1111,1120,1,1111,'Purity datum',NULL,'123.00','%',NULL,'canano_curator', '2019-12-06 12:15:00','datum',1010),
       (2222,1130,1,1100,'datum test1',NULL,'98','%',NULL,'canano_curator','2021-02-05 14:50:00','datum',2040),
       (2222,1140,1,1100,'condition 1-1',NULL,'98','%',NULL,'canano_curator','2021-02-05 14:50:00','condition',2050),
       (2222,1150,1,1100,'condition 1-2',NULL,'0.5','%',NULL,'canano_curator','2021-02-05 14:50:00','condition',2060),
       (2222,1160,2,1100,'datum test2',NULL,'97','%',NULL,'canano_curator','2021-02-05 15:10:00','datum',2040),
       (2222,1170,2,1100,'condition 2-1',NULL,'96.5','%',NULL,'canano_curator','2021-02-05 15:10:00','condition',2050),
       (2222,1180,2,1100,'condition 2-2',NULL,'0.7','%',NULL,'canano_curator','2021-02-05 15:10:00','condition',2060),
       (2222,1190,3,1100,'datum test3',NULL,'98','%',NULL,'canano_curator','2021-02-05 15:15:00','datum',2040),
       (2222,1200,3,1100,'condition 3-1',NULL,'98.4','%',NULL,'canano_curator','2021-02-05 15:15:00','condition',2050),
       (2222,1210,3,1100,'condition 3-2',NULL,'0.6','%',NULL,'canano_curator','2021-02-05 15:15:00','condition',2060);

/*!40000 ALTER TABLE `purity_datum_condition`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purity_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `purity_file`
(
    `purity_pk_id` bigint(20) NOT NULL COMMENT 'purity_pk_id',
    `file_pk_id`   bigint(20)  NOT NULL COMMENT 'file_pk_id',
    PRIMARY KEY (`purity_pk_id`, `file_pk_id`),
    KEY `FK_file_TO_purity_file` (`file_pk_id`),
    CONSTRAINT `FK_file_TO_purity_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_purity_TO_purity_file` FOREIGN KEY (`purity_pk_id`) REFERENCES `synthesis_purity` (`purity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purity_file`
--

LOCK TABLES `purity_file` WRITE;
/*!40000 ALTER TABLE `purity_file`
    DISABLE KEYS */;
INSERT IGNORE INTO `purity_file` (`purity_pk_id`, `file_pk_id`)
VALUES (1002, 1333);
/*!40000 ALTER TABLE `purity_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sample`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `sample`
(
    `sample_pk_id`          bigint(20)   NOT NULL,
    `sample_name`           varchar(200) NOT NULL,
    `created_date`          datetime     NOT NULL,
    `created_by`            varchar(200) NOT NULL,
    `primary_contact_pk_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`sample_pk_id`),
    UNIQUE KEY `sample_name` (`sample_name`),
    UNIQUE KEY `sample_pk_id` (`sample_pk_id`),
    KEY `primary_contact_pk_id` (`primary_contact_pk_id`),
    CONSTRAINT `FK_sample_point_of_contact` FOREIGN KEY (`primary_contact_pk_id`) REFERENCES `point_of_contact` (`poc_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sample`
--

LOCK TABLES `sample` WRITE;
/*!40000 ALTER TABLE `sample`
    DISABLE KEYS */;
INSERT IGNORE INTO `sample` (`sample_pk_id`, `sample_name`, `created_date`, `created_by`, `primary_contact_pk_id`)
VALUES (1000, 'Synthesis sample', '2019-12-06 12:15:00', 'canano_curator', 1000),
       (1005, 'Synthesis Demo Sample', '2019-12-06 12:15:00', 'canano_curator', 1005),
       (65634304, 'Synthesis Sample Jan82020 1', '2020-01-08 19:07:58', 'canano_curator', 65568768),
       (65634305, 'Synthesis Sample Jan82020 2', '2020-01-08 19:09:25', 'canano_curator', 65568768),
       (65634306, 'Synthesis Sample Jan82020 3', '2020-01-08 19:10:05', 'canano_curator', 65568768);
/*!40000 ALTER TABLE `sample`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sample_other_poc`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `sample_other_poc`
(
    `sample_pk_id` bigint(20) NOT NULL,
    `poc_pk_id`    bigint(20) NOT NULL,
    PRIMARY KEY (`sample_pk_id`, `poc_pk_id`),
    KEY `sample_pk_id` (`sample_pk_id`),
    KEY `poc_pk_id` (`poc_pk_id`),
    CONSTRAINT `FK_sample_other_poc_point_of_contact` FOREIGN KEY (`poc_pk_id`) REFERENCES `point_of_contact` (`poc_pk_id`),
    CONSTRAINT `FK_sample_other_poc_sample` FOREIGN KEY (`sample_pk_id`) REFERENCES `sample` (`sample_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sample_other_poc`
--

LOCK TABLES `sample_other_poc` WRITE;
/*!40000 ALTER TABLE `sample_other_poc`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sample_other_poc`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sample_publication`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `sample_publication`
(
    `sample_pk_id`      bigint(20) NOT NULL,
    `publication_pk_id` bigint(20) NOT NULL,
    KEY `particle_sample_pk_id` (`sample_pk_id`),
    KEY `file_pk_id` (`publication_pk_id`),
    CONSTRAINT `FK_sample_publication_publication` FOREIGN KEY (`publication_pk_id`) REFERENCES `publication` (`publication_pk_id`),
    CONSTRAINT `FK_sample_publication_sample` FOREIGN KEY (`sample_pk_id`) REFERENCES `sample` (`sample_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sample_publication`
--

LOCK TABLES `sample_publication` WRITE;
/*!40000 ALTER TABLE `sample_publication`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sample_publication`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sfe_inherent_function`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `sfe_inherent_function`
(
    `sfe_inherent_function_pk_id`               bigint(20) NOT NULL COMMENT 'sfe_inherent_function_pk_id',
    `synthesis_functionalization_element_pk_id` bigint(20) NOT NULL COMMENT 'synthesis_functionalization_element_pk_id',
    `type`                                      varchar(200) DEFAULT NULL COMMENT 'type',
    `description`                               text COMMENT 'description',
    PRIMARY KEY (`sfe_inherent_function_pk_id`),
    KEY `FK_synthesis_material_element_TO_sfe_inherent_function` (`synthesis_functionalization_element_pk_id`),
    CONSTRAINT `sfe_inherent_function_ibfk_1` FOREIGN KEY (`synthesis_functionalization_element_pk_id`) REFERENCES `synthesis_functionalization_element` (`synthesis_functionalization_element_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sfe_inherent_function`
--

LOCK TABLES `sfe_inherent_function` WRITE;
/*!40000 ALTER TABLE `sfe_inherent_function`
    DISABLE KEYS */;
INSERT IGNORE INTO `sfe_inherent_function` (`sfe_inherent_function_pk_id`, `synthesis_functionalization_element_pk_id`,
                                            `type`, `description`)
VALUES (1000, 1000, 'biocompatibility', 'Synthesis Functionalization Material Inherent Function 1'),
       (1001, 1000, 'activation', 'Inherent Function 2'),
       (1005, 1005, 'biocompatibility', ''),
       (1111, 1111, 'biocompatibility', 'Synthesis Functionalization Material Inherent Function 1'),
       (1222, 1222, 'biocompatibility', '');
/*!40000 ALTER TABLE `sfe_inherent_function`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shape`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `shape`
(
    `shape_pk_id`        bigint(20)   NOT NULL,
    `max_dimension`      decimal(22, 3) DEFAULT NULL,
    `min_dimension`      decimal(22, 3) DEFAULT NULL,
    `type`               varchar(200) NOT NULL,
    `min_dimension_unit` varchar(200)   DEFAULT NULL,
    `max_dimension_unit` varchar(200)   DEFAULT NULL,
    `aspect_ratio`       decimal(22, 3) DEFAULT NULL,
    PRIMARY KEY (`shape_pk_id`),
    KEY `shape_pk_id` (`shape_pk_id`),
    CONSTRAINT `FK_shape_characterization` FOREIGN KEY (`shape_pk_id`) REFERENCES `characterization` (`characterization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shape`
--

LOCK TABLES `shape` WRITE;
/*!40000 ALTER TABLE `shape`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `shape`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `small_molecule`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `small_molecule`
(
    `small_molecule_pk_id` bigint(20) NOT NULL,
    `alternate_name`       varchar(200) DEFAULT NULL,
    PRIMARY KEY (`small_molecule_pk_id`),
    KEY `small_molecule_pk_id` (`small_molecule_pk_id`),
    CONSTRAINT `FK_small_molecule_functionalizing_entity` FOREIGN KEY (`small_molecule_pk_id`) REFERENCES `functionalizing_entity` (`functionalizing_entity_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `small_molecule`
--

LOCK TABLES `small_molecule` WRITE;
/*!40000 ALTER TABLE `small_molecule`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `small_molecule`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sme_inherent_function`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `sme_inherent_function`
(
    `sme_inherent_function_pk_id`      bigint(20) NOT NULL COMMENT 'sme_inherent_function_pk_id',
    `synthesis_material_element_pk_id` bigint(20) NOT NULL COMMENT 'synthesis_material_element_pk_id',
    `type`                             varchar(200) DEFAULT NULL COMMENT 'type',
    `description`                      text COMMENT 'description',
    PRIMARY KEY (`sme_inherent_function_pk_id`),
    KEY `FK_synthesis_material_element_TO_sme_inherent_function` (`synthesis_material_element_pk_id`),
    CONSTRAINT `FK_synthesis_material_element_TO_sme_inherent_function` FOREIGN KEY (`synthesis_material_element_pk_id`) REFERENCES `synthesis_material_element` (`synthesis_material_element_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sme_inherent_function`
--

LOCK TABLES `sme_inherent_function` WRITE;
/*!40000 ALTER TABLE `sme_inherent_function`
    DISABLE KEYS */;
INSERT IGNORE INTO `sme_inherent_function` (`sme_inherent_function_pk_id`, `synthesis_material_element_pk_id`, `type`,
                                            `description`)
VALUES (1000, 1000, 'targeting function', 'SME test function'),
       (1001, 1000, 'Function Type', 'SME test function 2'),
       (1005, 1053, 'Function Type', 'SME Function description'),
       (1006, 1053, 'Function Type 2', 'SME Function description 2');
/*!40000 ALTER TABLE `sme_inherent_function`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solubility`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `solubility`
(
    `solubility_pk_id`            bigint(20) NOT NULL,
    `solvent`                     varchar(200)   DEFAULT NULL,
    `critical_concentration`      decimal(22, 3) DEFAULT NULL,
    `critical_concentration_unit` varchar(200)   DEFAULT NULL,
    `is_soluble`                  tinyint(4)     DEFAULT NULL,
    PRIMARY KEY (`solubility_pk_id`),
    KEY `solubility_pk_id` (`solubility_pk_id`),
    CONSTRAINT `FK_solubility_characterization` FOREIGN KEY (`solubility_pk_id`) REFERENCES `characterization` (`characterization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solubility`
--

LOCK TABLES `solubility` WRITE;
/*!40000 ALTER TABLE `solubility`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `solubility`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `supplier`
(
    `supplier_pk_id` bigint(20)   NOT NULL COMMENT 'supplier_pk_id',
    `supplier_name`  varchar(200) NOT NULL COMMENT 'supplier_name',
    `lot`            varchar(50) DEFAULT NULL COMMENT 'lot',
    PRIMARY KEY (`supplier_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier`
    DISABLE KEYS */;
INSERT IGNORE INTO `supplier` (`supplier_pk_id`, `supplier_name`, `lot`)
VALUES (1000, 'Synthesis supplier 1000', 'ABC123xyz'),
       (1005, 'Synthesis supplier 1005', 'ABC456xyz'),
       (1111, 'Synthesis supplier 1111', 'ABC789xyz');
/*!40000 ALTER TABLE `supplier`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis`
(
    `synthesis_pk_id` bigint(20) NOT NULL COMMENT 'synthesis_pk_id',
    `sample_pk_id`    bigint(20) NOT NULL COMMENT 'sample_pk_id',
    PRIMARY KEY (`synthesis_pk_id`),
    KEY `FK_sample_TO_synthesis` (`sample_pk_id`),
    CONSTRAINT `FK_sample_TO_synthesis` FOREIGN KEY (`sample_pk_id`) REFERENCES `sample` (`sample_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis`
--

LOCK TABLES `synthesis` WRITE;
/*!40000 ALTER TABLE `synthesis`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis` (`synthesis_pk_id`, `sample_pk_id`)
VALUES (1000, 1000),
       (1005, 1005),
       (1111, 65634304),
       (1222, 65634306);
/*!40000 ALTER TABLE `synthesis`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_functionalization`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_functionalization`
(
    `synthesis_functionalization_pk_id` bigint(20)   NOT NULL COMMENT 'synthesis_functionalization_pk_id',
    `synthesis_pk_id`                   bigint(20)   DEFAULT NULL COMMENT 'synthesis_pk_id',
    `protocol_pk_id`                    bigint(20)   DEFAULT NULL COMMENT 'protocol_pk_id',
    `description`                       text COMMENT 'description',
    `created_date`                      datetime     NOT NULL COMMENT 'created_date',
    `created_by`                        varchar(200) NOT NULL COMMENT 'created_by',
    PRIMARY KEY (`synthesis_functionalization_pk_id`),
    KEY `FK_synthesis_TO_synthesis_functionalization` (`synthesis_pk_id`),
    KEY `FK_protocol_TO_synthesis_functionalization` (`protocol_pk_id`),
    CONSTRAINT `FK_protocol_TO_synthesis_functionalization` FOREIGN KEY (`protocol_pk_id`) REFERENCES `protocol` (`protocol_pk_id`),
    CONSTRAINT `FK_synthesis_TO_synthesis_functionalization` FOREIGN KEY (`synthesis_pk_id`) REFERENCES `synthesis` (`synthesis_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_functionalization`
--

LOCK TABLES `synthesis_functionalization` WRITE;
/*!40000 ALTER TABLE `synthesis_functionalization`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_functionalization` (`synthesis_functionalization_pk_id`, `synthesis_pk_id`,
                                                  `protocol_pk_id`, `description`, `created_date`, `created_by`)
VALUES (1000, 1000, 66256896, 'Synthesis Functionalization Test 1', '2019-12-06 12:15:00', 'canano_curator'),
       (1005, 1005, 66256896,
        'In order to produce PEG-capped AuNPs, various concentrations (3.6, 8.4, 16.8 and 25.2 ug per ml of as synthesized AuNP suspension) of 5,000 Mw PEG were added to the ‘as synthesized’ AuNP solutions at room temperature. After the required amount of PEG was added, the solution was stirred at room temperature for 2 h to allow for complete exchange of the citrate molecules with PEG.',
        '2019-08-29 00:00:00', 'canano_curator'),
       (1111, 1111, 66256896, 'Synthesis Functionalization Test 1', '2019-12-06 12:15:00', 'canano_curator'),
       (1222, 1222, NULL,
        'In order to produce PEG-capped AuNPs, various concentrations (3.6, 8.4, 16.8 and 25.2 ug per ml of as synthesized AuNP suspension) of 5,000 Mw PEG were added to the ‘as synthesized’ AuNP solutions at room temperature. After the required amount of PEG was added, the solution was stirred at room temperature for 2 h to allow for complete exchange of the citrate molecules with PEG.',
        '2019-08-29 00:00:00', 'canano_curator');
/*!40000 ALTER TABLE `synthesis_functionalization`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_functionalization_element`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_functionalization_element`
(
    `synthesis_functionalization_element_pk_id` bigint(20)   NOT NULL COMMENT 'synthesis_functionalization_element_pk_id',
    `synthesis_functionalization_pk_id`         bigint(20)   NOT NULL COMMENT 'synthesis_functionalization_pk_id',
    `molecular_formula`                         varchar(2000)  DEFAULT NULL COMMENT 'molecular_formula',
    `molecular_formula_type`                    varchar(200)   DEFAULT NULL COMMENT 'molecular_formula_type',
    `description`                               text COMMENT 'description',
    `created_by`                                varchar(200) NOT NULL COMMENT 'created_by',
    `created_date`                              datetime     NOT NULL COMMENT 'created_date',
    `chemical_name`                             varchar(200)   DEFAULT NULL COMMENT 'chemical_name',
    `value`                                     decimal(22, 3) DEFAULT NULL COMMENT 'value',
    `value_unit`                                varchar(200)   DEFAULT NULL COMMENT 'value_unit',
    `pub_chem_datasource_name`                  varchar(200)   DEFAULT NULL COMMENT 'pub_chem_datasource_name',
    `pub_chem_id`                               bigint(20)     DEFAULT NULL COMMENT 'pub_chem_id',
    `type`                                      varchar(200)   DEFAULT NULL COMMENT 'type',
    `activation_method`                         varchar(200)   DEFAULT NULL,
    `activation_effect`                         text,
    PRIMARY KEY (`synthesis_functionalization_element_pk_id`),
    CONSTRAINT `FK_synthesis_funct_TO_synthesis_functionalization_element` FOREIGN KEY (`synthesis_functionalization_pk_id`)
        REFERENCES `synthesis_functionalization` (`synthesis_functionalization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_functionalization_element`
--

LOCK TABLES `synthesis_functionalization_element` WRITE;
/*!40000 ALTER TABLE `synthesis_functionalization_element`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_functionalization_element` (`synthesis_functionalization_element_pk_id`,
                                                          `synthesis_functionalization_pk_id`, `molecular_formula`,
                                                          `molecular_formula_type`, `description`, `created_by`,
                                                          `created_date`, `chemical_name`, `value`, `value_unit`,
                                                          `pub_chem_datasource_name`, `pub_chem_id`, `type`,
                                                          `activation_method`, `activation_effect`)
VALUES (1000, 1000, 'kK-N12-C4-L', 'Hill', 'Synthesis Functionalization Material Element 1', 'canano_curator',
        '2019-12-06 12:15:00', 'SynFuncMat Element1', 84.000, 'pg', NULL, NULL, 'material', 'Lightning', 'Animation'),
       (1001, 1000, 'AbCdEfG', 'Hill', 'Synthesis Functionalization Material Element 2', 'canano_curator',
        '2019-12-06 12:15:00', 'McNuggets', 10.000, 'piece', NULL, NULL, 'material', 'Does not require activation',
        NULL),
       (1005, 1005, 'CH3(OCH2CH2)nOH', 'Hill',
        'Added to the as-synthesized AuNP solution to a concentration of 3.6 ug/mL', 'canano_curator',
        '2019-08-28 00:00:00', 'polyethylene glycol, 5000 MW', 3.600, 'ug', 'Compound', 24887753, 'material',
        'Does not require activation', NULL),
       (1111, 1111, 'kK-N12-C4-L', 'Hill', 'Synthesis Functionalization Material Element 1', 'canano_curator',
        '2019-12-06 12:15:00', 'SynFuncMat Element1', 84.000, 'pg', NULL, NULL, 'material', NULL, NULL),
       (1222, 1222, 'CH3(OCH2CH2)nOH', 'Hill',
        'Added to the as-synthesized AuNP solution to a concentration of 3.6 ug/mL', 'canano_curator',
        '2019-08-28 00:00:00', 'polyethylene glycol, 5000 MW', 3.600, 'ug', 'Compound', 24887753, 'material', NULL,
        NULL);
/*!40000 ALTER TABLE `synthesis_functionalization_element`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_functionalization_element_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_functionalization_element_file`
(
    `synthesis_functionalization_element_pk_id` bigint(20) NOT NULL COMMENT 'synthesis_functionalization_element_file',
    `file_pk_id`                                bigint(20) NOT NULL COMMENT 'file_pk_id',
    PRIMARY KEY (`synthesis_functionalization_element_pk_id`, `file_pk_id`),
    KEY `FK_file_TO_synthesis_functionalization_element_file` (`file_pk_id`),
    CONSTRAINT `FK_file_TO_synthesis_functionalization_element_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_synthesis_func_element_TO_sfe_element_file` FOREIGN KEY (`synthesis_functionalization_element_pk_id`) REFERENCES `synthesis_functionalization_element` (`synthesis_functionalization_element_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_functionalization_element_file`
--

LOCK TABLES `synthesis_functionalization_element_file` WRITE;
/*!40000 ALTER TABLE `synthesis_functionalization_element_file`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_functionalization_element_file` (`synthesis_functionalization_element_pk_id`, `file_pk_id`)
VALUES (1005, 1005);
/*!40000 ALTER TABLE `synthesis_functionalization_element_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_functionalization_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_functionalization_file`
(
    `synthesis_functionalization_pk_id` bigint(20) NOT NULL COMMENT 'synthesis_material_pk_id',
    `file_pk_id`                        bigint(20) NOT NULL COMMENT 'file_pk_id',
    PRIMARY KEY (`synthesis_functionalization_pk_id`, `file_pk_id`),
    KEY `FK_file_TO_synthesis_file` (`file_pk_id`),
    CONSTRAINT `FK_file_TO_synthesis_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_syn_func_TO_syn_func_file` FOREIGN KEY (`synthesis_functionalization_pk_id`) REFERENCES `synthesis_functionalization` (`synthesis_functionalization_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_functionalization_file`
--

LOCK TABLES `synthesis_functionalization_file` WRITE;
/*!40000 ALTER TABLE `synthesis_functionalization_file`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `synthesis_functionalization_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_material`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_material`
(
    `synthesis_material_pk_id` bigint(20)   NOT NULL COMMENT 'synthesis_material_pk_id',
    `synthesis_pk_id`          bigint(20)   NOT NULL COMMENT 'synthesis_pk_id',
    `protocol_pk_id`           bigint(20) DEFAULT NULL COMMENT 'protocol_pk_id',
    `description`              text         NOT NULL COMMENT 'description',
    `created_date`             datetime     NOT NULL COMMENT 'created_date',
    `created_by`               varchar(200) NOT NULL COMMENT 'created_by',
    PRIMARY KEY (`synthesis_material_pk_id`),
    KEY `FK_synthesis_TO_synthesis_material` (`synthesis_pk_id`),
    KEY `FK_protocol_TO_synthesis_material` (`protocol_pk_id`),
    CONSTRAINT `FK_protocol_TO_synthesis_material` FOREIGN KEY (`protocol_pk_id`) REFERENCES `protocol` (`protocol_pk_id`),
    CONSTRAINT `FK_synthesis_TO_synthesis_material` FOREIGN KEY (`synthesis_pk_id`) REFERENCES `synthesis` (`synthesis_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_material`
--

LOCK TABLES `synthesis_material` WRITE;
/*!40000 ALTER TABLE `synthesis_material`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_material` (`synthesis_material_pk_id`, `synthesis_pk_id`, `protocol_pk_id`, `description`,
                                         `created_date`, `created_by`)
VALUES (1000, 1000, 66256896, 'Synthesis test sample 1', '2019-12-06 12:15:00', 'canano_curator'),
       (1005, 1005, NULL,
        'Chloroauric acid solution (200 ml of 0.01wt.%) was heated to a rolling boil and refluxed in a 500-ml-round-bottom flask using a temperature-controlled hot plate with continuous stirring [4]. A 4.5-ml aliquot of 1 wt.% sodium citrate solution was then added to the boiling chloroauric acid solution, and the heating was continued under reflux for 15 min to enable complete reaction. The solution was then allowed to cool to room temperature with continuous stirring yielding citrate-capped AuNPs.',
        '2019-08-28 00:00:00', 'canano_curator'),
       (1111, 1111, 66256896, 'Synthesis test sample 1', '2019-12-06 12:15:00', 'canano_curator'),
       (1222, 1222, NULL,
        'Chloroauric acid solution (200 ml of 0.01wt.%) was heated to a rolling boil and refluxed in a 500-ml-round-bottom flask using a temperature-controlled hot plate with continuous stirring [4]. A 4.5-ml aliquot of 1 wt.% sodium citrate solution was then added to the boiling chloroauric acid solution, and the heating was continued under reflux for 15 min to enable complete reaction. The solution was then allowed to cool to room temperature with continuous stirring yielding citrate-capped AuNPs.',
        '2019-08-28 00:00:00', 'canano_curator');
/*!40000 ALTER TABLE `synthesis_material`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_material_element`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_material_element`
(
    `synthesis_material_element_pk_id` bigint(20)   NOT NULL COMMENT 'synthesis_material_element_pk_id',
    `synthesis_material_pk_id`         bigint(20)   NOT NULL COMMENT 'synthesis_material_pk_id',
    `molecular_formula`                varchar(2000)  DEFAULT NULL COMMENT 'molecular_formula',
    `molecular_formula_type`           varchar(200)   DEFAULT NULL COMMENT 'molecular_formula_type',
    `description`                      text COMMENT 'description',
    `created_by`                       varchar(200) NOT NULL COMMENT 'created_by',
    `created_date`                     datetime     NOT NULL COMMENT 'created_date',
    `chemical_name`                    varchar(200) NOT NULL COMMENT 'chemical_name',
    `value`                            decimal(22, 3) DEFAULT NULL COMMENT 'value',
    `value_unit`                       varchar(200)   DEFAULT NULL COMMENT 'value_unit',
    `pub_chem_datasource_name`         varchar(200)   DEFAULT NULL COMMENT 'pub_chem_datasource_name',
    `pub_chem_id`                      bigint(20)     DEFAULT NULL COMMENT 'pub_chem_id',
    `supplier_pk_id`                   bigint(20)     DEFAULT NULL COMMENT 'supplier_pk_id',
    `type`                             varchar(200) NOT NULL,
    PRIMARY KEY (`synthesis_material_element_pk_id`),
    KEY `FK_synthesis_material_TO_synthesis_material_element` (`synthesis_material_pk_id`),
    KEY `FK_synthesis_material_element_TO_supplier` (`supplier_pk_id`),
    CONSTRAINT `FK_synthesis_material_TO_synthesis_material_element` FOREIGN KEY (`synthesis_material_pk_id`) REFERENCES `synthesis_material` (`synthesis_material_pk_id`),
    CONSTRAINT `FK_synthesis_material_element_TO_supplier` FOREIGN KEY (`supplier_pk_id`) REFERENCES `supplier` (`supplier_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_material_element`
--

LOCK TABLES `synthesis_material_element` WRITE;
/*!40000 ALTER TABLE `synthesis_material_element`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_material_element` (`synthesis_material_element_pk_id`, `synthesis_material_pk_id`,
                                                 `molecular_formula`, `molecular_formula_type`, `description`,
                                                 `created_by`, `created_date`, `chemical_name`, `value`, `value_unit`,
                                                 `pub_chem_datasource_name`, `pub_chem_id`, `supplier_pk_id`, `type`)
VALUES (1000, 1000, 'AA-2x-zZ', 'Hill', 'Synthesis Material Element 1', 'canano_curator', '2019-12-06 12:15:00',
        'Synthesis Chemical 1', 12.000, 'mg', 'Compound', 6224, 1000, 'reagent'),
       (1051, 1005, 'HAuCl4 . 3H2O', 'Hill', '200 mL of 0.01 wt% gold precursor', 'canano_curator',
        '2019-08-28 00:00:00', 'chloroauric acid', 0.010, '%wt', 'Compound', 44134746, 1005, 'composing element'),
       (1052, 1222, 'C6H5Na3O7', 'Hill', '4.5 mL of 1 wt% sodium citrate solution', 'canano_curator',
        '2019-08-28 00:00:00', 'sodium citrate', 1.000, '%wt', 'Compound', 6224, 1111, 'reagent'),
       (1053, 1222, 'H2O', 'Hill', 'Solutions of chloroauric acid and sodium citrate were created with deionized water',
        'canano_curator', '2019-08-28 00:00:00', 'deionized water', 204.500, 'mL', 'substance', 962, NULL, 'solvent');
/*!40000 ALTER TABLE `synthesis_material_element`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_material_element_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_material_element_file`
(
    `synthesis_material_element_pk_id` bigint(20) NOT NULL COMMENT 'synthesis_material_element_pk_id',
    `file_pk_id`                       bigint(20) NOT NULL COMMENT 'file_pk_id',
    PRIMARY KEY (`synthesis_material_element_pk_id`, `file_pk_id`),
    KEY `FK_file_TO_synthesis_material_element_file` (`file_pk_id`),
    CONSTRAINT `FK_file_TO_synthesis_material_element_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_synthesis_material_element_TO_synthesis_material_element_file` FOREIGN KEY (`synthesis_material_element_pk_id`) REFERENCES `synthesis_material_element` (`synthesis_material_element_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_material_element_file`
--

LOCK TABLES `synthesis_material_element_file` WRITE;
/*!40000 ALTER TABLE `synthesis_material_element_file`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_material_element_file` (`synthesis_material_element_pk_id`, `file_pk_id`)
VALUES (1000, 1555);
/*!40000 ALTER TABLE `synthesis_material_element_file`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_material_file`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_material_file`
(
    `synthesis_material_pk_id` bigint(20) NOT NULL COMMENT 'synthesis_material_pk_id',
    `file_pk_id`               bigint(20) NOT NULL COMMENT 'file_pk_id',
    PRIMARY KEY (`synthesis_material_pk_id`, `file_pk_id`),
    KEY `FK_file_TO_synthesis_material_file` (`file_pk_id`),
    CONSTRAINT `FK_file_TO_synthesis_material_file` FOREIGN KEY (`file_pk_id`) REFERENCES `file` (`file_pk_id`),
    CONSTRAINT `FK_synthesis_material_TO_synthesis_material_file` FOREIGN KEY (`synthesis_material_pk_id`) REFERENCES `synthesis_material` (`synthesis_material_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_material_file`
--

LOCK TABLES `synthesis_material_file` WRITE;
/*!40000 ALTER TABLE `synthesis_material_file`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_material_file` (`synthesis_material_pk_id`, `file_pk_id`)
VALUES (1000, 1000),
       (1005, 1005);
/*!40000 ALTER TABLE `synthesis_material_file`
    ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `synthesis_purification`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_purification`
(
    `synthesis_purification_pk_id` bigint(20)   NOT NULL COMMENT 'synthesis_purification_pk_id',
    `synthesis_pk_id`              bigint(20)     NOT NULL COMMENT 'synthesis_pk_id',
    `protocol_pk_id`               bigint(20)     DEFAULT NULL COMMENT 'protocol_pk_id',
    `type`                         varchar(200)   DEFAULT NULL COMMENT 'type',
    `method_name`                  varchar(200)   DEFAULT NULL COMMENT 'method_name',
    `design_method_description`    text COMMENT 'design_method_description',
    `created_by`                   varchar(200) NOT NULL COMMENT 'created_by',
    `created_date`                 datetime     NOT NULL COMMENT 'created_date',
    `yield`                        decimal(30, 3) DEFAULT NULL COMMENT 'yield',
    `analysis`                     text,
    PRIMARY KEY (`synthesis_purification_pk_id`),
    KEY `FK_synthesis_TO_synthesis_purification` (`synthesis_pk_id`),
    KEY `FK_protocol_TO_synthesis_purification` (`protocol_pk_id`),
    CONSTRAINT `FK_protocol_TO_synthesis_purification` FOREIGN KEY (`protocol_pk_id`) REFERENCES `protocol` (`protocol_pk_id`),
    CONSTRAINT `FK_synthesis_TO_synthesis_purification` FOREIGN KEY (`synthesis_pk_id`) REFERENCES `synthesis` (`synthesis_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_purification`
--

LOCK TABLES `synthesis_purification` WRITE;
/*!40000 ALTER TABLE `synthesis_purification`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_purification` (`synthesis_purification_pk_id`, `synthesis_pk_id`, `protocol_pk_id`,
                                             `type`, `method_name`, `design_method_description`, `created_by`,
                                             `created_date`, `yield`, `analysis`)
VALUES (1000, 1000, 66945024, 'Interim Purification', 'Synthesis Purification Method 1',
        'Test entry for synthesis purification', 'canano_user', '2019-12-06 12:15:00', 84.700, 'Analysis for synth 1'),
       (1001, 1000, 66945024, 'Final Purification', 'Synthesis Purification Method 2',
        'Test entry for final purification', 'canano_user', '2019-12-06 12:15:00', 99.540,
        'Analysis for final purification'),
       (1005, 1005, 66945024, 'Interim Purification', 'Synthesis Purification Method 1',
        'The AuNP solutions were then centrifuged using a Contifuge 17RS, Heraeus SEPATECH at 10,000 rpm for 90 min in 10 ml batches [28]. Of the supernatant, 9.9 ml was then decanted, leaving the AuNP pellet at the bottom of the centrifuge tube. The volume was then made back up to 10 ml by adding 9.9 ml of DI water and agitated. This centrifugal washing process was repeated again to remove any unattached PEG or other reactants.',
        'canano_user', '2019-08-28 00:00:00', NULL, NULL),
       (1111, 1111, 66945026, 'Interim Purification', 'Synthesis Purification Method 1',
        'Test entry for synthesis purification', 'canano_user', '2019-12-06 12:15:00', 84.700, NULL),
       (1222, 1222, 66945025, 'Interim Purification', 'Synthesis Purification Method 1',
        'The AuNP solutions were then centrifuged using a Contifuge 17RS, Heraeus SEPATECH at 10,000 rpm for 90 min in 10 ml batches [28]. Of the supernatant, 9.9 ml was then decanted, leaving the AuNP pellet at the bottom of the centrifuge tube. The volume was then made back up to 10 ml by adding 9.9 ml of DI water and agitated. This centrifugal washing process was repeated again to remove any unattached PEG or other reactants.',
        'canano_user', '2019-08-28 00:00:00', NULL, NULL);
/*!40000 ALTER TABLE `synthesis_purification`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synthesis_purity`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `synthesis_purity`
(
    `purity_pk_id`                 bigint(200)  NOT NULL COMMENT 'purity_pk_id',
    `synthesis_purification_pk_id` bigint(20)   NOT NULL COMMENT 'synthesis_purification_pk_id',
    `created_by`                   varchar(200) NOT NULL COMMENT 'created_by',
    `created_date`                 datetime     NOT NULL COMMENT 'created_date',
    PRIMARY KEY (`purity_pk_id`),
    KEY `FK_synthesis_purity_to purification` (`synthesis_purification_pk_id`),
    CONSTRAINT `FK_synthesis_purity_to purification` FOREIGN KEY (`synthesis_purification_pk_id`) REFERENCES `synthesis_purification` (`synthesis_purification_pk_id`)


) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synthesis_purity`
--

LOCK TABLES `synthesis_purity` WRITE;
/*!40000 ALTER TABLE `synthesis_purity`
    DISABLE KEYS */;
INSERT IGNORE INTO `synthesis_purity` (`purity_pk_id`, `synthesis_purification_pk_id`, `created_by`, `created_date`)
VALUES (1000, 1000, 'canano_curator', '2019-12-06 12:15:00'),
       (1010, 1001, 'canano_curator', '2020-10-06 12:15:00'),
       (1005, 1005, 'canano_curator', '2019-08-28 00:00:00'),
       (1100, 1000, 'canano_curator', '2021-02-05 15:50:00'),
       (1111, 1111, 'canano_curator', '2019-12-06 12:15:00'),
       (1222, 1222, 'canano_curator', '2019-08-28 00:00:00');
/*!40000 ALTER TABLE `synthesis_purity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `target`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `target`
(
    `target_pk_id`             bigint(20)   NOT NULL,
    `discriminator`            varchar(200)  DEFAULT NULL,
    `name`                     varchar(200)  DEFAULT NULL,
    `description`              varchar(2000) DEFAULT NULL,
    `targeting_function_pk_id` bigint(20)    DEFAULT NULL,
    `antigen_species`          varchar(200)  DEFAULT NULL,
    `other_target_type`        varchar(200)  DEFAULT NULL,
    `created_by`               varchar(200) NOT NULL,
    `created_date`             datetime     NOT NULL,
    PRIMARY KEY (`target_pk_id`),
    KEY `targeting_function_pk_id` (`targeting_function_pk_id`),
    CONSTRAINT `FK_target_function` FOREIGN KEY (`targeting_function_pk_id`) REFERENCES `nano_function` (`function_pk_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `target`
--

LOCK TABLES `target` WRITE;
/*!40000 ALTER TABLE `target`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `target`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `technique`
--

/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8;
CREATE TABLE `technique`
(
    `technique_pk_id` bigint(20)   NOT NULL,
    `type`            varchar(200) NOT NULL,
    `abbreviation`    varchar(50) DEFAULT NULL,
    `created_date`    datetime     NOT NULL,
    `created_by`      varchar(200) NOT NULL,
    PRIMARY KEY (`technique_pk_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technique`
--

LOCK TABLES `technique` WRITE;
/*!40000 ALTER TABLE `technique`
    DISABLE KEYS */;
INSERT IGNORE INTO `technique` (`technique_pk_id`, `type`, `abbreviation`, `created_date`, `created_by`)
VALUES (1000, 'Interim purification technique', 'InP', '2019-12-06 12:15:00', 'safrant'),
       (1005, 'centrifugal filtration', NULL, '2018-08-28 00:00:00', 'safrant'),
       (1111, 'Interim purification technique', 'InP', '2019-12-06 12:15:00', 'canano_curator'),
       (1222, 'centrifugal filtration', NULL, '2018-08-28 00:00:00', 'canano_curator'),
       (65896448, 'polymerase chain reaction', 'PCR', '2020-01-08 19:18:01', 'canano_curator'),
       (66158592, 'colony counting', 'cc', '2020-01-09 23:03:07', 'canano_curator'),
       (66355200, 'coulter principle', 'coulter', '2020-01-10 13:26:14', 'canano_curator');
/*!40000 ALTER TABLE `technique`
    ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-03-03 16:57:46
