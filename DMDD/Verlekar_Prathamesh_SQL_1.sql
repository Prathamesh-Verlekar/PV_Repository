-- 1.  What authors are in California

SELECT au_fname + ' ' + au_lname Author_name					    --Concatenating first name and last name
FROM authors 
WHERE state = 'CA';											        --Condition for getting authors name in California

-- 2.  List the titles and author names

SELECT t.title Title, a.au_fname + ' ' + a.au_lname Author_name     --Concatenating first name and last name
FROM titles t 
JOIN titleauthor ta											        --For joining titles and titleauthor table
ON t.title_id = ta.title_id									        --Based on join condition
JOIN authors a												        --For joining authors and titleauthor table
ON ta.au_id = a.au_id                                               --Based on join condition
ORDER BY t.title, Author_name;                                      --Ordered based on Title and Author name in ascending order

-- 3.  List all employees and their jobs

SELECT e.fname + ' ' + e.lname Employee_name, j.job_desc Job_description   
FROM employee e														
JOIN jobs j															--For joining employee and jobs table 
ON e.job_id = j.job_id			                                    --Based on join condition
ORDER BY Employee_name;                                             --Ordered based on Employee name in ascending order

-- 4.  List the titles by total sales price

SELECT t.title Title, sum(t.price*sa.qty) Total_sales_price         --Total sales price is the product of price and quantity
FROM titles t 
JOIN sales sa														
ON t.title_id = sa.title_id
GROUP BY t.title												    --Grouping based on title name
ORDER BY Total_sales_price desc;                                    --Ordered based on Total Sales price in descending order


-- 5.  Find the sales for stores in California

SELECT sum(t.price*sa.qty) Sales_california
FROM sales sa 
JOIN titles t
ON t.title_id = sa.title_id
JOIN stores st
ON sa.stor_id = st.stor_id
WHERE st.state = 'CA';                                              --For getting sales in California

-- 6.  Use SSMS to generate a script to create the authors table describe what the script does

USE [pubs]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[authors_duplicate_assignment](
	[au_id] [dbo].[id] NOT NULL,
	[au_lname] [varchar](40) NOT NULL,
	[au_fname] [varchar](20) NOT NULL,
	[phone] [char](12) NOT NULL,
	[address] [varchar](40) NULL,
	[city] [varchar](20) NULL,
	[state] [char](2) NULL,
	[zip] [char](5) NULL,
	[contract] [bit] NOT NULL,
 CONSTRAINT [UPKCL_audpidind] PRIMARY KEY CLUSTERED 
(
	[au_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[authors_duplicate_assignment] ADD  DEFAULT ('UNKNOWN') FOR [phone]
GO

ALTER TABLE [dbo].[authors_duplicate_assignment]  WITH CHECK ADD CHECK  (([au_id] like '[0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]'))
GO

ALTER TABLE [dbo].[authors_duplicate_assignment]  WITH CHECK ADD CHECK  (([zip] like '[0-9][0-9][0-9][0-9][0-9]'))
GO

/*
Explanation: The above script uses the pubs database and creates a table with name authors_duplicate_assignment in it. It has attributes as au_id(Data type as id which is a user defined data type and has varchar), 
               au_lname(Datatype varchar length = 40), au_fname(Datatype varchar length = 20), phone(Datatype char length = 12), address(Datatype varchar length = 40),
			   city(Datatype varchar length = 20), state(Datatype char length = 2), zip(Datatype char length = 5), contract(Datatype bit It can take a value of 0,1 or NULL)
			   Attributes au_id, au_lname, au_fname, phone, contract cannot have NULL values. 
			   au_id is the primary key in the table and it has to be unique as well as NOT NULL. 
			   Rules are set with the help of constraints for entering data in the table, for phone it will add default value as UNKNOWN
			   for au_id and zip it will only allow the values between 0 to 9 for each byte.
*/

-- 7.  Describe what the au_id is using for a data type

/*
The au_id in authors table is using a User Defined DataType(ID) which is varchar with length as 11, 
that means it can store a variable sized data upto 11 bytes out of which 2 bytes will hold the length information and remaining will store the characters.
For Example: If au_id is of 4 bytes, then the varchar data type will use 4 bytes for the characters and 2 bytes for the length information and will save remaining spaces.
*/

-- 8.  List the store name, title title name and quantity for all stores that have net 30 payterms

SELECT s.stor_name Store_name, t.title Title, s1.qty Quantity
FROM stores s
JOIN sales s1
ON s.stor_id = s1.stor_id
JOIN titles t 
ON s1.title_id = t.title_id
WHERE payterms = 'NET 30';									--For all stores that have payterms as NET 30

-- 9.  Find the titles that do not have any sales show the name of the title

SELECT t.title Title
FROM titles t
LEFT OUTER JOIN sales s
ON t.title_id = s.title_id
WHERE s.title_id is NULL;                                   --For title_id which does not have sales

-- 10. Do previous question another way

SELECT t.title Title
FROM titles t
WHERE NOT EXISTS (SELECT s.title_id							--For title_id which does not have sales
FROM sales s 
WHERE s.title_id = t.title_id);
