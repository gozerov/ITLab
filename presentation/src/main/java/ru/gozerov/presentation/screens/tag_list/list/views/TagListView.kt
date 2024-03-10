package ru.gozerov.presentation.screens.tag_list.list.views

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TagListView(
    tagList: List<Tag>,
    onTagClick: (tag: Tag) -> Unit,
    onSearchTextChanged: (text: String) -> Unit,
    defaultOptions: List<String>,
    imageOptions: List<String>,
    selectedDefaultOption: MutableState<String>,
    selectedImageOption: MutableState<String>,
    onResetFilters: () -> Unit,
    onConfirmFilters: (String, String) -> Unit
) {
    val searchState = remember { mutableStateOf("") }
    val isFilterDialogVisible = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ITLabTheme.colors.primaryBackground
    ) { paddingValues ->
        Column {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchField(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(1f),
                    textState = searchState,
                    hintStringRes = R.string.search,
                    onSearchTextChanged = onSearchTextChanged
                )
                IconButton(
                    onClick = {
                        isFilterDialogVisible.value = true
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter_24),
                        contentDescription = null,
                        tint = ITLabTheme.colors.primaryText
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(tagList.size) { index ->
                    TagCard(tag = tagList[index], onTagClick = onTagClick)
                }
            }
        }
        if (isFilterDialogVisible.value) {
            FilterDialog(
                defaultOptions = defaultOptions,
                imageOptions = imageOptions,
                selectedDefaultOption = selectedDefaultOption,
                selectedImageOption = selectedImageOption,
                onConfirm = { defaultOption, imageOption ->
                    isFilterDialogVisible.value = false
                    onConfirmFilters(defaultOption, imageOption)
                },
                onDismiss = {
                    isFilterDialogVisible.value = false
                },
                onReset = {
                    isFilterDialogVisible.value = false
                    onResetFilters()
                }
            )
        }
    }
}

@Composable
fun SearchField(
    modifier: Modifier,
    textState: MutableState<String>,
    onSearchTextChanged: (text: String) -> Unit,
    @StringRes hintStringRes: Int
) {
    OutlinedTextField(
        modifier = modifier,
        value = textState.value,
        onValueChange = {
            textState.value = it
            onSearchTextChanged(it)
        },
        singleLine = true,
        textStyle = ITLabTheme.typography.body,
        label = {
            Text(text = stringResource(hintStringRes))
        },
        colors = TextFieldDefaults.colors(
            focusedLabelColor = ITLabTheme.colors.tintColor,
            focusedContainerColor = ITLabTheme.colors.primaryBackground,
            unfocusedContainerColor = ITLabTheme.colors.primaryBackground,
            focusedIndicatorColor = ITLabTheme.colors.tintColor,
            unfocusedIndicatorColor = ITLabTheme.colors.controlColor,
            cursorColor = ITLabTheme.colors.tintColor,
            focusedTextColor = ITLabTheme.colors.primaryText,
            unfocusedLabelColor = ITLabTheme.colors.secondaryText
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}