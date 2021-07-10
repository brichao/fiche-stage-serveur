package com.projet.fiche.ServiceGestion;

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
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /services/.
@RequestMapping("/services")
public class ServiceGestionController {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet service de gestion
    @Autowired
    private ServiceGestionDAO serviceDAO;

    //Ressource HTTP préfixé par /services/ et dont la fonction est de récupérer tous les services de gestion, retourne une response de type HTTP
    @GetMapping("/")
    public ArrayList<ServiceGestion> findAll(HttpServletResponse response){
        try{
            //Utilisation de la méthode CRUD findAll() du service DAO
            return serviceDAO.findAll();
        } catch (Exception e){
            //En cas d'erreur, HTTP renvoie une réponse de code 500 (code 500 veut dire un problème avec le serveur)
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /services/mailService et dont la fonction est de récupérer un service de gestion par son adresse mail, retourne une response de type HTTP
    @GetMapping("/{serviceMail}")
    public ServiceGestion find(@PathVariable(value="serviceMail") String serviceMail, HttpServletResponse response){
        try{
            ServiceGestion serviceObject = new ServiceGestion();
            serviceObject = serviceDAO.find(serviceMail);

            //Erreur 404 si l'adresse n'existe pas dans la BD
            if(serviceObject.getMail() == null){
                System.out.println("L'adresse n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return serviceObject;
            }
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /services/mailService et dont la fonction est d'envoyer une requête de création d'un service de gestion (POST), retourne une response de type HTTP
    @PostMapping("/{serviceMail}")
    public ServiceGestion create(@PathVariable(value="serviceMail") String serviceMail, @RequestBody ServiceGestion serviceObject, HttpServletResponse response){
        try {

            //une erreur 412 si le mail du service de gestion dans l'URL n'est pas le même que celui du service de gestion dans le corp de la requête.
           if(!serviceMail.equals(serviceObject.getMail())){
               System.out.println("Request body not equivalent to variable path : " + serviceMail + " != " + serviceObject.getMail());
               response.setStatus(412);
               return null;
           }

           //Une erreur 403 si le service de gestion existe déjà dans la BD
           else if(find(serviceMail, response) == null){
               ServiceGestion serviceInsere = new ServiceGestion();
               serviceInsere = serviceDAO.create(serviceObject);
               response.setStatus(200);
               return serviceInsere;
           } else {
               System.out.println("Service de gestion already exists !");
               response.setStatus(403);
               return null;
           }

       } catch (Exception e) {
           response.setStatus(500);
           System.err.println(e.getMessage());
           return null;
       }
    }

    //Ressource HTTP préfixé par /services/mailService et dont la fonction est d'envoyer une requête de modification d'un service de gestion (PUT), retourne une response de type HTTP
    @PutMapping("/{serviceMail}")
    public ServiceGestion update(@PathVariable(value="serviceMail") String serviceMail, @RequestBody ServiceGestion serviceObject, HttpServletResponse response){
        try {

            ServiceGestion serviceExiste = new ServiceGestion();
            serviceExiste = serviceDAO.find(serviceMail);

            //Une erreur 403 si le service de gestion n'existe pas dans la BD
            if(serviceExiste.getMail() == null){
                System.out.println("Service de gestion does not exist !");
                response.setStatus(404);
                return null;
            } else {
                ServiceGestion serviceModifie = new ServiceGestion();
                serviceObject.setId(serviceExiste.getId());
                serviceModifie = serviceDAO.update(serviceObject);
                return serviceModifie;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /services/mailService et dont la fonction est d'envoyer une requête de suppression d'un service de gestion (DELETE), retourne une response de type HTTP
    @DeleteMapping("/{serviceMail}")
    public void delete(@PathVariable(value="serviceMail") String serviceMail, HttpServletResponse response){
        try {
            ServiceGestion serviceObject = new ServiceGestion();
            serviceObject = serviceDAO.find(serviceMail);

            //Erreur 404 si le service de gestion n'existe pas dans la BD
            if(serviceObject.getMail() == null){
                System.out.println("Le service de gestion n'existe pas !");
                response.setStatus(404);
            } else {
                serviceDAO.delete(serviceMail);
            }
            
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }
}
