package service

abstract class CRUD<T> {
    abstract fun create(item: T): Boolean
    abstract fun read(index: Int): T?
    abstract fun update(index: Int, item: T): Boolean
    abstract fun delete(index: Int): Boolean
    abstract fun list(): List<T>
}
