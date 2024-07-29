package com.ecoapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ecoapp.R
import com.ecoapp.data.model.EcoDetail
import com.ecoapp.presentation.theme.PurpleGrey80

@Composable
fun EcoItemDetail(item: EcoDetail) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.card_margin))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.radius_12)))
            .background(PurpleGrey80)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.fab_margin))) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = stringResource(id = R.string.list_item_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.view_margin_50)) // Size of the image
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.view_margin_top_10)))
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.card_margin)))
            Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.card_margin))) {
                Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                Text(text = item.subTitle, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Preview
@Composable
fun EcoItemDetailPreview() {
    val item = EcoDetail(
        id = 1, // replace with your sample image resource
        title = stringResource(id = R.string.app_name),
        subTitle = stringResource(id = R.string.app_name),
        imageResId = R.drawable.bird
    )
    EcoItemDetail(item = item)
}