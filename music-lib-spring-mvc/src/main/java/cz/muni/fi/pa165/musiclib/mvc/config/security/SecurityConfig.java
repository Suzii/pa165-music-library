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
            if(userDTO.isAdmin()) {
                auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                        .withUser(userDTO.getEmail()).password(userDTO.getPasswordHash()).roles("ADMIN");
            } else {
                auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                        .withUser(userDTO.getEmail()).password(userDTO.getPasswordHash()).roles("USER");
            }
        }

//        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder()).usersByUsernameQuery("select email,");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/song/create/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/song/update/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/song/remove/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/album/create/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/album/update/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/album/remove/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/album/changeImage/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/musician/create/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/musician/update/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/musician/remove/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/genre/create/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/genre/update/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/genre/remove/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/song/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/genre/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/album/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/musician/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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