package com.projet.fiche.Etablissement;

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
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /etablissements/.
@RequestMapping("/etablissements")
public class EtablissementController {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet etablissement
    @Autowired
    private EtablissementDAO etablissementService;

    //Ressource HTTP préfixé par /etablissements/ et dont la fonction est de récupérer tous les établissements, retourne une response de type HTTP
    @GetMapping("/")
    public ArrayList<Etablissement> findAll(HttpServletResponse response){
        try{
            //Utilisation de la méthode CRUD findAll() du service DAO
            return etablissementService.findAll();
        } catch (Exception e){
            //En cas d'erreur, HTTP renvoie une réponse de code 500 (code 500 veut dire un problème avec le serveur)
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /etablissements/id/idEtablissement et dont la fonction est de récupérer un établissement par son id, retourne une response de type HTTP
    @GetMapping("/id/{idEtablissement}")
    public Etablissement find(@PathVariable(value="idEtablissement") int idEtablissement, HttpServletResponse response){
        try{
            Etablissement etablissementObject = new Etablissement();
            etablissementObject = etablissementService.find(idEtablissement);

            //Erreur 404 si l'etablissement n'existe pas dans la BD
            if(etablissementObject.getRaisonSociale() == null){
                System.out.println("L'etablissement n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return etablissementObject;
            }
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /etablissements/numeroSiretEtablissement et dont la fonction est de récupérer un établissement par son numéro de siret, retourne une response de type HTTP
    @GetMapping("/{numeroSiret}")
    public Etablissement findByString(@PathVariable(value="numeroSiret") String numeroSiret, HttpServletResponse response){
        try{
            Etablissement etablissementObject = new Etablissement();
            etablissementObject = etablissementService.findByString(numeroSiret);

            //Erreur 404 si l'etablissement n'existe pas dans la BD
            if(etablissementObject.getRaisonSociale() == null){
                System.out.println("L'etablissement n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return etablissementObject;
            }
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /etablissements/numeroSiretEtablissement et dont la fonction est d'envoyer une requête de création d'un établissement (POST), retourne une response de type HTTP
    @PostMapping("/{numeroSiret}")
    public Etablissement create(@PathVariable(value="numeroSiret") String numeroSiret, @RequestBody Etablissement etablissementObject, HttpServletResponse response){
        try {

            //une erreur 412 si le mail de l'etablissement dans l'URL n'est pas le même que celui de l'etablissement dans le corp de la requête.
           if(!numeroSiret.equals(String.valueOf(etablissementObject.getNumeroSiret()))){
               System.out.println("Request body not equivalent to variable path : " + numeroSiret + " != " + String.valueOf(etablissementObject.getNumeroSiret()));
               response.setStatus(412);
               return null;
           }

           //Une erreur 403 si l'etablissement existe déjà dans la BD
           else if(findByString(numeroSiret, response) == null){
               Etablissement etablissementInsere = new Etablissement();
               System.out.println(etablissementObject.toString());
               etablissementInsere = etablissementService.create(etablissementObject);
               response.setStatus(200);
               return etablissementInsere;
           } else {
               System.out.println("Etablissement already exists !");
               response.setStatus(403);
               return null;
           }

       } catch (Exception e) {
           response.setStatus(500);
           System.err.println(e.getMessage());
           return null;
       }
    }

     //Ressource HTTP préfixé par /etablissements/numeroSiretEtablissement et dont la fonction est d'envoyer une requête de modification d'un établissement (PUT), retourne une response de type HTTP
     @PutMapping("/{numeroSiret}")
     public Etablissement update(@PathVariable(value="numeroSiret") String numeroSiret, @RequestBody Etablissement etablissementObject, HttpServletResponse response){
         try {
 
             Etablissement etablissementExiste = new Etablissement();
             etablissementExiste = etablissementService.findByString(numeroSiret);
 
             //Une erreur 403 si l'etablissement n'existe pas dans la BD
             if(etablissementExiste.getRaisonSociale() == null){
                 System.out.println("Etablissement does not exist !");
                 response.setStatus(404);
                 return null;
             } else {
                 Etablissement etablissementModifie = new Etablissement();
                 etablissementObject.setId(etablissementExiste.getId());
                 etablissementModifie = etablissementService.update(etablissementObject);
                 return etablissementModifie;
             }
 
         } catch (Exception e) {
             response.setStatus(500);
             System.err.println(e.getMessage());
             return null;
         }
     }

    //Ressource HTTP préfixé par /etablissements/idEtablissement et dont la fonction est d'envoyer une requête de suppression d'un établissement (DELETE), retourne une response de type HTTP
    @DeleteMapping("/{idEtablissement}")
    public void delete(@PathVariable(value="idEtablissement") int idEtablissement, HttpServletResponse response){
        try {
            Etablissement etablissementObject = new Etablissement();
            etablissementObject = etablissementService.find(idEtablissement);

            //Erreur 404 si l'etablissement n'existe pas dans la BD
            if(etablissementObject.getRaisonSociale() == null){
                System.out.println("L'etablissement n'existe pas !");
                response.setStatus(404);
            } else {
                etablissementService.delete(idEtablissement);
            }
            
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }

}
