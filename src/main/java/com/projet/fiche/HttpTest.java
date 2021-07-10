package com.projet.fiche;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Test de la BD et le serveur en accédant à l'url localhost:5000/test
@CrossOrigin
@RestController
@RequestMapping("/test")
public class HttpTest {

    //déclaration du service qui nous permettra de récupérer une connexion de la BD
    @Autowired
    private DataSource dataSource;

    //Test du serveur (localhost:5000/test/ping), si il est fonctionnel, on a pong comme réponse
    @GetMapping("/ping")
    String db() {
      return "pong";
    }

    //Test de la BD (localhost:5000/test/db), si la base de donnée est fonctionnel, on a db comme réponse si tout se passe bien, sinon "error"
    @GetMapping("/db")
    String db(Map<String, Object> model, HttpServletResponse response) {
      //On récupère la connexion du service dataSource du fichier ConnexionBD
      try (Connection connection = dataSource.getConnection()) {
        //Création d'une table ticks qui sert à justifier l'accès à la BD de donnée à l'instant du test
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
  
        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add("Read from DB: " + rs.getTimestamp("tick"));
        }
  
        model.put("records", output);
        return "db";
      } catch (Exception e) {
        try {
          response.getOutputStream().print( e.getMessage() );
        } catch (Exception e2) {
          System.err.println(e2.getMessage());
        }
        return "error";
      }
    }
  
}
