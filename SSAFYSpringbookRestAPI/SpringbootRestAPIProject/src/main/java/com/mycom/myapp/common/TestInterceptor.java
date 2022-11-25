package com.mycom.myapp.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TestInterceptor implements HandlerInterceptor {

	// DispathcerSerlvet 이후 다른 Controller 의 메소드를 실행하기 직전에 호출
	// 뭔가 테스트를 진행하고 그 결과에 따라 return true 하면 Controller 로 진행
	// return false 를 하면 Controller 로 진행 X -> empty response를 Client 에 보낸다.
	
	// 생각할거리 2 
	// 1) 테스트 하려는 항목이 뭔가  => 이곳 코드 안에서 처리/판단
	// 2) 이걸 누구에게 적용할 것인가  => servlet-context.xml에 지정하여 설정을 통해서 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("TestInterceptor : preHandle !!!");
		
		
		// #1 무조건 통과 test
		// return true;
		
		// #2 login test
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("login");
		
		//login 상태
		if("success".equals(login))return true;
		
		//logout 상태
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().write("로그인해라아아아아앙아ㅏㅏ");
		return false;
	}
}
