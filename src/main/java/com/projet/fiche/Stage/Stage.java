package com.projet.fiche.Stage;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Stage {
    private int id;
	@JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateDebutPartiel;
	@JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateFinPartiel;
	@JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateFinPlein;
	@JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateDebutInterruption;
	@JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateFinInterruption;
    private int nbHeures;
    private boolean gratification;
    private int montantGratification;
    private String versementGratification;
    private String laboratoireUGA;
    private String avantages;
    private boolean confidentialite;
    private String titre;
    private String description;
    private String objectifs;
    private String taches;
    private String details;
    
    public Stage() {}

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

	public int getNbHeures() {
		return nbHeures;
	}

	public void setNbHeures(int nbHeures) {
		this.nbHeures = nbHeures;
	}

	public boolean isGratification() {
		return gratification;
	}

	public void setGratification(boolean gratification) {
		this.gratification = gratification;
	}

	public int getMontantGratification() {
		return montantGratification;
	}

	public void setMontantGratification(int montantGratification) {
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
    
    
}
