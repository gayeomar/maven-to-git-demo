package com.omar.calendar.exception;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * Logging Aspect: use for all generic and recurring logging.
 * <p>
 * When custom/specific logging is needed, use (with moderation) the SPLUNK
 * friendly <code>CommonLogger</code>
 *
 * @author Omar.Gaye
 *
 *         Created 02/06/2017 Last modified: 02/06/2017
 *
 */
@Aspect
public class LoggingAspect {

	@Pointcut(value = "execution(public * *(..))")
	public void anyPublicMethod() {
	}

	/**
	 * Logs method call with response (max. 200 characters) as well as duration
	 * in milliseconds.
	 *
	 * @param point
	 *            joint point to be called
	 * @return return value of the joint point
	 * @throws Throwable
	 *             in case the joint point throws an excpetion
	 */
	@Around("execution(public * *(..)) && @annotation(AroundLog)")
	public Object around(final ProceedingJoinPoint point) throws Throwable {
		System.out.println("***************************  ASPECT CALLED ************");
		final long start = System.currentTimeMillis();
		////final Logger log = LoggerFactory.getLogger(point.getSignature().getDeclaringType().getName());
		Object result = null;
		Throwable t = null;
		try {
			result = point.proceed();
		} catch (final Exception e) {
			t = e;
		}
		final Signature signature = point.getSignature();
		final String methodName = signature.getName();
		final String arguments = Arrays.toString(point.getArgs());
		String sResult = result == null ? "EXCEPTION" : result.toString();
		if (sResult.length() > 200) {
			sResult = sResult.substring(0, 200);
		}
		//log.info("method: " + methodName + " args: " + arguments + " returns: " + sResult);
		final Long duration = System.currentTimeMillis() - start;
		//log.info("durationInMs:" + duration.toString());
		if (t != null) {
			throw t;
		}
		return result;
	}


}
