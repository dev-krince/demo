package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.user.entity.LoginInfo
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Function

class FakeLoginInfoRepository : com.pcn.demo.domain.user.repository.LoginInfoRepository {
    private val storage: MutableMap<Long, LoginInfo> = HashMap()
    private val idGenerator = AtomicLong(1)

    override fun countByIsActiveUser(activeUser: Boolean): Long {
        return storage.values
            .stream()
            .filter { loginInfo: LoginInfo -> loginInfo.isActiveUser == activeUser }
            .count()
    }

    override fun flush() {
    }

    override fun <S : LoginInfo> saveAndFlush(entity: S): S {
        val now = LocalDateTime.now()

        return LoginInfo(
            id = idGenerator.incrementAndGet(),
            userId = entity.userId,
            masterUserId = entity.masterUserId,
            isInitLogin = entity.isInitLogin,
            loginAttemptCount = entity.loginAttemptCount,
            isActiveUser = entity.isActiveUser,
            createdAt = now,
            modifiedAt = now
        ) as S
    }

    override fun <S : LoginInfo?> saveAllAndFlush(entities: Iterable<S>): List<S> {
        return listOf()
    }

    override fun deleteAllInBatch(entities: Iterable<LoginInfo>) {
    }

    override fun deleteAllByIdInBatch(longs: Iterable<Long>) {
    }

    override fun deleteAllInBatch() {
    }

    override fun getOne(aLong: Long): LoginInfo {
        return storage[aLong]!!
    }

    override fun getById(aLong: Long): LoginInfo {
        return storage[aLong]!!
    }

    override fun getReferenceById(aLong: Long): LoginInfo {
        return storage[aLong]!!
    }

    override fun <S : LoginInfo?> findOne(example: Example<S>): Optional<S> {
        return Optional.ofNullable(storage[0]) as Optional<S>
    }

    override fun <S : LoginInfo?> findAll(example: Example<S>): List<S> {
        return listOf()
    }

    override fun <S : LoginInfo?> findAll(example: Example<S>, sort: Sort): List<S> {
        return listOf()
    }

    override fun <S : LoginInfo?> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        return Page.empty()
    }

    override fun <S : LoginInfo?> count(example: Example<S>): Long {
        return 0
    }

    override fun <S : LoginInfo?> exists(example: Example<S>): Boolean {
        return false
    }

    override fun <S : LoginInfo?, R> findBy(
        example: Example<S>,
        queryFunction: Function<FetchableFluentQuery<S>, R>
    ): R {
        TODO()
    }

    override fun <S : LoginInfo?> save(entity: S): S {
        val now = LocalDateTime.now()
        val id = idGenerator.get()
        val loginInfo = LoginInfo(
            id = idGenerator.incrementAndGet(),
            userId = entity!!.userId,
            masterUserId = entity.masterUserId,
            isInitLogin = entity.isInitLogin,
            loginAttemptCount = entity.loginAttemptCount,
            isActiveUser = entity.isActiveUser,
            createdAt = now,
            modifiedAt = now
        )

        storage[id] = loginInfo

        return loginInfo as S
    }

    override fun <S : LoginInfo?> saveAll(entities: Iterable<S>): List<S> {
        return listOf()
    }

    override fun findById(aLong: Long): Optional<LoginInfo> {
        return Optional.empty()
    }

    override fun existsById(aLong: Long): Boolean {
        return false
    }

    override fun findAll(): List<LoginInfo> {
        return listOf()
    }

    override fun findAllById(longs: Iterable<Long>): List<LoginInfo> {
        return listOf()
    }

    override fun count(): Long {
        return 0
    }

    override fun deleteById(aLong: Long) {
    }

    override fun delete(entity: LoginInfo) {
    }

    override fun deleteAllById(longs: Iterable<Long>) {
    }

    override fun deleteAll(entities: Iterable<LoginInfo>) {
    }

    override fun deleteAll() {
        storage.clear()
    }

    override fun findAll(sort: Sort): List<LoginInfo> {
        return listOf()
    }

    override fun findAll(pageable: Pageable): Page<LoginInfo> {
        TODO()
    }
}
