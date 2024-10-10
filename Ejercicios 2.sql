

--Ejercicio01
/*
Realiza un procedimiento llamado listadocuatromasprestados que nos muestre por pantalla un listado de los cuatro libros más prestados y los socios a los
que han sido prestados con el siguiente formato:

NombreLibro1	NumPrestamosLibro1 	GeneroLibro1
	DNISocio1	FechaPrestamoalSocio1
	...
	DNISocion	FechaPrestamoalSocion

El procedimiento debe gestionar adecuadamente las siguientes excepciones:

    a) La tabla Libros está vacía.
    b) La tabla Socios está vacía.
    c) Hay menos de cuatro libros que hayan sido prestados.
*/
USE PracticaJuvenil
select *
from libros
select *
from prestamos
select *
from socios

CREATE OR ALTER PROCEDURE ListadoCuatroMasPrestados 
AS
BEGIN
	
	DECLARE @dniSocio varchar(20),
	@fechaPrestamo date,
	@nLibro varchar(20),
	@refLibro varchar(3),
	@genero varchar(20),
	@nPrestamos int,
	@gLibro varchar(20),
	@contador smallint

	DECLARE cLibros CURSOR FOR
	SELECT TOP 4 L.RefLibro, L.Nombre, L.Genero, COUNT(*) AS Prestamo
	FROM libros AS L
	JOIN prestamos AS P
	ON L.RefLibro = P.RefLibro
	GROUP BY L.RefLibro, Nombre, L.Genero
	ORDER BY Prestamo DESC

	OPEN cLibros
	FETCH cLibros INTO @refLibro, @nLibro, @genero, @contador

	WHILE(@@FETCH_STATUS = 0)
	BEGIN
		DECLARE cSocios CURSOR FOR
		SELECT P.FechaPrestamo, S.Dni
		FROM socios AS S
		JOIN prestamos AS P
		ON S.Dni = P.Dni
		WHERE RefLibro = @refLibro
		OPEN cSocios
		FETCH cSocios INTO @fechaPrestamo, @dniSocio 

		PRINT CONCAT ( @nLibro, ' ', @contador, ' ' , @genero)

		WHILE(@@FETCH_STATUS = 0)
			BEGIN
				PRINT CONCAT ('	', @dniSocio, ' ', @fechaPrestamo)
				FETCH cSocios INTO @fechaPrestamo, @dniSocio
				
			END
		FETCH cLibros INTO @refLibro, @nLibro, @genero, @contador
		CLOSE cSocios
		DEALLOCATE cSocios
	END
		CLOSE cLibros
		DEALLOCATE cLibros
END

EXECUTE ListadoCuatroMasPrestados



--Ejercicio02
/*
Diseña un procedimiento al que pasemos como parámetro de entrada el nombre de uno de los módulos existentes en la BD y visualice el nombre de los alumnos
que lo han cursado junto a su nota.
Al final del listado debe aparecer el nº de suspensos, aprobados, notables y sobresalientes.
Asimismo, deben aparecer al final los nombres y notas de los alumnos que tengan la nota más alta y la más baja.
Debes comprobar que las tablas tengan almacenada información y que exista el módulo cuyo nombre pasamos como parámetro al procedimiento.
*/

USE ALUMNOS

SELECT * FROM ALUMNOS
SELECT * FROM ASIGNATURAS
SELECT * FROM NOTAS

CREATE OR ALTER PROCEDURE compruebaNotas (@nAsignatura varchar(20))
AS 
BEGIN
	
	DECLARE @apenom varchar(40),
	@nota smallint,
	@suspensos smallint,
	@aprobado smallint,
	@suspenso smallint,
	@notable smallint,
	@sobresaliente smallint,
	@minima smallint,
	@maxima smallint

	SET @suspensos = 0
	SET @aprobado = 0
	SET @suspenso = 0
	SET @notable = 0
	SET @sobresaliente = 0
	SET @minima = 10
	SET @maxima = 0

	DECLARE cAlumnos CURSOR FOR
	SELECT APENOM, NOTA
	FROM NOTAS AS N
	JOIN ASIGNATURAS AS AG
	ON N.COD = AG.COD
	JOIN ALUMNOS AS AL
	ON AL.DNI = N.DNI
	WHERE NOMBRE = @nAsignatura
	GROUP BY APENOM, NOTA
	OPEN cAlumnos
	FETCH cAlumnos INTO @apenom, @nota

	WHILE(@@FETCH_STATUS = 0)
	BEGIN
		
		IF (@nota >= 5)
		BEGIN
			SET @aprobado += 1
			IF(@nota >= 8)
			BEGIN
				SET @sobresaliente += 1
			END
			ELSE IF (@nota >=7)
			BEGIN
				SET @notable += 1
			END
		END
		ELSE 
		BEGIN
			SET @suspenso += 1
		END

		IF(@nota < @minima)
		BEGIN
			SET @minima = @nota
		END
		IF(@nota > @maxima)
		BEGIN
			SET @maxima = @nota
		END

		PRINT CONCAT (@apenom, ' - ', @nota)
		
		FETCH cAlumnos INTO @apenom, @nota
	END

		PRINT CONCAT ('Suspensos: ', @suspenso)
		PRINT CONCAT ('Aprobados: ', @aprobado)
		PRINT CONCAT ('Notables: ', @notable)
		PRINT CONCAT ('Sobresaliente: ', @sobresaliente)
		PRINT CONCAT ('Máxima: ', @maxima)
		PRINT CONCAT ('Mínima: ', @minima)

	CLOSE cAlumnos
	DEALLOCATE cAlumnos
END

EXECUTE compruebaNotas @nAsignatura = 'fol'

--Ejercicio03

USE PRODUCTOS

SELECT * FROM ventas
SELECT * FROM productos


--a) Realiza un procedimiento que actualice la columna Stock de la tabla Productos a partir de los registros de la tabla Ventas.

--a.1) Suponemos que se han realizado una serie de Ventas (todos los registros añadidos en la tabla Ventas), así debemos realizar un 
--procedimiento para actualizar la tabla Productos con las ventas realizadas que están en la tabla Ventas.


CREATE OR ALTER PROCEDURE actualizaStock  AS
BEGIN

    DECLARE @codProducto int,
    @codVenta VARCHAR(20)

    DECLARE cProducto CURSOR FOR
    SELECT CodProducto, CodVenta FROM Ventas

    OPEN cProducto
    FETCH cProducto INTO @codProducto, @codVenta

    WHILE(@@FETCH_STATUS = 0)
    BEGIN
        IF ((SELECT Stock FROM Productos WHERE CodProducto = @codProducto) > (SELECT ISNULL(UnidadesVendidas,0)FROM Ventas WHERE CodVenta = @codVenta))
        BEGIN
            UPDATE Productos 
            SET Stock = Stock - (SELECT ISNULL(UnidadesVendidas, 0)FROM Ventas WHERE CodVenta = @codVenta)
            WHERE CodProducto = @codProducto
        END
        FETCH cProducto INTO @codProducto, @codVenta
    END
    CLOSE cProducto
    DEALLOCATE cProducto
END


BEGIN TRANSACTION
EXECUTE actualizaStock
Select * from productos
ROLLBACK

/*a.2) Mediante Triggers: Tenemos la tabla Ventas y Productos, debemos actualizar la tabla Productos con las modificaciones que se hagan en la tabla Ventas de la siguiente forma:
- Si se aumentan las unidades vendidas de una venta ya realizada (me pasarán el código de la venta, el código del producto y las unidades vendidas), se deberá actualizar el Stock
de la tabla Productos.
- Si se realiza una devolución de una venta (me pasan el código de la venta, el código del producto y las unidades devueltas), se deberá actualizar el Stock de la tabla Productos.
Hay que tener en cuenta que si se devuelven todas las unidades que habían sido vendidas, se deberá borrar esa venta de la tabla Ventas.
*/

CREATE OR ALTER TRIGGER ActualizaStockVenta
ON Ventas
AFTER UPDATE
AS
BEGIN
	UPDATE productos
    SET Stock = stock - (I.UnidadesVendidas - D.UnidadesVendidas)
    FROM productos AS P
    JOIN Inserted AS I ON P.CodProducto = I.CodProducto
    JOIN Deleted AS D ON P.CodProducto = D.CodProducto

	DELETE FROM Ventas
    WHERE CodVenta IN (
        SELECT I.CodVenta
        FROM Inserted AS I
        JOIN Deleted AS D ON I.CodVenta = D.CodVenta
        WHERE I.UnidadesVendidas = 0
    )
    
END


BEGIN TRANSACTION
UPDATE ventas
SET UnidadesVendidas = 0
WHERE CodVenta = 'V1'
SELECT * FROM ventas
SELECT * FROM PRODUCTOS
ROLLBACK

INSERT INTO ventas (CodVenta, CodProducto, UnidadesVendidas) VALUES ('V5', '2', '2')

/*
b) Realiza un procedimiento que presente por pantalla un listado de las ventas con el siguiente formato:

Linea Producto: NombreLinea1
	
	Prod11		UnidadesTotales1	ImporteTotal1
*/


CREATE OR ALTER PROCEDURE imprimeProductos
AS
BEGIN
	DECLARE @codProducto int, 
	@lineaProducto varchar(20),
	@importeTotal money,
	@stock int

	DECLARE cLineaProducto CURSOR FOR
	SELECT LineaProducto
	FROM productos
	GROUP BY LineaProducto 
	OPEN cLineaProducto
	FETCH cLineaProducto INTO @lineaProducto
	WHILE(@@FETCH_STATUS = 0)
	BEGIN
		PRINT CONCAT('Linea de producto: ', @lineaProducto)
		

		DECLARE cPrintProductos CURSOR FOR 
		SELECT codProducto, Stock
		FROM productos
		WHERE LineaProducto = @lineaProducto
		OPEN cPrintProductos
		FETCH cPrintProductos INTO @codProducto, @stock
		WHILE(@@FETCH_STATUS = 0)
		BEGIN
			SET @importeTotal =
								((SELECT PrecioUnitario FROM productos WHERE CodProducto = @codProducto) * 
								(SELECT ISNULL(SUM(UnidadesVendidas), 0) FROM ventas WHERE CodProducto = @codProducto))
			PRINT CONCAT(@codProducto, ' - ',@stock, ' - ', @importeTotal)
			FETCH cPrintProductos INTO @codProducto, @stock
		END
		FETCH cLineaProducto INTO @lineaProducto
		CLOSE cPrintProductos
		DEALLOCATE cPrintProductos	
	END

	CLOSE cLineaProducto
	DEALLOCATE cLineaProducto
	
END

EXECUTE imprimeProductos

SELECT * FROM productos


