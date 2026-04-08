INSERT INTO categories (id, name, description, parent_id) VALUES (1, 'Électronique', 'Tous les produits électroniques', NULL);
INSERT INTO categories (id, name, description, parent_id) VALUES (2, 'Informatique', 'Ordinateurs et accessoires', NULL);
INSERT INTO categories (id, name, description, parent_id) VALUES (3, 'Smartphones', 'Téléphones mobiles', 1);
INSERT INTO categories (id, name, description, parent_id) VALUES (4, 'TV & Audio', 'Télévisions et systèmes audio', 1);
INSERT INTO categories (id, name, description, parent_id) VALUES (5, 'Laptops', 'Ordinateurs portables', 2);
INSERT INTO categories (id, name, description, parent_id) VALUES (6, 'Périphériques', 'Claviers, souris, écrans', 2);
INSERT INTO categories (id, name, description, parent_id) VALUES (7, 'iPhone', 'Gamme Apple iPhone', 3);
INSERT INTO categories (id, name, description, parent_id) VALUES (8, 'Android', 'Smartphones Android', 3);

INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (1, 'iPhone 15 Pro', 1199.99, 50, 7);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (2, 'iPhone 15', 999.99, 80, 7);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (3, 'Samsung Galaxy S24', 899.99, 60, 8);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (4, 'Google Pixel 8', 749.99, 40, 8);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (5, 'MacBook Pro 14"', 2199.99, 25, 5);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (6, 'Dell XPS 15', 1599.99, 30, 5);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (7, 'Clavier Logitech MX Keys', 119.99, 100, 6);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (8, 'Sony WH-1000XM5', 349.99, 45, 4);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (9, 'LG OLED 55"', 1299.99, 15, 4);
INSERT INTO products (id, name, price, stock_quantity, category_id) VALUES (10, 'AirPods Pro 2', 279.99, 70, 1);

ALTER TABLE categories ALTER COLUMN id RESTART WITH 100;
ALTER TABLE products ALTER COLUMN id RESTART WITH 100;