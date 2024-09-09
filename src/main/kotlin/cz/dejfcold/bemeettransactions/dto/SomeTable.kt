package cz.dejfcold.bemeettransactions.dto

import jakarta.persistence.*
import java.util.*

@Entity
data class SomeTable(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "some_value", nullable = true)
    var someValue: String? = null,

    @Version
    var version: Int = 1,

    @Column(name = "gaston")
    var gaston: String? = null
)
