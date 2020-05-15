package com.mycodefu.draggier;

import com.mycodefu.draggier.compilation.ClassDefiner;
import com.mycodefu.draggier.compilation.Maven;
import com.mycodefu.draggier.compilation.PackageDefiner;
import com.mycodefu.draggier.compilation.imports.Importer;

public class Start {
	public static void main (String[] args) {
		if(args.length > 0) {
			System.out.println("starting draggier");
			for(int i = 0; i < args.length; i++) {
				String arg = args[i];
				if(arg.equalsIgnoreCase("-compile")) {
					com.mycodefu.draggier.compilation.Compiler.compile(args[(++i)], args[++i], new Importer(), new ClassDefiner(), new PackageDefiner(), new Maven());
				}
			}
		}else {
			throw new IllegalArgumentException("you must pass at least one argument");
		}
	}
	}
