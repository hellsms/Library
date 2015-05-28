create or replace procedure populate_books(v_books_no in integer) as
  v_book_id     number(10,0);
  v_quantity    integer;  
  v_available   integer;
  v_loaned      integer;
  v_book_title  varchar2(200);
  v_book_date   date;
  v_counter     integer := 1;
begin
  for each_book in 1..v_books_no
    loop
      begin
        v_book_title := concat('Book ',initcap(dbms_random.string('L', 20)));
        v_book_date := sysdate;
        v_quantity := dbms_random.value(1,500000);
      end;
      insert into all_books values(v_book_id, v_quantity, v_quantity, 0, books(book(v_book_title, v_book_date)));
    end loop;
end populate_books;