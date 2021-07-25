package com.projet.fiche.ServiceGestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.projet.fiche.InterfaceDAO;
import com.projet.fiche.FicheRenseignement.FicheRenseignement;
import com.projet.fiche.FicheRenseignement.FicheRenseignementDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//La classe DAO est un service qui implémente l'interface DAO, et qui définit les méthodes CRUD, cela assure une sécurité en plus entre le serveur
//et la base de donnée (éviter les injections de donnée dans la BD par exemple)
@Service
public class ServiceGestionDAO implements InterfaceDAO<ServiceGestion>{

    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet Fiche de renseignement
    @Autowired
    private FicheRenseignementDAO ficheDAO;

    //Méthode CRUD findAll() pour récupèrer tous les tuples de la table service de gestion dans la BD
    @Override
    public ArrayList<ServiceGestion> findAll() throws RuntimeException {
        //Récupèrer la connexion grâce au service dataSource
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            //On créé une requête et on sélectionne tous les tuples
            ResultSet results = statement.executeQuery("SELECT * FROM ServicesGestion");

            //Déclaration d'une liste pour stocker tous les objets services de gestion
            ArrayList<ServiceGestion> services = new ArrayList<ServiceGestion>();

            //Tant qu'on a encore des résultats de la BD
            while(results.next()){
                //On déclare un objet de type service de gestion, peuplement de tous les attributs de cet objet, et ajout dans la liste d'objets services de gestion
                ServiceGestion service = new ServiceGestion();
                service.setId(results.getInt("id"));
                service.setNom(results.getString("nom"));
                service.setPrenom(results.getString("prenom"));
                service.setNumeroTel(results.getInt("numeroTel"));
                service.setMail(results.getString("mail"));
                service.setAdresse(results.getString("adresse"));

                services.add(service);
            }
            statement.close();
            results.close();
            //Renvoie la liste des services de gestion
            return services;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD find() pour récupèrer un service de gestion par son id de la BD
    @Override
    public ServiceGestion find(int id) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection du service de gestion par son adresse mail avec une requête SQL préparée, ensuite on définit le mail
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM ServicesGestion where id = ?");
            findStatement.setInt(1, id);
            ResultSet results = findStatement.executeQuery();

            ServiceGestion service = new ServiceGestion();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type service de gestion a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                service.setId(results.getInt("id"));
                service.setNom(results.getString("nom"));
                service.setPrenom(results.getString("prenom"));
                service.setNumeroTel(results.getInt("numeroTel"));
                service.setMail(results.getString("mail"));
                service.setAdresse(results.getString("adresse"));
            }
            findStatement.close();
            results.close();
            return service;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD create() pour créer un service de gestion dans la BD, résultat: le tupe crée
    @Override
    public ServiceGestion create(ServiceGestion service) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //On récupère la connexion à la BD et on prépare une requête d'insertion
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO ServicesGestion(nom,prenom,numeroTel,mail,adresse)" 
            + " VALUES(?,?,?,?,?)");

            //Définition des champs requis pour la requête d'insertion, et execution ensuite grâce à la fonction executeUpdate()
            createStatement.setString(1, service.getNom().toUpperCase()); 
            createStatement.setString(2, service.getPrenom().toUpperCase()); 
            createStatement.setInt(3, service.getNumeroTel()); 
            createStatement.setString(4, service.getMail());  
            createStatement.setString(5, service.getAdresse());

            createStatement.executeUpdate();
            createStatement.close();

            //Insertion de l'établissement dans la fiche de renseignement: Tout d'abord, on recherche l'id de la fiche par nom et prenom de l'etudiant
            FicheRenseignement fiche = ficheDAO.find(service.getNomEtudiant(), service.getPrenomEtudiant());

            //On va chercher dans la BD le tuple inséré grâce à la fonction find, pour récupèrer l'id du service de gestion crée, ainsi retourner l'établissement
            ServiceGestion serviceInsere = this.findByString(service.getMail());

            PreparedStatement statement2 = connection.prepareStatement("UPDATE ficheRenseignement SET idServiceGestion = ? WHERE id = ?");
            statement2.setInt(1, serviceInsere.getId());
            statement2.setInt(2, fiche.getIdFiche());
            statement2.executeUpdate();

            return serviceInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD update() pour modifier un service de gestion présent dans la BD. Même fonctionnement que la méthode create(), à la différence
    //de la requête Update utilisée ici.
    @Override
    public ServiceGestion update(ServiceGestion service) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE ServicesGestion SET nom = ?, prenom = ?, numeroTel = ?, mail = ?, adresse = ?" +
            " where id = ?");

            
            updateStatement.setString(1, service.getNom().toUpperCase()); 
            updateStatement.setString(2, service.getPrenom().toUpperCase()); 
            updateStatement.setInt(3, service.getNumeroTel()); 
            updateStatement.setString(4, service.getMail());  
            updateStatement.setString(5, service.getAdresse());
            updateStatement.setInt(6, service.getId());
            
            updateStatement.executeUpdate();
            updateStatement.close();

            ServiceGestion serviceModifie = this.find(service.getId());
            return serviceModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD delete pour supprimer un service de gestion de la BD par son id
    @Override
    public void delete(int id) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM ServicesGestion WHERE id = ?");

            deleStatement.setInt(1, id); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public ServiceGestion findByString(String mail) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection du service de gestion par son adresse mail avec une requête SQL préparée, ensuite on définit le mail
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM ServicesGestion where mail = ?");
            findStatement.setString(1, mail);
            ResultSet results = findStatement.executeQuery();

            ServiceGestion service = new ServiceGestion();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type service de gestion a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                service.setId(results.getInt("id"));
                service.setNom(results.getString("nom"));
                service.setPrenom(results.getString("prenom"));
                service.setNumeroTel(results.getInt("numeroTel"));
                service.setMail(results.getString("mail"));
                service.setAdresse(results.getString("adresse"));
            }
            findStatement.close();
            results.close();
            return service;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
