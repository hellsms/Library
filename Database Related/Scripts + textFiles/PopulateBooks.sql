create or replace procedure populate_books(v_books_no in integer) as
  v_book_id     number(10,0) := 1000;
  v_quantity    integer;  
  v_books books := books();
  v_book_title  varchar2(200);
  v_book_date date;
  v_counter integer := 1;
begin
  for each_book in 1..v_books_no
    loop
      declare
        my_book books := books();
      begin
        v_book_title := concat('Book',dbms_random.string('L', 20));
        v_book_date := sysdate;
        my_books.extend();
        my_books(v_counter) := book(v_book_title, v_book_date);
      end;
      insert into customers values(v_book_id, v_quantity, my_books);
    end loop;
end populate_books;
/