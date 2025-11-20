-- Verificar qué hay en la tabla users
SELECT * FROM users;

-- Si el usuario existe, eliminarlo
DELETE FROM users WHERE username = 'admin';

-- Insertar usuario admin con contraseña admin123
-- Hash generado con BCrypt strength 10
INSERT INTO users (username, password, email, role, created_at) 
VALUES ('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'admin@peluqueriamarzetti.com', 'ADMIN', CURRENT_TIMESTAMP);

-- Verificar el resultado
SELECT username, email, role, LEFT(password, 20) as password_start FROM users WHERE username = 'admin';
