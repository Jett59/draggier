package com.mycodefu.draggier.memory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.mycodefu.draggier.compilation.CompilationException;

public class MemoryStorage {
	private ByteBuffer memory;
private Map<Integer, Integer> nameToLocation;
private Integer nextIndex = 0;

public MemoryStorage(int capacity) {
	memory = ByteBuffer.allocate(capacity);
	nameToLocation = new HashMap<>();
}

public void allocateInt(String id, int value) {
	System.out.println("allocating "+id);
	nameToLocation.put(id.hashCode(), nextIndex);
	memory.position(nextIndex);
	memory.put((byte)4).putInt(value);
	nextIndex = memory.position();
}

public void allocateString(String id, String value) {
	System.out.println("allocating "+id);
	nameToLocation.put(id.hashCode(), nextIndex);
	memory.position(nextIndex);
	memory.put((byte)value.length()).put(value.getBytes());
	nextIndex = memory.position();
}

public int getInt(String id) throws CompilationException{
	int index = nameToLocation.get(id.hashCode());
	memory.position(index);
	byte length = memory.get();
	if(length == 4) {
		return memory.getInt();
	}else {
		throw new CompilationException("type mismatch, "+id+" is not an int");
	}
}

public String getString(String id) {
	int index = nameToLocation.get(id.hashCode());
	memory.position(index);
	int length = (int)memory.get();
	byte[] content = new byte[length];
	memory.get(content, 0, length);
	String result = new String(content);
	return result;
}
}
