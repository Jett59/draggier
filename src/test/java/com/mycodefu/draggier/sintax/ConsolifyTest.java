package com.mycodefu.draggier.sintax;

import org.mockito.Mockito;
import com.mycodefu.draggier.compilation.CompilationException;
import com.mycodefu.draggier.memory.MemoryStorage;

import org.junit.jupiter.api.Test;

public class ConsolifyTest {
@Test
public void executeCommand() throws CompilationException {
	MemoryStorage mockedMemoryStorage = Mockito.mock(MemoryStorage.class);
	Consolify consolify = new Consolify();
	consolify.executeCommand("consolify(var=>test)", mockedMemoryStorage);
	Mockito.verify(mockedMemoryStorage).getString("test");
}
}
