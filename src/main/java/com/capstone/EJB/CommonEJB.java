package com.capstone.EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CommonEJB<T>
{
    // ======================================
    // =             Attributes             =
    // ======================================

    @PersistenceContext(unitName = "DVDStoreWebAppPU")
    private EntityManager em;

    // ======================================
    // =           Public Methods           =
    // ======================================

    // Persist the object to the database
    public boolean persistData(T data)
    {
        boolean check = true;
        try
        {
            em.persist(data);
        }
        catch(Exception e)
        {
            check = false;
        }
        return check;
    }
        
    public boolean deleteData(T data) 
    {
	boolean check = true;
	try
	{
            em.remove(em.merge(data));
	}
	catch(Exception e)
	{
            check = false;
	}
	return check;
    }

    public boolean updateData(T data) 
    {
	boolean check = true;
	try
	{
            em.merge(data);
	}
	catch(Exception e)
	{
            check = false;
	}
	return check;
    }
    
    // Get information of an object by executing the query
    public T getSingleInfo(String strQuery)
    {
	Query query = em.createQuery(strQuery);
	T obj = (T)query.getSingleResult();
	return obj;
    }
    
    // Get information of many objects by executing the query
    public List<T> getListInfo(String strQuery)
    {
	Query query = em.createQuery(strQuery);
	List<T> ls = query.getResultList();
	return ls;
    }
    
    public List<T> getListInfoNativeQuery(String strQuery)
    {
	Query query = em.createNativeQuery(strQuery);
	List<T> ls = query.getResultList();
	return ls;
    }
    
    public Integer executeQuery(String strQuery)
    {
	Query query = em.createNativeQuery(strQuery);
	Integer result = query.executeUpdate();
	return result;
    }
    
    public boolean checkExisted(String strQuery)
    {
        boolean check = false;
        Query query = em.createQuery(strQuery);
        int count = query.getResultList().size();
        if(count > 0)
        {
            check = true;
        }
        return check;
    }

}