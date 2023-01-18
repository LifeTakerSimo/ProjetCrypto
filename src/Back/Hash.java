package Back;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Hash {

    private double hashDebit;
    private double verifyDebit;

    public byte[] hashFile(Path file, String algorithm) throws IOException {
        Instant start = Instant.now();
        start = start.truncatedTo(ChronoUnit.MILLIS);

        byte[] fileBytes = null;
        try {
            fileBytes = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(fileBytes);
        byte[] digest = md.digest();
        Instant end = Instant.now();
        end = end.truncatedTo(ChronoUnit.MILLIS);

        double time = Duration.between(start, end).toMillis();
        FileChannel fileChannel;
        fileChannel = FileChannel.open(Path.of("Data/TestData.txt"));
        long fileSize = fileChannel.size()/1000;
        fileChannel.close();
        hashDebit = (double) fileSize / time;
        return digest;
    }

    public boolean verifyHash(Path file, byte[] hash, String algorithm) throws IOException {
        Instant start = Instant.now();
        start = start.truncatedTo(ChronoUnit.MILLIS);
        byte[] fileHash = hashFile(file, algorithm);
        boolean isEqual = MessageDigest.isEqual(fileHash, hash);
        Instant end = Instant.now();
        end = end.truncatedTo(ChronoUnit.MILLIS);

        double time = Duration.between(start, end).toMillis();
        FileChannel fileChannel;
        fileChannel = FileChannel.open(Path.of("Data/TestData.txt"));
        long fileSize = fileChannel.size()/1000;
        fileChannel.close();
        verifyDebit = (double) fileSize / time;
        return isEqual;
    }

    private double getVerifyDebit() {
        return verifyDebit;
    }

    private double getHashDebit() {
        return hashDebit;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
    /**
     * Hash class that contains two methods one that hashes the file
     * and another one that verifies the hash during these operations
     * a debit is calculated in Kilobytes per millisecond
     */
    public double hashVerify() throws IOException {
        Path file = Path.of("Data/TestData.txt"); // file path
        String algorithm = "SHA-256";
        byte[] hash = this.hashFile(file, algorithm);
        if (hash != null) {
            boolean isValid = this.verifyHash(file, hash, algorithm);
        }
        return this.hashDebit+ this.verifyDebit;
    }

}
