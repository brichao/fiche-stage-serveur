package com.projet.fiche.Etablissement;

import com.projet.fiche.Adresse.Adresse;

//Classe java etablissement qui respecte le schéma défini dans la base de donnée, avec les getters/setters et réécriture de le méthode
//toString() pour le bon affichage d'un objet etablissement
//Un objet a été créé de type Adresse parce que l'adresse de l'entreprise peut ne pas être unique
public class Etablissement {
    private int id;
    private String raisonSociale;
    private String representantLegal;
    private String fonction;
    private int numeroSiret;
    private String codeApe;
    private String domaineActivite;
    private int effectif;  
    private int idAdresse;
    private Adresse adresse = new Adresse();
    private String serviceAccueil;

    public Etablissement(){}

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getRaisonSociale(){
        return this.raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale){
        this.raisonSociale=raisonSociale;
    }

    public String getRepresentantLegal(){
        return this.representantLegal;
    }

    public void setRepresentantLegal(String representantLegal){
        this.representantLegal=representantLegal;
    }

    public String getFonction(){
        return this.fonction;
    }

    public void setFonction(String fonction){
        this.fonction=fonction;
    }

    public int getNumeroSiret(){
        return this.numeroSiret;
    }

    public void setNumeroSiret(int numeroSiret){
        this.numeroSiret=numeroSiret;
    }

    public String getCodeApe(){
        return this.codeApe;
    }

    public void setCodeApe(String codeApe){
        this.codeApe=codeApe;
    }

    public String getDomaineActivite(){
        return this.domaineActivite;
    }

    public void setDomaineActivite(String domaineActivite){
        this.domaineActivite=domaineActivite;
    }

    public int getEffectif(){
        return this.effectif;
    }

    public void setEffectif(int effectif){
        this.effectif=effectif;
    }

    public int getIdAdresse(){
        return this.idAdresse;
    }

    public void setIdAdresse(int idAdresse){
        this.idAdresse=idAdresse;
    }

    public String getServiceAccueil(){
        return this.serviceAccueil;
    }

    public void setServiceAccueil(String serviceAccueil){
        this.serviceAccueil=serviceAccueil;
    }

    public Adresse getAdresse(){
        return this.adresse;
    }

    public void setAdresse(Adresse adresse){
        this.adresse=adresse;
    }

    @Override
    public String toString(){
        return "Etablissement : id : " + this.id + ", raison sociale = " + this.raisonSociale + ", reprensentant = " + this.representantLegal
        + ", fonction = " + this.fonction + ", numero siret = " + this.numeroSiret + ", code APE = " + this.codeApe + ", domaine activite = "
        + this.domaineActivite + ", effectif = " + this.effectif + ", adresse = " + this.adresse.toString() + ", service accueil = "
        + this.serviceAccueil;
    }
}
