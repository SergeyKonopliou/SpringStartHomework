package by.homework.mvc.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 
 * Класс по принципу АОП для логирования
 * 
 */

@Aspect
@Component
public class AdviceLoggable {

	private Logger logger = Logger.getLogger(this.getClass());
		
	@Pointcut("within(by.homework.mvc..*)")
	public void loggableMethod() {
		
	}
	
	@AfterThrowing(value = "loggableMethod()",throwing = "exception")
	public void recordErrors(JoinPoint joinPoint, Exception exception) {
		logger.error("Method " + joinPoint.getSignature().getName() + " class "
				+ joinPoint.getSourceLocation().getWithinType().getName() + " was aborted with an exception "
				+ exception + "\n");
		System.out.println("1");
	}
	
	@Before("loggableMethod()")
	public void beforeMethodExecuting(JoinPoint joinPoint) {
		String args = Arrays.stream(joinPoint.getArgs()).map(a -> a.toString()).collect(Collectors.joining(","));
		logger.info("Before method " + joinPoint.getSignature().getName() + ",with args=[" + args + "]");
		System.out.println("2");
	}

	@AfterReturning(value = "loggableMethod()", returning = "returningValue")
	public void afterMethodExecuting(JoinPoint joinPoint, Object returningValue) {
		if (returningValue != null) {
			logger.info("After method " + joinPoint.getSignature().getName() + ",with result " + returningValue);
		} else {
			logger.info("After method " + joinPoint.getSignature().getName() + ",without returning result");
		}
		System.out.println("3");
	}
}
