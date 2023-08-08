select b.name,isbn,year, author,bc.name
from books b
join book_categories bc on b.book_category_id = bc.id
where b.name = 'Clean Code'
order by b.id desc;

