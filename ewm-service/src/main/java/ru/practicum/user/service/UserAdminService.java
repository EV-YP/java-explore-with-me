package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UsersListRequest;

import java.util.List;

public interface UserAdminService {
    UserDto addUser(NewUserRequest newUser);

    void deleteUserById(Integer id);

    UserDto getUserById(Integer id);

    List<UserDto> getUsers(UsersListRequest request);
}
