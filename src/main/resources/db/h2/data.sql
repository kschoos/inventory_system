INSERT INTO locations (name) VALUES ('Schrank');
INSERT INTO locations (name) VALUES ('Regal');

INSERT INTO tags (name) VALUES ('Elektronik');
INSERT INTO tags (name) VALUES ('Komponente');
INSERT INTO tags (name) VALUES ('Basteln');

INSERT INTO items (count, image_url, name, location_id) VALUES (5, 'https://placehold.co/600x400', 'Banana', 2);
INSERT INTO items (count, image_url, name, location_id) VALUES (2, 'https://placehold.co/600x400', 'Apple', 1);


INSERT INTO item_tags (item_id, tag_id) VALUES (1, 1);
INSERT INTO item_tags (item_id, tag_id) VALUES (1, 2);
INSERT INTO item_tags (item_id, tag_id) VALUES (2, 2);
INSERT INTO item_tags (item_id, tag_id) VALUES (2, 3);