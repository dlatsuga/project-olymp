package org.pantheon.filemanager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class MyLogger {
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

    public void print(Object obj) {
        System.out.println("print info begin >>");
        if (obj instanceof Set){
            Set set = (Set) obj;
            for (Object o : set) {
                System.out.println(o);
            }
        }

        else if(obj instanceof Map){
            Map map = (Map) obj;
            for (Object o : map.keySet()) {
                System.out.println("key = " + o + "," + map.get(o));
            }
        }
        System.out.println("print info end <<");
    }
}
