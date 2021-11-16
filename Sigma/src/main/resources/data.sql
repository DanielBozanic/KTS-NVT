INSERT INTO Users (USER_TYPE, name,username,password) VALUES ('MANAGER','Petar','Markuza', '12345');

INSERT INTO Item (ITEM_TYPE, buying_price, description, name, type) VALUES ('FOOD', 200, 'tasty', 'Spaghetti', 2);

INSERT INTO zone (name) VALUES ('Ground floor');
INSERT INTO zone (name) VALUES ('1st floor');
INSERT INTO zone (name) VALUES ('2nd floor');

INSERT INTO restaurant_table (number_of_chairs, table_number, state, zone_id) VALUES (4, 1, 0, 1);
INSERT INTO restaurant_table (number_of_chairs, table_number, state, zone_id) VALUES (2, 2, 2, 1);