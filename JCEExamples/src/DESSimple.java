/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
//import sun.misc.BASE64Encoder;

/**
 *
 * @author ahmed
 */
public class DESSimple {
    
    private SecretKey secretkey; 
//    private String keyFile;
    
    public DESSimple(String keyFileName) throws NoSuchAlgorithmException, IOException, ClassNotFoundException 
    {
        // search for key with provided filename. if none exists, generate one and save to disk.
    	File keyFile = new File(keyFileName);
    	if (keyFile.exists()) {
			FileInputStream fileStream = new FileInputStream(keyFileName);
			ObjectInputStream inputStream = new ObjectInputStream(fileStream);
			// Read the object
			SecretKey key = (SecretKey)inputStream.readObject();
			inputStream.close();
			// set key
			this.setSecretkey(key);
    	} else {
			generateKey();
			// want to save key to file now. as an object, not as textual data. use ObjectOutputStream/ObjectInputStream
			// from https://www.programiz.com/java-programming/objectoutputstream:
			// Creates a FileOutputStream where objects from ObjectOutputStream are written
			FileOutputStream fileStream = new FileOutputStream(keyFileName);
			ObjectOutputStream objStream = new ObjectOutputStream(fileStream);
			// write object to output stream
			objStream.writeObject(this.getSecretkey());
			objStream.close();
    	}
    }
    
    
    /**
	* Step 1. Generate a DES key using KeyGenerator 
    */
    
    public void generateKey() throws NoSuchAlgorithmException 
    {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        this.setSecretkey(keyGen.generateKey());        
    }
    
    public byte[] encrypt (String strDataToEncrypt) throws 
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
            InvalidAlgorithmParameterException, IllegalBlockSizeException, 
            BadPaddingException
    {
        Cipher desCipher = Cipher.getInstance("DES"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!
        desCipher.init(Cipher.ENCRYPT_MODE, this.getSecretkey());
        byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
        byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt);       
        return byteCipherText;
    }
    
    public String decrypt (byte[] strCipherText) throws 
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
            InvalidAlgorithmParameterException, IllegalBlockSizeException, 
            BadPaddingException
    {        
        Cipher desCipher = Cipher.getInstance("DES"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!				
        desCipher.init(Cipher.DECRYPT_MODE, this.getSecretkey());        
        byte[] byteDecryptedText = desCipher.doFinal(strCipherText);        
        return new String(byteDecryptedText);
    }   

    /**
     * @return the secretkey
     */
    public SecretKey getSecretkey() {
        return secretkey;
    }

    /**
     * @param secretkey the secretkey to set
     */
    public void setSecretkey(SecretKey secretkey) {
        this.secretkey = secretkey;
    }
}
