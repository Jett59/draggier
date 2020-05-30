package com.mycodefu.draggier.compilation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JarCopier {
public void copyJar(String jarPath) {
	try {
		File file = new File(jarPath);
		String newPath = System.getenv("Draggier_home")+"\\"+file.getName();
		Files.copy(file.toPath(), Paths.get(newPath));
	}catch (Exception e) {
		throw new CompilationException(e);
	}
}
}
