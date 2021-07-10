package com.projet.fiche;

import java.util.ArrayList;

//Interface DAO à faire respecter pour toutes les classes DAO
public interface InterfaceDAO<T> {
    
    ArrayList<T> findAll() throws RuntimeException;

    T find(String chaine) throws RuntimeException;

    T create(T object) throws RuntimeException;

    T update(T object) throws RuntimeException;

    void delete(String chaine) throws RuntimeException;
}
