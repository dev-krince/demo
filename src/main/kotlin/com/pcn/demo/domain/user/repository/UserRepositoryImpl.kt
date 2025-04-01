package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.user.dto.vo.UserInfoDto
import com.pcn.demo.domain.user.entity.QLoginInfo
import com.pcn.demo.domain.user.entity.QUser
import com.pcn.demo.domain.user.entity.User
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
    override fun findByUsername(name: String): User? {
        val u: QUser = QUser.user
        val user: ConstructorExpression<User> = generateUserConstructorExpression()

        return queryFactory
            .select(user)
            .from(u)
            .where(u.name.eq(name))
            .fetchFirst()
    }

    //시연용
    override fun searchUser(id: Long, loginId: String, name: String): List<User> {
        val u: QUser = QUser.user
        val user: ConstructorExpression<User> = generateUserConstructorExpression()
        val condition = BooleanBuilder()
            .or(eqUserId(id))
            .or(containsUserLoginId(loginId))
            .or(containsUserName(name))

        return queryFactory
            .select(user)
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
            u.name,
            u.role,
            li.masterUserId,
            li.isInitLogin,
            li.loginAttemptCount,
            li.isActiveUser,
            u.createdAt,
            u.modifiedAt
        )

        return queryFactory
            .select(userInfoDto)
            .from(u)
            .leftJoin(li).on(li.userId.eq(u.loginId))
            .where(u.id.eq(id))
            .fetchFirst()
    }

    //시연용
    private fun generateUserConstructorExpression(): ConstructorExpression<User> {
        val u: QUser = QUser.user
        return Projections.constructor<User>(
            User::class.java,
            u.id,
            u.loginId,
            u.password,
            u.name,
            u.role,
            u.createdAt,
            u.modifiedAt
        )
    }

    //시연용
    private fun eqUserId(id: Long): BooleanExpression? {
        val u: QUser = QUser.user
        return if (Objects.nonNull(id)) u.id.eq(id) else null
    }

    //시연용
    private fun containsUserLoginId(loginId: String): BooleanExpression? {
        val u: QUser = QUser.user
        return if (Objects.nonNull(loginId)) u.loginId.contains(loginId) else null
    }

    //시연용
    private fun containsUserName(name: String): BooleanExpression? {
        val u: QUser = QUser.user
        return if (Objects.nonNull(name)) u.name.contains(name) else null
    }
}
