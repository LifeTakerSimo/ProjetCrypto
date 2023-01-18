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

/**
 * This class implements a Message Authentication Code. The javax.crypto.Mac library is used to access MAC
 * algorithms which are used to check the integrity of a received message.
 *
 * @author Dovi Kinde (dovi.kinde@uha.fr)
 */

public class MessageAuthenticationCode {

    /**
     * Process the message and provide the MAC for the message sent by the sender.
     *
     * @param key           The shared secret key
     * @param message       The message sent
     * @return              A MAC for the message
     * @throws Exception    Exceptions
     */

    public static byte[] Mac(Key key, String message) throws Exception {
        //Converts the file data into a byte array
        Path path = Paths.get("Data/" + message);
        byte[] data = Files.readAllBytes(path);

        //Sends the message to the received
        Path path1 = Paths.get("Data/receivedMessage.txt");
        Files.deleteIfExists(path1);
        Files.write(path1, data);

        Mac senderMac = Mac.getInstance("HmacSHA256"); //Get the provider implementation of the required algorithm
        senderMac.init(key); //Initialize the algorithm with the key

        byte[] mac = senderMac.doFinal(data);

        return mac;
    }

    /**
     * Calculate the MAC for the message received by the receiver.
     *
     * @param key           The shared secret key
     * @param message       The message received
     * @return              The calculated MAC from the received message
     * @throws Exception    Exceptions
     */

    public static byte[] CalculatedMac(Key key, String message) throws Exception {
        //Converting the file data into a byte array
        Path path = Paths.get("Data/" + message);
        byte[] data = Files.readAllBytes(path);

        Mac receiverMac = Mac.getInstance("HmacSHA256");
        receiverMac.init(key);

        byte[] calculatedMac = receiverMac.doFinal(data);

        return calculatedMac;
    }

    /**
     * Check if the provided MAC and the calculated one are the same.
     *
     * @param sentMessage       The message sent
     * @param receivedMessage   The message received
     * @return
     * @throws Exception Exceptions
     */

    public static boolean IsAuthentic(String sentMessage, String receivedMessage) throws Exception {

        //Generates the key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES"); //Creates a KeyGenerator object
        SecureRandom secRandom = new SecureRandom(); //Creates a SecureRandom object
        keyGenerator.init(secRandom); //Initializes the KeyGenerator
        Key key = keyGenerator.generateKey(); //Generates a key

        byte [] mac = Mac(key, sentMessage);
        byte [] calculatedMac = CalculatedMac(key, receivedMessage);

        for (int i = 0; i < calculatedMac.length; i++) {
            if (calculatedMac[i] != mac[i]){
                System.out.println("Attention ! The message has been modified before arriving to you !");
                return false;
            }
        }

        System.out.println("The message is authentic !");
        return true;
    }

    /**
     * Calculates the performance of the authentication.
     *
     * @return              The execution speed in kilobytes/ms
     * @throws Exception    Exceptions
     */
    public static long MacPerformance() throws Exception{

        String sentFile = "TestData.txt";
        String receivedFile = "receivedFile.txt";
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
        MessageAuthenticationCode.IsAuthentic(sentFile, receivedFile);
        long authTime = (System.nanoTime() - startAuthTime)/1000000;

        //Performance
        long authPerf = fileSize/authTime;
        System.out.println(authPerf + " kilobytes/ms");

        return authPerf;
    }
}