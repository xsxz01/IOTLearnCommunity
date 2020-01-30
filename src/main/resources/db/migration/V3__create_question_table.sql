create table QUESTION
(
    ID           INTEGER      auto_increment primary key not null ,
    TITLE         VARCHAR(50) not null,
    DESCRIPTION   CLOB        not null,
    LIKE_COUNT    INTEGER default 0,
    TAG           VARCHAR(256),
    CREATOR       INTEGER     not null,
    COMMENT_COUNT INTEGER default 0,
    VIEW_COUNT    INTEGER default 0,
    GMT_MODIFIED  BIGINT      not null,
    GMT_CREATE    BIGINT      not null,
    constraint QUESTION_PK
        primary key (ID)
);

create unique index QUESTION_ID_UINDEX
    on QUESTION (ID);

