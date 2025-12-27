CREATE TABLE IF NOT EXISTS `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) COLLATE utf8mb4_general_ci NOT NULL,
  `idade` int NOT NULL,
  `peso` double NOT NULL,
  `altura` double NOT NULL,
  `imc` decimal(5,2) NOT NULL,
  `classificacao_imc` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
);