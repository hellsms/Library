create or replace procedure read_loan_csv(v_csv in varchar2) as
    f utl_file.file_type;
    v_line varchar2 (1000);
    v_loan_id number(10,0);
    v_member_id number(10,0);
    v_book_id number(10,0);
    v_request_date date;
    v_return_date date;
    v_details varchar2 (200);
    v_counter integer := 0;
   begin
    savepoint before_csv_insert;
    f := utl_file.fopen ('mycsv', v_csv, 'r');
    if utl_file.is_open(f) then
      loop
        begin
          utl_file.get_line(f, v_line, 1000);
          if v_line is null then
            exit;
          end if;
          v_counter := v_counter + 1;
          dbms_output.put_line('line' || v_counter);
          v_loan_id := regexp_substr(v_line, '[^,]+',1, 1);
          dbms_output.put_line(v_loan_id);
          v_member_id := regexp_substr(v_line, '[^,]+',1, 2);
          dbms_output.put_line(v_member_id);
          v_book_id := regexp_substr(v_line, '[^,]+',1, 3);
          dbms_output.put_line(v_book_id);
          v_request_date := regexp_substr(v_line, '[^,]+',1, 4);
          dbms_output.put_line(v_request_date);
          v_return_date := regexp_substr(v_line, '[^,]+',1, 5);
          dbms_output.put_line(v_return_date);
          v_details := regexp_substr(v_line, '[^,]+',1, 6);
          dbms_output.put_line(v_details);
          insert into all_loans values(v_loan_id, v_member_id, v_book_id, v_request_date,v_return_date,v_details);
          --commit;
        exception
        when no_data_found then
          exit;
        when others then
          dbms_output.put_line('error occured at line ' || v_counter);
          rollback to before_csv_insert;
          exit;
        end;
      end loop;
    end if;
    utl_file.fclose(f);
  end read_loan_csv;