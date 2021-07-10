package com.projet.fiche.Tuteur;

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
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /tuteurs/.
@RequestMapping("/tuteurs")
public class TuteurController {
 
    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet tuteur
    @Autowired
    private TuteurDAO tuteurService;

    //Ressource HTTP préfixé par /tuteurs/ et dont la fonction est de récupérer tous les tuteurs, retourne une response de type HTTP
    @GetMapping("/")
    public ArrayList<Tuteur> findAll(HttpServletResponse response){
        try{
            //Utilisation de la méthode CRUD findAll() du service DAO
            return tuteurService.findAll();
        } catch (Exception e){
            //En cas d'erreur, HTTP renvoie une réponse de code 500 (code 500 veut dire un problème avec le serveur)
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /tuteurs/mailTuteur et dont la fonction est de récupérer un tuteur par son adresse mail, retourne une response de type HTTP
    @GetMapping("/{mailTuteur}")
    public Tuteur find(@PathVariable(value="mailTuteur") String mailTuteur, HttpServletResponse response){
        try{
            Tuteur tuteurObject = new Tuteur();
            tuteurObject = tuteurService.find(mailTuteur);

            //Erreur 404 si le tuteur n'existe pas dans la BD
            if(tuteurObject.getMail() == null){
                System.out.println("Le tuteur n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return tuteurObject;
            }
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /tuteurs/mailTuteur et dont la fonction est d'envoyer une requête de création d'un tuteur (POST), retourne une response de type HTTP
    @PostMapping("/{mailTuteur}")
    public Tuteur create(@PathVariable(value="mailTuteur") String mailTuteur, @RequestBody Tuteur tuteurObject, HttpServletResponse response){
        try {

            //une erreur 412 si le mail du tuteur de gestion dans l'URL n'est pas le même que celui du tuteur dans le corp de la requête.
           if(!mailTuteur.equals(tuteurObject.getMail())){
               System.out.println("Request body not equivalent to variable path : " + mailTuteur + " != " + tuteurObject.getMail());
               response.setStatus(412);
               return null;
           } else if(find(mailTuteur, response) == null){
               Tuteur tuteurInsere = new Tuteur();
               tuteurInsere = tuteurService.create(tuteurObject);
               response.setStatus(200);
               return tuteurInsere;
           } 
           //Une erreur 403 si le tuteur existe déjà dans la BD
           else {
               System.out.println("Tuteur already exists !");
               response.setStatus(403);
               return null;
           }

       } catch (Exception e) {
           response.setStatus(500);
           System.err.println(e.getMessage());
           return null;
       }
    }

    //Ressource HTTP préfixé par /tuteurs/mailTuteur et dont la fonction est d'envoyer une requête de modification d'un tuteur (PUT), retourne une response de type HTTP
    @PutMapping("/{mailTuteur}")
    public Tuteur update(@PathVariable(value="mailTuteur") String mailTuteur, @RequestBody Tuteur tuteurObject, HttpServletResponse response){
        try {

            Tuteur tuteurExiste = new Tuteur();
            tuteurExiste = tuteurService.find(mailTuteur);

            //Une erreur 403 si le tuteur n'existe pas dans la BD
            if(tuteurExiste.getMail() == null){
                System.out.println("Tuteur does not exist !");
                response.setStatus(404);
                return null;
            } else {
                Tuteur tuteurModifie = new Tuteur();
                tuteurObject.setId(tuteurExiste.getId());
                tuteurModifie = tuteurService.update(tuteurObject);
                return tuteurModifie;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /tuteurs/mailTuteur et dont la fonction est d'envoyer une requête de suppression d'un tuteur (DELETE), retourne une response de type HTTP
    @DeleteMapping("/{mailTuteur}")
    public void delete(@PathVariable(value="mailTuteur") String mailTuteur, HttpServletResponse response){
        try {
            Tuteur tuteurObject = new Tuteur();
            tuteurObject = tuteurService.find(mailTuteur);

            //Erreur 404 si le tuteur n'existe pas dans la BD
            if(tuteurObject.getMail() == null){
                System.out.println("Le tuteur n'existe pas !");
                response.setStatus(404);
            } else {
                tuteurService.delete(mailTuteur);
            }
            
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }
}
