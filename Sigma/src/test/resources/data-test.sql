
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('BARTENDER', 'Mika Mikic', 2345, '2021-04-10', true);
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('WAITER','Marko Petruza', 1000, '2021-05-10', true);
INSERT INTO Users (USER_TYPE, name,username,password) VALUES ('MANAGER','Petar','Markuza', '12345');
INSERT INTO Users (USER_TYPE, name, code, date_of_employment, active) VALUES ('COOK', 'Sara Saric', 3456, '2021-05-10', true);

INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (30000, '2021-04-10', null, 1);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (30000, '2021-05-10', null, 2);
INSERT INTO Payment (payment, date_created, date_end, employee_id) VALUES (40000, '2021-06-10', null, 4);