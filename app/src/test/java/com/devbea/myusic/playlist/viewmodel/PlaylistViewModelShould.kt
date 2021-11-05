package com.devbea.myusic.playlist.viewmodel

import com.devbea.myusic.playlist.model.Playlist
import com.devbea.myusic.playlist.repository.PlaylistRepository
import com.devbea.myusic.utils.BaseUnitTest
import com.devbea.myusic.utils.captureValues
import com.devbea.myusic.utils.getValueForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlaylistViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()

    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something when south")

    @Test
    fun `get Playlists From Repository`() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.playlists.getValueForTest()
        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun `emits Playlists From Repository`() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

    @Test
    fun `emits Error When Receive Error`() = runBlockingTest {
        val viewModel = mockFailingCase()
        assertEquals(exception, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }



    @Test
    fun `hides Loader After Fetching Is Completed`() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun `hides Loader When Fetching Is Failed`() = runBlockingTest {
        val viewModel = mockFailingCase()
        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun `show Loader While Fetching Playlists`() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(true, values[0])
        }
    }


    private suspend fun mockSuccessfulCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(flow { emit(expected) })
        return PlaylistViewModel(repository)
    }

    private suspend fun mockFailingCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(flow {
            emit(
                Result.failure<List<Playlist>>(
                    exception
                )
            )
        })
        return PlaylistViewModel(repository)
    }
}