package ru.gozerov.presentation.screens.tag_list.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.models.tags.DeleteTagResult
import ru.gozerov.domain.models.tags.LikeTagResult
import ru.gozerov.domain.usecases.tags.DeleteLike
import ru.gozerov.domain.usecases.tags.DeleteTag
import ru.gozerov.domain.usecases.tags.LikeTag
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsIntent
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsViewState
import javax.inject.Inject

@HiltViewModel
class TagDetailsViewModel @Inject constructor(
    private val likeTag: LikeTag,
    private val deleteLike: DeleteLike,
    private val deleteTag: DeleteTag
) : ViewModel() {

    private val _viewState = MutableStateFlow<TagDetailsViewState>(TagDetailsViewState.None)
    val viewState: StateFlow<TagDetailsViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeLikeTagResult()
            observeDeleteLikeResult()
            observeDeleteTagResult()
        }
    }

    fun handleIntent(intent: TagDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is TagDetailsIntent.LikeTag -> likeTag.execute(intent.id)
                is TagDetailsIntent.UnlikeTag -> deleteLike.execute(intent.tag)
                is TagDetailsIntent.DeleteTag -> deleteTag.execute(intent.id)
            }
        }

    }

    private fun CoroutineScope.observeLikeTagResult() {
        launch {
            likeTag.result.collect { result ->
                when (result) {
                    is LikeTagResult.Success -> _viewState.emit(
                        TagDetailsViewState.UpdatedTag(
                            result.tag
                        )
                    )

                    is LikeTagResult.Error -> _viewState.emit(TagDetailsViewState.Error())
                }
            }
        }
    }

    private fun CoroutineScope.observeDeleteLikeResult() {
        launch {
            deleteLike.result.collect { result ->
                when (result) {
                    is DeleteLikeResult.Success -> _viewState.emit(
                        TagDetailsViewState.UpdatedTag(
                            result.tag
                        )
                    )

                    is DeleteLikeResult.Error -> _viewState.emit(TagDetailsViewState.Error())
                }
            }
        }
    }

    private fun CoroutineScope.observeDeleteTagResult() {
        launch {
            deleteTag.result.collect { result ->
                when (result) {
                    is DeleteTagResult.Success -> _viewState.emit(TagDetailsViewState.TagHasBeenDeleted())
                    is DeleteTagResult.Error -> _viewState.emit(TagDetailsViewState.Error())
                }
            }
        }
    }


}