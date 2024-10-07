-- H2 CAN NOT DEFINE CHARSET AND COLLATION :( --
DROP TABLE IF EXISTS brand;
CREATE TABLE brand (
id INT AUTO_INCREMENT  PRIMARY KEY,
name VARCHAR(20) NOT NULL,
description VARCHAR(50) DEFAULT NULL,
CONSTRAINT unique_brand_name UNIQUE (name)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product (
id BIGINT AUTO_INCREMENT  PRIMARY KEY,
name VARCHAR(20) NOT NULL,
description VARCHAR(50) DEFAULT NULL,
sku VARCHAR(20) NOT NULL,
join_life VARCHAR(20) DEFAULT NULL,
collection VARCHAR(20) DEFAULT NULL,
material VARCHAR(20) DEFAULT NULL,
CONSTRAINT unique_sku UNIQUE (sku)
);

DROP TABLE IF EXISTS price;
CREATE TABLE price (
id BIGINT AUTO_INCREMENT  PRIMARY KEY,
brand_id INT NOT NULL,
start_date DATETIME NOT NULL,
end_date DATETIME NOT NULL,
price_list INT NOT NULL,
product_id BIGINT NOT NULL,
priority INT NOT NULL,
price DECIMAL(20,2) NOT NULL,
currency VARCHAR(10) NOT NULL,
FOREIGN KEY (product_id) REFERENCES product(id),
FOREIGN KEY (brand_id) REFERENCES brand(id)
);

-- INSERT BRANDS --
INSERT INTO brand VALUES (1, 'Zara', 'Zara'),
(2, 'PullAndBear', 'Pull And Bear'),
(3, 'MassimoDutti', 'Massimo Dutti'),
(4, 'Bershka', 'Bershka'),
(5, 'Stradivarius', 'Stradivarius'),
(6, 'Oysho', 'Oysho'),
(7, 'Zara Home', 'Zara home');

-- INSERT PRODUCTS --
INSERT INTO product VALUES
(12345, 'Camiseta Negra', 'Camiseta básica negra, talla S', 'TSHRT-001-BLK-S', 'JL2', '2024-02', 'Cotton'),
(22234, 'Chaqueta mezclilla', 'Chaqueta de mezclilla, talla M', 'JCKT-245-DNM-M', null, '2024-03', 'Nylon'),
(35455, 'Vestido rojo', 'Vestido rojo, talla L', 'DRS-178-RD-L', 'JL1', '2024-03', 'Velvet'),
(67899, 'Pantalones caqui', 'Pantalones color caqui, talla 32', 'PNTS-091-KHK-32', 'JL7', '2023-04', 'Leather'),
(345, 'Sueter gris', 'Suéter gris, talla XL', 'SWT-033-GRY-XL', 'JL6', '2022-04', 'Corduroy');

-- INSERT PRICES --
INSERT INTO price VALUES
(1, 1, '2020-06-14 00:00:00.000', '2020-12-31 23:59:59.000', 1, 35455, 0, 35.50, 'EUR'),
(2, 1, '2020-06-14 15:00:00.000', '2020-06-14 18:30:00.000', 2, 35455, 1, 25.45, 'EUR'),
(3, 1, '2020-06-15 00:00:00.000', '2020-06-15 11:00:00.000', 3, 35455, 1, 30.50, 'EUR'),
(4, 1, '2020-06-15 16:00:00.000', '2020-12-31 23:59:59.000', 4, 35455, 1, 38.95, 'EUR');