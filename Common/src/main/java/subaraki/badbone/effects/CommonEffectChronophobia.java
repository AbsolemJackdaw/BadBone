package subaraki.badbone.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CommonEffectChronophobia extends MobEffect implements NotCurable {
    public CommonEffectChronophobia() {
        super(MobEffectCategory.NEUTRAL, 0x423636);
    }

    public String getName() {
        return "effect.chrono";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }
}