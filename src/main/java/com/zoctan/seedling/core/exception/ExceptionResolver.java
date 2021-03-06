package com.zoctan.seedling.core.exception;

import com.zoctan.seedling.core.response.Result;
import com.zoctan.seedling.core.response.ResultGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Set;

/**
 * 统一异常处理
 *
 * @author Zoctan
 * @date 2018/5/27
 */
@RestControllerAdvice
public class ExceptionResolver {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handle400(final ConstraintViolationException e) {
        final Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        final StringBuilder strBuilder = new StringBuilder();
        for (final ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage()).append(",");
        }
        return ResultGenerator.genFailedResult(strBuilder.toString());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class, ServletException.class})
    public Result handle400(final Throwable e) {
        return ResultGenerator.genFailedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public Result handle400() {
        return ResultGenerator.genFailedResult("Database error");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public Result handle401(final Throwable e) {
        return ResultGenerator.genUnauthorizedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class, UsernameNotFoundException.class})
    public Result handle403(final Throwable e) {
        return ResultGenerator.genFailedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handle404(final HttpServletRequest request) {
        return ResultGenerator.genFailedResult("API [" + request.getRequestURI() + "] didn't existed");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handle405() {
        return ResultGenerator.genMethodErrorResult();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Result globalException(final HttpServletRequest request, final Throwable e) {
        final Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status == null) {
            return ResultGenerator.genInternalServerErrorResult(request.getRequestURI());
        }
        return ResultGenerator.genFailedResult(e.getMessage());
    }
}
