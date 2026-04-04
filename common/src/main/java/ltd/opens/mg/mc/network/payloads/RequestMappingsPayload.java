package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RequestMappingsPayload() {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "request_mappings");

    public static RequestMappingsPayload decode(FriendlyByteBuf buf) {
        return new RequestMappingsPayload();
    }

    public static void encode(FriendlyByteBuf buf, RequestMappingsPayload payload) {
    }
}
