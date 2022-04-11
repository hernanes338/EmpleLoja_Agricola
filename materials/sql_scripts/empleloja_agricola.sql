-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 12, 2022 at 01:23 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `empleloja_agricola`
--

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `ROLE_ID` int(11) NOT NULL,
  `ROLE_CODE` varchar(12) NOT NULL,
  `ROLE_NAME` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`ROLE_ID`, `ROLE_CODE`, `ROLE_NAME`) VALUES
(1, 'OFERT', 'Ofertante'),
(2, 'DEMAN', 'Demandante');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `USER_ID` int(11) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  `SURNAME` varchar(60) NOT NULL,
  `PHONE` int(9) NOT NULL,
  `EMAIL` varchar(128) NOT NULL,
  `PASSWORD` varchar(128) NOT NULL,
  `ROLE_ID` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`USER_ID`, `NAME`, `SURNAME`, `PHONE`, `EMAIL`, `PASSWORD`, `ROLE_ID`) VALUES
(1, 'Admin', 'Ofertante', 123456789, 'admin_ofertante', '953a5d022f7bc377605900767d09e00c', 1),
(2, 'Admin', 'Demandante', 987654321, 'admin_demandante', '8970d7b377a51822f2674b8fdb51000e', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`ROLE_ID`),
  ADD UNIQUE KEY `ROLE_CODE` (`ROLE_CODE`),
  ADD UNIQUE KEY `ROLE_NAME` (`ROLE_NAME`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`USER_ID`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`),
  ADD KEY `ROLE_ID_FK` (`ROLE_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `USER_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `ROLE_ID_FK` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ROLE_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
