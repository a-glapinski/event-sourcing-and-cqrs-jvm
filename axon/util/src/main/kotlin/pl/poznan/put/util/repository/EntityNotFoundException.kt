package pl.poznan.put.util.repository

class EntityNotFoundException(
    id: Any,
    entityName: String? = null,
) : RuntimeException("${entityName ?: "Entity"} with id = $id not found.")