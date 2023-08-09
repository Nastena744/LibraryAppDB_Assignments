select b.name,isbn,year, author,bc.name
from books b
join book_categories bc on b.book_category_id = bc.id
where b.name = 'Clean Code'
order by b.id desc;
-- us04/Rasha
select * from books where name='Agile Testing';


-- US06 FATIMA

select b.name , isbn , year , author , bc.name as CategoryName
from books b left join book_categories bc on b.book_category_id = bc.id
where b.name = 'laila fatima';


-- us03 Bouchaib

select name from book_categories;