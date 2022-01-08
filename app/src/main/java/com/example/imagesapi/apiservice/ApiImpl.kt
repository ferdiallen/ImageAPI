package com.example.imagesapi.apiservice

import coil.network.HttpException
import com.example.imagesapi.base.BaseUrl
import com.example.imagesapi.data.Url
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*

class ApiImpl(private val httpInject: HttpClient) : ApiService {
    override suspend fun getResponseAsStart(): Url {
        return try {
            httpInject.get {
                url(BaseUrl.getDefaultData)
            }
        } catch (e: HttpException) {
            println(e.message)
            return Url(emptyList())
        } catch (e: RedirectResponseException) {
            return Url(emptyList())
        } catch (e: IOException) {
            e.printStackTrace()
            return Url(emptyList())
        }
    }
}