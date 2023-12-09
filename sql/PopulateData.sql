# Connor Love


INSERT INTO Topping (TopName, PerAMT, MedAMT, LgAMT, XlAMT, CustPrice, BusPrice, MinINVT, CurINVT)
VALUES
('Pepperoni', 1.25, 0.2, 100, 50, 2, 2.75, 3.5, 4.5),
('Sausage', 1.25, 0.15, 100, 50, 2.5, 3, 3.5, 4.25),
('Ham', 1.5, 0.15, 78, 25, 2, 2.5, 3.25, 4),
('Chicken', 1.75, 0.25, 56, 25, 1.5, 2, 2.25, 3),
('Green Pepper', 0.5, 0.02, 79, 25, 1, 1.5, 2, 2.5),
('Onion', 0.5, 0.02, 85, 25, 1, 1.5, 2, 2.75),
('Roma Tomato', 0.75, 0.03, 86, 10, 2, 3, 3.5, 4.5),
('Mushrooms', 0.75, 0.1, 52, 50, 1.5, 2, 2.5, 3),
('Black Olives', 0.6, 0.1, 39, 25, 0.75, 1, 1.5, 2),
('Pineapple', 1, 0.25, 15, 0, 1, 1.25, 1.75, 2),
('Jalapenos', 0.5, 0.05, 64, 0, 0.5, 0.75, 1.25, 1.75),
('Banana Peppers', 0.5, 0.05, 36, 0, 0.6, 1, 1.3, 1.75),
('Regular Cheese', 0.5, 0.12, 250, 50, 2, 3.5, 5, 7),
('Four Cheese Blend', 1, 0.15, 150, 25, 2, 3.5, 5, 7),
('Feta Cheese', 1.5, 0.18, 75, 0, 1.75, 3, 4, 5.5),
('Goat Cheese', 1.5, 0.2, 54, 0, 1.6, 2.75, 4, 5.5),
('Bacon', 1.5, 0.25, 89, 0, 1, 1.5, 2, 3);

-- Populate DISCOUNTS table
INSERT INTO Discount (DiscountName, Amount, isPercent)
VALUES
('Employee', 15, TRUE),
('Lunch Special Medium', 1.00 , FALSE),
('Lunch Special Large', 2.00 , FALSE),
('Specialty Pizza', 1.50, FALSE),
('Happy Hour', 10, TRUE),
('Gameday Special', 20, TRUE);
/***************************************************************************************************************/
INSERT INTO ORDERS (OrderID, OrderType, OrderDate, OrderTimestamp, TableNumber, TotalCost, TotalPrice, Completed, CustomerID, DiscountName)
VALUES ('1', 'Dine-In', '2023-03-05', '2023-03-05 12:03:00', 21, 3.68, 20.75, true, '1', 'Lunch Special Large');

INSERT INTO ORDER_DISCOUNTS (OrderID, DiscountName)
VALUES ('1', 'Lunch Special Large');

-- Insert into PIZZA table
INSERT INTO PIZZA (OrderID, Crust, Size, Price, Cost, Status)
VALUES ('1', 'Thin', 'Large', 20.75, 3.68, 'Completed');

INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, OrderID, Extra)
VALUES
('1', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Regular Cheese'),'1', '1'),
('1', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Pepperoni'),'1', '0'),
('1', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Sausage'),'1', '0');

#ORDER 2 ----------------------------------------------------------------

-- Insert into ORDERS table
INSERT INTO ORDERS (OrderID, OrderType, OrderDate, OrderTimestamp, TableNumber, TotalCost, TotalPrice, Completed, CustomerID, DiscountName)
VALUES ('2', 'Dine-In', '2023-04-03', '2023-04-03 12:05:00', 4, 4.63, 19.78, true, '1', 'Lunch Special Medium');

-- Retrieve the OrderID of the inserted order
SET @OrderID = '2';

-- Insert into DISCOUNTS table (Lunch Special Medium)
INSERT INTO ORDER_DISCOUNTS (OrderID, DiscountName, AmountOff)
VALUES ('2', 'Lunch Special Medium', '1.00');

INSERT INTO ORDER_DISCOUNTS (OrderID, DiscountName, AmountOff)
VALUES ('2', 'Specialty Pizza', '1.50');

-- Insert into PIZZA table (Medium Pan Pizza)
INSERT INTO PIZZA (OrderID, Crust, Size, Price, Cost, Status)
VALUES ('2', 'Pan', 'Medium', 12.85, 3.23, 'Completed');

-- Insert into PIZZA_TOPPING table (Medium Pan Pizza)
INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, OrderID, Extra)
VALUES
('2', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Feta Cheese'),'2','0'),
('2', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Black Olives'),'2', '0'),
('2', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Roma Tomato'),'2', '0'),
('2', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Mushrooms'),'2', '0'),
('2', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Banana Peppers'), '2', '0');

-- Insert into PIZZA table (Small Original Crust Pizza)
INSERT INTO PIZZA (OrderID, Crust, Size, Price, Cost, Status)
VALUES ('2', 'Original', 'Small', 6.93, 1.40, 'Completed');

-- Insert into ORDER_DISCOUNTS table (Lunch Special Medium)
INSERT INTO ORDER_DISCOUNTS (OrderID, DiscountName)
VALUES ('2', 'Lunch Special Medium');

-- Insert into ORDER_DISCOUNTS table (Specialty Pizza)
INSERT INTO ORDER_DISCOUNTS (OrderID, DiscountName)
VALUES ('2', 'Specialty Pizza');

-- Insert into PIZZA table (Medium Pan Pizza)
INSERT INTO PIZZA (OrderID, Crust, Size, Price, Cost, Status)
VALUES ('2', 'Pan', 'Medium', 12.85, 3.23, 'Completed');

-- Insert into PIZZA_TOPPING table (Medium Pan Pizza)
INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, OrderID, Extra)
VALUES
('3', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Feta Cheese'),'2', '0'),
('3', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Black Olives'),'2', '0'),
('3', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Roma Tomato'),'2', '0'),
('3', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Mushrooms'),'2', '0'),
('3', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Banana Peppers'),'2', '0');

-- Insert into PIZZA table (Small Original Crust Pizza)
INSERT INTO PIZZA (OrderID, Crust, Size, Price, Cost, Status)
VALUES ('2', 'Original', 'Small', 6.93, 1.40, 'Completed');

-- Insert into PIZZA_TOPPING table (Small Original Crust Pizza)
INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, OrderID, Extra)
VALUES
('4', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Regular Cheese'),'2', '0'),
('4', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Chicken'),'2', '0'),
('4', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Banana Peppers'),'2', '0');

# Order 3------------------------------------------------------------------------------
INSERT INTO ORDERS (OrderID, OrderType, OrderDate, OrderTimestamp, PickupCustomerName, PickupCustomerPhone, TotalCost, TotalPrice, Completed, DiscountName)
VALUES ('3', 'Pickup', '2023-03-03', '2023-03-03 21:30:00', 'Andrew Wilkes-Krier', '864-254-5861', 19.80, 89.28, true, NULL);

INSERT INTO PICKUP (OrderID, OrderDate, OrderTimestamp, PickupCustomerName, PickupCustomerPhone, TotalCost, TotalPrice, Completed)
VALUES ('3', '2023-03-03', '2023-03-03 21:30:00', 'Andrew Wilkes-Krier', '864-254-5861', 19.80, 89.28, true);

INSERT INTO PIZZA (OrderID, Crust, Size, Price, Cost, Status)
VALUES ('3', 'Original', 'Large', 14.88, 3.30, 'Completed');

INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, OrderID, Extra)
VALUES
('5', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Regular Cheese'),'3', '1'),
('5', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Pepperoni'),'3', '0');

#Order 4 ----------------------------------------
INSERT INTO ORDERS (OrderType, OrderDate, OrderTimestamp, DeliveryCustomerName, DeliveryCustomerPhone, DeliveryAddress, TotalCost, TotalPrice, Completed, DiscountName)
VALUES ('Delivery', '2023-04-20', '2023-04-20 19:11:00', 'Andrew Wilkes-Krier', '864-254-5861', '115 Party Blvd, Anderson SC 29621', 85.19, 85.19, true, NULL);

INSERT INTO DELIVERY (OrderID, OrderDate, OrderTimestamp, DeliveryCustomerName, DeliveryCustomerPhone, DeliveryAddress, TotalCost, TotalPrice, Completed)
VALUES ('4', '2023-04-20', '2023-04-20 19:11:00', 'Andrew Wilkes-Krier', '864-254-5861', '115 Party Blvd, Anderson SC 29621', 85.19, 85.19, true);

INSERT INTO PIZZA (OrderID, Crust, Size, Price, Cost, Status)
VALUES
('4', 'Original', 'XLarge', 27.94, 9.19, 'Completed'),
('4', 'Original', 'XLarge', 31.50, 6.25, 'Completed'),
('4', 'Original', 'XLarge', 26.75, 8.18, 'Completed');

INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, OrderID, Extra)
VALUES
('6', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Pepperoni'),'4', '0'),
('6', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Sausage'),'4', '0'),
('6', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Ham'),'4', '1'),
('6', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Pineapple'),'4', '1'),
('6', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Chicken'),'4', '0'),
('6', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Bacon'),'4', '0'),
('6', (SELECT ToppingID FROM TOPPINGS WHERE Name = 'Four Cheese Blend'),'4', '1');



#-------------------------------------------------------------------------------------------------------------------
-- Populate TOPPINGS table
INSERT INTO TOPPINGS (TopName, PerAMT, MedAMT, LgAMT, XlAMT, CustPrice, BusPrice, MinINVT, CurINVT)
VALUES
('Pepperoni', 1.25, 0.2, 100, 50, 2, 2.75, 3.5, 4.5),
('Sausage', 1.25, 0.15, 100, 50, 2.5, 3, 3.5, 4.25),
('Ham', 1.5, 0.15, 78, 25, 2, 2.5, 3.25, 4),
('Chicken', 1.75, 0.25, 56, 25, 1.5, 2, 2.25, 3),
('Green Pepper', 0.5, 0.02, 79, 25, 1, 1.5, 2, 2.5),
('Onion', 0.5, 0.02, 85, 25, 1, 1.5, 2, 2.75),
('Roma Tomato', 0.75, 0.03, 86, 10, 2, 3, 3.5, 4.5),
('Mushrooms', 0.75, 0.1, 52, 50, 1.5, 2, 2.5, 3),
('Black Olives', 0.6, 0.1, 39, 25, 0.75, 1, 1.5, 2),
('Pineapple', 1, 0.25, 15, 0, 1, 1.25, 1.75, 2),
('Jalapenos', 0.5, 0.05, 64, 0, 0.5, 0.75, 1.25, 1.75),
('Banana Peppers', 0.5, 0.05, 36, 0, 0.6, 1, 1.3, 1.75),
('Regular Cheese', 0.5, 0.12, 250, 50, 2, 3.5, 5, 7),
('Four Cheese Blend', 1, 0.15, 150, 25, 2, 3.5, 5, 7),
('Feta Cheese', 1.5, 0.18, 75, 0, 1.75, 3, 4, 5.5),
('Goat Cheese', 1.5, 0.2, 54, 0, 1.6, 2.75, 4, 5.5),
('Bacon', 1.5, 0.25, 89, 0, 1, 1.5, 2, 3);

-- Populate DISCOUNTS table
INSERT INTO Discount (DiscountName, Amount, isPercent)
VALUES
('Employee', 15, TRUE),
('Lunch Special Medium', 1.00 , FALSE),
('Lunch Special Large', 2.00 , FALSE),
('Specialty Pizza', 1.50, FALSE),
('Happy Hour', 10, TRUE),
('Gameday Special', 20, TRUE);

-- Populate BASEPRICES table
INSERT INTO BASEPRICES (Size, Crust, Price, Cost)
VALUES
('Small', 'Thin', 3, 0.5),
('Small', 'Original', 3, 0.75),
('Small', 'Pan', 3.5, 1),
('Small', 'Gluten-Free', 4, 2),
('Medium', 'Thin', 5, 1),
('Medium', 'Original', 5, 1.5),
('Medium', 'Pan', 6, 2.25),
('Medium', 'Gluten-Free', 6.25, 3),
('Large', 'Thin', 8, 1.25),
('Large', 'Original', 8, 2),
('Large', 'Pan', 9, 3),
('Large', 'Gluten-Free', 9.5, 4),
('XLarge', 'Thin', 10, 2),
('XLarge', 'Original', 10, 3),
('XLarge', 'Pan', 11.5, 4.5),
('XLarge', 'Gluten-Free', 12.5, 6);

INSERT INTO ORDERS (OrderID, OrderType, OrderDate, OrderTimestamp, TableNumber, PickupCustomerName, PickupCustomerPhone, DeliveryCustomerName, DeliveryCustomerPhone, DeliveryAddress, TotalCost, TotalPrice, Completed)
VALUES
(1, 'Dine-In', '2023-03-05', '2023-03-05 12:03:00', 21, NULL, NULL, NULL, NULL, NULL, 20.75, 20.75, true),
(2, 'Dine-In', '2023-04-03', '2023-04-03 12:05:00', 4, NULL, NULL, NULL, NULL, NULL, 19.18, 19.18, true),
(3, 'Pickup', '2023-03-03', '2023-03-03 21:30:00', NULL, 'Andrew Wilkes-Krier', '864-254-5861', NULL, NULL, NULL, 88.8, 88.8, true),
(4, 'Delivery', '2023-04-20', '2023-04-20 19:11:00', NULL, NULL, NULL, 'Andrew Wilkes-Krier', '864-254-5861', '115 Party Blvd, Anderson SC 29621', 73.39, 73.39, true),
(5, 'Pickup', '2023-03-02', '2023-03-02 17:30:00', NULL, 'Matt Engers', '864-474-9953', NULL, NULL, NULL, 27.45, 27.45, true),
(6, 'Delivery', '2023-03-02', '2023-03-02 18:17:00', NULL, NULL, NULL, 'Frank Turner', '864-232-8944', '6745 Wessex St Anderson SC 29621', 30.05, 30.05, true),
(7, 'Delivery', '2023-04-13', '2023-04-13 20:32:00', NULL, NULL, NULL, 'Milo Auckerman', '864-878-5679', '8879 Suburban Home, Anderson, SC 29621', 35.25, 35.25, true);

-- Create Trigger for PIZZA_ORDERS
DELIMITER //
CREATE TRIGGER after_order_insert
AFTER INSERT ON ORDERS
FOR EACH ROW
BEGIN
    INSERT INTO PIZZA_ORDERS (PizzaID, OrderID, Quantity)
    VALUES (NEW.PizzaID, NEW.OrderID, NEW.Quantity);
END;
//
DELIMITER ;

-- Create Trigger for PIZZA:
DELIMITER //
CREATE TRIGGER after_order_insert_pizza
AFTER INSERT ON ORDERS
FOR EACH ROW
BEGIN
    INSERT INTO PIZZA (PizzaID, CrustType, Size, Price, Cost, Status, OrderID)
    VALUES (NEW.PizzaID, NEW.CrustType, NEW.Size, NEW.Price, NEW.Cost, NEW.Status, NEW.OrderID);
END;
//
DELIMITER ;

-- Create Trigger for PIZZA_TOPPING:
DELIMITER //
CREATE TRIGGER after_order_insert_topping
AFTER INSERT ON ORDERS
FOR EACH ROW
BEGIN
    INSERT INTO PIZZA_TOPPING (PizzaID, ToppingID, Extra)
    VALUES (NEW.PizzaID, NEW.ToppingID, NEW.Extra);
END;
//
DELIMITER ;

-- Create Trigger for DISCOUNTS and ORDER_DISCOUNTS:
DELIMITER //
CREATE TRIGGER after_order_insert_discount
AFTER INSERT ON ORDERS
FOR EACH ROW
BEGIN
    INSERT INTO DISCOUNTS (DiscountID, DiscountName, PercentageOff, AmountOff)
    VALUES (NEW.DiscountID, NEW.DiscountName, NEW.PercentageOff, NEW.AmountOff);

    INSERT INTO ORDER_DISCOUNTS (OrderID, DiscountID)
    VALUES (NEW.OrderID, NEW.DiscountID);
END;
//
DELIMITER ;

-- Create Trigger for DINE_IN:
DELIMITER //
CREATE TRIGGER after_order_insert_dine_in
AFTER INSERT ON ORDERS
FOR EACH ROW
BEGIN
    IF NEW.OrderType = 'Dine-In' THEN
        INSERT INTO DINE_IN (OrderID, OrderDate, OrderTimestamp, TableNumber, TotalCost, TotalPrice, Completed)
        VALUES (NEW.OrderID, NEW.OrderDate, NEW.OrderTimestamp, NEW.TableNumber, NEW.TotalCost, NEW.TotalPrice, NEW.Completed);
    END IF;
END;
//
DELIMITER ;

-- Create Trigger for PICKUP:
DELIMITER //
CREATE TRIGGER after_order_insert_pickup
AFTER INSERT ON ORDERS
FOR EACH ROW
BEGIN
    IF NEW.OrderType = 'Pickup' THEN
        INSERT INTO PICKUP (OrderID, OrderDate, OrderTimestamp, PickupCustomerName, PickupCustomerPhone, TotalCost, TotalPrice, Completed)
        VALUES (NEW.OrderID, NEW.OrderDate, NEW.OrderTimestamp, NEW.PickupCustomerName, NEW.PickupCustomerPhone, NEW.TotalCost, NEW.TotalPrice, NEW.Completed);
    END IF;
END;
//
DELIMITER ;

-- Create Trigger for DELIVERY:
DELIMITER //
CREATE TRIGGER after_order_insert_delivery
AFTER INSERT ON ORDERS
FOR EACH ROW
BEGIN
    IF NEW.OrderType = 'Delivery' THEN
        INSERT INTO DELIVERY (OrderID, OrderDate, OrderTimestamp, DeliveryCustomerName, DeliveryCustomerPhone, DeliveryAddress, TotalCost, TotalPrice, Completed)
        VALUES (NEW.OrderID, NEW.OrderDate, NEW.OrderTimestamp, NEW.DeliveryCustomerName, NEW.DeliveryCustomerPhone, NEW.DeliveryAddress, NEW.TotalCost, NEW.TotalPrice, NEW.Completed);
    END IF;
END;
//
DELIMITER ;
