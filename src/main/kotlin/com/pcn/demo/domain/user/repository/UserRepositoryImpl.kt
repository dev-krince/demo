package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.model.user.QLoginInfo
import com.pcn.demo.domain.model.user.QUser
import com.pcn.demo.domain.model.user.dto.UserInfoDto
import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.LoginId
import com.pcn.demo.domain.model.user.vo.Username
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : UserQueryRepository {

    //시연용
    override fun findByUsername(name: Username): User? {
        val u: QUser = QUser.user

        return queryFactory
            .select(u)
            .from(u)
            .where(u.username.eq(name))
            .fetchFirst()
    }

    //시연용
    override fun searchUser(id: Long, loginId: LoginId, username: Username): List<User> {
        val u: QUser = QUser.user
        val condition = BooleanBuilder()
            .or(eqUserId(id))
            .or(containsUserLoginId(loginId))
            .or(containsUserName(username))

        return queryFactory
            .select(u)
            .from(u)
            .where(condition)
            .fetch()
    }

    //시연용
    override fun findUserInfoByUserId(id: Long): UserInfoDto? {
        val u: QUser = QUser.user
        val li: QLoginInfo = QLoginInfo.loginInfo
        val userInfoDto: ConstructorExpression<UserInfoDto> = Projections.constructor(
            UserInfoDto::class.java,
            u.id,
            u.loginId,
            u.password,
            u.username,
            u.role,
            li.masterUserId,
            li.isInitLogin,
            li.loginAttemptCount,
            li.isActiveUser,
            u.createdDate,
            u.modifiedDate
        )

        return queryFactory
            .select(userInfoDto)
            .from(u)
            .leftJoin(li).on(li.userId.eq(u.loginId.value))
            .where(u.id.eq(id))
            .fetchFirst()
    }

    //시연용
    private fun eqUserId(id: Long): BooleanExpression? {
        val u: QUser = QUser.user
        return if (Objects.nonNull(id)) u.id.eq(id) else null
    }

    //시연용
    private fun containsUserLoginId(loginId: LoginId): BooleanExpression? {
        val u: QUser = QUser.user
        return if (Objects.nonNull(loginId)) u.loginId.value.contains(loginId.value) else null
    }

    //시연용
    private fun containsUserName(username: Username): BooleanExpression? {
        val u: QUser = QUser.user
        return if (Objects.nonNull(username)) u.username.value.contains(username.value) else null
    }
}
