package com.projet.fiche;

public class EmailData {
    private String mailEtudiant;
    private String mailTuteur;
    private String messageRefus;

    EmailData(){}
    
    public String getMailEtudiant(){
        return this.mailEtudiant;
    }

    public void setMailEtudiant(String mailEtudiant){
        this.mailEtudiant=mailEtudiant;
    }

    public String getMailTuteur(){
        return this.mailTuteur;
    }

    public void setMailTuteur(String mailTuteur){
        this.mailTuteur=mailTuteur;
    }

    public String getMessageRefus(){
        return this.messageRefus;
    }

    public void setMessageRefus(String messageRefus){
        this.messageRefus=messageRefus;
    }
}
