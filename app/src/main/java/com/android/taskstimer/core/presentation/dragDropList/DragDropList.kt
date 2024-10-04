package com.android.taskstimer.core.presentation.dragDropList

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun DragDropList(
    modifier: Modifier = Modifier,
    dragDropListState: DragDropListState,
    scope: CoroutineScope = rememberCoroutineScope(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    content: LazyListScope.() -> Unit
) {

    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp, start = 10.dp, end = 10.dp)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(onDrag = { change, offset ->
                    change.consume()
                    dragDropListState.onDrag(offset = offset)
                    if (overScrollJob?.isActive == true)
                        return@detectDragGesturesAfterLongPress

                    dragDropListState
                        .checkForOverScroll()
                        .takeIf { it != 0f }
                        ?.let {
                            overScrollJob = scope.launch {
                                dragDropListState.lazyListState.scrollBy(it)
                            }
                        } ?: kotlin.run { overScrollJob?.cancel() }

                },
                    onDragStart = { offset ->
                        dragDropListState.onDragStart(
                            offset
                        )
                    },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() }

                )
            },
        state = dragDropListState.lazyListState,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }

}