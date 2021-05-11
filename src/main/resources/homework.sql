/*
1) Вывести список юзеров старше 18 лет и моложе 25 (вычисление возраста
сделать без добавления новой колонки age)
*/
select name, surname, (current_date - birth_date) / 365
from users
where (current_date - birth_date) between (19 * 365) and (24 * 365);

/*
2) Подсчитать число машин у каждого пользователя. Вывести в формате
User full name (username + пробел + user surname) | Число машин у пользователя.
*/

SELECT users.surname || ' ' || users.name as fullname, COUNT(cars.owner) as count
FROM users,
     cars
GROUP BY owner, fullname
limit 8;

/*
3) Вывести список самых популярных моделей (топ-3) машин в каждом диллере.
*/
select distinct cars.model, dealer.name
from cars
         inner join dealer on cars.dealer_id = dealer.id
order by name;

/*
4) Определить логины пользователей, имеющих больше 3 машин.
*/
select distinct users.name, cars.owner as countcar, users.login
from cars
         left join users on users.id = cars.owner
where owner in (SELECT COUNT(*) FROM cars GROUP BY owner having count(*) > 3);


SELECT owner, COUNT(*)
FROM cars
GROUP BY owner;

/*
5) Вывести уникальных диллеров с подсчитанной суммой стоимостей машин,
связанных с ними.
*/
select distinct dealer.name, sum(price)
from cars
         join dealer on cars.dealer_id = dealer.id
group by price, dealer.name;

/*
6) Подсчитать количество уникальных пользователей, владеющих хотя бы
одной машиной, стоимость которой превышает среднюю стоимость всех машин
(всех записей в таблице).
*/
select count(distinct users.id)
from users
         left join cars on users.id = cars.owner
where cars.price > (select avg(price) from cars);
