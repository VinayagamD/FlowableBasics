package com.vinaylogics.flowablebasics.bl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1.seconds)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    // Flattening example
    // [[1,2],[1,2,3]]
    // [1,2,1,2,3]

    @OptIn(FlowPreview::class)
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
            // conflate alternate buffer
            flow.onEach {
                println("FLOW: $it is delivered")
            }
                .buffer()
                .collect {
                    println("FLOW: Now eating $it")
                    delay(1.5.seconds)
                    println("FLOW: finish eating $it")
                }
        }
    }
}