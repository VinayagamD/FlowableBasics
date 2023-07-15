package com.vinaylogics.flowablebasics.bl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
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
        val flow1 = (1..5).asFlow()

        viewModelScope.launch {
            flow1.flatMapConcat { value ->
                flow {
                    emit(value+1)
                    delay(0.5.seconds)
                    emit(value+2)
                }
            }.collect {value ->
                println("The value is $value")

            }
        }
    }
}