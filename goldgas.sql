-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 14-Out-2016 às 22:56
-- Versão do servidor: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `goldgas`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `telefone` char(10) NOT NULL,
  `tipocliente` varchar(10) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `idendereco` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`idcliente`),
  KEY `FK_cliente_1` (`idendereco`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`idcliente`, `nome`, `telefone`, `tipocliente`, `email`, `idendereco`, `status`) VALUES
(1, 'Alexandrer', '34910000', 'físico', 'ale@ifsp.com', 3, 'ativo');

-- --------------------------------------------------------

--
-- Estrutura da tabela `clientefisico`
--

CREATE TABLE IF NOT EXISTS `clientefisico` (
  `idcliente` int(11) NOT NULL,
  `cpf` char(11) NOT NULL,
  PRIMARY KEY (`idcliente`),
  KEY `fk_clientefisico_cliente1_idx` (`idcliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `clientefisico`
--

INSERT INTO `clientefisico` (`idcliente`, `cpf`) VALUES
(1, '12345678900');

-- --------------------------------------------------------

--
-- Estrutura da tabela `clientejuridico`
--

CREATE TABLE IF NOT EXISTS `clientejuridico` (
  `idcliente` int(11) NOT NULL,
  `cnpj` char(14) NOT NULL,
  PRIMARY KEY (`idcliente`),
  KEY `fk_clientejuridico_cliente1_idx` (`idcliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `compra`
--

CREATE TABLE IF NOT EXISTS `compra` (
  `idcompra` int(11) NOT NULL AUTO_INCREMENT,
  `idfornecedor` int(11) NOT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idcompra`),
  KEY `fk_compra_fornecedor1_idx` (`idfornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

CREATE TABLE IF NOT EXISTS `endereco` (
  `idendereco` int(11) NOT NULL AUTO_INCREMENT,
  `rua` varchar(45) NOT NULL,
  `bairro` varchar(45) NOT NULL,
  `referencia` varchar(45) DEFAULT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `cidade` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`idendereco`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `endereco`
--

INSERT INTO `endereco` (`idendereco`, `rua`, `bairro`, `referencia`, `complemento`, `cidade`, `estado`) VALUES
(1, 'rua 1', 'bairro 2', NULL, NULL, 'cidade 3', 'estado 4'),
(2, 'Av. Brasil', 'Centro', '', '', 'Capivari', 'sp'),
(3, 'Av. Brasil', 'Centro', '', '', 'Capivari', 'sp');

-- --------------------------------------------------------

--
-- Estrutura da tabela `entrega`
--

CREATE TABLE IF NOT EXISTS `entrega` (
  `identrega` int(11) NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `descrição` varchar(200) DEFAULT NULL,
  `idfuncionario` int(11) NOT NULL,
  `idveiculo` int(11) NOT NULL,
  `idpedido` int(11) NOT NULL,
  PRIMARY KEY (`identrega`,`hora`),
  KEY `fk_entrega_funcionario1_idx` (`idfuncionario`),
  KEY `fk_entrega_veiculo1_idx` (`idveiculo`),
  KEY `fk_entrega_pedido1_idx` (`idpedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `fornecedor`
--

CREATE TABLE IF NOT EXISTS `fornecedor` (
  `idfornecedor` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cnpj` char(14) NOT NULL,
  `email` varchar(45) NOT NULL,
  `telefone` char(10) NOT NULL,
  `idendereco` int(11) NOT NULL,
  PRIMARY KEY (`idfornecedor`),
  KEY `FK_fornecedor_1` (`idendereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario`
--

CREATE TABLE IF NOT EXISTS `funcionario` (
  `idfuncionario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `rg` char(9) NOT NULL,
  `datanascimento` date NOT NULL,
  `cnh` char(11) NOT NULL,
  `login` varchar(45) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `cpf` char(11) NOT NULL,
  `cargo` varchar(45) NOT NULL,
  `idendereco` int(11) NOT NULL,
  PRIMARY KEY (`idfuncionario`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  UNIQUE KEY `rg_UNIQUE` (`rg`),
  UNIQUE KEY `cnh_UNIQUE` (`cnh`),
  KEY `FK_funcionario_1` (`idendereco`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `funcionario`
--

INSERT INTO `funcionario` (`idfuncionario`, `nome`, `rg`, `datanascimento`, `cnh`, `login`, `senha`, `cpf`, `cargo`, `idendereco`) VALUES
(1, 'admin', '123456789', '1998-08-22', '12345678901', 'admin', 'admin123', '12345678901', 'gerente', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario_visualiza_historico`
--

CREATE TABLE IF NOT EXISTS `funcionario_visualiza_historico` (
  `idfuncionario` int(11) NOT NULL,
  PRIMARY KEY (`idfuncionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `historico`
--

CREATE TABLE IF NOT EXISTS `historico` (
  `idHistorico` int(11) NOT NULL AUTO_INCREMENT,
  `descrição` varchar(200) DEFAULT NULL,
  `idfuncionario` int(11) NOT NULL,
  `idpedido` int(11) NOT NULL,
  PRIMARY KEY (`idHistorico`),
  KEY `fk_historico_funcionario1_idx` (`idfuncionario`),
  KEY `fk_historico_pedido1_idx` (`idpedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `itens_compra`
--

CREATE TABLE IF NOT EXISTS `itens_compra` (
  `idcompra` int(11) NOT NULL,
  `quantidade` varchar(45) NOT NULL,
  `valorcompra` float NOT NULL,
  `idproduto` int(11) NOT NULL,
  PRIMARY KEY (`idcompra`,`idproduto`),
  KEY `fk_itens_compra_compra1_idx` (`idcompra`),
  KEY `fk_itens_compra_produto1` (`idproduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `itens_pedido`
--

CREATE TABLE IF NOT EXISTS `itens_pedido` (
  `quantidade` int(11) NOT NULL,
  `valorvenda` varchar(45) NOT NULL,
  `idpedido` int(11) NOT NULL,
  `idProduto` int(11) NOT NULL,
  PRIMARY KEY (`idpedido`,`idProduto`),
  KEY `fk_itens_pedido_produto1_idx` (`idProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `pedido`
--

CREATE TABLE IF NOT EXISTS `pedido` (
  `idpedido` int(11) NOT NULL AUTO_INCREMENT,
  `prioridade` varchar(45) NOT NULL,
  `formapagamento` varchar(45) NOT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `status` varchar(45) NOT NULL,
  `idcliente` int(11) NOT NULL,
  PRIMARY KEY (`idpedido`),
  KEY `fk_pedido_cliente1_idx` (`idcliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `produto`
--

CREATE TABLE IF NOT EXISTS `produto` (
  `idproduto` int(11) NOT NULL AUTO_INCREMENT,
  `quant_min` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `precounitario` float NOT NULL,
  `quantestoque` int(11) NOT NULL,
  `status` varchar(15) NOT NULL,
  PRIMARY KEY (`idproduto`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `produto` (`idproduto`, `quant_min`, `nome`, `precounitario`, `quantestoque`, `status`) VALUES
(1, 25, 'Agua 50L', 22.5, 50, 'Ativo'),
(2, 25, 'Gas', 60, 65, 'inativo'),
(3, 25, 'Água 20L', 22, 50, 'Inativado'),
(4, 50, 'Agua Mineral com gás', 2.3, 100, 'Ativo');

-- --------------------------------------------------------

--
-- Estrutura da tabela `veiculo`
--

CREATE TABLE IF NOT EXISTS `veiculo` (
  `idVeiculo` int(11) NOT NULL AUTO_INCREMENT,
  `modelo` varchar(45) NOT NULL,
  `marca` varchar(45) NOT NULL,
  `tipocombustivel` varchar(45) NOT NULL,
  `placa` char(7) NOT NULL,
  `status` varchar(15) NOT NULL,
  PRIMARY KEY (`idVeiculo`),
  UNIQUE KEY `placa_UNIQUE` (`placa`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `veiculo`
--

INSERT INTO `veiculo` (`idVeiculo`, `modelo`, `marca`, `tipocombustivel`, `placa`, `status`) VALUES
(1, 'qweqw', 'asda', 'Gasolina', '1234567', 'Ativo'),
(2, 'qweqw', 'asdasd', 'Gasolina', '3546710', 'Inativo'),
(3, 'Fusca GT', 'VW', 'Gasolina', 'AAA1234', 'Ativo');

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FK_cliente_1` FOREIGN KEY (`idendereco`) REFERENCES `endereco` (`idendereco`);

--
-- Limitadores para a tabela `clientefisico`
--
ALTER TABLE `clientefisico`
  ADD CONSTRAINT `fk_clientefisico_cliente1` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `clientejuridico`
--
ALTER TABLE `clientejuridico`
  ADD CONSTRAINT `fk_clientejuridico_cliente1` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `fk_compra_fornecedor1` FOREIGN KEY (`idfornecedor`) REFERENCES `fornecedor` (`idfornecedor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `entrega`
--
ALTER TABLE `entrega`
  ADD CONSTRAINT `fk_entrega_funcionario1` FOREIGN KEY (`idfuncionario`) REFERENCES `funcionario` (`idfuncionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_entrega_veiculo1` FOREIGN KEY (`idveiculo`) REFERENCES `veiculo` (`idVeiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_entrega_pedido1` FOREIGN KEY (`idpedido`) REFERENCES `pedido` (`idpedido`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `fornecedor`
--
ALTER TABLE `fornecedor`
  ADD CONSTRAINT `FK_fornecedor_1` FOREIGN KEY (`idendereco`) REFERENCES `endereco` (`idendereco`);

--
-- Limitadores para a tabela `funcionario`
--
ALTER TABLE `funcionario`
  ADD CONSTRAINT `FK_funcionario_1` FOREIGN KEY (`idendereco`) REFERENCES `endereco` (`idendereco`);

--
-- Limitadores para a tabela `historico`
--
ALTER TABLE `historico`
  ADD CONSTRAINT `fk_historico_funcionario1` FOREIGN KEY (`idfuncionario`) REFERENCES `funcionario` (`idfuncionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_historico_pedido1` FOREIGN KEY (`idpedido`) REFERENCES `pedido` (`idpedido`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `itens_compra`
--
ALTER TABLE `itens_compra`
  ADD CONSTRAINT `fk_itens_compra_produto1` FOREIGN KEY (`idproduto`) REFERENCES `produto` (`idproduto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_itens_compra_compra1` FOREIGN KEY (`idcompra`) REFERENCES `compra` (`idcompra`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `itens_pedido`
--
ALTER TABLE `itens_pedido`
  ADD CONSTRAINT `fk_itens_pedido_pedido1` FOREIGN KEY (`idpedido`) REFERENCES `pedido` (`idpedido`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_itens_pedido_produto1` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`idproduto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `fk_pedido_cliente1` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
