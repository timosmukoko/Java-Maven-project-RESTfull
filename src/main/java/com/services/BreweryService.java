/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.entities.Breweries;
import com.entities.BreweriesGeocode;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author MTimos
 */
@Service
public class BreweryService {
    
    public static List<Breweries> getAllBreweries(){
    
        EntityManager em = DBUtil.getEmf().createEntityManager();
        String q = "SELECT b FROM Breweries b ORDER BY b.id DESC";
        TypedQuery<Breweries> tq = em.createQuery(q, Breweries.class);
        
        List<Breweries> list;
         
        try{
            list = tq.getResultList();
            if(list == null || list.isEmpty())
                list = null;
        }
        finally {
            em.close();
        }      
         return list;
    } 
    
     public static void editBrewery(Breweries b, BreweriesGeocode g) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(b); 
            em.merge(g);
            trans.commit();
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
            em.close();
        }  
    }  
     
     //Edoot Brewery
     public static void edit_Brewery(int id, Breweries b, BreweriesGeocode g) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(b); 
            em.merge(g);
            trans.commit();
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
            em.close();
        }  
    }  
     
     
      public static void editBreweryGeocode(BreweriesGeocode b) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(b);
            trans.commit();
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
            em.close();
        }  
    }   
   
      //Add Brewery
    public static void addBrewery(Breweries b, BreweriesGeocode g) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(b);
            em.persist(g); 
            trans.commit();
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
            em.close();
        }
    }
    
    public static void addBreweryGeocode(BreweriesGeocode b) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(b);           
            trans.commit();
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
            em.close();
        }
    }
    
     //Delte Brewery    
    public static void deleteBrewery(Breweries b) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
             
        try {
            trans.begin();
            em.remove(em.merge(b));          
            trans.commit();
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
         finally{
            em.close();
        }
    }
    
    public static void deleteBreweryGeocode(BreweriesGeocode b) {
        
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction trans = em.getTransaction();
             
        try {
            trans.begin();
            em.remove(em.merge(b));          
            trans.commit();
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
         finally{
            em.close();
        }
    }
    
        
    //Get by ID
    public static Breweries getBreweriesByID(int id){
         
        EntityManager em = DBUtil.getEmf().createEntityManager();      
        try{
            Breweries b = em.find(Breweries.class, id);
             return b;
        }
        finally{
            em.close();
        }  
    }   
    
     public static BreweriesGeocode getBreweriesGeocodeByID(int id){
        
         EntityManager em = DBUtil.getEmf().createEntityManager();
        try{
            BreweriesGeocode b = em.find(BreweriesGeocode.class, id);
            return b;
        }
        finally{
            em.close();
        }  
    }    
}
