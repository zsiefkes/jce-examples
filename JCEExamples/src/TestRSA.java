/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ahmed
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;


public class TestRSA {
    
    public static void main(String[] args) throws Exception {
		RSA rsa;
		try {
			rsa = new RSA(1024);
			rsa.createKeys();
			rsa.writeKeyToFile("KeyPair/publicKey", rsa.getPublicKey().getEncoded());
			rsa.writeKeyToFile("KeyPair/privateKey", rsa.getPrivateKey().getEncoded());
                        
                        PrivateKey privateKey = rsa.getPrivate("KeyPair/privateKey");
                        PublicKey publicKey = rsa.getPublic("KeyPair/publicKey");
                        
                        if (new File("KeyPair/text.txt").exists()) 
                        {
                            rsa.encryptFile(rsa.getFileInBytes(new File("KeyPair/text.txt")), 
				new File("KeyPair/text_encrypted.txt"),privateKey);
                            rsa.decryptFile(rsa.getFileInBytes(new File("KeyPair/text_encrypted.txt")),
				new File("KeyPair/text_decrypted.txt"), publicKey);
                        } else 
                        {
                            System.out.println("Create a file text.txt under folder KeyPair");
                        }
                        
                        
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}
    
}
