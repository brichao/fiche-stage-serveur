package com.projet.fiche.FicheRenseignement;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//controleur REST ( répondre à HTTP avec des données quelconques (pas nécessaires HTML) )
@RestController
//indique que le contrôleur accepte les requêtes provenant d'une source quelconque (et donc pas nécessairement le même serveur). 
@CrossOrigin
// Indique que les ressources HTTP qui seront déclarées dans la classe seront toutes préfixées par /fiches/
@RequestMapping("/fiches")
public class FicheRenseignementController {

    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet fiche de renseignement
    @Autowired
    private FicheRenseignementDAO ficheService;

    //Ressource HTTP préfixé par /fiches/ et dont la fonction est de récupérer toutes les fiches, retourne une response de type HTTP
    @GetMapping("/")
    public ArrayList<FicheRenseignement> findAll(HttpServletResponse response){
        try{
            //Utilisation de la méthode CRUD findAll() du service DAO
            return ficheService.findAll();
        } catch (Exception e){
            //En cas d'erreur, HTTP renvoie une réponse de code 500 (code 500 veut dire un problème avec le serveur)
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /fiches/nom/prenom et dont la fonction est de récupérer une fiche par le nom et prenom de l'étudiant
    //retourne une response de type HTTP
    @GetMapping("/{nomEtudiant}/{prenomEtudiant}")
    public FicheRenseignement find(@PathVariable(value="nomEtudiant") String nom, @PathVariable(value="prenomEtudiant") String prenom, 
                                    HttpServletResponse response){
        try{
            FicheRenseignement fiche = new FicheRenseignement();
            fiche = ficheService.find(nom, prenom);
            
            //Erreur 404 si l'étudiant n'existe pas dans la BD
            if(fiche.getIdEtudiant() == 0){
                System.out.println("L'etudiant n'existe pas !");
                response.setStatus(404);
                return null;
            } else {
                return fiche;
            }
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Ressource HTTP préfixé par /fiches/nom/prenom et dont la fonction est de modifier une fiche par le nom et prenom de l'étudiant
    //retourne une response de type HTTP
    @PutMapping("/{nomEtudiant}/{prenomEtudiant")
    public FicheRenseignement update(@PathVariable(value="nomEtudiant") String nomEtudiant, @PathVariable(value="prenomEtudiant") String prenomEtudiant,
     @RequestBody FicheRenseignement fiche, HttpServletResponse response){
        try{
            FicheRenseignement ficheExiste = new FicheRenseignement();
            ficheExiste = ficheService.find(nomEtudiant, prenomEtudiant);

            //Erreur 403 si la fiche n'existe pas (=> Etudiant n'existe pas)
            if(ficheExiste.getIdFiche() == 0){
                response.setStatus(403);
                System.out.println("La fiche de renseignements n'existe pas dans la BD");
                return null;
            } else {
                FicheRenseignement ficheModifiee = new FicheRenseignement();
                fiche.setIdFiche(ficheExiste.getIdFiche());
                ficheModifiee = ficheService.update(fiche);
                return ficheModifiee;
            }

        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    //Ressource HTTP préfixé par /fiches/nom/prenom et dont la fonction est de supprimer une fiche par le nom et prenom de l'étudiant
    //retourne une response de type HTTP
    @DeleteMapping("/{nomEtudiant}/{prenomEtudiant}")
    public void delete(@PathVariable(value="nomEtudiant") String nom, @PathVariable(value="prenomEtudiant") String prenom, HttpServletResponse response){
        try{
            //Erreur 404 si l'étudiant n'existe pas dans la BD
            if(ficheService.find(nom, prenom).getIdEtudiant() == 0){
                System.out.println("L'étudiant n'existe pas !");
                response.setStatus(404);
            } else {
                ficheService.delete(nom, prenom);
            }
        } catch(Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }
}
