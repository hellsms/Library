create or replace procedure populate_categories(v_categories_no in integer) as
  v_category_id     number(10,0);
  v_name            varchar2(200);
begin
  for each_category in 1..v_categories_no
    loop
      begin
        v_category_id := each_category;
        v_name := initcap(dbms_random.string('L', 20));
      end;
      insert into categories values(v_category_id, v_name);
    end loop;
end populate_categories;