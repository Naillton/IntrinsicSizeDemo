package com.nailton.intrinsicsizedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.nailton.intrinsicsizedemo.ui.theme.IntrinsicSizeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntrinsicSizeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

/**
 * Projeto criado com o intuito de usar constraintSet criando uma funcao que tera todos as definicoes
 * de constraints, tirando o codigo da MainScreen() e usando instrinctSize para definirmos o tamanho
 * do container pai em relacao a seus filhos, ou seja, quanto mais os filhos crescem dentro do container
 * mais o container pai aumenta de tamanho.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen() {
    var state by rememberSaveable { mutableStateOf("") }
    val onChange = {it: String ->
        state = it
    }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ) {
           val constraint = myConstraintSet()
            ConstraintLayout(
                constraint,
                Modifier
                    .background(Color.Yellow)
            ) {
                val (textTitle, edtText, contentText) = createRefs()
                TextFunc(text = "InstrinctSize Demo", Modifier.layoutId("textTitle"))
                EdtText(text = state, textChange = onChange, modifier = Modifier.layoutId("edtText"))
                TextContent(text = state, modifier = Modifier.layoutId("contentText") )
            }
    }
}


@Composable
private fun TextFunc(text: String, modifier: Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                )
            ) {
                append(text)
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EdtText(
    text: String,
    textChange: (String) -> Unit,
    modifier: Modifier
    ) {
    OutlinedTextField(
        value = text,
        onValueChange = textChange,
        modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        placeholder = { Text(text = "Digite Algo")},
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily.Serif
        )
    )
}

@Composable
private fun TextContent(text: String, modifier: Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
            ) {
                append(text)
            }
        },
        modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .background(Color.Cyan)
            .padding(10.dp)
    )
}

private fun myConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val textTitle = createRefFor("textTitle")
        val edtText = createRefFor("edtText")
        val contentText = createRefFor("contentText")

        constrain(textTitle) {
            top.linkTo(parent.top, 14.dp)
            centerHorizontallyTo(parent)
        }

        constrain(edtText) {
            top.linkTo(textTitle.bottom, 30.dp)
            centerHorizontallyTo(parent)
        }

        constrain(contentText) {
            top.linkTo(edtText.bottom, 30.dp)
            linkTo(parent.start, parent.end, startMargin = 30.dp, endMargin = 20.dp)
            bottom.linkTo(parent.bottom, 20.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntrinsicSizeDemoTheme {
        MainScreen()
    }
}