package com.mycodefu.draggier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.sintax.*;

public class Start {
	private static final Sintax[] commands = new Sintax[] {
			new Consolifyln(),
			new Consolifyf()
	};

public static void main(String[] args) throws Exception {
	List<String> code = Files.readAllLines(Paths.get(args[0]));
	for(String line : code) {
		boolean lineHasCommand = false;
	for(Sintax command : commands) {
		if(command.executeCommand(line)) {
			lineHasCommand = true;
		}
	}
	if(!lineHasCommand) {
		throw new CompilationException("the line "+line+" has no valid lines");
	}
}
}
}
