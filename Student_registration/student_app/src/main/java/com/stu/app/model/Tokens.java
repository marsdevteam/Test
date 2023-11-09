package com.stu.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for handling authentication tokens
 *
 */

public record Tokens(@JsonProperty(value = "token_type") String tokenType,
		@JsonProperty(value = "access_token") String accessToken,
		@JsonProperty(value = "access_token_validity") LocalDateTime accessTokenValidity) implements Serializable {

}
