# Connor Love

-- ToppingPopularity view
CREATE VIEW ToppingPopularity AS
SELECT
    T.Name AS "Topping",
    COUNT(*) AS "Topping Count"
FROM
    PIZZA_TOPPING PT
    JOIN TOPPINGS T ON PT.ToppingID = T.ToppingID
GROUP BY
    T.Name
ORDER BY
    ToppingCount DESC;

-- ProfitByPizza view
CREATE VIEW ProfitByPizza AS
SELECT
    P.Size AS "Pizza Size",
    P.CrustType AS "Pizza Crust",
    SUM(P.Price - P.Cost) AS "Profit",
    MAX(O.OrderDate) AS "LastOrderDate"
FROM
    PIZZA P
    JOIN PIZZA_ORDERS PO ON P.PizzaID = PO.PizzaID
    JOIN ORDERS O ON PO.OrderID = O.OrderID
GROUP BY
    P.Size,
    P.CrustType
ORDER BY
    "Profit" DESC;

-- ProfitByOrderType view
CREATE VIEW ProfitByOrderType AS
SELECT
    C.OrderType,
    EXTRACT(MONTH FROM O.OrderDate) AS OrderMonth,
    SUM(O.TotalCost) AS TotalOrderCost,
    SUM(O.TotalPrice - O.TotalCost) AS Profit
FROM
    ORDERS O
    LEFT JOIN CUSTOMER C ON O.CustomerID = C.CustomerID
GROUP BY
    OrderType,
    OrderMonth
WITH ROLLUP
ORDER BY
    OrderType,
    OrderMonth;