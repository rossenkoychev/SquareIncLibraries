package com.example.rossen.squareinclibs.dataProvider

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.rossen.squareinclibs.App
import com.example.rossen.squareinclibs.client.db.BookmarkEntity
import com.example.rossen.squareinclibs.client.db.BookmarksDB
import com.example.rossen.squareinclibs.client.webcalls.ReposClient
import com.example.rossen.squareinclibs.model.RepositoriesState
import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.StargazersState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * This content provider is used to get data from the appropriate data source. be it web call or db or both
 */
class DataProvider {

    private val internalReposState = MutableLiveData<RepositoriesState>()
    private val internalStargazersState = MutableLiveData<StargazersState>()
    private val reposClient: ReposClient = ReposClient()

    @Inject
    lateinit var bookmarksDB: BookmarksDB

    val reposState: LiveData<RepositoriesState> = internalReposState
    val stargazersState: LiveData<StargazersState> = internalStargazersState

    init {
        bookmarksDB = App.instance.dbComponent.providesDataBase()
    }

    /**
     * makes a web call to retrieve the list of repos_container
     */
    fun getRepos() {
        LiveDataStateIdlingResource.increment()
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
                LiveDataStateIdlingResource.decrement()
            }
                , { throwable ->
                    internalReposState.value =
                            RepositoriesState.ReposError(throwable.message)
                    disposable.dispose()
                }
            ))
    }

    /**
     * Checks if this repo has loaded stargazers, if not then makes a web call to retrieve stargazers.
     * If needed we can still load the stargazers from web, even if already loaded, but since they are not expected to change rapidly
     * we can safely show what has been loaded earlier.
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
                    repository.stargazers = stargazers
                    disposable.dispose()
                }
                    , { throwable ->
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
        internalReposState.value = internalReposState.value
    }

    fun deleteBookmark(repository: Repository) {
        Single.fromCallable {
            bookmarksDB.bookmarkDao().deleteBookmark(repository.name)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        repository.isBookmarked = false
        internalReposState.value = internalReposState.value
    }

    //get bookmarks is called initially to check which repos are already bookmarked
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
                internalReposState.value = internalReposState.value
            }
        )
    }
}