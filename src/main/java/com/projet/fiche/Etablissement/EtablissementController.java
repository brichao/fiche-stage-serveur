package com.projet.fiche.Etablissement;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.projet.fiche.Adresse.AdresseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
