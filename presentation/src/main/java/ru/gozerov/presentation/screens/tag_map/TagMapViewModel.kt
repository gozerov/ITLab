package ru.gozerov.presentation.screens.tag_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.tags.CreateTagResult
import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.models.tags.LikeTagResult
import ru.gozerov.domain.usecases.tags.CreateTag
import ru.gozerov.domain.usecases.tags.DeleteLike
import ru.gozerov.domain.usecases.tags.GetTags
import ru.gozerov.domain.usecases.tags.LikeTag
import ru.gozerov.presentation.screens.tag_map.models.TagMapIntent
import ru.gozerov.presentation.screens.tag_map.models.TagMapViewState
import javax.inject.Inject

@HiltViewModel
class TagMapViewModel @Inject constructor(
    private val getTags: GetTags,
    private val createTag: CreateTag,
    private val likeTag: LikeTag,
    private val deleteLike: DeleteLike
) : ViewModel() {

    private val _viewState = MutableStateFlow<TagMapViewState>(TagMapViewState.None)
    val viewState: StateFlow<TagMapViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeGetTagsResult()
            observeLikeTagResult()
            observeDeleteLikeTagResult()
            observeCreateTagResult()
        }
    }

    fun handleIntent(intent: TagMapIntent) {
        viewModelScope.launch {
            when (intent) {
                is TagMapIntent.LoadTags -> getTags.execute(Unit)
                is TagMapIntent.LikeTag -> likeTag.execute(intent.tag.id)
                is TagMapIntent.UnlikeTag -> deleteLike.execute(intent.tag)
                is TagMapIntent.CreateTag -> createTag.execute(intent.createTagData)
            }
        }
    }

    private fun CoroutineScope.observeGetTagsResult() {
        launch {
            getTags.result.collect { result ->
                when (result) {
                    is GetTagsResult.Success -> {
                        _viewState.emit(TagMapViewState.TagsOnMap(result.tags))
                    }

                    is GetTagsResult.Error -> {
                        _viewState.emit(TagMapViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeLikeTagResult() {
        launch {
            likeTag.result.collect { result ->
                when (result) {
                    is LikeTagResult.Success -> {
                        _viewState.emit(TagMapViewState.UpdateChosenTag(result.tag))
                    }

                    is LikeTagResult.Error -> {
                        _viewState.emit(TagMapViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeDeleteLikeTagResult() {
        launch {
            deleteLike.result.collect { result ->
                when (result) {
                    is DeleteLikeResult.Success -> {
                        _viewState.emit(TagMapViewState.UpdateChosenTag(result.tag))
                    }

                    is DeleteLikeResult.Error -> {
                        _viewState.emit(TagMapViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeCreateTagResult() {
        launch {
            createTag.result.collect { result ->
                when (result) {
                    is CreateTagResult.Success -> {
                        getTags.execute(Unit)
                    }

                    is CreateTagResult.Error -> {
                        _viewState.emit(TagMapViewState.Error())
                    }
                }
            }
        }
    }

}