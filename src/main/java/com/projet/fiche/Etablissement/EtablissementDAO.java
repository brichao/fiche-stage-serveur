package com.projet.fiche.Etablissement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.projet.fiche.InterfaceDAO;
import com.projet.fiche.Adresse.Adresse;
import com.projet.fiche.Adresse.AdresseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//La classe DAO est un service qui implémente l'interface DAO, et qui définit les méthodes CRUD, cela assure une sécurité en plus entre le serveur
//et la base de donnée (éviter les injections de donnée dans la BD par exemple)
@Service
public class EtablissementDAO implements InterfaceDAO<Etablissement>{

    //@Autowired permet au Framework Spring de résoudre et injecter le Bean qui gère la connexion à la base de donnée    
    @Autowired
    private DataSource dataSource;
    
    //@Autowired permet au Framework Spring de résoudre et injecter le service qui gère les méthodes CRUD de l'objet Adresse    
    @Autowired
    private AdresseDAO adresseDAO;

    //Méthode CRUD findAll() pour récupèrer tous les tuples de la table établissement dans la BD
    @Override
    public ArrayList<Etablissement> findAll() throws RuntimeException {
        //Récupèrer la connexion grâce au service dataSource
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            Statement statementAdresse = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //On créé une requête et on sélectionne tous les tuples
            ResultSet results = statement.executeQuery("SELECT * FROM Etablissements");
            //Déclaration d'une liste pour stocker tous les objets étudiants
            ArrayList<Etablissement> etablissements = new ArrayList<Etablissement>();

            //Tant qu'on a encore des résultats de la BD
            while(results.next()){
                //On déclare un objet de type étudiant, peuplement de tous les attributs de cet objet, et ajout dans la liste d'objets etudiants
                Etablissement etablissement = new Etablissement();
                etablissement.setId(results.getInt("id"));
                etablissement.setRaisonSociale(results.getString("raisonSociale"));
                etablissement.setRepresentantLegal(results.getString("representantLegal"));
                etablissement.setFonction(results.getString("fonction"));
                etablissement.setNumeroSiret(results.getInt("numeroSiret"));
                etablissement.setCodeApe(results.getString("codeApe"));
                etablissement.setDomaineActivite(results.getString("domaineActivite"));
                etablissement.setEffectif(results.getInt("effectif"));
                etablissement.setIdAdresse(results.getInt("idAdresse"));
                etablissement.setServiceAccueil(results.getString("serviceAccueil"));

                Adresse adresse = new Adresse();
                adresse.setId(etablissement.getIdAdresse());
                ResultSet resultsAdresse = statementAdresse.executeQuery("SELECT * FROM Adresses WHERE id = " + etablissement.getIdAdresse());
                if(resultsAdresse.first()){
                    adresse.setAdresse(resultsAdresse.getString("adresse"));
                    adresse.setCodePostal(resultsAdresse.getInt("codePostal"));
                    adresse.setVille(resultsAdresse.getString("ville"));
                    adresse.setPays(resultsAdresse.getString("pays"));
                }
                resultsAdresse.close();
                statementAdresse.close();
                etablissement.setAdresse(adresse);

                etablissements.add(etablissement);
            }
            results.close();
            statement.close();
            //Renvoie la liste des étudiants
            return etablissements;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD find() pour récupèrer un établissement par son id de la BD
    @Override
    public Etablissement find(int id) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection de l'établissement par son numéro de siret avec une requête SQL préparée, ensuite on définit le numéro de siret
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Etablissements where id = ?");
            Statement statementAdresse = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            selectStatement.setInt(1, id);
            ResultSet results = selectStatement.executeQuery();

            Etablissement etablissement = new Etablissement();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type établissement a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                etablissement.setId(results.getInt("id"));
                etablissement.setRaisonSociale(results.getString("raisonSociale"));
                etablissement.setRepresentantLegal(results.getString("representantLegal"));
                etablissement.setFonction(results.getString("fonction"));
                etablissement.setNumeroSiret(results.getInt("numeroSiret"));
                etablissement.setCodeApe(results.getString("codeApe"));
                etablissement.setDomaineActivite(results.getString("domaineActivite"));
                etablissement.setEffectif(results.getInt("effectif"));
                etablissement.setIdAdresse(results.getInt("idAdresse"));
                etablissement.setServiceAccueil(results.getString("serviceAccueil"));

                Adresse adresse = new Adresse();
                adresse.setId(etablissement.getIdAdresse());

                ResultSet resultsAdresse = statementAdresse.executeQuery("SELECT * FROM Adresses WHERE id = " + etablissement.getIdAdresse());

                if(resultsAdresse.first()){
                    adresse.setAdresse(resultsAdresse.getString("adresse"));
                    adresse.setCodePostal(resultsAdresse.getInt("codePostal"));
                    adresse.setVille(resultsAdresse.getString("ville"));
                    adresse.setPays(resultsAdresse.getString("pays"));
                }

                
                etablissement.setAdresse(adresse);
            }
            statementAdresse.close();
            results.close();
            selectStatement.close();
            return etablissement;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD create() pour créer un établissement dans la BD, résultat: le tuple crée
    @Override
    public Etablissement create(Etablissement etablissement) throws RuntimeException {
        //On récupère la connexion à la BD et on prépare une requête d'insertion
        try(Connection connection = dataSource.getConnection()){
            //On utilise le service adresseDAO pour créer l'adresse de l'établissement dans la BD
            Adresse adresse = new Adresse();
            adresse = adresseDAO.create(etablissement.getAdresse());
            //Définition des champs requis pour la requête d'insertion, et execution ensuite grâce à la fonction executeUpdate()
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Etablissements(raisonSociale,representantLegal,"
            + "fonction,numeroSiret,codeApe,domaineActivite,effectif,idAdresse,serviceAccueil)" 
            + "VALUES(?,?,?,?,?,?,?,?,?)");

            createStatement.setString(1, etablissement.getRaisonSociale()); 
            createStatement.setString(2, etablissement.getRepresentantLegal()); 
            createStatement.setString(3, etablissement.getFonction()); 
            createStatement.setInt(4, etablissement.getNumeroSiret()); 
            createStatement.setString(5, etablissement.getCodeApe()); 
            createStatement.setString(6, etablissement.getDomaineActivite()); 
            createStatement.setInt(7, etablissement.getEffectif());
            createStatement.setInt(8, adresse.getId());
            createStatement.setString(9, etablissement.getServiceAccueil());

            createStatement.executeUpdate();
            createStatement.close();

            //On va chercher dans la BD le tuple inséré grâce à la fonction find, et on retourne l'établissement inséré
            Etablissement etablissementInsere = this.findByString(String.valueOf(etablissement.getNumeroSiret()));
            return etablissementInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD update() pour modifier un établissement et son adresse présent dans la BD. Même fonctionnement que la méthode create(), à la différence
    //de la requête Update utilisée ici. 
    @Override
    public Etablissement update(Etablissement etablissement) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){

            adresseDAO.update(etablissement.getAdresse());

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Etablissements SET raisonSociale = ?, representantLegal = ?,"
            +" fonction = ?, numeroSiret = ?, codeApe = ?, domaineActivite = ?, effectif = ?, serviceAccueil = ?"
            +" where id = ?");

            updateStatement.setString(1, etablissement.getRaisonSociale()); 
            updateStatement.setString(2, etablissement.getRepresentantLegal()); 
            updateStatement.setString(3, etablissement.getFonction()); 
            updateStatement.setInt(4, etablissement.getNumeroSiret()); 
            updateStatement.setString(5, etablissement.getCodeApe()); 
            updateStatement.setString(6, etablissement.getDomaineActivite()); 
            updateStatement.setInt(7, etablissement.getEffectif());
            updateStatement.setString(8, etablissement.getServiceAccueil());
            updateStatement.setInt(9, etablissement.getId()); 

            updateStatement.executeUpdate();
            updateStatement.close();

            Etablissement etablissementModifie = this.find(etablissement.getId());
            return etablissementModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Méthode CRUD delete pour supprimer un étalissement de la BD par son numéro de siret
    @Override
    public void delete(int id) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){

            Etablissement etablissement = new Etablissement();
            etablissement = this.find(id);

            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Etablissements WHERE id = ?");

            deleStatement.setInt(1, id);

            deleStatement.executeUpdate();
            deleStatement.close();

            adresseDAO.delete(etablissement.getAdresse().getAdresse());
            
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Etablissement findByString(String chaineSiret) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            //Selection de l'établissement par son numéro de siret avec une requête SQL préparée, ensuite on définit le numéro de siret
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Etablissements where numeroSiret = ?");
            Statement statementAdresse = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int numeroSiret = Integer.parseInt(chaineSiret);
            selectStatement.setInt(1, numeroSiret);
            ResultSet results = selectStatement.executeQuery();

            Etablissement etablissement = new Etablissement();

            //On récupère la première ligne du résultat retourné, results.next() vaudra false ensuite. Un objet de type établissement a été déclaré, peuplé
            //avec le résultat de la sélection SQL, ensuite retourné
            while(results.next()){
                etablissement.setId(results.getInt("id"));
                etablissement.setRaisonSociale(results.getString("raisonSociale"));
                etablissement.setRepresentantLegal(results.getString("representantLegal"));
                etablissement.setFonction(results.getString("fonction"));
                etablissement.setNumeroSiret(results.getInt("numeroSiret"));
                etablissement.setCodeApe(results.getString("codeApe"));
                etablissement.setDomaineActivite(results.getString("domaineActivite"));
                etablissement.setEffectif(results.getInt("effectif"));
                etablissement.setIdAdresse(results.getInt("idAdresse"));
                etablissement.setServiceAccueil(results.getString("serviceAccueil"));

                Adresse adresse = new Adresse();
                adresse.setId(etablissement.getIdAdresse());

                ResultSet resultsAdresse = statementAdresse.executeQuery("SELECT * FROM Adresses WHERE id = " + etablissement.getIdAdresse());

                if(resultsAdresse.first()){
                    adresse.setAdresse(resultsAdresse.getString("adresse"));
                    adresse.setCodePostal(resultsAdresse.getInt("codePostal"));
                    adresse.setVille(resultsAdresse.getString("ville"));
                    adresse.setPays(resultsAdresse.getString("pays"));
                }

                
                etablissement.setAdresse(adresse);
            }
            statementAdresse.close();
            results.close();
            selectStatement.close();
            return etablissement;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
