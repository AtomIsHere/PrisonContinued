package com.github.atomishere.prisoncontinued.prisonenchantments.enchantments;

import lombok.Getter;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;

import static org.bukkit.enchantments.Enchantment.registerEnchantment;

public enum CustomEnchants {
    TEST_ENCHANT(new TestEnchant(101));

    @Getter
    private final CustomEnchant enchant;
    @Getter
    private static boolean registered = false;

    private CustomEnchants(CustomEnchant enchant) {
        this.enchant = enchant;
    }

    public static Result registerEnchantments() {
        if(registered) return Result.ALREADY_REGISTERED;

        try {
            Field fieldAcceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            fieldAcceptingNew.setAccessible(true);
            fieldAcceptingNew.set(null, true);

            for(CustomEnchants enchant : CustomEnchants.values()) {
                registerEnchantment(enchant.getEnchant());
            }

            fieldAcceptingNew.set(null, false);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            return Result.FIELD_VALUE_FAILURE;
        } catch(IllegalStateException ex) {
            return Result.NOT_ACCEPTING_NEW;
        }

        registered = true;
        return Result.SUCCESS;
    }

    public static enum Result {
        ALREADY_REGISTERED,
        FIELD_VALUE_FAILURE,
        NOT_ACCEPTING_NEW,
        SUCCESS
    }
}
