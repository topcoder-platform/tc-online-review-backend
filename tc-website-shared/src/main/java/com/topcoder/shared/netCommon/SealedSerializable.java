package com.topcoder.shared.netCommon;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;

public class SealedSerializable implements CustomSerializable, Serializable {
    private byte[] encoded;

    public SealedSerializable() {
    }

    public SealedSerializable(Object obj, Cipher cipher) throws IllegalBlockSizeException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        try {
            oos.writeObject(obj);
            this.encoded = cipher.doFinal(baos.toByteArray());
        } catch (BadPaddingException var9) {
        } finally {
            oos.close();
        }

    }

    public Object getObject(Cipher cipher) throws IllegalBlockSizeException, BadPaddingException, ClassNotFoundException, IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(cipher.doFinal(this.encoded));
        ObjectInputStream ois = new ObjectInputStream(bais);

        Object var4;
        try {
            var4 = ois.readObject();
        } finally {
            ois.close();
        }

        return var4;
    }

    public void customWriteObject(CSWriter writer) throws IOException {
        writer.writeByteArray(this.encoded);
    }

    public void customReadObject(CSReader reader) throws IOException, ObjectStreamException {
        this.encoded = reader.readByteArray();
    }
}
