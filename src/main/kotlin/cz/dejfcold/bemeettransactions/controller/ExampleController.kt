package cz.dejfcold.bemeettransactions.controller

import cz.dejfcold.bemeettransactions.dto.SomeTable
import cz.dejfcold.bemeettransactions.repository.SomeTableRepository
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/")
class ExampleController(
    val repository: SomeTableRepository
) {
    @GetMapping("/read/{id}")
    fun readDatabase(@PathVariable("id") id: UUID): SomeTable? {
        return repository.findById(id).getOrNull()
    }

    @GetMapping("/write-new/{value}")
    fun writeDatabase(@PathVariable("value") value: String): SomeTable {
        val entity = SomeTable(someValue = value)
        return repository.save(entity)
    }

    @GetMapping("/update-existing/{id}/{value}")
    fun writeDatabase(@PathVariable("id") id: UUID, @PathVariable("value") value: String): SomeTable {
        val entity = repository.findById(id).orElseThrow()
        entity.someValue = value
        return repository.save(entity)
    }

    @GetMapping("dynmic-filter")
    fun findByFilter(
        @RequestParam("value") value: String?,
        @RequestParam("gaston") gaston: String?,
        pageable: Pageable
    ): Page<SomeTable> {
        println(value)
        println(gaston)
        val specification = buildSpecification(value, gaston)

        return repository.findAll(specification, pageable)
    }

    // WHERE
    fun buildSpecification(value: String?, gaston: String?, hraci: List<String> = listOf()): Specification<SomeTable> {
        return Specification<SomeTable> { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (hraci.isNotEmpty()) {
                predicates += root.get<String>("hrac").`in`(hraci)
            }

            if (value != null) {
                // some_value = :value
                predicates += criteriaBuilder.equal(root.get<String?>("someValue"), value)
            }
            if (gaston != null) {
                // gaston = :gaston
                predicates += criteriaBuilder.equal(root.get<String?>("gaston"), gaston)
            }

            // some_value = :value AND gaston = :gaston
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}