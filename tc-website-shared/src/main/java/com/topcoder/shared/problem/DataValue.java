// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataValue extends BaseElement
{
    private static HashMap types;
    
    public abstract void parse(final DataValueReader p0, final DataType p1) throws IOException, DataValueParseException;
    
    public abstract String encode();
    
    public abstract Object getValue();
    
    public String toXML() {
        return BaseElement.encodeHTML(this.encode());
    }
    
    public static DataValue parseValue(final String text, final DataType type) throws IOException, DataValueParseException {
        return parseValue(new DataValueReader(text), type);
    }
    
    public static DataValue parseValue(final DataValueReader reader, final DataType type) throws IOException, DataValueParseException {
        if (type.getDimension() > 0) {
            return new ArrayValue(reader, type);
        }
        final String valuetype = (String)DataValue.types.get(type.getDescription());
        if (valuetype == null) {
            throw new DataValueParseException("Do not know how to handle data of type " + type.getDescription());
        }
        try {
            final Class c = Class.forName(valuetype);
            final DataValue value = (DataValue)c.newInstance();
            value.parse(reader, type);
            return value;
        }
        catch (DataValueParseException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new DataValueParseException("Error instantiating object of type " + valuetype + ": " + ex2);
        }
    }
    
    public static DataValue[] parseValues(final String[] values, final DataType[] types) throws IOException, DataValueParseException {
        if (values.length != types.length) {
            throw new IllegalArgumentException("Values and types should have the same number of elements.");
        }
        final DataValue[] dvs = new DataValue[values.length];
        for (int i = 0; i < values.length; ++i) {
            dvs[i] = parseValue(values[i], types[i]);
        }
        return dvs;
    }
    
    public static Object parseValueToObject(final String text, final DataType type) throws IOException, DataValueParseException {
        return convertDataValueToObject(parseValue(text, type), type);
    }
    
    public static Object[] parseValuesToObjects(final String[] values, final DataType[] types) throws IOException, DataValueParseException {
        return convertDataValuesToObjects(parseValues(values, types), types);
    }
    
    public static DataValue[] parseSequence(final String text, final DataType[] type) throws IOException, DataValueParseException {
        return parseSequence(new DataValueReader(text), type);
    }
    
    public static DataValue[] parseSequence(final DataValueReader reader, final DataType[] type) throws IOException, DataValueParseException {
        reader.expect('{', true);
        final DataValue[] result = new DataValue[type.length];
        for (int i = 0; i < type.length; ++i) {
            if (i > 0) {
                reader.expect(',', true);
            }
            result[i] = parseValue(reader, type[i]);
        }
        reader.expect('}', true);
        return result;
    }
    
    public static Object convertDataValueToObject(final DataValue value, final DataType type) throws DataValueParseException {
        final String desc = type.getDescription();
        Object object = null;
        try {
            if (desc.equals("int") || desc.equals("Integer")) {
                object = new Integer((int)(long)value.getValue());
            }
            else if (desc.equals("long") || desc.equals("Long")) {
                object = value.getValue();
            }
            else if (desc.equals("float") || desc.equals("Float")) {
                object = new Float(((Double)value.getValue()).floatValue());
            }
            else if (desc.equals("double") || desc.equals("Double")) {
                object = value.getValue();
            }
            else if (desc.equals("short") || desc.equals("Short")) {
                object = new Short((short)(long)value.getValue());
            }
            else {
                if (desc.equals("byte") || desc.equals("Byte")) {
                    throw new DataValueParseException("byte and Byte not supported.");
                }
                if (desc.equals("boolean") || desc.equals("Boolean")) {
                    throw new DataValueParseException("boolean and Boolean not supported.");
                }
                if (desc.equals("char") || desc.equals("Character")) {
                    object = value.getValue();
                }
                else if (desc.equals("String")) {
                    object = value.getValue();
                }
                else {
                    if (desc.equals("ArrayList")) {
                        throw new DataValueParseException("ArrayList not supported.");
                    }
                    if (desc.equals("int[]")) {
                        final Object[] arrayO = (Object[])value.getValue();
                        final int[] arrayI = new int[arrayO.length];
                        for (int j = 0; j < arrayO.length; ++j) {
                            arrayI[j] = (int)(long)((DataValue)arrayO[j]).getValue();
                        }
                        object = arrayI;
                    }
                    else if (desc.equals("long[]")) {
                        final Object[] arrayO = (Object[])value.getValue();
                        final long[] arrayI2 = new long[arrayO.length];
                        for (int j = 0; j < arrayO.length; ++j) {
                            arrayI2[j] = (long)((DataValue)arrayO[j]).getValue();
                        }
                        object = arrayI2;
                    }
                    else if (desc.equals("double[]")) {
                        final Object[] arrayO = (Object[])value.getValue();
                        final double[] arrayI3 = new double[arrayO.length];
                        for (int j = 0; j < arrayO.length; ++j) {
                            arrayI3[j] = (double)((DataValue)arrayO[j]).getValue();
                        }
                        object = arrayI3;
                    }
                    else if (desc.equals("String[]")) {
                        final Object[] arrayO = (Object[])value.getValue();
                        final String[] arrayS = new String[arrayO.length];
                        for (int j = 0; j < arrayO.length; ++j) {
                            arrayS[j] = (String)((DataValue)arrayO[j]).getValue();
                        }
                        object = arrayS;
                    }
                }
            }
        }
        catch (DataValueParseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DataValueParseException(e.toString());
        }
        return object;
    }
    
    public static Object[] convertDataValuesToObjects(final DataValue[] values, final DataType[] types) throws DataValueParseException {
        if (values.length != types.length) {
            throw new IllegalArgumentException("Values and types should have the same number of elements.");
        }
        final Object[] objects = new Object[values.length];
        try {
            for (int i = 0; i < objects.length; ++i) {
                objects[i] = convertDataValueToObject(values[i], types[i]);
            }
        }
        catch (DataValueParseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DataValueParseException(e.toString());
        }
        return objects;
    }
    
    public static DataValue convertObjectToDataValue(final Object obj, final DataType type) throws DataValueParseException {
        DataValue dataValue = null;
        try {
            final String desc = type.getDescription();
            if (desc.equals("int") || desc.equals("Integer")) {
                dataValue = new IntegralValue((long)obj);
            }
            else if (desc.equals("long") || desc.equals("Long")) {
                dataValue = new IntegralValue((long)obj);
            }
            else if (desc.equals("float") || desc.equals("Float")) {
                dataValue = new DecimalValue((double)obj);
            }
            else if (desc.equals("double") || desc.equals("Double")) {
                dataValue = new DecimalValue((double)obj);
            }
            else if (desc.equals("short") || desc.equals("Short")) {
                dataValue = new IntegralValue((long)obj);
            }
            else {
                if (desc.equals("byte") || desc.equals("Byte")) {
                    throw new DataValueParseException("byte and Byte not supported.");
                }
                if (desc.equals("boolean") || desc.equals("Boolean")) {
                    throw new DataValueParseException("boolean and Boolean not supported.");
                }
                if (desc.equals("char") || desc.equals("Character")) {
                    dataValue = new CharacterValue((char)obj);
                }
                else if (desc.equals("String")) {
                    dataValue = new StringValue((String)obj);
                }
                else {
                    if (desc.equals("ArrayList")) {
                        throw new DataValueParseException("ArrayList not supported.");
                    }
                    if (desc.equals("int[]")) {
                        final ArrayList values = new ArrayList();
                        final int[] ints = (int[])obj;
                        for (int i = 0; i < ints.length; ++i) {
                            values.add(new IntegralValue(ints[i]));
                        }
                        dataValue = new ArrayValue(values);
                    }
                    else if (desc.equals("long[]")) {
                        final ArrayList values = new ArrayList();
                        final long[] longs = (long[])obj;
                        for (int i = 0; i < longs.length; ++i) {
                            values.add(new IntegralValue(longs[i]));
                        }
                        dataValue = new ArrayValue(values);
                    }
                    else if (desc.equals("double[]")) {
                        final ArrayList values = new ArrayList();
                        final double[] nums = (double[])obj;
                        for (int i = 0; i < nums.length; ++i) {
                            values.add(new DecimalValue(nums[i]));
                        }
                        dataValue = new ArrayValue(values);
                    }
                    else if (desc.equals("String[]")) {
                        final ArrayList values = new ArrayList();
                        final String[] strings = (String[])obj;
                        for (int i = 0; i < strings.length; ++i) {
                            values.add(new StringValue(strings[i]));
                        }
                        dataValue = new ArrayValue(values);
                    }
                }
            }
        }
        catch (ClassCastException e) {
            throw new DataValueParseException("Error converting Object to DataValue. Expected " + type.getDescription() + ", got " + e.getMessage());
        }
        return dataValue;
    }
    
    public static DataValue[] convertObjectsToDataValues(final Object[] objs, final DataType[] types) throws DataValueParseException {
        if (objs.length != types.length) {
            throw new IllegalArgumentException("Objects and types should have the same number of elements.");
        }
        final DataValue[] values = new DataValue[objs.length];
        for (int i = 0; i < values.length; ++i) {
            values[i] = convertObjectToDataValue(objs[i], types[i]);
        }
        return values;
    }
    
    private static HashMap buildTypes() {
        final HashMap types = new HashMap();
        types.put("char", "com.topcoder.shared.problem.CharacterValue");
        types.put("Character", "com.topcoder.shared.problem.CharacterValue");
        types.put("int", "com.topcoder.shared.problem.IntegralValue");
        types.put("Integer", "com.topcoder.shared.problem.IntegralValue");
        types.put("long", "com.topcoder.shared.problem.IntegralValue");
        types.put("Long", "com.topcoder.shared.problem.IntegralValue");
        types.put("short", "com.topcoder.shared.problem.IntegralValue");
        types.put("Short", "com.topcoder.shared.problem.IntegralValue");
        types.put("double", "com.topcoder.shared.problem.DecimalValue");
        types.put("Double", "com.topcoder.shared.problem.DecimalValue");
        types.put("float", "com.topcoder.shared.problem.DecimalValue");
        types.put("Float", "com.topcoder.shared.problem.DecimalValue");
        types.put("String", "com.topcoder.shared.problem.StringValue");
        return types;
    }
    
    static {
        DataValue.types = buildTypes();
    }
}
