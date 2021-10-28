package subaraki.badbone.mod;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigData {

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static int frequencyHurt = 24_000 / 3;
    public static int chanceHurt = 10;
    public static int frequencyKnee = 10;
    public static int frequencyArthritis = 24_000;
    public static int frequencyEyes = 24_000;


    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static void refreshClient() {

    }

    public static void refreshServer() {

        frequencyHurt = SERVER.freqHurt.get();
        frequencyKnee = SERVER.freqKnee.get();
        frequencyArthritis = SERVER.freqArth.get();
        frequencyEyes = SERVER.freqEyes.get();
        chanceHurt = SERVER.chanHurt.get();

    }

    public static class ServerConfig {

        public final ForgeConfigSpec.IntValue freqHurt;
        public final ForgeConfigSpec.IntValue freqKnee;
        public final ForgeConfigSpec.IntValue freqArth;
        public final ForgeConfigSpec.IntValue freqEyes;
        public final ForgeConfigSpec.IntValue chanHurt;

        ServerConfig(ForgeConfigSpec.Builder builder) {

            builder.push("general");
            freqHurt = builder.comment("frequency at which the inventory gets checked for backpain (values in ticks)").defineInRange("backpain_frequency", 24_000 / 3, 1, 24_000 * 10);
            chanHurt = builder.comment("chance to have back pain applied when inventory is too full").defineInRange("backpain_chance", 10, 1, 1000);
            freqKnee = builder.comment("frequency at which your knees can go bad when falling (values in ticks)").defineInRange("bad_knees_frequency", 10, 1, 1000);
            freqArth = builder.comment("frequency at which arthritis will occur when using items (values in ticks)").defineInRange("arthritis_frequency", 24_000 / 2, 1, 24_000 * 10);
            freqEyes = builder.comment("frequency at which your eyes will go bad (values in ticks)").defineInRange("blurry_eyes_frequency", 24_000 * 3, 1, 24_000 * 30);

            builder.pop();
        }
    }

    public static class ClientConfig {

        ClientConfig(ForgeConfigSpec.Builder builder) {

        }
    }
}