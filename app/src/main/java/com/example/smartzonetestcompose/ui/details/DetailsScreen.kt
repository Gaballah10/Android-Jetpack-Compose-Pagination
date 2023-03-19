package com.example.smartzonetestcompose.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.smartzonetestcompose.R
import com.example.smartzonetestcompose.components.BackAppBar

@Composable
@Preview(showBackground = true)
fun DetailsScreen(
    navController: NavHostController = rememberNavController(),
    articleTitle: String? = "Title",
    imageUrl: String? = "https://cdn.mos.cms.futurecdn.net/ipnvHq3EDWGLRDGVhWmEjC-1200-80.jpg",
    publishedAt: String? = " publish at",
    content: String? = " Content"
) {

    val context = LocalContext.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            BackAppBar("Details", modifier = Modifier) {
                navController.popBackStack()
            }

            Image(
                painter = rememberImagePainter(imageUrl.toString()),
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(start = 5.dp, end = 5.dp, top = 10.dp),
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                ConstraintLayout {

                    val (articlePublishedAt, articleContent, title) = createRefs()               //--- Ref ...

                    Text(
                        text = articleTitle.toString(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(end = 20.dp, top = 20.dp, start = 20.dp)
                            .constrainAs(title) {
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                            },
                        color = Color.Black,
                        fontSize = 16.sp,
                        maxLines = 3,
                        fontStyle = FontStyle.Normal,
                        fontFamily = ernestineFont,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = publishedAt.toString(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(end = 10.dp, top = 14.dp)
                            .constrainAs(articlePublishedAt) {
                                end.linkTo(parent.end)
                                top.linkTo(title.bottom)
                            },
                        color = Color.Black.copy(0.4f),
                        fontSize = 12.sp,
                        maxLines = 2,
                        fontStyle = FontStyle.Normal,
                        fontFamily = ernestineFont,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = content.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp, top = 14.dp, start = 10.dp)
                            .constrainAs(articleContent) {
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                                top.linkTo(articlePublishedAt.bottom)
                            },
                        color = Color.Black.copy(0.75f),
                        fontSize = 14.sp,
                        maxLines = 15,
                        fontStyle = FontStyle.Normal,
                        fontFamily = ernestineFont,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}