package com.mycodefu.draggier.compilation.translation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.imports.Importer;

public class Import implements Translator{
	private static final Pattern pattern = Pattern.compile("^\\s+imp:\\s+(.*?)$");

	@Override
	public boolean canTranslate(String line) {
		return line.replace(" ", "").startsWith("imp");
	}

	@Override
	public void translate(String line, Importer importer, List<String> output) {
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			importer.importClass(matcher.group(1));
		}
	}

}
