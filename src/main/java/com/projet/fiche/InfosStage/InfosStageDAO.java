package com.projet.fiche.InfosStage;

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
public class InfosStageDAO implements InterfaceDAO<InfosStage>{

    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    //Méthode CRUD findAll() pour récupèrer tous les tuples de la table d'informations de stage dans la BD
    @Override
    public ArrayList<InfosStage> findAll() throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Récupèrer la connexion grâce au service dataSource
            Statement statement = connection.createStatement();
            //On créé une requête et on sélectionne tous les tuples
            ResultSet results = statement.executeQuery("SELECT * FROM infosStages");
            //Déclaration d'une liste pour stocker tous les objets d'informations de stage
            ArrayList<InfosStage> stages = new ArrayList<InfosStage>();

            //Tant qu'on a encore des résultats de la BD
            while(results.next()){
                //On déclare un objet de type infosStage, peuplement de tous les attributs de cet objet, et ajout dans la liste d'objets d'informations de stage
                InfosStage stage = new InfosStage();
                stage.setId(results.getInt("id"));
                stage.setDateDebutPartiel(results.getDate("dateDebutPartiel"));
                stage.setDateFinPartiel(results.getDate("dateFinPartiel"));
                stage.setDateDebutPlein(results.getDate("dateDebutPlein"));
                stage.setDateFinPlein(results.getDate("dateFinPlein"));
                stage.setDateDebutInterruption(results.getDate("dateDebutInterruption"));
                stage.setDateFinInterruption(results.getDate("dateFinInterruption"));
                stage.setNbHeures(results.getDouble("nbHeures"));
                stage.setGratification(results.getBoolean("gratification"));
                stage.setMontantGratification(results.getDouble("montantGratification"));
                stage.setVersementGratification(results.getString("versementGratification"));
                stage.setLaboratoireUGA(results.getString("laboratoireUGA"));
                stage.setAvantages(results.getString("avantages"));
                stage.setConfidentialite(results.getBoolean("confidentialite"));
                stage.setTitre(results.getString("titre"));
                stage.setDescription(results.getString("description"));
                stage.setObjectifs(results.getString("objectifs"));
                stage.setTaches(results.getString("taches"));
                stage.setDetails(results.getString("details"));

                stages.add(stage);
            }
            results.close();
            statement.close();
            //Renvoie la liste des informations de tous les stages
            return stages;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD find() pour récupèrer des informations d'un stage par son id de la BD
    @Override
    public InfosStage find(int idInfos) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection des informations du stage par son id avec une requête SQL préparée, ensuite on définit l'id
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM infosStages where id = ?");
            selectStatement.setInt(1, idInfos);
            ResultSet results = selectStatement.executeQuery();

            InfosStage stage = new InfosStage();
            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type infosStage a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                stage.setId(results.getInt("id"));
                stage.setDateDebutPartiel(results.getDate("dateDebutPartiel"));
                stage.setDateFinPartiel(results.getDate("dateFinPartiel"));
                stage.setDateDebutPlein(results.getDate("dateDebutPlein"));
                stage.setDateFinPlein(results.getDate("dateFinPlein"));
                stage.setDateDebutInterruption(results.getDate("dateDebutInterruption"));
                stage.setDateFinInterruption(results.getDate("dateFinInterruption"));
                stage.setNbHeures(results.getDouble("nbHeures"));
                stage.setGratification(results.getBoolean("gratification"));
                stage.setMontantGratification(results.getDouble("montantGratification"));
                stage.setVersementGratification(results.getString("versementGratification"));
                stage.setLaboratoireUGA(results.getString("laboratoireUGA"));
                stage.setAvantages(results.getString("avantages"));
                stage.setConfidentialite(results.getBoolean("confidentialite"));
                stage.setTitre(results.getString("titre"));
                stage.setDescription(results.getString("description"));
                stage.setObjectifs(results.getString("objectifs"));
                stage.setTaches(results.getString("taches"));
                stage.setDetails(results.getString("details"));
            }
            results.close();
            selectStatement.close();
            return stage;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD create() pour créer un tuple des informations d'un stage dans la BD, résultat: le tupe crée
    @Override
    public InfosStage create(InfosStage stageObject) throws RuntimeException {
        //On récupère la connexion à la BD et on prépare une requête d'insertion
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO infosStages(dateDebutPartiel,dateFinPartiel,dateDebutPlein," 
            + "dateFinPlein,dateDebutInterruption,dateFinInterruption,nbHeures,gratification,montantGratification,versementGratification,laboratoireUGA,"
            + "avantages,confidentialite,titre,description,objectifs,taches,details) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            //Définition des champs requis pour la requête d'insertion, et execution ensuite grâce à la fonction executeUpdate()
            createStatement.setDate(1, stageObject.getDateDebutPartiel()); 
            createStatement.setDate(2, stageObject.getDateFinPartiel()); 
            createStatement.setDate(3, stageObject.getDateDebutPlein()); 
            createStatement.setDate(4, stageObject.getDateFinPlein()); 
            createStatement.setDate(5, stageObject.getDateDebutInterruption()); 
            createStatement.setDate(6, stageObject.getDateFinInterruption()); 
            createStatement.setDouble(7, stageObject.getNbHeures()); 
            createStatement.setBoolean(8, stageObject.isGratification()); 
            createStatement.setDouble(9, stageObject.getMontantGratification()); 
            createStatement.setString(10, stageObject.getVersementGratification()); 
            createStatement.setString(11, stageObject.getLaboratoireUGA()); 
            createStatement.setString(12, stageObject.getAvantages()); 
            createStatement.setBoolean(13, stageObject.isConfidentialite()); 
            createStatement.setString(14, stageObject.getTitre()); 
            createStatement.setString(15, stageObject.getDescription()); 
            createStatement.setString(16, stageObject.getObjectifs());
            createStatement.setString(17, stageObject.getTaches());  
            createStatement.setString(18, stageObject.getDetails()); 

            createStatement.executeUpdate();
            createStatement.close();

            //On va chercher dans la BD le tuple inséré grâce à la fonction find, et on retourne les informations de stage insérées
            InfosStage stageInsere = this.findByString(stageObject.getTitre());
            return stageInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD update() pour modifier des informations de stage présentes dans la BD. Même fonctionnement que la méthode create(), à la différence
    //de la requête Update utilisée ici.
    @Override
    public InfosStage update(InfosStage stageObject) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE infosStages SET dateDebutPartiel = ?, dateFinPartiel = ?," 
            + "dateDebutPlein = ?, dateFinPlein = ?, dateDebutInterruption = ?, dateFinInterruption = ?, nbHeures = ?, gratification = ?, montantGratification = ?,"
            + "versementGratification = ?, laboratoireUGA = ?, avantages = ?, confidentialite = ?, titre = ?, description = ?, objectifs = ?,"
            + "taches = ?, details = ? where id = ?");

            updateStatement.setDate(1, stageObject.getDateDebutPartiel()); 
            updateStatement.setDate(2, stageObject.getDateFinPartiel()); 
            updateStatement.setDate(3, stageObject.getDateDebutPlein()); 
            updateStatement.setDate(4, stageObject.getDateFinPlein()); 
            updateStatement.setDate(5, stageObject.getDateDebutInterruption()); 
            updateStatement.setDate(6, stageObject.getDateFinInterruption()); 
            updateStatement.setDouble(7, stageObject.getNbHeures()); 
            updateStatement.setBoolean(8, stageObject.isGratification()); 
            updateStatement.setDouble(9, stageObject.getMontantGratification()); 
            updateStatement.setString(10, stageObject.getVersementGratification()); 
            updateStatement.setString(11, stageObject.getLaboratoireUGA()); 
            updateStatement.setString(12, stageObject.getAvantages()); 
            updateStatement.setBoolean(13, stageObject.isConfidentialite()); 
            updateStatement.setString(14, stageObject.getTitre()); 
            updateStatement.setString(15, stageObject.getDescription()); 
            updateStatement.setString(16, stageObject.getObjectifs());
            updateStatement.setString(17, stageObject.getTaches());  
            updateStatement.setString(18, stageObject.getDetails()); 
            updateStatement.setInt(19, stageObject.getId());

            updateStatement.executeUpdate();
            updateStatement.close();

            InfosStage stageModifie = this.find(stageObject.getId());
            return stageModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD delete pour supprimer des informations de stage de la BD par son id
    @Override
    public void delete(int idInfos) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM infosStages WHERE id = ?");

            deleStatement.setInt(1, idInfos); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public InfosStage findByString(String titre) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection des informations du stage par son titre avec une requête SQL préparée, ensuite on définit le titre
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM infosStages where titre = ?");
            selectStatement.setString(1, titre);
            ResultSet results = selectStatement.executeQuery();

            InfosStage stage = new InfosStage();
            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type infosStage a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                stage.setId(results.getInt("id"));
                stage.setDateDebutPartiel(results.getDate("dateDebutPartiel"));
                stage.setDateFinPartiel(results.getDate("dateFinPartiel"));
                stage.setDateDebutPlein(results.getDate("dateDebutPlein"));
                stage.setDateFinPlein(results.getDate("dateFinPlein"));
                stage.setDateDebutInterruption(results.getDate("dateDebutInterruption"));
                stage.setDateFinInterruption(results.getDate("dateFinInterruption"));
                stage.setNbHeures(results.getDouble("nbHeures"));
                stage.setGratification(results.getBoolean("gratification"));
                stage.setMontantGratification(results.getDouble("montantGratification"));
                stage.setVersementGratification(results.getString("versementGratification"));
                stage.setLaboratoireUGA(results.getString("laboratoireUGA"));
                stage.setAvantages(results.getString("avantages"));
                stage.setConfidentialite(results.getBoolean("confidentialite"));
                stage.setTitre(results.getString("titre"));
                stage.setDescription(results.getString("description"));
                stage.setObjectifs(results.getString("objectifs"));
                stage.setTaches(results.getString("taches"));
                stage.setDetails(results.getString("details"));
            }
            results.close();
            selectStatement.close();
            return stage;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
