package com.example.rossen.squareinclibs.dataProvider

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.rossen.squareinclibs.client.db.BookmarkEntity
import com.example.rossen.squareinclibs.client.db.BookmarksDB
import com.example.rossen.squareinclibs.client.webcalls.ReposClient
import com.example.rossen.squareinclibs.model.RepositoriesState
import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.Stargazer
import com.example.rossen.squareinclibs.model.StargazersState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * this content provider is used to get data from the appropriate data source. be it web call or db or both
 */
class DataProvider(val context: Context) {

    private val internalReposState = MutableLiveData<RepositoriesState>()
    private val internalStargazersState = MutableLiveData<StargazersState>()
    private val reposClient: ReposClient = ReposClient()
    private val bookmarksDB = BookmarksDB.getInstance(context)

    val reposState: LiveData<RepositoriesState> = internalReposState
    val stargazersState: LiveData<StargazersState> = internalStargazersState


    /**
     * makes a web call to retrieve the list of repos
     */
    fun getRepos() {
        internalReposState.value = RepositoriesState.Loading
        val disposable = CompositeDisposable()
        disposable.add(reposClient.queryRepos()
            // .delay(2000, TimeUnit.MILLISECONDS)  //just for testing
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ repositories ->
                internalReposState.value =
                        RepositoriesState.Repositories(repositories)
                getBookmarks(repositories)
                disposable.dispose()
            }
                , { throwable ->
                    internalReposState.value =
                            RepositoriesState.ReposError(throwable.message)
                    disposable.dispose()
                }
            ))
    }

    /**
     * Checks if this repo has loaded stargazers, if not then makes a web call to retrieve stargazers
     * @repository - the repo for which we are getting the data
     */
    fun getStargazers(repository: Repository) {
        val stargazerList = repository.stargazers
        if (stargazerList == null) {
            internalStargazersState.value = StargazersState.Loading

            val disposable = CompositeDisposable()
            disposable.add(reposClient.queryStargazers(repository.name)
                //.delay(2000, TimeUnit.MILLISECONDS)  //just for testing
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ stargazers ->
                    internalStargazersState.value =
                            StargazersState.Stargazers(stargazers)
                    disposable.dispose()
                }
                    , { throwable ->
                        // stargazers.value = listOf()
                        internalStargazersState.value =
                                StargazersState.StargazersError(throwable.message)
                        disposable.dispose()
                    }
                ))
        } else {
            internalStargazersState.value = StargazersState.Stargazers(stargazerList)
        }
    }

    fun addBookmark(repository: Repository) {
        Single.fromCallable {
            bookmarksDB.bookmarkDao().insertBookmark(BookmarkEntity(repository.name))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        repository.isBookmarked = true
    }

    fun deleteBookmark(repository: Repository) {
        Single.fromCallable {
            bookmarksDB.bookmarkDao().deleteBookmark(BookmarkEntity(repository.name))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        repository.isBookmarked = false
    }

    private fun getBookmarks(repos: List<Repository>) {
        val disposable = CompositeDisposable()
        disposable.add(bookmarksDB.bookmarkDao().getBookmarks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bookmarkedRepos ->
                val reposNames = bookmarkedRepos.flatMap { (String) -> listOf(String) }
                for (repo in repos) {
                    repo.isBookmarked = reposNames.contains(repo.name)
                }
            }
        )
    }


}