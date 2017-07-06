package com.fei.processor;

import com.fei.processor.annotation.Router;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

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
        generatetMethoBody(methodBuilder,classSet);
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
            method.addStatement("map.put($S,$S)", param, className);
        }
        method.addStatement("return map");
    }

    private TypeSpec.Builder getClassBuilder(String moduleName) {
        return TypeSpec.classBuilder(getClassName(moduleName))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    private MethodSpec.Builder getMethodBuilder() {
        return MethodSpec.methodBuilder(Method_NAME)
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(ParameterizedTypeName.get(HASH_MAP, STRING, STRING))
                .addStatement("$T<String,String> map = new $T<>()", HASH_MAP, HASH_MAP);
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

    private String getPackageName(String moduleName) {
        return String.format(PACKAGE_NAME, moduleName);
    }

    private String getClassName(String moduleName) {
        String name = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(CLASSS_NAME, name));
        return String.format(CLASSS_NAME, name);
    }

    private void writeToLocal(TypeSpec.Builder classBuilder, String moduleName) {
        TypeSpec typeSpec = classBuilder.build();
        JavaFile javaFile = JavaFile.builder(getPackageName(moduleName), typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException ioexcption) {
            throw new RuntimeException(ioexcption);
        }
    }

}
