package com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.notes_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.plcoding.cleanarchitecturenoteapp.feature_notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_notes.presentation.add_edit_note_screen.AddNoteEvent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom.current


@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeletedClicked: () -> Unit
) {
    val date = Date(note.timestamp)
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    size = size,
                    color = Color(note.color),
                    cornerRadius = CornerRadius(cornerRadius.toPx())

                )
                drawRoundRect(
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    color = Color(ColorUtils.blendARGB(note.color, Color.Black.toArgb(), 0.2f)),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f)
                )

            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 30.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom,

                ) {
                Text(
                    text = dateFormat.format(date).replaceFirstChar {it.uppercase()},
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                    color = Color.Black.copy(alpha = 0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (note.isFavorite){
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "add to favorites",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(20.dp),
                        tint = Color.Black.copy(alpha = 0.5f),
                    )
                }

            }


        }

        
        IconButton(
            onClick = onDeletedClicked,
            modifier = Modifier.align(Alignment.BottomEnd),

        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete a note",
                tint = Color.Black

            )
        }


    }
}











