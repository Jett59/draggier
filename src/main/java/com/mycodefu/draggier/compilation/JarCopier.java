package com.mycodefu.draggier.compilation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JarCopier {
public void copyJar(String jarPath) {
	try {
		File file = new File(jarPath);
		String newPath = System.getenv("Draggier_home")+"\\"+file.getName();
		System.out.printf("copying %s to %s\n", jarPath, newPath);
		long now = System.nanoTime();
		Files.deleteIfExists(Paths.get(newPath));
		Files.copy(file.toPath(), Paths.get(newPath));
		long finished = System.nanoTime();
		finished -= now;
		System.out.printf("copyed %s to %s, time: %ss\n", jarPath, newPath, Double.toString(finished/1000000000d));
	}catch (Exception e) {
		throw new CompilationException(e);
	}
}
}
