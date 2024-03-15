package ru.gozerov.presentation.screens.tag_list.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.login.GetLoginModeResult
import ru.gozerov.domain.models.login.SubscribeOnUserData
import ru.gozerov.domain.models.login.SubscribeOnUserResult
import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.models.tags.DeleteTagResult
import ru.gozerov.domain.models.tags.GetTagDetailsResult
import ru.gozerov.domain.models.tags.LikeTagResult
import ru.gozerov.domain.usecases.login.GetLoginMode
import ru.gozerov.domain.usecases.login.SubscribeOnUser
import ru.gozerov.domain.usecases.tags.DeleteLike
import ru.gozerov.domain.usecases.tags.DeleteTag
import ru.gozerov.domain.usecases.tags.GetTagDetails
import ru.gozerov.domain.usecases.tags.LikeTag
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsIntent
import ru.gozerov.presentation.screens.tag_list.details.models.TagDetailsViewState
import javax.inject.Inject

@HiltViewModel
class TagDetailsViewModel @Inject constructor(
    private val likeTag: LikeTag,
    private val deleteLike: DeleteLike,
    private val deleteTag: DeleteTag,
    private val getTagDetails: GetTagDetails,
    private val getLoginMode: GetLoginMode,
    private val subscribeOnUser: SubscribeOnUser
) : ViewModel() {

    private val _viewState = MutableStateFlow<TagDetailsViewState>(TagDetailsViewState.None)
    val viewState: StateFlow<TagDetailsViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeGetLoginModeResult()
            observeGetTagDetailsResult()
            observeLikeTagResult()
            observeDeleteLikeResult()
            observeDeleteTagResult()
            observeSubscribeOnUserResult()
        }
    }

    fun handleIntent(intent: TagDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is TagDetailsIntent.GetLoginMode -> getLoginMode.execute(Unit)
                is TagDetailsIntent.LoadTag -> getTagDetails.execute(intent.id)
                is TagDetailsIntent.LikeTag -> likeTag.execute(intent.id)
                is TagDetailsIntent.UnlikeTag -> deleteLike.execute(intent.tag)
                is TagDetailsIntent.DeleteTag -> deleteTag.execute(intent.id)
                is TagDetailsIntent.SubscribeOnAuthor -> subscribeOnUser.execute(
                    SubscribeOnUserData(
                        intent.username,
                        intent.subscribed
                    )
                )
            }
        }

    }

    private fun CoroutineScope.observeGetTagDetailsResult() {
        launch {
            getTagDetails.result.collect { result ->
                when (result) {
                    is GetTagDetailsResult.Success -> {
                        _viewState.emit(TagDetailsViewState.UpdatedTag(result.tagDetails))
                    }

                    is GetTagDetailsResult.Error -> {
                        _viewState.emit(TagDetailsViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeLikeTagResult() {
        launch {
            likeTag.result.collect { result ->
                when (result) {
                    is LikeTagResult.Success -> _viewState.emit(
                        TagDetailsViewState.UpdatedTag(result.tagDetails)
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
                        TagDetailsViewState.UpdatedTag(result.tagDetails)
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

    private fun CoroutineScope.observeGetLoginModeResult() {
        launch {
            getLoginMode.result.collect { result ->
                when (result) {
                    is GetLoginModeResult.Success -> {
                        _viewState.emit(TagDetailsViewState.UpdateLoginMode(result.mode))
                    }

                    is GetLoginModeResult.Error -> {
                        _viewState.emit(TagDetailsViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeSubscribeOnUserResult() {
        launch {
            subscribeOnUser.result.collect { result ->
                when (result) {
                    is SubscribeOnUserResult.Success -> {
                        _viewState.emit(TagDetailsViewState.UpdateSubscription(result.isSubscribed))
                    }

                    is SubscribeOnUserResult.Error -> {
                        _viewState.emit(TagDetailsViewState.Error())
                    }
                }
            }
        }
    }

}