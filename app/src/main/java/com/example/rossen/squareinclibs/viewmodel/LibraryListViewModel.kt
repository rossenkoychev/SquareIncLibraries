package com.example.rossen.squareinclibs.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.rossen.squareinclibs.model.DataProvider
import com.example.rossen.squareinclibs.model.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LibraryListViewModel(val context: Application) : AndroidViewModel(context) {

    var repositories: MutableLiveData<List<Repository>> = MutableLiveData()
    val dataProvider: DataProvider

    init {
        dataProvider = DataProvider()
        repositories = dataProvider.repos
        dataProvider.getRepos()
        //getRepos()
    }

//    private fun getRepos() {
//        val disposable = CompositeDisposable()
//        disposable.add(dataProvider.repos
//            .observeOn(Schedulers.io())
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//
//                    disposable.dispose()
//                },
//                {
//                    disposable.dispose()
//                }
//            ))
//        dataProvider.getRepos()
//    }
}