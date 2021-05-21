//                                                                             ;
package com.ideas2it.EmployeeManagementProject.projectManagement.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.ideas2it.EmployeeManagementProject.employeeManagementException.EmployeeManagementException;
import com.ideas2it.EmployeeManagementProject.logger.Logger;
import com.ideas2it.EmployeeManagementProject.projectManagement.dao.ProjectDao;
import com.ideas2it.EmployeeManagementProject.projectManagement.model.Project;
import com.ideas2it.EmployeeManagementProject.sessionFactory.HibernateSessionFactory;


/**
 * Class which implements Dao interface
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class ProjectDaoImpl implements ProjectDao {

    private HibernateSessionFactory singleton 
            = HibernateSessionFactory.getInstance();
    private Logger logger = new Logger(ProjectDaoImpl.class);

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertProject(Project project) 
            throws EmployeeManagementException {
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(project);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        	logger.logError(e);
            throw new EmployeeManagementException("Failed to add project");
        } finally {
        	closeSession(session);
        }
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public Project retrieveProject(int projectId) 
            throws EmployeeManagementException {
        Project project = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            project = (Project) session.get(Project.class, projectId);
            if (null != project) {
            	project.getEmployees().size();
            }
            //throw new HibernateException("qwertyuioooooop");
        } catch (HibernateException e) {
        	logger.logError(e);
        	throw new EmployeeManagementException("Failed to retrieve project");
        } finally {
        	closeSession(session);
        }
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProject(Project project) 
            throws EmployeeManagementException {
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(project);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.logError(e);
            throw new EmployeeManagementException("Failed to update project");
        } finally {
        	closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAllProjects(boolean isDeleted) 
            throws EmployeeManagementException {
        List<Project> projects = null;
        Session session = null;
        try {
            SessionFactory sessionFactory = singleton.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            //String s = "from project where is deleted: a";
            //Query query = session.createQuery(s);
            //query.setParameter("a", isDeleted);
            //projects = query.list();
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.eq("isDeleted", isDeleted));
            projects = criteria.list();
        } catch (Exception e) {
        	logger.logError(e);
        	throw new EmployeeManagementException("Failed to load projects");
        } finally {
        	closeSession(session);
        }
        return projects;
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
