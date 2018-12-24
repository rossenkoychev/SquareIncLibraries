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
import java.util.concurrent.TimeUnit

//this content provider is used to get data from the appropriate data source. be it web call or db or both
class DataProvider {

    val reposClient: ReposClient
    val repos: MutableLiveData<List<Repository>> = MutableLiveData()
    val errorLoadingRepos: PublishSubject<String> = PublishSubject.create() // TODO think of how to combine those two

    val stargazers: MutableLiveData<List<Stargazer>> = MutableLiveData()

    init {
        reposClient = ReposClient()

    }

    fun getRepos() {
        val disposable = CompositeDisposable()
        disposable.add(reposClient.queryRepos()
            .delay(2000, TimeUnit.MILLISECONDS)  //just for testing
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    repos.value = response.body()
                }
                disposable.dispose()
            }
                , { error ->
                    repos.value = listOf()
                    //TODO notify for an error, use the response object to add an error to it and it there is error ui should be able to show it.
                    //OR just use another obsrevable
                }
            ))
    }

    fun getStargazers(repository: Repository) {
        val disposable = CompositeDisposable()
        disposable.add(reposClient.queryStargazers(repository.name)
           // .delay(2000, TimeUnit.MILLISECONDS)  //just for testing
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    repository.stargazers = response.body()
                    stargazers.value=response.body()
                }
                disposable.dispose()
            }
                , { error ->
                    repos.value = listOf()
                    //TODO notify for an error, use the response object to add an error to it and it there is error ui should be able to show it.
                    //OR just use another obsrevable
                }
            ))
    }
}