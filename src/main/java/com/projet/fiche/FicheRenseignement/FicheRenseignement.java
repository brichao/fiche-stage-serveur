package com.projet.fiche.FicheRenseignement;

import java.sql.Date;

import com.projet.fiche.Etablissement.Etablissement;
import com.projet.fiche.Etudiant.Etudiant;
import com.projet.fiche.InfosStage.InfosStage;
import com.projet.fiche.ServiceGestion.ServiceGestion;
import com.projet.fiche.Tuteur.Tuteur;

//Classe java ficheRenseignement qui respecte le schéma défini dans la base de donnée, avec les getters/setters
public class FicheRenseignement {
    private int idFiche;
    private int idEtudiant;
    private int idEtablissement;
    private int idServiceGestion;
    private int idTuteur;
    private int idInfosStage;

    private Date dateDeCreation;

    private Etudiant etudiant = new Etudiant();
    private Etablissement etablissement = new Etablissement();
    private ServiceGestion serviceGestion = new ServiceGestion();
    private Tuteur tuteur = new Tuteur();
    private InfosStage infosStage = new InfosStage();

    FicheRenseignement(){}

    public int getIdFiche(){
        return this.idFiche;
    }

    public void setIdFiche(int idFiche){
        this.idFiche=idFiche;
    }

    public int getIdEtudiant(){
        return this.idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant){
        this.idEtudiant=idEtudiant;
    }

    public int getIdEtablissement(){
        return this.idEtablissement;
    }

    public void setIdEtablissement(int idEtablissement){
        this.idEtablissement=idEtablissement;
    }

    public int getIdServiceGestion(){
        return this.idServiceGestion;
    }

    public void setIdServiceGestion(int idServiceGestion){
        this.idServiceGestion=idServiceGestion;
    }

    public int getIdTuteur(){
        return this.idTuteur;
    }

    public void setIdTuteur(int idTuteur){
        this.idTuteur=idTuteur;
    }

    public int getIdInfosStage(){
        return this.idInfosStage;
    }

    public void setIdInfosStage(int idInfosStage){
        this.idInfosStage=idInfosStage;
    }

    public Etudiant getEtudiant(){
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant){
        this.etudiant=etudiant;
    }

    public Etablissement getEtablissement(){
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement){
        this.etablissement=etablissement;
    }

    public ServiceGestion getServiceGestion(){
        return this.serviceGestion;
    }

    public void setServiceGestion(ServiceGestion serviceGestion){
        this.serviceGestion=serviceGestion;
    }

    public Tuteur getTuteur(){
        return this.tuteur;
    }

    public void setTuteur(Tuteur tuteur){
        this.tuteur=tuteur;
    }

    public InfosStage getInfosStage(){
        return this.infosStage;
    }

    public void setInfosStage(InfosStage infosStage){
        this.infosStage=infosStage;
    }

    public Date getDateDeCreation(){
        return this.dateDeCreation;
    }

    public void setDateDeCreation(Date dateDeCreation){
        this.dateDeCreation=dateDeCreation;
    }
}
