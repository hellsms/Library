
--Books

drop force type book;
/
create or replace type book as object(
title          varchar2(200),
date_of_publication date null
);
/

drop force type books;
/
create or replace type books as table of book;
/

drop force table all_books;
/
create table all_books(
book_id             number(10,0) not null,
quantity            integer null,
available           integer null,
loaned             integer null
);
/

alter table all_books add(
  constraint book_pk primary key (book_id)
);
/
alter table all_books
 add (my_books books)
 nested table my_books store as my_books;
/

select * from all_books;

--Members

drop force type member;
/
create or replace type member as object(
address             varchar2(200) not null,
first_name          varchar2(200) not null,
last_name           varchar2(200),
phone_number        number(14,0),
email               varchar2(200) not null,
other_details       varchar2(200) null
);
/

drop force type members;
/
create or replace type members as table of member;
/

drop force table all_members;
/
create table all_members(
member_id      number(10,0) not null
);
/

alter table all_members add(
  constraint member_pk primary key (member_id)
);
/
alter table all_members
 add (my_members members)
 nested table my_members store as my_members;
/

select * from all_members;

--Authors

drop force table authors;
/
create table authors(
author_id     number(10,0) not null,
first_name    varchar2(200) not null,
last_name     varchar2(200) null,
constraint author_pk primary key (author_id)
);
/

--Categories

drop force table categories;
/
create table categories(
category_id       number(10,0) not null,
name              varchar2(200),
constraint category_pk primary key (category_id)
);

--Books by Author

drop force table books_by_author;
/
create table books_by_author(
author_id       number(10,0) not null,
book_id         number(10,0) not null,
constraint author_fk foreign key (author_id) references authors(author_id),
constraint bba_book_fk foreign key (book_id) references all_books(book_id)
);
/

--Books by Category

drop force table books_by_category;
/
create table books_by_category(
category_id       number(10,0) not null,
book_id           number(10,0) not null,
constraint category_fk foreign key (category_id) references categories(category_id),
constraint bbc_book_fk foreign key (book_id) references all_books(book_id)
);
/

--Member loans

drop force table all_loans;
/
create table all_loans(
loan_id             number(10,0) not null,
member_id           number(10,0) not null,
book_id             number(10,0) not null,
request_date        date,
return_date         date not null,
other_details       varchar2(200) null,
constraint loan_pk primary key (loan_id),
constraint loan_member_fk foreign key (member_id) references all_members(member_id),
constraint loan_book_fk foreign key (book_id) references all_books(book_id)
);
/


