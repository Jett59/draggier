package com.mycodefu.draggier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.mycodefu.draggier.assignments.Assigner;
import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.sintax.*;

public class Start {
	private static final Sintax[] commands = new Sintax[] {
			new Consolify(),
			new Consolifyln(),
			new Consolifyf()
	};

public static void main(String[] args) throws Exception {
	MemoryStorage memory = new MemoryStorage(1048576);
	Assigner assigner = new Assigner();
	List<String> code = Files.readAllLines(Paths.get(args[0]));
	for(String line : code) {
		boolean lineHasCommand = false;
		if(line.startsWith("var")) {
			assigner.assign(line, memory);
			lineHasCommand = true;
		}
	for(Sintax command : commands) {
		if(command.executeCommand(line, memory)) {
			lineHasCommand = true;
			break;
		}
	}
	if(!lineHasCommand) {
		throw new CompilationException("the line "+line+" has no valid lines");
	}
}
}
}
