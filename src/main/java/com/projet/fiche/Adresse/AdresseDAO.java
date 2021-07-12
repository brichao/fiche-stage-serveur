package com.projet.fiche.Adresse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.projet.fiche.InterfaceDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//La classe DAO est un service qui implémente l'interface DAO, et qui définit les méthodes CRUD, cela assure une sécurité en plus entre le serveur
//et la base de donnée (éviter les injections de donnée dans la BD par exemple)
@Service
public class AdresseDAO{

    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée   
    @Autowired
    private DataSource dataSource;

    //Méthode CRUD findAll() pour récupèrer tous les tuples de la table adresses dans la BD
    
    public ArrayList<Adresse> findAll() throws RuntimeException {
        //Récupèrer la connexion grâce au service dataSource
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            //On créé une requête et on sélectionne tous les tuples
            ResultSet results = statement.executeQuery("SELECT * FROM Adresses");
            //Déclaration d'une liste pour stocker tous les objets adresses
            ArrayList<Adresse> adresses = new ArrayList<Adresse>();

            //Tant qu'on a encore des résultats de la BD
            while(results.next()){
                //On déclare un objet de type adresse, peuplement de tous les attributs de cet objet, et ajout dans la liste d'objets adresses
                Adresse adresse = new Adresse();
                adresse.setId(results.getInt("id"));
                adresse.setAdresse(results.getString("adresse"));
                adresse.setCodePostal(results.getInt("codePostal"));
                adresse.setVille(results.getString("ville"));
                adresse.setPays(results.getString("pays"));
                adresses.add(adresse);
            }
            statement.close();
            results.close();
            //Renvoie la liste des adresses
            return adresses;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD find() pour récupèrer une adresse par son adresse de la BD
    
    public Adresse find(String adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection de l'adresse par son adresse avec une requête SQL préparée, ensuite on définit l'adresse
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Adresses where adresse = ?");
            findStatement.setString(1, adresse);
            ResultSet results = findStatement.executeQuery();

            Adresse adresseResult = new Adresse();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type adresse a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                adresseResult.setId(results.getInt("id"));
                adresseResult.setAdresse(results.getString("adresse"));
                adresseResult.setCodePostal(results.getInt("codePostal"));
                adresseResult.setVille(results.getString("ville"));
                adresseResult.setPays(results.getString("pays"));
            }
            findStatement.close();
            results.close();
            return adresseResult;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD create() pour créer une adresse dans la BD, résultat: le tuple crée
    
    public Adresse create(Adresse adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //On récupère la connexion à la BD et on prépare une requête d'insertion
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Adresses(adresse,codePostal,ville,pays)" 
            + " VALUES(?,?,?,?)");

            //Définition des champs requis pour la requête d'insertion, et execution ensuite grâce à la fonction executeUpdate()
            createStatement.setString(1, adresse.getAdresse()); 
            createStatement.setInt(2, adresse.getCodePostal()); 
            createStatement.setString(3, adresse.getVille()); 
            createStatement.setString(4, adresse.getPays());  

            createStatement.executeUpdate();
            createStatement.close();

            //On va chercher dans la BD le tuple inséré grâce à la fonction find, et on retourne l'adresse insérée
            Adresse adresseInseree = this.find(adresse.getAdresse());
            return adresseInseree;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD update() pour modifier une adresse présente dans la BD. Même fonctionnement que la méthode create(), à la différence
    //de la requête Update utilisée ici.
    
    public Adresse update(Adresse adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Adresses SET adresse = ?, codePostal = ?, ville = ?, pays = ?" +
            " where id = ?");

            
            updateStatement.setString(1, adresse.getAdresse());
            updateStatement.setInt(2, adresse.getCodePostal()); 
            updateStatement.setString(3, adresse.getVille()); 
            updateStatement.setString(4, adresse.getPays());
            updateStatement.setInt(5, adresse.getId());
            
            updateStatement.executeUpdate();
            updateStatement.close();

            Adresse adresseModifiee = this.find(adresse.getAdresse());
            return adresseModifiee;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD delete pour supprimer une adresse de la BD par son adresse
    
    public void delete(String adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Adresses WHERE adresse = ?");

            deleStatement.setString(1, adresse); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
