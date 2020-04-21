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

public void allocateInt(String id, int value) {
	nameToLocation.put(id.hashCode(), nextIndex);
	memory.position(nextIndex);
	memory.put((byte)4).putInt(value);
	nextIndex = memory.position();
}

public void allocateString(String id, String value) {
	nameToLocation.put(id.hashCode(), nextIndex);
	memory.position(nextIndex);
	memory.put((byte)value.length()).put(value.getBytes());
	nextIndex = memory.position();
}

public int getInt(String id) throws CompilationException{
	int index = nameToLocation.get(id.hashCode());
	memory.position(index);
	int length = memory.get();
	if(length == 4) {
		return memory.getInt();
	}else if(length == 0){
		throw new CompilationException("");
	}else {
		throw new CompilationException("the object "+id+" does not exist or has no allocated bytes");
	}
}

public String getString(String id) throws CompilationException{
	int index = nameToLocation.get(id.hashCode());
	memory.position(index);
	int length = (int)memory.get();
	if(length != 0) {
	byte[] content = new byte[length];
	memory.get(content, 0, length);
	String result = new String(content);
	return result;
	}else {
		throw new CompilationException("the object "+id+" does not exist or has no allocated bytes");
	}
}
}
