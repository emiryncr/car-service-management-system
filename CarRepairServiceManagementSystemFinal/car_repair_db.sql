-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 07 Haz 2025, 21:28:04
-- Sunucu sürümü: 10.4.32-MariaDB
-- PHP Sürümü: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `car_repair_db`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `address` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `customers`
--

INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `phone`, `email`, `address`, `created_at`, `updated_at`) VALUES
(1, 'Emir', 'Yeniçeri', '0533 875 0006', 'emir@gmail.com', 'Dörtyol', '2025-06-07 16:04:04', '2025-06-07 16:04:04'),
(2, 'John', 'Smith', '123-123-123', 'john.smith@email.com', '123 Main St, Anytown, ST 12345', '2025-06-07 16:04:04', '2025-06-07 16:04:04');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `inventories`
--

CREATE TABLE `inventories` (
  `part_id` int(11) NOT NULL,
  `part_name` varchar(100) NOT NULL,
  `part_number` varchar(50) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `quantity_in_stock` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `inventories`
--

INSERT INTO `inventories` (`part_id`, `part_name`, `part_number`, `category`, `quantity_in_stock`) VALUES
(1, 'Synthetic Oil', 'OIL-SYN-5W30', 'Fluids', 65),
(2, 'Oil Fluid', '1132', 'Fluids', 10);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `order_items`
--

CREATE TABLE `order_items` (
  `order_item_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `part_id` int(11) DEFAULT NULL,
  `service_description` text NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `line_total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `order_items`
--

INSERT INTO `order_items` (`order_item_id`, `order_id`, `part_id`, `service_description`, `quantity`, `unit_price`, `line_total`) VALUES
(1, 1, 2, 'Fluid service', 2, 50.00, 100.00),
(2, 2, 2, 'Oil change service', 4, 49.00, 196.00);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `service_orders`
--

CREATE TABLE `service_orders` (
  `order_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  `date_received` timestamp NULL DEFAULT current_timestamp(),
  `date_completed` timestamp NULL DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `description` text DEFAULT NULL,
  `total_cost` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `service_orders`
--

INSERT INTO `service_orders` (`order_id`, `vehicle_id`, `date_received`, `date_completed`, `status`, `description`, `total_cost`) VALUES
(1, 1, '2025-06-05 10:05:57', NULL, 'In Progress', 'Change oil fluid', 100.00),
(2, 3, '2025-06-07 16:02:54', '2025-06-07 16:03:08', 'Completed', 'Regular maintenance and oil change', 255.00);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `vehicles`
--

CREATE TABLE `vehicles` (
  `vehicle_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `make` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `vin` varchar(50) DEFAULT NULL,
  `license_plate` varchar(20) DEFAULT NULL,
  `color` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `vehicles`
--

INSERT INTO `vehicles` (`vehicle_id`, `customer_id`, `make`, `model`, `year`, `vin`, `license_plate`, `color`) VALUES
(1, 1, 'Toyota', 'Corolla', 2025, '12HJ12H3H12', 'SE101', 'Gray'),
(2, 2, 'Mazda', 'Demio', 2020, 'KJH123HJ453JHK', 'JZ803', 'Blue'),
(3, 2, 'Toyota', 'Camry', 2022, '4T1B11HK1KU123456', 'ABC123', 'Silver');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Tablo için indeksler `inventories`
--
ALTER TABLE `inventories`
  ADD PRIMARY KEY (`part_id`);

--
-- Tablo için indeksler `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_item_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `part_id` (`part_id`);

--
-- Tablo için indeksler `service_orders`
--
ALTER TABLE `service_orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `vehicle_id` (`vehicle_id`);

--
-- Tablo için indeksler `vehicles`
--
ALTER TABLE `vehicles`
  ADD PRIMARY KEY (`vehicle_id`);

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `service_orders` (`order_id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`part_id`) REFERENCES `inventories` (`part_id`);

--
-- Tablo kısıtlamaları `service_orders`
--
ALTER TABLE `service_orders`
  ADD CONSTRAINT `service_orders_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
