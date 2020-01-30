-- auto-generated definition
create table USER
(
    ID           INTEGER      auto_increment primary key not null ,
    ACCOUNT_ID   VARCHAR(100) not null,
    NAME         VARCHAR(50)  not null,
    TOKEN        CHAR(36)     not null,
    GMT_CREATE   BIGINT       not null,
    GMT_MODIFIED BIGINT       not null,
    BIO          varchar(256) null
);
