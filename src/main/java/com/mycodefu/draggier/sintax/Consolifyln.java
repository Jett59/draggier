package com.mycodefu.draggier.sintax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Consolifyln implements Sintax {
	private final Pattern pattern = Pattern.compile("consolifyln\\((.*?)\\)");

	@Override
	public boolean executeCommand(String line) {
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			String content = matcher.group(1);
			content = content.replace("\\n", "\n");
			System.out.println(content);
			return true;
		}
		return false;
	}

}
