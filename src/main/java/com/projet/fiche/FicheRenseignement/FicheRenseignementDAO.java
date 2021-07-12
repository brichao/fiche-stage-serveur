package com.projet.fiche.FicheRenseignement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.projet.fiche.Etudiant.Etudiant;
import com.projet.fiche.Etudiant.EtudiantDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//La classe DAO est un service qui implémente l'interface DAO, et qui définit les méthodes CRUD, cela assure une sécurité en plus entre le serveur
//et la base de donnée (éviter les injections de donnée dans la BD par exemple)
@Service
public class FicheRenseignementDAO {
    
    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée
    @Autowired
    private DataSource dataSource;

    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet étudiant
    @Autowired
    private EtudiantDAO etudiantService;

    //Méthode CRUD findAll() pour récupèrer tous les tuples de la table fiches de renseignement dans la BD
    public ArrayList<FicheRenseignement> findAll() throws RuntimeException {
        //Récupèrer la connexion grâce au service dataSource
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            Statement statementEtudiant = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //On créé une requête et on sélectionne tous les tuples
            ResultSet results = statement.executeQuery("SELECT * FROM ficheRenseignement");
            //Déclaration d'une liste pour stocker tous les objets étudiants
            ArrayList<FicheRenseignement> fiches = new ArrayList<FicheRenseignement>();

            //Tant qu'on a encore des résultats de la BD
            while(results.next()){
                //On déclare un objet de type fiche de renseignement, peuplement de tous les attributs de cet objet, et ajout dans la liste d'objets fiches
                FicheRenseignement fiche = new FicheRenseignement();
                fiche.setIdFiche(results.getInt("id"));
                fiche.setIdEtudiant(results.getInt("idEtudiant"));
                fiche.setIdEtablissement(results.getInt("idEtablissement"));
                fiche.setIdServiceGestion(results.getInt("idServiceGestion"));
                fiche.setIdTuteur(results.getInt("idTuteur"));
                fiche.setIdInfosStage(results.getInt("idInfosStage"));

                Etudiant etudiant = new Etudiant();
                etudiant.setId(fiche.getIdEtudiant());
                ResultSet resultsEtudiant = statementEtudiant.executeQuery("SELECT * FROM Etudiants WHERE id = " + fiche.getIdEtudiant());
                if(resultsEtudiant.first()){
                    etudiant.setId(resultsEtudiant.getInt("id"));
                    etudiant.setNom(resultsEtudiant.getString("nom"));
                    etudiant.setPrenom(resultsEtudiant.getString("prenom"));
                    etudiant.setNumEtudiant(resultsEtudiant.getInt("numEtudiant"));
                    etudiant.setNumPortable(resultsEtudiant.getInt("numPortable"));
                    etudiant.setMail(resultsEtudiant.getString("mail"));
                    etudiant.setAdresse(resultsEtudiant.getString("adresse"));
                    etudiant.setTypeAffiliation(resultsEtudiant.getString("typeAffiliation"));
                    etudiant.setCaisseAssurance(resultsEtudiant.getString("caisseAssurance"));
                }
                
                fiche.setEtudiant(etudiant);

                fiches.add(fiche);
            }
            statementEtudiant.close();
            results.close();
            statement.close();
            //Renvoie la liste des fiches
            return fiches;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD find() pour récupèrer une fiche par le nom et prénom de l'étudiant de la BD
    public FicheRenseignement find(String nom, String prenom) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            //Tout d'abord, on va chercher dans la table Etudiants si l'etudiant (nom et prenom) existe dans la BD
            PreparedStatement etudiantStatement = connection.prepareStatement("SELECT * FROM Etudiants where nom = ? AND prenom = ?");
            etudiantStatement.setString(1, nom);
            etudiantStatement.setString(2, prenom);
            ResultSet resultatEtudiant = etudiantStatement.executeQuery();
            Etudiant etudiant = new Etudiant();
            while(resultatEtudiant.next()){
                etudiant.setId(resultatEtudiant.getInt("id"));
            }
            //Après avoir cherché l'étudiant, on récupère l'id de l'étudiant, et on cherchera dans la table ficheRenseignement si cet id est présent
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ficheRenseignement where idEtudiant = ?");
            statement.setInt(1, etudiant.getId());
            ResultSet results = statement.executeQuery();
            
            FicheRenseignement fiche = new FicheRenseignement();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type fiche de renseignement a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                fiche.setIdFiche(results.getInt("id"));
                fiche.setIdEtudiant(results.getInt("idEtudiant"));
                fiche.setIdEtablissement(results.getInt("idEtablissement"));
                fiche.setIdServiceGestion(results.getInt("idServiceGestion"));
                fiche.setIdTuteur(results.getInt("idTuteur"));
                fiche.setIdInfosStage(results.getInt("idInfosStage"));

                Etudiant etudiantTrouver = new Etudiant();
                etudiantTrouver = etudiantService.find(fiche.getIdEtudiant());
                
                fiche.setEtudiant(etudiantTrouver);
            }    
            etudiantStatement.close();
            results.close();
            statement.close();
            return fiche;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }
}