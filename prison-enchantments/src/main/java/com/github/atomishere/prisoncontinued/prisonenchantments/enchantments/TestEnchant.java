package com.github.atomishere.prisoncontinued.prisonenchantments.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

//Enchantment designed to test the custom enchantment system.
public class TestEnchant extends CustomEnchant {
    public TestEnchant(int id) {
        super(101);
    }

    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    public int getMaxLevel() {
        return 1;
    }

    public String getName() {
        return "Test";
    }
}
