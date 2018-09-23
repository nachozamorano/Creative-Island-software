-- phpMyAdmin SQL Dump
-- version 3.4.4
-- http://www.phpmyadmin.net
--
-- Servidor: mysql.webcindario.com
-- Tiempo de generación: 23-09-2018 a las 22:00:17
-- Versión del servidor: 5.6.39
-- Versión de PHP: 5.6.35-1+0~20180405085409.12+jessie~1.gbpaa4624

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `ecoruta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `id_categoria` int(10) NOT NULL AUTO_INCREMENT,
  `nombre_categoria` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `nombre_categoria`) VALUES
(1, 'Hogar y electrodomesticos'),
(2, 'Instrumentos musicales'),
(3, 'Tecnologia'),
(4, 'Deportes y aire libre'),
(5, 'Herramientas e Industria'),
(6, 'Juguetes y Bebes'),
(7, 'Libros'),
(8, 'Ropa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Estado`
--

CREATE TABLE IF NOT EXISTS `Estado` (
  `id_estado` int(5) NOT NULL,
  `nombre_estado` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `Estado`
--

INSERT INTO `Estado` (`id_estado`, `nombre_estado`) VALUES
(1, 'Libre'),
(2, 'Reservado'),
(3, 'Donado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_objeto`
--

CREATE TABLE IF NOT EXISTS `estado_objeto` (
  `id_estado_objeto` int(10) NOT NULL,
  `nombre_estado_objeto` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_estado_objeto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `estado_objeto`
--

INSERT INTO `estado_objeto` (`id_estado_objeto`, `nombre_estado_objeto`) VALUES
(1, 'Nuevo'),
(2, 'Seminuevo'),
(3, 'Usado'),
(4, 'Con detalle');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero`
--

CREATE TABLE IF NOT EXISTS `genero` (
  `id_genero` int(10) NOT NULL,
  `nombre_genero` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `genero`
--

INSERT INTO `genero` (`id_genero`, `nombre_genero`) VALUES
(1, 'Masculino'),
(2, 'Femenino'),
(3, 'No aplica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `objeto`
--

CREATE TABLE IF NOT EXISTS `objeto` (
  `id_objeto` int(10) NOT NULL AUTO_INCREMENT,
  `id_propietario` int(10) NOT NULL,
  `nombre_objeto` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `imagen` text CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `subcategoria` int(10) NOT NULL,
  `fecha_publicacion` date NOT NULL,
  `fecha_expiracion` date NOT NULL,
  `id_estado_objeto` int(10) NOT NULL,
  `cantidad` int(10) NOT NULL,
  `id_unidad` int(10) NOT NULL,
  `Latitud` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `Longitud` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `desde` time NOT NULL,
  `hasta` time NOT NULL,
  `direccion` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_objeto`),
  KEY `id_propietario` (`id_propietario`),
  KEY `subcategoria` (`subcategoria`),
  KEY `id_estado_objeto` (`id_estado_objeto`),
  KEY `id_unidad` (`id_unidad`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=179 ;

--
-- Volcado de datos para la tabla `objeto`
--

INSERT INTO `objeto` (`id_objeto`, `id_propietario`, `nombre_objeto`, `descripcion`, `imagen`, `subcategoria`, `fecha_publicacion`, `fecha_expiracion`, `id_estado_objeto`, `cantidad`, `id_unidad`, `Latitud`, `Longitud`, `desde`, `hasta`, `direccion`) VALUES
(1, 1, 'Silla verdosa', 'silla con color verdoso', 'https://ecoruta.webcindario.com/fotosObjetos/168.png', 1, '2018-09-02', '0000-00-00', 1, 12, 1, '13', '123', '00:00:00', '00:00:00', 'mi casaaaaaa'),
(165, 2, 'mesa negra', 'mesa de color negro', 'https://ecoruta.webcindario.com/fotosObjetos/168.png', 2, '0000-00-00', '0000-00-00', 1, 0, 1, '', '', '00:00:00', '00:00:00', ''),
(171, 1, 'ÑÑÑÑÑÑÑíííííííiíí', 'ÑÑÑÑÑÑÑÑÑÑÑ', 'https://ecoruta.webcindario.com/fotosObjetos/168.png', 1, '2018-09-02', '2018-09-26', 1, 1, 1, '', '', '00:00:00', '00:00:00', 'ÑÑÑÑÑÑÑÑÑñññññññ'),
(175, 1, 'silla oscu', 'sdadasdas', 'https://ecoruta.webcindario.com/fotosObjetos/175.png', 6, '0000-00-00', '0000-00-00', 1, 1, 1, '-32.770540200968455', '-71.5352238714695', '00:00:00', '00:00:00', 'San Mart'),
(176, 1, 'silla oscu', 'sdadasdas', 'https://ecoruta.webcindario.com/fotosObjetos/173.png', 6, '0000-00-00', '0000-00-00', 1, 1, 1, '-32.77027773747207', '-71.53539318591356', '00:00:00', '00:00:00', 'Vicu'),
(177, 1, 'silla oscu', 'sdadasdas', 'https://ecoruta.webcindario.com/fotosObjetos/177.png', 6, '0000-00-00', '0000-00-00', 1, 1, 1, '12321', '1312', '00:00:00', '00:00:00', 'Vicu'),
(178, 1, 'cnxnzkkv', 'jfksjgksdk', 'https://ecoruta.webcindario.com/fotosObjetos/178.png', 6, '2018-09-23', '2018-09-23', 1, 1, 1, '-32.770725701017895', '-71.53510082513094', '02:42:00', '02:42:00', 'San Mart');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subcategoria`
--

CREATE TABLE IF NOT EXISTS `subcategoria` (
  `id_subcategoria` int(10) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(10) NOT NULL,
  `nombre` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_subcategoria`),
  KEY `id_categoria` (`id_categoria`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=35 ;

--
-- Volcado de datos para la tabla `subcategoria`
--

INSERT INTO `subcategoria` (`id_subcategoria`, `id_categoria`, `nombre`) VALUES
(1, 3, 'Celulares y telefonia'),
(2, 3, 'Computacion'),
(3, 3, 'Electronica, audio y video'),
(4, 3, 'Consolas y videojuegos'),
(5, 3, 'Celulares y telefonia'),
(6, 1, 'Electrodomesticos'),
(7, 1, 'Terraza y jardin'),
(8, 1, 'Decoracion cocina'),
(9, 1, 'Dormitorio'),
(10, 1, 'Living'),
(11, 2, 'Guitarras'),
(12, 2, 'Baterias y percucion \r\n'),
(13, 2, 'Teclados y pianos\r\n'),
(14, 2, 'Amplificadores\r\n'),
(15, 2, 'Bajo\r\n'),
(16, 2, 'Efectos de sonido\r\n'),
(17, 2, 'Violines'),
(18, 2, 'Saxos\r\n\r\n'),
(19, 2, 'Controladores midi\r\n\r\n'),
(20, 4, 'Bicicletas y ciclismo'),
(21, 4, 'Camping y pesca'),
(22, 4, 'Futbol'),
(23, 4, 'Patinaje'),
(24, 4, 'Aerobics y fitness'),
(25, 4, 'Skateboard'),
(26, 4, 'Deportes extremos'),
(27, 4, 'Deportes acuaticos'),
(28, 4, 'Trekking y montañismo'),
(29, 5, 'Herramientas'),
(30, 5, 'Construccion'),
(31, 5, 'Oficinas'),
(32, 5, 'Gastronomia'),
(33, 5, 'Fuentes de energia'),
(34, 5, 'Industria textil');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transaccion`
--

CREATE TABLE IF NOT EXISTS `transaccion` (
  `id_transaccion` int(10) NOT NULL AUTO_INCREMENT,
  `id_objeto` int(10) NOT NULL,
  `id_dueno` int(10) NOT NULL,
  `id_estado` int(5) NOT NULL,
  `fecha_retiro` date NOT NULL,
  `id_interesado` int(10) NOT NULL,
  `nombre_interesado` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_transaccion`),
  KEY `id_objeto` (`id_objeto`),
  KEY `id_dueno` (`id_dueno`),
  KEY `id_estado` (`id_estado`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=63 ;

--
-- Volcado de datos para la tabla `transaccion`
--

INSERT INTO `transaccion` (`id_transaccion`, `id_objeto`, `id_dueno`, `id_estado`, `fecha_retiro`, `id_interesado`, `nombre_interesado`) VALUES
(52, 165, 2, 2, '0000-00-00', 1, 'i.zamoranourra'),
(59, 175, 1, 1, '0000-00-00', 0, ''),
(60, 176, 1, 1, '0000-00-00', 0, ''),
(61, 177, 1, 1, '0000-00-00', 0, ''),
(62, 178, 1, 1, '0000-00-00', 0, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad_medida`
--

CREATE TABLE IF NOT EXISTS `unidad_medida` (
  `id_unidad` int(10) NOT NULL,
  `unidad_medida` varchar(10) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id_unidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `unidad_medida`
--

INSERT INTO `unidad_medida` (`id_unidad`, `unidad_medida`) VALUES
(1, 'Unidades'),
(2, 'Kilogramos'),
(3, 'Litros');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(10) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `img_perfil` text CHARACTER SET utf8 COLLATE utf8_spanish_ci,
  `correo` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `telefono_movil` int(10) NOT NULL,
  `telefono_fijo` int(10) NOT NULL,
  `id_genero` int(10) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `id_genero` (`id_genero`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=90 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `password`, `img_perfil`, `correo`, `telefono_movil`, `telefono_fijo`, `id_genero`) VALUES
(1, 'i.zamoranourra', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'i.zamoranourra@afhh', 987507658, 0, 1),
(2, 'Pedro', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'pedro@gmail.com', 12341234, 123, 3),
(59, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(60, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(61, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(62, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(63, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(64, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(65, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(66, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(67, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(68, '', '', 'https://ecoruta.webcindario.com/uploads/1.png', '', 0, 0, NULL),
(69, '', '', 'https://ecoruta.webcindario.com/uploads/1.png', '', 0, 0, NULL),
(70, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/1.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(71, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/71.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(72, 'Fernando', '', 'https://ecoruta.webcindario.com/uploads/72.png', 'micorreo@gmail.com', 321, 123, 3),
(73, '', '', 'https://ecoruta.webcindario.com/uploads/1.png', '', 0, 0, NULL),
(74, '', '', 'https://ecoruta.webcindario.com/uploads/1.png', '', 0, 0, NULL),
(75, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/75.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(76, 'kdkggm', 'tkdlkgkf', 'https://ecoruta.webcindario.com/uploads/76.png', 'flflflgm', 0, 956495959, NULL),
(77, 'kdkggm', 'tkdlkgkf', 'https://ecoruta.webcindario.com/uploads/77.png', 'flflflgm', 0, 956495959, NULL),
(78, 'basti', '1234', 'https://ecoruta.webcindario.com/uploads/78.png', 'basti', 0, 2147483647, NULL),
(79, 'Felipe2', '1234', 'https://ecoruta.webcindario.com/uploads/79.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(80, 'Bastian ', '1234', 'https://ecoruta.webcindario.com/uploads/80.png', 'dsdfsdfdsf', 0, 123123132, NULL),
(81, 'jfkdngkdk', 'fkrke', 'https://ecoruta.webcindario.com/uploads/81.png', 'rkekfkr', 0, 989468, NULL),
(82, 'gidkfk', '7483xxk', 'https://ecoruta.webcindario.com/uploads/82.png', 'jxnfkekg', 0, 656898, NULL),
(83, 've este basti', 'giirk', 'https://ecoruta.webcindario.com/uploads/83.png', 'kfk3oegn', 0, 356586, NULL),
(84, 'este si', '123', 'https://ecoruta.webcindario.com/uploads/84.png', 'fkdif8i3', 0, 353856, NULL),
(85, 'tiriif3k', '12', 'https://ecoruta.webcindario.com/uploads/85.png', '12', 0, 65386495, NULL),
(86, '13', '13', 'https://ecoruta.webcindario.com/uploads/86.png', '13', 0, 13, NULL),
(89, '15', '15', 'https://ecoruta.webcindario.com/uploads/89.png', '15', 15, 15, 2);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `objeto`
--
ALTER TABLE `objeto`
  ADD CONSTRAINT `objeto_ibfk_1` FOREIGN KEY (`subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `objeto_ibfk_2` FOREIGN KEY (`id_estado_objeto`) REFERENCES `estado_objeto` (`id_estado_objeto`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `objeto_ibfk_3` FOREIGN KEY (`id_unidad`) REFERENCES `unidad_medida` (`id_unidad`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `subcategoria`
--
ALTER TABLE `subcategoria`
  ADD CONSTRAINT `subcategoria_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `transaccion`
--
ALTER TABLE `transaccion`
  ADD CONSTRAINT `transaccion_ibfk_1` FOREIGN KEY (`id_dueno`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaccion_ibfk_3` FOREIGN KEY (`id_estado`) REFERENCES `Estado` (`id_estado`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `transaccion_ibfk_4` FOREIGN KEY (`id_objeto`) REFERENCES `objeto` (`id_objeto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id_genero`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
