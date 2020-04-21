package com.mycodefu.draggier.variables;

public class Variable {
public static boolean isVariableReference(String arg) {
	return arg.startsWith("var=>");
}

public static String getVariableName(String arg) {
	return arg.replace("var=>", "");
}
}
