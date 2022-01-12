package subaraki.badbone.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CommonEffectArthritis extends MobEffect implements NotCurable {
    public CommonEffectArthritis() {
        super(MobEffectCategory.NEUTRAL, 0x423636);
    }

    public String getName() {
        return "effect.arthritis";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }
}