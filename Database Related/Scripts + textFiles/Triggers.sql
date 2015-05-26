--Sequences
drop sequence inc_all_books;
/
create sequence inc_all_books
  minvalue 0
  maxvalue 1000000000
  start with 0
  increment by 1
  nocache;
/

drop sequence inc_all_members;
/
create sequence inc_all_members
  minvalue 0
  maxvalue 1000000000
  start with 0
  increment by 1
  nocache;
  
drop sequence inc_all_loans;
/
create sequence inc_all_loans
  minvalue 0
  maxvalue 1000000000
  start with 0
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