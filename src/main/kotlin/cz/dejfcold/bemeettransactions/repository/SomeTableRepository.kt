package cz.dejfcold.bemeettransactions.repository

import cz.dejfcold.bemeettransactions.dto.SomeTable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SomeTableRepository: CrudRepository<SomeTable, UUID>, JpaSpecificationExecutor<SomeTable>{


    @Query("select s from SomeTable s where upper(s.someValue) = upper(?1) and s.gaston = ?2 order by s.version DESC")
    fun findBySomeValueIgnoreCaseAndGastonOrderByVersionDesc(someValue: String, gaston: String): List<SomeTable>

}