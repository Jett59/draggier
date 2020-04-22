package com.mycodefu.draggier.assignments;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.variables.VariableType;

public class Assigner {
private final Pattern declarePattern = Pattern.compile("var (.*?)> (.*?) = (.*?) <var");
private final Pattern reassignPattern = Pattern.compile("var=>(.*?):(.*?):(.*?) <var");

public void declare (String name, VariableType type, String value, MemoryStorage memory) {
	switch (type) {
	case bool: {
		memory.allocateBoolean(name, Boolean.parseBoolean(value));
		break;
	}
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

public void reassign (String name, VariableType type, String newValue, MemoryStorage memory) throws NumberFormatException, CompilationException {
	switch (type) {
	case bool: {
		memory.replaceBoolean(name, Boolean.parseBoolean(newValue));
		break;
	}
	case integer: {
		memory.replaceInt(name, Integer.parseInt(newValue));
		break;
	}
	case String: {
		memory.replaceString(name, newValue);
		break;
	}
	}
}

public void assign(String assignment, MemoryStorage memory) throws CompilationException{
	Matcher declareMatcher = declarePattern.matcher(assignment);
	Matcher reassignMatcher = reassignPattern.matcher(assignment);
	if(declareMatcher.find()) {
		String name = declareMatcher.group(1);
		VariableType type = VariableType.valueOf(declareMatcher.group(2));
		String value = declareMatcher.group(3);
		declare(name, type, value, memory);
	}else if(reassignMatcher.find()) {
		String name = reassignMatcher.group(1);
		VariableType type = VariableType.valueOf(reassignMatcher.group(2));
		String newValue = reassignMatcher.group(3).replace("\"", "").replace("\\n", "\n");
		reassign(name, type, newValue, memory);
	}else {
		throw new CompilationException("the assignment "+assignment+" is not valid");
	}
}
}
