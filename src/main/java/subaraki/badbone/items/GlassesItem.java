package subaraki.badbone.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Wearable;

import javax.annotation.Nullable;

public class GlassesItem extends Item implements Wearable {
    private static final Properties PROPERTIES = new Properties().tab(CreativeModeTab.TAB_TOOLS);

    public GlassesItem() {
        super(PROPERTIES);
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
