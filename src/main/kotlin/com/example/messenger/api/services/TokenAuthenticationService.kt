package com.example.messenger.api.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal object TokenAuthenticationService {
    private val TOKEN_EXPIRY: Long = 864000000
    private val SECRET = "$78gr43g7g8feb8we"
    private val TOKEN_PREFIX = "Bearer"
    private val AUTHORIZATION_HEADER_KEY = "Authorization"

    @Suppress("DEPRECATION")
    fun addAuthentication(res: HttpServletResponse, username: String) {
        val JWT = Jwts
            .builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + TOKEN_EXPIRY))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()
        res.addHeader(AUTHORIZATION_HEADER_KEY, "$TOKEN_PREFIX $JWT")
    }

    @Suppress("DEPRECATION")
    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(AUTHORIZATION_HEADER_KEY)
        if (token != null) {
            val user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).body.subject
            if (user != null)
                return UsernamePasswordAuthenticationToken(user, null, emptyList<GrantedAuthority>())
        }
        return null
    }
}