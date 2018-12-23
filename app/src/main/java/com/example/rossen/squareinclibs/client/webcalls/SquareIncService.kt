package com.example.rossen.squareinclibs.client.webcalls

import com.example.rossen.squareinclibs.model.Repository
import com.google.gson.JsonArray
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SquareIncService {
    @GET("repos")
    fun queryRepos(): Observable<Response<List<Repository>>>

    @POST("{repositoryId}/stargazers")
    fun getStargazers(@Path("repositoryId") repositoryID: String): Observable<Response<JsonArray>>

}
