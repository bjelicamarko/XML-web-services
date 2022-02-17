package com.imunizacija.ImunizacijaApp.config.web_security;

import com.imunizacija.ImunizacijaApp.security.TokenUtils;
import com.imunizacija.ImunizacijaApp.security.auth.RestAuthenticationEntryPoint;
import com.imunizacija.ImunizacijaApp.security.auth.TokenAuthenticationFilter;
import com.imunizacija.ImunizacijaApp.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private KorisnikService jwtUserDetailsService;

	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	private TokenUtils tokenUtils;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public WebSecurityConfig(KorisnikService korisnikService, RestAuthenticationEntryPoint restAuthenticationEntryPoint, TokenUtils tokenUtils,
							 PasswordEncoder passwordEncoder){
		this.jwtUserDetailsService = korisnikService;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		this.tokenUtils = tokenUtils;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(this.passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
				.authorizeRequests().antMatchers("/auth/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/socket/**").permitAll()
				.antMatchers("/api/users/login").permitAll()
				.antMatchers("/api/users/registracija").permitAll()
				.antMatchers("/api/users/test-create-citizen").permitAll() //todo izbrisati
				.antMatchers("/api/users/test-create-doctor").permitAll() //todo izbrisati
				.antMatchers("/api/odgovori/dobaviTermin").permitAll() // ZA komunikaciju beckova
				.antMatchers("/api/izvestaji/dobaviIzvestaje/*").permitAll() // ZA komunikaciju beckova
				.antMatchers("/api/saglasnost/search").permitAll() // komunikacija bekova
				.antMatchers("/api/saglasnost/generatePDF/**").permitAll() // komunikacija bekova
				.antMatchers("/api/saglasnost/generateHTML/**").permitAll() // komunikacija bekova
				.antMatchers("/api/zahtev/generatePDF/**").permitAll() // komunikacija bekova
				.anyRequest().authenticated().and()
				.cors().and()
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/api/users/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}

}
