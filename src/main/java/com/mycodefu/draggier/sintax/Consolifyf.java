package com.mycodefu.draggier.sintax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.variables.Variable;

public class Consolifyf implements Sintax{
	private final Pattern pattern = Pattern.compile("consolifyf\\((.*?), (.*?)\\)");

	@Override
	public boolean executeCommand(String line, MemoryStorage memory) throws CompilationException {
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			String text = matcher.group(1);
			String[] arguments = matcher.group(2).split(", ");
			Object[] args = new Object[arguments.length];
			for(int i = 0; i < arguments.length; i++) {
				String argument = arguments[i];
				if(argument.startsWith("\"") && argument.endsWith("\"")) {
					args[i] = argument.replace("\"", "");
				}else if(Variable.isVariableReference(argument)) {
					args[i] = memory.getString(Variable.getVariableName(argument));
				}else {
					throw new CompilationException("the argument "+argument+" in the line "+line+" is not valid");
				}
			}
			if(text.startsWith("\"") && text.endsWith("\"")) {
				text = text.replace("\"", "");
			text = text.replace("\\n", "\n");
			}else if(Variable.isVariableReference(text)) {
				text = memory.getString(Variable.getVariableName(text));
			}else {
				throw new CompilationException("the argument "+text+" in the line "+line+" is not valid");
			}
			System.out.printf(text, args);
			return true;
		}
		return false;
	}

}
