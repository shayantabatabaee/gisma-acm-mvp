package com.gisma.competition.acm.executor.compiler;

import lombok.Getter;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Getter
public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private final Map<String, ByteArrayOutputStream> classBytes = new HashMap<>();

    public InMemoryFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        classBytes.put(className, baos);
        return new SimpleJavaFileObject(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.CLASS.extension), JavaFileObject.Kind.CLASS) {
            @Override
            public OutputStream openOutputStream() {
                return baos;
            }
        };
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return new CustomClassLoader(classBytes);
    }
}
