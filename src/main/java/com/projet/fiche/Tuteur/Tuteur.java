package com.projet.fiche.Tuteur;

//Classe java Tuteur qui respecte le schéma défini dans la base de donnée, avec les getters/setters et réécriture de le méthode
//toString() pour le bon affichage d'un objet tuteur
public class Tuteur {
    private int idTuteur;
    private String nom;
    private String prenom;
    private String fonction;
    private String service;
    private int numTelephone;
    private String mail;
    private String adresse;
    private String disponibilite;

    public Tuteur(){}

     public int getId(){
        return this.idTuteur;
    }

    public void setId(int idTuteur){
        this.idTuteur=idTuteur;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    public String getFonction(){
        return this.fonction;
    }

    public void setFonction(String fonction){
        this.fonction=fonction;
    }

    public String getService(){
        return this.service;
    }

    public void setService(String service){
        this.service=service;
    }

    public int getNumTelephone(){
        return this.numTelephone;
    }

    public void setNumTelephone(int numTelephone){
        this.numTelephone=numTelephone;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail=mail;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public void setAdresse(String adresse){
        this.adresse=adresse;
    }

    public String getDisponibilite(){
        return this.disponibilite;
    }

    public void setDisponibilite(String disponibilite){
        this.disponibilite= disponibilite;
    }
}
