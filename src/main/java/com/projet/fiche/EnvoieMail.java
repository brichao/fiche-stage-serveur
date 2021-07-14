package com.projet.fiche;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//controleur REST ( répondre à HTTP avec des données quelconques (pas nécessaires HTML) )
@RestController
//indique que le contrôleur accepte les requêtes provenant d'une source quelconque (et donc pas nécessairement le même serveur). 
@CrossOrigin
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /mail/.
@RequestMapping("/mail")
public class EnvoieMail {
    
    @Autowired
    private JavaMailSender javaMailSender;

    //Ressource HTTP préfixé par /mail/ et dont la fonction est d'envoyer un mail au tuteur et à l'étudiant avec un motif de refus de la fiche, retourne une response de type HTTP
    @PostMapping("/")
    public EmailData sendEmail(@RequestBody EmailData mailInfos, HttpServletResponse response){
        try{
            SimpleMailMessage refus = new SimpleMailMessage();
            System.out.println("Mail etudiant :" + mailInfos.getMailEtudiant());
            System.out.println("Mail tuteur :" + mailInfos.getMailTuteur());
            System.out.println("Message refus :" + mailInfos.getMessageRefus());
            refus.setTo(mailInfos.getMailEtudiant(), mailInfos.getMailTuteur());
            refus.setSubject("[L3 MIAGE] [Stage] Refus de la fiche de renseignements - Demande de modification");
            refus.setText("Bonjour,\n\nVotre fiche de renseignements a été refusée pour la cause suivante : " + mailInfos.getMessageRefus() + "\n\n"
            + "Veuillez appliquer les modifications nécessaires et re-valider la fiche de renseignements.\n\nCordialement,\n\nVotre responsable de stage.");
    
            javaMailSender.send(refus);
    
            return mailInfos;
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
        
    }

    //Ressource HTTP préfixé par /mail/validation et dont la fonction est d'envoyer un mail de validation de la fiche à l'étudiant, retourne une response de type HTTP
    @PostMapping("/validation")
    public EmailData sendEmailValidate(@RequestBody EmailData mailInfos, HttpServletResponse response){
        try{
            SimpleMailMessage refus = new SimpleMailMessage();
            refus.setTo(mailInfos.getMailEtudiant());
            refus.setSubject("[L3 MIAGE] [Stage] Fiche de renseignement validée");
            refus.setText("Bonjour,\n\nVotre fiche de renseignements a été validée par votre enseignant référent du stage.\n\nCordialement,\n\nVotre responsable de stage - UGA.");
    
            javaMailSender.send(refus);
    
            return mailInfos;
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
        
    }
}
