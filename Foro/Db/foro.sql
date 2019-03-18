    
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
CREATE PROCEDURE find_by_keyword(IN p_key VARCHAR(64)) 
BEGIN

	--SELECT 	p.title, p.description, p.img_url, u.nick, u.post_date, u.category
	SELECT 	p.id, p.title, u.post_date 
	FROM post p, user_post u 
	WHERE p.id = u.id_post
		AND ( p.title LIKE CONCAT("%", p_key, "%")
			OR u.post_date LIKE CONCAT("%", p_key, "%") )
	ORDER BY p.id, p.title;

END &
DELIMITER ; -- end definition =================================================



show tables;
desc post;
desc user_post;

call get_list_post;