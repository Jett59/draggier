package com.mycodefu.draggier.compilation;

import java.util.List;

public class PackageDefiner {
public PackageConfig findPackage(List<String> lines) {
	for(String line : lines) {
		if(line.replace(" ", "").startsWith("pack")) {
			return new PackageConfig(line.replace(" ", "").replace("pack", ""));
		}
	}
	throw new CompilationException("could not find package declaration");
}

	public static class PackageConfig {
private String packageName;

private PackageConfig(String packageName) {
	this.packageName = packageName;
}

public String getName() {
	return packageName;
}

public String toString() {
	return "package "+packageName+";";
}
}
}
