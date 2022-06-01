package com.openway.springtemplateapollo.adapter;

import com.openway.springtemplateapollo.entity.User;
import com.openway.springtemplateapollo.payload.response.UserInformationResponse;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements EntityAdapter<User, UserInformationResponse> {
    @Override
    public UserInformationResponse toMapper(User entity) {
        UserInformationResponse response = new UserInformationResponse();
        response.setId(entity.getId());
        response.setEmail(entity.getEmail());
        response.setAddress(entity.getAddress());
        response.setGender(entity.getGender().name());
        response.setRefreshToken(entity.getRefreshToken());
        return response;
    }
}
