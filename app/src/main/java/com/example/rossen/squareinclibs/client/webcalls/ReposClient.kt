package com.example.rossen.squareinclibs.client.webcalls

import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.Stargazer
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class is used to build and parametrise retrofit
 */
class ReposClient {
    private val baseUrl = "https://api.github.com"
    private val squareIncService: SquareIncService

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        squareIncService = retrofit.create(SquareIncService::class.java)
    }

    fun queryRepos(): Observable<List<Repository>> {
        return squareIncService.queryRepos()
    }

    fun queryStargazers(repoName: String): Observable<List<Stargazer>> {
        return squareIncService.getStargazers(repoName)
    }
}