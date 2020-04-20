package com.mycodefu.draggier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Start {
public static void main(String[] args) throws Exception {
	List<String> code = Files.readAllLines(Paths.get(args[0]));
	Pattern printPattern = Pattern.compile("consolifyln\\((.*?)\\)");
	for(String line : code) {
	Matcher printMatcher = printPattern.matcher(line);
			if(printMatcher.find()) {
				String content = printMatcher.group(1);
				System.out.println(content);
			}
}
}
}
