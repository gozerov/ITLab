package ru.gozerov.presentation.screens.tag_list.list.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onDismiss: () -> Unit
) {
    val options = listOf(
        "Алфавит авторов (от меньшего к большему)",
        "Алфавит авторов (от большего к меньшему)",
        "Количество лайков (от меньшего к большему)",
        "Количество лайков (от большего к меньшему)"
    )
    var selectedOption by remember { mutableStateOf(options[0]) }

    val imageOptions = listOf("Все", "Только с картинкой")
    var selectedImageOption by remember { mutableStateOf(imageOptions[0]) }
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
                options.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                selectedOption = option
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = {
                                selectedOption = option
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
                Divider(
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
                                selectedImageOption = option
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedImageOption),
                            onClick = {
                                selectedImageOption = option
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
            }

        }
    }
}