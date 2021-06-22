package com.projet.fiche.Adresse;

public class Adresse {
    private int id;
    private String adresse;
    private int codePostal;
    private String ville;
    private String pays;

    Adresse(){}

    Adresse(int id, String adresse, int codePostal, String ville, String pays){
        this.id=id;
        this.adresse=adresse;
        this.codePostal=codePostal;
        this.ville=ville;
        this.pays=pays;
    }

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
}