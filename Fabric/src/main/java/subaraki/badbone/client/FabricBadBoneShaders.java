package subaraki.badbone.client;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import subaraki.badbone.BadBone;

public class FabricBadBoneShaders extends BadBoneShaders implements IdentifiableResourceReloadListener {
    public static final FabricBadBoneShaders INSTANCE = new FabricBadBoneShaders();

    private FabricBadBoneShaders() {
        super();
    }

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(BadBone.MODID);
    }
}