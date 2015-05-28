--Sequences
drop sequence inc_all_books;
/
create sequence inc_all_books
  minvalue 1
  maxvalue 9999999999
  start with 1
  increment by 1
  nocache;
/

drop sequence inc_all_members;
/
create sequence inc_all_members
  minvalue 1
  maxvalue 9999999999
  start with 1
  increment by 1
  nocache;
  
drop sequence inc_all_loans;
/
create sequence inc_all_loans
  minvalue 1
  maxvalue 9999999999
  start with 1
  increment by 1
  nocache;
/

drop sequence inc_all_authors;
/
create sequence inc_all_authors
  minvalue 1
  maxvalue 9999999999
  start with 1
  increment by 1
  nocache;
/

drop sequence inc_all_categories;
/
create sequence inc_all_categories
  minvalue 1
  maxvalue 9999999999
  start with 1
  increment by 1
  nocache;
/

--Triggers
create or replace trigger inc_books
    before insert on all_books 
    for each row
  begin
    select inc_all_books.NEXTVAL
    into   :new.book_id
    from   dual;
  end;
/
alter trigger inc_books enable;
/

create or replace trigger inc_members
    before insert on all_members 
    for each row
  begin
    select inc_all_members.NEXTVAL
    into   :new.member_id
    from   dual;
  end;
/
alter trigger inc_members enable;
/

create or replace trigger inc_loans
    before insert on all_loans 
    for each row
  begin
    select inc_all_loans.NEXTVAL
    into   :new.loan_id
    from   dual;
  end;
/
alter trigger inc_loans enable;
/

create or replace trigger inc_authors
    before insert on authors 
    for each row
  begin
    select inc_all_authors.NEXTVAL
    into   :new.author_id
    from   dual;
  end;
/
alter trigger inc_authors enable;
/

create or replace trigger inc_categories
    before insert on categories 
    for each row
  begin
    select inc_all_categories.NEXTVAL
    into   :new.category_id
    from   dual;
  end;
/
alter trigger inc_categories enable;
/