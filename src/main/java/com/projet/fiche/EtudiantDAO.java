package com.projet.fiche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtudiantDAO implements InterfaceDAO<Etudiant>{

    @Autowired
    private DataSource dataSource;

    @Override
    public ArrayList<Etudiant> findAll() throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Etudiants");

            ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
            while(results.next()){
                Etudiant etudiant = new Etudiant();
                etudiant.setId(results.getInt("id"));
                etudiant.setNom(results.getString("nom"));
                etudiant.setPrenom(results.getString("prenom"));
                etudiant.setNumEtudiant(results.getInt("numEtudiant"));
                etudiant.setNumPortable(results.getInt("numPortable"));
                etudiant.setMail(results.getString("mail"));
                etudiant.setTypeAffiliation(results.getString("typeAffiliation"));
                etudiant.setInscription(results.getString("inscription"));
                etudiant.setEnseignant(results.getString("enseignant"));

                etudiants.add(etudiant);
            }
            results.close();
            statement.close();
            return etudiants;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Etudiant find(String mail) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Etudiants where mail = ?");
            selectStatement.setString(1, mail);
            ResultSet results = selectStatement.executeQuery();

            Etudiant etudiant = new Etudiant();
            while(results.next()){
                etudiant.setId(results.getInt("id"));
                etudiant.setNom(results.getString("nom"));
                etudiant.setPrenom(results.getString("prenom"));
                etudiant.setNumEtudiant(results.getInt("numEtudiant"));
                etudiant.setNumPortable(results.getInt("numPortable"));
                etudiant.setMail(results.getString("mail"));
                etudiant.setTypeAffiliation(results.getString("typeAffiliation"));
                etudiant.setInscription(results.getString("inscription"));
                etudiant.setEnseignant(results.getString("enseignant"));
            }
            results.close();
            selectStatement.close();
            return etudiant;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Etudiant create(Etudiant etudiant) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Etudiants(nom,prenom,numEtudiant,numPortable," 
            + "mail,typeAffiliation,inscription,enseignant) VALUES(?,?,?,?,?,?,?,?)");

            createStatement.setString(1, etudiant.getNom()); 
            createStatement.setString(2, etudiant.getPrenom()); 
            createStatement.setInt(3, etudiant.getNumEtudiant()); 
            createStatement.setInt(4, etudiant.getNumPortable()); 
            createStatement.setString(5, etudiant.getMail()); 
            createStatement.setString(6, etudiant.getTypeAffiliation()); 
            createStatement.setString(7, etudiant.getInscription()); 
            createStatement.setString(8, etudiant.getEnseignant()); 

            createStatement.executeUpdate();
            createStatement.close();

            Etudiant etudiantInsere = this.find(etudiant.getMail());
            return etudiantInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Etudiant update(Etudiant etudiant) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Etudiants SET nom = ?, prenom = ?, numEtudiant = ?," +
            "numPortable = ?, mail = ?, typeAffiliation = ?, inscription = ?, enseignant = ? where id = ?");

            updateStatement.setString(1, etudiant.getNom()); 
            updateStatement.setString(2, etudiant.getPrenom()); 
            updateStatement.setInt(3, etudiant.getNumEtudiant()); 
            updateStatement.setInt(4, etudiant.getNumPortable()); 
            updateStatement.setString(5, etudiant.getMail()); 
            updateStatement.setString(6, etudiant.getTypeAffiliation()); 
            updateStatement.setString(7, etudiant.getInscription()); 
            updateStatement.setString(8, etudiant.getEnseignant()); 
            updateStatement.setInt(9, etudiant.getId()); 

            updateStatement.executeUpdate();
            updateStatement.close();

            Etudiant etudiantModifie = this.find(etudiant.getMail());
            return etudiantModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String mail) throws RuntimeException{
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Etudiants WHERE mail = ?");

            deleStatement.setString(1, mail); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
