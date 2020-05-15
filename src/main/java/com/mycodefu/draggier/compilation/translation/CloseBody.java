package com.mycodefu.draggier.compilation.translation;

import java.util.List;

import com.mycodefu.draggier.compilation.imports.Importer;

public class CloseBody implements Translator {

	@Override
	public boolean canTranslate(String line) {
		return line.replace(" ", "").contentEquals(";");
	}

	@Override
	public void translate(String line, Importer importer, List<String> output) {
		output.add("}");
	}

}
