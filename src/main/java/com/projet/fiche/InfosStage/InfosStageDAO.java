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

@Service
public class InfosStageDAO implements InterfaceDAO<InfosStage>{

    @Autowired
    private DataSource dataSource;

    @Override
    public ArrayList<InfosStage> findAll() throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM infosStages");

            ArrayList<InfosStage> stages = new ArrayList<InfosStage>();
            while(results.next()){
                InfosStage stage = new InfosStage();
                stage.setId(results.getInt("id"));
                stage.setDateDebutPartiel(results.getDate("dateDebutPartiel"));
                stage.setDateFinPartiel(results.getDate("dateFinPartiel"));
                stage.setDateFinPlein(results.getDate("dateFinPlein"));
                stage.setDateDebutInterruption(results.getDate("dateDebutInterruption"));
                stage.setDateFinInterruption(results.getDate("dateFinInterruption"));
                stage.setNbHeures(results.getInt("nbHeures"));
                stage.setGratification(results.getBoolean("gratification"));
                stage.setMontantGratification(results.getInt("montantGratification"));
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
            return stages;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public InfosStage find(String titre) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM infosStages where titre = ?");
            selectStatement.setString(1, titre);
            ResultSet results = selectStatement.executeQuery();

            InfosStage stage = new InfosStage();
            while(results.next()){
                stage.setId(results.getInt("id"));
                stage.setDateDebutPartiel(results.getDate("dateDebutPartiel"));
                stage.setDateFinPartiel(results.getDate("dateFinPartiel"));
                stage.setDateFinPlein(results.getDate("dateFinPlein"));
                stage.setDateDebutInterruption(results.getDate("dateDebutInterruption"));
                stage.setDateFinInterruption(results.getDate("dateFinInterruption"));
                stage.setNbHeures(results.getInt("nbHeures"));
                stage.setGratification(results.getBoolean("gratification"));
                stage.setMontantGratification(results.getInt("montantGratification"));
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

    @Override
    public InfosStage create(InfosStage stageObject) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO infosStages(dateDebutPartiel,dateFinPartiel,dateFinPlein," 
            + "dateDebutInterruption,dateFinInterruption,nbHeures,gratification,montantGratification,versementGratification,laboratoireUGA,"
            + "avantages,confidentialite,titre,description,objectifs,taches,details) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            createStatement.setDate(1, stageObject.getDateDebutPartiel()); 
            createStatement.setDate(2, stageObject.getDateFinPartiel()); 
            createStatement.setDate(3, stageObject.getDateFinPlein()); 
            createStatement.setDate(4, stageObject.getDateDebutInterruption()); 
            createStatement.setDate(5, stageObject.getDateFinInterruption()); 
            createStatement.setInt(6, stageObject.getNbHeures()); 
            createStatement.setBoolean(7, stageObject.isGratification()); 
            createStatement.setInt(8, stageObject.getMontantGratification()); 
            createStatement.setString(9, stageObject.getVersementGratification()); 
            createStatement.setString(10, stageObject.getLaboratoireUGA()); 
            createStatement.setString(11, stageObject.getAvantages()); 
            createStatement.setBoolean(12, stageObject.isConfidentialite()); 
            createStatement.setString(13, stageObject.getTitre()); 
            createStatement.setString(14, stageObject.getDescription()); 
            createStatement.setString(15, stageObject.getObjectifs());
            createStatement.setString(16, stageObject.getTaches());  
            createStatement.setString(17, stageObject.getDetails()); 

            createStatement.executeUpdate();
            createStatement.close();

            InfosStage stageInsere = this.find(stageObject.getTitre());
            return stageInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public InfosStage update(InfosStage stageObject) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE infosStages SET dateDebutPartiel = ?, dateFinPartiel = ?," 
            + "dateFinPlein = ?, dateDebutInterruption = ?, dateFinInterruption = ?, nbHeures = ?, gratification = ?, montantGratification = ?,"
            + "versementGratification = ?, laboratoireUGA = ?, avantages = ?, confidentialite = ?, titre = ?, description = ?, objectifs = ?,"
            + "taches = ?, details = ? where id = ?");

            updateStatement.setDate(1, stageObject.getDateDebutPartiel()); 
            updateStatement.setDate(2, stageObject.getDateFinPartiel()); 
            updateStatement.setDate(3, stageObject.getDateFinPlein()); 
            updateStatement.setDate(4, stageObject.getDateDebutInterruption()); 
            updateStatement.setDate(5, stageObject.getDateFinInterruption()); 
            updateStatement.setInt(6, stageObject.getNbHeures()); 
            updateStatement.setBoolean(7, stageObject.isGratification()); 
            updateStatement.setInt(8, stageObject.getMontantGratification()); 
            updateStatement.setString(9, stageObject.getVersementGratification()); 
            updateStatement.setString(10, stageObject.getLaboratoireUGA()); 
            updateStatement.setString(11, stageObject.getAvantages()); 
            updateStatement.setBoolean(12, stageObject.isConfidentialite()); 
            updateStatement.setString(13, stageObject.getTitre()); 
            updateStatement.setString(14, stageObject.getDescription()); 
            updateStatement.setString(15, stageObject.getObjectifs());
            updateStatement.setString(16, stageObject.getTaches());  
            updateStatement.setString(17, stageObject.getDetails());  
            updateStatement.setInt(18, stageObject.getId());

            updateStatement.executeUpdate();
            updateStatement.close();

            InfosStage stageModifie = this.find(stageObject.getTitre());
            return stageModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String titre) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM infosStages WHERE titre = ?");

            deleStatement.setString(1, titre); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
