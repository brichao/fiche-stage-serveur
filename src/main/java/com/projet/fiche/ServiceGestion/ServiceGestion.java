package com.projet.fiche.ServiceGestion;

//Classe java service de gestion qui respecte le schéma défini dans la base de donnée, avec les getters/setters et réécriture de le méthode
//toString() pour le bon affichage d'un objet service de gestion
public class ServiceGestion {
    private int id;
    private String nom;
    private String prenom;
    private int numeroTel;
    private String mail;
    private String adresse;

    private String nomEtudiant;
    private String prenomEtudiant;

    public ServiceGestion(){}

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
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

    public String getNomEtudiant(){
        return this.nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant){
        this.nomEtudiant=nomEtudiant;
    }

    public String getPrenomEtudiant(){
        return this.prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant){
        this.prenomEtudiant=prenomEtudiant;
    }

    @Override
    public String toString(){
        return "id = " + this.id + ", nom = " + this.nom + ", prenom = " + this.prenom + ", num tel = " + this.numeroTel
        + ", mail = " + this.mail + ", adresse = " + this.adresse;
    }
}
