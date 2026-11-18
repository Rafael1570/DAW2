-- Solo datos didácticos; las contraseñas de altas y ediciones se cifran con BCrypt.
INSERT IGNORE INTO users (id,email,password_hash,active,account_non_locked,email_verified,must_change_password,failed_login_attempts,last_password_change,password_expires_at) VALUES
(1,'admin@app.local','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',TRUE,TRUE,TRUE,FALSE,0,NOW(),DATE_ADD(NOW(), INTERVAL 3 MONTH)),
(2,'jdoe@app.local','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',TRUE,TRUE,FALSE,FALSE,1,NOW(),DATE_ADD(NOW(), INTERVAL 3 MONTH)),
(3,'maria@app.local','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',TRUE,TRUE,TRUE,TRUE,0,NOW(),DATE_ADD(NOW(), INTERVAL 3 MONTH)),
(4,'blockeduser@app.local','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',FALSE,FALSE,FALSE,FALSE,5,NOW(),DATE_ADD(NOW(), INTERVAL 3 MONTH));
INSERT IGNORE INTO roles (id,name,display_name,description) VALUES
(1,'ROLE_ADMIN','Administrator','Acceso total'),(2,'ROLE_USER','User','Usuario estándar'),(3,'ROLE_MANAGER','Manager','Gestión de datos');
INSERT IGNORE INTO user_roles(user_id,role_id) VALUES (1,1),(1,2),(2,2),(3,2),(3,3);
