package com.projet.fiche;

import java.util.ArrayList;

public interface InterfaceDAO<T> {
    
    ArrayList<T> findAll() throws RuntimeException;

    T find(String chaine) throws RuntimeException;

    T create(T object) throws RuntimeException;

    T update(T object) throws RuntimeException;

    void delete(String chaine) throws RuntimeException;
}
