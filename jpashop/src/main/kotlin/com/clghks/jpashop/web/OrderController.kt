package com.clghks.jpashop.web

import com.clghks.jpashop.domain.Item
import com.clghks.jpashop.domain.Member
import com.clghks.jpashop.service.ItemService
import com.clghks.jpashop.service.MemberService
import com.clghks.jpashop.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
class OrderController(
    private val orderService: OrderService,
    private val memberService: MemberService,
    private val itemService: ItemService
) {
    @RequestMapping(value = ["/order"], method = [RequestMethod.GET])
    fun createFrom(model: Model): String {
        val members: List<Member> = memberService.findMembers()
        val items: List<Item> = itemService.findItems()

        model.addAttribute("members", members)
        model.addAttribute("items", items)

        return "order/orderForm"
    }

    @RequestMapping(value = ["/order"], method = [RequestMethod.POST])
    fun order(@RequestParam("memberId") memberId: Long, @RequestParam("itemId") itemId: Long, @RequestParam("count") count: Int): String {
        orderService.order(memberId, itemId, count)

        return "redirect:/orders"
    }
}