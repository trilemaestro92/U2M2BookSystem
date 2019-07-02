create schema if not exists note_test;
use note_test;

create table if not exists note (
	note_id int not null auto_increment primary key,
    book_id int not null,
    note varchar(255)
);