package com.rustudor.controller;

import com.rustudor.Dto.*;
import com.rustudor.Services.UserService;
import com.rustudor.Util.RequestValidator;
import com.rustudor.Util.Session;
import com.rustudor.Util.SessionManager;
import com.rustudor.entity.CurrencyType;
import com.rustudor.entity.Role;
import com.rustudor.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/viewProfile")
    public ResponseEntity<UserDto> viewProfile(@RequestHeader("token") String token) {
        System.out.println("yo" + token);
        Session session = SessionManager.getSessionMap().get(token);
        if (session == null) {
            System.out.println("eloo");
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            if (!RequestValidator.validate(session))
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            User user = userService.findByUsername(session.getUsername());
            return new ResponseEntity<>(new UserDto(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getAddress(), user.getCnp()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateProfile")
    public ResponseEntity<StringObj> updateProfile(@RequestBody UserDto userDto, @RequestHeader("token") String token) {
        Session session = SessionManager.getSessionMap().get(token);
        if (session == null)
            return new ResponseEntity<>(new StringObj("ERROR"), HttpStatus.FORBIDDEN);
        else {
            if (!RequestValidator.validate(session))
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            userService.update(userDto, session);
            return new ResponseEntity<>(new StringObj("SUCCESS : PROFILE UPDATED"), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getRole")
    public ResponseEntity<Role> getRole(@RequestHeader("token") String token) {
        Session session = SessionManager.getSessionMap().get(token);
        if (session == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(session.getRole(), HttpStatus.OK);
    }

    @GetMapping(value = "test/getC")
    public ResponseEntity<CurrencyType> getC() {
        return new ResponseEntity<>(CurrencyType.EUR, HttpStatus.OK);
    }

    @PostMapping(value = "/addAccount")
    public ResponseEntity<StringObj> addAccount(@RequestBody String currencyType, @RequestHeader("token") String token) {
        Session session = SessionManager.getSessionMap().get(token);
        if (session == null) {
            System.out.println("nu");
            return new ResponseEntity<>(new StringObj("ERROR"), HttpStatus.FORBIDDEN);
        } else {
            if (!RequestValidator.validate(session)) {
                System.out.println("da");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (userService.addAccount(currencyType, session) == 0) {
                System.out.println("dada");
                return new ResponseEntity<>(new StringObj("SUCCESS : ACCOUNT CREATED"), HttpStatus.OK);
            } else {
                System.out.println("dadada");
                return new ResponseEntity<>(new StringObj("ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping(value = "/makeTransfer")
    public ResponseEntity<String> makeTransfer(@RequestBody MakeTransferDto makeTransferDto, @RequestHeader("token") String token) {
        Session session = SessionManager.getSessionMap().get(token);
        if (session == null)
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            if (!RequestValidator.validate(session))
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            switch (userService.addTransfer(makeTransferDto, session)) {
                case 0:
                    return new ResponseEntity<>("SUCCESS: TRANSFER ADDED", HttpStatus.OK);
                case -1:
                    return new ResponseEntity<>("ERROR -1", HttpStatus.INTERNAL_SERVER_ERROR);
                case -2:
                    return new ResponseEntity<>("ERROR -2", HttpStatus.INTERNAL_SERVER_ERROR);
                case -3:
                    return new ResponseEntity<>("ERROR -3", HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


    @GetMapping(value = "/getTransfers")
    public ResponseEntity<ArrayList<TransferDto>> getTransfers(@RequestHeader("token") String token) {
        Session session = SessionManager.getSessionMap().get(token);
        if (session == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            if (!RequestValidator.validate(session))
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            ArrayList<TransferDto> transferDtos = userService.getTransfers(session);
            return new ResponseEntity<>(transferDtos, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAccounts")
    public ResponseEntity<ArrayList<AccountDto>> getAccounts(@RequestHeader("token") String token) {
        Session session = SessionManager.getSessionMap().get(token);

        if (session == null)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        else {
            if (!RequestValidator.validate(session))
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            ArrayList<AccountDto> accountDtos = userService.getAccounts(session);
            return new ResponseEntity<>(accountDtos, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody FullUserDto fullUserDto) {
        System.out.println(fullUserDto);
        switch (userService.register(fullUserDto)) {
            case 0:
                return new ResponseEntity<>("SUCCESS : USER REGISTERED", HttpStatus.OK);
            case -1:
                return new ResponseEntity<>("DUPLICATE", HttpStatus.CONFLICT);
            default:
                return new ResponseEntity<>("UNKNOWN ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String token) {
        if (!SessionManager.getSessionMap().containsKey(token))
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            userService.logout(token);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        TokenDto tokenDto = userService.login(loginDto);
        System.out.println(loginDto.getUsername());
        if (tokenDto != null)
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(tokenDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
