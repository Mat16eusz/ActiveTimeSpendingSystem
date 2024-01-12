package com.mateusz.jasiak.activetimespendingsystem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mateusz.jasiak.activetimespendingsystem.common.BaseViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ErrorResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCodeEnum
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.MapUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.RankingUseCase
import com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map.MapViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ResponseTest {
    private val mapUseCase = mockk<MapUseCase>()
    private val loginUseCase = mockk<LoginUseCase>()
    private val rankingUseCase = mockk<RankingUseCase>()
    private lateinit var viewModel: MapViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = MapViewModel(mapUseCase, loginUseCase, rankingUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `mocked Success ApiResponse should return expected values`() {
        // Given
        val responseData = "Mocked data"
        val successApiResponse: ApiResponse<String> = mockk {
            every { isSuccessful } returns true
            every { data } returns responseData
            every { errorResponse } returns null
        }

        // When
        val isSuccess = successApiResponse.isSuccessful
        val data = successApiResponse.data
        val errorResponse = successApiResponse.errorResponse

        // Then
        assertEquals(true, isSuccess)
        assertEquals(responseData, data)
        assertEquals(null, errorResponse)
    }

    @Test
    fun `mocked Failure ApiResponse should return expected values`() {
        // Given
        val errorData = ErrorResponse(code = ErrorCodeEnum.NO_NETWORK)
        val failureApiResponse: ApiResponse<String> = mockk {
            every { isSuccessful } returns false
            every { data } returns null
            every { errorResponse } returns errorData
        }

        // When
        val isSuccess = failureApiResponse.isSuccessful
        val data = failureApiResponse.data
        val errorResponse = failureApiResponse.errorResponse

        // Then
        assertEquals(false, isSuccess)
        assertEquals(null, data)
        assertEquals(errorData, errorResponse)
    }

    @Test
    fun `getUserByIdFromApi success should update loggedUserData`() = runTest {
        // Given
        val idSocialMedia = "someId"
        val userData = UserDomain()
        val successResponse = ApiResponse.Success(userData)
        coEvery { loginUseCase.getUserByIdFromApi(idSocialMedia) } returns successResponse

        // When
        viewModel.getUserByIdFromApi(idSocialMedia)

        // Then
        launch {
            loginUseCase.getUserByIdFromApi(idSocialMedia)
        }
        assertEquals(userData, viewModel.loggedUserData)
    }

    @Test
    fun `getUserByIdFromApi failure with NO_NETWORK should post NoNetwork action`() = runTest {
        // Given
        val idSocialMedia = "someId"
        val errorData = ErrorResponse(code = ErrorCodeEnum.NO_NETWORK)
        val failureApiResponse: ApiResponse<UserDomain> = ApiResponse.Failure(errorData)

        coEvery { loginUseCase.getUserByIdFromApi(idSocialMedia) } returns failureApiResponse

        // When
        viewModel.getUserByIdFromApi(idSocialMedia)

        // Then
        launch { loginUseCase.getUserByIdFromApi(idSocialMedia) }
        assertEquals(BaseViewModel.BaseAction.NoNetwork, viewModel.baseAction.value)
    }

    @Test
    fun `getUserByIdFromApi failure with unknown error should post UnknownError action`() =
        runTest {
            // Given
            val idSocialMedia = "someId"
            val errorData = ErrorResponse(code = ErrorCodeEnum.UNKNOWN)
            val failureApiResponse: ApiResponse<UserDomain> = ApiResponse.Failure(errorData)
            coEvery { loginUseCase.getUserByIdFromApi(idSocialMedia) } returns failureApiResponse

            // When
            viewModel.getUserByIdFromApi(idSocialMedia)

            // Then
            launch { loginUseCase.getUserByIdFromApi(idSocialMedia) }
            assertEquals(BaseViewModel.BaseAction.UnknownError, viewModel.baseAction.value)
        }

    @Test
    fun `getCoordinatesFromApi success should update allUserCoordinates`() = runTest {
        // Given
        val coordinateDomain = listOf<CoordinateDomain>()
        val successResponse = ApiResponse.Success(coordinateDomain)
        coEvery { mapUseCase.getCoordinatesFromApi() } returns successResponse

        // When
        viewModel.getCoordinatesFromApi()

        // Then
        launch {
            mapUseCase.getCoordinatesFromApi()
        }
        assertEquals(coordinateDomain, viewModel.allUserCoordinates)
    }

    @Test
    fun `getCoordinatesFromApi failure with NO_NETWORK should post NoNetwork action`() = runTest {
        // Given
        val errorData = ErrorResponse(code = ErrorCodeEnum.NO_NETWORK)
        val failureApiResponse: ApiResponse<List<CoordinateDomain>> =
            ApiResponse.Failure(errorData)

        coEvery { mapUseCase.getCoordinatesFromApi() } returns failureApiResponse

        // When
        viewModel.getCoordinatesFromApi()

        // Then
        launch { mapUseCase.getCoordinatesFromApi() }
        assertEquals(BaseViewModel.BaseAction.NoNetwork, viewModel.baseAction.value)
    }

    @Test
    fun `getCoordinatesFromApi failure with unknown error should post UnknownError action`() =
        runTest {
            // Given
            val errorData = ErrorResponse(code = ErrorCodeEnum.UNKNOWN)
            val failureApiResponse: ApiResponse<List<CoordinateDomain>> =
                ApiResponse.Failure(errorData)
            coEvery { mapUseCase.getCoordinatesFromApi() } returns failureApiResponse

            // When
            viewModel.getCoordinatesFromApi()

            // Then
            launch { mapUseCase.getCoordinatesFromApi() }
            assertEquals(BaseViewModel.BaseAction.UnknownError, viewModel.baseAction.value)
        }

    @Test
    fun `updateCoordinateByIdOnApi success should update coordinateDomain`() = runTest {
        // Given
        val idSocialMedia = "someId"
        val coordinateDomain = CoordinateDomain()
        val successResponse = ApiResponse.Success(coordinateDomain)
        coEvery {
            mapUseCase.updateCoordinateByIdOnApi(
                idSocialMedia,
                coordinateDomain
            )
        } returns successResponse

        // When
        viewModel.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain)

        // Then
        launch {
            mapUseCase.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain)
        }
        assertEquals(
            successResponse.isSuccessful,
            ApiResponse.Success(coordinateDomain).isSuccessful
        )
    }

    @Test
    fun `updateCoordinateByIdOnApi failure with NO_NETWORK should post NoNetwork action`() =
        runTest {
            // Given
            val idSocialMedia = "someId"
            val coordinateDomain = CoordinateDomain()
            val errorData = ErrorResponse(code = ErrorCodeEnum.NO_NETWORK)
            val failureApiResponse: ApiResponse<CoordinateDomain> = ApiResponse.Failure(errorData)

            coEvery {
                mapUseCase.updateCoordinateByIdOnApi(
                    idSocialMedia,
                    coordinateDomain
                )
            } returns failureApiResponse

            // When
            viewModel.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain)

            // Then
            launch { mapUseCase.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain) }
            assertEquals(BaseViewModel.BaseAction.NoNetwork, viewModel.baseAction.value)
        }

    @Test
    fun `updateCoordinateByIdOnApi failure with unknown error should post UnknownError action`() =
        runTest {
            // Given
            val idSocialMedia = "someId"
            val coordinateDomain = CoordinateDomain()
            val errorData = ErrorResponse(code = ErrorCodeEnum.UNKNOWN)
            val failureApiResponse: ApiResponse<CoordinateDomain> = ApiResponse.Failure(errorData)

            coEvery {
                mapUseCase.updateCoordinateByIdOnApi(
                    idSocialMedia,
                    coordinateDomain
                )
            } returns failureApiResponse

            // When
            viewModel.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain)

            // Then
            launch { mapUseCase.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain) }
            assertEquals(BaseViewModel.BaseAction.UnknownError, viewModel.baseAction.value)
        }

    @Test
    fun `deleteCoordinateByIdOnApi success should update coordinateDomain`() = runTest {
        // Given
        val idSocialMedia = "someId"
        val coordinateDomain = CoordinateDomain()
        val successResponse = ApiResponse.Success(coordinateDomain)
        coEvery {
            mapUseCase.deleteCoordinateByIdOnApi(idSocialMedia)
        } returns successResponse

        // When
        viewModel.deleteCoordinateByIdOnApi(idSocialMedia)

        // Then
        launch {
            mapUseCase.deleteCoordinateByIdOnApi(idSocialMedia)
        }
        assertEquals(
            successResponse.isSuccessful,
            ApiResponse.Success(coordinateDomain).isSuccessful
        )
    }

    @Test
    fun `deleteCoordinateByIdOnApi failure with NO_NETWORK should post NoNetwork action`() =
        runTest {
            // Given
            val idSocialMedia = "someId"
            val errorData = ErrorResponse(code = ErrorCodeEnum.NO_NETWORK)
            val failureApiResponse: ApiResponse<CoordinateDomain> = ApiResponse.Failure(errorData)

            coEvery {
                mapUseCase.deleteCoordinateByIdOnApi(idSocialMedia)
            } returns failureApiResponse

            // When
            viewModel.deleteCoordinateByIdOnApi(idSocialMedia)

            // Then
            launch { mapUseCase.deleteCoordinateByIdOnApi(idSocialMedia) }
            assertEquals(BaseViewModel.BaseAction.NoNetwork, viewModel.baseAction.value)
        }

    @Test
    fun `deleteCoordinateByIdOnApi failure with unknown error should post UnknownError action`() =
        runTest {
            // Given
            val idSocialMedia = "someId"
            val errorData = ErrorResponse(code = ErrorCodeEnum.UNKNOWN)
            val failureApiResponse: ApiResponse<CoordinateDomain> = ApiResponse.Failure(errorData)

            coEvery {
                mapUseCase.deleteCoordinateByIdOnApi(idSocialMedia)
            } returns failureApiResponse

            // When
            viewModel.deleteCoordinateByIdOnApi(idSocialMedia)

            // Then
            launch { mapUseCase.deleteCoordinateByIdOnApi(idSocialMedia) }
            assertEquals(BaseViewModel.BaseAction.UnknownError, viewModel.baseAction.value)
        }

    @Test
    fun `updateRankingByIdOnApi success should update rankingDomain`() = runTest {
        // Given
        val idSocialMedia = "someId"
        val rankingDomain = RankingDomain()
        val successResponse = ApiResponse.Success(rankingDomain)
        coEvery {
            rankingUseCase.updateRankingByIdOnApi(idSocialMedia, rankingDomain)
        } returns successResponse

        // When
        viewModel.updateRankingByIdOnApi(idSocialMedia)

        // Then
        launch {
            rankingUseCase.updateRankingByIdOnApi(idSocialMedia, rankingDomain)
        }
        assertEquals(
            successResponse.isSuccessful,
            ApiResponse.Success(rankingDomain).isSuccessful
        )
    }

    @Test
    fun `updateRankingByIdOnApi failure with NO_NETWORK should post NoNetwork action`() = runTest {
        // Given
        val idSocialMedia = "someId"
        val rankingDomain = RankingDomain(
            "someId",
            null,
            0
        )
        val errorData = ErrorResponse(code = ErrorCodeEnum.NO_NETWORK)
        val failureApiResponse: ApiResponse<RankingDomain> = ApiResponse.Failure(errorData)

        coEvery {
            rankingUseCase.updateRankingByIdOnApi(idSocialMedia, rankingDomain)
        } returns failureApiResponse

        // When
        viewModel.updateRankingByIdOnApi(idSocialMedia)

        // Then
        launch { rankingUseCase.updateRankingByIdOnApi(idSocialMedia, rankingDomain) }
        assertEquals(BaseViewModel.BaseAction.NoNetwork, viewModel.baseAction.value)
    }

    @Test
    fun `updateRankingByIdOnApi failure with unknown error should post UnknownError action`() =
        runTest {
            // Given
            val idSocialMedia = "someId"
            val rankingDomain = RankingDomain(
                "someId",
                null,
                0
            )
            val errorData = ErrorResponse(code = ErrorCodeEnum.UNKNOWN)
            val failureApiResponse: ApiResponse<RankingDomain> = ApiResponse.Failure(errorData)

            coEvery {
                rankingUseCase.updateRankingByIdOnApi(idSocialMedia, rankingDomain)
            } returns failureApiResponse

            // When
            viewModel.updateRankingByIdOnApi(idSocialMedia)

            // Then
            launch { rankingUseCase.updateRankingByIdOnApi(idSocialMedia, rankingDomain) }
            assertEquals(BaseViewModel.BaseAction.UnknownError, viewModel.baseAction.value)
        }
}