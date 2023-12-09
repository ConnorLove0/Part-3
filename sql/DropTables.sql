-- TABLES

-- TRUNCATE Customer TABLE
TRUNCATE TABLE Customer;
-- DROP Customer TABLE
DROP TABLE IF EXISTS Customer;

-- TRUNCATE DeliveryOrder TABLE
TRUNCATE TABLE DeliveryOrder;
-- DROP Discount TABLE
DROP TABLE IF EXISTS DeliveryOrder;

-- TRUNCATE DineinOrder TABLE
TRUNCATE TABLE DineinOrder;
-- DROP Order TABLE
DROP TABLE IF EXISTS DineinOrder;

-- TRUNCATE Discount TABLE
TRUNCATE TABLE Discount;
-- DROP Discount TABLE
DROP TABLE IF EXISTS Discount;

-- TRUNCATE Order TABLE
TRUNCATE TABLE 	`Order`;
-- DROP Order TABLE
DROP TABLE IF EXISTS `Order`;

-- TRUNCATE PickupOrder TABLE
TRUNCATE TABLE 	PickupOrder;
-- DROP PickupOrder TABLE
DROP TABLE IF EXISTS PickupOrder;

-- TRUNCATE Pizza TABLE
TRUNCATE TABLE Pizza;
-- DROP Pizza TABLE
DROP TABLE IF EXISTS Pizza;

-- TRUNCATE Topping TABLE
TRUNCATE TABLE Topping;
-- DROP Topping TABLE
DROP TABLE IF EXISTS Topping;

-- TRUNCATE Pizza_Topping TABLE
TRUNCATE TABLE Pizza_Topping;
-- DROP Topping TABLE
DROP TABLE IF EXISTS Pizza_Topping;

-- VIEWS

-- TRUNCATE ProfitByPizza View
TRUNCATE ToppingPopularity;
-- DROP ToppingPopularity View
DROP VIEW IF EXISTS ToppingPopularity;

-- TRUNCATE ProfitByPizza View
TRUNCATE ProfitByPizza;
-- DROP ProfitByPizza View
DROP VIEW IF EXISTS ProfitByPizza;

-- TRUNCATE ProfitByPizza View
TRUNCATE ProfitByOrderType;
-- DROP ProfitByOrderType View
DROP VIEW IF EXISTS ProfitByOrderType;
