package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UsersListRequest;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUser(NewUserRequest newUser) {
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(newUser)));
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(UsersListRequest request) {
        int page = request.from() / request.size();
        Pageable pageable = PageRequest.of(page, request.size(), Sort.by("id").ascending());

        List<User> users = request.ids() == null || request.ids().isEmpty()
                ? userRepository.findAll(pageable).getContent()
                : userRepository.findByIdIn(request.ids(), pageable).getContent();

        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }
}
