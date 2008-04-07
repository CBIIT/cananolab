-- MySQL dump 10.11
--
-- Host: localhost    Database: canano
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `common_lookup`
--

DROP TABLE IF EXISTS `common_lookup`;
CREATE TABLE `common_lookup` (
  `common_lookup_pk_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(200) NOT NULL,
  `attribute` varchar(200) NOT NULL,
  `value` varchar(200) NOT NULL,
  PRIMARY KEY  (`common_lookup_pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=234 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `common_lookup`
--

LOCK TABLES `common_lookup` WRITE;
/*!40000 ALTER TABLE `common_lookup` DISABLE KEYS */;
INSERT INTO `common_lookup` VALUES (1,'Attachment','bondType','covalent'),(2,'Attachment','bondType','electrostatic'),(3,'Attachment','bondType','hydrogen'),(4,'Attachment','bondType','ionic'),(5,'Attachment','bondType','van der Waals'),(6,'Report','category','associated file'),(7,'Report','category','report'),(8,'Cytotoxicity','cellDeathMethod','apoptosis'),(9,'Cytotoxicity','cellDeathMethod','necrosis'),(10,'Cytotoxicity','cellLine','human hepatocarinoma'),(11,'Cytotoxicity','cellLine','porcine renal tubule'),(12,'Emulsion','composingElementType','bulk phase'),(13,'Emulsion','composingElementType','dispersed phase'),(14,'Emulsion','composingElementType','emulsifier'),(15,'SampleContainer','concentrationUnit','g/ml'),(16,'SampleContainer','concentrationUnit','mg/ml'),(17,'SampleContainer','concentrationUnit','ug/ml'),(18,'SampleContainer','concentrationUnit','ug/ul'),(19,'CellViability','derivedDatumName','LC50'),(20,'MolecularWeight','derivedDatumName','molecular weight'),(21,'Size','derivedDatumName','PDI'),(22,'Size','derivedDatumName','peak1'),(23,'Size','derivedDatumName','RMS-size'),(24,'Size','derivedDatumName','Z-average'),(25,'Surface','derivedDatumName','charge'),(26,'Surface','derivedDatumName','surface area'),(27,'Surface','derivedDatumName','zeta potential'),(28,'Antibody','displayName','antibody'),(29,'Antigen','displayName','antigen'),(30,'Attachment','displayName','attachment'),(31,'Biopolymer','displayName','biopolymer'),(32,'BloodContact','displayName','Blood Contact'),(33,'CarbonNanotube','displayName','carbon nanotube'),(34,'Caspase3Activation','displayName','Caspase 3 Activation'),(35,'CellViability','displayName','Cell Viability'),(36,'CFU_GM','displayName','CFU GM'),(37,'Chemotaxis','displayName','Chemotaxis'),(38,'Coagulation','displayName','Coagulation'),(39,'ComplementActivation','displayName','Complement Activation'),(40,'CytokineInduction','displayName','Cytokine Induction'),(41,'Cytotoxicity','displayName','Cytotoxicity'),(42,'Dendrimer','displayName','dendrimer'),(43,'Emulsion','displayName','emulsion'),(44,'Encapsulation','displayName','encapsulation'),(45,'EnzymeInduction','displayName','Enzyme Induction'),(46,'Fullerene','displayName','fullerene'),(47,'Gene','displayName','gene'),(48,'Hemolysis','displayName','Hemolysis'),(49,'ImagingFunction','displayName','imaging'),(50,'ImmuneCellFunction','displayName','Immune Cell Function'),(51,'Immunotoxicity','displayName','Immunotoxicity'),(52,'LeukocyteProliferation','displayName','Leukocyte Proliferation'),(53,'Liposome','displayName','liposome'),(54,'MetalParticle','displayName','metal particle'),(55,'MolecularWeight','displayName','Molecular Weight'),(56,'NKCellCytotoxicActivity','displayName','NK Cell Cytotoxic Activity'),(57,'OxidativeBurst','displayName','Oxidative Burst'),(58,'OxidativeStress','displayName','Oxidative Stress'),(59,'Phagocytosis','displayName','Phagocytosis'),(60,'PhysicalState','displayName','Physical State'),(61,'PlasmaProteinBinding','displayName','Plasma Protein Binding'),(62,'PlateletAggregation','displayName','Platelet Aggregation'),(63,'Polymer','displayName','polymer'),(64,'Purity','displayName','Purity'),(65,'QuantumDot','displayName','quantum dot'),(66,'Receptor','displayName','receptor'),(67,'Shape','displayName','Shape'),(68,'Size','displayName','Size'),(69,'SmallMolecule','displayName','small molecule'),(70,'Solubility','displayName','Solubility'),(71,'Surface','displayName','Surface'),(72,'TargetingFunction','displayName','targeting'),(73,'TherapeuticFunction','displayName','therapeutic'),(74,'Toxicity','displayName','Toxicity'),(75,'Antibody','isotype','IgA'),(76,'Antibody','isotype','IgD'),(77,'Antibody','isotype','IgE'),(78,'Antibody','isotype','IgG'),(79,'Antibody','isotype','IgM'),(80,'ImagingFunction','modality','bioluminescence'),(81,'ImagingFunction','modality','flurorescence'),(82,'ImagingFunction','modality','infrared'),(83,'ImagingFunction','modality','MRI'),(84,'ImagingFunction','modality','neutron scattering'),(85,'ImagingFunction','modality','PET'),(86,'ImagingFunction','modality','Raman spectroscopy'),(87,'ImagingFunction','modality','SPECT'),(88,'ImagingFunction','modality','ultrasound'),(89,'ImagingFunction','modality','X-ray'),(90,'ComposingElement','molecularFormulaType','SMARTS'),(91,'ComposingElement','molecularFormulaType','SMILES'),(92,'FunctionalizingEntity','molecularFormulaType','SMARTS'),(93,'FunctionalizingEntity','molecularFormulaType','SMILES'),(94,'SurfaceChemistry','molecularFormulaType','SMARTS'),(95,'SurfaceChemistry','molecularFormulaType','SMILES'),(96,'SampleContainer','quantityUnit','g'),(97,'SampleContainer','quantityUnit','mg'),(98,'SampleContainer','quantityUnit','ug'),(99,'Solubility','solvent','alcohol'),(100,'Solubility','solvent','phospate-buffered saline'),(101,'Solubility','solvent','saline'),(102,'Solubility','solvent','water'),(103,'Antibody','species','cat'),(104,'Antibody','species','cattle'),(105,'Antibody','species','dog'),(106,'Antibody','species','goat'),(107,'Antibody','species','guinea pig'),(108,'Antibody','species','hamster'),(109,'Antibody','species','horse'),(110,'Antibody','species','human'),(111,'Antibody','species','mouse'),(112,'Antibody','species','pig'),(113,'Antibody','species','rat'),(114,'Antibody','species','sheep'),(115,'Antibody','species','yeast'),(116,'Antibody','species','zebrafish'),(117,'Antigen','species','cat'),(118,'Antigen','species','cattle'),(119,'Antigen','species','dog'),(120,'Antigen','species','goat'),(121,'Antigen','species','guinea pig'),(122,'Antigen','species','hamster'),(123,'Antigen','species','horse'),(124,'Antigen','species','human'),(125,'Antigen','species','mouse'),(126,'Antigen','species','pig'),(127,'Antigen','species','rat'),(128,'Antigen','species','sheep'),(129,'Antigen','species','yeast'),(130,'Antigen','species','zebrafish'),(131,'ActivationMethod','type','enzymatic cleavage'),(132,'ActivationMethod','type','infrared'),(133,'ActivationMethod','type','MRI'),(134,'ActivationMethod','type','NMR'),(135,'ActivationMethod','type','pH'),(136,'ActivationMethod','type','ultrasound'),(137,'ActivationMethod','type','ultraviolet'),(138,'Antibody','type','Fab'),(139,'Antibody','type','ScFv'),(140,'Antibody','type','whole'),(141,'Biopolymer','type','DNA'),(142,'Biopolymer','type','peptide'),(143,'Biopolymer','type','protein'),(144,'Biopolymer','type','RNA'),(145,'ComposingElement','type','coat'),(146,'ComposingElement','type','core'),(147,'ComposingElement','type','modifier'),(148,'ComposingElement','type','monomer'),(149,'ComposingElement','type','lipid'),(150,'ComposingElement','type','repeat unit'),(151,'ComposingElement','type','shell'),(152,'ComposingElement','type','terminal group'),(153,'LabFile','type','document'),(154,'LabFile','type','graph'),(155,'LabFile','type','image'),(156,'LabFile','type','spread sheet'),(157,'PhysicalState','type','colloid-emulsion'),(158,'PhysicalState','type','colloid-gel'),(159,'PhysicalState','type','colloid-sol'),(160,'PhysicalState','type','fluid-gas'),(161,'PhysicalState','type','fluid-liquid'),(162,'PhysicalState','type','fluid-vapor'),(163,'PhysicalState','type','solid-crystal'),(164,'PhysicalState','type','solid-glass'),(165,'PhysicalState','type','solid-granule'),(166,'PhysicalState','type','solid-powder'),(167,'PhysicalState','type','solid-fibril'),(168,'Protocol','type','in vitro assay'),(169,'Protocol','type','in vivo assay'),(170,'Protocol','type','physical assay'),(171,'Protocol','type','radio labeling'),(172,'Protocol','type','safety'),(173,'Protocol','type','sample preparation'),(174,'Protocol','type','synthesis'),(175,'Shape','type','2D-circle'),(176,'Shape','type','2D-diamond'),(177,'Shape','type','2D-ellipse'),(178,'Shape','type','2D-parallelogram'),(179,'Shape','type','2D-polygon'),(180,'Shape','type','2D-retangle'),(181,'Shape','type','2D-square'),(182,'Shape','type','2D-trapezoid'),(183,'Shape','type','2D-triangle'),(184,'Shape','type','3D-cone'),(185,'Shape','type','3D-cube'),(186,'Shape','type','3D-cylinder'),(187,'Shape','type','3D-disc'),(188,'Shape','type','3D-fibril'),(189,'Shape','type','3D-hexahedron'),(190,'Shape','type','3D-needle'),(191,'Shape','type','3D-oblate spheroid'),(192,'Shape','type','3D-polyhedron'),(193,'Shape','type','3D-prolate spheroid'),(194,'Shape','type','3D-rod'),(195,'Shape','type','3D-sphere'),(196,'Shape','type','3D-tetrahedron'),(197,'Shape','type','3D-tetrapod'),(198,'StorageElement','type','box'),(199,'StorageElement','type','freezer'),(200,'StorageElement','type','room'),(201,'StorageElement','type','shelf'),(202,'charge','unit','a.u.'),(203,'charge','unit','aC'),(204,'charge','unit','Ah'),(205,'charge','unit','C'),(206,'charge','unit','esu'),(207,'charge','unit','Fr'),(208,'charge','unit','statC'),(209,'molecular weight','unit','kDa'),(210,'peak1','unit','nm'),(211,'RMS-size','unit','nm'),(212,'surface area','unit','nm^2'),(213,'Z-average','unit','nm'),(214,'zeta potential','unit','mV'),(215,'DerivedDatum','valueType','boolean'),(216,'DerivedDatum','valueType','mean'),(217,'DerivedDatum','valueType','median'),(218,'DerivedDatum','valueType','mode'),(219,'DerivedDatum','valueType','observed'),(220,'DerivedDatum','valueType','standard deviation'),(221,'ComposingElement','valueUnit','%'),(222,'ComposingElement','valueUnit','%mole'),(223,'ComposingElement','valueUnit','%vol'),(224,'ComposingElement','valueUnit','%wt/vol'),(225,'FunctionalzingEntity','valueUnit','%'),(226,'FunctionalzingEntity','valueUnit','%mole'),(227,'FunctionalzingEntity','valueUnit','%vol'),(228,'FunctionalzingEntity','valueUnit','%wt/vol'),(229,'SampleContainer','volumeUnit','ml'),(230,'SampleContainer','volumeUnit','ul'),(231,'CarbonNanotube','wallType','DWNT'),(232,'CarbonNanotube','wallType','MWNT'),(233,'CarbonNanotube','wallType','SWNT');
/*!40000 ALTER TABLE `common_lookup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2008-04-07 21:24:28
