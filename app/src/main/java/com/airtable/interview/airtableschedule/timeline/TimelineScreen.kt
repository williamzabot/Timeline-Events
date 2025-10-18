package com.airtable.interview.airtableschedule.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airtable.interview.airtableschedule.R
import com.airtable.interview.airtableschedule.models.Event
import com.airtable.interview.airtableschedule.models.SampleTimelineItems.timelineItems
import com.airtable.interview.airtableschedule.theme.gray
import com.airtable.interview.airtableschedule.theme.blueLight
import com.airtable.interview.airtableschedule.theme.green
import com.airtable.interview.airtableschedule.utils.absoluteDay
import com.airtable.interview.airtableschedule.utils.daysBetweenAbsolute
import com.airtable.interview.airtableschedule.utils.groupByWeek

@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val showTour = remember { mutableStateOf(true) }
    if (showTour.value) {
        AppTourAlert(
            title = context.getString(R.string.welcome),
            message = context.getString(R.string.instructions),
            onClose = { showTour.value = false }
        )
    } else {
        TimelineContent(uiState.events)
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    TimelineContent(events = timelineItems)
}


@Composable
private fun TimelineContent(
    events: List<Event>
) {
    val eventClicked = remember { mutableStateOf<Event?>(null) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Row {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        val eventsGroupedByWeek = groupByWeek(events)
                        eventsGroupedByWeek.forEach { (_, eventsInWeek) ->
                            EventListItem(
                                threeFirstEvents = eventsInWeek.take(2),
                                eventClicked = eventClicked
                            )
                        }
                    }
                    TimelineComponent(eventClicked)
                }
            }
        }

    }
}

@Composable
private fun EventListItem(
    threeFirstEvents: List<Event>,
    eventClicked: MutableState<Event?>
) {
    Column {
        threeFirstEvents.forEach { event ->
            val isSameEvent = event == eventClicked.value
            val boxColor = getBoxColor(isSameEvent)
            EventItem(
                event = event,
                boxColor = boxColor,
                onClick = {
                    eventClicked.value = event
                }
            )
        }
        val changeDividerColor = threeFirstEvents.any { it == eventClicked.value }
        val dividerColor = getDividerColor(changeDividerColor)
        VerticalDivider(
            modifier = Modifier
                .padding(start = 20.dp)
                .width(4.dp)
                .height(70.dp)
                .background(dividerColor)
        )
    }
}

@Composable
private fun getDividerColor(isSameEvent: Boolean): Color = if (isSameEvent) {
    green
} else {
    gray
}

@Composable
private fun getBoxColor(
    isSameEvent: Boolean,
): Color = if (isSameEvent) {
    green
} else {
    blueLight
}

@Composable
private fun TimelineComponent(
    eventClicked: State<Event?>
) {
    val clicked = eventClicked.value
    val absoluteDay = absoluteDay(eventClicked.value?.startDate)
    val differenceDays = daysBetweenAbsolute(
        start = clicked?.startDate,
        end = clicked?.endDate
    )
    Row {
        for (i in 0..90) {
            val colorSquare = getColorSquare(
                index = i,
                absoluteDay = absoluteDay,
                differenceDays = differenceDays
            )
            SmallSquare(colorSquare)
        }
    }
}

@Composable
private fun getColorSquare(
    index: Int,
    absoluteDay: Int?,
    differenceDays: Long?
): Color = if (absoluteDay != null && differenceDays != null) {
    if (index in absoluteDay..absoluteDay + differenceDays) {
        green
    } else {
        blueLight
    }
} else {
    blueLight
}


@Composable
fun SmallSquare(
    colorSquare: Color = blueLight
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(colorSquare)
    )
}

@Composable
fun EventItem(
    event: Event,
    boxColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = 6.dp,
                end = 10.dp
            )
    ) {
        CardEventItem(
            event = event,
            boxColor = boxColor,
            onClick = onClick
        )
    }
}

@Composable
private fun CardEventItem(
    onClick: () -> Unit,
    event: Event,
    boxColor: Color
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .heightIn(max = 85.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(boxColor)
            )

            Spacer(modifier = Modifier.width(10.dp))
            EventInfo(
                name = event.name,
                startDate = event.startDate.toString(),
                endDate = event.endDate.toString()
            )
        }
    }
}

@Composable
private fun EventInfo(
    name: String,
    startDate: String,
    endDate: String
) {
    val context = LocalContext.current
    Column {
        Text(
            text = name,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        DateEvent(
            titleText = context.getString(R.string.start_date),
            date = startDate
        )
        Spacer(modifier = Modifier.height(6.dp))
        DateEvent(
            titleText = context.getString(R.string.end_date),
            date = endDate
        )
    }
}

@Composable
private fun DateEvent(titleText: String, date: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = titleText, maxLines = 1)
        Spacer(Modifier.width(6.dp))
        Text(text = date, maxLines = 1)
    }
}
