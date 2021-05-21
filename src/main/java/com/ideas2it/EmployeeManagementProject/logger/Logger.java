package com.ideas2it.EmployeeManagementProject.logger;

import org.apache.logging.log4j.LogManager;

/**
 * Logger to manage logs in the application
 *
 * @author Sembiyan
 * @version 1.0  May 09
 */
public class Logger {

	private org.apache.logging.log4j.Logger logger;
	
	public Logger(Class<?> classType) {
		logger = LogManager.getLogger(classType);
	}
	
	/**
	 * Logs the details about debug
	 * @param        
	 */
	public void logDebug(Object error) {
		logger.debug(error);
	}
	
	/**
	 * Logs the infos to the logger
	 * @param        
	 */
	public void logInfo(Object error) {
		logger.info(error);
	}
	
	/**
	 * Logs the warnings to the logger
	 * @param        
	 */
	public void logWarning(Object error) {
		logger.warn(error);
	}
	
	/**
	 * Logs the errors to the logger
	 * @param        
	 */
	public void logError(Object error) {
		logger.error(error);
	}
	
	/**
	 * Logs the fatal error to the logger
	 * @param        
	 */
	public void logFatal(Object error) {
		logger.fatal(error);
	}
}
