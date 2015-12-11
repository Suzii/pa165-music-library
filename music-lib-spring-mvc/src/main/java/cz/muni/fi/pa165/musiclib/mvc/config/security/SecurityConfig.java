package cz.muni.fi.pa165.musiclib.mvc.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/11/15
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("mkyong").password("123456").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN").roles("USER");
        auth.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/song/**").access("hasRole('ROLE_USER')")
                .antMatchers("/genre/**").access("hasRole('ROLE_USER')")
                .antMatchers("/album/**").access("hasRole('ROLE_USER')")
                .antMatchers("/musician/**").access("hasRole('ROLE_USER')")
                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .and()
                .formLogin();
//                .loginPage("/login").failureUrl("/error")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout().logoutSuccessUrl("/logout")
//                .and()
//                .csrf().disable();

    }
}