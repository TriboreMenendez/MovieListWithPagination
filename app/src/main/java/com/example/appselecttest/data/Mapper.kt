package com.example.appselecttest.data

import com.example.appselecttest.data.models.MovieApiModel
import com.example.appselecttest.domain.model.MovieDomainModel

fun MovieApiModel.toDomain(): MovieDomainModel {
    return MovieDomainModel(
        displayTitle = this.displayTitle,
        publicationDate = this.publicationDate,
        summaryShort = this.summaryShort,
        imageUrl = this.multimedia.urlImage
    )
}