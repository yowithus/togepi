package com.example.togepi.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class LogFilter extends OncePerRequestFilter {

    private static final String REQUEST_FORMAT = "Request -> "
            + "method=%s; "
            + "uri=%s%s; "
            + "headers=%s; "
            + "body=%s";

    private static final String RESPONSE_FORMAT = "Response -> "
            + "httpStatus=%s; "
            + "headers=%s; "
            + "body=%s";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        final ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            MDC.put("requestId", UUID.randomUUID().toString());
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequest(requestWrapper);
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper requestWrapper) {
        final String method = Optional.ofNullable(requestWrapper.getMethod()).orElse("");
        final String uri = Optional.ofNullable(requestWrapper.getRequestURI()).orElse("");
        final String queryString = Optional.ofNullable(requestWrapper.getQueryString()).map(param -> new StringBuilder("?").append(param).toString()).orElse("");
        final String headers = Optional.ofNullable(getRequestHeaders(requestWrapper)).orElse("");
        final String body = Optional.ofNullable(getRequestBody(requestWrapper)).orElse("");

        log.info(String.format(REQUEST_FORMAT, method, uri, queryString, headers, body));
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) {
        final String httpStatus = Optional.ofNullable(String.valueOf(responseWrapper.getStatusCode())).orElse("");
        final String headers = Optional.ofNullable(getResponseHeaders(responseWrapper)).orElse("");
        final String body = Optional.ofNullable(getResponseBody(responseWrapper)).orElse("");

        log.info(String.format(RESPONSE_FORMAT, httpStatus, headers, body));
    }

    private String getRequestHeaders(ContentCachingRequestWrapper requestWrapper) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        final Enumeration headerNames = requestWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = (String) headerNames.nextElement();
            requestHeaders.add(headerName, requestWrapper.getHeader(headerName));
        }
        return requestHeaders.toString();
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        try {
            final byte[] contents = requestWrapper.getContentAsByteArray();
            final String stringContents = new String(contents, 0, contents.length, requestWrapper.getCharacterEncoding());
            final String replacedContents = StringUtils.replaceEach(stringContents, new String[]{"\n", "\r", "\t"}, new String[]{"", "", ""});
            return replacedContents;
        } catch (Exception e) {
//            log.error("Error when reading request body {}", e);
            return "";
        }
    }

    private String getResponseHeaders(ContentCachingResponseWrapper responseWrapper) {
        final HttpHeaders responseHeaders = new HttpHeaders();
        for (String headerName : responseWrapper.getHeaderNames()) {
            responseHeaders.add(headerName, responseWrapper.getHeader(headerName));
        }
        return responseHeaders.toString();
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        try {
            final byte[] contents = responseWrapper.getContentAsByteArray();
            final String stringContents = new String(contents, 0, contents.length, responseWrapper.getCharacterEncoding());
            final String replacedContents = StringUtils.replaceEach(stringContents, new String[]{"\n", "\r", "\t"}, new String[]{"", "", ""});
            return replacedContents;
        } catch (Exception e) {
//            log.error("Error when reading response body {}", e);
            return "";
        }
    }
}
