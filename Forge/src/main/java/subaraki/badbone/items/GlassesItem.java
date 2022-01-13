package subaraki.badbone.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class GlassesItem extends CommonGlassesItem {
    private static final Properties PROPERTIES = new Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1);

    public GlassesItem() {
        super(PROPERTIES);
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
