package com.mateusz.jasiak.activetimespendingsystem

import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.MapUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.RankingUseCase
import com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map.MapViewModel
import com.mateusz.jasiak.activetimespendingsystem.utils.SCORE_ZERO
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class CalculateTimeTest {
    private lateinit var mapViewModel: MapViewModel

    private fun calculateTime(): Long {
        mapViewModel.timestampEnd?.let { timestampEnd ->
            mapViewModel.timestampStart?.let { timestampStart ->
                return timestampEnd.minus(timestampStart)
            }
        }
        return SCORE_ZERO
    }

    @Before
    fun setup() {
        mapViewModel = MapViewModel(
            mock(MapUseCase::class.java),
            mock(LoginUseCase::class.java),
            mock(RankingUseCase::class.java)
        )
    }

    @Test
    fun `calculateTime should return the correct duration when both timestampEnd and timestampStart are not null`() {
        // Given
        mapViewModel.timestampStart = 1000L
        mapViewModel.timestampEnd = 5000L

        // When
        val result = calculateTime()

        // Then
        assertEquals(4000L, result)
    }

    @Test
    fun `calculateTime should return SCORE_ZERO when timestampStart or timestampEnd is null`() {
        // When
        val result = calculateTime()

        // Then
        assertEquals(SCORE_ZERO, result)
    }

    @Test
    fun `calculateTime should return zero when timestampEnd is null`() {
        // Given
        mapViewModel.timestampStart = 1000L
        mapViewModel.timestampEnd = null

        // When
        val result = calculateTime()

        // Then
        assertEquals(SCORE_ZERO, result)
    }
}