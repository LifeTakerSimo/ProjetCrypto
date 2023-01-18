package Back;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.time.Duration;
import java.time.Instant;

public class SignatureElec {
    private double signDebit;
    private double verifyDebit;

    public byte[] sign(String fileName, PrivateKey privateKey) throws Exception {
        Instant start = Instant.now();
        byte[] data = Files.readAllBytes(new File(fileName).toPath());

        // Initialize the signature object with the private key and the algorithm
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initSign(privateKey);

        // Update the signature object with the data
        signature.update(data);

        // Generate the signature
        byte[] sign = signature.sign();
        Instant end = Instant.now();
        double time = Duration.between(start, end).toMillis();
        FileChannel fileChannel;
        fileChannel = FileChannel.open(Path.of("Data/TestData.txt"));
        long fileSize = fileChannel.size()/1000;
        fileChannel.close();
        signDebit = (double) fileSize / time;
        return sign;
    }

    public boolean verify(String fileName, PublicKey publicKey, byte[] signatureBytes) throws Exception {
        Instant start = Instant.now();
        // Read the input file
        byte[] data = Files.readAllBytes(new File(fileName).toPath());

        // Initialize the signature object with the public key and the algorithm
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(publicKey);

        // Update the signature object with the data
        signature.update(data);

        boolean verify = signature.verify(signatureBytes);
        Instant end = Instant.now();
        double time = Duration.between(start, end).toMillis();
        FileChannel fileChannel;
        fileChannel = FileChannel.open(Path.of("Data/TestData.txt"));
        long fileSize = fileChannel.size()/1000;
        fileChannel.close();
        verifyDebit = (double) fileSize / time;
        return verify;
    }
    public double signVerify() throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();

        // Sign a file
        byte[] signatureBytes = this.sign("Data/TestData.txt", keyPair.getPrivate());

        // Save the signature to a file
        FileOutputStream signatureOut = new FileOutputStream("example.txt.sig");
        signatureOut.write(signatureBytes);
        signatureOut.close();

        // Verify the signature
        boolean isValid = this.verify("Data/TestData.txt", keyPair.getPublic(), signatureBytes);
        return signDebit + verifyDebit;
    }

}
