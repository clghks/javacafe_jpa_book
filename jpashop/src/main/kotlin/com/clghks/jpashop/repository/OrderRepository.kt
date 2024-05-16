package com.clghks.jpashop.repository

import com.clghks.jpashop.domain.Member
import com.clghks.jpashop.domain.Order
import com.clghks.jpashop.domain.OrderSearch
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.*
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

@Repository
class OrderRepository(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    fun save(order: Order) {
        entityManager.persist(order)
    }

    fun findOne(id: Long?): Order {
        return entityManager.find(Order::class.java, id)
    }

    fun findAll(orderSearch: OrderSearch): List<Order> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Order::class.java)
        val order: Root<Order> = criteriaQuery.from(Order::class.java)

        val criteria: MutableList<Predicate> = mutableListOf()

        if (orderSearch.orderStatus != null) {
            val status: Predicate = criteriaBuilder.equal(order.get<Any>("status"), orderSearch.orderStatus)
            criteria.add(status)
        }

        if (StringUtils.hasText(orderSearch.memberName)) {
            val m: Join<Order, Member> = order.join("member", JoinType.INNER)
            val name = criteriaBuilder.like(m.get("name"), "%${orderSearch.memberName}%")
            criteria.add(name)
        }

        criteriaQuery.where(criteriaBuilder.and(*criteria.toTypedArray<Predicate>()))
        val query: TypedQuery<Order> = entityManager.createQuery(criteriaQuery).setMaxResults(1000)

        return query.resultList
    }
}