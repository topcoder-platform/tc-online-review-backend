package com.topcoder.shared.netCommon;

import com.topcoder.shared.netCommon.customserializer.CustomSerializer;
import com.topcoder.shared.netCommon.customserializer.CustomSerializerProvider;
import com.topcoder.shared.netCommon.customserializer.NullCustomSerializerProvider;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.*;
import java.util.Map.Entry;

public abstract class CSHandler implements CSReader, CSWriter {
    private static final byte NULL = 1;
    private static final byte STRING = 2;
    private static final byte BOOLEAN = 3;
    private static final byte INTEGER = 4;
    private static final byte CHAR_ARRAY = 5;
    private static final byte DOUBLE = 6;
    private static final byte BYTE = 7;
    private static final byte BYTE_ARRAY = 8;
    private static final byte OBJECT_ARRAY = 9;
    private static final byte INT_ARRAY = 10;
    private static final byte CHAR = 11;
    private static final byte STRING_ARRAY = 12;
    private static final byte LONG = 13;
    private static final byte OBJECT_ARRAY_ARRAY = 14;
    private static final byte DOUBLE_ARRAY = 16;
    private static final byte CLASS = 17;
    private static final byte DOUBLE_ARRAY_ARRAY = 18;
    private static final byte LONG_ARRAY = 19;
    private static final byte ARRAY_LIST = 33;
    private static final byte HASH_MAP = 34;
    private static final byte LIST = 35;
    private static final byte MAP = 36;
    private static final byte COLLECTION = 37;
    private static final byte CUSTOM_SERIALIZABLE = 65;
    private static final byte CUSTOM_SERIALIZER = 64;
    private CustomSerializerProvider customSerializer;
    private Cipher encryptor;
    private Cipher decryptor;

    public CSHandler() {
        this((CustomSerializerProvider) (new NullCustomSerializerProvider()));
    }

    public CSHandler(CustomSerializerProvider customSerializer) {
        this(customSerializer, (Key) null);
    }

    public CSHandler(Key key) {
        this(new NullCustomSerializerProvider(), key);
    }

    public CSHandler(CustomSerializerProvider customSerializer, Key key) {
        this.customSerializer = customSerializer;
        if (key != null) {
            try {
                this.encryptor = Cipher.getInstance(key.getAlgorithm());
                this.encryptor.init(1, key);
                this.decryptor = Cipher.getInstance(key.getAlgorithm());
                this.decryptor.init(2, key);
            } catch (GeneralSecurityException var4) {
                this.encryptor = null;
                this.decryptor = null;
            }
        }

    }

    public final void setMemoryUsageLimit(long limit) {
        this.resetMemoryUsage();

    }

    public final void resetMemoryUsage() {
//        this.input.resetMemoryUsageCounter();
    }

    public final void writeByte(byte b) throws IOException {
//        this.output.writeByte(b);
    }

    public final byte readByte() throws IOException {
        return 0;
    }

    private boolean isNull(byte expected) throws IOException {
        byte b = this.readByte();
        if (b == 1) {
            return true;
        } else if (b != expected) {
            throw new RuntimeException("unexpected, b=" + b + ", expected=" + expected);
        } else {
            return false;
        }
    }

    private boolean isNull(byte expected1, byte expected2) throws IOException {
        byte b = this.readByte();
        if (b == 1) {
            return true;
        } else if (b != expected1 && b != expected2) {
            throw new RuntimeException("unexpected, b=" + b + ", expected=" + expected1 + " or " + expected2);
        } else {
            return false;
        }
    }

    private boolean isNull(byte expected1, byte expected2, byte expected3) throws IOException {
        byte b = this.readByte();
        if (b == 1) {
            return true;
        } else if (b != expected1 && b != expected2 && b != expected3) {
            throw new RuntimeException("unexpected, b=" + b + ", expected=" + expected1 + " or " + expected2 + " or " + expected3);
        } else {
            return false;
        }
    }

    private void writeNull() throws IOException {
        this.writeByte((byte) 1);
    }

    public final short readShort() throws IOException {
        return 0;
    }

    public final void writeShort(short v) throws IOException {
//        this.output.writeShort(v);
    }

    public final int readInt() throws IOException {
        return 0;
    }

    public final void writeInt(int v) throws IOException {
//        this.output.writeInt(v);
    }

    public final long readLong() throws IOException {
        return 0;
    }

    public final void writeLong(long v) throws IOException {
//        this.output.writeLong(v);
    }

    public final boolean readBoolean() throws IOException {
        return false;
    }

    public final void writeBoolean(boolean v) throws IOException {
//        this.output.writeBoolean(v);
    }

    public final ArrayList readArrayList() throws IOException {
        return this.isNull((byte) 33, (byte) 35) ? null : this.readJustArrayList();
    }

    public final List readList(List list) throws IOException {
        return this.isNull((byte) 33, (byte) 35) ? null : (List) this.readJustCollection(list);
    }

    private ArrayList readJustArrayList() throws IOException {
        int size = this.readInt();
        ArrayList list = new ArrayList(size);

        for (int i = 0; i < size; ++i) {
            list.add(this.readObject());
        }

        return list;
    }

    public final Collection readCollection(Collection collection) throws IOException {
        return this.isNull((byte) 37, (byte) 35, (byte) 33) ? null : this.readJustCollection(collection);
    }

    public final void writeCollection(Collection collection) throws IOException {
        this.writeCollection(collection, (byte) 37);
    }

    private Collection readJustCollection(Collection list) throws IOException {
        int size = this.readInt();

        for (int i = 0; i < size; ++i) {
            list.add(this.readObject());
        }

        return list;
    }

    public final void writeArrayList(ArrayList list) throws IOException {
        this.writeCollection(list, (byte) 33);
    }

    public void writeList(List list) throws IOException {
        this.writeCollection(list, (byte) 35);
    }

    public void writeCollection(Collection list, byte type) throws IOException {
        if (list == null) {
            this.writeNull();
        } else {
            int size = list.size();
            this.writeByte(type);
            this.writeInt(size);

            try {
                Iterator it = list.iterator();

                while (it.hasNext()) {
                    this.writeObject(it.next());
                }
            } catch (ConcurrentModificationException var5) {
                this.throwConcurrentModificationException(var5, list);
            }

        }
    }

    private Object[] readJustObjectArray(Class clazz) throws IOException {
        int size = this.readInt();
        Object[] r = (Object[]) ((Object[]) Array.newInstance(clazz, size));

        for (int i = 0; i < size; ++i) {
            r[i] = this.readObject();
        }

        return r;
    }

    private Object[] readJustObjectArray() throws IOException {
        int size = this.readInt();
        Object[] r = new Object[size];

        for (int i = 0; i < size; ++i) {
            r[i] = this.readObject();
        }

        return r;
    }

    public final Object[] readObjectArray() throws IOException {
        return this.isNull((byte) 9) ? null : this.readJustObjectArray();
    }

    public final Object[] readObjectArray(Class clazz) throws IOException {
        return this.isNull((byte) 9) ? null : this.readJustObjectArray(clazz);
    }

    private void writeJustObjectArray(Object[] objectArray) throws IOException {
        int size = objectArray.length;
        this.writeInt(size);

        for (int i = 0; i < size; ++i) {
            this.writeObject(objectArray[i]);
        }

    }

    public final void writeObjectArray(Object[] objectArray) throws IOException {
        if (objectArray == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 9);
            this.writeJustObjectArray(objectArray);
        }
    }

    public final Object[][] readObjectArrayArray(Class clazz) throws IOException {
        if (this.isNull((byte) 14)) {
            return (Object[][]) null;
        } else {
            int size = this.readInt();
            Object[][] r = (Object[][]) ((Object[][]) Array.newInstance(clazz, new int[]{size, 0}));

            for (int i = 0; i < size; ++i) {
                r[i] = this.readJustObjectArray(clazz);
            }

            return r;
        }
    }

    public final Object[][] readObjectArrayArray() throws IOException {
        return this.isNull((byte) 14) ? (Object[][]) null : this.readJustObjectArrayArray();
    }

    private Object[][] readJustObjectArrayArray() throws IOException {
        int size = this.readInt();
        Object[][] r = new Object[size][];

        for (int i = 0; i < size; ++i) {
            r[i] = this.readJustObjectArray();
        }

        return r;
    }

    public final void writeObjectArrayArray(Object[][] objectArrayArray) throws IOException {
        if (objectArrayArray == null) {
            this.writeNull();
        } else {
            int size = objectArrayArray.length;
            this.writeByte((byte) 14);
            this.writeInt(size);

            for (int i = 0; i < size; ++i) {
                this.writeJustObjectArray(objectArrayArray[i]);
            }

        }
    }

    public final double[][] readDoubleArrayArray() throws IOException {
        return this.isNull((byte) 18) ? (double[][]) null : this.readJustDoubleArrayArray();
    }

    private double[][] readJustDoubleArrayArray() throws IOException {
        int size = this.readInt();
        double[][] r = new double[size][];

        for (int i = 0; i < size; ++i) {
            r[i] = this.readJustDoubleArray();
        }

        return r;
    }

    public final void writeDoubleArrayArray(double[][] doubleArrayArray) throws IOException {
        if (doubleArrayArray == null) {
            this.writeNull();
        } else {
            int size = doubleArrayArray.length;
            this.writeByte((byte) 18);
            this.writeInt(size);

            for (int i = 0; i < size; ++i) {
                this.writeJustDoubleArray(doubleArrayArray[i]);
            }

        }
    }

    private void writeJustDoubleArray(double[] doubleArray) throws IOException {
//        this.output.writeDoubleArray(doubleArray);
    }

    private int[] readJustIntArray() throws IOException {
        return new int[0];
    }

    private long[] readJustLongArray() throws IOException {
        return new long[0];
    }

    private void writeIntArray(int[] intArray) throws IOException {
        if (intArray == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 10);
//            this.output.writeIntArray(intArray);
        }
    }

    private void writeLongArray(long[] longArray) throws IOException {
        if (longArray == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 19);
//            this.output.writeLongArray(longArray);
        }
    }

    private double[] readJustDoubleArray() throws IOException {
        return new double[0];
    }

    private void writeDoubleArray(double[] doubleArray) throws IOException {
        if (doubleArray == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 16);
//            this.output.writeDoubleArray(doubleArray);
        }
    }

    private String[] readJustStringArray() throws IOException {
        return new String[0];
    }

    public String[] readStringArray() throws IOException {
        return this.isNull((byte) 12) ? null : this.readJustStringArray();
    }

    public void writeStringArray(String[] stringArray) throws IOException {
        if (stringArray == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 12);
//            this.output.writeStringArray(stringArray, (String)null);
        }
    }

    public final HashMap readHashMap() throws IOException {
        return this.isNull((byte) 34, (byte) 36) ? null : this.readJustHashMap();
    }

    public final Map readMap(Map map) throws IOException {
        return this.isNull((byte) 34, (byte) 36) ? null : this.readJustMap(map);
    }

    private HashMap readJustHashMap() throws IOException {
        int size = this.readInt();
        HashMap map = new HashMap(size);

        for (int i = 0; i < size; ++i) {
            Object key = this.readObject();
            Object value = this.readObject();
            map.put(key, value);
        }

        return map;
    }

    private Map readJustMap(Map map) throws IOException {
        int size = this.readInt();

        for (int i = 0; i < size; ++i) {
            Object key = this.readObject();
            Object value = this.readObject();
            map.put(key, value);
        }

        return map;
    }

    public final void writeHashMap(HashMap map) throws IOException {
        this.doWriteMap(map, (byte) 34);
    }

    public final void writeMap(Map map) throws IOException {
        this.doWriteMap(map, (byte) 36);
    }

    private void doWriteMap(Map map, byte type) throws IOException {
        if (map == null) {
            this.writeNull();
        } else {
            int size = map.size();
            this.writeByte(type);
            this.writeInt(size);

            try {
                Iterator it = map.entrySet().iterator();

                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    this.writeObject(entry.getKey());
                    this.writeObject(entry.getValue());
                }
            } catch (ConcurrentModificationException var6) {
                this.throwConcurrentModificationException(var6, map);
            }

        }
    }

    private void throwConcurrentModificationException(ConcurrentModificationException e, Object obj) {
        throw new ConcurrentModificationException(e + ", object=" + obj);
    }

    public String readUTF() throws IOException {
        return "";
    }

    public void writeUTF(String s) throws IOException {
//        this.output.writeString(s, (String)null);
    }

    public final String readString() throws IOException {
        return this.isNull((byte) 2) ? null : "";
    }

    public final void writeString(String string) throws IOException {
        if (string == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 2);
//            this.output.writeString(string, (String)null);
        }
    }

    private byte[] readJustByteArray() throws IOException {
        return new byte[0];
    }

    public final byte[] readByteArray() throws IOException {
        return this.isNull((byte) 8) ? null : this.readJustByteArray();
    }

    public final void writeByteArray(byte[] byteArray) throws IOException {
        if (byteArray == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 8);
//            this.output.writeByteArray(byteArray);
        }
    }

    public final char[] readCharArray() throws IOException {
        return this.isNull((byte) 5) ? null : this.readJustCharArray();
    }

    private char[] readJustCharArray() throws IOException {
        return new char[0];
    }

    public final void writeCharArray(char[] charArray) throws IOException {
        if (charArray == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 5);
//            this.output.writeCharArray(charArray);
        }
    }

    private char readChar() throws IOException {
        return 0;
    }

    private void writeChar(char c) throws IOException {
//        this.output.writeChar(c);
    }

    public double readDouble() throws IOException {
        return 0;
    }

    public void writeDouble(double v) throws IOException {
//        this.output.writeDouble(v);
    }

    public void writeClass(Class clazz) throws IOException {
        if (clazz == null) {
            this.writeNull();
        } else {
            this.writeByte((byte) 17);
            this.writeUTF(clazz.getName());
        }
    }

    public Class readClass() throws IOException {
        return this.isNull((byte) 17) ? null : this.readJustClass();
    }

    private Class readJustClass() throws IOException {
        String className = this.readUTF();

        try {
            return ClassCache.findClass(className);
        } catch (ClassNotFoundException var3) {
            throw new IllegalArgumentException(var3.getMessage());
        }
    }

    protected final void customWriteObject(Object object) throws IOException {
        ((CustomSerializable) object).customWriteObject(this);
    }

    protected abstract boolean writeObjectOverride(Object var1) throws IOException;

    public final void writeObject(Object object) throws IOException {
        if (object == null) {
            this.writeNull();
        } else {
            CustomSerializer serializer = this.customSerializer.getSerializer(object.getClass());
            if (serializer != null) {
                this.writeByte((byte) 64);
                this.writeUTF(object.getClass().getName());
                serializer.writeObject(this, object);
            } else if (!this.writeObjectOverride(object)) {
                if (object instanceof ArrayList) {
                    this.writeArrayList((ArrayList) object);
                } else if (object instanceof String) {
                    this.writeString((String) object);
                } else if (object instanceof Integer) {
                    this.writeByte((byte) 4);
                    this.writeInt((Integer) object);
                } else if (object instanceof HashMap) {
                    this.writeHashMap((HashMap) object);
                } else if (object instanceof Boolean) {
                    this.writeByte((byte) 3);
                    this.writeBoolean((Boolean) object);
                } else if (object instanceof Byte) {
                    this.writeByte((byte) 7);
                    this.writeByte((Byte) object);
                } else if (object instanceof Long) {
                    this.writeByte((byte) 13);
                    this.writeLong((Long) object);
                } else if (object instanceof Character) {
                    this.writeByte((byte) 11);
                    this.writeChar((Character) object);
                } else if (object instanceof Double) {
                    this.writeByte((byte) 6);
                    this.writeDouble((Double) object);
                } else if (object instanceof char[]) {
                    this.writeCharArray((char[]) ((char[]) object));
                } else if (object instanceof int[]) {
                    this.writeIntArray((int[]) ((int[]) object));
                } else if (object instanceof long[]) {
                    this.writeLongArray((long[]) ((long[]) object));
                } else if (object instanceof double[]) {
                    this.writeDoubleArray((double[]) ((double[]) object));
                } else if (object instanceof double[][]) {
                    this.writeDoubleArrayArray((double[][]) ((double[][]) object));
                } else if (object instanceof String[]) {
                    this.writeStringArray((String[]) ((String[]) object));
                } else if (object instanceof byte[]) {
                    this.writeByteArray((byte[]) ((byte[]) object));
                } else if (object instanceof CustomSerializable) {
                    this.writeByte((byte) 65);
                    this.writeUTF(object.getClass().getName());
                    this.customWriteObject(object);
                } else if (object instanceof Object[][]) {
                    this.writeObjectArrayArray((Object[][]) ((Object[][]) object));
                } else if (object instanceof Object[]) {
                    this.writeObjectArray((Object[]) ((Object[]) object));
                } else if (object instanceof Class) {
                    this.writeClass((Class) object);
                } else if (object instanceof List) {
                    this.writeList((List) object);
                } else if (object instanceof Map) {
                    this.writeMap((Map) object);
                } else if (object instanceof Collection) {
                    this.writeCollection((Collection) object);
                } else {
                    this.writeUnhandledObject(object);
                }

            }
        }
    }

    protected void writeUnhandledObject(Object object) throws IOException {
        throw new RuntimeException("writeBaseObject, not implemented: " + object.getClass());
    }

    private static Boolean booleanValueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    public final Object readObject() throws IOException {
        Class clazz = null;
        byte type = this.readByte();
        switch (type) {
            case 1:
                return null;
            case 2:
                return this.readUTF();
            case 3:
                return booleanValueOf(this.readBoolean());
            case 4:
                return new Integer(this.readInt());
            case 5:
                return this.readJustCharArray();
            case 6:
                return new Double(this.readDouble());
            case 7:
                return new Byte(this.readByte());
            case 8:
                return this.readJustByteArray();
            case 9:
                return this.readJustObjectArray();
            case 10:
                return this.readJustIntArray();
            case 11:
                return new Character(this.readChar());
            case 12:
                return this.readJustStringArray();
            case 13:
                return new Long(this.readLong());
            case 14:
                return this.readJustObjectArrayArray();
            case 15:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            default:
                return this.readObjectOverride(type);
            case 16:
                return this.readJustDoubleArray();
            case 17:
                return this.readJustClass();
            case 18:
                return this.readJustDoubleArrayArray();
            case 19:
                return this.readJustLongArray();
            case 33:
                return this.readJustArrayList();
            case 34:
                return this.readJustHashMap();
            case 35:
                return this.readJustArrayList();
            case 36:
                return this.readJustHashMap();
            case 37:
                return this.readJustArrayList();
            case 64:
                clazz = this.findClassGuarded(this.readUTF());
                CustomSerializer serializer = this.customSerializer.getSerializer(clazz);
                if (serializer != null) {
                    return serializer.readObject(this);
                }

                throw new StreamCorruptedException("Custom serializer can't handle class=" + clazz.getName());
            case 65:
                clazz = this.findClassGuarded(this.readUTF());
                return this.readCustomSerializable(clazz);
        }
    }

    protected Class findClassGuarded(String name) {
        try {
            return ClassCache.findClass(name);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public Object readCustomSerializable(Class clazz) throws IOException, ObjectStreamException {
        CustomSerializable cs = (CustomSerializable) ReflectUtils.newInstance(clazz);
        cs.customReadObject(this);
        return cs instanceof ResolvedCustomSerializable ? ((ResolvedCustomSerializable) cs).readResolve() : cs;
    }

    protected Object readObjectOverride(byte type) throws IOException {
        throw new StreamCorruptedException("readObjectOverride, type=" + type);
    }

    public Object readEncrypt() throws IOException {
        if (this.decryptor == null) {
            throw new UnsupportedOperationException("No encryption algorithm/key provided.");
        } else {
            try {
                return ((SealedSerializable) this.readObject()).getObject(this.decryptor);
            } catch (GeneralSecurityException var2) {
                throw (IOException) (new IOException("Encrypted data corrupted.")).initCause(var2);
            } catch (ClassNotFoundException var3) {
                throw (IOException) (new IOException("Encrypted data corrupted.")).initCause(var3);
            }
        }
    }

    public void writeEncrypt(Object object) throws IOException {
        if (this.encryptor == null) {
            throw new UnsupportedOperationException("No encryption algorithm/key provided.");
        } else {
            try {
                this.writeObject(new SealedSerializable((Serializable) object, this.encryptor));
            } catch (GeneralSecurityException var3) {
                throw (IOException) (new IOException("Encryption failed for the input data.")).initCause(var3);
            }
        }
    }
}
