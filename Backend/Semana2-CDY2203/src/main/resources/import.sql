-- Contraseña para todos es: password123
INSERT INTO user (id, username, email, password, enabled) VALUES (1, 'admin', 'admin@duoc.cl', '$2a$12$R9h/lIPzHZluv601zs8uRe58WzZ7lyp2ReM8nS.jS.S8/l/yK/Z.m', 1);
INSERT INTO user (id, username, email, password, enabled) VALUES (2, 'veterinario', 'vet@duoc.cl', '$2a$12$R9h/lIPzHZluv601zs8uRe58WzZ7lyp2ReM8nS.jS.S8/l/yK/Z.m', 1);
INSERT INTO user (id, username, email, password, enabled) VALUES (3, 'asistente', 'asistente@duoc.cl', '$2a$12$R9h/lIPzHZluv601zs8uRe58WzZ7lyp2ReM8nS.jS.S8/l/yK/Z.m', 1);

-- Actualizar la secuencia para que Hibernate no intente usar IDs ya ocupados
UPDATE user_seq SET next_val = 4;