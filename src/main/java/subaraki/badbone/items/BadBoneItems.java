package subaraki.badbone.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import subaraki.badbone.mod.BadBone;

public class BadBoneItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BadBone.MODID);

    public static final RegistryObject<Item> GLASSES = ITEMS.register("glasses", GlassesItem::new);

}
