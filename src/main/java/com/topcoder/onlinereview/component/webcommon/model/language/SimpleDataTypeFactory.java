package com.topcoder.onlinereview.component.webcommon.model.language;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

public class SimpleDataTypeFactory {
    protected static HashMap types;
    protected static boolean initialized;

    public static void initialize() {
        if (SimpleDataTypeFactory.initialized) {
            return;
        }
        try {
            DataTypeFactory.initializeFromDB();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataType getDataType(final String description) throws InvalidTypeException {
        initialize();
        final DataType type = (DataType)SimpleDataTypeFactory.types.get(description);
        if (type == null) {
            throw new InvalidTypeException(description);
        }
        return type.cloneDataType();
    }

    public static DataType getDataType(final int typeID) throws InvalidTypeException {
        initialize();
        final DataType type = (DataType)SimpleDataTypeFactory.types.get(new Integer(typeID));
        if (type == null) {
            throw new InvalidTypeException(new String("" + typeID));
        }
        return type.cloneDataType();
    }

    static void registerDataType(final DataType type) {
        SimpleDataTypeFactory.initialized = true;
        if (SimpleDataTypeFactory.types.containsKey(type.getDescription())) {
            return;
        }
        SimpleDataTypeFactory.types.put(type.getDescription(), type.cloneDataType());
        SimpleDataTypeFactory.types.put(new Integer(type.getID()), type.cloneDataType());
    }

    public static Collection getDataTypes() {
        initialize();
        return SimpleDataTypeFactory.types.values();
    }

    static {
        SimpleDataTypeFactory.types = new HashMap();
        SimpleDataTypeFactory.initialized = false;
    }
}
