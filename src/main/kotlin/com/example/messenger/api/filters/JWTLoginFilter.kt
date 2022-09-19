package com.example.messenger.api.filters

import com.example.messenger.api.security.AccountCredentials
import com.example.messenger.api.services.TokenAuthenticationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(url: String, authManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter( AntPathRequestMatcher(url) ) {
    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().readValue(req.inputStream, AccountCredentials::class.java)
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                credentials.username,
                credentials.password,
                emptyList()
            )
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication( req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain, auth  : Authentication) {
        TokenAuthenticationService.addAuthentication(res, auth.name)
    }

}