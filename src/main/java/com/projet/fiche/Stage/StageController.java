package com.projet.fiche.Stage;

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

@RestController
@CrossOrigin
@RequestMapping("/stages")
public class StageController {
    
    @Autowired
    private StageDAO stageService;

    @GetMapping("/")
    public ArrayList<Stage> findAll(HttpServletResponse response){
        try{
            return stageService.findAll();
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{titreStage}")
    public Stage find(@PathVariable(value="titreStage") String titreStage, HttpServletResponse response){
        try{
            Stage stage = new Stage();
            stage = stageService.find(titreStage);

            //Erreur 404 si le stage n'existe pas dans la BD
            if(stage.getTitre() == null){
                System.out.println("Le stage n'existe pas !");
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

    @PostMapping("/{titreStage}")
    public Stage create(@PathVariable(value="titreStage") String titreStage, @RequestBody Stage stage, HttpServletResponse response){
        try {

             //une erreur 412 si le titre du stage dans l'URL n'est pas le même que celui du stage dans le corp de la requête.
            if(!titreStage.equals(stage.getTitre())){
                System.out.println("Request body not equivalent to variable path : " + titreStage + " != " + stage.getTitre());
                response.setStatus(412);
                return null;
            }

            //Une erreur 403 si le stage existe déjà dans la BD
            else if(find(titreStage, response) == null){
                Stage stageInsere = new Stage();
                stageInsere = stageService.create(stage);
                response.setStatus(200);
                return stageInsere;
            } else {
                System.out.println("Stage already exists !");
                response.setStatus(403);
                return null;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    @PutMapping("/{titreStage}")
    public Stage update(@PathVariable(value="titreStage") String titreStage, @RequestBody Stage stage, HttpServletResponse response){
        try {

            Stage stageExiste = new Stage();
            stageExiste = stageService.find(titreStage);

            //Erreur 404 si le stage n'existe pas dans la BD
            if(stageExiste.getTitre() == null){
                System.out.println("Le stage n'existe pas !");
                response.setStatus(404);
                return null;
            }
            else {
                Stage stageModifie = new Stage();
                stage.setId(stageExiste.getId());
                stageModifie = stageService.update(stage);
                return stageModifie;
            }

        } catch (Exception e) {
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/{titreStage}")
    public void delete(@PathVariable(value="titreStage") String titreStage, HttpServletResponse response){
        try {
            Stage stage = new Stage();
            stage = stageService.find(titreStage);

            //Erreur 404 si le stage n'existe pas dans la BD
            if(stage.getTitre() == null){
                System.out.println("Le stage n'existe pas !");
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
