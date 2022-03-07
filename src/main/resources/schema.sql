CREATE TABLE account
(
    id                   INTEGER       NOT NULL AUTO_INCREMENT,
    balance              NUMBER(10, 2) NOT NULL,
    mercado_pago_account VARCHAR(128),
    active               BOOLEAN       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accounts_users
(
    id_user INTEGER NOT NULL,
    id_account INTEGER NOT NULL,
    PRIMARY KEY (id_user, id_account)
);