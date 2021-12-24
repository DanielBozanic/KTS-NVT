INSERT INTO Users (USER_TYPE, name,username,password) VALUES ('MANAGER','Petar','Markuza', '12345');
INSERT INTO Users (USER_TYPE, name, code, active) VALUES ('WAITER','Marko Petruza', 1000, true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('WAITER', 'Pera Peric', 1234, '2021-03-10', true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('BARTENDER', 'Mika Mikic', 2345, '2021-04-10', true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('COOK', 'Sara Saric', 3456, '2021-05-10', true);

INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (20000, '2021-03-10', null, 2);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (30000, '2021-04-10', null, 3);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (40000, '2021-05-10', null, 4);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (40000, '2021-06-10', null, 5);


INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 200, 'tasty', 'Spaghetti', 2);
INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 150, 'healthy', 'Cesar Salad', 1);
INSERT INTO Item (ITEM_TYPE, buying_price, description, name) VALUES ('DRINK', 50, 'classic', 'Coca Cola');
INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 200, 'tasty', 'Pizza', 2);
INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 150, 'healthy', 'Chicken', 2);
INSERT INTO Item (ITEM_TYPE, buying_price, description, name) VALUES ('DRINK', 50, 'classic', 'Sprite');
INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 200, 'tasty', 'Pasta', 2);
INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 150, 'healthy', 'Cheeseburger', 2);
INSERT INTO Item (ITEM_TYPE, buying_price, description, name) VALUES ('DRINK', 50, 'classic', 'Fanta');
INSERT INTO Item (ITEM_TYPE, buying_price, description, name) VALUES ('DRINK', 50, 'classic', 'Pepsi');


INSERT INTO Menu (name, start_date, expiration_date, active) VALUES ('Standard', '2021-12-10', '2022-05-31', true);



INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2012-06-18 10:34:09.000' AS DateTime), 0, 800, 1, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2012-06-18 10:34:09.000' AS DateTime), 0, 1800, 2, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2012-06-18 10:34:09.000' AS DateTime), 0, 1200, 3, 2);

INSERT INTO Restaurant_Order (state, total_price, table_id, waiter_id) VALUES (0, 800, 1, 3);



INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (350, 1, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (100, 3, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (200, 2, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (300, 4, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (250, 5, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (100, 6, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (300, 7, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (200, 8, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (100, 9, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (100, 10, 1, true);


INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 1, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 1, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 2, 1);

INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 2, 2);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 3, 2);

INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 4, 3);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 4, 3);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 3, 3);


INSERT INTO Zone (name) VALUES ('Ground floor');
INSERT INTO Zone (name) VALUES ('Garden');
INSERT INTO zone (name) VALUES ('1st floor');
INSERT INTO zone (name) VALUES ('2nd floor');


INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (4, 2, 1, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (6, 0, 2, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (4, 0, 3, 2);