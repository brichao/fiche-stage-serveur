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
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername("jcuefylwpjunph");
            config.setPassword("3f25e5139b8a0e863758c0ce3d05963a9f4af21425cd3b6846200f81bf68f2f8");
            config.setMaximumPoolSize(3);
            return new HikariDataSource(config);
        }
    }
}
