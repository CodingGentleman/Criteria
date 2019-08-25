create table country (
    alpha3 char(3) primary key,
    alpha2 char(2) unique
)

insert into country values ('AUT', 'AT'), ('GER', 'DE');