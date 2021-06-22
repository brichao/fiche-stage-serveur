package com.projet.fiche.Adresse;

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
public class AdresseDAO implements InterfaceDAO<Adresse>{

    @Autowired
    private DataSource dataSource;

    @Override
    public ArrayList<Adresse> findAll() throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Adresses");

            ArrayList<Adresse> adresses = new ArrayList<Adresse>();

            while(results.next()){
                Adresse adresse = new Adresse();
                adresse.setId(results.getInt("id"));
                adresse.setAdresse(results.getString("adresse"));
                adresse.setCodePostal(results.getInt("codePostal"));
                adresse.setVille(results.getString("ville"));
                adresse.setPays(results.getString("pays"));
                adresses.add(adresse);
            }
            statement.close();
            results.close();
            return adresses;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Adresse find(String adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Adresses where adresse = ?");
            findStatement.setString(1, adresse);
            ResultSet results = findStatement.executeQuery();

            Adresse adresseResult = new Adresse();

            while(results.next()){
                adresseResult.setId(results.getInt("id"));
                adresseResult.setAdresse(results.getString("adresse"));
                adresseResult.setCodePostal(results.getInt("codePostal"));
                adresseResult.setVille(results.getString("ville"));
                adresseResult.setPays(results.getString("pays"));
            }
            findStatement.close();
            results.close();
            return adresseResult;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Adresse create(Adresse adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Adresses(adresse,codePostal,ville,pays)" 
            + " VALUES(?,?,?,?)");

            createStatement.setString(1, adresse.getAdresse()); 
            createStatement.setInt(2, adresse.getCodePostal()); 
            createStatement.setString(3, adresse.getVille()); 
            createStatement.setString(4, adresse.getPays());  

            createStatement.executeUpdate();
            createStatement.close();

            Adresse adresseInseree = this.find(adresse.getAdresse());
            return adresseInseree;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Adresse update(Adresse adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Adresses SET adresse = ?, codePostal = ?, ville = ?, pays = ?" +
            " where id = ?");

            
            updateStatement.setString(1, adresse.getAdresse());
            updateStatement.setInt(2, adresse.getCodePostal()); 
            updateStatement.setString(3, adresse.getVille()); 
            updateStatement.setString(4, adresse.getPays());
            updateStatement.setInt(5, adresse.getId());
            
            updateStatement.executeUpdate();
            updateStatement.close();

            Adresse adresseModifiee = this.find(adresse.getAdresse());
            return adresseModifiee;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String adresse) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM Adresses WHERE adresse = ?");

            deleStatement.setString(1, adresse); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
