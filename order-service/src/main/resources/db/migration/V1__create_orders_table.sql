CREATE TABLE orders (
                        id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        user_id      BIGINT       NOT NULL,
                        product_name VARCHAR(255) NOT NULL,
                        quantity     INTEGER      NOT NULL,
                        status       VARCHAR(50)  NOT NULL,
                        created_at   TIMESTAMP    NOT NULL DEFAULT now()
);