package xyz.hoenle.potionmod

import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import org.slf4j.LoggerFactory
import xyz.hoenle.potionmod.light.PotionLightColorMapper

object PotionMod : DedicatedServerModInitializer {
    private val logger = LoggerFactory.getLogger("potion-mod")

	private val potionColorMapper = PotionLightColorMapper()

	override fun onInitializeServer() {
		logger.info("Hello Fabric world!")
		ServerTickEvents.START_SERVER_TICK.register(ServerTickEvents.StartTick { server: MinecraftServer ->
			for (player in server.playerManager.playerList) {
				checkPotionInHand(player)
			}
		})
	}

	private fun checkPotionInHand(player: ServerPlayerEntity) {
		val mainHand = player.mainHandStack
		val offHand = player.offHandStack // second hand

		if (isPotion(mainHand) || isPotion(offHand)) {
			logger.info(player.name.string + " is holding a potion!")
			val color = potionColorMapper.getPotionLightColor(mainHand)
			logger.info(color.name)
			mainHand.item
		}
	}

	private fun isPotion(stack: ItemStack): Boolean {
		return stack.item === Items.POTION || stack.item === Items.SPLASH_POTION || stack.item === Items.LINGERING_POTION
	}
}