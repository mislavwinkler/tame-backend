create table if not exists users (
    id identity,
    username varchar(100) not null unique,
    password varchar(1000) not null,
    email varchar(100),
    firstname varchar(100),
    lastname varchar(100),
    birth_date date,
    registration_date date,
    profile_picture varchar(1000)
);

create table if not exists post (
    id identity,
    text varchar(600) not null,
    creation_date date not null,
    user_id bigint not null,
    constraint fk_maker foreign key (user_id) references users(id) on delete cascade
    );

create table if not exists post_has_likes (
    post_id bigint not null,
    user_id bigint not null,
    constraint fk_post foreign key (post_id) references post(id) on delete cascade,
    constraint fk_user_liked  foreign key (user_id) references users(id) on delete cascade
    );

create table if not exists comment (
    id identity,
    text varchar(600),
    post_id bigint not null,
    user_id bigint not null,
    creation_date date not null,
    constraint fk_post_comment foreign key (post_id) references post(id) on delete cascade,
    constraint fk_user_comment foreign key (user_id) references users(id) on delete cascade
    );

create table if not exists authority (
    id identity,
    authority_name varchar(100) not null unique
    );

create table if not exists user_has_authority (
    user_id bigint not null,
    authority_id bigint not null,
    constraint fk_user foreign key (user_id) references users(id) on delete cascade,
    constraint fk_authority foreign key (authority_id) references authority(id) on delete cascade
    );

create table if not exists user_has_following (
    user_id bigint not null,
    following_id bigint not null,
    constraint fk_user_that_follows foreign key (user_id) references users(id) on delete cascade,
    constraint fk_user_that_is_followed foreign key (following_id) references users(id) on delete cascade
    );
