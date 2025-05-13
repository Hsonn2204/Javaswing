-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2024 at 02:06 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `detaithltm`
--

-- --------------------------------------------------------

--
-- Table structure for table `cau_hoi`
--

CREATE TABLE `cau_hoi` (
  `id` int(11) NOT NULL,
  `cau_hoi` text NOT NULL,
  `phuong_an_A` varchar(255) NOT NULL,
  `phuong_an_B` varchar(255) NOT NULL,
  `phuong_an_C` varchar(255) NOT NULL,
  `phuong_an_D` varchar(255) NOT NULL,
  `dap_an_dung` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `cau_hoi`
--

INSERT INTO `cau_hoi` (`id`, `cau_hoi`, `phuong_an_A`, `phuong_an_B`, `phuong_an_C`, `phuong_an_D`, `dap_an_dung`) VALUES
(1, 'Try and be a little more cheerful because if you don\'t bear .......soon, you\'ll make everyone else miserable.', 'through', 'along', 'up', 'to', 'C'),
(2, 'We were in a small rowing boat and were terrified that the steamer hadn\'t seen us as it was bearing .......on us.', 'down', 'across', 'over', 'under', 'A'),
(3, 'I fully understand your comments and bearing those in ......., I have made the appropriate decision.', 'brain', 'mind', 'thought', 'sense', 'B'),
(4, 'As we have all worked very hard this year, I\'m hoping that our efforts will bear ........', 'produce', 'benefits', 'yields', 'fruit', 'D'),
(5, 'We all have our .......to bear so I should be grateful if you would stop complaining all the time.', 'problems', 'situations', 'crosses', 'results', 'C'),
(6, 'There is really nothing much you can do to stop it and I\'m afraid you\'ll just have to .......and bear it.', 'scorn', 'grin', 'laugh', 'smile', 'B'),
(7, 'I hope you can be patient for a little longer and bear .......me while I try and solve the problem.', 'by', 'on', 'at', 'with', 'D'),
(8, 'She has been proved right in everything she did as the report quite clearly bears ........', 'out', 'to', 'for', 'onto', 'A'),
(9, 'The judge dismissed the new evidence completely because it had no bearing .......the trial.', 'to', 'on', 'into', 'by', 'B'),
(10, 'Quite honestly the two cases are so completely different that they really don\'t bear ........', 'confirmation', 'contrast', 'comprehension', 'comparison', 'D');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cau_hoi`
--
ALTER TABLE `cau_hoi`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cau_hoi`
--
ALTER TABLE `cau_hoi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
