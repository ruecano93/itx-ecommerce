package com.itx.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    public static final String[] INFRA_WHITELIST = {"/actuator", "/actuator/**"};
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private ObjectMapper m;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);

        logRequest(cachedHttpServletRequest);
        final long startTime = System.currentTimeMillis();
        filterChain.doFilter(cachedHttpServletRequest, response);
        final long duration = System.currentTimeMillis() - startTime;
        log.info("The operation was processed: status={}, time={} ms", response.getStatus(), duration);
    }

    private void logRequest(CachedHttpServletRequest cachedHttpRquest) {

        String body = getMessagePayload(cachedHttpRquest.getInputStream());
        String method = cachedHttpRquest.getMethod();
        String url = Optional.ofNullable(cachedHttpRquest.getQueryString())
            .map(query -> String.join("?", cachedHttpRquest.getRequestURI(), query))
            .orElseGet(cachedHttpRquest::getRequestURI);
        log.info("Operation request: {} {}. With body: {}", method, url, body);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String requestUri = request.getRequestURI();

        return Arrays.stream(INFRA_WHITELIST)
            .anyMatch(pattern -> pathMatcher.match(pattern, requestUri));
    }

    @Nullable
    protected String getMessagePayload(final InputStream input) {

        if (input != null) {
            try {
                byte[] buf = input.readAllBytes();
                if (buf.length > 0) {
                    int length = Math.min(buf.length, getMaxPayloadLength());
                    return m.readTree(buf, 0, length).toString();
                }
            } catch (Exception ignored) {
                return "[unknown]";
            }
        }
        return "";
    }

    private int getMaxPayloadLength() {
        return 10000;
    }


    static class CachedHttpServletRequest extends HttpServletRequestWrapper {

        private byte[] cachedPayload;

        public CachedHttpServletRequest(HttpServletRequest request) {
            super(request);
            try {
                cachedPayload = request.getInputStream().readAllBytes();
            } catch (IOException ex) {
                cachedPayload = new byte[0];
            }
        }

        @Override
        public BufferedReader getReader() {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedPayload);
            return new BufferedReader(new InputStreamReader(byteArrayInputStream));
        }

        @Override
        public ServletInputStream getInputStream() {
            return new ServletInputStream() {
                final ByteArrayInputStream byteArray = new ByteArrayInputStream(cachedPayload);

                public boolean isFinished() {
                    return byteArray.available() == 0;
                }

                public boolean isReady() {
                    return true;
                }

                public void setReadListener(ReadListener readListener) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int read() {
                    return byteArray.read();
                }
            };
        }
    }
}