package com.gisma.competition.acm.util.compiler;

import com.gisma.competition.acm.api.exception.CompilationException;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryJavaCompiler {

    private final JavaCompiler compiler;
    private final StandardJavaFileManager standardFileManager;

    public InMemoryJavaCompiler() {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.standardFileManager = compiler.getStandardFileManager(null, null, null);
    }

    public Class<?> compile(String className, String code) throws CompilationException {
        InMemoryJavaFileObject fileObject = new InMemoryJavaFileObject(className, code);
        InMemoryFileManager fileManager = new InMemoryFileManager(this.standardFileManager);
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        Iterable<JavaFileObject> compilationUnits = Collections.singletonList(fileObject);
        JavaCompiler.CompilationTask task = this.compiler.
                getTask(null, fileManager, diagnostics, null, null, compilationUnits);

        boolean success = task.call();

        if (!success) {
            Map<String, String> errorDetails = new HashMap<>();
            CompilationException compilationException = new CompilationException();
            compilationException.setDetails(errorDetails);
            int i = 0;
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                    errorDetails.put(String.format("Error%d", i),
                            "Error on line number " + diagnostic.getLineNumber() + ", " + diagnostic.getMessage(null));
                    i++;
                }
            }
            throw compilationException;
        }
        try {
            return fileManager.getClassLoader(null).loadClass(className);
        } catch (ClassNotFoundException e) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("classNotFound", String.format("Class %s not found.", className));
            CompilationException compilationException = new CompilationException();
            compilationException.setDetails(errorDetails);
            throw compilationException;
        }
    }

}
