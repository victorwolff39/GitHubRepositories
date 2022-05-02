package com.victorwolff.githubrepositories.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.victorwolff.githubrepositories.model.OwnerModel
import com.victorwolff.githubrepositories.model.RepositoriesListModel
import com.victorwolff.githubrepositories.service.OwnerService
import com.victorwolff.githubrepositories.service.RepositoriesService
import com.victorwolff.githubrepositories.service.RetrofitClient
import com.victorwolff.githubrepositories.utils.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryListViewModel(application: Application) : AndroidViewModel(application) {
    private val _repository = MutableLiveData<RepositoriesListModel?>()
    val repository = _repository
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _areAllRepositoriesLoaded = MutableLiveData<Boolean>()
    val areAllRepositoriesLoaded = _areAllRepositoriesLoaded
    private val _message = MutableLiveData<String>()
    val message = _message
    private var _nextPage: Int = 0
    private var _isLast: Boolean = false
    private var _authKey: String = ""

    private val mRepositoryRemote = RetrofitClient.createService(RepositoriesService::class.java)
    private val mOwnerRemote = RetrofitClient.createService(OwnerService::class.java)
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    init {
        getAuthHeader(Helper.getConfigValue(context, "github_key"))
        mGetRepositoryList()
    }

    fun refreshRepositoryList() {
        _nextPage = 0
        _isLast = false
        mGetRepositoryList()
    }

    fun getRepositoryList() {
        mGetRepositoryList()
    }

    private fun mGetRepositoryList() {
        _isLoading.value = true

        if (!_isLast) {
            val call: Call<RepositoriesListModel> = if (_nextPage != 0) {
                mRepositoryRemote.list("language:kotlin", "stars", _nextPage)
            } else {
                mRepositoryRemote.list("language:kotlin", "stars")
            }
            call.enqueue(object : Callback<RepositoriesListModel> {
                override fun onResponse(
                    call: Call<RepositoriesListModel>,
                    response: Response<RepositoriesListModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val headers = response.headers()

                        if (data != null) {

                            if (headers.get("Link") != null) {
                                val header = headers.get("Link")
                                parseNextPageHeader(header)
                            }
                            _areAllRepositoriesLoaded.value = true

                            if (data.items.isNotEmpty()) {

                                // Get owner name or uses user's login as default
                                setOwnerName(data)
                            }
                        } else {
                            _message.value = "No repositories found"
                        }
                    } else {
                        _message.value = "Unknown server error"
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<RepositoriesListModel>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = "Unknown server error"
                }
            })
        }
    }

    private fun setOwnerName(data: RepositoriesListModel) {
        val items = data.items

        for (i in items.indices) {
            val item = items[i]
            val login = item.owner.login
            val call: Call<OwnerModel> = mOwnerRemote.getInfo(login, _authKey)

            val response = call.execute()

            if (response.isSuccessful) {
                val name = response.body()?.name

                if (name != null) {
                    item.owner.name = name
                } else {
                    item.owner.name = login
                }
            } else {
                item.owner.name = login
            }

            data.items[i] = item
        }

        _repository.value = data
    }

    private fun parseNextPageHeader(header: String?) {
        if (header != null) {
            val aux: String = header.trim()
            val map: HashMap<String, Int> = hashMapOf()

            val links: List<String> = aux.split(",")

            for (link in links) {
                var page: Int = 0
                var type: String = ""

                type = link.trim().substringAfter("rel=\"").substringBefore("\"")
                page = link.trim().substringAfter("&page=").substringBefore(">").toInt()
                map[type] = page
            }

            if (map.containsKey("next")) {
                _nextPage = map["next"]!!
                _isLast = false
            } else {
                _isLast = true
            }
        }
    }

    private fun getAuthHeader(key: String?) {
        if (key != null && key.isNotEmpty()) {
            val data = key.toByteArray()
            _authKey = "Basic ${Base64.encodeToString(data, Base64.NO_WRAP)}".trim()
        }
    }

    companion object {
        private val TAG = RepositoryListViewModel::class.java.simpleName
    }
}