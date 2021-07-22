package com.projet.fiche.Etudiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.projet.fiche.InterfaceDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//La classe DAO est un service qui implémente l'interface DAO, et qui définit les méthodes CRUD, cela assure une sécurité en plus entre le serveur
//et la base de donnée (éviter les injections de donnée dans la BD par exemple)
@Service
public class EtudiantDAO implements InterfaceDAO<Etudiant>{

    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée    
    @Autowired
    private DataSource dataSource;

    //Méthode CRUD findAll() pour récupèrer tous les tuples de la table étudiant dans la BD
    @Override
    public ArrayList<Etudiant> findAll() throws RuntimeException{
        //Récupèrer la connexion grâce au service dataSource
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            //On créé une requête et on sélectionne tous les tuples
            ResultSet results = statement.executeQuery("SELECT * FROM Etudiants");
            //Déclaration d'une liste pour stocker tous les objets étudiants
            ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();

            //Tant qu'on a encore des résultats de la BD
            while(results.next()){
                //On déclare un objet de type étudiant, peuplement de tous les attributs de cet objet, et ajout dans la liste d'objets étudiants
                Etudiant etudiant = new Etudiant();
                etudiant.setId(results.getInt("id"));
                etudiant.setNom(results.getString("nom"));
                etudiant.setPrenom(results.getString("prenom"));
                etudiant.setNumEtudiant(results.getInt("numEtudiant"));
                etudiant.setNumPortable(results.getInt("numPortable"));
                etudiant.setMail(results.getString("mail"));
                etudiant.setAdresse(results.getString("adresse"));
                etudiant.setTypeAffiliation(results.getString("typeAffiliation"));
                etudiant.setCaisseAssurance(results.getString("caisseAssurance"));

                etudiants.add(etudiant);
            }
            results.close();
            statement.close();
            //Renvoie la liste des étudiants
            return etudiants;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD find() pour récupèrer un étudiant par son id de la BD
    @Override
    public Etudiant find(int id) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            //Selection de l'étudiant par son adresse mail avec une requête SQL préparée, ensuite on définit le mail
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Etudiants where id = ?");
            selectStatement.setInt(1, id);
            ResultSet results = selectStatement.executeQuery();

            Etudiant etudiant = new Etudiant();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type étudiant a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                etudiant.setId(results.getInt("id"));
                etudiant.setNom(results.getString("nom"));
                etudiant.setPrenom(results.getString("prenom"));
                etudiant.setNumEtudiant(results.getInt("numEtudiant"));
                etudiant.setNumPortable(results.getInt("numPortable"));
                etudiant.setMail(results.getString("mail"));
                etudiant.setAdresse(results.getString("adresse"));
                etudiant.setTypeAffiliation(results.getString("typeAffiliation"));
                etudiant.setCaisseAssurance(results.getString("caisseAssurance"));
            }
            results.close();
            selectStatement.close();
            return etudiant;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD create() pour créer un étudiant dans la BD, résultat: le tuple crée
    @Override
    public Etudiant create(Etudiant etudiant) throws RuntimeException{
        //On récupère la connexion à la BD et on prépare une requête d'insertion
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Etudiants(nom,prenom,numEtudiant,numPortable," 
            + "mail,adresse,typeAffiliation,caisseAssurance) VALUES(?,?,?,?,?,?,?,?)");

            //Définition des champs requis pour la requête d'insertion, et execution ensuite grâce à la fonction executeUpdate()
            createStatement.setString(1, etudiant.getNom()); 
            createStatement.setString(2, etudiant.getPrenom()); 
            createStatement.setInt(3, etudiant.getNumEtudiant()); 
            createStatement.setInt(4, etudiant.getNumPortable()); 
            createStatement.setString(5, etudiant.getMail()); 
            createStatement.setString(6, etudiant.getAdresse());
            createStatement.setString(7, etudiant.getTypeAffiliation()); 
            createStatement.setString(8, etudiant.getCaisseAssurance()); 

            createStatement.executeUpdate();
            createStatement.close();

            PreparedStatement createStatementFiche = connection.prepareStatement("INSERT INTO ficheRenseignement(idEtudiant, dateDeCreation) VALUES (?,?)");

            //On va chercher dans la BD le tuple inséré grâce à la fonction find, et on retourne l'étudiant inséré
            Etudiant etudiantInsere = this.findByString(etudiant.getMail());

            //Creation d'une fiche de renseignement et insertion de l'étudiant crée dedans
            createStatementFiche.setInt(1, etudiantInsere.getId());
            createStatementFiche.setDate(2, etudiant.getDateDeCreation());
            createStatementFiche.executeUpdate();
            createStatementFiche.close();

            etudiantInsere.setDateDeCreation(etudiant.getDateDeCreation());

            return etudiantInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD update() pour modifier un étudiant présent dans la BD. Même fonctionnement que la méthode create(), à la différence
    //de la requête Update utilisée ici.  
    @Override
    public Etudiant update(Etudiant etudiant) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Etudiants SET nom = ?, prenom = ?, numEtudiant = ?," +
            "numPortable = ?, mail = ?, adresse = ?, typeAffiliation = ?, caisseAssurance = ? where id = ?");

            updateStatement.setString(1, etudiant.getNom()); 
            updateStatement.setString(2, etudiant.getPrenom()); 
            updateStatement.setInt(3, etudiant.getNumEtudiant()); 
            updateStatement.setInt(4, etudiant.getNumPortable()); 
            updateStatement.setString(5, etudiant.getMail()); 
            updateStatement.setString(6, etudiant.getAdresse());
            updateStatement.setString(7, etudiant.getTypeAffiliation()); 
            updateStatement.setString(8, etudiant.getCaisseAssurance()); 
            updateStatement.setInt(9, etudiant.getId()); 

            updateStatement.executeUpdate();
            updateStatement.close();

            Etudiant etudiantModifie = this.find(etudiant.getId());
            return etudiantModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD delete pour supprimer un étudiant de la BD par son mail
    @Override
    public void delete(int id) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Etudiants WHERE id = ?");

            deleStatement.setInt(1, id); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Etudiant findByString(String mail) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection de l'étudiant par son adresse mail avec une requête SQL préparée, ensuite on définit le mail
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Etudiants where mail = ?");
            selectStatement.setString(1, mail);
            ResultSet results = selectStatement.executeQuery();

            Etudiant etudiant = new Etudiant();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type étudiant a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                etudiant.setId(results.getInt("id"));
                etudiant.setNom(results.getString("nom"));
                etudiant.setPrenom(results.getString("prenom"));
                etudiant.setNumEtudiant(results.getInt("numEtudiant"));
                etudiant.setNumPortable(results.getInt("numPortable"));
                etudiant.setMail(results.getString("mail"));
                etudiant.setAdresse(results.getString("adresse"));
                etudiant.setTypeAffiliation(results.getString("typeAffiliation"));
                etudiant.setCaisseAssurance(results.getString("caisseAssurance"));
            }
            results.close();
            selectStatement.close();
            return etudiant;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
