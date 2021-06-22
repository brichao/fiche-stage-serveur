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

@RestController
@CrossOrigin
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantDAO etudiantService;

    @GetMapping("/")
    public ArrayList<Etudiant> findAll(HttpServletResponse response){
        try{
            return etudiantService.findAll();
        } catch (Exception e){
            response.setStatus(500);
            System.err.println(e.getMessage());
            return null;
        }
    }

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
