package com.mycodefu.draggier.sintax;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;

public interface Sintax {
boolean executeCommand(String line, MemoryStorage memory) throws CompilationException;
}
