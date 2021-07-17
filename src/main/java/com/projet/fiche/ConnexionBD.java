package com.projet.fiche;

import java.sql.SQLException;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class ConnexionBD {

    //Récupérer l'url de la database du fichier .env
    @Value("${spring.datasource.jdbc-url}")
    private String dbUrl;

    

    //Mise en place d'une connexion à la BD
    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        System.out.println("L'url de la base de donnée : " + dbUrl);
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setMaximumPoolSize(3);
            return new HikariDataSource(config);
        }
    }
}
