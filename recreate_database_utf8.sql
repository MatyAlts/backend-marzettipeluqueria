-- Script para recrear la base de datos con encoding UTF-8 correcto
-- IMPORTANTE: Ejecutar este script desde psql como superusuario (postgres)

-- Desconectar todas las conexiones activas
SELECT pg_terminate_backend(pid) 
FROM pg_stat_activity 
WHERE datname = 'peluqueria_marzetti' AND pid <> pg_backend_pid();

-- Eliminar la base de datos existente
DROP DATABASE IF EXISTS peluqueria_marzetti;

-- Crear la base de datos con encoding UTF-8
CREATE DATABASE peluqueria_marzetti
    WITH 
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Argentina.1252'
    LC_CTYPE = 'Spanish_Argentina.1252'
    TEMPLATE = template0;

-- Conectar a la nueva base de datos
\c peluqueria_marzetti

-- Verificar el encoding
SELECT pg_encoding_to_char(encoding) FROM pg_database WHERE datname = 'peluqueria_marzetti';
