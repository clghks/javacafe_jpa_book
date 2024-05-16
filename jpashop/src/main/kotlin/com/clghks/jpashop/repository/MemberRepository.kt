package com.clghks.jpashop.repository

import com.clghks.jpashop.domain.Member
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun save(member: Member) {
        entityManager.persist(member)
    }

    fun findOne(id: Long): Member {
        return entityManager.find(Member::class.java, id)
    }

    fun findAll(): List<Member> {
        return entityManager.createQuery("select m from Member m", Member::class.java).resultList
    }

    fun findByName(name: String?): List<Member> {
        return entityManager.createQuery("select m from Member m where m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
    }
}