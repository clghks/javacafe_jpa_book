package com.clghks.jpashop.domain

import jakarta.persistence.*

@Entity
class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    var id: Long? = null

    @OneToOne(mappedBy = "delivery")
    var order: Order? = null

    @Embedded
    var address: Address? = null

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus? = null

    constructor(address: Address?) {
        this.address = address
    }
}