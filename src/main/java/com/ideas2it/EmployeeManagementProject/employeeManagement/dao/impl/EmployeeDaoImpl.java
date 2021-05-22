//                                                                             ;
package com.ideas2it.EmployeeManagementProject.employeeManagement.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ideas2it.EmployeeManagementProject.employeeManagement.dao.EmployeeDao;
import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Employee;
import com.ideas2it.EmployeeManagementProject.employeeManagementException.EmployeeManagementException;
import com.ideas2it.EmployeeManagementProject.logger.Logger;
import com.ideas2it.EmployeeManagementProject.projectManagement.model.Project;
import com.ideas2it.EmployeeManagementProject.sessionFactory.HibernateSessionFactory;

/**
 * Class which implements Dao interface
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private HibernateSessionFactory singleton 
            = HibernateSessionFactory.getInstance();
    private Logger logger = new Logger(EmployeeDaoImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEmployee(Employee employee) 
            throws EmployeeManagementException {
        boolean isAdded = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            isAdded = false;
            logger.logError(e);
            throw new EmployeeManagementException("Failed to add employee");
        } finally {
        	closeSession(session);
        }
        return isAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee retrieveEmployee(int id) 
            throws EmployeeManagementException {
        Employee employee = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            employee = (Employee) session.get(Employee.class, id);
            if (null != employee) {
            	employee.getAddresses().size();
            	employee.getProjects().size();	
            }
        } catch (HibernateException e) {
        	logger.logError(e);
        	throw new EmployeeManagementException("Failed to retrieve employee");
        } finally {
        	closeSession(session);
        }
        return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(Employee employee) 
            throws EmployeeManagementException {
        boolean isUpdated = true;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();


            session.update(employee);
	        System.out.println("dao method caleed...............updated....................");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            isUpdated = false;
            logger.logError(e);
        	throw new EmployeeManagementException("Failed to update employee");
        } finally {
        	closeSession(session);
        }
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees(boolean isDeleted) 
            throws EmployeeManagementException {
        List<Employee> employees = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            /*String s = "from employee where is deleted: a";
            Query query = session.createQuery(s);
            query.setParameter("a", isDeleted);
            employees = query.list();*/
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("isDeleted", isDeleted));
            employees = criteria.list();
        } catch (Exception e) {
        	logger.logError(e);
        	throw new EmployeeManagementException("Failed to retrieve employees");
        } finally {
        	closeSession(session);
        }
        return employees;
    }

    /**
     * Closes the session
     * @param session          session object
     */
    private void closeSession(Session session) {
    	if (null != session) {
    		session.close();
    	}
    }
}