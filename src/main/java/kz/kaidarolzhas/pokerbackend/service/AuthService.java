package kz.kaidarolzhas.pokerbackend.service;


import kz.kaidarolzhas.pokerbackend.data.dto.LoginRequestDto;
import kz.kaidarolzhas.pokerbackend.data.dto.LoginResponseDto;
import kz.kaidarolzhas.pokerbackend.data.dto.RegistrationRequestDto;

public interface AuthService {
    void registration(RegistrationRequestDto registrationRequest);
    LoginResponseDto login(LoginRequestDto loginRequest);
}
