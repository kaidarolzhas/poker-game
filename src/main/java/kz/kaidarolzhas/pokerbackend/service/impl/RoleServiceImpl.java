package kz.kaidarolzhas.pokerbackend.service.impl;

import kz.kaidarolzhas.pokerbackend.data.entity.Role;
import kz.kaidarolzhas.pokerbackend.data.exception.RoleNotFoundException;
import kz.kaidarolzhas.pokerbackend.repository.RoleRepository;
import kz.kaidarolzhas.pokerbackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }
}
