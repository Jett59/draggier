package com.mycodefu.draggier.bodies;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.sintax.Sintax;
import com.mycodefu.draggier.variables.Variable;

public class Iter implements Body{
private final Pattern pattern = Pattern.compile("iter>(.*?) \\"+Body.BODY_OPEN_CHAR);

	@Override
	public void run(Sintax[] commands, List<String> lines, String openingLine, MemoryStorage memory) throws CompilationException {
		Matcher matcher = pattern.matcher(openingLine);
		if(matcher.find()) {
			int iterations = 0;
			String iterationString = matcher.group(1);
			if(Variable.isVariableReference(iterationString)) {
				iterations = memory.getInt(Variable.getVariableName(iterationString));
			}else {
				iterations = Integer.parseInt(iterationString);
			}
				for(int i = 0; i < iterations; i++) {
					for(String line : lines) {
					for(Sintax command : commands) {
						if(command.executeCommand(line, memory)) {
							break;
						}
					}
				}
				}
		}else {
			throw new CompilationException("invalid iter statement: "+openingLine);
		}
	}

	@Override
	public boolean identifiedIn(String line) {
		return line.startsWith("iter");
	}

}
