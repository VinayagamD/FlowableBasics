package com.vinaylogics.flowablebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinaylogics.flowablebasics.bl.viewmodels.MainViewModel
import com.vinaylogics.flowablebasics.ui.theme.FlowableBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowableBasicsTheme {
                val viewModel = viewModel<MainViewModel>()
               val time = viewModel.countDownFlow.collectAsState(initial = 10)
                Box(modifier=Modifier.fillMaxSize()){
                   Text(
                       text = time.value.toString(),
                       fontSize = 30.sp,
                       modifier = Modifier.align(Alignment.Center)

                   )
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