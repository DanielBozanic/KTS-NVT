INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('BARTENDER', 'Mika Mikic', 2345, '2021-04-10', true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('WAITER','Marko Markovic', 1000, '2021-05-10', true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('COOK', 'Sara Saric', 3456, '2021-05-10', true);
INSERT INTO Users (USER_TYPE, name,username,password) VALUES ('MANAGER','Petar','Markovic', '12345');

INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (30000, '2021-04-10', null, 1);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (30000, '2021-05-10', null, 2);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (40000, '2021-06-10', null, 3);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (25000, '2020-06-10', '2021-04-10', 1);

INSERT INTO Menu (name, start_date, active) VALUES ('Standard', '2021-12-10', true);

INSERT INTO Item (ITEM_TYPE, buying_price, description, name) VALUES ('DRINK', 50, 'classic', 'Coca Cola');
INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 200, 'tasty', 'Spaghetti', 2);
INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 100, 'tasty', 'Crazy appetizer', 0);


INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (100, 1, 1, true);
INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (200, 3, 1, true);

INSERT INTO Zone (name) VALUES ('Ground floor');
INSERT INTO Zone (name) VALUES ('Garden');
INSERT INTO zone (name) VALUES ('1st floor');

INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (4, 0, 1, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (2, 1, 2, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 2, 3, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (2, 4, 4, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 0, 5, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 2, 6, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 4, 7, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 2, 8, 1);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 0, 9, 2);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 0, 10, 2);

INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 1, 11, 2);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 1, 12, 2);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 1, 13, 2);

INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 1, 14, 2);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 1, 15, 2);
INSERT INTO Restaurant_Table (number_of_chairs, state, table_number, zone_id) VALUES (3, 1, 16, 2);

INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-06 10:34:09.000' AS DateTime), 1, 700, 3, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-06 10:59:09.000' AS DateTime), 2, 400, 4, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 0, 700, 6, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 2, 100, 7, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 0, 100, 8, 2);

INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 0, 400, 11, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 1, 400, 12, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 1, 400, 13, 2);

INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 0, 400, 14, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 1, 400, 15, 2);
INSERT INTO Restaurant_Order (order_date_time, state, total_price, table_id, waiter_id) VALUES (CAST(N'2022-1-07 10:34:09.000' AS DateTime), 1, 400, 16, 2);

INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 1, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 1, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 1, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (2, 1, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 2, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (3, 2, 1);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (3, 2, 1);

INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (2, 1, 2);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (2, 1, 2);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (2, 2, 2);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (2, 2, 2);

INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (2, 1, 4);

INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 2, 6);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 2, 7);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 2, 7);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 2, 7);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 2, 7);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 2, 8);

INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 1, 9);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 1, 10);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (0, 1, 10);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 1, 10);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 1, 10);
INSERT INTO Item_In_Order (state, item_id, order_id) VALUES (1, 1, 11);