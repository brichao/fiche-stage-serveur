package com.projet.fiche.Etablissement;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.projet.fiche.Adresse.AdresseDAO;

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
@RequestMapping("/etablissements")
public class EtablissementController {
    
    @Autowired
    private EtablissementDAO etablissementService;

    @Autowired
    private AdresseDAO adresseService;

    @GetMapping("/")
    public ArrayList<Etablissement> findAll(HttpServletResponse response){
        try{
            return etablissementService.findAll();
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{numeroSiret}")
    public Etablissement find(@PathVariable(value="numeroSiret") String numeroSiret, HttpServletResponse response){
        try{
            Etablissement etablissementObject = new Etablissement();
            etablissementObject = etablissementService.find(numeroSiret);

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
           else if(find(numeroSiret, response) == null){
               Etablissement etablissementInsere = new Etablissement();
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

    @PutMapping("/{numeroSiret}")
    public Etablissement update(@PathVariable(value="numeroSiret") String numeroSiret, @RequestBody Etablissement etablissementObject, HttpServletResponse response){
        try {

            Etablissement etablissementExiste = new Etablissement();
            etablissementExiste = etablissementService.find(numeroSiret);

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

    @DeleteMapping("/{numeroSiret}")
    public void delete(@PathVariable(value="numeroSiret") String numeroSiret, HttpServletResponse response){
        try {
            Etablissement etablissementObject = new Etablissement();
            etablissementObject = etablissementService.find(numeroSiret);

            //Erreur 404 si l'etablissement n'existe pas dans la BD
            if(etablissementObject.getRaisonSociale() == null){
                System.out.println("L'etablissement n'existe pas !");
                response.setStatus(404);
            } else {
                adresseService.delete(numeroSiret);
            }
            
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
        }
    }

}
