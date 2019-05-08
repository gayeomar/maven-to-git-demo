package com.fdc.ucom.address.exception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fdc.ucom.address.enums.ReturnCode;
import com.fdc.ucom.address.service.AddressServiceResponse;
import com.fdc.ucom.address.service.to.AddressServiceResponseTO;

@Aspect
public class ExceptionAspect {

	@Around("execution(public * *(..)) && @annotation(AfterThrowingException)")
	public Object handleException(final ProceedingJoinPoint point) {
		final Logger log = LoggerFactory.getLogger(point.getSignature().getDeclaringType().getName());
		Object result = null;
		AddressServiceResponse response = null;
		final Signature signature = point.getSignature();
		final String methodName = signature.getName();
		final String methodSignature = signature.toString();
		try {
			result = point.proceed();
		} catch (final Throwable e) {
			if (e instanceof UComException) {
				response = this.ucomHostedPagesExceptionResponse((UComException) e);
			} else {
				response = this.unknownErrorReponse(e);
			}
			log.error(
					" \n ### An Exception was thrown from the method : " + methodName + "\n ### the full toString is: "
							+ methodSignature + "\n ### the exception message is: " + e.getMessage(),
					e);
		}
		return response != null ? response : result;
	}

	private AddressServiceResponse ucomHostedPagesExceptionResponse(final UComException ex) {
		final AddressServiceResponseTO response = new AddressServiceResponseTO();
		response.setReturnCode(ReturnCode.FAILED.getCode());

		String errorMessage = ex.getMessage();
		if (errorMessage == null) {
			errorMessage = ex.getCode().getMessage();
		}

		response.setReturnMessage(errorMessage);

		return new AddressServiceResponse(HttpStatus.OK.name(), response);
	}

	private AddressServiceResponse unknownErrorReponse(final Throwable ex) {
		final AddressServiceResponseTO response = new AddressServiceResponseTO();
		response.setReturnCode(ReturnCode.FAILED.getCode());
		String message = "";
		if (ex != null) {
			message = ex.getMessage();
		}
		response.setReturnMessage("Unknown error occurred while processing request: " + message);

		return new AddressServiceResponse(HttpStatus.OK.name(), response);
	}
}
