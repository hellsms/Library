create or replace view return_view as
  select * from all_loans order by return_date;


create or replace view request_view as
  select * from all_loans order by request_date;