-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 04, 2022 at 10:34 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `demospringboot`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `ID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `User_ID` int(11) NOT NULL,
  `Total_Price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `cart_item`
--

CREATE TABLE `cart_item` (
  `Id` int(11) NOT NULL,
  `Product_ID` int(11) NOT NULL,
  `SIze_ID` int(11) NOT NULL,
  `Color_ID` int(11) NOT NULL,
  `Cart_ID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Total_Price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `color`
--

CREATE TABLE `color` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `color`
--

INSERT INTO `color` (`ID`, `Name`, `Description`) VALUES
(1, 'red', 'Red has a range of symbolic meanings through many different cultures, including life, health, vigor, war, courage, anger, love and religious fervor.'),
(2, 'blue', 'Blue calls to mind feelings of calmness or serenity. It is often described as peaceful, tranquil, secure, and orderly'),
(3, 'green', 'Green is often described as a refreshing and tranquil color');

-- --------------------------------------------------------

--
-- Table structure for table `filter_price`
--

CREATE TABLE `filter_price` (
  `ID` int(11) NOT NULL,
  `Max_price` double NOT NULL,
  `Min_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `filter_price`
--

INSERT INTO `filter_price` (`ID`, `Max_price`, `Min_price`) VALUES
(1, 50, 0),
(2, 100, 50),
(3, 150, 100);

-- --------------------------------------------------------

--
-- Table structure for table `material`
--

CREATE TABLE `material` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `material`
--

INSERT INTO `material` (`ID`, `Name`, `Description`) VALUES
(1, 'cotton', 'Is a fabric woven from natural fibers created from cotton plants and some chemical preservatives. It has the characteristics of light weight, high strength, good sweat absorption, high elasticity, quick washing and drying.'),
(2, 'kaki', 'A fabric that is woven from natural or synthetic fibers woven together. It is harder and thicker, so it is mostly used to sew office uniforms, or cafe uniforms'),
(3, 'jeans', 'Jean fabric, also known as cow cloth, is woven from Duck cotton fabric, which is almost exclusively blue. The characteristics of the fabric are durable, strong, not shrinking. Most are produced from Asian countries including Vietnam.');

-- --------------------------------------------------------

--
-- Table structure for table `oder`
--

CREATE TABLE `oder` (
  `ID` int(11) NOT NULL,
  `Date_Create` date NOT NULL,
  `User_ID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Total_Price` double NOT NULL,
  `Status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `oder`
--

INSERT INTO `oder` (`ID`, `Date_Create`, `User_ID`, `Quantity`, `Total_Price`, `Status`) VALUES
(18, '2022-08-03', 2, 92, 105.93, 'Success');

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `ID` int(11) NOT NULL,
  `Order_ID` int(11) NOT NULL,
  `Product_ID` int(11) NOT NULL,
  `Color_ID` int(11) NOT NULL,
  `Size_ID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`ID`, `Order_ID`, `Product_ID`, `Color_ID`, `Size_ID`, `Quantity`, `Price`) VALUES
(26, 18, 6, 3, 3, 1, 35.31),
(27, 18, 6, 1, 5, 2, 70.62);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Price` double NOT NULL,
  `Img_Url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ID`, `Name`, `Price`, `Img_Url`) VALUES
(6, 'Herschel supply', 35.31, 'images/product-02.jpg'),
(7, 'Esprit Ruffle Shirt', 16.64, 'images/product-01.jpg'),
(8, 'Only Check Trouser', 25.5, 'images/product-03.jpg'),
(9, 'Classic Trench Coat', 75, 'images/product-04.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `product_category_stock`
--

CREATE TABLE `product_category_stock` (
  `ID` int(11) NOT NULL,
  `Product_ID` int(11) NOT NULL,
  `SIze_ID` int(11) NOT NULL,
  `Color_ID` int(11) NOT NULL,
  `Quantity` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product_category_stock`
--

INSERT INTO `product_category_stock` (`ID`, `Product_ID`, `SIze_ID`, `Color_ID`, `Quantity`) VALUES
(1, 6, 3, 3, 19),
(2, 6, 5, 1, 17);

-- --------------------------------------------------------

--
-- Table structure for table `size`
--

CREATE TABLE `size` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `size`
--

INSERT INTO `size` (`ID`, `Name`) VALUES
(1, 'X'),
(2, 'S'),
(3, 'M'),
(4, 'XL'),
(5, 'L'),
(6, 'XXL');

-- --------------------------------------------------------

--
-- Table structure for table `tai_khoan`
--

CREATE TABLE `tai_khoan` (
  `ID` int(11) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `MaNV` int(100) NOT NULL,
  `RoleID` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tai_khoan`
--

INSERT INTO `tai_khoan` (`ID`, `Username`, `Password`, `Email`, `MaNV`, `RoleID`) VALUES
(1, 'dung', '$2a$10$0Rc3yzxdXYXbnFA8/o/MwOigAQIPocLAW4ZLhpcQD7sdex4Qrc/BG', 'hau123pro2@gmail.com', 1, 1),
(2, 'hau', '$2a$12$4RzWJ97EUCbvoUzMh8kvX.sZCmPFBgVgZSqSaa3NLXi5XcN24UCyO', 'hau123pro2@gmail.com', 1, 1),
(3, 'trung', '$2a$10$mplxLRXol3ViLiTKNrvm2.pV1HzZJJgZOGg.unSAolak2fbwGFunu', 'hau123pro2@gmail.com', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`ID`, `Name`, `Description`) VALUES
(1, 't-shirt', 'A T-shirt, or tee, is a style of fabric shirt named after the T shape of its body and sleeves. \r\n'),
(2, 'shirt', 'A shirt is a cloth garment for the upper body (from the neck to the waist).'),
(3, 'dress', 'A dress (also known as a frock or a gown) is a garment traditionally worn by women or girls consisting of a skirt with an attached bodice (or a matching bodice giving the effect of a one-piece garment)'),
(4, 'jeans', 'Jeans are a type of pants traditionally made from denim (a kind of cotton fabric). The word most commonly refers to denim blue jeans'),
(5, 'jacket', ' a garment for the upper body usually having a front opening, collar, lapels, sleeves, and pockets');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `UserID` (`User_ID`);

--
-- Indexes for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Color_ID` (`Color_ID`),
  ADD KEY `SIze_ID` (`SIze_ID`),
  ADD KEY `Cart_ID` (`Cart_ID`),
  ADD KEY `Product_ID` (`Product_ID`);

--
-- Indexes for table `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `filter_price`
--
ALTER TABLE `filter_price`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `oder`
--
ALTER TABLE `oder`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `User_ID` (`User_ID`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Product_ID` (`Product_ID`),
  ADD KEY `Color_ID` (`Color_ID`),
  ADD KEY `Order_ID` (`Order_ID`),
  ADD KEY `Size_ID` (`Size_ID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `product_category_stock`
--
ALTER TABLE `product_category_stock`
  ADD PRIMARY KEY (`ID`,`Product_ID`,`SIze_ID`,`Color_ID`),
  ADD KEY `Color_ID` (`Color_ID`),
  ADD KEY `SIze_ID` (`SIze_ID`),
  ADD KEY `Product_ID` (`Product_ID`);

--
-- Indexes for table `size`
--
ALTER TABLE `size`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tai_khoan`
--
ALTER TABLE `tai_khoan`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `cart_item`
--
ALTER TABLE `cart_item`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `color`
--
ALTER TABLE `color`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `filter_price`
--
ALTER TABLE `filter_price`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `material`
--
ALTER TABLE `material`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `oder`
--
ALTER TABLE `oder`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `product_category_stock`
--
ALTER TABLE `product_category_stock`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `size`
--
ALTER TABLE `size`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tai_khoan`
--
ALTER TABLE `tai_khoan`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `type`
--
ALTER TABLE `type`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `tai_khoan` (`ID`);

--
-- Constraints for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`Color_ID`) REFERENCES `color` (`ID`),
  ADD CONSTRAINT `cart_item_ibfk_4` FOREIGN KEY (`SIze_ID`) REFERENCES `size` (`ID`),
  ADD CONSTRAINT `cart_item_ibfk_5` FOREIGN KEY (`Cart_ID`) REFERENCES `cart` (`ID`),
  ADD CONSTRAINT `cart_item_ibfk_6` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`ID`);

--
-- Constraints for table `oder`
--
ALTER TABLE `oder`
  ADD CONSTRAINT `oder_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `tai_khoan` (`ID`);

--
-- Constraints for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`ID`),
  ADD CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`Color_ID`) REFERENCES `color` (`ID`),
  ADD CONSTRAINT `order_detail_ibfk_3` FOREIGN KEY (`Order_ID`) REFERENCES `oder` (`ID`),
  ADD CONSTRAINT `order_detail_ibfk_4` FOREIGN KEY (`Size_ID`) REFERENCES `size` (`ID`);

--
-- Constraints for table `product_category_stock`
--
ALTER TABLE `product_category_stock`
  ADD CONSTRAINT `product_category_stock_ibfk_2` FOREIGN KEY (`Color_ID`) REFERENCES `color` (`ID`),
  ADD CONSTRAINT `product_category_stock_ibfk_3` FOREIGN KEY (`SIze_ID`) REFERENCES `size` (`ID`),
  ADD CONSTRAINT `product_category_stock_ibfk_4` FOREIGN KEY (`Product_ID`) REFERENCES `product` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
