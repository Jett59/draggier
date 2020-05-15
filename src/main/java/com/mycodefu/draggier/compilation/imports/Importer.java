package com.mycodefu.draggier.compilation.imports;

import java.util.ArrayList;
import java.util.List;

public class Importer {
private List<String> imports;

public Importer() {
	this.imports = new ArrayList<>();
}

public void importClass(String path) {
	imports.add(path);
}

public String toString() {
	String result = "";
	for(String importPath : imports) {
		result+=String.format("\n import %s;\n", importPath);
	}
	return result;
}
}
