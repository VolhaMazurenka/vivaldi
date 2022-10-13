package com.mazurenka.vivaldi.data.repository


import com.google.common.truth.Truth.assertThat
import com.mazurenka.vivaldi.data.model.AudioModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.mockito.kotlin.whenever

class AudioRepositoryTest {

    private val mapper = mockk<JsonToAudioModelsMapper>()
    private val jsonProvider = mockk<JsonProvider>()
    private val audioRepository = AudioRepository(mapper, jsonProvider)

    @Test
    fun getAudioList() {
        //arrange
        val expectedModels = listOf(
            AudioModel(
                "1",
                "Winter: First Movement",
                1,
                "Antonio Vivaldi",
                1,
                "The Four Seasons",
                "cover.jpg",
                198,
                "winter_0.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/0/04/Vivaldi_Winter_mvt_1_Allegro_non_molto_-_The_USAF_Concert.ogg"
            )
        )

        every { jsonProvider.getJson("media_list.json") }.answers { testJson }
        every { mapper.map(testJson) }.answers { expectedModels }

        //act
        val models = audioRepository.getAudioList()

        //assert
        assertThat(models == expectedModels).isTrue()
    }

    companion object {
        private const val testJson = """
            [
                {
                    "id": "1",
                    "title": "Winter: First Movement",
                    "author_id": "1",
                    "author_name": "Antonio Vivaldi",
                    "album_id": "1",
                    "album_name": "The Four Seasons",
                    "album_cover_uri": "cover.jpg",
                    "length": "198",
                    "image_uri": "winter_0.jpg",
                    "uri": "https://upload.wikimedia.org/wikipedia/commons/0/04/Vivaldi_Winter_mvt_1_Allegro_non_molto_-_The_USAF_Concert.ogg"
                }
            ]
            """
    }
}