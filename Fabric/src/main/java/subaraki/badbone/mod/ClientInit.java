package subaraki.badbone.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import subaraki.badbone.client.BadBoneShaders;
import subaraki.badbone.client.FabricBadBoneShaders;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(FabricBadBoneShaders.INSTANCE);
        BadBoneShaders.INSTANCE = FabricBadBoneShaders.INSTANCE;
    }
}
