package com.nat.bless.screens.addNewClient.domain.models

data class TagsResponse(
    val code: Int,
    val `data`: List<TagsList>,
    val message: String
)

data class TagsList(
    val color: Int,
    val id: Int,
    val name: String
)