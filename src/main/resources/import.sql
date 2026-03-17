
--init products
INSERT INTO products(name,price) values ('apple',0.5);
INSERT INTO products(name,price) values ('orange',0.48);
INSERT INTO products(name,price) values ('pear',0.45);
INSERT INTO products(name,price) values ('banana',0.40);

-- init offers
INSERT INTO offers(product_id, product_quantity, offer_price) values (1, 2, 0.70);
INSERT INTO offers(product_id, product_quantity, offer_price) values (2, 3, 1.10);

