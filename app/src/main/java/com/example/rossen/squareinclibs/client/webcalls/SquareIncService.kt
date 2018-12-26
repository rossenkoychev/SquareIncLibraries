package com.example.rossen.squareinclibs.client.webcalls

import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.Stargazer
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface SquareIncService {
    @GET("/orgs/square/repos")
    fun queryRepos(): Observable<List<Repository>>

    @GET("/repos/square/{repositoryId}/stargazers")
    fun getStargazers(@Path("repositoryId") repositoryID: String): Observable<List<Stargazer>>

}
