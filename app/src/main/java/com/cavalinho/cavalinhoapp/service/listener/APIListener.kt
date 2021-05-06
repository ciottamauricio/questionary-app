package com.cavalinho.cavalinhoapp.service.listener

interface APIListener<T> {
    fun onSuccess(result: T, statusCode: Int)
    fun onFailure(message: String)
}