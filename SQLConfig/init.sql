USE cardapioautomatizado;

CREATE TABLE ingrediente (
                              idIngrediente INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
                              nome VARCHAR(40) NOT NULL UNIQUE,
                              quantidade INTEGER NOT NULL,
                              PRIMARY KEY (idIngrediente)
);

CREATE TABLE pratos (
                        idPrato INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
                        nome VARCHAR(60) NOT NULL UNIQUE,
                        preco DECIMAL(10, 2) NOT NULL,

                        PRIMARY KEY (idPrato)
);

CREATE TABLE ingredientes_pratos (
                                     idIngrediente INTEGER UNSIGNED NOT NULL,
                                     idPrato INTEGER UNSIGNED NOT NULL,
                                     quantidade INTEGER NOT NULL,
                                     PRIMARY KEY (idIngrediente, idPrato),
                                     FOREIGN KEY (idIngrediente) REFERENCES ingrediente(idIngrediente),
                                     FOREIGN KEY (idPrato) REFERENCES pratos(idPrato)
);

SELECT USER();
