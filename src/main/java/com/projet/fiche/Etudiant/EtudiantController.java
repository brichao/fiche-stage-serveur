package com.projet.fiche.Etudiant;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//controleur REST ( répondre à HTTP avec des données quelconques (pas nécessaires HTML) )
@RestController
//indique que le contrôleur accepte les requêtes provenant d'une source quelconque (et donc pas nécessairement le même serveur). 
@CrossOrigin
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /etudiants/
@RequestMapping("/etudiants")
public class EtudiantController {

    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet étudiant
    @Autowired
    private EtudiantDAO etudiantService;

    //Ressource HTTP préfixé par /etudiants/ et dont la fonction est de récupérer tous les étudiants, retourne une response de type HTTP
    @GetMapping("/")
    public ArrayList<Etudiant> findAll(HttpServletResponse response){
        try{
            //Utilisation de la méthode CRUD findAll() du service DAO
            return etudiantService.findAll();
        } catch (Exception e){
            //En cas d'erreur, HTTP renvoie une réponse de code 500 (code 500 veut dire un problème avec le serveur)
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /etudiants/mailEtudiant et dont la fonction est de récupérer un étudiant par son adresse mail, retourne une response de type HTTP
    @GetMapping("/{etudiantMail}")
    public Etudiant find(@PathVariable(value="etudiantMail") String mail, HttpServletResponse response){
        try{
            Etudiant etudiant = new Etudiant();
            etudiant = etudiantService.find(mail);

            //Erreur 404 si l'etudiant n'existe pas dans la BD
            if(etudiant.getNom() == null){
                System.out.println("L'etudiant n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return etudiant;
            }
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /etudiants/mailEtudiant et dont la fonction est d'envoyer une requête de création d'un étudiant (POST), retourne une response de type HTTP
    @PostMapping("/{etudiantMail}")
    public Etudiant create(@PathVariable(value="etudiantMail") String mail, @RequestBody Etudiant etudiant, HttpServletResponse response){
        try {

             //une erreur 412 si le mail de l'etudiant dans l'URL n'est pas le même que celui de l'etudiant dans le corp de la requête.
            if(!mail.equals(etudiant.getMail())){
                System.out.println("Request body not equivalent to variable path : " + mail + " != " + etudiant.getMail());
                response.setStatus(412);
                return null;
            }

            //Une erreur 403 si l'etudiant existe déjà dans la BD
            else if(find(mail, response) == null){
                Etudiant etudiantInsere = new Etudiant();
                etudiantInsere = etudiantService.create(etudiant);
                response.setStatus(200);
                return etudiantInsere;
            } else {
                System.out.println("Etudiant already exists !");
                response.setStatus(403);
                return null;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /etudiants/mailEtudiant et dont la fonction est d'envoyer une requête de modification d'un étudiant (PUT), retourne une response de type HTTP
    @PutMapping("/{etudiantMail}")
    public Etudiant update(@PathVariable(value="etudiantMail") String mail, @RequestBody Etudiant etudiant, HttpServletResponse response){
        try {

            Etudiant etudiantExiste = new Etudiant();
            etudiantExiste = etudiantService.find(mail);

            //Erreur 404 si l'etudiant n'existe pas dans la BD
            if(etudiantExiste.getNom() == null){
                System.out.println("L'etudiant n'existe pas !");
                response.setStatus(404);
                return null;
            }
            else {
                Etudiant etudiantModifie = new Etudiant();
                etudiant.setId(etudiantExiste.getId());
                etudiantModifie = etudiantService.update(etudiant);
                return etudiantModifie;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /etudiants/mailEtudiant et dont la fonction est d'envoyer une requête de suppression d'un étudiant (DELETE), retourne une response de type HTTP
    @DeleteMapping("/{etudiantMail}")
    public void delete(@PathVariable(value="etudiantMail") String mail, HttpServletResponse response){
        try {
            Etudiant etudiant = new Etudiant();
            etudiant = etudiantService.find(mail);

            //Erreur 404 si l'etudiant n'existe pas dans la BD
            if(etudiant.getNom() == null){
                System.out.println("L'etudiant n'existe pas !");
                response.setStatus(404);
            } else {
                etudiantService.delete(mail);
            }
            
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }
}
