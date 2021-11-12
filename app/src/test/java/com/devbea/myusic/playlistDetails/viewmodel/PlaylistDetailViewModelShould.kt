package com.devbea.myusic.playlistDetails.viewmodel

import com.devbea.myusic.playlistDetails.model.PlaylistDetails
import com.devbea.myusic.playlistDetails.repository.PlaylistDetailsRepository
import com.devbea.myusic.playlistDetails.viewmodel.PlaylistDetailViewModel.ScreenState.FailureState
import com.devbea.myusic.playlistDetails.viewmodel.PlaylistDetailViewModel.ScreenState.SuccessState
import com.devbea.myusic.utils.BaseUnitTest
import com.devbea.myusic.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistDetailViewModelShould : BaseUnitTest() {

    private val repository: PlaylistDetailsRepository = mock()

    private lateinit var viewModel: PlaylistDetailViewModel

    private val playlist = mock<PlaylistDetails>()

    private val expected = Result.success(playlist)
    private val exception = RuntimeException("Something when south")

    @Test
    fun `get playlist from repository`() = runBlockingTest {
        viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetailsWithId("1")
        verify(repository, times(1)).getPlaylistWithId("1")


    }

    @Test
    fun `emits playlist from repository`() = runBlockingTest {
        viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetailsWithId("1")
        val result = viewModel.screenState.getValueForTest()
        assertTrue(result is SuccessState)
        assertEquals(playlist, (result as SuccessState).playlist)

    }

    @Test
    fun `emits exception from repository`() = runBlockingTest {
        viewModel = mockFailureCase()
        viewModel.getPlaylistDetailsWithId("1")
        val result = viewModel.screenState.getValueForTest()
        assertTrue(result is FailureState)
        assertEquals(exception.message, (result as FailureState).errorMessage)

    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailViewModel {
        whenever(repository.getPlaylistWithId("1")).thenReturn(flow { emit(expected) })
        return PlaylistDetailViewModel(repository)
    }

    private suspend fun mockFailureCase(): PlaylistDetailViewModel {
        whenever(repository.getPlaylistWithId("1")).thenReturn(flow {
            emit(
                Result.failure<PlaylistDetails>(
                    exception
                )
            )
        })
        return PlaylistDetailViewModel(repository)
    }
}