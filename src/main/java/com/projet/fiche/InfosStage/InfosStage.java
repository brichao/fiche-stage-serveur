package com.projet.fiche.InfosStage;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

//Classe java informations du stage qui respecte le schéma défini dans la base de donnée, avec les getters/setters et réécriture de le méthode
//toString() pour le bon affichage d'un objet d'informations de stage
public class InfosStage {
    private int id;
    private Date dateDebutPartiel;
    private Date dateFinPartiel;
    private Date dateDebutPlein;
    private Date dateFinPlein;
    private Date dateDebutInterruption;
    private Date dateFinInterruption;
    private double nbHeures;
    private boolean gratification;
    private double montantGratification;
    private String versementGratification;
    private String laboratoireUGA;
    private String avantages;
    private boolean confidentialite;
    private String titre;
    private String description;
    private String objectifs;
    private String taches;
    private String details;

	private String nomEtudiant;
    private String prenomEtudiant;
    
    public InfosStage() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateDebutPartiel() {
		return dateDebutPartiel;
	}

	public void setDateDebutPartiel(Date dateDebutPartiel) {
		this.dateDebutPartiel = dateDebutPartiel;
	}

	public Date getDateFinPartiel() {
		return dateFinPartiel;
	}

	public void setDateFinPartiel(Date dateFinPartiel) {
		this.dateFinPartiel = dateFinPartiel;
	}

	public Date getDateDebutPlein() {
		return dateDebutPlein;
	}

	public void setDateDebutPlein(Date dateDebutPlein) {
		this.dateDebutPlein = dateDebutPlein;
	}

	public Date getDateFinPlein() {
		return dateFinPlein;
	}

	public void setDateFinPlein(Date dateFinPlein) {
		this.dateFinPlein = dateFinPlein;
	}

	public Date getDateDebutInterruption() {
		return dateDebutInterruption;
	}

	public void setDateDebutInterruption(Date dateDebutInterruption) {
		this.dateDebutInterruption = dateDebutInterruption;
	}

	public Date getDateFinInterruption() {
		return dateFinInterruption;
	}

	public void setDateFinInterruption(Date dateFinInterruption) {
		this.dateFinInterruption = dateFinInterruption;
	}

	public double getNbHeures() {
		return nbHeures;
	}

	public void setNbHeures(double nbHeures) {
		this.nbHeures = nbHeures;
	}

	public boolean isGratification() {
		return gratification;
	}

	public void setGratification(boolean gratification) {
		this.gratification = gratification;
	}

	public double getMontantGratification() {
		return montantGratification;
	}

	public void setMontantGratification(double montantGratification) {
		this.montantGratification = montantGratification;
	}

	public String getVersementGratification() {
		return versementGratification;
	}

	public void setVersementGratification(String versementGratification) {
		this.versementGratification = versementGratification;
	}

	public String getLaboratoireUGA() {
		return laboratoireUGA;
	}

	public void setLaboratoireUGA(String laboratoireUGA) {
		this.laboratoireUGA = laboratoireUGA;
	}

	public String getAvantages() {
		return avantages;
	}

	public void setAvantages(String avantages) {
		this.avantages = avantages;
	}

	public boolean isConfidentialite() {
		return confidentialite;
	}

	public void setConfidentialite(boolean confidentialite) {
		this.confidentialite = confidentialite;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObjectifs() {
		return objectifs;
	}

	public void setObjectifs(String objectifs) {
		this.objectifs = objectifs;
	}

	public String getTaches() {
		return taches;
	}

	public void setTaches(String taches) {
		this.taches = taches;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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
    
}
