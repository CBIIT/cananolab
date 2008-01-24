DROP DATABASE IF EXISTS cananolab;
CREATE DATABASE cananolab
  CHARACTER SET latin1 COLLATE latin1_swedish_ci;

DELETE FROM mysql.user WHERE User='cananolab_app';

GRANT SELECT,INSERT,UPDATE,DELETE ON cananolab.* TO 'cananolab_app'@'localhost' IDENTIFIED BY '@DB_PASS@' WITH GRANT OPTION;
GRANT SELECT,INSERT,UPDATE,DELETE ON cananolab.* TO 'cananolab_app'@'%' IDENTIFIED BY '@DB_PASS@' WITH GRANT OPTION;

select count(*) from information_schema.tables where table_schema='cananolab' and table_type='BASE TABLE';