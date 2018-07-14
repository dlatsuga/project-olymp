package org.pantheon.filemanager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Aspect
public class MyLogger {
    @Pointcut("execution(* org.pantheon.filemanager.Manager.*(..))")
    private void allMethod() {
    }

    @Around("allMethod()")
    public Object watchTime(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        System.out.println("method begin: " + joinPoint.getSignature().toShortString());
        Object output = null;
        try {
            output = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("method end: " + joinPoint.getSignature().toShortString() + ", time=" + time + " ms");
        return output;
    }

    @AfterReturning(value = "allMethod() && execution(java.util.Set *(..))", returning = "obj")
    public void printSet(Object obj) {
        System.out.println("print info begin >>");
            Set set = (Set) obj;
            for (Object o : set) {
                System.out.println(o);
            }
        System.out.println("print info end <<");
    }

    @AfterReturning(value = "allMethod() && execution(java.util.Map *(..))", returning = "obj")
    public void printMap(Object obj) {
        System.out.println("print info begin >>");
            Map map = (Map) obj;
            for (Object o : map.keySet()) {
                System.out.println("key = " + o + "," + map.get(o));
            }
        System.out.println("print info end <<");
    }
}
