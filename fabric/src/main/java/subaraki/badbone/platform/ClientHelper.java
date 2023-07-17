package subaraki.badbone.platform;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import subaraki.badbone.mixin.AccessorPostChain;

import java.util.List;

public class ClientHelper implements IClientHelper {
    @Override
    public int getPostChainScreenWidth(PostChain chain) {
        return ((AccessorPostChain) chain).getScreenWidth();
    }

    @Override
    public int getPostChainScreenHeight(PostChain chain) {
        return ((AccessorPostChain) chain).getScreenHeight();
    }

    @Override
    public List<PostPass> getPasses(PostChain chain) {
        return ((AccessorPostChain) chain).getPasses();
    }
}
