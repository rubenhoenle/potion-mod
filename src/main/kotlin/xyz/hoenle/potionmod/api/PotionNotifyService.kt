package xyz.hoenle.potionmod.api

import xyz.hoenle.potionmod.light.PotionLightState

interface PotionNotifyService {
    fun updateLightState(state: PotionLightState)
}