insert into categories (name)
values ('Процессоры'),
       ('Мониторы');

insert into products(category_id, name, price)
values (1, 'Intel Core I9 9900', 107500),
       (1, 'AMD Ryzen 7 3700X', 103400),
       (2, 'Samsung MZ23U255', 157200),
       (2, 'AOC L215U266', 121750);

insert into characteristics (category_id, name)
values (1, 'Производитель'),
       (1, 'Сокет'),
       (1, 'Максимальный объем'),
       (1, 'Тактовая частота'),
       (2, 'Производитель'),
       (2, 'Диагональ'),
       (2, 'Матрица'),
       (2, 'Материал изделия');

insert into options (products_id, characteristics_id, option)
values (1, 1, 'Intel'),
       (1, 2, '1151'),
       (1, 3, '128Gb'),
       (1, 4, '4.8MHz'),
       (2, 1, 'AMD'),
       (2, 2, 'AM4'),
       (2, 3, '128Gb'),
       (2, 4, '4.6MHz'),
       (3, 5, 'Samsung'),
       (3, 6, '23"'),
       (3, 7, 'TN-Film'),
       (3, 8, 'Plastic');
