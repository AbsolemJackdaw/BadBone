package subaraki.badbone.platform;

import subaraki.badbone.BadBone;

import java.util.ServiceLoader;

public class Services {

    public static final IClientHelper CLIENT_HELPER = load(IClientHelper.class);
    public static final IEffectHelper EFFECT_HELPER = load(IEffectHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        BadBone.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
