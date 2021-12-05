package com.foodmenu.model.business.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.exceptions.UserPrivilegesException;
import com.foodmenu.model.business.factory.ServiceFactory;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;
import com.foodmenu.model.services.userservice.IUserService;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Zach Stanfill
 * Adapted from Prof. Ishmael, MSSE670, Regis University
 */
public class UserManager {
	
	private static String propertiesFile = "config/application.properties";
	
	private User user;
	
	/** Default Password Policy Values */
	private static int minLength = 8;
	private static int maxLength = 24;
	private static int charClasses = 4;
	private static int minCharClass = 0;
	private static int iterations = 10000;
	private static int keyLength = 512;
	
	public UserManager() {
	}
	
	public UserManager(User user) {
		this.user = user;
	}
	
	/** 
	 * Use Case : Users-400
	 * Add New User
	 * @throws IOException 
	 */
	public boolean addNewUser(User user) throws ServiceLoadException, 
		UserServiceException, IOException {
		
		String salt = "";
		String password = user.getPassword();
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		
		if(isValidPassword(password)) {
			salt = generateSaltKey();
			
            char[] passwordChars = password.toCharArray();
            byte[] saltBytes = salt.getBytes();
            
            byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
            String hashedString = Hex.encodeHexString(hashedBytes);
            
            user.setPassword(hashedString);
		} else {
			return false;
		}
		
		
		if(userSvc.createUserData(user, salt)) {
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * Use Case : Users-410
	 * Delete Existing User 
	 */
	public boolean deleteUser(User user) throws ServiceLoadException, 
		UserServiceException, UserPrivilegesException {
		
		if(this.user.getRole().equals("admin")) {
			ServiceFactory serviceFactory = new ServiceFactory();
			IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
			if(userSvc.deleteUserData(user)) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new UserPrivilegesException(String.format("User %s isn't an admin, and therefore does not have the \nappropriate privileges to perform delete task!", user.getEmailAddress()));
		}
	}
	
	/**
	 * Use Case : Users-420
	 * Authenticate User
	 */
	public boolean authenticateUser(String email, String password) throws 
		ServiceLoadException, UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		
		String salt = "";
		
		salt = userSvc.retrieveUserSaltData(email);
		
		if(salt == null) {
			return false;
		}
		
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        
        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
        String hashedString = Hex.encodeHexString(hashedBytes);
		
		if(userSvc.authenticateUserData(email, hashedString)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Use Case : Users-430
	 * Retrieve User
	 */
	public User retrieveUser(String email) throws 
		ServiceLoadException, UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		return userSvc.retrieveUserData(email);	
	}
	
	/**
	 * Use Case : Users-440
	 * Retrieve All Users
	 */
	public ArrayList<User> retrieveAllUsers() throws 
		ServiceLoadException, UserServiceException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		return userSvc.retrieveAllUserData();	
	}
	
	/**
	 * Use Case : Users-450
	 * Reset User Password -- Trusted
	 * @throws IOException 
	 */
	public boolean resetUserPassword(User user) throws 
		ServiceLoadException, UserServiceException, IOException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		
		String salt = "";
		
		salt = userSvc.retrieveUserSaltData(user.getEmailAddress());
		
        char[] passwordChars = user.getPassword().toCharArray();
        byte[] saltBytes = salt.getBytes();
        
        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
        String hashedString = Hex.encodeHexString(hashedBytes);
        
        user.setPassword(hashedString);
		
		return userSvc.updateUserPasswordData(user);	
	}
	
	/**
	 * Use Case : Users-451
	 * Reset User Password -- Untrusted - Verifying Identity
	 * @throws UserServiceException 
	 */
	public boolean resetUserPassword(String email, int age, String recPhrase) throws ServiceLoadException, UserServiceException {

		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		
		return userSvc.resetUserPasswordData(email, age, recPhrase);	
	}
	
	/**
	 * Use Case : Users-451
	 * Reset User Password -- Untrusted - Verified
	 * @throws UserServiceException ServiceLoadException 
	 * @throws IOException 
	 */
	public boolean resetUserPassword(String email, String password) throws UserServiceException, ServiceLoadException, IOException {
		
		ServiceFactory serviceFactory = new ServiceFactory();
		IUserService userSvc = (IUserService)serviceFactory.getService("IUserService");
		
		User user = userSvc.retrieveUserData(email);
		
		String salt = "";

		if(isValidPassword(password)) {
			salt = userSvc.retrieveUserSaltData(user.getEmailAddress());
			
	        char[] passwordChars = user.getPassword().toCharArray();
	        byte[] saltBytes = salt.getBytes();
	        
	        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
	        String hashedString = Hex.encodeHexString(hashedBytes);
	        
	        user.setPassword(hashedString);
			
			return userSvc.updateUserPasswordData(user);
		} else {
			return false;
		}
	}
	
    /**
     * Read Properties Files
     */
	public static void readProperties() throws IOException {
		/** Read Configured Properties */
		try (InputStream input = new FileInputStream(propertiesFile)) {
            Properties prop = new Properties();
            prop.load(input);
                        
            if(prop.getProperty("password.minLength") != null) {
            	minLength = Integer.parseInt(prop.getProperty("password.minLength"));
            }
            if(prop.getProperty("password.maxLength") != null) {
            	maxLength = Integer.parseInt(prop.getProperty("password.maxLength"));
            }
            if(prop.getProperty("password.charClasses") != null) {
            	charClasses = Integer.parseInt(prop.getProperty("password.charClasses"));
            }
            if(prop.getProperty("password.minCharClass") != null) {
            	minCharClass = Integer.parseInt(prop.getProperty("password.minCharClass"));
            }

            /** Validate Password Parameters */
            if(minLength < 0 || minLength > maxLength) {
            	System.err.println("Invalid password.minLength ; Setting Value to 8.");
            	minLength = 8;
            }
            if((maxLength > 50)) {
            	System.err.println("Invalid password.maxLength ; Setting Value to 24.");
            	maxLength = 24;
            }
            if(!(charClasses >= 1 && charClasses <= 4)) {
            	System.err.println("Invalid password.charClasses ; Setting Value to 4.");
            	charClasses = 4;	
            }
            if((charClasses*minCharClass) > maxLength) {
        		System.err.println("Invalid password.minCharClass ; Setting Value to 0");
        		minCharClass = 0;	
        	}
			
		} catch (Exception e) {
			minLength = 8;
			maxLength = 24;
			charClasses = 4;
			minCharClass = 0;
			iterations = 10000;
			keyLength = 512;
			System.err.println("Error in reading property file password values, setting to default values!");
		}
		
		
		
	}
	
	/**
     * Validate Password against Policy
     * @param password
     * @return boolean
     * @throws NumberFormatException 
	 * @throws IOException 
     */
    public static boolean isValidPassword(String password) throws NumberFormatException, IOException {  	   	

    	readProperties();
    	
    	/** Verify the password length is within the specified parameters */
    	if (password.length() < minLength || password.length() > maxLength) {
    		System.err.println("Invalid Password Length");
    		return false;
    	}
    	
    	/** Initialize Counter Variables */
		int upper = 0, lower = 0, number = 0, special = 0;
		
		/** Initialize Available Special Characters for use in Passwords */
		String specialCharacters = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
		ArrayList<Character>specChar = new ArrayList<Character>();
		for(int c = 0; c < specialCharacters.length(); c++) { 
			specChar.add(specialCharacters.charAt(c));
		}

		/** Count Character Classes */
		for(int c = 0; c < password.length(); c++) { 
			if (password.charAt(c) >= 'A' && password.charAt(c) <= 'Z') { upper++; }
			else if (password.charAt(c) >= 'a' && password.charAt(c) <= 'z') { lower++; }
			else if (password.charAt(c) >= '0' && password.charAt(c) <= '9') { number++; }
			else if (specChar.indexOf(password.charAt(c)) > -1) { special++; }
		}
		
		/** Validate Password Meets Parameters */
		if(charClasses == 1) {
			if(upper > 0 || lower > 0 || special > 0) {
				System.err.println("Invalid Password Characters. Password can only contain Numbers [0-9].");
				return false;
			} else if (!(number >= minCharClass)) {
				System.err.println("Password does not contain the Minimum Number of Characters from the Number [0-9] Character Class.");
				return false;
			}
		} else if (charClasses == 2 ) {
			if(number > 0 || special > 0) {
				System.err.println("Invalid Password Characters. Password can only contain Alphabetic Characters [a-zA-Z].");
				return false;
			} else if (!(upper >= minCharClass && lower >= minCharClass)) {
				System.err.println("Password does not contain the Minimum Number of Characters from each Character Class.");
				return false;
			}
		} else if (charClasses == 3 ) {
			if(special > 0) {
				System.err.println("Invalid Password Characters. Password can only contain Alphanumeric Characters [a-zA-Z0-9].");
				return false;
			} else if (!(upper >= minCharClass && lower >= minCharClass && number >= minCharClass)) {
				System.err.println("Password does not contain the Minimum Number of Characters from each Character Class.");
				return false;
			}
		} else if (charClasses == 4 ) {
			if(!(upper >= minCharClass && lower >= minCharClass && number >= minCharClass && special >= minCharClass)) {
				System.err.println("Password does not contain the Minimum Number of Characters from each Character Class.");
				System.err.printf("\tUpper: %d\n\tLower: %d\n\tNumbers: %d\n\tSpecial: %d\n", upper, lower, number, special);
				return false;
			}
		}
			
        return true;
    }
    
	/**
	 * Generate Salt Key
	 * @return salt key
	 */
	public static String generateSaltKey() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 32;
	    Random random = new Random();

	    String salt = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    return salt;
	}


    /**
     * Hash Password
     * @param password
     * @param salt
     * @param iterations
     * @param keyLength
     * @return
     */
    public static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
	
	
}
