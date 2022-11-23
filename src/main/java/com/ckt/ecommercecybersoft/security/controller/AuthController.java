package com.ckt.ecommercecybersoft.security.controller;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.security.dto.LoginRequestModel;
import com.ckt.ecommercecybersoft.security.jwt.JwtUtils;
import com.ckt.ecommercecybersoft.user.model.response.OperationName;
import com.ckt.ecommercecybersoft.user.model.response.OperationStatus;
import com.ckt.ecommercecybersoft.user.model.response.OperationStatusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Login with username and password
     * @param user includes username and password
     * @return operation status and token attached to header
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestModel user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // String jwtSubject = authentication.getPrincipal().toString();
        User userDetail = (User) authentication.getPrincipal();

        String jwtSubject = userDetail.getUsername();
        String jwtToken = JwtUtils.generateLoginToken(jwtSubject);
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.LOGIN.name());
        operationStatusModel.setOperationResult(OperationStatus.SUCCESS.name());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        headers.set("Access-Control-Expose-Headers", "Authorization");

        return ResponseEntity.ok().headers(headers)
                .body(new ResponseDTO(operationStatusModel, false, null, System.currentTimeMillis(), false, 200));
    }
}
