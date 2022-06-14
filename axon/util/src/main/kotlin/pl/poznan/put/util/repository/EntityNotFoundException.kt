package pl.poznan.put.util.repository

class EntityNotFoundException(id: Any) : RuntimeException("Entity with id = $id not found.")