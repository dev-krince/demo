package com.pcn.demo.global.security

import com.pcn.demo.domain.user.constant.Role
import com.pcn.demo.domain.user.dto.response.TokenDto
import com.pcn.demo.global.response.ExceptionResponseCode
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtProvider(
    @Value("\${jwt.secret.key}") secretKey: String,
    @Value("\${jwt.access-token-expired}") accessTokenExpired: Long,
    @Value("\${jwt.refresh-token-expired}") refreshTokenExpired: Long
) {
    final val keyBytes = Decoders.BASE64.decode(secretKey)
    private final val secretKey: Key = Keys.hmacShaKeyFor(keyBytes)
    private final val ACCESS_TOKEN_EXPIRED: Long = accessTokenExpired
    private final val REFRESH_TOKEN_EXPIRED: Long = refreshTokenExpired
    private final val HEADER_NAME = "Authorization"
    private final val ROLE = "role"

    fun createAccessToken(id: Long, role: Role): String {
        val now = Date()
        val validity = Date(now.time + ACCESS_TOKEN_EXPIRED)

        return TOKEN_TYPE + Jwts.builder()
            .setSubject(id.toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .claim(ROLE, role)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun createRefreshToken(id: Long): String {
        val now = Date()
        val validity = Date(now.time + REFRESH_TOKEN_EXPIRED)

        return TOKEN_TYPE + Jwts.builder()
            .setSubject(id.toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateTokenDto(id: Long, role: Role): TokenDto {
        val accessToken = createAccessToken(id, role)
        val refreshToken = createRefreshToken(id)

        return TokenDto.of(accessToken, refreshToken)
    }

    fun isValidToken(token: String): Boolean {
        return try {
            getClaimsJws(token).body
                .expiration
                .after(Date())
        } catch (exception: ExpiredJwtException) {
            throw exception
        } catch (exception: JwtException) {
            false
        } catch (exception: IllegalArgumentException) {
            false
        }
    }

    fun getRole(token: String): Role {
        val roleValue = getClaimsJws(token)
            .body
            .get(ROLE, String::class.java)

        return Role.valueOf(roleValue)
    }

    fun getId(token: String): Long {
        val idValue = getClaimsJws(token)
            .body
            .subject

        return idValue.toLong()
    }

    private fun getClaimsJws(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
    }

    fun extractToken(token: String): String {
        require(!isInvalidTokenType(token)) { ExceptionResponseCode.INVALID_TOKEN.message }

        return token.substring(TOKEN_TYPE.length)
    }

    private fun isInvalidTokenType(token: String?): Boolean {
        return token == null || !token.startsWith(TOKEN_TYPE)
    }

    fun getClaims(token: String): Claims {
        return getClaimsJws(token).body
    }

    fun getToken(request: HttpServletRequest): String {
        return request.getHeader(HEADER_NAME)
    }

    companion object {
        private const val TOKEN_TYPE = "Bearer "
    }
}