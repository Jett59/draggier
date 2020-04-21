package com.mycodefu.draggier.assignments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.variables.VariableType;

public class Assigner {
private final Pattern pattern = Pattern.compile("var (.*?)> (.*?) = (.*?) <var");

public void assign(String name, VariableType type, String value, MemoryStorage memory) {
	switch (type) {
	case integer: {
		memory.allocateInt(name, Integer.parseInt(value));
		break;
	}
	case String: {
		memory.allocateString(name, value.replace("\"", "").replace("\\n", "\n"));
		break;
	}
	}
}

public void assign(String assignment, MemoryStorage memory) throws CompilationException{
	Matcher matcher = pattern.matcher(assignment);
	if(matcher.find()) {
		String name = matcher.group(1);
		VariableType type = VariableType.valueOf(matcher.group(2));
		String value = matcher.group(3);
		assign(name, type, value, memory);
	}else {
		throw new CompilationException("the assignment "+assignment+" is not valid");
	}
}
}
