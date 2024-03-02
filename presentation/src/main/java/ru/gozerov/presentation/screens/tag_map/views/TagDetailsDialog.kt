package ru.gozerov.presentation.screens.tag_map.views

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagDetailsDialog(
    tagState: MutableState<Tag?>,
    tagBottomSheetState: SheetState,
    coroutineScope: CoroutineScope
) {
    ModalBottomSheet(
        containerColor = ITLabTheme.colors.primaryBackground,
        onDismissRequest = {
            tagState.value = null
        },
        sheetState = tagBottomSheetState
    ) {
        Button(onClick = {
            coroutineScope.launch { tagBottomSheetState.hide() }.invokeOnCompletion {
                tagState.value = null
            }
        }) {
            Text("Hide bottom sheet")
        }
    }
}