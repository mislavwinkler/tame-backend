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

insert into post (id, text, creation_date, user_id)
values
    (1, 'Ovo je prva probna objava', '2023-04-01', 1),
    (2, 'Ovo je druga probna objava', '2023-04-02', 1),
    (3, 'Ovo je prva objava drugog korisnika', '2023-03-25', 2);

insert into comment (id, text, post_id, user_id, creation_date)
values
    (1, 'Ovo je prvi probni komentar drugog korisnika na prvu probnu objavu', 1, 2, '2023-04-01'),
    (2, 'Ovo je drugi probni komentar na prvu probnu objavu', 1, 1, '2023-04-01'),
    (3, 'Ovo je prvi probni komentar na drugu probnu objavu', 2, 1, '2023-04-02');

insert into user_is_following (user_id, following_id)
values
    (5, 1),
    (5, 2),
    (4, 5),
    (3, 5);

insert into post_has_likes (post_id, user_id)
values
    (3, 5),
    (3, 3),
    (1, 4);