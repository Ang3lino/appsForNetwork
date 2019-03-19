    
-- reset
DROP DATABASE IF EXISTS forum;
CREATE DATABASE forum;
USE forum;

-- soporte para acentos
ALTER DATABASE forum DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE post (
	id INT PRIMARY KEY NOT NULL auto_increment,
	title VARCHAR(64),
	description VARCHAR(256),
	img_url VARCHAR(256)
);

CREATE TABLE user_post (
	nick VARCHAR(32) NOT NULL,
	id_post INT NOT NULL,
	post_date DATE,
	category VARCHAR(32),
	FOREIGN KEY (id_post) REFERENCES post(id) 
			ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (nick, id_post)
);

-- procedures =================================================================

DELIMITER &
CREATE PROCEDURE get_list_post() 
BEGIN

	SELECT 	p.id, p.title, u.post_date 
	FROM post p, user_post u 
	WHERE p.id = u.id_post
	ORDER BY 1, 2, 3;

END &
DELIMITER ; -- end definition =================================================

DELIMITER &
CREATE PROCEDURE get_post(IN p_id_post INT) 
BEGIN

	SELECT 	u.nick, u.post_date, u.category, p.title, p.description, p.img_url
	FROM post p, user_post u 
	WHERE p.id = u.id_post
		AND p_id_post = p.id;

END &
DELIMITER ; -- end definition =================================================

--SELECT 	p.title, p.description, p.img_url, u.nick, u.post_date, u.category
DELIMITER &
CREATE PROCEDURE find_by_keyword(IN p_key VARCHAR(64)) 
BEGIN
	DECLARE v_matchable VARCHAR(64) DEFAULT CONCAT("%", p_key, "%");

	SELECT 	p.id, p.title, u.post_date 
	FROM post p, user_post u 
	WHERE p.id = u.id_post
		AND ( p.title LIKE v_matchable
			OR u.post_date LIKE v_matchable 
			OR p.description LIKE v_matchable )
	ORDER BY p.id, p.title;

END &
DELIMITER ; -- end definition =================================================

DELIMITER &
CREATE PROCEDURE add_post (
	IN p_nick VARCHAR(64),
	IN p_category VARCHAR(32),
	IN p_title VARCHAR(64),
	IN p_description VARCHAR(256),
	IN p_img_url VARCHAR(256)
) 
BEGIN

	DECLARE v_last_index INT DEFAULT 0;
	DECLARE v_current_date DATE DEFAULT CURDATE();

	INSERT INTO post(title, description, img_url) 
		VALUES (p_title, p_description, p_img_url);

	SELECT MAX(id) INTO v_last_index FROM post;

	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES (p_nick, v_last_index, v_current_date, p_category);

END &
DELIMITER ; -- end definition =================================================

-- call add_post("Sigma", "Prueba", "Esto es una prueba", "Probando declaracion de variables en MySQL", NULL);


show tables;
desc post;
desc user_post;

call get_list_post;