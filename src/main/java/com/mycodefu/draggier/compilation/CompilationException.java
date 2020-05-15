package com.mycodefu.draggier.compilation;

public class CompilationException extends RuntimeException{
	private static final long serialVersionUID = 1818456974084699858L;

public CompilationException(String message) {
	super(message);
}

public CompilationException(Throwable cause) {
	super(cause);
}
}
