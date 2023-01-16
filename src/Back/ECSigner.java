
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

public class ECSigner {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

   
    public static class ECKeyPairGenerator {
        private final KeyPairGenerator kpg;
        public ECKeyPairGenerator(String curveName) throws GeneralSecurityException {
            this.kpg = KeyPairGenerator.getInstance("DSA");//("DSA", "SUN")
            kpg.initialize(new ECGenParameterSpec(curveName));
        }

        public KeyPair getECKeyPair() {
            return kpg.generateKeyPair();
        }
        public static String getCurvesNames() {
            StringBuilder sb = new StringBuilder();
            for(Enumeration<String> curves = ECNamedCurveTable.getNames(); curves.hasMoreElements();){
                sb.append(curves.nextElement()).append('\n');
            }
            return sb.toString();
        }
    }
    private final Signature signer;

    public ECSigner(String algorithm) throws GeneralSecurityException {
        this.signer = Signature.getInstance(algorithm);//("SHA1withDSA", "SUN")
    }
    public String signFile(File file, PrivateKey privateKey)
            throws GeneralSecurityException, IOException {
        signer.initSign(privateKey);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[1024];
        int nl;
        while((nl = in.read(buffer)) != -1)
            signer.update(buffer, 0, nl);
        return Base64.encodeBase64String(signer.sign());
    }
    public String signFile(String fileName, PrivateKey privateKey)
            throws GeneralSecurityException, IOException {
        return signFile(new File(fileName), privateKey);
    }
    public boolean verifyFile(File file, PublicKey publicKey, String tagB64)
            throws GeneralSecurityException, IOException {
        signer.initVerify(publicKey);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[1024];
        int nl;
        while((nl = in.read(buffer)) != -1)
            signer.update(buffer, 0, nl);
        return signer.verify(Base64.decodeBase64(tagB64));
    }


    public boolean verifyFile(String fileName, PublicKey publicKey, String tagB64)
            throws GeneralSecurityException, IOException {
        return verifyFile(new File(fileName), publicKey, tagB64);
    }
}




