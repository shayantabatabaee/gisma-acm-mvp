package com.gisma.competition.acm.util.compiler;

import com.gisma.competition.acm.api.exception.CompilationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class InMemoryJavaExecutor {

    public static Object execute(Class<?> loadedClass, String methodName,
                                 Object[] parameters, Class<?>... parameterTypes) throws CompilationException {
        try {
            Object instance = loadedClass.getDeclaredConstructor().newInstance();
            Method method = loadedClass.getMethod(methodName, parameterTypes);

            return method.invoke(instance, parameters);
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            CompilationException compilationException = new CompilationException(e);
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("Execute", e.getMessage());
            compilationException.setDetails(errorDetails);
            throw compilationException;
        }
    }
}
