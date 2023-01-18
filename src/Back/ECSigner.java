package Back;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.Enumeration;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to design documents with the DSA on elliptic curves
 */

public class ECSigner {
        // Installation of the BouncyCastle provider
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static class ECKeyPairGenerator {
 // Generate the keys 
        private final KeyPairGenerator kpg;
        /**
         * Construction of an instance of the classe
         * @param curveName the officiel name of the curve (NIST) used 
         * @throws GeneralSecurityException if we can't build the curve
         */
        public ECKeyPairGenerator(String curveName) throws GeneralSecurityException {
            this.kpg = KeyPairGenerator.getInstance("DSA");//("DSA", "SUN")
            kpg.initialize(new ECGenParameterSpec(curveName));
        }
        /**
         * Generation of the key for the DSA algorithme
         */
        public KeyPair getECKeyPair() {
            return kpg.generateKeyPair();
        }
        /**
         * Methode  to read the implemanted curve
         * @return a list of all the officiel name of the elliptical curves implemanted 
         */
        public static String getCurvesNames() {
            StringBuilder sb = new StringBuilder();
            for(Enumeration<String> curves = ECNamedCurveTable.getNames(); curves.hasMoreElements();){
                sb.append(curves.nextElement()).append('\n');
            }
            return sb.toString();
        }
    }
    private final Signature signer;
    /**
     * Construction of an instance of the  classe
     * @param algorithm the algorithm to use
     * @throws GeneralSecurityException if we can't construct the signant object 
     */
    public ECSigner(String algorithm) throws GeneralSecurityException {
        this.signer = Signature.getInstance(algorithm);//("SHA1withDSA", "SUN")
    }
        /**
     * Calculate the signature of the file given
     * @param file file to sign
     * @param privateKey private key to initialize the signature
     * @return signature in base64
     * @throws GeneralSecurityException if the signature calculate doesn't work 
     * @throws IOException if we can't read the file 
     */
    public String signFile(File file, PrivateKey privateKey)
            throws GeneralSecurityException, IOException {
        signer.initSign(privateKey);
        // input float
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        // reading buff
        byte[] buffer = new byte[1024];
        //nbr of octets red
        int nl;
        // calculate the signature
        while((nl = in.read(buffer)) != -1)
            //reset 
            signer.update(buffer, 0, nl);
        return Base64.encodeBase64String(signer.sign());
    }
        /**
     * Calculate  the signature  of the file
     * @param fileName name  of the file 
     * @param privateKey private key to initialise the  signature
     * @return signature in base64
     * @throws GeneralSecurityException if the signature calculate dosen't work
     * @throws IOException reading the file dosn't work
     */
    public String signFile(String fileName, PrivateKey privateKey)
            throws GeneralSecurityException, IOException {
        return signFile(new File(fileName), privateKey);
    }


    /**
     * Vérification of the file's signature
     * @param file file to verify
     * @param publicKey public key to initialse the verification
     * @param tagB64 the 64Base of signature to verify
     * @return <code>true</code> if the signature is correcte or <code>false</code> 
     * @throws GeneralSecurityException  if the verification doesn't work
     * @throws IOException if we can't read the file 
     */

    public boolean verifyFile(File file, PublicKey publicKey, String tagB64)
            throws GeneralSecurityException, IOException {
        signer.initVerify(publicKey);
        //input float
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        // buffer for reading 
        byte[] buffer = new byte[1024];
        //nbr of octets red
        int nl;
        //verify the signature
        while((nl = in.read(buffer)) != -1)
        // reset the signature 
            signer.update(buffer, 0, nl);
        return signer.verify(Base64.decodeBase64(tagB64));
    }

    /**
     * verify the signature of the file 
     * @param fileName file name to verify
     * @param publicKey the public key to initialse the  verification 
     * @param tagB64 Base64 of the signature to verify
     * @return <code>true</code> if the signature is correct <code>false</code> 
     * @throws GeneralSecurityException  verify if signature dosn't work
     * @throws IOException if we can't read the file
     */
    public boolean verifyFile(String fileName, PublicKey publicKey, String tagB64)
            throws GeneralSecurityException, IOException {
        return verifyFile(new File(fileName), publicKey, tagB64);
    }
}




