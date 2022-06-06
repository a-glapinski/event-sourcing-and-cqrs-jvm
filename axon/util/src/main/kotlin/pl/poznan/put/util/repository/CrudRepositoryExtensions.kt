package pl.poznan.put.util.repository

import org.springframework.data.repository.CrudRepository
import java.util.function.Supplier

fun <T, ID : Any> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T =
    findById(id).orElseThrow()

fun <T, ID : Any, X : Throwable> CrudRepository<T, ID>.findByIdOrThrow(id: ID, exceptionSupplier: Supplier<X>): T =
    findById(id).orElseThrow(exceptionSupplier)