package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record ResponseExportPayload(String name, String data, Map<String, Set<String>> relatedMappings) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "response_export");

    public static ResponseExportPayload decode(FriendlyByteBuf buf) {
        String name = buf.readUtf();
        String data = buf.readUtf();
        Map<String, Set<String>> map = buf.readMap(
            HashMap::new,
            FriendlyByteBuf::readUtf,
            b -> b.readCollection(HashSet::new, FriendlyByteBuf::readUtf)
        );
        return new ResponseExportPayload(name, data, map);
    }

    public static void encode(FriendlyByteBuf buf, ResponseExportPayload payload) {
        buf.writeUtf(payload.name());
        buf.writeUtf(payload.data());
        buf.writeMap(payload.relatedMappings(),
            FriendlyByteBuf::writeUtf,
            (b, set) -> b.writeCollection(set, FriendlyByteBuf::writeUtf)
        );
    }
}
