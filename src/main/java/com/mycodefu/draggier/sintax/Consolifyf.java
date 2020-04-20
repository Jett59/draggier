package com.mycodefu.draggier.sintax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Consolifyf implements Sintax{
	private final Pattern pattern = Pattern.compile("consolifyf\\((.*?), (.*?)\\)");

	@Override
	public boolean executeCommand(String line) {
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			String text = matcher.group(1);
			String[] arguments = matcher.group(2).split(", ");
			text = text.replace("\\n", "\n");
			System.out.printf(text, (Object[])arguments);
			return true;
		}
		return false;
	}

}
