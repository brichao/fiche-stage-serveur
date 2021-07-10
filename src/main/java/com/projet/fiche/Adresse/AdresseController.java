package com.projet.fiche.Adresse;

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
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /adresses/.
@RequestMapping("/adresses")
public class AdresseController {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet adresse
    @Autowired
    private AdresseDAO adresseService;

    //Ressource HTTP préfixé par /adresses/ et dont la fonction est de récupérer toutes les adresses, retourne une response de type HTTP
    @GetMapping("/")
    public ArrayList<Adresse> findAll(HttpServletResponse response){
        try{
            //Utilisation de la méthode CRUD findAll() du service DAO
            return adresseService.findAll();
        } catch (Exception e){
            //En cas d'erreur, HTTP renvoie une réponse de code 500 (code 500 veut dire un problème avec le serveur)
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /adresses/adresse et dont la fonction est de récupérer une adresse par son adresse, retourne une response de type HTTP
    @GetMapping("/{adresse}")
    public Adresse find(@PathVariable(value="adresse") String adresse, HttpServletResponse response){
        try{
            Adresse adresseObject = new Adresse();
            adresseObject = adresseService.find(adresse);

            //Erreur 404 si l'adresse n'existe pas dans la BD
            if(adresseObject.getAdresse() == null){
                System.out.println("L'adresse n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return adresseObject;
            }
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /adresses/adresse et dont la fonction est d'envoyer une requête de création d'une adresse (POST), retourne une response de type HTTP
    @PostMapping("/{adresse}")
    public Adresse create(@PathVariable(value="adresse") String adresse, @RequestBody Adresse adresseObject, HttpServletResponse response){
        try {

            //une erreur 412 si le mail de l'etudiant dans l'URL n'est pas le même que celui de l'etudiant dans le corp de la requête.
           if(!adresse.equals(adresseObject.getAdresse())){
               System.out.println("Request body not equivalent to variable path : " + adresse + " != " + adresseObject.getAdresse());
               response.setStatus(412);
               return null;
           }

           //Une erreur 403 si l'etudiant existe déjà dans la BD
           else if(find(adresse, response) == null){
               Adresse adresseInseree = new Adresse();
               adresseInseree = adresseService.create(adresseObject);
               response.setStatus(200);
               return adresseInseree;
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

//Ressource HTTP préfixé par /adresses/adresse et dont la fonction est d'envoyer une requête de modification d'une adresse (PUT), retourne une response de type HTTP
    @PutMapping("/{adresse}")
    public Adresse update(@PathVariable(value="adresse") String adresse, @RequestBody Adresse adresseObject, HttpServletResponse response){
        try {

            Adresse adresseExiste = new Adresse();
            adresseExiste = adresseService.find(adresse);

            //Une erreur 403 si l'adresse n'existe pas dans la BD
            if(adresseExiste.getAdresse() == null){
                System.out.println("Adresse does not exist !");
                response.setStatus(404);
                return null;
            } else {
                Adresse adresseModifiee = new Adresse();
                adresseObject.setId(adresseExiste.getId());
                adresseModifiee = adresseService.update(adresseObject);
                return adresseModifiee;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /adresses/adresse et dont la fonction est d'envoyer une requête de suppression d'une adresse (DELETE), retourne une response de type HTTP
    @DeleteMapping("/{adresse}")
    public void delete(@PathVariable(value="adresse") String adresse, HttpServletResponse response){
        try {
            Adresse adresseObject = new Adresse();
            adresseObject = adresseService.find(adresse);

            //Erreur 404 si l'adresse n'existe pas dans la BD
            if(adresseObject.getAdresse() == null){
                System.out.println("L'adresse n'existe pas !");
                response.setStatus(404);
            } else {
                adresseService.delete(adresse);
            }
            
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }
}
