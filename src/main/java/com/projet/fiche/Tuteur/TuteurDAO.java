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

@Service
public class TuteurDAO implements InterfaceDAO<Tuteur>{

    @Autowired
    private DataSource dataSource;

    @Override
    public ArrayList<Tuteur> findAll() throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Tuteurs");

            ArrayList<Tuteur> tuteurs = new ArrayList<Tuteur>();

            while(results.next()){
                Tuteur tuteur = new Tuteur();
                tuteur.setId(results.getInt("id"));
                tuteur.setNom(results.getString("nom"));
                tuteur.setPrenom(results.getString("prenom"));
                tuteur.setFonction(results.getString("fonction"));
                tuteur.setService(results.getString("service"));
                tuteur.setNumeroTel(results.getInt("numTelephone"));
                tuteur.setMail(results.getString("mail"));
                tuteur.setAdresse(results.getString("adresse"));
                tuteur.setDisponibilite(results.getString("disponibilite"));

                tuteurs.add(tuteur);
            }
            statement.close();
            results.close();
            return tuteurs;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tuteur find(String mail) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Tuteurs where mail = ?");
            findStatement.setString(1, mail);
            ResultSet results = findStatement.executeQuery();

            Tuteur tuteur = new Tuteur();

            while(results.next()){
                tuteur.setId(results.getInt("id"));
                tuteur.setNom(results.getString("nom"));
                tuteur.setPrenom(results.getString("prenom"));
                tuteur.setFonction(results.getString("fonction"));
                tuteur.setService(results.getString("service"));
                tuteur.setNumeroTel(results.getInt("numTelephone"));
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

    @Override
    public Tuteur create(Tuteur tuteur) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Tuteurs(nom,prenom,fonction,service,numTelephone,mail,adresse,"
            + "disponibilite) VALUES(?,?,?,?,?,?,?,?)"); 

            createStatement.setString(1, tuteur.getNom()); 
            createStatement.setString(2, tuteur.getPrenom());
            createStatement.setString(3, tuteur.getFonction()); 
            createStatement.setString(4, tuteur.getService());  
            createStatement.setInt(5, tuteur.getNumeroTel()); 
            createStatement.setString(6, tuteur.getMail());  
            createStatement.setString(7, tuteur.getAdresse());
            createStatement.setString(8, tuteur.getDisponibilite());

            createStatement.executeUpdate();
            createStatement.close();

            Tuteur tuteurInsere = this.find(tuteur.getMail());
            return tuteurInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tuteur update(Tuteur tuteur) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Tuteurs SET nom = ?, prenom = ?, fonction = ?, service = ?,"
            + " numTelephone = ?, mail = ?, adresse = ?, disponibilite = ? where id = ?");

            updateStatement.setString(1, tuteur.getNom()); 
            updateStatement.setString(2, tuteur.getPrenom());
            updateStatement.setString(3, tuteur.getFonction()); 
            updateStatement.setString(4, tuteur.getService());  
            updateStatement.setInt(5, tuteur.getNumeroTel()); 
            updateStatement.setString(6, tuteur.getMail());  
            updateStatement.setString(7, tuteur.getAdresse());
            updateStatement.setString(8, tuteur.getDisponibilite());
            updateStatement.setInt(9, tuteur.getId());
            
            updateStatement.executeUpdate();
            updateStatement.close();

            Tuteur tuteurModifie = this.find(tuteur.getMail());
            return tuteurModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String mail) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Tuteurs WHERE mail = ?");

            deleStatement.setString(1, mail); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
