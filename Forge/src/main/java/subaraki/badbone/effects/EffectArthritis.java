package subaraki.badbone.effects;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EffectArthritis extends CommonEffectArthritis {

    public String getName() {
        return "effect.arthritis";
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