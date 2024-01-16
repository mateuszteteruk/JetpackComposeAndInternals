@file:OptIn(ExperimentalMaterialApi::class)

package dev.jorgecastillo.compose.app.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.jorgecastillo.compose.app.R
import dev.jorgecastillo.compose.app.data.FakeSpeakerRepository
import dev.jorgecastillo.compose.app.models.Speaker
import dev.jorgecastillo.compose.app.ui.theme.ComposeAndInternalsTheme

@Composable
fun SpeakersScreen(speakers: List<Speaker>) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Speakers") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.Add),
                    contentDescription = stringResource(id = R.string.content_desc_fab_add_speaker),
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .testTag("SpeakersList")
        ) {
            for (speaker in speakers) {
                SpeakerCard(speaker = speaker)
            }
        }
    }
}

@Composable
fun SpeakerCard(speaker: Speaker, onClick: (Speaker) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_small)),
        onClick = { onClick(speaker) }
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_regular))
        ) {
            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.avatar_size))
                    .clip(CircleShape),
                painter = painterResource(id = avatarResForId(id = speaker.id)),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(
                    id = R.string.content_desc_speaker_avatar,
                    speaker.name
                ),
            )
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_regular))
            ) {
                Text(
                    text = speaker.name,
                    style = MaterialTheme.typography.h6,
                )
                Text(
                    text = speaker.company,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun avatarResForId(id: String): Int {
    val localContext = LocalContext.current
    return localContext.resources
        .getIdentifier("avatar_$id", "drawable", localContext.packageName)
}

@Composable
@Preview(showBackground = true)
private fun SpeakersScreenPreview() {
    ComposeAndInternalsTheme {
        SpeakersScreen(speakers = FakeSpeakerRepository().getSpeakers())
    }
}
