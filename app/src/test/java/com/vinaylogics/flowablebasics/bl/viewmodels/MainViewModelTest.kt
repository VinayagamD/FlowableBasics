package com.vinaylogics.flowablebasics.bl.viewmodels

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()



    private lateinit var viewModel: MainViewModel

    private lateinit var testDispatchers: TestDispatchers

    @Before
    fun setUp() {
        testDispatchers = TestDispatchers()
        viewModel = MainViewModel(testDispatchers)
    }

   @Test
   fun `countDownFlow, properly counts from 5 to 0`() = runBlocking{
       viewModel.countDownFlow.test {
           for (i in 5 downTo 0) {
               testDispatchers.testDispatcher.scheduler.apply { advanceTimeBy(1000L); runCurrent() }
                val emission = awaitItem()
               assertThat(emission).isEqualTo(i)
           }
           cancelAndConsumeRemainingEvents()

       }
   }

    @Test
    fun `squareNumber, number properly squared`() = runBlocking {
        val job = launch {
            viewModel.sharedFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(9)
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.squareNumber(3)
        job.join()
        job.cancel()
    }
}