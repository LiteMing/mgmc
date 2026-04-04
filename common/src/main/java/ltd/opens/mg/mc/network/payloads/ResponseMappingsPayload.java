package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record ResponseMappingsPayload(Map<String, Set<String>> mappings) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "response_mappings");

    public static ResponseMappingsPayload decode(FriendlyByteBuf buf) {
        Map<String, Set<String>> map = buf.readMap(
            HashMap::new,
            FriendlyByteBuf::readUtf,
            b -> b.readCollection(HashSet::new, FriendlyByteBuf::readUtf)
        );
        return new ResponseMappingsPayload(map);
    }

    public static void encode(FriendlyByteBuf buf, ResponseMappingsPayload payload) {
        buf.writeMap(payload.mappings(),
            FriendlyByteBuf::writeUtf,
            (b, set) -> b.writeCollection(set, FriendlyByteBuf::writeUtf)
        );
    }
}
