package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record ImportBlueprintPayload(String name, String data, Map<String, Set<String>> mappings) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "import_blueprint");

    public static ImportBlueprintPayload decode(FriendlyByteBuf buf) {
        String name = buf.readUtf();
        String data = buf.readUtf(1048576);
        Map<String, Set<String>> map = buf.readMap(
            HashMap::new,
            FriendlyByteBuf::readUtf,
            b -> b.readCollection(HashSet::new, FriendlyByteBuf::readUtf)
        );
        return new ImportBlueprintPayload(name, data, map);
    }

    public static void encode(FriendlyByteBuf buf, ImportBlueprintPayload payload) {
        buf.writeUtf(payload.name());
        buf.writeUtf(payload.data(), 1048576);
        buf.writeMap(payload.mappings(),
            FriendlyByteBuf::writeUtf,
            (b, set) -> b.writeCollection(set, FriendlyByteBuf::writeUtf)
        );
    }
}
