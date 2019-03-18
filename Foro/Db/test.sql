
-- Valores de prueba ==========================================================
INSERT INTO post(title, description, img_url) 
	VALUES ("Un toston", "Los huachicoleros se prendieron.", "urlimagen0");
INSERT INTO user_post(nick, id_post, post_date, category)
	VALUES ("angelon", 1, "2019-03-17", "ocio");

INSERT INTO post(title, description, img_url) 
	VALUES ("Acuchillado", "Novia amorosa apuñala amorosamente a su parega <3", "urlimagen1");
INSERT INTO user_post(nick, id_post, post_date, category)
	VALUES ("filera", 2, "2019-03-17", "amor");

SELECT * FROM post;
SELECT * FROM user_post;

DELETE FROM post;
ALTER TABLE post AUTO_INCREMENT = 1;
DELETE FROM user_post;

