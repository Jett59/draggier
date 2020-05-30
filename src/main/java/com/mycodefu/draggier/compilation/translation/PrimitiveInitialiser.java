package com.mycodefu.draggier.compilation.translation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycodefu.draggier.compilation.imports.Importer;

public class PrimitiveInitialiser implements Translator{
private static Pattern pattern = Pattern.compile("^\\s+priminit\\s+::\\s+(.*?)\\s+-\\s+\\((.*?)\\)\\s+(.*?)$");

	@Override
	public boolean canTranslate(String line) {
		return line.replace(" ", "").startsWith("priminit::");
	}

	@Override
	public void translate(String line, Importer importer, List<String> output) {
		System.out.println("matching line "+line);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			System.out.println("matched!");
			String primitiveType = matcher.group(1);
			String args = matcher.group(2);
			String name = matcher.group(3);
			output.add(String.format("%s %s = %s;", primitiveType, name, args));
		}
	}

}
