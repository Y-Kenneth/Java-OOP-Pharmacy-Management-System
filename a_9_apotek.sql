-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 15, 2025 at 06:38 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `a_9_apotek`
--

-- --------------------------------------------------------

--
-- Table structure for table `apoteker`
--

CREATE TABLE `apoteker` (
  `id_apoteker` int(11) NOT NULL,
  `nama_apoteker` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `gaji` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `apoteker`
--

INSERT INTO `apoteker` (`id_apoteker`, `nama_apoteker`, `email`, `gaji`, `username`, `password`) VALUES
(1, 'Niko', 'niko@gmail.com', 1000000, 'NikoNiko', 'Niko123'),
(2, 'Matthew', 'matthew@gmail.com', 2000000, 'MatthewMatthew', 'Matthew123'),
(4, 'yakhekenneth', 'yakhekenneth@gmail.com', 4000000, 'yakhekennethyakhekenneth', 'yakhekenneth123'),
(5, 'Sonik', 'Sonik@gmail.com', 5500000, 'SonikSonik', 'Sonik123'),
(6, 'Beda', 'Beda@gmail.com', 6000000, 'BedaBeda', 'Beda123'),
(7, 'a', 'a@gmail.com', 1500000, 'aa', 'a123'),
(8, 'Steve', 'Steve@gmail.com', 7500000, 'SteveSteve', 'Steve123');

-- --------------------------------------------------------

--
-- Table structure for table `detail_pembuatan_resep`
--

CREATE TABLE `detail_pembuatan_resep` (
  `id_resep` int(11) NOT NULL,
  `id_produk` varchar(255) NOT NULL,
  `jumlah_resep` int(11) NOT NULL,
  `aturan_pakai` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detail_pembuatan_resep`
--

INSERT INTO `detail_pembuatan_resep` (`id_resep`, `id_produk`, `jumlah_resep`, `aturan_pakai`) VALUES
(3, 'P1', 1, 'Minum saat Pusing'),
(3, 'P8', 0, 'Minum setelah diare'),
(4, 'P1', 1, 'Minum bebas'),
(4, 'P8', 2, 'Minum setelah diare'),
(5, 'P1', 1, 'Minum setelah Pusing'),
(5, 'P3', 2, 'Tidak usah Diminum'),
(5, 'P8', 3, 'Buang saja'),
(6, 'P8', 1, 'Diminum bebas');

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_produk` varchar(255) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `sub_total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id_transaksi`, `id_produk`, `jumlah`, `sub_total`) VALUES
(4, 'P0', 11, 165000),
(4, 'P1', 5, 50000),
(7, 'P0', 12, 180000),
(7, 'P6', 5, 177500),
(8, 'P5', 59, 1180000),
(8, 'P6', 21, 745500),
(9, 'P0', 3, 45000),
(9, 'P4', 100, 800000),
(10, 'P0', 12, 180000),
(10, 'P6', 5, 177500),
(10, 'P7', 6, 54000),
(11, 'P8', 150, 10500000),
(14, 'P1', 5, 50000),
(14, 'P7', 15, 135000),
(15, 'P1', 1, 10000),
(16, 'P1', 6, 60000),
(16, 'P8', 2, 140000),
(17, 'P1', 4, 40000),
(17, 'P3', 5, 127500),
(17, 'P8', 3, 210000),
(18, 'P7', 3, 27000),
(18, 'P8', 5, 350000),
(21, 'P2', 2, 11000),
(21, 'P7', 1, 9000),
(22, 'P0', 2, 30000),
(22, 'P2', 1, 5500),
(22, 'P5', 1, 20000),
(23, 'P6', 1, 35500),
(24, 'P4', 3, 24000);

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `id_produk` varchar(255) NOT NULL,
  `jenis_resep` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`id_produk`, `jenis_resep`) VALUES
('P0', 'wajib'),
('P1', 'Wajib'),
('P2', 'Bebas'),
('P3', 'Wajib'),
('P4', 'Bebas'),
('P8', 'Wajib');

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(5) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `umur` int(11) NOT NULL,
  `notelp` varchar(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama`, `umur`, `notelp`) VALUES
(2, 'putri', 12, '0813'),
(3, 'memet', 13, '0814'),
(4, 'vincent', 14, '0815'),
(5, 'dio', 15, '+62816'),
(6, 'Bambang', 16, '+62192'),
(7, 'Widhi', 20, '0819'),
(8, 'Jono', 13, '0813'),
(9, 'Mukti', 25, '0825'),
(10, 'Dandy', 30, '0830'),
(11, 'Michelle', 17, '+6288'),
(12, 'Chany', 19, '+6278'),
(13, 'Puput', 16, '0816');

-- --------------------------------------------------------

--
-- Table structure for table `pembuatan_resep`
--

CREATE TABLE `pembuatan_resep` (
  `id_resep` int(11) NOT NULL,
  `id_pelanggan` int(5) NOT NULL,
  `id_apoteker` int(11) NOT NULL,
  `nama_dokter` varchar(255) NOT NULL,
  `tanggal_resep` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembuatan_resep`
--

INSERT INTO `pembuatan_resep` (`id_resep`, `id_pelanggan`, `id_apoteker`, `nama_dokter`, `tanggal_resep`) VALUES
(1, 2, 7, 'Dokter Susilo Bambang Yudoyono', '2025-06-14'),
(3, 10, 2, 'Dokter GG Gaming', '2025-11-13'),
(4, 11, 5, 'Dokter Jojo', '2025-06-15'),
(5, 12, 6, 'Dokter Cong', '2025-06-15'),
(6, 13, 4, 'Dokter Tokseka', '2025-06-15');

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id_produk` varchar(255) NOT NULL,
  `nama_produk` varchar(255) NOT NULL,
  `jenis` varchar(255) NOT NULL,
  `stok` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `tgl_kadaluarsa` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id_produk`, `nama_produk`, `jenis`, `stok`, `harga`, `tgl_kadaluarsa`) VALUES
('P0', 'LiverPrime', 'Vitamin', 138, 15000, '2025-06-20'),
('P1', 'Panadol', 'Obat', 62, 10000, '2025-12-13'),
('P2', 'Paracetamol', 'Obat', 0, 5500, '2025-12-13'),
('P3', 'Amoxilin', 'Obat', 311, 25500, '2025-08-10'),
('P4', 'Antasida Doen', 'Obat', 697, 8000, '2027-03-15'),
('P5', 'Enervon C', 'Vitamin', 458, 20000, '2026-12-15'),
('P6', 'Neurobion Forte', 'Vitamin', 510, 35500, '2026-11-10'),
('P7', 'Buahvita', 'Vitamin', 28, 9000, '2027-06-05'),
('P8', 'Imodium', 'Obat', 190, 70000, '2028-06-17');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_pelanggan` int(5) NOT NULL,
  `id_apoteker` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_pelanggan`, `id_apoteker`, `tanggal`, `total_harga`) VALUES
(4, 2, 7, '2025-06-14', 215000),
(7, 3, 1, '2025-06-14', 357500),
(8, 6, 7, '2025-06-14', 1925500),
(9, 8, 7, '2025-06-14', 845000),
(10, 8, 1, '2025-06-14', 411500),
(11, 10, 4, '2025-11-13', 10500000),
(14, 8, 7, '2025-06-15', 185000),
(15, 10, 2, '2025-06-15', 10000),
(16, 11, 5, '2025-06-15', 200000),
(17, 12, 6, '2025-06-15', 377500),
(18, 13, 7, '2025-06-15', 377000),
(21, 5, 7, '2025-06-15', 20000),
(22, 2, 7, '2025-06-15', 55500),
(23, 5, 6, '2025-06-15', 35500),
(24, 5, 6, '2025-06-24', 24000);

-- --------------------------------------------------------

--
-- Table structure for table `vitamin`
--

CREATE TABLE `vitamin` (
  `id_produk` varchar(255) NOT NULL,
  `cara_konsumsi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vitamin`
--

INSERT INTO `vitamin` (`id_produk`, `cara_konsumsi`) VALUES
('P0', 'Diminum'),
('P5', '1 tablet sehari'),
('P6', '3x sehari setelah makan'),
('P7', 'Diminum ampe mati');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `apoteker`
--
ALTER TABLE `apoteker`
  ADD PRIMARY KEY (`id_apoteker`);

--
-- Indexes for table `detail_pembuatan_resep`
--
ALTER TABLE `detail_pembuatan_resep`
  ADD PRIMARY KEY (`id_resep`,`id_produk`);

--
-- Indexes for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id_transaksi`,`id_produk`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD UNIQUE KEY `id_produk` (`id_produk`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indexes for table `pembuatan_resep`
--
ALTER TABLE `pembuatan_resep`
  ADD PRIMARY KEY (`id_resep`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- Indexes for table `vitamin`
--
ALTER TABLE `vitamin`
  ADD UNIQUE KEY `id_produk` (`id_produk`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `apoteker`
--
ALTER TABLE `apoteker`
  MODIFY `id_apoteker` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `id_pelanggan` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `pembuatan_resep`
--
ALTER TABLE `pembuatan_resep`
  MODIFY `id_resep` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_pembuatan_resep`
--
ALTER TABLE `detail_pembuatan_resep`
  ADD CONSTRAINT `fk_detailpembuatanresep_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_detailpembuatanresep_resep` FOREIGN KEY (`id_resep`) REFERENCES `pembuatan_resep` (`id_resep`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `fk_detailtransaksi_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_detailtransaksi_transaksi` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `obat`
--
ALTER TABLE `obat`
  ADD CONSTRAINT `fk_obat_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pembuatan_resep`
--
ALTER TABLE `pembuatan_resep`
  ADD CONSTRAINT `fk_resep_apoteker` FOREIGN KEY (`id_apoteker`) REFERENCES `apoteker` (`id_apoteker`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_resep_pelanggan` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_transaksi_apoteker` FOREIGN KEY (`id_apoteker`) REFERENCES `apoteker` (`id_apoteker`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_transaksi_pelanggan` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vitamin`
--
ALTER TABLE `vitamin`
  ADD CONSTRAINT `fk_vitamin_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
