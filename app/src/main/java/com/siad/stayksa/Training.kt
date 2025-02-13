package com.siad.stayksa

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.runBlocking

data class Producet(
    val name: String,
    val price: Int,
)

data class Review(
    val name: String,
    val rating: Int,
)

// Those two flows are depended on each other
fun getProduce(): Flow<Producet> = flow {
    emit(Producet(name = "A", price = 4999))
    emit(Producet(name = "B", price = 5120))
    emit(Producet(name = "C", price = 1225))
}

fun getReview(producet: Producet) : Flow<Review> = flow {
    delay(1000)
    emit(Review(name = producet.name, rating = 4 ))
}

fun fetchData(): Flow<String> = flow<String> {
    delay(3000)
    throw Exception("Network Error")
}.retry(3)

fun main() {
    runBlocking {
        getProduce().map {
            it.copy(price = it.price / 100)
        }.collect {
            println("${it.name} costs \$ ${it.price}")
        }
    }
    runBlocking {
        getProduce().flatMapMerge {
            getReview(it)
        }.collect{
            println("Review For ${it.name} \$ ${it.rating}")

        }
    }
    runBlocking {
        fetchData()
            .onEach {
                println("Recived: ${it}")
            }
            .catch {
                println("Failed: ${it.message}")
            }
            .collect()
    }
}