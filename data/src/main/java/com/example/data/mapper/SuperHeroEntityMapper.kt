package com.example.data.mapper

import com.example.data.entity.SuperHeroEntity
import com.example.domain.model.SuperHeroModel

class SuperHeroEntityMapper: Mapper<SuperHeroEntity, SuperHeroModel> {
    override fun toOutput(input: SuperHeroEntity): SuperHeroModel =
        SuperHeroModel(input.id, input.name, input.description, input.image)

    override fun toInput(output: SuperHeroModel): SuperHeroEntity =
        SuperHeroEntity(name = output.name, description = output.description, image = output.image)
}