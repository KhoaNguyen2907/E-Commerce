package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.role.repository.RoleRepository;
import com.ckt.ecommercecybersoft.security.jwt.JwtUtils;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    User mockUser = new User();
    UserDto mockUserDto = new UserDto();
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectMapper mapper;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;


    @Test
    final void testFindAll() {
        User mockUser2 = new User();
        User mockUser3 = new User();

        mockUser.setUsername("us1");
        mockUser2.setUsername("us2");
        mockUser3.setUsername("us3");

        Mockito.when(userRepository.findAll()).thenReturn(List.of(mockUser, mockUser2, mockUser3));
        List<User> users = userService.findAll();

        Assertions.assertEquals(3, users.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    final void testFindById() {
        mockUser.setUsername("us1");

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(mockUser));

        User user = userService.findById(UUID.randomUUID()).orElse(null);
        Assertions.assertEquals("us1", user.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    final void testCreateUser() {
        mockUser.setUsername("us1");
        mockUser.setPassword("123456");
        mockUser.setEmail("us1.email@email.com");
        mockUser.setName("test us1");
        mockUserDto.setUsername("us1");
        mockUserDto.setPassword("123456");
        mockUserDto.setEmail("us1.email@email.com");
        mockUserDto.setName("test us1");

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(mockUser);
        Mockito.when(mapper.map(Mockito.any(), Mockito.eq(User.class))).thenReturn(mockUser);
        Mockito.when(mapper.map(Mockito.any(), Mockito.eq(UserDto.class))).thenReturn(mockUserDto);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByCode(Mockito.any())).thenReturn(Optional.of(new Role()));

        UserDto userDto = userService.createUser(mockUserDto);

        Assertions.assertEquals("us1", userDto.getUsername());
        Assertions.assertEquals("test us1", userDto.getName());
        Assertions.assertEquals("us1.email@email.com", userDto.getEmail());
        Assertions.assertEquals("123456", userDto.getPassword());


        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(mapper, Mockito.times(2)).map(Mockito.any(), Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.any());
    }

    @Test
    final void testFindByUsername() {
        mockUser.setUsername("us1");
        mockUser.setEmail("us1.email@email.com");
        mockUser.setName("test us1");
        mockUserDto.setUsername("us1");
        mockUserDto.setEmail("us1.email@email.com");
        mockUserDto.setName("test us1");

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(mockUser));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(mockUserDto);

        UserDto returnValue = userService.findByUsername("us1");

        Assertions.assertEquals("us1", returnValue.getUsername());
        Assertions.assertEquals("test us1", returnValue.getName());
        Assertions.assertEquals("us1.email@email.com", returnValue.getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.any());
        Mockito.verify(mapper, Mockito.times(1)).map(Mockito.any(), Mockito.any());
    }

    @Test
    final void testVerifyEmailToken() {
        boolean isValidToken = userService.verifyEmailToken("dummytoken");
        Assertions.assertEquals(false, isValidToken);
    }

    @Test
    final void testChangeRole() {
        RoleDto roleDto = new RoleDto("user", "user", "user");
        Role role = Role.builder().code("user").name("user").description("user").build();
        mockUser.setUsername("us1");
        mockUser.setEmail("us1.email@email.com");
        mockUser.setName("test us1");

        mockUserDto.setUsername("us1");
        mockUserDto.setEmail("us1.email@email.com");
        mockUserDto.setName("test us1");
        mockUserDto.setRole(roleDto);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(mockUser));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(mockUserDto);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(mockUser);
        Mockito.when(roleRepository.findByCode(Mockito.any())).thenReturn(Optional.of(role));

        UserDto returnValue = userService.changeRole(UUID.randomUUID(), roleDto);

        Assertions.assertEquals("us1", returnValue.getUsername());
        Assertions.assertEquals("user", returnValue.getRole().getCode());
        Assertions.assertEquals("test us1", returnValue.getName());
        Assertions.assertEquals("us1.email@email.com", returnValue.getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(mapper, Mockito.times(1)).map(Mockito.any(), Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(roleRepository, Mockito.times(1)).findByCode(Mockito.any());
    }

    @Test
    final void testRequestPasswordReset() {
        mockUser.setUsername("us1");
        mockUser.setEmail("us1@email.com");

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(mockUser));

        boolean isValid = userService.requestPasswordReset("us1@email.com");

        Assertions.assertEquals(true, isValid);

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    final void testVerifyPasswordResetToken() {
        mockUser.setUsername("us1");
        mockUser.setEmail("us1@email.com");

        String token = JwtUtils.generateEmailToken(mockUser.getUsername());


        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(mockUser));

        boolean isValid = userService.verifyPasswordResetToken(token);

        Assertions.assertEquals(true, isValid);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.any());
    }

    @Test
    final void testResetPassword() {
        mockUser.setUsername("us1");
        mockUser.setEmail("us1@email.com");

        mockUserDto.setUsername("us1");
        mockUserDto.setEmail("us1@email.com");
        mockUserDto.setPassword("123456");

        String token = JwtUtils.generateEmailToken(mockUser.getUsername());

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(mockUser);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(mockUserDto);


        UserDto returnValue = userService.resetPassword(token, "123456");

        Assertions.assertEquals("us1", returnValue.getUsername());
        Assertions.assertEquals("123456", returnValue.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(mapper, Mockito.times(1)).map(Mockito.any(), Mockito.any());
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());
    }

}
