package com.mycodefu.draggier.compilation.translation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.imports.Importer;

public class JavaFunctionRunner implements Translator {
private static final Pattern pattern = Pattern.compile("^\\s+run:\\s+(.*?):\\s+(.*?)\\s+-\\s+\\((.*?)\\)$");

	@Override
	public boolean canTranslate(String line) {
		String noSpacesLine = line.replace(" ", "");
		return noSpacesLine.startsWith("run:");
	}

	@Override
	public void translate(String line, Importer importer, List<String> output) {
Matcher matcher = pattern.matcher(line);

if(matcher.find()) {
	String claz = matcher.group(1);
	String function = matcher.group(2);
	String params = matcher.group(3);
	output.add(claz+"."+function+"("+params+");");
}
	}

}
