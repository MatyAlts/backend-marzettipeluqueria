-- Script para crear la base de datos peluqueria_marzetti si no existe
-- Este script debe ejecutarse conectado a la base de datos 'postgres' o 'synapsse'

-- Intentar crear la base de datos (ignorará error si ya existe)
SELECT 'CREATE DATABASE peluqueria_marzetti'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'peluqueria_marzetti')\gexec
