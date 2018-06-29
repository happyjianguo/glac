package net.engining.sccc.mgm.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.web.WebCommonUtils;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
public class JWTBasicAuthenticationFilter extends BasicAuthenticationFilter {
	
	/**
	 * 需要定义共用常量 JWT secret key
	 */
	private String signingKey = WebCommonUtils.SE_JWT_SIGNKEY;
	
	/**
	 * @param authenticationManager
	 */
	public JWTBasicAuthenticationFilter(AuthenticationManager authenticationManager, String signingKey) {
		super(authenticationManager);
		this.signingKey = signingKey;
	}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
			Claims claims = Jwts.parser()
								.setSigningKey(signingKey)
								.parseClaimsJws(token.replace("Bearer ", ""))
								.getBody();
			//从JWT中获取登录ID
			String user = claims.getSubject();
			//从JWT中获取权限
			String authorities =(String) claims.get("authorities");
			List<String> authoritiesLs = Splitter.on(",").trimResults().splitToList(authorities);
			Collection<GrantedAuthority> grantedAuthorities = Collections2.transform(authoritiesLs, new Function<String, GrantedAuthority>() {

				@Override
				@Nullable
				public GrantedAuthority apply(@Nullable String input) {
					return new SimpleGrantedAuthority(input);
				}
			});
            if (StringUtils.isNoneBlank(user) && !grantedAuthorities.isEmpty()) {
                return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
            }
            else {
            	throw new ErrorMessageException(ErrorCode.Restricted, "用户登录账户不合法");
            }
        }
        return null;
    }

}
