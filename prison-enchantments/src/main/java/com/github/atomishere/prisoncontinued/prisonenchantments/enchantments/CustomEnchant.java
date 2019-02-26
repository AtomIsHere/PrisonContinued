package com.github.atomishere.prisoncontinued.prisonenchantments.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

//Thanks to the spigot forums mod 2008Choco for the custom enchant framework. Here is the thread if you're interested https://www.spigotmc.org/threads/making-a-custom-enchantment.226403/
public abstract class CustomEnchant extends Enchantment {

    public CustomEnchant(int id) {
        super(id);
    }

    /**
     * <p>See if the enchantment can enchant an item.</p>
     *
     * @param item <p>The item to enchant</p>
     * @return <p>If the enchantment can enchant the item.</p>
     */
    public abstract boolean canEnchantItem(ItemStack item);

    /**
     * <p>What type of items the enchantment can enchant</p>
     *
     * @return <p>The type of item</p>
     */
    public abstract EnchantmentTarget getItemTarget();

    /**
     * <p>Get the maximum level that the enchantment can go up to</p>
     */
    public abstract int getMaxLevel();

    /**
     * <p>Get the enchantment name</p>
     */
    public abstract String getName();

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }
}
