package com.projet.fiche.ServiceGestion;

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
public class ServiceGestionDAO implements InterfaceDAO<ServiceGestion>{

    @Autowired
    private DataSource dataSource;

    @Override
    public ArrayList<ServiceGestion> findAll() throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM ServicesGestion");

            ArrayList<ServiceGestion> services = new ArrayList<ServiceGestion>();

            while(results.next()){
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
            return services;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ServiceGestion find(String mail) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM ServicesGestion where mail = ?");
            findStatement.setString(1, mail);
            ResultSet results = findStatement.executeQuery();

            ServiceGestion service = new ServiceGestion();

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

    @Override
    public ServiceGestion create(ServiceGestion service) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO ServicesGestion(nom,prenom,numeroTel,mail,adresse)" 
            + " VALUES(?,?,?,?,?)");

            createStatement.setString(1, service.getNom()); 
            createStatement.setString(2, service.getPrenom()); 
            createStatement.setInt(3, service.getNumeroTel()); 
            createStatement.setString(4, service.getMail());  
            createStatement.setString(5, service.getAdresse());

            createStatement.executeUpdate();
            createStatement.close();

            ServiceGestion serviceInsere = this.find(service.getMail());
            return serviceInsere;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ServiceGestion update(ServiceGestion service) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE ServicesGestion SET nom = ?, prenom = ?, numeroTel = ?, mail = ?, adresse = ?" +
            " where id = ?");

            
            updateStatement.setString(1, service.getNom()); 
            updateStatement.setString(2, service.getPrenom()); 
            updateStatement.setInt(3, service.getNumeroTel()); 
            updateStatement.setString(4, service.getMail());  
            updateStatement.setString(5, service.getAdresse());
            updateStatement.setInt(6, service.getId());
            
            updateStatement.executeUpdate();
            updateStatement.close();

            ServiceGestion serviceModifie = this.find(service.getMail());
            return serviceModifie;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String mail) throws RuntimeException {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement deleStatement = connection.prepareStatement("DELETE FROM ServicesGestion WHERE mail = ?");

            deleStatement.setString(1, mail); 

            deleStatement.executeUpdate();
            deleStatement.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
