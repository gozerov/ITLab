package ru.gozerov.presentation.screens.tag_list.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.tags.GetFilterOptionResult
import ru.gozerov.domain.models.tags.GetTagsByOptionAndUserData
import ru.gozerov.domain.models.tags.GetTagsByOptionData
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.usecases.tags.GetFilterOptions
import ru.gozerov.domain.usecases.tags.GetTagsByOption
import ru.gozerov.domain.usecases.tags.GetTagsByOptionAndUser
import ru.gozerov.presentation.screens.tag_list.list.models.TagListIntent
import ru.gozerov.presentation.screens.tag_list.list.models.TagListViewState
import javax.inject.Inject

@HiltViewModel
class TagListViewModel @Inject constructor(
    private val getTagsByOption: GetTagsByOption,
    private val getFilterOptions: GetFilterOptions,
    private val getTagsByOptionAndUser: GetTagsByOptionAndUser
) : ViewModel() {

    private val _viewState = MutableStateFlow<TagListViewState>(TagListViewState.None)
    val viewState: StateFlow<TagListViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeGetFilterOptionsResult()
            observeGetTagsByOptionResult()
            observeGetTagsByOptionAndUserResult()
        }
    }

    fun handleIntent(intent: TagListIntent) {
        viewModelScope.launch {
            when (intent) {
                is TagListIntent.LoadFilters -> getFilterOptions.execute(Unit)
                is TagListIntent.LoadTagsByFilters ->
                    getTagsByOption.execute(
                        GetTagsByOptionData(
                            intent.defaultOption,
                            intent.imageOption
                        )
                    )

                is TagListIntent.LoadTagsByFiltersAndUser ->
                    getTagsByOptionAndUser.execute(
                        GetTagsByOptionAndUserData(
                            intent.username,
                            intent.defaultOption,
                            intent.imageOption
                        )
                    )
            }
        }
    }

    private fun CoroutineScope.observeGetFilterOptionsResult() {
        launch {
            getFilterOptions.result.collect { result ->
                when (result) {
                    is GetFilterOptionResult.Success -> _viewState.emit(
                        TagListViewState.Filters(
                            result.option.defaultOptions,
                            result.option.imageOptions
                        )
                    )

                    is GetFilterOptionResult.Error -> _viewState.emit(TagListViewState.Error())
                }
            }
        }
    }

    private fun CoroutineScope.observeGetTagsByOptionResult() {
        launch {
            getTagsByOption.result.collect { result ->
                when (result) {
                    is GetTagsResult.Success -> _viewState.emit(
                        TagListViewState.TagList(
                            result.tags
                        )
                    )

                    is GetTagsResult.Error -> _viewState.emit(TagListViewState.Error())
                }
            }
        }
    }

    private fun CoroutineScope.observeGetTagsByOptionAndUserResult() {
        launch {
            getTagsByOptionAndUser.result.collect { result ->
                when (result) {
                    is GetTagsResult.Success -> _viewState.emit(
                        TagListViewState.TagList(
                            result.tags
                        )
                    )

                    is GetTagsResult.Error -> _viewState.emit(TagListViewState.Error())
                }
            }
        }
    }

}