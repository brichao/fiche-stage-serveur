package com.projet.fiche.Tuteur;

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
public class TuteurDAO implements InterfaceDAO<Tuteur>{

    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée    
    @Autowired
    private DataSource dataSource;

    //Méthode CRUD findAll() pour récupèrer tous les tuples de la table Tuteur dans la BD
    @Override
    public ArrayList<Tuteur> findAll() throws RuntimeException {
        //Récupèrer la connexion grâce au service dataSource
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            //On créé une requête et on sélectionne tous les tuples
            ResultSet results = statement.executeQuery("SELECT * FROM Tuteurs");

            //Déclaration d'une liste pour stocker tous les objets tuteurs
            ArrayList<Tuteur> tuteurs = new ArrayList<Tuteur>();

            //Tant qu'on a encore des résultats de la BD
            while(results.next()){
                //On déclare un objet de type tuteur, peuplement de tous les attributs de cet objet, et ajout dans la liste d'objets tuteurs
                Tuteur tuteur = new Tuteur();
                tuteur.setId(results.getInt("id"));
                tuteur.setNom(results.getString("nom"));
                tuteur.setPrenom(results.getString("prenom"));
                tuteur.setFonction(results.getString("fonction"));
                tuteur.setService(results.getString("service"));
                tuteur.setNumTelephone(results.getInt("numTelephone"));
                tuteur.setMail(results.getString("mail"));
                tuteur.setAdresse(results.getString("adresse"));
                tuteur.setDisponibilite(results.getString("disponibilite"));

                tuteurs.add(tuteur);
            }
            statement.close();
            results.close();
            //Renvoie la liste des tuteurs
            return tuteurs;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD find() pour récupèrer un tuteur par son id de la BD
    @Override
    public Tuteur find(int id) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection du tuteur par son id avec une requête SQL préparée, ensuite on définit l'id
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Tuteurs where id = ?");
            findStatement.setInt(1, id);
            ResultSet results = findStatement.executeQuery();

            Tuteur tuteur = new Tuteur();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type tuteur a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                tuteur.setId(results.getInt("id"));
                tuteur.setNom(results.getString("nom"));
                tuteur.setPrenom(results.getString("prenom"));
                tuteur.setFonction(results.getString("fonction"));
                tuteur.setService(results.getString("service"));
                tuteur.setNumTelephone(results.getInt("numTelephone"));
                tuteur.setMail(results.getString("mail"));
                tuteur.setAdresse(results.getString("adresse"));
                tuteur.setDisponibilite(results.getString("disponibilite"));
            }
            findStatement.close();
            results.close();
            return tuteur;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD create() pour créer un tuteur dans la BD, résultat: le tuple crée
    @Override
    public Tuteur create(Tuteur tuteur) throws RuntimeException {
        //On récupère la connexion à la BD et on prépare une requête d'insertion
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Tuteurs(nom,prenom,fonction,service,numTelephone,mail,adresse,"
            + "disponibilite) VALUES(?,?,?,?,?,?,?,?)"); 

            //Définition des champs requis pour la requête d'insertion, et execution ensuite grâce à la fonction executeUpdate()
            createStatement.setString(1, tuteur.getNom()); 
            createStatement.setString(2, tuteur.getPrenom());
            createStatement.setString(3, tuteur.getFonction()); 
            createStatement.setString(4, tuteur.getService());  
            createStatement.setInt(5, tuteur.getNumTelephone()); 
            createStatement.setString(6, tuteur.getMail());  
            createStatement.setString(7, tuteur.getAdresse());
            createStatement.setString(8, tuteur.getDisponibilite());

            createStatement.executeUpdate();
            createStatement.close();

            //On va chercher dans la BD le tuple inséré grâce à la fonction find, et on retourne le tuteur inséré
            Tuteur tuteurInsere = this.findByString(tuteur.getMail());
            return tuteurInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD update() pour modifier un tuteur présent dans la BD. Même fonctionnement que la méthode create(), à la différence
    //de la requête Update utilisée ici.    
    @Override
    public Tuteur update(Tuteur tuteur) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Tuteurs SET nom = ?, prenom = ?, fonction = ?, service = ?,"
            + " numTelephone = ?, mail = ?, adresse = ?, disponibilite = ? where id = ?");

            updateStatement.setString(1, tuteur.getNom()); 
            updateStatement.setString(2, tuteur.getPrenom());
            updateStatement.setString(3, tuteur.getFonction()); 
            updateStatement.setString(4, tuteur.getService());  
            updateStatement.setInt(5, tuteur.getNumTelephone()); 
            updateStatement.setString(6, tuteur.getMail());  
            updateStatement.setString(7, tuteur.getAdresse());
            updateStatement.setString(8, tuteur.getDisponibilite());
            updateStatement.setInt(9, tuteur.getId());
            
            updateStatement.executeUpdate();
            updateStatement.close();

            Tuteur tuteurModifie = this.find(tuteur.getId());
            return tuteurModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD delete pour supprimer un tuteur de la BD par son id
    @Override
    public void delete(int id) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Tuteurs WHERE id = ?");

            deleStatement.setInt(1, id); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Tuteur findByString(String mail) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection du tuteur par son adresse mail avec une requête SQL préparée, ensuite on définit le mail
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Tuteurs where mail = ?");
            findStatement.setString(1, mail);
            ResultSet results = findStatement.executeQuery();

            Tuteur tuteur = new Tuteur();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type tuteur a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                tuteur.setId(results.getInt("id"));
                tuteur.setNom(results.getString("nom"));
                tuteur.setPrenom(results.getString("prenom"));
                tuteur.setFonction(results.getString("fonction"));
                tuteur.setService(results.getString("service"));
                tuteur.setNumTelephone(results.getInt("numTelephone"));
                tuteur.setMail(results.getString("mail"));
                tuteur.setAdresse(results.getString("adresse"));
                tuteur.setDisponibilite(results.getString("disponibilite"));
            }
            findStatement.close();
            results.close();
            return tuteur;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
