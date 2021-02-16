create table product (
    id int primary key,
    product_name text not null,
    product_price numeric not null,
    price_before_promotion numeric,
    product_url text not null,
    last_updated timestamp not null default now()
);
create sequence product_seq;