package medilux.aquabe.auth;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import medilux.aquabe.domain.user.entity.UserEntity;
import medilux.aquabe.domain.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String googleToken = request.getHeader("X-Google-Token");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            // JWT 토큰 처리
            String token = authorization.split(" ")[1].trim();
            processJwtToken(token, request, response, filterChain);
        }

        filterChain.doFilter(request, response);
    }

    private void processJwtToken(String token, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (JwtTokenUtil.isExpired(token, secretKey)) {
                filterChain.doFilter(request, response);
                return;
            }

            String email = JwtTokenUtil.getEmail(token, secretKey);
            UserEntity loginUser = userService.getLoginUserByEmail(email);

            // fixme : 임시로 admin으로 설정, 추후 사용자 권한 추가하여 설정 필요
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    email, null, List.of(new SimpleGrantedAuthority("ADMIN")));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
        }
    }

}
