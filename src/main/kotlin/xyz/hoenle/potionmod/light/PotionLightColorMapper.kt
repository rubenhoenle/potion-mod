package xyz.hoenle.potionmod.light

import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.Potions
import net.minecraft.registry.entry.RegistryEntry

class PotionLightColorMapper {
    private val registeredPotions: HashMap<RegistryEntry<Potion>, PotionLightColor> = hashMapOf(
        // healing potions
        Potions.HEALING to PotionLightColor.RED,
        Potions.STRONG_HEALING to PotionLightColor.RED,
        // leaping potions
        Potions.LEAPING to PotionLightColor.GREEN,
        Potions.LONG_LEAPING to PotionLightColor.GREEN,
        Potions.STRONG_LEAPING to PotionLightColor.GREEN,
        // water
        Potions.WATER to PotionLightColor.BLUE,
        // water breathing
        Potions.WATER_BREATHING to PotionLightColor.BLUE,
        Potions.LONG_WATER_BREATHING to PotionLightColor.BLUE
    )

    private val defaultColor = PotionLightColor.YELLOW

    fun getPotionLightColor(potion: ItemStack): PotionLightColor {
        val potionContent = potion.get(DataComponentTypes.POTION_CONTENTS)
        potionContent?.let {
            registeredPotions.forEach { (potionType, color) ->
                it.potion?.get()?.let {
                    if (it == potionType) {
                        return color
                    }
                }
            }
        }
        /*if (potionContent?.potion == Potions.HEALING) {
            return PotionLightColor.RED
        }*/
        return defaultColor
    }
}