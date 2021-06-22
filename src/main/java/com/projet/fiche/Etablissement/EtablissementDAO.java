package com.projet.fiche.Etablissement;

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
public class EtablissementDAO implements InterfaceDAO<Etablissement>{

    @Autowired
    private DataSource dataSource;

    @Override
    public ArrayList<Etablissement> findAll() throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Etablissements");

            ArrayList<Etablissement> etablissements = new ArrayList<Etablissement>();
            while(results.next()){
                Etablissement etablissement = new Etablissement();
                etablissement.setId(results.getInt("idEtablissement"));
                etablissement.setRaisonSociale(results.getString("raisonSociale"));
                etablissement.setRepresentantLegal(results.getString("representantLegal"));
                etablissement.setFonction(results.getString("fonction"));
                etablissement.setNumeroSiret(results.getInt("numeroSiret"));
                etablissement.setCodeApe(results.getString("codeApe"));
                etablissement.setDomaineActivite(results.getString("domaineActivite"));
                etablissement.setEffectif(results.getInt("effectif"));
                etablissement.setIdAdresse(results.getInt("idAdresse"));
                etablissement.setServiceAccueil(results.getString("serviceAccueil"));
                etablissement.setServiceGestion(results.getInt("serviceGestion"));
                
                etablissements.add(etablissement);
            }
            results.close();
            statement.close();
            return etablissements;

        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Etablissement find(String chaineSiret) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Etablissements where numeroSiret = ?");
            int numeroSiret = Integer.parseInt(chaineSiret);
            selectStatement.setInt(1, numeroSiret);
            ResultSet results = selectStatement.executeQuery();

            Etablissement etablissement = new Etablissement();
            while(results.next()){
                etablissement.setId(results.getInt("idEtablissement"));
                etablissement.setRaisonSociale(results.getString("raisonSociale"));
                etablissement.setRepresentantLegal(results.getString("representantLegal"));
                etablissement.setFonction(results.getString("fonction"));
                etablissement.setNumeroSiret(results.getInt("numeroSiret"));
                etablissement.setCodeApe(results.getString("codeApe"));
                etablissement.setDomaineActivite(results.getString("domaineActivite"));
                etablissement.setEffectif(results.getInt("effectif"));
                etablissement.setIdAdresse(results.getInt("idAdresse"));
                etablissement.setServiceAccueil(results.getString("serviceAccueil"));
                etablissement.setServiceGestion(results.getInt("serviceGestion"));
            }

            results.close();
            selectStatement.close();
            return etablissement;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Etablissement create(Etablissement etablissement) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Etablissements(raisonSociale,representantLegal,"
            + "fonction,numeroSiret,codeApe,domaineActivite,effectif,idAdresse,serviceAccueil,serviceGestion)" 
            + "VALUES(?,?,?,?,?,?,?,?,?,?)");

            createStatement.setString(1, etablissement.getRaisonSociale()); 
            createStatement.setString(2, etablissement.getRepresentantLegal()); 
            createStatement.setString(3, etablissement.getFonction()); 
            createStatement.setInt(4, etablissement.getNumeroSiret()); 
            createStatement.setString(5, etablissement.getCodeApe()); 
            createStatement.setString(6, etablissement.getDomaineActivite()); 
            createStatement.setInt(7, etablissement.getEffectif());
            createStatement.setInt(8, etablissement.getIdAdresse());
            createStatement.setString(9, etablissement.getServiceAccueil());
            createStatement.setInt(10, etablissement.getServiceGestion()); 

            createStatement.executeUpdate();
            createStatement.close();

            Etablissement etablissementInsere = this.find(String.valueOf(etablissement.getNumeroSiret()));
            return etablissementInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Etablissement update(Etablissement etablissement) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Etudiants SET raisonSociale = ?, representantLegal = ?,"
            +" fonction = ?, numeroSiret = ?, codeApe = ?, domaineActivite = ?, effectif = ?, idAdresse = ?, serviceAccueil = ?,"
            +" serviceGestion = ? where idEtablissement = ?");

            updateStatement.setString(1, etablissement.getRaisonSociale()); 
            updateStatement.setString(2, etablissement.getRepresentantLegal()); 
            updateStatement.setString(3, etablissement.getFonction()); 
            updateStatement.setInt(4, etablissement.getNumeroSiret()); 
            updateStatement.setString(5, etablissement.getCodeApe()); 
            updateStatement.setString(6, etablissement.getDomaineActivite()); 
            updateStatement.setInt(7, etablissement.getEffectif());
            updateStatement.setInt(8, etablissement.getIdAdresse());
            updateStatement.setString(9, etablissement.getServiceAccueil());
            updateStatement.setInt(10, etablissement.getServiceGestion()); 
            updateStatement.setInt(11, etablissement.getId()); 

            updateStatement.executeUpdate();
            updateStatement.close();

            Etablissement etablissementModifie = this.find(String.valueOf(etablissement.getNumeroSiret()));
            return etablissementModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String chaineSiret) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Etablissements WHERE numeroSiret = ?");

            int numeroSiret = Integer.parseInt(chaineSiret);
            deleStatement.setInt(1, numeroSiret);

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
