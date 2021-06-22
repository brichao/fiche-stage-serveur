package com.projet.fiche.Etablissement;

public class Etablissement {
    private int idEtablissement;
    private String raisonSociale;
    private String representantLegal;
    private String fonction;
    private int numeroSiret;
    private String codeApe;
    private String domaineActivite;
    private int effectif;
    private int idAdresse;
    private String serviceAccueil;
    private int serviceGestion;

    Etablissement(){}

    Etablissement(int idEtablissement, String raisonSociale, String representantLegal, String fonction, int numeroSiret, String codeApe, String domaineActivite,
    int effectif, int idAdresse, String serviceAccueil, int serviceGestion){
        this.idEtablissement=idEtablissement;
        this.raisonSociale=raisonSociale;
        this.representantLegal=representantLegal;
        this.fonction=fonction;
        this.numeroSiret=numeroSiret;
        this.codeApe=codeApe;
        this.domaineActivite=domaineActivite;
        this.effectif=effectif;
        this.idAdresse=idAdresse;
        this.serviceAccueil=serviceAccueil;
        this.serviceGestion=serviceGestion;
    }

    public int getId(){
        return this.idEtablissement;
    }

    public void setId(int idEtablissement){
        this.idEtablissement=idEtablissement;
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

    public int getServiceGestion(){
        return this.serviceGestion;
    }

    public void setServiceGestion(int serviceGestion){
        this.serviceGestion=serviceGestion;
    }
}
