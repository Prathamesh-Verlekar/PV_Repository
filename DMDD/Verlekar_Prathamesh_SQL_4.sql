-- 1 Using AdventureWorks - List all the employees show name and department

SELECT concat(p.FirstName, ' ' ,p.MiddleName, ' ' ,p.LastName) Employee_name, d.Name Department   
FROM Person.Person p
JOIN HumanResources.EmployeeDepartmentHistory e			 --Joining Employeedepartmenthistory and Department based on Department 
ON p.BusinessEntityID = e.BusinessEntityID
JOIN HumanResources.Department d						 --Joining Employeedepartmenthistory and Department based on Department
ON e.DepartmentID = d.DepartmentID
ORDER BY Employee_name;

-- 2 Using AdventureWorks - Show the employees and their current and prior departments

SELECT concat(p.FirstName, ' ' ,p.MiddleName, ' ' ,p.LastName) Employee_name, d.Name Department,		
--Select Department and showing Current for current department and prior for previous departments
CASE WHEN e.EndDate IS NULL								--Checking based on the end date if it is null then displaying Current 																
THEN 'Current'
ELSE 'Prior' END AS Departname_Status
FROM Person.Person p
JOIN HumanResources.EmployeeDepartmentHistory e
ON p.BusinessEntityID = e.BusinessEntityID			    --Joining Person and Employeedepartmenthistory based on BusinessEntityId
JOIN HumanResources.Department d
ON e.DepartmentID = d.DepartmentID;						--Joining Employeedepartmenthistory and Department based on Department

-- 3 Using AdventureWorks - Break apart the employee login id so that you have the domain (before the /) in one column and the login id (after the /) in two columns

SELECT SUBSTRING(LoginId, 1, CHARINDEX('\',LoginId) - 1) Domain,	     --Using substring to get the string from start to the charindex of '\'
REVERSE( LEFT(REVERSE(LoginId), CHARINDEX('\', REVERSE(LoginId))-1)) Login_Id, --Using reverse to get the string from charindex '\' to last char.
BusinessEntityID
FROM HumanResources.Employee;

-- 4 Using AdventureWorks - Build a new column in a copy of the employee table that is the employee email address in the form login_id@domain.com, populate the column

SELECT * into HumanResources.Employee_C                              --To make a copy of Employee Table as Emloyee_C
FROM HumanResources.Employee
ALTER TABLE HumanResources.Employee_C ADD Email_Id VARCHAR(40) NULL  --To add a new column(Email_Id) in the copy of Empoyee Table created
UPDATE HumanResources.Employee_C								     -- Updating the Email_Id column based on the required condition.
SET Email_Id = concat(REVERSE( LEFT(REVERSE(LoginId), CHARINDEX('\', REVERSE(LoginId))-1)),'@',SUBSTRING(LoginId, 0, CHARINDEX('\',LoginId) - 1),'.com')                
SELECT * FROM HumanResources.Employee_C

-- 5 Using AdventureWorks - Calculate the age of an employee in years - Does it work if the birthday hasn't happened yet if not fix it

SELECT  concat(p.FirstName, ' ' ,p.MiddleName, ' ' ,p.LastName) Employee_name, e.BirthDate,
CASE WHEN 
DATEADD(YEAR,DATEDIFF(YEAR,e.BirthDate,GETDATE()),e.BirthDate)<GETDATE()  --Using datediff to find the difference between birthday and current date in the form of year
THEN DATEDIFF(YEAR,e.BirthDate,GETDATE())								  --For employee's whose birthday has passed in the current year 
ELSE DATEDIFF(YEAR,e.BirthDate,GETDATE()) - 1 END AS AGE                  --For employee's whose birthday has not come yet
FROM HumanResources.Employee e
JOIN Person.Person p
ON p.BusinessEntityID = e.BusinessEntityID
ORDER BY Employee_name;												                             --Ordering based on employee_name

-- 6 Using Pubs - List a titles prior year sales, define the current year as the max year in the sales (use a subquery).

SELECT t.title Title, YEAR(sa.ord_date) Order_Year, sum(t.price*sa.qty) Total_sales_price         --Total sales price is the product of price and quantity
FROM titles t 
JOIN sales sa														
ON t.title_id = sa.title_id
WHERE YEAR(sa.ord_date) = (SELECT MAX(YEAR(ord_date)-1) FROM sales)                               --Finding the max year in the sales and defining it as current year
GROUP BY Title, YEAR(sa.ord_date)															      --Subtracting by 1 to get the previous year and checking in the where condition
ORDER BY sum(t.price*sa.qty) desc;                                                               --Ordering based on total sales
