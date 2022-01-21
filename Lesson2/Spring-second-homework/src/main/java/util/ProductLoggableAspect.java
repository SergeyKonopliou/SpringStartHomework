package util;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ProductLoggableAspect {

	private Logger logger = Logger.getLogger(this.getClass());

	@Pointcut("within(dao.ProductDao..*) || within(service..*)")
	public void methodExecuting() {
	}

	@AfterThrowing(value = "methodExecuting()", throwing = "exception")
	public void recordFailedExecution(JoinPoint joinPoint, Exception exception) {
		logger.error("Method " + joinPoint.getSignature().getName() + " class "
				+ joinPoint.getSourceLocation().getWithinType().getName() + " was aborted with an exception "
				+ exception + "\n");
	}

	@Before("methodExecuting()")
	public void beforeMethodExecuting(JoinPoint joinPoint) {
		String args = Arrays.stream(joinPoint.getArgs()).map(a -> a.toString()).collect(Collectors.joining(","));
		logger.info("Before method " + joinPoint.getSignature().getName() + ",with args=[" + args + "]");
	}

	@AfterReturning(value = "methodExecuting()", returning = "returningValue")
	public void afterMethodExecuting(JoinPoint joinPoint, Object returningValue) {
		if (returningValue != null) {
			logger.info("After method " + joinPoint.getSignature().getName() + ",with result " + returningValue);
		} else {
			logger.info("After method " + joinPoint.getSignature().getName() + ",without returning result");
		}
	}

}
