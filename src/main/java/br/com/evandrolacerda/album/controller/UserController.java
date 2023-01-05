package br.com.evandrolacerda.album.controller;

import br.com.evandrolacerda.album.dto.UserDto;
import br.com.evandrolacerda.album.entity.User;
import br.com.evandrolacerda.album.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public String save(@Valid UserDto userDto, BindingResult bindingResult ){

        if( bindingResult.hasErrors() ){
            return "erro";
        }

        String password = bCryptPasswordEncoder.encode( userDto.getPassword() );
        userDto.setPassword( password );

        User user = new User();
        user.setPassword( password );
        user.setEmail( userDto.getEmail() );
        user.setUsername( userDto.getUsername() );

        userRepository.save( user );

        return "success";

    }


}
