CREATE TABLE IF NOT EXISTS account
(
    id                   INTEGER       NOT NULL AUTO_INCREMENT,
    balance              DOUBLE(10, 2) NOT NULL,
    mercado_pago_account VARCHAR(128),
    is_active               BOOLEAN       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS accounts_users
(
    user_id INTEGER NOT NULL,
    account_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, account_id)
);