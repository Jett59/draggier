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
}
