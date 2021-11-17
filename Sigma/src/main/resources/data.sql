INSERT INTO Users (USER_TYPE, name,username,password) VALUES ('MANAGER','Petar','Markuza', '12345');
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('WAITER', 'Pera Peric', 1234, '2021-03-10', true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('BARTENDER', 'Mika Mikic', 2345, '2021-04-10', true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('COOK', 'Sara Saric', 3456, '2021-05-10', true);

INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (20000, '2021-03-10', null, 2);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (30000, '2021-04-10', null, 3);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (40000, '2021-05-10', null, 4);

INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 200, 'tasty', 'Spaghetti', 2);

INSERT INTO zone (name) VALUES ('Ground floor');
INSERT INTO zone (name) VALUES ('1st floor');
INSERT INTO zone (name) VALUES ('2nd floor');

INSERT INTO restaurant_table (number_of_chairs, table_number, state, zone_id) VALUES (4, 1, 0, 1);
INSERT INTO restaurant_table (number_of_chairs, table_number, state, zone_id) VALUES (2, 2, 2, 1);