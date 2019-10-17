-- 1. List the titles in order of total sale. Totals sales is determined by reading the qty from sales and calculating the sales price based on the price of the book in the titles table

SELECT t.title Title, sum(t.price*sa.qty) Total_sales_price         --Total sales price is the product of price and quantity
FROM titles t 
JOIN sales sa														
ON t.title_id = sa.title_id
GROUP BY t.title												    --Grouping based on title name
ORDER BY Total_sales_price desc;

-- 2. Add the store name to the query above

SELECT t.title Title, st.stor_name, sum(t.price*sa.qty) Total_sales_price
FROM sales sa 
JOIN titles t
ON t.title_id = sa.title_id
JOIN stores st
ON sa.stor_id = st.stor_id
GROUP BY t.title, st.stor_name
ORDER BY Total_sales_price desc;

--3. List all the titles and list the royalty schedule

SELECT t.title Title, rs.*
FROM titles t
JOIN roysched rs
ON t.title_id = rs.title_id
ORDER BY Title;

--4. List the stores that have orders with more than one title on the order

SELECT st.stor_name
FROM sales sa 
JOIN titles t
ON t.title_id = sa.title_id
JOIN stores st
ON sa.stor_id = st.stor_id
GROUP BY sa.ord_num, st.stor_name
HAVING COUNT(sa.ord_num) > 1;										  --For getting the titles which have more than one order

--5 Using the last position of the employee id to determine gender generate a count of the number of males and females

SELECT DISTINCT (SELECT COUNT(*) 
FROM employee 
WHERE RIGHT("emp_id",1)='M') No_Of_Males,                             --For getting the last character from emp_id and checking if it is 'M'
(SELECT COUNT(*) 
FROM employee 
WHERE RIGHT("emp_id",1)='F') No_Of_Females 
FROM employee;

--6 Produce a report firstname, lastname and gender.  Show gender as Male or Female based on last position in the employee id

SELECT e.fname Firstname, e.lname Lastname,
(CASE WHEN RIGHT("emp_id",1)='M' THEN 'Male'                          --For checking if last character is M or F and dislaying the corresonding gender based on it.
WHEN RIGHT("emp_id",1)='F' THEN 'Female' END) Gender FROM employee e;

