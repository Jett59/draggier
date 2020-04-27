package com.mycodefu.draggier.memory;

import com.mycodefu.draggier.compilation.CompilationException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

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
    
    @Test
    public void replaceBoolean_replacingBoolean() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateBoolean("test", false);
    	memoryStorage.replaceBoolean("test", true);
    	assertTrue(memoryStorage.getBoolean("test"));
    }
    
    @Test
    public void replaceBoolean_replacingString() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateString("test", "abcdefghijklmnopqrstuvwxyz");
    	memoryStorage.replaceBoolean("test", false);
    	assertEquals("false", memoryStorage.getString("test"));
    }
    
    @Test
    public void replaceInt_replacingInt() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateInt("test", 512);
    	memoryStorage.replaceInt("test", 1024);
    	assertEquals(memoryStorage.getInt("test"), 1024);
    }
    
    @Test
    public void replaceInt_replacingString() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateString("test", "abcdefghijklmnopqrstuvwxyz");
    	memoryStorage.replaceInt("test", 1024);
    	assertEquals(memoryStorage.getString("test"), "1024");
    }
    
    @Test
    public void replaceString_replacingString() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateString("test", "abcdefghijklmnopqrstuvwxyz");
    	memoryStorage.replaceString("test", "qwertyuiopasdfghjklzxcvbnm");
    	assertEquals("qwertyuiopasdfghjklzxcvbnm", memoryStorage.getString("test"));
    }
    
    @Test
    public void replaceString_replacingBoolean() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateBoolean("test", false);
    	memoryStorage.replaceString("test", "true");
    	assertTrue(memoryStorage.getBoolean("test"));
    }
    
    @Test
    public void replaceString_replacingInt() throws CompilationException {
    	MemoryStorage memoryStorage = new MemoryStorage(1024);
    	memoryStorage.allocateInt("test", 512);
    	memoryStorage.replaceString("test", "1024");
    	assertEquals(memoryStorage.getInt("test"), 1024);
    }
    
    @Test
    public void testBooleanAllocationSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		memoryStorage.allocateBoolean("test"+count.get(), true);
    	});
    	assertTrue(speed < 500, "boolean allocation is not fast enough, speed (nanos): "+speed);
    }
    
    @Test
    public void testIntAllocationSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		memoryStorage.allocateInt("test"+count.get(), count.get());
    	});
    	assertTrue(speed < 500, "int allocation is not fast enough, speed (nanos): "+speed);
    }
    
    @Test
    public void testStringAllocationSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		memoryStorage.allocateString("test"+count.get(), "test");
    	});
    	assertTrue(speed < 500, "string allocation is not fast enough, speed (nanos): "+speed);
    }
    
    public long speedTest(BiConsumer<AtomicInteger, MemoryStorage> testAction) {
    	long result = 0;
    	MemoryStorage memoryStorage = new MemoryStorage(1048576);
		AtomicInteger count = new AtomicInteger();
    	for(int i = 0; i < 10; i++) {
    		while(count.addAndGet(1) <= 10000) {
    			testAction.accept(count, memoryStorage);
    		}
    		memoryStorage.clear();
			count.set(0);
    	}
    	int iterations = 100;
    	for(int i = 0; i < iterations; i++) {
    		long now = System.nanoTime();
    		while(count.addAndGet(1) < 10000) {
    			testAction.accept(count, memoryStorage);
    		}
    		long finished = System.nanoTime();
    		long speed = (finished-now)/10000;
    		result+=speed;
    		memoryStorage.clear();
    		count.set(0);
    	}
    	result/=iterations;
    	return result;
    }
}
