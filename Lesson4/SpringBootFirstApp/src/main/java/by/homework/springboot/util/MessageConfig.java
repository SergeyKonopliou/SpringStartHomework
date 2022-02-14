package by.homework.springboot.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessageConfig implements WebMvcConfigurer{

	@Bean("messageSource")
	  public MessageSource messageSource() {
	      ResourceBundleMessageSource messageSource = 
	               new ResourceBundleMessageSource();
	      messageSource.setBasenames("language/rez");
	      messageSource.setDefaultEncoding("UTF-8");
	      return messageSource;
	  }
  
  @Bean
  public LocaleResolver localeResolver() {
      SessionLocaleResolver slr = new SessionLocaleResolver();
      slr.setDefaultLocale(Locale.US);
      slr.setLocaleAttributeName("session.current.locale");
      slr.setTimeZoneAttributeName("session.current.timezone");
      return slr;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
      LocaleChangeInterceptor localeChangeInterceptor 
                      = new LocaleChangeInterceptor();
      localeChangeInterceptor.setParamName("lang");
      return localeChangeInterceptor;
  }
  
  public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(localeChangeInterceptor());
      registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/*");
  }
}