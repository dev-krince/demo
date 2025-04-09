package com.pcn.demo.domain.user.repository

import com.pcn.demo.domain.model.user.dto.UserInfoDto
import com.pcn.demo.domain.model.user.User
import com.pcn.demo.domain.model.user.vo.LoginId
import com.pcn.demo.domain.model.user.vo.Username
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Function

class FakeUserRepository : UserRepository {
    private val storage: MutableMap<Long, User> = HashMap()
    private val idGenerator = AtomicLong(1)

    override fun existsByLoginId(loginId: LoginId): Boolean {
        return storage.values
            .stream()
            .anyMatch { user: User -> user.loginId == loginId }
    }

    override fun findByLoginId(loginId: LoginId): User? {
        return storage.values.find { user: User -> user.loginId == loginId }
    }

    override fun findByUsername(username: Username): User? {
        TODO()
    }

    override fun searchUser(id: Long, loginId: LoginId, username: Username): List<User> {
        TODO()
    }

    override fun findUserInfoByUserId(id: Long): UserInfoDto? {
        TODO()
    }

    override fun flush() {
    }

    override fun <S : User?> saveAndFlush(entity: S): S {
        TODO()
    }

    override fun <S : User?> saveAllAndFlush(entities: Iterable<S>): List<S> {
        return listOf()
    }

    override fun deleteAllInBatch(entities: Iterable<User>) {
    }

    override fun deleteAllByIdInBatch(longs: Iterable<Long>) {
    }

    override fun deleteAllInBatch() {
    }

    override fun getOne(aLong: Long): User {
        TODO()
    }

    override fun getById(aLong: Long): User {
        TODO()
    }

    override fun getReferenceById(aLong: Long): User {
        TODO()
    }

    override fun <S : User?> findOne(example: Example<S>): Optional<S> {
        TODO()
    }

    override fun <S : User?> findAll(example: Example<S>): List<S> {
        return listOf()
    }

    override fun <S : User?> findAll(example: Example<S>, sort: Sort): List<S> {
        return listOf()
    }

    override fun <S : User?> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        TODO()
    }

    override fun <S : User?> count(example: Example<S>): Long {
        return 0
    }

    override fun <S : User?> exists(example: Example<S>): Boolean {
        return false
    }

    override fun <S : User?, R> findBy(example: Example<S>, queryFunction: Function<FetchableFluentQuery<S>, R>): R {
        TODO()
    }

    override fun <S : User?> save(entity: S): S {
        val now = LocalDateTime.now()
        val id = idGenerator.get()
        val user = User.of(
            id = idGenerator.incrementAndGet(),
            loginId = entity!!.loginId,
            password = entity.password,
            username = entity.username,
            role = entity.role,
            createdDate = now,
            modifiedDate = now,
        )

        storage[id] = entity

        return user as S
    }

    override fun <S : User?> saveAll(entities: Iterable<S>): List<S> {
        return listOf()
    }

    override fun findById(aLong: Long): Optional<User> {
        return Optional.empty()
    }

    override fun existsById(aLong: Long): Boolean {
        return false
    }

    override fun findAll(): List<User> {
        return listOf()
    }

    override fun findAllById(longs: Iterable<Long>): List<User> {
        return listOf()
    }

    override fun count(): Long {
        return 0
    }

    override fun deleteById(aLong: Long) {
    }

    override fun delete(entity: User) {
    }

    override fun deleteAllById(longs: Iterable<Long>) {
    }

    override fun deleteAll(entities: Iterable<User>) {
    }

    override fun deleteAll() {
        storage.clear()
    }

    override fun findAll(sort: Sort): List<User> {
        return listOf()
    }

    override fun findAll(pageable: Pageable): Page<User> {
        TODO()
    }
}
