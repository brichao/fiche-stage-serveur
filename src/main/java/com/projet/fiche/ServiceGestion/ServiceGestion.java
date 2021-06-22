package com.projet.fiche.ServiceGestion;

public class ServiceGestion {
    private int idService;
    private String nom;
    private String prenom;
    private int numeroTel;
    private String mail;
    private String adresse;

    ServiceGestion(){}

    ServiceGestion(int idService, String nom, String prenom, int numeroTel, String mail, String adresse){
        this.idService=idService;
        this.nom=nom;
        this.prenom=prenom;
        this.numeroTel=numeroTel;
        this.mail=mail;
        this.adresse=adresse;
    }

    public int getId(){
        return this.idService;
    }

    public void setId(int idService){
        this.idService=idService;
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

    public int getNumeroTel(){
        return this.numeroTel;
    }

    public void setNumeroTel(int numeroTel){
        this.numeroTel=numeroTel;
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
}
