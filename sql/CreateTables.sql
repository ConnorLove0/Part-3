# Connor Love
-- Create Schema
CREATE SCHEMA IF NOT EXISTS Pizzeria;

-- Use Schema
USE Pizzeria;
-- Create Customer table
CREATE TABLE Customer (
    CustID INT AUTO_INCREMENT PRIMARY KEY,
    FName VARCHAR(255),
    LName VARCHAR(255),
    Phone VARCHAR(20),
    Address VARCHAR(255)
);

CREATE TABLE Discount (
    DiscountID INT AUTO_INCREMENT PRIMARY KEY,
    DiscountName VARCHAR(255),
    Amount DOUBLE,
    isPercent BOOLEAN
);

-- Create Order table
CREATE TABLE `Order` (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustID INT,
    OrderType VARCHAR(255),
    Date VARCHAR(255),
    CustPrice DOUBLE,
    BusPrice DOUBLE,
    isComplete INT
);

CREATE TABLE DineinOrder (
    TableNum INT PRIMARY KEY
);

CREATE TABLE DeliveryOrder (
    Address VARCHAR(255) PRIMARY KEY
);

CREATE TABLE PickupOrder (
    isPickedUp INT PRIMARY KEY
);

CREATE TABLE Topping (
    TopID INT AUTO_INCREMENT PRIMARY KEY,
    TopName VARCHAR(255),
    PerAMT DOUBLE,
    MedAMT DOUBLE,
    LgAMT DOUBLE,
    XLAMT DOUBLE,
    CustPrice DOUBLE,
    BusPrice DOUBLE,
    MinINVT INT,
    CurINVT INT
);

CREATE TABLE Pizza (
    PizzaID INT AUTO_INCREMENT PRIMARY KEY,
    CrustType VARCHAR(255),
    Size VARCHAR(255),
    OrderID INT,
    PizzaState VARCHAR(255),
    PizzaDate VARCHAR(255),
    CustPrice DOUBLE,
    BusPrice DOUBLE,
    IsToppingDoubled TINYTEXT
);

CREATE TABLE Pizza_Topping (
    PizzaID INT,
    ToppingID INT,
    PRIMARY KEY (PizzaID, ToppingID),
    FOREIGN KEY (PizzaID) REFERENCES Pizza(PizzaID),
    FOREIGN KEY (ToppingID) REFERENCES Topping(TopID)
);
