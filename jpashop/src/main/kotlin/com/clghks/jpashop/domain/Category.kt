package com.clghks.jpashop.domain

import jakarta.persistence.*

@Entity
class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    var id: Long? = null

    var name: String? = null

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM", joinColumns = [JoinColumn(name = "CATEGORY_ID")], inverseJoinColumns = [JoinColumn(name = "ITEM_ID")])
    var items: MutableList<Item> = arrayListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    var parent: Category? = null

    @OneToMany(mappedBy = "parent")
    var child: MutableList<Category> = arrayListOf()

    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}