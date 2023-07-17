package subaraki.badbone.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EffectWeakKnees extends MobEffect implements NotCurable {
    public EffectWeakKnees() {
        super(MobEffectCategory.NEUTRAL, 0x423636);
    }

    public String getName() {
        return "effect.kneepain";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}