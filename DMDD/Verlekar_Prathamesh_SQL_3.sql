-- 1. List the titles in order of total sale. Totals sales is determined by reading the qty from sales and calculating the sales price based on the price of the book in the titles table

SELECT t.title Title, sum(t.price*sa.qty) Total_sales_price         --Total sales price is the product of price and quantity
FROM titles t 
JOIN sales sa														
ON t.title_id = sa.title_id
GROUP BY t.title												    --Grouping based on title name
ORDER BY Total_sales_price desc;									--Ordering based on the total sale

-- 2. Add the store name to the query above

SELECT t.title Title, st.stor_name Store_name, sum(t.price*sa.qty) Total_sales_price --Added store name in the above query
FROM sales sa 
JOIN titles t
ON t.title_id = sa.title_id
JOIN stores st
ON sa.stor_id = st.stor_id
GROUP BY t.title, st.stor_name										--Grouping based on title name and store name
ORDER BY Total_sales_price desc;									--Ordering based on the total sale

--3. List all the titles and list the royalty schedule

SELECT t.title Title, rs.*							                --Selecting all the columns from roysched table
FROM titles t
JOIN roysched rs
ON t.title_id = rs.title_id 
AND t.royalty = rs.royalty
ORDER BY Title;														--Ordering based on Title name

--4. List the stores that have orders with more than one title on the order

SELECT st.stor_id Store_id, st.stor_name Store_name					 --Selecting Store Id and Store Name 
FROM sales sa 
JOIN stores st
ON sa.stor_id = st.stor_id
GROUP BY sa.ord_num, st.stor_id, st.stor_name						  --Grouping based on Order number and store name
HAVING COUNT(sa.ord_num) > 1;										  --For getting the stores that have orders with more than one title on the order

--5 Using the last position of the employee id to determine gender generate a count of the number of males and females

SELECT DISTINCT (SELECT COUNT(*)									  --For getting the count of number of males
FROM employee 
WHERE RIGHT("emp_id",1)='M') No_of_males,                             --For getting the last character from emp_id and checking if it is 'M'
(SELECT COUNT(*)													  --For getting the count of number of females 
FROM employee 
WHERE RIGHT("emp_id",1)='F') No_of_females							  --For getting the last character from emp_id and checking if it is 'F'
FROM employee;

--6 Produce a report firstname, lastname and gender.  Show gender as Male or Female based on last position in the employee id

SELECT e.fname First_name, e.lname Last_name,						  --Getting the first name and last name
(CASE WHEN RIGHT("emp_id",1)='M' THEN 'Male'                          --For checking if last character is M or F and dislaying the corresonding gender based on it.
WHEN RIGHT("emp_id",1)='F' THEN 'Female' END) Gender FROM employee e;

