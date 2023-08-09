select id from users;  -- actual

select count(distinct id) from users; -- expected

select bc.name, count(*) from book_borrow bb
    inner join books b on bb.book_id = b.id
    inner join book_categories bc on b.book_category_id = bc.id
group by name
order by 2 desc;






