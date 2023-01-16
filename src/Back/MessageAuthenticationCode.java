package Back;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.SecureRandom;

public class MessageAuthenticationCode {


    public static void main(String args[]) throws Exception{


        //Creating a KeyGenerator object
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        //Creating a SecureRandom object
        SecureRandom secRandom = new SecureRandom();
        //Initializing the KeyGenerator
        keyGen.init(secRandom);
        //Creating/Generating a key
        Key key = keyGen.generateKey();
        //Creating a Mac object
        Mac mac = Mac.getInstance("HmacSHA256");
        //Initializing the Mac object
        mac.init(key);

        //Computing the Mac
        Path path = Paths.get("Data/TestData");
        byte[] fileContent = Files.readAllBytes(path);
        byte[] macResult = mac.doFinal(fileContent);


        System.out.println("Mac result:");
        System.out.println(new String(macResult));

    }
}
