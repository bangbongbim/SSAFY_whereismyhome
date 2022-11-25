package com.mycom.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mycom.myapp.common.TestInterceptor;

@Configuration // xml 설정을 대신 ( servlet-context.xml 의 설정 내용을 대체), intercetpor 외에 다른 많은 설정 가능
public class WebMvcConfig implements WebMvcConfigurer {
	// 필요한 것 2가지

	
	// 1. interceptor 객체 DI

	@Autowired
	private TestInterceptor testInterceptor;

	// 2. interceptor 에 대한 정책
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(testInterceptor)
			.addPathPatterns("/users/**")
			.excludePathPatterns("/login/**","/register/**");
	}
}

// interface 는 추상 메소드만으로 구성, 바디를 가지는 default 메소드를 가질 수 없었음
// interface 에 가령 20개의 추상 메소드가 있으면, 그걸 implements 하는 클래스는 딱 2개만 구현하고 싶은데 18개를 다 구현해야 함(empty 바디)
// 이게 불편해서 api 에서 자주 사용되는 interface에 대해서는 클래스로 ...Adaptor 를 제공 
// ...Adaptor 클래스는 interface 의 20개 메소드를 모두 구현(empty 바디)함.
// A interface 를 implements 하는 것이 아니고, extends AAdaptor 해서 2개만 오버라이딩하면 되도록
// AAdaptor implements A로 구현
