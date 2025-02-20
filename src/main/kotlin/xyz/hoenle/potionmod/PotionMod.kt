package xyz.hoenle.potionmod

import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import org.slf4j.LoggerFactory
import xyz.hoenle.potionmod.api.PotionNotifyServiceRegistry
import xyz.hoenle.potionmod.game.PotionDetector
import xyz.hoenle.potionmod.light.PotionLightColorMapper

object PotionMod : DedicatedServerModInitializer {
    private val logger = LoggerFactory.getLogger("potion-mod")

	private val potionDetector = PotionDetector()
	private val potionNotifyServiceRegistry = PotionNotifyServiceRegistry()

	override fun onInitializeServer() {
		logger.info("Hello Fabric world!")

		ServerTickEvents.START_SERVER_TICK.register(ServerTickEvents.StartTick { server: MinecraftServer ->
			val lightState = potionDetector.checkPotionInHand(server.playerManager.playerList)
			potionNotifyServiceRegistry.updateLightState(lightState)
		})
	}


}