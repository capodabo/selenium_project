create table product (
    id int primary key,
    product_name text not null,
    price numeric not null,
    currency text not null
);
create sequence product_seq;