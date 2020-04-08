package org.project.expendituremanagement.config;

import org.project.expendituremanagement.filter.CorsFilter;
import org.project.expendituremanagement.filter.SessionRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SessionRequestFilter sessionRequestFilter;
    @Autowired
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //don't do validation for api which has pattern as {"/user/validate","/user/register"} because these are open public API.(Any one can login without and register without needing sessionId )
        http.csrf().disable()
                .authorizeRequests().antMatchers("/user/validate").permitAll()
                .and()
                .authorizeRequests().antMatchers("/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(sessionRequestFilter, UsernamePasswordAuthenticationFilter.class);//before UsernamePasswordAuthenticationFilter go through sessionRequestFilter
        http.addFilterBefore(corsFilter,SessionRequestFilter.class);//before SessionRequestFilter go through CorsFilter
        //after our authentication it go to UsernamePasswordAuthenticationFilter but in our authentication we set user as authenticated by setting authenticated field of UsernamePasswordAuthenticationToken, so it will not look for generated password(i guess)
    }
    //by default spring will generate password(if you specify password by in memory or by taking control of authentication Manager spring will not generate password)
}
