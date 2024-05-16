package com.clghks.jpashop.service

import com.clghks.jpashop.domain.*
import com.clghks.jpashop.exception.NotEnoughStockException
import com.clghks.jpashop.repository.ItemRepository
import com.clghks.jpashop.repository.MemberRepository
import com.clghks.jpashop.repository.OrderRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class OrderServiceTest(
    @Autowired private val orderService: OrderService,
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val itemRepository: ItemRepository
) {
    @Test
    fun `상품 주문`() {
        // Given
        val member: Member = createMember()
        val item: Item = createBook("시골 JPQ", 10000, 10)
        val orderCount = 2

        // When
        val orderId = orderService.order(member.id, item.id, orderCount)

        // Then
        val getOrder: Order = orderRepository.findOne(orderId)

        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(getOrder.status)
        Assertions.assertThat(1).isEqualTo(getOrder.orderItems.size)
        Assertions.assertThat(10000 * 2).isEqualTo(getOrder.getTotalPrice())
        Assertions.assertThat(8).isEqualTo(item.stockQuantity)
    }

    @Test
    fun `상품주문_재고수량초과`() {
        // Given
        val member: Member = createMember()
        val item: Item = createBook("시골 JPQ", 10000, 10)
        val orderCount = 11

        // Then
        assertThrows(NotEnoughStockException::class.java) {
            // When
            orderService.order(member.id, item.id, orderCount)
        }
    }

    @Test
    fun `주문취소`() {
        // Given
        val member: Member = createMember()
        val item: Item = createBook("시골 JPQ", 10000, 10)
        val orderCount = 2

        val orderId = orderService.order(member.id, item.id, orderCount)

        // When
        orderService.cancelOrder(orderId)

        // Then
        val getOrder: Order = orderRepository.findOne(orderId)
        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.status)
        Assertions.assertThat(10).isEqualTo(item.stockQuantity)
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Item {
        val book = Book()
        book.name = name
        book.price = price
        book.stockQuantity = stockQuantity
        itemRepository.save(book)

        return book
    }

    private fun createMember(): Member {
        val member = Member()
        member.name = "회원1"
        member.address = Address("서울", "강가", "123-1234")
        memberRepository.save(member)

        return member
    }
}