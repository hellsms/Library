create or replace procedure populate_books(v_books_no in integer) as
  v_book_id     number(10,0) := 1000;
  v_quantity    integer;  
  v_book_counter integer := 1000;
  v_books books := books();
begin
  for each_book in 1..v_books_no
    loop
      insert into customers values(v_book_id, v_quantity);
      v_book_counter := v_book_counter + 1;
    end loop;
end populate_books;
/