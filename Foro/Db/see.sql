create table votante ( -- super entidad
	email varchar(32) primary key not null,
	nombre varchar(64),
	localidad varchar(64),
	sexo enum('m', 'f'), -- masculino, femenino. OJO que si pones algun caracter distino a este no se inserta nada
	fecha_nacimiento date,
	passwd varchar(32),
	perfil varchar(256)  -- url de la foto de perfil 
);

create table sala_votacion (
	numero int not null auto_increment primary key, -- autoincrementable 
	se_puede_votar boolean, -- tiny int 
	email_creador varchar(32) not null
);

alter table sala_votacion add foreign key (email_creador) references votante(email)
	on delete cascade ;

create table postulante (
	post_email varchar(32) not null,
	sala_num int not null,
	post_logo varchar(256),
	escolaridad varchar(32),
	partido varchar(32),
	primary key (post_email, sala_num),
	foreign key (post_email) references votante(email) on delete cascade,
	foreign key(sala_num) references sala_votacion(numero) on delete cascade
);

-- aqui el usuario pondra algun sitio de contacto
create table post_sitio (
	num int not null,	
	email varchar(32) not null,
	sitio varchar(64) not null,
	primary key (num, email, sitio),
	foreign key (email, num) references postulante (post_email, sala_num)
		on delete cascade
);

create table propuesta (
	num int not null,
	post_email varchar(32) not null,
	nombre varchar(64),
	presupuesto decimal(10, 2),
	descripcion varchar(64),
	primary key (num, post_email, nombre),
	foreign key (post_email, num) references postulante(post_email, sala_num) 
		on delete cascade
);
	
-- el orden de las llaves debe coincidir
create table prop_beneficio (
	num int not null,
	post_email varchar(32) not null,
	prop_nombre varchar(64) not null,
	descripcion varchar(64) not null,
	primary key (num, post_email, prop_nombre, descripcion),
	foreign key (num, post_email, prop_nombre)  
		references propuesta (num, post_email, nombre)
		on delete cascade
);

create table prop_lugar (
	num int not null,
	post_email varchar(32) not null,
	prop_nombre varchar(64) not null,
	lugar varchar(64) not null,
	primary key (num, post_email, prop_nombre, lugar),
	foreign key (num, post_email, prop_nombre) 
		references propuesta(num, post_email, nombre)
);

-- relacion en la que intervienen las personas con la votacion
create table medio_votacion ( 
	post_email varchar(32) not null,
	email varchar(32) not null, -- del votante
	numero int not null, -- de la votacion
	primary key (post_email, email, numero)
);

alter table medio_votacion 
	add foreign key(post_email) 
	references postulante(post_email) on delete cascade ;

alter table medio_votacion
	add foreign key(email) 
	references votante(email) on delete cascade on update cascade;

alter table medio_votacion 
	add foreign key(numero) 
	references sala_votacion(numero) on delete cascade ;

