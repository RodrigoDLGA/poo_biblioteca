-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 05-12-2020 a las 23:33:15
-- Versión del servidor: 5.7.31
-- Versión de PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `biblioteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

DROP TABLE IF EXISTS `inventario`;
CREATE TABLE IF NOT EXISTS `inventario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `cantidad` int(11) DEFAULT '1',
  `estatus` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'Disponible',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`id`, `titulo`, `cantidad`, `estatus`) VALUES
(14, 'Python Tricks: A Buffet of Awesome Python Features', 1, 'Disponible'),
(13, 'Learning Python, 5th Edition', 0, 'No disponible'),
(12, 'Python Crash Course', 1, 'Disponible');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

DROP TABLE IF EXISTS `libros`;
CREATE TABLE IF NOT EXISTS `libros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `autor` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `editorial` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `tema` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `anio` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`id`, `titulo`, `autor`, `editorial`, `tema`, `anio`) VALUES
(22, 'Python Tricks: A Buffet of Awesome Python Features', 'Dan Bader', 'Dan Bader (dbader.org)', 'Programacion', 2017),
(21, 'Learning Python, 5th Edition', 'Mark Lutz', 'OReilly Media', 'Programacion', 2013),
(20, 'Python Crash Course', 'Eric Matthes', 'No Starch Press', 'Programacion', 2015);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
CREATE TABLE IF NOT EXISTS `prestamos` (
  `idLibro` int(11) NOT NULL,
  `nombre_alumno` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `matricula` int(11) NOT NULL,
  PRIMARY KEY (`idLibro`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `prestamos`
--

INSERT INTO `prestamos` (`idLibro`, `nombre_alumno`, `matricula`) VALUES
(22, 'Rodrigo de la Garza Arredondo', 1830016),
(21, 'Jorge Eduardo Monita', 1830014),
(20, 'Luis Antonio', 1830006);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(2, 'user', 'pass'),
(3, 'rodrigo', '1234'),
(4, 'monita', 'poo');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
