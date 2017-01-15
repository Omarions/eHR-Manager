-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 13, 2017 at 11:40 AM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ehrmanager`
--
CREATE DATABASE IF NOT EXISTS `ehrmanager` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ehrmanager`;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `photo` varchar(150) NOT NULL DEFAULT 'aboutme-256.png',
  `department` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `national_id` varchar(15) NOT NULL DEFAULT 'N/A',
  `insurance_num` varchar(20) NOT NULL DEFAULT 'N/A',
  `hiring_date` date NOT NULL,
  `basic_salary` decimal(6,2) NOT NULL DEFAULT '0.00',
  `gross_salary` decimal(7,2) NOT NULL DEFAULT '0.00',
  `insurance_deduction` decimal(7,2) NOT NULL DEFAULT '0.00',
  `health_ins_deduction` decimal(6,2) NOT NULL DEFAULT '0.00',
  `other_deduction` decimal(7,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `photo`, `department`, `title`, `national_id`, `insurance_num`, `hiring_date`, `basic_salary`, `gross_salary`, `insurance_deduction`, `health_ins_deduction`, `other_deduction`) VALUES
(1, 'OMAR', '1.jpg', 'Sales', 'sales eng', '2103355', '1245897', '2015-10-10', '1000.00', '2000.00', '50.00', '150.00', '50.00'),
(2, 'diaa', '2.jpg', 'Financial', 'accountant', 'N/A', 'N/A', '1970-01-01', '0.00', '1800.00', '100.00', '0.00', '50.00'),
(3, 'Ayman', '3.jpg', 'Sales', 'sales eng', 'N/A', 'N/A', '1970-01-01', '0.00', '2000.00', '200.00', '0.00', '100.00'),
(4, 'diaa', '4.jpg', 'Stock', 'stock manager', 'N/A', 'N/A', '1970-01-01', '0.00', '2900.00', '0.00', '0.00', '0.00'),
(5, 'Tarek', '5.jpg', 'Financial', 'accountant', 'N/A', 'N/A', '1970-01-01', '0.00', '1200.00', '50.00', '0.00', '50.00'),
(6, 'Moaz', '6.jpg', 'Financial', 'accountant', 'N/A', 'N/A', '1970-01-01', '0.00', '1200.00', '50.00', '0.00', '0.00'),
(9, 'Mohamed', '9.jpg', 'Stock', 'labor', 'N/A', 'N/A', '1970-01-01', '0.00', '300.00', '40.00', '0.00', '20.00'),
(29, 'mostaf', '29.jpg', 'Stock', 'mos', '123', '456', '2010-01-01', '100.00', '1000.00', '0.00', '100.00', '10.00');

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `customer` varchar(100) NOT NULL DEFAULT 'N/A' COMMENT 'who we are working for (e.g vendor or other sub-contractor)',
  `operator` varchar(100) NOT NULL DEFAULT 'N/A' COMMENT 'the operator',
  `pm_id` int(11) NOT NULL COMMENT 'ref to employee id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='saves info about projects';

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`id`, `name`, `customer`, `operator`, `pm_id`) VALUES
(1, 'Project 1', 'Ericsson', 'Vodafone', 1),
(2, 'Project 2', 'Huawei', 'Etisalate', 2),
(3, 'Project 3', 'TE', 'TE', 3);

-- --------------------------------------------------------

--
-- Table structure for table `timesheet`
--

DROP TABLE IF EXISTS `timesheet`;
CREATE TABLE IF NOT EXISTS `timesheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'the id of the time sheet',
  `ts_date` date NOT NULL COMMENT 'saves the year and month of time sheet only',
  `emp_id` int(11) NOT NULL COMMENT 'the foreign key that saves the employee id of the time sheet',
  `project_id` int(11) NOT NULL,
  `zone` varchar(50) NOT NULL DEFAULT 'N/A',
  `site_name` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL,
  `transport_allowance` decimal(6,2) NOT NULL DEFAULT '0.00',
  `allowance` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT 'allowance for the day',
  `bonus` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT 'bonus for the day',
  `bonus_reason` varchar(500) NOT NULL DEFAULT 'N/A',
  `penalty` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT 'penalty for the day',
  `penalty_reason` varchar(500) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='saves time sheet summary of each employee';

--
-- Dumping data for table `timesheet`
--

INSERT INTO `timesheet` (`id`, `ts_date`, `emp_id`, `project_id`, `zone`, `site_name`, `description`, `transport_allowance`, `allowance`, `bonus`, `bonus_reason`, `penalty`, `penalty_reason`) VALUES
(1, '2016-12-01', 1, 1, 'N/A', '', '', '0.00', '120.00', '50.00', 'N/A', '10.00', 'N/A'),
(2, '2016-11-01', 1, 1, 'Smart Vlg', 'ZTE', 'Meeting to discus project with ZTE resources manager and his team', '50.00', '150.00', '500.00', 'great negotiation and good presentation for our products and profile', '100.00', 'N/A'),
(3, '2016-11-01', 2, 2, 'Cairo', '', '', '25.00', '300.00', '0.00', 'N/A', '50.00', 'N/A'),
(4, '2016-12-01', 2, 3, 'N/A', '', '', '0.00', '500.00', '400.00', 'Great work, on time and good attitude', '250.00', 'N/A'),
(5, '2016-11-01', 3, 1, 'Smart Vlg', 'Actel', 'Meeting for sales', '50.00', '30.00', '100.00', 'Great work', '10.00', 'Late response'),
(6, '2016-11-01', 3, 2, 'Alex', 'Hadra Exch', 'lying 75 ohm cable', '150.00', '70.00', '50.00', 'quality, and speed', '0.00', 'N/A');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
