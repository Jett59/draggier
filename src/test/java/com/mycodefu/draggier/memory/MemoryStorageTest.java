package com.mycodefu.draggier.memory;

import com.mycodefu.draggier.compilation.CompilationException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.nio.BufferOverflowException;

import static org.junit.jupiter.api.Assertions.*;

class MemoryStorageTest {

    @Test()
    void allocateString_200chars() throws CompilationException {
        MemoryStorage memoryStorage = new MemoryStorage(1024);
        memoryStorage.allocateString("test", "p".repeat(200));
        String test = memoryStorage.getString("test");
        assertEquals("p".repeat(200), test);
    }
}