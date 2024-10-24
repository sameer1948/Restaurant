package org.app.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.app.restaurant.entity.CustomUser;
import org.app.restaurant.entity.CustomUserDetails;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewCustomUserRequest {

    private CustomUser customUser;  // Username Should be selected From Front-End
    private CustomUserDetails customUserDetails;

}
