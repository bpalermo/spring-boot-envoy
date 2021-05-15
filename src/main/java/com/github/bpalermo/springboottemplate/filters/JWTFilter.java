package com.github.bpalermo.springboottemplate.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@Component
@Order(1)
public class JWTFilter implements Filter {

    public static final String HEADER_JWT_PAYLOAD = "x-jwt-payload";
    public static final String ATTRIBUTE_JWT_SUBJECT = "x-jwt-subject";

    private static ObjectMapper mapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mapper = new ObjectMapper();
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String payload = httpReq.getHeader(HEADER_JWT_PAYLOAD);
        if (payload == null || payload.isBlank()) {
            httpReq.setAttribute(ATTRIBUTE_JWT_SUBJECT, "");
            chain.doFilter(request, response);
            return;
        }
        String jwt = new String(Base64.getDecoder().decode(payload));

        JsonNode jsonNode = mapper.readTree(jwt);

        httpReq.setAttribute(ATTRIBUTE_JWT_SUBJECT, jsonNode.get("sub").asText());
        chain.doFilter(request, response);
    }
}
