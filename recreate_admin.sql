-- Primero elimina el usuario admin si existe
DELETE FROM users WHERE username = 'admin';

-- Inserta el usuario admin con la contraseña 'admin123' (hasheada con BCrypt)
-- Esta es la hash BCrypt de 'admin123'
INSERT INTO users (username, password, email, role, created_at, updated_at) 
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IizAGdXMGJ7BsF8N7CGY1p25VWN/8y', 'admin@peluqueriamarzetti.com', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Verificar que el usuario se insertó correctamente
SELECT * FROM users WHERE username = 'admin';
