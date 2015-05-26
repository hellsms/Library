create or replace procedure populate_members(v_members_no in integer) as
  v_member_id   number(10,0);
  v_address     varchar2(200);
  v_first       varchar2(200);
  v_last        varchar2(200);
  v_email       varchar2(200);
  v_details     varchar2(200);
  v_phone_no    number(14,0);
  v_counter     integer := 1;
begin
  for each_member in 1..v_members_no
    loop
      begin
        v_address := concat('Street ', initcap(dbms_random.string('L', 40)));
        v_first := initcap(dbms_random.string('L', 10));
        v_last := initcap(dbms_random.string('L', 10));
        v_details := 'Super member';
        v_phone_no := dbms_random.value(10000000000000,99999999999999);
        v_counter := v_counter + 1;
      end;
      insert into all_members values(v_member_id, members(member(v_address, v_first, v_last, v_phone_no, v_email, v_details)));
    end loop;
end populate_members;