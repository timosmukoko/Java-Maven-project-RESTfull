/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author MTimos
 */
public class DBUtil {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com_Assignment-2C_war_1.0-SNAPSHOTPU");
    
    public static EntityManagerFactory getEmf()
    {
        return emf;
    }
    
}
