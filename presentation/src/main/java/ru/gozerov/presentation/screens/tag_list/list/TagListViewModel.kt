package ru.gozerov.presentation.screens.tag_list.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.usecases.tags.GetTags
import ru.gozerov.domain.usecases.tags.GetTagsByUser
import ru.gozerov.presentation.screens.tag_list.list.models.TagListIntent
import ru.gozerov.presentation.screens.tag_list.list.models.TagListViewState
import javax.inject.Inject

@HiltViewModel
class TagListViewModel @Inject constructor(
    private val getTags: GetTags,
    private val getTagsByUser: GetTagsByUser
) : ViewModel() {

    private val _viewState = MutableStateFlow<TagListViewState>(TagListViewState.None)
    val viewState: StateFlow<TagListViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeGetTagsResult()
            observeGetTagsByUserResult()
        }
    }

    fun handleIntent(intent: TagListIntent) {
        viewModelScope.launch {
            when (intent) {
                is TagListIntent.LoadTags -> getTags.execute(Unit)
                is TagListIntent.GetTagsByUser -> getTagsByUser.execute(intent.username)
            }
        }
    }

    private fun CoroutineScope.observeGetTagsResult() {
        launch {
            getTags.result.collect { result ->
                when (result) {
                    is GetTagsResult.Success -> _viewState.emit(TagListViewState.TagList(result.tags))
                    is GetTagsResult.Error -> _viewState.emit(TagListViewState.Error())
                }
            }
        }
    }

    private fun CoroutineScope.observeGetTagsByUserResult() {
        launch {
            getTagsByUser.result.collect { result ->
                when (result) {
                    is GetTagsResult.Success -> _viewState.emit(TagListViewState.TagList(result.tags))
                    is GetTagsResult.Error -> _viewState.emit(TagListViewState.Error())
                }
            }
        }
    }

}