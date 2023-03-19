package com.example.smartzonetestcompose.ui.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartzonetestcompose.R
import com.example.smartzonetestcompose.components.InputSearchField
import com.example.smartzonetestcompose.components.showToast
import com.example.smartzonetestcompose.navigation.SmartScreens


@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(showBackground = true)
fun SearchScreen(navController: NavController = rememberNavController()) {

    val context = LocalContext.current
    val searchValue = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val ernestineFont = remember {
        FontFamily(
            typeface = ResourcesCompat.getFont(context, R.font.ernestine_font)!!
        )
    }
    val valid = remember(searchValue.value) {
        searchValue.value.trim().isNotEmpty()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (searchCard, instructionCard) = createRefs()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .constrainAs(searchCard) {
                    top.linkTo(parent.top)
                },

            shape = RoundedCornerShape(0),
            elevation = 0.dp,
            backgroundColor = colorResource(id = R.color.brown_modified)
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                val (searchField, searchIconImage) = createRefs()

                InputSearchField(
                    Modifier
                        .fillMaxWidth(.9f)
                        .wrapContentHeight()
                        .padding(start = 15.dp, top = 8.dp)
                        .constrainAs(searchField) {
                            top.linkTo(searchCard.top)
                            bottom.linkTo(searchCard.bottom)
                            start.linkTo(searchCard.start)
                        },
                    searchValue,
                    stringResource(id = R.string.search),
                    true,
                    true,
                    onAction = KeyboardActions {
                        if (!valid) return@KeyboardActions
                        onSearch(searchValue.value)
                        searchValue.value = ""
                        keyboardController?.hide()
                    })

                IconButton(
                    onClick = {
                          if (searchValue.value.isEmpty()){
                              searchValue.value = "empty"
                          }
                        navController.navigate(SmartScreens.HomeScreen.name + "/${searchValue.value}")
                    },
                    modifier = Modifier
                        .padding(end = 10.dp, start = 50.dp)
                        .constrainAs(searchIconImage) {
                            top.linkTo(searchCard.top)
                            bottom.linkTo(searchCard.bottom)
                            end.linkTo(searchCard.end)
                            start.linkTo(searchField.end)
                        }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(top = 10.dp, start = 8.dp, end = 8.dp)
                .constrainAs(instructionCard) {
                    top.linkTo(searchCard.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, backgroundColor = colorResource(id = R.color.grey)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.search_tip),
                    Modifier
                        .padding(5.dp)
                        .wrapContentWidth(),
                    color = Color.White,
                    fontFamily = ernestineFont, fontSize = 14.sp
                )
            }
        }
    }
}

fun onSearch(searchValue: String) {
    Log.d("ValueofSearching", searchValue)
}
