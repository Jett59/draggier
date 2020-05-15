package com.mycodefu.draggier.compilation.translation;

import java.util.List;

import com.mycodefu.draggier.compilation.imports.Importer;

public class MainMethod implements Translator {

	@Override
	public boolean canTranslate(String line) {
		return line.replace(" ", "").equalsIgnoreCase("main:");
	}

	@Override
	public void translate(String line, Importer importer, List<String> output) {
		output.add("public static void main (String[] args) {");
	}

}
