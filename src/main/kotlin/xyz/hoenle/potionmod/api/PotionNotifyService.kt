package xyz.hoenle.potionmod.api

import xyz.hoenle.potionmod.api.http.PotionHttpService
import xyz.hoenle.potionmod.light.PotionLightState

interface PotionNotifyService {
    fun updateLightState(state: PotionLightState)
}

class PotionNotifyServiceRegistry {
    private val potionNotifyServices: List<PotionNotifyService> = listOf(PotionHttpService())

    fun updateLightState(state: PotionLightState) {
        potionNotifyServices.forEach { it.updateLightState(state) }
    }
}