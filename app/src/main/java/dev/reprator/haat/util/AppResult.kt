package dev.reprator.haat.util

sealed interface AppResult<out T> {
    open fun get(): T? = null
}

data class AppSuccess<T>(val data: T, val responseModified: Boolean = true) : AppResult<T> {
    override fun get(): T = data
}

data class AppError(
    val throwable: Throwable? = null,
    val message: String? = null
) : AppResult<Nothing>