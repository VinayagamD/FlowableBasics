package com.vinaylogics.flowablebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinaylogics.flowablebasics.bl.viewmodels.MainViewModel
import com.vinaylogics.flowablebasics.ui.theme.FlowableBasicsTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowableBasicsTheme {
                val viewModel = viewModel<MainViewModel>()
               val count = viewModel.stateFlow.collectAsState(initial = 0)
                Box(modifier=Modifier.fillMaxSize()){
                   Button(onClick = {
                       viewModel.incrementCounter()
                   }) {
                       Text(text = "Counter: ${count.value}")
                   }
               }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlowableBasicsTheme {
        Greeting("Android")
    }
}

fun <T> ComponentActivity.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend(T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}
fun <T> ComponentActivity.collectLifecycleFlow(flow: Flow<T>, collect: suspend(T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}
