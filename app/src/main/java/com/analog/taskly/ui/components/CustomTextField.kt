package com.analog.taskly.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextFieldLibrary(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    width: Dp? = null,
    height: Dp = 40.dp,
    padding: Dp = 8.dp,
    textColor: Color = Color.Black,
    placeholderColor: Color = Color.Gray,
    backgroundColor: Color = Color.Transparent,
    border: BorderStroke? = null,
    shape: Shape = RectangleShape,
    cursorColor: Color = textColor,
    textStyle: TextStyle = TextStyle(color = textColor),
    singleLine: Boolean = true
) {
    val boxModifier = modifier
        .then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
        .height(height)
        .background(backgroundColor, shape)
        .then(if (border != null) Modifier.border(border, shape) else Modifier)
        .padding(padding)

    // Calculate font size based on height
    val calculatedFontSize = (height.value * 0.4).sp // 40% of the height

    Box(modifier = boxModifier, contentAlignment = Alignment.CenterStart) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = placeholderColor,
                style = textStyle.copy(fontSize = calculatedFontSize)
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle.copy(
                color = textColor,
                fontSize = calculatedFontSize
            ),
            singleLine = singleLine,
            cursorBrush = SolidColor(cursorColor),
            modifier = Modifier.fillMaxSize()
        )
    }
}
