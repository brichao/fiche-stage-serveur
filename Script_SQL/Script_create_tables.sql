drop table if exists Etudiants;
drop table if exists Etablissements;
drop table if exists Adresses;
drop table if exists ServicesGestion;
drop table if exists Tuteurs;
drop table if exists infosStages;

create table Etudiants(
	id SERIAL primary key,
	nom varchar(30),
	prenom varchar(30),
	numEtudiant int,
	numPortable int,
	mail varchar(40),
	adresse varchar(200),
	typeAffiliation varchar(40),
	caisseAssurance varchar(40)
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
	mail varchar(50),
	adresse varchar(500)
);

create table Tuteurs(
	id SERIAL primary key,
	nom varchar(30),
	prenom varchar(30),
	fonction varchar(50),
	service varchar(100),
	numTelephone int,
	mail varchar(50),
	adresse varchar(400),
	disponibilite varchar(20)
);

create table Etablissements(
	id SERIAL primary key,
	raisonSociale varchar(300),
	representantLegal varchar(300),
	fonction varchar(300),
	numeroSiret int,
	codeApe varchar(50),
	domaineActivite varchar(300),
	effectif int,
	idAdresse int,
	serviceAccueil varchar(700),
	constraint idAdresse_fk foreign key(idAdresse) references Adresses(id)
);

create table infosStages(
	id serial primary key,
	dateDebutPartiel Date,
	dateFinPartiel Date,
	dateDebutPlein Date,
	dateFinPlein Date,
	dateDebutInterruption Date,
	dateFinInterruption Date,
	nbHeures real,
	gratification boolean,
	montantGratification real,
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