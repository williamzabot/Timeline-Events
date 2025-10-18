package com.airtable.interview.airtableschedule.models

import java.util.Date

object SampleTimelineItems {
    private val year = 2020 - 1900
    var timelineItems: List<Event> = listOf(
        Event(
            1,
            Date(year, 0, 1),
            Date(year, 0, 8),
            "First item"
        ),
        Event(
            2,
            Date(year, 0, 2),
            Date(year, 0, 8),
            "Second item"
        ),
        Event(
            3,
            Date(year, 0, 15),
            Date(year, 1, 12),
            "Another item"
        ),
        Event(
            4,
            Date(year, 1, 1),
            Date(year, 1, 7),
            "Another item"
        ),
        Event(
            5,
            Date(year, 1, 2),
            Date(year, 1, 15),
            "Third item"
        ),
        Event(
            6,
            Date(year, 1, 9),
            Date(year, 1, 16),
            "Fourth item with a super long name"
        ),
        Event(
            7,
            Date(year, 1, 11),
            Date(year, 1, 16),
            "Fifth item with a super long name"
        ),
        Event(
            8,
            Date(year, 1, 24),
            Date(year, 1, 27),
            "First item"
        ),
        Event(
            9,
            Date(year, 2, 4),
            Date(year, 2, 12),
            "Second item"
        ),
        Event(
            10,
            Date(year, 2, 16),
            Date(year, 2, 19),
            "Another item"
        ),
        Event(
            11,
            Date(year, 2, 21),
            Date(year, 2, 29),
            "Another item"
        ),
        Event(
            12,
            Date(year, 2, 27),
            Date(year, 2, 30),
            "Third item"
        ),
        Event(
            13,
            Date(year, 1, 12),
            Date(year, 1, 16),
            "Fourth item with a super long name"
        ),
        Event(
            14,
            Date(year, 1, 3),
            Date(year, 2, 6),
            "Fifth item with a super long name"
        )
    )
}