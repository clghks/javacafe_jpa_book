package com.clghks.jpashop.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    var id: Long = 0

    var name: String? = null

    @Embedded
    var address: Address? = null

    @OneToMany(mappedBy = "member")
    var orders: MutableList<Order> = arrayListOf()

}