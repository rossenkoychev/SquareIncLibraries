package com.example.rossen.squareinclibs.model

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
    val repos: PublishSubject<JsonObject> = PublishSubject.create()
    val errorLoadingRepos:PublishSubject<String> = PublishSubject.create() // TODO think of how to combine those two

    init {
        reposClient = ReposClient()
    }

    fun getRepos() {
        val disposable=CompositeDisposable()
        disposable.add(reposClient.queryRepos()
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              if(  it.isSuccessful ){
                val a =5
                  disposable.dispose()
              }
            },{
                //TODO notify for an error, use the response object to add an error to it and it there is error ui shuld be able to show it.
                //OR just use another obsrevable
                var b =4
                var c=b
            }
            ))
    }

    fun getStargazers(repo: String) {
        //TODO get query satrgazer with rxkotlin
    }
}