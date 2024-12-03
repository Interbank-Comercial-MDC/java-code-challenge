-- Habilitar la autenticación remota
ALTER SYSTEM SET listen_addresses = '*';
-- Configurar las reglas para conexiones remotas
\! echo "host all all 0.0.0.0/0 md5" >> /var/lib/postgresql/data/pg_hba.conf