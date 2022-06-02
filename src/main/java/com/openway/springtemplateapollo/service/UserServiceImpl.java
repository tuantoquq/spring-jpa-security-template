package com.openway.springtemplateapollo.service;

import com.openway.springtemplateapollo.adapter.EntityAdapter;
import com.openway.springtemplateapollo.builder.Response;
import com.openway.springtemplateapollo.constants.GenderEnum;
import com.openway.springtemplateapollo.constants.RoleEnum;
import com.openway.springtemplateapollo.constants.StatusApiConstants;
import com.openway.springtemplateapollo.entity.Role;
import com.openway.springtemplateapollo.entity.User;
import com.openway.springtemplateapollo.exception.ApolloTemplateException;
import com.openway.springtemplateapollo.payload.request.AddUserRequest;
import com.openway.springtemplateapollo.payload.response.UserInformationResponse;
import com.openway.springtemplateapollo.repository.RoleRepository;
import com.openway.springtemplateapollo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityAdapter<User, UserInformationResponse> entityAdapter;

    @Override
    public void addUser(AddUserRequest addUserRequest) throws ApolloTemplateException {
        User user = new User();
        user.setAddress(addUserRequest.getAddress());
        user.setEmail(addUserRequest.getEmail());
        user.setGender(GenderEnum.valueOf(addUserRequest.getGender().toUpperCase()));
        user.setPassword(passwordEncoder.encode(addUserRequest.getPassword()));

        //get user role
        Optional<Role> userRole = roleRepository.findRoleByRoleName(RoleEnum.USER);
        if(userRole.isEmpty()) throw new ApolloTemplateException("Did not find user role", HttpStatus.NOT_FOUND.value());
        user.setRole(userRole.get());
        user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) throws ApolloTemplateException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new ApolloTemplateException("Did not find user with email: " + email,
                    HttpStatus.NOT_FOUND.value());
        }
        return user;
    }

    @Override
    public void saveRefreshToken(String email, String refreshToken) throws ApolloTemplateException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new ApolloTemplateException("Did not find user with email: " + email,
                    HttpStatus.NOT_FOUND.value());
        }

        user.setRefreshToken(refreshToken);
        user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    @Override
    public User findUserById(int id) throws ApolloTemplateException {
        Optional<User> userResult = userRepository.findById(id);
        if(userResult.isPresent()){
            return userResult.get();
        }else throw new ApolloTemplateException("Did not find user with id: "+ id,
                HttpStatus.NOT_FOUND.value());
    }

    @Override
    public Response getUserProfile(int userId) throws ApolloTemplateException {
        Optional<User> userResult = userRepository.findById(userId);
        User user;
        if(userResult.isPresent()){
            user = userResult.get();
        }else throw new ApolloTemplateException("Did not find user with id: "+ userId,
                HttpStatus.NOT_FOUND.value());
        return new Response.Builder(StatusApiConstants.SUCCESS.getHttpStatusCode(), StatusApiConstants.SUCCESS.getErrorCode())
                .buildMessage("get user information successfully")
                .buildData(entityAdapter.toMapper(user))
                .build();
    }
}
