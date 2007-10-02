-- ----------------------------------------------------------------------
-- SQL data bulk transfer script generated by the MySQL Migration Toolkit
-- ----------------------------------------------------------------------
USE cananolab;

-- Disable foreign key checks
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

INSERT INTO assay(assay_pk_id, assay_name, description, assay_type, created_date, created_by, protocol_pk_id)
VALUES (1, 'STE-1', NULL, 'Pre-screening', '2006-04-06 11:12:02', ' ', NULL),
  (2, 'STE-2', NULL, 'Pre-screening', '2006-04-06 11:12:02', ' ', NULL),
  (3, 'STE-3', NULL, 'Pre-screening', '2006-04-06 11:12:02', ' ', NULL),
  (4, 'PCC-1', NULL, 'Pre-screening', '2006-04-06 11:12:02', ' ', NULL),
  (5, 'ITA-1', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (6, 'ITA-2', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (7, 'ITA-3', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (8, 'ITA-4', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (9, 'ITA-5', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (10, 'ITA-6', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (11, 'ITA-7', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (12, 'ITA-8', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (13, 'ITA-9', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (14, 'ITA-10', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (15, 'ITA-11', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (16, 'ITA-12', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (17, 'GTA-1', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (18, 'GTA-2', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (19, 'GTA-3', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (20, 'GTA-4', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (21, 'GTA-5', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL),
  (22, 'GTA-6', NULL, 'In Vitro', '2006-04-06 11:12:02', ' ', NULL);

INSERT INTO def_activation_method(activation_method_pk_id, name)
VALUES (1, 'MRI'),
  (2, 'NMR'),
  (3, 'Radiation'),
  (4, 'Ultrasound'),
  (5, 'Ultraviolet');

INSERT INTO def_assay_type(assay_type_pk_id, name, description, execute_order)
VALUES (1, 'Pre-screening', NULL, '1'),
  (2, 'In Vitro', NULL, '2'),
  (3, 'In Vivo', NULL, '3'),
  (4, 'PCC', NULL, '4');

INSERT INTO def_bioassay_data_category(category_pk_id, name, characterization_name)
VALUES (1, 'Volume Distribution', 'Size'),
  (2, 'Number Distribution ', 'Size'),
  (3, 'Intensity Distribution', 'Size'),
  (4, 'Mass Distribution', 'Molecular Weight'),
  (5, 'Height Distribution', 'Size'),
  (6, 'Radius Vs. Time Distribution', 'Size'),
  (7, 'Volume Distribution', 'Molecular Weight'),
  (8, 'Number Distribution', 'Molecular Weight'),
  (9, 'Intensity Vs. m/z Spectrum Distribution', 'Molecular Weight'),
  (10, 'Zeta Potential Distribution', 'Surface'),
  (11, 'Hemolytic Properties', 'Hemolysis');

INSERT INTO def_bond_type(bond_type_pk_id, name)
VALUES (1, 'Covalent'),
  (2, 'Vander Walls'),
  (3, 'Ionic'),
  (4, 'Non-specific');

INSERT INTO def_cellline_type(cellline_type_pk_id, name)
VALUES (1, 'Human Hepatocarinma'),
  (2, 'Porcine Renal Tubule');

INSERT INTO def_characterization_category(char_category_pk_id, category, name, has_action, category_order, indent_level, name_abbreviation)
VALUES (1, 'Toxicity', 'Oxidative Stress', 1, 2, 1, 'OS'),
  (2, 'Toxicity', 'Enzyme Induction', 1, 2, 1, 'EI'),
  (3, 'Cytotoxicity', 'Cell Viability', 1, 3, 2, 'CV'),
  (4, 'Cytotoxicity', 'Caspase 3 Activation', 1, 3, 2, 'C3'),
  (5, 'Blood Contact', 'Platelet Aggregation', 1, 5, 3, 'PA'),
  (6, 'Blood Contact', 'Hemolysis', 1, 5, 3, 'HM'),
  (7, 'Blood Contact', 'Coagulation', 1, 5, 3, 'CG'),
  (8, 'Blood Contact', 'Plasma Protein Binding', 1, 5, 3, 'PB'),
  (9, 'Immune Cell Function', 'Complement Activation', 1, 6, 3, 'CA'),
  (10, 'Immune Cell Function', 'Phagocytosis', 1, 6, 3, 'PC'),
  (11, 'Immune Cell Function', 'Chemotaxis', 1, 6, 3, 'CT'),
  (12, 'Immune Cell Function', 'CFU_GM', 1, 6, 3, 'CU'),
  (13, 'Immune Cell Function', 'Oxidative Burst', 1, 6, 3, 'OB'),
  (14, 'Immune Cell Function', 'Leukocyte Proliferation', 1, 6, 3, 'LP'),
  (15, 'Immune Cell Function', 'Cytokine Induction', 1, 6, 3, 'CI'),
  (16, 'Immune Cell Function', 'NK Cell Cytotoxic Activity', 1, 6, 3, 'NK'),
  (17, 'Physical', 'Size', 1, 0, 0, 'SZ'),
  (18, 'Physical', 'Purity', 1, 0, 0, 'PT'),
  (19, 'Physical', 'Surface', 1, 0, 0, 'SF'),
  (21, 'Physical', 'Solubility', 1, 0, 0, 'SL'),
  (22, 'Physical', 'Molecular Weight', 1, 0, 0, 'MW'),
  (23, 'Physical', 'Shape', 1, 0, 0, 'SH'),
  (24, 'Physical', 'Morphology', 1, 0, 0, 'MP'),
  (25, 'Physical', 'Composition', 1, 0, 0, 'CP'),
  (26, 'In Vitro', 'Toxicity', 0, 1, 0, NULL),
  (27, 'Toxicity', 'Cytotoxicity', 1, 2, 1, NULL),
  (28, 'Toxicity', 'Immunotoxicity', 1, 2, 1, NULL),
  (29, 'Immunotoxicity', 'Blood Contact', 0, 4, 2, NULL),
  (30, 'Immunotoxicity', 'Immune Cell Function', 0, 4, 2, NULL);

INSERT INTO def_characterization_file_type(file_type_pk_id, name)
VALUES (1, 'Image'),
  (2, 'Graph'),
  (3, 'SpreadSheet');

INSERT INTO def_datum_name(datum_name_pk_id, name, is_datum_parsed, characterization_name)
VALUES (1, 'PDI', 0, 'Size'),
  (2, 'Z-Average', 0, 'Size'),
  (3, 'Peak 1', 0, 'Size'),
  (4, 'RMS Size', 0, 'Size'),
  (5, 'Molecular Weight', 0, 'Molecular Weight'),
  (6, 'Zeta Potential', 0, 'Surface'),
  (8, 'LC50', 0, 'Cell Viability'),
  (9, 'Is Hemolytic', 0, 'Hemolysis'),
  (10, 'Is Above Threshold', 0, 'Platelet Aggregation'),
  (11, 'Number of CFU-GM Colonies', 0, 'CFU_GM'),
  (12, 'Total Number of Bone Marrow Cells', 0, 'CFU_GM');

INSERT INTO def_function_agent_target_type(agent_target_type_pk_id, name)
VALUES (1, 'Receptor'),
  (2, 'Antigen');

INSERT INTO def_function_agent_type(agent_type_pk_id, name)
VALUES (1, 'Peptide'),
  (2, 'Small Molecule'),
  (3, 'Antibody'),
  (4, 'DNA'),
  (5, 'Image Contrast Agent');

INSERT INTO def_function_linkage_type(linkage_type_pk_id, name)
VALUES (1, 'Attachment'),
  (2, 'Encapsulation');

INSERT INTO def_function_type(function_type_pk_id, name)
VALUES (1, 'Therapeutic'),
  (2, 'Targeting'),
  (3, 'Diagnostic Imaging'),
  (4, 'Diagnostic Reporting');

INSERT INTO def_image_contrast_agent_type(agent_type_pk_id, name)
VALUES (1, 'Flurorescent'),
  (2, 'Infrared'),
  (3, 'MRI'),
  (4, 'Neutron Scattering'),
  (5, 'PET'),
  (6, 'SPECT'),
  (7, 'Ultrasound'),
  (8, 'X-Ray');

INSERT INTO def_measure_type(measure_type_pk_id, name)
VALUES (5, 'threshold'),
  (6, 'boolean'),
  (1, 'mean'),
  (2, 'median'),
  (3, 'observed'),
  (4, 'standard deviation');

INSERT INTO def_measure_unit(measure_unit_pk_id, unit_name, description, unit_type)
VALUES (13, 'a.u', NULL, 'Charge'),
  (21, 'mV', NULL, 'Zeta Potential'),
  (14, 'aC', NULL, 'Charge'),
  (15, 'Ah', NULL, 'Charge'),
  (16, 'C', NULL, 'Charge'),
  (17, 'esu', NULL, 'Charge'),
  (18, 'Fr', NULL, 'Charge'),
  (19, 'statC', NULL, 'Charge'),
  (20, 'nm^2', NULL, 'Area'),
  (1, 'g', NULL, 'Quantity'),
  (2, 'mg', NULL, 'Quantity'),
  (3, 'ug', NULL, 'Quantity'),
  (4, 'g/ml', NULL, 'Concentration'),
  (5, 'mg/ml', NULL, 'Concentration'),
  (6, 'ug/ml', NULL, 'Concentration'),
  (7, 'ug/ul', NULL, 'Concentration'),
  (8, 'ml', NULL, 'Volume'),
  (9, 'ul', NULL, 'Volume'),
  (10, 'nm', NULL, 'Size'),
  (12, 'kDa', NULL, 'Molecular Weight');

INSERT INTO def_molecular_formula_type(molecular_formula_type_pk_id, name)
VALUES (1, 'SMILES'),
  (2, 'SMARTS');

INSERT INTO def_morphology_type(morphology_type_pk_id, name)
VALUES (1, 'Powder'),
  (2, 'Liquid'),
  (3, 'Solid'),
  (4, 'Crystalline'),
  (5, 'Copolymer'),
  (6, 'Fibril'),
  (7, 'Colloid'),
  (8, 'Oil');

INSERT INTO def_protocol_type(protocol_type_pk_id, name)
VALUES (1, 'Physical assay'),
  (2, 'In Vivo assay'),
  (3, 'In Vitro assay'),
  (4, 'Radio Labeling'),
  (5, 'Synthesis'),
  (6, 'Sample Preparation'),
  (7, 'Safety');

INSERT INTO def_sample_type(sample_type_pk_id, name)
VALUES (9, 'Complex Particle'),
  (8, 'Emulsion'),
  (1, 'Dendrimer'),
  (2, 'Quantum Dot'),
  (3, 'Polymer'),
  (4, 'Metal Particle'),
  (5, 'Fullerene'),
  (6, 'Liposome'),
  (7, 'Carbon Nanotube');

INSERT INTO def_shape_type(shape_type_pk_id, name)
VALUES (1, 'Composite'),
  (2, 'Cubic'),
  (3, 'Cylinder'),
  (4, 'Elliptical'),
  (5, 'Ellipsoid'),
  (6, 'Hexagonal'),
  (7, 'Irregular'),
  (8, 'Needle'),
  (9, 'Oblate'),
  (10, 'Rod'),
  (11, 'Spherical'),
  (12, 'Tetrahedron'),
  (13, 'Tetrapod'),
  (14, 'Triangle'),
  (15, 'Vesicle');

INSERT INTO def_solvent_type(solvent_type_pk_id, name)
VALUES (1, 'Pooled Blood Serum (PBS)'),
  (2, 'Saline '),
  (3, 'Water');

INSERT INTO def_species_name(species_name_pk_id, name)
VALUES (1, 'Cat'),
  (2, 'Cattle'),
  (3, 'Dog'),
  (4, 'Goat'),
  (5, 'Guinea Pig'),
  (6, 'Hamster'),
  (7, 'Horse'),
  (8, 'Human'),
  (9, 'Mouse'),
  (10, 'Pig'),
  (11, 'Rat'),
  (12, 'Sheep'),
  (13, 'Yeast'),
  (14, 'Zebrafish');

INSERT INTO def_storage_type(storage_type_id, name)
VALUES (1, 'Lab'),
  (2, 'Room'),
  (3, 'Freezer'),
  (4, 'Shelf'),
  (5, 'Rack'),
  (6, 'Box');

INSERT INTO def_surface_group_type(surface_group_type_pk_id, name)
VALUES (1, 'Amine'),
  (2, 'Carboxyl'),
  (3, 'Hydroxyl');

INSERT INTO def_wall_type(wall_type_pk_id, name)
VALUES (1, 'SWNT'),
  (2, 'DWNT'),
  (3, 'MWNT');

INSERT INTO hibernate_unique_key(next_hi)
VALUES (113);

INSERT INTO instrument(instrument_pk_id, type, abbreviation, manufacturer)
VALUES (1, 'Atomic Force Microscope', 'AFM', 'Molecular Imaging'),
  (2, 'Clot Detection System', NULL, 'Diagnostica'),
  (3, 'Coulter Counter', NULL, 'Beckman/Coulter'),
  (4, 'Dynamic Light Scattering', 'DLS', 'Wyatt Technologies'),
  (5, 'Energy Dispersive Spectroscopy', 'EDS', 'EDAX'),
  (6, 'Flow Cytometer', NULL, 'Becton Dickinson'),
  (7, 'High Performance Liquid Chromatography', 'HPLC', 'Agilent'),
  (8, 'High Performance Liquid Chromatography', 'HPLC', 'Shimadzu'),
  (9, 'Imaging System', NULL, 'Kodak'),
  (10, 'Liquid Chromatography', 'LC', 'Amersham'),
  (11, 'Refractometer', NULL, 'Waters'),
  (12, 'Scintillation Counter', NULL, 'Beckman/Coulter'),
  (13, 'Spectrophotometer', NULL, 'Molecular Devices'),
  (14, 'Spectrophotometer', NULL, 'Tecan'),
  (15, 'Spectrophotometer', NULL, 'Thermo Electron'),
  (16, 'Thermal Cycler', NULL, 'Biorad'),
  (17, 'Zetasizer', NULL, 'Malvern');

INSERT INTO lab_file(file_pk_id, file_name, file_uri, file_type_extension, file_source_type, version, status, reason, created_by, created_date, sample_sop_pk_id, run_pk_id, data_status_pk_id, title, description, comments, type)
VALUES (1, NULL, '/doc/solubilized.doc', 'doc', 'SOP', NULL, NULL, NULL, NULL, NULL, 4, NULL, NULL, NULL, NULL, NULL, NULL),
  (2, NULL, '/doc/lyophilized.doc', 'doc', 'SOP', NULL, NULL, NULL, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO sample_sop(sample_sop_pk_id, description, sop_name)
VALUES (1, 'sample creation', 'Original'),
  (2, 'sample creation', 'Testing'),
  (3, 'aliquot creation', 'Lyophilized'),
  (4, 'aliquot creation', 'Solubilized');

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;

-- End of script
