package com.example.messenger.api.config

import com.example.messenger.api.components.AccountValidityInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class AppConfig: WebMvcConfigurer {
    @Autowired
    lateinit var accountValidityInterceptor: AccountValidityInterceptor
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(accountValidityInterceptor)
        super.addInterceptors(registry)
    }
}