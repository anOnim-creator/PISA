import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class Cipher {
    private LowCipher lowCipher;

    public Cipher() {
        try {
            lowCipher = new LowCipher();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            //e.printStackTrace();
        }
    }

    public byte[] encrypt(String msg, PublicKey publicKey) {
        try {
            return lowCipher.encryptText(msg, publicKey);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] bytes, PrivateKey privateKey) {
        try {
            return lowCipher.decryptText(bytes, privateKey);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public PublicKey getPublicMyKey() {
        return this.lowCipher.getPublicMyKey();
    }

    public void setPublicMyKey(PublicKey publicMyKey) {
        this.lowCipher.setPublicMyKey(publicMyKey);
    }

    public PrivateKey getPrivateMyKey() {
        return this.lowCipher.getPrivateMyKey();
    }

    public PublicKey getPublicKey() {
        return this.lowCipher.getPublicKey();
    }

    public void setPublicKey(PublicKey publicKey) {
        this.lowCipher.setPublicKey(publicKey);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class LowCipher {
        private javax.crypto.Cipher cipher;
        private KeyPairGenerator keyGen;

        private PublicKey publicMyKey;
        private PrivateKey privateMyKey;
        private PublicKey publicKey;

        public LowCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
            setCipher("RSA");
            generateKeys(2048);
            createKeys();
        }

        public void setCipher(String cipherT) throws NoSuchPaddingException, NoSuchAlgorithmException {
            if (cipherT == null || cipherT.equals("")) cipherT = "RSA";
            cipher = javax.crypto.Cipher.getInstance(cipherT);
        }

        public void generateKeys(int keyLength) throws NoSuchAlgorithmException {
            if (keyLength <= 2048) keyLength = 2048;
            this.keyGen = KeyPairGenerator.getInstance(cipher.getAlgorithm());
            this.keyGen.initialize(keyLength);
        }

        public void createKeys() {
            KeyPair pair = this.keyGen.generateKeyPair();
            this.privateMyKey = pair.getPrivate();
            this.publicMyKey = pair.getPublic();
        }

        public byte[] encryptText(String msg, PublicKey publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        }

        public String decryptText(byte[] bytes, PrivateKey key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
        }

        public PublicKey getPublicMyKey() {
            return publicMyKey;
        }

        public void setPublicMyKey(PublicKey publicMyKey) {
            this.publicMyKey = publicMyKey;
        }

        public PrivateKey getPrivateMyKey() {
            return privateMyKey;
        }

        public PublicKey getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
        }
    }
}