package com.mycodefu.draggier.compilation;

import java.util.List;

public class ClassDefiner {
public ClassConfig findClass(List<String> lines) {
	ClassConfig classConfig;
	for(String line : lines) {
		if(line.replace(" ", "").startsWith("$class")) {
			classConfig = new ClassConfig(line.replace(" ", "").replace("$class", ""));
			return classConfig;
		}
	}
	throw new CompilationException("no class could be found");
}

public static class ClassConfig {
private String name;

private ClassConfig(String name) {
	this.name = name;
}

public String getName() {
	return name;
}

public String toString() {
	return "public class "+name+" {";
}
}
}
