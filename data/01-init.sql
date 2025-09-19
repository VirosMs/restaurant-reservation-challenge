-- Script de inicialización para Sistema de Reservas de Restaurante
-- Se ejecuta automáticamente cuando se crea el contenedor PostgreSQL por primera vez

-- Crear extensiones útiles
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Configurar zona horaria
SET timezone = 'UTC';

-- Crear esquema si no existe
CREATE SCHEMA IF NOT EXISTS public;

-- Dar permisos al usuario de la aplicación
GRANT ALL PRIVILEGES ON SCHEMA public TO api_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO api_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO api_user;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO api_user;

-- =====================================================
-- CREACIÓN DE TABLAS (adaptadas de H2 a PostgreSQL)
-- =====================================================

-- Tabla USERS (Usuarios del sistema)
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Aumentado para hashes BCrypt
    role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN', 'CLIENT')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Tabla RESTAURANT_TABLES (Mesas del restaurante)
CREATE TABLE IF NOT EXISTS restaurant_tables (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    capacidad INTEGER NOT NULL CHECK (capacidad > 0),
    status VARCHAR(50) NOT NULL DEFAULT 'disponible'
        CHECK (status IN ('disponible', 'reservada', 'inactiva')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Tabla RESERVATIONS (Reservas)
CREATE TABLE IF NOT EXISTS reservations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    table_id BIGINT NOT NULL,
    fecha_reserva_inicio TIMESTAMP WITH TIME ZONE NOT NULL,
    fecha_reserva_fin TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'activo'
        CHECK (status IN ('activo', 'cancelado', 'completado')),
    cantidad_personas INTEGER NOT NULL DEFAULT 1 CHECK (cantidad_personas > 0),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),

    -- Claves foráneas
    CONSTRAINT fk_reservations_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_reservations_table FOREIGN KEY (table_id) REFERENCES restaurant_tables(id) ON DELETE CASCADE,

    -- Constraint para asegurar que fecha_fin > fecha_inicio
    CONSTRAINT check_fecha_reserva CHECK (fecha_reserva_fin > fecha_reserva_inicio)
);

-- =====================================================
-- ÍNDICES PARA MEJORAR PERFORMANCE
-- =====================================================

-- Índices para búsquedas frecuentes
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);
CREATE INDEX IF NOT EXISTS idx_restaurant_tables_status ON restaurant_tables(status);
CREATE INDEX IF NOT EXISTS idx_reservations_user_id ON reservations(user_id);
CREATE INDEX IF NOT EXISTS idx_reservations_table_id ON reservations(table_id);
CREATE INDEX IF NOT EXISTS idx_reservations_fecha_inicio ON reservations(fecha_reserva_inicio);
CREATE INDEX IF NOT EXISTS idx_reservations_status ON reservations(status);

-- =====================================================
-- DATOS INICIALES (Migración desde H2)
-- =====================================================

-- Insertar usuarios (con contraseñas hasheadas con BCrypt)
-- Nota: Las contraseñas serán hasheadas por Spring Security en la aplicación
INSERT INTO users (nombre, email, password, role) VALUES
    ('Charles Arruda', 'charlesarruda@hotmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN'), -- password: 1234
    ('Ana Solera', 'anasolera@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'CLIENT'), -- password: 1234
    ('Carlos Pérez', 'carlosperez@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'CLIENT'), -- password: abcd
    ('Lucía Gómez', 'luciagomez@gmail.com', '$2a$10$DvTr4C7w67.3z3Y8FNJ4WuBn8FjHQwO9aKsKk7k2H.QfK4z5F0o1m', 'CLIENT'), -- password: pass123
    ('Miguel Torres', 'migueltorres@gmail.com', '$2a$10$E3T5xJ6p78.4A4Y9GOK5Xua9CfHRwQ0aLsLl8k3H.RgL5A6G1p2n', 'CLIENT'), -- password: secure456
    ('Sofía López', 'sofialopez@gmail.com', '$2a$10$F4U6yK7q89.5B5Z0HPL6Yub0DgISxR1bMtMm9l4I.SiM6B7H2q3o', 'CLIENT') -- password: qwerty
ON CONFLICT (email) DO NOTHING;

-- Insertar mesas del restaurante
INSERT INTO restaurant_tables (nombre, capacidad, status) VALUES
    ('Mesa 1', 4, 'disponible'),
    ('Mesa 2', 2, 'reservada'),
    ('Mesa 3', 6, 'reservada'),
    ('Mesa 4', 8, 'disponible'),
    ('Mesa 5', 4, 'disponible'),
    ('Mesa 6', 2, 'reservada'),
    ('Mesa 7', 6, 'disponible'),
    ('Mesa 8', 8, 'disponible'),
    ('Mesa 9', 4, 'disponible'),
    ('Mesa 10', 2, 'inactiva'),
    ('Mesa 11', 6, 'disponible')
ON CONFLICT DO NOTHING;

-- Insertar reservas (actualizando fechas a fechas más recientes)
INSERT INTO reservations (user_id, table_id, fecha_reserva_inicio, fecha_reserva_fin, status, cantidad_personas) VALUES
    (1, 1, '2024-10-01 12:00:00+00', '2024-10-01 13:00:00+00', 'cancelado', 3),
    (2, 2, '2024-10-02 13:00:00+00', '2024-10-02 14:00:00+00', 'cancelado', 3),
    (3, 3, '2024-10-03 14:00:00+00', '2024-10-03 15:00:00+00', 'activo', 3),
    (4, 4, '2024-10-04 15:00:00+00', '2024-10-04 16:00:00+00', 'activo', 3),
    (5, 5, '2024-10-05 16:00:00+00', '2024-10-05 17:00:00+00', 'cancelado', 3),
    (6, 6, '2024-10-06 17:00:00+00', '2024-10-06 18:00:00+00', 'cancelado', 3),
    (6, 7, '2024-10-07 18:00:00+00', '2024-10-07 19:00:00+00', 'activo', 3),
    (6, 8, '2024-10-08 19:00:00+00', '2024-10-08 20:00:00+00', 'activo', 3),
    (2, 9, '2024-10-09 20:00:00+00', '2024-10-09 21:00:00+00', 'activo', 3),
    (1, 11, '2024-10-11 22:00:00+00', '2024-10-11 23:00:00+00', 'activo', 3),
    (2, 1, '2024-10-12 23:00:00+00', '2024-10-13 00:00:00+00', 'cancelado', 3),
    (3, 2, '2024-10-13 12:00:00+00', '2024-10-13 13:00:00+00', 'cancelado', 3),
    (4, 3, '2024-10-14 13:00:00+00', '2024-10-14 14:00:00+00', 'activo', 3),
    (5, 4, '2024-10-15 14:00:00+00', '2024-10-15 15:00:00+00', 'activo', 3),
    (6, 5, '2024-10-16 15:00:00+00', '2024-10-16 16:00:00+00', 'activo', 3),
    (5, 6, '2024-10-17 16:00:00+00', '2024-10-17 17:00:00+00', 'cancelado', 3)
ON CONFLICT DO NOTHING;

-- =====================================================
-- FUNCIONES Y TRIGGERS (OPCIONAL)
-- =====================================================

-- Función para actualizar timestamp automáticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$ language 'plpgsql';

-- Triggers para actualizar updated_at automáticamente
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_restaurant_tables_updated_at BEFORE UPDATE ON restaurant_tables
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_reservations_updated_at BEFORE UPDATE ON reservations
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Conceder permisos en las nuevas tablas
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO api_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO api_user;

-- Log de inicialización
DO $
BEGIN
    RAISE NOTICE 'Base de datos del Sistema de Reservas de Restaurante inicializada correctamente';
    RAISE NOTICE 'Tablas creadas: users, restaurant_tables, reservations';
    RAISE NOTICE 'Datos de prueba insertados correctamente';
END
$;