CREATE TABLE products (  id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         name      VARCHAR(255)       NOT NULL,
                         price NUMERIC(19,2) NOT NULL,
                         available_quantity     INTEGER      NOT NULL,
                         version  BIGINT  NOT NULL DEFAULT 0
                      );

CREATE TABLE stock_reservations (
                                    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                    order_id      BIGINT      NOT NULL UNIQUE,
                                    product_id      BIGINT      NOT NULL,
                                    quantity INTEGER NOT NULL,
                                    status VARCHAR(50) NOT NULL
);

INSERT INTO products (name, price, available_quantity) VALUES ('Product 1', 10.0, 10);
INSERT INTO products (name, price, available_quantity) VALUES ('Product 2', 10.0, 10);
INSERT INTO products (name, price, available_quantity) VALUES ('Product 3', 10.0, 10);
INSERT INTO products (name, price, available_quantity) VALUES ('Product 4', 10.0, 10);