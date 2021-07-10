package com.projet.fiche.InfosStage;

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
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /infosStage/.
@RequestMapping("/infosStages")
public class InfosStageController {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet informations de stage
    @Autowired
    private InfosStageDAO stageService;

    //Ressource HTTP préfixé par /infosStage/ et dont la fonction est de récupérer toutes les informations des stages, retourne une response de type HTTP
    @GetMapping("/")
    public ArrayList<InfosStage> findAll(HttpServletResponse response){
        try{
            //Utilisation de la méthode CRUD findAll() du service DAO
            return stageService.findAll();
        } catch (Exception e){
            //En cas d'erreur, HTTP renvoie une réponse de code 500 (code 500 veut dire un problème avec le serveur)
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /infosStages/titreStage et dont la fonction est de récupérer des informations de stage par son titre, retourne une response de type HTTP
    @GetMapping("/{titreStage}")
    public InfosStage find(@PathVariable(value="titreStage") String titreStage, HttpServletResponse response){
        try{
            InfosStage stage = new InfosStage();
            stage = stageService.find(titreStage);

            //Erreur 404 si le stage n'existe pas dans la BD
            if(stage.getTitre() == null){
                System.out.println("Les informations du stage n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return stage;
            }
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /infosStages/titreStage et dont la fonction est d'envoyer une requête de création des informations d'un stage (POST), retourne une response de type HTTP
    @PostMapping("/{titreStage}")
    public InfosStage create(@PathVariable(value="titreStage") String titreStage, @RequestBody InfosStage infosStage, HttpServletResponse response){
        try {

             //une erreur 412 si le titre du stage dans l'URL n'est pas le même que celui du stage dans le corp de la requête.
            if(!titreStage.equals(infosStage.getTitre())){
                System.out.println("Request body not equivalent to variable path : " + titreStage + " != " + infosStage.getTitre());
                response.setStatus(412);
                return null;
            }

            //Une erreur 403 si le stage existe déjà dans la BD
            else if(find(titreStage, response) == null){
                InfosStage stageInsere = new InfosStage();
                stageInsere = stageService.create(infosStage);
                response.setStatus(200);
                return stageInsere;
            } else {
                System.out.println("Informations stage already exists !");
                response.setStatus(403);
                return null;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /infosStages/titreStage et dont la fonction est d'envoyer une requête de modification des informations d'un stage (PUT), retourne une response de type HTTP
    @PutMapping("/{titreStage}")
    public InfosStage update(@PathVariable(value="titreStage") String titreStage, @RequestBody InfosStage infosStage, HttpServletResponse response){
        try {

            InfosStage stageExiste = new InfosStage();
            stageExiste = stageService.find(titreStage);

            //Erreur 404 si le stage n'existe pas dans la BD
            if(stageExiste.getTitre() == null){
                System.out.println("Les informatios du stage n'existe pas !");
                response.setStatus(404);
                return null;
            }
            else {
                InfosStage stageModifie = new InfosStage();
                infosStage.setId(stageExiste.getId());
                stageModifie = stageService.update(infosStage);
                return stageModifie;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /infosStages/titreStage et dont la fonction est d'envoyer une requête de suppression des informations d'un stage (DELETE), retourne une response de type HTTP
    @DeleteMapping("/{titreStage}")
    public void delete(@PathVariable(value="titreStage") String titreStage, HttpServletResponse response){
        try {
            InfosStage stage = new InfosStage();
            stage = stageService.find(titreStage);

            //Erreur 404 si le stage n'existe pas dans la BD
            if(stage.getTitre() == null){
                System.out.println("Les informations du stage n'existe pas !");
                response.setStatus(404);
            } else {
                stageService.delete(titreStage);
            }
            
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }
}
