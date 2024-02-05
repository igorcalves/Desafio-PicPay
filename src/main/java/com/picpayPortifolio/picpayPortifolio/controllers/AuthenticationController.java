package com.picpayPortifolio.picpayPortifolio.controllers;


import com.picpayPortifolio.picpayPortifolio.domain.user.AuthenticationDTO;
import com.picpayPortifolio.picpayPortifolio.domain.user.LoginResponseDTO;
import com.picpayPortifolio.picpayPortifolio.domain.user.RegisterDTO;
import com.picpayPortifolio.picpayPortifolio.domain.user.UserLogin;
import com.picpayPortifolio.picpayPortifolio.infra.security.securityConfiguration.TokenService;
import com.picpayPortifolio.picpayPortifolio.repositories.UserLoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    UserLoginRepository repositoriy;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((UserLogin) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.repositoriy.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        String encryptPassword = new BCryptPasswordEncoder().encode(data.password());


        UserLogin newUser = new UserLogin(data.login(), encryptPassword, data.role());
        this.repositoriy.save(newUser);
        return ResponseEntity.ok().build();
    }


}
