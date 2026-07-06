CREATE TABLE payments
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id       BIGINT         NOT NULL,
    amount         NUMERIC(19, 2) NOT NULL,
    status VARCHAR(50)    NOT NULL
);