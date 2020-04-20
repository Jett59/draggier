package com.mycodefu.draggier.sintax;

import com.mycodefu.draggier.compilation.CompilationException;

public interface Sintax {
boolean executeCommand(String line) throws CompilationException;
}
