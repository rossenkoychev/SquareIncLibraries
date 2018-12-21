package com.example.rossen.squareinclibs

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.rossen.squareinclibs.model.DataProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LibrariyListViewModel(val context: Application) : AndroidViewModel(context) {


    val dataProvider: DataProvider

    init {
        dataProvider = DataProvider()
        getRepos()
    }

    private fun getRepos() {
        val disposable = CompositeDisposable()
        disposable.add(dataProvider.repos
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    disposable.dispose()
                },
                {
                    disposable.dispose()
                }
            ))
        dataProvider.getRepos()
    }
}