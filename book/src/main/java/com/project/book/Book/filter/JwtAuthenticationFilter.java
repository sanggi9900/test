package com.project.book.Book.filter; // 패키지 선언

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain; // 여기에서 jakarta.servlet으로 변경
import jakarta.servlet.ServletException; // 여기에서 jakarta.servlet으로 변경
import jakarta.servlet.http.HttpServletRequest; // 여기에서 jakarta.servlet으로 변경
import jakarta.servlet.http.HttpServletResponse; // 여기에서 jakarta.servlet으로 변경
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}") // application.properties에서 비밀 키 주입
    private String secretKey;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // HTTP 요청 헤더에서 Authorization 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 "Bearer"로 시작하는지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7); // "Bearer " 부분 제거

            try {
                // JWT에서 사용자 이름 가져오기
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(jwtToken)
                        .getBody();

                String username = claims.getSubject();

                // 사용자 정보를 기반으로 인증 객체 생성
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, userDetailsService.loadUserByUsername(username).getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // SecurityContext에 인증 정보 저장
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (ExpiredJwtException e) {
                // JWT 만료 처리
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is expired");
                return; // 필터 체인 중단
            } catch (Exception e) {
                // JWT 처리 중 발생한 예외 처리
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Could not authenticate token");
                return; // 필터 체인 중단
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
