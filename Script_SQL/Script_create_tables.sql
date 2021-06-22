drop table if exists Etudiants;
drop table if exists Adresses;
drop table if exists ServicesGestion;

create table Etudiants(
	id SERIAL primary key,
	nom varchar(30),
	prenom varchar(30),
	numEtudiant int,
	numPortable int,
	mail varchar(40),
	typeAffiliation varchar(10),
	caisseAssurance varchar(10)
);

create table Adresses(
	id SERIAL primary key,
	adresse varchar(100),
	codePostal int,
	ville varchar(30),
	pays varchar(30)
);

create table ServicesGestion(
	id SERIAL primary key,
	nom varchar(30),
	prenom varchar(30),
	numeroTel int,
	mail varchar(30),
	adresse varchar(200)
)