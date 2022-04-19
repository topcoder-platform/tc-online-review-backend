package com.topcoder.shared.netCommon;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;

public class MessageEncryptionHandler {
    private byte[] requestKey;
    private byte[] replyKey;
    private static final KeyGenerator keygen;
    private static final Cipher cipher;

    public static Object unsealObject(SealedSerializable obj, Key key) throws GeneralSecurityException {
        try {
            synchronized (cipher) {
                cipher.init(2, key);
                return obj.getObject(cipher);
            }
        } catch (ClassNotFoundException var5) {
            throw new GeneralSecurityException("Decryption failed.", var5);
        } catch (IOException var6) {
            throw new GeneralSecurityException("Decryption failed.", var6);
        }
    }

    public static SealedSerializable sealObject(Object obj, Key key) throws GeneralSecurityException {
        try {
            synchronized (cipher) {
                cipher.init(1, key);
                return new SealedSerializable(obj, cipher);
            }
        } catch (IOException var5) {
            throw new GeneralSecurityException("Encryption failed.", var5);
        }
    }

    public MessageEncryptionHandler() {
    }

    private byte[] generateKey() {
        synchronized (keygen) {
            return keygen.generateKey().getEncoded();
        }
    }

    public byte[] generateRequestKey() {
        this.requestKey = this.generateKey();
        return this.requestKey;
    }

    public byte[] generateReplyKey() {
        this.replyKey = this.generateKey();
        return this.replyKey;
    }

    public void setRequestKey(byte[] key) {
        if (key.length != 16) {
            throw new IllegalArgumentException("The request key must be 16 bytes.");
        } else {
            this.requestKey = key;
        }
    }

    public void setReplyKey(byte[] key) {
        if (key.length != 16) {
            throw new IllegalArgumentException("The reply key must be 16 bytes.");
        } else {
            this.replyKey = key;
        }
    }

    public Key getFinalKey() {
        if (this.requestKey != null && this.replyKey != null) {
            byte[] key = new byte[16];

            for (int i = 0; i < 16; ++i) {
                key[i] = (byte) (this.requestKey[i] + this.replyKey[i]);
            }

            return new SecretKeySpec(key, "AES");
        } else {
            throw new IllegalStateException("Either request key or reply key is missing.");
        }
    }

    public boolean isKeyFinal() {
        return this.requestKey != null && this.replyKey != null;
    }

    static {
        try {
            keygen = KeyGenerator.getInstance("AES");
            keygen.init(128);
            cipher = Cipher.getInstance("AES");
        } catch (GeneralSecurityException var1) {
            throw new UnsupportedOperationException("AES 128-bit is not supported by Java.", var1);
        }
    }
}
