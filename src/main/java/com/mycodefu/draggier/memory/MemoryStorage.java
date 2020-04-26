package com.mycodefu.draggier.memory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.mycodefu.draggier.compilation.CompilationException;

public class MemoryStorage {
    private ByteBuffer memory;
    private Map<Integer, Integer> nameToLocation;
    private int nextIndex = 1;

    public MemoryStorage(int capacity) {
        memory = ByteBuffer.allocate(capacity);
        nameToLocation = new HashMap<>();
    }

    public void allocateBoolean(String id, boolean value) {
        nameToLocation.put(id.hashCode(), nextIndex);
        memory.position(nextIndex);
        memory.put(Objects.BOOL);
        memory.put((byte) (value ? 1 : 0));
        nextIndex = memory.position();
    }

    public void allocateInt(String id, int value) {
        nameToLocation.put(id.hashCode(), nextIndex);
        memory.position(nextIndex);
        memory.put(Objects.INTEGER);
        memory.putInt(value);
        nextIndex = memory.position();
    }

    public void allocateString(String id, String value) {
        byte[] bytes = value.getBytes();
        nameToLocation.put(id.hashCode(), nextIndex);
        memory.position(nextIndex);
        memory.put(Objects.STRING);
        memory.putShort((short) bytes.length);
        memory.put(bytes);
        nextIndex = memory.position();
    }

    public boolean getBoolean(String id) throws CompilationException {
        int index = nameToLocation.get(id.hashCode());
        memory.position(index);
        byte type = memory.get();
        if (type == Objects.BOOL) {
            return memory.get() == 1;
        } else if (type == Objects.STRING) {
            return Boolean.parseBoolean(getString(id));
        } else {
            throw new CompilationException("the object " + id + "is not valid for the type boolean");
        }
    }

    public int getInt(String id) throws CompilationException {
        int index = nameToLocation.get(id.hashCode());
        memory.position(index);
        byte type = memory.get();
        if (type == Objects.INTEGER) {
                return memory.getInt();
        } else if (type == Objects.STRING) {
            return Integer.parseInt(getString(id));
        } else {
            throw new CompilationException("the object " + id + " is not valid for the type int");
        }
    }

    public String getString(String id) throws CompilationException {
        int index = nameToLocation.get(id.hashCode());
        memory.position(index);
        byte type = memory.get();
        if (type == Objects.BOOL) {
            return Boolean.toString(getBoolean(id));
        }else if(type == Objects.INTEGER) {
        	return Integer.toString(getInt(id));
        }
        short length = memory.getShort();
        if (length != 0) {
            String result;
            if (type == Objects.STRING) {
                byte[] content = new byte[length];
                memory.get(content, 0, length);
                result = new String(content);
            } else {
                throw new CompilationException("the object " + id + " is invalid for the type String");
            }
            return result;
        } else {
            throw new CompilationException("the object " + id + " does not exist or has no allocated bytes");
        }
    }

    public void replaceBoolean(String id, boolean newValue) throws CompilationException {
        int index = nameToLocation.get(id.hashCode());
        memory.position(index);
        byte type = memory.get();
        if (type == Objects.BOOL) {
            memory.put((byte) (newValue ? 1 : 0));
        }else if(type == Objects.STRING) {
        	replaceString(id, Boolean.toString(newValue));
        } else {
            throw new CompilationException("the object " + id + " cannot be replaced by a boolean");
        }
    }

    public void replaceInt(String id, int newValue) throws CompilationException {
        int index = nameToLocation.get(id.hashCode());
        memory.position(index);
        byte type = memory.get();
        if (type == Objects.INTEGER) {
                memory.putInt(newValue);
        }else if(type == Objects.STRING) {
        	replaceString(id, Integer.toString(newValue));
        } else {
            throw new CompilationException("type mismatch: object " + id + " is not an int");
        }
    }

    public void replaceString(String id, String newValue) throws CompilationException {
        int index = nameToLocation.get(id.hashCode());
        memory.position(index);
        byte type = memory.get();
        int length = memory.get();
        if (type == Objects.STRING) {
            byte[] bytes = newValue.getBytes();
            if (length == bytes.length) {
                memory.put(newValue.getBytes());
            } else {
                memory.position(index).put((byte) (0 - length));
                allocateString(id, newValue);
            }
        } else if (type == Objects.INTEGER) {
            allocateInt(id, Integer.parseInt(newValue));
        } else if (type == Objects.BOOL) {
            allocateBoolean(id, Boolean.parseBoolean(newValue));
        } else {
            throw new CompilationException("the object " + id + " cannot be replaced by a String");
        }
    }
}
