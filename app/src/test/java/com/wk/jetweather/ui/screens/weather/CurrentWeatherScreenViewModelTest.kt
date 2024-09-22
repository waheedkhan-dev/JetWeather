import app.cash.turbine.test
import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.jetweather.data.repositories.WeatherRepositoryImpl
import com.wk.jetweather.ui.screens.weather.CurrentWeatherScreenViewModel
import com.wk.jetweather.ui.screens.weather.uistate.HomeScreenUiState
import com.wk.jetweather.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherScreenViewModelTest {

    private lateinit var viewModel: CurrentWeatherScreenViewModel
    private val weatherRepositoryImpl = mockk<WeatherRepositoryImpl>()
    private val defaultCity = "Attock"

    // Set the main dispatcher to TestDispatcher for testing coroutine behavior.
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Inject mock WeatherRepositoryImpl and defaultCity
        viewModel = CurrentWeatherScreenViewModel(defaultCity, weatherRepositoryImpl)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTodayWeather emits loading and success states when API call is successful`() = runTest {
        // Arrange
        val mockWeather = mockk<CurrentWeatherEntity>()
        coEvery { weatherRepositoryImpl.fetchTodayWeather(any()) } returns flow {
            emit(Resource.Loading) // Use Resource.Loading() if it's a function
            emit(Resource.Success(mockWeather))
        }

        // Act
        viewModel.homeScreenUiState.test {
            // Assert: Initial state is InitialState
            assertEquals(HomeScreenUiState.InitialState, awaitItem())

            // Trigger the fetchTodayWeather function
            viewModel.fetchTodayWeather()

            // Assert: The first emitted state should be Loading
            assertEquals(HomeScreenUiState.Loading, awaitItem())

            // Assert: The second emitted state should be Success with mockWeather
            assertEquals(HomeScreenUiState.Success(mockWeather), awaitItem())

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `fetchTodayWeather emits loading and error states when API call fails`() = runTest {
        // Arrange: Prepare the mock error response
        val errorMessage = "Unable to fetch weather"
        coEvery { weatherRepositoryImpl.fetchTodayWeather(any()) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(errorMessage))
        }

        // Act: Collect the state from the StateFlow
        viewModel.homeScreenUiState.test {
            // Assert: Initial state is InitialState
            assertEquals(HomeScreenUiState.InitialState, awaitItem())
            // Trigger the fetchTodayWeather function
            viewModel.fetchTodayWeather()

            // Assert: The first emitted state should be Loading
            assertEquals(HomeScreenUiState.Loading, awaitItem())

            // Assert: The second emitted state should be Error with errorMessage
            assertEquals(HomeScreenUiState.Error(errorMessage), awaitItem())

            cancelAndConsumeRemainingEvents()
        }
    }
}