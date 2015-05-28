create or replace procedure populate_books_by_category(v_no in integer) as
  v_category_id     number(10,0);
  v_book_id         number(10,0);
begin
  for each_no in 1..v_no
    loop
      begin
        select category_id into v_category_id from (select * from categories order by dbms_random.value) where rownum = 1;
        select book_id into v_book_id from all_books where rownum = each_no;
      end;
      insert into books_by_category values(v_category_id, v_book_id);
    end loop;
end populate_books_by_category;