-- begin SEC_USER
alter table SEC_USER add column LAST_TIME_ONLINE timestamp ^
alter table SEC_USER add column DTYPE varchar(31) ^
update SEC_USER set DTYPE = 'MyMessenger_MyUser' where DTYPE is null ^
-- end SEC_USER
-- begin MY_MESSENGER_MESSAGE
create table MY_MESSENGER_MESSAGE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    USER_SENDER_ID varchar(36) not null,
    USER_RECIPIENT_ID varchar(36) not null,
    MESSAGE varchar(255) not null,
    DATE_TIME timestamp not null,
    --
    primary key (ID)
)^
-- end MY_MESSENGER_MESSAGE
