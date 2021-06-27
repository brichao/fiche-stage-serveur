drop table if exists Etudiants;
drop table if exists Etablissements;
drop table if exists Adresses;
drop table if exists ServicesGestion;
drop table if exists Tuteurs;
drop table if exists Stages;

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
);

create table Tuteurs(
	id SERIAL primary key,
	nom varchar(30),
	prenom varchar(30),
	fonction varchar(50),
	service varchar(50),
	numTelephone int,
	mail varchar(30),
	adresse varchar(200),
	disponibilite varchar(20)
);

create table Etablissements(
	id SERIAL primary key,
	raisonSociale varchar(300),
	representantLegal varchar(200),
	fonction varchar(200),
	numeroSiret int,
	codeApe varchar(50),
	domaineActivite varchar(300),
	effectif int,
	idAdresse int,
	serviceAccueil varchar(700),
	idServiceGestion int,
	constraint idAdresse_fk foreign key(idAdresse) references Adresses(id),
	constraint serviceGestion_fk foreign key(idServiceGestion) references ServicesGestion(id)
);

create table Stages(
	id serial primary key,
	dateDebutPartiel Date,
	dateFinPartiel Date,
	dateFinPlein Date,
	dateDebutInterruption Date,
	dateFinInterruption Date,
	nbHeures int,
	gratification boolean,
	montantGratification int,
	versementGratification varchar(20),
	laboratoireUGA varchar(50),
	avantages varchar(50),
	confidentialite boolean,
	titre varchar(100),
	description varchar(1500),
	objectifs varchar(1500),
	taches varchar(1500),
	details varchar(1500)
);