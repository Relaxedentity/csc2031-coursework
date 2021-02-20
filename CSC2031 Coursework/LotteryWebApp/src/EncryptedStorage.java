import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

public class EncryptedStorage {
    private Cipher cipher;
    private KeyPair pair;

    public EncryptedStorage(){}

    public byte[] encryptData(String data){

        try{
            //Instantiate Key pair
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            this.pair = keyPairGen.generateKeyPair();
            PublicKey publicKey = this.pair.getPublic();
            this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //Encrypt the data
            this.cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            this.cipher.update(data.getBytes());
            return this.cipher.doFinal();
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex){
            ex.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void bytesFileWriter(String filename, byte[] data){
        try {
            //Save bytes to file
            OutputStream os = new FileOutputStream(filename);
            os.write(data);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] bytesFileReader(String filename){
        try {
            //Read Bytes from file
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decryptData(byte[] data) {
        try {
            //decrypt the data received
            this.cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
            byte[] decipheredText = this.cipher.doFinal(data);
            return decipheredText;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return data;
    }
}
