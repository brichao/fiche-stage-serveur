package com.projet.fiche.FicheRenseignement;

import com.projet.fiche.Etudiant.Etudiant;

//Classe java ficheRenseignement qui respecte le schéma défini dans la base de donnée, avec les getters/setters
public class FicheRenseignement {
    private int idFiche;
    private int idEtudiant;
    private int idEtablissement;
    private int idServiceGestion;
    private int idTuteur;
    private int idInfosStage;
    private Etudiant etudiant = new Etudiant();

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
}
