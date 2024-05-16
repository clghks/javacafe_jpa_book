package com.clghks.jpashop.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("M")
class Movie: Item() {
    var director: String? = null
    var actor: String? = null
}