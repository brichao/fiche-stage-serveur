package com.projet.fiche.Adresse;

//Classe java adresse qui respecte le schéma défini dans la base de donnée, avec les getters/setters et réécriture de le méthode
//toString() pour le bon affichage d'un objet adresse
public class Adresse {
    private int id;
    private String adresse;
    private int codePostal;
    private String ville;
    private String pays;

    public Adresse(){}

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public void setAdresse(String adresse){
        this.adresse=adresse;
    }

    public int getCodePostal(){
        return this.codePostal;
    }

    public void setCodePostal(int codePostal){
        this.codePostal=codePostal;
    }

    public String getVille(){
        return this.ville;
    }

    public void setVille(String ville){
        this.ville=ville;
    }

    public String getPays(){
        return this.pays;
    }

    public void setPays(String pays){
        this.pays=pays;
    }

    @Override
    public String toString(){
        return "id = " + this.id + ", adresse = " + this.adresse + ", code postal = " + this.codePostal + ", ville = " + this.ville
         + ", pays = " + this.pays;
    }
}