package com.mycodefu.draggier.compilation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.mycodefu.draggier.compilation.ClassDefiner.ClassConfig;
import com.mycodefu.draggier.compilation.PackageDefiner.PackageConfig;
import com.mycodefu.draggier.compilation.imports.Importer;
import com.mycodefu.draggier.compilation.translation.*;

public class Compiler {
	private static Translator[] translators = new Translator[] {
			new Import(),
			new MainMethod(),
			new Instance(),
			new JavaFunctionRunner(),
			new CloseBody()
	};

public static void compile(String projectDir, String mainClass, Importer importer, ClassDefiner classDefiner, PackageDefiner packageDefiner, Maven maven) {
	String mainClassPath = projectDir+"\\"+mainClass;
	System.out.println("compiling "+mainClassPath);
	if(Files.exists(Paths.get(mainClassPath))) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(mainClassPath));
			List<String> output = new ArrayList<>();
			ClassConfig classConfig = classDefiner.findClass(lines);
			PackageConfig packageConfig = packageDefiner.findPackage(lines);
			for(String line : lines) {
			for(Translator translator : translators) {
				if(translator.canTranslate(line)) {
					translator.translate(line, importer, output);
					break;
				}
			}
			}
			List<String> finalCode = new ArrayList<>();
			finalCode.add(packageConfig.toString());
			finalCode.add(importer.toString());
			finalCode.add(classConfig.toString());
			for(String line : output) {
				finalCode.add(line);
			}
			finalCode.add("}");
			System.out.println("compiled to java:");
			for(String line : finalCode) {
				System.out.println(line);
			}
			StringBuilder generatedJavaPath = new StringBuilder()
					.append(projectDir)
					.append("\\generatedJava");
			StringBuilder packagePath = new StringBuilder()
					.append(generatedJavaPath)
					.append("\\src\\main\\java")
					.append("\\")
					.append(packageConfig.getName());
			StringBuilder filePath = new StringBuilder()
			.append(packagePath)
			.append("\\"+classConfig.getName()+".java");
			System.out.println("writing "+filePath.toString());
			String[] individualPaths = packagePath.toString().split("\\\\");
					StringBuilder currentPath = new StringBuilder();
			for(String path : individualPaths) {
				if(path.equalsIgnoreCase("c:")) {
					currentPath.append("C:");
				}else {
				currentPath.append("\\"+path);
				if(Files.notExists(Paths.get(currentPath.toString()))) {
					Files.createDirectory(Paths.get(currentPath.toString()));
				}
				}
			}
			Files.write(Paths.get(filePath.toString()), finalCode);
			System.out.println("wrote "+filePath.toString());
			System.out.println("running maven build");
			maven.mavenCompile(generatedJavaPath.toString(), packageConfig.getName()+"."+classConfig.getName());
			System.out.println("maven built");
		} catch (IOException e) {
			throw new CompilationException(e);
		}
	}else {
		throw new CompilationException(String.format("cannot find file %s", projectDir));
	}
}
}
