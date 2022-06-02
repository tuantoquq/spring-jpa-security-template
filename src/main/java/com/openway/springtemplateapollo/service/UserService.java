package com.openway.springtemplateapollo.service;

import com.openway.springtemplateapollo.builder.Response;
import com.openway.springtemplateapollo.entity.User;
import com.openway.springtemplateapollo.exception.ApolloTemplateException;
import com.openway.springtemplateapollo.payload.request.AddUserRequest;

public interface UserService {
    public void addUser(AddUserRequest addUserRequest) throws ApolloTemplateException;
    public User findUserByEmail(String email) throws ApolloTemplateException;
    public void saveRefreshToken(String email, String refreshToken) throws ApolloTemplateException;
    public User findUserById(int id) throws ApolloTemplateException;
    public Response getUserProfile(int userId) throws ApolloTemplateException;
}
