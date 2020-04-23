package com.mycodefu.draggier.bodies;

import java.util.List;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.sintax.Sintax;

public interface Body {
public static final String BODY_OPEN_CHAR = "{";
public static final String BODY_CLOSE_CHAR = "}";

void run(Sintax[] commands, List<String> lines, String openingLine, MemoryStorage memory) throws CompilationException;
boolean identifiedIn(String line);
}
