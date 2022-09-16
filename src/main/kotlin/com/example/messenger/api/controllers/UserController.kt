package com.example.messenger.api.controllers

import com.example.messenger.api.components.UserAssembler
import com.example.messenger.api.helpers.objects.UserListVO
import com.example.messenger.api.helpers.objects.UserVO
import com.example.messenger.api.models.User
import com.example.messenger.api.repositaries.UserRepository
import com.example.messenger.api.services.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
class UserController(val userService: UserServiceImpl, val userAssembler: UserAssembler, val userRepository: UserRepository) {

    @PostMapping
    @RequestMapping("/registrations")
    fun create(@Validated @RequestBody userDetails: User): ResponseEntity<UserVO> {
        val user = userService.attemptRegistration(userDetails)
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    @RequestMapping("/{user_id}")
    fun echoDetails(request: HttpServletRequest): ResponseEntity<UserVO>{
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        return ResponseEntity.ok(userAssembler.toUserVO(user))
    }

    @GetMapping
    fun index(request: HttpServletRequest): ResponseEntity<UserListVO> {
        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val users = userService.listUsers(user)
        return ResponseEntity.ok(userAssembler.toUserListVO(users))
    }

    @PutMapping
    fun update(@RequestBody updateDetails: User, request: HttpServletRequest): ResponseEntity<UserVO> {
        val currentUser = userRepository.findByUsername(request.userPrincipal.name)
        userService.updateUserStatus(currentUser as User, updateDetails)
        return ResponseEntity.ok(userAssembler.toUserVO(currentUser))
    }
}