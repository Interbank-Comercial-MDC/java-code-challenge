package com.interbank.challenge.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SigninResponse {

	private String accessToken;
	private String tokenType = "Bearer";
	private String id;
	private String username;
	private String email;
	private Boolean locked = true;
	private Boolean disable = true;
	private String role;
}
