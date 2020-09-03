import java.io.Serializable;
import java.security.*;

public class Message implements Serializable {
    private int hash;
    private byte[] encryptMessage;
    private String decryptMessage;

    public Message(String msg){
        decryptMessage = msg.substring(0, Math.min(msg.length(), 245));
    }

    public void decrypt(Cipher cipher, PrivateKey privateKey){
        decryptMessage = cipher.decrypt(encryptMessage, privateKey);
    }
    public void encrypt(Cipher cipher, PublicKey publicKey){
        encryptMessage = cipher.encrypt(decryptMessage, publicKey);
        hash = decryptMessage.hashCode();
        decryptMessage = null;
    }
    public boolean checkHash(){
        return (hash == decryptMessage.hashCode());
    }
    @Override
    public String toString() {
        return decryptMessage;
    }
}