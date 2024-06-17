package net.sb.userservice.service.impl;

import lombok.AllArgsConstructor;

import net.sb.userservice.client.APIClient;
import net.sb.userservice.dto.DepartmentDto;
import net.sb.userservice.dto.ResponseDto;
import net.sb.userservice.dto.UserDto;
import net.sb.userservice.entity.User;
import net.sb.userservice.repository.UserRepository;
import net.sb.userservice.service.UserService;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private APIClient apiClient;

    /**
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public ResponseDto getUser(Long userId) {
        ResponseDto responseDto = new ResponseDto();
        User user = userRepository.findById(userId).orElseThrow();
        UserDto userDto = mapToUser(user);

        DepartmentDto departmentDto = apiClient.getDepartmentById(user.getDepartmentId());

        responseDto.setUser(userDto);
        responseDto.setDepartment(departmentDto);

        return responseDto;
    }

    private UserDto mapToUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
