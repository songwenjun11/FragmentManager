package com.example.manager_prossion;

import com.example.note.RegisterFragment;
import com.google.auto.service.AutoService;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * 功能描述
 *
 * @author SongWenjun
 * @since 2022-06-10
 */
@AutoService(Processor.class)
public class ManagerProcess extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new TreeSet<>();
        types.add(RegisterFragment.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        List<String> typeNames = new ArrayList<>();
        for (TypeElement type : set) {
            Set<? extends Element> annotatedWith = roundEnvironment.getElementsAnnotatedWith(RegisterFragment.class);
            Map<String, RegisterFragment> map = new HashMap<>();
            for (Element element : annotatedWith) {
                if (element instanceof TypeElement) {
                    TypeElement typeElement = (TypeElement) element;
                    RegisterFragment annotation = typeElement.getAnnotation(RegisterFragment.class);
                    if (annotation != null) {
                        String value = typeElement.getQualifiedName().toString();
                        map.put(value, annotation);
                    }
                }
            }

            StringBuffer sb = new StringBuffer();
            String keyName = null;
            if (map.size() > 0) {
                for (String string : map.keySet()) {
                    String[] split = string.split("\\.");
                    if (split.length > 0) {
                        keyName = split[split.length - 1];
                    }
                    break;
                }
            }
            if (keyName == null) {
                keyName = System.currentTimeMillis() + "";
            }
            String clazzName = "FragmentAddManager" + keyName;
            if (typeNames.contains(clazzName)) {
                clazzName += "2";
            }
            clazzName += "Imp";
            String packageName = type.getQualifiedName().toString();
            sb.append("package fragment.easy.com;\n\n")
                    .append("import static com.example.note.enums.LaunchMode.STANDARD;\n")
                    .append("import static com.example.note.enums.LaunchMode.SINGLE_INSTANCE;\n")
                    .append("import static com.example.note.enums.LaunchMode.SINGLE_TASK;\n")
                    .append("import static com.example.note.enums.LaunchMode.SINGLE_TOP;\n\n")
                    .append("import com.model.fragmentmanager.interfaces.IFragmentPut;\n\n")
                    .append("import com.example.note.enums.LaunchMode;\n\n")
                    .append("import com.model.fragmentmanager.tools.FragmentManager;\n\n")
                    .append("import java.util.Map;\n\n")
                    .append("public class " + clazzName + " implements IFragmentPut{\n")
                    .append("\t@Override\n")
                    .append("\tpublic void put() {\n");
            for (String key : map.keySet()) {
                sb.append(
                        "\t\tFragmentManager.getInstance().addFragment(\"" + map.get(key).action()[0] + "\", " +
                                "\"" + map.get(key).launchMode() + "\"" +
                                ", \"" + key + "\");\n");
            }
            sb.append("\t}\n");
            sb.append("}");
            Writer writer = null;
            try {
                JavaFileObject sourceFile = filer.createSourceFile(clazzName);
                writer = sourceFile.openWriter();
                writer.write(sb.toString());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.ERROR, "error catch funcation:" + e.getMessage());
            }
            typeNames.add(clazzName);
        }
        return false;
    }
}
