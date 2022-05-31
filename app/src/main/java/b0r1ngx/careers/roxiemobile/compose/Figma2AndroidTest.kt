package b0r1ngx.careers.roxiemobile.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DesignTest() =
    Box(
        modifier = Modifier
            .width(360.dp)
            .height(800.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(
                Color(
                    red = 0.12615346908569336f,
                    green = 0.11574654281139374f,
                    blue = 0.24583333730697632f,
                    alpha = 1f
                )
            )
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
            .alpha(1f)
    ) {
        Column {
            Text(
                text = "Предыдущие поездки",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
//                    .align(Alignment.TopStart)
                    .width(213.dp)

                    //.height(28.dp)

                    .alpha(1f),
                color = Color(red = 1f, green = 1f, blue = 1f, alpha = 1f),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
            Text(
                text = "Санкт-Петербург",
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
//                    .align(Alignment.TopStart)
                    .width(168.dp)

                    //.height(28.dp)

                    .alpha(1f),
                color = Color(red = 1f, green = 1f, blue = 1f, alpha = 1f),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
            Box(
                modifier = Modifier
                    .width(294.dp)
                    .height(108.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Color(red = 1f, green = 1f, blue = 1f, alpha = 1f))
            )
        }
    }