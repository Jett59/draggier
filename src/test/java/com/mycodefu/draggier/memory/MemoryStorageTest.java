package com.mycodefu.draggier.memory;

import com.mycodefu.draggier.compilation.CompilationException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MemoryStorageTest {

@Test
public void allocateBoolean_true() throws CompilationException{
	MemoryStorage memoryStorage = new MemoryStorage(1024);
	memoryStorage.allocateBoolean("test", true);
	assertTrue(memoryStorage.getBoolean("test"));
}

@Test
public void allocateBoolean_false() throws CompilationException{
	MemoryStorage memoryStorage = new MemoryStorage(1024);
	memoryStorage.allocateBoolean("test", false);
	assertFalse(memoryStorage.getBoolean("test"));
}

@Test
public void allocateInt_0() throws CompilationException{
	MemoryStorage memoryStorage = new MemoryStorage(1024);
	memoryStorage.allocateInt("test", 0);
	assertEquals(memoryStorage.getInt("test"), 0);
}

@Test
public void allocateInt_max() throws CompilationException{
	MemoryStorage memoryStorage = new MemoryStorage(1024);
	memoryStorage.allocateInt("test", Integer.MAX_VALUE);
	assertEquals(memoryStorage.getInt("test"), Integer.MAX_VALUE);
}

    @Test    
    public void allocateString_200chars() throws CompilationException {
        MemoryStorage memoryStorage = new MemoryStorage(1024);
        memoryStorage.allocateString("test", "p".repeat(200));
        String test = memoryStorage.getString("test");
        assertEquals("p".repeat(200), test);
    }
    
    @Test
    public void allocateString_33000chars() throws Exception{
    	MemoryStorage memoryStorage = new MemoryStorage(65535);
    	assertThrows(NegativeArraySizeException.class, ()->{
    		memoryStorage.allocateString("test", "p".repeat(33000));
    		memoryStorage.getString("test");
    	});
    }
    
    @Test
    public void allocateString_accentedChars() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	String testString = "thìs ìs à tèst";
    	memoryStorage.allocateString("test", testString);
    	assertEquals(testString, memoryStorage.getString("test"));
    }
    
    @Test
    public void getBoolean_parsedFromString() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateString("test", "true");
    	assertTrue(memoryStorage.getBoolean("test"));
    }
    
    @Test
    public void getInt_parsedFromString() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateString("test", "512");
    	assertEquals(memoryStorage.getInt("test"), 512);
    }
    
    @Test
    public void getString_fromBoolean() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateBoolean("test", true);
    	assertEquals("true", memoryStorage.getString("test"));
    }
    
    @Test
    public void getString_fromInt() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateInt("test", 1048576);
    	assertEquals("1048576", memoryStorage.getString("test"));
    }
}
