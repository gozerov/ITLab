package ru.gozerov.presentation.screens.tag_map.views

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.screens.shared.SetupSystemBars
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagDetailsDialog(
    tagState: MutableState<Tag?>,
    tagBottomSheetState: SheetState,
    coroutineScope: CoroutineScope,
    onDismiss: () -> Unit
) {
    SetupSystemBars(statusBarColor = Color(0x52000000))

    ModalBottomSheet(
        containerColor = ITLabTheme.colors.primaryBackground,
        onDismissRequest = onDismiss,
        sheetState = tagBottomSheetState
    ) {
        Button(onClick = {
            coroutineScope.launch { tagBottomSheetState.hide() }.invokeOnCompletion {
                onDismiss()
            }
        }) {
            Text("Hide bottom sheet")
        }
    }
}