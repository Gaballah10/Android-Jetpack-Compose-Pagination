package com.example.smartzonetestcompose.ui.fragments.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.res.ResourcesCompat
import coil.compose.rememberImagePainter
import com.example.smartzonetestcompose.network.model.Article
import com.example.smartzonetestcompose.R

@Composable
fun NewsRow(
    modifier: Modifier = Modifier,
    onDetailsClicked: (Article) -> Unit,
    article: Article,
    onAddClick: (Article) -> Unit
) {
    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .padding(6.dp)
            .height(130.dp)
            .clickable {
                onDetailsClicked.invoke(article)
            }, elevation = 8.dp
    ) {
        ConstraintLayout {
            val (image, titleText, publishedText, buttonInsert) = createRefs()
            val startGuideLine = createGuidelineFromStart(10.dp)

            Image(
                painter = rememberImagePainter(article.urlToImage),
                contentDescription = "News Image",
                modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(100.dp)
                    .height(100.dp)

                    .constrainAs(image) {
                        start.linkTo(startGuideLine)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentScale = ContentScale.Crop
            )
            Text(
                text = article.title,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(start = 10.dp)
                    .constrainAs(titleText) {
                        start.linkTo(image.end)
                        top.linkTo(image.top)
                    },
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 2,
                fontStyle = FontStyle.Normal,
                fontFamily = ernestineFont,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.publishedAt,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 3.dp)
                    .constrainAs(publishedText) {
                        start.linkTo(image.end)
                        top.linkTo(titleText.bottom)
                    },
                color = Color.Black,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Button(modifier = modifier
                .padding(start = 10.dp)
                .width(100.dp)
                .height(30.dp)
                .constrainAs(buttonInsert) {
                    start.linkTo(image.end)
                    bottom.linkTo(image.bottom)
                }, onClick = { onAddClick(article) }) {
                Text(
                    text = "INSERT",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}