package cz.muni.fi.pa165.musiclib.mvc.config.security;

import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import javax.inject.Inject;
import java.util.List;

/**
 * @author xstefank (422697@mail.muni.cz)
 * @version 12/11/15
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    DataSource dataSource;

    @Inject
    private UserFacade userFacade;

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        List<UserDTO> userDTOs = userFacade.getAllUsers();

        for(UserDTO userDTO : userDTOs) {
            auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                    .withUser(userDTO.getEmail()).password(userDTO.getPasswordHash()).roles("USER");
        }

//        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder()).usersByUsernameQuery("select email,");
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
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error=invalidLoginAttempt")
                .usernameParameter("user").passwordParameter("pass")
                .and()
                .logout().logoutSuccessUrl("/home")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .csrf().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new PasswordEncoding();
        return encoder;
    }
}