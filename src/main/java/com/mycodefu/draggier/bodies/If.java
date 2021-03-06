package com.mycodefu.draggier.bodies;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.sintax.Sintax;
import com.mycodefu.draggier.variables.Variable;

public class If implements Body {
	private final Pattern pattern = Pattern.compile("if> (.*?):");

	@Override
	public void run(Sintax[] commands, List<String> lines, String openingLine, MemoryStorage memory) throws CompilationException {
Matcher matcher = pattern.matcher(openingLine);
if(matcher.find()) {
	boolean shouldRun = false;
	String predicate = matcher.group(1);
	if(predicate.contains(" == ")) {
		String[] sides = predicate.split(" == ");
		boolean expected, actual;
		if(Variable.isVariableReference(sides[0])) {
			actual = memory.getBoolean(Variable.getVariableName(sides[0]));
		}else {
			throw new CompilationException("the left hand side of an if statement must be a variable");
		}
		if(Variable.isVariableReference(sides[1])) {
			expected = memory.getBoolean(Variable.getVariableName(sides[1]));
		}else {
			expected = Boolean.parseBoolean(sides[1]);
		}
		shouldRun = expected == actual;
	}else if(Variable.isVariableReference(predicate)) {
		shouldRun = memory.getBoolean(Variable.getVariableName(predicate));
	}else {
		throw new CompilationException("the statememnt "+predicate+" is undefined");
	}
	if(shouldRun) {
		for(String line : lines) {
			for(Sintax command : commands) {
				command.executeCommand(line, memory);
			}
		}
	}
}
	}

	@Override
	public boolean identifiedIn(String line) {
		return line.startsWith("if");
	}

}
