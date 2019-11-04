/*7 Create a function to change the case of a string. As Follows.
          This is A TEST String
          becomes         
          tHIS IS a test sTRING*/

IF OBJECT_ID (N'dbo.udfGetOppositeCase', N'FN') IS NOT NULL  --Checking if there is any function with the same name
    DROP FUNCTION udfGetOppositeCase;  
GO
CREATE FUNCTION dbo.udfGetOppositeCase(@Change varchar(100))
RETURNS varchar(100)
AS

BEGIN
    DECLARE @Ret varchar(100);							--Declaring a return variable as varchar(100)
    DECLARE @i int;										--Declaring @i for using as index in while loop
    DECLARE @c char(1);									--Taking first character of the string

	IF @Change is null									--Checking if the string is null or not
		RETURN NULL;

	SELECT @i = 1, @Ret = '';						    --Initializing index and return variable

	WHILE (@i <= LEN(@Change))							--Iterating till the last character in the string
		SELECT @c = SUBSTRING(@Change, @i, 1),
			@Ret = @Ret + CASE WHEN ASCII(@c) BETWEEN 97 and 122 THEN UPPER(@c) WHEN ASCII(@c) BETWEEN 65 and 90 THEN LOWER(@c) ELSE @c END,
			@i = @i + 1
	RETURN @Ret
END

--SELECT dbo.udfGetOppositeCase('This is A TEST String') Case_Change

/*8 Create a function to return the gender of an employee based on the emploee id (gender is Male / Female)*/

IF OBJECT_ID (N'dbo.udfGetGender', N'FN') IS NOT NULL  
    DROP FUNCTION udfGetGender;  
GO
CREATE FUNCTION dbo.udfGetGender(@EMPLOYEEID varchar(10))
RETURNS varchar(30)
AS

BEGIN
	DECLARE @Ret varchar(30)
	SELECT @Ret = CASE WHEN @EMPLOYEEID LIKE '%M' THEN 'Male'        --Returning Male if emp_id ends with M
					   WHEN @EMPLOYEEID LIKE '%F' THEN 'Female' END  --Returning Female if emp_id ends with F
				  FROM EMPLOYEE
				  WHERE emp_id = @EMPLOYEEID
	IF (@Ret IS NULL)   
        SET @Ret = 'Invalid Employee_Id';							 --If emp_id doesn't end with either M or F
    RETURN @Ret  
END;

--SELECT emp_id ID,concat(fname,' ',lname) Name, dbo.udfGetGender(emp_id) Gender FROM EMPLOYEE

/*9 Create a table function that returns the employee columns and a new row called gender (again Male / Female)*/

IF OBJECT_ID (N'dbo.udfGetGenderTable', N'IF') IS NOT NULL  
    DROP FUNCTION udfGetGenderTable;  
GO
CREATE FUNCTION dbo.udfGetGenderTable()
RETURNS TABLE														  --Returns a table with gender column added
AS 
RETURN
	SELECT *, 'gender' = CASE WHEN RIGHT("emp_id",1)='M' THEN 'Male'  --For checking emp_id
					 WHEN RIGHT("emp_id",1)='F' THEN 'Female' END
	FROM employee;

--SELECT * FROM dbo.udfGetGenderTable()

/*10 Write a function to calculate age in years take into account the birth date*/

IF OBJECT_ID (N'dbo.udfGetAge', N'FN') IS NOT NULL
	DROP FUNCTION udfGetAge;
GO
CREATE FUNCTION dbo.udfGetAge(@DOB date)
RETURNS VARCHAR(MAX)
AS
BEGIN
	DECLARE @Ret as VARCHAR(MAX)
	SELECT @Ret = FLOOR(DATEDIFF(month,@DOB,GETDATE())/12)				--For calculating the age based on BirthDate and Current Date
	FROM HumanResources.Employee
	WHERE BirthDate = @DOB
RETURN @Ret																--Returning the age
END;

--SELECT BirthDate, dbo.udfGetAge(BirthDate) AGE FROM HumanResources.Employee;

/*11 Create a SQLprocedure to insert a record into the jobs table in pubs.  The procedure should validate the input before doing the insert.*/

IF OBJECT_ID (N'dbo.InsertIntoJobs', N'P') IS NOT NULL
	DROP PROCEDURE InsertIntoJobs;
GO
CREATE PROCEDURE dbo.InsertIntoJobs(@job_desc VARCHAR(50), @min_lvl TINYINT, @max_lvl TINYINT)
AS
BEGIN
   --Checking If min is less than or equal to max or not and also checking if job_desc is unique or not.
IF (@min_lvl<=@max_lvl) AND (@job_desc not in(SELECT job_desc FROM jobs)) AND @job_desc is not null AND @max_lvl is not null AND @min_lvl is not null
	INSERT INTO dbo.jobs(job_desc, min_lvl, max_lvl) VALUES (@job_desc, @min_lvl, @max_lvl)
ELSE
    --If the above condition doesn't satisfy it will throw an error.
	RAISERROR('Please insert correct values...min_lvl should be less than max_lvl and Job Description should be unique',0,1) WITH NOWAIT
END;
GO
EXECUTE InsertIntoJobs @job_desc = 'Senior Analyst', @min_lvl = 40, @max_lvl =80; 
GO
SELECT * FROM jobs;







