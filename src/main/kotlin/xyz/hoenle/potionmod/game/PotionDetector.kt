package xyz.hoenle.potionmod.game

import net.minecraft.entity.boss.BossBar
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import org.slf4j.LoggerFactory
import xyz.hoenle.potionmod.light.PotionLightColor
import xyz.hoenle.potionmod.light.PotionLightColorMapper
import xyz.hoenle.potionmod.light.PotionLightState

class PotionDetector {
    private val potionColorMapper = PotionLightColorMapper()

    fun checkPotionInHand(players: List<ServerPlayerEntity>): PotionLightState {
        players.forEach { player ->
            val mainHand = player.mainHandStack
            val offHand = player.offHandStack // second hand

            listOf(mainHand, offHand).forEach { hand ->
                if (isPotion(hand)) {
                    val color = potionColorMapper.getPotionLightColor(hand)
                    return PotionLightState(true, color)
                }
            }
        }

        return PotionLightState(false, PotionLightColor.RED)
    }

    private fun isPotion(stack: ItemStack): Boolean {
        return stack.item === Items.POTION || stack.item === Items.SPLASH_POTION || stack.item === Items.LINGERING_POTION
    }
}