package com.example.messenger.api.config

import com.example.messenger.api.filters.JWTAuthenticationFilter
import com.example.messenger.api.filters.JWTLoginFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import com.example.messenger.api.services.AppUserDetailsService
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig(val userDetailsService: AppUserDetailsService): WebSecurityConfigurerAdapter() {
    // Определяет какие пути должны быть защищены, а какие нет.
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf()
            .disable()
            .authorizeRequests()

            // разрешить все post-запросы к регистрации и авторизации
            .antMatchers(HttpMethod.POST, "/users/registrations").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()

            // запросы к остальным путям помечаем как "прошедшие авторизацию" если они прошли JWTLoginFilter (только для авторизации) или
            // JWTAuthenticationManagerBuilder (аутентификация)
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    // Указывает применяемый кодировщик пароля
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userDetailsService).passwordEncoder(BCryptPasswordEncoder())
    }
}