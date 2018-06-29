package net.engining.sccc.mgm.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import net.engining.pg.support.core.exception.ErrorCode;
import net.engining.pg.support.core.exception.ErrorMessageException;
import net.engining.pg.web.bean.WebLoginUser;

/**
 * RESTful风格的用户登录验证Filter， 继承{@link UsernamePasswordAuthenticationFilter},
 * 用{@link WebLoginUser}的子类代替直接从request获取username和password，而是从；<br>
 * 重写attemptAuthentication ：接收并解析用户凭证。<br>
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 * 
 * @author Eric Lu
 */
public class RESTfulUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private boolean postOnly = true;

	public RESTfulUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
		super();
		super.setAuthenticationManager(authenticationManager);
		super.setAuthenticationSuccessHandler(successHandler);
		super.setAuthenticationFailureHandler(failureHandler);
	}

	/**
	 * 接收并解析用户凭证
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		if (postOnly && !RequestMethod.POST.toString().equals(request.getMethod())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		WebLoginUser user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), WebLoginUser.class);
		} catch (JsonParseException e) {
			ErrorMessageException error = new ErrorMessageException(ErrorCode.BadRequest, "登录请求不合法:");
			error.dump(e);
			throw error;
		} catch (JsonMappingException e) {
			ErrorMessageException error = new ErrorMessageException(ErrorCode.BadRequest, "登录请求不合法:");
			error.dump(e);
			throw error;
		} catch (IOException e) {
			ErrorMessageException error = new ErrorMessageException(ErrorCode.BadRequest, "登录请求不合法:");
			error.dump(e);
			throw error;
		}
		
		if (!Optional.fromNullable(user).isPresent()) {
			throw new ErrorMessageException(ErrorCode.BadRequest, "登录请求不合法");
		} else {
			if (StringUtils.isBlank(user.getLoginId())) {
				throw new ErrorMessageException(ErrorCode.BadRequest, "登录请求不合法");
			}
			if (StringUtils.isBlank(user.getPassword())) {
				throw new ErrorMessageException(ErrorCode.BadRequest, "登录请求不合法");
			}
		}

		String username = user.getLoginId().trim();
		String password = user.getPassword();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);

	}

}
