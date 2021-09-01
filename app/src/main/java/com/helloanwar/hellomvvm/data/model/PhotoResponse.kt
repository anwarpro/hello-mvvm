package com.helloanwar.hellomvvm.data.model

class PhotoResponse : ArrayList<PhotoResponseItem>() {
    companion object {
        fun demo(): PhotoResponse {
            return PhotoResponse().also {
                it.addAll(
                    listOf(
                        PhotoResponseItem(
                            1,
                            1,
                            "https://via.placeholder.com/150/771796",
                            "Demo image",
                            "https://via.placeholder.com/600/771796"
                        ),
                        PhotoResponseItem(
                            1,
                            1,
                            "https://via.placeholder.com/150/771796",
                            "Demo image",
                            "https://via.placeholder.com/600/771796"
                        )
                    )
                )
            }
        }
    }
}