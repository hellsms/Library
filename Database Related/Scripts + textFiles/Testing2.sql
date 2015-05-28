create or replace directory MYCSV as 'C:\Users\IoanaAlexandra\Desktop\Library\Database Related\Scripts + textFiles';
/
grant read, write on directory MYCSV to public;
/
begin
  --populate_books(1000);
  --populate_members(5000);
  --read_csv('BadCsv.csv');
  read_loan_csv('GoodCsv.csv');
end;
/
select * from all_books;
/
--delete from all_books;


select * from all_members order by member_id asc;
/
--delete from all_members;

select * from all_loans;
/
--delete from all_loans;
/
