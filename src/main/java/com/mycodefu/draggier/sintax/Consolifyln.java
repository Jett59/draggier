package com.mycodefu.draggier.sintax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.variables.Variable;

public class Consolifyln implements Sintax {
	private final Pattern pattern = Pattern.compile("consolifyln\\((.*?)\\)");

	@Override
	public boolean executeCommand(String line, MemoryStorage memory) throws CompilationException{
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			String content = matcher.group(1);
			if(content.startsWith("\"") && content.endsWith("\"")) {
				content = content.replace("\"", "");
			content = content.replace("\\n", "\n");
			}else if(Variable.isVariableReference(content)) {
				content = memory.getString(Variable.getVariableName(content));
			}else {
				throw new CompilationException("the argument "+content+" in the line "+line+" is not valid");
			}
			System.out.println(content);
			return true;
		}
		return false;
	}

}
