package kz.kaidarolzhas.pokerbackend.service;

import kz.kaidarolzhas.pokerbackend.data.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
