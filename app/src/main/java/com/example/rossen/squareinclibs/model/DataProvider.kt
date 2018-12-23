package com.example.rossen.squareinclibs.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.rossen.squareinclibs.client.webcalls.ReposClient
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

//this content provider is used to get data from the appropriate data source. be it web call or db or both
class DataProvider {

    val reposClient: ReposClient
    val repos: MutableLiveData<List<Repository>> = MutableLiveData()
    val errorLoadingRepos: PublishSubject<String> = PublishSubject.create() // TODO think of how to combine those two

    init {
        reposClient = ReposClient()
        //repos.value= listOf()
    }

    fun getRepos() {
        val disposable = CompositeDisposable()
        disposable.add(reposClient.queryRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    repos.value = response.body()

//                    response?.let {
//                        if (response.isSuccessful) {
//                            val result = response.body()
//                            result?.let {
//                                return List(result.size()) { i -> SkuDetails(result.get(i).toString()) }
//                            }
//                        }
//                    }
                }
                disposable.dispose()
            }
                , { error ->
                    repos.value = listOf()
                    //TODO notify for an error, use the response object to add an error to it and it there is error ui should be able to show it.
                    //OR just use another obsrevable
                    var b = 4
                    var c = b
                }
            ))
    }

    fun getStargazers(repo: String) {
        //TODO get query satrgazer with rxkotlin
    }
}