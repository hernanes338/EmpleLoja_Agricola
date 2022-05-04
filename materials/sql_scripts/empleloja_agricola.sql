-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 04, 2022 at 11:12 PM
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
-- Table structure for table `job_demands`
--

CREATE TABLE `job_demands` (
  `ID` int(11) NOT NULL,
  `TITLE` varchar(128) NOT NULL,
  `DESCRIPTION` varchar(300) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `AVAILABLE_FROM` date NOT NULL,
  `ACTIVE` varchar(1) NOT NULL DEFAULT 'Y'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `job_demands`
--

INSERT INTO `job_demands` (`ID`, `TITLE`, `DESCRIPTION`, `USER_ID`, `AVAILABLE_FROM`, `ACTIVE`) VALUES
(1, 'Titulo demanda de trabajo', 'Descripcion demanda de trabajo', 3, '2020-10-05', 'Y'),
(2, 'Titulo demanda de trabajo dos', 'Descripcion demanda de trabajo dos', 3, '2022-07-01', 'Y'),
(3, 'Titulo demanda de trabajo tres', 'Descripcion demanda de trabajo tres', 5, '2022-01-01', 'Y'),
(4, 'Titulo demanda de trabajo cuatro', 'Descripcion demanda de trabajo cuatro', 5, '2022-02-02', 'Y');

-- --------------------------------------------------------

--
-- Table structure for table `job_offers`
--

CREATE TABLE `job_offers` (
  `ID` int(11) NOT NULL,
  `TITLE` varchar(128) NOT NULL,
  `DESCRIPTION` varchar(300) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `START_DATE` date NOT NULL,
  `END_DATE` date NOT NULL,
  `SALARY_HOUR` float NOT NULL,
  `ACTIVE` varchar(1) NOT NULL DEFAULT 'Y'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `job_offers`
--

INSERT INTO `job_offers` (`ID`, `TITLE`, `DESCRIPTION`, `USER_ID`, `START_DATE`, `END_DATE`, `SALARY_HOUR`, `ACTIVE`) VALUES
(1, 'Titulo oferta de trabajo', 'Descripcion oferta de trabajo', 2, '2020-10-09', '2020-10-08', 10.5, 'Y'),
(2, 'Titulo oferta de trabajo dos', 'Descripcion de oferta de trabajo dos', 2, '2020-10-09', '2020-10-08', 9.5, 'Y'),
(3, 'Titulo oferta de trabajo tres', 'Descripcion de oferta de trabajo tres', 4, '2022-05-01', '2022-05-01', 5.5, 'Y'),
(4, 'Titulo oferta de trabajo cuatro', 'Descripcion oferta de trabajo cuatro', 4, '2022-09-01', '2022-09-30', 7, 'Y');

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
(1, 'ADMIN', 'Administrador'),
(2, 'OFERT', 'Ofertante de empleo'),
(3, 'DEMAN', 'Demandante de empleo');

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
(1, 'Admin', 'Admin', 123456789, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1),
(2, 'Usuario', 'Ofertante', 123, 'usuario_ofertante', '0b6a4bbf52c501be76f61139b3b04ab3', 2),
(3, 'Usuario', 'Demandante', 456, 'usuario_demandante', '173d7a26658653c3fa21b5005c2016e2', 3),
(4, 'Usuario', 'Ofertante Dos', 789, 'usuario_ofertante_dos', 'd694ff21e2ddbb8f11b0a80c48938f25', 2),
(5, 'Usuario', 'Demandante Dos', 123456789, 'usuario_demandante_dos', '03d307822eb1678885f1e42bf6a27217', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `job_demands`
--
ALTER TABLE `job_demands`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `USER_ID_JOB_DEMANDS_FK` (`USER_ID`);

--
-- Indexes for table `job_offers`
--
ALTER TABLE `job_offers`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `USER_ID_JOB_OFFERS_FK` (`USER_ID`);

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
-- AUTO_INCREMENT for table `job_demands`
--
ALTER TABLE `job_demands`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `job_offers`
--
ALTER TABLE `job_offers`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `USER_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `job_demands`
--
ALTER TABLE `job_demands`
  ADD CONSTRAINT `USER_ID_JOB_DEMANDS_FK` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`);

--
-- Constraints for table `job_offers`
--
ALTER TABLE `job_offers`
  ADD CONSTRAINT `USER_ID_JOB_OFFERS_FK` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `ROLE_ID_FK` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ROLE_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
