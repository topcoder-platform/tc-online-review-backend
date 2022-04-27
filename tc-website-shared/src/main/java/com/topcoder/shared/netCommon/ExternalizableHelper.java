package com.topcoder.shared.netCommon;

import java.io.*;
import java.lang.reflect.Constructor;

public class ExternalizableHelper {
    public static final String DEFAULT_HANDLER_VALUE = "com.topcoder.server.serialization.ExternalizableCSHandler";
    public static final String DEFAULT_HANDLER_KEY = "com.topcoder.shared.netCommon.externalizable.handlerClass";
    private static Constructor handlerConstructor;
    private static ThreadLocal threadLocal = new ThreadLocal() {
        protected Object initialValue() {
            try {
                if (ExternalizableHelper.handlerConstructor == null) {
                    String className = System.getProperty("com.topcoder.shared.netCommon.externalizable.handlerClass", "com.topcoder.server.serialization.ExternalizableCSHandler");
                    ExternalizableHelper.handlerConstructor = Class.forName(className).getConstructor();
                }

                return ExternalizableHelper.handlerConstructor.newInstance();
            } catch (Exception var2) {
                throw (IllegalStateException) (new IllegalStateException("Exception trying to instantiate externalizable handler")).initCause(var2);
            }
        }
    };

    public ExternalizableHelper() {
    }

    public static void writeExternal(final ObjectOutput out, CustomSerializable obj) throws IOException {
        CSHandler handler = getInstance();
//        handler.setDataOutput(new BasicTypeDataOutputImpl(new OutputStream() {
//            public void write(byte[] b, int off, int len) throws IOException {
//                out.write(b, off, len);
//            }
//
//            public void write(byte[] b) throws IOException {
//                out.write(b);
//            }
//
//            public void write(int b) throws IOException {
//                out.write(b);
//            }
//
//            public void close() throws IOException {
//                out.close();
//            }
//
//            public void flush() throws IOException {
//                out.flush();
//            }
//        }));
        obj.customWriteObject(handler);
//        handler.setDataOutput((BasicTypeDataOutput) null);
    }

    public static void readExternal(final ObjectInput in, CustomSerializable obj) throws IOException {
        CSHandler handler = getInstance();
//        handler.setDataInput(new BasicTypeDataInputImpl(new InputStream() {
//            public boolean markSupported() {
//                return false;
//            }
//
//            public int read() throws IOException {
//                return in.read();
//            }
//
//            public int read(byte[] b, int off, int len) throws IOException {
//                return in.read(b, off, len);
//            }
//
//            public int read(byte[] b) throws IOException {
//                return in.read(b);
//            }
//
//            public long skip(long n) throws IOException {
//                return in.skip(n);
//            }
//
//            public int available() throws IOException {
//                return in.available();
//            }
//
//            public void close() throws IOException {
//                in.close();
//            }
//        }));
        obj.customReadObject(handler);
//        handler.setDataInput((BasicTypeDataInput) null);
    }

    public static CSHandler getInstance() {
        return (CSHandler) threadLocal.get();
    }
}
