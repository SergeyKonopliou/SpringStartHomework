package util;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 
 * Create aspect,pointcut and advice(@Around).Binding advice and annotations
 *
 */
@Aspect
public class ProductAnnotationAspect {

	@Pointcut("@annotation(util.Timed)")
	public void callTimedAnnotation() {

	}

	@Around("callTimedAnnotation()")
	public Object calculateMethodWorkTime(ProceedingJoinPoint proceedingJoinPoint) {
		System.out.println("Method [" + proceedingJoinPoint.getSignature().getName() + "] start");
		System.out.println("Method's arguments : " + Arrays.toString(proceedingJoinPoint.getArgs()));
		Long workTime = (long) 0;
		// если не возвращать Object при проодолжении вызова метода
		// proceedingJoinPoint.proceed(),то будет возвращаться
		// null(в дебаггере пишет что то вроде Source not found)
		Object object = null;
		Long currentTime = System.currentTimeMillis();
		try {
			object = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			System.out.println(e);
		}
		workTime = System.currentTimeMillis() - currentTime;
		System.out.println("Method [" + proceedingJoinPoint.getSignature() + "] end. Runtime,ms " + workTime);
		return object;
	}
}
