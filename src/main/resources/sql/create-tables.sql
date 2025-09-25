-- ========================================================
-- SCHEMA PARA SISTEMA DE VENDAS
-- ========================================================

CREATE TABLE cliente
(
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    ativo         BOOLEAN      NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE filial
(
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    ativa         BOOLEAN      NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE item
(
    id                      BIGSERIAL PRIMARY KEY,
    descricao               VARCHAR(200)   NOT NULL,
    preco_unitario          NUMERIC(10, 2) NOT NULL CHECK (preco_unitario > 0),
    ativo                   BOOLEAN        NOT NULL DEFAULT TRUE,
    data_cadastro           TIMESTAMP      NOT NULL DEFAULT NOW(),
    data_ultima_atualizacao TIMESTAMP
);

-- StatusVenda é um ENUM em Java.
-- Podemos criar como tipo ENUM ou usar VARCHAR.
-- Aqui usaremos VARCHAR por simplicidade.
CREATE TABLE venda
(
    id                BIGSERIAL PRIMARY KEY,
    numero_venda      VARCHAR(255)   NOT NULL UNIQUE,
    data_venda        TIMESTAMP      NOT NULL DEFAULT NOW(),
    valor_total_venda NUMERIC(10, 2) NOT NULL DEFAULT 0,
    status_venda      VARCHAR(50)    NOT NULL DEFAULT 'NAO_CANCELADO',
    cliente_id        BIGINT         NOT NULL,
    filial_id         BIGINT         NOT NULL,
    CONSTRAINT fk_venda_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE RESTRICT,
    CONSTRAINT fk_venda_filial FOREIGN KEY (filial_id) REFERENCES filial (id) ON DELETE RESTRICT
);

CREATE TABLE venda_item
(
    id               BIGSERIAL PRIMARY KEY,
    item_id          BIGINT         NOT NULL,
    quantidade       INTEGER        NOT NULL CHECK (quantidade > 0),
    desconto         NUMERIC(10, 2) NOT NULL DEFAULT 0 CHECK (desconto >= 0),
    valor_total_item NUMERIC(10, 2) NOT NULL CHECK (valor_total_item >= 0),
    venda_id         BIGINT,
    CONSTRAINT fk_venda_item_item FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE RESTRICT,
    CONSTRAINT fk_venda_item_venda FOREIGN KEY (venda_id) REFERENCES venda (id) ON DELETE CASCADE
);

-- Índices úteis para performance:
CREATE INDEX idx_venda_cliente_id ON venda (cliente_id);
CREATE INDEX idx_venda_filial_id ON venda (filial_id);
CREATE INDEX idx_vendaitem_item_id ON venda_item (item_id);
CREATE INDEX idx_vendaitem_venda_id ON venda_item (venda_id);
