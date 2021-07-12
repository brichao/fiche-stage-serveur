package com.projet.fiche.Etudiant;

//Classe java etudiant qui respecte le schéma défini dans la base de donnée, avec les getters/setters et réécriture de le méthode
//toString() pour le bon affichage d'un objet etudiant
public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private int numEtudiant;
    private int numPortable;
    private String mail;
    private String adresse;
    private String typeAffiliation;
    private String caisseAssurance;

    public Etudiant(){
    }

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

    public int getNumEtudiant(){
        return this.numEtudiant;
    }

    public void setNumEtudiant(int numEtudiant){
        this.numEtudiant=numEtudiant;
    }

    public int getNumPortable(){
        return this.numPortable;
    }

    public void setNumPortable(int numPortable){
        this.numPortable=numPortable;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail=mail;
    }

    public String getTypeAffiliation(){
        return this.typeAffiliation;
    }

    public void setTypeAffiliation(String typeAffiliation){
        this.typeAffiliation=typeAffiliation;
    }

    public String getCaisseAssurance(){
        return this.caisseAssurance;
    }

    public void setCaisseAssurance(String caisseAssurance){
        this.caisseAssurance=caisseAssurance;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public void setAdresse(String adresse){
        this.adresse=adresse;
    }
}
