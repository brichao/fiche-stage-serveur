drop table if exists Etudiants;

create table Etudiants(
	id SERIAL primary key not null,
	nom varchar(30),
	prenom varchar(30),
	numEtudiant int,
	numPortable int,
	mail varchar(40),
	typeAffiliation varchar(10),
	inscription varchar(100),
	enseignant varchar(30)
);