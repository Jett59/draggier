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
	if(Files.notExists(Paths.get(projectPath+"\\pom.xml"))) {
			String defaultPom = getDefaultPom();
			defaultPom = defaultPom.replace("-----insert main class here-----", mainClassPath);
			Files.write(Paths.get(projectPath+"\\pom.xml"), defaultPom.getBytes(StandardCharsets.UTF_8));
	}
	ProcessBuilder draggierInstall = new ProcessBuilder("mvn.cmd", "install");
	ProcessBuilder p = new ProcessBuilder("mvn.cmd", "clean", "package");
		draggierInstall.redirectOutput(new File("maven-install.log"));
		draggierInstall.start();
		p.redirectOutput(new File("maven.log"));
		p.directory(new File(projectPath));
		p.start();
	} catch (IOException e) {
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
}
