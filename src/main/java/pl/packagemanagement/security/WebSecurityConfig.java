package pl.packagemanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.packagemanagement.model.user.UserServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled =  true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserServiceImpl passwordService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(passwordService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.
                cors().and().
                csrf().disable()
                .authorizeRequests().antMatchers("/authenticate").permitAll() //logowanie uzytkownika
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/register").permitAll() //dostep nieautoryzowany podczas rejestracji
                .antMatchers("/userdetails").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers("/users/{userId}").hasRole("ADMIN")  //get /users/x with parameters
                .antMatchers(HttpMethod.DELETE, "/users").hasAnyRole("ADMIN", "USER", "WORKER")
                .antMatchers(HttpMethod.GET, "/warehouses").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/packages/{id}/raport", "/cars/{id}/document", "/warehouses/{id}/document").hasAnyRole("USER", "ADMIN", "WORKER")
                .antMatchers(HttpMethod.GET, "/cars").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/packages/number/{packageNumber}").permitAll()
                .antMatchers("/packages", "/senders", "/recipients", "/categories", "products", "/content").hasAnyRole("ADMIN", "USER", "WORKER")
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
