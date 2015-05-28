create or replace procedure populate_books_by_author(v_no in integer) as
  v_author_id     number(10,0);
  v_book_id       number(10,0);
begin
  for each_no in 1..v_no
    loop
      begin
        select author_id into v_author_id from (select * from authors order by dbms_random.value) where rownum = 1;
        select book_id into v_book_id from all_books where rownum = each_no;
      end;
      insert into books_by_author values(v_author_id, v_book_id);
    end loop;
end populate_books_by_author;