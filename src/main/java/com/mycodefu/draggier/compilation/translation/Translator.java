package com.mycodefu.draggier.compilation.translation;

import java.util.List;

import com.mycodefu.draggier.compilation.imports.Importer;

public interface Translator {
boolean canTranslate(String line);
void translate(String line, Importer importer, List<String> output);
}
