package com.mycodefu.draggier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.mycodefu.draggier.assignments.Assigner;
import com.mycodefu.draggier.bodies.*;
import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;
import com.mycodefu.draggier.sintax.*;

public class Start {
	private static final Body[] bodyTypes = new Body[] {
			new Iter(),
			new If()
	};
	private static final Sintax[] commands = new Sintax[] {
			new Consolify(),
			new Consolifyln(),
			new Consolifyf()
	};

public static void main(String[] args) throws Exception {
	if(args.length < 1) {
		throw new CompilationException("you must specify the file to run as an argument");
	}else if(Files.notExists(Paths.get(args[0]))) {
		throw new IllegalArgumentException("the file "+args[0]+"does not exist");
	}
	MemoryStorage memory = new MemoryStorage(1048576);
	Assigner assigner = new Assigner();
	List<String> code = Files.readAllLines(Paths.get(args[0]));
	boolean readingBody = false;
	Body body = null;
	String openingLine = null;
	List<String> bodyLines = new ArrayList<>();
	for(String line : code) {
		if(line.endsWith(""+Body.BODY_OPEN_CHAR)) {
			for(Body b : bodyTypes) {
				if(b.identifiedIn(line)) {
					readingBody = true;
					body = b;
					openingLine = line;
					break;
				}
			}
			continue;
		}
		if(readingBody) {
			if(line.contains(Body.BODY_CLOSE_CHAR)) {
				readingBody = false;
				body.run(commands, bodyLines, openingLine, memory);
				bodyLines.clear();
				continue;
			}else {
				bodyLines.add(line);
				continue;
			}
		}
		if(line.startsWith("var")) {
			assigner.assign(line, memory);
		}
	for(Sintax command : commands) {
		if(command.executeCommand(line, memory)) {
			break;
		}
	}
}
}
}
