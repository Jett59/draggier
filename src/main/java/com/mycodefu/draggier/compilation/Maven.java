package com.mycodefu.draggier.compilation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Maven {
public void mavenCompile(String projectPath, String mainClassPath) {
	try {
		Files.deleteIfExists(Paths.get(projectPath+"\\pom.xml"));
			String defaultPom = getDefaultPom();
			defaultPom = defaultPom.replace("-----insert main class here-----", mainClassPath);
			Files.write(Paths.get(projectPath+"\\pom.xml"), defaultPom.getBytes(StandardCharsets.UTF_8));
	ProcessBuilder draggierInstall = new ProcessBuilder(mavenCommandPath(), "install");
	ProcessBuilder p = new ProcessBuilder(mavenCommandPath(), "package");
		draggierInstall.redirectOutput(new File("maven-install.log"));
		System.out.println("installing draggier");
		long now = System.nanoTime();
		draggierInstall.start().waitFor();
		long finished = System.nanoTime();
		System.out.println("installed draggier, time: "+((finished-now)/1000000000d)+"s");
		ProcessBuilder mavenCompile = new ProcessBuilder(mavenCommandPath(), "compile");
		mavenCompile.directory(new File(projectPath));
		mavenCompile.redirectOutput(new File("maven-compile.log"));
		System.out.println("compiling draggier project");
		now = System.nanoTime();
		Process mavenCompileProcess = mavenCompile.start();
		mavenCompileProcess.waitFor();
		finished = System.nanoTime();
		System.out.println("finished compile, time: "+((finished-now)/1000000000d)+"s");
		if(mavenCompileProcess.exitValue() != 0) {
			System.out.println(new String(mavenCompileProcess.getErrorStream().readAllBytes()));
			throw new CompilationException("error: maven compile failed");
		}
		p.redirectOutput(new File("maven.log"));
		p.directory(new File(projectPath));
		System.out.println("packaging draggier project");
		now = System.nanoTime();
		p.start().waitFor();
		finished = System.nanoTime();
		System.out.println("packaged draggier project, time: "+((finished-now)/1000000000d)+"s");
	} catch (Exception e) {
		throw new CompilationException(e);
	}
	}

public String getDefaultPom() {
	InputStream in = getClass().getResourceAsStream("/maven/default.pom.xml");
	byte[] bytes;
	try {
		bytes = in.readAllBytes();
	} catch (IOException e) {
throw new CompilationException(e);
	}
			return new String(bytes, StandardCharsets.UTF_8);
}

public static String mavenCommandPath() {
	String result;
	String os = System.getProperty("os.name");
	System.out.println(os);
	switch(Os.forName(os)) {
	case win: {
		result = "mvn.cmd";
		break;
	}
	default:
		throw new RuntimeException("no valid case statement for os "+os);
	}
	return result;
}

public static enum Os {
	win("windows 10");
private String displayName;

private Os(String displayName) {
	this.displayName = displayName;
}

public static Os forName(String name) {
	for(Os value : Os.values()) {
		if(value.displayName.equalsIgnoreCase(name)) {
			return value;
		}
	}
	throw new IllegalArgumentException(name+" is not a valid os");
}
}
}
