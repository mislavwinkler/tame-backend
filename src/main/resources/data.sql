delete from post;

insert into users(username, password, email)
values
    ('mislav', '$2a$12$XwLlBuU73fyu9L0pmcFuc.SJClgjCHQ7Ve8lVeNZt4RlHQBN.afku', 'mislav@email.com'), -- password = mislav123
    ('admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG', 'admin@email.com'); -- password = admin

insert into authority (id, authority_name)
values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

insert into user_has_authority (user_id, authority_id)
values
    (1, 2),
    (2, 1);