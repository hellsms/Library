create or replace procedure populate_authors(v_authors_no in integer) as
  v_author_id     number(10,0);
  v_first         varchar2(200);
  v_last          varchar2(200);
begin
  for each_author in 1..v_authors_no
    loop
      begin
        v_author_id := each_author;
        v_first := initcap(dbms_random.string('L', 20));
        v_last := initcap(dbms_random.string('L', 20));
      end;
      insert into authors values(v_author_id, v_first, v_last);
    end loop;
end populate_authors;