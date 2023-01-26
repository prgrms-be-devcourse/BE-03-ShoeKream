package com.prgrms.kream.common.jwt;

import java.util.Arrays;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Jwt {

	private final String issuer;
	private final int expirySeconds;
	private final Algorithm algorithm;
	private final JWTVerifier jwtVerifier;

	public Jwt(String issuer, String clientSecret, int expirySeconds) {
		this.issuer = issuer;
		this.expirySeconds = expirySeconds;
		this.algorithm = Algorithm.HMAC512(clientSecret);
		this.jwtVerifier = JWT.require(algorithm)
				.withIssuer(issuer)
				.build();
	}

	public String sign(Claims claims) {
		Date now = new Date();
		return JWT.create()
				.withIssuer(issuer)
				.withIssuedAt(now)
				.withExpiresAt(new Date(now.getTime() + expirySeconds * 1000L))
				.withClaim("memberId", claims.memberId)
				.withArrayClaim("roles", claims.roles)
				.sign(algorithm);
	}

	public Claims verify(String token) {
		return new Claims(jwtVerifier.verify(token));
	}

	public static class Claims {
		Long memberId;
		String[] roles;
		Date iat;
		Date exp;

		private Claims() {
		}

		Claims(DecodedJWT decodedJWT) {
			Claim memberId = decodedJWT.getClaim("memberId");
			if (!memberId.isNull()) {
				this.memberId = memberId.asLong();
			}
			Claim roles = decodedJWT.getClaim("roles");
			if (!roles.isNull()) {
				this.roles = roles.asArray(String.class);
			}
			this.iat = decodedJWT.getIssuedAt();
			this.exp = decodedJWT.getExpiresAt();
		}

		public static Claims from(Long memberId, String[] roles) {
			Claims claims = new Claims();
			claims.memberId = memberId;
			claims.roles = roles;
			return claims;
		}

		@Override
		public String toString() {
			return "Claims{" +
					"memberId=" + memberId +
					", roles=" + Arrays.toString(roles) +
					", iat=" + iat +
					", exp=" + exp +
					'}';
		}
	}
}
