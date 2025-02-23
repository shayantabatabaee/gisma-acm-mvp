package com.gisma.competition.acm.util.compiler;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CustomClassLoader extends ClassLoader {

    private final Map<String, byte[]> classBytes;

    public CustomClassLoader(Map<String, ByteArrayOutputStream> classBytes) {
        this.classBytes = new HashMap<>(classBytes.size());
        for (var entry : classBytes.entrySet()) {
            this.classBytes.put(entry.getKey(), entry.getValue().toByteArray());
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = this.classBytes.get(name);
        if (bytes == null) {
            throw new ClassNotFoundException(name);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}
