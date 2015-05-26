create or replace procedure populate_books(v_books_no INTEGER) as
  v_book_id     number(10,0) := 1000;
  v_quantity    integer;  
  v_book_counter integer := 1000;
  v_books books := books();
BEGIN
  FOR each_book IN 1..v_books_no
    LOOP
      INSERT INTO customers VALUES(v_book_id, v_quantity, null, null);
      v_book_counter := v_book_counter + 1;
    END LOOP;
END populate_books;
/