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


INSERT INTO Item_In_Menu (selling_price, item_id, menu_id, active) VALUES (100, 1, 1, true);