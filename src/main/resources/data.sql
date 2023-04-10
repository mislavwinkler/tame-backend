delete from post;
delete from comment;

insert into users(username, password, email, firstname, lastname)
values
    ('admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG', 'admin@email.com', 'Admin', 'Profile'), -- password = admin
    ('jtovernic', '$2a$10$XFUr05lCQIGrKVfqkEeQPeE1Ckht7WeTdjAvQy0/17wWugO0Qzqca', 'jtovernic@tvz.com', 'Jan', 'Tovernić'), -- password = jan1234
    ('mblazekovic', '$2a$10$BeYCwXAcb4J4MQVLAlGEWehps3TAiY.yR8HuGIEmsUL4YjJO17Mu2', 'mblazekovic@tvz.com', 'Martin ', 'Blažeković'), -- password = martin123
    ('lcrepak', '$2a$10$5HjelJMuiXig3Is0CficEOSUCMgNRzRD3crW4Xzx5V1XGQdI596ni', 'lcrepak@tvz.com', 'Lea ', 'Črepak'), -- password = lea123
    ('mwinkler', '$2a$12$XwLlBuU73fyu9L0pmcFuc.SJClgjCHQ7Ve8lVeNZt4RlHQBN.afku', 'mwinkler@tvz.com', 'Mislav', 'Winkler'), -- password = mislav123
    ('test', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG', 'test@email.com', 'Test', 'Profile'); -- password = admin

insert into authority (id, authority_name)
values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

insert into user_has_authority (user_id, authority_id)
values
    (1, 1),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2);

insert into post (text, creation_date, user_id)
values
    ('Bok svima, moje ime je Jan!!', '2023-04-01', 2),
    ('Danas je lijep dan', '2023-04-02', 3),
    ('Inače sam tester za ovu aplikaciju', '2023-03-25', 4),
    ('Laku noć svima!', '2023-03-23', 5);

insert into comment (text, post_id, user_id, creation_date)
values
    ('Bok Jan, ja sam Martin!', 1, 3, '2023-04-01'),
    ('A ja sam programer', 3, 5, '2023-04-01'),
    ('Lkn', 4, 1, '2023-04-02');

insert into user_is_following (user_id, following_id)
values
    (3, 2),
    (4, 2),
    (5, 2),
    (2, 3),
    (4, 3),
    (5, 3),
    (2, 4),
    (3, 4),
    (5, 4),
    (2, 5),
    (3, 5),
    (4, 5);

insert into post_has_likes (post_id, user_id)
values
    (1, 3),
    (1, 4),
    (1, 5),
    (2, 5),
    (3, 2),
    (3, 3),
    (4, 2);