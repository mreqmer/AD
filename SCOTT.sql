USE SCOTT

--Ejercicio 1
--Haz una función llamada DevolverCodDept que reciba el nombre de un departamento y devuelva su código.

SELECT * FROM DEPT

CREATE OR ALTER FUNCTION DevolverCodDept (@nombreDep varchar(20))
RETURNS smallint
	BEGIN
		DECLARE @numDep smallint
		SET @numDep =  (SELECT DEPTNO FROM DEPT WHERE @nombreDep = DNAME)
		RETURN @numDep
	END

SELECT dbo.DevolverCodDept('OPERATIONS') AS NumDept


--Ejercicio 2
--Realiza un procedimiento llamado HallarNumEmp que recibiendo un nombre de departamento, muestre en pantalla el número de empleados de dicho departamento. Puedes utilizar la función creada en el ejercicio 1.
--Si el departamento no tiene empleados deberá mostrar un mensaje informando de ello. Si el departamento no existe se tratará la excepción correspondiente.

SELECT * FROM DEPT
SELECT * FROM EMP

CREATE OR ALTER PROCEDURE HallarNumEmp (@nombreDep varchar(20))
AS 
	BEGIN
		DECLARE @numDept smallint
		DECLARE @numEmp smallint
		SET @numDept = (SELECT dbo.DevolverCodDept(@nombreDep))
		SELECT COUNT(*) AS empleados FROM EMP WHERE (@numDept = DEPTNO) 
	END

EXECUTE HallarNumEmp @nombreDep = 'sales'
EXECUTE HallarNumEmp @nombreDep = 'operations'
EXECUTE HallarNumEmp @nombreDep = 'a'

--Ejercicio 3
--Realiza una función llamada CalcularCosteSalarial que reciba un nombre de departamento y devuelva la suma de los salarios y comisiones de los empleados de dicho departamento. Trata las excepciones que consideres necesarias.

SELECT * FROM DEPT
SELECT * FROM EMP
SELECT * FROM BONUS
SELECT * FROM SALGRADE

CREATE OR ALTER FUNCTION CalcularCosteSalarial (@nombreDept varchar(20))
RETURNS int
	BEGIN
		DECLARE @sumaSalarios money
		DECLARE @sumaComisiones money
		DECLARE @sumaSalarioComisiones money
		DECLARE @numDept int
		SET @numDept = (SELECT dbo.DevolverCodDept(@nombreDept))
		SET @sumaSalarios = (SELECT SUM(SAL) FROM EMP WHERE (@numDept = DEPTNO))
		--SET @sumaSalarios = (SELECT SUM(ISNULL(SAL, 0)) FROM EMP WHERE (@numDept = DEPTNO))
		SET @sumaComisiones = (SELECT SUM(ISNULL(COMM, 0)) FROM EMP WHERE (@numDept = DEPTNO))
		SET @sumaSalarioComisiones = @sumaSalarios + @sumaComisiones
		RETURN @sumaSalarioComisiones
	END

SELECT dbo.CalcularCosteSalarial('SALES') as CosteSalarial
SELECT dbo.CalcularCosteSalarial('OPERATIONS') as CosteSalarial
SELECT dbo.CalcularCosteSalarial('ACCOUNTING') as CosteSalarial
SELECT dbo.CalcularCosteSalarial('RESEARCH') as CosteSalarial
SELECT dbo.CalcularCosteSalarial('j') as CosteSalarial

--Ejercicio 4 Cr
--Realiza un procedimiento MostrarCostesSalariales que muestre los nombres de todos los departamentos y el coste salarial de cada uno de ellos. Puedes usar la función del ejercicio 3.

SELECT * FROM DEPT
SELECT * FROM EMP

CREATE OR ALTER PROCEDURE MostrarCostesSalariales
AS 
	BEGIN
		 SELECT DNAME, dbo.CalcularCosteSalarial(DNAME) AS Costo FROM DEPT
	END

SELECT * FROM EMP
SELECT * FROM DEPT

CREATE OR ALTER PROCEDURE MostrarCostesSalariales
AS
	BEGIN
		DECLARE @dep smallint,
		@dname varchar(20),
		@total int

		DECLARE dCursor CURSOR FOR
		SELECT D.DEPTNO, D.DNAME 
		FROM DEPT AS D 
		JOIN EMP AS E
		ON D.DEPTNO = E.DEPTNO
		GROUP BY D.DNAME, D.DEPTNO
		OPEN dCursor
		FETCH dCursor INTO @dep, @dname
		WHILE(@@FETCH_STATUS = 0)
			BEGIN
				SET @total = (SELECT dbo.CalcularCosteSalarial (@dname))
				PRINT CONCAT(@dname,' : ', @total)
				FETCH dCursor INTO @dep, @dname
			END

		CLOSE dCursor
		DEALLOCATE dCursor
	END

EXECUTE MostrarCostesSalariales

--Ejercicio 5
--Realiza un procedimiento MostrarAbreviaturas que muestre las tres primeras letras del nombre de cada empleado.

CREATE OR ALTER PROCEDURE MostrarAbreviaturas
AS
	BEGIN
		SELECT LEFT(ENAME, 3) AS ABV FROM EMP
	END

EXECUTE MostrarAbreviaturas 

--Ejercicio 6 cr 
--Realiza un procedimiento MostrarMasAntiguos que muestre el nombre del empleado más antiguo de cada departamento junto con el nombre del departamento. Trata las excepciones que consideres necesarias.

SELECT * FROM EMP
SELECT * FROM DEPT
SELECT TOP 1 ENAME, HIREDATE FROM EMP WHERE DEPTNO = 10 ORDER BY HIREDATE

CREATE OR ALTER PROCEDURE MostrarMasAntiguos
AS
	BEGIN
		DECLARE @dep smallint, 
		@dname varchar(20),
		@ename varchar(20)

		DECLARE cAntiguos CURSOR FOR
		SELECT D.DEPTNO, D.DNAME 
		FROM DEPT AS D 
		JOIN EMP AS E
		ON D.DEPTNO = E.DEPTNO
		GROUP BY D.DNAME, D.DEPTNO
		OPEN cAntiguos
		FETCH cAntiguos INTO @dep, @dname

		WHILE(@@FETCH_STATUS = 0)
			BEGIN
				SET @ename = (SELECT TOP 1 ENAME FROM EMP WHERE DEPTNO = @dep ORDER BY HIREDATE DESC)
				PRINT CONCAT(@ename, ' : ', @dname)
				FETCH cAntiguos INTO @dep, @dname 
			END
		CLOSE cAntiguos
		DEALLOCATE cAntiguos
	END

EXECUTE MostrarMasAntiguos

--Ejercicio 7 CR 
--Realiza un procedimiento MostrarJefes que reciba el nombre de un departamento y muestre los nombres de los empleados de ese departamento que son jefes de otros empleados.Trata las excepciones que consideres necesarias.

SELECT * FROM DEPT
SELECT * FROM EMP
SELECT * FROM SALGRADE
SELECT * FROM BONUS

CREATE OR ALTER PROCEDURE MostrarJefes (@dName varchar(20))
AS 
	BEGIN
		DECLARE @dep smallint, 
		@depname varchar(20),
		@empname varchar(20),
		@empnumber int,
		@cosanombre varchar(20),
		@cosaid int

		DECLARE cJefes CURSOR FOR
		SELECT E2.EMPNO, E2.ENAME 
		FROM EMP AS E1 
		JOIN EMP AS E2 ON E2.EMPNO = E1.MGR  
		GROUP BY  E2.EMPNO, E2.ENAME

		OPEN cJefes
		FETCH cJefes INTO @empnumber, @empname

		WHILE(@@FETCH_STATUS = 0)
			BEGIN
				SET @cosanombre = (SELECT ENAME FROM EMP WHERE EMPNO = @empnumber AND DEPTNO = (SELECT DEPTNO FROM DEPT WHERE DNAME = @dName))
				SET @cosaid = (SELECT EMPNO FROM EMP WHERE EMPNO = @empnumber AND DEPTNO = (SELECT DEPTNO FROM DEPT WHERE DNAME = @dName))
				PRINT CONCAT(@cosaid, ' ', @cosanombre)
				FETCH cJefes INTO  @empnumber, @empname
			END

		CLOSE cJefes
		DEALLOCATE cJefes
		
	END

EXECUTE MostrarJefes @dname = 'SALES'
EXECUTE MostrarJefes @dname = 'OPERATIONS'
EXECUTE MostrarJefes @dname = 'ACCOUNTING'
EXECUTE MostrarJefes @dname = 'RESEARCH'

--Ejercicio 8
--Realiza un procedimiento MostrarMejoresVendedores que muestre los nombres de los dos vendedores con más comisiones. Trata las excepciones que consideres necesarias.

SELECT * FROM EMP

CREATE OR ALTER PROCEDURE MostrarMejoresVendedores
AS
	BEGIN
		SELECT TOP 2 ENAME FROM EMP ORDER BY  COMM DESC
	END

--Ejercicio 9



--Ejercicio 10
--Realiza un procedimiento RecortarSueldos que recorte el   sueldo un 20% a los empleados cuyo nombre empiece por la  letra que recibe como parámetro.Trata las excepciones  que consideres necesarias

CREATE OR ALTER PROCEDURE RecortarSueldos (@letra char)
AS
	BEGIN
		--SELECT ENAME FROM EMP WHERE ENAME LIKE CONCAT(@letra, '%')
		UPDATE EMP
		SET SAL = SAL - (SAL*0.2)
		WHERE ENAME LIKE CONCAT(@letra, '%')
	END

BEGIN TRANSACTION
EXECUTE RecortarSueldos @letra = 'b'
select * from emp
ROLLBACK

--Ejercicio 11 cr
 --Realiza un procedimiento BorrarBecarios que borre a los dos empleados más nuevos de cada departamento. Trata las excepciones que consideres necesarias.

 CREATE OR ALTER PROCEDURE BorrarBecarios
 AS
	BEGIN
		DECLARE @dep smallint, 
		@empNo int

		DECLARE cBecarios CURSOR FOR
		SELECT D.DEPTNO
		FROM DEPT AS D 
		JOIN EMP AS E
		ON D.DEPTNO = E.DEPTNO
		GROUP BY D.DEPTNO
		OPEN cBecarios
		FETCH cBecarios INTO @dep

		WHILE(@@FETCH_STATUS = 0)
			BEGIN
				--SET @empNo = (SELECT TOP 1 EMPNO FROM EMP WHERE DEPTNO = @dep ORDER BY HIREDATE DESC)
				DELETE FROM EMP WHERE EMPNO IN (SELECT TOP 2 EMPNO FROM EMP WHERE DEPTNO = @dep ORDER BY HIREDATE DESC)
				FETCH cBecarios INTO @dep
			END
		CLOSE cBecarios
		DEALLOCATE cBecarios
	END

BEGIN TRANSACTION

SELECT * FROM EMP
ROLLBACK