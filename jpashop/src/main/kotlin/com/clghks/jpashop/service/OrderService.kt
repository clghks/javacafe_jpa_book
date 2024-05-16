package com.clghks.jpashop.service

import com.clghks.jpashop.domain.Delivery
import com.clghks.jpashop.domain.Order
import com.clghks.jpashop.domain.OrderItem
import com.clghks.jpashop.domain.OrderSearch
import com.clghks.jpashop.repository.MemberRepository
import com.clghks.jpashop.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(
    @Autowired val memberRepository: MemberRepository,
    @Autowired val orderRepository: OrderRepository,
    @Autowired val itemService: ItemService
) {
    // 주문
    fun order(memberId: Long, itemId: Long?, count: Int): Long? {
        val member = memberRepository.findOne(memberId)
        val item = itemService.findOne(itemId)

        val delivery = Delivery(member.address)
        val orderItem = OrderItem.createOrderItem(item, item.price, count)

        val order = Order.createOrder(member, delivery, orderItem)
        orderRepository.save(order)

        return order.id
    }

    // 주문 취소
    fun cancelOrder(orderId: Long?) {
        val order = orderRepository.findOne(orderId)
        order.cancel()
    }

    // 주문 검색
    fun findOrders(orderSearch: OrderSearch): List<Order> {
        return orderRepository.findAll(orderSearch)
    }
}