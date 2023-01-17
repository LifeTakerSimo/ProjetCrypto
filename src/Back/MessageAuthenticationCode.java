package Back;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.SecureRandom;

public class MessageAuthenticationCode {


    public static byte[] Mac(Key key, String file) throws Exception{
        //Converting the file data into a byte array
        Path path = Paths.get("Data/" + file);
        byte[] data = Files.readAllBytes(path);

        Mac senderMac = Mac.getInstance("HmacSHA256"); //Getting the provider implementation of the required algorithm
        senderMac.init(key); //Initializing the algorithm with the key

        byte[] mac = senderMac.doFinal(data);

        return mac;
    }

    public static byte[] CalculatedMac(Key key, String file) throws Exception {
        //Converting the file data into a byte array
        Path path = Paths.get("Data/" + file);
        byte[] data = Files.readAllBytes(path);

        Mac receiverMac = Mac.getInstance("HmacSHA256");
        receiverMac.init(key);

        byte[] calculatedMac = receiverMac.doFinal(data);

        //Path path2 = Paths.get("Data/DoviVerification.txt");
        //Files.write(path2, calculatedMac);

        return calculatedMac;
    }

     public static boolean IsAuthentic(Key key, String file) throws Exception {

         byte [] mac = Mac(key, file);
         byte [] calculatedMac = CalculatedMac(key, file);

         for (int i = 0; i < calculatedMac.length; i++) {
             if (calculatedMac[i] != mac[i]){
                 System.out.println("Attention ! The message has been modified before arriving to you !");
                 return false;
             }
         }
         System.out.println("The message is authentic !");
         return true;
     }

     public static long MacPerf() throws Exception {

         String file = "TestData.txt";
         KeyGenerator keyGenerator = KeyGenerator.getInstance("DES"); //Creating a KeyGenerator object
         SecureRandom secRandom = new SecureRandom(); //Creating a SecureRandom object
         keyGenerator.init(secRandom); //Initializing the KeyGenerator
         Key key = keyGenerator.generateKey(); //Creating/Generating a key

         //Getting the size of the file
         FileChannel fileChannel;
         fileChannel = FileChannel.open(Path.of("Data/TestData.txt"));
         long fileSize = fileChannel.size()/1000;
         fileChannel.close();

         // Authenticate the file
         long startAuthTime = System.nanoTime();
         MessageAuthenticationCode.IsAuthentic(key, file);
         long authTime = (System.nanoTime() - startAuthTime)/1000000;

         //Performance
         long authPerf = fileSize/authTime;
         System.out.println(authPerf + " kilobytes/ms");

         return authPerf;
     }
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES"); //Creating a KeyGenerator object
        SecureRandom secRandom = new SecureRandom(); //Creating a SecureRandom object
        keyGenerator.init(secRandom); //Initializing the KeyGenerator
        Key key = keyGenerator.generateKey(); //Creating/Generating a key

        IsAuthentic(key, "TestData.txt");

        MacPerf();
    }
}
