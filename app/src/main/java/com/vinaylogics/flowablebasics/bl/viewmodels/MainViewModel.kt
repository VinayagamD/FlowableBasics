package com.vinaylogics.flowablebasics.bl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinaylogics.flowablebasics.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainViewModel(
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1.seconds)
            currentValue--
            emit(currentValue)
        }
    }.flowOn(dispatchers.main)

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>(replay = 5)
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        squareNumber(3)
        collectFlow()
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect{
                delay(2.seconds)
                println("FIRST FLOW: The receive number is $it")
            }
        }

        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect{
                delay(3.seconds)
                println("SECOND FLOW: The receive number is $it")
            }
        }

    }

    // Flattening example
    // [[1,2],[1,2,3]]
    // [1,2,1,2,3]

    fun squareNumber(number: Int) {
        viewModelScope.launch(dispatchers.main) {
            _sharedFlow.emit(number * number)
        }
    }

    fun incrementCounter(){
        _stateFlow.value += 1
    }

    private fun collectFlow() {
        val flow = flow {
            delay(0.25.seconds)
            emit("Appetizer")
            delay(1.seconds)
            emit("Main dish")
            delay(0.1.seconds)
            emit("Desserts")
        }

        viewModelScope.launch {
            // buffer will always run in different coroutine
            // conflate alternate to buffer
            // collectLatest alternate to buffer
            flow.onEach {
                println("FLOW: $it is delivered")
            }
                .collectLatest {
                    println("FLOW: Now eating $it")
                    delay(1.5.seconds)
                    println("FLOW: finish eating $it")
                }
        }
    }
}