package pl.poznan.put.util.repository

import org.springframework.data.repository.CrudRepository
import java.util.function.Supplier

inline fun <reified T, ID : Any> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T =
    findByIdOrThrow(id) { EntityNotFoundException(id = id, entityName = T::class.simpleName) }

fun <T, ID : Any, X : Throwable> CrudRepository<T, ID>.findByIdOrThrow(id: ID, exceptionSupplier: Supplier<X>): T =
    findById(id).orElseThrow(exceptionSupplier)