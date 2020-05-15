package com.mycodefu.draggier.memory;

import com.mycodefu.draggier.compilation.CompilationException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    	System.out.println("allocateBoolean (nanos): "+speed);
    	assertTrue(speed < 500, "boolean allocation is not fast enough, speed (nanos): "+speed);
    }
    
    @Test
    public void testIntAllocationSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		memoryStorage.allocateInt("test"+count.get(), count.get());
    	});
    	System.out.println("allocateInt (nanos): "+speed);
    	assertTrue(speed < 500, "int allocation is not fast enough, speed (nanos): "+speed);
    }
    
    @Test
    public void testStringAllocationSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		memoryStorage.allocateString("test"+count.get(), "test");
    	});
    	System.out.println("allocateString (nanos): "+speed);
    	assertTrue(speed < 500, "string allocation is not fast enough, speed (nanos): "+speed);
    }
    
    @Test
    public void testBooleanGettingFromBooleanSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage) ->{
				try {
					memoryStorage.getBoolean("test");
				} catch (CompilationException e) {
					throw new IllegalStateException(e);
				}
    	}, memoryStorage->{
    	memoryStorage.allocateBoolean("test", true);
    	});
    	System.out.println("getBoolean_fromBoolean (nanos): "+speed);
    	assertTrue(speed < 500, "getting booleans is too slow, speed (nanos): "+speed);
    }
    
    @Test
    public void testGetBooleanFromStringSpeed() {
    	long speed = speedTest((count, memoryStorage) ->{
			try {
				memoryStorage.getBoolean("test");
			} catch (CompilationException e) {
				throw new IllegalStateException(e);
			}
	}, memoryStorage->{
	memoryStorage.allocateString("test", "true");
	});
    	System.out.println("getBoolean_fromString (nanos): "+speed);
    	assertTrue(speed < 500, "getting booleans from strings is too slow, speed (nanos): "+speed);
    }
    
    @Test
    public void testGetIntFromIntSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		try {
    			memoryStorage.getInt("test");
    		}catch(CompilationException e) {
    			throw new IllegalStateException(e);
    		}
    	}, memoryStorage->{
    		memoryStorage.allocateInt("test", Integer.MAX_VALUE);
    	});
    	System.out.println("getInt_fromInt (nanos): "+speed);
    	assertTrue(speed < 500, "int getting speed is too slow, speed (nanos): "+speed);
    }
    
    @Test
    public void testGetIntFromStringSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		try {
				memoryStorage.getInt("test");
			} catch (CompilationException e) {
				throw new IllegalStateException(e);
			}
    	}, memoryStorage->{
    		memoryStorage.allocateString("test", "512");
    	});
    	System.out.println("getInt_fromString (nanos): "+speed);
    	assertTrue(speed < 500, "int getting from string is too slow, speed (nanos): "+speed);
    }
    
    @Test
    public void testGetStringFromStringSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		try {
    			memoryStorage.getString("test");
    		}catch(CompilationException e) {
    			throw new IllegalStateException(e);
    		}
    	}, memoryStorage->{
    		memoryStorage.allocateString("test", "testing");
    	});
    	System.out.println("getString_fromString (nanos): "+speed);
    	assertTrue(speed < 500, "getting strings is too slow, speed (nanos): "+speed);
    }
    
    @Test
    public void testGetStringFromBooleanSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		try {
    			memoryStorage.getString("test");
    		}catch (CompilationException e) {
    			throw new IllegalStateException(e);
    		}
    	}, memoryStorage->{
    		memoryStorage.allocateBoolean("test", true);
    	});
    	System.out.println("getString_fromBoolean (nanos): "+speed);
    	assertTrue(speed < 500, "string getting speed is too slow, speed (nanos): "+speed);
    }
    
    @Test
    public void testGetStringFromIntSpeed() throws CompilationException {
    	long speed = speedTest((count, memoryStorage)->{
    		try {
    			memoryStorage.getString("test");
    		}catch(CompilationException e) {
    			throw new IllegalStateException(e);
    		}
    	}, memoryStorage->{
    		memoryStorage.allocateInt("test", 0);
    	});
    	System.out.println("getString_fromInt (nanos): "+speed);
    	assertTrue(speed < 500, "getting strings from ints is too slow, speed (nanos): "+speed);
}
    
    public long speedTest(BiConsumer<AtomicInteger, MemoryStorage> testAction, Consumer<MemoryStorage> preparationFunction) {
    	long result = 0;
    	MemoryStorage memoryStorage = new MemoryStorage(1048576);
		AtomicInteger count = new AtomicInteger();
		if(preparationFunction != null) {
				preparationFunction.accept(memoryStorage);
		}
    	for(int i = 0; i < 10; i++) {
    		while(count.addAndGet(1) <= 10000) {
    			testAction.accept(count, memoryStorage);
    		}
    		memoryStorage.clear();
			count.set(0);
			if(preparationFunction != null) {
				preparationFunction.accept(memoryStorage);
		}
    	}
    	int iterations = 100;
    	for(int i = 0; i < iterations; i++) {
    		long now = System.nanoTime();
    		while(count.addAndGet(1) <= 10000) {
    			testAction.accept(count, memoryStorage);
    		}
    		long finished = System.nanoTime();
    		long speed = (finished-now)/10000;
    		result+=speed;
    		memoryStorage.clear();
    		count.set(0);
    		if(preparationFunction != null) {
				preparationFunction.accept(memoryStorage);
		}
    	}
    	result/=iterations;
    	return result;
    }
    
    private long speedTest(BiConsumer<AtomicInteger, MemoryStorage> testAction) {
    	return speedTest(testAction, null);
    }
}
