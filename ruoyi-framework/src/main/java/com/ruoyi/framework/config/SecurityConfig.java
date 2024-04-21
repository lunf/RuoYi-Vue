package com.ruoyi.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;
import com.ruoyi.framework.config.properties.PermitAllUrlProperties;
import com.ruoyi.framework.security.filter.JwtAuthenticationTokenFilter;
import com.ruoyi.framework.security.handle.AuthenticationEntryPointImpl;
import com.ruoyi.framework.security.handle.LogoutSuccessHandlerImpl;

/**
 * spring securityConfiguration
 * 
 * @author ruoyi
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * Custom User Certification Logic
     */
    @Autowired
    private UserDetailsService userDetailsService;
    
    /**
     * Certification of failure processing.
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * Exit from treatment.
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * tokenCertification of Filters
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;
    
    /**
     * cross-space filters
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * Anonymous address permitted.
     */
    @Autowired
    private PermitAllUrlProperties permitAllUrl;

    /**
     * solved cannot be injected directly. AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   Compatible with all requests.
     * access              |   SpringElThe expressive result istruecan be accessed.
     * anonymous           |   Anonymous is accessible.
     * denyAll             |   Users cannot access.
     * fullyAuthenticated  |   Full user certification is accessible.（notremember-meAutomatic logging.）
     * hasAnyAuthority     |   If there are parameters.，Parameters show authority.，Any authority can be accessed.
     * hasAnyRole          |   If there are parameters.，Parameters indicate the role.，Any of these roles can be accessed.
     * hasAuthority        |   If there are parameters.，Parameters show authority.，The authority can be accessed.
     * hasIpAddress        |   If there are parameters.，Parameters sayIPAddressed，If the userIPThe parameters match.，can be accessed.
     * hasRole             |   If there are parameters.，Parameters indicate the role.，The role is accessible.
     * permitAll           |   Users can access arbitrarily.
     * rememberMe          |   Permit to passremember-meUser access logged in.
     * authenticated       |   User access after logging.
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        // Note labels allow anonymous accessurl
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        permitAllUrl.getUrls().forEach(url -> registry.antMatchers(url).permitAll());

        httpSecurity
                // CSRFprohibited，Because not used.session
                .csrf().disable()
                // prohibitedHTTPReact to the headline.
                .headers().cacheControl().disable().and()
                // Certification of failure processing.
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // Based ontoken，Therefore not necessary.session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Filtering request.
                .authorizeRequests()
                // for registration.login Registeredregister verification codecaptchaImage Enable anonymous access.
                .antMatchers("/login", "/register", "/captchaImage").permitAll()
                // static resources，Anonymous access.
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                // All requests except above require certification.
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        // AddedLogout filter
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // AddedJWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // AddedCORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }

    /**
     * Requirements for LeHash.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * Identification Interface
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
