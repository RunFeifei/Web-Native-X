package com.fei.processor;

import com.fei.processor.annotation.Router;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(javax.annotation.processing.Processor.class)
public class ClassProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.route.%1s";
    private static final String CLASSS_NAME = "%1sRouteMap";
    private static final String Method_NAME = "getRouteMap";

    private static final ClassName HASH_MAP = ClassName.get("java.util", "HashMap");
    private static final ClassName STRING = ClassName.get("java.lang", "String");
    private static final ClassName CLASS = ClassName.get("java.lang", "Class");


    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(Arrays.asList(new String[] {
                Router.class.getCanonicalName(),
        }));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> classSet = roundEnv.getElementsAnnotatedWith(Router.class);
        if (classSet != null && classSet.size() > 0) {
            messager.printMessage(Diagnostic.Kind.NOTE, classSet.toString());
            generateCode(classSet);
        }
        return true;
    }

    private void generateCode(Set<? extends Element> classSet) {
        String moduleName = getModuleName(classSet);
        TypeSpec.Builder classBuilder = getClassBuilder(moduleName);
        MethodSpec.Builder methodBuilder = getMethodBuilder();
        generatetMethoBody(methodBuilder, classSet);
        classBuilder.addMethod(methodBuilder.build());
        writeToLocal(classBuilder, moduleName);
    }

    private void generatetMethoBody(MethodSpec.Builder method, Set<? extends Element> classSet) {
        for (Element element : classSet) {
            String className = element.toString();
            if (className == null || className.length() == 0) {
                continue;
            }
            Router router = element.getAnnotation(Router.class);
            String param = router == null ? null : router.param();
            method.addStatement("map.put($S,$T.class)", param, ClassName.get(getPackageName(className), getSimpleName(className)));
        }
        method.addStatement("return map");
    }

    private TypeSpec.Builder getClassBuilder(String moduleName) {
        return TypeSpec.classBuilder(getFileName(moduleName))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    private MethodSpec.Builder getMethodBuilder() {
        return MethodSpec.methodBuilder(Method_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(HASH_MAP, STRING, ParameterizedTypeName.get(CLASS, WildcardTypeName.subtypeOf(Object.class))))
                .addStatement("$T<String,Class<?>> map = new $T<>()", HASH_MAP, HASH_MAP);
    }

    private String getModuleName(Set<? extends Element> classSet) {
        String moduleName = null;
        for (Element element : classSet) {
            Router router = element.getAnnotation(Router.class);
            moduleName = router == null ? null : router.module();
            if (moduleName != null) {
                return moduleName;
            }
        }
        throw new RuntimeException("should define moduleValue to Router annotation");
    }

    /**
     * 获得生成文件的包名
     */
    private String getFilePackageName(String moduleName) {
        return String.format(PACKAGE_NAME, moduleName);
    }

    /**
     * 获得生成文件的文件名
     */
    private String getFileName(String moduleName) {
        String name = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(CLASSS_NAME, name));
        return String.format(CLASSS_NAME, name);
    }

    /**
     * @param className java.util.Set
     * @return java.util
     */
    private String getPackageName(String className) {
        int index = className.lastIndexOf(".");
        return className.substring(0, index);
    }

    /**
     * @param className java.util.Set
     * @return java.util
     */
    private String getSimpleName(String className) {
        int index = className.lastIndexOf(".");
        return className.substring(index+1, className.length());
    }

    private void writeToLocal(TypeSpec.Builder classBuilder, String moduleName) {
        TypeSpec typeSpec = classBuilder.build();
        JavaFile javaFile = JavaFile.builder(getFilePackageName(moduleName), typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException ioexcption) {
            throw new RuntimeException(ioexcption);
        }
    }

}
