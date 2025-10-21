CREATE TABLE item (
  id          varchar(36) NOT NULL,
  order_id    varchar(36) NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  product_id  varchar(36) NOT NULL,
  quantity    integer NOT NULL,
  total       decimal(12,2) NOT NULL,
  CONSTRAINT item_pkey PRIMARY KEY (id)
);