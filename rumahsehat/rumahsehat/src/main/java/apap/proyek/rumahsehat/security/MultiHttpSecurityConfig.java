//package apap.proyek.rumahsehat.security;
//
//import apap.proyek.rumahsehat.security.jwt_config.JwtAuthenticationEntryPoint;
//import apap.proyek.rumahsehat.security.jwt_config.JwtRequestFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//public class MultiHttpSecurityConfig {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//    }
//
//    @Configuration
//    public static class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//        @Autowired
//        private JwtRequestFilter jwtRequestFilter;
//
//        @Bean
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }
//
//        @Override
//        protected void configure(HttpSecurity httpSecurity) throws Exception {
//            // We don't need CSRF for this example
//            httpSecurity.csrf().disable()
//                    // dont authenticate this particular request
//                    .authorizeRequests().antMatchers("/authenticate").permitAll().
//                    // all other requests need to be authenticated
//                            anyRequest().authenticated().and().
//                    // make sure we use stateless session; session won't be used to
//                    // store user's state.
//                            exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//            // Add a filter to validate the tokens with every request
//            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        }
//
//    }
//
//    @Configuration
//    @Order(1)
//    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .antMatchers("/css/**").permitAll()
//                    .antMatchers("/js/**").permitAll()
//                    .antMatchers("/login-sso", "/validate-ticket").permitAll()
//                    .antMatchers("/users/**").hasAuthority("Admin")
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login").permitAll()
//                    .and()
//                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/login").permitAll();
//
//        }
//    }
//}
