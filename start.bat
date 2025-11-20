@echo off
echo ============================================
echo  Peluquer�a Marzetti - Backend Setup
echo ============================================
echo.

echo STEP 1: Verificando PostgreSQL...
echo Aseg�rate de que PostgreSQL est� corriendo y que tengas una base de datos llamada 'peluqueria_marzetti'
echo.
echo Puedes crear la base de datos con:
echo   CREATE DATABASE peluqueria_marzetti;
echo.
pause

echo.
echo STEP 2: Configurando credenciales...
echo Edita el archivo: src\main\resources\application.properties
echo Documentaci�n API: http://localhost:8080/swagger-ui.html
echo.
echo Presiona Ctrl+C para detener el servidor
echo.

echo STEP 3: Compilando el proyecto...
call mvn clean package -U -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo ERROR: La compilación falló!
    echo Verifica que tienes Java 17 y Maven instalados
    pause
    exit /b %errorlevel%
)

echo.
echo STEP 4: Iniciando el servidor...
call mvn spring-boot:run
