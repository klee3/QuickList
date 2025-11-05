package com.analog.taskly.ui.screens.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Lucide

//  Code to play Sound
//    val context = LocalContext.current
//    // Remember SoundPool and load sound only once
//    val soundPool = remember {
//        SoundPool.Builder()
//            .setMaxStreams(1)
//            .build()
//    }
//
//    val soundId = remember {
//        // Load your sound resource, e.g. R.raw.click_sound
//        soundPool.load(context, R.raw.completed_2, 1)
//    }
//
//    // Play sound effect when checked changes to true
//    LaunchedEffect(checked) {
//        if (checked) {
//            soundPool.play(soundId, .2f, .2f, 1, 0, 1f)
//        }
//    }

@Composable
fun CircleCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    size: Int = 22,
    checkboxColor: Color = Color.Gray
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (checked) checkboxColor else Color.Transparent // checkboxColor.copy(0.1f)
    )

    val scale by animateFloatAsState(
        targetValue = if (checked) 1.1f else 1f
    )

    Box(
        modifier = modifier
            .size(size.dp)
            .scale(scale)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .border(1.5.dp, checkboxColor, CircleShape),

        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Lucide.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size((size * 0.7f).dp)
            )
        }
    }
}
