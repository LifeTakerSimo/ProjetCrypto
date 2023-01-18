package Back;

import javax.crypto.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class EncryptPublicKey {

    /**
     *
     * RSA public key
     * @param keysize
     * @return public key
     * @throws NoSuchAlgorithmException
     */

    public static PublicKey getPublicRSAKey(int keysize) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Obtenir une instance de KeyPairGenerator spécialisée pour les paires de clés RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // Initialiser le générateur
        kpg.initialize(2048);
        // Générer la paire de clés
        KeyPair kp = kpg.generateKeyPair();

        // Récupérer l'encodage (format X.509) de la clé publique
        byte[] ePubKey = kp.getPublic().getEncoded();

        // Obtenir une instance de KeyFactory spécialisée pour les clés RSA
        KeyFactory kf = KeyFactory.getInstance("RSA");
        // Reconstruire la clé publique depuis son encodage X.509: ePubKey
        RSAPublicKey pubKey1 =
                (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(ePubKey));
        return pubKey1;
    }

    /**
     *
     * RSA private key
     * @return private key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */

    public static PrivateKey getPrivateRSAKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Obtenir une instance de KeyPairGenerator spécialisée pour les paires de clés RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // Initialiser le générateur
        kpg.initialize(2048);
        // Générer la paire de clés
        KeyPair kp = kpg.generateKeyPair();

        // Récupérer l'encodage (format PKCS#8) de la clé privée
        byte[] ePriKey = kp.getPrivate().getEncoded();

        // Obtenir une instance de KeyFactory spécialisée pour les clés RSA
        KeyFactory kf = KeyFactory.getInstance("RSA");
        // Reconstruire la clé privée depuis son encodage PKCS#8 : ePriKey
        RSAPrivateKey priKey1 =
                (RSAPrivateKey)kf.generatePrivate(new PKCS8EncodedKeySpec(ePriKey));
        return priKey1;
    }

    /**
     * @return double
     */

    public static double encryptDecrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {
        // Coté "émetteur"
        // kPub est la clé publique du destinataire
        // Obtention d'une instance du RSA en mode ECB
        // (suffisant car un seul bloc sera transféré et la même clé
        // de session ne devrait jamais être utilisée deux fois)
        Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Initialisation du chiffreur en mode "WRAPPING"
        // kPub est la clé publique du récepteur
        PublicKey kPub = getPublicRSAKey(256);
        rsa.init(Cipher.WRAP_MODE, kPub);
        // Génération, sous forme opaque d'une clé secrète pour RC4
        KeyGenerator kg = KeyGenerator.getInstance("RC4");
        kg.init(128);
        SecretKey key = kg.generateKey();
        //Encapsulation de la clé
        byte[] km = rsa.wrap(key);

        // Coté "récepteur"
        // kPri est la clé privée du récepteur
        // Obtention d'une instance du RSA en mode ECB
        // (suffisant car un seul bloc a été transféré)
        Cipher rsa1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Initialisation du déchiffreur en mode "UNWRAPPING"
        // kPri est la clé privée du récepteur
        PrivateKey kPri = getPrivateRSAKey();
        rsa1.init(Cipher.UNWRAP_MODE, kPri);
        // Récupération de la clé
        Key k = rsa1.unwrap(km, "RC4", Cipher.SECRET_KEY);
        return 0;
    }

}


