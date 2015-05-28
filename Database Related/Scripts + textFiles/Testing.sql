create or replace directory MYCSV as 'C:\Users\IoanaAlexandra\Desktop\Library\Database Related\Scripts + textFiles';

begin
  populate_books(1000);
  populate_members(5000);
  populate_authors(100);
  populate_categories(100);
  populate_books_by_author(1000);
  populate_books_by_category(1000);
  read_loan_csv('GoodCsv.csv');
  --read_loan_csv('BadCsv.csv');
end;
/
select count(*) from all_books;
/
--delete from all_books;


select count(*) from all_members;
/
--delete from all_members;

select count(*) from all_loans;
/
--delete from all_loans;


select count(*) from authors;
/
--delete from authors;

select count(*) from categories;
/
--delete from categories;

select count(*) from books_by_author;
/
--delete from books_by_author;

select count(*) from books_by_category;
/
--delete from books_by_category;









SELECT * FROM (
   SELECT book_id,ROWNUM RN FROM all_books WHERE ROWNUM < 101 )
WHERE  RN = 100;





SELECT book_id FROM (SELECT * FROM ALL_BOOKS order by book_id) WHERE ROWNUM = 1;
select author_id from (select * from authors order by dbms_random.value) where rownum = 1;
