package ru.gozerov.presentation.screens.tag_list.list.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.gozerov.presentation.R
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun FilterDialog(
    defaultOptions: List<String>,
    imageOptions: List<String>,
    selectedDefaultOption: MutableState<String>,
    selectedImageOption: MutableState<String>,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {
    LaunchedEffect(key1 = null) {
        if (selectedDefaultOption.value.isEmpty() && defaultOptions.isNotEmpty())
            selectedDefaultOption.value = defaultOptions[0]
        if (selectedImageOption.value.isEmpty() && imageOptions.isNotEmpty())
            selectedImageOption.value = imageOptions[0]
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = ITLabTheme.colors.primaryBackground)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                text = stringResource(id = R.string.filter),
                color = ITLabTheme.colors.primaryText,
                style = ITLabTheme.typography.heading,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column {
                defaultOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                selectedDefaultOption.value = option
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedDefaultOption.value),
                            onClick = {
                                selectedDefaultOption.value = option
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ITLabTheme.colors.tintColor,
                                unselectedColor = ITLabTheme.colors.secondaryBackground
                            )
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 8.dp),
                            color = ITLabTheme.colors.primaryText
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = ITLabTheme.colors.secondaryBackground
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    text = stringResource(id = R.string.by_image),
                    color = ITLabTheme.colors.primaryText,
                    style = ITLabTheme.typography.heading,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                imageOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                selectedImageOption.value = option
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedImageOption.value),
                            onClick = {
                                selectedImageOption.value = option
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ITLabTheme.colors.tintColor,
                                unselectedColor = ITLabTheme.colors.secondaryBackground
                            )
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 8.dp),
                            color = ITLabTheme.colors.primaryText
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ITLabTheme.colors.tintColor),
                    onClick = {
                        onConfirm(selectedDefaultOption.value, selectedImageOption.value)
                    }) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        color = ITLabTheme.colors.primaryText
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ITLabTheme.colors.primaryBackground),
                    border = BorderStroke(2.dp, ITLabTheme.colors.secondaryBackground),
                    onClick = {
                        onReset()
                    }) {
                    Text(
                        text = stringResource(id = R.string.reset_filters),
                        color = ITLabTheme.colors.primaryText
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}