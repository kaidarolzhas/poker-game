package kz.kaidarolzhas.pokerbackend.service.impl;

import kz.kaidarolzhas.pokerbackend.data.dto.LoginRequestDto;
import kz.kaidarolzhas.pokerbackend.data.dto.LoginResponseDto;
import kz.kaidarolzhas.pokerbackend.data.dto.RegistrationRequestDto;
import kz.kaidarolzhas.pokerbackend.data.entity.Role;
import kz.kaidarolzhas.pokerbackend.data.entity.User;
import kz.kaidarolzhas.pokerbackend.data.exception.AuthException;
import kz.kaidarolzhas.pokerbackend.repository.UserRepository;
import kz.kaidarolzhas.pokerbackend.service.AuthService;
import kz.kaidarolzhas.pokerbackend.service.RoleService;
import kz.kaidarolzhas.pokerbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static kz.kaidarolzhas.pokerbackend.util.Constants.INVALID_USERNAME_OR_PASSWORD;
import static kz.kaidarolzhas.pokerbackend.util.Constants.USERNAME_EXISTS;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleService roleService;

    @Override
    public void registration(RegistrationRequestDto registrationRequest) {
        if (repository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new AuthException(USERNAME_EXISTS);
        }
        create(registrationRequest.getUsername(), registrationRequest.getPassword());
    }

    private void create(String username, String password) {
        Set<Role> role = new HashSet<>();
        role.add(roleService.findByName("ROLE_USER"));
        repository.save(
                User.builder()
                        .roles(role)
                        .isEnabled(true)
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .build()
        );
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = repository.findByUsername(loginRequestDto.getUsername()).orElseThrow(()-> new AuthException(INVALID_USERNAME_OR_PASSWORD));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new AuthException(INVALID_USERNAME_OR_PASSWORD);
        }
        return new LoginResponseDto(jwtUtil.generateToken(user.getId(), user.getRoles()));
    }
}
