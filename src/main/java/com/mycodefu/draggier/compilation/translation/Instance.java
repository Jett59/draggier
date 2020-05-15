package com.mycodefu.draggier.compilation.translation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.imports.Importer;

public class Instance implements Translator{
	private static final Pattern pattern = Pattern.compile("^\\s+new\\s+::\\s+(.*?)\\s-\\s\\((.*?)\\)\\s+(.*?)$");

	@Override
	public boolean canTranslate(String line) {
		return line.replace(" ", "").startsWith("new");
	}

	@Override
	public void translate(String line, Importer importer, List<String> output) {
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			String claz = matcher.group(1);
			String params = matcher.group(2);
			String instanceName = matcher.group(3);
			output.add(String.format("%s %s = new %s (%s);", claz, instanceName, claz, params));
		}
	}

}
