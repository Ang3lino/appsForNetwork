
	-- Valores de prueba ==========================================================

	INSERT INTO post(title, description, img_url) 
		VALUES ("Un toston", "Los huachicoleros se prendieron.", "urlimagen0");
	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES ("angelon", 1, "2019-03-17", "ocio");

	INSERT INTO post(title, description, img_url) 
		VALUES ("Acuchillado", "Novia amorosa apuñala amorosamente a su parega <3", "urlimagen1");
	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES ("filera", 2, "2019-03-17", "amor");

	INSERT INTO post(title, description, img_url) 
		VALUES ("Sillas voladoras (De verdad)", 
			"Niños se suben a las sillas voladoras las cuales no estaban bien atornilladas lo cual ocasion cuando se cayeran, terrible, oremos.", null);
	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES ("ricky", 3, "2019-03-17", "impacto");

	INSERT INTO post(title, description, img_url) 
		VALUES ("San Pedro la nacion del fuego", 
			"Explosion de una pipa ocasiono multiples muertos", null);
	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES ("Zuko", 4, "2019-03-17", "impacto");

	INSERT INTO post(title, description, img_url) 
		VALUES (
			"El ayuwoki", 
			"Michael Jackson resucita y provoca un gi-giro en redes sociales", 
			null
		);
	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES ("Annie", 5, "2019-03-17", "impacto");

	INSERT INTO post(title, description, img_url) 
		VALUES (
			"El picador criminal mutilador de Ecatepec", 
			"Pareja criminal asesina en seria destaza multiples mujeres en Ecatec. En Ecatepec repudiamos completamente lo sucedido ya que todo es paz y felicidad.", 
			null
		);
	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES ("Annie", 6, "2019-03-17", "impacto");

	INSERT INTO post(title, description, img_url) 
		VALUES (
			"Alumnos del IPN descubren que el nopal es comestible", 
			"Impactante descubrimiento realizan estudiantes de biologia mientras combinaban nopal con chocolate.", 
			null
		);
	INSERT INTO user_post(nick, id_post, post_date, category)
		VALUES ("Annie", 7, "2019-03-17", "impacto");

	SELECT * FROM post;
	SELECT * FROM user_post;

	DELETE FROM post;
	ALTER TABLE post AUTO_INCREMENT = 1;
	DELETE FROM user_post;

