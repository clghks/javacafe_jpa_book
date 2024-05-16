package com.clghks.jpashop.domain

import jakarta.persistence.Embeddable

@Embeddable
class Address {
    var city: String? = null
    var street: String? = null
    var zipcode: String? = null

    constructor(city: String, street: String, zipcode: String) {
        this.city = city
        this.street = street
        this.zipcode = zipcode
    }
}