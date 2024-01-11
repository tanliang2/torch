package com.bear.torch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bear.torch.ui.theme.TorchTheme
import com.bear.torch.util.TorchUtil
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.android.play.core.review.testing.FakeReviewManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Open("Android")
//                        Close("Android")
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun showNativeGoogleRateDialog() {
//        val manager = ReviewManagerFactory.create(this)
        val manager = FakeReviewManager(this)

        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = task.result
                Log.d("tanliang","reviewInfo:${reviewInfo} }")
                val flow = manager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener { it ->
                    Log.d("tanliang","onComplete:${it}")
                }
            } else {
                // There was some problem, log or handle the error code.
                @ReviewErrorCode
                val reviewErrorCode = (task.exception as ReviewException).errorCode
                Log.d("tanliang","reviewErrorCode:${reviewErrorCode}")
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Open(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "打开",
            modifier = Modifier.combinedClickable (
                onClick = {
                    showNativeGoogleRateDialog()
                }
            ),
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TorchTheme {
            Open("Android")
        }
    }
}


//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun Close(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "关闭",
//        modifier = Modifier.combinedClickable (8
//            onClick = {
//                TorchUtil.close()
//            }
//        ),
//    )
//}

