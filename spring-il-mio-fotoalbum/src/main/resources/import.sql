INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT INTO users (first_name, last_name, email, password) VALUES('Mario', 'Rossi', 'mariorossi@gmail.com', '{noop}mario');
INSERT INTO users (first_name, last_name, email, password) VALUES('Luca', 'Bianchi', 'lucabianchi@gmail.com', '{noop}luca');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);

INSERT INTO photos (title, description, url, visible, user_id) VALUES('Wolf', 'Wolf inside the tree', 'https://img.freepik.com/premium-photo/unique-wild-animals-photography-amazing-power-nature-professional-photo-ai-artwork_997220-5323.jpg', true, 1);
INSERT INTO photos (title, description, url, visible, user_id) VALUES('Parrots', 'Parrots on leaves', 'https://img.freepik.com/premium-photo/unique-wild-animals-photography-amazing-power-nature-professional-photo-ai-artwork_997220-5275.jpg', true, 1);
INSERT INTO photos (title, description, url, visible, user_id) VALUES('Bear', 'Bear on stream', 'https://img.freepik.com/premium-photo/unique-wild-animals-photography-song-nature-singing-cuties-professional-photo-ai-artwork_997220-5488.jpg', true, 1);
INSERT INTO photos (title, description, url, visible, user_id) VALUES('Autumn leaves', 'Street with autumn leaves', 'https://previews.123rf.com/images/rokvel/rokvel1412/rokvel141200020/41976741-foresta-di-autunno-nella-luce-del-tramonto-bellissimo-sfondo-della-natura.jpg', true, 2);
INSERT INTO photos (title, description, url, visible, user_id) VALUES('northern Lights', 'Green northern Lights', 'https://i0.wp.com/www.giovannimiele.com/wp-content/uploads/2016/01/islanda-book-fotografico-aurora-boreale.jpg?ssl=1', true, 2);

INSERT INTO categories (name) VALUES('animals');
INSERT INTO categories (name) VALUES('nature');
INSERT INTO categories (name) VALUES('4K');

INSERT INTO photos_categories (photo_id, categories_id) VALUES(1, 1);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(1, 2);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(1, 3);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(2, 1);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(2, 2);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(2, 3);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(3, 1);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(3, 2);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(4, 2);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(5, 2);
INSERT INTO photos_categories (photo_id, categories_id) VALUES(5, 3);