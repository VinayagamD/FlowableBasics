package com.vinaylogics.flowablebasics.bl.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
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

    private fun collectFlow() {
        viewModelScope.launch {
            val reduceResult = countDownFlow
                .reduce { accumulator, value ->
                    accumulator + value
                }
            println("Reduce result is $reduceResult")
        }
    }
}