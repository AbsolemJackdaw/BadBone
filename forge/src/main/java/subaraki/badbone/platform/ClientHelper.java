package subaraki.badbone.platform;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;

import java.util.List;

public class ClientHelper implements IClientHelper {
    @Override
    public int getPostChainScreenWidth(PostChain chain) {
        return chain.screenWidth;
    }

    @Override
    public int getPostChainScreenHeight(PostChain chain) {
        return chain.screenHeight;
    }

    @Override
    public List<PostPass> getPasses(PostChain chain) {
        return chain.passes;
    }
}
