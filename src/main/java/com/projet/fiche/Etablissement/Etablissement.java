package com.projet.fiche.Etablissement;

import com.projet.fiche.Adresse.Adresse;
import com.projet.fiche.ServiceGestion.ServiceGestion;

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
    private int idServiceGestion;
    private ServiceGestion serviceGestion = new ServiceGestion();

    Etablissement(){}

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

    public int getIdServiceGestion(){
        return this.idServiceGestion;
    }

    public void setIdServiceGestion(int idServiceGestion){
        this.idServiceGestion=idServiceGestion;
    }

    public Adresse getAdresse(){
        return this.adresse;
    }

    public void setAdresse(Adresse adresse){
        this.adresse=adresse;
    }

    public ServiceGestion getService(){
        return this.serviceGestion;
    }

    public void setService(ServiceGestion serviceGestion){
        this.serviceGestion=serviceGestion;
    }

    @Override
    public String toString(){
        return "Etablissement : id : " + this.id + ", raison sociale = " + this.raisonSociale + ", reprensentant = " + this.representantLegal
        + ", fonction = " + this.fonction + ", numero siret = " + this.numeroSiret + ", code APE = " + this.codeApe + ", domaine activite = "
        + this.domaineActivite + ", effectif = " + this.effectif + ", adresse = " + this.adresse.toString() + ", service accueil = "
        + this.serviceAccueil + ", service gestion = " + this.serviceGestion.toString();
    }
}
