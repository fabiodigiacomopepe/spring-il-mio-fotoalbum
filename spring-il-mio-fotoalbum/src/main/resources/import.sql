INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT INTO users (first_name, last_name, email, password) VALUES('Mario', 'Rossi', 'mariorossi@gmail.com', '{noop}mario');
INSERT INTO users (first_name, last_name, email, password) VALUES('Luca', 'Bianchi', 'lucabianchi@gmail.com', '{noop}luca');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);