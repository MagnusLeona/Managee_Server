package com.magnus.project.managee.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class MagnusContext {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.registerBean(MagnusConfig.class);
        annotationConfigApplicationContext.registerBean(MagnusComponent.class);
        annotationConfigApplicationContext.refresh();

        Dog bean = annotationConfigApplicationContext.getBean(Dog.class);
        Dog bean1 = annotationConfigApplicationContext.getBean(Dog.class);
        System.out.println(bean == bean1);

        Cat bean2 = annotationConfigApplicationContext.getBean(Cat.class);
        Cat bean3 = annotationConfigApplicationContext.getBean(Cat.class);
        System.out.println(bean2 == bean3);

        Dog anotherDog = (Dog) annotationConfigApplicationContext.getBean("anotherDog");

        System.out.println(anotherDog == bean);


        Cat anotherCat = (Cat) annotationConfigApplicationContext.getBean("anotherCat");
        System.out.println(anotherCat == bean2);


        ClassLoader classLoader = MagnusContext.class.getClassLoader();
        Enumeration<URL> resources = classLoader.getResources("META-INF/spring.factories");
        while(resources.hasMoreElements()) {
            System.out.println(resources.nextElement());
        }
    }
}
