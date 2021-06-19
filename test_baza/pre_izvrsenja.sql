CREATE DATABASE `psprojekattest`;

USE `psprojekattest`;

CREATE TABLE `predmet` (
  `sifra` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(255) DEFAULT NULL,
  `uslov` INT(10) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `student` (
  `sifra` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `brojindeksa` VARCHAR(9) DEFAULT NULL,
  `ime` VARCHAR(255) DEFAULT NULL,
  `prezime` VARCHAR(255) DEFAULT NULL,
  `polozio` TINYINT(4) DEFAULT NULL,
  `predmet` BIGINT(20) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`sifra`),
  UNIQUE KEY `brojindeksa` (`brojindeksa`),
  KEY `predmet` (`predmet`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`predmet`) REFERENCES `predmet` (`sifra`) ON UPDATE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `eksperimentator` (
  `sifra` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ime` VARCHAR(255) DEFAULT NULL,
  `prezime` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `rasporedeksperimenata` (
  `sifra` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `datumOd` DATE NOT NULL,
  `datumDo` DATE NOT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=INNODB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `eksperiment` (
  `sifra` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(255) DEFAULT NULL,
  `datumOdrzavanja` DATE DEFAULT NULL,
  `bodovi` INT(11) DEFAULT NULL,
  `eksperimentator` BIGINT(20) UNSIGNED DEFAULT NULL,
  `raspored` BIGINT(20) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`sifra`),
  UNIQUE KEY `datumOdrzavanja` (`datumOdrzavanja`),
  KEY `eksperimentator` (`eksperimentator`),
  KEY `raspored` (`raspored`),
  CONSTRAINT `eksperiment_ibfk_1` FOREIGN KEY (`eksperimentator`) REFERENCES `eksperimentator` (`sifra`) ON UPDATE CASCADE,
  CONSTRAINT `eksperiment_ibfk_2` FOREIGN KEY (`raspored`) REFERENCES `rasporedeksperimenata` (`sifra`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;



CREATE TABLE `listastudenata` (
  `sifra` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `rok` VARCHAR(30) NOT NULL,
  `predmet` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`sifra`),
  UNIQUE KEY `rok` (`rok`,`predmet`),
  KEY `predmet` (`predmet`),
  CONSTRAINT `listastudenata_ibfk_1` FOREIGN KEY (`predmet`) REFERENCES `predmet` (`sifra`) ON UPDATE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `lss` (
  `student` BIGINT(20) UNSIGNED NOT NULL,
  `listaStudenata` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`student`,`listaStudenata`),
  KEY `listaStudenata` (`listaStudenata`),
  CONSTRAINT `lss_ibfk_1` FOREIGN KEY (`student`) REFERENCES `student` (`sifra`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lss_ibfk_2` FOREIGN KEY (`listaStudenata`) REFERENCES `listastudenata` (`sifra`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `se` (
  `student` BIGINT(20) UNSIGNED NOT NULL,
  `eksperiment` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`student`,`eksperiment`),
  KEY `se_ibfk_1` (`eksperiment`),
  CONSTRAINT `se_ibfk_1` FOREIGN KEY (`eksperiment`) REFERENCES `eksperiment` (`sifra`) ON UPDATE CASCADE,
  CONSTRAINT `se_ibfk_2` FOREIGN KEY (`student`) REFERENCES `student` (`sifra`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `korisnik` (
  `sifra` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;`student`


INSERT INTO `korisnik` VALUES (1,'ksenijaM','ksenija123');

INSERT INTO `predmet` VALUES (1,'Pamcenje',8);
INSERT INTO `predmet` VALUES (2,'Ucenje',9);
INSERT INTO `predmet` VALUES (3,'PIR',5);
