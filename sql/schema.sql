create table categories
(
    id   serial primary key,
    name varchar(30) not null
);

create table products
(
    id          serial primary key,
    category_id int4        not null,
    name        varchar(50) not null,
    price       decimal     not null,
    foreign key (category_id) references categories (id)
);

create table characteristics
(
    id          serial primary key,
    category_id int4        not null,
    name        varchar(30) not null,
    foreign key (category_id) references categories (id)
);

create table options
(
    id                 serial primary key,
    products_id        int4        not null,
    characteristics_id int4        not null,
    option             varchar(50) not null,
    foreign key (products_id) references products (id),
    foreign key (characteristics_id) references characteristics (id)
);
