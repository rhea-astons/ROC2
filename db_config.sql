-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 25, 2013 at 04:32 PM
-- Server version: 5.5.31
-- PHP Version: 5.4.4-14

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `rocc`
--

-- --------------------------------------------------------

--
-- Table structure for table `Budget`
--

CREATE TABLE IF NOT EXISTS `Budget` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libBudget` varchar(55) COLLATE utf8_swedish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=21 ;

-- --------------------------------------------------------

--
-- Table structure for table `Categorie`
--

CREATE TABLE IF NOT EXISTS `Categorie` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libCategorie` varchar(55) COLLATE utf8_swedish_ci NOT NULL,
  `idBudget` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idBudget` (`idBudget`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=116 ;

-- --------------------------------------------------------

--
-- Table structure for table `Mouvement`
--

CREATE TABLE IF NOT EXISTS `Mouvement` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libMouvement` varchar(55) COLLATE utf8_swedish_ci NOT NULL,
  `montant` float NOT NULL,
  `type` tinyint(4) NOT NULL,
  `ESType` tinyint(4) NOT NULL,
  `date` datetime NOT NULL,
  `periodicite` int(10) unsigned NOT NULL,
  `idBudget` int(10) unsigned NOT NULL,
  `idCategorie` int(10) unsigned NOT NULL,
  `idSousCategorie` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idBudget` (`idBudget`),
  KEY `idCategorie` (`idCategorie`),
  KEY `idSousCategorie` (`idSousCategorie`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=55 ;

-- --------------------------------------------------------

--
-- Table structure for table `SousCategorie`
--

CREATE TABLE IF NOT EXISTS `SousCategorie` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `libSousCategorie` varchar(55) COLLATE utf8_swedish_ci NOT NULL,
  `idCategorie` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idCategorie` (`idCategorie`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=90 ;

-- --------------------------------------------------------

--
-- Table structure for table `Utilisateur`
--

CREATE TABLE IF NOT EXISTS `Utilisateur` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_swedish_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8_swedish_ci NOT NULL,
  `login` varchar(45) COLLATE utf8_swedish_ci NOT NULL,
  `password` char(32) COLLATE utf8_swedish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Table structure for table `UtilisateurBudget`
--

CREATE TABLE IF NOT EXISTS `UtilisateurBudget` (
  `idUtilisateur` int(10) unsigned NOT NULL,
  `idBudget` int(10) unsigned NOT NULL,
  `droit` tinyint(4) NOT NULL,
  KEY `idUtilisateur` (`idUtilisateur`),
  KEY `idBudget` (`idBudget`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Categorie`
--
ALTER TABLE `Categorie`
  ADD CONSTRAINT `Categorie_ibfk_2` FOREIGN KEY (`idBudget`) REFERENCES `Budget` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Mouvement`
--
ALTER TABLE `Mouvement`
  ADD CONSTRAINT `Mouvement_ibfk_8` FOREIGN KEY (`idBudget`) REFERENCES `Budget` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Mouvement_ibfk_6` FOREIGN KEY (`idCategorie`) REFERENCES `Categorie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Mouvement_ibfk_7` FOREIGN KEY (`idSousCategorie`) REFERENCES `SousCategorie` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `SousCategorie`
--
ALTER TABLE `SousCategorie`
  ADD CONSTRAINT `SousCategorie_ibfk_2` FOREIGN KEY (`idCategorie`) REFERENCES `Categorie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `UtilisateurBudget`
--
ALTER TABLE `UtilisateurBudget`
  ADD CONSTRAINT `UtilisateurBudget_ibfk_4` FOREIGN KEY (`idBudget`) REFERENCES `Budget` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `UtilisateurBudget_ibfk_3` FOREIGN KEY (`idUtilisateur`) REFERENCES `Utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
