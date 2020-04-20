package com.mycodefu.draggier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Start {
public static void main(String[] args) throws Exception {
	List<String> code = Files.readAllLines(Paths.get(args[0]));
	Pattern printlnPattern = Pattern.compile("consolifyln\\((.*?)\\)");
	Pattern printfPattern = Pattern.compile("consolifyf\\((.*?), (.*?)\\)");
	for(String line : code) {
	Matcher printlnMatcher = printlnPattern.matcher(line);
			if(printlnMatcher.find()) {
				String content = printlnMatcher.group(1);
				content = content.replace("\\n", "\n");
				System.out.println(content);
			}
			Matcher printfMatcher = printfPattern.matcher(line);
			if(printfMatcher.find()) {
				String text = printfMatcher.group(1);
				String[] arguments = printfMatcher.group(2).split(", ");
				text = text.replace("\\n", "\n");
				System.out.printf(text, (Object[])arguments);
			}
}
}
}
