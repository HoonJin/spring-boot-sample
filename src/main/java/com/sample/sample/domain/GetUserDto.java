package com.sample.sample.domain;

import com.sample.sample.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter(AccessLevel.NONE)
public class GetUserDto {

    private String email;

    private LocalDateTime createdAt;

    public static GetUserDto of(User user) {
        GetUserDto dto = new GetUserDto();
        dto.email = user.getEmail();
        dto.createdAt = user.getCreatedAt();
        return dto;
    }
}
