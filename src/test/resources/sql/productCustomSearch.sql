show tables;

insert into manufacturer (id, name, logo_path)
values (1, 'ABUS', '/img/companies/abus.png'),
       (2, 'Aladdin', '/img/companies/aladdin.png'),
       (3, 'Buff', '/img/companies/buff.png'),
       (4, 'Bodydry', '/img/companies/bodydry.png'),
       (5, 'BBB', '/img/companies/bbb.png'),
       (6, 'CampuS', '/img/companies/campus.png');


insert into product_group (id, name, parent_group_id, image_path)
values (1, 'Clothes', null, '/img/groups/clothes.png'),
       (2, 'Jackets', 1, '/img/groups/jackets.png'),
       (3, 'Socks', 1, '/img/groups/socks.png');


insert into product (id, name, description, price, image_path, group_id, manufacturer_id)
values (1, 'Alpine Pro Nootk 4', 'description', 1995, '/img/products/1.png', 2, 1),
       (2, 'Salewa Aqua', 'description', 3194, '/img/products/2.png', 2, 1),
       (3, 'Salewa Puez 2 PTX', 'description', 8694, '/img/products/3.png', 2, 2),
       (4, 'Dynafit Transalper', 'description', 6978, '/img/products/4.png', 2, 2),
       (5, 'Lasting TWW', 'description', 253, '/img/products/5.png', 3, 2),
       (6, 'Lasting ITU', 'description', 687, '/img/products/6.png', 3, 3),
       (7, 'X-socks Run', 'description', 199, '/img/products/7.png', 3, 3);


insert into product_parameter (id, name, value, product_id)
values (1, 'season',   'winter',  1),
       (2, 'color',    'red',     1),
       (3, 'gender',   'male',    1),
       (4, 'season',   'winter',  2),
       (5, 'color',    'blue',    2),
       (6, 'gender',   'female',  2),
       (7, 'season',   'winter',  3),
       (8, 'color',    'green',   3),
       (9, 'gender',   'male',    3),
       (10, 'season',   'summer',  4),
       (11, 'color',    'white',   4),
       (12, 'gender',   'female',  4),
       (13, 'height',   'high',    5),
       (14, 'color',    'gray',    5),
       (15, 'usecase',  'running', 5),
       (16, 'height',   'high',    6),
       (17, 'color',    'red',     6),
       (18, 'usecase',  'skiing',  6),
       (19, 'height',   'high',    7),
       (20, 'color',    'green',   7),
       (21, 'usecase',  'skiing',  7);
